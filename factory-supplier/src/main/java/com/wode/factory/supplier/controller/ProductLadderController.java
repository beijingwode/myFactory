package com.wode.factory.supplier.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.stereotype.Token;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.supplier.service.ProductLadderService;

@Controller
@RequestMapping("productLadder")
public class ProductLadderController extends BaseSpringController {
	
	@Autowired
	@Qualifier("productLadderService")
	private ProductLadderService productLadderService;

	@RequestMapping(value = "ajaxGetLadder", method = RequestMethod.GET)
	@Token(remove = true)
	public ModelMap ajaxGetLadder(HttpServletRequest request,ModelMap model, Long productId){
		List<ProductLadder> ls = productLadderService.getlistByProductid(productId);
		
		if(ls==null || ls.isEmpty()) { 
			model.addAttribute("data", null);
		}else{
			model.addAttribute("data", ls);
		}
		return model;
	}
}
