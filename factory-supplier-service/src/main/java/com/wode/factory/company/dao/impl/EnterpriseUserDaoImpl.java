/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.company.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;
import com.wode.factory.company.dao.EnterpriseUserDao;
import com.wode.factory.company.query.EmpLevelCountVo;
import com.wode.factory.company.query.EnterpriseUserTakeOrderVo;
import com.wode.factory.company.query.EnterpriseUserVo;
import com.wode.factory.model.EnterpriseUser;

@Repository("enterpriseUserDao")
public class EnterpriseUserDaoImpl extends BasePageDaoImpl<EnterpriseUser> implements EnterpriseUserDao{
	public String getIbatisMapperNamesapce() {
		return "EnterpriseUserMapper";
	}

	@Override
	public EnterpriseUserVo selectByAccount(String account) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".selectByAccount", account);
	}
	
	/* 注销员工
	 * @see com.wode.factory.company.dao.EnterpriseUserDao#updateLogoutEmp(java.lang.Long)
	 */
	@Override
	public Integer updateLogoutEmp(Long id) {
		// TODO Auto-generated method stub  
		return getSqlSession().update(getIbatisMapperNamesapce()+".updateLogoutEmp",id);
	}
	
	/*
	 * 查看各福利级别的人数（未注销）
	 * @see com.wode.factory.company.dao.EnterpriseUserDao#selectLevelCount(com.wode.factory.company.query.EnterpriseUserVo)
	 */
	@Override
	public List<EmpLevelCountVo> selectLevelCount(EmpLevelCountVo vo) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectLevelCount", vo);
	}
	@Override
	public List<EnterpriseUser> selectExceedWelfareLevelEmp(Map map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectExceedWelfareLevelEmp", map);
	}
	@Override
	public Integer updateBatchEmpWelfareLevel(Map map) {
		// TODO Auto-generated method stub
		return getSqlSession().update(getIbatisMapperNamesapce()+".updateBatchEmpWelfareLevel", map);
	}
	@Override
	public String selectMaxEmpNumber(Long enterpriseId) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".selectMaxEmpNumber", enterpriseId);
	}

	@Override
	public void saveOrUpdate(EnterpriseUser entity) throws DataAccessException {

		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public int updateEmp(EnterpriseUser entUser) {
		// TODO Auto-generated method stub
		return getSqlSession().update(getIbatisMapperNamesapce()+".updateEmp", entUser);
	}

	@Override
	public int insertSelective(EnterpriseUser entUser) {
		if(entUser.getEmpNumber().length()>20) {
			String empNumber = entUser.getEmpNumber().substring(0, 20);
			entUser.setEmpNumber(empNumber);
		}
		return getSqlSession().insert(getIbatisMapperNamesapce()+".insertSelective", entUser);
	}

	@Override
	public int insert(EnterpriseUser entUser) {
		return getSqlSession().insert(getIbatisMapperNamesapce()+".insert", entUser);
	}

	/* 分页查询
	 */
	@Override
	public PageInfo<EnterpriseUserVo> selectPageInfo(EnterpriseUserVo entUserVo) {
		// TODO Auto-generated method stub
		 List<EnterpriseUserVo> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".selectPageInfo", entUserVo, new RowBounds(entUserVo.getPageNumber(), entUserVo.getPageSize()));
		return new PageInfo<EnterpriseUserVo>(list);
	}
	@Override
	public Long getId(EnterpriseUser model) {
		return model.getId();
	}

	@Override
	public EnterpriseUserVo selectByEmpId(Long id) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".selectByEmpId",id);
	}

	@Override
	public PageInfo<EnterpriseUserTakeOrderVo> selectTakeOrderListPageInfo(EnterpriseUserTakeOrderVo takeOrderVo) {
		List<EnterpriseUserTakeOrderVo> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".selectTakeOrderListPageInfo", takeOrderVo, new RowBounds(takeOrderVo.getPageNumber(), takeOrderVo.getPageSize()));
		return new PageInfo<EnterpriseUserTakeOrderVo>(list);
	}
}
