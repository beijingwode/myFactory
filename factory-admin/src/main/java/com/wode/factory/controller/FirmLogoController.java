package com.wode.factory.controller;

import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserShare;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.UserShareService;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysUser;

/**
 * 设置企业logo Controller
 * @author user
 *
 */
@Controller
@RequestMapping("firmlogo")
public class FirmLogoController {
	@Autowired
	private SupplierService supplierService;
	@Resource
	private SysUserMapper sysUserMapper;
	@Autowired
	private UserShareService userShareService;
	@Autowired
	RedisUtil redis;
	/**
	 * 跳转到设置企业logo页面
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("view")
	public String toFirmLogoView(Model model,HttpSession session){
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
		return "manager/firmLogo/supplier-firmLogo";
	}
	/**
	 * 数据展示方法
	 * @param params
	 * @param model
	 * @param viewStatus
	 * @return
	 */
	@RequestMapping("/list")
	public String queryFilter(@RequestParam Map<String, Object> params,ModelMap model,String viewStatus) {
		String[] str = viewStatus.split(",");
		if(str.length>0)
			viewStatus = str[0];
		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}
		params.put("status", "4");
		Calendar firstc = Calendar.getInstance();
		firstc.set(Calendar.DAY_OF_MONTH,1);
		firstc.set(Calendar.HOUR_OF_DAY, 0);
		firstc.set(Calendar.MINUTE, 0);
		firstc.set(Calendar.SECOND, 0);
		PageInfo pageInfo = supplierService.findSupplierCount(params);
		model.addAttribute("page", pageInfo);
		return "manager/firmLogo/supplier-firmLogo-list";
	}
	/**
	 * 跳转到设置logo页面 加回显数据
	 * @param m
	 * @param id 商家id
	 * @return
	 */
	@RequestMapping(value = "/toSetCompany", method = RequestMethod.POST)
	public String toSetCompany(Model m,Long id){
		 Supplier findByid = supplierService.findByid(id);
		m.addAttribute("supplier",findByid);
		return "manager/firmLogo/supplier-set-firmLogo";
	}
	/**
	 * 修改图片方法
	 * @param firmLogo 图片路径
	 * @param supplierId 商家id
	 */
	@RequestMapping(value="/setCompany", method = RequestMethod.POST)
	public void setCompany(String firmLogo,Long supplierId,String nickName){
		Supplier supplier = new Supplier();
		supplier.setId(supplierId);
		supplier.setFirmLogo(firmLogo);
		supplier.setNickName(nickName);
		if(supplier!=null){
			supplierService.updateFirmLogo(supplier);
			if(!StringUtils.isEmpty(nickName)){
				UserShare userShare = userShareService.getByUserId(supplierId);
				if(userShare!=null){
					redis.del("REDIS_USER_SHARE_"+userShare.getId());
					userShare.setUserNick(nickName);
					userShare.setShareMsg1(nickName);
					userShareService.update(userShare);
				}
			}
		}
	}
}
