package com.wode.factory.service;

import java.util.List;

import com.wode.factory.model.GroupBuyProduct;

public interface GroupBuyProductService {

	List<GroupBuyProduct> findByGroupId(Long groupId);

	Integer findByCount(Long groupId);

}
