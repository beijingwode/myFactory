package com.wode.factory.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.ParameterDao;
import com.wode.factory.mapper.ParameterGroupDao;
import com.wode.factory.model.Parameter;
import com.wode.factory.service.ParameterGroupService;
import com.wode.factory.vo.ParameterGroupVo;
@Service("parameterGroupService")
public class ParameterGroupServiceImpl implements ParameterGroupService{

	@Autowired
	private ParameterGroupDao parameterGroupDao;
	
	@Autowired
	private ParameterDao  parameterDao;
	
	@Override
	public PageInfo<ParameterGroupVo> selectParameterGroup(Integer pages,
			Integer size, Long categoryId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("categoryId", categoryId);
		PageHelper.startPage(pages,size);
		List<ParameterGroupVo> listParVo=this.parameterGroupDao.selectParameterGroup(map);
		return new PageInfo<ParameterGroupVo>(listParVo);
	}

	@Override
	public ParameterGroupVo selectById(Long id) {
		// TODO Auto-generated method stub
		return this.parameterGroupDao.selectById(id);
	}

	@Override
	public Integer deleteById(Long id) {
		parameterDao.delByGroupId(id);
		this.parameterGroupDao.deleteById(id);
		return 1;
	}

	@Override
	public Integer updateParameterGroupVo(ParameterGroupVo parVo) {
		parVo.setUpdateDate(Calendar.getInstance().getTime());
		return this.parameterGroupDao.updateById(parVo);
	}

	@Override
	public Integer insertParameterGroupVo(ParameterGroupVo parVo) {
		parVo.setCreateDate(Calendar.getInstance().getTime());
		return this.parameterGroupDao.insertParameterGorp(parVo);
	}

	@Override
	public List<Parameter> selectParamValue(Long id) {
		return parameterDao.findList(id);
	}

	@Override
	public Integer paramBatchAdd(List<Parameter> listParam) {
		return parameterDao.batchAdd(listParam);
	}

	@Override
	public Integer paramBatchUpdate(List<Parameter> listParam) {
		return parameterDao.batchUpdate(listParam);
	}

	@Override
	public Integer paramBatchDel(List<String> ids) {
		return parameterDao.deleteList(ids);
	}
 
}
