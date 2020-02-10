package com.zxkj.hc.qspt.setu.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.setu.framework.exception.AbstractException;
import org.setu.framework.exception.AbstractRuntimeException;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@RestController
public class SetuWebServerConfigurer {
	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer(){
		return new WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>() {

			@Override
			public void customize(ConfigurableServletWebServerFactory factory) {
				factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html")
						,new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500")
						);
			}
			
		};
		
	}
	@Bean
	public  WebMvcConfigurer  webMvcConfigurer() {
		return new WebMvcConfigurer() {
		    /**
		     * 添加静态资源文件，外部可以直接访问地址
		     * @param registry
		     */
		    @Override
		    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		        registry.addResourceHandler("/**").addResourceLocations("classpath:/pages/");
		    }
		};
	}
	@Bean
	public CommonsMultipartResolver commonsMultipartResolver() {
		CommonsMultipartResolver resolver= new CommonsMultipartResolver();
		resolver.setMaxUploadSize(10000000);
		resolver.setDefaultEncoding("UTF-8");
		return resolver;
	}
	@RequestMapping("/500")
    @ResponseBody
    public SetuResult error(HttpServletRequest request,HttpServletResponse response,Exception exception){
        Object obj= request.getAttribute("javax.servlet.error.exception");
        if(obj instanceof AbstractRuntimeException) {
        	AbstractRuntimeException e=(AbstractRuntimeException) obj;
            return new SetuResult(e.getCode(),e.getMessage());
        }else if(obj instanceof AbstractException) {
        	AbstractException e=(AbstractException) obj;
            return new SetuResult(e.getCode(),e.getMessage());
        }
        Throwable t = (Throwable) obj;
        return new SetuResult(500,t.getMessage());
    }

}
