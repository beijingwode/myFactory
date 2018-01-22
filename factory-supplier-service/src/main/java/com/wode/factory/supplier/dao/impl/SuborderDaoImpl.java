/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.company.query.ExchangeProductVo;
import com.wode.factory.model.Suborder;
import com.wode.factory.supplier.dao.SuborderDao;
import com.wode.factory.supplier.query.OrderTypeCountVO;
import com.wode.factory.supplier.query.SuborderQuery;

@Repository("suborderDao")
public class SuborderDaoImpl extends BaseDao<Suborder,java.lang.String> implements SuborderDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SuborderMapper";
	}
	
	public void saveOrUpdate(Suborder entity){
		if(entity.getSubOrderId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(SuborderQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	
	/**
	 * 分页总条数
	 * @param map
	 * @return
	 */
	public Integer findSuborderlistPageCount(Map map){
		Number num = this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".findSuborderlistPage_count",map);
		return num.intValue();
	}
	
	/**
	 * 订单（子单）list
	 * @param map
	 * @return
	 */
	public List<Suborder> findSuborderlistPage(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findSuborderlistPage",map);
	}
	
	
	/**
	 * 分页总条数
	 * @param map
	 * @return
	 */
	public Integer findSelllistPageCount(Map map){
		Number num = this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".findSelllistPage_count",map);
		return num.intValue();
	}
	
	/**
	 * 订单（子单）list
	 * @param map
	 * @return
	 */
	public List<Suborder> findSelllistPage(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findSelllistPage",map);
	}

	@Override
	public Integer findCountByMap(Map<String, Object> map) {
		Number num = this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".findCountByMap",map);
		return num.intValue();
	}

	
	/**
	 * 订单（子单）list
	 * @param map
	 * @return
	 */
	public Suborder findOrderSum(Map map){
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".findOrderSum",map);
	}
	// 统计日/月销售额  1./*根据商家ID获取日销售或是月销售，/*仅退款*/的订单的单数、/*仅退款*/后剩余的总的销售额*/
	public Suborder findJustRefundOrderStatistics(Map<String, Object> map) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".findJustRefundOrderStatistics",map);
	}
	// 统计日/月销售额  2.根据商家ID获取日销售或是月销售，/*退货退款*/的订单的单数、/*退货退款*/后剩余的总的销售额
	public Suborder findReturnAndRefundOrderStatistics(Map<String, Object> map) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".findReturnAndRefundOrderStatistics",map);
	}
	// 统计日/月销售额  3./*根据商家ID获取日销售或是月销售，/*已收货*/的订单的单数、/*已收货*/的销售额*/
	public Suborder findReceivedOrderStatistics(Map<String, Object> map) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".findReceivedOrderStatistics",map);
	}

	@Override
	public List<OrderTypeCountVO> getOrderCount(Map map) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getOrderCount",map);
	}

	@Override
	public void updateCashNo(String suborderId, String tradeNo) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("suborderId", suborderId);
		param.put("tradeNo", tradeNo);
		getSqlSession().update(getIbatisMapperNamesapce()+".updateCashNo", param);
	}

	@Override
	public void updateStockUp(String suborderId, Integer stockUp) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("suborderId", suborderId);
		param.put("stockUp", stockUp);
		getSqlSession().update(getIbatisMapperNamesapce()+".updateStockUp", param);
	}

	@Override
	public Suborder getSuborderByRefundOrderId(Long refundOrderId) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getSuborderByRefundOrderId",refundOrderId);
	}

	@Override
	public Integer findExchangeSuNumByMap(Map<String, Object> map) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".findExchangeSuNumByMap",map);
	}

	@Override
	public List<ExchangeProductVo> findSubOrderItemByMap(Map<String, Object> map) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findSubOrderItemByMap",map);
	}

	@Override
	public Integer findOrderTotalByMap(Map<String, Object> map) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".findOrderTotalByMap",map);
	}
}
