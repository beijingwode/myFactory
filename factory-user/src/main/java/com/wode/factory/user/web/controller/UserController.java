
package com.wode.factory.user.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.wode.common.constant.UserConstant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.ClientAccessLog;
import com.wode.factory.model.Currency;
import com.wode.factory.model.Enterprise;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.ShippingAddress;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.MailService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.user.dao.SupplierDao;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.CartItem;
import com.wode.factory.user.service.ClientAccessLogService;
import com.wode.factory.user.service.CurrencyService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.ShippingAddressService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.service.UserExchangeTicketService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.CookieUtils;
import com.wode.factory.user.util.IPUtils;
import com.wode.factory.user.util.LoginUtil;
import com.wode.factory.user.util.MatrixToImageWriter;
import com.wode.factory.user.util.SessonRedisUtil;
import com.wode.factory.user.util.UserEmail;
import com.wode.factory.user.vo.ProductSpecificationsVo;
import com.wode.model.CommUser;

/**
 * 用户web层 User: guziye 用户注册、登录、激活、修改密码
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Qualifier("userService")
	@Autowired
	private UserService userService;
	@Qualifier("productSpecificationsService")
	@Autowired
	private ProductSpecificationsService productSpecificationsService;// 合并购物车

	@Autowired
	private ShippingAddressService sas;

	@Qualifier("redis")
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	SupplierDao supplierDao;
	@Autowired
	private ClientAccessLogService clientAccessLogService;

	private static final int CAR_REDIS_EXPTIME = 60 * 60 * 24 * 30 * 6;// redis 过期时间6个月

	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
	static MailService mail = ServiceFactory.getMailService(Constant.OUTSIDE_SERVICE_URL);

	@Autowired
	private UserBalanceService userBalanceService;

	@Autowired
	private CurrencyService currencyService;
	
	@Autowired
	private UserExchangeTicketService userExchangeTicketService;

	@RequestMapping("")
	public String tologin(HttpServletRequest request, ModelMap map, String from) {
		if (StringUtils.isNullOrEmpty(from)) {
			from = request.getHeader("Referer");
		}
		map.put("from", from);

		return "login";
	}

	/*
	 * 判断用户是否登录
	 */
	@RequestMapping("checkLogin")
	@ResponseBody
	public ActResult<String> checkLogin(HttpServletRequest request, HttpServletResponse response) {
		ActResult<String> ret = new ActResult<String>();
		ret.setSuccess(false);
		UserFactory user = getUser(request, response);
		if (null != user) {
			ret.setSuccess(true);
			if (StringUtils.isNullOrEmpty(user.getNickName()))
				ret.setData(user.getUserName());
			else
				ret.setData(user.getNickName());
		}
		return ret;
	}

	/*
	 * 获取购物车商品数量
	 */
	@RequestMapping("getCartNum")
	@ResponseBody
	public ActResult<Object> getCartNum(HttpServletRequest request, HttpServletResponse response) {
		ActResult<Object> ret = new ActResult<Object>();
		UserFactory user = getUser(request, response);
		Cart cart = null;
		int num = 0;
		List<CartItem> cartItems = null;
		if (null == user) {// 用户未登录，从cookie取购物车数量
			String uuid = CookieUtils.getUUID(request, response);
			String value = SessonRedisUtil.getData(UserConstant.CART_COOKIE + uuid, redisUtil);
			if (value != null && !value.trim().equals("") && !value.trim().equals("null")) {
				cart = JsonUtil.getObject(value, Cart.class);
			}
		} else {// 从redis去购物车数量
			String cartJson = redisUtil.getData(RedisConstant.CART_REDIS + "_" + user.getId());
			if (null != cartJson && !cartJson.trim().equals("") && !cartJson.trim().equals("null")) {
				cart = JsonUtil.getObject(cartJson, Cart.class);
			}
		}
		if (null != cart) {
			num = cart.calculateTotalNumber();
			cartItems = cart.getAllItems();
		}
		JSONObject obj = new JSONObject();
		obj.put("num", num);
		obj.put("cartItems", cartItems);
		ret.setData(obj);
		return ret;
	}

	/*
	 * 判断是否登录
	 */
	@RequestMapping("hasLogin")
	@ResponseBody
	public ActResult<UserFactory> hasLogin(HttpServletRequest request, HttpServletResponse response, String ticket,
			String remenber) throws UnsupportedEncodingException {
		ActResult<UserFactory> ar = this.getUserByTicket(ticket);
		if (ar == null)
			return ActResult.fail("ticket数据不正确");
		if (ar.isSuccess()) {
			UserFactory user = ar.getData();
			// if(user.getType()==2){//卖家帐号登录
			// ar.setData(null);
			// ar.setMsg("商家账户不能登录该客户端");
			// ar.setSuccess(false);
			// }else {//买家
			if (StringUtils.isEmpty(user.getNickName())) {
				user.setNickName(user.getUserName());
				if (user.getNickName().equals(user.getPhone())) {
					String nickName = user.getPhone();
					if (nickName.length() > 4) {
						nickName = "1***" + nickName.substring(nickName.length() - 4);
						user.setNickName(nickName);
					}
				}
			}
			// 合并购物车
			mergeCart(user.getId(), request, response);

			// 计入cooki及缓存
			LoginUtil.setLoginInfoForWeb(request, response, user, ticket, remenber, redisUtil);
			// }
		}
		return ar;
	}

	@RequestMapping("/loginBack{uid}")
	@NoCheckLogin
	public String loginBack(@PathVariable Long uid, String ticket, String remember, String returnUrl,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		ActResult<UserFactory> ar = this.getUserByTicket(ticket);
		// if(ar == null) return ActResult.fail("ticket数据不正确");
		if (ar.isSuccess()) {
			UserFactory user = ar.getData();
			// if(user.getType()==2){//卖家帐号登录
			// ar.setData(null);
			// ar.setMsg("商家账户不能登录该客户端");
			// ar.setSuccess(false);
			// }else {//买家
			if (StringUtils.isEmpty(user.getNickName())) {
				user.setNickName(user.getUserName());
				if (user.getNickName().equals(user.getPhone())) {
					String nickName = user.getPhone();
					if (nickName.length() > 4) {
						nickName = "1***" + nickName.substring(nickName.length() - 4);
						user.setNickName(nickName);
					}
				}
			}
			// 合并购物车
			mergeCart(user.getId(), request, response);

			// 计入cooki及缓存
			LoginUtil.setLoginInfoForWeb(request, response, user, ticket, remember, redisUtil);
			// }
		}

		if (StringUtils.isNullOrEmpty(returnUrl)) {
			returnUrl = "/index.html";
		} else {
			returnUrl = new String(org.apache.commons.codec.binary.Base64.decodeBase64(returnUrl.getBytes()));
		}

		return "redirect:" + returnUrl;
	}

	@RequestMapping("/registerBack{uid}")
	@NoCheckLogin
	public String registerBack(@PathVariable Long uid, String registerType, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		ActResult<String> re = new ActResult<String>();
		UserFactory user = userService.getById(uid);
		if (user == null) {
			ActResult<CommUser> ar = us.getUserById(uid);
			if (ar.isSuccess()) {
				UserFactory uf = new UserFactory();
				CommUser u = ar.getData();
				uf.setId(u.getUserId());
				uf.setUserName(u.getUserName());
				uf.setEmail(u.getUserEmail());
				uf.setEnabled(u.getEnabled());
				uf.setUsable(u.getUsable());
				uf.setCreatTime(new Date());
				uf.setNickName(u.getNickName());
				uf.setEnabled(0);
				uf.setType(1);
				uf.setPhone(u.getUserPhone());
				userService.specialSave(uf);
				user = uf;
			}
		}

		re = UserEmail.sendEmail(user.getId(), "register", "", user, redisUtil);

		return "redirect:/user/emailRegistrationSuccess?userId=" + uid;
	}

	/*
	 * 用户注销
	 */
	@RequestMapping("loginOut")
	public String loginOut(HttpServletResponse response, HttpServletRequest request, String from) {
		try {
			UserFactory user = getUser(request, response);
			if (user != null) {
				us.logout(CookieUtils.getTicket(request));
			}
			// 清除登陆信息
			LoginUtil.clearLoginInfoForWeb(request, response, redisUtil);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + Constant.COMM_USER_URL + "?from=myFactory&domain=" + Constant.SYSTEM_DOMAIN;
	}

	/*
	 * 用户激活(包括本地激活) guziye
	 */
	@RequestMapping("active")
	public String userActive(Long uid) {
		ActResult<String> ar = us.active(uid, UserConstant.COMFROM, "0", false, null);
		if (ar.isSuccess()) {
			UserFactory user = new UserFactory();
			user.setId(uid);
			user.setEnabled(1);
			userService.update(user);
		}
		if (ar.isSuccess())
			return "jsp/successPage";
		else
			return "jsp/failPage";
	}

	/*
	 * 用户邮箱注册成功发送确认邮件之后跳转成功提示页面 guziye
	 */
	@RequestMapping("emailRegistrationSuccess")
	public String userRegistrationByEmailSuccess(HttpServletRequest request, ModelMap map, Long userId) {
		map.put("userId", userId);
		return "register_email_success";
	}

	/**
	 * 用户点击邮件中注册成功确认邮件之后进行激活 guziye
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("registerCheckByEmail")
	public String checkByEmail(HttpServletRequest request, HttpServletResponse response, ModelMap map, Long userId,
			String code) throws UnsupportedEncodingException {
		String result = redisUtil.getMapData(UserConstant.BINDMAILFUNCTION + "_" + userId, "userId");
		String b = "0";
		String to = "check_email_success_or_false";
		String type = "";
		if (StringUtils.isNullOrEmpty(result)) {
			// 注册认证连接已失效
			to = "/prompt";
			type = "abate";
		} else {
			Long redisUserId = Long.parseLong(result);
			String redisCode = redisUtil.getMapData(UserConstant.BINDMAILFUNCTION + "_" + userId, "code");
			if (StringUtils.isEquals(userId, redisUserId) && StringUtils.isEquals(code, redisCode)) {
				UserFactory user = userService.selectById(userId);
				if (user == null) {
					to = "/prompt";
					type = "abate";
				} else {
					ActResult<String> ar = us.active(userId, UserConstant.COMFROM, "1", false, null);
					if (ar.isSuccess()) {
						user.setEnabled(1);
						userService.update(user);

						String email = user.getEmail();
						if (!StringUtils.isEmpty(email)) {
							email = email.substring(email.indexOf("@") + 1);
							Enterprise ent = userService.findByEmailPostfix(email);
							if (ent != null) {
								Map<String, Object> comMap = new HashMap<String, Object>();

								comMap.put("userName", user.getUserName());
								comMap.put("email", user.getEmail());
								comMap.put("enterpriseId", ent.getId());
								comMap.put("name", user.getEmail().substring(0, user.getEmail().indexOf("@")));

								HttpClientUtil.sendHttpRequest("post",
										com.wode.factory.user.util.Constant.QIYE_API_URL + "api/addEmployee", comMap);
							}
						}

						redisUtil.del(UserConstant.BINDMAILFUNCTION + "_" + user.getId());
						// 激活后自动登录
						SessonRedisUtil.setLoginId(CookieUtils.getUUID(request, response), user, redisUtil);

						// 计入cooki及缓存
						LoginUtil.setLoginInfoForWeb(request, response, user, ar.getData(), "1", redisUtil);
						b = "1";
						to = "check_email_success_or_false";
					}
				}
			}
		}
		map.put("type", type);
		map.put("result", b);
		map.put("userId", userId);
		return to;
	}

	/**
	 * 个人中心邮箱更改绑定
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/changeBindEmail")
	public String changeBindByEmail(HttpServletRequest request, HttpServletResponse response, ModelMap map, Long userId,
			String code) {
		UserFactory user = userService.selectById(userId);
		String to = "";
		String type = "";
		String result = redisUtil.getMapData(UserConstant.BINDMAILFUNCTION + "_" + userId, "userId");
		if (StringUtils.isNullOrEmpty(result)) {
			to = "/prompt";
			type = "abate";
		} else {
			Long redisUserId = Long
					.parseLong(redisUtil.getMapData(UserConstant.BINDMAILFUNCTION + "_" + userId, "userId"));
			String redisCode = redisUtil.getMapData(UserConstant.BINDMAILFUNCTION + "_" + userId, "code");
			if (StringUtils.isEquals(userId, redisUserId) && StringUtils.isEquals(code, redisCode)) {
				if (user == null) {
					to = "/prompt";
					type = "nullUser";
				} else {
					to = "/personal_security_bind_email";
					type = "bind";
					SessonRedisUtil.setLoginId(CookieUtils.getUUID(request, response), user, redisUtil);
				}
			} else {
				to = "/prompt";
				type = "abate";
			}
		}
		map.put("type", type);
		map.put("user", user);
		return to;
	}

	/**
	 * 个人中心密码修改第二步(邮件验证之后)
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/checkByEmail")
	public String securityPasswordByEmail(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Long userId, String code) throws UnsupportedEncodingException {
		String to = "/index";
		String tmpCode = "";
		String result = redisUtil.getMapData(UserConstant.FINDPWDBYEMAILFUNCTION + "_" + userId, "userId");
		if (StringUtils.isNullOrEmpty(result)) {
			// 密码修改确认连接已失效
			to = "/prompt";
			map.put("type", "abate");
		} else {
			Long redisUserId = Long
					.parseLong(redisUtil.getMapData(UserConstant.FINDPWDBYEMAILFUNCTION + "_" + userId, "userId"));
			String redisCode = redisUtil.getMapData(UserConstant.FINDPWDBYEMAILFUNCTION + "_" + userId, "code");
			if (StringUtils.isEquals(userId, redisUserId) && StringUtils.isEquals(code, redisCode)) {
				to = "/member/personal_security_phone2";
				UserFactory user = userService.selectById(userId);

				if (user == null) {
					to = "/prompt";
					map.put("type", "nullUser");
				} else {

					ActResult<String> ar = us.getTmpCode(user.getEmail());
					if (ar.isSuccess()) {
						tmpCode = ar.getData();
					}
					map.put("code", tmpCode);
					map.put("userName", user.getEmail());

					SessonRedisUtil.setLoginId(CookieUtils.getUUID(request, response), user, redisUtil);
					// 计入cooki及缓存
					LoginUtil.setLoginInfoForWeb(request, response, user, null, "1", redisUtil);
				}
			} else {
				to = "/prompt";
				map.put("type", "abate");
			}
		}
		return to;
	}

	/**
	 * 个人中心邮箱点击邮件连接绑定/更改绑定
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/bindEmailSuccess")
	public String bindEmailSuccess(HttpServletRequest request, HttpServletResponse response, ModelMap map, Long userId,
			String code, String email) {
		UserFactory user = userService.selectById(userId);
		String to = "";
		String type = "";
		if (user == null) {
			to = "/prompt";
			type = "nullUser";
		} else {
			String result = redisUtil.getMapData(UserConstant.BINDMAILFUNCTION + "_" + userId, "userId");
			if (StringUtils.isNullOrEmpty(result)) {
				// 绑定邮件连接已失效
				to = "/prompt";
				type = "abate";
			} else {
				ActResult<String> ar = new ActResult<String>();
				Long redisUserId = Long
						.parseLong(redisUtil.getMapData(UserConstant.BINDMAILFUNCTION + "_" + userId, "userId"));
				String redisCode = redisUtil.getMapData(UserConstant.BINDMAILFUNCTION + "_" + userId, "code");
				String redisEmail = redisUtil.getMapData(UserConstant.BINDMAILFUNCTION + "_" + userId, "email");
				if (StringUtils.isEquals(userId, redisUserId) && StringUtils.isEquals(code, redisCode)
						&& StringUtils.isEquals(email, redisEmail)) {
					ar = us.updateEmail(email, userId, UserConstant.COMFROM);
					if (ar.isSuccess()) {
						user.setEmail(email);
						userService.saveOrUpdate(user);
						redisUtil.del(UserConstant.BINDMAILFUNCTION + "_" + user.getId());
						UserFactory userFactory = userService.selectById(user.getId());
						SessonRedisUtil.setLoginId(CookieUtils.getUUID(request, response), user, redisUtil);
						to = "/member/personal_security__bind_email_success";
						type = "bind";
					}
				} else {
					// 绑定邮件连接已失效
					to = "/prompt";
					type = "abate";
				}
			}
		}
		map.put("type", type);
		return to;
	}

	/**
	 * 邮箱进行激活
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/activation")
	public String activationUser(HttpServletRequest request, HttpServletResponse response, ModelMap map, Long userId,
			String code, String email) {
		UserFactory user = userService.selectById(userId);
		String to = "";
		String type = "";
		if (user == null) {
			to = "/prompt";
			type = "nullUser";
		} else {
			String result = redisUtil.getMapData(UserConstant.BINDMAILFUNCTION + "_" + userId, "userId");
			if (StringUtils.isNullOrEmpty(result)) {
				// 绑定邮件连接已失效
				to = "/prompt";
				type = "abate";
			} else {
				ActResult<String> ar = new ActResult<String>();
				Long redisUserId = Long
						.parseLong(redisUtil.getMapData(UserConstant.BINDMAILFUNCTION + "_" + userId, "userId"));
				String redisCode = redisUtil.getMapData(UserConstant.BINDMAILFUNCTION + "_" + userId, "code");
				String redisEmail = redisUtil.getMapData(UserConstant.BINDMAILFUNCTION + "_" + userId, "email");
				if (StringUtils.isEquals(userId, redisUserId) && StringUtils.isEquals(code, redisCode)
						&& StringUtils.isEquals(email, redisEmail)) {
					ar = us.active(userId, UserConstant.COMFROM, "0", false, null);
					if (ar.isSuccess()) {
						user.setEnabled(1);
						userService.saveOrUpdate(user);
						redisUtil.del(UserConstant.BINDMAILFUNCTION + "_" + user.getId());
						UserFactory userFactory = userService.selectById(user.getId());
						SessonRedisUtil.setLoginId(CookieUtils.getUUID(request, response), user, redisUtil);
						to = "/member/personal_security__bind_email_success";
						type = "bind";
					}
				} else {
					// 绑定邮件连接已失效
					to = "/prompt";
					type = "abate";
				}
			}
		}
		map.put("type", type);
		return to;
	}

	/**
	 * 合并购物车
	 */
	private void mergeCart(long userId, HttpServletRequest request, HttpServletResponse response) {
		try {
			Cart redisCart = null;
			Cart cookieCart = null;
			String cartJson = redisUtil.getData(RedisConstant.CART_REDIS + "_" + userId);
			if (null != cartJson && !cartJson.trim().equals("") && !cartJson.trim().equals("null")) {
				redisCart = JsonUtil.getObject(cartJson, Cart.class);
			}
			String uuid = CookieUtils.getUUID(request, response);
			String value = SessonRedisUtil.getData(UserConstant.CART_COOKIE + uuid, redisUtil);
			if (value != null && !value.trim().equals("") && !value.trim().equals("null")) {
				cookieCart = JsonUtil.getObject(value, Cart.class);
			}
			if (null != cookieCart) {
				if (null == redisCart) {
					redisCart = new Cart();
				}
				List<CartItem> cookieCartItems = cookieCart.getAllItems();// redis 购物车信息
				// List<CartItem> redisCartItems = redisCart.getAllItems();//cookie 购物车信息

				for (CartItem cookieCartItem : cookieCartItems) {// 检查redis 是否有同一供应商
					CartItem inItem = redisCart.getItem(Long.valueOf(cookieCartItem.getPartNumber()));// 购物车的产品项
					if (inItem == null) {
						List<CartItem> psku = redisCart.getItemsByProductId(cookieCartItem.getProductId());
						for (CartItem cartItem2 : psku) {
							Long skuId = Long.parseLong(cartItem2.getPartNumber());
							ProductSpecificationsVo productSpecifications = productSpecificationsService.findByIdCache(
									Long.parseLong(cartItem2.getPartNumber()), cartItem2.getProductId().toString());
							if (productSpecifications != null) {
								if (!skuId.equals(productSpecifications.getId())) {
									cartItem2.setPartNumber(productSpecifications.getId() + "");
									cartItem2.setProductCode(productSpecifications.getProductCode());
									cartItem2.setPrice(productSpecifications.getPrice());
									cartItem2.setMaxCompanyTicket(productSpecifications.getMaxFucoin());
									if (cookieCartItem.getPartNumber().equals(productSpecifications.getId() + "")) {
										inItem = cartItem2;
									}
								}
							}
						}
					}

					if (inItem != null) {// 若已经购买，合并数量
						int quantity = inItem.getQuantity() + cookieCartItem.getQuantity();
						// TODO 判断库存
						inItem.setQuantity(quantity);
					} else {// 没购买，添加购物项
						redisCart.addItem(cookieCartItem);
					}

				}
				redisUtil.setData(RedisConstant.CART_REDIS + "_" + userId, JsonUtil.toJsonString(redisCart),
						CAR_REDIS_EXPTIME);
				SessonRedisUtil.delData(UserConstant.CART_COOKIE + uuid, redisUtil);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 厂用户基本信息修改（只修改本地数据） guziye
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("modifyuser")
	@ResponseBody
	public ActResult<UserFactory> modifyUserInfo(HttpServletRequest request, HttpServletResponse response,
			String nickName, String gender, String city, String province, String district, String avatar,
			String birthDay) throws UnsupportedEncodingException {
		ActResult<UserFactory> ar = new ActResult<UserFactory>();
		boolean updNickName = false;
		UserFactory user = getUser(request, response);
		if (!StringUtils.isEquals(avatar, user.getAvatar())) {
			user.setAvatar(avatar);
			user.setShopLink(null);
		}
		if (!StringUtils.isNullOrEmpty(nickName)) {
			user.setNickName(nickName);
			updNickName = true;
		} else {
			if (StringUtils.isNullOrEmpty(user.getNickName())) {
				user.setNickName(user.getUserName());
			}
		}

		if (StringUtils.isNullOrEmpty(gender))
			user.setGender("n");
		else
			user.setGender(gender);

		user.setCity(city);
		user.setProvince(province);
		user.setDistrict(district);

		if (!StringUtils.isNullOrEmpty(birthDay))
			user.setBirthday(TimeUtil.strToDate(birthDay));
		else
			user.setBirthday(null);

		userService.saveOrUpdate(user);
		SessonRedisUtil.setLoginId(CookieUtils.getUUID(request, response), user, redisUtil);
		// 修改cookie中的昵称
		CookieUtils.set(response, "nickname", URLEncoder.encode(user.getNickName(), "utf-8"),
				365 * (24 * 60 * 60 * 1000));

		ar.setMsg("修改成功");

		if (updNickName) {
			EnterpriseUser eu = userService.getEmpById(user.getId());
			if (eu != null) {
				if (StringUtils.isNullOrEmpty(eu.getName())) {
					eu.setName(user.getNickName());
					userService.updEmp(eu);
				} else if (eu.getName().equals(user.getPhone()) || eu.getName().equals(user.getEmail())) {
					eu.setName(user.getNickName());
					userService.updEmp(eu);
				}
			}
		}
		return ar;
	}

	/**
	 * 厂用户新增/修改收货地址 guziye
	 */
	@RequestMapping("addAddress")
	@ResponseBody
	public ActResult<ShippingAddress> addShippingAddress(HttpServletRequest request, HttpServletResponse response,
			String name, String cityName, String provinceName, String districtName, String address, String phone,
			int send, Long id, String aid) {
		ActResult<ShippingAddress> ar = new ActResult<ShippingAddress>();
		UserFactory user = getUser(request, response);
		if (user == null) {
			ar.setSuccess(false);
		} else {
			ShippingAddress sa = new ShippingAddress();
			sa.setId(id);
			sa.setAddress(address);
			sa.setName(name);
			sa.setAreaName(districtName);
			sa.setCityName(cityName);
			sa.setProvinceName(provinceName);
			sa.setPhone(phone);
			sa.setSend(send);
			sa.setAid(aid);
			sa.setUserId(user.getId());
			sas.saveOrupdateAddress(sa);
		}
		return ar;
	}

	/**
	 * 厂用户根据id查询收货地址 guziye
	 */
	@RequestMapping("selectAddress")
	@ResponseBody
	public ActResult<ShippingAddress> selectShippingAddress(HttpServletRequest request, HttpServletResponse response,
			Long id) {
		ActResult<ShippingAddress> ar = new ActResult<ShippingAddress>();
		UserFactory u = getUser(request, response);
		if (u == null) {
			ar.setSuccess(false);
		} else {
			ShippingAddress sa = new ShippingAddress();
			sa = sas.getById(u.getId(), id);
			if (sa != null)
				ar.setData(sa);
			else
				ar.setSuccess(false);
		}
		return ar;
	}

	/**
	 * 厂用户根据id删除收货地址 guziye
	 */
	@RequestMapping("removeAddress")
	@ResponseBody
	public ActResult<ShippingAddress> deleteShippingAddress(HttpServletRequest request, HttpServletResponse response,
			Long id) {
		ActResult<ShippingAddress> ar = new ActResult<ShippingAddress>();
		UserFactory u = getUser(request, response);
		if (u == null) {
			ar.setSuccess(false);
		} else {
			ShippingAddress sa = new ShippingAddress();
			sa = sas.getById(u.getId(), id);
			if (sa != null)
				sas.deleteAddress(u.getId(), id);
			else
				ar.setSuccess(false);
		}
		return ar;
	}

	/**
	 * 个人中心错误提示页面跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/prompt")
	public String prompt(HttpServletRequest request, ModelMap map, String type) {
		map.put("type", type);
		String to = "/prompt";
		if (StringUtils.isEquals(type, "login")) {
			to = "/index";
		}
		return to;
	}

	/**
	 * 通过绑定手机/邮箱找回密码
	 */
	@RequestMapping("/forgetPassword")
	public String frorgetPassword(HttpServletRequest request, ModelMap map) {
		return "find_password";
	}

	/**
	 * 通过绑定手机找回密码发送验证码成功
	 */
	@RequestMapping("/sendSmsSuccess")
	public String sendSmsSuccess(HttpServletRequest request, ModelMap map, String phone) {
		map.put("allPhone", phone);
		map.put("phone", StringUtils.subString(phone, 3, 7, " **** "));
		ActResult<UserFactory> re = userService.findByPhone(phone);
		map.put("uid", re.getData().getId());
		return "find_password_send_sms";
	}

	/**
	 * 通过绑定邮箱找回密码发送邮件成功
	 */
	@RequestMapping("/sendEmailSuccess")
	public String sendEmailSuccess(HttpServletRequest request, ModelMap map, String email) {
		map.put("email", email);
		// map.put("phone", StringUtils.subString(phone, 3, 7, " **** "));
		return "find_password_send_email";
	}

	/**
	 * 通过绑定手机找回密码修改密码
	 */
	@RequestMapping("/modifyPasswordByPhone")
	public String modifyPasswordByPhone(HttpServletRequest request, ModelMap map, String phone, String code) {
		map.put("phone", phone);
		map.put("code", code);
		return "find_password_modify";
	}

	/**
	 * 通过绑定手机/邮箱找回密码成功
	 */
	@RequestMapping("/modifyPasswordSuccess")
	public String modifyPasswordSuccess(HttpServletRequest request, ModelMap map) {
		return "find_password_success";
	}

	/**
	 * 厂用户根据邮箱/手机号码找回密码（未连接用户通用服务器进行查询，只查询厂库本地用户） guziye
	 */
	@RequestMapping("findPasswordByUserName")
	@ResponseBody
	public ActResult<String> findPasswordByUser(HttpServletRequest request, ModelMap map, String userName) {
		ActResult<String> ar = new ActResult<String>();
		ActResult<UserFactory> re = new ActResult<UserFactory>();
		if (StringUtils.isPhoneNumber(userName)) {
			re = userService.findByPhone(userName);
			if (!re.isSuccess()) {
				ActResult<CommUser> result = us.findByPhone(userName);
				if (result.isSuccess()) {
					UserFactory uf = new UserFactory();
					CommUser u = result.getData();
					uf.setId(u.getUserId());
					uf.setUserName(u.getUserName());
					uf.setEmail(u.getUserEmail());
					uf.setEnabled(u.getEnabled());
					uf.setUsable(u.getUsable());
					uf.setCreatTime(new Date());
					uf.setNickName(u.getNickName());
					uf.setEnabled(u.getEnabled());
					uf.setType(u.getUserType());
					uf.setPhone(u.getUserPhone());
					userService.specialSave(uf);

					if (result.getData().getUserType() == 1) {
						ar.setMsg("验证码已发送，有效时间为30分钟");
						ar.setData("/user/sendSmsSuccess?phone=" + userName);
					} else {
						ar.setSuccess(false);
						ar.setMsg("非法用户");
					}
				} else {
					ar.setSuccess(false);
					ar.setMsg(re.getMsg());
				}
			} else {
				if (re.getData().getType() == 1) {
					ar.setMsg("验证码已发送，有效时间为30分钟");
					ar.setData("/user/sendSmsSuccess?phone=" + userName);
				} else {
					ar.setSuccess(false);
					ar.setMsg("非法用户");
				}
			}
		} else if (StringUtils.isEmail(userName)) {
			re = userService.findByEmail(userName);
			if (!re.isSuccess()) {
				ActResult<CommUser> result = us.findByEmail(userName);
				if (result.isSuccess()) {
					UserFactory uf = new UserFactory();
					CommUser u = result.getData();
					uf.setId(u.getUserId());
					uf.setUserName(u.getUserName());
					uf.setEmail(u.getUserEmail());
					uf.setEnabled(u.getEnabled());
					uf.setUsable(u.getUsable());
					uf.setCreatTime(new Date());
					uf.setNickName(u.getNickName());
					uf.setEnabled(u.getEnabled());
					uf.setType(u.getUserType());
					uf.setPhone(u.getUserPhone());
					userService.specialSave(uf);

					if (result.getData().getEnabled() == 0) {
						ar.setSuccess(false);
						ar.setMsg("该绑定邮箱帐号未激活，请先进行用户激活");
					} else if (result.getData().getUserType() == 1) {
						ar.setSuccess(true);
						ar.setData("/user/sendEmailSuccess?email=" + userName);
					} else {
						ar.setSuccess(false);
						ar.setMsg("非法用户,可能是商家端注册的用户");
					}
				} else {
					ar.setSuccess(false);
					ar.setMsg(re.getMsg());
				}
			} else {
				if (re.getData().getEnabled() == 0) {
					ar.setSuccess(false);
					ar.setMsg("该绑定邮箱帐号未激活，请先进行用户激活");
				} else {
					if (re.getData().getType() == 1) {
						ar.setSuccess(true);
						ar.setData("/user/sendEmailSuccess?email=" + userName);
					} else {
						ar.setSuccess(false);
						ar.setMsg("非法用户");
					}
				}
			}

			if (re.isSuccess()) {
				Boolean result = sendEmailFindPassword(userName);
				if (!result) {
					ar.setSuccess(false);
					ar.setMsg("邮件发送失败，请检查您的绑定邮箱是否正常使用");
				} else {
					ar.setMsg("邮件已发送");
					ar.setData("/user/sendEmailSuccess?email=" + userName);
				}
			}
		} else {
			ar.setSuccess(false);
			ar.setData("请填写有效的手机号码或邮箱");
		}
		return ar;
	}

	private Boolean sendEmailFindPassword(String userName) {
		UUID code = UUID.randomUUID();
		Map<String, String> redisMap = new HashMap<String, String>();
		redisMap.put("email", userName);
		redisMap.put("code", code.toString());
		redisUtil.setData(UserConstant.FINDPWDBYEMAILFUNCTION + "_" + userName, redisMap, 60 * 30);// 有效期为30分钟
		StringBuilder text = new StringBuilder();
		text.append("<body>");
		text.append("<div style='width:100%; height:90px; margin:0 auto; border-bottom:2px solid #eee;'>");
		text.append("<div style='width:690px; margin:0 auto; padding-top:30px; height:60px; overflow:hidden;'>");
		text.append(
				"<div style='width:116px; height:50px; float:left;'><a href='http://www.wd-w.com/index.html'><img src='http://www.wd-w.com/images/logo.png' width='116' height='50'></a></div>");
		text.append("<div style='float:right; padding-top:10px;'>");
		text.append(
				"<a style='font:14px Microsoft YaHei; color:#828282; text-decoration:none;' href='javascript:void(0);'>"
						+ "<i style='width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(images/emailicon.png) no-repeat 0 0;'></i>我的网</a>"
						+ "<em style='font:14px Microsoft YaHei; color:#828282; margin:0 10px;'>|</em>");
		text.append(
				"<a style='font:14px Microsoft YaHei; color:#828282; text-decoration:none;' href='javascript:void(0);'>"
						+ "<i style='width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(images/emailicon.png) no-repeat 0 -26px;'></i>帮助中心</a></div></div></div>");
		text.append("<div style='width:100%; margin:0 auto; text-align:center;'>");
		text.append("<h2 style='font:18px/36px Microsoft YaHei; color:#434343; margin-top:50px;'>亲爱的我的网用户，您好：</h2>");
		text.append("<h3 style='font:20px/36px Microsoft YaHei; color:#8fc31f;'>您正在进行密码找回操作，请确认，此链接半小时内有效，请及时确认。</h3>");
		text.append(
				"<p style='width:300px; height:50px; margin:0 auto; text-align:center; background:#ff6161; margin-top:30px; border-radius:4px; behavior:url(iecss3/PIE.htc); position:relative; z-index:2;'>"
						+ "<a style='display:block; font:18px/50px Microsoft YaHei; color:#fff; text-decoration:none;' href='http://www.wd-w.com/user/findPasswordByEmail?email="
						+ userName + "&code=" + code.toString() + "'>请点击这里完成确认</a></p>");
		text.append(
				"<p style='margin-top:50px; font:14px Microsoft YaHei; color:#434343;'>如果您不能点击上面的链接，您可以将下面的链接复制到浏览器进行访问：</p>");
		text.append("<p style='margin:5px 0 16px;'>"
				+ "<a style='font:14px Microsoft YaHei; color:#2b8dff; text-decoration:none;' href='javascript:void(0);'>"
				+ "http://www.wd-w.com/user/findPasswordByEmail?email=" + userName + "&code=" + code.toString()
				+ "</a></p>");
		text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>为了保证您的账号安全，请在半小时内完成确认。</p>");
		text.append("<div style='width:100%; margin:24px auto 0; border-top:1px solid #eee;'></div>");
		text.append("<div style='width:690px; margin:0 auto; padding-top:20px; text-align:left;'>");
		text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>收到此邮件，说明您已是我的网尊贵的会员。</p>");
		text.append(
				"<p style='font:12px/20px Microsoft YaHei; color:#828282;'>为确保您接受的服务信息不被当做垃圾邮件处理，请将service@wodechang.com添加为联系人。</p>");
		text.append("</div></div>");
		/*
		 * String text =
		 * "这是一封找回密码确认邮件，请点击以下链接确认：http://www.wd-w.com/user/findPasswordByEmail?email="
		 * +userName+"&code="+code.toString();
		 */
		Boolean result = mail.sendMail(userName, "找回密码确认", text.toString(), null, null, "myFactory", false, null);
		return result;
	}

	private Boolean sendEmailCode(String userName, String code) {
		Map<String, String> redisMap = new HashMap<String, String>();
		redisMap.put("email", userName);
		redisMap.put("code", code);
		redisUtil.setData(UserConstant.FINDPWDBYEMAILFUNCTION + "_" + userName, redisMap, 60 * 30);// 有效期为30分钟
		StringBuilder text = new StringBuilder();
		text.append("<body>");
		text.append("<div style='width:100%; height:90px; margin:0 auto; border-bottom:2px solid #eee;'>");
		text.append("<div style='width:690px; margin:0 auto; padding-top:30px; height:60px; overflow:hidden;'>");
		text.append(
				"<div style='width:116px; height:50px; float:left;'><a href='http://www.wd-w.com/index.html'><img src='http://www.wd-w.com/images/logo.png' width='116' height='50'></a></div>");
		text.append("<div style='float:right; padding-top:10px;'>");
		text.append(
				"<a style='font:14px Microsoft YaHei; color:#828282; text-decoration:none;' href='javascript:void(0);'>"
						+ "<i style='width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(images/emailicon.png) no-repeat 0 0;'></i>我的网</a>"
						+ "<em style='font:14px Microsoft YaHei; color:#828282; margin:0 10px;'>|</em>");
		text.append(
				"<a style='font:14px Microsoft YaHei; color:#828282; text-decoration:none;' href='javascript:void(0);'>"
						+ "<i style='width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(images/emailicon.png) no-repeat 0 -26px;'></i>帮助中心</a></div></div></div>");
		text.append("<div style='width:100%; margin:0 auto; text-align:center;'>");
		text.append("<h1 style=\"font:24px/36px Microsoft YaHei; color:#8fc31f; margin:50px 0 33px;\">您好，请在校验码输入框中输入"
				+ code + "<br>以完成操作</h1>");
		text.append("<p style=\"font:14px/20px Microsoft YaHei; color:#434343;\">您好，您正在使用我的网找回密码/安全设置功能！</p>");
		text.append(
				"<p style=\"font:14px/20px Microsoft YaHei; color:#434343; margin-bottom:12px;\">注意：此操作可能会修改您的密码、手机号、登录邮箱。如非本人操作，请及时登录并修改密码以保证账户安全</p>");
		text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">（工作人员不会向您索取此校验码，请勿泄漏！）</p>");
		text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>为了保证您的账号安全，请在半小时内完成确认。</p>");
		text.append("<div style='width:100%; margin:24px auto 0; border-top:1px solid #eee;'></div>");
		text.append("<div style='width:690px; margin:0 auto; padding-top:20px; text-align:left;'>");
		text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>收到此邮件，说明您已是我的网尊贵的会员。</p>");
		text.append(
				"<p style='font:12px/20px Microsoft YaHei; color:#828282;'>为确保您接受的服务信息不被当做垃圾邮件处理，请将service@wodechang.com添加为联系人。</p>");
		text.append("</div></div>");
		/*
		 * String text =
		 * "这是一封找回密码确认邮件，请点击以下链接确认：http://www.wd-w.com/user/findPasswordByEmail?email="
		 * +userName+"&code="+code.toString();
		 */
		Boolean result = mail.sendMail(userName, "找回密码/安全设置身份确认", text.toString(), null, null, "myFactory", false,
				null);
		return result;
	}

	/**
	 * 通过绑定邮箱找回密码发送邮件成功
	 */
	@RequestMapping("/findPasswordSendEmail")
	public String findPasswordSendEmail(HttpServletRequest request, ModelMap map, String email) {
		this.sendEmailFindPassword(email);
		map.put("email", email);
		// map.put("phone", StringUtils.subString(phone, 3, 7, " **** "));
		return "find_password_send_email";
	}

	/**
	 * 厂用户绑定邮箱找回密码，验证邮箱连接（只查厂库） guziye
	 */
	@RequestMapping("findPasswordByEmail")
	@NoCheckLogin
	public String checkEmailCode(HttpServletRequest request, ModelMap map, String code, String email) {
		String redisEmail = redisUtil.getMapData(UserConstant.FINDPWDBYEMAILFUNCTION + "_" + email, "email");
		String to = "";
		String type = "";
		String tmpCode = "";
		if (StringUtils.isNullOrEmpty(redisEmail)) {
			// 连接已失效
			to = "/prompt";
			type = "abate";
		} else {
			String redisCode = redisUtil.getMapData(UserConstant.FINDPWDBYEMAILFUNCTION + "_" + email, "code");
			if (StringUtils.isEquals(email, redisEmail) && StringUtils.isEquals(code, redisCode)) {
				UserFactory user = userService.findByEmail(email).getData();
				if (user == null) {
					to = "/prompt";
					type = "nullUser";
				} else {
					ActResult<String> ar = us.getTmpCode(email);
					if (ar.isSuccess()) {
						tmpCode = ar.getData();
					}
					to = "redirect:" + Constant.COMM_USER_URL + "modifyPasswordByPhone?from=myFactory&domain="
							+ Constant.SYSTEM_DOMAIN + "&returnUrl=";
				}
			} else {
				to = "/prompt";
				type = "abate";
			}
		}
		map.put("type", type);
		map.put("phone", email);
		map.put("code", tmpCode);
		return to;
	}

	/**
	 * 发送邮件
	 *
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "sendEmailSecurity.json")
	@NoCheckLogin
	@ResponseBody
	public ActResult<String> emailFindPassword(String toEmail, String type, String userId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 发送邮件后缀
		if (StringUtils.isNullOrEmpty(toEmail)) {
			return ActResult.fail("请输入正确的邮箱！");
		} else {
			String code = StringUtils.randomNum() + "";
			Boolean sendMailResult = sendEmailCode(toEmail, code);

			// if(sendMailResult) {
			return ActResult.successSetMsg(userId + "_" + code);
			// } else {
			// return ActResult.fail("发送邮件失败，");
			// }
		}
	}

	/**
	 * 厂用户绑定邮箱找回密码，验证邮箱连接（只查厂库） guziye
	 */
	@RequestMapping("writeUserResult")
	public String writeUserResult(HttpServletRequest request, ModelMap map, String type, String data,
			String showVcode) {
		String to = "/writeUserResult";

		map.put("type", type);
		map.put("data", data);
		map.put("showVcode", showVcode);
		return to;
	}

	/**
	 * 厂用户绑定邮箱找回密码，验证邮箱连接（只查厂库） guziye
	 * 
	 * @throws IOException
	 */
	@RequestMapping("toLogin")
	public void toLogin(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {

		if (url.contains("://")) {
			url = url.substring(url.indexOf("://") + 3);
		}
		if (url.indexOf("/") != 0) {
			url = url.substring(url.indexOf("/"));
		}

		String from = org.apache.commons.codec.binary.Base64.encodeBase64String(url.getBytes());
		response.sendRedirect(
				Constant.COMM_USER_URL + "?from=myFactory&domain=" + Constant.SYSTEM_DOMAIN + "&returnUrl=" + from);
	}

	@RequestMapping("getQrForLogin")
	public void getQrForLogin(HttpServletRequest request, HttpServletResponse response, int num)
			throws WriterException, IOException {
		String text = "uuid=" + CookieUtils.getUUID(request, response) + "&todo=QrForLogin&num=" + num;
		int width = 140;
		int height = 140;
		Hashtable hints = new Hashtable();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		String format = "png";
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
		MatrixToImageWriter.writeToStream(bitMatrix, format, response.getOutputStream());
	}

	/*
	 * 判断是否登录
	 */
	@RequestMapping("getQrTicket")
	@ResponseBody
	public void getQrTicket(HttpServletRequest request, HttpServletResponse response, int num) throws IOException {
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		String uuid = CookieUtils.getUUID(request, response);
		String t = redisUtil.getData("QrForLogin_" + uuid);
		if (!StringUtils.isEmpty(t) && !"1".equals(t)) {
			redisUtil.del("QrForLogin_" + uuid);
		}
		response.getWriter().write("showQrMsg('" + t + "');");
	}

	@RequestMapping("clientLog")
	public void clientLog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = SessonRedisUtil.getLoginId(request, response, redisUtil);
		clientAccessLogService.saveNormal(ClientAccessLog.ACCESS_TYPE_INDEX, "index", "首页",
				StringUtils.isEmpty(uid) ? null : NumberUtil.toLong(uid), null, CookieUtils.getUUID(request, response),
				IPUtils.getClientAddress(request));
	}

	@RequestMapping("activityLog")
	public void activityLog(String url, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = SessonRedisUtil.getLoginId(request, response, redisUtil);
		clientAccessLogService.saveActivity("pc", url, StringUtils.isEmpty(uid) ? null : NumberUtil.toLong(uid), null,
				CookieUtils.getUUID(request, response), IPUtils.getClientAddress(request));
	}

	@RequestMapping("/getLogoAndBalance")
	@ResponseBody
	public ActResult<Object> getLogoAndBalance(HttpServletRequest request, HttpServletResponse response) {
		UserFactory user = getUser(request, response);
		ActResult<Object> act = new ActResult<Object>();
		if(null == user) {
			act.setSuccess(false);
			return act;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		List<UserBalance> list = userBalanceService.findByUser(user.getId());
		for (UserBalance ub : list) {
			Currency currency = currencyService.getById(ub.getCurrencyId());
			if (currency != null) {
				if(!"point".equals(currency.getName())) {
					map.put(currency.getName(), ub.getBalance());
				}
			}
		}
		//计算换领总额
		List<UserExchangeTicket> userExchangeList = userExchangeTicketService.usingTicket(user.getId());
		BigDecimal total = new BigDecimal(0.00);
		if(null != userExchangeList && userExchangeList.size() >0) {
			for (UserExchangeTicket userExchangeTicket : userExchangeList) {
				total = total.add(userExchangeTicket.getEmpAvgAmount().subtract(userExchangeTicket.getUsedAmount()));
			}
		}
		map.put("userExchange", total);
		
		if(!StringUtils.isEmpty(user.getSupplierId())) {
			Supplier s= supplierDao.getById(user.getSupplierId());
			if(s!=null && !StringUtils.isEmpty(s.getFirmLogo())) {
				map.put("logo", s.getFirmLogo());
			}
			map.put("comId", user.getSupplierId());
		}
		act.setData(map);
		return act;
	}
}
