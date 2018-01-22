
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

import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.model.OpenRequestBase;
import com.wode.factory.model.OpenResponse;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.open.OrderListGet;
import com.wode.factory.model.open.SendGet;
import com.wode.factory.model.open.TradeGet;
import com.wode.factory.supplier.service.OrdersService;
import com.wode.factory.supplier.service.ProductService;
import com.wode.factory.supplier.service.ProductSpecificationsService;
import com.wode.factory.supplier.service.SuborderService;
import com.wode.factory.supplier.service.SuborderitemService;
import com.wode.factory.supplier.util.AppPushUtil;
import com.wode.factory.supplier.util.ExpressUtils;


@Controller
@RequestMapping("open/supplier")
public class OpenOrderController  extends BaseController {
	
	/**
	 * 物流
	 */
	@Autowired
	@Qualifier("expressComService")
	private ExpressUtils expressComService;
	/**
	 * 总单
	 */
	@Autowired
	@Qualifier("ordersService")
	private OrdersService ordersService;
	@Autowired
	private SuborderitemService suborderitemService;
	
	@Autowired
	@Qualifier("productService-supplier")
	private ProductService productService;
	
	/**
	 * sku
	 */
	@Autowired
	@Qualifier("productSpecificationsService")
	private ProductSpecificationsService productSpecificationsService;
	/**
	 * 订单
	 */
	@Autowired
	@Qualifier("suborderService")
	private SuborderService suborderService;
	@RequestMapping(value="trades/get")
	@ResponseBody
	public Object orderListGet(HttpServletRequest request,OpenRequestBase openRequestBase,OrderListGet orderListGet){
		String startCreated=orderListGet.getStart_created();
		String endCreated=orderListGet.getEnd_created();
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
		String status=orderListGet.getStatus();
//		orderListGet.statusMap.get("");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", openRequestBase.getSupplierId());
		map.put("starttime", startCreated);
		map.put("endtime", endCreated);
		map.put("status", OrderListGet.statusMap.get(status));
		if(status==null || status.equals("")){
			map.put("status", OrderListGet.statusMap.get("WAIT_SELLER_SEND_GOODS"));
		}
		List<Suborder> list=suborderService.findSuborderlistPage(map);
		List<Map<String, Object>> resultMap=new ArrayList<Map<String,Object>>();
		if(null!=list && list.size() > 0){
			for (Suborder suborder : list) {
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("trade_id", suborder.getSubOrderId());
				result.put("status", OrderListGet.forEachStatusMap(suborder.getStatus()));
				result.put("created", suborder.getCreateTime());
				result.put("payment", suborder.getRealPrice());
				resultMap.add(result);
			}
		}
		return OpenResponse.success(resultMap);
	}
	
