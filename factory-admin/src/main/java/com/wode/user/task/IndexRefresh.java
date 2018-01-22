package com.wode.user.task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;


@Service
public class IndexRefresh {

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
	
	/**
	 * 添加月统计信息
	 */
	public void run() {
		ActResult<String> ret = ActResult.success(null);
		
		Map paramMap=new HashMap();
		for(String apiurl:interfaceUrl){
			String response=HttpClientUtil.sendHttpRequest("post", apiurl+"/index", paramMap);
	        ActResult as = JsonUtil.getObject(response, ActResult.class);
	        if(!as.isSuccess()){
				HttpClientUtil.sendHttpRequest("post", apiurl+"/index_m", paramMap);
	        }
		}
	}
}
