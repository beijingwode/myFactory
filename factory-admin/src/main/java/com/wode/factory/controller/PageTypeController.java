package com.wode.factory.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.PageTypeSetting;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.service.PageTypeService;
import com.wode.factory.service.ProductCategoryService;
import com.wode.sys.service.SysRoleService;
import com.wode.sys.service.SysUserService;

@Controller
@RequestMapping("pageType")
public class PageTypeController {

	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private PageTypeService pageTypeService;
	
	@Resource
	private ProductCategoryService productCategoryService;
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Integer save(@ModelAttribute PageTypeSetting pojo) {
		pojo.setCreateDate(new Date());
		pojo.setUpdateDate(new Date());
	    return pageTypeService.add(pojo);
	}
	
	@RequestMapping(value = "del", method = RequestMethod.POST)
	@ResponseBody
	public Integer del(Long pageTypeId) {
		return pageTypeService.delete(pageTypeId);
		 
	}
	
	/**
	 * 跳转到位置管理页
	* @return
	 */
	@RequestMapping
	public String toPageType(Model model) {
		List<ProductCategory> list = new ArrayList<ProductCategory>();
		ProductCategory pc = new ProductCategory();
		pc.setId(1l);
		pc.setName("商城页");
		list.add(0, pc);
		model.addAttribute("listCategory", list);
		return "manager/pageType";
	}
	
	
	/**
	 * 位置列表
	* @param params
	* @param model
	* @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		PageInfo<PageTypeSetting> page = pageTypeService.findTypeList(params);
		model.addAttribute("page", page);
		return "manager/pageType-list";
	}
	
	/**
	 * 
	 * 功能说明：跳转到修改页面
	 * 日期:	2015年6月23日
	 * 开发者:宋艳垒
	 *
	 * @param pageTypeId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toEdit",method=RequestMethod.POST)
	public String toEdit(Long pageTypeId, Model model){
		PageTypeSetting pageType = pageTypeService.getById(pageTypeId);
		model.addAttribute("pageType", pageType);
		return "manager/pageType-save";
	}
	
	/**
	 * 
	 * 功能说明：更新位置信息
	 * 日期:	2015年6月23日
	 * 开发者:宋艳垒
	 *
	 * @param pojo
	 * @return
	 */
	@RequestMapping(value="doEdit",method=RequestMethod.POST)
	public @ResponseBody Integer doEdit(@ModelAttribute PageTypeSetting pojo){
		pojo.setUpdateDate(new Date());
		return pageTypeService.update(pojo);
	}
	
	@RequestMapping(value = "getPageTypes", method = RequestMethod.POST)
	@ResponseBody
	public List<PageTypeSetting> getPageTypes(@RequestBody PageTypeSetting data){
		return this.pageTypeService.selectPageType(data);
	}
	
}
