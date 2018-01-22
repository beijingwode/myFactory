package com.wode.user.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.mapper.SuborderDao;
import com.wode.tongji.mapper.PvDayStatisticalMapper;
import com.wode.tongji.mapper.SearchDayStatisticalMapper;
import com.wode.tongji.model.PvDayStatistical;
import com.wode.tongji.model.SearchDayStatistical;
import com.wode.tongji.vo.SubOrdersVo;
@Service
public class PvDayStatistic {

	@Autowired
	PvDayStatisticalMapper pvDayStatisticalMapper;
	@Autowired
	SearchDayStatisticalMapper searchDayStatisticalMapper;
	@Autowired
	private SuborderDao SuborderDao;
	
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
	
	public void run() {
		/**
		 * 统计时间为次日，需要计算昨天的用户信息
		 * 
		 * */
		SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		//将统计数据的时间减一天
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
		//根据时间查询用户的活动量
		String yesTerday = si.format(date.getTime());
		this.runReal(yesTerday);
	}

	public void runReal(String yesTerday) {		
		
		Long[] cnts={0L,0L,0L,0L,0L,0L,0L};
		
		Map paramMap=new HashMap();
		paramMap.put("date", yesTerday);
		for(String apiurl:interfaceUrl){
			String response=HttpClientUtil.sendHttpRequest("post", apiurl+"/getDayPvCnt", paramMap);
	        ActResult as = JsonUtil.getObject(response, ActResult.class);
	        if(as.isSuccess()){
	        	JSONArray ary =  (JSONArray)as.getData();
	        	for (int i = 0; i < ary.size(); i++) {
	        		cnts[i]=NumberUtil.toLong(ary.get(i));
				}
	        	break;
	        }
		}
		PvDayStatistical userDay = new PvDayStatistical();
		//统计每日数据的时间  统计时间为次日凌晨
		userDay.setDay(TimeUtil.strToDate(yesTerday));
		userDay.setCreateTime(new Date());
		userDay.setProductCnt(cnts[0]);
		userDay.setIndexCnt(cnts[1]);
		userDay.setSearchCnt(cnts[2]);
		userDay.setShopCnt(cnts[3]);
		userDay.setCartCnt(cnts[4]);
		userDay.setDirectCnt(cnts[5]);

		userDay.setOrderCnt(0L);
		userDay.setElse1Cnt(0L);
		userDay.setElse2Cnt(0L);
		userDay.setElse3Cnt(cnts[6]);			//活动页总数
		List<SubOrdersVo> orders = SuborderDao.selectByDay(null);
		for (SubOrdersVo subOrdersVo : orders) {
			userDay.setOrderCnt(userDay.getOrderCnt()+subOrdersVo.getCnt());
			userDay.setElse1Cnt(userDay.getElse1Cnt()+subOrdersVo.getTotalDealAmount());	//实付金额 含运费不含内购券
			userDay.setElse2Cnt(userDay.getElse2Cnt()+subOrdersVo.getTotalSalesAmount()+subOrdersVo.getFreightPrice());	//销售总额 含内购券及运费
		}
		//添加查询信息
		this.pvDayStatisticalMapper.insert(userDay);
		
		searchKey(yesTerday);
	}

	public void searchKey(String yesTerday) {

		SearchDayStatistical userDay = new SearchDayStatistical();
		//统计每日数据的时间  统计时间为次日凌晨
		userDay.setCreateTime(new Date());
		userDay.setDay(TimeUtil.strToDate(yesTerday));
		
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("date", yesTerday);
		for(String apiurl:interfaceUrl){
			String response=HttpClientUtil.sendHttpRequest("post", apiurl+"/getDaySearchKeyCnt", paramMap);
	        ActResult as = JsonUtil.getObject(response, ActResult.class);
	        if(as.isSuccess()){
	        	JSONArray ary =  (JSONArray)as.getData();
	        	for (Object object : ary) {
					JSONObject json = (JSONObject)object;
					userDay.setSearchKey(json.getString("key"));
					if(StringUtils.isEmpty(userDay.getSearchKey())) continue;
					if(userDay.getSearchKey().length()>200) userDay.setSearchKey(userDay.getSearchKey().substring(0, 200));
					userDay.setSearchCnt(NumberUtil.toLong(json.get("cnt")));
					userDay.setHitsCnt(NumberUtil.toLong(json.get("hits")));
					userDay.setAvgScore(NumberUtil.toDouble(json.get("score")));
					//添加查询信息
					this.searchDayStatisticalMapper.insert(userDay);
				}
	        }
		}
	}
}
