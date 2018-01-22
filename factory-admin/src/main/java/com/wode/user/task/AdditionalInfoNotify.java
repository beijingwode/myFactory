package com.wode.user.task;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.UserFactoryService;


@Service
public class AdditionalInfoNotify {
	@Autowired
	private UserFactoryService userFactoryService;

	@Autowired
	private SupplierService supplierService;
	
	@Value("#{configProperties['factory.api.url']}")
	private  String apiUrl;
	
	/**
	 * 手机绑定通知
	 */
	public void phone() {

		Calendar date = Calendar.getInstance();
		int day = date.get(Calendar.DAY_OF_MONTH);
		if(day==31) return;			//月末休息
		day = day%10;
		UserFactory model = new UserFactory();
		model.setType(day);
		List<UserFactory> lst= userFactoryService.selectNoPhone(model);
		Map<Long,String> map = new HashMap<Long,String>();
		if (lst!=null && !lst.isEmpty()) {
			//遍历
			Map<String,Object> paramWX = new HashMap<String,Object>();
			paramWX.put("type", "additional_info");
			paramWX.put("first", "为保障员工权益，请您完善个人资料以便确认员工身份");
			paramWX.put("info", "绑定手机号");
			paramWX.put("note", "手机号将帮助企业核实员工身份，并能保护您的账户安全");
			
			Map<String,Object> paramPush = new HashMap<String,Object>();
			paramPush.put("title", "完善资料通知");
			for (UserFactory u : lst) {
				try {
					// app 推送
					paramPush.put("userId", u.getId());
					paramPush.put("msg", "为保障员工权益，"+getComName(u.getSupplierId(), map)+"(公司)要求您完善个人资料，以便确认员工身份。请绑定电话，填写真实姓名");
					HttpClientUtil.sendHttpRequest("post", apiUrl + "user/pushMsg", paramPush);
				} catch (Exception ex) {

				}
				
				try {
					// 微信推送
					paramWX.put("userId", u.getId());
					paramWX.put("comName", getComName(u.getSupplierId(), map));
					paramWX.put("info", "绑定常用手机号");
					HttpClientUtil.sendHttpRequest("post", apiUrl + "wx/templateMsgSend", paramWX);
				} catch (Exception ex) {

				}
			}
		}
	}

	/**
	 * 手机绑定通知
	 */
	public void nickName() {

		Calendar date = Calendar.getInstance();
		int day = date.get(Calendar.DAY_OF_MONTH);
		if(day==31) return;			//月末休息
		day = day%10;
		UserFactory model = new UserFactory();
		model.setType(day);
		List<UserFactory> lst= userFactoryService.selectNoNickName(model);
		Map<Long,String> map = new HashMap<Long,String>();
		if (lst!=null && !lst.isEmpty()) {
			//遍历
			Map<String,Object> paramWX = new HashMap<String,Object>();
			paramWX.put("type", "additional_info");
			paramWX.put("first", "为保障员工权益，请您完善个人资料以便确认员工身份");
			paramWX.put("info", "修改昵称");
			paramWX.put("note", "请使用常用昵称，以便同事们快速找到您，建议使用真实姓名");
			
			Map<String,Object> paramPush = new HashMap<String,Object>();
			paramPush.put("title", "完善资料通知");
			for (UserFactory u : lst) {
				try {
					// app 推送
					paramPush.put("userId", u.getId());
					paramPush.put("msg", "为保障员工权益，"+getComName(u.getSupplierId(), map)+"(公司)要求您完善个人资料，以便确认员工身份。请绑定电话，填写真实姓名");
					HttpClientUtil.sendHttpRequest("post", apiUrl + "user/pushMsg", paramPush);
				} catch (Exception ex) {

				}
				
				try {
					// 微信推送
					paramWX.put("userId", u.getId());
					paramWX.put("comName", getComName(u.getSupplierId(), map));
					paramWX.put("info", "绑定常用手机号");
					HttpClientUtil.sendHttpRequest("post", apiUrl + "wx/templateMsgSend", paramWX);
				} catch (Exception ex) {

				}
			}
		}
	}
	
	private String getComName(Long comId,Map<Long,String> map) {
		if(StringUtils.isEmpty(comId)) return "我的福利 联合内购";
		
		if(map.containsKey(comId)) {
			Supplier s = supplierService.findByid(comId);
			if(s==null) {
				map.put(comId, "我的福利 联合内购");
			} else {
				map.put(comId, s.getComName());
			}
		}
		return map.get(comId);		
	}
}
