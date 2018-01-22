package com.wode.tongji.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.constant.Constant;
import com.wode.common.util.ActResult;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserDevice;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.JPushService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.service.EnterpriseUserService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.UserDevicesService;
import com.wode.factory.service.UserFactoryService;

@Controller
@RequestMapping("appPush")
public class AppPushController {
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private UserFactoryService userFactoryService;
	@Autowired
	private EnterpriseUserService enterpriseUserService;
	@Autowired
	private UserDevicesService userDevicesService;
	static JPushService push = ServiceFactory.getJPushService(Constant.OUTSIDE_SERVICE_URL);
	@RequestMapping("page")
	public String viewInfo(Model model) {
		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("suppliers", getSupplierList(query));
		return "tongji/appPush/appPush";
	}
	@RequestMapping("findCount")
	@ResponseBody
	public ActResult<String> findCount(Model model,Long supplierId,String phone) {
		Integer userCount = 0;
		Map<String,Long> paramPush = new HashMap<String,Long>();
		if("".equals(phone)){
			paramPush.put("enterpriseId", supplierId);
			userCount = enterpriseUserService.findEnterpriseByDeviceCnt(paramPush);
		}else{
			EnterpriseUser enterpriseUser = new EnterpriseUser();
			enterpriseUser.setPhone(phone);		
			enterpriseUser.setLogout((byte) 0);
			List<EnterpriseUser> selectByModel = enterpriseUserService.selectByModel(enterpriseUser);
			if(selectByModel.size()<=0){
				return ActResult.fail("参数错误");
			}else{
				paramPush.put("id", selectByModel.get(0).getId());
				userCount = enterpriseUserService.findEnterpriseByDeviceCnt(paramPush);
			}
		}
		return ActResult.success(userCount);
	}
	@RequestMapping("appSms")
	@ResponseBody
	public ActResult<String> appSms(String phone,Long supplierId,String headline,String content,
			String appLink, HttpSession session){
		UserFactory model = new UserFactory();
		model.setEnabled(1);
		model.setUsable(1);
		model.setEmployeeType(1);
		if("".equals(phone)){
			if(supplierId==null&&"".equals(supplierId)){
				return ActResult.fail("系统错误");
			}
			model.setSupplierId(supplierId);
		}else{
			model.setPhone(phone);
		}
		Map<String,String> paramPush = new HashMap<String,String>();
		paramPush.put("url", appLink);
		paramPush.put("flag", "1");
		List<UserFactory> lst= userFactoryService.selectByModel(model);
		try {
			for (UserFactory userFactory : lst) {
				List<UserDevice> userDeviceList = userDevicesService.findByUserId(userFactory.getId());
					for (UserDevice userDevice : userDeviceList) {
						push.sendNotification("user", content, headline, paramPush, this.formatDriver(userDevice.getDeviceType()),"alias", userDevice.getAsname(), true, null);
					}
			}
		} catch (Exception ex) {
			return ActResult.fail("系统错误");
		}
		return ActResult.success("推送成功");
	}
	
	private List<Supplier> getSupplierList(Map<String, Object> query) {
		query.put("status", 2);
		query.put("pageNum", 1);
		query.put("pageSize",1000);
		
		return supplierService.getPage(query).getList();
	}
	public String formatDriver(String type) {
		if("iPhone".equals(type)) return "ios";
		return type;
	}
}
