
package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.AttributeDao;
import com.wode.factory.mapper.AttributeOptionDao;
import com.wode.factory.model.Attribute;
import com.wode.factory.model.AttributeOption;
import com.wode.factory.service.AttributeService;
import com.wode.factory.vo.AttributeVo;

@Service("AttributeService")
public class AttributeServiceImpl implements AttributeService {
	
	@Autowired
	private AttributeDao attributeDao;
	
	@Autowired
	private AttributeOptionDao attributeOptionDao;



	@Override
	public PageInfo<AttributeVo> findList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<AttributeVo> list = attributeDao.findList(params);
		return new PageInfo<AttributeVo>(list);
	}



	@Override
	public AttributeVo getById(Long id) {
		return attributeDao.getById(id);
	}



	@Override
	public Integer delete(Long id) {
		return attributeDao.delete(id);
	}



	@Override
	public Integer add(Attribute pojo) {
		return attributeDao.insert(pojo);
	}



	@Override
	public Integer update(Attribute pojo) {
		return attributeDao.update(pojo);
	}



	@Override
	public List<AttributeOption> selectAttrValue(Long id){
		return attributeOptionDao.findList(id);
	}



	@Override
	public Integer optionDelete(List<String> ids) {
		return attributeOptionDao.deleteList(ids);
	}



	@Override
	public Integer optionUpdate(List<AttributeOption> listPojo) {
		return attributeOptionDao.batchUpdate(listPojo);
	}



	@Override
	public Integer optionAdd(List<AttributeOption> listOption) {
		return attributeOptionDao.batchAdd(listOption);
	}


	
}
