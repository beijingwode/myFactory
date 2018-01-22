package com.wode.factory.company.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.company.dao.EnterpriseDao;
import com.wode.factory.company.dao.EnterpriseStructureDao;
import com.wode.factory.company.query.EnterpriseStructureVo;
import com.wode.factory.company.service.EnterpriseStructureService;
import com.wode.factory.model.Enterprise;
import com.wode.factory.model.EnterpriseStructure;
@Service("enterpriseStructureService")
public class EnterpriseStructureServiceImpl extends BasePageServiceImpl<EnterpriseStructure> implements EnterpriseStructureService{
	@Autowired
	EnterpriseStructureDao enterpriseStructureDao;
	@Autowired
	EnterpriseDao enterpriseDao;

	/* 添加企业的组织结构
	 * @see com.wode.factory.company.service.EnterpriseStructureService#addEnterpriseStructure(com.wode.factory.company.model.EnterpriseStructure)
	 */
	@Override
	public Integer addEnterpriseStructure(EnterpriseStructure ent) {
		// TODO Auto-generated method stub
		//关联企业id、type  不能为空，为空表示数据错误
		if(StringUtils.isEmpty(ent.getRelatedEntId())||StringUtils.isEmpty(ent.getType())||StringUtils.isEmpty(ent.getEnterpriseId())){
			return -2;
		}
		List<EnterpriseStructure> list_entStr = this.getBaseDao().selectByModel(ent);
		if(list_entStr.isEmpty()){
			this.getBaseDao().save(ent);
			return 1;
		}else{
			return -1;
		}
	}

	/* 修改企业的组织结构
	 * @see com.wode.factory.company.service.EnterpriseStructureService#updateBatchEnterpriseStructure(com.wode.factory.company.model.EnterpriseStructure)
	 */
	@Override
	public Integer updateBatchEnterpriseStructure(List<EnterpriseStructureVo> ent) {
		// TODO Auto-generated method stub
		List<EnterpriseStructure> li = new ArrayList<EnterpriseStructure>();
		for(EnterpriseStructureVo entVo : JsonUtil.getList(ent.toString(),EnterpriseStructureVo.class)){
			//根据名称查询
			List<Enterprise> list_ent = this.enterpriseDao.selectByPrimaryName(entVo.getEnterpriseName());
			if(list_ent.isEmpty()){
				//-1表示企业不存在
				return -1;
			}else{
				EnterpriseStructure entStr = new EnterpriseStructure();
				entStr.setRelatedEntId(list_ent.get(0).getId());
				entStr.setId(entVo.getId());
				li.add(entStr);
			}
		}
		if(li.isEmpty()){
			return 0;
		}
		return this.enterpriseStructureDao.batchUpdate(li);
	}

	/* 批量删除企业的组织结构
	 * @see com.wode.factory.company.service.EnterpriseStructureService#deleteBatchEnterpriseStructure(java.util.List)
	 */
	@Override
	public Integer deleteBatchEnterpriseStructure(List<EnterpriseStructure> ent) {
		// TODO Auto-generated method stub
		return this.enterpriseStructureDao.deleteBatchByPrimaryKey(ent);
	}

	/* 根据企业id和企业关系查询(1:表示是母公司。2:表示的是子公司。3:表示的是友盟关系公司)
	 * @see com.wode.factory.company.service.EnterpriseStructureService#selectEnterpriseInfo(java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<EnterpriseStructureVo> selectEnterpriseInfo(Long enterpriseId,
			Integer type1, Integer type2) {
		// TODO Auto-generated method stub
		List<EnterpriseStructureVo> list_entStr = new ArrayList<EnterpriseStructureVo>();
		if(!StringUtils.isEmpty(enterpriseId)&&!StringUtils.isEmpty(type1)){
			
			if(StringUtils.isEmpty(type2))
				type2=null;
			
			list_entStr = this.enterpriseStructureDao.selectByEntIdAndType(enterpriseId, type1, type2);
		}else{
			return list_entStr;
		}
		return list_entStr;
	}

	@Override
	protected BasePageDao<EnterpriseStructure> getBaseDao() {
		return enterpriseStructureDao;
	}
	
}
