/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.common.util.ActResult;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.vo.OrderTypeCountVO;
import com.wode.factory.user.vo.SubOrderVo;

public interface SuborderService extends EntityService<Suborder, String> {

    /**
     * 根据用户ID、订单状态 获取订单信息
     *
     * @param userId
     * @param status
     * @param page
     * @return
     */
    PageInfo getOrderByUserId(Long userId,Long batchId, Integer status, Integer page);
    PageInfo getOrderByUserId(Long userId,Long batchId, Integer status, Integer page,Integer pageSize);
    
    /**
     * 查询不同状态下的订单列表
     *
     * @param userId
     * @param status
     * @param page
     * @author 刘聪
     * @since 2015-06-23
     */
    PageInfo getSubordersList(Long userId, Integer status, Integer page, Integer size);
    
//    /**
//     * 查询退款/售后状态下的订单列表
//     *
//     * @param userId
//     * @param page
//     * @param size
//     * @author 刘聪
//     * @since 2015-07-09
//     */
//    public PageInfo getRefundSubordersList(Long userId, Integer page, Integer size);
    
    /**
     * 查询不同状态下的订单个数
     * @param userId
     * @param status
     * @param statusFlag 评论状态
     * @author 刘聪
     * @since 2015-06-19
     */
    int getOrderByUserIdCount(Long userId, Integer status, Integer commentStatus);

    /**
     * 查询不同状态下的订单个数
     * @param userId
     * @param status
     * @param statusFlag 评论状态
     * @author 刘聪
     * @since 2015-06-19
     */
    List<OrderTypeCountVO> getOrderCountByUserId(Long userId, Integer status, Integer commentStatus);
    /**
     * 查询订单项目列表
     *
     * @param userId
     * @param status
     * @param page
     * @author 刘聪
     * @since 2015-06-23
     */
    List<Suborderitem> getSubOrderItemById(String subOrderId);
    
    /**
     * 查询订单详情
     *
     * @param query
     * @return
     */
    SubOrderVo findOrderDetailById(SuborderQuery query);

    /**
     * 查询子订单
     *
     * @param query
     * @return
     */
    Suborder querySubOrder(SuborderQuery query);

    /**
     * 删除订单
     * @param user
     * @param subOrderId
     * @return
     */
    ActResult delete(UserFactory user, String subOrderId);

    

    /**
     * 催促发货
     *
     * @param suborderId
     * @return
     */
    ActResult urgedDelivery(UserFactory user, String suborderId);

    /**
     * 根据母单号查询子单
     *
     * @param orderId
     * @return
     */
    List<Suborder> findByOrderId(long orderId);

    /**
     * 查询可退货的订单
     *
     * @param page
     * @param userId
     * @return
     */
    PageInfo findReturnableOrders(Integer page, Long userId);
    
    /**
     * 根据母单号查找子单
     * @param query
     * @return
     */
    List<SubOrderVo> findSubOrdersByOrderId(Long userId, Long orderId);
    /**
     * 根据子单号查询子单
     * @param subOrderId
     * @return
     */
    GroupSuborder findGroupSuborderObjbyId(String subOrderId);
    /**
     * 根据母单号查找子单
     * @param orderId
     * @return
     */
    List<GroupSuborder> findGroupSuborderbyId(Long orderId);

	void updateGroupSbuorder(GroupSuborder groupSuborder);

    /**
     * 
     * 功能说明：查询物流信息
     * 日期:	2015年9月25日
     * 开发者:宋艳垒
     *
     * @param expressType
     * @param expressNo
     * @param id
     * @param type 1:pc,2:app
     * @return Map
     */
//	public Map getTogistics(String expressType, String expressNo, Long id,
//			int type);
}
