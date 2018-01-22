/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.user.dao.SuborderitemDao;
import com.wode.factory.user.query.SuborderitemQuery;
import com.wode.factory.user.vo.SubOrderItemVo2;

import cn.org.rapid_framework.page.Page;

@Repository("suborderitemDao")
public class SuborderitemDaoImpl extends BaseDao<Suborderitem,java.lang.Long> implements SuborderitemDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SuborderitemMapper";
	}
	
	public void saveOrUpdate(Suborderitem entity){
		if(entity.getSubOrderItemId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(SuborderitemQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public List<Suborderitem> findBySubOrderId(String subOrderId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findBySubOrderId", subOrderId);
	}

	@Override
	public PageInfo<SubOrderItemVo2> findForComment(SubOrderItemVo2 vo,int pageNumber,int pageSize) {

        List<SubOrderItemVo2> list = getSqlSession().selectList(getIbatisMapperNamesapce() + ".findForComment", vo, new RowBounds(pageNumber, pageSize));
        return new PageInfo<SubOrderItemVo2>(list);
	}

	@Override
	public Integer getCountByUserAndProduct(Map<String, Long> map) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".getCountByUserAndProduct", map);
	}

	@Override
	public Integer getTrialCountByUser(Map<String, Long> map) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".getTrialCountByUser", map);
	}

	@Override
	public BigDecimal getTrialReturnByOrder(Map<String, String> map) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".getTrialReturnByOrder", map);
	}

	@Override
	public Integer getSuborderitemListByProductId(Map<String, Object> map) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".getSuborderitemListByProductId", map);
	}

	@Override
	public Integer getCountByPageKeyAndProduct(Map map) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".getCountByPageKeyAndProduct", map);
	}

	@Override
	public List<Suborderitem> findByOrderId(String orderId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findByOrderId", orderId);
	}

	@Override
	public Suborderitem findLastOne(Long skuId, Long userId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("skuId", skuId);
		map.put("userId", userId);
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findLastOne", map);
	}
	
}
