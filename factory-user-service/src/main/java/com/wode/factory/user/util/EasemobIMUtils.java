package com.wode.factory.user.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserIm;
import com.wode.factory.model.UserImGroup;
import com.wode.factory.model.UserImGroupMember;
import com.wode.factory.outside.service.EasemobIMService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.vo.GroupBuyVo;

public class EasemobIMUtils {

	static EasemobIMService os = ServiceFactory.getEasemobIMService(com.wode.factory.user.util.Constant.OUTSIDE_SERVICE_URL);
	
    private static JSONObject initUserinfos(UserFactory uf,UserIm im) {
    	JSONObject uinfos = new JSONObject();
    	uinfos.put("username", im.getOpenimId());
    	uinfos.put("password", im.getOpenimPw());
    	//uinfos.put("nickname", uf.getNickName());
        return uinfos;
    }
    
    /**
     * 更新用户昵称
     * @param username
     * @param nickname
     * @return
     */
    public static boolean updUserNickName(String username ,String nickname) {
    	return os.updUserNickName(username, nickname, EasemobIMService.APP_TYPE_USER, "myFactory", false, null);
    }

    public static UserIm addImUser(UserFactory uf) {
    	UserIm im = new UserIm();
    	im.setUserId(uf.getId());
    	im.setAppType(EasemobIMService.APP_TYPE_USER);
    	im.setAppKey(EasemobIMService.APP_KEY_SHOP);
    	im.setOpenimId(EasemobIMService.APP_TYPE_USER+uf.getId());
    	im.setOpenimPw("openimPw");
    	
		String json = initUserinfos(uf,im).toJSONString();
		
		if(os.addImUser(json, EasemobIMService.APP_TYPE_USER, "myFactory", false, null)){
			return im;
		} else {
			return null;
		}
    }
    

    public static UserImGroup addUserImGroup(UserFactory uf,Long id,String groupName,String note,List<UserImGroupMember> members) {
    	UserImGroup group = new UserImGroup();
    	group.setId(id);
    	group.setGroupname(groupName);
    	group.setNote(note);
    	group.setPubliced("1");
    	group.setMaxusers(500);
    	group.setApproval("0");
    	group.setOwner(EasemobIMService.APP_TYPE_USER+uf.getId());
    	group.setOwnerId(uf.getId());
    	group.setCreateTime(new Date());
    	group.setCnt(members==null?1:(members.size()+1));

    	JSONObject uinfos = initGroupJson(uf, members, group);

		String openGroupId= os.addUserImGroup(uinfos.toJSONString(), EasemobIMService.APP_KEY_USER, "myFactory", false, null);
		if(openGroupId!=null) {
			group.setImGroupId(openGroupId);
	    	return group;
		} else {
	        return null;
		}
    }

    public static boolean updUserImGroup(UserImGroup group,String groupName,String note) {
    	note = StringUtils.isEmpty(note)?(StringUtils.isEmpty(group.getNote())?groupName:group.getNote()):note;
    	note = note.replace(" ", "+").replace("/", "");
    	groupName = groupName.replace(" ", "+").replace("/", "");
    	
		JSONObject uinfos = new JSONObject();
		group.setGroupname(groupName);
    	uinfos.put("groupname", groupName);	//群组名称，此属性为必须的
    	uinfos.put("description",note );			//群组描述，此属性为必须的
    	group.setNote(note);
    	uinfos.put("maxusers", group.getMaxusers());	//群组成员最大数（包括群主），值为数值类型，默认值200，此属性为可选的

    	return os.updUserImGroup(group.getImGroupId(), uinfos.toJSONString(), EasemobIMService.APP_KEY_USER, "myFactory");
    }
    
    
    public static boolean delUserImGroup(UserImGroup group) {    	
    	return os.delUserImGroup(group.getImGroupId(), EasemobIMService.APP_KEY_USER, "myFactory");
   }
    
