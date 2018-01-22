package com.wode.tongji.controller;

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

import com.wode.tongji.model.PvDayStatistical;
import com.wode.tongji.service.PvDayStatisticalService;

@Controller
@RequestMapping("pvDayStatistical")
public class PvDayStatisticalController {
	
	@Autowired
	PvDayStatisticalService pvDayStatisticalService;
	

	@SuppressWarnings("rawtypes")
	@RequestMapping(value="pvDay")
	public String toUserDayStatistical(HttpServletRequest request,HttpServletResponse response,Model model){
		Map map = this.byYearAndMonthSearch(request, null,null);
		model.addAttribute("dayInfo", map);
		return "sys/pvStatistical/dayStatistical";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@RequestMapping("daySearch")
	@ResponseBody
	public Map byYearAndMonthSearch(HttpServletRequest request,String startTime,String endTime){
		Map map=new HashMap();
		List<PvDayStatistical> li_userDay = null;
		li_userDay = this.pvDayStatisticalService.day(startTime,endTime);
		if(!li_userDay.isEmpty()){
			//日活量信息字符串
			StringBuffer dayPv = new StringBuffer();
			//日注量信息字符串
			StringBuffer dayOrder = new StringBuffer();
			//日注量信息字符串
			StringBuffer dayCart = new StringBuffer();
			//日注量信息字符串
			StringBuffer daySearch = new StringBuffer();
			//月份字符串
			StringBuffer dateSb = new StringBuffer();
			//月份字符串
			StringBuffer dateSale = new StringBuffer();
			StringBuffer dateSaleAll = new StringBuffer();
			/*#################################################*/
			for(int i=0;i<li_userDay.size();i++){
				dayPv.append(li_userDay.get(i).getProductCnt()+li_userDay.get(i).getIndexCnt()+li_userDay.get(i).getShopCnt());
				dayOrder.append(li_userDay.get(i).getOrderCnt());
				dayCart.append(li_userDay.get(i).getCartCnt()+li_userDay.get(i).getDirectCnt());
				dateSale.append(li_userDay.get(i).getElse1Cnt());
				daySearch.append(li_userDay.get(i).getSearchCnt());
				dateSaleAll.append(li_userDay.get(i).getElse2Cnt());
				dateSb.append(new SimpleDateFormat("yyyy-MM-dd").format(li_userDay.get(i).getDay()));
				if(i!=(li_userDay.size()-1)){
					dateSb.append(",");
					dayCart.append(",");
					dayPv.append(",");
					dayOrder.append(",");
					dateSale.append(",");
					daySearch.append(",");
					dateSaleAll.append(",");
				}
			}
			
			map.put("dayPv", dayPv);
			map.put("dayCart", dayCart);
			map.put("dayOrder", dayOrder);
			map.put("dateSale", dateSale);
			map.put("daySearch", daySearch);
			map.put("dateSaleAll", dateSaleAll);
			map.put("date", dateSb);
		}
		return map;
	}
}
