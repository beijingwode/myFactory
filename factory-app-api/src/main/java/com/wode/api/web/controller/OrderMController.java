package com.wode.api.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.model.Returnorder;
import com.wode.factory.user.service.ExpressComService;
import com.wode.factory.user.service.ReturnorderService;
@Controller
@RequestMapping("orderM")
public class OrderMController {
	@Autowired
	private ExpressComService expressComService;
	
	@Autowired
	private ReturnorderService returnorderService;
	
	@RequestMapping()
	public ModelAndView page(String subOrderId,ModelAndView model,HttpServletRequest request){
		model.addObject("subOrderId", subOrderId);
		model.setViewName("order_details");
		return model;
	}
	
	@RequestMapping("/applyReturn")
	public ModelAndView applyReturn(String subOrderId,String realPrice,String returnOrderId, String refundOrderId,ModelAndView model,HttpServletRequest request){
		model.addObject("subOrderId", subOrderId);
		model.addObject("returnOrderId", returnOrderId);
		model.addObject("refundOrderId", refundOrderId);
		model.addObject("realPrice", realPrice);
		if(!StringUtils.isEmpty(returnOrderId)){
			Returnorder returnorder = returnorderService.getReturnOrdersById(Long.valueOf(returnOrderId));
			if(returnorder!=null){
				model.addObject("returnStatus", returnorder.getStatus());
			}
		}
		//Map<String, ExpressCompany> map_e = expressComService.getExpressCompanys();
		//model.addObject("expressCompanys", map_e.values());
		model.setViewName("after_sales");
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getExpressCompany.user")
	@ResponseBody
	public ActResult<BigDecimal> getExpressCompany(HttpServletRequest request) {
		Map<String, ExpressCompany> map_e = expressComService.getExpressCompanys();
		List<String> removeList= new ArrayList<String>(); 
		for (ExpressCompany expressCompany : map_e.values()) {
			if("14660000000000000".equals(expressCompany.getId()) || "14660000000000001".equals(expressCompany.getId())){//电子卡券或者厂家直送
				removeList.add(expressCompany.getId());
			}
		}
		for (String string : removeList) {
			map_e.remove(string);
		}
		//model.addObject("expressCompanys", map_e.values());
		return ActResult.success(map_e.values());
	}
	
	@RequestMapping("/after_SaleDetails")
	public ModelAndView afterSaleDetail(String subOrderId,ModelAndView model,HttpServletRequest request){
		model.addObject("subOrderId", subOrderId);
		model.setViewName("after_salesDetails");
		return model;
	}
}
