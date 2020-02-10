package com.zxkj.hc.qspt.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.sql.Date;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zxkj.hc.qspt.controller.WxsmdlWebsocketController;
import com.zxkj.hc.qspt.orm.hcqspt.bean.consts.QsptConsts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.setu.framework.exception.BusinessRuntimeException;
import org.setu.framework.extension.sysfield.EntitySysFieldBuilder;
import org.setu.framework.jdbc.core.ConditionItem;
import org.setu.framework.session.controller.SetuSessionController;
import org.setu.framework.utils.CommonUtil;
import org.setu.framework.utils.encrypt.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyFlowEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyRecordEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyUserEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ShuidanEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.UploadtableEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.UserEntity;
import com.zxkj.hc.qspt.service.LoginService;
import org.springframework.util.StringUtils;
import sun.security.provider.MD5;

@Service("loginService")
public class LoginServiceImpl extends SetuSessionController implements LoginService{
	private transient static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Value("${wxxcx.appid}")
	private String xcxAppid;
	@Value("${wxxcx.secret}")
	private String xcxSecret;
	@Value("${wxgzh.appid}")
	private String gzhAppid;
	@Value("${wxgzh.secret}")
	private String gzhSecret;
	
	/**
	 * 首先判断  微信ID 是否存在 ，如果不存在新增用户信息，并返回记录
	 */
	@Override
	public ApplyUserEntity applyUserWxLogin(String userName,String wechatId,String wechatUnionId,boolean tag){
		ApplyUserEntity applyUserEntity=new ApplyUserEntity();
		applyUserEntity.setWechatId(wechatId);
		applyUserEntity.setWechatUnionid(wechatUnionId);
		List<ApplyUserEntity> list=applyUserEntity.queryListBySelf();
		if(list.size()==0) {
			applyUserEntity.setUserName(userName);
			applyUserEntity.setLoginTime(getTimeNow());
			applyUserEntity.insertSelf();
		}else {
			applyUserEntity=list.get(0);
			ApplyUserEntity applyUserTemp=new ApplyUserEntity();
			applyUserTemp.setLoginTime(getTimeNow());
			applyUserTemp.setId(applyUserEntity.getId());
			if(!userName.equals("") &&tag) {
				applyUserTemp.setUserName(userName);
			}
			applyUserTemp.updateBySelfId();
		}
		return applyUserEntity;
	}

	@Override
	public UserEntity userLogin(String userName, String password,String wxOpenid) {
		UserEntity user=new UserEntity();
		user.setUserName(userName);
		try{
			user.queryBySelf();
		}catch (Exception e){
			return null;
		}
		if(!user.getPassword().equals(Md5Util.encode(password,user.getSalt()))){
		    return null;
        }
		if(!StringUtils.isEmpty(wxOpenid)){
			UserEntity userEntity=new UserEntity();
			userEntity.setWxOpenid("");
			userEntity.setWxUnionid("");
			userEntity.addConditonItem(new ConditionItem("wx_openid","=",wxOpenid));
			userEntity.updateBySelfCondition();

			user.setWxOpenid(wxOpenid);
			user.updateBySelfId();
		}
		return user;
	}

