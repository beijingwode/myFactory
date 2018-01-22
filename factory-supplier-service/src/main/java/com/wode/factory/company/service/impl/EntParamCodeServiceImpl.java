package com.wode.factory.company.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.company.dao.EntParamCodeDao;
import com.wode.factory.company.service.EntParamCodeService;
import com.wode.factory.model.EntParamCode;
@Service("entParamCodeService")
public class EntParamCodeServiceImpl extends BasePageServiceImpl<EntParamCode> implements EntParamCodeService{

	public static String REDIS_ENT_PARAM_CODE = "REDIS_ENT_PARAM_CODE_";
	public static String REDIS_FLOW_CODE = "010";	//福利账务流水分组
	public static String REDIS_ENT_TYPE_CODE = "020";	//企业类型分组
	public static String REDIS_ENT_INDUSTRY_CODE = "030";	//企业经营行业
	public static String REDIS_BANK_CODE = "040";	//企业经营行业
	public static String REDIS_APP_FIRST_PRIZE_CODE = "080"; //app首次登陆奖励
	
	@Autowired
	EntParamCodeDao entParamCodeDao;

	@Autowired
	private RedisUtil redis;
	

	@Override
	public Map<String, EntParamCode> getBenefitFlowCode() {
		
		return getParamCode(REDIS_FLOW_CODE);
	}

	@Override
	public Map<String, EntParamCode> getEntTypeCode() {
		// TODO Auto-generated method stub
		return getParamCode(REDIS_ENT_TYPE_CODE);
	}

	@Override
	public List<EntParamCode> getBanks() {
		Map<String, EntParamCode> map=getParamCode(REDIS_BANK_CODE);
		List<EntParamCode> ls = new ArrayList<EntParamCode>();
		ls.addAll(map.values());
		Collections.sort(ls,new Comparator<EntParamCode>(){

			@Override
			public int compare(EntParamCode arg0, EntParamCode arg1) {
				int i1 = NumberUtil.toInt(arg0.getCode());
				int i2 = NumberUtil.toInt(arg1.getCode());
				return i1-i2;
			}});
		return ls;
	}
	
	@Override
	public Map<String, EntParamCode> getEntIndustryCode() {
		// TODO Auto-generated method stub
		return getParamCode(REDIS_ENT_INDUSTRY_CODE);
	}
	/**
	 * 取得参数map
	 * @param gropcd
	 * @return
	 */
	private Map<String, EntParamCode> getParamCode(String gropcd) {
		
		//缓存中取出
		Map<String,String> maps = redis.getMap(REDIS_ENT_PARAM_CODE + gropcd);
		Map<String, EntParamCode> rtn = new HashMap<String, EntParamCode>();
		if(!maps.isEmpty()) {
			//缓存有数据
			for (String key : maps.keySet()) {
				EntParamCode epc = JsonUtil.getObject(maps.get(key), EntParamCode.class);
				rtn.put(key, epc);
			}
		} else {
			//缓存没有数据，则从DB中取得数据
			maps =  new HashMap<String, String>();
			EntParamCode m = new EntParamCode();
			m.setGroupCd(gropcd);
			List<EntParamCode> rs= entParamCodeDao.selectByModel(m);
			if(rs != null && rs.size()>0) {
				for(EntParamCode r : rs) {
					maps.put(r.getCode(), JsonUtil.toJson(r));
					rtn.put(r.getCode(), r);
				}
				
				//并将数据暂时存放在缓存中，缓存时间为20分
				redis.setData(REDIS_ENT_PARAM_CODE + gropcd, maps,1200);
			}
		}
		return rtn;
	}

	@Override
	protected BasePageDao<EntParamCode> getBaseDao() {
		return entParamCodeDao;
	}

	@Override
	public Map<String, EntParamCode> getAppFirstPrizeCode() {
		return getParamCode(REDIS_APP_FIRST_PRIZE_CODE);
	}
}
