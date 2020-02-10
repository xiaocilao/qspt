package org.setu.framework.http.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.setu.framework.authorization.AbstractSetcModulePlatformAuthorizationManager;
import org.setu.framework.authorization.bean.SetuAuthResourceHandler;
import org.setu.framework.authorization.bean.impl.SetuResourceCacheBean;
import org.setu.framework.authorization.bean.impl.SetuResourceCacheParam;
import org.setu.framework.cache.console.CacheApplicationUtils;
import org.setu.framework.cache.redis.RediscacheCache;
import org.setu.framework.constant.SetuConstant;
import org.setu.framework.core.component.SetuBaseComponent;
import org.setu.framework.core.properties.SetuSystemUtil;
import org.setu.framework.exception.BusinessWarnException;
import org.setu.framework.http.request.SetuHttpRequestWrapper;
import org.setu.framework.http.request.XssHttpServletRequestWrapper;
import org.setu.framework.http.response.SetuHttpResponseWrapper;
import org.setu.framework.http.thread.HttpThreadLocalHandler;
import org.setu.framework.monitor.MonitorType;
import org.setu.framework.monitor.thread.MonitorThreadLocalHandler;
import org.setu.framework.thread.AuthThreadLocalHandler;
import org.setu.framework.thread.SetuThreadLocalHandler;
import org.setu.framework.thread.offline.task.SetuOffLineThreadTaskHandler;
import org.setu.framework.util.SetuApplicationUtil;
import org.setu.framework.utils.NetHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartResolver;
/**
 * 
 * @author: hfl 
 * @date:   2018骞�6鏈�28鏃� 涓嬪崍3:20:05
 */
