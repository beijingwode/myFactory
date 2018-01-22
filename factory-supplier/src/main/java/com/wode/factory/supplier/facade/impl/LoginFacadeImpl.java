package com.wode.factory.supplier.facade.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserDevice;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserIm;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.EasemobIMService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.SmsService;
import com.wode.factory.supplier.facade.LoginFacade;
import com.wode.factory.supplier.service.UserDeviceService;
import com.wode.factory.supplier.service.UserImService;
import com.wode.factory.supplier.service.UserService;
import com.wode.factory.supplier.util.Constant;
import com.wode.factory.supplier.util.EasemobIMUtils;
import com.wode.factory.supplier.util.JPushUtils;
import com.wode.model.CommUser;

@Service("loginFacade")
public class LoginFacadeImpl  implements LoginFacade{

	@Autowired
	private UserService userService;

	@Autowired
	private UserDeviceService userDeviceService;
	@Autowired
	private UserImService userImService;
	@Qualifier("redis")
    @Autowired
    private RedisUtil redisUtil;
	@Autowired
    private JPushUtils push;

	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
	static SmsService sms = ServiceFactory.getSmsService(Constant.OUTSIDE_SERVICE_URL);
	@Override
	@Transactional
	public ActResult<String> doCommit(String ticket, String mobileId, String aliasName,String deviceName,
			String deviceType) {

		ActResult<UserFactory> ar = this.hasLogin(ticket);
		if(!ar.isSuccess()) return ActResult.fail(ar.getMsg());
		
		UserFactory uf = ar.getData();
		///////////////////////////////////////////////////////////////////////
		//查询已经登录的设备
		UserDevice query = new UserDevice();
		query.setUserId(uf.getId());
		List<UserDevice> ls = userDeviceService.findByAttribute(query);
		List<UserDevice> needNotifys = new ArrayList<UserDevice>();
		UserDevice current=null;
		for (UserDevice userDevice : ls) {
			
			if(userDevice.getDeviceId().equals(mobileId)) {
				//相同设备
				current = userDevice;
			} else if("1".equals(userDevice.getStatus())) {
				//其他设备
				if(!"user".equals(userDevice.getChannelId())){
					needNotifys.add(userDevice);
				}
			}
		}
		///////////////////////////////////////////////////////////////////////

		UserIm im=new UserIm();
		im.setAppType(EasemobIMService.APP_TYPE_SHOP);
		im.setUserId(uf.getId());
		UserIm ui=null;
		List<UserIm> ims = userImService.selectByModel(im);
		if(ims==null || ims.isEmpty()) {

			ui = EasemobIMUtils.addImUser(uf);
			if(ui!=null){
				userImService.save(ui);
			}

		} else {
			ui=ims.get(0);
		}
		
		///////////////////////////////////////////////////////////////////////
		
		//记录当前设备
		if(current==null) {
			current= new UserDevice();
		}else{
			if("1".equals(current.getStatus()) && "user".equals(current.getChannelId())){//已经登录了
				current= new UserDevice();
			}
		}
		current.setUserId(uf.getId());
		current.setDeviceId(mobileId);
		current.setDeviceName(deviceName);
		current.setDeviceType(deviceType);
		current.setAsname(aliasName);
		current.setStatus("1");
		current.setTicket(ticket);
		current.setChannelId("shop");//设置登录平台user表示买家shop表示店铺
		current.setUpdateTime(new Date());
		userDeviceService.saveOrUpdate(current);
		///////////////////////////////////////////////////////////////////////

		///////////////////////////////////////////////////////////////////////
		//处理其他设备
		for (UserDevice userDevice : needNotifys) {
			// 下线处理
			userDevice.setStatus("0");
			userDevice.setUpdateTime(new Date());
			userDeviceService.update(userDevice);

			try {
				logOut(userDevice.getTicket());

				// 推送消息
				push.sendMessage("您的账号已在其他设备(" + deviceName + ")上登录，如非本人操作，请立即登录，并修改密码、提升安全设置", "下线通知",null,
						JPushUtils.formatDriver(userDevice.getDeviceType()), "alias", null, userDevice.getAsname());
				// push.sendNotification("您的账号已在其他设备(" + deviceName +
				// ")上登录，如非本人操作，请立即登录，并修改密码、提升安全设置", "退出通知", null,
				// JPushUtils.formatDriver(userDevice.getDeviceType()),
				// "alias", userDevice.getAsname());
			} catch (Exception e) {
			}
		}
		///////////////////////////////////////////////////////////////////////

		String rtn="";
		if(ui!=null){
			rtn= ui.getOpenimId()+","+ui.getOpenimPw();
		}
		return ActResult.success(rtn);
	}
	
