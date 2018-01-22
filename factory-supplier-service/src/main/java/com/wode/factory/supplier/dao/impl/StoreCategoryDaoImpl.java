/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import com.github.pagehelper.PageInfo;
import com.wode.common.db.DBUtils;
import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductStoreCategory;
import com.wode.factory.model.StoreCategory;
import com.wode.factory.supplier.dao.StoreCategoryDao;
import com.wode.factory.supplier.query.ProductQuery;
import com.wode.factory.supplier.query.StoreCategoryQuery;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

@Repository("storeCategoryDao")
public class StoreCategoryDaoImpl extends BaseDao<StoreCategory, java.lang.Long> implements StoreCategoryDao {

    @Autowired
    DBUtils dbUtils;

    @Override
    public String getIbatisMapperNamesapce() {
        return "StoreCategoryMapper";
    }

    public void saveOrUpdate(StoreCategory entity) {
        if (entity.getId() == null)
            save(entity);
        else
            update(entity);
    }

    public PageInfo findPage(StoreCategoryQuery query) {
        List<StoreCategory> list = getSqlSession().selectList(getIbatisMapperNamesapce() + ".findPage", query, new RowBounds(query.getPageNumber(), query.getPageSize()));
        return new PageInfo(list);
    }

    public Integer delete(Long id) {
        deleteRelationships(id);
        return getSqlSession().delete(getIbatisMapperNamesapce()+".deleteIncludeChildren", id);
    }

    /**
     *
     * @param id
     * @return
     */
    private void deleteRelationships(Long id) {
        getSqlSession().delete(getIbatisMapperNamesapce() + ".deleteRelationships", id);
    }
    
    /**
     * 二级目录
     * @param map
     * @return
     */
    public  List<StoreCategory> findAllBymap(Map map){
    	 return getSqlSession().selectList(getIbatisMapperNamesapce()+".findAllBymap", map);
    }


    @Override
    public PageInfo getProductsByStoreCategory(ProductQuery query) {
        List<Product> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".getProductsByStoreCategory", query, new RowBounds(query.getPageNumber(), query.getPageSize()));
        return new PageInfo(list);
    }

    @Override
    public PageInfo getProductsBySupplierId(ProductQuery query) {
        Assert.notNull(query.getSupplierId(), "A supplier id was needed.");
        List<Product> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".getProductsBySupplier", query, new RowBounds(query.getPageNumber(), query.getPageSize()));
        return new PageInfo(list);
    }

    @Override
    public Integer deleteProductStoreCategory(ProductQuery query) {
        return getSqlSession().delete(getIbatisMapperNamesapce()+".deleteProductStoreCategory", query);
    }

    @Override
    public Integer addProductStoreCategory(ProductStoreCategory productStoreCategory) {
        long pk = dbUtils.CreateID();
        productStoreCategory.setId(pk);
        return getSqlSession().insert(getIbatisMapperNamesapce()+".addProductStoreCategory", productStoreCategory);
    }
}
