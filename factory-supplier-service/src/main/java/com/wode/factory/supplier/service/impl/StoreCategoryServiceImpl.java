/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductStoreCategory;
import com.wode.factory.model.StoreCategory;
import com.wode.factory.supplier.dao.StoreCategoryDao;
import com.wode.factory.supplier.query.ProductQuery;
import com.wode.factory.supplier.query.StoreCategoryQuery;
import com.wode.factory.supplier.service.StoreCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("storeCategoryService")
public class StoreCategoryServiceImpl extends BaseService<StoreCategory, java.lang.Long> implements StoreCategoryService {
    @Autowired
    @Qualifier("storeCategoryDao")
    private StoreCategoryDao storeCategoryDao;

    public EntityDao getEntityDao() {
        return this.storeCategoryDao;
    }

    public PageInfo findPage(StoreCategoryQuery query) {
        return storeCategoryDao.findPage(query);
    }

    public Integer delete(Long id) {
        return storeCategoryDao.delete(id);
    }
    
    /**
     * 二级目录
     * @param map
     * @return
     */
    public  List<StoreCategory> findAllBymap(Map map){
    	return storeCategoryDao.findAllBymap(map);
    }

    @Override
    public PageInfo getProductsBySupplierId(ProductQuery query) {
        Assert.notNull(query.getSupplierId(),"A supplier id was needed.");
        return storeCategoryDao.getProductsBySupplierId(query);
    }

    @Override
    public PageInfo getProductsByStoreCategory(ProductQuery query) {
        if(query.getId() == null) {
            Assert.notNull(query.getSupplierId(),"A supplier id was needed.");
        }
        if(query.getSupplierId() == null) {
            Assert.notNull(query.getId(), "A category id was needed.");
        }
        return storeCategoryDao.getProductsByStoreCategory(query);
    }

    @Override
    public void saveOrUpdateProductStoreCategory(ProductQuery query, List<ProductStoreCategory> productStoreCategories) {
        storeCategoryDao.deleteProductStoreCategory(query);
        for (ProductStoreCategory psc : productStoreCategories) {
            storeCategoryDao.addProductStoreCategory(psc);
        }
    }
}
