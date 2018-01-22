/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.factory.company.query.ExchangeProductVo;
import com.wode.factory.model.Body;
import com.wode.factory.model.CompInfo;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.model.LogisticsInfo;
import com.wode.factory.model.Response;
import com.wode.factory.model.Suborder;
import com.wode.factory.supplier.dao.SuborderDao;
import com.wode.factory.supplier.query.OrderTypeCountVO;
import com.wode.factory.supplier.query.SuborderQuery;
import com.wode.factory.supplier.service.SuborderService;

@Service("suborderService")
public class SuborderServiceImpl extends BaseService<Suborder,java.lang.String> implements  SuborderService{
	
	@Autowired
	@Qualifier("suborderDao")
	private SuborderDao suborderDao;
	
	public EntityDao getEntityDao() {
		return this.suborderDao;
	}
	
	public Page findPage(SuborderQuery query) {
		return suborderDao.findPage(query);
	}
	
	/**
	 * 分页总条数
	 * @param map
	 * @return
	 */
	public Integer findSuborderlistPageCount(Map map){
		return this.suborderDao.findSuborderlistPageCount(map);
	}
	
	/**
	 * 订单（子单）list
	 * @param map
	 * @return
	 */
	public List<Suborder> findSuborderlistPage(Map map){
		return this.suborderDao.findSuborderlistPage(map);
	}
	
	
	/**
	 * 分页总条数
	 * @param map
	 * @return
	 */
	public Integer findSelllistPageCount(Map map){
		return this.suborderDao.findSelllistPageCount(map);
	}
	
	/**
	 * 订单（子单）list
	 * @param map
	 * @return
	 */
	public List<Suborder> findSelllistPage(Map map){
		return this.suborderDao.findSelllistPage(map);
	}

	@Override
	public Integer findCountByMap(Map<String, Object> map) {
		return this.suborderDao.findCountByMap(map);
	}
	
	
	/**
	 * 订单（子单）list
	 * @param map
	 * @return
	 */
	public Suborder findOrderSum(Map map){
		return this.suborderDao.findOrderSum(map);
	}

	/**
	 * 销售情况统计（日销售或月销售统计）
	 * @param map
	 * @return
	 */
	public Suborder findDayOrMonthOrdersStatistics(Map<String, Object> map) {
		// 仅退款
		Suborder justRefund = null;//suborderDao.findJustRefundOrderStatistics(map);
		// 退货退款
		Suborder returnAndRefund = null;//suborderDao.findReturnAndRefundOrderStatistics(map);
		// 已收货修改为已付款
		Suborder received= suborderDao.findReceivedOrderStatistics(map);
		
		// 初始化返回统计结果
		Suborder result = new Suborder();
		result.setSubOrderCount(0);
		result.setSumPrice(new BigDecimal(0));
		
		// 当存在仅退款时
		if(justRefund != null) {
			result.setSubOrderCount(result.getSubOrderCount() + (justRefund.getSubOrderCount() == null?0:justRefund.getSubOrderCount()));
			result.setSumPrice(result.getSumPrice().add(justRefund.getSumPrice() == null?new BigDecimal(0):justRefund.getSumPrice()));
		}
		
		// 当存在退货退款时
		if(returnAndRefund != null) {
			result.setSubOrderCount(result.getSubOrderCount() + (returnAndRefund.getSubOrderCount() == null?0:returnAndRefund.getSubOrderCount()));
			result.setSumPrice(result.getSumPrice().add(returnAndRefund.getSumPrice() == null?new BigDecimal(0):returnAndRefund.getSumPrice()));
		}
		
		// 当存在已收货时
		if(received != null) {
			result.setSubOrderCount(result.getSubOrderCount() + (received.getSubOrderCount() == null?0:received.getSubOrderCount()));
			result.setSumPrice(result.getSumPrice().add(received.getSumPrice() == null?new BigDecimal(0):received.getSumPrice()));
		}
		// 返回统计结果
		return result;
	}

	@Override
	public List<OrderTypeCountVO> getOrderCount(Long supplierId, Integer status, Integer commentStatus) {

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("supplierId", supplierId);
		map.put("status", status);
		map.put("commentStatus", commentStatus);
		return suborderDao.getOrderCount(map);
	}

	@Override
	public void updateStockUp(String suborderId, Integer stockUp) {
		suborderDao.updateStockUp(suborderId, stockUp);
	}

	@Override
	public Suborder getSuborderByRefundOrderId(Long refundOrderId) {
		return suborderDao.getSuborderByRefundOrderId(refundOrderId);
	}

	@Override
	public Integer findExchangeSuNumByMap(Map<String, Object> map) {
		return suborderDao.findExchangeSuNumByMap(map);
	}

	@Override
	public List<ExchangeProductVo> findSubOrderItemByMap(Map<String, Object> map) {
		return suborderDao.findSubOrderItemByMap(map);
	}

	@Override
	public Integer findOrderTotalByMap(Map<String, Object> map) {
		return suborderDao.findOrderTotalByMap(map);
	}
	
}
