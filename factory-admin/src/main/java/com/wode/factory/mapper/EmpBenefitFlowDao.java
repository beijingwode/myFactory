package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.EmpBenefitFlow;
import com.wode.factory.vo.EmpBenefitFlowVo;

/**
 * Created by zoln on 2015/7/24.
 */
public interface EmpBenefitFlowDao extends  FactoryBaseDao<EmpBenefitFlow> {

	public List<EmpBenefitFlowVo> selectByMap(Map<String,Object> map);
}
