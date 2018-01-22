package com.wode.factory.company.facade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.factory.company.facade.DeleteApprRelationFacade;
import com.wode.factory.model.Inventory;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.supplier.dao.InventoryDao;
import com.wode.factory.supplier.dao.ProductAttributeDao;
import com.wode.factory.supplier.dao.ProductDetailListDao;
import com.wode.factory.supplier.dao.ProductParameterValueDao;
import com.wode.factory.supplier.dao.ProductShippingDao;
import com.wode.factory.supplier.dao.ProductSpecificationValueDao;
import com.wode.factory.supplier.dao.ProductSpecificationsDao;
import com.wode.factory.supplier.dao.ProductSpecificationsImageDao;

@Service("deleteApprRelationFacade")
public class DeleteApprRelationFacadeImpl implements DeleteApprRelationFacade {
  @Autowired
  ProductSpecificationValueDao productSpecificationValueDao;
  @Autowired
  ProductAttributeDao productAttributeDao;
  @Autowired
  ProductSpecificationsDao productSpecificationsDao;
  @Autowired
  ProductParameterValueDao productParameterValueDao;
  @Autowired
  ProductDetailListDao productDetailListDao;
  @Autowired
  ProductSpecificationsImageDao productSpecificationsImageDao;
  @Autowired
  InventoryDao inventoryDao;
  @Autowired
  ProductShippingDao productShippingDao;
  /**
   * 临时表数据删除后，要把关联的sku等垃圾信息删除
   */
	@Override
	@Transactional
	public void deleteApprRelation(Long apprid){
		Map map=new HashMap<>();
		map.put("productid", apprid);
		//删除规格值
		productSpecificationValueDao.deleteApprRelation(apprid);
		//删除属性表
		productAttributeDao.removeAllByProductid(map);		
		//删除参数值
		productParameterValueDao.removeAllByProductid(map);
		//删除清单值
		productDetailListDao.removeAllByProductid(map);
		//删除运费模板
		productShippingDao.deleteApprRelation(apprid);
		//删除sku关联的image
		productSpecificationsImageDao.deleteApprRelation(apprid);
		//删除sku关联的库存
		inventoryDao.deleteApprRelation(apprid);
		//删除sku
		productSpecificationsDao.deleteApprRelation(apprid);
		
	}

}
