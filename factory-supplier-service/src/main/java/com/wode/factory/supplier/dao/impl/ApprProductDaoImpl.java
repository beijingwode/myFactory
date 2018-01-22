/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.ApprProduct;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.supplier.dao.ApprProductDao;

@Repository("apprProductDao")
public class ApprProductDaoImpl extends BaseDao<ApprProduct, java.lang.Long> implements ApprProductDao {

	@Override
	public String getIbatisMapperNamesapce() {
		return "ApprProductMapper";
	}

	public void saveOrUpdate(ApprProduct entity) {
		if (entity.getId() == null)
			save(entity);
		else
			update(entity);
	}

	/**
	 * 获取商品列表（带分页）
	 *
	 * @param map
	 * @return
	 */
	public List<ApprProduct> findProductlistPage(Map map) {
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
	 * 
	 * @return
	 */
	public ApprProduct getProductCheckById(Map map) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce() + ".getProductCheckById", map);
	}

	/**
	 * 销售排行榜
	 * 
	 * @return
	 */
	public List<ApprProduct> getSaleroom(Map map) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce() + ".getSaleroom", map);
	}

	/**
	 * 根据供应商ID获取所有在售商品，以商品排位数降序排列
	 * 
	 * @return
	 */
	public List<ApprProduct> getSellingBySupplierId(Map<String, Object> map) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce() + ".getSellingBySupplierId", map);
	}

	/**
	 * 更新商品展示排位
	 * 
	 * @param map
	 */
	public void updateSortNum(Map<String, Object> map) {
		this.getSqlSession().update(getIbatisMapperNamesapce() + ".updateSortNum", map);
	}

	@Override
	public void insert(ApprProduct product) {
		getSqlSession().insert(getIbatisMapperNamesapce() + ".insert", product);
	}

	@Override
	public List<ApprProduct> getNotDeleteProductByFullName(ApprProduct product) {
		return getSqlSession().selectList(getIbatisMapperNamesapce() + ".getNotDeleteProductByFullName", product);
	}

	@Override
	public List<ProductThirdPrice> getProductThirdPriceByProductId(Long id) {
		return getSqlSession().selectList(getIbatisMapperNamesapce() + ".getProductThirdPriceByProductId", id);
	}

	@Override
	public ApprProduct getProductandstauts(ApprProduct product) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".getProductThirdPriceByProductId", product);

	}

	@Override
	public ApprProduct selectProductIdAndStatus(Long productId) {

		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".selectProductIdAndStatus", productId);
	}

	@Override
	public ApprProduct selectByProductId(Long productId) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".selectByProductId", productId);
	}

	@Override
	public void deleteById(Long id) {
		getSqlSession().selectOne(getIbatisMapperNamesapce() + ".delete", id);
	}

	@Override
	public Long getSupplierapprFullname(Map map) {

		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".getSupplierapprFullname", map);
	}

}
