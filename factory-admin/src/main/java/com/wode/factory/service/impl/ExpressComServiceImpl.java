package com.wode.factory.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.constant.Constant;
import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.outside.service.ExpressService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.service.ExpressComService;
@Service("expressComService")
public class ExpressComServiceImpl extends BaseService<ExpressCompany,Long> implements ExpressComService{

	public static String REDIS_EXPRESS_COM_ALL = "REDIS_EXPRESS_COM_ALL";
	
	@Autowired
	private RedisUtil redis;

    static ExpressService uploadService = ServiceFactory.getExpressService(Constant.OUTSIDE_SERVICE_URL);
    
	@Override
	public EntityDao getEntityDao() {
		return null;
	}

	/**
	 * 取得参数map
	 * @param gropcd
	 * @return
	 */
	private Map<String, ExpressCompany> getParamCode() {
		
		//缓存中取出
		Map<String,String> maps = redis.getMap(REDIS_EXPRESS_COM_ALL);
		Map<String, ExpressCompany> rtn = new HashMap<String, ExpressCompany>();
		if(!maps.isEmpty()) {
			//缓存有数据
			for (String key : maps.keySet()) {
				ExpressCompany epc = JsonUtil.getObject(maps.get(key), ExpressCompany.class);
				rtn.put(key, epc);
			}
		} else {
			
			String allCompInfoJSON = uploadService.companyInfo();
			if(allCompInfoJSON != null && !allCompInfoJSON.equals("")) {
				List<ExpressCompany> allCompInfoList = JsonUtil.getList(allCompInfoJSON, ExpressCompany.class);
				if(allCompInfoList != null && allCompInfoList.size() > 0) {
					for(ExpressCompany r : allCompInfoList) {
						maps.put(r.getId(), JsonUtil.toJson(r));
						rtn.put(r.getId(), r);
					}
					redis.setData(REDIS_EXPRESS_COM_ALL, maps,3600*24);
				}
			}
		}
		return rtn;
	}

	@Override
	public Map<String, ExpressCompany> getExpressCompanys() {
		return getParamCode();
	}

	@Override
	public ExpressCompany getExpressComById(String expressType) {
		return getExpressCompanys().get(expressType);
	}

}
