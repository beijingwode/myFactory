package com.wode.api.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.util.ActResult;
import com.wode.factory.model.Teatime;
import com.wode.factory.service.TeaTimeService;

@Controller
@RequestMapping("/teaTime")
@SuppressWarnings("unchecked")
public class TeaTimeController extends BaseController {    

	@Autowired
	private TeaTimeService teaTimeService;

	/**
	 * 增加下午茶
	 * @param request
	 * @param response
	 * @param uid
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/insert")
	@ResponseBody
    public ActResult<String> logout(HttpServletRequest request, HttpServletResponse response,Teatime teatime) throws IOException {
		teaTimeService.insert(teatime);
		return ActResult.success(true); 
    }
	
	
}
