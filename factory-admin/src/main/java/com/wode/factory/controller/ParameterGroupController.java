/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.Parameter;
import com.wode.factory.service.ParameterGroupService;
import com.wode.factory.vo.ParameterGroupVo;

@Controller
@RequestMapping("parameterGroup")
public class ParameterGroupController{
	@Autowired
	private ParameterGroupService parameterGroupService;
//	@Autowired
//	private SpecificationService specificationService;
	@RequestMapping
	public String toParameterGroup() {
		return "manager/parameterGroup";
	}
	@RequestMapping("parameterGroupInfo")
	public String ParameterGroup(Model model,Integer pageNum,Integer pageSize,Long categoryId){
		PageInfo<ParameterGroupVo> pageInfo = this.parameterGroupService.selectParameterGroup(pageNum, pageSize, categoryId);
		model.addAttribute("pageInfo", pageInfo);
		return "manager/parameterGroup-list";
	}
	
	@RequestMapping("del")
	public @ResponseBody Integer deleteParameterGroupInfo(Model model,Long id){
		return parameterGroupService.deleteById(id);
	}
	
	@RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
	public String showlayer(Model m,@PathVariable("mode") String mode,Long id){
		if("edit".equals(mode)){
			ParameterGroupVo parVo = this.parameterGroupService.selectById(id);
			m.addAttribute("parVoInfo", parVo);
			return "manager/parameterGroup-update";
		}else if("children".equals(mode)){
//			List<SpecificationValue> listSpeValue = this.specificationService.selectSpecificationValue(id);
//			m.addAttribute("speValue", listSpeValue);
//			if(!listSpeValue.isEmpty())
//				m.addAttribute("speId", listSpeValue.get(0).getSpecificationId());
				
			return "manager/parameterGroup-detail";
		}else{
			return null;
		}
	}

	
	@RequestMapping(value = "showlayer", method = RequestMethod.POST)
	public String showlayer(Model m,Long id){
		List<Parameter> ListParam = parameterGroupService.selectParamValue(id);
		m.addAttribute("paramList",parameterGroupService.selectParamValue(id));
		m.addAttribute("parameterGroupId", id);
		return "manager/parameter-list";
	}
	
	@RequestMapping("update")
	public @ResponseBody Integer update(Model model,ParameterGroupVo par){
		
		return this.parameterGroupService.updateParameterGroupVo(par);
	}
	
	
	@RequestMapping("save")
	public @ResponseBody Integer insertPageTypeSetting(ParameterGroupVo proVo){
		return this.parameterGroupService.insertParameterGroupVo(proVo);
	}
	
	@RequestMapping("parameter/save")
	public @ResponseBody Integer parameterSave(@RequestBody List<Parameter> listParam){
		return this.parameterGroupService.paramBatchAdd(listParam);
	}
	
	@RequestMapping("parameter/doEdit")
	public @ResponseBody Integer parameterDoEdit(@RequestBody List<Parameter> listParam){
		return this.parameterGroupService.paramBatchUpdate(listParam);
	}
	
	@RequestMapping("parameter/del")
	public @ResponseBody Integer paramDel(@RequestBody List<String> ids){
		return parameterGroupService.paramBatchDel(ids);
	}
	
//	/**
//	 * 功能说明：
//	 * 日期:	2015年7月29日
//	 * 开发者:张晨旭
//	 * 版本号:1.0
//	 *
//	 * @param add  orders-name 数组，用逗号隔开
//	 * @param update  id-orders-name 数组，用逗号隔开
//	 * @param del  id数组用逗号隔开
//	 * @param speId   规格id
//	 * @return
//	 */
//	@RequestMapping("saveSpeValue")
//	public @ResponseBody Integer save(String[] add,String[] update,String[] del,Long speId){
//		
//		return this.specificationService.updateSpecificationValue(add, update, del,speId);
//	}
}

