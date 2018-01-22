/**
 * 
 */
package com.wode.factory.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wode.factory.model.Suborder;
import com.wode.factory.outside.service.SmsService;
import com.wode.factory.vo.SuborderOrderVo;
import com.wode.tongji.model.SmsTemplate;


/**
 * 
 * <pre>
 * 功能说明: 子单service
 * 日期:	2015年3月9日
 * 开发者:	宋艳垒
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 2015年3月9日
 * </pre>
 */
public interface SubOrderService  {
	
	/**
	 * 修改订单状态
	 * @param oldStatus 旧状态
	 * @param newStatus	新状态
	 * @param day	时间点
	 * @return
	 */
	int updateOrderSataus(Integer status,int newStatus,int day);
	
	/**
	 * 佣金计算，按照每月已确认收货的子单数据进行计算
	 */
//	int commission();
	

	/**
	 * 功能说明：更新子单
	 * @param so
	 */
	void updateToClose(String subOrderId,Long saleBillId);

	/**
	 * 功能说明：更新子单
	 * @param so
	 */
	void updateToStockUp(String subOrderId,Integer stockUp);
	
	Suborder getById(String subOrderId);

	/**
	 * 功能说明：更新子单
	 * @param so
	 */
	void update(Suborder so);
	void delete(String id);

	List<Suborder> getsuborderIdByOrderId(Long orderId);
	List<Suborder> findByStatusAndPayTime(Integer status,Integer stockUp,Date payTime);

	List<Suborder> findByProductId(Long productId);

	List<Suborder> findByRelationId(Long groupId);
	
	/**
	 * 订单支付成功后 向商家运营推送消息，如自动发货电子卡券，则自动发货
	 * @param suborderId
	 */
	void pushOrderPayAndECard(String subOrderId,SmsTemplate t,SmsService sms,String outId);

	/**
	 * 发货催促
	 * @param subOrderId
	 */
	void urgedDelivery(String subOrderId);

	List<SuborderOrderVo> findNoComment(Map<String, Object> map);
}
