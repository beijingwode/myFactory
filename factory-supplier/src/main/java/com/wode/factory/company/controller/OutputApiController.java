/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.company.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.db.DBUtils;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.ActResult;
import com.wode.common.util.EncryptUtils;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.company.facade.EmpBenefitFacade;
import com.wode.factory.company.facade.EntBenefitFacade;
import com.wode.factory.company.query.EnterpriseUserVo;
import com.wode.factory.company.service.EntParamCodeService;
import com.wode.factory.company.service.EnterpriseService;
import com.wode.factory.company.service.EnterpriseUserService;
import com.wode.factory.company.service.PaymentService;
import com.wode.factory.company.util.SeasonUtil;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.Enterprise;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.Payment;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.supplier.service.UserExchangeTicketService;
import com.wode.factory.supplier.service.UserService;
import com.wode.factory.supplier.util.AppPushUtil;
import com.wode.factory.supplier.util.Constant;
import com.wode.model.CommUser;

@Controller
@RequestMapping("api")
public class OutputApiController {
	
	private static final Long aceSupplierId = 201712221700825L;
		
	@Autowired
	private EntBenefitFacade entBenefitFacade;
	
	@Autowired
	private EmpBenefitFacade empBenefitFacade;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private DBUtils dbUtils;
	
	@Autowired
	private EnterpriseUserService enterpriseUserService;
//	@Autowired
//	private EmpBenefitFlowService empBenefitFlowService;
	@Autowired
	UserService userService;
	@Autowired
	UserExchangeTicketService userExchangeTicketService;
	@Autowired
	private EntParamCodeService entParamCodeService;

	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
	
	@Value("#{configProperties['ace.url']}")
	private  String aceUrl;
	/**
	 * 批准企业申请，准许企业福利发放额度
	 * @param apprId 审批记录ID
	 * @param entId 企业id
	 * @param limit 核准金额
	 * @param updName 更新者
	 * @return 0:系统异常、-1：审批记录已被处理过，1：正常处理
	 */
	@RequestMapping(value="grantBenefit")
	@ResponseBody
	@NoCheckLogin
	public ActResult<Long> grantBenefit(Long apprId,Long entId,BigDecimal limit,String updName){
		//流水id
		Long flowId=dbUtils.CreateID();
		int r = entBenefitFacade.grantBenefit(apprId, entId, limit, flowId ,updName);
		ActResult<Long> act = new ActResult<Long>();
		switch (r) {
		case 0:
			act.setSuccess(false);
			act.setMsg("系统异常");
			act.setData(0l);
			break;
		case 1:
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(flowId);
			break;
		case -1:
			act.setSuccess(false);
			act.setMsg("审批记录已被处理过");
			act.setData(-1l);
			break;
		}
		return act;
	}
	@RequestMapping(value="saveEnterprise")
	@ResponseBody
	@NoCheckLogin
	public ActResult<Long> saveEnterprise(String supplierName){
		Enterprise enterprise = new Enterprise();
		enterprise.setId(dbUtils.CreateID());
		enterprise.setName(supplierName);
		enterprise.setWelfareLevel(20);
		Enterprise save = enterpriseService.save(enterprise);
		return ActResult.success(save.getId());
	}
	
	/**
	 * 平台储值接口
	 * @param entId
	 * @param amount
	 * @param desrc
	 * @param updUser
	 * @return
	 */
	@RequestMapping(value="bank")
	@ResponseBody
	@NoCheckLogin
	public ActResult<Long> bank(Long entId,BigDecimal amount, String desrc,String updUser){
		//流水id
		Long flowId=dbUtils.CreateID();
		int r = entBenefitFacade.bankCash(entId, SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(), amount, flowId,desrc, updUser);
		ActResult<Long> act = new ActResult<Long>();
		switch (r) {
		case 0:
			act.setSuccess(false);
			act.setMsg("系统异常");
			act.setData(0l);
			break;
		case 1:
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(flowId);
			break;
		}
		return act;
	}

	/**
	 * 买家储值接口
	 * @param entId
	 * @param amount
	 * @param desrc
	 * @param updUser
	 * @return
	 */
	@RequestMapping(value="userCharge")
	@ResponseBody
	@NoCheckLogin
	public ActResult<Long> userCharge(Long empId,BigDecimal amount, String desrc,String updUser,String key){
		//流水id
		Long flowId=dbUtils.CreateID();
		int r = empBenefitFacade.dealEmpBenefit(empId, "200", amount, BigDecimal.ZERO, updUser, key, flowId, null, updUser, desrc, 0, 0,"");
				
		ActResult<Long> act = new ActResult<Long>();
		switch (r) {
		case 1:
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(flowId);
			break;
		default:
			act.setSuccess(false);
			act.setMsg("系统异常");
			act.setData(0l);
			break;
		}
		return act;
	}
	/**
	 * 货款结算到商家现金账户
	 * @param saleBillId 对账单ID
	 * @param updName 更新者
	 * @return 0:系统异常、-1：处理失败，1：正常处理
	 */
	@RequestMapping(value="paySaleBill")
	@ResponseBody
	@NoCheckLogin
	public ActResult<Long> paySaleBill(Long saleBillId,String updName){
		//流水id
		Long flowId=dbUtils.CreateID();
		int r = entBenefitFacade.paySaleBill(saleBillId, updName, flowId);
		ActResult<Long> act = new ActResult<Long>();
		switch (r) {
		case 0:
			act.setSuccess(false);
			act.setMsg("系统异常");
			act.setData(0l);
			break;
		case 1:
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(flowId);
			break;
		case -1:
			act.setSuccess(false);
			act.setMsg("对账单状态不正确，可能已经处理过，或审核未通过，请刷新数据重试");
			act.setData(-1l);
			break;
		}
		return act;
	}
	