public  class DefaultSetuFilter extends SetuBaseComponent  implements Filter{
	private transient static final Logger logger = LoggerFactory.getLogger(DefaultSetuFilter.class);
	public static final String STATIC_VERSION="static_version";
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		SetuSystemUtil.localIp=initLocalIp();
	}
	protected  String initLocalIp() {
		return NetHelper.getLocalAddress().getHostAddress();
	}
	@Override
	public void destroy() {
		
	}
	/**
	 * filter澶勭悊锛屽鐞嗗畬鎴愯繑鍥瀟rue  澶勭悊鏈畬鎴愯繑鍥瀎alse锛屽皢浼氭墽琛宑hain.doFilter
	 * @param handler
	 * @param request
	 * @param response
	 * @param chain
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	protected  boolean dealFilter(SetuFilterHandler handler,HttpServletRequest  request,HttpServletResponse response,FilterChain chain) throws IOException, ServletException {
		if(handler.url.contains("undefined")){//鎺掗櫎js閿欒璇锋眰
			handler.beforeHandler.forword=true;//璺宠繃
		}
		handler.beforeHandler.skip=skipType(handler.url);//鏄惁璺宠繃楠岃瘉
		if(!handler.beforeHandler.forword){
	        if(!handler.beforeHandler.skip){
	    		logger.debug("++++++++++++++++++++++++++++++ url:{}寮�濮� ++++++++++++++++++++++++++++++",handler.url);
	    		//璧勬簮鍙傛暟澶勭悊鍣�  浠ュ強  璇锋眰鍙傛暟澶勭悊
	    		SetuAuthResourceHandler resourceHandler=dealParam(handler.url, request);
	    		//杩囩▼澶勭悊浠ュ強椤甸潰缂撳瓨澶勭悊
	    		handler.bytes=filterChainHandler(handler.url, request, response, chain);
	    		//缁撴灉闆嗗鐞�
	    		handler.exception=dealResult(handler.bytes, resourceHandler, request, response);
	    		return true;
	        }
		}
		return false;
    	
	}
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest  request=dealHttpRequestWrapper(arg0);
		HttpServletResponse response=dealHttpResponseWrapper(arg1);
		long startTime=System.currentTimeMillis();
		SetuFilterHandler handler=new SetuFilterHandler();
		handler.url=request.getRequestURI();
		try{
			if(!dealFilter(handler, request, response, chain)) {
	        	chain.doFilter(request, ((SetuHttpResponseWrapper) response).getRootResponse());
				   }
		}catch(Exception e){
			handler.exception=e;
			//寮傛绾跨▼
			throw e;
		}finally {
			if(!handler.beforeHandler.forword&&!handler.beforeHandler.skip) {
				String result=new String(handler.bytes,SetuConstant.ENCODING_UTF_8);
				long endTime=System.currentTimeMillis();
				SetuOffLineThreadTaskHandler.exec(handler.url,result,startTime,endTime,handler.exception);
				SetuThreadLocalHandler.RESULT_JSON_STR.set(result);
				logger.debug("{} 鎵ц鏃堕棿锛歿}ms",handler.url,endTime-startTime);
			}
		}
	}
	protected HttpServletRequest dealHttpRequestWrapper(ServletRequest  servletRequest) throws UnsupportedEncodingException {
		HttpServletRequest  request=(HttpServletRequest) servletRequest;
		HttpThreadLocalHandler.REQUEST.set(request);
    	request.setCharacterEncoding(SetuConstant.ENCODING_UTF_8);
    	//绫诲瀷杞崲
		MultipartResolver resolver=(MultipartResolver)SetuApplicationUtil.getApplicationcontext().getBean("commonsMultipartResolver");
		if(resolver.isMultipart(request)) {
			request=resolver.resolveMultipart(request);
		}
		request=new SetuHttpRequestWrapper(request);
		if(SetuSystemUtil.xssFilter){
			request=new XssHttpServletRequestWrapper(request);
		}
		return request;
	}
	protected HttpServletResponse dealHttpResponseWrapper(ServletResponse  servletResponse) throws UnsupportedEncodingException {
		HttpServletResponse  response=(HttpServletResponse) servletResponse;
		HttpThreadLocalHandler.RESPONSE.set(response);
		response.setCharacterEncoding(SetuConstant.ENCODING_UTF_8);
    	return new SetuHttpResponseWrapper(response);
	}
	/**
	 * 	璁剧疆闈欐�佽祫婧愮増鏈彿
	 * @param response
	 */
	protected void setStaticVersion(HttpServletResponse response) {
		//璁剧疆闈欐�佽祫婧愮殑鐗堟湰
//		Cookie staticVersionCookie = new Cookie(STATIC_VERSION, Double.toString(SetuSystemUtil.getStaticVersion()));
//		staticVersionCookie.setMaxAge(-1);
//		staticVersionCookie.setPath("/");
//		staticVersionCookie.setDomain(SetuSystemUtil.crossDomain);
//		response.addCookie(staticVersionCookie);
	}
	
	/**
	 * 璇锋眰鍙傛暟澶勭悊
	 * @param: @param url
	 * @param: @param request      
	 * @return: void      
	 * @throws
	 */
	protected SetuAuthResourceHandler dealParam(String url,HttpServletRequest  request) {
		SetuAuthResourceHandler resourceHandler=AuthThreadLocalHandler.LOCAL_RESOURCE_HANDLER.get();
		if(resourceHandler!=null){
			try {
				resourceHandler.dealParam(request.getParameterMap());
			} catch (BusinessWarnException e) {
				new ServletException(e.getMessage());
			}
		}
		Map<String,String> paramMap=SetuThreadLocalHandler.PARAM_MAP.get();
		for(String key:request.getParameterMap().keySet()){
			paramMap.put(key, request.getParameter(key));
		}
        SetuThreadLocalHandler.REQUEST_PARAM_MAP.set(request.getParameterMap());
		return resourceHandler;
	}
	/**
	 * 杩斿洖鍊煎鐞�
	 * @param: @param resourceHandler
	 * @param: @param bytes
	 * @param: @return
	 * @param: @throws BusinessWarnException      
	 * @return: byte[]      
	 * @throws
	 */
	protected byte[] dealReturnParam(SetuAuthResourceHandler resourceHandler,byte[] bytes) throws BusinessWarnException {
		if(bytes.length>0&&resourceHandler!=null){
			bytes=resourceHandler.dealReturnParam(bytes);
		}
		return bytes;
	}
	/**
	 * 	filterChain鍚庣疆澶勭悊浠ュ強闈欐�佽祫婧愮増鏈彿锛岀粨鏋滈泦锛屽紓甯稿鐞�
	 * @param bytes
	 * @param resourceHandler
	 * @param request
	 * @param responseWrapper
	 * @return
	 * @throws IOException
	 */
	protected Exception dealResult(byte[] bytes,SetuAuthResourceHandler resourceHandler,HttpServletRequest  request,HttpServletResponse response) throws IOException {
		//璁剧疆闈欐�佽祫婧愮増鏈彿
		setStaticVersion(response);
		//閿欒澶勭悊
		Exception exception=(Exception)request.getAttribute("Exception");
		if(bytes.length>0&&resourceHandler!=null){
			try {
				//杩斿洖鍙傛暟澶勭悊
				bytes=dealReturnParam(resourceHandler, bytes);
			} catch (BusinessWarnException e) {
				exception=e;
			}
		}
		if(exception!=null){
			//閿欒澶勭悊
			bytes=errorHandler(exception, response, bytes);
		}
		ServletOutputStream servletOutputStream=((SetuHttpResponseWrapper) response).getRootResponse().getOutputStream();
		//鍐欏叆杩斿洖鍊�
		if(bytes.length>0){
			servletOutputStream.write(bytes);
		}
		servletOutputStream.flush();
		return exception;
	}
	public final static String EXCEPTION_NAME_PREFIX_BUSSINESS="Bussiness";

	/**
	 * 閿欒澶勭悊
	 * @param: @param exception
	 * @param: @param servletOutputStream
	 * @param: @param responseWrapper
	 * @param: @param bytes
	 * @param: @return
	 * @param: @throws IOException      
	 * @return: byte[]      
	 * @throws
	 */
	protected byte[]  errorHandler(Exception exception,HttpServletResponse response,byte[] bytes) throws IOException {
		if(exception instanceof InvocationTargetException){
			//restEasy
			InvocationTargetException e=(InvocationTargetException) exception;
			if(e.getTargetException().getMessage().contains(EXCEPTION_NAME_PREFIX_BUSSINESS)){
				try {
					String msg=e.getTargetException().getMessage().trim();
					try{
						msg=(msg.substring(msg.indexOf("Exception:")+10, msg.indexOf("\n"))).replaceFirst("\r","");
					}catch(Exception e1){
						logger.error("",e1);
					}
					bytes=msg.trim().getBytes(SetuConstant.ENCODING_UTF_8);
					response.setStatus(500);
				} catch (Exception e1) {
					logger.error("",e);
				}
			}
		}
		SetuThreadLocalHandler.EXCEPTION.set(exception);
		return bytes;
	}
	/**
	 * filterChain澶勭悊浠ュ強椤甸潰缂撳瓨澶勭悊
	 * @param: @param url
	 * @param: @param request
	 * @param: @param responseWrapper
	 * @param: @param chain
	 * @param: @param session
	 * @param: @return
	 * @param: @throws IOException
	 * @param: @throws ServletException      
	 * @return: byte[]      
	 * @throws
	 */
	protected byte[] filterChainHandler(String url,HttpServletRequest  request,HttpServletResponse response,FilterChain chain) throws IOException, ServletException {
		SetuHttpResponseWrapper responseWrapper=(SetuHttpResponseWrapper) response;
		byte[] bytes =new byte[]{};
		SetuResourceCacheBean cacheBean=AbstractSetcModulePlatformAuthorizationManager.cacheAbleResMap.get(url);
		if(cacheBean!=null){
			long beginTime=System.currentTimeMillis();
			//鎵ц缂撳瓨
			String key=cacheBean.cachePrefix;
			for(SetuResourceCacheParam param:cacheBean.paramList){
				key+=SetuConstant.SPLIT_CHARACTER_UNDERLINE+param.prefix+SetuConstant.SPLIT_CHARACTER_UNDERLINE;
				key+=request.getParameter(param.parameter);
			}
			RediscacheCache cache=((RediscacheCache)CacheApplicationUtils.getRediscacheCacheManager().getCache(cacheBean.cacheName));
			//鏌ョ湅缂撳瓨
			try{
				bytes=(byte[]) cache.get(key);
			}catch(Exception e){
				logger.error("",e);
			}
	    	MonitorThreadLocalHandler.put(MonitorType.PAGE_CACHE_GET, url,beginTime, System.currentTimeMillis());
			if(bytes!=null){
				responseWrapper.addCookie(new Cookie("cachePrefix", key));
			}else{
				chain.doFilter(request, responseWrapper);
				beginTime=System.currentTimeMillis();
				bytes=responseWrapper.getDataString();
				try{
					cache.put(key, bytes, cacheBean.timeout);
				}catch(Exception e){
					logger.error("",e);
				}
				MonitorThreadLocalHandler.put(MonitorType.PAGE_CACHE_PUT, url,beginTime, System.currentTimeMillis());
			}
			return bytes;
		}else {
			return pageEvictCacheHandler(url, request, responseWrapper, chain);
		}
	}
	/**
	 * 椤甸潰缂撳瓨娓呯悊澶勭悊鍣�
	 * @param: @param url
	 * @param: @param request
	 * @param: @param responseWrapper
	 * @param: @param chain
	 * @param: @param session
	 * @param: @return
	 * @param: @throws IOException
	 * @param: @throws ServletException      
	 * @return: byte[]      
	 * @throws
	 */
	protected byte[] pageEvictCacheHandler(String url,HttpServletRequest  request,SetuHttpResponseWrapper responseWrapper,FilterChain chain) throws IOException, ServletException {
		SetuResourceCacheBean cacheBean=AbstractSetcModulePlatformAuthorizationManager.evictResMap.get(url);
		if(cacheBean!=null){
			long beginTime=System.currentTimeMillis();
			String key=cacheBean.cachePrefix;
			for(SetuResourceCacheParam param:cacheBean.paramList){
				key+=SetuConstant.SPLIT_CHARACTER_UNDERLINE+param.prefix+SetuConstant.SPLIT_CHARACTER_UNDERLINE;
				key+=request.getParameter(param.parameter);
			}
			RediscacheCache cache=((RediscacheCache)CacheApplicationUtils.getRediscacheCacheManager().getCache(cacheBean.cacheName));
			try{
				cache.evict(key);
			}catch(Exception e){
				logger.error("",e);
			}
        	MonitorThreadLocalHandler.put(MonitorType.PAGE_CACHE_PUT, url,beginTime, System.currentTimeMillis());
		}
		chain.doFilter(request, responseWrapper);
		return responseWrapper.getDataString();
	}
	/**
	 * 	鏄惁璺宠浆
	 * @param name
	 * @return
	 */
	protected boolean skipType(String name){
		if(name.contains(".")){
			if(name.contains("jsp")){
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
}