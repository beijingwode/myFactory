/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.tongji.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wode.common.constant.Constant;
import com.wode.common.db.DBUtils;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.SmsService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.UserFactoryService;
import com.wode.tongji.model.SmsTemplate;
import com.wode.tongji.service.SmsTemplateService;

@Controller
@RequestMapping("sendSms")
public class SendSmsController extends BaseSpringController {
	@Autowired
	private SmsTemplateService smsTemplateService;
	@Autowired
	private UserFactoryService userFactoryService;

	@Autowired
	DBUtils dbUtils;
	
	public SendSmsController() {
	}

	@Autowired
	private SupplierService supplierService;
	
	@RequestMapping
	public String viewInfo(Model model) {
		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("suppliers", getSupplierList(query));
		SmsTemplate q =new SmsTemplate();
		q.setStopFlg("0");
		q.setAutoSend("2");
		model.addAttribute("templates", smsTemplateService.selectByModel(q));
		
		return "tongji/sms/sendSms";
	}

	@RequestMapping(value = "getTemplate", method = RequestMethod.POST)
	@ResponseBody
	public SmsTemplate getTemplates(Long id){
		return smsTemplateService.getById(id);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "doSend", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<String> doSend(Long id,String rd,String phone,Long supplierId,String param1Val,String param2Val,
			String param3Val,String param4Val,String param5Val,String param6Val, HttpSession session) {		
		SmsTemplate t =  smsTemplateService.getById(id);
		SmsService sms= ServiceFactory.getSmsService(Constant.OUTSIDE_SERVICE_URL);
		
		JSONObject joPra=new JSONObject();

		if(!StringUtils.isEmpty(t.getParam1())) {
			JSONObject p = JSONObject.parseObject(t.getParam1());
			joPra.put(p.getString("key"), param1Val);
		}
		if(!StringUtils.isEmpty(t.getParam2())) {
			JSONObject p = JSONObject.parseObject(t.getParam2());
			joPra.put(p.getString("key"), param2Val);
		}
		if(!StringUtils.isEmpty(t.getParam3())) {
			JSONObject p = JSONObject.parseObject(t.getParam3());
			joPra.put(p.getString("key"), param3Val);
		}
		if(!StringUtils.isEmpty(t.getParam4())) {
			JSONObject p = JSONObject.parseObject(t.getParam4());
			joPra.put(p.getString("key"), param4Val);
		}
		if(!StringUtils.isEmpty(t.getParam5())) {
			JSONObject p = JSONObject.parseObject(t.getParam5());
			joPra.put(p.getString("key"), param5Val);
		}
		if(!StringUtils.isEmpty(t.getParam6())) {
			JSONObject p = JSONObject.parseObject(t.getParam6());
			joPra.put(p.getString("key"), param1Val);
		}
		
		String outId = dbUtils.CreateID()+"";
		int sendCnt = 0;
		//model.setType(1);
		if("4".equals(rd)) {

			sms.sendSms(phone, "我的福利", t.getCode(), "myFactory", joPra.toJSONString(),outId, true, null);
			sendCnt=1;
		} else if("5".equals(rd)) {
			UserFactory model = new UserFactory();
			model.setEnabled(1);
			model.setUsable(1);
			model.setEmployeeType(1);
			model.setSupplierId(supplierId);
			
			List<UserFactory> lst= userFactoryService.selectByModel(model);
			int maxSize=50;
			int index=0;
			while(index<lst.size()) {
				String mobile="";
				for(int i=0;index<lst.size() && i<maxSize;i++) {
					UserFactory uf=lst.get(index);
					index++;
					if(!StringUtils.isEmpty(uf.getPhone())) {
						mobile += uf.getPhone()+",";
						sendCnt++;
					}
				}
				
				if(mobile.length()>0) {
					sms.sendSms(mobile.substring(0, mobile.length()-1), t.getSignature(), t.getCode(),outId, "myFactory", joPra.toJSONString(), true, null);
				}
			}
		}
		
		return ActResult.successSetMsg("发送成功，共发出"+sendCnt+"条短信");
	}
	
	private List<Supplier> getSupplierList(Map<String, Object> query) {
		query.put("status", 2);
		query.put("pageNum", 1);
		query.put("pageSize",1000);
		
		return supplierService.getPage(query).getList();
	}
}

