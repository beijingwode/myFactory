package com.wode.api.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.util.ActResult;

/**
 * 功能：获取服务器相关信息
 * @author user
 *
 */
@Controller
@RequestMapping("/sys")
@ResponseBody
public class GetSystemInfoController {

	/**
	 * 获取服务器当前时间
	 */
	@RequestMapping("getTime")
	public ActResult<Object> getNow(HttpServletRequest request){
		return ActResult.success(new Date());
	}
	
}
