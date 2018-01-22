/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.GroupSuborderitem;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.user.dao.SuborderitemDao;
import com.wode.factory.user.query.SuborderitemQuery;
import com.wode.factory.user.service.SuborderitemService;
import com.wode.factory.user.vo.SubOrderItemVo2;

import cn.org.rapid_framework.page.Page;

@Service("suborderitemService")
public class SuborderitemServiceImpl extends BaseService<Suborderitem,java.lang.Long> implements  SuborderitemService{
	@Autowired
	@Qualifier("suborderitemDao")
	private SuborderitemDao suborderitemDao;
	
	@Autowired
	private Dao dao;
	public EntityDao getEntityDao() {
		return this.suborderitemDao;
	}
	
	public Page findPage(SuborderitemQuery query) {
		return suborderitemDao.findPage(query);
	}

	@Override
	public List<Suborderitem> findBySubOrderId(String subOrderId) {
		// TODO Auto-generated method stub
		return suborderitemDao.findBySubOrderId(subOrderId);
	}

	@Override
	public PageInfo<SubOrderItemVo2> findForComment(SubOrderItemVo2 vo,int pageNumber,int pageSize) {
		return suborderitemDao.findForComment(vo,pageNumber,pageSize);
	}

	@Override
	public Integer getCountByUserAndProduct(Long productId, Long userId) {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("productId",productId);
		map.put("userId",userId);
		return suborderitemDao.getCountByUserAndProduct(map);
	}

	@Override
	public Integer getTrialCountByUser(Long userId) {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("userId",userId);
		return suborderitemDao.getTrialCountByUser(map);
	}

	@Override
	public BigDecimal getTrialReturnByOrder(String subOrderId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("subOrderId",subOrderId);
		return suborderitemDao.getTrialReturnByOrder(map);
	}

	@Override
	public List<GroupSuborderitem> findByGroupSuborederId(String subOrderId) {
		List<GroupSuborderitem> query = dao.query(GroupSuborderitem.class, Cnd.where("subOrderId","=",subOrderId));
		return query;
	}

	@Override
	public Integer getSuborderitemListByProductId(Map<String, Object> map) {
		return suborderitemDao.getSuborderitemListByProductId(map);
	}

	@Override
	public Integer getCountByPageKeyAndProduct(Long productId, String pageKey) {
		Map map = new HashMap<String,Long>();
		map.put("productId",productId);
		map.put("fromWay",pageKey);
		return suborderitemDao.getCountByPageKeyAndProduct(map);
	}

	@Override
	public List<Suborderitem> findByOrderId(String orderId) {
		return suborderitemDao.findByOrderId(orderId);
	}

	@Override
	public Suborderitem findLastOne(Long skuId, Long userId) {
		return suborderitemDao.findLastOne(skuId, userId);
	}
	
}
