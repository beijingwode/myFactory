package com.wode.factory.service;

import java.util.List;

import com.wode.factory.model.ProductSpecificationsImage;

public interface ProductSpecificationsImageService {
	/**
	 * 根据SkuId查询产品图片(App用)
	 * @param specificationsId
	 * @return
	 */
	public List<ProductSpecificationsImage> findProductPicture(Long specificationsId,Long productId);

}
