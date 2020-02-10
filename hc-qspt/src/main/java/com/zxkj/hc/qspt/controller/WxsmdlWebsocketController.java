/**
 * 微信扫码登录Websocket
 */
package com.zxkj.hc.qspt.controller;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.UserEntity;
import com.zxkj.hc.qspt.util.RandomStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;

@ServerEndpoint("/wxsmdl.ws")
@Component
public class WxsmdlWebsocketController {
    private transient static final Logger logger = LoggerFactory.getLogger(WxsmdlWebsocketController.class);

    private static HashMap<String, WxsmdlWebsocketController> connections=new HashMap<>();
    private Session session;
    private String token;
    private int action;
    private UserEntity user;

    public static final int ACTION_NONE=0;
    public static final int ACTION_BIND=1;
    public static final int ACTION_LOGIN=2;

    public static WxsmdlWebsocketController getConnection(String wsid){
        synchronized (connections){
           return connections.get(wsid);
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session=session;
        synchronized (connections){
            connections.put(session.getId(),this);
        }
        sendMessage("{\"wsid\":\""+session.getId()+"\"}");
    }

    @OnClose
    public void onClose() {
        synchronized (connections){
            connections.remove(session.getId());
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("",error);
    }

    /**
     * 发送消息
     * @param message
     * @return 是否发送成功
     */
    private boolean sendMessage(String message){
        try {
            session.getBasicRemote().sendText(message);
            return true;
        } catch (IOException e) {
            logger.error("",e);
        }
        return false;
    }

    /**
     * 生成token并发送到客户端
     * @return
     */
    public boolean sendToken(){
        token= RandomStringUtil.getRandomString(32);
        return sendMessage("{\"token\":\""+token+"\",\"action\":"+action+"}");
    }

}