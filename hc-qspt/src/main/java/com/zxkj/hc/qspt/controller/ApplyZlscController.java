package com.zxkj.hc.qspt.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyRecordEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.enums.Spzt;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity.FbDataFgjEntity;
import com.zxkj.hc.qspt.service.WxgzhService;
import com.zxkj.hc.qspt.setu.system.BeanUtil;

import io.netty.handler.codec.base64.Base64Decoder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.setu.framework.exception.BusinessRuntimeException;
import org.setu.framework.exception.BusinessWarnException;
import org.setu.framework.extension.sysfield.EntitySysFieldBuilder;
import org.setu.framework.jdbc.core.ConditionItem;
import org.setu.framework.session.controller.SetuSessionController;
import org.setu.framework.utils.CommonUtil;
import org.setu.framework.utils.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.FlowDataEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.FlowFileEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.FlowInfoEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.UploadtableEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.vo.ZlscVo;
import com.zxkj.hc.qspt.service.ApplyService;
import com.zxkj.hc.qspt.service.BankService;
import com.zxkj.hc.qspt.service.impl.ApplyServiceImpl;
import com.zxkj.hc.qspt.setu.system.SetuResult;
import com.zxkj.hc.qspt.setu.system.SetuSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;


@RestController()
@RequestMapping("/api/apply")
public class ApplyZlscController extends SetuSessionController{
	private transient static final Logger logger = LoggerFactory.getLogger(ApplyZlscController.class);

	@Autowired
	WxgzhService wxgzhService;


	@Resource
	ApplyService applyService;
	
	@Resource
    BankService bankService;

	/**
	  * 资料上传只针对文件
	 * @author 甘晓龙
	 * 2018年11月17日
	 * @throws IOException 
	 * @throws BusinessWarnException 
	 */
	@RequestMapping(value ="/zlsc",method = RequestMethod.POST, consumes ="multipart/form-data")
    @ResponseBody
    public ZlscVo zlsc(@RequestParam("file") MultipartFile  file,String applyId,String flowId,String infoId,String fileId,String flowType,String qmType,String tag) throws IOException, BusinessWarnException{
		if((CommonUtil.isNotNull(applyId))){
			//删除预览图片
			FlowFileEntity flowFileEntity = new FlowFileEntity();
			flowFileEntity.addConditonItem(new ConditionItem("apply_id","=",applyId));
			flowFileEntity.addConditonItem(new ConditionItem("file_bz","=",qmType));
			flowFileEntity.deleteBySelf();
		}
		Object[][] pressTexts = null;
		SetuSession session=(SetuSession) getSetuSession();
		ApplyService apply = new ApplyServiceImpl();
		ZlscVo zlscVo=new ZlscVo();
		pressTexts = apply.tphc(applyId, qmType,tag);
		if(file==null&&tag==null) {
			throw new BusinessWarnException("附件不能为空");
		}
		if(CommonUtil.isNotNull(fileId)) {
			applyService.updateFlowFile(fileId, file,qmType,pressTexts, applyId,tag);
		}else if(CommonUtil.isNotNull(infoId)) {
			FlowInfoEntity info = new FlowInfoEntity();
			info.setId(infoId);
			info.setXtZxbz("0");
			info.updateBySelfId();
			if(CommonUtil.isNull(applyId)) {
				throw new BusinessWarnException("申请记录ID不能为空");
			}
			applyService.createFlowFile(applyId,flowId, infoId, file, zlscVo,qmType,pressTexts,tag);
		}else if(CommonUtil.isNotNull(flowId)) {
			if(CommonUtil.isNull(applyId)) {
				throw new BusinessWarnException("申请记录ID不能为空");
			}
			applyService.createFlowInfoAndFlowFile(applyId, flowId, file, zlscVo,qmType,pressTexts,tag);
		}else {
			if(CommonUtil.isNull(flowType)) {
				throw new BusinessWarnException("附件类型不能为空");
			}
			if(CommonUtil.isNotNull(applyId)) {
				//新增流程
				applyService.createFlowAndFlowFile(applyId, session.getUserId(), flowType, file, zlscVo,qmType,pressTexts,tag);
			}else {
				//新增审核记录并上传图片
					applyService.createApplyRecordAndFlowFile(session.getUserId(),session.getUserName(), flowType, file, zlscVo,qmType,pressTexts,tag);
			}
		}

		return zlscVo;
    }
	
