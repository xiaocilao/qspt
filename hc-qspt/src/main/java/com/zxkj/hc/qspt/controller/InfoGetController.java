package com.zxkj.hc.qspt.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.zxkj.hc.qspt.setu.system.SetuResult;

@RestController
@RequestMapping("/api/infoGet")
public class InfoGetController {

//	@RequestMapping("/getInfo")
//	public HttpResponse getInfo() throws ClientProtocolException, IOException {
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpPost post = new HttpPost("http://118.123.243.201:8086/RevService_DZTX.ashx");
//		post.setEntity(new StringEntity("{\r\n" + 
//				" \"fundID\": \"BDC_01\",\r\n" + 
//				"  \"XTDWMC\": \"自贡税务部门\",\r\n" + 
//				"  \"HTBAH\": \"201612310000159\"\r\n" + 
//				"}", "UTF-8"));
//		post.setHeader("Content-Type", "application/json");
//		post.setHeader("charset", "UTF-8");
//		 httpResponse = ;
//		return new httpClient.execute(post);
//	}
}
