package com.zxkj.hc.qspt.controller;

import com.alibaba.fastjson.JSONObject;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.WxgzhUserEntity;
import com.zxkj.hc.qspt.service.WxgzhService;
import com.zxkj.hc.qspt.setu.system.SetuResult;
import com.zxkj.hc.qspt.util.AccessTokenManager;
import com.zxkj.hc.qspt.util.HttpClientUtil;
import org.setu.framework.http.filter.DefaultSetuFilter;
import org.setu.framework.jdbc.core.ConditionItem;
import org.setu.framework.session.controller.SetuSessionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/gzh")
public class GzhController extends SetuSessionController {
    private transient static final Logger logger = LoggerFactory.getLogger(GzhController.class);

    @Autowired
    AccessTokenManager accessTokenManager;
    @Autowired
    WxgzhService wxgzhService;
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;

    {
        documentBuilderFactory=DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("com.zxkj.hc.qspt.controller",e);
        }
    }

    @RequestMapping("/wx.i")
    String gzh(HttpServletRequest request,HttpServletResponse response) throws IOException, SAXException {
        String accessToken=accessTokenManager.getWxgzhAccessToken();
        InputStream inputStream=request.getInputStream();
        Document document=documentBuilder.parse(inputStream);
        String ToUserName=document.getElementsByTagName("ToUserName").item(0).getTextContent();
        String FromUserName=document.getElementsByTagName("FromUserName").item(0).getTextContent();
        String CreateTime=document.getElementsByTagName("CreateTime").item(0).getTextContent();
        String MsgType=document.getElementsByTagName("MsgType").item(0).getTextContent();
        String Event=document.getElementsByTagName("Event").item(0).getTextContent();
        if("subscribe".equals(Event)){
            WxgzhUserEntity wxgzhUserEntity=new WxgzhUserEntity();
            wxgzhUserEntity.setOpenid(FromUserName);
            String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+FromUserName+"&lang=zh_CN";
            String responseStr= HttpClientUtil.sendGet(url);
            if(responseStr!=null){
                JSONObject responseJson=JSONObject.parseObject(responseStr);
                if(responseJson.getInteger("errcode")==null){
                    String unionid=responseJson.getString("unionid");
                    String nickname=responseJson.getString("nickname");
                    nickname= nickname.replaceAll("[^\\u0000-\\uFFFF]", "");
                    wxgzhUserEntity.setUnionid(unionid);
                    wxgzhUserEntity.setNickname(nickname);
                }else {
                    System.out.println(response);
                }
            }
            System.out.println(FromUserName+"关注了公众号");
            wxgzhUserEntity.insertSelf();
        }else if("unsubscribe".equals(Event)){
            WxgzhUserEntity wxgzhUserEntity=new WxgzhUserEntity();
            wxgzhUserEntity.setXtZxbz("1");
            wxgzhUserEntity.addConditonItem(new ConditionItem("openid","=",FromUserName));
            wxgzhUserEntity.updateBySelfCondition();
            System.out.println(FromUserName+"取消关注了公众号");
        }
        return "";
    }

    @RequestMapping("/grabWxgzhUsers")
    SetuResult grabWxgzhUsers(HttpServletRequest request){
        List<WxgzhUserEntity> wxgzhUserEntities=wxgzhService.grabWxgzhUsers();
        for(WxgzhUserEntity wxgzhUserEntity:wxgzhUserEntities){
            wxgzhUserEntity.insertSelf();
        }
        return new SetuResult("总共获取到："+wxgzhUserEntities.size()+"个用户");
    }

    @RequestMapping("/sendMessage")
    SetuResult sendMessage(String userId){
        HashMap<String,WxgzhService.TempleteValue> data=new HashMap<>();
        data.put("first",new WxgzhService.TempleteValue("这是一条测试信息","#000000"));
        data.put("keyword1",new WxgzhService.TempleteValue("我是主题","#000000"));
        data.put("keyword2",new WxgzhService.TempleteValue("2018-1-1 12:00","#000000"));
        data.put("keyword3",new WxgzhService.TempleteValue("通过","#000000"));
        data.put("keyword4",new WxgzhService.TempleteValue("我是审核意见","#000000"));
        data.put("remark",new WxgzhService.TempleteValue("---------","#000000"));
        wxgzhService.sendTempleteMessage(userId,"7NU985dIpitIGm_o-xZHMv_pSd7-JBtBmjiJQd6g1xE",data);
        return new SetuResult("success");
    }


}
