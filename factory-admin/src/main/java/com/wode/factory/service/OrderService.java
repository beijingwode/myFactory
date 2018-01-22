package com.wode.factory.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.util.ActResult;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.vo.SuborderOrderVo;

/**
 * Created by zoln on 2015/7/24.
 */
public interface OrderService {

	/**
	 * 获取订单详情, 包含订单项
	 * @param id
	 * @return
	 */
	Orders getOrderDetailWithItems(Long id);
	
	/**
	 * 获取订单
	 * @param id
	 * @return
	 */
	Orders findById(Long id);

	void update(Orders order);
	void delete(Long id);
	
	public ActResult createOrderXls(Map<String, Object> map);

	List<Orders> findByUserId(Long userId);
	List<Orders> findOwnOrders(Long supplierId,Long productId);
	/**
	 * 
	 * 功能说明：查询商品列表（带分页）
	 * @param params
	 * @return
	 */
	PageInfo<SuborderOrderVo> getSuborderList(Map<String, Object> params);
	public BigDecimal getSumCashPayByProduct(Long productId,Date firstDate);
	public Long getNoClearOrderByProduct(Long productId,Date startDate,Date endDate);

	void save(Orders orders, Suborder suborder, List<Suborderitem> suborderitems);
}
