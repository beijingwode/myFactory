package com.wode.factory.user.web.controller.unionpay;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Payment;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.facade.OrdersFacade;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.service.OrdersService;
import com.wode.factory.user.service.PaymentService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.web.controller.BaseController;

/**
 * Created by zoln on 2015/8/31.
 */
@Controller
@RequestMapping("/unionpay")
public class UnionPayController extends BaseController {

	@Autowired
	private OrdersFacade ordersFacade;
	
	@Qualifier("ordersService")
	@Autowired
	private OrdersService ordersService;

	@Qualifier("suborderService")
	@Autowired
	private SuborderService suborderService;

	@Qualifier("paymentService")
	@Autowired
	private PaymentService paymentService;

	@RequestMapping("topay")
	public String toPay(ModelMap model, HttpServletRequest request, HttpServletResponse response, Long orderId, String subOrderId) {
		Orders order = null;
		UserFactory user = getUser(request, response);
		Payment payment = new Payment();
		if (orderId != null) {
			order = ordersService.findById(user.getId(), orderId);
			if (null == order) {
				return "redirect:/index.html";
			}
			payment.setOrderId(order.getOrderId());
			payment.setTotalFee(order.getRealPrice());
		}
		Suborder suborder = null;
		if (subOrderId != null) {
			SuborderQuery query = new SuborderQuery();
			query.setUserId(user.getId());
			query.setSubOrderId(subOrderId);
			suborder = this.suborderService.findOrderDetailById(query);
			if (null == suborder) {
				return "redirect:/index.html";
			}
			payment.setSubOrderId(suborder.getSubOrderId());
			payment.setTotalFee(suborder.getRealPrice());
		}
		payment.setStatus(0);
		payment.setPayType(1);		//1:订单支付
		payment.setCreateTime(new Date());
		payment.setWay("unionpay");
		payment.setOrderType(0);
		paymentService.save(payment);

		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", "5.0.0");
		// 字符集编码 默认"UTF-8"
		data.put("encoding", "UTF-8");
		// 签名方法 01 RSA
		data.put("signMethod", "01");
		// 交易类型 01-消费
		data.put("txnType", "01");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "07");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", UnionPayConfig.REDIRECT_URL);
		// 后台通知地址
		data.put("backUrl", UnionPayConfig.NOTIFY_URL);
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", UnionPayConfig.PARTNER);
		// 商户订单号，8-40位数字字母
		data.put("orderId", String.valueOf(payment.getOutTradeNo()));
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额，单位分
		data.put("txnAmt", payment.getTotalFee().multiply(new BigDecimal(100)).setScale(0).toString());
		// 交易币种
		data.put("currencyCode", "156");
		Map<String, String> submitFromData = UnionPayConfig.signData(data);
		model.addAttribute("payment", submitFromData);
		model.addAttribute("requestUrl", UnionPayConfig.REQUEST_URL);
		return "unionpay";
	}

	@RequestMapping("back")
	@ResponseBody
	public String backResponse(Model model, HttpServletRequest req) {
		try {
			req.setCharacterEncoding("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String encoding = req.getParameter(SDKConstants.param_encoding);
		// 获取请求参数中所有的信息
		Map<String, String> reqParam = UnionPayConfig.getAllRequestParam(req);
		// 打印请求报文
		LogUtil.printRequestLog(reqParam);

		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Map.Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap(reqParam.size());
			while (it.hasNext()) {
				Map.Entry<String, String> e = it.next();
				String key = e.getKey();
				String value = e.getValue();
				try {
					value = new String(value.getBytes("ISO-8859-1"), encoding);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				valideData.put(key, value);
			}
		}

		// 验证签名
		if (!SDKUtil.validate(valideData, encoding)) {
			model.addAttribute("message", "验证签名结果[失败].");
			return "fail";
		} else {
			String orderId = valideData.get("orderId");
			String queryId = valideData.get("queryId");
			if (StringUtils.equalsIgnoreCase(valideData.get("respCode"), "00")) {
				Payment payment = paymentService.getById(Long.parseLong(orderId));
				payment.setTradeNo(queryId);
				payment.setStatus(2);//支付成功已回调
				if(com.wode.common.util.StringUtils.isEmpty(payment.getWay())) {
					payment.setWay("unionpay");
				}
				ordersFacade.updateOrderToPay(payment);
			}
		}
		return "success";
	}


	@RequestMapping("front")
	public String frontResponse(Model model, HttpServletRequest req) {
		try {
			req.setCharacterEncoding("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String encoding = req.getParameter(SDKConstants.param_encoding);
		// 获取请求参数中所有的信息
		Map<String, String> reqParam = UnionPayConfig.getAllRequestParam(req);
		// 打印请求报文
		LogUtil.printRequestLog(reqParam);

		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Map.Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap(reqParam.size());
			while (it.hasNext()) {
				Map.Entry<String, String> e = it.next();
				String key = e.getKey();
				String value = e.getValue();
				try {
					value = new String(value.getBytes("ISO-8859-1"), encoding);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				valideData.put(key, value);
			}
		}

		// 验证签名
		if (!SDKUtil.validate(valideData, encoding)) {
			model.addAttribute("message", "验证签名结果[失败].");
		} else {
			String orderId = valideData.get("orderId");
			Payment payment = paymentService.getById(Long.valueOf(orderId));
			if (payment.getStatus() == 2) {
				if (payment.getOrderId() != null)
					return "redirect:/member/paySuccess?orderId=" + payment.getOrderId();
				else
					return "redirect:/member/paySuccess?subOrderId=" + payment.getSubOrderId();
			}
		}
		return "response";
	}

}
