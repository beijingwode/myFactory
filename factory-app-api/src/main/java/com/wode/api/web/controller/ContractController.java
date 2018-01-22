 	package com.wode.api.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.api.util.EasemobIMUtils;
import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.GroupBuy;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserIm;
import com.wode.factory.model.UserImGroup;
import com.wode.factory.model.UserImGroupMember;
import com.wode.factory.outside.service.EasemobIMService;
import com.wode.factory.service.GroupBuyService;
import com.wode.factory.service.GroupBuyUserService;
import com.wode.factory.user.dao.SupplierDao;
import com.wode.factory.user.model.UserContacts;
import com.wode.factory.user.service.GroupOrdersService;
import com.wode.factory.user.service.UserContactsService;
import com.wode.factory.user.service.UserImGroupMemberService;
import com.wode.factory.user.service.UserImGroupService;
import com.wode.factory.user.service.UserImService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.vo.ContactsVo;
import com.wode.factory.user.vo.UserRemarks;
import com.wode.factory.vo.GroupBuyUserVo;
import com.wode.factory.vo.GroupBuyVo;

/**
 * 2015-8-20
 * @author 谷子夜
 *
 */
@Controller
@RequestMapping("/contract")
@ResponseBody
public class ContractController extends BaseController{

	@Autowired
	private UserImGroupService userImGroupService;
	@Autowired
	private UserImGroupMemberService userImGroupMemberService;
	@Qualifier("redis")
    @Autowired
    private RedisUtil redisUtil;
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	DBUtils dbUtils;
	@Autowired
	private UserContactsService userContactsService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private GroupBuyService groupBuyService; 
	@Autowired
	private GroupBuyUserService groupBuyUserService;
	@Autowired
	private UserImService userImService;
	@Autowired
	private GroupOrdersService groupOrdersService;
	/**
	 * 功能：员工用户亲友列表
	 * 员工账户亲友上限5人
	 * @return
	 */
	@RequestMapping("list.user")
	public ActResult<List<ContactsVo>> findAll(HttpServletRequest request){
		if(loginUser.getEmployeeType()==1 && loginUser.getSupplierId()!=null) {
			//员工
			List<ContactsVo> ls = userContactsService.selectContactsByEmployee(loginUser.getSupplierId(), loginUser.getId());
			return ActResult.success(ls);
		} else {
			return ActResult.success(userContactsService.selectContactsByUser(loginUser.getId()));
		}
	}

