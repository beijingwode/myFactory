/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductStoreCategory;
import com.wode.factory.model.StoreCategory;
import com.wode.factory.supplier.query.ProductQuery;
import com.wode.factory.supplier.query.StoreCategoryQuery;

import org.springframework.stereotype.Service;

public interface StoreCategoryService extends EntityService<StoreCategory, Long> {

    public PageInfo findPage(StoreCategoryQuery query);

    public Integer delete(Long id);
    
    /**
     * 二级目录
     * @param map
     * @return
     */
    public  List<StoreCategory> findAllBymap(Map map);

    /**
     * 查询商品列表
     * @param query
     * @return
     */
    public  PageInfo getProductsBySupplierId(ProductQuery query);

    /**
     *
     * @param query
     * @return
     */
    public PageInfo getProductsByStoreCategory(ProductQuery query);

    /**
     * 添加或更新商品的商家分类
     * @param query 用于删除原有的分类
     * @param productStoreCategories  新增分类
     */
    public void saveOrUpdateProductStoreCategory(ProductQuery query, List<ProductStoreCategory> productStoreCategories);
}
