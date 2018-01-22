package com.wode.factory.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.wode.factory.model.Suborder;
import com.wode.factory.vo.SuborderOrderVo;
import com.wode.tongji.vo.SubOrdersVo;


public interface SuborderDao {

	/**
	 * 
	 * 功能说明：根据日查询
	 * 日期:	2015年5月14日
	 * 开发者:宋艳垒
	 *
	 * @param month
	 * @return
	 */
	List<SubOrdersVo> selectByDay(String day);
	
	/**
	 * 功能说明：根据订单ID查询所有的子单
	 * @param orderId
	 * @return
	 */
	List<Suborder> findByOrderId(Long orderId);
	List<Suborder> findByUserId(Long userId);
	
	/**
	 * 功能说明：更新子单
	 * @param so
	 */
	void update(Suborder so);
	void delete(String id);
	/**
	 * 功能说明：更新子单
	 * @param so
	 */
	void updateToClose(Map<String,Object> map);
	/**
	 * 功能说明：更新子单
	 * @param so
	 */
	void updateToStockUp(Map<String,Object> map);
	
	/**
	 * 根据创建时间与当前订单状态查询List
	 * @param date 
	 * @param status 
	 * @return 
	 */
	List<Suborder> findByStatusAndCreate(Map<String, Object> map);
	List<Suborder> findSelfDeliveryPayTime(Map<String, Object> map);
	
	List<Suborder> findByStatusAndPayTime(Map<String, Object> map);
	
	Suborder getById(String subOrderId);

	List<Suborder> findForCommission(Map<String, Object> map);
	

	BigDecimal getSumCashPayByProduct(Map<String, Object> map);
	Long getNoClearOrderByProduct(Map<String, Object> map);

	List<Suborder> getsuborderIdByOrderId(Long orderId);

	List<Suborder> findByProductId(Long productId);

	void insert(Suborder suborder);

	List<Suborder> getMsgBySupplierId(Long supplierId);

	List<Suborder> findByRelationId(Long groupId);

	List<SuborderOrderVo> findNoComment(Map<String, Object> map);
}