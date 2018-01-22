package com.wode.api.web.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wode.api.facade.LoginFacade;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.ExchangeOrders;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.ExchangeSuborderitem;
import com.wode.factory.model.ManagerOrderRecord;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.WxOpenService;
import com.wode.factory.user.model.ManagerTicketGrant;
import com.wode.factory.user.service.ManagerOrderRecordService;
import com.wode.factory.user.service.ManagerTicketGrantService;
import com.wode.factory.user.service.SupplierService;
import com.wode.factory.user.util.Constant;

/**
 * 招商（线下发货记录）
 * @author user
 *
 */

@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/managerOrderRecord")
public class ManagerOrderRecordController extends BaseController{
	
	@Autowired
	private ManagerOrderRecordService managerOrderRecordService;
	@Autowired
	private ManagerTicketGrantService managerTicketGrantService;
	@Autowired
	private LoginFacade loginFacade;
	@Autowired
	private SupplierService supplierService;
	/**
	 * 查询当前招商的线下发货列表信息
	 * @return
	 */
	@RequestMapping("/getList.user")
	@ResponseBody
	public ActResult<Map<String, Object>> getOfflineRecordList(Integer page, Integer pageSize,Integer type){
		Map<String,Object> query = new HashMap<String,Object>();
		if(type == 1) {
			query.put("createDate", new Date());
		}
		query.put("userId", Long.valueOf(loginUser.getId()));
		query.put("page", page);
		query.put("pageSize", pageSize);
		List<ManagerOrderRecord> list = managerOrderRecordService.getManagerOrderRecordList(query);
		PageInfo pageInfo = new PageInfo(list);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", pageInfo.getList());
		data.put("totalCount", pageInfo.getTotal());
		data.put("page", page);
		return ActResult.success(data);
	}
	