	/**
	 *图片合成 
	 */
	@RequestMapping("/tphc")
	@ResponseBody
	public ZlscVo tphc(String applyId,String flowId,String infoId,String fileId,String flowType,String qmType,String tag) throws IOException, BusinessWarnException{

		Object[][] pressTexts = null;
		SetuSession session=(SetuSession) getSetuSession();
		ApplyService apply = new ApplyServiceImpl();
		ZlscVo zlscVo=new ZlscVo();
		pressTexts = apply.tphc(applyId, qmType,tag);
		if(CommonUtil.isNotNull(fileId)) {
			applyService.updateFlowFile(fileId, null,qmType,pressTexts, applyId,tag);
		}else if(CommonUtil.isNotNull(infoId)) {
			if(CommonUtil.isNull(applyId)) {
				throw new BusinessWarnException("申请记录ID不能为空");
			}
			applyService.createFlowFile(applyId,flowId, infoId, null, zlscVo,qmType,pressTexts,tag);
		}else if(CommonUtil.isNotNull(flowId)) {
			if(CommonUtil.isNull(applyId)) {
				throw new BusinessWarnException("申请记录ID不能为空");
			}
			applyService.createFlowInfoAndFlowFile(applyId, flowId, null, zlscVo,qmType,pressTexts,tag);
		}else {
			if(CommonUtil.isNull(flowType)) {
				throw new BusinessWarnException("附件类型不能为空");
			}
			if(CommonUtil.isNotNull(applyId)) {
				//新增流程
				applyService.createFlowAndFlowFile(applyId, session.getUserId(), flowType, null, zlscVo,qmType,pressTexts,tag);
			}else {
				//新增审核记录并上传图片
				applyService.createApplyRecordAndFlowFile(session.getUserId(),session.getUserName(), flowType, null, zlscVo,qmType,pressTexts,tag);
			}
		}
		return zlscVo;
    }
	
	/**
	 * 删除流程中的所有图片
	 * @throws IOException
	 * @throws BusinessWarnException
	 */
	@RequestMapping("/deleteall")
	@ResponseBody
	public void deleteall(String infoId) throws IOException, BusinessWarnException{
		FlowFileEntity files = new FlowFileEntity();
		FlowDataEntity data = new FlowDataEntity();
		FlowInfoEntity info = new FlowInfoEntity();
		files.addConditonItem(new ConditionItem("info_id","=",infoId));
		data.addConditonItem(new ConditionItem("info_id","=",infoId));
		info.addConditonItem(new ConditionItem("id","=",infoId));
		files.setXtZxbz("1");
		data.setXtZxbz("1");
		info.setXtZxbz("1");
		files.updateBySelfCondition();
		data.updateBySelfCondition();
		info.updateBySelfCondition();
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String fileId) throws IOException, BusinessWarnException{
		FlowFileEntity files = new FlowFileEntity();
		files.addConditonItem(new ConditionItem("id","=",fileId));
		files.setXtZxbz("1");
		files.updateBySelfCondition();
	}
	
	/**
	 * 保存flow数据并且更新flow状态
	 * TODO 如果没有图片只有文字类的审核，目前设计不支持
	 */
	@RequestMapping("/nextflow")
    @ResponseBody
    public SetuResult nextflow(String applyId,String flowType,String flowId,String flowData,String tag) throws IOException, BusinessWarnException{
		SetuSession session=(SetuSession) getSetuSession();
		if(CommonUtil.isNull(applyId)) {
			throw new BusinessWarnException("申请记录ID不能为空");
		}
		if(CommonUtil.isNull(flowType)) {
			throw new BusinessWarnException("附件类型不能为空");
		}
		List<FlowDataEntity> flowDataListPack=new ArrayList<>();
		if(CommonUtil.isNotNull(flowData)) {
			JsonHelper.parseJson2Collection(flowData, flowDataListPack,FlowDataEntity.class);
		}
		if(CommonUtil.isNull(flowId)&&CommonUtil.isNull(tag)) {
			throw new BusinessWarnException("流程ID不能为空");
		}else if(CommonUtil.isNull(flowId)&&"1".equals(tag)) {
			flowId = applyService.createFlowAndInfoWithoutFiles(applyId, session.getUserId(), flowType);
		}
		applyService.createFlowDataAndUpdateFlow(applyId, flowId, flowType, flowDataListPack);
		return new SetuResult("保存成功");
	}

