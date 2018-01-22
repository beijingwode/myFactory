package com.wode.user.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.constant.Constant;
import com.wode.common.util.ActResult;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.tongji.mapper.UserGeoMapper;
import com.wode.tongji.model.UserGeo;

@Service
public class UserGeoStatistic  {
	@Autowired
	UserGeoMapper userGeoMapper;
	
	public void run() {
		SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		//统计每日数据的时间  统计时间为次日凌晨
		//将统计数据的时间减一天
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
		//根据时间查询用户的活动量
		String yesTerday = si.format(date.getTime());

		CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
		ActResult<List<String>> rs = us.getCityRegisterCnt(yesTerday);
		if(rs.isSuccess()){
			
			for (String jsonString : rs.getData()) {
				Map map = JsonUtil.getMap4Json(jsonString);
				String cityName = (String)map.get("city");
				int count =NumberUtil.toInt(map.get("cnt"));
				UserGeo ug = userGeoMapper.selectByPrimaryKey(cityName);
				/**
				 * 若各ug存在则更新ug信息，若不存在做插入操作
				 */
				if(ug != null){
					long allCount = ug.getRegistNumber()+count;
					ug.setRegistNumber(allCount);
					userGeoMapper.updateByPrimaryKeySelective(ug);
				}else{
					ug = new UserGeo();
					ug.setCity(cityName);
					ug.setRegistNumber((long)count);
					ug.setUpdateTime(new Date());
					userGeoMapper.insertSelective(ug);
				}
			}
		}
	}
}
