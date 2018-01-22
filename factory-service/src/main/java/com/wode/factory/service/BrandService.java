/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service;

import java.util.List;

import com.wode.common.stereotype.QueryCached;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.vo.BrandVo;

public interface BrandService {

	@QueryCached(keyPreFix="selectByCategory")
	List<BrandVo> selectByCategory(Long categoryId);

	ProductBrand selectById(Long brandId);
	@QueryCached
	List<ProductBrand> findBySupplier(Long sid);
}
