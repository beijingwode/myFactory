/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductStoreCategory;
import com.wode.factory.model.StoreCategory;
import com.wode.factory.supplier.query.ProductQuery;
import com.wode.factory.supplier.query.StoreCategoryQuery;

public interface StoreCategoryDao extends EntityDao<StoreCategory, Long> {

    public PageInfo findPage(StoreCategoryQuery query);

    public void saveOrUpdate(StoreCategory entity);

    public Integer delete(Long id);
    
    /**
     * 二级目录
     * @param map
     * @return
     */
    public  List<StoreCategory> findAllBymap(Map map);

    /**
     * 根据商家分类获取商品
     * @param query    商家分类
     * @return
     */
    public PageInfo getProductsByStoreCategory(ProductQuery query);

    /**
     * 根据供应商ID获取商品
     * @param query
     * @return
     */
    public PageInfo getProductsBySupplierId(ProductQuery query);

    /**
     * 删除商品的商家分类
     * @param query
     * @return
     */
    public Integer deleteProductStoreCategory(ProductQuery query);

    /**
     * 添加商品的商家分类
     * @param productStoreCategory
     * @return
     */
    public Integer addProductStoreCategory(ProductStoreCategory productStoreCategory);
}
