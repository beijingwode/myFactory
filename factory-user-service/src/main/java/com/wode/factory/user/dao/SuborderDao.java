/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.vo.OrderTypeCountVO;
import com.wode.factory.user.vo.SubOrderVo;

public interface SuborderDao extends  EntityDao<Suborder,String>{
	public void saveOrUpdate(Suborder entity);
	public PageInfo findPage(SuborderQuery query);
	/**
     * 查询退款/售后状态下的订单列表
     * @param query
     * @author 刘聪
     * @since 2015-07-09
     */
	public PageInfo findRefundOrderByUserId(SuborderQuery query);
	/**
     * 查询不同状态下的订单个数
     * @param query
     * @author 刘聪
     * @since 2015-06-19
     */
	public int findOrderByUserIdCount(SuborderQuery query);

    /**
     * 查询不同状态下的订单个数
     * @param userId
     * @param status
     * @param statusFlag 评论状态
     * @author 刘聪
     * @since 2015-06-19
     */
    List<OrderTypeCountVO> getOrderCountByUserId(SuborderQuery query);
	/**
     * 查询订单项目列表
     *
     * @param userId
     * @param status
     * @param page
     * @author 刘聪
     * @since 2015-06-23
     */
    public List<Suborderitem> getSubOrderItemById(String subOrderId);
	
	/**
	 * 查询订单详情
	 * @param query
	 * @return
	 */
	public SubOrderVo findOrderDetailById(SuborderQuery query);
	/**
	 * 查询子订单
	 * @param query
	 * @return
	 */
	public Suborder querySubOrder(SuborderQuery query);
	/**
	 * 根据母单号查询子单
	 * @param orderId
	 * @return
	 */
	public List<Suborder> findByOrderId(long orderId);

	/**
	 * 查询可以退货的订单
	 * @return
	 */
	public PageInfo findReturnableOrders(SuborderQuery query);
	
	public List<SubOrderVo> findSubOrdersByOrderId(SuborderQuery query);
}