    public static int addUserImGroupMember(UserImGroup group,List<UserImGroupMember> members,boolean async) {
		JSONObject uinfos = new JSONObject();
    	List<String> ms = new ArrayList<String>();
    	for (UserImGroupMember userImGroupMember : members) {
			ms.add(EasemobIMService.APP_TYPE_USER+userImGroupMember.getUserId());
			userImGroupMember.setGroupId(group.getId());
			userImGroupMember.setImGroupId(group.getImGroupId());
			userImGroupMember.setOpenimId(EasemobIMService.APP_TYPE_USER+userImGroupMember.getUserId());
		}
    	
    	if(!ms.isEmpty()) {
        	uinfos.put("usernames", ms);	//群组成员，此属性为可选的，但是如果加了此项，数组元素至少一个（注：群主jma1不需要写入到members里面）    		
    	}

    	Integer r = os.addUserImGroupMember(group.getImGroupId(), uinfos.toJSONString(), EasemobIMService.APP_KEY_USER, "myFactory", async, null);
		if(r!=null) {
			return r;
		} else {
			return 0;
		}
    }

    public static int deleteGroupMembers(UserImGroup group,List<UserImGroupMember> members) {
		String uinfos = members.get(0).getOpenimId();
		for(int i=1;i<members.size();i++) {
			uinfos+=","+members.get(i).getOpenimId();
		}
    	if(os.deleteGroupMembers(group.getImGroupId(), uinfos, EasemobIMService.APP_KEY_USER, "myFactory", true, null)){
    		return members.size();
    	} else {
    		return 0;
    	}
   }

    public static void groupMessages(String text,List<UserImGroup> groups,Long uid,String im,String nickName) {
		JSONObject body = groupsTxt(text, groups, uid, im, nickName);
		os.groupMessages(body.toJSONString(), EasemobIMService.APP_KEY_USER, "myFactory", true, null);
   }

	/**
	 * 查看用户状态
	 * @param redis
	 * @param username
	 * @return
	 */
	public static String checkUserStatus(String username) {
		String status = os.checkUserStatus(username, EasemobIMService.APP_KEY_USER, "myFactory");
		if(StringUtils.isEmpty(status)) {
			return "false";
		} else {
			return status;
		}
	}
	
	private static JSONObject initGroupJson(UserFactory uf, List<UserImGroupMember> members, UserImGroup group) {
		JSONObject uinfos = new JSONObject();
    	uinfos.put("groupname", group.getGroupname());	//群组名称，此属性为必须的
    	uinfos.put("desc", group.getNote());			//群组描述，此属性为必须的
    	uinfos.put("public", "1".equals(group.getPubliced()));	//是否是公开群，此属性为必须的
    	uinfos.put("maxusers", group.getMaxusers());	//群组成员最大数（包括群主），值为数值类型，默认值200，此属性为可选的
    	uinfos.put("approval", "1".equals(group.getApproval()));	//加入公开群是否需要批准，默认值是false（加入公开群不需要群主批准），此属性为必选的，私有群必须为true
    	uinfos.put("owner", group.getOwner());	//群组的管理员，此属性为必须的
    	
    	List<String> ms = new ArrayList<String>();
    	for (UserImGroupMember userImGroupMember : members) {
			if(!userImGroupMember.getUserId().equals(uf.getId())) {
				ms.add(userImGroupMember.getOpenimId());
			}
		}
    	
    	if(!ms.isEmpty()) {
        	uinfos.put("members", ms);	//群组成员，此属性为可选的，但是如果加了此项，数组元素至少一个（注：群主jma1不需要写入到members里面）    		
    	}
		return uinfos;
	}
	
	
	private static JSONObject groupsTxt(String text, List<UserImGroup> groups,Long uid,String im,String nickName) {
		JSONObject uinfos = new JSONObject();
    	uinfos.put("target_type", "chatgroups");	//users 给用户发消息。chatgroups: 给群发消息，chatrooms: 给聊天室发消息
    	
    	List<String> ms = new ArrayList<String>();
    	for(int i=0;i<groups.size() && i<20;i++) {
			ms.add(groups.get(i).getImGroupId());
		}
    	
    	if(!ms.isEmpty()) {
        	uinfos.put("target", ms);	//注意这里需要用数组，数组长度建议不大于20，即使只有一个用户，也要用数组 ['u1']，给用户发送时数组元素是用户名，给群组发送时,数组元素是groupid
    	}

    	JSONObject msg = new JSONObject();
    	msg.put("type", "txt");	//users 给用户发消息。chatgroups: 给群发消息，chatrooms: 给聊天室发消息
    	msg.put("msg", text);	//消息内容，参考[[start:100serverintegration:30chatlog|聊天记录]]里的bodies内容
    	uinfos.put("msg", msg);
    	
    	JSONObject ext = new JSONObject();
    	ext.put("uid", uid);			//用户id
    	ext.put("im", im);				//用户im id
    	ext.put("nickName", nickName);	//用户昵称
    	uinfos.put("ext", ext);
    	
		return uinfos;
	}