	@Override
	public ActResult<UserFactory> hasLogin(String ticket) {
		if(StringUtils.isEmpty(ticket)) return ActResult.fail("ticket不正确");
		String ufid = redisUtil.getMapData(CACHE_APP_TICKET_ID, ticket);
		if(StringUtils.isEmpty(ufid)) {
			ActResult<CommUser> actUser = us.hasLogin(ticket);
			if (actUser.isSuccess()) {
				UserFactory userFactory = userService.getById(actUser.getData().getUserId());
				if (userFactory == null) {
					return ActResult.fail("不是商家用户");
				} else {

					if(userFactory.getType()==2||userFactory.getType()==3){
						
						userFactory.setLoginTime(new Date());
						userService.update(userFactory);
					//商家下的子管理员
					}else{
						return ActResult.fail("不是商家用户");
					}
				}
				redisUtil.setMapData(CACHE_APP_TICKET_ID, ticket, userFactory.getId()+"");
				return ActResult.success(userFactory);
			} else {
				return ActResult.fail(actUser.getMsg());
			}
		} else {
			
			return ActResult.success(userService.getById(NumberUtil.toLong(ufid)));
		}
	}
	
	@Override
	public ActResult<UserFactory> hasLogin(Long uid) {
		if (uid != null) {
			UserFactory userFactory = userService.getById(uid);
			if (userFactory != null) {
				return ActResult.success(userFactory);
			} else {
				return ActResult.fail("用户数据不存在");
			}
		}
		return ActResult.fail("参数错误");
	}
	@Override
	public void logOut(String ticket) {
		if(StringUtils.isNullOrEmpty(ticket)) return;
		us.logout(ticket);
		
		redisUtil.delMapData(CACHE_APP_TICKET_ID, ticket);
		
		//查询已经登录的设备
		UserDevice query = new UserDevice();
		query.setStatus("1");
		query.setTicket(ticket);
		
		List<UserDevice> ls = userDeviceService.findByAttribute(query);
		for (UserDevice userDevice : ls) {

			userDevice.setStatus("0");
			userDevice.setUpdateTime(new Date());
			userDeviceService.update(userDevice);
		}
	}

	@Override
	public void doPushNotify(Long userId, UserDevice ud,String title,String msg) {

		if(ud ==null) {

			//查询已经登录的设备
			UserDevice query = new UserDevice();
			query.setUserId(userId);
			query.setStatus("1");
			List<UserDevice> ls = userDeviceService.findByAttribute(query);
			if(ls!=null && !ls.isEmpty()) {
				for (UserDevice userDevice : ls) {
					push.sendNotification(msg, title, null,
							JPushUtils.formatDriver(userDevice.getDeviceType()), "alias", userDevice.getAsname());				
				}
			} else {
				if("公司发放福利".equals(title)) {

					UserFactory uf = userService.getById(userId);
					if(uf!=null && !StringUtils.isEmpty(uf.getPhone())){
						String contnt="";
						int index = msg.indexOf("可以用来购买");
						if(index==-1){
							contnt=msg;
						} else {
							contnt=msg.substring(0, index)+"更多员工专享福利尽在我的福利平台。登陆账号及密码均为您收到此短信的手机号码，更多内容猛戳 http://wd-w.com/app.htm";
						}
						sms.sendSms(uf.getPhone(), Constant.SMS_SIGNATURE, contnt, "myShop", false, null);
					}
				}
			}
		} else {
			push.sendNotification(msg, title, null,
					JPushUtils.formatDriver(ud.getDeviceType()), "alias", ud.getAsname());
			
		}
	}

}