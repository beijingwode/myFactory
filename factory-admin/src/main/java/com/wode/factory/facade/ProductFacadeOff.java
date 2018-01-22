package com.wode.factory.facade;

import com.wode.factory.model.Product;

public interface ProductFacadeOff {
    /*
     * 这里要操作正式表商品下架后要把他放入临时表的待售状态中，和前端要操作sku弹出修改价格的时候功能一样，把正式表的数据更新到临时表中，还有商品的规格，属性等操作要放在一个事务中
     */
	public void productToapprProduct(Long productid);
	public Integer forceselloff(Product product);
	
}
