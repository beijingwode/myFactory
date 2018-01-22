/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.tongji.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.util.CacheManager;
import com.wode.tongji.model.AppDayStatistic;
import com.wode.tongji.service.AppDayStatisticService;

@Controller
@RequestMapping("appDayStatistics")
public class AppDayStatisticsController{
	@Resource
	private AppDayStatisticService appDayStatisticService;

	
	
	/**
	 * 
	 * 功能说明：更新日活量
	 * 日期:	2015年5月20日
	 * 开发者:宋艳垒
	 *
	 * @param mark
	 * @return
	 */
	@RequestMapping("/active")
	public void updateDayActive(AppDayStatistic ads,String imei){
		if(!CacheManager.hasCache(ads.getAppCode()+imei)){
			//根据appCode和手机IMEI号查询缓存，缓存中若没有说明用户当天首次登陆这个app
			appDayStatisticService.updateDayActive(ads);
			CacheManager.add(ads.getAppCode()+imei);
		}
	}
	/**
	 * 功能说明：
	 * 日期:	2015年5月20日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("appDayStatistical")
	public String selectAppDayActive(HttpServletRequest request,Model model){
		Map map = this.selectAppDayActive(request, null, null, null);
		model.addAttribute("appDayInfo", map);
		return "sys/app/appDayStatisticalShow";
	}
	
	@RequestMapping("searchAppDayInfo")
	public @ResponseBody Map selectAppDayActive(HttpServletRequest request,String startTime,String endTime,String mark){
		Map map=new HashMap();
		List<AppDayStatistic> li_appDayInfo = this.appDayStatisticService.selectAppDayStatistic(startTime, endTime, mark);
		if(!li_appDayInfo.isEmpty()){
			List<String> li_AppCode = this.appDayStatisticService.selectDistinctApkCode();
			
			//日活量字符串
			StringBuffer appDayActiveSb = new StringBuffer();
			//时间字符串
			StringBuffer appDayDateSb = new StringBuffer();
			for(int i=0;i<li_appDayInfo.size();i++){
				appDayActiveSb.append(li_appDayInfo.get(i).getActiveAmount());
				appDayDateSb.append(new SimpleDateFormat("yyyy-MM-dd").format(li_appDayInfo.get(i).getDay()));
				if(i!=(li_appDayInfo.size()-1)){
					appDayActiveSb.append(",");
					appDayDateSb.append(",");
				}
			}
			//app日活量
			map.put("appDayActive", appDayActiveSb);
			//app名称
			map.put("appDayCodeName", li_appDayInfo.get(0).getAppCode());
			//app日活量时间
			map.put("appDayDate", appDayDateSb);
			//appCode
			map.put("appCode", li_AppCode);
		}
		
		return map;
	}
}

