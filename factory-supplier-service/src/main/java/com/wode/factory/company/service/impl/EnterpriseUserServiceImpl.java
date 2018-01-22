package com.wode.factory.company.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.db.DBUtils;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.company.dao.EnterpriseDao;
import com.wode.factory.company.dao.EnterpriseUserDao;
import com.wode.factory.company.query.EmpLevelCountVo;
import com.wode.factory.company.query.EnterpriseUserTakeOrderVo;
import com.wode.factory.company.query.EnterpriseUserVo;
import com.wode.factory.company.query.EnterpriseVo;
import com.wode.factory.company.service.EnterpriseUserService;
import com.wode.factory.model.Enterprise;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.service.UserService;
@Service("enterpriseUserService")
public class EnterpriseUserServiceImpl extends BasePageServiceImpl<EnterpriseUser> implements EnterpriseUserService{
	
	@Autowired
	EnterpriseUserDao enterpriseUserDao;
	@Autowired
	EnterpriseDao enterpriseDao;
	@Autowired
	UserService userService;
	@Autowired
	DBUtils dbUtils;

	 /**
	  * 注册厂商家信息
	 * @param userId
	 * @param email
	 * @param creatDate
	 * @return
	 */
	@Override
	public Integer activeFactoryUser(Long id) {
		UserFactory user = userService.getById(id);
		if(!StringUtils.isEmpty(user)){
			user.setEnabled(1);
		}else{
			return 0;
		}
		this.userService.update(user);
		return 1;
	}

	/* 查询账号是否是企业管理员
	 * @see com.wode.factory.company.service.EnterpriseUserService#getEnterpriseManagerUser(java.lang.Long)
	 */
	@Override
	public EnterpriseUserVo getEnterpriseManagerUser(String account) {
		EnterpriseUserVo entUser = this.enterpriseUserDao.selectByAccount(account);
		if(StringUtils.isEmpty(entUser)){
			return null;
		}else{
			//type 1:为管理员  2:为员工
			if(entUser.getType().equals(1)){
				return entUser;
			}else{
				return null;
			}
		}
	}

	/* 根据id查询
	 * @see com.wode.factory.company.service.EnterpriseUserService#selectById(java.lang.Long)
	 */
	@Override
	public EnterpriseUser selectById(Long id) {
		if(StringUtils.isEmpty(id)){
			return new EnterpriseUser();
		}else{
			EnterpriseUser entUser = this.getBaseDao().getById(id);
			//entUser.setEnterpriseId(null);
			return entUser;
		}
	}
	
	/* 修改数据
	 * @see com.wode.factory.company.service.impl.BaseServiceImpl#updateByPrimaryKeySelective(java.lang.Object)
	 */
	@Override
	public Integer updateById(EnterpriseUser entUser,String mark){
		Integer update_status=0;
		if(entUser.getType()!=null &&entUser.getType()!=1){
			entUser.setType(2);//2表示员工，1表示管理员
		}
		entUser.setLogout(Byte.valueOf(0+""));//0表示未注销
		//entUser.setEmail(null);
		//只修改企业的用户信息
		if("update_ent".equals(mark)){//"");
			update_status=this.getEntityDao().update(entUser);
		//修改所有的用户信息(共通、厂、企业员工)
		}else if("update_all".equals(mark)){
			Integer userFactoryUpdate=0;
			Integer entUpdate=0;
			//修改厂中的用户信息
			UserFactory uf = userService.getById(entUser.getId());
			if(StringUtils.isEmpty(uf)){
				return 0;
			}else{
				if(entUser.getEmail()!=null) {
					uf.setEmail(entUser.getEmail());
				}
				if (entUser.getUserName() ==null && entUser.getPhone()!=null) {//用户名为空 手机不为空
					uf.setUserName(entUser.getPhone());
				}else if(entUser.getUserName() ==null && entUser.getEmail()!=null){//用户名为空 邮箱不为空
					uf.setEmail(entUser.getEmail());
				}
				
				uf.setPhone(entUser.getPhone());
				userService.update(uf);
				userFactoryUpdate=1;
			}
			//修改共通用户  (controller已经进行了修改)
			
			//修改企业用户
			if (entUser.getUserName()==null && entUser.getPhone()!=null) {//用户名
				entUser.setUserName(entUser.getPhone());
			}else if(entUser.getUserName()==null && entUser.getEmail()!=null){
				entUser.setUserName(entUser.getEmail());
			}
			
			entUpdate=this.getEntityDao().update(entUser);
			if(userFactoryUpdate>0&&entUpdate>0){
				update_status=1; 
			}else{
				update_status=0;
			}
			
		}else{
			return 0;
		}
		return update_status;
	}
	
	/* 删除员工(删除即注销该员工账号)
	 * @see com.wode.factory.company.service.EnterpriseUserService#deleteEnterpriseUser(java.lang.Long)
	 */
	@Override
	public Integer deleteEnterpriseUser(Long id) {
		if(StringUtils.isEmpty(id)){
			return 0;
		}else{
			UserFactory uf = userService.getById(id);
			if(StringUtils.isEmpty(uf)){
				return 0;
			}else{
				//supplierTicketService.recoveryUserTicket(uf.getSupplierId(), id);
				// 清空企业默认头像
				if(!StringUtils.isEmpty(uf.getAvatar())) {
					Enterprise ent= enterpriseDao.getById(uf.getSupplierId());
					if(StringUtils.isEquals(uf.getAvatar(), ent.getEmpDefultAvatar())){
						uf.setAvatar("");
					}
				}

				uf.setEmployeeType(0);
				uf.setSupplierId(null);
				uf.setShopLink(null);
				userService.update(uf);
				return this.enterpriseUserDao.updateLogoutEmp(id);
			}
		}
	}
	@Override
	public List<EmpLevelCountVo> selectLevelCount(EmpLevelCountVo vo) {
		return enterpriseUserDao.selectLevelCount(vo);
	}
	
