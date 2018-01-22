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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.tongji.model.SearchDayStatistical;
import com.wode.tongji.service.SearchDayStatisticalService;

@Controller
@RequestMapping("searchKey")
public class SearchKeyStatisticalController {
	
	@Autowired
	SearchDayStatisticalService searchDayStatisticalService;


	@RequestMapping(value="base")
	public String base(HttpServletRequest request,HttpServletResponse response,Model model){
		return "tongji/search_key/base";
	}
	@RequestMapping("/list")
	public String list(@RequestParam Map<String, Object> params,ModelMap model) {
		model.addAttribute("page", searchDayStatisticalService.getPage(params));
		return "tongji/search_key/list";
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping(value="pvDay")
	public String toUserDayStatistical(HttpServletRequest request,HttpServletResponse response,Model model,String searchKey){
		Map map = this.byYearAndMonthSearch(request,searchKey, null,null);
		model.addAttribute("dayInfo", map);
		model.addAttribute("searchKey", searchKey);
		return "tongji/search_key/dayStatistical";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@RequestMapping("daySearch")
	@ResponseBody
	public Map byYearAndMonthSearch(HttpServletRequest request,String searchKey,String startTime,String endTime){
		Map map=new HashMap();
		List<SearchDayStatistical> li_userDay = null;
		li_userDay = this.searchDayStatisticalService.day(searchKey,startTime,endTime);
		if(!li_userDay.isEmpty()){
			//日检索次数
			StringBuffer dayCnt = new StringBuffer();
			//平均命中商品数
			StringBuffer dayHit = new StringBuffer();
			//命中率
			StringBuffer dayScore = new StringBuffer();
			//月份字符串
			StringBuffer dateSb = new StringBuffer();
			/*#################################################*/
			for(int i=0;i<li_userDay.size();i++){
				dayCnt.append(li_userDay.get(i).getSearchCnt());
				dayHit.append(li_userDay.get(i).getHitsCnt());
				dayScore.append(li_userDay.get(i).getAvgScore()*li_userDay.get(i).getSearchCnt());
				dateSb.append(new SimpleDateFormat("yyyy-MM-dd").format(li_userDay.get(i).getDay()));
				if(i!=(li_userDay.size()-1)){
					dateSb.append(",");
					dayCnt.append(",");
					dayHit.append(",");
					dayScore.append(",");
				}
			}
			
			map.put("dayCnt", dayCnt);
			map.put("dayHit", dayHit);
			map.put("dayScore", dayScore);
			map.put("date", dateSb);
		}
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="month")
	public String toUserMonthStatistical(HttpServletRequest request,String searchKey,HttpServletResponse response,Model model){
		Map map = this.byYearSearch(request,searchKey, null,null);
		model.addAttribute("monthInfo", map);
		model.addAttribute("searchKey", searchKey);
		return "tongji/search_key/monthStatistical";
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("monthSearch")
	@ResponseBody
	public  Map byYearSearch(HttpServletRequest request,String searchKey,String monthStartTime,String monthEndTime){
		Map map=new HashMap();
		List<SearchDayStatistical> li_userMonth = null;
		try {
			li_userMonth = this.searchDayStatisticalService.month(searchKey,monthStartTime,monthEndTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(!li_userMonth.isEmpty()){
			//日检索次数
			StringBuffer dayCnt = new StringBuffer();
			//平均命中商品数
			StringBuffer dayHit = new StringBuffer();
			//命中率
			StringBuffer dayScore = new StringBuffer();
			//年份字符串
			StringBuffer dateYearSb = new StringBuffer();
			
			for(int i=0;i<li_userMonth.size();i++){
				dayCnt.append(li_userMonth.get(i).getSearchCnt());
				dayHit.append(li_userMonth.get(i).getHitsCnt());
				dayScore.append(li_userMonth.get(i).getAvgScore()*li_userMonth.get(i).getSearchCnt());
				dateYearSb.append(new SimpleDateFormat("yyyy-MM").format(li_userMonth.get(i).getDay()));
				if(i!=(li_userMonth.size()-1)){
					dateYearSb.append(",");
					dayCnt.append(",");
					dayHit.append(",");
					dayScore.append(",");
				}
			}
			map.put("dayCnt", dayCnt);
			map.put("dayHit", dayHit);
			map.put("dayScore", dayScore);
			map.put("dateYear", dateYearSb);
		}
		return map;
	}
}
