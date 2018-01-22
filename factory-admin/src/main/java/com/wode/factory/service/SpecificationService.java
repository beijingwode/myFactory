package com.wode.factory.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.SpecificationValue;
import com.wode.factory.vo.SpecificationVo;

public interface SpecificationService {

	public PageInfo<SpecificationVo> selectSpecification(Integer pages,
			Integer size,Long categoryId);
	
	public List<SpecificationValue> selectSpecificationValue(Long specificationId);
	

	public SpecificationVo selectById(Long id);

	public Integer deleteById(Long id);

	public Integer updateSpecificationVo(SpecificationVo speVo);

	public Integer insertSpecificationVo(SpecificationVo speVo);
	
	public Integer updateSpecificationValue(String[] add,String[] update,String[] del,Long speId);
	
}
