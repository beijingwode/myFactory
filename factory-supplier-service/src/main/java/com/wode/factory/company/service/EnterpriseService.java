package com.wode.factory.company.service;

import com.wode.factory.company.query.EnterpriseVo;
import com.wode.factory.model.Enterprise;



public interface EnterpriseService extends BasePageService<Enterprise>{
	/**
	 * 添加(修改)企业信息
	 * @param ent
	 * @return
	 */
	public Integer updateOrInsertEnterprise(Enterprise ent,Long enterpriseId);
	
	/**
	 * 根据企业名称查询
	 * @param name
	 * @return
	 */
	public Enterprise selectByPrimaryName(String name);
	public EnterpriseVo  selectById(Long id);
	public Integer insertSelective(EnterpriseVo entVo);

	Long getFirstShopId(Long id);
	
	void updateEnterpriseSet(Long id,String emailPostfix1,String emailPostfix2,String emailPostfix3,String empDefultAvatar,String canSearch);

	Enterprise findByEmailPostfix(String emailPostfix);
}
