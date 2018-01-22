/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.user.query.SuborderitemQuery;
import com.wode.factory.user.vo.SubOrderItemVo2;

import cn.org.rapid_framework.page.Page;

public interface SuborderitemDao extends  EntityDao<Suborderitem,Long>{
	public Page findPage(SuborderitemQuery query);
	public void saveOrUpdate(Suborderitem entity);
	public List<Suborderitem> findBySubOrderId(String subOrderId);

	public PageInfo<SubOrderItemVo2> findForComment(SubOrderItemVo2 vo, int pageNumber,int pageSize);
	
	public Integer getCountByUserAndProduct(Map<String,Long> map);
	public Integer getTrialCountByUser(Map<String,Long> map);

	public BigDecimal getTrialReturnByOrder(Map<String,String> map);
	
	public Integer getSuborderitemListByProductId(Map<String, Object> map);
	public Integer getCountByPageKeyAndProduct(Map map);
	public List<Suborderitem> findByOrderId(String orderId);
	
	public Suborderitem findLastOne(Long skuId,Long userId);
}
