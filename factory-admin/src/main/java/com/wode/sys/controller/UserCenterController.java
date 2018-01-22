package com.wode.sys.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.base.BaseController;
import com.wode.sys.model.SysUser;
import com.wode.sys.service.SysUserCenterService;
import com.wode.sys.utils.SysUserUtils;

@Controller
@RequestMapping("userCenter")
public class UserCenterController extends BaseController{

	@Resource
	private SysUserCenterService sysUserCenterService;
	
	
	
	@RequestMapping
	public String viewInfo(Model model) {
		SysUser sysUser = sysUserCenterService.getSysUserInfo(loginUser);
		model.addAttribute("sysUser", sysUser);
		return "sys/userCenter/userCenter";
	}
	
	/**
	 * 更新用户信息
	 * @param sysUser
	 */
	@RequestMapping(value = "updateInfo",method = RequestMethod.POST)
	public @ResponseBody Integer updateSysuserInfo(@ModelAttribute SysUser sysUser){
		Integer count = sysUserCenterService.updateSysuserInfo(sysUser);
		if(count>0){
			SysUserUtils.getSession().invalidate();
		}
		return count;
	}
	
	@RequestMapping("conversation")
	public String conversation(){
		return "sys/userCenter/conversation";
	}

}