	/**
	 * 资料审批提交
	 * @param applyId
	 * @param tgcqbc 跳过拆迁补偿
	 * @return
	 * @throws BusinessWarnException
	 */
	@RequestMapping("/zlspTj")
	@ResponseBody
	public SetuResult zlspTj(String applyId,String tgcqbc) throws BusinessWarnException {
		if(applyId==null) {
			throw new BusinessWarnException("审批记录ID不能为空");
		}
		ApplyRecordEntity entity=new ApplyRecordEntity();
		entity.setSpzt(Spzt.TJSH.getCode());
		entity.setSqsj(getTimeNow());
		if("1".equals(tgcqbc)){
			entity.setCqbc("1");
		}
		BeanUtil.setXgField(entity, getTimeNow());
		entity.addConditonItem(new ConditionItem("id","=",applyId));
		SetuSession session=(SetuSession) getSetuSession();
		entity.addConditonItem("and",new ConditionItem("sqr_id","=",session.getUserId()));
		if(entity.updateBySelfCondition()==0){
			throw new BusinessWarnException("操作失败");
		}
		HashMap<String, WxgzhService.TempleteValue> data=new HashMap<>();
		data.put("first",new WxgzhService.TempleteValue("","#000000"));
		data.put("keyword1",new WxgzhService.TempleteValue("资料审核","#000000"));
		data.put("keyword2",new WxgzhService.TempleteValue("已受理","#000000"));
		data.put("keyword3",new WxgzhService.TempleteValue(getTimeNow(),"#000000"));
		data.put("remark",new WxgzhService.TempleteValue("","#000000"));
		wxgzhService.sendTempleteMessage(session.getUserId(),"bPwMPsAleVGaASVgHs-dTeAxNklsdVNp4gZX6OUuRaY",data);
		return new SetuResult("审核已提交");
	}

