/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.db.DBUtils;
import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserIm;
import com.wode.factory.model.UserImGroup;
import com.wode.factory.model.UserImGroupMember;
import com.wode.factory.outside.service.EasemobIMService;
import com.wode.factory.service.GroupBuyService;
import com.wode.factory.service.GroupBuyUserService;
import com.wode.factory.user.dao.UserContactsDao;
import com.wode.factory.user.dao.UserDao;
import com.wode.factory.user.model.UserContacts;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.service.UserContactsService;
import com.wode.factory.user.service.UserImGroupMemberService;
import com.wode.factory.user.service.UserImGroupService;
import com.wode.factory.user.service.UserImService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.util.EasemobIMUtils;
import com.wode.factory.user.vo.ContactsVo;
import com.wode.factory.user.vo.UserContactsVo;
import com.wode.factory.vo.GroupBuyUserVo;
import com.wode.factory.vo.GroupBuyVo;

@SuppressWarnings("unchecked")
@Service("userContactsService")
public class UserContactsServiceImpl extends FactoryEntityServiceImpl<UserContacts> implements  UserContactsService{
	@Autowired
	private UserContactsDao dao;

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserService userService;
	@Autowired
	UserBalanceService userBalanceService;
	
	@Autowired
	private GroupBuyService groupBuyService; 
	@Autowired
	private GroupBuyUserService groupBuyUserService;
	
	@Autowired
	private UserImGroupService userImGroupService;
	@Autowired
	private UserImGroupMemberService userImGroupMemberService;
	
	@Autowired
	private UserImService userImService;
	
	@Autowired
	DBUtils dbUtils;
	
	@Override
	public UserContactsDao getDao() {
		return dao;
	}

	@Override
	public Long getId(UserContacts entity) {
		return entity.getId();
	}

	@Override
	public void setId(UserContacts entity, Long id) {
		if(entity!=null) {
			entity.setId(id);
		}
	}

	@Override
	public UserContacts selectOneByModel(UserContacts query) {
		List<UserContacts> ls = this.selectByModel(query);
		if(ls!=null && !ls.isEmpty()) {
			return ls.get(0);
		}
		return null;
	}

	@Override
	public ActResult<List<UserContactsVo>> normalUserFriendsList(Long userId) {
		UserContacts query = new UserContacts();
		query.setUserId(userId);
		query.setFirendType("1");
		List<UserContactsVo> userFriends = dao.selectVoByModel(query);
		
		if(userFriends.isEmpty()){
			return ActResult.successSetMsg("您还没有添加亲友");
		}else{

			//查询亲友的内购券使用情况
			for(UserContactsVo ufVo : userFriends){
				UserFactory u = this.userService.getById(ufVo.getEmployeeid());
				if(u != null) {
					ufVo.setPhoneNumber(u.getPhone());
				}
				ufVo.setTotalBalance(null);
				ufVo.setBalance(null);
				ufVo.setState(1);
			}
			
			return ActResult.success(userFriends);
		}
	}

	@Override
	public ActResult<List<UserContactsVo>> empUserFriendsList(Long empId) {
		UserContacts query = new UserContacts();
		query.setUserId(empId);
		query.setFirendType("1");
		List<UserContactsVo> userFriends = dao.selectVoByModel(query);
		
		if(userFriends.isEmpty()){
			return ActResult.successSetMsg("您还没有添加亲友");
		}else{
			//查询亲友的内购券使用情况
			for(UserContactsVo ufVo : userFriends){
				UserBalance ub = this.userBalanceService.findByUserAndName(ufVo.getUserid(), "companyTicket");

				UserFactory u = this.userService.getById(ufVo.getUserid());
				if(u != null) {
					ufVo.setPhoneNumber(u.getPhone());
				}
				
				if(StringUtils.isNullOrEmpty(ub)){
					ufVo.setTotalBalance(0);
					ufVo.setBalance(0);
				}else{
					ufVo.setTotalBalance(NumberUtil.toInt(ub.getBalanceTotal()));
					ufVo.setBalance(NumberUtil.toInt(ub.getBalance()));
				}
				ufVo.setState(1);
			}
			return ActResult.success(userFriends);
		}
	}

