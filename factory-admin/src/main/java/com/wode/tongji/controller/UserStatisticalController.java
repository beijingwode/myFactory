package com.wode.tongji.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.wode.tongji.model.UserDayStatistical;
import com.wode.tongji.model.UserGeoInfo;
import com.wode.tongji.model.UserMonthStatistical;
import com.wode.tongji.service.UserStatisticalService;

@Controller
@RequestMapping("userStatistical")
public class UserStatisticalController {
	
	@Autowired
	UserStatisticalService userStatisticalServiceImpl;
	

	@SuppressWarnings("rawtypes")
	@RequestMapping(value="userDay")
	public String toUserDayStatistical(HttpServletRequest request,HttpServletResponse response,Model model){
		Map map = this.byYearAndMonthSearch(request, null,null);
		model.addAttribute("dayInfo", map);
		return "sys/userStatistical/userDayStatistical";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="userMonth")
	public String toUserMonthStatistical(HttpServletRequest request,HttpServletResponse response,Model model){
		Map map = this.byYearSearch(request, null,null);
		model.addAttribute("monthInfo", map);
		return "sys/userStatistical/userMonthStatistical";
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("monthSearch")
	@ResponseBody
	public  Map byYearSearch(HttpServletRequest request,String monthStartTime,String monthEndTime){
		Map map=new HashMap();
		List<UserMonthStatistical> li_userMonth = null;
		try {
			li_userMonth = this.userStatisticalServiceImpl.month(monthStartTime,monthEndTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!li_userMonth.isEmpty()){
			//月注量信息字符串
			StringBuffer monthRegisterSb = new StringBuffer();
			//年份字符串
			StringBuffer dateYearSb = new StringBuffer();
			//月活量信息字符串
			StringBuffer monthActiveSb = new StringBuffer();
			
			for(int i=0;i<li_userMonth.size();i++){
				monthRegisterSb.append(li_userMonth.get(i).getRegisterNumber());
				dateYearSb.append(new SimpleDateFormat("yyyy-MM").format(li_userMonth.get(i).getMonth()));
				monthActiveSb.append(li_userMonth.get(i).getActiveNumber());
				if(i!=(li_userMonth.size()-1)){
					dateYearSb.append(",");
					monthRegisterSb.append(",");
					monthActiveSb.append(",");
				}
			}
			map.put("monthRegister", monthRegisterSb);
			map.put("monthActive", monthActiveSb);
			map.put("dateYear", dateYearSb);
		}
		return map;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@RequestMapping("daySearch")
	@ResponseBody
	public  Map byYearAndMonthSearch(HttpServletRequest request,String startTime,String endTime){
		Map map=new HashMap();
		List<UserDayStatistical> li_userDay = null;
		li_userDay = this.userStatisticalServiceImpl.day(startTime,endTime);
		if(!li_userDay.isEmpty()){
			//日活量信息字符串
			StringBuffer dayActiveSb = new StringBuffer();
			//日注量信息字符串
			StringBuffer dayRegisterSb = new StringBuffer();
			//月份字符串
			StringBuffer dateSb = new StringBuffer();
			/*#################################################*/
			for(int i=0;i<li_userDay.size();i++){
				dayActiveSb.append(li_userDay.get(i).getActiveNumber());
				dayRegisterSb.append(li_userDay.get(i).getRegisterNumber());
				dateSb.append(new SimpleDateFormat("yyyy-MM-dd").format(li_userDay.get(i).getDay()));
				if(i!=(li_userDay.size()-1)){
					dateSb.append(",");
					dayRegisterSb.append(",");
					dayActiveSb.append(",");
				}
			}
			
			map.put("dayActive", dayActiveSb);
			map.put("dayRegister", dayRegisterSb);
			map.put("date", dateSb);
		}
		return map;
	}
	/**
	 * 功能说明：全部城市的信息
	 * 日期:	2015年5月22日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param model
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping("allUserGeoInfo")
	public String userGeoInfo(Model model) {
		UserGeoInfo ugi = this.userStatisticalServiceImpl.allCity();
		model.addAttribute("info", new JSONArray().toJSON(ugi));
		return "sys/map/userGeoStatistical";
	}
}
