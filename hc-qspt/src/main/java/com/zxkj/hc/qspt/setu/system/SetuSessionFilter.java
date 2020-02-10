package com.zxkj.hc.qspt.setu.system;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.setu.framework.authorization.AbstractSetcModulePlatformAuthorizationManager;
import org.setu.framework.authorization.bean.impl.SetuVerify;
import org.setu.framework.core.properties.SetuSystemUtil;
import org.setu.framework.exception.AbstractException;
import org.setu.framework.exception.BusinessRuntimeException;
import org.setu.framework.exception.BusinessWarnException;
import org.setu.framework.exception.SetuErrorCode;
import org.setu.framework.http.filter.AbstractSetuSessionFilter;
import org.setu.framework.http.filter.BeforeHandler;
import org.setu.framework.monitor.MonitorType;
import org.setu.framework.monitor.thread.MonitorThreadLocalHandler;
import org.setu.framework.session.AbStractSetuSession;
import org.setu.framework.session.controller.SetuSessionDispatch;
import org.setu.framework.session.exception.SessionSetuErrorCode;
import org.setu.framework.session.thread.SessionThreadLocalHandler;
import org.setu.framework.session.util.SetuAutoLoginExecutor;
import org.setu.framework.session.util.SetuSessionUtil;
import org.setu.framework.util.HttpUtil;
import org.setu.framework.util.SetuApplicationUtil;
import org.setu.framework.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartResolutionDelegate;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.RoleEntity;
import com.zxkj.hc.qspt.service.RoleService;


public class SetuSessionFilter extends AbstractSetuSessionFilter{
	private transient static final Logger logger = LoggerFactory.getLogger(SetuSessionFilter.class);
	@Override
	public BeforeHandler before(String url, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		BeforeHandler handler=new BeforeHandler();
		if(url.contains("undefined")){//排除js错误请求
			handler.forword=true;//跳过
			return handler;
		}
		handler.skip=skipType(url);//是否跳过验证
		if(!handler.skip){
					//检测服务器端跳转
					if(forwardType(url,request, response)){
						handler.forword=true;//跳过
						return handler;
					}
		}
		return handler;
	}

