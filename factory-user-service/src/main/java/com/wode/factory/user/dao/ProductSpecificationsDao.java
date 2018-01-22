package com.wode.factory.user.dao;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.FLJProductModel;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.user.vo.ProductSpecificationsVo;

public interface ProductSpecificationsDao extends EntityDao<ProductSpecifications,Long>{

	ProductSpecificationsVo selectOne(String itemids);

	List<FLJProductModel> findTop3(Long supplierId);

	List<ProductSpecifications> findByProductId(String productId);

}
