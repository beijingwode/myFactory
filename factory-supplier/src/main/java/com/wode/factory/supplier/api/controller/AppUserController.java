package com.wode.factory.supplier.api.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.constant.UserConstant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.ActResult;
import com.wode.common.util.FileUtils;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.Product;
import com.wode.factory.model.Shop;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserDevice;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.EasemobIMService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.SmsService;
import com.wode.factory.outside.service.UploadService;
import com.wode.factory.service.CommentsService;
import com.wode.factory.supplier.dao.SupplierDao;
import com.wode.factory.supplier.facade.LoginFacade;
import com.wode.factory.supplier.service.ProductService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SuborderitemService;
import com.wode.factory.supplier.service.UserDeviceService;
import com.wode.factory.supplier.service.UserService;
import com.wode.factory.supplier.util.AppPushUtil;
import com.wode.factory.supplier.util.Constant;
import com.wode.factory.supplier.util.EasemobIMUtils;
import com.wode.model.CommUser;

/**
 * 2015-6-17
 *
 * @author 谷子夜
 */
@Controller
@RequestMapping("/app/user")
public class AppUserController extends BaseController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserDeviceService userDeviceService;
	@Autowired
	ShopService shopService;
	@Autowired
	CommentsService commentsService;

	@Autowired
	@Qualifier("productService-supplier")
	private ProductService productService;
	
	@Autowired
	RedisUtil redisUtil;

	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private LoginFacade loginFacade;
	@Autowired
	@Qualifier("suborderitemService")
	private SuborderitemService suborderService;

	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
	static UploadService uploader = ServiceFactory.getUploadService(Constant.OUTSIDE_SERVICE_URL);
	static SmsService sms = ServiceFactory.getSmsService(Constant.OUTSIDE_SERVICE_URL);

	private static final int CAR_REDIS_EXPTIME = 60 * 60 * 24 * 30 * 6;//redis 过期时间6个月


	/**
	 * 功能：验证密码找回/修改验证码
	 *
	 * @param request
	 * @param phoneNumber
	 * @param code
	 * @return
	 */
	@RequestMapping("checkCode")
	@NoCheckLogin
	@ResponseBody
	public ActResult<String> checkCode(HttpServletRequest request, String phoneNumber, String code) {
		if (StringUtils.isPhoneNumber(phoneNumber)) {
			ActResult<CommUser> uar = us.findByPhone(phoneNumber);
			if (uar.isSuccess()) {
				if (uar.getData().getUserType() == 2) {
					return ActResult.fail("非法用户，权限受限");
				} else {
					String checkCode = redisUtil.get(UserConstant.FINDPWDBYPHONEFUNCTION + "_" + phoneNumber) + "";
					if (code.equals(checkCode)) {
						Map<String, String> data = new HashMap<String, String>();
						data.put("phoneNumber", phoneNumber);
						data.put("code", code);
						return ActResult.success(data);
					} else {
						return ActResult.fail("验证码错误，请重新输入");
					}
				}
			} else {
				return ActResult.fail("该手机未绑定注册");
			}
		} else {
			return ActResult.fail("请输入正确手机号码");
		}
	}

	/**
	 * 功能：app登录
	 *
	 * @param userName
	 * @param passWord
	 * @return
	 */
	@RequestMapping("commit")
	@NoCheckLogin
	@ResponseBody
	public ActResult<String> commit(String ticket, String mobileId,String aliasName,String deviceName,String deviceType) {
		return loginFacade.doCommit(ticket, mobileId, aliasName, deviceName, deviceType);
	}

	/**
	 * 用户注销
	 */
	@RequestMapping("logout")
	@NoCheckLogin
	@ResponseBody
	public ActResult<String> loginOut(HttpServletRequest request, String ticket) {
		loginFacade.logOut(ticket);
		return ActResult.successSetMsg("注销成功");
	}

	/**
	 * 用户注销
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("logoutWx")
	@NoCheckLogin
	public ModelAndView logoutWx(HttpServletRequest request,HttpServletResponse response,ModelAndView model) throws UnsupportedEncodingException {
		Cookie cookie_uid = new Cookie("uid","");
		cookie_uid.setPath("/");
		cookie_uid.setMaxAge(0);
		response.addCookie(cookie_uid);
		
		Cookie cookie_shopId = new Cookie("shopId","");
		cookie_uid.setPath("/");
		cookie_uid.setMaxAge(0);
		response.addCookie(cookie_shopId);
		
		//String rtn =URLEncoder.encode("http://api.wd-w.com/wx/getOpenId","utf-8");
		//return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb62e121cbeffdddf&redirect_uri="+rtn+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
		model.setViewName("wx_logout");
		return model;
	}
	
	/**
	 * 功能：个人中心首页信息整合（1、昵称；2、收藏和订单个数统计）
	 *
	 * @author 刘聪
	 * @since 2015-06-17
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("statistic.user")
	@NoCheckLogin
	@ResponseBody
	public ActResult<String> statistic(ModelMap model,HttpServletRequest request,HttpServletResponse response) {
		UserFactory userFactory = userService.getById(loginUser.getId());
		// 个人信息:昵称
		model.addAttribute("nickName", userFactory.getNickName());
		// 个人信息:昵称
		model.addAttribute("userName", userFactory.getUserName());
		// 个人信息:昵称
		model.addAttribute("phone", userFactory.getPhone());
		// 个人信息:头像
		model.addAttribute("avatar", userFactory.getAvatar());

		UserFactory uf = userService.getById(loginUser.getId());
		if (!StringUtils.isNullOrEmpty(uf.getSupplierId())) {
			Supplier sup = supplierDao.getById(uf.getSupplierId());
			model.addAttribute("companyName", sup == null ? "" : sup.getComName());
		}

		String shopId=request.getParameter("shopId");
        Shop record = new Shop();
        record.setSupplierId(userFactory.getSupplierId());
        List<Shop> shopList=shopService.selectByModel(record);
		// 个人信息:头像
		model.addAttribute("shopList", shopList);
        Shop shop = null;
        if(shopList!=null && !shopList.isEmpty()){
            if(StringUtils.isEmpty(shopId)) {
            	shop=shopList.get(0);
            } else {
            	for (Shop ss : shopList) {
            		if(ss.getId().equals(Long.parseLong(shopId))) {
            			shop=ss;
            			break;
            		}
				}
            }        	
        }
        
        if(shop != null) {
    		//model.addAttribute("shop", shop);
    		model.addAttribute("shopName", shop.getShopname());
    		model.addAttribute("shopIntroduce", shop.getIntroduce());//店铺签名
    		Cookie cookie_shopId = new Cookie("shopId",shop.getId()+"");
    	    cookie_shopId.setPath("/");
    	    cookie_shopId.setMaxAge(24 * 60 * 60 * 1000);
    		response.addCookie(cookie_shopId);
        	//'服务评级'
        	Map<String, Object> map= commentsService.getSupplierScore(shop.getSupplierId());

        	model.addAttribute("shopDescription", map.get(CommentsService.CACHE_MAP_KEY_GOODS_AVG));
        	model.addAttribute("shopService",  map.get(CommentsService.CACHE_MAP_KEY_SERVICE_AVG));
        	model.addAttribute("deliverySpeed", map.get(CommentsService.CACHE_MAP_KEY_LOGISTICS_AVG));
        }
		return ActResult.success(model);
	}

	/**
	 * 个人信息
	 */
	@RequestMapping("info.user")
	@NoCheckLogin
	@ResponseBody
	public ActResult<Object> userInfo(HttpServletRequest request) {
		UserFactory userFactory = userService.getById(loginUser.getId());
		return ActResult.success(userFactory);
	}

	/**
	 * 用户上传头像
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("uploadAvatar.user")
	@NoCheckLogin
	@ResponseBody
	public ActResult<String> uploadAvatar(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> map = multipartRequest.getFileMap();
		if (map.size() > 0) {
			Long size = 0l;
			for (String key : map.keySet()) {
				size = map.get(key).getSize();
				if (size > 2097152) {
					return ActResult.fail("头像大小不能超过2M");
				}
			}
			for (String fname : map.keySet()) {
				// 获得文件：
				MultipartFile file = multipartRequest.getFile(fname);
				// 获得文件名：
				String filename = file.getOriginalFilename();
				String type = filename.substring(filename.lastIndexOf('.'));
				try {
					String picTYpe = FileUtils.getPicType(file.getInputStream());
					if (picTYpe == null) {
						CommonsMultipartFile cf = (CommonsMultipartFile) file;
						cf.getFileItem().delete();
						return ActResult.fail("头像格式不支持");
					}
					ActResult<List<String>> as = uploader.uploadPic(file.getInputStream(), file.getSize(), type, loginUser.getId() + "");
					if (!as.isSuccess()) {
						return ActResult.fail("头像上传失败");
					} else {
						String address = "http://" + as.getData().get(0);
						return ActResult.success(address);
					}
				} catch (IOException e) {
					e.printStackTrace();
					return ActResult.fail("头像上传失败");
				}
			}
		}
		return ActResult.fail("头像未修改");
	}

	/**
	 * 更新用户基本信息
	 *
	 * @param request
	 * @param gender
	 * @param nickName
	 * @return
	 */
	@RequestMapping("update.user")
	@NoCheckLogin
	@ResponseBody
	public ActResult<Object> updateUser(HttpServletRequest request, String avatar, String gender, String nickName) {
		boolean updNickName = false;
		UserFactory userFactory = userService.getById(loginUser.getId());
		if (!StringUtils.isNullOrEmpty(avatar)) {
			if(!StringUtils.isEquals(avatar, userFactory.getAvatar())) {
				userFactory.setAvatar(avatar);
				userFactory.setShopLink(null);
			}
		}
		if (!StringUtils.isNullOrEmpty(nickName)) {
			userFactory.setNickName(nickName);
			updNickName = true;
		} else {
			if(StringUtils.isNullOrEmpty(userFactory.getNickName())) {
				userFactory.setNickName(userFactory.getUserName());
			}
		}
		if (!StringUtils.isNullOrEmpty(gender)) {
			if ("m".equals(gender) || "f".equals(gender) || "n".equals(gender)) {
				userFactory.setGender(gender);
			} else {
				return ActResult.fail("请输入正确性别");
			}
		}
		userService.update(userFactory);
		
		if(updNickName) {
			EasemobIMUtils.updUserNickName(EasemobIMService.APP_TYPE_SHOP+userFactory.getId(), userFactory.getNickName());
		}
		
		return ActResult.success(userFactory);
	}
	

	/**
	 * 扫描web登录二维码后，先进行提交
	 * 
	 * @param request
	 * @param uuid
	 * @param ticket
	 * @return
	 */
	@RequestMapping("scanQrForWebLogin")
	@NoCheckLogin
	@ResponseBody
	public ActResult<String> scanQrForWebLogin(HttpServletRequest request, String uuid,String ticket){
//		ActResult<User> act=ServiceFactory.getService().hasLogin(ticket);
//		if(act.isSuccess()) {
			redisUtil.setData("QrForLogin_" + uuid, "1", 50);
			us.scanQrForWebLogin(uuid);
			return ActResult.success("");
//		}
//		return ActResult.fail(act.getMsg());
	}
	/**
	 * 点击确认登录后处理
	 * @param request
	 * @param uuid
	 * @param ticket
	 * @return
	 */
	@RequestMapping("qrLoginForWeb")
	@NoCheckLogin
	@ResponseBody
	public ActResult<String> verify(HttpServletRequest request, String uuid,String ticket){
		if("1".equals(redisUtil.getData("QrForLogin_" + uuid))) {
			ActResult<Long> act= us.qrLogin(uuid,ticket,"seller_web_login");
			if(act.isSuccess()) {
				redisUtil.setData("QrForLogin_" + uuid, act.getMsg(), 50);
				return ActResult.success("");
			} 
			return ActResult.fail(act.getMsg());
		} else {
			return ActResult.fail("二维码时效已过，请刷新页面后重新扫描登录。");
		}
	}
	
	/**
	 * 点击确认登录后处理
	 * @param request
	 * @param uuid
	 * @param ticket
	 * @return
	 */
	@RequestMapping("pushMsg")
	@NoCheckLogin
	@ResponseBody
	public ActResult<String> pushMsg(Long userId,String title,String msg){
		loginFacade.doPushNotify(userId, null, title, msg);
		return ActResult.success("");
	}
	

	/**
	 * 点击确认登录后处理
	 * @param request
	 * @param uuid
	 * @param ticket
	 * @return
	 */
	@RequestMapping("pushMsg4order")
	@NoCheckLogin
	@ResponseBody
	public ActResult<String> pushMsg4order(String subOrderId,String title,String msg){
		Suborderitem record = new Suborderitem();
		record.setSubOrderId(subOrderId);
		List<Suborderitem> ls= suborderService.selectByModel(record);
		if(ls != null && !ls.isEmpty()) {
			Product p = productService.getById(ls.get(0).getProductId());
			if(p!=null) {
				UserDevice ud = userDeviceService.selectByShopAndSupplier(p.getShopId(), p.getSupplierId());
				if(ud!=null) {
					if(ud.getId()!=null) {
						loginFacade.doPushNotify(ud.getUserId(), ud, title, msg.replace("XXXXX", p.getName()));
					}
					
					//获取店铺名称
					String shopName = "";
					Shop s = shopService.getById(p.getShopId());
					if(s!=null) {
						shopName = s.getShopname();
					}
					
					AppPushUtil.pushWxNewOrder(ud.getUserId(), shopName, p.getName(), TimeUtil.getCurrentTime(), ls.get(0).getRealPay().toString(), subOrderId);
				}
				
			}
		}
		return ActResult.success("");
	}
	
	/**
	 * 点击确认登录后处理
	 * @param request
	 * @param uuid
	 * @param ticket
	 * @return
	 */
	@RequestMapping("sendSms")
	@NoCheckLogin
	@ResponseBody
	public ActResult<String> pushMsg(String phoneNumber,String contnt){
		if(sms.sendSms(phoneNumber, Constant.SMS_SIGNATURE, contnt, "myShop", false, null)){
			return ActResult.success("");
		} else {
			return ActResult.fail("发送失败");
		}
	}
	/**
	 * 微信个人信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("page")
	@NoCheckLogin
	public ModelAndView page(HttpServletRequest request,ModelAndView model,String shopId) {
		model.addObject("shopId", shopId);
		model.setViewName("user_info");
		return model;
	}
}
