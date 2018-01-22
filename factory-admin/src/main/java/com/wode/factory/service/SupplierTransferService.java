package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.SupplierTransfer;
import com.wode.factory.vo.SupplierTransferVo;

/**
 *
 */
public interface SupplierTransferService extends FactoryEntityService<SupplierTransfer> {

	/**
	 * @param 		
	 * @param page		页数
	 * @param pageSize  每页显示条数
	 * @return
	 */
	PageInfo<SupplierTransferVo> getPage(Map<String, Object> params);
}
