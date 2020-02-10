package com.zxkj.hc.qspt.util;

import com.alibaba.fastjson.JSONObject;
import com.zxkj.hc.qspt.controller.WxsmdlWebsocketController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AccessTokenManager {
    private transient static final Logger logger = LoggerFactory.getLogger(AccessTokenManager.class);

    @Value("${wxxcx.appid}")
    private String xcxAppid;
    @Value("${wxxcx.secret}")
    private String xcxSecret;
    @Value("${wxgzh.appid}")
    private String gzhAppid;
    @Value("${wxgzh.secret}")
    private String gzhSecret;
    @Value("${baiduyun.ocr.clientId}")
    private String bdyClientId;
    @Value("${baiduyun.ocr.clientSecret}")
    private String bdyClientSecret;

    private String wxgzhAccessToken;
    private String bdyAccessToken;

    @PostConstruct
    public void init(){
        //定时请求wxgzhAccessToken
//        Thread thread1=new Thread(){
//            @Override
//            public void run() {
//                String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+gzhAppid+"&secret="+gzhSecret;
//                while (true){
//                    String responseStr=HttpClientUtil.sendGet(url);
//                    JSONObject responseJson=JSONObject.parseObject(responseStr);
//                    Integer errcode=responseJson.getInteger("errcode");
//                    if(errcode!=null && !errcode.equals(0)){
//                        System.out.println(responseStr);
//                        if(errcode.equals(-1)){
//                            try {
//                                Thread.sleep(10000);
//                            }catch (Exception e){
//                                logger.error("",e);
//                            }
//                        }else{
//                            return;
//                        }
//                    }
//                    String access_token=responseJson.getString("access_token");
//                    Long expires_in=responseJson.getLong("expires_in");
//                    AccessTokenManager.this.wxgzhAccessToken=access_token;
//                    try {
//                        Thread.sleep(Math.max((expires_in-30)*1000,0));
//                    }catch (Exception e){
//                        logger.error("",e);
//                    }
//                }
//            }
//        };
//        thread1.setDaemon(true);
//        thread1.start();

        //定时请求bdyAccessToken
//        Thread thread2=new Thread(){
//            @Override
//            public void run() {
//                String url="https://aip.baidubce.com:2702/oauth/2.0/token?grant_type=client_credentials&client_id=" + bdyClientId + "&client_secret=" + bdyClientSecret;
//                while (true){
//                    String responseStr=HttpClientUtil.sendGet(url);
//                    JSONObject responseJson=JSONObject.parseObject(responseStr);
//                    String error=responseJson.getString("error");
//                    if(error!=null){
//                        System.out.println(responseStr);
//                        return;
//                    }
//                    String access_token=responseJson.getString("access_token");
//                    Long expires_in=responseJson.getLong("expires_in");
//                    AccessTokenManager.this.bdyAccessToken=access_token;
//                    try {
//                        Thread.sleep(Math.max((expires_in-30)*1000,0));
//                    }catch (Exception e){
//                        logger.error("",e);
//                    }
//                }
//            }
//        };
//        thread2.setDaemon(true);
//        thread2.start();
    }

    public String getWxgzhAccessToken() {
        return wxgzhAccessToken;
    }

    public void setWxgzhAccessToken(String wxgzhAccessToken) {
        this.wxgzhAccessToken = wxgzhAccessToken;
    }

    public String getBdyAccessToken() {
        return bdyAccessToken;
    }

    public void setBdyAccessToken(String bdyAccessToken) {
        this.bdyAccessToken = bdyAccessToken;
    }

}
