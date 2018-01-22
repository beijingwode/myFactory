package com.wode.factory.company.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.db.DBUtils;
import com.wode.factory.company.service.PaymentService;
import com.wode.factory.model.Payment;
import com.wode.factory.outside.service.AlipayService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.supplier.util.Constant;

@Controller
@RequestMapping("company/payment")
public class PaymentController extends BaseCompanyController {

	@Autowired
	private DBUtils dbUtils;

	@Autowired
	private PaymentService paymentService;
	
	
	//订单名称
	private String subject = "我的网现金储值";
	
	//订单描述
	private String body = "";
	//商品展示地址
	private String show_url = "";
	//需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html


	static AlipayService us = ServiceFactory.getAlipayService(Constant.OUTSIDE_SERVICE_URL);
	

	//页面跳转同步通知页面路径
	public final static String returnUrl=Constant.ALIPAY_RETURN_URL;

	//服务器异步通知页面路径
	public final static String notifyUrl=Constant.ALIPAY_NOTIFY_URL;
	
	/**
	 * 进入支付宝支付界面
	 * @param request
	 * @param money 金额
	 * @return
	 */
	@RequestMapping(value = { "/toPay" })
	public ModelAndView toPay(HttpServletRequest request ,BigDecimal money,String zhifu) {
		if("unionpay".equals(zhifu)){
			Payment payment = new Payment();
			payment.setOutTradeNo(dbUtils.CreateID()+"");
			payment.setTotalFee(money);
			payment.setStatus(0);
			payment.setCreateTime(new Date());
			payment.setOrderId(getSupplierId(request));
			payment.setPayType(3);		//3:现金账户充值
			payment.setOrderType(-1);
			payment.setPayConfirm(0);
			payment.setWay("unionpay");
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
			
			ModelAndView model = new ModelAndView();
			model.setViewName("unionpay");
			model.addObject("payment", submitFromData);
			model.addObject("requestUrl", UnionPayConfig.REQUEST_URL);

			return model;			
		} else {

			//外部订单号 企业id+时间戳
			String out_trade_no  = getSupplierId(request).toString()+dbUtils.CreateID()+"" ;
			
			String payFrom = aliPay(out_trade_no,money);
			Payment payment = new Payment();
			payment.setOutTradeNo(out_trade_no);
			payment.setTotalFee(money);
			payment.setStatus(0);
			payment.setPayType(3);		//3:现金账户充值
			payment.setOrderType(-1);
			payment.setPayConfirm(0);
			payment.setCreateTime(new Date());
			payment.setOrderId(getSupplierId(request));
			payment.setWay("zhifubao");
			payment.setAppId(AlipayService.APP_ID_OLD);
			paymentService.save(payment);
			ModelAndView model = new ModelAndView();
			model.addObject("payFrom", payFrom);
			model.setViewName("company/benefit/pay");
			return model;
		}
	}
	
	/**
	 * 
	 * @param out_trade_no 商户订单号 （商户网站订单系统中唯一订单号，必填）
	 * @param total_fee 付款金额
	 */
	private String aliPay(String outTradeNo,BigDecimal money){

		//防钓鱼时间戳
//		String anti_phishing_key = "";
		//若要使用请调用类文件submit中的query_timestamp函数
		//客户端的IP地址
		//String exter_invoke_ip = "";
		//非局域网的外网IP地址，如：221.0.0.1
		
		//把请求参数打包成数组
		return us.createDirectPay(outTradeNo, subject, money, body, show_url, notifyUrl, returnUrl);
		//return us.webPay(outTradeNo, subject, money, body, "http://test.wd-w.com/supplier/company/paymentCallBack/alipayNotify", "http://test.wd-w.com/supplier/company/paymentCallBack/alipayResult");	
	}
	
}
