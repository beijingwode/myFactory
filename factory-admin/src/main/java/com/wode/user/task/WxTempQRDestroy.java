package com.wode.user.task;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.model.UserShare;
import com.wode.factory.service.UserShareService;


@Service
public class WxTempQRDestroy {
	@Autowired
	private UserShareService userShareService;

	/**
	 * 定期清空到期微信临时二维码
	 */
	public void run() {

		Calendar date = Calendar.getInstance();
		UserShare model = new UserShare();
		model.setShareType(9);
		model.setWxTempLimitEnd(date.getTime());
		List<UserShare> lst= userShareService.selectByModel(model);
		
		if (lst!=null && !lst.isEmpty()) {
			//遍历
			for (UserShare u : lst) {
				u.setWxTempLimitEnd(null);
				u.setWxTempQrUrl(null);
				
				userShareService.update(u);
			}
		}
	}
	

}
