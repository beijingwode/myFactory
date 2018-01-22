/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.GroupSuborderitem;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.user.query.SuborderitemQuery;
import com.wode.factory.user.vo.SubOrderItemVo2;

import cn.org.rapid_framework.page.Page;

@Service("suborderitemService")
public interface SuborderitemService extends EntityService<Suborderitem,Long>{
	
	
	public Page findPage(SuborderitemQuery query);
	
	public List<Suborderitem> findBySubOrderId(String subOrderId);
	
	public PageInfo<SubOrderItemVo2> findForComment(SubOrderItemVo2 vo,int pageNumber,int pageSize);
	

	public Integer getCountByUserAndProduct(Long productId,Long userId);
	public Integer getTrialCountByUser(Long userId);
	public BigDecimal getTrialReturnByOrder(String subOrderId);

	public List<GroupSuborderitem> findByGroupSuborederId(String subOrderId);

	public Integer getSuborderitemListByProductId(Map<String, Object> map);

	public Integer getCountByPageKeyAndProduct(Long productId, String pageKey);

	public List<Suborderitem> findByOrderId(String orderId);
	
	public Suborderitem findLastOne(Long skuId,Long userId);
}
