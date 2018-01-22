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
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.common.redis.RedisUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.mapper.ProductTrialLimitGroupDao;
import com.wode.factory.mapper.ProductTrialLimitItemDao;
import com.wode.factory.model.LimitSupplierVo;
import com.wode.factory.model.ProductTrialLimitGroup;
import com.wode.factory.model.ProductTrialLimitItem;
import com.wode.factory.service.ProductTrialLimitItemService;
import com.wode.factory.vo.ProductTrialLimitItemProductVo;

@Service("productTrialLimitItemService")
public class ProductTrialLimitItemServiceImpl extends FactoryEntityServiceImpl<ProductTrialLimitItem> implements  ProductTrialLimitItemService{
	@Autowired
	private ProductTrialLimitItemDao dao;
	
	@Autowired
	private ProductTrialLimitGroupDao productTrialLimitGroupDao;
	
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public ProductTrialLimitItemDao getDao() {
		return dao;
	}
	@Override
	public Long getId(ProductTrialLimitItem entity) {
		return entity.getId();
	}

	@Override
	public void setId(ProductTrialLimitItem entity, Long id) {
		if(entity!=null) {
			entity.setId(id);
		}
	}
	
	@Override
	public List<ProductTrialLimitItem> getListByProductId(Long productId) {
		return dao.getListByProductId(productId);
	}
	@Override
	public ProductTrialLimitItem getProductTrialLimitItemByProductId(Long productId) {
		return dao.getProductTrialLimitItemByProductId(productId);
	}
	@Override
	public PageInfo<ProductTrialLimitItemProductVo> getGroupLimitProductList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ProductTrialLimitItemProductVo> list = dao.getGroupLimitProductList(params);
		return new PageInfo(list) ;
	}
	@Override
	public List<LimitSupplierVo> findSupplierByProductId() {
		return dao.findSupplierByProductId();
	}
	@Override
	public void delProductById(Long id, Long productId,Long groupId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("productId", productId);
		map.put("groupId", groupId);
		Integer totalCount = productTrialLimitGroupDao.findGroupIsExsit(map);
		if(totalCount == 0) {//如果该商品id不存在其他有效分组中，则直接删除缓存中的商品id
			if(redisUtil.getData(RedisConstant.GROUP_LIMIT_PRODUCT_PRE+productId) != null) {
				redisUtil.del(RedisConstant.GROUP_LIMIT_PRODUCT_PRE+productId);
			}
		}
		dao.delProductById(id);		
	}
	@Override
	public void addGroupLimitProduct(ProductTrialLimitItem productTrialLimitItem) {
		ProductTrialLimitGroup productTrialLimitGroup = productTrialLimitGroupDao.getGroupById(productTrialLimitItem.getGroupId());
		if(productTrialLimitGroup != null) {//有效时间内分组中===处理缓存中商品id
			Long productId = productTrialLimitItem.getProductId();
//			if(redisUtil.getData(RedisConstant.GROUP_LIMIT_PRODUCT_PRE+productId) == null) {
				redisUtil.setData(RedisConstant.GROUP_LIMIT_PRODUCT_PRE+productId, String.valueOf(productId),24*60*60);
//			}
		}
		dao.insert(productTrialLimitItem);
	}
	@Override
	public ProductTrialLimitItem getGroupLimitProductByMap(Map<String, Object> map) {
		return dao.getGroupLimitProductByMap(map);
	}	
}