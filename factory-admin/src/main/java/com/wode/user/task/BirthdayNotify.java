package com.wode.user.task;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wode.common.util.HttpClientUtil;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserIm;
import com.wode.factory.service.UserFactoryService;


@Service
public class BirthdayNotify {
	@Autowired
	private UserFactoryService userFactoryService;

	@Value("#{configProperties['factory.api.url']}")
	private  String apiUrl;
	
	/**
	 * 添加周统计信息
	 */
	public void run() {

		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, 5);	//提前5天生日提醒
		UserFactory model = new UserFactory();
		model.setEnabled(1);
		model.setUsable(1);
		model.setType(1);
		model.setEmployeeType(-1);  //查询全部数据
		model.setBirthday(date.getTime());
		List<UserFactory> lst= userFactoryService.selectByModel(model);
		
		if (lst!=null && !lst.isEmpty()) {
			//遍历
			for (UserFactory u : lst) {
				UserIm q = new UserIm();
				q.setUserId(u.getId());
				List<UserIm> ims = userFactoryService.selectByUserId(q);
				if(ims!=null && ims.size()>0) {
					Map paramPush=new HashMap();
					paramPush.put("uid", u.getId());
					paramPush.put("msg", u.getNickName()+"的生日（"+(date.get(Calendar.MONTH)+1)+"月"+(date.get(Calendar.DAY_OF_MONTH))+"日）快到了，送给他些祝福和礼物吧");
					paramPush.put("im", ims.get(0).getOpenimId());
					paramPush.put("nickName", u.getNickName());
					HttpClientUtil.sendHttpRequest("post", apiUrl+"contract/groupMessages", paramPush);
				}
			}
		}
	}
	

}
