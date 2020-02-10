package com.zxkj.hc.qspt.setu.system;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SetuCustomFilterConfig {
	@Bean
    public FilterRegistrationBean<SetuSessionFilter> setuSessionFilter() {
        FilterRegistrationBean<SetuSessionFilter> registrationBean = new FilterRegistrationBean<SetuSessionFilter>();
        registrationBean.setFilter(new SetuSessionFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,*.ws,*.i");
        registrationBean.setOrder(100);
        return registrationBean;
    }
}