	/**
	 * 批准企业申请，准许企业福利发放额度
	 * @param apprId 审批记录ID
	 * @param entId 企业id
	 * @param limit 核准金额
	 * @param updName 更新者
	 * @return 0:系统异常，1：正常处理
	 */
	@RequestMapping(value="salePrize")
	@ResponseBody
	@NoCheckLogin
	public ActResult<Long> dispatcheBenefit(Long empId,BigDecimal limitm,String empName,String desrc,String updName){
		//流水id
		Long flowId=dbUtils.CreateID();
		int r = empBenefitFacade.dealEmpBenefit(empId, "294", BigDecimal.ZERO, limitm, updName, null, flowId, null, empName, desrc, 0, 0,"");
				
		ActResult<Long> act = new ActResult<Long>();
		switch (r) {
		case 1:
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(flowId);
			break;
		default:
			act.setSuccess(false);
			act.setMsg("系统异常");
			act.setData(0l);
			break;
		}
		return act;
	}

	/**
	 * 员工福利券奖励
	 * @param empId	员工id
	 * @param limitm	福利券数量
	 * @param empName	员工姓名
	 * @param desrc		描述
	 * @param updName	更新者名称
	 * @param key		关键字
	 * @return 0:系统异常，1：正常处理
	 */
	@RequestMapping(value="fisrtPrize")
	@ResponseBody
	@NoCheckLogin
	public ActResult<Long> fisrtPrize(Long empId,BigDecimal limitm,String empName,String desrc,String updName,String key){
		//流水id
		Long flowId=dbUtils.CreateID();
		int r = empBenefitFacade.dealEmpBenefit(empId, "292", BigDecimal.ZERO, limitm, updName, key, flowId, null, empName, desrc, 0, 0,"");
				
		ActResult<Long> act = new ActResult<Long>();
		switch (r) {
		case 1:
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(flowId);
			break;
		default:
			act.setSuccess(false);
			act.setMsg("系统异常");
			act.setData(0l);
			break;
		}
		return act;
	}

	/**
	 * 评论后奖励
	 * @param empId 用户id
	 * @param absCash 奖励现金券
	 * @param absTicket 奖励内购券
	 * @param empName 用户名
	 * @param key 关键字
	 * @param desrc 描述
	 * @param updName 更新者
	 * @return 0:系统异常，1：正常处理
	 */
	@RequestMapping(value="commentPrize")
	@ResponseBody
	@NoCheckLogin
	public ActResult<Long> commentPrize(Long empId, BigDecimal absCash,BigDecimal absTicket,String empName,String key,String desrc,String updName){
		//流水id
		Long flowId=dbUtils.CreateID();
		int r = empBenefitFacade.dealEmpBenefit(empId, "296", absCash, absTicket, updName, key, flowId, null, empName, desrc, 0, 0,"");
				
		ActResult<Long> act = new ActResult<Long>();
		switch (r) {
		case 1:
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(flowId);
			
			if(absCash.compareTo(BigDecimal.ZERO) > 0) {
				AppPushUtil.pushWxCommentPrize(empId, absCash.toString(),key);
			}
			break;
		default:
			act.setSuccess(false);
			act.setMsg("系统异常");
			act.setData(0l);
			break;
		}
		return act;
	}
	
