package com.wode.api.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.Orders;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserShare;
import com.wode.factory.user.facade.OrdersFacade;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.UserShareAutoBuy;
import com.wode.factory.user.service.OrdersService;
import com.wode.factory.user.service.SuborderitemService;
import com.wode.factory.user.service.UserShareAutoBuyService;
import com.wode.factory.user.service.UserShareService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.OrderUtils;
import com.wode.factory.user.vo.OrderVO;

/**
 * 2015-6-17
 *
 * @author 谷子夜
 */
@Controller
@RequestMapping("/autoBuy")
@ResponseBody
public class AutoBuyController extends BaseController {

	@Autowired
	private OrdersFacade ordersFacade;

	@Autowired
	UserShareService userShareService;

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private SuborderitemService suborderitemService;
	@Autowired
	private UserShareAutoBuyService userShareAutoBuyService;
    
	private static Logger logger = LoggerFactory.getLogger(AutoBuyController.class);
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/share{shareId}.user")
	@ResponseBody
	public ModelAndView share(HttpServletRequest request, @PathVariable Long shareId,String type,Long skuId,Long autoBuyId) {
		ModelAndView result = new ModelAndView();
				
		UserShareAutoBuy ab = userShareAutoBuyService.getById(autoBuyId);
		String orderLink = "";
		String moreLink = "";
		String errMsg = "";

		UserShare vo = userShareService.getById(shareId);
		moreLink = "http://"+Constant.SYSTEM_DOMAIN+"/index_m.htm";
		if(!StringUtils.isEmpty(vo.getTargetActionUrl())) {
			moreLink = vo.getTargetActionUrl();
		}
		if(!vo.getUserId().equals(loginUser.getSupplierId())) {
			// 非该企业员工 不进行自动购买，跳转活动页
			result.setViewName("redirect:"+moreLink);
	 		return result;
		}
		Suborderitem item = suborderitemService.findLastOne(skuId, loginUser.getId());
		if(item == null) {
			// 没有购买，自动购买
			Orders order = new Orders();
			order.setSelfDelivery("1");
			order.setUserId(loginUser.getId());
			order.setName(loginUser.getNickName());
			order.setPhone(loginUser.getPhone());
			OrderUtils.setMobileAndAddress(order, loginUser);

			order.setCreateTime(new Date());
			OrderVO ovo = new OrderVO();
			ovo.setOrders(order);
			
			ActResult<Cart> actCart = makeBuyCart(skuId, autoBuyId, ab,loginUser);
						
			if(actCart.isSuccess()) {
				//cartItem.setIsLadder(false);
				List<String> subIds = new ArrayList<String>();
				ActResult<Object> ar = ordersFacade.createOrder(ovo,actCart.getData(), BigDecimal.ZERO,subIds,"");// 保存订单
				if(!ar.isSuccess()){
					errMsg =ar.getMsg();
				} else {
	
					//记录内购券流水
					ActResult recordFlowResult = ordersService.recordFlow(order.getOrderId(), loginUser);
					if(!recordFlowResult.isSuccess()){
						ordersFacade.cancelOrder(loginUser, order.getOrderId(), recordFlowResult.getMsg(),true);
						errMsg =ar.getMsg();
					}
					orderLink = "orderM?subOrderId="+order.getOrderId()+"-1";
				}
			} else {
				errMsg =actCart.getMsg();
			}
		} else {
			// 已经领取成功
			errMsg ="again";
			orderLink = "orderM?subOrderId="+item.getSubOrderId();
		}
		result.addObject("moreLink", moreLink);
		result.addObject("orderLink", orderLink);
		result.addObject("errMsg", errMsg);
		ab.setItemValues(ab.getItemValues().replace("{", "").replace("}", "").replace("\"", "").replace("\\", ""));
		result.addObject("autoBuy", ab);
		result.setViewName("autoBuy/auto_buy_result");
 		return result;
	}

	private ActResult<Cart> makeBuyCart(Long skuId, Long autoBuyId, UserShareAutoBuy ab,UserFactory user) {
		// 创建结算购物车
		List<Long> skuIds = new ArrayList<Long>();
		skuIds.add(skuId);
		Map<Long,Integer> sku_nuns = new HashMap<Long,Integer>();
		sku_nuns.put(skuId, ab.getSkuNum());
		Map<Long,BigDecimal> sku_realPrices = new HashMap<Long,BigDecimal>();
		Map<Long,BigDecimal> sku_freights = new HashMap<Long,BigDecimal>();
		sku_freights.put(skuId, BigDecimal.ZERO);
		Map<Long,String> sku_images = new HashMap<Long,String>();
		sku_images.put(skuId, ab.getImage());
		Map<Long,String> sku_pageKeys = new HashMap<Long,String>();
		sku_pageKeys.put(skuId, "autoBuyC"+autoBuyId);
		Map<Long,String> sku_froms = new HashMap<Long,String>();
		sku_froms.put(skuId, "wx");
		List<ProductSpecifications> outSkus = new ArrayList<ProductSpecifications>();

		ActResult<Cart> actCart = ordersService.mergeBuyCart("wx", user,skuIds, sku_nuns,sku_realPrices, sku_freights,
				sku_images, sku_pageKeys, sku_froms,null, null, null, null, outSkus);
		return actCart;
	}
}
