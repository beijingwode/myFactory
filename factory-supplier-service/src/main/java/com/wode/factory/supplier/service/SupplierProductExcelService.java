/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.ApprProduct;
import com.wode.factory.model.Product;
import com.wode.factory.model.SupplierProductExcel;
import com.wode.factory.supplier.query.BatchAddProductVo;
import com.wode.factory.supplier.query.SupplierProductExcelQuery;

@Service("supplierProductExcelService")
public interface SupplierProductExcelService extends EntityService<SupplierProductExcel,Long>{
	
	public EntityDao getEntityDao() ;
	public PageInfo<SupplierProductExcelQuery> selectPageInfo(SupplierProductExcelQuery productExcelQuery);
	
	//public Product saveProduct(BatchAddProductVo pro,Long supplierId);
	public ApprProduct saveProduct(BatchAddProductVo pro,Long supplierId);
	public int saveProductSku(Long productId,BatchAddProductVo product,Long supplierId,Map<String,List<String>> skuImages);
	public SupplierProductExcel getUndisposedByTimeAsc();
	
	
	public void updateSupplierProductExcel(SupplierProductExcel supplierProductExcel,int status,String processingResult);
}
