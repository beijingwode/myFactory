/**
 * 
 */
package com.wode.factory.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.wode.factory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.constant.Constant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;

/**
 * 
 * <pre>
 * 功能说明: 
 * 日期:	2015年10月13日
 * 开发者:	宋艳垒
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 2015年10月13日
 * </pre>
 */
@Controller
@RequestMapping(Constant.sys_url_pre_manager + "/html")
public class HtmlAction {
	
	@Qualifier("redis")
	@Autowired
	private RedisUtil redisUtil;

	private Set<String> interfaceUrl=new HashSet<String>();

	@Qualifier("creat_html_url")
	@Autowired
	public void setInterfaceUrl(String interfaceUrl) {
		String[] arr=interfaceUrl.split(",");
		for(String url:arr){
			if(!StringUtils.isEmpty(url)){
				this.interfaceUrl.add(url);
			}
		}

	}

	@Resource(name = "productService")
	ProductService productService;
	
	
	@RequestMapping("")
	public String index(){
		return "html";
	}
	
	
	@RequestMapping("/create/{page}")
	public @ResponseBody ActResult createIndex(HttpServletRequest request,@PathVariable String page, String pram,Boolean preView) {
		ActResult<String> ret = ActResult.success(null);
		
		Map paramMap=new HashMap();
		paramMap.put("preView", preView);
		paramMap.put("pram", pram);
		for(String apiurl:interfaceUrl){
			String response=HttpClientUtil.sendHttpRequest("post", apiurl+"/index", paramMap);
	        ActResult as = JsonUtil.getObject(response, ActResult.class);
	        if(!as.isSuccess()){
	        	ret.setMsg(apiurl+":"+as.getMsg());
	        	ret.setSuccess(false);
	        }
		}
		
        
		return ret;

	}
	
	
	@RequestMapping("/channel")
	public @ResponseBody ActResult channel(HttpServletRequest request,int ppid) {
		ActResult<String> ret = ActResult.success(null);
		Map paramMap=new HashMap();
		for(String apiurl:interfaceUrl){
			String response=HttpClientUtil.sendHttpRequest("post", apiurl+"/channel/"+ppid, paramMap);
	        ActResult as = JsonUtil.getObject(response, ActResult.class);
	        if(!as.isSuccess()){
	        	ret.setMsg(apiurl+":"+as.getMsg());
	        	ret.setSuccess(false);
	        }
		}
		return ret;
	}
	

	@RequestMapping("/category")
	public @ResponseBody ActResult category(HttpServletRequest request) {
		ActResult<String> ret = ActResult.success(null);
		Map paramMap=new HashMap();
		for(String apiurl:interfaceUrl){
			String response=HttpClientUtil.sendHttpRequest("post", apiurl+"/category", paramMap);
	        ActResult as = JsonUtil.getObject(response, ActResult.class);
	        if(!as.isSuccess()){
	        	ret.setMsg(apiurl+":"+as.getMsg());
	        	ret.setSuccess(false);
	        }
		}
		
        
		return ret;
	}
	
	
	/**
	 * 生成静态产品详情页
	 * @param request
	 * @return
	 */
	@RequestMapping("/product")
	public @ResponseBody ActResult createProductDetail(HttpServletRequest request,Long productId) {
		return productService.createProductHtml(productId);
	}

}
