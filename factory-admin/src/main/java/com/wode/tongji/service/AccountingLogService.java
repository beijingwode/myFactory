/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.tongji.service;

import org.springframework.stereotype.Service;

import com.wode.tongji.model.AccountingLog;

@Service("accountingLogService")
public interface AccountingLogService{

	void insert(AccountingLog al);
	
	
}