	/**
	 * 功能：员工用户亲友列表
	 * 员工账户亲友上限5人
	 * @return
	 */
	@RequestMapping("one.user")
	public ActResult<ContactsVo> findOne(HttpServletRequest request,Long friendId){
		ContactsVo rtn=null;
		if(loginUser.getEmployeeType()==1 && loginUser.getSupplierId()!=null) {
			//当前用户为员工
			UserFactory userFactory = userService.getById(friendId);
			if (userFactory!=null && userFactory.getSupplierId()!=null && userFactory.getSupplierId().equals(loginUser.getSupplierId())) {//同事
				rtn = userContactsService.findColleague(loginUser.getSupplierId(), loginUser.getId(),friendId);
			}else{//其他
				rtn = userContactsService.selectContactByUser(loginUser.getId(),friendId);
			}
		}else{//当前用户为普通
			rtn = userContactsService.selectContactByUser(loginUser.getId(),friendId);
		}

		if(rtn == null) {
			// 不是联系人时
			UserFactory u = userService.getById(friendId);
			if(u!=null) {
				rtn = new ContactsVo();

				rtn.setUserType(0);
				rtn.setRelationType(0); 
				rtn.setUserId(u.getId()); 
				rtn.setAvatar(u.getAvatar());
				rtn.setUsername(u.getUserName());
				rtn.setGender(u.getGender());
				rtn.setBirthDay(u.getBirthday());
				rtn.setHideInfo(u.getHideInfo());
				rtn.setNickname(u.getNickName());
				
				UserIm query = new UserIm();
				query.setUserId(u.getId());
				//query.setAppType("user");
				List<UserIm> ims= userImService.selectByModel(query);
				if(ims!=null && ims.size()>0) {
					UserIm im = ims.get(0);
					rtn.setImUser(im.getOpenimId());
					rtn.setAppType(im.getAppType());
					rtn.setAppKey(im.getAppKey());
				}
			}
		}
		
		return ActResult.success(rtn);
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
	
	/**
	 * 功能：员工用户亲友列表
	 * 员工账户亲友上限5人
	 * @return
	 */
	@RequestMapping("groupMembers.user")
	public ActResult<List<UserImGroupMember>> groupMembers(HttpServletRequest request,Long groupId,String imGroupId){
		UserImGroup query = new UserImGroup();
		query.setId(groupId);
		query.setImGroupId(imGroupId);
		List<UserImGroup> groups = userImGroupService.selectByModel(query);
		
		if(groups==null ||  groups.isEmpty()) {
			return ActResult.fail("该群组信息取得失败，请刷新后重试");
		} else {
			UserImGroup group = groups.get(0);
			if(group.getRelationType()==3) {
				//同事群，自动同步成员
				refreshGroupMembers(group);
			}
			UserImGroupMember query2 = new UserImGroupMember();
			query2.setGroupId(group.getId());
			List<UserImGroupMember> mems=userImGroupMemberService.selectByModel(query2);
			if(mems.size()==0) {
				userImGroupService.removeById(group.getId());
			} else if(mems.size()!=group.getCnt()) {
				group.setCnt(mems.size());
				userImGroupService.update(group);
			}
			GroupBuy groupBuy = groupOrdersService.findByBuyId(Long.valueOf(group.getId()),null);
			ActResult success = ActResult.success(userImGroupMemberService.selectByModel(query2));
			if(groupBuy!=null && groupBuy.getStatus()==1 && groupBuy.getOrderStatus()==0) {
					success.setMsg(groupBuy.getId()+"");
			}else {
				success.setMsg(null);
			}
			//success.setMsg(groupBuy == null || groupBuy.getStatus()>0?null:groupBuy.getId()+"");
			return success;
		}
	}
	
	@RequestMapping("groupAdd.user")
	public ActResult<UserImGroup> groupAdd(HttpServletRequest request,String groupName,String note,String members){
		// 避免群组名称过长
		if(groupName.length()>50) groupName=groupName.substring(0, 50);
		List<UserImGroupMember> adds = null;
		if(StringUtils.isEmpty(members)) {
			adds = new ArrayList<UserImGroupMember>();
		} else {
			adds = userImGroupMemberService.selectByImIds(members.split(","));
		}
		UserImGroup userImGroup=  EasemobIMUtils.addUserImGroup(loginUser,dbUtils.CreateID(), groupName,note , adds);
		userImGroup.setRelationType(2);		//好友群
		userImGroupService.save(userImGroup);
		
		UserImGroupMember owner = new UserImGroupMember();
		owner.setUserId(loginUser.getId());
		owner.setNickname(loginUser.getNickName()==null?loginUser.getUserName():loginUser.getNickName());
		owner.setOpenimId(EasemobIMService.APP_TYPE_USER+loginUser.getId());
		adds.add(owner);
		for (UserImGroupMember userImGroupMember : adds) {
			userImGroupMember.setGroupId(userImGroup.getId());
			userImGroupMember.setImGroupId(userImGroup.getImGroupId());
			userImGroupMemberService.save(userImGroupMember);
		}
		return ActResult.success(userImGroup);
	}

	@RequestMapping("groupDel.user")
	public ActResult<UserImGroup> groupDel(HttpServletRequest request,Long groupId,String imGroupId){
		UserImGroup g = this.getGroup(groupId, imGroupId);
		if(g==null) {
			return ActResult.fail("该群组信息取得失败，请刷新后重试");
		} else {
			GroupBuy groupBuy = groupBuyService.findByImGroupId(g.getId());
			if(groupBuy==null){
				if(EasemobIMUtils.delUserImGroup(g)) {
					userImGroupService.removeById(g.getId());
					return ActResult.success(null);
				} else {
					return ActResult.fail("操作失败，请刷新后重试。");
				}
			}else{
				return ActResult.fail("拼团尚未完成，暂时不能退出群。");
			}
		}
	}

	@RequestMapping("groupUpd.user")
	public ActResult<UserImGroup> groupUpd(HttpServletRequest request,Long groupId,String imGroupId,String groupName,String note){
		// 避免群组名称过长
		if(groupName.length()>50) groupName=groupName.substring(0, 50);
		UserImGroup g = this.getGroup(groupId, imGroupId);
		if(g==null) {
			return ActResult.fail("该群组信息取得失败，请刷新后重试");
		} else {
			if(EasemobIMUtils.updUserImGroup(g, groupName, note)) {
				userImGroupService.update(g);
				return ActResult.success(g);
			} else {
				return ActResult.fail("更新失败，请刷新后重试。");
			}
		}
	}

	@RequestMapping("groupList.user")
	public ActResult<List<UserImGroup>> groupList(HttpServletRequest request){
		if(loginUser.getEmployeeType()==1 && loginUser.getSupplierId()!=null) {
			//员工
			UserImGroup userImGroup = userImGroupService.getById(loginUser.getSupplierId());
			if(userImGroup==null) {
				List<UserImGroupMember> adds = userImGroupMemberService.selectEmployeeAdds(loginUser.getSupplierId());
				Supplier sup = supplierDao.getById(loginUser.getSupplierId());
				userImGroup=  EasemobIMUtils.addUserImGroup(loginUser, sup.getId(), "我的同事",sup.getComName() + "同事群" , adds);
				userImGroup.setRelationType(3);		//同事群
				userImGroupService.save(userImGroup);
				for (UserImGroupMember userImGroupMember : adds) {
					userImGroupMember.setGroupId(userImGroup.getId());
					userImGroupMember.setImGroupId(userImGroup.getImGroupId());
					userImGroupMemberService.save(userImGroupMember);
				}
			} else {
				refreshGroupMembers(userImGroup);
			}
		}
		return ActResult.success(userImGroupService.selectByMember(loginUser.getId()));
	}

	/**
	 * 功能：员工用户亲友列表
	 * 员工账户亲友上限5人
	 * @return
	 */
	@RequestMapping("groupInfo.user")
	public ActResult<UserImGroup> groupInfo(HttpServletRequest request,Long groupId,String imGroupId){
		UserImGroup g = this.getGroup(groupId, imGroupId);
		if(g==null) {
			return ActResult.fail("该群组信息取得失败，请刷新后重试");
		} else {
			return ActResult.success(g);
		}
	}
	/**
	 * 修改本人群昵称
	 * @param request
	 * @param groupId
	 * @param imGroupId
	 * @param memo  本群昵称
	 * @return
	 */
	@RequestMapping("groupMemberUpd.user")
	public ActResult<UserImGroupMember> groupMemberUpd(HttpServletRequest request,String imGroupId,String memo){
		if (StringUtils.isNullOrEmpty(memo)) {
			return ActResult.fail("昵称不能为空！");
		}
		UserImGroupMember query = new UserImGroupMember();
		query.setImGroupId(imGroupId);
		query.setUserId(loginUser.getId());
		List<UserImGroupMember> userGroupM = userImGroupMemberService.selectByModel(query);
		if(userGroupM==null || userGroupM.isEmpty()) {
			return ActResult.fail("该群组信息取得失败，请刷新后重试");
		} else {
			UserImGroupMember ugm = userGroupM.get(0);
			ugm.setUserCard(memo);
			userImGroupMemberService.update(ugm);
			return ActResult.success(ugm);
		}
	}
	/**
	 * 功能：员工用户亲友列表
	 * 员工账户亲友上限5人
	 * @return
	 */
	@RequestMapping("groupMessages")
	public void messages(HttpServletRequest request,Long uid,String msg,String im,String nickName){
		List<UserImGroup> groups = userImGroupService.selectByMember(uid);
		if(groups!=null && !groups.isEmpty()) {
			EasemobIMUtils.groupMessages(msg, groups, uid, im, nickName);
		}
	}
	
	@RequestMapping("groupMemberAdd.user")
	public ActResult<UserImGroup> groupMemberAdd(HttpServletRequest request,Long groupId,String imGroupId,String members){
		List<UserImGroupMember> adds = null;
		if(StringUtils.isEmpty(members)) {
			adds = new ArrayList<UserImGroupMember>();
		} else {
			adds = userImGroupMemberService.selectByImIds(members.split(","));
		}
		UserImGroup g = this.getGroup(groupId, imGroupId);
		if(g==null) {
			return ActResult.fail("该群组信息取得失败，请刷新后重试");
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
				for (UserImGroupMember u : adds) {
					Map paramPush=new HashMap();
					paramPush.put("toUserId", u.getUserId());
					paramPush.put("msg", "欢迎"+u.getNickname()+"加入，送给他些祝福和礼物吧");
					paramPush.put("imId", u.getOpenimId());
					paramPush.put("nickName", u.getNickname());
					paramPush.put("groupId", u.getGroupId());
					HttpClientUtil.sendHttpRequest("post", "http://api.wd-com/contract/shoppingGroupMessage", paramPush);
				}
				
				return ActResult.success(g);
			} else {
				return ActResult.fail("操作失败，请刷新后重试。");
			}
		}
	}