	/**
	 *RAS解密
	 */
	public static String decrypt(String str, String privateKey) throws Exception{
		//64位解码加密后的字符串
		byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
		//base64编码的私钥
		byte[] decoded = Base64.decodeBase64(privateKey);  
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));  
		//RSA解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		String outStr;
		outStr = new String(cipher.doFinal(inputByte));
		return outStr;
	}
	
	@RequestMapping("/changeState")
	public SetuResult yfk(String applyId, String state, String reson) throws BusinessWarnException {
		
		try {
			applyId = decrypt(applyId, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJWgYXVdixGiz6tTW920PrZmZ6ZUxJ+NyQlMLw6hhjTVI8XrSqkWg7vPkMIzfDyAdR7+MhrvsoHg0HOlsdHVZ4rExYr4lDc/XWWS2QT4DK2GmKA7+7CpQmuzIlLZdIy3j8nB8gBLqO7p8GsN/TqRs00FMaeQtVD2C+Wr1PkO+rXtAgMBAAECgYEAlMlLwFqJnLrqiLI2qsq+hcBlF1g5wXNLUsyICqOCAe1kSj5EGsoBqvOnPgXWZ49RZ8Vb678pIc99WVKN9VuhPi0QbWH2xXLAO9jXoFf9km2j0A4qfIIcW3/NXca/XOcUru6diu6ALqT3J2zKMkCONZExm1QXuP6IsHWQzv+GaaECQQDUTUqPYQ1jI5hRwa6XSYiQe7I9Mwd9vPhLAYufOBURsodgUgVGXzy3wW6EP2zETVPBOVdvbi7Kqt57nG+jVwClAkEAtGyRNqWJaa/q/KO7dg7B8PJIKRC70ajCajvuk4hXxblY4pZit+elfNEuq6rRiwJWTfJxMPmV6QxAqVvumVrVqQJAMte7xnCYsyhgHxxCQqrykJ9/BRcGcT9jOxtIo73bldxqfU/HQZ4nPV6OqA9EbhKSv1CLNv1tKU4YcAP73GNGnQJAce7xEEoeaQ/+2wTl9xpAnE/TA6OehzoDcyRfduitTJ3LpGKE+k8csKJDz4HyCs8yAszrb5rccKsdFhWIqWyz6QJBALxKuPYWhaeGPTYVcuqggbNUuZS4dEblX639NMUN1oIi5ViekrQs2bYskUGXP/Wo9XaHBRWyRcDKWrx+S7ok/ng=\r\n");
			state = decrypt(state, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJWgYXVdixGiz6tTW920PrZmZ6ZUxJ+NyQlMLw6hhjTVI8XrSqkWg7vPkMIzfDyAdR7+MhrvsoHg0HOlsdHVZ4rExYr4lDc/XWWS2QT4DK2GmKA7+7CpQmuzIlLZdIy3j8nB8gBLqO7p8GsN/TqRs00FMaeQtVD2C+Wr1PkO+rXtAgMBAAECgYEAlMlLwFqJnLrqiLI2qsq+hcBlF1g5wXNLUsyICqOCAe1kSj5EGsoBqvOnPgXWZ49RZ8Vb678pIc99WVKN9VuhPi0QbWH2xXLAO9jXoFf9km2j0A4qfIIcW3/NXca/XOcUru6diu6ALqT3J2zKMkCONZExm1QXuP6IsHWQzv+GaaECQQDUTUqPYQ1jI5hRwa6XSYiQe7I9Mwd9vPhLAYufOBURsodgUgVGXzy3wW6EP2zETVPBOVdvbi7Kqt57nG+jVwClAkEAtGyRNqWJaa/q/KO7dg7B8PJIKRC70ajCajvuk4hXxblY4pZit+elfNEuq6rRiwJWTfJxMPmV6QxAqVvumVrVqQJAMte7xnCYsyhgHxxCQqrykJ9/BRcGcT9jOxtIo73bldxqfU/HQZ4nPV6OqA9EbhKSv1CLNv1tKU4YcAP73GNGnQJAce7xEEoeaQ/+2wTl9xpAnE/TA6OehzoDcyRfduitTJ3LpGKE+k8csKJDz4HyCs8yAszrb5rccKsdFhWIqWyz6QJBALxKuPYWhaeGPTYVcuqggbNUuZS4dEblX639NMUN1oIi5ViekrQs2bYskUGXP/Wo9XaHBRWyRcDKWrx+S7ok/ng=\r\n");
			reson = decrypt(reson, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJWgYXVdixGiz6tTW920PrZmZ6ZUxJ+NyQlMLw6hhjTVI8XrSqkWg7vPkMIzfDyAdR7+MhrvsoHg0HOlsdHVZ4rExYr4lDc/XWWS2QT4DK2GmKA7+7CpQmuzIlLZdIy3j8nB8gBLqO7p8GsN/TqRs00FMaeQtVD2C+Wr1PkO+rXtAgMBAAECgYEAlMlLwFqJnLrqiLI2qsq+hcBlF1g5wXNLUsyICqOCAe1kSj5EGsoBqvOnPgXWZ49RZ8Vb678pIc99WVKN9VuhPi0QbWH2xXLAO9jXoFf9km2j0A4qfIIcW3/NXca/XOcUru6diu6ALqT3J2zKMkCONZExm1QXuP6IsHWQzv+GaaECQQDUTUqPYQ1jI5hRwa6XSYiQe7I9Mwd9vPhLAYufOBURsodgUgVGXzy3wW6EP2zETVPBOVdvbi7Kqt57nG+jVwClAkEAtGyRNqWJaa/q/KO7dg7B8PJIKRC70ajCajvuk4hXxblY4pZit+elfNEuq6rRiwJWTfJxMPmV6QxAqVvumVrVqQJAMte7xnCYsyhgHxxCQqrykJ9/BRcGcT9jOxtIo73bldxqfU/HQZ4nPV6OqA9EbhKSv1CLNv1tKU4YcAP73GNGnQJAce7xEEoeaQ/+2wTl9xpAnE/TA6OehzoDcyRfduitTJ3LpGKE+k8csKJDz4HyCs8yAszrb5rccKsdFhWIqWyz6QJBALxKuPYWhaeGPTYVcuqggbNUuZS4dEblX639NMUN1oIi5ViekrQs2bYskUGXP/Wo9XaHBRWyRcDKWrx+S7ok/ng=\r\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BusinessRuntimeException("解密失败！");
		}
		
		if(CommonUtil.isNull(applyId)) {
			throw new BusinessWarnException("审批记录ID不能为空");
		}
		if(CommonUtil.isNull(state)) {
			throw new BusinessWarnException("状态码不能为空");
		}
		UploadtableEntity upload = new UploadtableEntity();
		ApplyRecordEntity entity=new ApplyRecordEntity();
		if("0".equals(state)) {
			upload.setIssuccess("-1");
			entity.setSpzt(Spzt.KKSB.getCode());
		}
		if("1".equals(state)){
			upload.setIssuccess("1");
			entity.setSpzt(Spzt.KKCG.getCode());
		}
		upload.setJnzt("1");
		entity.setSqsj(getTimeNow());
		BeanUtil.setXgField(entity, getTimeNow());
		entity.addConditonItem(new ConditionItem("id","=",applyId));
		upload.addConditonItem(new ConditionItem("applyId", "=", applyId));
		upload.addConditonItem(new ConditionItem("jnzt", "=", "0"));
		upload.addConditonItem(new ConditionItem("xt_zxbz", "=", "1"));
		EntitySysFieldBuilder.getBuilder().setModifyOpField(upload);
//		SetuSession session=(SetuSession) getSetuSession();
//		entity.addConditonItem("and",new ConditionItem("sqr_id","=",session.getUserId()));
		if(entity.updateBySelfCondition()==0){
			throw new BusinessWarnException("操作失败");
		}
		if(upload.updateBySelfCondition()==0) {
			throw new BusinessWarnException("操作失败");
		}
		return new SetuResult("扣款成功！");
	}
	
	
	
	/**
	 * 修改为已付款状态
	 */
	@RequestMapping("/yfk1")
	public SetuResult yfk1(String applyId) throws BusinessWarnException {
		
		try {
			applyId = decrypt(applyId, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJWgYXVdixGiz6tTW920PrZmZ6ZUxJ+NyQlMLw6hhjTVI8XrSqkWg7vPkMIzfDyAdR7+MhrvsoHg0HOlsdHVZ4rExYr4lDc/XWWS2QT4DK2GmKA7+7CpQmuzIlLZdIy3j8nB8gBLqO7p8GsN/TqRs00FMaeQtVD2C+Wr1PkO+rXtAgMBAAECgYEAlMlLwFqJnLrqiLI2qsq+hcBlF1g5wXNLUsyICqOCAe1kSj5EGsoBqvOnPgXWZ49RZ8Vb678pIc99WVKN9VuhPi0QbWH2xXLAO9jXoFf9km2j0A4qfIIcW3/NXca/XOcUru6diu6ALqT3J2zKMkCONZExm1QXuP6IsHWQzv+GaaECQQDUTUqPYQ1jI5hRwa6XSYiQe7I9Mwd9vPhLAYufOBURsodgUgVGXzy3wW6EP2zETVPBOVdvbi7Kqt57nG+jVwClAkEAtGyRNqWJaa/q/KO7dg7B8PJIKRC70ajCajvuk4hXxblY4pZit+elfNEuq6rRiwJWTfJxMPmV6QxAqVvumVrVqQJAMte7xnCYsyhgHxxCQqrykJ9/BRcGcT9jOxtIo73bldxqfU/HQZ4nPV6OqA9EbhKSv1CLNv1tKU4YcAP73GNGnQJAce7xEEoeaQ/+2wTl9xpAnE/TA6OehzoDcyRfduitTJ3LpGKE+k8csKJDz4HyCs8yAszrb5rccKsdFhWIqWyz6QJBALxKuPYWhaeGPTYVcuqggbNUuZS4dEblX639NMUN1oIi5ViekrQs2bYskUGXP/Wo9XaHBRWyRcDKWrx+S7ok/ng=\r\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BusinessRuntimeException("解密失败！");
		}

		ApplyRecordEntity entity=new ApplyRecordEntity();
		entity.setSpzt(Spzt.KKCG.getCode());
		entity.setSqsj(getTimeNow());
		BeanUtil.setXgField(entity, getTimeNow());
		entity.addConditonItem(new ConditionItem("id","=",applyId));
//		SetuSession session=(SetuSession) getSetuSession();
//		entity.addConditonItem("and",new ConditionItem("sqr_id","=",session.getUserId()));
		if(entity.updateBySelfCondition()==0){
			throw new BusinessWarnException("操作失败");
		}
		return new SetuResult("扣款成功！");
	}
	
	/**
	 * 修改为签收成功状态
	 */
	@RequestMapping("/yqs")
	public SetuResult yqs() throws BusinessWarnException {
//		if(applyId==null) {
//			throw new BusinessWarnException("审批记录ID不能为空");
//		}
		ApplyRecordEntity entity=new ApplyRecordEntity();
		entity.setSpzt(Spzt.YQS.getCode());
		entity.setSqsj(getTimeNow());
		BeanUtil.setXgField(entity, getTimeNow());
//		entity.addConditonItem(new ConditionItem("id","=",applyId));
		SetuSession session=(SetuSession) getSetuSession();
		entity.addConditonItem("and",new ConditionItem("sqr_id","=",session.getUserId()));
		if(entity.updateBySelfCondition()==0){
			throw new BusinessWarnException("操作失败");
		}
		return new SetuResult("扣款成功！");
	}
	
	/**
	 * 修改为自取状态
	 */
	@RequestMapping("/zq")
	public SetuResult zq(){
		ApplyRecordEntity entity=new ApplyRecordEntity();
		entity.setSpzt(Spzt.ZQ.getCode());
		entity.setSqsj(getTimeNow());
		BeanUtil.setXgField(entity, getTimeNow());
		SetuSession session=(SetuSession) getSetuSession();
		entity.addConditonItem("and",new ConditionItem("sqr_id","=",session.getUserId()));
		entity.addConditonItem("and",new ConditionItem("xt_zxbz","=","0"));

		if(entity.updateBySelfCondition()==0){
			return new SetuResult("自取更新失败,请联系管理员！");
		}
		return new SetuResult("SUCCESS");
	}
	

	@RequestMapping("/jcrz")
	public SetuResult jcrz(String url,String token,String clientKey) {
		Map<String,String> headers=new HashMap<String, String>();
		Map<String, String> content = new HashMap<String, String>();
//		if(CommonUtil.isNull(url)) {
//			throw new BusinessRuntimeException("url不能为空！");
//		}
//		if(CommonUtil.isNull(token)) {
//			throw new BusinessRuntimeException("token不能为空！");
//		}

		if(CommonUtil.isNull(token)) {

		}


		System.out.println("token:"+token);
		headers.put("h5sysid", "6FE94B3472469D1F04907F3FC28832AE");
		headers.put("Content-Type", "application/json");
		headers.put("Access-Control-Allow-Origin","*");
		headers.put("token", token);
        System.out.println("交叉认证返回的参数：");
			System.out.println(httpURLConnectionPOST(url,headers));
			return new SetuResult(httpURLConnectionPOST(url,headers));
//		}
		
		
	}

	public static Map<String, List<String>> httpURLConnectionPOST(String posturl,Map<String,String> headerMap) {
        try {
            URL url = new URL(posturl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            // 设置连接输入流为true
            connection.setDoInput(true);
            // 设置请求方式为post
            connection.setRequestMethod("POST");
            // post请求缓存设为false
            connection.setUseCaches(false);
            // 设置该HttpURLConnection实例是否自动执行重定向
            connection.setInstanceFollowRedirects(true);
            connection.addRequestProperty("from", "sfzh");  //来源哪个系统
            connection.setRequestProperty("user", "user");  //访问申请用户
            InetAddress address = InetAddress.getLocalHost();
            String ip = address.getHostAddress();//获得本机IP
            //循环设置header
            Iterator headerIterator = headerMap.entrySet().iterator();          //循环增加header
            while(headerIterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) headerIterator.next();  
                connection.setRequestProperty(elem.getKey(),elem.getValue());
            }
            // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
            connection.connect();
            // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
            // 格式 parm = aaa=111&bbb=222&ccc=333&ddd=444
            String parm = "";
            // 将参数输出到连接
            if(parm.length()>0) {
                dataout.writeBytes(parm);
            }
            // 输出完成后刷新并关闭流
            dataout.flush();
            dataout.close(); // 重要且易忽略步骤 (关闭流,切记!)
            //System.out.println(connection.getResponseCode());
            // 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)
            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder(); // 用来存储响应数据
            Map<String, List<String>> map = connection.getHeaderFields();
            // 循环读取流,若不到结尾处
            while ((line = bf.readLine()) != null) {
                //sb.append(bf.readLine());
                sb.append(line).append(System.getProperty("line.separator"));
            }
            bf.close();    // 重要且易忽略步骤 (关闭流,切记!)
            connection.disconnect(); // 销毁连接
            Map<Integer, String> mapKey = new HashMap<Integer, String>();
            Map<Integer, List<String>> mapValue = new HashMap<Integer, List<String>>();
            Map<String, List<String>> maps = new HashMap<String, List<String>>();
            int i = map.size()-1;
            int j = map.size();
            for(java.util.Map.Entry<String, List<String>> entry : map.entrySet()) {
    //将原来MAP的VALUE放入新的MAP的VALUE里面
            	if(CommonUtil.isNull(entry.getKey())) {
            		System.out.println(entry.getKey());
                    mapKey.put(i, "null"+i);
            	}else {
                    mapKey.put(i, entry.getKey());
            	}
    //将原来MAP的KEY放入新的MAP的VALUE 里面
            	List<String> temp = new ArrayList<String>();
            	for(int k=0;k<entry.getValue().size();k++) {
            		temp.add(new String(entry.getValue().get(k).getBytes("ISO-8859-1"),"UTF-8"));
            	}
                 mapValue.put(i, temp);
                 i--;
             }
            for(int k=0;k<j;k++) {
            	maps.put(mapKey.get(k), mapValue.get(k));
            }
            System.out.println(maps.toString());
            return maps;
        } catch (Exception e) {
        	logger.error("com.zxkj.hc.qspt.controller",e);
            throw new BusinessRuntimeException("请求失败！");
        }
    }

    //huxiaotian 2019-10-08 请求body响应
    public static Map<String, String> httpRequestBodyConnectionPOST(String posturl,Map<String,String> headerMap,String bodys) {
		HttpURLConnection connection = getHttpConnect(posturl,headerMap);
        System.out.println("======访问订单创建接口模块======");
		try {
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
			String parm = bodys;
			if(parm.length()>0) {
				//解决中文乱码
				dataout.write(parm.getBytes("utf-8"));
			}
			// 输出完成后刷新并关闭流
			dataout.flush();
			dataout.close();
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			//存储body中的数据
			Map<String,String> bodyData = new HashMap<>();
			// 循环读取流,若不到结尾处
			while ((line = bf.readLine()) != null) {
				bodyData.put("body",line);
                System.out.println("订单请求成功！");
                System.out.println("返回参数：");
                System.out.println(line);
			}
			if(bodyData.isEmpty() || bodyData ==null){
				throw new BusinessRuntimeException("订单创建失败！");
			}
			bf.close();    // 重要且易忽略步骤 (关闭流,切记!)
			connection.disconnect(); // 销毁连接
			return bodyData;
		} catch (IOException e) {
			throw new BusinessRuntimeException("请求失败！");
		}
    }

    public static HttpURLConnection getHttpConnect(String posturl,Map<String,String> headerMap){
		try {
			URL url = new URL(posturl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			//循环设置header
			if (headerMap != null && !headerMap.isEmpty()) {
				Iterator headerIterator = headerMap.entrySet().iterator();          //循环增加header
				while (headerIterator.hasNext()) {
					Entry<String, String> elem = (Entry<String, String>) headerIterator.next();
					connection.setRequestProperty(elem.getKey(), elem.getValue());
				}
			}
//			connection.connect();
			return connection;
		}catch(Exception e) {
			logger.error("com.zxkj.hc.qspt.controller",e);
			throw new BusinessRuntimeException("请求失败！");
		}

	}

	/**
	 * 从房管局获取同步数据，并写入数据库表
	 * //{"fundID": "BDC_01","XTDWMC": "自贡税务部门","HTBAH": "201903140002427","XM": "徐曦"}
	 * @param appId
	 * @param fcrmc
	 * @param htbah
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/fgjData")
	public Boolean fgjData(String appId, String fcrmc, String htbah) throws Exception{
        SetuSession session=(SetuSession) getSetuSession();
        System.out.println("user_id="+session.getUserId());
	    if(CommonUtil.isNull(fcrmc)) {
			throw new BusinessRuntimeException("房产人所有人姓名 不能为空！");
		}
		if(CommonUtil.isNull(htbah)) {
			throw new BusinessRuntimeException("合同备案号 不能为空！");
		}

		if(CommonUtil.isNull(appId)) {
			throw new BusinessRuntimeException("AppId 不能为空！");
		}

		System.out.println("fcrmc:"+fcrmc);
		String url="http://10.11.80.9:8084/RevService_DZTX.ashx";//172.65.11.10:10051 //正式
//		String url="http://172.65.11.10:10051/RevService_DZTX.ashx";//  //测试
		JSONObject fgjJosn = new JSONObject();
		fgjJosn.put("fundID","BDC_01");
		fgjJosn.put("XTDWMC","自贡税务部门");
		fgjJosn.put("HTBAH",htbah);
		fgjJosn.put("XM",fcrmc);
		StringEntity s = new StringEntity(fgjJosn.toString(), "utf-8");
		JSONObject result = (JSONObject)sendPost(url, s, 1);

		if (result!=null){
			String returnStr = result.getString("resultCode");
			String returnMsg = result.getString("resultMsg");
			String bab_url = result.getString("bab_url");
			String wfzm_url = result.getString("wfzm_url");
			String sfk_id = result.getString("sfk_id");
			String sfz_id = result.getString("sfz_id");
			String hkb_id = result.getString("hkb_id");
			String qzy_id = result.getString("qzy_id");
			String hyzkzm_id = result.getString("hyzkzm_id");
			String ghdyfcxy_id = result.getString("ghdyfcxy_id");
			String ht_url = result.getString("ht_url");
			System.out.println(returnStr);
			System.out.println(returnMsg);

			System.out.println(GetBase64ImageInfo(qzy_id));

			//返回100表示成功，并将数据写入到数据库中
			if (!"100".equals(returnStr))
				return false;
			if (!applyService.InsertOrUpdateFGJData(appId,bab_url,wfzm_url,
					hyzkzm_id,sfk_id,sfz_id,hkb_id,qzy_id,ghdyfcxy_id,ht_url)){
                return false;
            }else{
                Boolean status = GetFgjDataImage(appId,session.getUserId());
                return true;
            }
		}
		return true;
	}

	/***
	 * 从FB_Fgj_Data表中获取房产同步数据
	 * sfyz:身份验证
	 * hyyz:婚姻验证
	 * hkbyz:户口薄验证
	 * gffp:购房发票验证
	 * gfht:购房合同
	 */
	public Boolean GetFgjDataImage(String AppId,String userId){
		try {
            FbDataFgjEntity FgjData = new FbDataFgjEntity();
            FgjData.setUserId(AppId);

            List<FbDataFgjEntity> lst = FgjData.queryListBySelf();
            if (lst.size() == 0)
                return null;
            FgjData = lst.get(0);
            String returnStr = "";
            ImageDataSyncController imageDataSyncController = new ImageDataSyncController();
//            returnStr = FgjData.getSfzId();
//            if (!"".equals(returnStr))
//                imageDataSyncController.sync(AppId, "sfyz", returnStr,userId,true);
            returnStr = FgjData.getHyzkzmId();
            if (!"".equals(returnStr))
                imageDataSyncController.sync(AppId, "hyyz", returnStr,userId, true);
            returnStr = FgjData.getHkbId();
            if (!"".equals(returnStr))
                imageDataSyncController.sync(AppId, "hkbyz", returnStr,userId, true);
            returnStr = FgjData.getSfkId();
            if (!"".equals(returnStr))
                imageDataSyncController.sync(AppId, "gffp", returnStr,userId, true);
            returnStr = FgjData.getHtUrl();
            if (!"".equals(returnStr))
                imageDataSyncController.sync(AppId, "gfht", returnStr,userId, false);
        }catch (Exception e){
            e.getMessage();
            return false;
        }
		return true;
	}

	/**
	 * 根据ID从服务器取得BASE64格式的图片
	 * @param ImgId
	 * @return
	 * @throws Exception
	 */
	public static String GetBase64ImageInfo(String ImgId) throws Exception{
		String url="http://10.11.80.9:8084/RevService_DZTX.ashx";  //正式
//		String url="http://172.65.11.10:10051/RevService_DZTX.ashx";  //测试
		/**
		 * "fundID": "BDC_Image",
		 * "BSM": "33DCDAE1-32CC-4FB2-B54A-0EE1A34B6019"
		 */

		JSONObject fgjJosn = new JSONObject();
		fgjJosn.put("fundID","BDC_Image");
		fgjJosn.put("BSM",ImgId);
		StringEntity s = new StringEntity(fgjJosn.toString(), "utf-8");
		String Base64ImageStr = sendPost(url, s, 2).toString();

		return Base64ImageStr.replace("\"","");
	}


	/**
	 * base64转blob 用于存入数据库
	 * @param str
	 * @return
	 */
	public static byte[] transformBase64(String str) {
		Base64Decoder decoder = new Base64Decoder();
		return Base64.decodeBase64(str);
	}

	/**
	 * 发送POST请求
	 * @param url
	 * @param nameValuePairList
	 * @return JSON或者字符串
	 * @throws Exception
	 */
	private static final int SUCCESS_CODE = 200;
	//public static Object sendPost(String url, List<NameValuePair> nameValuePairList) throws Exception{
	public static Object sendPost(String url, StringEntity entity, Integer returnType) throws Exception{
		JSONObject jsonObject = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;

		try{
			/**
			 *  创建一个httpclient对象
			 */
			client = HttpClients.createDefault();
			/**
			 * 创建一个post对象
			 */
			HttpPost post = new HttpPost(url);
			/**
			 * 包装成一个Entity对象
			 */

			//StringEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
			/**
			 * 设置请求的内容
			 */
			post.setEntity(entity);
			/**
			 * 设置请求的报文头部的编码
			 */
			post.setHeader(new BasicHeader("Content-Type", "application/json; charset=utf-8"));
			/**
			 * 设置请求的报文头部的编码
			 */
			post.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
			/**
			 * 执行post请求
			 */
			response = client.execute(post);
			/**
			 * 获取响应码
			 */
			int statusCode = response.getStatusLine().getStatusCode();
			if (SUCCESS_CODE == statusCode){
				/**
				 * 通过EntityUitls获取返回内容
				 */
				String result = EntityUtils.toString(response.getEntity(),"UTF-8");
				/**
				 * 转换成json,根据合法性返回json或者字符串
				 */
				try{
					if (returnType == 1){
						jsonObject = JSONObject.parseObject(result);
						System.out.println("房产数据请求成功："+jsonObject);
						return jsonObject;
					}
					return result;
				}catch (Exception e){
					return null;
				}
			}else{
				System.out.println("POST请求失败！");
			}
		}catch (Exception e){
			System.out.println(e.getMessage());
		}finally {
			if(response!=null)
			response.close();
			if(client!=null)
			client.close();
		}
		return null;
	}

}
