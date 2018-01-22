package com.wode.api.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.wode.api.facade.LoginFacade;
import com.wode.common.constant.UserConstant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.FileUtils;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.Currency;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserImGroup;
import com.wode.factory.model.UserImGroupMember;
import com.wode.factory.model.UserWeixin;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.EasemobIMService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.SmsService;
import com.wode.factory.outside.service.UploadService;
import com.wode.factory.service.GroupBuyService;
import com.wode.factory.user.dao.SupplierDao;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.CartItem;
import com.wode.factory.user.model.SupplierTemp;
import com.wode.factory.user.service.CollectionProductService;
import com.wode.factory.user.service.CollectionShopService;
import com.wode.factory.user.service.CurrencyService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.service.SuborderitemService;
import com.wode.factory.user.service.SupplierTempService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.service.UserExchangeTicketService;
import com.wode.factory.user.service.UserImGroupMemberService;
import com.wode.factory.user.service.UserImGroupService;
import com.wode.factory.user.service.UserLimitTicketService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.service.UserWeixinService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.EasemobIMUtils;
import com.wode.factory.user.vo.OrderTypeCountVO;
import com.wode.factory.user.vo.ProductSpecificationsVo;
import com.wode.factory.vo.GroupBuyVo;
import com.wode.model.CommUser;

/**
 * 2015-6-17
 *
 * @author 谷子夜
 */
