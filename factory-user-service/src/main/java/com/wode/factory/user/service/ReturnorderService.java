/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.common.util.ActResult;
import com.wode.factory.model.Returnorder;
import com.wode.factory.user.query.ReturnorderQuery;

@Service("returnorderService")
public interface ReturnorderService extends EntityService<Returnorder, Long> {


    public PageInfo findPage(ReturnorderQuery query);

    public boolean createReturnOrder(Returnorder returnOrder,Long subOrderItemId, String[] images);

    public PageInfo getReturnOrders(Integer page, Long userId, Integer status);
    // 退货退款/仅退款申请
    public Map<String, Object> applyReturn(Integer type, Returnorder returnOrder, Integer goodsStaus, List<String> imgPathList);
    // 延长收货
    public ActResult<Object> extendedReceipt(String subOrderId);
    //退货退款带有凭证
	public Returnorder getReturnOrdersById(Long returnOrderId);
	/**
	 * 修改售后申请
	 * @param type
	 * @param returnOrder
	 * @param refundOrderId
	 * @param goodsStatus
	 * @param asList
	 * @return
	 */
	public Map<String, Object> updateReturn(Integer type, Returnorder returnOrder, Long refundOrderId,
			Integer goodsStatus, List<String> asList);
}