	public static UserImGroup addShoppIngUserImGroup(UserFactory loginUser, long id, GroupBuyVo groupBuyVo,
			List<UserImGroupMember> members) {
		UserImGroup group = new UserImGroup();
    	group.setId(id);
    	group.setGroupname(groupBuyVo.getGroupName());
    	group.setNote("购物团");
    	group.setPubliced("1");
    	group.setMaxusers(500);
    	group.setApproval("0");
    	group.setOwner(EasemobIMService.APP_TYPE_USER+loginUser.getId());
    	group.setOwnerId(loginUser.getId());
    	group.setCreateTime(new Date());
    	group.setCnt(members==null?1:(members.size()+1));

    	JSONObject uinfos = initGroupJson(loginUser, members, group);

		String openGroupId= os.addUserImGroup(uinfos.toJSONString(), EasemobIMService.APP_KEY_USER, "myFactory", false, null);
		if(openGroupId!=null) {
			group.setImGroupId(openGroupId);
	    	return group;
		} else {
	        return null;
		}
	}
	
	/**
	 * 购物团发送消息
	 * @param msg
	 * @param group
	 * @param toUserId
	 * @param imId
	 * @param nickName
	 * @param state 
	 */
	public static void shoppingGroupMessage(String text, List<UserImGroup> groups, Long toUserId, String imId,
			String nickName, String state) {
		JSONObject body = shoppingGroupsTxt(text, groups, toUserId, imId, nickName,state);
		os.groupMessages(body.toJSONString(), EasemobIMService.APP_KEY_USER, "myFactory", true, null);
	}
	
	private static JSONObject shoppingGroupsTxt(String text, List<UserImGroup> groups,Long uid,String im,String nickName, String state) {
		JSONObject uinfos = new JSONObject();
    	uinfos.put("target_type", "chatgroups");	//users 给用户发消息。chatgroups: 给群发消息，chatrooms: 给聊天室发消息
    	
    	List<String> ms = new ArrayList<String>();
    	for(int i=0;i<groups.size() && i<20;i++) {
			ms.add(groups.get(i).getImGroupId());
		}
    	
    	if(!ms.isEmpty()) {
        	uinfos.put("target", ms);	//注意这里需要用数组，数组长度建议不大于20，即使只有一个用户，也要用数组 ['u1']，给用户发送时数组元素是用户名，给群组发送时,数组元素是groupid
    	}

    	JSONObject msg = new JSONObject();
    	msg.put("type", "txt");	//users 给用户发消息。chatgroups: 给群发消息，chatrooms: 给聊天室发消息
    	msg.put("msg", text);	//消息内容，参考[[start:100serverintegration:30chatlog|聊天记录]]里的bodies内容
    	uinfos.put("msg", msg);
    	
    	JSONObject ext = new JSONObject();
    	ext.put("uid", uid);			//用户id
    	ext.put("im", im);				//用户im id
    	ext.put("nickName", nickName);	//用户昵称
    	if("create".equals(state)) {
    		ext.put("group_buy_leader_add_mem_msg", true);			//团长消息特殊
    	}else if("add".equals(state)){
    		ext.put("group_buy_user_add_mem_msg", true);	//团员加入消息特殊处理
    	}
    	uinfos.put("ext", ext);
    	
    	//JSONObject from = new JSONObject();
    	//from.put("from","user11111111");
    	uinfos.put("from", "user11111111");//表示群助手 写死
		return uinfos;
	}
}
