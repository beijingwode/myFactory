package com.wode.factory.company.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.db.DBUtils;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.JsonUtil;
import com.wode.factory.company.facade.EntBenefitFacade;
import com.wode.factory.company.query.EnterpriseVo;
import com.wode.factory.company.service.EnterpriseService;
import com.wode.factory.company.service.PaymentService;
import com.wode.factory.company.util.SeasonUtil;
import com.wode.factory.model.Payment;
import com.wode.factory.outside.service.AlipayService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.supplier.util.Constant;

@Controller
@RequestMapping("company/paymentCallBack")
public class PaymentCallBackController extends BaseCompanyController {

	@Autowired
	private EntBenefitFacade entBenefitFacade;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private DBUtils dBUtils;
	
	static AlipayService us = ServiceFactory.getAlipayService(Constant.OUTSIDE_SERVICE_URL);
	
	//异步通知(新版app/web 支付回调)
	@RequestMapping(value = { "/alipayNotify" })
	@NoCheckLogin
	public void alipayNotify(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		    String name = (String) iter.next();
		    String[] values = (String[]) requestParams.get(name);
		    String valueStr = "";
		    for (int i = 0; i < values.length; i++) {
		        valueStr = (i == values.length - 1) ? valueStr + values[i]
		                    : valueStr + values[i] + ",";
		  }
		    //乱码解决，这段代码在出现乱码时使用。
		    //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
		    params.put(name, valueStr);
		 }
		
		//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		boolean flag = us.verify(params);
				
		if(flag) {
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号
			String notify_type = request.getParameter("notify_type");
			if("batch_refund_notify".equals(notify_type)) {
				//TODO 退款通知
				
				// do something
				return;
			} else if("trade_status_sync".equals(notify_type)){
				
				String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

				//支付宝交易号
				String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

				//交易状态
				String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

				//交易状态
				BigDecimal price = new BigDecimal(request.getParameter("price"));

				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
					//计算得出通知验证结果
					Payment payment = new Payment();
					payment.setOutTradeNo(out_trade_no);
					List<Payment> list = paymentService.selectByModel(payment);
					payment = list.get(0);
					
					//福利流水id
					Long flowId = dBUtils.CreateID();
					//查询企业信息
					EnterpriseVo ent = enterpriseService.selectById(payment.getOrderId());
					
					if(payment.getTotalFee().compareTo(price)>=0 && payment.getStatus()<2) {				

						int result = entBenefitFacade.bankCash(payment.getOrderId(), SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(), price,flowId,null, ent.getName());
						if(result == 0){
							//存入DB 失败
							System.out.println("扣款成功 充值失败");
						}
						payment = list.get(0);
						payment.setOrderId(flowId);
						payment.setTradeNo(trade_no);
						payment.setStatus(2);//支付成功已回调
						payment.setUpdateTime(new Date());
						paymentService.update(payment);
					}
				}
			}

			logger.info("验证成功");
			//response.getOutputStream().flush();
			response.getOutputStream().println("success");
		}else{
			logger.info("验证失败");
			response.getOutputStream().println("fail");
		}
	}
	

	@RequestMapping(value = { "/alipayResult" })
	@NoCheckLogin
	public ModelAndView alipayResult(HttpServletRequest request) throws UnsupportedEncodingException{
		//获取支付宝GET过来反馈信息
		ModelAndView mv = new ModelAndView();
		int success = 1;
		String error="";
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		logger.info("alipay callback:"+JsonUtil.toJsonString(params));
		boolean flag = us.verify(params);
		
		if(flag) {
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号

			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝交易号

			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

			//企业id 去掉后15位的时间戳

			String enterpriseId = out_trade_no.substring(0,out_trade_no.length()-15);
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

			Payment payment = new Payment();
			payment.setOutTradeNo(out_trade_no);
			List<Payment> list = paymentService.selectByModel(payment);
			//如果已经完成后台处理，返回success
			if(list.size()>0 && list.get(0).getStatus() == 2){
				mv.addObject("success",success+"");
				mv.setViewName("redirect:/company/benefit/toSaveCash");
				return mv;
			}
			//查询企业信息
			EnterpriseVo ent = enterpriseService.selectById(Long.parseLong(enterpriseId));
			//验证成功
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				BigDecimal totalfee = new BigDecimal( params.get("total_fee"));//充值金额
				//福利流水id
				Long flowId = dBUtils.CreateID();
				int result = entBenefitFacade.bankCash(Long.parseLong(enterpriseId), SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(), totalfee,flowId,null, ent.getName());
				if(result == 0){
					//存入DB 失败
					System.out.println("扣款成功 充值失败");
					success=0;
					error="扣款成功 充值失败";
					mv.addObject("success",success+"");
					mv.addObject("error",error);
					mv.setViewName("redirect:/benefit/toSaveCash");
					return mv;
				}
				if(list.size()>0){
					payment = list.get(0);
					payment.setOrderId(flowId);
					payment.setTradeNo(trade_no);
					payment.setPayType(3); 		//3:现金账户充值
					payment.setStatus(2);//支付成功已回调
					payment.setUpdateTime(new Date());
					paymentService.update(payment);
				}else{
					payment.setCreateTime(new Date());
					payment.setUpdateTime(new Date());
					payment.setPayType(3); 		//3:现金账户充值
					payment.setTotalFee(totalfee);
					payment.setOrderId(flowId);
					payment.setTradeNo(trade_no);
					payment.setStatus(2);//支付成功已回调
					paymentService.save(payment);
				}
			}
			System.out.println("验证成功");
			
			mv.addObject("success",success+"");
			mv.setViewName("redirect:/company/benefit/toSaveCash");
			return mv;

		} else{
			System.out.println("验证失败");
			error="支付宝返回信息验证失败";
			success=0;
			mv.addObject("success",success+"");
			mv.addObject("error",error);
			mv.setViewName("redirect:/benefit/toSaveCash");
			return mv;
		}
	}
}
