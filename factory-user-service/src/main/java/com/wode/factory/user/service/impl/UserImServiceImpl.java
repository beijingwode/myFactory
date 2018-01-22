/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.factory.model.UserIm;
import com.wode.factory.user.dao.UserImDao;
import com.wode.factory.user.service.UserImService;
import com.wode.factory.user.vo.ContactsVo;

@Service("userImService")
public class UserImServiceImpl extends BaseService<UserIm,java.lang.Long> implements  UserImService{
	@Autowired
	private UserImDao userImDao;
	
	public UserImDao getEntityDao() {
		return this.userImDao;
	}

	@Override
	public List<UserIm> selectByModel(UserIm query) {
		return userImDao.selectByModel(query);
	}

	@Override
	public UserIm selectByShop(Long shopId) {
		List<UserIm> ls = userImDao.selectByShop(shopId);
		if(ls!=null && !ls.isEmpty()) {
			return ls.get(0);
		}
		return null;
	}

	@Override
	public UserIm selectBySupplier(Long supplierId) {
		List<UserIm> ls = userImDao.selectBySupplier(supplierId);
		if(ls!=null && !ls.isEmpty()) {
			return ls.get(0);
		}
		return null;
	}

	@Override
	public UserIm selectByShopAndSupplier(Long shopId, Long supplierId) {
		UserIm rtn = this.selectByShop(shopId);
		if(rtn!=null) return rtn;
		return this.selectBySupplier(supplierId);
	}
}