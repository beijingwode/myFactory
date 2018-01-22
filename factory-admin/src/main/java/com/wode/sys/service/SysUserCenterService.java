package com.wode.sys.service;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.wode.common.base.ServiceMybatis;
import com.wode.common.beetl.function.OfficeFunctions;
import com.wode.common.util.IPUtils;
import com.wode.common.util.PasswordEncoder;
import com.wode.sys.model.SysOffice;
import com.wode.sys.model.SysUser;
import com.wode.sys.utils.SysUserUtils;

@Service
public class SysUserCenterService extends ServiceMybatis<SysUser> {

	@Resource
	private OfficeFunctions officeFunctions;

	public SysUser getSysUserInfo(SysUser sysUser) {
		Map<Long, SysOffice> offices = officeFunctions.getAllOfficeMap();
		Long companyId = sysUser.getCompanyId();
		Long officeId = sysUser.getOfficeId();
		String orgStr = null;
		if (companyId.equals(officeId)) { // 机构
			orgStr = ((SysOffice) offices.get(companyId)).getName();
		} else {
			orgStr = ((SysOffice) offices.get(companyId)).getName() + " —— "
					+ ((SysOffice) offices.get(officeId)).getName();
		}
		String curIP = IPUtils.getClientAddress(SysUserUtils.getCurRequest());
		String ipEx = "";
		if (!StringUtils.equals(sysUser.getLoginIp(), curIP))
			ipEx = "(当前IP为:" + curIP + "，与上次登录IP不一致，请注意!)";
		sysUser.set("orgStr", orgStr);
		sysUser.set("ipEx", ipEx);
		return sysUser;
	}

	/**
	 * 用户更新资料
	 */
	public Integer updateSysuserInfo(SysUser sysUser) {
		String pwd = null;
		if(StringUtils.isNotBlank(sysUser.getPassword())){
			pwd = PasswordEncoder.encrypt(sysUser.getPassword(),
					sysUser.getUsername());
		}
		sysUser.setPassword(pwd);
		sysUser.setId(SysUserUtils.getLoginUser().getId());
		return this.updateByPrimaryKeySelective(sysUser);
	}

}