	@RequestMapping("groupMemberDel.user")
	public ActResult<UserImGroup> groupMemberDel(HttpServletRequest request,Long groupId,String imGroupId,String members){

		UserImGroup g = this.getGroup(groupId, imGroupId);
		if(g==null) {
			return ActResult.fail("该群组信息取得失败，请刷新后重试");
		} else {
			GroupBuy groupBuy = groupBuyService.findByImGroupId(g.getId());
			if(groupBuy==null){
				List<UserImGroupMember> adds = null;
				if(StringUtils.isEmpty(members)) {
					adds = new ArrayList<UserImGroupMember>();
				} else {
					adds = userImGroupMemberService.select4delByImIds(members.split(","),g.getId());
				}
				
				if(adds!=null && !adds.isEmpty()) {
					int rst = EasemobIMUtils.deleteGroupMembers(g, adds);
					if(rst>0) {
						for (UserImGroupMember userImGroupMember : adds) {
							userImGroupMemberService.removeById(userImGroupMember.getId());
						}
						g.setCnt(g.getCnt()-rst);
						userImGroupService.update(g);
					}
					return ActResult.success(g);
				} else {
					return ActResult.fail("操作失败，请刷新后重试。");
				}
			}else{
				return ActResult.fail("拼团尚未完成，暂时不能退出群。");
			}
		}
	}

