package com.wode.factory.user.facade;

import java.util.List;

import com.wode.factory.model.GroupBuyProduct;

public interface GroupBuyFacade {

	List<GroupBuyProduct> createGroupBuyProduct(Long id, String productIds);

}
