package com.wode.factory.supplier.open.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.redis.RedisUtil;

/**
 * Created by Bing King on 2015/3/3.
 */
public class OpenApiInterceptor implements HandlerInterceptor {
	
	@Qualifier("redis")
    @Autowired
    private RedisUtil redisUtil;
	
	public Logger logger = LoggerFactory.getLogger(getClass()); 
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {	
		if(request.getRequestURL().toString().endsWith("open") || request.getRequestURL().toString().endsWith("error")  || request.getRequestURL().toString().endsWith("doc")){
			return true;
		}else{
			if("org.apache.catalina.core.ApplicationHttpRequest".equals(request.getClass().getName())) {
				return true;
			}
		}
		response.sendRedirect(request.getContextPath() + "/open/error?message=" + URLEncoder.encode("请求错误，请查看请求地址","UTF-8"));
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}

}