	@Override
	public List<ContactsVo> selectContactsByEmployee(Long supplierId, Long userId) {
		// 先查询出好友列表包括临时好友
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("userId", userId);
		List<ContactsVo> rtn = dao.selectFriendWithIm(param);

		param.put("supplierId", supplierId);
		List<ContactsVo> colleages = userDao.findColleagueWithIm(param);

		// 好友及同事合并，同事优先
		mergeContacts(rtn, colleages);

		// 用户信息设置，并去除临时好友
		setUserInfo(rtn,true);
		
		return rtn;
	}

	@Override
	public ContactsVo findColleague(Long supplierId, Long userId, Long friendId) {
		// 先查询出好友列表包括临时好友
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("friendId", friendId);
		List<ContactsVo> rtn = dao.selectFriendWithIm(param);

		param.put("supplierId", supplierId);
		List<ContactsVo> colleages = userDao.findColleagueWithIm(param);
		
		// 好友及同事合并，同事优先
		mergeContacts(rtn, colleages);

		// 用户信息设置，并去除临时好友
		setUserInfo(rtn,false);
		
		return (rtn!=null && !rtn.isEmpty())?rtn.get(0):null;
	}

	@Override
	public ContactsVo findOneSelf(Long userId) {
		ContactsVo vo = new ContactsVo();
		EnterpriseUser eu =userService.getEmpById(userId);
		
		if(eu!=null) {
			vo.setUserId(eu.getId());
			vo.setWorkName(eu.getName());
			vo.setDuty(eu.getDuty());
			vo.setPhone(eu.getPhone());
			vo.setNickname(eu.getName());
		} else {
			vo.setUserId(userId);
		}
		List<ContactsVo> rtn = new ArrayList<ContactsVo>();
		rtn.add(vo);
		setUserInfo(rtn,false);
		
		return vo;
	}
	
	@Override
	public List<ContactsVo> selectContactsByUser(Long userId) {
		return selectContactsByUser(userId,null,true);
	}

	@Override
	public ContactsVo selectContactByUser(Long userId, Long friendId) {
		List<ContactsVo> lst = selectContactsByUser(userId,friendId,false);
		return (lst!=null && !lst.isEmpty())?lst.get(0):null;
	}
	
	private List<ContactsVo> selectContactsByUser(Long userId, Long friendId,boolean mustFriend) {
		// 先查询出好友列表包括临时好友
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("firendType", "1");
		if(friendId!=null) {
			param.put("friendId", friendId);
		}
		List<ContactsVo> rtn = dao.selectFriendWithIm(param);
		setUserInfo(rtn,mustFriend);
		
		return rtn;
	}


	private void mergeContacts(List<ContactsVo> rtn, List<ContactsVo> colleages) {
		// 好友及同事合并，同事优先
		Map<Long,ContactsVo> friendMap = new HashMap<Long,ContactsVo>();
		for (ContactsVo contactsVo : rtn) {
			friendMap.put(contactsVo.getUserId(), contactsVo);
		}
		
		for (ContactsVo contactsVo : colleages) {
			if(friendMap.containsKey(contactsVo.getUserId())) {
				ContactsVo friend=friendMap.get(contactsVo.getUserId());
				friend.setRelationType(3);
				friend.setDuty(contactsVo.getDuty());
				friend.setPhone(contactsVo.getPhone());
				friend.setWorkName(contactsVo.getWorkName());
				friend.setNickname(contactsVo.getNickname());
			} else {
				rtn.add(contactsVo);
			}
		}
	}
	
	/**
	 * 联系人列表中 用户信息设置
	 * @param rtn
	 */
	private void setUserInfo(List<ContactsVo> rtn,boolean mustFriend) {
		if(rtn==null) return;
		
		for (int i=rtn.size()-1;i>-1;i--) {
			ContactsVo friend=rtn.get(i);
			if(mustFriend) {
				if(friend.getRelationType()!=null && friend.getRelationType()==0) {
					rtn.remove(i);
					continue;
				}
			}
			
			UserFactory u = this.userService.getById(friend.getUserId());
			if(u!=null) {
				friend.setAvatar(u.getAvatar());
				friend.setUsername(u.getUserName());
				friend.setGender(u.getGender());
				friend.setBirthDay(u.getBirthday());
				friend.setHideInfo(u.getHideInfo());
				friend.setNickname(u.getNickName());
			}
		}
	}

