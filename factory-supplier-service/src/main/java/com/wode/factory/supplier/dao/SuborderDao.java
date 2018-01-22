/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.company.query.ExchangeProductVo;
import com.wode.factory.model.Suborder;
import com.wode.factory.supplier.query.OrderTypeCountVO;
import com.wode.factory.supplier.query.SuborderQuery;

import cn.org.rapid_framework.page.Page;

public interface SuborderDao extends  EntityDao<Suborder,String>{
	public Page findPage(SuborderQuery query);
	public void saveOrUpdate(Suborder entity);

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
	 * 分页总条数
	 * @param map
	 * @return
	 */
	public Integer findSelllistPageCount(Map map);
	
	/**
	 * 订单（子单）list
	 * @param map
	 * @return
	 */
	public List<Suborder> findSelllistPage(Map map);
	public Integer findCountByMap(Map<String, Object> map);
	
	/**
	 * 订单（子单）list
	 * @param map
	 * @return
	 */
	public Suborder findOrderSum(Map map);
    /**
     * 查询不同状态下的订单个数
     * @param userId
     * @param status
     * @param statusFlag 评论状态
     * @author 刘聪
     * @since 2015-06-19
     */
    List<OrderTypeCountVO> getOrderCount(Map map);

	// 统计日/月销售额  1./*根据商家ID获取日销售或是月销售，/*仅退款*/的订单的单数、/*仅退款*/后剩余的总的销售额*/
	public Suborder findJustRefundOrderStatistics(Map<String, Object> map);
	// 统计日/月销售额  2.根据商家ID获取日销售或是月销售，/*退货退款*/的订单的单数、/*退货退款*/后剩余的总的销售额
	public Suborder findReturnAndRefundOrderStatistics(Map<String, Object> map);
	// 统计日/月销售额  3./*根据商家ID获取日销售或是月销售，/*已收货*/的订单的单数、/*已收货*/的销售额*/
	public Suborder findReceivedOrderStatistics(Map<String, Object> map);
	
	public void updateCashNo(String suborderId,String tradeNo );
	public void updateStockUp(String suborderId, Integer stockUp);
	public Suborder getSuborderByRefundOrderId(Long refundOrderId);
	public Integer findExchangeSuNumByMap(Map<String, Object> map);
	public List<ExchangeProductVo> findSubOrderItemByMap(Map<String, Object> map);
	public Integer findOrderTotalByMap(Map<String, Object> map);
}
