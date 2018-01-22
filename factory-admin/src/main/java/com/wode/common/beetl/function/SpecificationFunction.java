package com.wode.common.beetl.function;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.wode.factory.mapper.SpecificationDao;
import com.wode.factory.vo.SpecificationVo;

@Component
public class SpecificationFunction {
	@Resource
	private SpecificationDao specificationDao;
	
	public List<SpecificationVo> getSpecificationCategory(){
		
		return this.specificationDao.selectSpecificationCategory();
	}
	
}
