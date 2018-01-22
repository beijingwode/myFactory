/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import com.wode.common.frame.base.EntityService;
import com.wode.common.stereotype.QueryCached;
import com.wode.factory.model.UserIm;

public interface UserImService extends EntityService<UserIm,Long>{
	public List<UserIm> selectByModel(UserIm query);
	@QueryCached(keyPreFix="userIm_selectByShop")
	public UserIm selectByShop(Long shopId);
	@QueryCached(keyPreFix="userIm_selectBySupplier")
	public UserIm selectBySupplier(Long supplierId);
	@QueryCached(keyPreFix="userIm_selectByShopAndSupplier")
	public UserIm selectByShopAndSupplier(Long shopId,Long supplierId);
}
