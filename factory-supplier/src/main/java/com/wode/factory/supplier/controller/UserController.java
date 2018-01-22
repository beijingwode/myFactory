/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import static com.wode.common.constant.UserConstant.BINDMAILFUNCTION;
import static com.wode.common.constant.UserConstant.CHANGEEMAILFUNCTION;
import static com.wode.common.constant.UserConstant.COMFROM;
import static com.wode.common.constant.UserConstant.EMAILPREFIX;
import static com.wode.common.constant.UserConstant.FINDPWDBYEMAILFUNCTION;
import static com.wode.common.constant.UserConstant.FINDPWDBYPHONEFUNCTION;
import static com.wode.common.constant.UserConstant.MAILEXPIRE_TIME;
import static com.wode.common.constant.UserConstant.PASSWORD;
import static com.wode.common.constant.UserConstant.REGEMAILFUNCTION;
import static com.wode.common.constant.UserConstant.SENDMAILBINDMAIL_SUFFIX;
import static com.wode.common.constant.UserConstant.SENDMAILFINDPWD_SUFFIX;
import static com.wode.common.constant.UserConstant.SENDMAILREGMAIL_SUFFIX;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.wode.common.constant.UserConstant;
import com.wode.common.db.DBUtils;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.redis.RedisUtil;
import com.wode.common.result.Result;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.ActResult;
import com.wode.common.util.EncryptUtils;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.company.controller.BaseCompanyController;
import com.wode.factory.company.facade.EmpBenefitFacade;
import com.wode.factory.company.query.EnterpriseVo;
import com.wode.factory.company.service.EnterpriseService;
import com.wode.factory.company.service.EnterpriseUserService;
import com.wode.factory.company.util.SeasonUtil;
import com.wode.factory.model.ApprShop;
import com.wode.factory.model.ApprSupplier;
import com.wode.factory.model.Enterprise;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.Product;
import com.wode.factory.model.Shop;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.EasemobIMService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.supplier.model.SupplierAppSecurity;
import com.wode.factory.supplier.query.CmsContentQuery;
import com.wode.factory.supplier.service.ApprShopService;
import com.wode.factory.supplier.service.ApprSupplierService;
import com.wode.factory.supplier.service.CmsContentService;
import com.wode.factory.supplier.service.ProductService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SuborderService;
import com.wode.factory.supplier.service.SupplierAppSecurityService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.service.UserService;
import com.wode.factory.supplier.util.Constant;
import com.wode.factory.supplier.util.EasemobIMUtils;
import com.wode.factory.supplier.util.MailUtils;
import com.wode.factory.supplier.util.MatrixToImageWriter;
import com.wode.factory.supplier.util.UserInterceptor;
import com.wode.model.CommUser;

import cn.org.rapid_framework.page.Page;


@Controller
@RequestMapping("user")
public class UserController extends BaseSpringController {
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	@Autowired
	@Qualifier("mail")
	private MailUtils mailUtils;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	private SupplierService supplierService;
	@Autowired
	private EnterpriseService enterpriseService;

	@Resource
	private RedisUtil redisUtil;


	@Autowired
	@Qualifier("suborderService")
	private SuborderService suborderService;

	@Autowired
	@Qualifier("productService-supplier")
	private ProductService productService;

