/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.DateUtils;
import com.wode.common.util.EmailUtil;
import com.wode.common.util.FileUtils;
import com.wode.common.util.StringUtils;
import com.wode.factory.mapper.SuborderitemDao;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.ExchangeSuborderitem;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.GroupSuborderitem;
import com.wode.factory.model.Payment;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.Supplier;
import com.wode.factory.service.ExchangeSuborderService;
import com.wode.factory.service.GroupOrdersService;
import com.wode.factory.service.PaymentService;
import com.wode.factory.service.SubOrderService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.vo.PaymentVo;
import com.wode.sys.model.SysUser;

@Controller
@RequestMapping("payment")
public class PaymentController {
    @Qualifier("redis")
    @Autowired
    private RedisUtil redisUtil;
	@Autowired
	@Qualifier("emailUtil")
	private EmailUtil emailUtil;
	@Resource
	private PaymentService paymentService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private SubOrderService subOrderService;
	@Autowired
	private GroupOrdersService groupOrdersService;
	@Autowired
	private ExchangeSuborderService exchangeSuborderService;
	@Autowired
	private SuborderitemDao suborderitemDao;
	
	@RequestMapping("base")
	public String toPageAttrView(Model model,HttpSession session) {
		return "manager/payment/base";
	}
	
	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params, Model model,HttpSession session) {				
		PageInfo<Payment> page = paymentService.findList(params);
		model.addAttribute("page", page);
		return "manager/payment/list";
	}

	/**第三方支付
	 * @param cash
	 * @return
	 */
	@RequestMapping("/thirdPayment")
	public String thirdPayment( Model model,HttpSession session) {
//		
//		Map<String, Object> query = new HashMap<String, Object>();
//		model.addAttribute("supplierList", getSupplierList(query));
		
		return "manager/payment/third-payment";
	}

	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listPayment", method = RequestMethod.POST)
	public String listPayment(@RequestParam Map<String, Object> params, Model model,HttpSession session,HttpServletRequest request) {
		formatParams(params, request,false);
		params.put("sortColumns", "createTime desc");
		PageInfo<PaymentVo> page = paymentService.findPaymentList(params);
		//Map<String, PaymentVo> map = new HashMap<String, PaymentVo>();
		for(int i=0;i<page.getList().size();i++) {
			PaymentVo one = page.getList().get(i);
			if(StringUtils.isEmpty(one.getSubOrderId())) {
				one.setSubOrderId(one.getOrderId()+"");
			} else {
				if(one.getOrderType()==4||one.getOrderType()==1){
					one.setSubOrderId("<a class='purple' href='group/detail/showteail?id="+one.getSubOrderId()+"' target='_blank'>"+one.getSubOrderId()+"</a>" );		
				}else if(one.getOrderType()==5) {
					one.setSubOrderId("<a class='purple' href='exchange/detail/showteail?id="+one.getSubOrderId()+"' target='_blank'>"+one.getSubOrderId()+"</a>" );
				}else {
					one.setSubOrderId("<a class='purple' href='orderList/detail/showlayer?id="+one.getSubOrderId()+"' target='_blank'>"+one.getSubOrderId()+"</a>" );
				}
				
			}
		}
	
		model.addAttribute("page", page);
		return "manager/payment/list-payment";
	}

	/** 订单支付入账
	 * @param cash
	 * @return
	 */
	@RequestMapping("/orderPayment")
	public String orderPayment( Model model,HttpSession session) {
//		
//		Map<String, Object> query = new HashMap<String, Object>();
//		model.addAttribute("supplierList", getSupplierList(query));
		
		return "manager/payment/order-payment";
	}

	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listOrderPayment", method = RequestMethod.POST)
	public String listOrderPayment(@RequestParam Map<String, Object> params, Model model,HttpSession session,HttpServletRequest request) {
		formatParams(params, request,true);
		
		PageInfo<PaymentVo> page = paymentService.findPaymentList(params);
		Map<Long, Supplier> map = new HashMap<Long, Supplier>();
		
		for (PaymentVo p : page.getList()) {

    		Date createDate= null;
    		StringBuilder productName= new StringBuilder();
    		BigDecimal ticcket= BigDecimal.ZERO;
    		int productCnt = 0;
    		BigDecimal amount= BigDecimal.ZERO;
    		BigDecimal shipping= BigDecimal.ZERO;
    		//BigDecimal cashPay= BigDecimal.ZERO;
    		//String cashNo="";
    		//BigDecimal needPay= BigDecimal.ZERO;
    		
            if(null==p.getOrderType() || 0==p.getOrderType()) {
            	//普通订单
        		if(StringUtils.isNullOrEmpty(p.getSubOrderId())){
        			//通过orderid查suborder
    				List<Suborder> subList=subOrderService.getsuborderIdByOrderId(p.getOrderId());
    				//suborder为list
    				boolean first=true;
    				for (Suborder sub : subList) {
    					if(first) {
    						createDate=sub.getCreateTime();
    						Supplier s = this.getSupplier(map, sub.getSupplierId());
    						if(s!=null) {
    							p.setSupplierName(s.getComName());
    						}
    						first=false;
    						//cashNo=sub.getCashNo();
    					}
    					
    					ticcket=ticcket.add(sub.getCompanyTicket());
    					List<Suborderitem> items = suborderitemDao.findBySubIdForView(sub.getSubOrderId());
    					for (Suborderitem suborderitem : items) {
    						productName.append(suborderitem.getProductName()).append(",");
    						productCnt +=suborderitem.getNumber();
    						amount=amount.add(suborderitem.getRealPay());
						}
    					shipping=shipping.add(sub.getTotalShipping());
    					//cashPay=cashPay.add(sub.getCashPay());
    				}
    				p.setSubOrderId(p.getOrderId()+"");
        		} else {
    				Suborder sub=subOrderService.getById(p.getSubOrderId());
					createDate=sub.getCreateTime();
					Supplier s = this.getSupplier(map, sub.getSupplierId());
					if(s!=null) {
						p.setSupplierName(s.getComName());
					}
					//cashNo=sub.getCashNo();
					ticcket=ticcket.add(sub.getCompanyTicket());
					List<Suborderitem> items = suborderitemDao.findBySubIdForView(sub.getSubOrderId());
					for (Suborderitem suborderitem : items) {
						productName.append(suborderitem.getProductName()).append(",");
						productCnt +=suborderitem.getNumber();
						amount=amount.add(suborderitem.getRealPay());
					}
					shipping=shipping.add(sub.getTotalShipping());
					//cashPay=cashPay.add(sub.getCashPay());
					p.setSubOrderId("<a class='purple' href='orderList/detail/showlayer?id="+p.getSubOrderId()+"' target='_blank'>"+p.getSubOrderId()+"</a>" );
        		}
            } else if(1==p.getOrderType() || 4==p.getOrderType()) {
            	//团购单
            	if(StringUtils.isNullOrEmpty(p.getSubOrderId())){
        			//通过orderid查suborder
            		GroupOrders go = groupOrdersService.findById(p.getOrderId());
					shipping=go.getTotalShipping();
					
    				List<GroupSuborder> subList=groupOrdersService.findSuborderIdLikeOrderId(p.getOrderId());
    				//suborder为list
    				boolean first=true;
    				for (GroupSuborder sub : subList) {
    					if(first) {
    						createDate=sub.getCreateTime();
    						Supplier s = this.getSupplier(map, sub.getSupplierId());
    						if(s!=null) {
    							p.setSupplierName(s.getComName());
    						}
    						first=false;
    						//cashNo=sub.getCashNo();
    					}
    					
    					ticcket=ticcket.add(sub.getCompanyTicket());
    					List<GroupSuborderitem> items = groupOrdersService.findByItemsSubId(sub.getSubOrderId());
    					for (GroupSuborderitem suborderitem : items) {
    						productName.append(suborderitem.getProductName()).append(",");
    						productCnt +=suborderitem.getNumber();
    						amount=amount.add(suborderitem.getRealPay());
						}
    					//cashPay=cashPay.add(sub.getCashPay());
    				}
    				p.setSubOrderId(p.getOrderId()+"");
        		} else {
        			GroupSuborder sub=groupOrdersService.getBySubOrderId(p.getSubOrderId());
					createDate=sub.getCreateTime();
					Supplier s = this.getSupplier(map, sub.getSupplierId());
					if(s!=null) {
						p.setSupplierName(s.getComName());
					}
					//cashNo=sub.getCashNo();
					ticcket=ticcket.add(sub.getCompanyTicket());
					List<GroupSuborderitem> items = groupOrdersService.findByItemsSubId(sub.getSubOrderId());
					for (GroupSuborderitem suborderitem : items) {
						productName.append(suborderitem.getProductName()).append(",");
						productCnt +=suborderitem.getNumber();
						amount=amount.add(suborderitem.getRealPay());
					}

            		GroupOrders go = groupOrdersService.findById(Long.valueOf(p.getSubOrderId().substring(0,p.getSubOrderId().indexOf("-"))));
					shipping=go.getTotalShipping();
					//shipping=shipping.add(sub.getTotalShipping());
					//cashPay=cashPay.add(sub.getCashPay());
					p.setSubOrderId("<a class='purple' href='group/detail/showteail?id="+p.getSubOrderId()+"' target='_blank'>"+p.getSubOrderId()+"</a>" );	
        		}
            } else if(5==p.getOrderType()) {
            	//换领单
            	//团购单
            	if(StringUtils.isNullOrEmpty(p.getSubOrderId())){
        			//通过orderid查suborder
    				ExchangeSuborder ex = new ExchangeSuborder();
    				ex.setOrderId(p.getOrderId());
    				List<ExchangeSuborder> subList = exchangeSuborderService.selectByModel(ex);
    				//suborder为list
    				boolean first=true;
    				for (ExchangeSuborder sub : subList) {
    					if(first) {
    						createDate=sub.getCreateTime();
    						Supplier s = this.getSupplier(map, sub.getSupplierId());
    						if(s!=null) {
    							p.setSupplierName(s.getComName());
    						}
    						first=false;
    						//cashNo=sub.getCashNo();
    					}
    					
    					ticcket=ticcket.add(sub.getCompanyTicket());
    					List<ExchangeSuborderitem> items = exchangeSuborderService.selectItems(sub.getSubOrderId());
    					for (ExchangeSuborderitem suborderitem : items) {
    						productName.append(suborderitem.getProductName()).append(",");
    						productCnt +=suborderitem.getNumber();
    						amount=amount.add(suborderitem.getRealPay());
						}
    					shipping=shipping.add(sub.getTotalShipping());
    					//cashPay=cashPay.add(sub.getCashPay());
    				}
    				p.setSubOrderId(p.getOrderId()+"");
        		} else {
        			ExchangeSuborder sub=exchangeSuborderService.getById(p.getSubOrderId());
					createDate=sub.getCreateTime();
					Supplier s = this.getSupplier(map, sub.getSupplierId());
					if(s!=null) {
						p.setSupplierName(s.getComName());
					}
					//cashNo=sub.getCashNo();
					ticcket=ticcket.add(sub.getCompanyTicket());
					List<ExchangeSuborderitem> items = exchangeSuborderService.selectItems(sub.getSubOrderId());
					for (ExchangeSuborderitem suborderitem : items) {
						productName.append(suborderitem.getProductName()).append(",");
						productCnt +=suborderitem.getNumber();
						amount=amount.add(suborderitem.getRealPay());
					}
					shipping=shipping.add(sub.getTotalShipping());
					//cashPay=cashPay.add(sub.getCashPay());
					p.setSubOrderId("<a class='purple' href='exchange/detail/showteail?id="+p.getSubOrderId()+"' target='_blank'>"+p.getSubOrderId()+"</a>" );
        		}
            	
            }
            
			//			<th class="center">下单时间</th>
			//			<th class="center">商品名称（标题） </th>
			//			<th class="center">内购券金额 </th>
			//			<th class="center">商品数量</th>
			//			<th class="center">商品金额</th>
			//			<th class="center">运费</th>
    		StringBuilder td= new StringBuilder();
    		td.append("").append(DateUtils.formatDate(createDate,"yyyy-MM-dd HH:mm")).append("</td>");
    		td.append("<td>").append(productName.toString().substring(0, productName.length()>30?30:productName.length())).append("</td>");
    		td.append("<td>").append(ticcket).append("</td>");
    		td.append("<td>").append(productCnt).append("</td>");
    		td.append("<td>").append(amount).append("</td>");
    		td.append("<td>").append(shipping).append("");
    		p.setNote(td.toString());
		}
	
		model.addAttribute("page", page);
		return "manager/payment/list-order-payment";
	}
	
	private void formatParams(Map<String, Object> params, HttpServletRequest request,boolean forOrder) {
		// 支付类型
		String[] ary = request.getParameterValues("payType");
		if(ary!=null && ary.length>0) {
			StringBuilder sb =new StringBuilder();
			for (String string : ary) {
				string = string.trim();
				if("1".equals(string)) {
					sb.append("1,4,5");	
				} else {
					sb.append(string).append(",");
				}
			}
			params.put("payType", sb.toString());
		} else {
			if(forOrder) {
				params.put("payType", "1,4,5,11,-1,-2,-3");
			} else {
				params.put("payType", "1,2,3,4,5,11,-1,-2,-3");
			}
		}
		
		// 订单类型
		ary = request.getParameterValues("orderType");
		if(ary!=null && ary.length>0) {
			StringBuilder sb =new StringBuilder();
			for (String string : ary) {
				string = string.trim();
				if("1".equals(string)) {
					sb.append("1,4,");	
				} else {
					sb.append(string).append(",");
				}
			}
			params.put("orderType", sb.toString());
		} else {
			params.put("orderType", "0,1,4,5");
		}
				
		// 支付方式
		String py = request.getParameter("way");
		if(py.equals("wxgz")){
			params.put("way", "wxpay");
			params.put("appId", "wxb62e121cbeffdddf");
		}else if(py.equals("wxapp")){
			params.put("way", "wxpay");
			params.put("appId", "wx1b153767a3760be4");
		}else if(py.equals("zhifubao")){
			params.put("way", "zhifubao");
		}else if(py.equals("unionpay")){
			params.put("way", "unionpay");
		}else if(py.equals("pingtaiyue")){
			params.put("way", "pingtaiyue");
		} else {
			params.put("way", "wxpay,zhifubao,unionpay,pingtaiyue");
		}
	}
	/**
	 * 弹窗
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "toConfirmPay", method = RequestMethod.POST)
	public String toConfirmPay(Model model,String id) {
		Payment payment = paymentService.getById(id);
		model.addAttribute("payment", payment);
		return "manager/payment/confirmBox";
	}

	/**修改对账单状态值
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/confirmPay")
	@ResponseBody
	public ActResult<Object> comfirmReturn(String id,Integer payStatus, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		confirmPay(id, payStatus, user);
		return ActResult.success(null);
	}
	
	
	/**退款弹框
	 * @param saleBill
	 * @return
	 */
	@RequestMapping(value = "toConfirmRePay", method = RequestMethod.POST)
	public String toConfirmRePay(Model model,String id) {
		Payment payment = paymentService.getById(id);
		model.addAttribute("payment", payment);
		return "manager/payment/repay-confirmBox";
	}
	/**修改对退款账单状态值
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/comfirmRePayReturn")
	@ResponseBody
	public ActResult<Object> comfirmRePayReturn(String id,Integer payStatus, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;

		Payment payment = paymentService.getById(id);
		payment.setPayConfirm(payStatus);
		payment.setPayConfirmDate(new Date());
		payment.setUpdUser(user.getName());
		payment.setUpdateTime(new Date());
		//更新payment表
		paymentService.update(payment);
		return ActResult.success(null);
	}
	
	/**
	 * 统一操作弹窗
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "tobatchOperation", method = RequestMethod.POST)
	public String tobatchOperation(Model model,String ids) {
		String[] idsarr = ids.split(",");
		int length = idsarr.length;
		model.addAttribute("ids",ids);
		model.addAttribute("count",length);
		return "manager/payment/batchOperation";
	}
	/**
	 * 统一操作
	 * @param ids
	 * @param payStatus
	 * @param session
	 * @return
	 */
	@RequestMapping("/batchOperation")
	@ResponseBody
	public ActResult<Object> batchOperation(String ids,Integer payStatus, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		String[] idsarr = ids.split(",");
		for (int i = 0; i < idsarr.length; i++) {
			this.confirmPay(idsarr[i], payStatus, user);
		}
		return ActResult.success(null);
	}

	/**
	 * 支付到款确认同时标记支付记录，及相关个种订单到款状态
	 * @param id
	 * @param payStatus
	 * @param user
	 */
	private void confirmPay(String id, Integer payStatus, SysUser user) {
		Payment payment = paymentService.getById(id);
		Date now = new Date();
		payment.setPayConfirm(payStatus);
		payment.setPayConfirmDate(now);
		payment.setUpdUser(user.getName());
		payment.setUpdateTime(now);
		boolean whether = true;
		//更新payment表
		paymentService.update(payment);
		//更新subOrder表
		if(StringUtils.isNullOrEmpty(payment.getSubOrderId())){
			if(payment.getOrderType()==1||payment.getOrderType()==4) {//团购单到款确认
				List<GroupSuborder> findSuborderIdLikeOrderId = groupOrdersService.findSuborderIdLikeOrderId(payment.getOrderId());
				for (GroupSuborder groupSuborder : findSuborderIdLikeOrderId) {
					groupSuborder.setPayConfirm(payStatus);
					groupSuborder.setPayConfirmDate(now);
					groupSuborder.setUpdateBy(user.getName());
					groupSuborder.setUpdateTime(now);
					groupOrdersService.update(groupSuborder);
					GroupOrders findById = groupOrdersService.findById(groupSuborder.getOrderId());
					//根据团id查询全部团订单
					List<GroupOrders> findByGroupId = groupOrdersService.findByGroupId(findById.getGroupId());
					for (GroupOrders groupOrders : findByGroupId) {
						//查询子单
						List<GroupSuborder> findByOrderId = groupOrdersService.findByOrderId(groupOrders.getOrderId());
						for (GroupSuborder groupSuborder1 : findByOrderId) {
							if(groupSuborder1.getPayConfirm()!=1) {
								whether = false;
								break;
							}
							//Payment payments = paymentService.findPayByGroupOrderIdOrGroupSubOrderId(groupSuborder.getSubOrderId());
						}
						if(whether == false) {
							break;
						}
					}
					if(whether==true) {//团下订单全部确认到款    更新订单
						List<Suborder> suborderList = subOrderService.findByRelationId(findById.getGroupId());
						if(suborderList!=null) {
							for (Suborder suborder : suborderList) {
								suborder.setPayConfirm(1);
								suborder.setPayConfirmDate(now);
								suborder.setUpdateBy(user.getName());
								suborder.setUpdateTime(now);
								subOrderService.update(suborder);
							}
					   }
					}else {
						List<Suborder> suborderList = subOrderService.findByRelationId(findById.getGroupId());
						if(suborderList!=null) {
							for (Suborder suborder : suborderList) {
								suborder.setPayConfirm(0);
								suborder.setPayConfirmDate(now);
								suborder.setUpdateBy(user.getName());
								suborder.setUpdateTime(now);
								subOrderService.update(suborder);
							}
					   }
					}
				}
			}else if(payment.getOrderType()==5) {//换领单到款确认
				ExchangeSuborder ex = new ExchangeSuborder();
				ex.setOrderId(payment.getOrderId());
				List<ExchangeSuborder> selectByModel = exchangeSuborderService.selectByModel(ex);
				//suborder为list
				for (ExchangeSuborder suborder : selectByModel) {
					suborder.setPayConfirm(payStatus);
					suborder.setPayConfirmDate(now);
					suborder.setUpdateBy(user.getName());
					suborder.setUpdateTime(now);
					exchangeSuborderService.update(suborder);
					if(suborder.getRelationId()!=null&&suborder.getBatchId()==null) {
						Suborder byId = subOrderService.getById(suborder.getRelationId());
						//Payment payments = paymentService.findPaymentByOrderOrSuborderId(byId.getOrderId(),byId.getSubOrderId());
						byId.setPayConfirm(payStatus);
						byId.setPayConfirmDate(now);
						byId.setUpdateBy(user.getName());
						byId.setUpdateTime(now);
						subOrderService.update(byId);
					}else if(suborder.getRelationId()==null&&suborder.getBatchId()!=null) {
						//boolean whether = true;
						ExchangeSuborder exs = new ExchangeSuborder();
						exs.setBatchId(suborder.getBatchId());
						List<ExchangeSuborder> selectByModels = exchangeSuborderService.selectByModel(exs);
						for (ExchangeSuborder exchangeSuborder : selectByModels) {
							if(exchangeSuborder.getPayConfirm()!=1) {
								whether = false;
								break;
							}
						}
						if(whether==true) {
							List<Suborder> findByRelationId = subOrderService.findByRelationId(Long.valueOf(suborder.getBatchId()));
							for (Suborder suborder1 : findByRelationId) {
								suborder1.setPayConfirm(1);
								suborder1.setPayConfirmDate(now);
								suborder1.setUpdateBy(user.getName());
								suborder1.setUpdateTime(now);
								subOrderService.update(suborder1);
							}
						}else {
							List<Suborder> findByRelationId = subOrderService.findByRelationId(Long.valueOf(suborder.getBatchId()));
							for (Suborder suborder1 : findByRelationId) {
								suborder1.setPayConfirm(0);
								suborder1.setPayConfirmDate(now);
								suborder1.setUpdateBy(user.getName());
								suborder1.setUpdateTime(now);
								subOrderService.update(suborder1);
							}
						}
						
					}
				}
			}else {
				//通过orderid查suborder
				List subList=subOrderService.getsuborderIdByOrderId(payment.getOrderId());
				//suborder为list
				for (Object sub : subList) {
					Suborder suborder = (Suborder)sub;
					suborder.setPayConfirm(payStatus);
					suborder.setPayConfirmDate(now);
					suborder.setUpdateBy(user.getName());
					suborder.setUpdateTime(now);
					subOrderService.update(suborder);
				}
			}
		}else{
			if(payment.getOrderType()==1||payment.getOrderType()==4) {//团购单到款确认
				GroupSuborder suborder = groupOrdersService.getBySubOrderId(payment.getSubOrderId());
				suborder.setPayConfirm(payStatus);
				suborder.setPayConfirmDate(now);
				suborder.setUpdateBy(user.getName());
				suborder.setUpdateTime(now);
				groupOrdersService.update(suborder);
				GroupOrders findById = groupOrdersService.findById(suborder.getOrderId());
				//根据团id查询全部团订单
				List<GroupOrders> findByGroupId = groupOrdersService.findByGroupId(findById.getGroupId());
				for (GroupOrders groupOrders : findByGroupId) {
					//查询子单
					List<GroupSuborder> findByOrderId = groupOrdersService.findByOrderId(groupOrders.getOrderId());
					for (GroupSuborder groupSuborder1 : findByOrderId) {
						if(groupSuborder1.getPayConfirm()!=1) {
							whether = false;
							break;
						}
					}
					if(whether == false) {
						break;
					}
					
				}
				if(whether==true) {//团下订单全部确认到款    更新订单
					List<Suborder> suborderList = subOrderService.findByRelationId(findById.getGroupId());
					if(suborderList!=null) {
						for (Suborder suborder1 : suborderList) {
							suborder1.setPayConfirm(1);
							suborder1.setPayConfirmDate(now);
							suborder1.setUpdateBy(user.getName());
							suborder1.setUpdateTime(now);
							subOrderService.update(suborder1);
						}
				   }
				}else {
					List<Suborder> suborderList = subOrderService.findByRelationId(findById.getGroupId());
					if(suborderList!=null) {
						for (Suborder suborder1 : suborderList) {
							suborder1.setPayConfirm(0);
							suborder1.setPayConfirmDate(now);
							suborder1.setUpdateBy(user.getName());
							suborder1.setUpdateTime(now);
							subOrderService.update(suborder1);
						}
				   }
				}
			}else if(payment.getOrderType()==5) {//换领单到款确认
				ExchangeSuborder suborder = exchangeSuborderService.getById(payment.getSubOrderId());
				suborder.setPayConfirm(payStatus);
				suborder.setPayConfirmDate(now);
				suborder.setUpdateBy(user.getName());
				suborder.setUpdateTime(now);
				exchangeSuborderService.update(suborder);
				if(suborder.getRelationId()!=null&&suborder.getBatchId()==null) {
					Suborder byId = subOrderService.getById(suborder.getRelationId());
					//Payment payments = paymentService.findPaymentByOrderOrSuborderId(byId.getOrderId(),byId.getSubOrderId());
					byId.setPayConfirm(payStatus);
					byId.setPayConfirmDate(now);
					byId.setUpdateBy(user.getName());
					byId.setUpdateTime(now);
					subOrderService.update(byId);
				}else if(suborder.getRelationId()==null&&suborder.getBatchId()!=null) {
					ExchangeSuborder exs = new ExchangeSuborder();
					exs.setBatchId(suborder.getBatchId());
					List<ExchangeSuborder> selectByModels = exchangeSuborderService.selectByModel(exs);
					for (ExchangeSuborder exchangeSuborder : selectByModels) {
						if(exchangeSuborder.getPayConfirm()!=1) {
							whether = false;
							break;
						}
					}
					if(whether==true) {
						List<Suborder> findByRelationId = subOrderService.findByRelationId(Long.valueOf(suborder.getBatchId()));
						for (Suborder suborder1 : findByRelationId) {
							suborder1.setPayConfirm(1);
							suborder1.setPayConfirmDate(now);
							suborder1.setUpdateBy(user.getName());
							suborder1.setUpdateTime(now);
							subOrderService.update(suborder1);
						}
					}else {
						List<Suborder> findByRelationId = subOrderService.findByRelationId(Long.valueOf(suborder.getBatchId()));
						for (Suborder suborder1 : findByRelationId) {
							suborder1.setPayConfirm(0);
							suborder1.setPayConfirmDate(now);
							suborder1.setUpdateBy(user.getName());
							suborder1.setUpdateTime(now);
							subOrderService.update(suborder1);
						}
					}
					
				}
			}else {
				Suborder suborder=subOrderService.getById(payment.getSubOrderId());
				suborder.setPayConfirm(payStatus);
				suborder.setPayConfirmDate(now);
				suborder.setUpdateBy(user.getName());
				suborder.setUpdateTime(now);
				subOrderService.update(suborder);
			}
		}
	}
	
