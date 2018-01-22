/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.result.Result;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.Returnorder;
import com.wode.factory.model.Suborder;
import com.wode.factory.supplier.facade.OrderRefundFacade;
import com.wode.factory.supplier.facade.OrderReturnFacade;
import com.wode.factory.supplier.service.RefundorderService;
import com.wode.factory.supplier.service.ReturnorderService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SuborderService;
import com.wode.factory.supplier.util.UserInterceptor;

@Controller
@RequestMapping("returnorder")
public class ReturnorderController extends BaseSpringController {
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;
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
	@Qualifier("shopService")
	private ShopService shopService;
	
	@Autowired
	private OrderReturnFacade orderReturnFacade;


	public ReturnorderController() {

	}

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}


	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "returncheck")
	public ModelAndView returncheck(HttpServletRequest request, HttpServletResponse response) {
		String returnOrderId = request.getParameter("returnOrderId");
		String subOrderId = request.getParameter("subOrderId");
		String refundOrderId = request.getParameter("refundOrderId");
		String userId = request.getParameter("userId");
		Result result = new Result();
		ActResult<String> ar = new ActResult<String>();
		ModelAndView mv = new ModelAndView();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		//用户是否为空
		if (userModel == null) {
			//System.out.println("\n 空降中心--------------失败-------------" );
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000");
		} else {
			boolean checkResult = false;
			//退款单
			Refundorder refundorder = refundorderService.getById(Long.parseLong(refundOrderId));
			//退货单
			Returnorder returnorder = null;

			//调用退款接口
			//if(!StringUtils.isNullOrEmpty(refundorder)&&refundorder.getStatus()==2){
			//暂时忽视退款 申请中状态， 2015-11-26 gaoyj
			if (!StringUtils.isNullOrEmpty(refundorder) && refundorder.getStatus() == 1) {
				if (!StringUtils.isNullOrEmpty(returnOrderId)) {
					returnorder = returnorderService.getById(refundorder.getReturnOrderId());

					//if(!StringUtils.isNullOrEmpty(returnorder) && returnorder.getStatus()==1){
					//暂时忽视退货成功状态， 2015-11-26 gaoyj
					if (!StringUtils.isNullOrEmpty(returnorder) && returnorder.getStatus() == 0) {
						if (!StringUtils.isNullOrEmpty(userId) && StringUtils.isEquals(returnorder.getUserId(), userId)) {
							checkResult = true;
						} else {
							ar.setSuccess(false);
							ar.setMsg("退货单中UserId与传入参数userId不匹配");
						}
					} else {
						ar.setSuccess(false);
						ar.setMsg("returnOrderId：" + refundorder.getReturnOrderId() + " 在退款表中不存在匹配数据");
					}
				} else {
					checkResult = true;
				}
			} else {
				ar.setSuccess(false);
				ar.setMsg("refundOrderId：" + refundorder.getReturnOrderId() + " 在退款表中不存在匹配数据");
			}

			//检查Ok
			if (checkResult) {
				Suborder suborder = suborderService.getById(subOrderId);
				if (suborder == null) {
					ar.setSuccess(false);
					ar.setMsg("subOrderId：" + refundorder.getReturnOrderId() + " 在订单表中不存在匹配数据");

				} else {
					try {
						Long customerId = refundorder.getUserId() != null ? refundorder.getUserId() : returnorder.getUserId();
						ar = orderRefundFacade.refundToUser(refundorder, returnorder, suborder, customerId, userModel.getUserName(), null);
					} catch (Exception e) {
						ar.setSuccess(false);
						ar.setMsg("系统异常");
					}
				}
			}

			//TODO 处理结果通知
			//判断退款是否成功
//			if(ar.isSuccess()){
//				
//				if(!StringUtils.isNullOrEmpty(suborder)){
//					suborder.setStatus(11);
//					suborderService.update(suborder);
//				}
//			}
			mv.setViewName("redirect:/suborder/toOrderDetail.html?subOrderId=" + subOrderId + "");
		}
		return mv;
	}


	@RequestMapping(value = "ajaxRefuse")
	public ModelAndView ajaxRefuse(HttpServletRequest request, HttpServletResponse response) {
		String refundOrderId = request.getParameter("refundOrderId");
		String refuseNote = request.getParameter("refuseNote");
		String subOrderId = request.getParameter("subOrderId");
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		//用户是否为空
		if (userModel == null) {
			//System.out.println("\n 空降中心--------------失败-------------" );
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000");
		} else {
			//退款单
			Refundorder refundorder = refundorderService.getById(Long.parseLong(refundOrderId));
			//退货单
			Returnorder returnorder = null;
			if (!StringUtils.isNullOrEmpty(refundorder.getReturnOrderId())) {
				returnorder = returnorderService.getById(refundorder.getReturnOrderId());
			}

			 orderRefundFacade.refuseApply(refundorder, returnorder, suborderService.getById(subOrderId), refuseNote, userModel.getUserName());
		}
		//TODO 处理结果通知
		return mv;
	}

	/**
	 * 同意退货退款
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deal")
	public ActResult<String> deal(Long returnOrderId, Long refundOrderId, Integer action, Integer type, String reason,String returnedAddress,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request, shopService);
		// 用户是否为空
		if (userModel == null) {
			ActResult<String> ar = new ActResult<String>();
			ar.setSuccess(false);
			ar.setMsg("未登录");
			return ar;
		} else {
			// action 1 同意 2 拒绝 type 1.退货 2.退款3.退货收到并直接退款
			return orderReturnFacade.deal(returnOrderId, refundOrderId, action, type, reason,userModel.getUserName(),returnedAddress);
		}
	}
	/**
	 * 商家签收退货单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateGoodsStatus")
	public ActResult<String> deal(Long returnOrderId,HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request, shopService);
		// 用户是否为空
		ActResult<String> ar = new ActResult<String>();
		if (userModel == null) {
			ar.setSuccess(false);
			ar.setMsg("未登录");
		} else {
			Returnorder returnorder = returnorderService.getById(returnOrderId);
			if(returnorder!=null){
				if(returnorder.getGoodsStatus()==null ||returnorder.getGoodsStatus().equals(0)){
					returnorder.setGoodsStatus(1);
					returnorderService.update(returnorder);
				}
				ar.setSuccess(true);
			}else{
				ar.setSuccess(false);
				ar.setMsg("查询不到该退货单");
			}
		}
		return ar;
	}
}
