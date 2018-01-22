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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.SpecificationValue;
import com.wode.factory.service.SpecificationService;
import com.wode.factory.vo.SpecificationVo;

@Controller
@RequestMapping("specification")
public class SpecificationController{
	@Autowired
	private SpecificationService specificationService;
	@RequestMapping
	public String toSpecification() {
		return "manager/specification";
	}
	@RequestMapping("specificationInfo")
	public String specificationInfo(Model model,Integer pageNum,Integer pageSize,Long categoryId){
		PageInfo<SpecificationVo> pageInfo = this.specificationService.selectSpecification(pageNum, pageSize,categoryId);
		model.addAttribute("pageInfo", pageInfo);
		return "manager/specification-list";
	}
	
	@RequestMapping("del")
	public @ResponseBody Integer deleteSpecificationInfo(Model model,Long id){
		return this.specificationService.deleteById(id);
	}
	
	@RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
	public String showlayer(Model m,@PathVariable("mode") String mode,Long id){
		if("edit".equals(mode)){
			SpecificationVo speVo = this.specificationService.selectById(id);
			m.addAttribute("speVoInfo", speVo);
			return "manager/specification-update";
		}else if("children".equals(mode)){
			List<SpecificationValue> listSpeValue = this.specificationService.selectSpecificationValue(id);
			m.addAttribute("speValue", listSpeValue);
			m.addAttribute("speId", id);
				
			return "manager/specification-detail";
		}else{
			return null;
		}
	}
	
	@RequestMapping("update")
	public @ResponseBody Integer update(Model model,SpecificationVo speVo){
		
		return this.specificationService.updateSpecificationVo(speVo);
	}
	
	
	@RequestMapping("save")
	public @ResponseBody Integer insertSpecification(SpecificationVo speVo){
		return this.specificationService.insertSpecificationVo(speVo);
	}
	/**
	 * 功能说明：
	 * 日期:	2015年7月29日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param add  orders-name 数组，用逗号隔开
	 * @param update  id-orders-name 数组，用逗号隔开
	 * @param del  id数组用逗号隔开
	 * @param speId   规格id
	 * @return
	 */
	@RequestMapping("saveSpeValue")
	public @ResponseBody Integer save(String[] add,String[] update,String[] del,Long speId){
		
		return this.specificationService.updateSpecificationValue(add, update, del,speId);
	}
}