@Controller
@RequestMapping("/user")
@SuppressWarnings("unchecked")
@ResponseBody
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	@Qualifier("productSpecificationsService")
	@Autowired
	private ProductSpecificationsService productSpecificationsService;

	@Autowired
	CollectionProductService collectionProductService;

	@Autowired
	CollectionShopService collectionShopService;

	@Autowired
	SuborderService suborderService;

	@Autowired
	RedisUtil redisUtil;

	@Autowired
	CurrencyService currencyService;

	@Autowired
	UserBalanceService userBalanceService;
	
	@Autowired
	SuborderitemService suborderitemService;

	@Autowired
	private GroupBuyService groupBuyService;
	@Autowired
	private UserImGroupMemberService userImGroupMemberService;
	@Autowired
	private UserImGroupService userImGroupService;

	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private LoginFacade loginFacade;
	@Autowired
	private UserExchangeTicketService userExchangeTicketService;
	@Autowired
	private SupplierTempService supplierTempService;
	@Autowired
	private UserLimitTicketService userLimitTicketService;
	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
	static UploadService upservice = ServiceFactory.getUploadService(Constant.OUTSIDE_SERVICE_URL);
	static SmsService sms = ServiceFactory.getSmsService(Constant.OUTSIDE_SERVICE_URL);

	private static final int CAR_REDIS_EXPTIME = 60 * 60 * 24 * 30 * 6;//redis 过期时间6个月

	@Autowired
	private UserWeixinService userWeixinService;
	
	/**
	 * 功能：验证密码找回/修改验证码
	 *
	 * @param request
	 * @param phoneNumber
	 * @param code
	 * @return
	 */
	@RequestMapping("checkCode")
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
	public ActResult<String> commit(String ticket, String mobileId,String aliasName,String deviceName,String deviceType) {
		ActResult<String> rnt= loginFacade.doCommit(ticket, mobileId, aliasName, deviceName, deviceType);
		if(rnt.isSuccess()) {
			ActResult<UserFactory>  uf =loginFacade.hasLogin(ticket);
			this.mergeCart(mobileId, uf.getData().getId());
		}
		return rnt;
	}

	/**
	 * 用户注销
	 */
	@RequestMapping("logout")
	public ActResult<String> loginOut(HttpServletRequest request, String ticket) {
		loginFacade.logOut(ticket);
		return ActResult.successSetMsg("注销成功");
	}

	/**
	 * 功能：个人中心首页信息整合（1、昵称；2、收藏和订单个数统计）
	 *
	 * @author 刘聪
	 * @since 2015-06-17
	 */
	@RequestMapping("statistic.user")
	public ActResult<String> statistic(ModelMap model) {
		// 商品收藏个数
		int wishlistCount = collectionProductService.selectProductCount(loginUser.getId());
		// 店铺收藏个数
		int shopCollectCount = collectionShopService.selectShopCount(loginUser.getId());

		// （0、未支付（待支付），1、已支付（待发货），2、已发货（待收货），3、退单申请中，4、已收货（待评价），10、买家已评价，11、已退货完毕，-1、已取消）
		List<OrderTypeCountVO> lst= suborderService.getOrderCountByUserId(loginUser.getId(), null, null);
		// 待付款订单个数
		int nonPaymentCount = 0;//suborderService.getOrderByUserIdCount(loginUser.getId(), 0, null);
		// 待发货订单个数
		int unfilledCount = 0;//suborderService.getOrderByUserIdCount(loginUser.getId(), 1, null);
		// 待收货订单个数
		int notReceivingCount = 0;//suborderService.getOrderByUserIdCount(loginUser.getId(), 2, null);
		// 待评价订单个数
		int unvaluedCount = 0;//suborderService.getOrderByUserIdCount(loginUser.getId(), 4, 0);
		List<OrderTypeCountVO> lst2= suborderService.getOrderCountByUserId(loginUser.getId(), null, 0);
		for (OrderTypeCountVO orderTypeCountVO : lst2) {
			if(orderTypeCountVO.getStatus()!=null && orderTypeCountVO.getStatus()==4){
				unvaluedCount=orderTypeCountVO.getCnt();
				break;
			}
		}
		// 待评价订单中试用品个数
		int trialCount = 0;
		if(unvaluedCount>0) {
			trialCount=suborderitemService.getTrialCountByUser(loginUser.getId());
		}
		// 退货退款申请中
		int refundOrderCount = 0;//suborderService.getOrderByUserIdCount(loginUser.getId(), 3, null);
		// 已退货完毕
		int refundOrderSuccessCount = 0;//suborderService.getOrderByUserIdCount(loginUser.getId(), 11, null);
		//退货退款失败
		int refundOrderFailCount = 0;//suborderService.getOrderByUserIdCount(loginUser.getId(), -11, null);
		
		//仅退款申请中
		int refundCount = 0;//suborderService.getOrderByUserIdCount(loginUser.getId(), 5, null);
		// 已退款成功
		int refundSuccessCount = 0;//suborderService.getOrderByUserIdCount(loginUser.getId(), 12, null);
		//仅退款失败
		int refundFailCount = 0;//suborderService.getOrderByUserIdCount(loginUser.getId(), -12, null);
		
		int supplierAgreeCount= 0;//商家同意退货
		int supplierRefuseCount= 0;//商家同意退货
		int supplierAgreeMoneyCount= 0;//商家同意退款
		int supplierRefuseMoneyCount= 0;//商家拒绝退款

		for (OrderTypeCountVO orderTypeCountVO : lst) {
			if(orderTypeCountVO.getStatus()==null) continue;
			
			switch(orderTypeCountVO.getStatus()) {
			case 0:
				nonPaymentCount=orderTypeCountVO.getCnt();
				break;
			case 1:
				unfilledCount=orderTypeCountVO.getCnt();
				break;
			case 2:
				notReceivingCount=orderTypeCountVO.getCnt();
				break;
			case 3:
				refundOrderCount=orderTypeCountVO.getCnt();
				break;
			case 5:
				refundCount=orderTypeCountVO.getCnt();
				break;
			case 11:
				refundOrderSuccessCount=orderTypeCountVO.getCnt();
				break;
			case 12:
				refundSuccessCount=orderTypeCountVO.getCnt();
				break;
			case -11:
				refundOrderFailCount=orderTypeCountVO.getCnt();
				break;
			case -12:
				refundFailCount=orderTypeCountVO.getCnt();
				break;
			case 13:
				supplierAgreeCount=orderTypeCountVO.getCnt();
				break;
			case 14:
				supplierRefuseCount=orderTypeCountVO.getCnt();
				break;
			case 15:
				supplierAgreeMoneyCount=orderTypeCountVO.getCnt();
				break;
			case 16:
				supplierRefuseMoneyCount=orderTypeCountVO.getCnt();
				break;
			}
		}
		
		UserFactory userFactory = userService.getById(loginUser.getId());
		
		// 个人信息:昵称
		model.addAttribute("nickName", userFactory.getNickName());
		// 个人信息:头像
		model.addAttribute("avatar", userFactory.getAvatar());
		// 个人信息:用户类型
		model.addAttribute("employeeType", userFactory.getEmployeeType());
		// 商品收藏个数
		model.addAttribute("wishlistCount", wishlistCount);
		// 店铺收藏个数
		model.addAttribute("shopCollectCount", shopCollectCount);
		// 待付款订单个数
		model.addAttribute("nonPaymentCount", nonPaymentCount);
		// 待发货订单个数
		model.addAttribute("unfilledCount", unfilledCount);
		// 待收货订单个数
		model.addAttribute("notReceivingCount", notReceivingCount);
		// 待评价订单个数
		model.addAttribute("unvaluedCount", unvaluedCount);
		// 待评价订单中试用品个数
		model.addAttribute("trialCount", trialCount);
		// 退款/售后个数
		model.addAttribute("refundCount", refundOrderCount + refundOrderSuccessCount + refundOrderFailCount + refundCount + refundSuccessCount + refundFailCount+supplierAgreeCount+supplierRefuseCount+supplierAgreeMoneyCount+supplierRefuseMoneyCount);
		// 角色
		model.addAttribute("role", userFactory.getRole());
		Currency currencyTicket = currencyService.findByName("companyTicket");
		Currency currencyBalance = currencyService.findByName("balance");
		Currency currencyPoint = currencyService.findByName("point");
		UserBalance ub = null;//userBalanceService.findByUserAndType(loginUser.getId(), currencyTicket.getId());
		UserBalance balance = null;//userBalanceService.findByUserAndType(loginUser.getId(), currencyBalance.getId());
		UserBalance point = null;//userBalanceService.findByUserAndType(loginUser.getId(), currencyPoint.getId());
		
		List<UserBalance> lstUB = userBalanceService.findByUser(loginUser.getId());
		for (UserBalance userBalance : lstUB) {
			if(userBalance.getCurrencyId().equals(currencyTicket.getId())) {
				ub=userBalance;
			} else if(userBalance.getCurrencyId().equals(currencyBalance.getId())) {
				balance=userBalance;
			} else if(userBalance.getCurrencyId().equals(currencyPoint.getId())) {
				point=userBalance;
			} 
		}
		
		UserFactory uf = userService.getById(loginUser.getId());
		if (!StringUtils.isEmpty(uf.getSupplierId())) {
			Supplier sup = supplierDao.getById(uf.getSupplierId());
			if(sup == null) {
				SupplierTemp supt = supplierTempService.getById(uf.getSupplierId());
				model.addAttribute("companyName",supt.getComName() == null ? "" : supt.getComName());
			}else {
				model.addAttribute("companyName",StringUtils.isEmpty(sup.getNickName())?sup.getComName():sup.getNickName());
			}
		}
		//换领币余额
		BigDecimal totalExchangeTicket = BigDecimal.ZERO;
		BigDecimal exchangeTicket = BigDecimal.ZERO;
		List<UserExchangeTicket> ets = userExchangeTicketService.usingTicket(loginUser.getId());
		for (UserExchangeTicket userExchangeTicket : ets) {
			totalExchangeTicket=totalExchangeTicket.add(userExchangeTicket.getEmpAvgAmount());
			exchangeTicket=exchangeTicket.add(userExchangeTicket.getUsedAmount());	
		}
		model.addAttribute("exchangeTicket",totalExchangeTicket.subtract(exchangeTicket));
		//内购券余额
		if (StringUtils.isEmpty(ub))
			model.addAttribute("companyTicket", 0);
		else
			model.addAttribute("companyTicket", ub.getBalance() == null ? 0 : ub.getBalance());
		if (StringUtils.isEmpty(balance))
			model.addAttribute("balance", 0);
		else
			model.addAttribute("balance", balance.getBalance() == null ? 0 : balance.getBalance());
		if (StringUtils.isEmpty(point))
			model.addAttribute("point", 0);
		else
			model.addAttribute("point", point.getBalance() == null ? 0 : point.getBalance());
		Integer ticketCount = userLimitTicketService.getTicketCount(loginUser.getId());
		model.addAttribute("ticketCount", ticketCount);	
		return ActResult.success(model);
	}

	/**
	 * 查询用户资产明细
	 *
	 * @param type
	 * @return
	 */
	@RequestMapping("wealth.user")
	public ActResult<Object> userWealth(String type, Integer pageNumber, ModelMap model) {
		Currency currency = currencyService.findByName(type);
		if (currency == null) {
			return ActResult.fail("不存在该类型资产");
		}
		if (StringUtils.isEmpty(pageNumber) || pageNumber == 0) {
			pageNumber = 1;
		}
		
		UserBalance ub = userBalanceService.findByUserAndType(loginUser.getId(), currency.getId());
		BigDecimal balance = new BigDecimal(0);
		if (!StringUtils.isEmpty(ub)) {
			balance = ub.getBalance() == null ?new BigDecimal(0): ub.getBalance();
		}
		//model.addAttribute("pageInfo", page);
		model.addAttribute("balance", balance);
		return ActResult.success(model);
	}

	/**
	 * 个人信息
	 */
	@RequestMapping("balance.user")
	public ActResult<Object> balance(HttpServletRequest request,Long currencyId) {
		UserBalance ub = userBalanceService.findByUserAndType(loginUser.getId(), currencyId);
		if(ub==null) {
			return ActResult.success(0);
		} else {
			return ActResult.success(ub.getBalance());
		}
	}
	
	/**
	 * 个人信息
	 */
	@RequestMapping("info.user")
	public ActResult<Object> userInfo(HttpServletRequest request) {
		ActResult<Object> result = new ActResult<Object>();
		UserFactory userFactory = userService.getById(loginUser.getId());
		if(userFactory!= null && userFactory.getEmployeeType()==1) {//员工账户
			EnterpriseUser eu= userService.getEmpById(loginUser.getId());
			if(eu!=null) {
				userFactory.setSectionName(eu.getSectionName());
				if(StringUtils.isEmpty(userFactory.getRealName())) {
					userFactory.setRealName(eu.getName());
				}
			}
		}
		result.setData(userFactory);
		UserWeixin uw = userWeixinService.getOneModelByUserId(loginUser.getId());
		String flag;
		if (uw==null) {//不存在
			flag="1";
			result.setMsg(flag);
		}else{
			flag="0";
			result.setMsg(flag);
		}
		return result;
	}

	/**
	 * 用户上传头像
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("uploadAvatar.user")
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
					ActResult<List<String>> as = upservice.uploadPic(file.getInputStream(), file.getSize(), type, loginUser.getId()+"");
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
	 * @param birthDay	yyyy-MM-dd
	 * @return
	 */
	@RequestMapping("update.user")
	public ActResult<Object> updateUser(HttpServletRequest request, String avatar, String gender, String nickName, String birthDay) {
		UserFactory userFactory = userService.getById(loginUser.getId());
		boolean updNickName = false;
		if (!StringUtils.isEmpty(avatar)) {
			if(!StringUtils.isEquals(avatar, userFactory.getAvatar())) {
				userFactory.setAvatar(avatar);
				userFactory.setShopLink(null);
			}
		}
		if (!StringUtils.isEmpty(nickName)) {
			userFactory.setNickName(nickName);
			updNickName = true;
		} else {
			if(StringUtils.isEmpty(userFactory.getNickName())) {
				userFactory.setNickName(userFactory.getUserName());
			}
		}
		if (!StringUtils.isEmpty(birthDay)) {
			userFactory.setBirthday(TimeUtil.strToDate(birthDay));
		} else {
			userFactory.setBirthday(null);
		}
		if (!StringUtils.isEmpty(gender)) {
			if ("m".equals(gender) || "f".equals(gender) || "n".equals(gender)) {
				userFactory.setGender(gender);
			} else {
				return ActResult.fail("请输入正确性别");
			}
		}
		userService.update(userFactory);
		
		if(updNickName) {
			// 更新企业用名称
			EnterpriseUser eu= userService.getEmpById(userFactory.getId());
			if(eu !=null) {
				if(StringUtils.isEmpty(eu.getName())){
					eu.setName(userFactory.getNickName());
					userService.updEmp(eu);
				} else if(eu.getName().equals(userFactory.getPhone()) || eu.getName().equals(userFactory.getEmail())){
					eu.setName(userFactory.getNickName());
					userService.updEmp(eu);
				}
			}
			
			// 更新im群组聊天中的昵称
			UserImGroupMember entity = new UserImGroupMember();
			entity.setUserId(userFactory.getId());
			entity.setNickname(userFactory.getNickName());
			userImGroupMemberService.updateUserNick(entity);
			
			// 更新环信用户昵称
			EasemobIMUtils.updUserNickName(EasemobIMService.APP_TYPE_USER+userFactory.getId(), userFactory.getNickName());
		}

		return ActResult.success(userFactory);
	}

	/**
	 * 合并购物车
	 */
	private void mergeCart(String mobileId, Long userId) {
		try {
			Cart redisCart = null;
			Cart noLoginCart = null;
			String cartJson = redisUtil.getData(RedisConstant.CART_REDIS + "_" + userId);
			if (!StringUtils.isEmpty(cartJson)) {
				redisCart = JsonUtil.getObject(cartJson, Cart.class);
			}
			String value = redisUtil.getData(RedisConstant.CART_APP + "_" + mobileId);
			if (!StringUtils.isEmpty(value)) {
				noLoginCart = JsonUtil.getObject(value, Cart.class);
			}
			if (null != noLoginCart) {
				if (null == redisCart) {
					redisCart = new Cart();
				}
				List<CartItem> noLoginCartItems = noLoginCart.getAllItems();//设备购物车信息
				//List<CartItem> redisCartItems = redisCart.getAllItems();//账户购物车信息

				for (CartItem nologinCartItem : noLoginCartItems) {//
					CartItem  inItem =redisCart.getItem(Long.valueOf(nologinCartItem.getPartNumber()));//购物车的产品项
					if(inItem == null) {
						List<CartItem> psku = redisCart.getItemsByProductId(nologinCartItem.getProductId());
						for (CartItem cartItem2 : psku) {
							Long skuId = Long.parseLong(cartItem2.getPartNumber());
							ProductSpecificationsVo productSpecifications = productSpecificationsService.findByIdCache(Long.parseLong(cartItem2.getPartNumber()),cartItem2.getProductId().toString());
							if(productSpecifications!=null) {
								if(!skuId.equals(productSpecifications.getId())) {
									cartItem2.setPartNumber(productSpecifications.getId()+"");
									cartItem2.setProductCode(productSpecifications.getProductCode());
									cartItem2.setPrice(productSpecifications.getPrice());
									cartItem2.setMaxCompanyTicket(productSpecifications.getMaxFucoin());
									if(nologinCartItem.getPartNumber().equals(productSpecifications.getId()+"")) {
										inItem = cartItem2;
									}
								}
							}
						}
					}
					
					if(inItem!=null){//若已经购买，合并数量
						int quantity = inItem.getQuantity() + nologinCartItem.getQuantity();
						inItem.setQuantity(quantity);
					}else{//没购买，添加购物项
						redisCart.addItem(nologinCartItem);
					}

				}
				redisUtil.setData(RedisConstant.CART_REDIS + "_" + userId, JsonUtil.toJsonString(redisCart), CAR_REDIS_EXPTIME);
				redisUtil.del(RedisConstant.CART_APP + "_" + mobileId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 用户通过邮箱修改密码时，发送验证邮件
	 * @param request
	 * @return
	 */
	@RequestMapping("emailPostfixs")
	@ResponseBody
	public ActResult<List<String>> emailPostfixs(HttpServletRequest request, HttpServletResponse response){
		return ActResult.success(userService.emailPostfixs());
	}

	@RequestMapping("/toLogin")
	public ModelAndView toLogin(HttpServletRequest request, Long forId,String msg,String exp1,String toUrl,String type) {
		ModelAndView result = new ModelAndView("login");
		result.addObject("forId", forId);
		result.addObject("msg", msg);
		result.addObject("exp1", exp1);
		result.addObject("toUrl", toUrl);
		result.addObject("type", type);
		return result;
	}

	@RequestMapping("/toPhoneLogin")
	public ModelAndView toForgetPassword(HttpServletRequest request, Long forId,String msg,String exp1,String toUrl,String type) {
		ModelAndView result = new ModelAndView("phoneLogin");
		result.addObject("forId", forId);
		result.addObject("msg", msg);
		result.addObject("exp1", exp1);
		result.addObject("toUrl", toUrl);
		result.addObject("type", type);
		return result;
	}
	
	@RequestMapping("/register")
	public ModelAndView register(HttpServletRequest request, String openId) {
		ModelAndView result = new ModelAndView("register");
		result.addObject("openId", openId);
		return result;
	}
	
	/**
	 * 普通账号进行审核
	 * @param request
	 * @param result
	 * @param empId
	 * @return
	 */
	@RequestMapping("getUserIdByPhone")
	@ResponseBody
	public ActResult<String> getUserIdByPhone(HttpServletRequest request,String phone,String code){

		String old = redisUtil.getData("phoneFactory_"+phone);
		if(!StringUtils.isEmpty(phone) && old==null) return ActResult.fail("验证码错误，请重新获取验证码。");
		
		if(!StringUtils.isEmpty(phone) && !old.equals(code)) return ActResult.fail("验证码错误，请输入正确的验证码。");

		ActResult<UserFactory> ar =  userService.findByPhone(phone);
		if(!ar.isSuccess()) {
			ActResult<CommUser> comU= us.findByPhone(phone);
			
			if(!comU.isSuccess()) return ActResult.fail("您输入的手机号还没有注册，无法添加为亲友");
			
			UserFactory uf = new UserFactory();
			uf.setId(comU.getData().getUserId());
			uf.setUserName(comU.getData().getUserName());
			uf.setEmail(comU.getData().getUserEmail());
			uf.setEnabled(comU.getData().getEnabled());
			uf.setUsable(comU.getData().getUsable());
			uf.setCreatTime(new Date());
			uf.setNickName(comU.getData().getUserName());
			if(uf.getNickName().equals(uf.getPhone())) {
				String nickName=uf.getPhone();
				if(nickName.length()>4) {
					nickName="1***"+nickName.substring(nickName.length()-4);
					uf.setNickName(nickName);
				}
			}
			uf.setEnabled(1);
			uf.setType(1);
			uf.setPhone(comU.getData().getUserPhone());
			uf.setGender("m");
			uf.setLoginTime(new Date());
			userService.specialSave(uf);
			
			return ActResult.success(comU.getData().getUserId()+"");
			
		} else {
			return ActResult.success(ar.getData().getId()+"");
		}
	}

	@RequestMapping("writeUserResult")
	public ModelAndView writeUserResult(HttpServletRequest request,ModelMap map,String type,String data,String showVcode){
		ModelAndView result = new ModelAndView("writeUserResult");

		result.addObject("type", type);
		result.addObject("data", data);
		result.addObject("showVcode", showVcode);
		return result;
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
	public ActResult<String> verify(HttpServletRequest request, String uuid,String ticket){
		if("1".equals(redisUtil.getData("QrForLogin_" + uuid))) {
			ActResult<Long> act= us.qrLogin(uuid,ticket,"factory_web_login");
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
	public ActResult<String> pushMsg(Long userId,String title,String msg){
		if(userId!=null) {
			loginFacade.doPushNotify(userId, null, title, msg);
		}
		return ActResult.success("");
	}

	/**
	 * 团消息推送
	 * @param groupBuyId	团id
	 * @param orderStatus	订单状态
	 * @return
	 */
	@RequestMapping("pushGroupBuyMsg")
	public ActResult<String> pushGroupBuyMsg(Long groupBuyId,Integer orderStatus){
		if(groupBuyId != null && orderStatus!=null) {
			GroupBuyVo vo = groupBuyService.getById(groupBuyId);
			if(vo != null) {
				UserImGroup im= userImGroupService.getById(NumberUtil.toLong(vo.getIm_groupId()));
				if(im!=null) {
					List<UserImGroup> groups = new ArrayList<UserImGroup>();
					groups.add(im);
					String msg="";
					if(orderStatus==2) {
						msg="@所有人 商家已发货。\r\n" + "将有团长统一收货后纷发给大家~\r\n" + "@"+vo.getCommanderName()+"团长辛苦了！";
					} else {
						msg="@所有人 团长已签收。\r\n" + "请联系团长领取您的商品。\r\n" + "别忘了感谢团长辛苦收货哦~";
					}
					EasemobIMUtils.shoppingGroupMessage(msg, groups, null, null, null,"sendOrComfirm");
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
	public ActResult<String> pushMsg(String phoneNumber,String contnt){
		sms.sendSms(phoneNumber, Constant.SMS_SIGNATURE, contnt, "factoryAPI", false, null);
		return ActResult.success("");
	}
	/**
	 * 发送验证码并检查手机号
	 * @param request
	 * @param phone
	 * @return
	 */
	@RequestMapping("sendCodeSms")
	@ResponseBody
	public ActResult<String> sendCodeSms(HttpServletRequest request, String phone){
		if (StringUtils.isPhoneNumber(phone)) {
			 ActResult<CommUser> result = us.findByPhone(phone);
			 if (result.isSuccess()) {
				return ActResult.fail("该手机号已注册。");
			}
		}else{
			return ActResult.fail("请输入正确的手机号");
		}
		String contnt = StringUtils.getRandomNum();
		try {
			String old = redisUtil.getData("phoneFactory_"+phone);
			//半小时内验证码相同
			if(!StringUtils.isEmpty(old)) {
				contnt=old;
			}
			redisUtil.setData("phoneFactory_"+phone, contnt, 60*30);
			contnt = "验证码"+contnt+"（有效期30分钟）。如非本人操作，请忽略此短信。";
			/** 
			 * signature  短信签名
			 * soure   短信来源
			 * ip   为空
			 * signature,ver_code1+contnt+ver_code2   短信内容
			 * */
			sms.sendSms(phone, "我的网", contnt, "factoryAPI", false, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActResult.success("");
	}
	/**
	 * 绑定手机或修改手机
	 * @param request
	 * @param phone
	 * @param code
	 * @return
	 */
	@RequestMapping("updateEmp.user")
	public ActResult<String> updateEmp(HttpServletRequest request,String realName, String sectionName){
		if(loginUser.getEmployeeType()!=1) {
			return ActResult.fail("非员工账户无法操作");
		}
		EnterpriseUser emp = userService.getEmpById(loginUser.getId());
		if(emp != null) {
			if(!StringUtils.isEmpty(realName)) {
				emp.setName(realName);
			}
			if(!StringUtils.isEmpty(sectionName)) {
				emp.setSectionName(sectionName);
			}
			userService.updEmp(emp);
			return ActResult.success("保存成功!");
		}else {
			return ActResult.fail("用户信息错误!");
		}
	}
	
	/**
	 * 绑定手机或修改手机
	 * @param request
	 * @param phone
	 * @param code
	 * @return
	 */
	@RequestMapping("updatePhone.user")
	@ResponseBody
	public ActResult<String> updatePhone(HttpServletRequest request,String phone,String code){
		String old = redisUtil.getData("phoneFactory_"+phone);
		if(!StringUtils.isEmpty(phone) && old==null) return ActResult.fail("验证码错误，请重新获取验证码。");
		if(!StringUtils.isEmpty(phone) && !old.equals(code)) return ActResult.fail("验证码错误，请输入正确的验证码。");
		//修改或绑定手机
		ActResult<String> result = us.updatePhone(phone, loginUser.getId(), UserConstant.COMFROM);
		if (result.isSuccess()) {//修改成功
			UserFactory uf = userService.getById(loginUser.getId());
			if (uf!=null) {
				uf.setPhone(phone);
				userService.update(uf);
			}
			EnterpriseUser emp = userService.getEmpById(uf.getId());
			if (emp!=null) {
				emp.setPhone(phone);
				userService.updEmp(emp);
			}
		}
		return result;
	}
	
	@RequestMapping("page")
	public ModelAndView page(ModelAndView model,HttpServletRequest request){
			model.setViewName("details");
			return model;
		
	}
	
	@RequestMapping("personal")
	public ModelAndView personal(String avatar,String nickName,String gender,String birthday,String phone,String sectionName,Integer pageId,ModelAndView model,HttpServletRequest request){
	    if(0==pageId){//昵称
	    	model.addObject("nickName", nickName);
			model.setViewName("personal_nc");
			return model;
		}else if(1==pageId){//性别
			model.addObject("gender", gender);
			model.setViewName("personal_xb");
			return model;
		}else if(2==pageId){//生日
			model.addObject("birthday", birthday);
			model.setViewName("personal_sr");
			return model;
		}else if(3==pageId){//手机
			model.addObject("phone", phone);
			model.setViewName("personal_phone");
			return model;
		}else if(5==pageId){//部门
			model.addObject("sectionName", sectionName);
			model.setViewName("personal_sectionName");
			return model;
		}else{//个人资料pageId=4
			model.addObject("avatar", avatar);
			model.setViewName("personal");
			return model;
		}
	}
	/**
	 * 设置权限
	 * @param request
	 * @return
	 */
	@RequestMapping("setPermission.user")
	public ActResult<Object> setPermission(HttpServletRequest request,String hideInfo){
		if (!StringUtils.isEmpty(hideInfo)) {
			UserFactory userFactory = userService.getById(loginUser.getId());
			userFactory.setHideInfo(Integer.valueOf(hideInfo).intValue());
			userService.update(userFactory);
			return ActResult.success(userFactory);
		}else{
			return ActResult.fail("参数错误");
		}
		
	}
	/**
	 * 获取用户购物车商品数量
	 */
	@RequestMapping("getCartNum.user")
	@ResponseBody
	public ActResult<Object> getCartNum(HttpServletRequest request, HttpServletResponse response){
		ActResult<Object> ret = new ActResult<Object>();
	    Cart cart = null;
	    int num = 0;
	    //从redis去购物车数量
	    String cartJson = redisUtil.getData(RedisConstant.CART_REDIS+"_"+loginUser.getId());
	    if(null != cartJson && !cartJson.trim().equals("")  && !cartJson.trim().equals("null")){
	    	cart = JsonUtil.getObject(cartJson, Cart.class);
	    }
	    if(null != cart){
	    	num = cart.calculateTotalNumber();
	    }
	    ret.setData(num);
		return ret;
	}	
}
