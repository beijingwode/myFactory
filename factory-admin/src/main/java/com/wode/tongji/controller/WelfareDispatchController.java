/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.tongji.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.constant.Constant;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.MailService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.service.EntParamCodeService;
import com.wode.factory.service.EnterpriseUserService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.UserFactoryService;
import com.wode.sys.model.SysUser;

@Controller
@RequestMapping("welfareDispatch")
public class WelfareDispatchController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	private EntParamCodeService entParamCodeService;
	@Autowired
	private UserFactoryService userFactoryService;
	@Autowired
	private EnterpriseUserService enterpriseUserService;
	
	public WelfareDispatchController() {
	}

	@RequestMapping
	public String toHtml(ModelMap model) {
		return "manager/welfareDispatch/welfareDispatch";
	}
	@Autowired
	private SupplierService supplierService;
	
	/** 
	 * 执行搜索 
	 **/
	@RequestMapping(value="toPrizeSet")
	public String toPrizeSet(Model model) {
		EntParamCode p = new EntParamCode();
		p.setId(8001L);
		List<EntParamCode> l = entParamCodeService.selectByModel(p);
		model.addAttribute("value", l.get(0).getValue());
		model.addAttribute("desc", l.get(0).getDescr());
		String stop ="启用";
		if(!"1".equals(l.get(0).getStopFlg())) {
			stop = "停用";
		}
		model.addAttribute("stop", stop);
		return "manager/welfareDispatch/prizeSet";
	}
	/** 
	 * 执行搜索 
	 **/
	@RequestMapping(value="saveFirstPrize")
	@ResponseBody
	public List<String> saveFirstPrize(String value,String desc) {
		EntParamCode p = new EntParamCode();
		p.setId(8001L);
		List<EntParamCode> l = entParamCodeService.selectByModel(p);
		l.get(0).setValue(value);
		l.get(0).setDescr(desc);
		if(!"1".equals(l.get(0).getStopFlg())) {
			l.get(0).setStopFlg("1");
		} else {
			l.get(0).setStopFlg("0");
		}
		entParamCodeService.update(l.get(0));
		return new ArrayList<String>();
	}
	
	/**
	 * 执行设置评论搜索
	 */
	@RequestMapping(value="toComment")
	public String toComment(Model model) {
		EntParamCode p = new EntParamCode();
		p.setId(8002L);
		List<EntParamCode> l = entParamCodeService.selectByModel(p);
		model.addAttribute("value", l.get(0).getValue());
		model.addAttribute("desc", l.get(0).getDescr());
		String stop="启用";
		if (!"1".equals(l.get(0).getStopFlg())) {
			stop="停用";
		}
		model.addAttribute("stop", stop);
		return "manager/welfareDispatch/comment";
	}
	/**
	 * 执行设置首次评价保存
	 * @param value
	 * @param desc
	 * @return
	 */
	@RequestMapping(value="saveFirstComment")
	@ResponseBody
	public List<String> saveFirstComment(String value,String desc) {
		EntParamCode p = new EntParamCode();
		p.setId(8002L);
		List<EntParamCode> l = entParamCodeService.selectByModel(p);
		l.get(0).setValue(value);
		l.get(0).setDescr(desc);
		if(!"1".equals(l.get(0).getStopFlg())) {
			l.get(0).setStopFlg("1");
		} else {
			l.get(0).setStopFlg("0");
		}
		entParamCodeService.update(l.get(0));
		return new ArrayList<String>();
	}
	/** 
	 * 执行搜索 
	 **/
	@RequestMapping(value="toDispatch")
	public String toDispatch(Model model) {

		return "manager/welfareDispatch/dispatchWelfare";
	}
	

	private List<Supplier> getSupplierList(Map<String, Object> query) {
		query.put("status", 2);
		query.put("pageNum", 1);
		query.put("pageSize",1000);
		
		return supplierService.getPage(query).getList();
	}
	/** 
	 * 执行搜索 
	 **/
	@RequestMapping(value="dispatche")
	@ResponseBody
	public List<String> dispatche(String value,String rd,String desrc,HttpSession session) {
		UserFactory model = new UserFactory();
		model.setEnabled(1);
		model.setUsable(1);
		model.setType(1);
		if("1".equals(rd)) {
			model.setEmployeeType(1);
		} else if("2".equals(rd)) {
			model.setEmployeeType(0);
		}
		String updName = "admin";
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		if(obj != null){
			SysUser user = (SysUser)obj;
			updName = user.getUsername();
		}

		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("limitm", value);
		paramMap.put("desrc", StringUtils.isEmpty(desrc)?"平台向您派送":desrc);
		paramMap.put("updName",updName);

		Map<String,Object> paramPush=new HashMap<String,Object>();
		paramPush.put("title", "平台派发福利");
		paramPush.put("msg", desrc);
		List<UserFactory> lst= userFactoryService.selectByModel(model);
		
		Map<String,Object> paramWX = new HashMap<String,Object>();
		paramWX.put("type", "balace");
		paramWX.put("cId", "1");
		paramWX.put("date", TimeUtil.getStringDateShort());
		paramWX.put("amount", value);
		paramWX.put("note", desrc);
		
		for (UserFactory userFactory : lst) {			
			try{
				//派发福利
				paramMap.put("empId", userFactory.getId());
				paramMap.put("empName", userFactory.getUserName());
				HttpClientUtil.sendHttpRequest("post", Constant.FACTORY_SUPPLIER_URL+"api/dispatcheBenefit", paramMap);

				//app 推送
				paramPush.put("userId", userFactory.getId());
				HttpClientUtil.sendHttpRequest("post", Constant.FACTORY_API_URL+"user/pushMsg", paramPush);

				//微信推送
				paramWX.put("userId", userFactory.getId());
				HttpClientUtil.sendHttpRequest("post", Constant.FACTORY_API_URL + "wx/templateMsgSend", paramWX);
			} catch(Exception ex) {
				
			}
		}
		return new ArrayList<String>();
	}

	/** 
	 * 执行搜索 
	 **/
	@RequestMapping(value="toSmsSend")
	public String toSmsSend(Model model) {
		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("supplierList", getSupplierList(query));
		return "manager/welfareDispatch/smsSend";
	}
	/**
	 * 推送邮件
	 */
	@RequestMapping(value="toEmailSend")
	public String toEmailSend(Model model){
		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("supplierList", getSupplierList(query));
		return "manager/welfareDispatch/emailSend";
	}
	/** 
	 * 执行搜索 
	 **/
	@RequestMapping(value="sendSms")
	@ResponseBody
	public List<String> sendSms(String value,String rd,String desrc,String phone,Long supplierId,HttpSession session) {
		UserFactory model = new UserFactory();
		model.setEnabled(1);
		model.setUsable(1);
		model.setType(1);
		if("1".equals(rd)) {
			model.setEmployeeType(1);
		} else if("2".equals(rd)) {
			model.setEmployeeType(0);
		} else if("4".equals(rd)) {
			model.setEmployeeType(-1); //全部数据
			model.setPhone(phone);
		} else if("5".equals(rd)) {
			model.setEmployeeType(1);
			model.setSupplierId(supplierId);
		} 
		
		Map<Long,Supplier> map = new HashMap<Long,Supplier>();
		List<UserFactory> lst= userFactoryService.selectByModel(model);
		for (UserFactory userFactory : lst) {			
			try{
				String contnt = value+desrc;
				if(contnt.contains("[company]") && userFactory.getSupplierId()!=null) {
					Supplier s=map.get(userFactory.getSupplierId());
					if(s==null) {
						s=supplierService.findByid(userFactory.getSupplierId());
						if(s!=null) {
							map.put(userFactory.getSupplierId(), s);
						}
					}
					
					if(s!=null) {
						contnt= contnt.replace("[company]", s.getComName());
					}
				}
				if(!contnt.startsWith("【我的福利】")) {
					contnt= "【我的福利】"+contnt;
				}
				Map<String,Object> paramPush=new HashMap<String,Object>();
				paramPush.put("phoneNumber", userFactory.getPhone());
				paramPush.put("contnt", contnt);
				HttpClientUtil.sendHttpRequest("post", Constant.FACTORY_API_URL+"user/sendSms", paramPush);
				
			} catch(Exception ex) {
				
			}
		}
		return new ArrayList<String>();
	}
	/**
	 * 执行推送
	 */
	@RequestMapping(value="sendEmail")
	@ResponseBody
	public List<String> sendEmail(String value,String rd,String title,String email,Long supplierId,int number) {

		MailService mailService = ServiceFactory.getMailService(Constant.OUTSIDE_SERVICE_URL);
		ArrayList<String> arrayList = new ArrayList<String>();
		EnterpriseUser query = new EnterpriseUser();
		StringBuilder sb = new StringBuilder();
		if("1".equals(rd)) {//全部企业
			query.setLogout((byte)0);
			List<EnterpriseUser> elist = enterpriseUserService.selectByModel(query);
			if (elist.size()>0&&elist!=null) {
				for (EnterpriseUser enterpriseUser : elist) {
					 email=enterpriseUser.getEmail();
					if (!StringUtils.isEmpty(email)) {
						sb.append(email+",");
						number++;
					}
				}
				String toEmail = sb.toString();
				if (!StringUtils.isEmpty(toEmail)) {
					mailService.sendMail(toEmail, title, value,null,null,"myFactory",true,null);
					arrayList.add("发送成功，数量为"+number);
				}else{
					arrayList.add("没有有效的邮箱！");
				}
			}
		}else if("5".equals(rd)){//单个企业
			query.setLogout((byte)0);
			query.setEnterpriseId(supplierId);
			List<EnterpriseUser> elist = enterpriseUserService.selectByModel(query);
			if (elist.size()>0&&elist!=null) {
				for (EnterpriseUser enterpriseUser : elist) {
					 email=enterpriseUser.getEmail();
					if (!StringUtils.isEmpty(email)) {
						sb.append(email+",");
						number++;
					}
				}
				String toEmail = sb.toString();
				if (!StringUtils.isEmpty(toEmail)) {
					mailService.sendMail(toEmail, title, value,null,null,"myFactory",true,null);
					arrayList.add("发送成功，数量为"+number);
				}else{
					arrayList.add("该企业下没有有效邮箱！");
				}
			}
		}else if("4".equals(rd)){//指定邮箱
			if(StringUtils.isEmail(email)){
				mailService.sendMail(email, title, value,null,null,"myFactory",true,null);
				number++;
				arrayList.add("发送成功，数量为"+number);
			}else{
				String s ="邮箱格式不正确！";
				arrayList.add(s);
				return arrayList;
			}
		}
		return arrayList;
	}
}

