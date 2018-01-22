/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Product;
import com.wode.factory.model.StoreCategory;
import com.wode.factory.user.dao.StoreCategoryDao;
import com.wode.factory.user.query.ProductQuery;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository("storeCategoryDao")
public class StoreCategoryDaoImpl extends BaseDao<StoreCategory, Long> implements StoreCategoryDao {

    @Override
    public String getIbatisMapperNamesapce() {
        return "StoreCategoryMapper";
    }

    @Override
    public List<StoreCategory> getStoreCategoriesBySupplierId(Long supplierId) {
        return getSqlSession().selectList(getIbatisMapperNamesapce()+".getStoreCategoriesBySupplierId", supplierId);
    }

    @Override
    public PageInfo getProductsByStoreCategory(ProductQuery query) {
        List<Product> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".getProductsByStoreCategory", query, new RowBounds(query.getPageNumber(), query.getPageSize()));
        return new PageInfo(list);
    }

    @Override
    public void saveOrUpdate(StoreCategory entity) throws DataAccessException {}
}
