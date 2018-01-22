/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wode.common.frame.base.BaseSpringController;
import com.wode.factory.supplier.service.CurrencyService;

@Controller
@RequestMapping("currency")
public class CurrencyController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("currencyService")
	private CurrencyService currencyService;
	
	private final String LIST_ACTION = "redirect:/currency/list.html";
	
	public CurrencyController() {
	}

	
}

