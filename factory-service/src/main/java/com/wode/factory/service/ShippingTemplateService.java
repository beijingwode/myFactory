package com.wode.factory.service;

import java.util.List;

import com.wode.common.stereotype.QueryCached;
import com.wode.factory.model.ShippingTemplate;

public interface ShippingTemplateService {
	
	/**
	 * 根据商家id查询商家运费模板 version = 2的
	 * @param supplierId
	 * @return
	 */
	@QueryCached(keyPreFix="selectTemplateBySupplierId")
	public ShippingTemplate selectTemplateBySupplierId(Long supplierId);

	public ShippingTemplate selectTemplateBySupplierIds(Long supplierId);

}
