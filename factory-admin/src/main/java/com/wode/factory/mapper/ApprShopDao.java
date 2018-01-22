package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ApprShop;

/**
 * Created by zoln on 2015/7/24.
 */
public interface ApprShopDao extends  EntityDao<ApprShop,Long> {
	
	List<ApprShop> findApprShop(Map<String, Object> map);
	List<ApprShop> findApprShopEmpty(Map<String, Object> map);
	void insertShop(ApprShop appr);
	void updateShop(ApprShop appr);
	List<ApprShop> findByCreatTime(Map<String, Object> map);
	void deleteBySupplierId(Long supplierId);
	void deleteUserShareItemBySupplierId(Long supplierId);
}
