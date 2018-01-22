package com.wode.common.beetl.function;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.wode.factory.mapper.ParameterGroupDao;
import com.wode.factory.vo.ParameterGroupVo;

@Component
public class ParameterGroupFunction {
	@Resource
	private ParameterGroupDao parameterGroupDao;
	
	public List<ParameterGroupVo> getParameterGroupCategory(){
		
		return this.parameterGroupDao.selectParameterGorpCategory();
	}
	
}