	/**
	 * 员工绑定
	 * @param addEntUser	员工信息
	 * @param request		
	 * @param fromId		邀请员工链接
	 * @param unSafe		1表示信息未验证，如已有用户不允许绑定
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="addEmployee")
	@ResponseBody
	@NoCheckLogin
	public ActResult<String> addEmployee(EnterpriseUser addEntUser, HttpServletRequest request,String fromId,String unSafe){
		boolean hasEmp = false;
		//查询最大的员工序号
		if(StringUtils.isEmpty(addEntUser.getEmpNumber())) {
			String maxEmpNumber = this.enterpriseUserService.selectMaxEmpNumber(addEntUser.getEnterpriseId());
			addEntUser.setEmpNumber(maxEmpNumber);
		}
		addEntUser.setWelfareLevel(1);
		//根据手机号查询
		if(StringUtils.isEmpty(addEntUser.getUserName())) {
			if(!StringUtils.isEmpty(addEntUser.getPhone())) {
				addEntUser.setUserName(addEntUser.getPhone());
			} else if(!StringUtils.isEmpty(addEntUser.getEmail())) {
				addEntUser.setUserName(addEntUser.getEmail());
			} else {
				addEntUser.setUserName(addEntUser.getEmpNumber());
			}
		}
		EnterpriseUserVo entVo = this.enterpriseUserService.selectByAccount(addEntUser.getUserName());
		List<EnterpriseUser> newEmps = new ArrayList<EnterpriseUser>();
		Enterprise ent= enterpriseService.getById(addEntUser.getEnterpriseId());
		String firstShopId = StringUtils.isEmpty(ent.getEmpDefultAvatar())?null:(enterpriseService.getFirstShopId(addEntUser.getEnterpriseId())+"");
		
		/**
		 * 该手机号在企业表中不存在，需要注册
		 * */
		 ActResult<String> rnt = null;
		if(StringUtils.isEmpty(entVo)){
			//企业用户不存在   判断该手机号在factory厂存在不存在
			UserFactory uf = null;
			if(!StringUtils.isEmpty(addEntUser.getPhone())) {
				uf=userService.getByPhone(addEntUser.getPhone());
			} else if(!StringUtils.isEmpty(addEntUser.getEmail())) {
				uf=userService.getByUserName(addEntUser.getEmail());
			}
			/**
			 * factory不存在 
			 * 		 注册共通
			 * 		 注册factory
			 * 		 注册企业
			 * */
			if(StringUtils.isEmpty(uf)){
				ActResult<CommUser> actUser =null;
				if(StringUtils.isEmpty(addEntUser.getPhone())) {
					actUser = ActResult.fail("手机为空");
				} else {
					// 根据手机号获得用户信息
					actUser= us.findByPhone(addEntUser.getPhone());
					if(actUser.isSuccess()) {
						if(StringUtils.isEmpty(addEntUser.getEmail())) {
							addEntUser.setEmail(actUser.getData().getUserEmail());
						} else {
							if(!StringUtils.isEquals(addEntUser.getEmail(), actUser.getData().getUserEmail())) {
								return ActResult.fail("该邮箱已经被其他员工注册过，不能重复注册");
							}
						}
					}
				}
				//共通注册成功
				if(!actUser.isSuccess()){
					if(!StringUtils.isEmpty(addEntUser.getEmail())) {
						actUser = us.findByEmail(addEntUser.getEmail());
						if(actUser.isSuccess()) {
							return ActResult.fail("该邮箱已经被其他员工注册过，不能重复注册");
						}
					}
					actUser = this.registerCommon(addEntUser,1,request);
				}
				
				//共通注册成功
				if(actUser.isSuccess()){
					
					CommUser user = actUser.getData();
					//注册厂用户
					this.registerFactoryUser(user,addEntUser.getEnterpriseId(),ent.getEmpDefultAvatar(),firstShopId);

					addEntUser.setId(user.getUserId());
					//注册员工信息
					EnterpriseUser entEmp = this.registerEnterpriseUser(addEntUser);
					if(entEmp!=null){
						newEmps.add(entEmp);
						rnt = ActResult.success(entEmp.getId()+"");
					}else{
						rnt = ActResult.fail("绑定失败");
					}
				}else{
					rnt = ActResult.fail("数据异常，请联系客服");
				}
			/**
			 * 只要factory存在该用户，表示共通中已经存在用户了。
			 * 
			 * factory用户存在  注册企业用户,并将factory用户更新成员工
			 * */
			}else{
				if("1".equals(unSafe)) {
					return ActResult.fail("该手机/邮箱已经绑定过，请直接登录。");
				}
				if(StringUtils.isEmpty(addEntUser.getEmail())) {
					addEntUser.setEmail(uf.getEmail());
				} else {
					if(!StringUtils.isEquals(addEntUser.getEmail(), uf.getEmail())) {
						return ActResult.fail("该邮箱已经被其他员工注册过，不能重复注册");
					}
				}
				entVo = enterpriseUserService.selectByEmpId(uf.getId());
				if (entVo!=null) {
					hasEmp=true;
				}else{
					addEntUser.setId(uf.getId());
					// 注册企业用户
					EnterpriseUser entEmp = this.registerEnterpriseUser(addEntUser);
					if (entEmp != null) {
						// 用户类型：0普通，1员工，2亲友
						uf.setEmployeeType(1);
						// 企业id（用户属于哪个企业）
						uf.setSupplierId(addEntUser.getEnterpriseId());
						// 更新factory用户表中用户类型和企业id

						if (StringUtils.isEmpty(uf.getAvatar())) {
							uf.setAvatar(ent.getEmpDefultAvatar());
							uf.setShopLink(firstShopId);
						}
						this.userService.update(uf);

						newEmps.add(entEmp);
						rnt = ActResult.success(entEmp.getId() + "");
					} else {
						rnt = ActResult.fail("数据异常，请联系客服");
					}
				}
			}
		}else{
			if("1".equals(unSafe)) {
				return ActResult.fail("该手机/邮箱已经绑定过，请直接登录。");
			}
			hasEmp = true;
		}
//			if(!StringUtils.isEmpty(addEntUser.getPhone())) {
//				return ActResult.fail("员工信息已存在，请输入手机验证码确认身份。");
//			}
		if(hasEmp) {
			//1   已注销(已注销用户的企业id不存在)
			if(entVo.getLogout().equals(new Byte("1"))){
				addEntUser.setId(entVo.getId());
				addEntUser.setLogout(new Byte("0"));
				addEntUser.setType(2);
				addEntUser.setUserName(addEntUser.getUserName());
				this.enterpriseUserService.updateEmp(addEntUser);
				//添加企业员工。员工账号存在,判断员工账号的类型 。 1为员工2为亲友
				UserFactory uf = userService.getByPhone(addEntUser.getPhone());
				if(!StringUtils.isEmpty(uf)&&uf.getEmployeeType()!=1){
					
					uf.setEmployeeType(1);
					uf.setSupplierId(addEntUser.getEnterpriseId());
					if(StringUtils.isEmpty(uf.getAvatar())) {
						uf.setAvatar(ent.getEmpDefultAvatar());
						uf.setShopLink(firstShopId);
					}
					this.userService.update(uf);
				}
				return ActResult.success(entVo.getId()+"");
			} else {
				if(StringUtils.isEmpty(entVo.getEnterpriseId())){
					//员工企业id不存在，表示该数据有误
					return ActResult.success("数据异常");
				}else{
					//管理员的企业id和被添加的用户企业id相等
					if(addEntUser.getEnterpriseId().equals(entVo.getEnterpriseId())){
						//有更新用户,变为提示用户该账号在本企业已注册
						return ActResult.success(entVo.getId()+"");
					}else{
						return ActResult.fail("您已是其他企业员工，不能再绑定到此企业");
					}
				}
			}			
		}
		//流水id
		if(!aceSupplierId.equals(ent.getId())) {
			autoBenefit(request, newEmps,ent.getId());
		}
		if(newEmps.size()>0) {
			rnt.setMsg("need_push500");
		}
		if (!StringUtils.isEmpty(fromId)) {//邀请人信息
			EnterpriseUser from = this.enterpriseUserService.getById(Long.valueOf(fromId));//邀请人企业信息
			if (from!=null && !newEmps.isEmpty()) {
				EnterpriseUser to = newEmps.get(0);
				invitBonuses(from,to);//邀请奖励
			}
		}
		return rnt;
	}
	
	/**
	 * 电商消费
	 * @param empId 员工id
	 * @param absCash 消费现金
	 * @param absTicket 消费内购券
	 * @param key 关键字
	 * @param desrc 备注 
	 * @param updUser 更新者
	 * @return 1：正常处理/0：系统异常/-1：现金余额不足/-2：内购券余额不足/-3：现金、内购券余额都不足
	 */
	@RequestMapping(value="payOrder")
	@ResponseBody
	@NoCheckLogin
	public ActResult<Long> payOrder(Long empId, BigDecimal absCash,
			BigDecimal absTicket, String key, String desrc, String updUser,HttpServletRequest request) {
		ActResult<Long> act = new ActResult<Long>();
		EnterpriseUser user = enterpriseUserService.selectById(empId);
		Long entId = null;
		String user_name = null;
		if (user != null){
			entId = user.getEnterpriseId();
			user_name = user.getName();
		} else {
			user_name = updUser;
		}
		
		// 清零
		if (absCash == null)
			absCash = BigDecimal.ZERO;
		if (absTicket == null)
			absTicket = BigDecimal.ZERO;
		//流水id
		Long flowId=dbUtils.CreateID();
		// 账务处理
		int r = empBenefitFacade.dealEmpBenefit(empId, "203", absCash,
				absTicket, updUser, key, flowId , entId, user_name, desrc, 0, 0,"");
		switch (r) {
		case 1:
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(flowId);
			break;
		case 0:
			act.setSuccess(false);
			act.setMsg("系统异常");
			act.setData(0l);
			break;
		case -1:
			act.setSuccess(false);
			act.setMsg("现金余额不足");
			act.setData(-1l);
			break;
		case -2:
			act.setSuccess(false);
			act.setMsg("内购券余额不足");
			act.setData(-2l);
			break;
		case -3:
			act.setSuccess(false);
			act.setMsg("现金、内购券余额都不足");
			act.setData(-3l);
			break;
		}
		
		return act;
	}

	/**
	 * 电商消费
	 * @param empId 员工id
	 * @param absCash 消费现金
	 * @param absTicket 消费内购券
	 * @param key 关键字
	 * @param desrc 备注 
	 * @param updUser 更新者
	 * @return 1：正常处理/0：系统异常/-1：现金余额不足/-2：内购券余额不足/-3：现金、内购券余额都不足
	 */
	@RequestMapping(value="dealEmpBenefit")
	@ResponseBody
	@NoCheckLogin
	public ActResult<Long> dealEmpBenefit(String opCode,Long empId, BigDecimal absCash,
			BigDecimal absTicket, String key, String desrc, String updUser,String exBenefitType,HttpServletRequest request) {
		ActResult<Long> act = new ActResult<Long>();
		EnterpriseUser user = enterpriseUserService.selectById(empId);
		Long entId = null;
		String user_name = null;
		if (user != null){
			entId = user.getEnterpriseId();
			user_name = user.getName();
		} else {
			user_name = updUser;
		}
		
		// 清零
		if (absCash == null)
			absCash = BigDecimal.ZERO;
		if (absTicket == null)
			absTicket = BigDecimal.ZERO;
		//流水id
		Long flowId=dbUtils.CreateID();
		// 账务处理
		int r = empBenefitFacade.dealEmpBenefit(empId, opCode, absCash,
				absTicket, updUser, key, flowId , entId, user_name, desrc, 0, 0,exBenefitType);
		
		switch (r) {
		case 1:
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(flowId);
			break;
		case 0:
			act.setSuccess(false);
			act.setMsg("系统异常");
			act.setData(0l);
			break;
		case -1:
			act.setSuccess(false);
			act.setMsg("现金余额不足");
			act.setData(-1l);
			break;
		case -2:
			act.setSuccess(false);
			act.setMsg("内购券余额不足");
			act.setData(-2l);
			break;
		case -3:
			act.setSuccess(false);
			act.setMsg("现金、内购券余额都不足");
			act.setData(-3l);
			break;
		}
		
		return act;
	}
	
	/**
	 * 电商消费
	 * @param empId 员工id
	 * @param orderId 订单id
	 * @param updUser 更新者
	 * @return 1：正常处理/0：系统异常/-1：现金余额不足/-2：内购券余额不足/-3：现金、内购券余额都不足
	 */
	@RequestMapping(value="payOrderTicket")
	@ResponseBody
	@NoCheckLogin
	public ActResult<Long> payOrderTicket(Long empId, Long orderId, String updUser,String aceTicket,String aceUserId,HttpServletRequest request) {
		ActResult<Long> act = new ActResult<Long>();
		EnterpriseUser user = enterpriseUserService.selectById(empId);
		Long entId = null;
		String user_name = null;
		if (user != null){
			entId = user.getEnterpriseId();
			user_name = user.getName();
		} else {
			user_name = updUser;
		}

		int r=1;
		Payment payment=new Payment();
		payment.setOrderId(orderId);
		payment.setStatus(0);
		payment.setPayType(11);
		List<Payment> lstP= paymentService.selectByModel(payment);
		if(lstP==null || lstP.isEmpty()) {
			payment=null;
		} else {
			payment=lstP.get(0);
		}
		BigDecimal absCash=BigDecimal.ZERO;
		BigDecimal absTicket=BigDecimal.ZERO;
		
		if(payment!=null) {
			absCash=payment.getTotalFee();
			absTicket=NumberUtil.toBigDecimal(payment.getExp1());
		}
		
		//处理ACE用户消费积分抵消处理
		if(aceSupplierId.equals(entId)) {
			Map<String,Object> paramPush=new HashMap<String,Object>();
			paramPush.put("ticket", aceTicket);
			paramPush.put("userid", aceUserId);
			paramPush.put("resume", absTicket.intValue());
			Long time = System.currentTimeMillis();
			paramPush.put("M8", EncryptUtils.Md5Encode(aceTicket+aceUserId+absTicket.intValue()+time));
			paramPush.put("M9", time);
			String str = HttpClientUtil.sendHttpRequest("post", aceUrl + "aceserver/updateUserAccount.r", paramPush);
			Map paraMap = JsonUtil.getMap4Json(str);
			Integer code = (Integer) paraMap.get("code");
			if(code == 2) {
				act.setSuccess(false);
				act.setMsg("内购券余额不足");
				act.setData(-2l);
				return act;
			}
		}

		//流水id
		Long flowId=dbUtils.CreateID();
		if(payment!=null && (NumberUtil.isGreaterZero(absCash) || NumberUtil.isGreaterZero(absTicket))) {
			r=empBenefitFacade.dealEmpBenefit(empId, "203", absCash, absTicket, updUser, orderId+"", flowId, entId, user_name,
					"下单时抵扣,订单ID:"+orderId, 0, 0,"");
			
			// 更新支付状态
			payment.setStatus(2);
			payment.setTradeNo(flowId+"");
			paymentService.update(payment);
		}
		
		switch (r) {
		case 1:
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(1L);
			break;
		case 0:
			act.setSuccess(false);
			act.setMsg("系统异常");
			act.setData(0l);
			break;
		case -1:
			act.setSuccess(false);
			act.setMsg("现金余额不足");
			act.setData(-1l);
			break;
		case -2:
			act.setSuccess(false);
			act.setMsg("内购券余额不足");
			act.setData(-2l);
			break;
		case -3:
			act.setSuccess(false);
			act.setMsg("现金、内购券余额都不足");
			act.setData(-3l);
			break;
		}
		
		return act;
	}
	
	/**
	 * 订单取消返还金额
	 * @param empId 员工id
	 * @param absCash 消费现金
	 * @param absTicket 消费内购券
	 * @param key 关键字
	 * @param desrc 备注 
	 * @param updUser 更新者
	 * @return 1：正常处理/0：系统异常/-1：现金余额不足/-2：内购券余额不足/-3：现金、内购券余额都不足
	 */
	@RequestMapping(value="cancelOrder")
	@ResponseBody
	@NoCheckLogin
	public ActResult<Long> cancelOrder(Long empId, BigDecimal absCash,
			BigDecimal absTicket, String key, String desrc, String updUser,
			String aceTicket,String aceUserId,HttpServletRequest request) {
		
		ActResult<Long> act = new ActResult<Long>();
		EnterpriseUser user = enterpriseUserService.selectById(empId);
		Long entId = null;
		String user_name = null;
		if (user != null){
			entId = user.getEnterpriseId();
			user_name = user.getName();
		} else {
			user_name = updUser;
		}
		// 清零
		if (absCash == null)
			absCash = BigDecimal.ZERO;
		if (absTicket == null)
			absTicket = BigDecimal.ZERO;

		//处理ACE用户消费积分抵消处理
		if(aceSupplierId.equals(entId) && NumberUtil.isGreaterZero(absTicket)) {
			Map<String,Object> paramPush=new HashMap<String,Object>();
			paramPush.put("ticket", aceTicket);
			paramPush.put("userid", aceUserId);
			paramPush.put("resume", absTicket.intValue()*-1);
			Long time = System.currentTimeMillis();
			paramPush.put("M8", EncryptUtils.Md5Encode(aceTicket+aceUserId+absTicket.intValue()+time));
			paramPush.put("M9", time);
			String str = HttpClientUtil.sendHttpRequest("post", aceUrl + "aceserver/updateUserAccount.r", paramPush);
			Map paraMap = JsonUtil.getMap4Json(str);
			Integer code = (Integer) paraMap.get("code");
			if(code == 2) {
				act.setSuccess(false);
				act.setMsg("内购券余额不足");
				act.setData(-2l);
				return act;
			}
		}
		//流水id
		Long flowId=dbUtils.CreateID();
		// 账务处理
		int r = empBenefitFacade.dealEmpBenefit(empId, "202", absCash,
				absTicket, updUser, key, flowId, entId, user_name, desrc, 0, 0,"");
		switch (r) {
		case 1:
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(flowId);
			break;
		case 0:
			act.setSuccess(false);
			act.setMsg("系统异常");
			act.setData(0l);
			break;
		case -1:
			act.setSuccess(false);
			act.setMsg("现金余额不足");
			act.setData(-1l);
			break;
		case -2:
			act.setSuccess(false);
			act.setMsg("内购券余额不足");
			act.setData(-2l);
			break;
		case -3:
			act.setSuccess(false);
			act.setMsg("现金、内购券余额都不足");
			act.setData(-3l);
			break;
		}
		
		return act;
	}

	/**
	 * 员工向亲友馈赠
	 * @param empId	员工id
	 * @param userId 亲友账户Id
	 * @param absCash 操作现金
	 * @param absTicket 操作内购券
	 * @param empName 员工姓名
	 * @param userName 亲友姓名
	 * @param updUser 当前账号
	 * @param desrc1 备注
	 * @param desrc2 备注
	 * @return 1：正常处理/0：系统异常/-1：现金余额不足/-2：内购券余额不足/-3：现金、内购券余额都不足
	 */
	@RequestMapping(value="giftEmpBenefit")
	@ResponseBody
	@NoCheckLogin
	public ActResult<Long> giftEmpBenefit(Long empId,Long userId, BigDecimal absCash,
			BigDecimal absTicket, String empName, String userName,
			String updUser,String desrc1,String desrc2) {
		
		ActResult<Long> act = new ActResult<Long>();
		EnterpriseUser user = enterpriseUserService.selectById(empId);
		Long entId = null;
		if (user != null){
			entId = user.getEnterpriseId();
		}
		// 清零
		if (absCash == null)
			absCash = BigDecimal.ZERO;
		if (absTicket == null)
			absTicket = BigDecimal.ZERO;
		
		//流水id
		Long flowId=dbUtils.CreateID();
		// 账务处理
		int r = empBenefitFacade.giftEmpBenefit(empId, userId, absCash, absTicket, empName, userName, flowId, entId, updUser, desrc1, desrc2);
		
		switch (r) {
		case 1:
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(flowId);
			break;
		case 0:
			act.setSuccess(false);
			act.setMsg("系统异常");
			act.setData(0l);
			break;
		case -1:
			act.setSuccess(false);
			act.setMsg("现金余额不足");
			act.setData(-1l);
			break;
		case -2:
			act.setSuccess(false);
			act.setMsg("内购券余额不足");
			act.setData(-2l);
			break;
		case -3:
			act.setSuccess(false);
			act.setMsg("现金、内购券余额都不足");
			act.setData(-3l);
			break;
		}
		
		return act;
	}
		

	/**
	 * 黑包支付
	 * @param fromId from用户id
	 * @param toId to用户Id
	 * @param absCash 操作现金
	 * @param absTicket 操作内购券
	 * @param fromName 员工姓名
	 * @param toName 亲友姓名
	 * @param entId 企业id
	 * @param key 关键字
	 * @param updUser 当前账号
	 * @param desrc1 备注
	 * @param desrc2 备注
	 * @return 1：正常处理/0：系统异常/-1：现金余额不足/-2：内购券余额不足/-3：现金、内购券余额都不足
	 */
	@RequestMapping(value="payEnvelope")
	@ResponseBody
	@NoCheckLogin
	public ActResult<Long> payEnvelope(Long fromId, Long toId, BigDecimal absCash, BigDecimal absTicket, String fromName,
			String toName, Long entId,String key, String updUser, String desrc1, String desrc2) {

		ActResult<Long> act = new ActResult<Long>();
		// 清零
		if (absCash == null)
			absCash = BigDecimal.ZERO;
		if (absTicket == null)
			absTicket = BigDecimal.ZERO;
		
		//流水id
		Long flowId=dbUtils.CreateID();
		// 账务处理
		int r = empBenefitFacade.envelopeEmpBenefit(fromId, toId, absCash, absTicket, fromName, toName, flowId, entId, updUser,key, desrc1, desrc2);
		
		switch (r) {
		case 1:
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(flowId);
			break;
		case 0:
			act.setSuccess(false);
			act.setMsg("系统异常");
			act.setData(0l);
			break;
		case -1:
			act.setSuccess(false);
			act.setMsg("现金余额不足");
			act.setData(-1l);
			break;
		case -2:
			act.setSuccess(false);
			act.setMsg("内购券余额不足");
			act.setData(-2l);
			break;
		case -3:
			act.setSuccess(false);
			act.setMsg("现金、内购券余额都不足");
			act.setData(-3l);
			break;
		}
		
		return act;
	}
	
	/**
	 * 回收馈赠
	 * @param empId	员工id
	 * @param userId 亲友账户Id
	 * @param absCash 操作现金
	 * @param absTicket 操作内购券
	 * @param empName 员工姓名
	 * @param userName 亲友姓名
	 * @param updUser 当前账号
	 * @param desrc1 备注
	 * @param desrc2 备注
	 * @return 1：正常处理/0：系统异常/-1：现金余额不足/-2：内购券余额不足/-3：现金、内购券余额都不足
	 */
	@RequestMapping(value="recoveryEmpGift")
	@ResponseBody
	@NoCheckLogin
	public ActResult<Long> recoveryEmpGift(Long empId,Long userId, BigDecimal absCash,
			BigDecimal absTicket, String empName, String userName,
			String updUser,String desrc1,String desrc2) {

		ActResult<Long> act = new ActResult<Long>();
		EnterpriseUser user = enterpriseUserService.selectById(empId);
		Long entId = null;
		if (user != null){
			entId = user.getEnterpriseId();
		}
		// 清零
		if (absCash == null)
			absCash = BigDecimal.ZERO;
		if (absTicket == null)
			absTicket = BigDecimal.ZERO;
		
		//流水id
		Long flowId=dbUtils.CreateID();
		// 账务处理
		int r = empBenefitFacade.recoveryEmpGift(empId, userId, absCash, absTicket, empName, userName, flowId, entId, updUser, desrc1, desrc2);
		
		switch (r) {
		case 1:
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(flowId);
			break;
		case 0:
			act.setSuccess(false);
			act.setMsg("系统异常");
			act.setData(0l);
			break;
		case -1:
			act.setSuccess(false);
			act.setMsg("现金余额不足");
			act.setData(-1l);
			break;
		case -2:
			act.setSuccess(false);
			act.setMsg("内购券余额不足");
			act.setData(-2l);
			break;
		case -3:
			act.setSuccess(false);
			act.setMsg("现金、内购券余额都不足");
			act.setData(-3l);
			break;
		}
		
		return act;
	}
	

	/**注册共通的用户，用手机号注册
	 * @param phone
	 * @return
	 */
	private ActResult<CommUser> registerCommon(EnterpriseUser addEntUser,int userType, HttpServletRequest request){
		CommUser user = new CommUser();
		user.setUserName(StringUtils.isEmpty(addEntUser.getUserName())?addEntUser.getPhone():addEntUser.getUserName());
		user.setNickName(StringUtils.isEmpty(addEntUser.getName())?user.getUserName():addEntUser.getName());
		if(user.getNickName().equals(addEntUser.getPhone())) {
			String nickName=addEntUser.getPhone();
			if(nickName.length()>4) {
				nickName="1***"+nickName.substring(nickName.length()-4);
				user.setNickName(nickName);
			}
		}
		user.setPassword(StringUtils.isEmpty(addEntUser.getPhone())?"1234":addEntUser.getPhone());
		user.setRealName(addEntUser.getName());
		user.setUserPhone(addEntUser.getPhone());
		user.setUserEmail(addEntUser.getEmail());
		user.setUsable(1);
		user.setUserType(userType);
		user.setEnabled(1);//用户默认是被激活的
		user.setUserCom("company");
		user.setUserIp(getIpAddr(request));
		return us.insert(user);
	}

	/**
	 * 注册厂买家信息，并激活
	 * @param userId
	 * @param email
	 * @param creatDate
	 * @param enterpriseId
	 * @return
	 */
	private Boolean registerFactoryUser(CommUser user,Long enterpriseId,String empDefultAvatar,String shopLink){
	 	UserFactory fus = new UserFactory();
		fus.setId(user.getUserId());
		fus.setUserName(user.getUserName());
		fus.setNickName(user.getNickName());
		fus.setPhone(user.getUserPhone());
		fus.setRealName(user.getRealName());
		fus.setEmail(user.getUserEmail());
		//禁用标示  0:禁用。1:未禁用
		fus.setUsable(1);
		//类型 2:是商家，1:是买家
		fus.setType(1);//2
		//是否激活  0:未激活。1:表示已激活
		fus.setEnabled(1);//0
		//用户等级，默认为0
		fus.setUserLevel(0);
		fus.setCreatTime(user.getCreatedTime());
		//用户类型：0普通，1员工，2亲友  
		fus.setEmployeeType(1);
		//企业id
		fus.setSupplierId(enterpriseId);
		fus.setAvatar(empDefultAvatar);
		fus.setShopLink(shopLink);
		userService.saveId(fus);
		return true;
    }

	/**注册企业用户
	 * @param userId
	 * @param email
	 * @return
	 */
	private EnterpriseUser registerEnterpriseUser(EnterpriseUser entuser){
		EnterpriseUser entUser = new EnterpriseUser();
		entUser.setId(entuser.getId());
		
		entUser.setEnterpriseId(entuser.getEnterpriseId());
		entUser.setUserName(entuser.getUserName());
		entUser.setType(2);
		entUser.setAge(entuser.getAge());
		entUser.setEmpNumber(entuser.getEmpNumber());
		entUser.setName(entuser.getName());
		entUser.setPhone(entuser.getPhone()==null?"":entuser.getPhone());
		entUser.setSeniority(entuser.getSeniority());
		entUser.setSex(entuser.getSex());
		entUser.setDuty(entuser.getDuty());
		entUser.setWelfareLevel(entuser.getWelfareLevel());
		entUser.setEmail(entuser.getEmail()==null?"":entuser.getEmail());
		entUser.setLogout(Byte.valueOf("0"));

		this.enterpriseUserService.insertSelective(entUser);
		return entUser;
	}
	private void autoBenefit(HttpServletRequest request, List<EnterpriseUser> newEmps,Long supplierId) {
		for (EnterpriseUser enterpriseUser : newEmps) {

			int ticket = 500;
			//int result= 
			empBenefitFacade.dealEmpBenefit(enterpriseUser.getId(), "216", BigDecimal.ZERO, NumberUtil.toBigDecimal(ticket), 
					enterpriseUser.getName(), enterpriseUser.getId()+"", dbUtils.CreateID(), enterpriseUser.getEnterpriseId(), 
					enterpriseUser.getName(), "员工注册，平台代企业下发内购券。", SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(),"");

			//String msg = "公司向您发放内购券500元！您可以登陆 我的福利商城购买内购产品，或分享给亲友，享受大牌员工福利！";
			//AppPushUtil.pushMsg(enterpriseUser.getId(), "公司发放福利", msg);
			
			//AppPushUtil.pushWxBalace(enterpriseUser.getId(), ticket+"", TimeUtil.getStringDateShort(), msg, "1");
		}
	}
	private static String getIpAddr(HttpServletRequest request) {
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
	/**
	 * 邀请奖励
	 * @param from 邀请人
	 * @param to 被邀请人
	 */
	private void invitBonuses(EnterpriseUser from,EnterpriseUser to){
		EntParamCode fistPrize = entParamCodeService.getAppFirstPrizeCode().get("201");
		if(fistPrize!=null && !"1".equals(fistPrize.getStopFlg())) {
			String note = StringUtils.isEmpty(fistPrize.getDescr())?"和同事一起享受员工内购福利，平台奖励内购券：":fistPrize.getDescr();
			empBenefitFacade.invitBonuses(from,to,fistPrize,note);
			AppPushUtil.pushWxBalace(from.getId(), fistPrize.getValue(), TimeUtil.getStringDateShort(), note, "1");
			AppPushUtil.pushMsg(from.getId(), fistPrize.getName(), note);
			AppPushUtil.pushMsg(to.getId(), fistPrize.getName(), note);
			AppPushUtil.pushWxBalace(to.getId(), fistPrize.getValue(), TimeUtil.getStringDateShort(), note, "1");
		}
	}
}

