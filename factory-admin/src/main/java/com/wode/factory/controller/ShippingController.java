/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.wode.common.constant.Constant;
import com.wode.common.db.DBUtils;
import com.wode.common.util.ActResult;
import com.wode.factory.model.CheckOpinion;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.model.ShippingTemplate;
import com.wode.factory.model.ShippingTemplateRule;
import com.wode.factory.service.ShippingFreeRuleService;
import com.wode.factory.service.ShippingTemplateRuleService;
import com.wode.factory.service.ShippingTemplateService;
import com.wode.factory.service.SupplierService;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysUser;

@Controller
@RequestMapping("shipping")
public class ShippingController {
	
	@Autowired
	private ShippingTemplateService shippingTemplateService;
	
	@Autowired
	private ShippingTemplateRuleService shippingTemplateRuleService;
	
	@Autowired
	private ShippingFreeRuleService shippingFreeRuleService;
	
	@Resource
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private DBUtils dbUtils;
	
	@RequestMapping("baseCheck")
	public String toPageCheck(Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("officeId", 0);
		params.put("officeType", "");
		params.put("roles", "-108,-111");
		params.put("pageNum", 1);
		params.put("pageSize", 150);

		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		
		model.addAttribute("mlist", list);
		return "manager/shipping/shipping-check-base";
	}
	

	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "shippinglist", method = RequestMethod.POST)
	public String shippinglist(@RequestParam Map<String, Object> params, Model model,HttpSession session,HttpServletRequest request) {

		model.addAttribute("page", shippingTemplateService.findPage(params));
		return "manager/shipping/shipping-list";
	}
	
	
	@RequestMapping(value = "shippingView")
	public String shippingView(Long id,Model model,HttpSession session,HttpServletRequest request) {

		ShippingTemplate shippingTemplate = new ShippingTemplate();
		shippingTemplate.setId(id);
		ShippingTemplate  newShipping = shippingTemplateService.selectByModel(shippingTemplate).get(0);
		model.addAttribute("newShipping",newShipping);
		shippingTemplate = new ShippingTemplate();
		shippingTemplate.setVersion(2);
		shippingTemplate.setSupplierId(newShipping.getSupplierId());
		shippingTemplate.setIsAudit(0);
		List<ShippingTemplate> olds = shippingTemplateService.selectByModel(shippingTemplate);
		ShippingTemplate  oldShipping =null;
		if(olds!=null && !olds.isEmpty()) {
			oldShipping=olds.get(0);
		}
		
		//取新旧数据
		ShippingFreeRule shippingFreeRule = new ShippingFreeRule();
		shippingFreeRule.setTemplateId(newShipping.getId());
		model.addAttribute("newshippingFreeRule",shippingFreeRuleService.selectByModel(shippingFreeRule));
		if(oldShipping!=null) {
			shippingFreeRule.setTemplateId(oldShipping.getId());
		} else {
			shippingFreeRule.setTemplateId(-1L);
		}
		model.addAttribute("oldshippingFreeRule",shippingFreeRuleService.selectByModel(shippingFreeRule));
		//取新旧数据
		ShippingTemplateRule shippingTemplateRule = new ShippingTemplateRule();
		shippingTemplateRule.setTemplateId(newShipping.getId());
		model.addAttribute("newshippingTemplateRule",shippingTemplateRuleService.selectByModel(shippingTemplateRule));
		if(oldShipping!=null) {
			shippingTemplateRule.setTemplateId(oldShipping.getId());
		} else {
			shippingTemplateRule.setTemplateId(-1L);
		}
		model.addAttribute("oldshippingTemplateRule",shippingTemplateRuleService.selectByModel(shippingTemplateRule));
		
		return "manager/shipping/shippingView";
	}
	
	@RequestMapping(value = "shippingAudit")
	@ResponseBody
	public ActResult<String> shippingAudit(Long id,Integer isAudit,String opinion, Model model,HttpSession session,HttpServletRequest request) {

		ShippingTemplate shippingTemplate = new ShippingTemplate();
		shippingTemplate.setId(id);
		ShippingTemplate  newShipping = shippingTemplateService.selectByModel(shippingTemplate).get(0);
		
		if (isAudit == 0) {
			shippingTemplate = new ShippingTemplate();
			shippingTemplate.setVersion(2);
			shippingTemplate.setSupplierId(newShipping.getSupplierId());
			shippingTemplate.setIsAudit(0);
			List<ShippingTemplate> oldShippingList = shippingTemplateService.selectByModel(shippingTemplate);
			if(oldShippingList != null && oldShippingList.size() > 0) {
				ShippingTemplate oldShipping = oldShippingList.get(0);
				// 删除旧数据
				shippingTemplateService.delete(oldShipping.getId());
				shippingTemplateRuleService.deleteByTemplateId(oldShipping.getId());
				shippingFreeRuleService.deleteByTemplateId(oldShipping.getId());
			}
			// 更新新模板
			newShipping.setIsAudit(0);
			shippingTemplateService.update(newShipping);
		} else if (isAudit == 2) {
			// 更新新模板
			newShipping.setIsAudit(2);
			shippingTemplateService.update(newShipping);
		}
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		//增加审核记录
		//记录审核表
		CheckOpinion co = new CheckOpinion();
		co.setId(dbUtils.CreateID());
		co.setUsername(user.getName());
		co.setCheckId(newShipping.getId());
		co.setResult(newShipping.getIsAudit());
		co.setOpinion(opinion);
		co.setTime(new Date());
		co.setType(0);
		co.setUserId(user.getId());
		supplierService.saveCheckOpinion(co);
		
		return ActResult.success("");
	}
}

