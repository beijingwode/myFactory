package com.wode.factory.user.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wode.common.util.JsonUtil;
import com.wode.search.SearchParams;
import com.wode.search.WodeSearchManager;
import com.wode.search.WodeSearcher;

/**
 * 商品
 *
 * @author Bing King
 */
@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private WodeSearchManager wsm;

	/**
	 * 搜索页面
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dd")
	@ResponseBody
	public void dd(String jsonpcallback,HttpServletRequest request, HttpServletResponse response) {
		List<JSONObject> ret =new ArrayList<JSONObject>();
		String keyword = request.getParameter(SearchParams.Param.KEYWORD);
		if(!com.wode.common.util.StringUtils.isNullOrEmpty(keyword)){
			try {
				keyword = URLDecoder.decode(keyword,"utf-8");
				WodeSearcher searcher = wsm.getSearcher();
				ret = searcher.suggest(keyword);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		writeCustomJSON(jsonpcallback+"("+JsonUtil.toJson(ret)+")",response);
	}

	/**
	 * 输出json
	 * @param data
	 * json字符
	 * @param response
	 */
	protected void  writeCustomJSON(String data,HttpServletResponse response){
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(data);
		    pw.flush();
		    pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
}
