package com.wode.api.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserShare;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.service.UserShareService;
import com.wode.factory.user.service.UserShareTicketService;
import com.wode.factory.user.util.Constant;

/**
 * 2015-6-17
 *
 * @author 谷子夜
 */
@Controller
@RequestMapping("/autoTicket")
@ResponseBody
public class AutoTicketController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	UserShareService userShareService;

	@Autowired
	private UserShareTicketService userShareTicketService;

	@RequestMapping("/share{shareId}.user")
	@ResponseBody
	public ModelAndView exchange(HttpServletRequest request, @PathVariable Long shareId,String ticketType,Long autoBuyId) {
		ModelAndView result = new ModelAndView();

		String moreLink = "";
		String errMsg = "";

		UserShare vo = userShareService.getById(shareId);
		moreLink = "http://"+Constant.SYSTEM_DOMAIN+"/huanling.html";
		if(vo!=null && !StringUtils.isEmpty(vo.getTargetActionUrl())) {
			moreLink = vo.getTargetActionUrl();
		}
		if(vo==null || !vo.getUserId().equals(loginUser.getSupplierId())) {
			// 非该企业员工 不进行自动购买，跳转活动页
			result.setViewName("redirect:"+moreLink);
	 		return result;
		}
		// 自动发放换领币
		ActResult<Map<String,Object>> rnt= userShareTicketService.autoTicket(vo.getId(), this.userService.getEmpById(loginUser.getId()));
		BigDecimal amount=(BigDecimal)rnt.getData().get("amount");
		if(rnt.isSuccess()) {
			// 取成功
			errMsg ="";			
		} else {
			if(NumberUtil.isGreaterZero(amount)) {
				// 已经领取
				errMsg ="again";
			} else {
				errMsg ="换领币不存在或已过期";
			}
		}
		result.addObject("moreLink", moreLink);
		result.addObject("companyName", vo.getUserNick());
		result.addObject("amount", amount);
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		String limits = String.format("有效日期：%s - %s", df.format(rnt.getData().get("limitStart")), df.format(rnt.getData().get("limitEnd")));
		result.addObject("limits", limits);
		result.addObject("errMsg", errMsg);
		result.setViewName("autoBuy/get_exchange_result");
 		return result;
	}
}
