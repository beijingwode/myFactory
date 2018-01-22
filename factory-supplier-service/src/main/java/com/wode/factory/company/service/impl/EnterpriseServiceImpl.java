package com.wode.factory.company.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.util.StringUtils;
import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.company.dao.EnterpriseDao;
import com.wode.factory.company.query.EnterpriseVo;
import com.wode.factory.company.service.EnterpriseService;
import com.wode.factory.model.Enterprise;
@Service("enterpriseService")
public class EnterpriseServiceImpl extends BasePageServiceImpl<Enterprise> implements EnterpriseService{
	@Autowired
	EnterpriseDao enterpriseDao;

	
	@Override
	public Integer updateOrInsertEnterprise(Enterprise ent,Long enterpriseId) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(ent)||StringUtils.isEmpty(enterpriseId)){
			return 0;
		}else{
			Integer i = 0;
			if(StringUtils.isNullOrEmpty(ent.getId())){
				Enterprise e = this.getBaseDao().getById(enterpriseId);
				//如果数据库存在企业id，将不能插入数据
				if(!StringUtils.isEmpty(e))
					return 0;
				
				ent.setId(enterpriseId);
				ent.setWelfareLevel(20);
				this.enterpriseDao.insert(ent);
			}else{
				i = this.enterpriseDao.updateByPrimaryKey(ent);
			}
			return 1;
		}
	}

	@Override
	public Enterprise selectByPrimaryName(String name) {
		List<Enterprise> list_ent = this.enterpriseDao.selectByPrimaryName(name);
		if(list_ent.isEmpty()){
			return null;
		}else{
			return list_ent.get(0);
		}
	}

	@Override
	public EnterpriseVo selectById(Long id) {
		return this.enterpriseDao.selectById(id);
	}

	@Override
	protected BasePageDao<Enterprise> getBaseDao() {
		return enterpriseDao;
	}

	@Override
	public Integer insertSelective(EnterpriseVo entVo) {
		return this.enterpriseDao.insertSelective(entVo);
	}

	@Override
	public Long getFirstShopId(Long id) {
		return this.enterpriseDao.getFirstShopId(id);
	}

	@Override
	@Transactional
	public void updateEnterpriseSet(Long id, String emailPostfix1, String emailPostfix2, String emailPostfix3,
			String empDefultAvatar, String canSearch) {
		Enterprise ent = this.getById(id);
		
		if(!StringUtils.isEquals(ent.getEmpDefultAvatar(), empDefultAvatar)) {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("id", id);
			param.put("oldAvatar", StringUtils.isEmpty(ent.getEmpDefultAvatar())?"":ent.getEmpDefultAvatar());
			param.put("newAvatar", empDefultAvatar);
			param.put("shopLink", StringUtils.isEmpty(empDefultAvatar)?null:(this.getFirstShopId(id)+""));
			this.enterpriseDao.updateEmpDefultAvatars(param);
		}
		
		ent.setEmailPostfix1(emailPostfix1);
		ent.setEmailPostfix2(emailPostfix2);
		ent.setEmailPostfix3(emailPostfix3);
		ent.setEmpDefultAvatar(empDefultAvatar);
		ent.setCanSearch(canSearch);
		this.enterpriseDao.updateByPrimaryKey(ent);
	}

	@Override
	public Enterprise findByEmailPostfix(String emailPostfix) {
		return this.enterpriseDao.findByEmailPostfix(emailPostfix);
	}
	 
}