	@Override
	public ActResult updateEmpWelfareLevel(Long enterpriseId,Integer welfareLevel,EnterpriseVo entUserVo) {
		ActResult act = new ActResult();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("enterpriseId", enterpriseId);
		map.put("welfareLevel", welfareLevel);
		//根据设置的福利级别查询（大于设置的福利级别）
		List<EnterpriseUser> list_entUser = this.enterpriseUserDao.selectExceedWelfareLevelEmp(map);
		//根据企业id查询企业信息
		EnterpriseVo entVo = this.enterpriseDao.selectById(enterpriseId);
		if(!StringUtils.isNullOrEmpty(entVo)){
			Enterprise ent = new Enterprise();
			ent.setId(entVo.getId());
			ent.setWelfareLevel(welfareLevel);
			this.enterpriseDao.updateByPrimaryKeySelective(ent);
//				entUserVo.setWelfareLevel(welfareLevel);
			
		}
		if(list_entUser.isEmpty()){
			act.setData(welfareLevel);
			act.setSuccess(true);
			return act;
		}else{
			map.put("list", list_entUser);
			Integer i = this.enterpriseUserDao.updateBatchEmpWelfareLevel(map);
			if(i>0){
				act.setData(welfareLevel);
				act.setSuccess(true);
			}else{
				act.setSuccess(false);
			}
			return act;
		}
	}
	@Override
	public String selectMaxEmpNumber(Long enterpriseId) {
		String maxEmpNumber = this.enterpriseUserDao.selectMaxEmpNumber(enterpriseId);
		if(StringUtils.isNullOrEmpty(maxEmpNumber)){
			return "10000";
		}else{
			//全部为数字 
			if(maxEmpNumber.matches("^[0-9]*$")){
				Long v=Long.valueOf(maxEmpNumber);
				if(v<10000) {
					v=10000L;
				}
				return v+1+"";
			}
			//全部为字母
			if(maxEmpNumber.matches("[a-zA-Z]+")){
				return maxEmpNumber+"1";
			}
			StringBuffer sb = new StringBuffer();
			//字符串中有数字
			for(int i = 0; i <maxEmpNumber.length();i++){
				//是否为数字
				if(Character.isDigit(maxEmpNumber.charAt(i))){
					sb.append(Integer.valueOf(maxEmpNumber.charAt(i))+1);
				}else{
					sb.append(maxEmpNumber.charAt(i));
				}
			}
			return sb.toString();
		}
	}
	
	
	@Override
	public EnterpriseUser selectByempNumber(String empNumber, Long enterpriseId) {
		EnterpriseUser entUser = new EnterpriseUser();
		entUser.setEmpNumber(empNumber);
		entUser.setEnterpriseId(enterpriseId);
		List<EnterpriseUser> page = this.enterpriseUserDao.selectByModel(entUser);
		//如果该员工存在，返回true
		return (page.isEmpty()?null:page.get(0));
	}
	/* 修改厂平台用户信息
	 * @see com.wode.factory.company.service.EnterpriseUserService#updateFactoryUser(com.wode.factory.company.model.EnterpriseUser)
	 */
	@Override
	public void updateFactoryUserPhone(EnterpriseUser entUser) {
		UserFactory uf = userService.getById(entUser.getId());
		if(!StringUtils.isNullOrEmpty(uf)){
			uf.setPhone(entUser.getPhone());
			userService.update(uf);
		}
	}
	@Override
	protected BasePageDao<EnterpriseUser> getBaseDao() {
		return enterpriseUserDao;
	}



	@Override
	public EnterpriseUserVo selectByAccount(String account) {
		return this.enterpriseUserDao.selectByAccount(account);
	}



	@Override
	public int updateEmp(EnterpriseUser entUser) {
		return this.enterpriseUserDao.updateEmp(entUser);
	}



	@Override
	public int insertSelective(EnterpriseUser entUser) {
		return this.enterpriseUserDao.insertSelective(entUser);
	}



	@Override
	public int insert(EnterpriseUser entUser) {
		return this.enterpriseUserDao.insert(entUser);
	}

	/* 分页查询
	 * @see com.wode.company.service.EnterpriseUserService#findByPage(com.wode.company.query.EnterpriseUserVo)
	 */
	@Override
	public PageInfo<EnterpriseUserVo> findByPage(EnterpriseUserVo entUserVo) {
		// TODO Auto-generated method stub
		Boolean start = StringUtils.isEmpty(entUserVo.getStartSeniority());//开始工龄
		Boolean end = StringUtils.isEmpty(entUserVo.getEndSeniority());//结束工龄
		//开始工龄为空,结束工龄不为空
		if(start&&!end){
			entUserVo.setStartSeniority(entUserVo.getEndSeniority());	
		}
		//开始工龄不为空,结束工龄为空
		if(!start&&end){
			entUserVo.setEndSeniority(entUserVo.getStartSeniority());
		}
//		if((start&&!end)||(!start&&end)){
//			entUserVo.setStartSeniority(start?entUserVo.getEndSeniority():entUserVo.getStartSeniority());
//		}
		return this.enterpriseUserDao.selectPageInfo(entUserVo);
	}

	@Override
	public EnterpriseUserVo selectByEmpId(Long id) {
		return this.enterpriseUserDao.selectByEmpId(id);
	}

	@Override
	public PageInfo<EnterpriseUserTakeOrderVo> findTakeOrderListByPage(EnterpriseUserTakeOrderVo takeOrderVo) {
		return this.enterpriseUserDao.selectTakeOrderListPageInfo(takeOrderVo);
	}
}
