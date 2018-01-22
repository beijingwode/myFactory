package com.wode.factory.supplier.open.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.factory.model.OpenResponse;

public class BaseController {
	public Logger logger = LoggerFactory.getLogger(getClass()); 
	

	@ExceptionHandler
	@ResponseBody
	public Object te(HttpServletRequest request, Exception ex){
		ex.printStackTrace();
		logger.info("openapi is exception {}" ,ex);
		return OpenResponse.fail("系统异常");
	}

}
