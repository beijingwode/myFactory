package com.wode.factory.service;

import java.util.List;

import com.wode.factory.model.ProductLadder;

public interface ProductLadderService extends FactoryEntityService<ProductLadder> {
	
	/**
	 * 通过id获取
	 * @param id
	 * @return
	 */
	List<ProductLadder> getByProductId(Long productId);

}
