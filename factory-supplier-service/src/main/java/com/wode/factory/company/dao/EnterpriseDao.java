package com.wode.factory.company.dao;

import java.util.List;
import java.util.Map;

import com.wode.factory.company.query.EnterpriseVo;
import com.wode.factory.model.Enterprise;


public interface EnterpriseDao extends BasePageDao<Enterprise>{
	
	/**
	 * 根据企业名称查询
	 * @param name
	 * @return
	 */
	public List<Enterprise> selectByPrimaryName(String name);
	/**根据id查找企业信息
	 * @param id
	 * @return
	 */
	public EnterpriseVo selectById(Long id);
	public Integer updateByPrimaryKeySelective(Enterprise ent);
	public Integer updateByPrimaryKey(Enterprise ent);
	public Integer insertSelective(EnterpriseVo entVo);
	public Integer insert(Enterprise ent);
	
	Long getFirstShopId(Long id);

	public void updateEmpDefultAvatars(Map<String,Object> param);
	
	Enterprise findByEmailPostfix(String emailPostfix);
}