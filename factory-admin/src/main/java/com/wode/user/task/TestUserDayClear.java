package com.wode.user.task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.constant.Constant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Payment;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.model.SupplierLimitTicket;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserLimitTicket;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.service.EnterpriseUserService;
import com.wode.factory.service.OrderService;
import com.wode.factory.service.PaymentService;
import com.wode.factory.service.SubOrderService;
import com.wode.factory.service.SupplierExchangeProductService;
import com.wode.factory.service.SupplierLimitTicketService;
import com.wode.factory.service.UserBalanceService;
import com.wode.factory.service.UserExchangeTicketService;
import com.wode.factory.service.UserFactoryService;
import com.wode.factory.service.UserLimitTicketService;
@Service
public class TestUserDayClear {
	@Autowired
	UserFactoryService userFactoryService;
	@Autowired
	EnterpriseUserService enterpriseUserService;
	@Autowired
	UserBalanceService userBalanceService;
	@Autowired
	SupplierExchangeProductService supplierExchangeProductService;
	@Autowired
	UserExchangeTicketService userExchangeTicketService;
	@Autowired
	OrderService orderService;
	@Autowired
	SubOrderService subOrderService;
	@Autowired
	PaymentService paymentService;
	@Autowired
	SupplierLimitTicketService supplierLimitTicketService;
	@Autowired
	UserLimitTicketService userLimitTicketService;
	@Autowired
	private RedisUtil redisUtil;
	
	private Set<String> interfaceUrl=new HashSet<String>();

	@Qualifier("creat_html_url")
	@Autowired
	public void setInterfaceUrl(String interfaceUrl) {
		String[] arr=interfaceUrl.split(",");
		for(String url:arr){
			if(!StringUtils.isEmpty(url)){
				this.interfaceUrl.add(url);
			}
		}

	}
	CommUserService cus= ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);

	/**
	 * 清除运营测试账号 每日凌晨执行
	 */
	public void run() {
		List<UserFactory> us =userFactoryService.getYYTUser();		
		for (UserFactory userFactory : us) {
			clearUserById(userFactory.getId());
		}
	}
	
	@Deprecated
	public void clearUserById(Long userId) {
		/////////////////订单清除//////////////////////
		List<Orders> os= orderService.findByUserId(userId);
		for (Orders orders : os) {
			List<Suborder>  sod = subOrderService.getsuborderIdByOrderId(orders.getOrderId());
			boolean orderdel = true;
			for (Suborder subOrder : sod) {
				Map<String,Object> paramMap = new HashMap<String,Object>();
				Orders order = orderService.findById(subOrder.getOrderId());
				paramMap.put("userId", order.getUserId());
				paramMap.put("subOrderId", subOrder.getSubOrderId());
				paramMap.put("closeReason", "因超时未付款，交易自动关闭");

				boolean cancel =false;
				for(String apiurl:interfaceUrl){
					try {
						String response = HttpClientUtil.sendHttpRequest("post", apiurl.replace("creatHtml","member/autoCancelOrder"), paramMap);
						ActResult as = JsonUtil.getObject(response, ActResult.class);
						if (as.isSuccess() || "订单已取消".equals(as.getMsg()) || "商家已经开始备货了，如要取消订单需要先联系商家。".equals(as.getMsg())) {
							cancel = true;
							break;							
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				if(cancel) {
					List<Payment> paymentList = paymentService.findBySubOrderId(subOrder.getSubOrderId());
					for (Payment payment : paymentList) {
						paymentService.delete(payment.getOutTradeNo());
					}
					subOrderService.delete(subOrder.getSubOrderId());
				} else {
					orderdel=false;
				}
			}
			
			if(orderdel) {
				List<Payment> paymentList = paymentService.findByOrderId(orders.getOrderId());
				for (Payment payment : paymentList) {
					paymentService.delete(payment.getOutTradeNo());
				}
				orderService.delete(orders.getOrderId());
			}
		}
		
		/////////////////换领币清除//////////////////////
		UserExchangeTicket uet = new UserExchangeTicket();
		uet.setUserId(userId);
		List<UserExchangeTicket> uets= userExchangeTicketService.selectByModel(uet);
		for (UserExchangeTicket userExchangeTicket : uets) {
			userExchangeTicketService.removeById(userExchangeTicket.getId());
			
			SupplierExchangeProduct sep= supplierExchangeProductService.getById(userExchangeTicket.getExchangeProductId());
			if(sep!=null && sep.getEmpCnt()!=null) {
				sep.setEmpCnt(sep.getEmpCnt()-1);
				if(sep.getEmpCnt()>-1) {
					supplierExchangeProductService.update(sep);
				}
			}
		}
		
		/////////////////优惠券清除//////////////////////
		UserLimitTicket ult = new UserLimitTicket();
		ult.setUserId(userId);
		List<UserLimitTicket> userLimitTicketList = userLimitTicketService.selectByModel(ult);
		for (UserLimitTicket userLimitTicket : userLimitTicketList) {
			SupplierLimitTicket supplierLimitTicket = supplierLimitTicketService.getById(userLimitTicket.getLimitTicketId());
			if(supplierLimitTicket.getReceiveNum()>0) {
				supplierLimitTicket.setReceiveNum(supplierLimitTicket.getReceiveNum()-1);
				supplierLimitTicketService.update(supplierLimitTicket);
			}
			userLimitTicketService.removeById(userLimitTicket.getId());
		}
		
		/////////////////用户余额清除//////////////////////
		userBalanceService.deleteByUserId(userId);

		/////////////////微信用户绑定信息清除//////////////////////
		userFactoryService.deleteUserWeixinByUserId(userId);

		/////////////////员工信息清除//////////////////////
		enterpriseUserService.delete(userId);

		/////////////////用户信息清除//////////////////////
		userFactoryService.delete(userId);
		
		/////////////////共同用户信息清除//////////////////////
		cus.deleteUser(userId, "myFactory", 2L);

		/////////////////缓存用户信息清除//////////////////////
		redisUtil.delMapData(RedisConstant.FACTORY_USER_MAP, userId+"");		
	}
}
