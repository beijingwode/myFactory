package com.wode.factory.service.impl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.Attribute;
import com.wode.factory.service.CategoryAttributeService;

@Service("categoryAttributeService")
public class CategoryAttributeServiceImpl implements CategoryAttributeService {

	
	
	private Dao dao;
	
	private RedisUtil redisUtil;
	
	
	private static Logger logger= LoggerFactory.getLogger(CategoryAttributeServiceImpl.class);
	
	
	
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}

	@Autowired
	public void setRedisUtil(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}


	
	
	public List<Attribute> findByCategory(Long categoryid) {
		
		String json = redisUtil.getMapData("Category_Attribute",categoryid+"");
		if(!StringUtils.isEmpty(json)){
			return JsonUtil.getList(json, Attribute.class);
		}else{
			logger.warn("缓存分类"+categoryid+"的属性失效");
			List<Attribute> ret= dao.query(Attribute.class, Cnd.where("categoryId", "=", categoryid).asc("orders"));
			if(ret!=null&&ret.size()>0){
				redisUtil.setMapData("Category_Attribute", categoryid+"", JsonUtil.toJson(ret));
			}
			return ret;
		}
		
	}
	
	

}
