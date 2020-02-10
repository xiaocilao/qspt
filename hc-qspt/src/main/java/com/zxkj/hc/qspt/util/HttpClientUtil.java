package com.zxkj.hc.qspt.util;

import com.zxkj.hc.qspt.controller.WxsmdlWebsocketController;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;

public class HttpClientUtil {

    private transient static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final RequestConfig requestConfig;

    static {
        requestConfig=RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
    }

    public static String sendGet(String url){
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet();
        request.setConfig(requestConfig);
        try {
            request.setURI(new URI(url));
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                return responseStr;
            }
        }catch (Exception e){
            logger.error("",e);
        }
        return null;
    }
    public static String sendGet(String url, Map<String,String> params) {
        StringBuilder stringBuilder=new StringBuilder(url+"?");
        stringBuilder.append('?');
        for(Map.Entry<String,String> entry:params.entrySet()){
            stringBuilder.append(entry.getKey());
            stringBuilder.append('=');
            stringBuilder.append(entry.getValue());
            stringBuilder.append('&');
        }
        return sendGet(stringBuilder.toString());
    }

    public static String sendPost(String url,String contentType,String data) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost();
        request.setConfig(requestConfig);
        request.setHeader("Content-Type", contentType);
        try {
            request.setURI(new URI(url));
            request.setEntity(new StringEntity(data,"UTF-8"));
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("请求返回参数为:"+statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                System.out.println("http请求成功！");
                String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                return responseStr;
            }
        }catch (Exception e){
            logger.error("",e);
        }
        return null;
    }

    public static String sendPost(String url,String data){
        return sendPost(url,"application/x-www-form-urlencoded",data);
    }

    public static String sendPost(String url,Map<String,String> params) {
        StringBuilder stringBuilder=new StringBuilder(url+"?");
        for(Map.Entry<String,String> entry:params.entrySet()){
            stringBuilder.append(entry.getKey());
            stringBuilder.append('=');
            stringBuilder.append(entry.getValue());
            stringBuilder.append('&');
        }
        return sendPost(url,"application/x-www-form-urlencoded",stringBuilder.toString());
    }

}
