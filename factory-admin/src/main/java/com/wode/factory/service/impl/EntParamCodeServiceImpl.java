/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.factory.mapper.EntParamCodeDao;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.EntParamCodeVo;
import com.wode.factory.service.EntParamCodeService;

@Service("entParamCodeService")
public class EntParamCodeServiceImpl implements  EntParamCodeService{
	public static String REDIS_ENT_PARAM_CODE = "REDIS_ENT_PARAM_CODE_";
	public static String REDIS_FLOW_CODE = "010";	//福利账务流水分组
	@Autowired
	@Qualifier("entParamCodeDao")
	private EntParamCodeDao entParamCodeDao;
    @Autowired
    DBUtils dbUtils;

	@Autowired
	private RedisUtil redis;

	@Override
	public void update(EntParamCode entity) {
		entParamCodeDao.update(entity);
	}
	
	@Override
	public List<EntParamCode> selectByModel(EntParamCode model) {
		return entParamCodeDao.selectByModel(model);
	}

	@Override
	public List<EntParamCodeVo> selectBanks() {
		return entParamCodeDao.selectBanks();
	}

	@Override
	public void delete(Long id) {
		entParamCodeDao.deleteById(id);
	}

	@Override
	public void insert(EntParamCode entity) {
    	entity.setId(dbUtils.CreateID());
		entParamCodeDao.insert(entity);
	}

	@Override
	public Map<String, EntParamCode> getBenefitFlowCode() {
		return getParamCode(REDIS_FLOW_CODE);
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
}
