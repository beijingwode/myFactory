package com.wode.factory.company.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.company.query.EmpLevelCountVo;
import com.wode.factory.company.query.EnterpriseUserTakeOrderVo;
import com.wode.factory.company.query.EnterpriseUserVo;
import com.wode.factory.model.EnterpriseUser;

public interface EnterpriseUserDao extends BasePageDao<EnterpriseUser> {
//	EnterpriseUser selectByPrimaryKey(Long id);
	/**根据账号查询
	 * @param account
	 * @return
	 */
	EnterpriseUserVo selectByAccount(String account);
	/**
	 * 分页(条件)查询员工
	 * @param entUserVo
	 * @return
	 */
	PageInfo<EnterpriseUserVo> selectPageInfo(EnterpriseUserVo entUserVo);
	/**
	 * 注销员工
	 * @param id
	 * @return
	 */
	Integer updateLogoutEmp(Long id);
	
	/**
	 * 查看各福利级别的人数（未注销）
	 * @return
	 */
	List<EmpLevelCountVo> selectLevelCount(EmpLevelCountVo vo);
	/**查看超过该福利级别的员工
	 * @param map
	 * @return
	 */
	List<EnterpriseUser> selectExceedWelfareLevelEmp(Map map);
	/**批量修改员工的福利等级
	 * @param map
	 * @return
	 */
	Integer updateBatchEmpWelfareLevel(Map map);
	/**查询最大的员工序号
	 * @param enterpriseId
	 * @return
	 */
	String selectMaxEmpNumber(Long enterpriseId);
	int updateEmp(EnterpriseUser entUser);
	int insertSelective(EnterpriseUser entUser);
	int insert(EnterpriseUser entUser);

	EnterpriseUserVo selectByEmpId(Long id);
	PageInfo<EnterpriseUserTakeOrderVo> selectTakeOrderListPageInfo(EnterpriseUserTakeOrderVo takeOrderVo);
	
}