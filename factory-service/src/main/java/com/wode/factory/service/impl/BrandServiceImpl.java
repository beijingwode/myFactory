/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.stereotype.QueryCached;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.service.BrandService;
import com.wode.factory.vo.BrandVo;

@Service("brandProducttypeService")
public class BrandServiceImpl implements  BrandService{
	@Autowired
	private Dao dao;
	
	private static final Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);
	
	public List<BrandVo> selectByCategory(Long categoryId) {
		Sql sql = Sqls.create("SELECT DISTINCT A.`name` FROM (SELECT pb.`name` FROM t_product_brand pb INNER JOIN t_shop s ON (s.id = pb.shop_id) INNER JOIN t_brand_producttype bp ON(bp.brand_id=pb.id) WHERE pb.is_delete=0 AND bp.category_id = @cid ORDER BY IFNULL(pb.category_id,999)) A");
		sql.params().set("cid",categoryId);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(ProductBrand.class));
		dao.execute(sql);		
		List<ProductBrand> sList = sql.getList(ProductBrand.class);
		
		List<BrandVo> bvList = new ArrayList<BrandVo>();
		for (ProductBrand productBrand : sList) {
			BrandVo vo=new BrandVo();
			//vo.setSupplierId(productBrand.getSupplierId());
			vo.setCategoryId(categoryId);
			//vo.setBrandId(productBrand.getId());
			vo.setBrandName(productBrand.getName());
			bvList.add(vo);
		}
		return bvList;
	}
	
	
	@QueryCached
	public ProductBrand selectById(Long brandId) {
		ProductBrand pb=dao.fetch(ProductBrand.class,brandId);
		return pb;
	}
	
	@QueryCached
	public List<ProductBrand> findBySupplier(Long sid) {
		return dao.query(ProductBrand.class, Cnd.where("supplierId", "=", sid));
	}
	
}