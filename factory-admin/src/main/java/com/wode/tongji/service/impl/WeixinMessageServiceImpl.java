package com.wode.tongji.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.WeixinMessage;
import com.wode.factory.service.impl.FactoryEntityServiceImpl;
import com.wode.tongji.mapper.WeixinMessageMapper;
import com.wode.tongji.service.WeixinMessageService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("weixinMessageService")
public class WeixinMessageServiceImpl extends FactoryEntityServiceImpl<WeixinMessage> implements WeixinMessageService {
	@Autowired
	WeixinMessageMapper dao;
	
	@Override
	public WeixinMessageMapper getDao() {
		return dao;
	}

	@Override
	public Long getId(WeixinMessage entity) {
		return entity.getId();	// 自增列 无需设置id
	}

	@Override
	public void setId(WeixinMessage entity, Long id) {
		entity.setId(id);
	}
	
	@Value("#{configProperties['factory.api.url']}")
	private  String apiUrl;
	
	@Override
	public PageInfo<WeixinMessage> findWeiXinMessageList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<WeixinMessage> list = dao.findWeiXinMessageList(params);
		return new PageInfo<WeixinMessage>(list);
	}

	@Override
	public void pushMsg(String message) {
		String[] strings = message.split("_");
		Map<String,Object> paramPush=new HashMap<String,Object>();//app传递消息
		Map<String,Object> paramWX=new HashMap<String,Object>();//微信
		
		String note = "";
		if(strings.length>0 && strings.length==4) {
			//message = 用户id+type+content
			String uid = strings[0];
			String type = strings[1];
			String content = strings[2];
			String way = strings[3];
			paramPush.put("userId", uid);
			paramWX.put("userId", uid);
			if("userBind".equals(type)) {//用户绑定
				paramPush.put("title", "公司发放福利");
				paramWX.put("type", "balace");
				if(!StringUtils.isEmpty(content)) {//内容非空
					if(content.indexOf("ticket")>0) {//内容含券
						String ticket = content.substring(0,content.indexOf("ticket"));
						note="公司向您发放内购券"+ticket+"元！您可以登陆 我的福利商城购买内购产品，或分享给亲友，享受大牌员工福利！";
						paramWX.put("cId", "1");
						paramWX.put("amount", ticket);
					}
					paramPush.put("msg", note);
					paramWX.put("note", note);
				}
			}
			paramWX.put("date", TimeUtil.getStringDateShort());
			if(StringUtils.isEmpty(way) ||"all".equals(way)) {
				//app 推送
				HttpClientUtil.sendHttpRequest("post", apiUrl+"user/pushMsg", paramPush);
				//微信推送
				HttpClientUtil.sendHttpRequest("post", apiUrl + "wx/templateMsgSend", paramWX);
			}else if("wx".equals(way)) {
				//微信推送
				HttpClientUtil.sendHttpRequest("post", apiUrl + "wx/templateMsgSend", paramWX);
			}else if("app".equals(way)) {
				//app 推送
				HttpClientUtil.sendHttpRequest("post", apiUrl+"user/pushMsg", paramPush);
			}
			
		}
	}
}
