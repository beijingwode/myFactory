/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.company.query.ExchangeProductVo;
import com.wode.factory.model.Suborder;
import com.wode.factory.supplier.query.OrderTypeCountVO;
import com.wode.factory.supplier.query.SuborderQuery;

import cn.org.rapid_framework.page.Page;

public interface SuborderService extends EntityService<Suborder,String>{
	
	public EntityDao getEntityDao() ;
	
	public Page findPage(SuborderQuery query);
	
	/**
	 * 分页总条数
	 * @param map
	 * @return
	 */
	public Integer findSuborderlistPageCount(Map map);
	
	/**
	 * 订单（子单）list
	 * @param map
	 * @return
	 */
	public List<Suborder> findSuborderlistPage(Map map);
	
	
	/**
	 * 已售出的商品分页总条数
	 * @param map
	 * @return
	 */
	public Integer findSelllistPageCount(Map map);
	
	/**
	 * 已售出的商品订单（子单）list
	 * @param map
	 * @return
	 */
	public List<Suborder> findSelllistPage(Map map);

	/**
	 * 通过商家id和订单状态查询数量
	 * @param map
	 * @return
	 */
	public Integer findCountByMap(Map<String, Object> map);

	/**
	 * 订单（子单）list
	 * @param map
	 * @return
	 */
	public Suborder findOrderSum(Map map);
	
	/**
	 * 销售情况统计（日销售或月销售统计）
	 * @param map
	 * @return
	 */
	public Suborder findDayOrMonthOrdersStatistics(Map<String, Object> map);
	
    /**
     * 查询不同状态下的订单个数
     * @param userId
     * @param status
     * @param statusFlag 评论状态
     * @author 刘聪
     * @since 2015-06-19
     */
    List<OrderTypeCountVO> getOrderCount(Long supplierId, Integer status, Integer commentStatus);
	void updateStockUp(String suborderId, Integer stockUp);
	
	/**
	 * 通过退款订单id查询suborder
	 * @param refundOrderId
	 * @return
	 */
	public Suborder getSuborderByRefundOrderId(Long refundOrderId);

	public Integer findExchangeSuNumByMap(Map<String, Object> map);

	public List<ExchangeProductVo> findSubOrderItemByMap(Map<String, Object> map);

	public Integer findOrderTotalByMap(Map<String, Object> map);
}
