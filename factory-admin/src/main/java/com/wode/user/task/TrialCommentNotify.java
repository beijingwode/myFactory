package com.wode.user.task;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.TimeUtil;
import com.wode.factory.mapper.SuborderitemDao;
import com.wode.factory.model.Suborderitem;


@Service
public class TrialCommentNotify {
	@Autowired
	private SuborderitemDao suborderitemDao;

	@Value("#{configProperties['factory.api.url']}")
	private  String apiUrl;
	
	/**
	 * 3天未获得评价的试用商品
	 */
	public void run() {

		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, -3);	//3天未获得评价的试用商品
		List<Suborderitem> lst=suborderitemDao.findTrialByDate(TimeUtil.dateToStr(date.getTime()));
		Map<Long,Suborderitem> mapUser = new HashMap<Long,Suborderitem>();
		if (lst!=null && !lst.isEmpty()) {
			Map<String,Object> paramWX = new HashMap<String,Object>();
			paramWX.put("type", "trial_comment");
			//遍历
			for (Suborderitem o : lst) {
				if(!mapUser.containsKey(o.getSkuId())) {
					paramWX.put("subOrderId", o.getSubOrderId());
					paramWX.put("productName", o.getProductName());
					paramWX.put("amount", o.getRealPay());
					paramWX.put("prize", o.getEmpPrice().multiply(NumberUtil.toBigDecimal(o.getNumber())));
					paramWX.put("userId", o.getSkuId());
					
					try {
						HttpClientUtil.sendHttpRequest("post", apiUrl + "wx/templateMsgSend", paramWX);
						mapUser.put(o.getSkuId(), o);
					} catch (Exception ex) {

					}
				}
			}
		}
	}
	

}
