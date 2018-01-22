package com.wode.factory.mapper;

import java.util.List;

import com.wode.factory.model.ProductLadder;

public interface ProductLadderDao extends  FactoryBaseDao<ProductLadder> {

	List<ProductLadder> getByProductId(Long productId);

	void deleteBySupplierId(Long supplierId);

}
