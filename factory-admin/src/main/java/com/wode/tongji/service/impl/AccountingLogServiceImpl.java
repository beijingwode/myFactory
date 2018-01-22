/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.tongji.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.sys.model.SysUser;
import com.wode.tongji.mapper.AccountingLogMapper;
import com.wode.tongji.model.AccountingLog;
import com.wode.tongji.service.AccountingLogService;

@Service("accountingLogService")
public class AccountingLogServiceImpl implements  AccountingLogService{
	@Autowired
	private AccountingLogMapper accountingLogMapper;

	@Override
	public void insert(AccountingLog al) {
		accountingLogMapper.insert(al);
	}
	
}
