
package com.wode.factory.supplier.open.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.model.OpenRequestBase;
import com.wode.factory.model.OpenResponse;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.Returnorder;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.enums.RefundGoodsStatus;
import com.wode.factory.model.enums.RefundOrderStatus;
import com.wode.factory.model.enums.RefundRefundType;
import com.wode.factory.model.open.RefundConfirm;
import com.wode.factory.model.open.RefundDeatailGet;
import com.wode.factory.model.open.RefundGet;
import com.wode.factory.model.open.RefundOrders;
import com.wode.factory.supplier.facade.OrderRefundFacade;
import com.wode.factory.supplier.service.ProductSpecificationsService;
import com.wode.factory.supplier.service.RefundorderService;
import com.wode.factory.supplier.service.ReturnorderService;
import com.wode.factory.supplier.service.SuborderService;
import com.wode.factory.supplier.service.SuborderitemService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.util.ExpressUtils;
/**
 * 异常订单处理类
 * @author user
 *
 */
@Controller
@RequestMapping("open/supplier")
public class OpenReturnOrderController extends BaseController {
	
	/**
	 * 物流
	 */
	@Autowired
	@Qualifier("expressComService")
	private ExpressUtils expressComService;

	@Autowired
	@Qualifier("returnorderService")
	private ReturnorderService returnorderService;

	@Autowired
	private RefundorderService refundorderService;
	@Autowired
	@Qualifier("orderRefundFacade")
	private OrderRefundFacade orderRefundFacade;

	@Autowired
	@Qualifier("suborderService")
	private SuborderService suborderService;
	
	@Autowired
	private SuborderitemService suborderitemService;

	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	
	/**
	 * sku
	 */
	@Autowired
	@Qualifier("productSpecificationsService")
	private ProductSpecificationsService productSpecificationsService;

	/**
	 * 退款
	 * @param request
	 * @param openRequestBase
	 * @param refundOrderGet
	 * @return
	 */
	@RequestMapping(value = "refund/confirm")
	@ResponseBody
	public Object refund(HttpServletRequest request, OpenRequestBase openRequestBase, RefundConfirm refundOrderGet) {

		Refundorder refundorder = refundorderService.getById(refundOrderGet.getRefund_id());
		
		Suborder suborder = suborderService.getSuborderByRefundOrderId(refundorder.getRefundOrderId());
			
		if (suborder.getSupplierId().compareTo(Long.parseLong(refundOrderGet.getSupplierId())) != 0
				|| null == suborder.getRefundOrderId()) {
			return OpenResponse.fail("没有权限操作");
		}
		// 查询商家信息
		Supplier supplier = supplierService.getById(suborder.getSupplierId());
		// 退款单
		// 退货单
		Returnorder returnorder = null;

		// 调用退款接口
		// 暂时忽视退款 申请中状态， 2015-11-26 gaoyj
		if (StringUtils.isEmpty(refundorder) || refundorder.getStatus() != 1) {
			return OpenResponse.fail("订单状态不允许退款");
		}
		if (!StringUtils.isEmpty(suborder.getReturnOrderId())) {
			returnorder = returnorderService.getById(suborder.getReturnOrderId());
			if (StringUtils.isEmpty(returnorder) || returnorder.getStatus() != 0) {
				return OpenResponse.fail("退款单状态不允许退款");
			}
		}
		Long customerId = refundorder.getUserId() != null ? refundorder.getUserId() : returnorder.getUserId();
		ActResult<String> ar;
		try {
			ar = orderRefundFacade.refundToUser(refundorder, returnorder, suborder, customerId, supplier.getComName(),
					null);
		} catch (Exception e) {
			return OpenResponse.fail("系统错误");
		}
		if (!ar.isSuccess()) {
			return OpenResponse.fail(ar.getData());
		}
		return OpenResponse.success("");
	}
	