	/**
	 * 添加线下发货信息记录
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("/addManagerOrderRecord.user")
	@ResponseBody
	public ActResult<Object> addManagerOrderRecord(ManagerOrderRecord managerOrderRecord,Integer flag) throws IllegalAccessException, InvocationTargetException{
		managerOrderRecordService.addManagerOrderRecord(managerOrderRecord,loginUser,flag);
		return ActResult.success("");
	}
	
	/**
	 * 跳转到线下发货页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toOfflineRecordPage.user")
	@ResponseBody
	public ModelAndView toOfflineRecordPage(String uid,ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		UserFactory user = loginUser;
		model.addObject("uid", user.getId());
		model.setViewName("offline_delivery");
		return model;
	}
	
	/**
	 * 跳转到线下发货页面
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/makeTicketGrant.user")
	@ResponseBody
	public ActResult<String> makeTicketGrant(Integer currencyId,BigDecimal amount,Long companyId) throws UnsupportedEncodingException{
		currencyId = 1; 						// 内购券
		amount = NumberUtil.toBigDecimal(500);	// 500
		
		if(loginUser.getRole() == null || loginUser.getRole()!=9) {
			return ActResult.fail("参数错误，请联系运营人员重新设置");
		}

		ManagerTicketGrant mtg = new ManagerTicketGrant();
		mtg.setManagerId(loginUser.getId());
		mtg.setManagerName(StringUtils.isEmpty(loginUser.getRealName())?loginUser.getNickName():loginUser.getRealName());
		mtg.setCreateDate(new Date());
		mtg.setCurrencyId(currencyId);
		mtg.setAmount(amount);
		mtg.setStatus(0);
		managerTicketGrantService.save(mtg);
		String qrUrl = "http://"+Constant.SYSTEM_DOMAIN+"/managerOrderRecord/toGrantTicket"+mtg.getId()+"Page?date="+new Date();
		return ActResult.success(qrUrl);
	}

	@RequestMapping("/toGrantTicket{grantId}Page")
	public ModelAndView toGrantTicketPage(HttpServletRequest request, @PathVariable Long grantId) {
		ModelAndView result = new ModelAndView("toGrantTicket");
		String errCode = "";
		ManagerTicketGrant mtg = managerTicketGrantService.getById(grantId);
		if(mtg == null || mtg.getStatus()!=0) {
			errCode = "qr invalid";
			result.setViewName("grant_ticket");
			result.addObject("errCode", errCode);
		} else {
			result.addObject("state", "ticketGrant"+mtg.getId());
		}
		return result;
	}
	
	@RequestMapping("/ticketGrant{shareId}Page")
	public ModelAndView ticketGrantPage(HttpServletRequest request, @PathVariable Long shareId,Long uid,String openId) {
		ModelAndView result = new ModelAndView("grant_ticket");		// TODO 新的发券页
		String errCode = "success";
		UserFactory user = null;
		ManagerTicketGrant mtg = null;
		// 参数检查
		if(uid == null) {
			errCode= "need login";
		} else {
			user = this.getLoginUser(request);
			if(user == null) {
				errCode= "need login";
			} else {
				mtg = managerTicketGrantService.getById(shareId);
				if(mtg == null || mtg.getStatus()!=0) {
					errCode = "qr invalid";
				}
			}
		}
		
		if("success".equals(errCode)) {
			if(this.sendUserFuli(user, mtg.getAmount().toString(), "感谢您的积极参与，"+mtg.getManagerName()+"送您内购券", mtg.getManagerName(), mtg.getId()+"")) {
				// 入账成功
				mtg.setUserId(user.getId());
				mtg.setUserName(user.getNickName());
				mtg.setUserCompany(user.getSupplierId());	// 暂不做公司检查
				mtg.setStatus(1); // 已领用
				mtg.setUpdateDate(new Date());
				managerTicketGrantService.update(mtg);
			} else {
				// 系统异常
				errCode = "system error";
			}
		}
		
		result.addObject("errCode", errCode); 		// 根据errCode 提示信息，跳转或者关闭页面
		result.addObject("openId", openId);
		return result;
	}
	

	private boolean sendUserFuli(UserFactory emp, String limitm, String note,String updName,String key) {
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("limitm", limitm);
		paramMap.put("desrc", note);
		if(emp!=null) {
			paramMap.put("empId", emp.getId());
			paramMap.put("empName", emp.getNickName());
			paramMap.put("updName",updName);
		}
		if(key!=null) {
			paramMap.put("key", key);
		}
		String ticketResult = HttpClientUtil.sendHttpRequest("post", Constant.QIYE_API_URL+"api/fisrtPrize", paramMap);
		ActResult<Long> acTicket = JsonUtil.getObject(ticketResult, ActResult.class);
		return acTicket.isSuccess();
	}
	
	@RequestMapping("/merchanPage.user")
	public ModelAndView merchanPage(HttpServletRequest request) {
		UserFactory user = loginUser;
		ModelAndView result = new ModelAndView("merchan_qr_code");
		result.addObject("userId", user.getId());
		return result;
	}
	@RequestMapping("/managerSupplier.user")
	@ResponseBody
	public ActResult<String> managerSupplier(String comName){
		ActResult act = new ActResult();
		Map paramMap = new HashMap();
		paramMap.put("userId", loginUser.getId());
		String resu = HttpClientUtil.sendHttpRequest("post", Constant.CACHE_API_URL +"/supplierExit/findMangerId" ,paramMap);
		ActResult<String> acTicket = JsonUtil.getObject(resu, ActResult.class);
		Map<String,String> queryMap = new HashMap<String,String>();
		queryMap.put("managerId", acTicket.getData());
		if(comName!=null&&!"".equals(comName)){
			queryMap.put("comName", comName);
		}else{
			queryMap.put("comName", "");
		}
		List<Supplier> supplierList = supplierService.findByManagerId(queryMap);
		Map map = new HashMap();
		map.put("supplierList", supplierList);
		map.put("managerId", acTicket.getData().toString());
		map.put("managerName", acTicket.getMsg());
		//act.setMsg(managerId);
		act.setData(map);
		return act;
	} 
}
