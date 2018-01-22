/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.mongo.MongoBaseService;
import com.wode.factory.dao.ProductHisDao;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductHis;
import com.wode.factory.service.ProductHisService;


@Service("productHisService")
public class ProductHisServiceImpl extends MongoBaseService<ProductHis>
        implements ProductHisService {
	
    
    @Autowired
    private ProductHisDao productHisDao;


	@Override
	public List<ProductHis> find(ProductHis model, Integer page, Integer pageSize) {
		return this.find(model,"createDate",-1,page, pageSize);
	}


	@Override
	public ProductHisDao getMongoDao() {
		return productHisDao;
	}


	@Override
	public void save(ProductHis model) {
		getMongoDao().insert(model);
	}


	@Override
	public Product getLast(Long productId) {
		ProductHis model = new ProductHis();
		model.setProductId(productId);
		List<ProductHis> ls = this.find(model,"createDate",-1, 1, 1);
		if(ls!=null && !ls.isEmpty()) {
			return ls.get(0).getProduct();
		}
		return null;
	}



}
