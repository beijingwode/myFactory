package com.wode.factory.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.SupplierTemp;
import com.wode.factory.model.UserShare;
import com.wode.factory.model.UserShareAutoBuy;
import com.wode.factory.service.EnterpriseUserService;
import com.wode.factory.service.SupplierExchangeProductService;
import com.wode.factory.service.SupplierTempService;
import com.wode.factory.service.UserShareAutoBuyService;
import com.wode.factory.service.UserShareService;
import com.wode.factory.vo.SupplierExchangeProductVo;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysUser;

@Controller
@SuppressWarnings("unchecked")
@RequestMapping("supplierTemp")
public class SupplierTempController {
	@Autowired
	private SupplierTempService supplierTempService;
	@Resource
	private SysUserMapper sysUserMapper;
	@Autowired
	private EnterpriseUserService enterpriseUserService;
	@Autowired
	private UserShareService userShareService;
	@Resource
	private UserShareAutoBuyService userShareAutoBuyService;
	@Autowired
	private SupplierExchangeProductService supplierExchangeProductService;
	
	@RequestMapping("view")
	public String toPage(Model model,HttpSession session){
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
		return "manager/supplierTemp/supplier-temp";
	}
	@RequestMapping("/list")
	public String queryFilter(@RequestParam Map<String, Object> params,ModelMap model,String viewStatus,HttpSession session) {
		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}
		PageInfo<SupplierTemp> page = supplierTempService.pageInfoList(params);
		List<SupplierTemp> list = page.getList();
		for (SupplierTemp supplierTemp : list) {
			Integer findEnterprisePeopleNumber = enterpriseUserService.findEnterprisePeopleNumber(supplierTemp.getId());
			//Integer cnt = enterpriseUserService.findEnterpriseActivePeopleCnt(supplierTemp.getId());
			supplierTemp.setEnCount(findEnterprisePeopleNumber);
		}
		model.addAttribute("page", page);
		return "manager/supplierTemp/supplier-temp-list";
	}
	@RequestMapping("/detail/showlayer")
	public String detail(Model model,Long id,String viewStatus) {
		SupplierTemp supplierTemp = supplierTempService.getById(id);
		UserShare userShare = userShareService.getByUserId(id);
		List<UserShareAutoBuy> skulst;
		if(userShare!=null) {
			// 为jsp 方便处理，暂时交换
			String wxUrl = userShare.getWxTempQrUrl();
			if(!StringUtils.isEmpty(wxUrl)) {
				userShare.setWxTempQrUrl(userShare.getShareUrl());
				userShare.setShareUrl(wxUrl);
			}
			UserShareAutoBuy query = new UserShareAutoBuy();
			query.setShareId(userShare.getId());
			skulst= userShareAutoBuyService.selectByModel(query);
		} else {
			skulst = new ArrayList<UserShareAutoBuy>();
		}
		BigDecimal avgAmount = BigDecimal.ZERO;
		if(userShare!=null) {
			List<SupplierExchangeProductVo> sep = supplierExchangeProductService.findProductBySupplierId(userShare.getId());
			if(sep.size()>0) {
				for (SupplierExchangeProductVo supplierExchangeProductVo : sep) {
					avgAmount = avgAmount.add(supplierExchangeProductVo.getEmpAvgAmount());
				}
			}
			model.addAttribute("sep", sep);
		}
		model.addAttribute("count", avgAmount);
		model.addAttribute("userBuySkus", skulst);
		model.addAttribute("userShare", userShare);
		model.addAttribute("spt", supplierTemp);
		model.addAttribute("webUrl", Constant.FACTORY_WEB_URL);
		return "manager/supplierTemp/supplier-temp-detil";
	}
}
