package com.wode.factory.company.service;


import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wode.common.util.ActResult;
import com.wode.factory.company.query.EmpLevelCountVo;
import com.wode.factory.company.query.EnterpriseUserTakeOrderVo;
import com.wode.factory.company.query.EnterpriseUserVo;
import com.wode.factory.company.query.EnterpriseVo;
import com.wode.factory.model.EnterpriseUser;

public interface EnterpriseUserService extends BasePageService<EnterpriseUser>{
	/**
	 * 根据账号判断是不是企业管理员
	 * @param userId
	 * @return
	 */
	public EnterpriseUserVo getEnterpriseManagerUser(String account);
	/**
	 * 注册企业用户
	 * 
	 * @param email
	 * @param password
	 * @param registerNumber
	 * @param ip
	 * @return
	 */
//	public ActResult<EnterpriseUser> register(String email,String password,String registerNumber,String ip);
	
	/**激活厂用户
	 * @param id
	 * @return
	 */
	public Integer activeFactoryUser(Long id);
	/**修改厂中用户信息
	 * @param id
	 */
	public void updateFactoryUserPhone(EnterpriseUser entUser);
	
	/*####################################企业用户操作方法############################################*/
	/**
	 * 分页查询
	 * @param entUserVo
	 * @return
	 */
	PageInfo<EnterpriseUserVo> findByPage(EnterpriseUserVo entUserVo);
	/**
	 * 根据id查询信息
	 * @param id
	 * @return
	 */
	EnterpriseUser selectById(Long id);
	/**
	 * 修改
	 * @param entUser
	 * @return
	 */
	Integer updateById(EnterpriseUser entUser,String mark);

	/**
	 * 查看各福利级别的人数（未注销）
	 * @return
	 */
	public List<EmpLevelCountVo> selectLevelCount(EmpLevelCountVo vo);
	/**
	 * 删除员工(删除即注销该员工账号)
	 * @param id
	 * @return
	 */
	Integer deleteEnterpriseUser(Long id);
	/**修改员工的福利级别
	 * @param enterpriseId
	 * @param welfareLevel
	 * @return
	 */
	ActResult updateEmpWelfareLevel(Long enterpriseId,Integer welfareLevel,EnterpriseVo entVo);
	/**查询最大的员工序号
	 * @param enterpriseId
	 * @return
	 */
	String selectMaxEmpNumber(Long enterpriseId);
	/**
	 * 查询该企业是否有重复的员工序号
	 * @param name
	 * @param enterpriseId
	 * @return
	 */
	EnterpriseUser selectByempNumber(String empNumber,Long enterpriseId);
	EnterpriseUserVo selectByAccount(String account);
	int updateEmp(EnterpriseUser entUser);
	int insertSelective(EnterpriseUser entUser);
	int insert(EnterpriseUser entUser);
	/**
	 * 通过id获取vo
	 * @param id
	 * @return
	 */
	EnterpriseUserVo selectByEmpId(Long id);

	public PageInfo<EnterpriseUserTakeOrderVo> findTakeOrderListByPage(EnterpriseUserTakeOrderVo takeOrderVo);
}
