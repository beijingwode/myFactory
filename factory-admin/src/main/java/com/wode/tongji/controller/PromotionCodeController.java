/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.tongji.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.frame.base.BaseSpringController;
import com.wode.factory.model.PromotionCode;
import com.wode.tongji.service.PromotionCodeService;

@Controller
@RequestMapping("promotionCode")
public class PromotionCodeController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("promotionCodeService")
	private PromotionCodeService promotionCodeService;
	
	private final String LIST_ACTION = "redirect:/promotionCode/list.html";
	
	public PromotionCodeController() {
	}

	@RequestMapping
	public String toHtml(ModelMap model) {
		PromotionCode pc = new PromotionCode();
		int all =  promotionCodeService.findCount(null);
		pc.setStatus(1);
		int used =  promotionCodeService.findCount(pc);
		model.addAttribute("all", all);
		model.addAttribute("used", used);
		model.addAttribute("noUse", all-used);
		return "manager/promotionCode";
	}
	
	/** 
	 * 执行搜索 
	 **/
	@RequestMapping(value="findList")
	@ResponseBody
	public List<PromotionCode> list(int count) {
		return promotionCodeService.extract(count,0);
	}
	
	/** 
	 * 执行搜索 
	 **/
	@RequestMapping(value="toCodeCount")
	public String toCodeCount(String noUse,Model model) {
		model.addAttribute("noUse", noUse);
		return "manager/codeCount";
	}
	
	@RequestMapping(value="batchInsert")
	@ResponseBody
	public String batchInsert(String noUse,Model model) {
		promotionCodeService.batchInsert();
		return "";
	}
	
	
}

