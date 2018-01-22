package com.wode.factory.controller;

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
import com.wode.factory.model.Attribute;
import com.wode.factory.model.AttributeOption;
import com.wode.factory.service.AttributeService;
import com.wode.factory.vo.AttributeVo;

@Controller
@RequestMapping("attribute")
public class AttibuteController {

	@Resource
	private AttributeService attributeService;
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Integer save(@ModelAttribute Attribute pojo) {
		pojo.setCreateDate(new Date());
		pojo.setUpdateDate(new Date());
	    return attributeService.add(pojo);
	}
	
	@RequestMapping(value = "del", method = RequestMethod.POST)
	@ResponseBody
	public Integer del(Long id) {
		return attributeService.delete(id);
		
	}
	
	/**
	 * 跳转到位置管理页
	* @return
	 */
	@RequestMapping
	public String toPageAttr(Model model){
		return "manager/attribute";
	}
	
	
	/**
	 * 属性列表
	* @param params
	* @param model
	* @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		PageInfo<AttributeVo> page = attributeService.findList(params);
		model.addAttribute("page", page);
		return "manager/attribute-list";
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
	public String toEdit(Long id, Model model){
		AttributeVo attribute = attributeService.getById(id);
		model.addAttribute("Attribute", attribute);
		return "manager/attribute-save";
	}
	
	
	@RequestMapping(value="doEdit",method=RequestMethod.POST)
	public @ResponseBody Integer doEdit(@ModelAttribute Attribute pojo){
		pojo.setUpdateDate(new Date());
		return attributeService.update(pojo);
	}
	
	@RequestMapping(value = "showlayer", method = RequestMethod.POST)
	public String showlayer(Model m,Long id){
		m.addAttribute("attrValue",attributeService.selectAttrValue(id));
		m.addAttribute("attributeId", id);
		return "manager/AttributeOption-list";
	}
	
	@RequestMapping(value = "attrOption/save", method = RequestMethod.POST)
	@ResponseBody
	public Integer attrOptionSave(@RequestBody List<AttributeOption> listOption) {
	    return attributeService.optionAdd(listOption);
	}
	
	/**
	 * 
	 * 功能说明：删除属性项
	 * 日期:	2015年7月29日
	 * 开发者:宋艳垒
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "attrOption/del", method = RequestMethod.POST)
	@ResponseBody
	public Integer attrOptionDel(@RequestBody List<String> ids) {
		return attributeService.optionDelete(ids);
	}
	
	/**
	 * 
	 * 功能说明：更新属性项
	 * 日期:	2015年6月23日
	 * 开发者:宋艳垒
	 * @param pojo
	 * @return
	 */
	@RequestMapping(value="attrOption/doEdit",method=RequestMethod.POST)
	public @ResponseBody Integer doOptionEdit(@RequestBody List<AttributeOption> listPojo){
		return attributeService.optionUpdate(listPojo);
	}
	
}
