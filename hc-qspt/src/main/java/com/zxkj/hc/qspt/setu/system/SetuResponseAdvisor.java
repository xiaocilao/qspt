package com.zxkj.hc.qspt.setu.system;
import java.io.IOException;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.view.RedirectView;


@ControllerAdvice
public class SetuResponseAdvisor implements ResponseBodyAdvice<Object> {

    /**
     * 判断支持的类型 
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    	if("java.lang.String".equals(returnType.getGenericParameterType().getTypeName())) {
    		return false;
    	}
    	//注意，这里必须返回true，否则不会执行beforeBodyWrite方法 
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
    	if(body==null &&"void".equals(returnType.getGenericParameterType().getTypeName())) {
    		return body;
    	}
    	if (body instanceof SetuResult) {
            return body;
        }else if (body instanceof RedirectView) {
            return body;
        }else if(body!=null&&body.getClass().isAssignableFrom(byte[].class)) {
    		try {
				response.getBody().write((byte[])body);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
    		return null;
    	}
    	SetuResult result=new SetuResult(body);
        return result;
    }

}