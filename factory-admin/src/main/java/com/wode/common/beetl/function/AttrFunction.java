package com.wode.common.beetl.function;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.wode.factory.mapper.AttributeDao;
import com.wode.factory.vo.SpecificationVo;

@Component
public class AttrFunction {
	@Resource
	private AttributeDao attributeDao;
	
	public List<SpecificationVo> getAttrCategory(){
		
		return this.attributeDao.selectAttrCategory();
	}
	
}