	/**
	 * 收到退货
	 * @param request
	 * @param openRequestBase
	 * @param refundOrderGet
	 * @return
	 */
	@RequestMapping(value = "refund/goods/sign")
	@ResponseBody
	public Object goodsSign(HttpServletRequest request, OpenRequestBase openRequestBase, RefundConfirm refundOrderGet) {

		Refundorder refundorder = refundorderService.getById(refundOrderGet.getRefund_id());
		
		
		Suborder suborder = suborderService.getSuborderByRefundOrderId(refundorder.getRefundOrderId());
			
		if (suborder.getSupplierId().compareTo(Long.parseLong(refundOrderGet.getSupplierId())) != 0
				|| null == suborder.getRefundOrderId()) {
			return OpenResponse.fail("没有权限操作");
		}
		//这里要改退货单 的状态而不是 退款单
		Returnorder returnorder = returnorderService.getById(refundorder.getReturnOrderId());
		
		returnorder.setGoodsStatus(RefundGoodsStatus.SELLER_SIGNED_GOODS.getValue());
		returnorderService.update(returnorder);
		return OpenResponse.success("");
	}

	@RequestMapping(value = "refunds/get")
	@ResponseBody
	public Object refundsGet(HttpServletRequest request, OpenRequestBase openRequestBase, RefundGet refundGet) {
		String startCreated=refundGet.getStart_created();
		String endCreated=refundGet.getEnd_created();
		//时间处理
		if(StringUtils.isEmpty(startCreated)){
			startCreated = TimeUtil.dateToStr19(TimeUtil.addMonth(new Date(), -3));
		}else{//如果时间不为空则进行区间判断判断
			if(TimeUtil.strToDate(startCreated).before(TimeUtil.addMonth(new Date(), -3))){
				startCreated = TimeUtil.dateToStr19(TimeUtil.addMonth(new Date(), -3));
			}
			if(TimeUtil.strToDate(startCreated).after(new Date())){
				startCreated = TimeUtil.dateToStr19(new Date());
			}
		}
        if(StringUtils.isEmpty(endCreated)){
        	endCreated = TimeUtil.dateToStr19(new Date());
		}else{
			if(TimeUtil.strToDate(endCreated).after(new Date())){
				endCreated = TimeUtil.dateToStr19(new Date());
			}
			if(TimeUtil.strToDate(endCreated).before(TimeUtil.addMonth(new Date(), -3))){
				endCreated = TimeUtil.dateToStr19(TimeUtil.addMonth(new Date(), -3));
			}
		}
        String status=refundGet.getStatus();
//		orderListGet.statusMap.get("");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", openRequestBase.getSupplierId());
		map.put("starttime", startCreated);
		map.put("endtime", endCreated);
		if(status==null || status.equals("")){
			map.put("status", RefundOrderStatus.WAIT_SELLER_CONFIRM.getValue());
		}else{
			map.put("status", RefundOrderStatus.getValue(status));
		}
		if(!StringUtils.isEmpty(refundGet.getGoods_status())){
			map.put("goods_status", RefundGoodsStatus.getValue(refundGet.getGoods_status()));
		}
		if(!StringUtils.isEmpty(refundGet.getRefund_type())){
			map.put("refund_type", RefundRefundType.getValue(refundGet.getRefund_type()));
		}
		List<RefundOrders> list = refundorderService.getRefundorWithSuborder(map);
		if(null != list && list.size() > 0){
			for (RefundOrders refundOrders : list) {
				refundOrders.setStatus(RefundOrderStatus.getName(Integer.parseInt(refundOrders.getStatus())));
				if(null != refundOrders.getReturnOrderid()){
					//这里用退货退款表里面的订单物流状态
					Returnorder returnorder = returnorderService.getById(refundOrders.getReturnOrderid());
					if(null != returnorder.getGoodsStatus()){
						refundOrders.setGoods_status(RefundGoodsStatus.getName(returnorder.getGoodsStatus()));
					}
				}
				refundOrders.setRefund_type(RefundRefundType.getName(Integer.parseInt(refundOrders.getRefund_type())));
			}
		}
		return OpenResponse.success(list);
	}
	
	
	
