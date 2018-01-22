package com.wode.api.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.factory.model.Enterprise;
import com.wode.factory.user.model.SupplierTemp;
import com.wode.factory.user.service.SupplierTempService;
import com.wode.factory.user.util.Constant;

@Controller
@RequestMapping("/supplierTemp")
public class SupplierTempController extends BaseController{
	@Autowired
	private SupplierTempService supplierTempService;
	private String qiyeApiUrl = Constant.QIYE_API_URL;
	
	@RequestMapping("/saveSupplierTemp")
	@ResponseBody
	public ActResult<String> saveSupplierTemp(Integer managerId,String managerName,String suppilerName,String brandName,String addres,String contact,String phone){
		Map paramMap = new HashMap();
		paramMap.put("supplierName",suppilerName);
		String result =  HttpClientUtil.sendHttpRequest("post", qiyeApiUrl+"api/saveEnterprise", paramMap);
		ActResult acTicket = JsonUtil.getObject(result, ActResult.class);
		if(acTicket.isSuccess()) {
			SupplierTemp temp = new SupplierTemp();
			temp.setId((Long) acTicket.getData());
			temp.setComName(suppilerName);
			temp.setBrandName(brandName);
			temp.setAddres(addres);
			temp.setContact(contact);
			temp.setPhone(phone);
			temp.setCreateTime(new Date());
			temp.setManagerId(managerId);
			temp.setManagerName(managerName);
			temp.setStatus(0);
			supplierTempService.save(temp);
		}
		return ActResult.success("");
	}
	@RequestMapping("/queryAddPage.user")
	public ModelAndView queryAddPage(String managerId,String managerName) {
		ModelAndView model = new ModelAndView();
		model.addObject("userId", loginUser.getId());
		model.addObject("managerId", managerId);
		model.addObject("managerName", managerName);
		model.setViewName("addSupplierTemp");
		return model;
	}
	@RequestMapping("/findBySupplierTemp")
	@ResponseBody
	public ActResult<String> findBySupplierTemp (Integer managerId,String comName){
		ActResult act = new ActResult();
		SupplierTemp temp = new SupplierTemp();
		temp.setManagerId(managerId);
		temp.setComName(comName);
		List<SupplierTemp> selectByModel = supplierTempService.selectByModel(temp);
		act.setData(selectByModel);
		return act;
	}
}
