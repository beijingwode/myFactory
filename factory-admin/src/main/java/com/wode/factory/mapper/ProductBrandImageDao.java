package com.wode.factory.mapper;

import java.util.List;

import com.wode.factory.model.ProductBrandImage;

public interface ProductBrandImageDao {

	List<ProductBrandImage> findByBrandId(Long brandId);

	void deleteById(Long id);

}