	@Override
	public void after(String url, BeforeHandler handler, HttpServletRequest request, HttpServletResponse response,AbStractSetuSession abStractSetuSession) throws IOException, ServletException {
		SetuSession session=(SetuSession) AbStractSetuSession.getLocalSession();
		if(url.endsWith(SetuSystemUtil.logoutAction)){
			SetuSessionUtil.logout();
			if(request.getCookies()!=null){
				for (Cookie cookie : request.getCookies()) {
					// 删除
					if (AbStractSetuSession.CLIENTKEY.equals(cookie.getName())) {
						cookie = new Cookie(AbStractSetuSession.CLIENTKEY, null);
						cookie.setMaxAge(0);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
			}
		}else if(session.getUserId()!=null && session.isUpdate()){
			if(url.endsWith(SetuSystemUtil.loginAction)||url.endsWith("Login")){
				SetuSessionUtil.login(session);
			}else{
				SetuSessionUtil.update(session);
			}
			/*if(!CommonUtil.isNull(session.getClientKey())){
				Cookie cookie = new Cookie(AbStractSetuSession.CLIENTKEY, session.getClientKey());
				cookie.setMaxAge(-1);
				cookie.setPath("/");
				cookie.setDomain(SetuSystemUtil.crossDomain);
				response.addCookie(cookie);
			}*/
			logger.debug("++++++++++++++++++++++++++++++ url:{}结束 ++++++++++++++++++++++++++++++",url);
		}
		//TODO 设置静态资源版本
	}

	@SuppressWarnings("deprecation")
	@Override
	public void errorHandler(String url, HttpServletRequest request, HttpServletResponse response,
							 Exception exception,AbStractSetuSession abStractSetuSession){
		if(exception.getClass().getName().contains("TechnicalException")){
			try {
				java.lang.reflect.Field  field = Throwable.class.getDeclaredField("detailMessage");
				field.setAccessible(true);
				field.set(exception, URLEncoder.encode(exception.getMessage()));
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				logger.error("",e);
			}
		}
	}
	/**
	 * 服务器端跳转，检测权限并执行跳转
	 * @param request
	 * @param response
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public static boolean forwardType(String url, HttpServletRequest request, HttpServletResponse  response) throws IOException, ServletException {
		boolean flag=false;
		if(!url.endsWith(SetuSystemUtil.loginPage)
				&&!url.contains("/setu/monitor")
				&&!url.startsWith("/api/bank/")
				&&!url.startsWith("/api/apply/changeState")
				&&!url.startsWith("/api/apply/jcrz")
				&&!url.startsWith("/api/apply/zlshlb")
//				&&!url.startsWith("/api/apply/zlspRecord")
				&&!url.startsWith("/api/apply/sdsclb")
//				&&!url.startsWith("/api/apply/sdyjlb")
				&&!url.startsWith("/api/apply/sqxx")
//				&&!url.startsWith("/api/apply/tptg")
				&&!url.startsWith("/api/apply/tjzl")
				&&!url.startsWith("/order/apply/return")   //用于接收支付返回参数
				&&!url.startsWith("/api/apply/ocr")
				&&!url.startsWith("/api/apply/scsd")
				&&!url.startsWith("/api/apply/yjsd")
//				&&!url.startsWith("/api/apply/tpbtg")
				&&!url.startsWith("/api/apply/sqtg")
				&&!url.startsWith("/api/apply/refund")
//				&&!url.startsWith("/api/apply/fgjData")
				//&&!url.startsWith("接口路径")
				&&!"/wxsmdl.ws".equals(url)
				&&!"/api/gzh/wx.i".equals(url)){
        	if(!url.endsWith(SetuSystemUtil.loginAction)){
        		long start=System.currentTimeMillis();
    			try{
    				//如果是异步登录（第三方系统接入页面默认执行登录的情况），如果无会话则执行登录  如果有会话则延用当前会话
    				//获取会话信息
    				validate(url, request, response);
                	MonitorThreadLocalHandler.put(MonitorType.SESSION_VALIDATE, SessionThreadLocalHandler.localSession.get().getClientKey(),start, System.currentTimeMillis());
                	//权限验证
					if(!url.endsWith(SetuSystemUtil.logoutAction)){
						RoleService roleService= SetuApplicationUtil.getBean(RoleService.class);
						List<RoleEntity> roleEntityList=roleService.listRoleByUrl(url);
						SetuSession session=(SetuSession) SessionThreadLocalHandler.localSession.get();
						String[] userRole=session.getUserRole();
						boolean hasPermission=false;
						loop:{
							for(String roleId:userRole){
								for(RoleEntity roleEntity:roleEntityList){
									if(roleId.equals(roleEntity.getId())){
										hasPermission=true;
										break loop;
									}
								}
							}
						}
						if(!hasPermission){
							throw new BusinessRuntimeException("没有权限!",401);
						}
					}
                	//如果是登录操作时已有会话 直接跳过
                	if(url.endsWith(SetuSystemUtil.loginAction)){
                		response.getWriter().append("{\"status\":\"success\"}");
            			return true;
            		}
    			}catch(ServletException e){
    				if(!url.endsWith(SetuSystemUtil.loginAction)){
    					throw e;
    				}else{
        				response.setStatus(200);
    				}
    			}
        	}
		}
		return flag;
	}


	public static void validate(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException {
		boolean hasCookies = false;
		String clientKey = request.getParameter(AbStractSetuSession.CLIENTKEY);
		System.out.println("url:"+url+"===>clientKey:"+clientKey);
		String token =null, userId=null;
		Cookie[] cookies = request.getCookies();
		if (CommonUtil.isNull(clientKey)) {
			clientKey = (String) request.getAttribute(AbStractSetuSession.CLIENTKEY);
			if (CommonUtil.isNull(clientKey)) {
				try {
					if (cookies == null) {

					} else {
						for (Cookie cookie : cookies) {
							if (AbStractSetuSession.CLIENTKEY.equals(cookie.getName())) {
								hasCookies = true;
								clientKey = cookie.getValue();
								if(cookie.getDomain()!=null&&cookie.getDomain().contains(SetuSystemUtil.crossDomain)){
									break;
								}
							}else if(AbStractSetuSession.TOKEN.equals(cookie.getName())){
								hasCookies = true;
								token= cookie.getValue();
							}else if(AbStractSetuSession.USERID.equals(cookie.getName())){
								hasCookies = true;
								userId= cookie.getValue();
							}

						}
					}
				} catch (Exception e) {
					logger.error("",e);
					throw new ServletException(e.getMessage());
				}
				if (clientKey == null&&token==null) {
					String referer = request.getHeader("Referer");
					if (referer != null && referer.length() != 0) {
						Pattern p = Pattern.compile(AbStractSetuSession.CLIENTKEY + "=([^&]+)");
						Matcher m = p.matcher(referer);
						while (m.find()) {
							clientKey = m.group(1);
						}
					}
				}
			}
		}
		try {
			SetuVerify verify;
			if(clientKey==null&&token!=null){
				verify=AbstractSetcModulePlatformAuthorizationManager.setuVerifieMap.get(token);
				if(verify==null){
					throw new BusinessWarnException(SessionSetuErrorCode.LOGIN_NO_VERIFY_SESSION);
				}
				if(verify.ip!=null&&verify.ip.length()>0&&verify.ip.indexOf(HttpUtil.getRemoteIp(request))<0){
					throw new BusinessWarnException(SessionSetuErrorCode.LOGIN_NO_VERIFY_SESSION);
				}
				if(userId==null){
					throw new BusinessWarnException(SessionSetuErrorCode.LOGIN_NO_USERID_SESSION);
				}
				SetuSessionUtil.validate(null,token, userId,verify.getName(),new SetuAutoLoginExecutor (){
					@Override
					public void execute(String userId, String verify,AbStractSetuSession abStractSetuSession) {
						//设置登录
//						SetuSession session=(SetuSession) abStractSetuSession;
					}
				});
			}else{
				SetuSessionUtil.validate(clientKey,null, null,null,null);
			}

			SetuSession session=(SetuSession) SessionThreadLocalHandler.localSession.get();
			session.setRemoteIp(HttpUtil.getRemoteIp(request));
			request.setAttribute(SetuSessionDispatch.SETU_SESSION_KEY, session);
			request.setAttribute(AbStractSetuSession.CLIENTKEY, clientKey);
			request.setAttribute(SetuSessionDispatch.SETU_SESSION_KEY, SessionThreadLocalHandler.localSession.get());
		} catch (AbstractException e) {
			response.setStatus(418);

				request.setAttribute(SetuErrorCode.ERRORCODE, e.errorCode);
				if(url.endsWith(SetuSystemUtil.logoutAction)){
					if(!CommonUtil.isNull(request.getParameter("userId"))){
						SetuSessionUtil.logout(request.getParameter("userId"));
					}else{
						SetuSessionUtil.logout();
						if(request.getCookies()!=null){
							for (Cookie cookie : request.getCookies()) {
								// 删除
								if (AbStractSetuSession.CLIENTKEY.equals(cookie.getName())) {
									cookie = new Cookie(AbStractSetuSession.CLIENTKEY, null);
									cookie.setMaxAge(0);
									cookie.setPath("/");
									response.addCookie(cookie);
								}
							}
						}
					}
				}else{
					throw new BusinessRuntimeException(e.getMessage(),e.getCode());
				}
				request.setAttribute(AbStractSetuSession.CLIENTKEY, clientKey);
				SetuSession session=(SetuSession) SessionThreadLocalHandler.localSession.get();
				session.setRemoteIp(HttpUtil.getRemoteIp(request));
		}
	}
	/**
	 * 是否跳转
	 * @param name
	 * @return
	 */
	public boolean skipType(String name){
		if(name.contains(".")){
			if(name.contains("jsp")&&!name.contains("login.jsp")){
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
}
