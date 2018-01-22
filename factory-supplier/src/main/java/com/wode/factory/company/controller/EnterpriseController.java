/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.company.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.util.JsonUtil;
import com.wode.factory.company.query.EnterpriseStructureVo;
import com.wode.factory.company.query.EnterpriseVo;
import com.wode.factory.company.service.EntParamCodeService;
import com.wode.factory.company.service.EnterpriseService;
import com.wode.factory.company.service.EnterpriseStructureService;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.Enterprise;
import com.wode.factory.model.EnterpriseStructure;
import com.wode.factory.model.Supplier;
import com.wode.factory.supplier.service.SupplierService;

@Controller
@RequestMapping("company/enterprise")
public class EnterpriseController extends BaseCompanyController{
	
	@Autowired
	EnterpriseService enterpriseService;

	@Autowired
	EnterpriseStructureService enterpriseStructureService;
	
	@Autowired
	EntParamCodeService entParamCodeService;
	
	@Autowired
	SupplierService supplierService;
	/**
	 * 修改(添加)企业信息
	 * @param request
	 * @param response
	 * @param ent
	 * @return
	 */
	@RequestMapping(value="saveOrUpdate")
	@ResponseBody
	public Integer saveOrUpdateEnterprise(HttpServletRequest request,HttpServletResponse response,Enterprise ent){
		Integer i = this.enterpriseService.updateOrInsertEnterprise(ent, getSupplierId(request));
		Supplier supplier = this.supplierService.getById(getSupplierId(request));
		supplier.setPeopleNumber(ent.getPeopleNumber());
		this.supplierService.updatePeopleNumber(supplier);
		//更新成功后，session刷新
		if(i>0){
			EnterpriseVo entv = this.enterpriseService.selectById(getSupplierId(request));
			request.getSession().setAttribute(ENTERPRISE_SESSION, entv);	//企业信息存入session
		}
		return i;
	}
	
	/**
	 * 获取企业信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getEnterpriseInfo")
	public ModelAndView getEnterprise(ModelAndView mv,HttpServletRequest request){
		EnterpriseVo ent = this.enterpriseService.selectById(getSupplierId(request));
		Supplier supplier = this.supplierService.getById(ent.getId());
		Integer peopleNumber = supplier.getPeopleNumber();
		ent.setPeopleNumber(peopleNumber);
		//获取企业类型信息
		Map<String, EntParamCode> ent_type = this.entParamCodeService.getEntTypeCode();
		//获取企业主营行业信息
		Map<String, EntParamCode> ent_industry = this.entParamCodeService.getEntIndustryCode();
		
		mv.addObject("ent_type", ent_type.values());
		mv.addObject("ent_industry", ent_industry.values());
		mv.addObject("enterprise", ent);
		mv.setViewName("company/enterprise/enterpriseinfo");
		return mv;
	}
	
	/**
	 * 根据名称查询企业信息
	 * @param request
	 * @param name
	 * @return
	 */
	@RequestMapping(value="getByEnterpriseName")
	@ResponseBody
	public Enterprise getByEnterpriseName(HttpServletRequest request,String name){
		return this.enterpriseService.selectByPrimaryName(name);
	}
	/*#####################################################企业组织结构操作 start####################################################*/
	/**
	 * 添加企业的组织结构
	 * @param request
	 * @param entStr
	 * @return
	 */
	@RequestMapping(value="structure/addEnterprise")
	@ResponseBody
	public Integer addEnterpriseStructure(HttpServletRequest request,EnterpriseStructure entStr){
		//将企业id填进去
		entStr.setEnterpriseId(getSupplierId(request));
		
		return this.enterpriseStructureService.addEnterpriseStructure(entStr);
	}
	
	/**
	 * 修改母企业的组织结构
	 * @param request
	 * @param entStr
	 * @return
	 */
	@RequestMapping(value="structure/updateEnterprise",method=RequestMethod.POST, headers = {"content-type=application/json","content-type=application/xml"})
	@ResponseBody
	public Integer updateEnterpriseMonter(@RequestBody List<EnterpriseStructureVo> list){  
		return this.enterpriseStructureService.updateBatchEnterpriseStructure(list);
	}
	/**
	 * 删除企业的组织结构
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value="structure/delEnterprise",method=RequestMethod.POST)
	@ResponseBody
	public Integer deleteEnterpriseStructure(@RequestBody List<EnterpriseStructure> entId){
		return this.enterpriseStructureService.deleteBatchEnterpriseStructure(JsonUtil.getList(entId.toString(),EnterpriseStructure.class));
	}
	
	/**
	 * 查询企业组织信息
	 * @param request
	 * @param enterpriseId
	 * @param type1  (1:表示是母公司。2:表示的是子公司。3:表示的是友盟关系公司)
	 * @param type2
	 * @return
	 */
	@RequestMapping(value="structure/getEnterprise")
	public ModelAndView getEnterprise(HttpServletRequest request){
		//getSupplierId(request)
		ModelAndView mv  = new ModelAndView();
		List<EnterpriseStructureVo> li_entstr = this.enterpriseStructureService.selectEnterpriseInfo(getSupplierId(request), 1, 2);
		List<EnterpriseStructureVo> mother_ent = new ArrayList<EnterpriseStructureVo>();
		List<EnterpriseStructureVo> child_ent = new ArrayList<EnterpriseStructureVo>();
		for(EnterpriseStructureVo esVo :li_entstr){
			//母公司
			if(esVo.getType().equals(1)){
				mother_ent.add(esVo);
			//子公司
			}else if(esVo.getType().equals(2)){
				child_ent.add(esVo);
			}
		}
		mv.addObject("motherEnt", mother_ent);
		mv.addObject("childEnt", child_ent);
		mv.addObject("ent_id", getSupplierId(request));
		mv.setViewName("company/enterprise/enterprisestructure");
		return mv;
	}
	/*#####################################################企业组织结构操作 end####################################################*/
}