	@RequestMapping(value = "refund/get")
	@ResponseBody
	public Object refundDeatailGet(HttpServletRequest request, OpenRequestBase openRequestBase, RefundDeatailGet refundGet) {
		Suborder  suborder = null;
		if(!StringUtils.isEmpty(refundGet.getTrade_id())){
			suborder = suborderService.getById(refundGet.getTrade_id());
		}
		if(!StringUtils.isEmpty(refundGet.getRefund_id())){
			suborder = suborderService.getSuborderByRefundOrderId(Long.valueOf(refundGet.getRefund_id()));
		}
		if(null == suborder){
			return OpenResponse.fail("没有找到对应售后单");
		}else if(suborder.getSupplierId().compareTo(Long.parseLong(openRequestBase.getSupplierId())) != 0){
			return OpenResponse.fail("没有找到对应售后单");
		}
        Refundorder refundorder = refundorderService.getById(suborder.getRefundOrderId());
        if(null == refundorder){
        	return OpenResponse.fail("没有找到对应售后单");
        }
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("refund_id",suborder.getRefundOrderId());//售后单id;
        result.put("trade_id",suborder.getSubOrderId());//订单id; 
        result.put("status",RefundOrderStatus.getName(refundorder.getStatus()));//售后状态;
        if(StringUtils.isEmpty(suborder.getReturnOrderId())){
        	result.put("refund_type",RefundRefundType.REFUDN_FEE_ONLY.getName());//售后类型;
        }else{
        	result.put("refund_type",RefundRefundType.RETURN_OF_GOODS.getName());//售后类型;
        }
        if(null != refundorder.getGoodsStatus()){
        	result.put("goods_status",RefundGoodsStatus.getName(refundorder.getGoodsStatus()));//退货物流状态 （退货退款时适用）;
        }
        result.put("created",refundorder.getCreateTime());//创建时间;
        result.put("payment",suborder.getRealPrice());//订单金额;
        result.put("shipping_fee",suborder.getTotalShipping());//订单运费;
        result.put("refund_fee",refundorder.getRefundPrice());//退款金额;
        result.put("reason",refundorder.getReason());//退款原因;
        result.put("note",refundorder.getNote());//退款说明;
        
        if(!StringUtils.isEmpty(suborder.getReturnOrderId())){
        	Returnorder returnOrder = returnorderService.getById(suborder.getReturnOrderId());
        	if(null != returnOrder.getExpressType()){
        		ExpressCompany expressCompany = expressComService.getExpressCompanys().get(returnOrder.getExpressType());
        		result.put("express_com",null != expressCompany ? expressCompany.getName():"");
        	}
        	result.put("express_no",returnOrder.getExpressNo());//快递单号    （退货退款时适用）
        	
        	List<Suborderitem> subOrderItems = suborderitemService.selectSuborderItemByrenturnOrderId(suborder.getReturnOrderId());
        	if(null != subOrderItems){
        		List<Map<String,Object>> skuList = new ArrayList<Map<String,Object>>();
        		for (Suborderitem suborderitem : subOrderItems) {
    				Map<String,Object> skus = new HashMap<String, Object>();
    				skus.put("sku_id", suborderitem.getPartNumber());
    				skus.put("p_title", suborderitem.getProductName());
    				skus.put("spec", OpenProductController.dealItemValues(suborderitem.getItemValues()));
    				skus.put("sku_code", suborderitem.getProductCode());
    				skus.put("num", suborderitem.getNumber());
    				skus.put("price", suborderitem.getRealPay());
    				skus.put("image", suborderitem.getImage());
    				ProductSpecifications sku =  productSpecificationsService.getById(new Long(suborderitem.getPartNumber()));
    				if(null != sku){
    					skus.put("outer_id", sku.getOuterId());
    				}
    				skuList.add(skus);
    			}
    			result.put("skus", skuList);
        	}
        }
        Map<String,Object> quaryAttachment = new HashMap<String,Object>();
        quaryAttachment.put("refundOrderId", suborder.getRefundOrderId());
        List<Map> attachmentList = refundorderService.selectAttachmentsByRefundOrderId(quaryAttachment);
        if(null !=attachmentList){
        	for (int i = 1; i <= attachmentList.size(); i++) {
        		if(i > 3){
        			break;
        		}//只放3张
        		result.put("attachment" + i,attachmentList.get(i-1).get("image"));
			}
        }

		return OpenResponse.success(result);
	}
	
}
