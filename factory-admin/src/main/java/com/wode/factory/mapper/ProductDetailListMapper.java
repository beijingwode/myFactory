package com.wode.factory.mapper;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductDetailList;

public interface ProductDetailListMapper extends  EntityDao<ProductDetailList,Long>{
 public List<ProductDetailList> getByProductId(Long productId);
 public void saveOrUpdate(ProductDetailList entity);
 public void insert(ProductDetailList entity);
public void deleteBySupplierId(Long supplierId);
public void deleteProductImageBySupplierId(Long supplierId);
}
