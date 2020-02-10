package com.zxkj.hc.qspt.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxkj.hc.qspt.controller.WxsmdlWebsocketController;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyUserEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.WxgzhUserEntity;
import com.zxkj.hc.qspt.service.WxgzhService;
import com.zxkj.hc.qspt.util.AccessTokenManager;
import com.zxkj.hc.qspt.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WxgzhServiceImpl implements WxgzhService {
    private transient static final Logger logger = LoggerFactory.getLogger(WxsmdlWebsocketController.class);

    @Value("${wxxcx.appid}")
    private String xcxAppid;
    @Autowired
    AccessTokenManager accessTokenManager;

    @Override
    public List<WxgzhUserEntity> grabWxgzhUsers() {
        String accessToken=accessTokenManager.getWxgzhAccessToken();
        ArrayList<WxgzhUserEntity> list=new ArrayList<>();
        String nextOpenid=null;
        do {
            String url="https://api.weixin.qq.com/cgi-bin/user/get?access_token="+accessToken+(nextOpenid==null?"":"&next_openid="+nextOpenid);
            String response=HttpClientUtil.sendGet(url);
            JSONObject responseJson=JSONObject.parseObject(response);
            if(responseJson.getInteger("errcode")!=null){
                System.out.println(response);
                break;
            }
            int total=responseJson.getInteger("total");
            int count=responseJson.getInteger("count");
            JSONArray data=responseJson.getJSONObject("data").getJSONArray("openid");
            for(int i=nextOpenid==null?0:1;i<count;i++){
                WxgzhUserEntity wxgzhUser=new WxgzhUserEntity();
                wxgzhUser.setOpenid(data.getString(i));
                list.add(wxgzhUser);
            }
            if(list.size()<total){
                nextOpenid=responseJson.getString("next_openid");
            }else{
                nextOpenid=null;
            }
        }while (nextOpenid!=null);

        for (WxgzhUserEntity wxgzhUser:list){
            String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+wxgzhUser.getOpenid()+"&lang=zh_CN";
            String response=HttpClientUtil.sendGet(url);
            if(response==null){
                continue;
            }
            JSONObject responseJson=JSONObject.parseObject(response);
            if(responseJson.getInteger("errcode")!=null){
                System.out.println(response);
                continue;
            }
            String unionid=responseJson.getString("unionid");
            String nickname=responseJson.getString("nickname");
            nickname= nickname.replaceAll("[^\\u0000-\\uFFFF]", "");
            wxgzhUser.setUnionid(unionid);
            wxgzhUser.setNickname(nickname);
        }

        return list;
    }

    @Override
    public boolean sendTempleteMessage(String applyUserId, String templeteId, Map<String, TempleteValue> data) {
        ApplyUserEntity applyUserEntity=new ApplyUserEntity();
        applyUserEntity.setId(applyUserId);
        try {
            applyUserEntity.queryBySelf();
        }catch (Exception e){
            logger.error("",e);
            return false;
        }
        String unionId=applyUserEntity.getWechatUnionid();
        if(StringUtils.isEmpty(unionId)){
            return false;
        }
        WxgzhUserEntity wxgzhUserEntity=new WxgzhUserEntity();
        wxgzhUserEntity.setUnionid(unionId);
        try {
            wxgzhUserEntity.queryBySelf();
        }catch (Exception e){
            logger.error("",e);
            return false;
        }
        String touser=wxgzhUserEntity.getOpenid();

        String accessToken=accessTokenManager.getWxgzhAccessToken();
        String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken;
        JSONObject requestJson=new JSONObject();
        requestJson.put("touser",touser);
        requestJson.put("template_id",templeteId);
        JSONObject miniprogram=new JSONObject();
        miniprogram.put("appid",xcxAppid);
        miniprogram.put("pagepath","pages/index/index");
        requestJson.put("miniprogram",miniprogram);
        requestJson.put("data", data);
        String response=HttpClientUtil.sendPost(url,"application/json;charset=UTF-8",requestJson.toString());
        if(response==null){
            return false;
        }
        JSONObject responseJson=JSONObject.parseObject(response);
        if(!responseJson.getInteger("errcode").equals(0)){
            System.out.println(response);
            return false;
        }
        return true;
    }
}
