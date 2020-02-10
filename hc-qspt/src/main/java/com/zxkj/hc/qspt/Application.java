package com.zxkj.hc.qspt;

import org.setu.framework.core.properties.SetuSystemUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.zxkj.hc.qspt.setu.system.SetuSession;

/**
 *
 * @author: hfl
 * @date:   2018年6月27日 下午2:52:57
 */
@SpringBootApplication
@ImportResource({"classpath:applicationContext-all.xml"})
@EnableAspectJAutoProxy (exposeProxy = true)
@EnableTransactionManagement
public class Application
{
    public static void main( String[] args )
    {
    	SetuSystemUtil.sessionBeanClazz=SetuSession.class;
		SetuSystemUtil.devModel=true;
		SetuSystemUtil.sqlfile="classpath:sqlfile";
		SetuSystemUtil.applicationName="hc-qspt";
		SetuSystemUtil.loginAction="";
		SetuSystemUtil.logoutAction="/logout";
		//SetuSystemUtil.loginPage="/loginpage";
		if(args != null && args.length>0) {
			for(String arg:args) {
				if(arg.startsWith("setuFilePath:")) {
					String setuFilePath = arg.substring(13);
					SetuSystemUtil.setuFilePath = setuFilePath;
					System.out.println("使用外部配置文件："+setuFilePath);
				}
			}
		}
        SpringApplication.run(Application.class, args);
    }
}
