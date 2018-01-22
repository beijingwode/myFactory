package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.SupplierTransfer;
import com.wode.factory.vo.SupplierTransferVo;

/**
 * Created by zoln on 2015/7/24.
 */
public interface SupplierTransferDao extends  FactoryBaseDao<SupplierTransfer> {

	/**
	 * 分页条件查询对账单信息
	 * @param map
	 * @return
	 */
	public List<SupplierTransferVo> findPageList(Map<String, Object> map);
}
