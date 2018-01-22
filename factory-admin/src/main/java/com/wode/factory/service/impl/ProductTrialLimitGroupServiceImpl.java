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
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.FactoryBaseDao;
import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.common.redis.RedisUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.mapper.ProductTrialLimitGroupDao;
import com.wode.factory.mapper.ProductTrialLimitItemDao;
import com.wode.factory.model.ProductTrialLimitGroup;
import com.wode.factory.model.ProductTrialLimitItem;
import com.wode.factory.service.ProductTrialLimitGroupService;

@Service("productTrialLimitGroupService")
public class ProductTrialLimitGroupServiceImpl extends FactoryEntityServiceImpl<ProductTrialLimitGroup> implements  ProductTrialLimitGroupService{
	@Autowired
	private ProductTrialLimitGroupDao dao;
	
	@Autowired
	private ProductTrialLimitItemDao productTrialLimitItemDao;
	
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public FactoryBaseDao<ProductTrialLimitGroup> getDao() {
		return dao;
	}
	@Override
	public Long getId(ProductTrialLimitGroup entity) {
		return entity.getId();
	}

	@Override
	public void setId(ProductTrialLimitGroup entity, Long id) {
		if(entity!=null) {
			entity.setId(id);
		}
	}
	@Override
	public PageInfo getGroupManageListByMap(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ProductTrialLimitGroup> productTrialLimitGroupList =dao.getGroupManageListByMap(params);
		return new PageInfo(productTrialLimitGroupList);
	}
	@Override
	public void addGroupMsg(ProductTrialLimitGroup productTrialLimitGroup) {
		dao.insert(productTrialLimitGroup);
	}
	@Override
	public ProductTrialLimitGroup getGroupMsgById(Long id) {
		ProductTrialLimitGroup group = dao.getById(id);
		Integer totalCount = productTrialLimitItemDao.getTotalCount(group.getId());
		group.setProductCount(totalCount);
		return group;
	}
	@Override
	public void editGroupMsg(ProductTrialLimitGroup productTrialLimitGroup) {
		dao.editGroupMsg(productTrialLimitGroup);
	}
	@Override
	public List<ProductTrialLimitGroup> findGroupOperatorList() {
		return dao.findGroupOperatorList();
	}
	@Transactional
	public void updateStatus(Long groupId,Integer status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", groupId);
		map.put("status", status);
		dao.updateStatus(map);
		map.clear();
		map.put("groupId", groupId);
		List<ProductTrialLimitItem> list = productTrialLimitItemDao.getAssignValidGroupProductIdList(map);
		if(list != null && list.size()>0) {
			//处理缓存中的商品id
			if(status == 0) {//启用 
				for (ProductTrialLimitItem productTrialLimitItem : list) {
					if(redisUtil.getData(RedisConstant.GROUP_LIMIT_PRODUCT_PRE+productTrialLimitItem.getProductId()) == null) {
						redisUtil.setData(RedisConstant.GROUP_LIMIT_PRODUCT_PRE+productTrialLimitItem.getProductId(), String.valueOf(productTrialLimitItem.getProductId()),24*60*60);
					}
				}
			}else {//禁用
				for (ProductTrialLimitItem productTrialLimitItem : list) {
					map.put("productId", productTrialLimitItem.getProductId());
					Integer flag = dao.findGroupIsExsit(map);//判断是否存在于其他有效分组中
					if(redisUtil.getData(RedisConstant.GROUP_LIMIT_PRODUCT_PRE+productTrialLimitItem.getProductId()) != null && flag == 0) {
						redisUtil.del(RedisConstant.GROUP_LIMIT_PRODUCT_PRE+productTrialLimitItem.getProductId());
					}
				}
			}
				
		}
	}
	@Override
	public List<ProductTrialLimitItem> getProductListByMap() {
		return productTrialLimitItemDao.getValidGroupProductIdList();
	}
	@Override
	public void updateGroupStatus() {
		dao.updateGroupStatus();
	}
	@Override
	public void delGroup(Long groupId) {
		dao.delete(groupId);
		productTrialLimitItemDao.deleteByGroupId(groupId);
	}	
}