package com.wode.api.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/productm")
public class ProductDetailMController {
	
	@RequestMapping()
    public ModelAndView page(Long productId, Long specificationsId,Integer quantity,Long userId,String pageKey,String pageStock, String from,String app,ModelAndView model,HttpServletRequest request) {
		
		model.addObject("productId", productId);
		model.addObject("specificationsId", specificationsId);	
		model.addObject("quantity", (quantity==null||quantity<=0)?1:quantity);
		model.addObject("userId", userId);
		model.addObject("from", from);
		model.addObject("app", app);
		model.addObject("pageKey", pageKey);//表示从页面进入的
		model.addObject("pageStock", pageStock);//表示该页面下的商品库存
		model.setViewName("product_m_v2");
		//model.setViewName("product_m");
		return model;
	}
}
