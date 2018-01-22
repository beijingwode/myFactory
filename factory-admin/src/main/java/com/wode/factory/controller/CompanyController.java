package com.wode.factory.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.util.ActResult;
import com.wode.factory.mapper.EnterpriseStructureDao;
import com.wode.factory.model.EnterpriseStructure;
import com.wode.factory.model.Supplier;
import com.wode.factory.service.SupplierService;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysUser;


/**
 * @author mkx
 *
 */
@Controller
@RequestMapping("company")
public class CompanyController {

	@Autowired
	private SupplierService supplierService;
	@Resource
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private EnterpriseStructureDao enterpriseStructureDao;
	@RequestMapping("view")
	public String toSupplierView(Model model,HttpSession session){
		model.addAttribute("viewStatus", "view");
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("officeId", 0);
		params.put("officeType", "");
		params.put("roles", "-108,-111");
		params.put("pageNum", 1);
		params.put("pageSize", 120);

		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		
		model.addAttribute("mlist", list);
		model.addAttribute("uid", user.getId());
		return "manager/company/supplier-base";
	}

	
	@RequestMapping("/list")
	public String queryFilter(@RequestParam Map<String, Object> params,ModelMap model,String viewStatus) {
		String[] str = viewStatus.split(",");
		if(str.length>0)
			viewStatus = str[0];
		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}

		Calendar firstc = Calendar.getInstance();
		firstc.set(Calendar.DAY_OF_MONTH,1);
		firstc.set(Calendar.HOUR_OF_DAY, 0);
		firstc.set(Calendar.MINUTE, 0);
		firstc.set(Calendar.SECOND, 0);
		PageInfo pageInfo = supplierService.findSupplierCount(params);
		
		model.addAttribute("page", pageInfo);
		return "manager/company/supplier-list-view";
	}

	@RequestMapping(value = "/toSetCompany", method = RequestMethod.POST)
	public String toSetCompany(Model m,Long id){
		m.addAttribute("id", id);
		EnterpriseStructure enterpriseStructure = new EnterpriseStructure();
		enterpriseStructure.setEnterpriseId(id);
		enterpriseStructure.setType(1);
		List<EnterpriseStructure> list = enterpriseStructureDao.selectByModel(enterpriseStructure);
		if(null != list && list.size() > 0) {
			m.addAttribute("selectId", list.get(0).getRelatedEntId());
		}else {
			m.addAttribute("selectId", "");
		}
		m.addAttribute("companys", supplierService.findAll());
		return "manager/company/set_company";
	}
	
	@RequestMapping(value = "/setCompany", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public ActResult<String> setCompany(Model m,Long id,Long business){
		//删除旧数据
		enterpriseStructureDao.deleteOld(id);
		
		if(null != business) {
			//增加新数据
			EnterpriseStructure enterpriseStructure = new EnterpriseStructure();
			enterpriseStructure.setEnterpriseId(id);
			enterpriseStructure.setRelatedEntId(business);
			enterpriseStructure.setType(1);
			enterpriseStructureDao.insert(enterpriseStructure);
			enterpriseStructure = new EnterpriseStructure();
			enterpriseStructure.setEnterpriseId(business);
			enterpriseStructure.setRelatedEntId(id);
			enterpriseStructure.setType(2);
			enterpriseStructureDao.insert(enterpriseStructure);
		}
		return new ActResult<String>();
	}
}
