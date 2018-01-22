/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.supplier.dao.ProductDao;
import com.wode.factory.supplier.query.ProductQuery;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("productDao")
public class ProductDaoImpl extends BaseDao<Product, java.lang.Long> implements ProductDao {

    @Override
    public String getIbatisMapperNamesapce() {
        return "ProductMapper";
    }

    public void saveOrUpdate(Product entity) {
        if (entity.getId() == null)
            save(entity);
        else
            update(entity);
    }

    public PageInfo findPage(ProductQuery query) {
        List<Product> list = getSqlSession().selectList(getIbatisMapperNamesapce() + ".findProductlistPage", query, new RowBounds(query.getPageNumber(), query.getPageSize()));
        return new PageInfo(list);
    }

    /**
     * 获取商品列表（带分页）
     *
     * @param map
     * @return
     */
    public List<Product> findProductlistPage(Map map) {
        return this.getSqlSession().selectList(getIbatisMapperNamesapce() + ".findProductlistPage", map);
    }

    /**
     * 获取商品列表总条数（带分页）
     *
     * @param map
     * @return
     */
    public Integer findProductlistPageCount(Map map) {
        Number num = this.getSqlSession().selectOne(getIbatisMapperNamesapce() + ".findProductlistPage_count", map);
        return num.intValue();
    }

    /**
     * 批量修改
     *
     * @param map
     */
    public void updateProductByids(Map map) {
        this.getSqlSession().update(getIbatisMapperNamesapce() + ".updateProductByids", map);
    }

    /**
     * 获取商品列表（带分页）
     *
     * @param map
     * @return
     */
    public List<Product> getProductByMap(Map map) {
        return this.getSqlSession().selectList(getIbatisMapperNamesapce() + ".getProductByMap", map);
    }
	
	/**
	 * 获取实体类对象，包含评价不通过信息
	 * @return
	 */
	public Product getProductCheckById(Map map){
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getProductCheckById",map);
	}
	
	/**
	 * 销售排行榜
	 * @return
	 */
	public List<Product> getSaleroom(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce() + ".getSaleroom", map);
	}
	/**
	 * 根据供应商ID获取所有在售商品，以商品排位数降序排列
	 * @return
	 */
	public List<Product> getSellingBySupplierId(Map<String, Object> map) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce() + ".getSellingBySupplierId", map);
	}
	/**
	 * 更新商品展示排位
	 * @param map
	 */
	public void updateSortNum(Map<String, Object> map) {
		this.getSqlSession().update(getIbatisMapperNamesapce() + ".updateSortNum", map);
	}

	@Override
	public List<Product> findProduct(ProductQuery productQuery) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce() + ".findPage", productQuery);
	}

	@Override
	public void insert(Product product) {
		getSqlSession().insert(getIbatisMapperNamesapce()+".insert", product);
	}

	@Override
	public List<Product> getNotDeleteProductByFullName(Product product) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".getNotDeleteProductByFullName", product);
	}

	@Override
	public List<ProductThirdPrice> getProductThirdPriceByProductId(Long id) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".getProductThirdPriceByProductId",id);
	}

	
	@Override
	public Long getSupplierFullname(Map map){
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getSupplierFullname",map);
	}

	@Override
	public List<Product> findProductlist(Map map) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce() + ".findProductlist", map);
	}

	@Override
	public void changProductOuterId(Map map) {
		this.getSqlSession().update(getIbatisMapperNamesapce() + ".changProductOuterId", map);
	}
}