	private void refreshGroupMembers(UserImGroup group) {
		//同步数据
		//群中删除已离职员工、
		List<UserImGroupMember> dels = userImGroupMemberService.selectEmployeeDels(group.getId());
		if(dels!=null && !dels.isEmpty()) {
			EasemobIMUtils.deleteGroupMembers(group, dels);
			for (UserImGroupMember userImGroupMember : dels) {
				userImGroupMemberService.removeById(userImGroupMember.getId());
			}
		}
		//群中删除已离职员工、
		List<UserImGroupMember> adds = userImGroupMemberService.selectEmployeeAdds(group.getId());
		if(adds!=null && !adds.isEmpty()) {
			EasemobIMUtils.addUserImGroupMember(group, adds,true);
			for (UserImGroupMember userImGroupMember : adds) {
				userImGroupMemberService.save(userImGroupMember);
			}
		}
	}
	/**
	 * 备注
	 * @param request
	 * @param remarkUserId
	 * @param memo
	 * @return
	 */
	@RequestMapping("remarks.user")
	public ActResult<UserRemarks> remarks(HttpServletRequest request,Long friendId,String remarkOpenId,String userNote){
		if (StringUtils.isNullOrEmpty(userNote)) {
			return ActResult.fail("备注不能为空！");
		}
		UserContacts query = new UserContacts();
		query.setUserId(loginUser.getId());
		query.setContactsId(friendId);
		UserContacts userRemarks = userContactsService.selectOneByModel(query);
		if (userRemarks == null) {//没有 添加
			query.setContactsMemo(userNote);
			query.setContactsImId(remarkOpenId);
			query.setFirendType("0");	//非好友
			query.setApprFrom("app remarks");
			query.setCreateTime(new Date());
			query.setId(dbUtils.CreateID());
			userContactsService.save(query);
			return ActResult.success(query);
		}else{//有 修改
			userRemarks.setContactsMemo(userNote);
			userContactsService.update(userRemarks);
			return ActResult.success(userRemarks);
		}
	}
	/**
	 * 获取备注列表
	 * @param request
	 * @return
	 */
	@RequestMapping("getRemarks.user")
	public ActResult<List<UserRemarks>> getRemarks(HttpServletRequest request){
		UserContacts query = new UserContacts();
		query.setUserId(loginUser.getId());
		return ActResult.success(userContactsService.selectByModel(query));
	}
	
	/**
	 * 获取自己信息
	 * @param request
	 * @return
	 */
	@RequestMapping("findOneself.user")
	public ActResult<ContactsVo> findOneself(HttpServletRequest request){
		return ActResult.success(userContactsService.findOneSelf(loginUser.getId()));
	}
}
