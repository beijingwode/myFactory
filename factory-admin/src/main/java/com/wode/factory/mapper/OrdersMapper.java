package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.Orders;
import com.wode.factory.vo.SuborderOrderVo;

/**
 * Created by zoln on 2015/7/24.
 */
public interface OrdersMapper {

	List<Orders> findOrdersBySelective(Map<String, Object> map);


	List<Orders> findOwnOrders(Map<String, Object> map);
	List<Orders> findByUserId(Long userId);
	
	Orders getOrderByIdWithItems(Long orderId);
	
	Orders getById(Long orderId);

	int update(Orders order);
	void delete(Long id);

	String[] getHeaders();

	/**
	 * 
	 * 功能说明：查询属性列表
	 *
	 * @param params
	 * @return
	 */
	public List<SuborderOrderVo> getSuborderList(Map<String, Object> params);
	
	int insert(Orders order);
}
