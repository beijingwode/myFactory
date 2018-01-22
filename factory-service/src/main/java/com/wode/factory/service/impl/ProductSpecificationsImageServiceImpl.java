package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.service.ProductSpecificationsImageService;

@Service("productSpecificationsImageAppService")
public class ProductSpecificationsImageServiceImpl implements ProductSpecificationsImageService{

	@Autowired
	private Dao dao;
	@Autowired
	private RedisUtil redisUtil;
	
	@Override
	public List<ProductSpecificationsImage> findProductPicture(Long specificationsId,Long productId) {

		//缓存规格对应的图片信息
		String jsonM = redisUtil.getMapData(RedisConstant.PRODUCT_PRE + productId, RedisConstant.PRODUCT_REDIS_IMAGE);
		if(!StringUtils.isEmpty(jsonM)) {
			Map<String,String> map = JsonUtil.getMap4Json(jsonM);
			String jsonL = map.get(specificationsId + "");
			if(!StringUtils.isEmpty(jsonL)) {
				return JsonUtil.getList(jsonL, ProductSpecificationsImage.class);
			}
		}
		
		List<ProductSpecificationsImage> pAttr=dao.query(ProductSpecificationsImage.class, Cnd.where("specifications_id", "=", specificationsId));
		return pAttr;
	}
}
