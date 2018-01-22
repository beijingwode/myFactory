package com.wode.factory.supplier.facade;

import java.util.Map;

import com.wode.factory.model.ApprProduct;
import com.wode.factory.model.Inventory;
import com.wode.factory.model.ProductSpecifications;

public interface ProductFacade {
    /*
     * 这里要操作sku弹出修改价格的时候，修改完价格后要把正式表的数据更新到临时表中，还有商品的规格，属性等操作要放在一个事务中
     */
	public ApprProduct productToapprProduct(Long productid,Integer status,Map<Long,ProductSpecifications> skuMap,Map<Long,Inventory> stockMap);
}