	/**
	 * //创建团购订单团聊
	 */
	@Override
	public String createGroupBuyGroup(Long userId, String shoppingGroupId) {
		try {
			UserFactory loginUser = userService.getById(userId);
			if (StringUtils.isEmpty(shoppingGroupId))
				return null;
			GroupBuyVo groupBuyVo = groupBuyService.getGroupBuyById(shoppingGroupId);
			if (groupBuyVo == null)
				return null;
			// 获取当前用户在购物团中的用户信息
			GroupBuyUserVo groupBuyUserVo = groupBuyUserService.getByUserIdAndGroupId(userId, shoppingGroupId);
			if (groupBuyUserVo == null)
				return null;
			List<UserImGroupMember> adds = null;
			String userImId = null;
			Boolean isLadder = true;
			if (groupBuyUserVo.getIsLadder() == 0) {// 当前用户为团员
				userImId = checkUserAdmin(userId);
				isLadder = false;
			} else {
				userImId = checkUserAdmin(Long.valueOf(11111111));// 群助手信息
			}
			if (StringUtils.isEmpty(userImId))
				return null;
			adds = userImGroupMemberService.selectByImIds(userImId.split(","));
			List<UserImGroup> groups = new ArrayList<UserImGroup>();
			if (isLadder) {// 团长创建群聊
				UserImGroup userImGroup = EasemobIMUtils.addShoppIngUserImGroup(loginUser, dbUtils.CreateID(),
						groupBuyVo, adds);
				userImGroup.setRelationType(2); // 好友群
				userImGroupService.save(userImGroup);
				// 修改购物购物团
				groupBuyVo.setIm_groupId(userImGroup.getId().toString());
				groupBuyService.update(groupBuyVo);
				// 添加群主及群助手到购物群成员中
				UserImGroupMember owner = new UserImGroupMember();
				owner.setUserId(loginUser.getId());
				owner.setNickname(loginUser.getNickName() == null ? loginUser.getUserName() : loginUser.getNickName());
				owner.setOpenimId(EasemobIMService.APP_TYPE_USER + loginUser.getId());
				adds.add(owner);
				for (UserImGroupMember userImGroupMember : adds) {
					userImGroupMember.setGroupId(userImGroup.getId());
					userImGroupMember.setImGroupId(userImGroup.getImGroupId());
					userImGroupMemberService.save(userImGroupMember);
				}
				groups.add(userImGroup);
				return userImGroup.getId().toString();
			}else {//团员加入群聊
				UserImGroup g = this.getGroup(Long.valueOf(groupBuyVo.getIm_groupId()), null);
				if(g==null) {
					return null;
				} else {
					if(adds!=null && !adds.isEmpty()) {
						int rst = EasemobIMUtils.addUserImGroupMember(g, adds,false);
						if(rst>0) {
							for (UserImGroupMember userImGroupMember : adds) {
								userImGroupMemberService.save(userImGroupMember);
							}
							g.setCnt(g.getCnt()+rst);
							userImGroupService.update(g);
						}
						groups.add(g);
						return g.getId().toString();
					} else {
						return null;
					}
				}
			}
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
	 * 检查是否创建环信群助手并返回im群助手
	 * @param uid 
	 * @return
	 */
	private  String checkUserAdmin(Long uid) {
		UserIm im=new UserIm();
		im.setAppType(EasemobIMService.APP_TYPE_USER);
		im.setUserId(uid);
		UserIm ui=null;
		List<UserIm> ims = userImService.selectByModel(im);
		if(ims==null || ims.isEmpty()) {
			UserFactory uf = new UserFactory();
			uf.setId(uid);
			ui = EasemobIMUtils.addImUser(uf);
			if(ui!=null){
				userImService.save(ui);
			}
			return ui.getOpenimId();
		} else {
			return ims.get(0).getOpenimId();
		}
	}
	
	private UserImGroup getGroup(Long groupId,String imGroupId) {
		UserImGroup query = new UserImGroup();
		query.setId(groupId);
		query.setImGroupId(imGroupId);
		List<UserImGroup> groups = userImGroupService.selectByModel(query);
		if(groups==null ||  groups.isEmpty()) {
			return null;
		} else {
			return groups.get(0);
		}
	}
}