	@Autowired
	@Qualifier("cmsContentService")
	private CmsContentService cmsContentService;
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	@Autowired
	private ApprShopService apprShopService;
	@Autowired
	private ApprSupplierService apprSupplierService;
	@Autowired
	private DBUtils dbUtils;
	@Autowired
	private EnterpriseUserService enterpriseUserService;
	@Autowired
	private EmpBenefitFacade empBenefitFacade;
	@Autowired
	private SupplierAppSecurityService supplierAppSecurityService;
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);

	/**
	 * 邮箱正则
	 */
	private final Pattern mailPat = Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+");

	public UserController() {
	}

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}


	/**
	 * 进入登陆页面
	 **/
	@RequestMapping(value = "login")
	@NoCheckLogin
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "redirect:" +Constant.COMM_USER_URL+"?from=myFactory&domain="+Constant.SYSTEM_DOMAIN;     
	}

	/**
	 * to忘记密码
	 **/
	@RequestMapping(value = "findpassword")
	@NoCheckLogin
	public ModelAndView findpassword(HttpServletRequest request, HttpServletResponse response,String email) throws Exception {
		return new ModelAndView("findpassword","toEmail",email);
	}

	/**
	 * to忘记密码2
	 **/
	@RequestMapping(value = "findpassword2")
	@NoCheckLogin
	public ModelAndView findpassword2(HttpServletRequest request, HttpServletResponse response, String toEmail) throws Exception {
		return new ModelAndView("findpassword2", "toEmail", toEmail).addObject("code", request.getParameter("code")).addObject("phone", request.getParameter("phone"));
	}

	/**
	 * to忘记密码3
	 **/
	@RequestMapping(value = "findpassword3")
	@NoCheckLogin
	public ModelAndView findpassword3(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("findpassword3");
	}

	/**
	 * 进入注册页面
	 **/
	@RequestMapping(value = "toregister")
	@NoCheckLogin
	public ModelAndView toregister(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("redirect:" +Constant.COMM_USER_URL+"register?from=myFactory&domain="+Constant.SYSTEM_DOMAIN);
	}

	/**
	 * 进入注册页面2
	 **/
	@RequestMapping(value = "toregister2")
	@NoCheckLogin
	public ModelAndView toregister2(HttpServletRequest request, HttpServletResponse response, String email, String key) throws Exception {
		Result result = new Result();
		//页面传值
		result.setKey(email);
		result.setMessage(key);
		return new ModelAndView("register2", "result", result);
	}

	/**
	 * 进入注册页面3
	 **/
	@RequestMapping(value = "toregister3")
	@NoCheckLogin
	public ModelAndView toregister3(HttpServletRequest request, HttpServletResponse response, String email) throws Exception {
		Result result = new Result();
		//页面传值
		result.setKey(email);
		return new ModelAndView("register3", "result", result);
	}

	/**
	 * 进入链接失效页面
	 **/
	@RequestMapping(value = "efficacyerror")
	@NoCheckLogin
	public ModelAndView efficacyerror(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Result result = new Result();
		return new ModelAndView("efficacy_error", "result", result);
	}

	/**
	 * 跳转到商户中心首页
	 *
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "tosuppliermain")
	public ModelAndView toUserMain(HttpServletRequest request) throws Exception {
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		//用户是否为空
		if (userModel == null) {
			//System.out.println("\n 空降中心--------------失败-------------" );
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000");
		} else {
			//获取商家信息
			Supplier supplier = supplierService.getById(userModel.getSupplierId());

			/**
			 * 如果企业信息不存在,会创造一份企业信息,保存，并放到session
			 * ******************************************************************************************/
			EnterpriseVo enterpriseVo = this.enterpriseService.selectById(supplier.getId());
			HttpSession session = request.getSession();
			if (enterpriseVo == null) {
				enterpriseVo = new EnterpriseVo();
				enterpriseVo.setId(supplier.getId());
				enterpriseVo.setName(supplier.getComName());
				enterpriseVo.setWelfareLevel(20);

				this.enterpriseService.insertSelective(enterpriseVo);
				session.setAttribute(BaseCompanyController.ENTERPRISE_SESSION, enterpriseVo);
			} else {
				session.setAttribute(BaseCompanyController.ENTERPRISE_SESSION, enterpriseVo);
			}
			/*******************************************************************************************/
			/**
			 * 如企业员工信息不存在，则创建企业员工信息*/
			EnterpriseUser enterpriseUser = enterpriseUserService.getById(userModel.getId());
			String maxEmpNumber = enterpriseUserService.selectMaxEmpNumber(supplier.getId());
			if (enterpriseUser==null) {
				enterpriseUser = new EnterpriseUser();
				enterpriseUser.setId(userModel.getId());//员工id
				enterpriseUser.setEnterpriseId(supplier.getId());//企业id
				enterpriseUser.setType(1);//员工类型 	1表示管理员2表示员工
				String userName = userModel.getRealName();
				if (StringUtils.isNullOrEmpty(userName)) {
					enterpriseUser.setName("管理员");
					userModel.setRealName("管理员");
				}else{
					enterpriseUser.setName(userName);
				}
				enterpriseUser.setEmail(userModel.getEmail());//邮箱
				enterpriseUser.setPhone(userModel.getPhone());//手机
				enterpriseUser.setUserName(userModel.getUserName());
				enterpriseUser.setEmpNumber(maxEmpNumber);//最大员工号
				enterpriseUserService.insertSelective(enterpriseUser);
				//发送内购券2000
				autoBenefit(userModel.getUserName(),enterpriseUser);
			}else{
				Integer oldType = enterpriseUser.getType();
				String oldEmpNumber = enterpriseUser.getEmpNumber();
				if(2==oldType){
					enterpriseUser.setType(1);
					
				}
				if(StringUtils.isNullOrEmpty(oldEmpNumber)){
					enterpriseUser.setEmpNumber(maxEmpNumber);
				}
				if(!enterpriseUser.getType().equals(oldType) || !enterpriseUser.getEmpNumber().equals(oldEmpNumber)){
					enterpriseUserService.update(enterpriseUser);
				}
			}
			if (userModel.getEmployeeType()!=1) {
				userModel.setEmployeeType(1);
				userService.update(userModel);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			List<Product> saleroomlist = new ArrayList<Product>();
			Suborder subordery = new Suborder();
			Suborder suborderr = new Suborder();
			CmsContentQuery query = new CmsContentQuery();
			Page<?> page = null;
			Page<?> page2 = null;
			if (supplier != null) {
				//销售排行榜 前三门
				map.put("supplierId", supplier.getId());
				map.put("startnum", 0);
				map.put("size", 3);
				saleroomlist = productService.getSaleroom(map);
				//订单数量
				map.put("status", 1);
				Integer df = suborderService.findCountByMap(map);
				map.put("status", 3);
				Integer tk = suborderService.findCountByMap(map);
				map.put("status", -1);
				Integer wtg = productService.findProductlistPageCount(map);
				Date nowdate = new Date();

				//输出上月最后一天日期
				String begin = getFirstDay();
				String end = getLastDay();
				map.put("begin", begin);
				map.put("end", end);
				//月统计
				subordery = suborderService.findDayOrMonthOrdersStatistics(map);

				String now = TimeUtil.dateToStr(nowdate);
				map.put("begin", now + " 00:00:00");
				map.put("end", now + " 23:59:59");
				//日统计
				suborderr = suborderService.findDayOrMonthOrdersStatistics(map);
				
				//店铺信息
				Shop record = new Shop();
				record.setSupplierId(supplier.getId());
				List<Shop> shopSettings = this.shopService.selectByModel(record);
				supplier.setShopList(shopSettings);

				query.setPageSize(10);
				query.setChannelid(new Long(1));
				query.setSortColumns("creatdate desc");
				//通知
				page = this.cmsContentService.findPage(query);
				query.setChannelid(new Long(2));
				//新功能
				page2 = this.cmsContentService.findPage(query);
				result.setErrorCode("0");
				//展示企业logo
				/*List<Shop> shopList = supplier.getShopList();
				for (int i = 0; i < shopList.size(); i++) {
					Shop shop = shopList.get(i);
					String logo = shop.getLogo();
					if(StringUtils.isEmpty(logo)) continue;
					if(StringUtils.isEmpty(supplier.getFirmLogo())){
						supplier.setQueryLogo(logo);
					}else{
						String firmLogo = supplier.getFirmLogo();
						supplier.setQueryLogo(firmLogo);
					}
				}*/
				
				result.setMsgBody(supplier);
				mv.addObject("result", result);
				mv.addObject("df", df);
				mv.addObject("tk", tk);
				mv.addObject("wtg", wtg);

				//获取商家信息
				ApprShop appr= apprShopService.getShopApprIng(userModel.getSupplierId());
				ApprSupplier apprS = apprSupplierService.getSupplierApprIng(userModel.getSupplierId());
				mv.addObject("apprShop", appr==null?"+添加店铺":(appr.getStatus()>=1?"店铺审核中":"继续编辑店铺"));
				mv.addObject("apprS", apprS==null?"商户信息":(apprS.getStatus()>=1?"商户审核中":"修改商户信息"));
			} else {
				result.setErrorCode("1000"); 
				result.setMsgBody(supplier);
				mv.addObject("result", result);
				mv.addObject("df", 0);
				mv.addObject("tk", 0);
				mv.addObject("wtg", 0);
			}
			mv.setViewName("index");
			mv.addObject("saleroomlist", saleroomlist);
			mv.addObject("subordery", subordery);
			mv.addObject("suborderr", suborderr);
			mv.addObject("page", page);
			mv.addObject("page2", page2);
		}
		return mv;
	}

	private void autoBenefit(String string, EnterpriseUser enterpriseUser) {
		empBenefitFacade.dealEmpBenefit(enterpriseUser.getId(), "216", BigDecimal.ZERO, NumberUtil.toBigDecimal(2000), 
				string, enterpriseUser.getId()+"", dbUtils.CreateID(), enterpriseUser.getEnterpriseId(), 
				enterpriseUser.getName(), "员工注册，平台代企业下发内购券。", SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(),"");
	}

	/**
	 * 跳转到首页
	 *
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "toIndexcenter")
	@NoCheckLogin
	public ModelAndView toIndexcenter(HttpServletRequest request) throws Exception {
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		//用户是否为空
		if (userModel == null) {
			//System.out.println("\n 空降中心--------------失败-------------" );
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("indexcenter");
		} else {
			//mv.setViewName("redirect:/user/tosuppliermain.html");
			mv.setViewName("indexcenter");
		}

		return mv;
	}


	/**
	 * 登陆
	 **/
	@RequestMapping(value = "hasLogin")
	@NoCheckLogin
	public ModelAndView hasLogin(HttpServletRequest request, HttpServletResponse response, String ticket) {
		ActResult<CommUser> ar = us.hasLogin(ticket);
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		CommUser user;
		//服务器登陆是否成功
		if (ar.isSuccess()) {
			//登陆存储session

			user = ar.getData();
			com.wode.factory.model.UserFactory user_ = userService.getById(user.getUserId());
			Boolean flag = false;
			//判断是商家超级管理员登录
			if(null!=user_&&user_.getType()==2){
				flag =true;
			//商家下的子管理员
			}else if(null!=user_&&user_.getType()==3){
				flag = true;
			//不是商家登录，是其他账号登录
			}else{
				mv.setViewName("redirect:/user/login.html?error=otherSupplierLogin");
				return mv;
			}
			
			if(flag){
				if(user_.getSupplierId() == null) {
					Supplier supplier = supplierService.getByUserId(user.getUserId());
					if(supplier !=null ){
						user_.setSupplierId(supplier.getId());
						userService.update(user_);
					} else {
						user_.setSupplierId(dbUtils.CreateID());
						userService.update(user_);						
					}
				}
				if (user_.getType() == 3) {
					user_.setResources(userService.getAuth(user_.getId()));
				}
				
				Shop p = new Shop();
				p.setSupplierId(user_.getSupplierId());
				user_.setShopCount(shopService.selectByModel(p).size());
				
				HttpSession session = request.getSession();
				session.setAttribute(UserConstant.USER_SESSION, user_);
				session.setAttribute(UserConstant.USER_SESSION+"_TICKET", ticket);
				
				result.setErrorCode("0");
				mv.setViewName("redirect:/supplier/enterMain.html");
			}
			
		} else {
			result.setErrorCode("1000");
			String flag = request.getParameter("flag");
			if ("2".equals(flag)) {
				mv.setViewName("redirect:/user/login.html?error=error");
			} else {
				mv.setViewName("redirect:/user/toIndexcenter.html?error=error");
			}
		}
		mv.addObject("result", result);
		return mv;
	}


	@RequestMapping("/loginBack{uid}")
	@NoCheckLogin
	public ModelAndView loginBack(@PathVariable Long uid,String ticket,String remember,String returnUrl,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		ActResult<CommUser> ar = us.hasLogin(ticket);
		//if(ar == null) return ActResult.fail("ticket数据不正确");
		if(ar.isSuccess()){
			if(uid==null || uid.equals(0L)){
				uid = ar.getData().getUserId();
			}
			com.wode.factory.model.UserFactory user_ = userService.getById(uid);
			if(user_.getSupplierId() == null) {
				Supplier supplier = supplierService.getByUserId(uid);
				if(supplier !=null ){
					user_.setSupplierId(supplier.getId());
					userService.update(user_);
				} else {
					user_.setSupplierId(dbUtils.CreateID());
					userService.update(user_);						
				}
			}
			if (user_.getType() == 3) {
				user_.setResources(userService.getAuth(user_.getId()));
			}
			
			Shop p = new Shop();
			p.setSupplierId(user_.getSupplierId());
			user_.setShopCount(shopService.selectByModel(p).size());
			
			HttpSession session = request.getSession();
			session.setAttribute(UserConstant.USER_SESSION, user_);
			session.setAttribute(UserConstant.USER_SESSION+"_TICKET", ticket);
			
			result.setErrorCode("0");
			mv.setViewName("redirect:/supplier/enterMain.html");
		} else {
			result.setErrorCode("1000");
			String flag = request.getParameter("flag");
			if ("2".equals(flag)) {
				mv.setViewName("redirect:/user/login.html?error=error");
			} else {
				mv.setViewName("redirect:/user/toIndexcenter.html?error=error");
			}
		}
		
		mv.addObject("result", result);
		return mv;
	}
	
	/**
	 * 用户邮箱注册
	 *
	 * @param email
	 * @param password
	 * @return
	 **/
	@RequestMapping(value = "emailRegistration")
	@NoCheckLogin
	public ModelAndView emailRegistration(Long userId, HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		String viewName = "register";
		com.wode.common.util.ActResult<CommUser> ar = new com.wode.common.util.ActResult<CommUser>();

		//服务器注册操作
		ar =us.getUserById(userId);
		//判断注册结果
		if (ar.isSuccess()) {
			//本地存储一份用户数据
			com.wode.factory.model.UserFactory fus = new com.wode.factory.model.UserFactory();
			fus.setId(ar.getData().getUserId());
			fus.setUserName(ar.getData().getUserName());
			fus.setEmail(ar.getData().getUserEmail());
			fus.setNickName(ar.getData().getNickName());
			fus.setUsable(ar.getData().getUsable());
			fus.setType(ar.getData().getUserType());
			fus.setEnabled(ar.getData().getEnabled());
			fus.setUserLevel(ar.getData().getUserLevel());
			fus.setCreatTime(ar.getData().getCreatedTime());
			userService.saveId(fus);
			result.setErrorCode("0");
			//发送注册激活邮件
			sendRegForActiveEmail(ar.getData().getUserEmail(), ar.getData().getUserId() + "", getWebAppPath(request));
			viewName = "redirect:/user/toregister2.html?email=" + ar.getData().getUserEmail() + "&key=" + ar.getData().getUserId();
		} else {
			result.setErrorCode("1000");
		}
		result.setMsgBody(ar.getData());
		return new ModelAndView(viewName, "result", result);
	}

	/**
	 * 发送邮件
	 *
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "sendEmailSecurity")
	@NoCheckLogin
	@ResponseBody
	public ActResult<String> sendEmailSecurity(String toEmail, String type, String userId, HttpServletRequest request,
	                           HttpServletResponse response) throws Exception {
		//发送邮件后缀
		if (StringUtils.isNullOrEmpty(toEmail) || !mailPat.matcher(toEmail).matches()) {
			return ActResult.fail("请输入正确的邮箱！");
		} else {
			
			//向册箱发送邮并redis
			return mailUtils.sendMailAndRedis(toEmail, SENDMAILFINDPWD_SUFFIX, getWebAppPath(request),
					userId, type, MAILEXPIRE_TIME);
		}
	}

	/**
	 * 用户注销
	 **/
	@RequestMapping(value = "loginOut")
	@NoCheckLogin
	public ModelAndView loginOut(HttpServletResponse response, HttpServletRequest request, String from) {
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		//判断属于登陆状态
		if (userModel != null) {
			//执行注销
			us.logout((String)request.getSession().getAttribute(UserConstant.USER_SESSION+"_TICKET"));
			request.getSession().removeAttribute(UserConstant.USER_SESSION);
		}
		if (StringUtils.isNullOrEmpty(from)) {
			from = request.getHeader("Referer");
		}
		return new ModelAndView("redirect:" +Constant.COMM_USER_URL+"?from=myFactory&domain="+Constant.SYSTEM_DOMAIN);
	}

	/**
	 * 获取web工程访问路径（绝对路径）
	 *
	 * @param request
	 * @return
	 */
	public String getWebAppPath(HttpServletRequest request) {
		return StringUtils.TrimRight(request.getSession().getServletContext().getInitParameter("userdomain"), "/") + "/";
	}

	/**
	 * 发送注册待激活邮件
	 *
	 * @param toEmail
	 * @param userid
	 * @param basePath
	 */
	@NoCheckLogin
	private void sendRegForActiveEmail(final String toEmail, final String userid, final String basePath) {
		//创建线程池
		ExecutorService executorService = Executors.newCachedThreadPool();
		//添加线程进线程池执行线程
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				//向册箱发送注册待激活邮件并记录redis
				ActResult<String> act = null;
				try {
					 act = mailUtils.sendMailAndRedis(toEmail, SENDMAILREGMAIL_SUFFIX,
							basePath, userid, REGEMAILFUNCTION, MAILEXPIRE_TIME);
				} catch (UnsupportedEncodingException e) {
					logger.error("\n send sendRegForActiveEmail exception	" + e.getMessage());
				}
				
				logger.info("\n send sendRegForActiveEmail result	" + (act==null?false:act.isSuccess()) + " emailAddress	" + toEmail +
						"	regType	" + REGEMAILFUNCTION + "	sendTime:	" + TimeUtil.getCurrentTime());
			}
		});
		logger.info("\n		send regForActiveEmail end	：--------------");
	}


	/**
	 * 通过激活注册邮箱邮件链接激活账号
	 *
	 * @param randomId
	 * @return ModelAndView
	 * @throws Exception
	 */
	@NoCheckLogin
	@RequestMapping(value = "toActivatingMail{randomId}/{type}")
	public ModelAndView toActivatingMail(@PathVariable String randomId, @PathVariable String type, String toEmail,
	                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("\ntoActivatingMail randomId：----	" + randomId + "		Account:		" + toEmail);
		Result result = new Result();
		//解密用户邮箱(此处得到的toEmail的值已经是decode之后的值，无需再decode)
		ModelAndView mv = new ModelAndView();
		if (StringUtils.isNullOrEmpty(toEmail)) {
			result.setErrorCode("3701");
			result.setMessage("邮件激活地址不存在！");
			mv.addObject("result", result);
			mv.setViewName("user/activating");
			logger.error("\nAccount：" + toEmail + "	邮件激活地址不存在");
			return mv;
		}
		toEmail = EncryptUtils.decrypt(toEmail, PASSWORD);
		//设置当发送邮件出现错误时，将邮箱地址传递到激活结果页面
		result.setKey(toEmail);

		//redis中对应功能发送记录的redis后缀
		String redisSuffix = "";
		//绑定邮箱功能
		if (BINDMAILFUNCTION.equals(type)) {
			redisSuffix = SENDMAILBINDMAIL_SUFFIX;
			//注册邮箱功能
		} else if (REGEMAILFUNCTION.equals(type)) {
			redisSuffix = SENDMAILREGMAIL_SUFFIX;
		}
		//判断redis中是否存在激活账号邮件记录
		String userIdAndEmail = mailUtils.getMsgOrMailData(randomId + redisSuffix);
		if (StringUtils.isNullOrEmpty(userIdAndEmail)) {
			result.setErrorCode("3701");
			result.setMessage("链接已过期，请重新发送邮件！");
			mv.addObject("result", result);
			mv.setViewName("redirect:/user/efficacyerror.html");
			logger.error("\nAccount：" + toEmail + "	maiLMessage of redis is expire , please resendEmail");
			return mv;
		} else {
			//删除激活账号邮件记录
			mailUtils.delMsgOrMailData(randomId + redisSuffix);
			//为用户绑定注册邮箱
			String[] userArray = userIdAndEmail.split("_");
			/*User user = new User();
			user.setUserId(Long.valueOf(userArray[0]));
			User dbUser = userService.getById(Long.valueOf(userArray[0]));
			if(dbUser == null) {
				result.setErrorCode("3710");
				result.setMessage("系统出现异常，请重试！");
				mv.addObject("result", result);
				mv.setViewName("user/activating");
				logger.error("\nAccount：" + toEmail + "	for User of DB is NULL , please check Username");
				return mv;
			}*/
			//绑定邮箱功能
			if (BINDMAILFUNCTION.equals(type)) {
				//us.active(Long.valueOf(userArray[0]), COMFROM);
				ActResult<String> ar = us.updateEmail(toEmail, Long.valueOf(userArray[0]), COMFROM);
				if(ar.isSuccess()){
					com.wode.factory.model.UserFactory user = userService.getById(Long.valueOf(userArray[0]));
					user.setEmail(toEmail);
					userService.saveOrUpdate(user);
					
					HttpSession session = request.getSession();
					session.setAttribute(UserConstant.USER_SESSION, user);
				}
				
				//注册邮箱功能
			} else if (REGEMAILFUNCTION.equals(type)) {
				//判断用户是否为启用状态
				ActResult<String> act = us.active(Long.valueOf(userArray[0]), COMFROM,"0",false,"");
				if (!act.isSuccess()) {
					logger.error("\nAccount：" + toEmail + "	," + act.getMsg());
				} else {
					com.wode.factory.model.UserFactory user = userService.getById(Long.valueOf(userArray[0]));
					user.setEnabled(1);

					if(user.getSupplierId() == null) {
						Supplier supplier = supplierService.getByUserId(user.getId());
						if(supplier !=null ){
							user.setSupplierId(supplier.getId());
						} else {
							user.setSupplierId(dbUtils.CreateID());
						}
						
						if(StringUtils.isEmpty(user.getAvatar())) {
							Enterprise ent= enterpriseService.getById(user.getSupplierId());
							if(ent != null) {
								String shopLink = StringUtils.isEmpty(ent.getEmpDefultAvatar())?null:(enterpriseService.getFirstShopId(supplier.getId())+"");
								
								user.setAvatar(ent.getEmpDefultAvatar());
								user.setShopLink(shopLink);
							}
						}
					}
					
					userService.update(user);
					HttpSession session = request.getSession();
					session.setAttribute(UserConstant.USER_SESSION, user);
				}
			} else {
				result.setErrorCode("3712");
				mv.addObject("result", result);
				mv.setViewName("user/activating");
				logger.error("\nAccount：" + toEmail + "	, no Functioning——operate in the list of function");
				return mv;
			}
			//做登录操作
			//loginOperate(act);
			//激活成功则设置此处的key值为空，无需传递到页面
			result.setKey(null);
			result.setErrorCode("0");
			//dbUser.setPassword(null);
			//result.setMsgBody(dbUser);
			result.setMessage("账户已激活，请使用您的账户登录！");
			logger.info("\nAccount：" + toEmail + "	, activating user is successful, please use the account to login");
			//异步调用注册成功发送邮件方法
			//	sendRegSuccessEmail(toEmail,"",COMMONREG);
			return new ModelAndView("redirect:/user/toregister3.html?email=" + toEmail, "result", result);
		}
	}

	/**
	 * 发送邮件
	 *
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "userSendMail")
	@NoCheckLogin
	@ResponseBody
	public ActResult<String> userSendMail(String toEmail, String type, String userId, HttpServletRequest request,
	                           HttpServletResponse response) throws Exception {
		Result result = new Result();
		result.setKey("toEmail");
		boolean errorFlag = false;
		//发送邮件后缀
		String emailSuffix = "";
		//功能名称
		String funcString = "";
		if (StringUtils.isNullOrEmpty(toEmail) || !mailPat.matcher(toEmail).matches()) {
			result.setErrorCode("3506");
			result.setMessage("请输入正确的邮箱！");
		} else {
			ActResult<CommUser> ar= us.findByEmail(toEmail);
			if (FINDPWDBYPHONEFUNCTION.equals(type) || FINDPWDBYEMAILFUNCTION.equals(type)) {
				//无用户注册该邮箱
				if (ar.getData() == null) {
					result.setErrorCode("3507");
					result.setMessage("请确认该邮箱是否注册！");
					errorFlag = true;
				} else {
					if (toEmail.equals(ar.getData().getUserEmail())) {
					emailSuffix = SENDMAILFINDPWD_SUFFIX;
					funcString = "找回密码";
					//找回密码时是查询出用户的id对userId进行赋值
					userId = String.valueOf(ar.getData().getUserId());
					}else{
						result.setErrorCode("3505");
						result.setMessage("请使用绑定邮箱！");
						errorFlag = true;
					}
				}
				//绑定邮箱方式
				//注册邮箱方式
			} else if (REGEMAILFUNCTION.equals(type) || BINDMAILFUNCTION.equals(type)) {
				if (BINDMAILFUNCTION.equals(type)) {
					emailSuffix = SENDMAILBINDMAIL_SUFFIX;
					//注册邮箱功能
				} else if (REGEMAILFUNCTION.equals(type)) {
					emailSuffix = SENDMAILREGMAIL_SUFFIX;
				}
				if (REGEMAILFUNCTION.equals(type) && ar.getData()!=null && ar.getData().getEnabled() == 1) {
					result.setErrorCode("3509");
					result.setMessage("邮箱已激活或已绑定过其他用户！");
					errorFlag = true;
				} else if (BINDMAILFUNCTION.equals(type) && ar.getData()!=null && !ar.getData().getUserId().equals(Long.parseLong(userId))) {
					result.setErrorCode("3509");
					result.setMessage("邮箱已绑定过其他用户！");
					errorFlag = true;
				} else {
					result.setKey(EMAILPREFIX + toEmail.substring(toEmail.indexOf("@") + 1));
					result.setMsgBody(ar.getData());
					funcString = "注册邮箱";
					//userId = String.valueOf(ar.getData().getUserId());
				}
			} else {
				result.setErrorCode("3591");
				result.setMessage("请重新发送邮件！");
				return ActResult.success(result);
			}
			//判断是否出现错误
			if (!errorFlag) {
				//向册箱发送邮并redis
				ActResult<String> act = mailUtils.sendMailAndRedis(toEmail, emailSuffix, getWebAppPath(request),
						userId, type, MAILEXPIRE_TIME);
				//发送邮件成功
				if (!act.isSuccess()) {
					result.setErrorCode("3508");
					result.setMessage("发送邮件失败，请重新发送邮件！");
				} else {
					result.setErrorCode("0");
					result.setMessage(funcString + "邮件已发送到您的邮箱，请注意查收邮件，链接失效时间为半小时。");
				}
				logger.info("功能：" + type + "	发送邮件结果：" + act.isSuccess() + "，发送邮箱为：" + toEmail
						+ "		发送时间：" + TimeUtil.getCurrentTime());
			}
		}
		return ActResult.success(result);
	}
	
	/**
	 * 找回密码方式重置密码
	 *
	 * @return ModelAndView
	 */
	@RequestMapping(value = "resetPwdByFind")
	@NoCheckLogin
	public ModelAndView resetPwdByFind(String password, String confirmPassword, String type, HttpServletRequest request,
	                                   HttpServletResponse response) throws Exception {
		Result result = new Result();
		String viewName = "user/resetpwd";
		//判断新修改密码是否正确
		result = validateResetPwd(password, confirmPassword);
		//获取redis中存储记录对应的value即userId
		String userId = "";
		//判断是否出错
		boolean isError = false;
		//获取会话
		HttpSession session = request.getSession();
		//验证通过
		if ("0".equals(result.getErrorCode())) {
			//会话中手机号
			String phoneNumber = null;
			//注册手机找回密码方式重置密码
			if (FINDPWDBYPHONEFUNCTION.equals(type)) {
				//获取session中发送短信的手机号
				phoneNumber = session.getAttribute("phoneNumber") + "";
				logger.info("\n		===================		短信重置密码：				--------------- 手机号		" + phoneNumber);
				//判断redis中查是否存在短信发送记录
				if (StringUtils.isNullOrEmpty(phoneNumber)) {
					result.setErrorCode("9011");
					result.setMessage("请重新找回密码！");
					viewName = "user/findpwd";
					isError = true;
				} else {
					/*User user = new User();
					user.setUserPhone(phoneNumber);
					user = userService.getByUsername(user);
					if(user == null) {
						result.setErrorCode("9012");
						result.setErrorCode("用户不存在！");
						viewName = "user/findpwd";
						isError = true;
					} else {
						//获取userId并清除会话中的个人信息
						userId = user.getId() + "";
					}*/
				}
				//注册邮箱找回密码方式重置密码
			} else if (FINDPWDBYEMAILFUNCTION.equals(type)) {
				userId = session.getAttribute("findPwdEmail") + "";
				if (StringUtils.isNullOrEmpty(userId)) {
					result.setErrorCode("9011");
					result.setMessage("请重新找回密码！");
					viewName = "findpassword";
					isError = true;
				} else {
					com.wode.factory.model.UserFactory user = userService.getById(Long.valueOf(userId));
					if (user == null) {
						result.setErrorCode("9012");
						result.setErrorCode("用户不存在！");
						viewName = "findpassword";
						isError = true;
					}
				}
			} else {
				result.setErrorCode("9015");
				result.setMessage("出现异常，请重新提交！");
				return new ModelAndView(viewName, "result", result);
			}
//			if (!isError) {
//				//重置用户密码
//
//				com.wode.comm.user.client.service.UserService us = ServiceFactory.getService();
//				ActResult<User> act = us.updatePassword(Long.valueOf(userId), password, COMFROM);
//				if (!act.isSuccess()) {
//					result.setErrorCode("9016");
//					result.setMessage("服务器出现异常，请重新提交！");
//					return new ModelAndView(viewName, "result", result);
//				}
//				//清除redis中和会话中的信息
//				if (FINDPWDBYPHONEFUNCTION.equals(type)) {
//					userService.delRedisMsgCode(phoneNumber + SENDMSGFINDPWD_SUFFIX);
//					session.removeAttribute("phoneNumber");
//				} else if (FINDPWDBYEMAILFUNCTION.equals(type)) {
//					session.removeAttribute("findPwdEmail");
//				}
//				//修改密码成功后做登录操作
//				//loginOperate(user, user.getUsername(), "", queryHeaderModel(request), request, response);
//				viewName = "redirect:/user/findpassword3.html";
//			}
		}
		return new ModelAndView(viewName, "result", result);
	}


	/**
	 * 找回密码方式重置密码
	 *
	 * @return ModelAndView
	 */
	@RequestMapping(value = "info")
	public ModelAndView info(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = "person_info";
		UserFactory user = UserInterceptor.getSessionUser(request,shopService);
		if(!"m".equals(user.getGender()) && !"f".equals(user.getGender())){
			user.setGender("f");
		}
		return new ModelAndView(viewName, "user",user);
	}

	/**
	 * 找回密码方式重置密码
	 *
	 * @return ModelAndView
	 */
	@RequestMapping(value = "updateInfo")
	public ModelAndView updateInfo(UserFactory upd,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = "person_info";
		boolean updNickName = false;
		UserFactory user = UserInterceptor.getSessionUser(request,shopService);
		if(!"m".equals(user.getGender()) && !"f".equals(user.getGender())){
			user.setGender("f");
		}
		if(!StringUtils.isEquals(upd.getAvatar(), user.getAvatar())) {
			user.setAvatar(upd.getAvatar());
			user.setShopLink(null);
		}
		if(!StringUtils.isNullOrEmpty(upd.getNickName())) {
			user.setNickName(upd.getNickName());
			updNickName = true;
		} else {
			if(StringUtils.isNullOrEmpty(user.getNickName())) {
				user.setNickName(user.getUserName());
			}
		}
		if(StringUtils.isNullOrEmpty(upd.getGender()))
			user.setGender("n");
		else
			user.setGender(upd.getGender());
		
		user.setCity(upd.getCity());
		user.setProvince(upd.getProvince());
		user.setDistrict(upd.getDistrict());
		userService.saveOrUpdate(user);

		HttpSession session = request.getSession();
		session.setAttribute(UserConstant.USER_SESSION, user);
		
		if(updNickName) {
			EasemobIMUtils.updUserNickName(EasemobIMService.APP_TYPE_SHOP+user.getId(), upd.getNickName());
		}
			
		return new ModelAndView(viewName, "user",user);
	}

	/**
	 * 找回密码方式重置密码
	 *
	 * @return ModelAndView
	 */
	@RequestMapping(value = "security")
	public ModelAndView security(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = "person_security";
		UserFactory user = UserInterceptor.getSessionUser(request,shopService);
		return new ModelAndView(viewName, "user",user);
	}

	/**
	 * api安全
	 *
	 * @return ModelAndView
	 */
	@RequestMapping(value = "app_security")
	public ModelAndView appSecurity(HttpServletRequest request, HttpServletResponse response) throws Exception {

		UserFactory user = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			mv.setViewName("app_security");
			SupplierAppSecurity app= new SupplierAppSecurity();
			app.setSupplierId(user.getSupplierId());
			List<SupplierAppSecurity> ls = supplierAppSecurityService.selectByModel(app);
			if(ls!=null && !ls.isEmpty()) {
				app = ls.get(0);
			} else {
				app.setId(user.getSupplierId());
			}
			mv.addObject("app", app);
		}
		return mv;
	}
	

	/**
	 * 更新app密钥
	 * @param app
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="updAppSecurity")
	@ResponseBody
	public ActResult updAppSecurity(SupplierAppSecurity app,HttpServletRequest request,HttpServletResponse response){

		UserFactory user = UserInterceptor.getSessionUser(request,shopService);
		if(user==null) {
			return ActResult.fail("登录超时，请重新登录后重试");
		}
		SupplierAppSecurity q = supplierAppSecurityService.getById(app.getId());
		if(q!=null) {
			q.setSupplierId(user.getSupplierId());
			q.setSecret(app.getSecret());
			supplierAppSecurityService.update(q);
		} else {
			app.setSupplierId(user.getSupplierId());
			app.setSecurityType("1");
			supplierAppSecurityService.save(app);
		}
		return ActResult.success("");
	}
	
	/**
	 * 找回密码方式重置密码
	 *
	 * @return ModelAndView
	 */
	@RequestMapping(value = "bindEmail")
	public ModelAndView bindEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserFactory user = UserInterceptor.getSessionUser(request,shopService);		
		return new ModelAndView("personal_security_bind_email", "user",user);
	}

	/**
	 * 找回密码方式重置密码
	 *
	 * @return ModelAndView
	 */
	@RequestMapping(value = "bindPhone")
	public ModelAndView bindPhone(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserFactory user = UserInterceptor.getSessionUser(request,shopService);		
		return new ModelAndView("personal_bind_phone", "user",user);
	}
    /**
     * 个人中心手机成功绑定
     *
     * @param request
     * @return
     */
    @RequestMapping("/bindPhoneSuccess")
    public ModelAndView userBindPhoneSuccess(HttpServletRequest request, HttpServletResponse response) {
		UserFactory user = UserInterceptor.getSessionUser(request,shopService);
        
    	ActResult<CommUser> art = us.getUserById(user.getId());
    	
    	if(art.isSuccess()) {
    		if(!StringUtils.isEquals(art.getData().getUserPhone(), user.getPhone())){
    			user.setPhone(art.getData().getUserPhone());
    			userService.saveOrUpdate(user);
				HttpSession session = request.getSession();
				session.setAttribute(UserConstant.USER_SESSION, user);
    		}
    	}
		return new ModelAndView("person_security", "user",user);
    }
    

    /**
     * 个人中心手机成功绑定
     *
     * @param request
     * @return
     */
    @RequestMapping("/bindSuccess")
    public ModelAndView userBindPhoneSuccess(HttpServletRequest request, HttpServletResponse response,String type,Long uid) {
		UserFactory user = UserInterceptor.getSessionUser(request,shopService);
        if(user.getId().equals(uid)) {
	    	ActResult<CommUser> art = us.getUserById(user.getId());
	    	
	    	if(art.isSuccess()) {
	    		if("bindEmail".equals(type)) {
	    			if(!StringUtils.isEquals(art.getData().getUserEmail(), user.getEmail())){
		    			user.setEmail(art.getData().getUserEmail());
		    			userService.saveOrUpdate(user);
		    			EnterpriseUser emp = enterpriseUserService.getById(user.getId());
		    			if (emp!=null) {
		    				emp.setEmail(art.getData().getUserEmail());
		    				enterpriseUserService.update(emp);
		    			}
		    			request.getSession().setAttribute(UserConstant.USER_SESSION, user);
		    		}
	    		} else {
		    		if(!StringUtils.isEquals(art.getData().getUserPhone(), user.getPhone())){
		    			user.setPhone(art.getData().getUserPhone());
		    			userService.saveOrUpdate(user);
		    			EnterpriseUser emp = enterpriseUserService.getById(user.getId());
		    			if (emp!=null) {
		    				emp.setPhone(art.getData().getUserPhone());
		    				enterpriseUserService.update(emp);
		    			}
		    			request.getSession().setAttribute(UserConstant.USER_SESSION, user);
		    		}
	    		}
	    	}
        }

		return new ModelAndView("person_security", "user",user);
		
    }
	/**
	 * 修改绑定邮箱
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("toChangeBindEmail")
	@ResponseBody
	public ActResult<String> toChangeBindEmail(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		UserFactory user = UserInterceptor.getSessionUser(request,shopService);
		ActResult<String> re = new ActResult<String>();
		if(user==null){
			re.setSuccess(false);
			re.setMsg("该用户不存在");
		}else{

			ActResult<String> act = mailUtils.sendMailAndRedis(user.getEmail(), SENDMAILBINDMAIL_SUFFIX, getWebAppPath(request),
					user.getId().toString(), CHANGEEMAILFUNCTION, MAILEXPIRE_TIME);
			
			if(!act.isSuccess()){
				re.setSuccess(false);
				re.setMsg("邮件发送失败，请检查您的注册邮箱是否正常使用");
			}else{
				re.setMsg("邮件已发送");
			}
		}
		return re;
	}

	/**
	 * 修改绑定邮箱
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("toBindEmail")
	@ResponseBody
	@NoCheckLogin
	public ActResult<String> toBindingMail(String toEmail, String type, String userId, HttpServletRequest request,
            HttpServletResponse response) throws Exception{		
		return this.userSendMail(toEmail, type, userId, request, response);
	}
	/**
	 * 通过激活注册邮箱邮件链接激活账号
	 *
	 * @param randomId
	 * @return ModelAndView
	 * @throws Exception
	 */
	@NoCheckLogin
	@RequestMapping(value = "changeBindEmail{randomId}/{type}")
	public ModelAndView changeBindEmail(@PathVariable String randomId, @PathVariable String type,
	                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
		Result result = new Result();
		//解密用户邮箱(此处得到的toEmail的值已经是decode之后的值，无需再decode)
		ModelAndView mv = new ModelAndView();
		//redis中对应功能发送记录的redis后缀
		String redisSuffix = SENDMAILBINDMAIL_SUFFIX;
		//判断redis中是否存在激活账号邮件记录
		String userId = mailUtils.getMsgOrMailData(randomId + redisSuffix);
		if (StringUtils.isNullOrEmpty(userId)) {
			result.setErrorCode("3701");
			result.setMessage("链接已过期，请重新发送邮件！");
			mv.addObject("result", result);
			mv.setViewName("redirect:/user/efficacyerror.html");
			return mv;
		}
				
		return new ModelAndView("personal_security_bind_email", "user",userService.getById(Long.parseLong(userId)));
	}
	/**
	 * 重置密码时判断修改密码是否正确
	 *
	 * @param pwd
	 * @param confirmPwd
	 * @return Result
	 */
	@NoCheckLogin
	public Result validateResetPwd(String pwd, String confirmPwd) {
		Result result = new Result();
		//判断输入密码是否正确
		if (StringUtils.isNullOrEmpty(pwd)) {
			result.setKey("password");
			result.setErrorCode("3502");
			result.setMessage("新密码不能为空！");
		} else if (StringUtils.isNullOrEmpty(confirmPwd)) {
			result.setKey("confirmpassword");
			result.setErrorCode("3502");
			result.setMessage("确认密码不能为空！");
		} else if (!pwd.equals(confirmPwd)) {
			result.setKey("confirmpassword");
			result.setErrorCode("3503");
			result.setMessage("新密码与确认密码不一致！");
		} else {
			result.setErrorCode("0");
		}

		return result;
	}


	/**
	 * 验证邮箱唯一
	 **/
	@NoCheckLogin
	@RequestMapping(value = "getEmail")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Result result = new Result();
		String email = request.getParameter("email");
		//判断用户邮箱是否已注册
		com.wode.factory.model.UserFactory us = userService.getByEmailNew(email);
		if (us == null) {
			result.setErrorCode("0");
		} else {
			result.setErrorCode("1000");
		}
		return new ModelAndView("", "result", result);
	}

	/**
	 * 当月第一天
	 *
	 * @return
	 */
	private static String getFirstDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();

		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
		return str.toString();

	}

	/**
	 * 当月最后一天
	 *
	 * @return
	 */
	private static String getLastDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();
		String s = df.format(theDate);
		StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
		return str.toString();

	}

	/**
	 * 厂用户绑定邮箱找回密码，验证邮箱连接（只查厂库）
	 * guziye
	 */
	@RequestMapping("writeUserResult")
	@NoCheckLogin
	public String writeUserResult(HttpServletRequest request,ModelMap map,String type,String data,String showVcode){
		String to ="/writeUserResult";

		map.put("type", type);
		map.put("data", data);
		map.put("showVcode", showVcode);
		return to;
	}

	@RequestMapping("getQrForLogin")
	@NoCheckLogin
	public void getQrForLogin(HttpServletRequest request, HttpServletResponse response,int num) throws WriterException, IOException{
		
		String text = "uuid=" +request.getSession().getId() +"&todo=QrForLogin&num="+num;
        int width = 140;   
        int height = 140;
        Hashtable hints= new Hashtable();   
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        String format = "png";   
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);  
        MatrixToImageWriter.writeToStream(bitMatrix, format, response.getOutputStream());  
	}
	/*
	 * 判断是否登录
	 */
	@RequestMapping("getQrTicket")
	@ResponseBody
	@NoCheckLogin
	public void getQrTicket(HttpServletRequest request,HttpServletResponse response,int num) throws IOException{
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		String uuid=request.getSession().getId();
		String t = redisUtil.getData("QrForLogin_" + uuid);
		if(!StringUtils.isEmpty(t) && !"1".equals(t)) {
			redisUtil.del("QrForLogin_" + uuid);
		}
		response.getWriter().write("showQrMsg('"+t+"');");
	}

	/*
	 * 判断是否登录
	 */
	@RequestMapping("qrLogin")
	@ResponseBody
	@NoCheckLogin
	public ActResult<UserFactory> qrLogin(HttpServletRequest request,HttpServletResponse response,String ticket,String remenber) throws UnsupportedEncodingException{
		ActResult<CommUser> ar = us.hasLogin(ticket);
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		CommUser user;
		//服务器登陆是否成功
		if (ar.isSuccess()) {
			//登陆存储session

			user = ar.getData();
			com.wode.factory.model.UserFactory user_ = userService.getById(user.getUserId());
			Boolean flag = false;
			//判断是商家超级管理员登录
			if(null!=user_&&user_.getType()==2){
				flag =true;
			//商家下的子管理员
			}else if(null!=user_&&user_.getType()==3){
				flag = true;
			//不是商家登录，是其他账号登录
			}else{
				return ActResult.fail("不是商家登录，是其他账号登录");
			}
			
			if(flag){
				if(user_.getSupplierId() == null) {
					Supplier supplier = supplierService.getByUserId(user.getUserId());
					if(supplier !=null ){
						user_.setSupplierId(supplier.getId());
						userService.update(user_);
					} else {
						user_.setSupplierId(dbUtils.CreateID());
						userService.update(user_);						
					}
				}
				if (user_.getType() == 3) {
					user_.setResources(userService.getAuth(user_.getId()));
				}
				
				Shop p = new Shop();
				p.setSupplierId(user_.getSupplierId());
				user_.setShopCount(shopService.selectByModel(p).size());
				
				HttpSession session = request.getSession();
				session.setAttribute(UserConstant.USER_SESSION, user_);
				session.setAttribute(UserConstant.USER_SESSION+"_TICKET", ticket);
				
				return ActResult.successSetMsg("/supplier/enterMain.html");
			} else {
				return ActResult.fail("不是商家登录，是其他账号登录");
			}
			
		} else {
			return ActResult.fail("ticket数据不正确");
		}
	}

	public static String getIpAddr(HttpServletRequest request) {
		if (null == request) {
			return null;
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
}