	@RequestMapping("trade/get")
	@ResponseBody
	public Object tradeGet(TradeGet tradeGet){
		String tradeId = tradeGet.getTrade_id();
		String supplierId = tradeGet.getSupplierId();
		if(StringUtils.isEmpty(tradeId)){
			return OpenResponse.fail("订单id不能为空");
		}
		// 获取订单信息
		Suborder suboer = suborderService.getById(tradeId);
		Map<String,Object> result = new HashMap<String, Object>();
		//查询订单不为空 并且商家id等于传过来的商家id
		if(null != suboer && suboer.getSupplierId().compareTo(new Long(supplierId)) == 0){

			result.put("trade_id", suboer.getSubOrderId());
			result.put("status",  OrderListGet.forEachStatusMap(suboer.getStatus()));
			result.put("created", suboer.getCreateTimeString());
			result.put("payment", suboer.getRealPrice());
			result.put("shipping_fee",suboer.getTotalShipping());
			Orders order = ordersService.getById(suboer.getOrderId());
			result.put("buyer_id",order.getUserId());
			result.put("buyer_name",order.getName());
			result.put("buyer_phone",order.getMobile());
			result.put("buyer_address",order.getAddress());
			//不是自提
			if("0".equals(order.getSelfDelivery())){
				String[] address = order.getAddress().split("\\s");
				result.put("buyer_address_province",address[0]);
				result.put("buyer_address_city",address[1]);
				result.put("buyer_address_area",address[2]);
				result.put("buyer_address_detail",address[3]);
			}
			result.put("buyer_noto",order.getNote());
			result.put("buyer_ invoice",order.getInvoiceTitle());
			ExpressCompany expressCompany = expressComService.getExpressCompanys().get(suboer.getExpressType());
			result.put("express_com",null != expressCompany ? expressCompany.getName():"");
			result.put("express_no",suboer.getExpressNo());
			List<Map<String,Object>> skuList = new ArrayList<Map<String,Object>>();
			Suborderitem suborderitemquery = new Suborderitem();
			suborderitemquery.setSubOrderId(suboer.getSubOrderId());
			List<Suborderitem> suborderitemList = suborderitemService.selectByModel(suborderitemquery);
			
			for (Suborderitem suborderitem : suborderitemList) {
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
		return OpenResponse.success(result);
	}
	/**
	 * 发货
	 */
	@RequestMapping(value="trade/express/send")
	@ResponseBody
	public Object sendOutGet(HttpServletRequest request,OpenRequestBase openRequestBase,SendGet sendGet){
		String suborderId=sendGet.getTrade_id();
		String expressCom=sendGet.getExpress_com();
		String expressNo=sendGet.getExpress_no();
		String supplierId = sendGet.getSupplierId();
		if(StringUtils.isEmpty(suborderId)||StringUtils.isEmpty(expressCom)||StringUtils.isEmpty(expressNo)){
			return OpenResponse.fail("缺少信息,请填写完整信息!");
		}
		Suborder suborder =suborderService.getById(suborderId);
		if(suborder == null){
			return OpenResponse.fail("订单信息错误,请联系客服!");
		}else{
			if(supplierId.equals(suborder.getSupplierId().toString())){
				if(suborder.getStatus() ==1 ){
				suborder.setStatus(2);//发货
				if(suborder.getStockUp()!=null && suborder.getStockUp()==1){//修改订单备货状态
					suborder.setStockUp(0);
				}
				// 获取所有物流公司信息
			    Map<String, ExpressCompany> map_e = expressComService.getExpressCompanys();
			    String expressType="";
			    for (ExpressCompany ec : map_e.values()) {
					if(expressCom.equals(ec.getJaneSpell())||expressCom.equals(ec.getAbbreviation())){
						expressType = ec.getId();
						break;
					}
				}
				suborder.setExpressType(expressType);
				suborder.setExpressNo(expressNo);
				suborderService.update(suborder);
				String poductTitle = null;
				int num = 0;
				if(suborder.getSuborderitemlist()!=null&&suborder.getSuborderitemlist().size()>0){
					for(Suborderitem si :suborder.getSuborderitemlist()){
						num += si.getNumber();
						Product p = productService.getById(si.getProductId());
						if(p!=null){
							p.setAllnum(p.getAllnum()-si.getNumber());
							productService.saveOrUpdate(p);
															
							if(poductTitle==null) {
								poductTitle=p.getFullName();
							} else {
								if(!poductTitle.endsWith("...")){
									poductTitle += "...";
								}
							}
						}
					}
				}

				Orders o= ordersService.getById(suborder.getOrderId());
			    String msg = "";
			    String name = "";
			    if(o.isSelf()) {
					msg = "您购买的商品："+poductTitle+"已发货。请您确认是否已收到货物";
					name = "买家自提";
			    } else if("14660000000000000".equals(expressCom)) {
					msg = "您购买的商品："+poductTitle+"商家已确认。卡券密码："+expressNo+"，请在使用时出示，并注意妥善保管,订单编号("+suborder.getSubOrderId()+")";
					name = "电子卡券";
			    } else {
			    	ExpressCompany com = expressComService.getExpressComById(expressType);
			    	if(com!=null){
			    		name=com.getName();
			    	}
				    //ExpressCompany com = expressComService.getExpressCompanys().get(expressCom);
					msg = "您购买的商品："+poductTitle+"已经发货。快递公司："+name+"，运单号："+expressNo+"，请注意查收,订单编号("+suborder.getSubOrderId()+")";
					//name = com.getName();
			    }
			    AppPushUtil.pushMsg(o.getUserId(), "订单已发货", msg);
				
				//订单发出微信通知
				AppPushUtil.pushWxOrderSend(o.getUserId(), suborder.getSubOrderId(), poductTitle, num+"", name, expressNo);
				
				return OpenResponse.successSetMsg("发货成功!");
				}else if(2 ==suborder.getStatus()){
					return OpenResponse.successSetMsg("订单已发货,请勿重复操作!");
				}else{
					return OpenResponse.fail("订单状态错误,请确认订单信息!");
				}
			}else{
				return OpenResponse.fail("订单信息错误,请联系客服!");
			}
		}
	}
	
	
	
	
	@RequestMapping("expresses/get")
	@ResponseBody
	public Object expressList(){
		// 获取所有物流公司信息
		Map<String, ExpressCompany> map_e = expressComService.getExpressCompanys();		
		List<Map<String,Object>> resultMap = new ArrayList<Map<String,Object>>();
		if(null != map_e.values() && map_e.values().size() > 0){
			for (ExpressCompany expressCompany : map_e.values()) {
				Map<String,Object> result = new HashMap<String, Object>();
				result.put("id", expressCompany.getId());
				result.put("code", expressCompany.getJaneSpell());
				result.put("name", expressCompany.getAbbreviation());
				result.put("full_name", expressCompany.getName());
				resultMap.add(result);
			}
		}
		return OpenResponse.success(resultMap);
	}
	
	
}
