/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserIm;
import com.wode.factory.user.dao.UserImDao;
import com.wode.factory.user.vo.ContactsVo;

@Repository("userImDao")
public class UserImDaoImpl extends BaseDao<UserIm,java.lang.Long> implements UserImDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserImMapper";
	}
	
	public void saveOrUpdate(UserIm entity){
		if(StringUtils.isNullOrEmpty(entity.getId())) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public List<UserIm> selectByModel(UserIm query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByModel", query);
	}

	@Override
	public List<UserIm> selectByShop(Long shopId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByShop", shopId);
	}
	
	@Override
	public List<UserIm> selectBySupplier(Long supplierId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectBySupplier", supplierId);
	}
}
