package com.wode.factory.mapper;

import com.wode.factory.model.ProductECard;

/**
 * Created by zoln on 2015/7/24.
 */
public interface ProductECardDao extends  FactoryBaseDao<ProductECard> {

	void deleteBySupplierId(Long supplierId);
}
