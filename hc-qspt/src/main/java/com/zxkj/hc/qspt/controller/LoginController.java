package com.zxkj.hc.qspt.controller;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.RoleEntity;
import com.zxkj.hc.qspt.service.RoleService;
import com.zxkj.hc.qspt.service.VerificationCodeService;
import com.zxkj.hc.qspt.setu.system.SetuSession;
import org.setu.framework.exception.BusinessWarnException;
import org.setu.framework.session.controller.SetuSessionController;
import org.setu.framework.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyUserEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.UserEntity;
import com.zxkj.hc.qspt.service.LoginService;
import com.zxkj.hc.qspt.setu.system.SetuResult;

/**
 * @author hfl
 * 2018年11月17日
 */
@RestController
public class LoginController  extends SetuSessionController {
    @Resource
    LoginService loginService;
    @Resource
    VerificationCodeService verificationCodeService;
    @Value("${web.url}")
    private String webUrl;
    @Resource
    RoleService roleService;

    @RequestMapping("/login")
    @ResponseBody
    public SetuResult login(HttpServletResponse response,String loginType, String userName, String password, String code, String state, String wsid, String token,String yzm,String phoneNumber,String name,String userId) throws BusinessWarnException, IOException {
        loginType = CommonUtil.isNull(loginType) ? "3" : loginType;
        SetuSession session = (SetuSession) getSetuSession();
        switch (loginType) {
            //后台用户名密码登录
            case "1": {
                UserEntity userEntity = loginService.userLogin(userName, password,null);
                if (userEntity == null) {
                    throw new BusinessWarnException("登录失败：密码错误");
                }
                session.setUserId(userEntity.getId());
                session.setUserName(userEntity.getUserName());
                List<RoleEntity> roleEntityList=roleService.listRoleByUserId(userEntity.getId());
                String[] userRole=new String[roleEntityList.size()];
                for(int i=0;i<userRole.length;i++){
                    userRole[i]=roleEntityList.get(i).getId();
                }
                session.setUserRole(userRole);
            }
            break;
            //扫码成功过后网页端凭借token登录
            case "2": {
                WxsmdlWebsocketController connection=WxsmdlWebsocketController.getConnection(wsid);
                if(connection==null || !connection.getToken().equals(token)){
                    throw new BusinessWarnException("无效token");
                }
                if(connection.getAction()==WxsmdlWebsocketController.ACTION_LOGIN){
                    UserEntity user=connection.getUser();
                    session.setUserId(user.getId());
                    session.setUserName(user.getUserName());
                    List<RoleEntity> roleEntityList=roleService.listRoleByUserId(user.getId());
                    String[] userRole=new String[roleEntityList.size()];
                    for(int i=0;i<userRole.length;i++){
                        userRole[i]=roleEntityList.get(i).getId();
                    }
                    session.setUserRole(userRole);
                }else if(connection.getAction()==WxsmdlWebsocketController.ACTION_BIND){
                    UserEntity userEntity = loginService.userLogin(userName, password,connection.getUser().getWxOpenid());
                    if (userEntity == null) {
                        throw new BusinessWarnException("登录失败：密码错误");
                    }
                    session.setUserId(userEntity.getId());
                    session.setUserName(userEntity.getUserName());
                    List<RoleEntity> roleEntityList=roleService.listRoleByUserId(userEntity.getId());
                    String[] userRole=new String[roleEntityList.size()];
                    for(int i=0;i<userRole.length;i++){
                        userRole[i]=roleEntityList.get(i).getId();
                    }
                    session.setUserRole(userRole);
//                    verificationCodeService.verificate(phoneNumber, code);
                }else{
                    throw new BusinessWarnException("登录失败");
                }
            }
            break;
            //银行登录
            case "4": {
            	ApplyUserEntity user = loginService.bankLogin(userId, phoneNumber, name);

            	//胡小天，重新查询
                ApplyUserEntity aapp = new ApplyUserEntity();
                aapp.setUserName(user.getUserName());
                aapp.setPhoneNumber(phoneNumber);
                aapp.queryBySelf();
            	session.setUserId(aapp.getId());
                session.setUserName(user.getUserName());
                session.setUserRole(new String[]{"1"});
            }
            break;
            //微信登录
            default:
            {
                if(StringUtils.isEmpty(state)){
                    //用户微信登录
                    String wechatId[] = loginService.getXcxOpenid(code);
                    ApplyUserEntity applyUserEntity = loginService.applyUserWxLogin(userName, wechatId[0],wechatId[1],true);
                    session.setUserId(applyUserEntity.getId());
                    session.setUserName(applyUserEntity.getUserName());
                    session.setUserRole(new String[]{"1"});
                }else{
                    //管理员微信登录
                    String openid = loginService.getGzhOpenid(code);
                    if (openid == null) {
                        response.sendRedirect(webUrl+"/wxLoginFail.html");
                        break;
                    }
                    System.out.println("openid:"+openid);
                    UserEntity userEntity = new UserEntity();
                    userEntity.setWxOpenid(openid);
                    userEntity.setXtZxbz("0");
                    List<UserEntity> list = userEntity.queryListBySelf();
                    if (list.size() == 0) {
                        WxsmdlWebsocketController connection=WxsmdlWebsocketController.getConnection(state);
                        if(connection==null){
                            response.sendRedirect(webUrl+"/wxLoginFail.html");
                            break;
                        }
                        connection.setAction(WxsmdlWebsocketController.ACTION_BIND);
                        UserEntity entity=new UserEntity();
                        entity.setWxOpenid(openid);
                        connection.setUser(entity);
                        if(connection.sendToken()){
                            response.sendRedirect(webUrl+"/wxLoginSuccess.html");
                        }else{
                            response.sendRedirect(webUrl+"/wxLoginFail.html");
                        }
                        break;
                    }else{
                        WxsmdlWebsocketController connection=WxsmdlWebsocketController.getConnection(state);
                        if(connection==null){
                            response.sendRedirect(webUrl+"/wxLoginFail.html");
                            break;
                        }
                        connection.setAction(WxsmdlWebsocketController.ACTION_LOGIN);
                        UserEntity user = list.get(0);
                        connection.setUser(user);
                        if(connection.sendToken()){
                            response.sendRedirect(webUrl+"/wxLoginSuccess.html");
                        }else{
                            response.sendRedirect(webUrl+"/wxLoginFail.html");
                        }
                    }
                }
            }
            	break;
        }
        return new SetuResult(200, session.initClientKey());
    }

