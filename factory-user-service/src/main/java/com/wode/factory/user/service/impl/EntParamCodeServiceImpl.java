package com.wode.factory.user.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.user.dao.EntParamCodeDao;
import com.wode.factory.user.service.EntParamCodeService;
@Service("entParamCodeService")
public class EntParamCodeServiceImpl extends BaseService<EntParamCode,Long> implements EntParamCodeService{

	public static String REDIS_ENT_PARAM_CODE = "REDIS_ENT_PARAM_CODE_";
	public static String REDIS_FLOW_CODE = "010";	//福利账务流水分组
	public static String REDIS_ENT_TYPE_CODE = "020";	//企业类型分组
	public static String REDIS_ENT_INDUSTRY_CODE = "030";	//企业经营行业
	public static String REDIS_APP_FIRST_PRIZE_CODE = "080";	//app首次登陆奖励
	public static String REDIS_PURCHASEDFLOW_CODE = "110";    //换领账户流水分组
	public static BigDecimal MAX_SUBSIDY = BigDecimal.valueOf(999999);    //内购券最大值
	
	@Autowired
	EntParamCodeDao entParamCodeDao;

	@Autowired
	private RedisUtil redisUtil;
	
	@Override
	public EntParamCodeDao getEntityDao() {
		return entParamCodeDao;
	}

	@Override
	public Map<String, EntParamCode> getBenefitFlowCode() {
		
		return getParamCode(REDIS_FLOW_CODE);
	}

	@Override
	public Map<String, EntParamCode> getEntTypeCode() {
		return getParamCode(REDIS_ENT_TYPE_CODE);
	}

	@Override
	public Map<String, EntParamCode> getEntIndustryCode() {
		return getParamCode(REDIS_ENT_INDUSTRY_CODE);
	}

	@Override
	public Map<String, EntParamCode> getAppFirstPrizeCode() {
		return getParamCode(REDIS_APP_FIRST_PRIZE_CODE);
	}
	
	@Override
	public Map<String, EntParamCode> getPurchasedFlowCode() {
		return getParamCode(REDIS_PURCHASEDFLOW_CODE);
	}
	
	/**
	 * 取得参数map
	 * @param gropcd
	 * @return
	 */
	private Map<String, EntParamCode> getParamCode(String gropcd) {
		
		//缓存中取出
		Map<String,String> maps = redisUtil.getMap(REDIS_ENT_PARAM_CODE + gropcd);
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
				redisUtil.setData(REDIS_ENT_PARAM_CODE + gropcd, maps,1200);
			}
		}
		return rtn;
	}

	@Override
	public BigDecimal getBenefitSubsidy() {
//		EntParamCode fistPrize = getAppFirstPrizeCode().get("003");
//		if(fistPrize!=null && !"1".equals(fistPrize.getStopFlg())) {
//			return NumberUtil.toBigDecimal(fistPrize.getValue());
//		} else {
			return MAX_SUBSIDY;
//		}
	}

	@Override
	public BigDecimal getGroupMasterPrize() {
		EntParamCode prize = getAppFirstPrizeCode().get("004");
		if(prize!=null && !"1".equals(prize.getStopFlg())) {
			return NumberUtil.toBigDecimal(prize.getValue());
		} else {
			return BigDecimal.ZERO;
		}
	}
}
