/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.Product;
import com.wode.factory.user.dao.ProductDao;
import com.wode.factory.user.model.ProductBrandLevel;
import com.wode.factory.user.query.ProductQuery;
import com.wode.factory.user.service.ProductService;
import com.wode.factory.vo.ProductVo;

import cn.org.rapid_framework.page.Page;

@Service("user-productService")
public class ProductServiceImpl extends BaseService<Product,java.lang.Long> implements  ProductService{
	@Autowired
	@Qualifier("productDao")
	private ProductDao productDao;
    @Qualifier("redis")
    @Autowired
    private RedisUtil redisUtil;
	private static Logger logger= LoggerFactory.getLogger(ProductSpecificationsServiceImpl.class);
    
	public EntityDao getEntityDao() {
		return this.productDao;
	}
	
	public Page findPage(ProductQuery query) {
		return productDao.findPage(query);
	}

	@Override
	public List<Product> selectByShop(Long id,Long shopId) {
		return productDao.selectByShop(id,shopId);
	}
	
	@Override
	public Product getById(Long id) {
		ProductVo pdv =null;
		String json=redisUtil.getMapData(RedisConstant.PRODUCT_PRE+id, RedisConstant.PRODUCT_REDIS_INFO);
		if(json!=null){
			pdv=JsonUtil.getObject(json, ProductVo.class);
		} 
		if(pdv==null){
			logger.warn("缓存数据异常，缓存中无法获取商品base信息，商品ID:"+id);

			return super.getById(id);
		} else {
			return pdv;
		}
	}
	
	@Override
	public String[] sortArray(String[] old) {
		if(old==null || old.length<1) return null;
		String[] rtn = new String[old.length];
		List<ProductBrandLevel> ls = new ArrayList<ProductBrandLevel>();
		for (String string : old) {
			ProductBrandLevel pbl = new ProductBrandLevel(getBrandLevel(string),string);
			ls.add(pbl);
		}
		
		Collections.sort(ls,new Comparator<ProductBrandLevel>(){

			@Override
			public int compare(ProductBrandLevel arg0, ProductBrandLevel arg1) {
				return arg0.getLevel()-arg1.getLevel();
			}});
		
		for (int i=0;i<ls.size();i++) {
			rtn[i] = ls.get(i).getName();
		}
		return rtn;
	}

	@Override
	public Integer getBrandLevel(String name) {
		String level = redisUtil.getMapData("REDIS_PRODUCT_BRAND", name);
		if(level==null || "".equals(level)) {
			Integer result = 10;
			Long l=productDao.getBrandLevel(name);
			if(l!=null && l>0) {
				result=l.intValue();
			}
			redisUtil.setMapData("REDIS_PRODUCT_BRAND", name, result+"");
			
			return result;
		} else {
			return Integer.parseInt(level);
		}
	}
}
