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

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.FLJModel;
import com.wode.factory.model.Shop;
import com.wode.factory.user.dao.ShopSettingDao;

@Repository("shopSettingDao")
public class ShopSettingDaoImpl extends BaseDao<Shop,Long> implements ShopSettingDao {
    @Override
    public void saveOrUpdate(Shop entity) throws DataAccessException {

    }

    @Override
	public String getIbatisMapperNamesapce() {
		return "ShopSettingMapper";
	}

    @Override
    public List<Shop> getBySupplierId(Long supplierId) {
        return getSqlSession().selectList(getIbatisMapperNamesapce()+".getBySupplierId", supplierId);
    }
    
    @Override
    public List<FLJModel> findAllShop() {
    	return getSqlSession().selectList(getIbatisMapperNamesapce()+".findAll");
    }
    
	@Override
	public int findcount(Long supplierId,Long shopId) {
		Map<String,Long> map =new HashMap<String,Long>();
		map.put("supplierId", supplierId);
		map.put("shopId", shopId);
		Number num = getSqlSession().selectOne(getIbatisMapperNamesapce()+".findcount", map);
		return num.intValue();
	}
}
