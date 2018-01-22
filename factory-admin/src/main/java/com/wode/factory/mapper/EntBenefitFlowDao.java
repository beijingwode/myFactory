package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.EntBenefitFlow;
import com.wode.factory.vo.EntBenefitFlowVo;

/**
 * Created by zoln on 2015/7/24.
 */
public interface EntBenefitFlowDao extends  FactoryBaseDao<EntBenefitFlow> {
	
	List<EntBenefitFlowVo> findList(Map<String, Object> map);
}
