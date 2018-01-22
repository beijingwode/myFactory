package com.wode.tongji.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.WeixinMessage;
import com.wode.tongji.service.WeixinMessageService;

@Controller
@RequestMapping("/wxMessage")
public class WxMessageController {
	@Autowired
	private WeixinMessageService weixinMessageService;
	
	@Autowired
	private RedisUtil redisUtil;
	/**
	 * 公众号关注消息
	 * @param model
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping("base")
	public String towxMessageBase(Model model,HttpSession session) {
		return "cms/wxMessage/base";
	}
	
	/**
	 * 公众号关注消息列表
	 * @param model
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String wxMessageList(@RequestParam Map<String, Object> params, Model model,HttpSession session) {				
		PageInfo<WeixinMessage> page = weixinMessageService.findWeiXinMessageList(params);
		List<WeixinMessage> wxMessageList = page.getList();
		if(wxMessageList!=null && !wxMessageList.isEmpty()) {
			for (WeixinMessage weixinMessage : wxMessageList) {
				setWxMessageStatus(weixinMessage);
			}
		}
		
		model.addAttribute("page", page);
		return "cms/wxMessage/list";
	}

	private void setWxMessageStatus(WeixinMessage weixinMessage) {
		String data = redisUtil.getMapData(RedisConstant.WX_OPEN_MESSAGE_MAP, (weixinMessage.getEventType()+weixinMessage.getMsgType()));
		if(data== null) {//缓存没有
			 if("0".equals(weixinMessage.getStopFlg())){
				 weixinMessage.setStatus(0);//未使用
			 }else if("1".equals(weixinMessage.getStopFlg())){
				 weixinMessage.setStatus(-1);//手动停用
			 }
		}else {
			weixinMessage.setStatus(1);//使用中
		}
	}
	
	/**
	 * 公众号关注消息编辑
	 * @param model
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String wxMessageEdit( Model model,HttpSession session,Long id) {		
		//Map<String,String> map = redisUtil.getMap(RedisConstant.WX_OPEN_MESSAGE_MAP);
		WeixinMessage weixinMessage = weixinMessageService.getById(id);
		if(weixinMessage!=null) {
			setWxMessageStatus(weixinMessage);
			model.addAttribute("wxMessage", weixinMessage);
			return "cms/wxMessage/edit";
		}else {
			return "-1";
		}
	}
	
	/**
	 * 模板保存
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<String> wxMessageSave(WeixinMessage weixinMessage, HttpServletRequest request,String wxM_limitEnd,String wxM_limitStart) {
		if(weixinMessage.getId()== null)return ActResult.fail("缺少参数");
		
		if(StringUtils.isNullOrEmpty(wxM_limitStart) || StringUtils.isNullOrEmpty(wxM_limitEnd))return ActResult.fail("缺少参数");
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); 
		try {
			weixinMessage.setLimitStart(format1.parse(wxM_limitStart));
			weixinMessage.setLimitEnd(format1.parse(wxM_limitEnd));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		weixinMessage.setUpdateTime(new Date());
		weixinMessageService.update(weixinMessage);
		
		if(1==weixinMessage.getStatus()) {//使用中
			if("2".equals(weixinMessage.getMsgType())) {//临时消息
				if (new Date().before(weixinMessage.getLimitEnd())) {//在有效期
					setWxMessageRedis(weixinMessage);
				}
			}else {
				setWxMessageRedis(weixinMessage);
			}
		}
		return ActResult.success("");
	}

	private void setWxMessageRedis(WeixinMessage weixinMessage) {
		redisUtil.delMapData(RedisConstant.WX_OPEN_MESSAGE_MAP, (weixinMessage.getEventType()+weixinMessage.getMsgType()));
		redisUtil.setMapData(RedisConstant.WX_OPEN_MESSAGE_MAP, (weixinMessage.getEventType()+weixinMessage.getMsgType()),JsonUtil.toJsonString(weixinMessage));
		redisUtil.setTime(RedisConstant.WX_OPEN_MESSAGE_MAP,3600*24+10);
	}
	
	/**
	 * 启动
	* @param id 任务id
	* @return
	 */
	@RequestMapping(value="start",method = RequestMethod.POST)
	public @ResponseBody ActResult start(Long id){
		WeixinMessage weixinMessage = weixinMessageService.getById(id);
		if(weixinMessage!=null) {
			if("2".equals(weixinMessage.getMsgType()) && new Date().after(weixinMessage.getLimitEnd())) {//临时消息 且未在有效期
				return ActResult.fail("消息错误，启动失败");
			}
			setWxMessageRedis(weixinMessage);
			return ActResult.success(null);
		}else {
			 return ActResult.fail("消息错误，启动失败");
		}
	}
	
	/**
	 * 停止
	* @param id 任务id
	* @return
	 */
	@RequestMapping(value="stop",method = RequestMethod.POST)
	public @ResponseBody ActResult stop(Long id){
		WeixinMessage weixinMessage = weixinMessageService.getById(id);
		if(weixinMessage!=null) {
			redisUtil.delMapData(RedisConstant.WX_OPEN_MESSAGE_MAP, (weixinMessage.getEventType()+weixinMessage.getMsgType()));
			weixinMessage.setStopFlg("1");
			weixinMessageService.update(weixinMessage);
			return ActResult.success(null);
		}else {
			return ActResult.fail("消息错误，停止失败");
		}
	}
}
