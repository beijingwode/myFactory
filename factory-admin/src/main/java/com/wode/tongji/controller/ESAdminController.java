/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.tongji.controller;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.search.WodeSearchManager;

@Controller
@RequestMapping("esadmin")
public class ESAdminController{
	@Autowired
	private WodeSearchManager wsm;
	
    private final Logger logger = LoggerFactory.getLogger(ESAdminController.class);
	
	/**
	 * 删除
	* @param id 用户id
	* @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="createIndex")
	public @ResponseBody Integer createIndex(String index,int version) throws ParseException{
		wsm.createIndex(index, version);
		wsm.putMapping(index, version);
		return 1;
	}

	/**
	 * 删除
	* @param id 用户id
	* @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="versionUp")
	public @ResponseBody Integer versionUp(String index,int version,String create,String refreshData,String delOld) throws ParseException{
		if("1".equals(create)) {
			wsm.createIndex(index, version);
			wsm.putMapping(index, version);
		}
		wsm.versionUp(index, version);
		if("1".equals(refreshData)) {
			wsm.refreshWodeData(version-1);
		}
		if("1".equals(delOld)) {
			wsm.dropIndex(index, version-1);
		}
		return 1;
	}

	/**
	 * 删除
	* @param id 用户id
	* @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="dropData")
	public @ResponseBody Integer dropData(String index) throws ParseException{
		wsm.removeAllWodeProduct();
		return 1;
	}

	/**
	 * 删除
	* @param id 用户id
	* @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="refreshWodeData")
	public @ResponseBody Integer refreshWodeData(String index,int version) throws ParseException{
		wsm.refreshWodeData(version-1);
		return 1;
	}

	/**
	 * 删除
	* @param id 用户id
	* @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="refreshKey")
	public @ResponseBody Integer refreshKey() throws ParseException{
		wsm.refreshKey();
		return 1;
	}
}