//	private List<Supplier> getSupplierList(Map<String, Object> query) {
//		query.put("status", 2);
//		query.put("pageNum", 1);
//		query.put("pageSize", 5000);
//		
//		return supplierService.getPage(query).getList();
//	}
	
	/**
	 * 现金券储值导出
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "exportExcel")
	@ResponseBody
	public void downLoadExcel(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 50000);

		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("充值记录一览"); 
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        
        /**
         * 设置样式 start
         * */
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        /**设置样式  end
         * */
        List<String> headers= new ArrayList<String>();
        headers.add("用户id");
        headers.add("用户姓名");
        headers.add("储值时间");
        headers.add("支付状态");
        headers.add("储值金额");
        headers.add("支付方式");
        headers.add("支付流水号");
        headers.add("到款状态");
        /**
         * 
         * 设置订单详情表头 start
         * */
        HSSFRow row = sheet.createRow((int) 0); 
        for (int i = 0; i < headers.size(); i++) {
        	HSSFCell cell = row.createCell(i);
            //设置值
            cell.setCellValue(headers.get(i));  
            //设置样式
            cell.setCellStyle(style);
		}
        /** 设置订单详情表头 end
         * */
        int currentRow = 0;
        PageInfo<Payment> page = paymentService.findList(params);
		for (Payment p : page.getList()) {

			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 
            //headers.add("用户id");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getOrderId().toString());
            //headers.add("用户姓名");
            row.createCell(col++).setCellValue(p.getExp1());
            //headers.add("储值时间");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getCreateTime()==null?"":DateUtils.formatDate(p.getCreateTime(),"yyyy-MM-dd")); //创建时间
            //headers.add("支付状态");
            if(p.getStatus()==null) {
                row.createCell(col++).setCellValue("");
            } else if(p.getStatus()==0) {
                row.createCell(col++).setCellValue("待支付");
            } else if(p.getStatus()==1) {
                row.createCell(col++).setCellValue("支付成功");
            } else {
                row.createCell(col++).setCellValue("");
            } 
            //headers.add("储值金额");
            row.createCell(col++).setCellValue(p.getTotalFee().doubleValue());
            //headers.add("支付方式");
            if("zhifubao".equals(p.getWay())) {
	            row.createCell(col++).setCellValue("支付宝"); //创建时间
            } else if("pingtaiyue".equals(p.getWay()))  {
	            row.createCell(col++).setCellValue("现金券"); //创建时间
            } else if("wxpay".equals(p.getWay()))  {
	            row.createCell(col++).setCellValue("微信"); //创建时间
            } else if("unionpay".equals(p.getWay()))  {
	            row.createCell(col++).setCellValue("银联"); //创建时间
            } else {
	            row.createCell(col++).setCellValue("");
            }
            //headers.add("支付流水号");
            row.createCell(col++).setCellValue(p.getTradeNo()); //创建时间
            //headers.add("到款状态");
            if(1 == p.getPayConfirm()) {
	            row.createCell(col++).setCellValue("已到款"); //创建时间
            } else {
	            row.createCell(col++).setCellValue("未到款");
            }
		}
		
        // 第六步，将文件存到指定位置  
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ FileUtils.getFileNameForSave(request,"现金券充值一览"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		
		try {
			wb.write(response.getOutputStream());
			wb.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	// 支付到款确认导出
	@RequestMapping(value = "exportExcelPayFinance")
	@ResponseBody
	public void exportExcelPayFinance(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		formatParams(params, request,false);
		params.put("pageNum", 1);
		params.put("pageSize", 50000);
		
		PageInfo<PaymentVo> page = paymentService.findPaymentList(params);
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("第三方支付一览"); 
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        
        /**
         * 设置样式 start
         * */
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        /**设置样式  end
         * */
        List<String> headers= new ArrayList<String>();
        headers.add("操作日期");
        headers.add("支付操作");
        headers.add("订单类型");
        headers.add("金额 ");
        headers.add("支付方式");
        headers.add("支付流水号");
        headers.add("处理状态");
        headers.add("订单id");
        headers.add("商家名称");
        headers.add("到款状态");

        /**
         * 
         * 设置订单详情表头 start
         * */
        HSSFRow row = sheet.createRow((int) 0); 
        for (int i = 0; i < headers.size(); i++) {
        	HSSFCell cell = row.createCell(i);
            //设置值
            cell.setCellValue(headers.get(i));  
            //设置样式
            cell.setCellStyle(style);
		}
        /** 设置订单详情表头 end
         * */
        int currentRow = 0;
        Map<String, PaymentVo> map = new HashMap<String, PaymentVo>();
        for (PaymentVo p : page.getList()) {
        	if(map.containsKey(p.getOutTradeNo())) {
        		PaymentVo one = map.get(p.getOutTradeNo());
        		one.setSubOrderId(one.getSubOrderId()+"\r\n"+p.getSubOrderId());
        		one.setSupplierName(one.getSupplierName()+"\r\n"+p.getSupplierName());
        		p.setSubOrderId("-1");
			} else {
				map.put(p.getOutTradeNo(), p);
			}
        }
		for (PaymentVo p : page.getList()) {
			if(p.getSubOrderId()!="-1"){
			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 
            //headers.add("操作日期");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getCreateTime()==null?"":DateUtils.formatDate(p.getCreateTime(),"yyyy-MM-dd")); //创建时间
	        //headers.add("操作");
            if(p.getPayType()==1){
            	row.createCell(col++).setCellValue("订单支付");
            }else if(p.getPayType()==4){
            	row.createCell(col++).setCellValue("团购单支付");
            }else if(p.getPayType()==5){
            	row.createCell(col++).setCellValue("换领单支付");
            }else if(p.getPayType()==11){
            	row.createCell(col++).setCellValue("现金券抵扣");
            }else if(p.getPayType()==2){
            	row.createCell(col++).setCellValue("现金券充值(买家)");
            }else if(p.getPayType()==3){
            	row.createCell(col++).setCellValue("现金账户充值(商家)");
            }else if(p.getPayType()==-1){
            	row.createCell(col++).setCellValue("订单取消");
            }else if(p.getPayType()==-2){
            	row.createCell(col++).setCellValue("售后退款");
            }else if(p.getPayType()==-3){
            	row.createCell(col++).setCellValue("团购退费");
            } else {
            	row.createCell(col++).setCellValue("");
            }
            
            //headers.add("订单类型");
            if(p.getOrderType()==null){
            	row.createCell(col++).setCellValue("");
            }else if(p.getOrderType()==1 || p.getOrderType()==4){
            	row.createCell(col++).setCellValue("团购单");
            }else if(p.getOrderType()==5){
            	row.createCell(col++).setCellValue("换领单");
            }else if(p.getOrderType()==0){
            	row.createCell(col++).setCellValue("普通订单");
            }else if(p.getOrderType()==-1){
            	row.createCell(col++).setCellValue("储值");
            } else {
            	row.createCell(col++).setCellValue("");
            }
            //headers.add("金额");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getTotalFee().doubleValue()); 
            //headers.add("支付方式");
            
            
            if("zhifubao".equals(p.getWay())) {
	            row.createCell(col++).setCellValue("支付宝"); //创建时间
            } else if("wxpay".equals(p.getWay()))  {
	            if("wxb62e121cbeffdddf".equals(p.getAppId())) {
		            row.createCell(col++).setCellValue("微信(公众号)"); //创建时间	
	            } else {
		            row.createCell(col++).setCellValue("微信(app)"); //创建时间
	            }
            } else if("wxgz".equals(p.getWay()))  {
	            row.createCell(col++).setCellValue("微信(公众号)"); //创建时间
            } else if("wxapp".equals(p.getWay()))  {
	            row.createCell(col++).setCellValue("微信(app)"); //创建时间
            } else if("unionpay".equals(p.getWay()))  {
	            row.createCell(col++).setCellValue("银联"); //创建时间
            } else if("pingtaiyue".equals(p.getWay()))  {
	            row.createCell(col++).setCellValue("现金券"); //创建时间
            } else {
	            row.createCell(col++).setCellValue("");
            }
            //headers.add("支付流水号");
            if(p.getTradeNo()==null){
            	row.createCell(col++).setCellValue("");
            }else{
            	row.createCell(col++).setCellValue(p.getTradeNo().toString());
            }
            //headers.add("处理状态");
            if(p.getStatus()==null) {
                row.createCell(col++).setCellValue("");
            } else if(p.getStatus()==0) {
                row.createCell(col++).setCellValue("退款已申请");
            } else if(p.getStatus()==2) {
                row.createCell(col++).setCellValue("成功");
            } else {
                row.createCell(col++).setCellValue("");
            } 
            //headers.add("订单id");
            if(p.getSubOrderId()==null){
            	row.createCell(col++).setCellValue(p.getOrderId().toString()); 
            }else{
            	row.createCell(col++).setCellValue(p.getSubOrderId().toString()); 
            }
            
          //headers.add("商家名称");
            row.createCell(col++).setCellValue(p.getSupplierName());
            //headers.add("到款状态");
            if(p.getPayType()==-1){
            	if(1== p.getPayConfirm()){
            		 row.createCell(col++).setCellValue("已退款"); //创建时间
	            } else {
		            row.createCell(col++).setCellValue("未退款");
	            	}
            }else{
				 if(1 == p.getPayConfirm()) {
			            row.createCell(col++).setCellValue("已到款"); //创建时间
		            } else {
			            row.createCell(col++).setCellValue("未到款");
		            	}
					}
				}
		}
        // 第六步，将文件存到指定位置  
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ FileUtils.getFileNameForSave(request,"第三方支付一览"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		
		try {
			wb.write(response.getOutputStream());
			wb.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 订单支付入账导出
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "exportExcelOrderPayment")
	@ResponseBody
	public void exportExcelOrderPayment(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		
		formatParams(params, request,true);
		params.put("pageNum", 1);
		params.put("pageSize", 50000);
		
		params.put("noTest", "1");
		//PageInfo<SuborderOrderVo> page = orderService.getSuborderList(params);
		PageInfo<PaymentVo> page = paymentService.findPaymentList(params);
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("订单一览"); 
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        
        /**
         * 设置样式 start
         * */
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        /**设置样式  end
         * */
        List<String> headers= new ArrayList<String>();
        headers.add("订单号 ");
        headers.add("订单类型");
        headers.add("操作");
        headers.add("支付时间");
        headers.add("取消时间");
        headers.add("商家名称");
        headers.add("下单时间");
        headers.add("商品名称（标题） ");
        headers.add("内购券金额");
        headers.add("商品数量");
        headers.add("商品金额");
        headers.add("运费");
        //headers.add("现金券抵扣");
        //headers.add("抵扣流水号");
        //headers.add("应收金额");
        headers.add("操作金额");
        headers.add("支付方式");
        headers.add("支付流水号");
        headers.add("到款确认");

        /**
         * 
         * 设置订单详情表头 start
         * */
        HSSFRow row = sheet.createRow((int) 0); 
        for (int i = 0; i < headers.size(); i++) {
        	HSSFCell cell = row.createCell(i);
            //设置值
            cell.setCellValue(headers.get(i));  
            //设置样式
            cell.setCellStyle(style);
		}
        /** 设置订单详情表头 end
         * */
        int currentRow = 0;	

		Map<Long, Supplier> map = new HashMap<Long, Supplier>();
		
		for (PaymentVo p : page.getList()) {

			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 

    		Date createDate= null;
    		String supplierName= "";
    		StringBuilder productName= new StringBuilder();
    		BigDecimal ticcket= BigDecimal.ZERO;
    		int productCnt = 0;
    		BigDecimal amount= BigDecimal.ZERO;
    		BigDecimal shipping= BigDecimal.ZERO;
    		//BigDecimal cashPay= BigDecimal.ZERO;
    		//String cashNo="";
    		//BigDecimal needPay= BigDecimal.ZERO;
    		
            if(null==p.getOrderType() || 0==p.getOrderType()) {
            	//普通订单
        		if(StringUtils.isNullOrEmpty(p.getSubOrderId())){
        			//通过orderid查suborder
    				List<Suborder> subList=subOrderService.getsuborderIdByOrderId(p.getOrderId());
    				//suborder为list
    				boolean first=true;
    				for (Suborder sub : subList) {
    					if(first) {
    						createDate=sub.getCreateTime();
    						Supplier s = this.getSupplier(map, sub.getSupplierId());
    						if(s!=null) {
    							supplierName=s.getComName();
    						}
    						first=false;
    						//cashNo=sub.getCashNo();
    					}
    					
    					ticcket=ticcket.add(sub.getCompanyTicket());
    					List<Suborderitem> items = suborderitemDao.findBySubIdForView(sub.getSubOrderId());
    					for (Suborderitem suborderitem : items) {
    						productName.append(suborderitem.getProductName()).append(",");
    						productCnt +=suborderitem.getNumber();
    						amount=amount.add(suborderitem.getRealPay());
						}
    					shipping=shipping.add(sub.getTotalShipping());
    					//cashPay=cashPay.add(sub.getCashPay());
    				}
        		} else {
    				Suborder sub=subOrderService.getById(p.getSubOrderId());
					createDate=sub.getCreateTime();
					Supplier s = this.getSupplier(map, sub.getSupplierId());
					if(s!=null) {
						supplierName=s.getComName();
					}
					//cashNo=sub.getCashNo();
					ticcket=ticcket.add(sub.getCompanyTicket());
					List<Suborderitem> items = suborderitemDao.findBySubIdForView(sub.getSubOrderId());
					for (Suborderitem suborderitem : items) {
						productName.append(suborderitem.getProductName()).append(",");
						productCnt +=suborderitem.getNumber();
						amount=amount.add(suborderitem.getRealPay());
					}
					shipping=shipping.add(sub.getTotalShipping());
					//cashPay=cashPay.add(sub.getCashPay());
        		}
            } else if(1==p.getOrderType() || 4==p.getOrderType()) {
            	//团购单
            	if(StringUtils.isNullOrEmpty(p.getSubOrderId())){
        			//通过orderid查suborder
            		GroupOrders go = groupOrdersService.findById(p.getOrderId());
					shipping=go.getTotalShipping();
					
    				List<GroupSuborder> subList=groupOrdersService.findSuborderIdLikeOrderId(p.getOrderId());
    				//suborder为list
    				boolean first=true;
    				for (GroupSuborder sub : subList) {
    					if(first) {
    						createDate=sub.getCreateTime();
    						Supplier s = this.getSupplier(map, sub.getSupplierId());
    						if(s!=null) {
    							supplierName=s.getComName();
    						}
    						first=false;
    						//cashNo=sub.getCashNo();
    					}
    					
    					ticcket=ticcket.add(sub.getCompanyTicket());
    					List<GroupSuborderitem> items = groupOrdersService.findByItemsSubId(sub.getSubOrderId());
    					for (GroupSuborderitem suborderitem : items) {
    						productName.append(suborderitem.getProductName()).append(",");
    						productCnt +=suborderitem.getNumber();
    						amount=amount.add(suborderitem.getRealPay());
						}
    					//cashPay=cashPay.add(sub.getCashPay());
    				}
        		} else {
        			GroupSuborder sub=groupOrdersService.getBySubOrderId(p.getSubOrderId());
					createDate=sub.getCreateTime();
					Supplier s = this.getSupplier(map, sub.getSupplierId());
					if(s!=null) {
						supplierName=s.getComName();
					}
					//cashNo=sub.getCashNo();
					ticcket=ticcket.add(sub.getCompanyTicket());
					List<GroupSuborderitem> items = groupOrdersService.findByItemsSubId(sub.getSubOrderId());
					for (GroupSuborderitem suborderitem : items) {
						productName.append(suborderitem.getProductName()).append(",");
						productCnt +=suborderitem.getNumber();
						amount=amount.add(suborderitem.getRealPay());
					}

            		GroupOrders go = groupOrdersService.findById(Long.valueOf(p.getSubOrderId().substring(0,p.getSubOrderId().indexOf("-"))));
					shipping=go.getTotalShipping();
					//shipping=shipping.add(sub.getTotalShipping());
					//cashPay=cashPay.add(sub.getCashPay());
        		}
            } else if(5==p.getOrderType()) {
            	//换领单
            	//团购单
            	if(StringUtils.isNullOrEmpty(p.getSubOrderId())){
        			//通过orderid查suborder
    				ExchangeSuborder ex = new ExchangeSuborder();
    				ex.setOrderId(p.getOrderId());
    				List<ExchangeSuborder> subList = exchangeSuborderService.selectByModel(ex);
    				//suborder为list
    				boolean first=true;
    				for (ExchangeSuborder sub : subList) {
    					if(first) {
    						createDate=sub.getCreateTime();
    						Supplier s = this.getSupplier(map, sub.getSupplierId());
    						if(s!=null) {
    							supplierName=s.getComName();
    						}
    						first=false;
    						//cashNo=sub.getCashNo();
    					}
    					
    					ticcket=ticcket.add(sub.getCompanyTicket());
    					List<ExchangeSuborderitem> items = exchangeSuborderService.selectItems(sub.getSubOrderId());
    					for (ExchangeSuborderitem suborderitem : items) {
    						productName.append(suborderitem.getProductName()).append(",");
    						productCnt +=suborderitem.getNumber();
    						amount=amount.add(suborderitem.getRealPay());
						}
    					shipping=shipping.add(sub.getTotalShipping());
    					//cashPay=cashPay.add(sub.getCashPay());
    				}
        		} else {
        			ExchangeSuborder sub=exchangeSuborderService.getById(p.getSubOrderId());
					createDate=sub.getCreateTime();
					Supplier s = this.getSupplier(map, sub.getSupplierId());
					if(s!=null) {
						supplierName=s.getComName();
					}
					//cashNo=sub.getCashNo();
					ticcket=ticcket.add(sub.getCompanyTicket());
					List<ExchangeSuborderitem> items = exchangeSuborderService.selectItems(sub.getSubOrderId());
					for (ExchangeSuborderitem suborderitem : items) {
						productName.append(suborderitem.getProductName()).append(",");
						productCnt +=suborderitem.getNumber();
						amount=amount.add(suborderitem.getRealPay());
					}
					shipping=shipping.add(sub.getTotalShipping());
					//cashPay=cashPay.add(sub.getCashPay());
        		}
            	
            }
//			if (p.getPayType() <= -1) {
//				amount=p.getTotalFee().multiply(BigDecimal.valueOf(-1));
//				//cashPay=BigDecimal.ZERO;
//				shipping=BigDecimal.ZERO;
//			}
            //needPay = amount.add(shipping).subtract(cashPay);
			//headers.add("订单号 ");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(StringUtils.isEmpty(p.getSubOrderId())?""+p.getOrderId():p.getSubOrderId());
			//headers.add("订单类型");
            if(null==p.getOrderType() || 0==p.getOrderType()) {
            	row.createCell(col++).setCellValue("普通订单");
            } else if(1==p.getOrderType() || 4==p.getOrderType()) {
            	row.createCell(col++).setCellValue("团购单");
            } else if(5==p.getOrderType()) {
            	row.createCell(col++).setCellValue("换领单");
            } else {
            	row.createCell(col++).setCellValue("");
            }
	        //headers.add("操作");
            if(p.getPayType()==1){
            	row.createCell(col++).setCellValue("支付");
            }else if(p.getPayType()==4){
            	row.createCell(col++).setCellValue("支付");
            }else if(p.getPayType()==5){
            	row.createCell(col++).setCellValue("支付");
            }else if(p.getPayType()==11){
            	row.createCell(col++).setCellValue("现金券抵扣");
            }else if(p.getPayType()==2){
            	row.createCell(col++).setCellValue("现金券充值(买家)");
            }else if(p.getPayType()==3){
            	row.createCell(col++).setCellValue("现金账户充值(商家)");
            }else if(p.getPayType()==-1){
            	row.createCell(col++).setCellValue("订单取消");
            }else if(p.getPayType()==-2){
            	row.createCell(col++).setCellValue("售后退款");
            }else if(p.getPayType()==-3){
            	row.createCell(col++).setCellValue("团购退费");
            } else {
            	row.createCell(col++).setCellValue("");
            }
	        //headers.add("支付时间");
            //headers.add("取消时间");
            if(p.getPayType()!=-1) {
            	row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getCreateTime()==null?"":DateUtils.formatDate(p.getCreateTime(),"yyyy-MM-dd HH:mm"));
        		row.createCell(col++).setCellValue("");
        	} else {
        		row.createCell(col++).setCellValue("");
            	row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getCreateTime()==null?"":DateUtils.formatDate(p.getCreateTime(),"yyyy-MM-dd HH:mm"));
        	}
	        //headers.add("商家名称");
    		row.createCell(col++).setCellValue(supplierName);
            //headers.add("下单时间");
    		row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(createDate==null?"":DateUtils.formatDate(createDate,"yyyy-MM-dd HH:mm"));
        	//headers.add("商品名称（标题） ");
    		if(productName.length()>1) {
    			productName.delete(productName.length()-1, productName.length());
    		}
    		row.createCell(col++).setCellValue(productName.toString());
	        //headers.add("内购券金额");
    		FileUtils.createAndSetDoubleValue(row,col++,ticcket);
	        //headers.add("商品数量");
    		row.createCell(col++).setCellValue(productCnt);
	        //headers.add("商品金额");
    		FileUtils.createAndSetDoubleValue(row,col++,amount);
	        //headers.add("运费");
    		FileUtils.createAndSetDoubleValue(row,col++,shipping);
	        //headers.add("现金券抵扣");
            //createAndSetDoubleValue(row,col++,cashPay);
	        //headers.add("抵扣流水号");
        	//row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(cashNo);
	        //headers.add("应收金额");
            //createAndSetDoubleValue(row,col++,needPay);
	        //headers.add("实收金额");
    		BigDecimal flg = BigDecimal.ONE;
    		if(p.getPayType()==-1){
    			flg = new BigDecimal("-1");
    		}else if(p.getPayType()==-2){
    			flg = new BigDecimal("-1");
        	}else if(p.getPayType()==-3){
        		flg = new BigDecimal("-1");
        	}
    		FileUtils.createAndSetDoubleValue(row,col++,p.getTotalFee().multiply(flg));
            //headers.add("支付方式");
            if("zhifubao".equals(p.getWay())) {
	            row.createCell(col++).setCellValue("支付宝"); //创建时间
            } else if("wxpay".equals(p.getWay()))  {
	            if("wxb62e121cbeffdddf".equals(p.getAppId())) {
		            row.createCell(col++).setCellValue("微信(公众号)"); //创建时间	
	            } else {
		            row.createCell(col++).setCellValue("微信(app)"); //创建时间
	            }
            } else if("wxgz".equals(p.getWay()))  {
	            row.createCell(col++).setCellValue("微信(公众号)"); //创建时间
            } else if("wxapp".equals(p.getWay()))  {
	            row.createCell(col++).setCellValue("微信(app)"); //创建时间
            } else if("unionpay".equals(p.getWay()))  {
	            row.createCell(col++).setCellValue("银联"); //创建时间
            } else if("pingtaiyue".equals(p.getWay()))  {
	            row.createCell(col++).setCellValue("现金券"); //创建时间
            } else {
	            row.createCell(col++).setCellValue("");
            }
	        //headers.add("支付流水号");
        	row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getTradeNo());
	        //headers.add("到款确认");
			if (p.getPayType() == -1 || p.getPayType() == -2 || p.getPayType() == -3) {
				if (1 == p.getPayConfirm()) {
					row.createCell(col++).setCellValue("已退款"); // 创建时间
				} else {
					row.createCell(col++).setCellValue("未退款");
				}
			} else {
				if (1 == p.getPayConfirm()) {
					row.createCell(col++).setCellValue("已到款"); // 创建时间
				} else {
					row.createCell(col++).setCellValue("未到款");
				}
			}
		}
				
        // 第六步，将文件存到指定位置  
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ FileUtils.getFileNameForSave(request,"订单一览"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		
		try {
			wb.write(response.getOutputStream());
			wb.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	private Supplier getSupplier(Map<Long, Supplier> map, Long supplierId) {
		if (!map.containsKey(supplierId)) {
			Supplier s = supplierService.findByid(supplierId);
			if (s != null) {
				map.put(supplierId, s);
			}
		}
		return map.get(supplierId);
	}
}

