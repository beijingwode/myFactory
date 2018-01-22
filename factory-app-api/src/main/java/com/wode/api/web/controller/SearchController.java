package com.wode.api.web.controller;

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
import com.wode.search.SearchParams;
import com.wode.search.WodeSearchManager;
import com.wode.search.WodeSearcher;

/**
 * Created by Bing King on 2015/6/23.
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
	public List<JSONObject> dd(String key,HttpServletRequest request, HttpServletResponse response) {
		List<JSONObject> ret =new ArrayList<JSONObject>();
		if(!com.wode.common.util.StringUtils.isNullOrEmpty(key)){
			try {
				key = URLDecoder.decode(key,"utf-8");
				WodeSearcher searcher = wsm.getSearcher();
				ret = searcher.suggest(key);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
}
