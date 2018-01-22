package com.wode.factory.task;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.service.ProductService;


/**
 * 
 * <pre>
 * 功能说明: 商品定时任务
 * 日期:	2015年8月18日
 * 开发者:	宋艳垒
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 2015年8月18日
 * </pre>
 */
@Component
public class ProductTask {

	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Autowired
	private ProductService productService;
	
	Logger log=LoggerFactory.getLogger(ProductTask.class);
	
	
	/**
	 * 
	 * 功能说明：热卖商品推荐 
	 * 日期:	2015年8月18日
	 * 开发者:宋艳垒
	 *
	 */
	@Scheduled(cron="0 0/15 * * * ?") 
	public void recormendHotPro(){
		Long id = null;
		//查询最后一级分类
		List<ProductCategory> listProCat = productCategoryService.findLastLevel(id);
		
		if(!StringUtils.isEmpty(listProCat)){
			for(ProductCategory proCat : listProCat){
				cacheRecommendHot(proCat.getId());
			}
		}
		
	}
	
	/**
	 * 
	 * 功能说明：缓存推荐商品id
	 * 日期:	2015年8月18日
	 * 开发者:宋艳垒
	 *
	 * @param categoryId
	 */
	private void cacheRecommendHot(Long categoryId) {
		List<Long> ids = new ArrayList<Long>();
		Product parPro = new Product();
		parPro.setCategoryId(categoryId);
			
		List<Product> list = productService.findRecormendHotPro(parPro);
		for(Product p:list){
			if(p.getSelfType()==null ||(p.getSelfType()!=2 && p.getSelfType()!=1)) {
				ids.add(p.getId());
			}
		}
		redisUtil.setMapData(RedisConstant.HOT_SELL, categoryId+"", JsonUtil.toJsonString(ids));
	}
	
}