    @RequestMapping("/api/bankSign")
    public SetuResult bankSign(HttpServletResponse response, String userId, String phoneNumber,String name) {
    	return new SetuResult(loginService.banksign(userId, phoneNumber, name));
    }

    @RequestMapping("/api/signin")
    public SetuResult signin(HttpServletResponse response, String userName, String code, String yzm) throws BusinessWarnException, IOException {
    	SetuSession session = (SetuSession) getSetuSession();
    	boolean tag = true;
    	if(!StringUtils.isEmpty(yzm)&&!yzm.equals("null")) {
    		tag = verificationCodeService.verificate(userName, yzm);
    		System.out.println("用户名："+userName+"验证码："+yzm);
    		System.out.println("tag"+tag);
    	}
        String wechatId[] = loginService.getXcxOpenid(code);
        ApplyUserEntity applyUserEntity = loginService.applyUserWxLogin(userName,wechatId[0],wechatId[1],tag);
        if(tag) {
        	session.setUserId(applyUserEntity.getId());
            session.setUserName(applyUserEntity.getUserName());
        }else {
        	return new SetuResult(-300, "验证码错误，注册失败!");
        }
        return new SetuResult(200,"注册成功！");
    }

    @RequestMapping("/api/finduser")
    public String finduser(String code) {
        String openid = loginService.getXcxOpenid(code)[0];
        ApplyUserEntity applyUserEntity = new ApplyUserEntity();
        applyUserEntity.setWechatId(openid);
        List<ApplyUserEntity> list = applyUserEntity.queryListBySelf();
        if (list.size() == 0) {
            return null;
        } else {
            applyUserEntity = list.get(0);
            return applyUserEntity.getUserName();
        }
    }

    @RequestMapping("/api/finduser1")
    public String finduser1(String code) {
    	if(CommonUtil.isNotNull(code)) {
    		String openid = loginService.getXcxOpenid(code)[0];
    		ApplyUserEntity applyUserEntity = new ApplyUserEntity();
    		applyUserEntity.setWechatId(openid);
    		List<ApplyUserEntity> list = applyUserEntity.queryListBySelf();
    		if (list.size() == 0) {
    			return null;
    		} else {
    			applyUserEntity = list.get(0);
    			return applyUserEntity.getUserName();
    		}
    	}else {
    		SetuSession session = (SetuSession) getSetuSession();
        	String userName = session.getUserName();
        	String userId = session.getUserId();
        	ApplyUserEntity userEntity = new ApplyUserEntity();
            userEntity.setId(userId);
            userEntity.setUserName(userName);
            List<ApplyUserEntity> list = userEntity.queryListBySelf();
            if (list.size() == 0) {
                return null;
            } else {
            	userEntity = list.get(0);
                return userEntity.getUserName();
            }
    	}

    }

    /**
     * 获取验证码
     * @param phoneNumber
     * @return
     */
    @RequestMapping("/api/hqyzm")
    public SetuResult hqyzm(String phoneNumber) throws BusinessWarnException {
        if (verificationCodeService.sendVerificationCode(phoneNumber)) {
            return new SetuResult("发送成功");
        }
        return new SetuResult("发送失败");
    }

    @RequestMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect(webUrl+"/index.html");
    }

    @RequestMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        response.sendRedirect(webUrl+"/index.html");
    }
}