	@Override
	public ApplyUserEntity bankLogin(String userId, String phoneNumber,String name) {
		ApplyUserEntity user = new ApplyUserEntity();
		user.setUserName(name);
		user.setPhoneNumber(phoneNumber);
		user.setWechatId(userId);
		if(CommonUtil.isNull(name)) {
			throw new BusinessRuntimeException("name 不能为空！");
		}
		if(CommonUtil.isNull(phoneNumber)) {
			throw new BusinessRuntimeException("phoneNumber 不能为空！");
		}
		if(CommonUtil.isNull(userId)) {
			throw new BusinessRuntimeException("userId 不能为空！");
		}
		try {
			user.queryBySelf();
			System.out.println("用戶信息存在");
		} catch (Exception e) {
			System.out.println("沒有用戶信息！");
			banksign(userId, phoneNumber, name);
		}
		
		ApplyRecordEntity apply = new ApplyRecordEntity();
		apply.addConditonItem(new ConditionItem("sqr_id", "=", userId));
		apply.addConditonItem(new ConditionItem("xt_zxbz", "=", EntitySysFieldBuilder.XT_ZXBZ_WZX));
		List<ApplyRecordEntity> applyList = apply.queryListBySelf();
		System.out.println("apply size:" +applyList.size());
		for(ApplyRecordEntity applyTemp:applyList) {
			ShuidanEntity shuidan = new ShuidanEntity();
			shuidan.setApplyId(applyTemp.getId());
			shuidan.queryBySelf();
			
			if(Date.valueOf(shuidan.getSkxjrq()).getTime()<Date.valueOf(getTimeNow()).getTime()) {
				applyTemp.setXtZxbz(EntitySysFieldBuilder.XT_ZXBZ_YZX);
				applyTemp.setId(applyTemp.getId());
				applyTemp.updateBySelfId();
				UploadtableEntity upload = new UploadtableEntity();
				upload.addConditonItem(new ConditionItem("applyId", "=", applyTemp.getId()));
				upload.setXtZxbz(EntitySysFieldBuilder.XT_ZXBZ_YZX);
				EntitySysFieldBuilder.getBuilder().setModifyOpField(upload);
				upload.updateBySelfCondition();
			}
		}
		
		return user;
	}
	
	@Override
	public ApplyUserEntity banksign(String userId, String phoneNumber,String name) {
		ApplyUserEntity user = new ApplyUserEntity();
		user.setUserName(name);
		user.setPhoneNumber(phoneNumber);
		user.setWechatId(userId);

		user.insertSelf();
		user.queryBySelf();
		//添加流程
		ApplyRecordEntity entity=new ApplyRecordEntity();
		entity.setSqrId(user.getId());
		entity.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
		entity.insertSelf();
		return user;
	}
	
	@Override
	//用前端传过来的code获取openid
	public String[] getXcxOpenid(String code) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+xcxAppid+"&secret="+xcxSecret+"&js_code="+code+"&grant_type=authorization_code";
		String result = "";
		String openid = "";
		String unionid = "";
	    BufferedReader in = null;
	    try {
	        URL realUrl = new URL(url);
	        // 打开和URL之间的连接
	        URLConnection connection = realUrl.openConnection();
	        // 建立实际的连接
	        connection.connect();
	        // 定义 BufferedReader输入流来读取URL的响应
	        in = new BufferedReader(new InputStreamReader(
	                connection.getInputStream()));
	        String line;
	        while ((line = in.readLine()) != null) {
	            result += line;
	        }
	        if(!result.equals("")) {
	        	JSONObject resultJson= JSON.parseObject(result);
	        	openid = resultJson.getString("openid");
				unionid = resultJson.getString("unionid");
	        }else {
	        	return null;
	        }
	        
	    } catch (Exception e) {
	     logger.error("",e);
	    }
	    // 使用finally块来关闭输入流
	    finally {
	        try {
	            if (in != null) {
	                in.close();
	            }
	        } catch (Exception e2) {
				logger.error("",e2);
	        }
	    }
	    return new String[]{openid,unionid};
	}

	@Override
	public String getGzhOpenid(String code) {
		try {
			String apiUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
			HttpClient httpClient = HttpClients.createDefault();
			HttpGet request = new HttpGet();
			request.setHeader("Content-Type", "application/x-www-form-urlencoded");
			request.setURI(new URI(apiUrl + "?appid=" + gzhAppid+"&secret="+gzhSecret+"&code="+code+"&grant_type=authorization_code"));
			HttpResponse response = null;
			response = httpClient.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
				JSONObject jsonObject=JSONObject.parseObject(responseStr);
				return jsonObject.getString("openid");
			}
		} catch (IOException | URISyntaxException e) {
			logger.error("",e);
		}
		return null;
	}

}
