package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.SupplierTicketFlow;
import com.wode.factory.vo.SupplierTicketFlowVo;

/**
 * Created by zoln on 2015/7/24.
 */
public interface SupplierTicketFlowDao extends  FactoryBaseDao<SupplierTicketFlow> {

	public List<SupplierTicketFlow> selectForClear();
	
	/**
	 * 分页条件查询对账单信息
	 * @param map
	 * @return
	 */
	public List<SupplierTicketFlowVo> findPageList(Map<String, Object> map);
}
