/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.StoreCategory;
import com.wode.factory.user.query.ProductQuery;

import java.util.List;

public interface StoreCategoryDao extends EntityDao<StoreCategory, Long> {

    /**
     * 根据供应商ID获取所有店铺分类
     * @param supplierId
     * @return
     */
    public List<StoreCategory> getStoreCategoriesBySupplierId(Long supplierId);


    /**
     * 根据商家分类获取商品
     * @return
     */
    public PageInfo getProductsByStoreCategory(ProductQuery query);
}
