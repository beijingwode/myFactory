package com.wode.factory.servlet;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.unionpay.acp.sdk.SDKConfig;

/**
 * Created by zoln on 2015/8/31.
 */
@Component("initListener")
public class InitServlet implements ServletContextAware, ApplicationListener<ContextRefreshedEvent> {

	private ServletContext servletContext;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (servletContext != null && contextRefreshedEvent.getApplicationContext().getParent() == null)
			SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}