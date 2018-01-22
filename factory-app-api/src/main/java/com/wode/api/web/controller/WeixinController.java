package com.wode.api.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserWeixin;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.WxOpenService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.service.UserWeixinService;
import com.wode.factory.user.util.Constant;

@Controller
@RequestMapping("/wx")
@SuppressWarnings("unchecked")
public class WeixinController extends BaseController {    

	@Autowired
	private UserWeixinService userWeixinService;
	@Autowired
	private UserService userService;

	@Qualifier("redis")
    @Autowired
    private RedisUtil redisUtil;
	private static Logger logger = LoggerFactory.getLogger(WeixinController.class);

	static WxOpenService wxOpen = ServiceFactory.getWxOpenService(Constant.OUTSIDE_SERVICE_URL);
	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
    /**
     * 微信计入认证
     * @param request
     * @param signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp	时间戳
     * @param nonce		随机数
     * @param echostr	随机字符串
     * @return
     */
	@RequestMapping(method = RequestMethod.GET)
    public void enter(HttpServletRequest request, PrintWriter out, String signature, String timestamp,String nonce,String echostr) {

		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
        if (wxOpen.checkSignature(signature, timestamp, nonce)) {  
    		out.print(echostr);
        } else {  
            System.out.println("不是微信服务器发来的请求,请小心!");  
    		out.print("");
        }  
    }
	   
    /**
     * 微信计入认证
     * @param request
     * @param signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp	时间戳
     * @param nonce		随机数
     * @param echostr	随机字符串
     * @return
     * @throws Exception 
     */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
    public void returnMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        request.setCharacterEncoding("UTF-8");  //微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
        response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
        List<String> subscribes = new ArrayList<String>();
        //初始化配置文件

		// xml请求解析
		// 调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；
		Map<String, String> requestMap = com.wode.api.util.Constant.parseXml(request);
        String respMessage = wxOpen.processRequest(JSON.toJSONString(requestMap), subscribes);//调用CoreService类的processRequest方法接收、处理消息，并得到处理结果；
        
        // 响应消息  
        //调用response.getWriter().write()方法将消息的处理结果返回给用户
        response.getWriter().print(respMessage);
        
        if(!subscribes.isEmpty()) {
        	UserWeixin user = userWeixinService.getOneModelByOpenId(subscribes.get(0));
			if(user!=null) {
				this.setTag(user, WxOpenService.TAG_ID_SHOP);

				// 关注后无法立即推送消息
//		        Map<String,Object> paramPush=new HashMap<String,Object>();
//				paramPush.put("amount", 500);
//				paramPush.put("date", TimeUtil.getStringDateShort());
//				paramPush.put("note", "公司向您发放内购券500元！您可以登陆 我的福利商城购买内购产品，或分享给亲友，享受大牌员工福利！");
//				paramPush.put("cId", 1);
//		        paramPush.put("type", "balace");
//				paramPush.put("userId", user.getUserId());
//				HttpClientUtil.sendHttpRequest("post", "http://"+Constant.SYSTEM_DOMAIN+"/wx/templateMsgSend", paramPush);
			}
        }
    }

	@RequestMapping("/updateMemu")
	@ResponseBody
    public String updateMemu(HttpServletRequest request, HttpServletResponse response,String type) throws IOException {
		return wxOpen.updateMemu(type);
    }


	@RequestMapping("/templateMsgSend")
	@ResponseBody
    public String templateMsgSend(HttpServletRequest request, HttpServletResponse response,String type,Long userId) throws IOException {
		if(userId==null) return "-1";
		
		UserWeixin user = userWeixinService.getOneModelByUserId(userId);
		if(user != null) {
			Map<String,Object> param = new HashMap<String,Object>();
			Enumeration<String> enu = request.getParameterNames();
	        while(enu.hasMoreElements()){
	            String paraName = enu.nextElement();
	            if (!"_dc".equals(paraName) && !"node".equals(paraName) && !"type".equals(paraName) && !"userId".equals(paraName)){//_dc的参数,isAsync不要
	            	param.put(paraName, request.getParameter(paraName));
	            }
	        }
	        
	        ActResult<String> act = wxOpen.templateMsgSend(user.getOpenId(), type, userId, param, true, null);
			if(act.isSuccess()) {
				return act.getData();
			} else {
				return "-1";
			}
		} else {
			return "";
		}
    }

	@RequestMapping("/wxConfig")
	@ResponseBody
    public ActResult<JSONObject> wxConfig(HttpServletRequest request, HttpServletResponse response,String url) throws IOException {

		try {
			JSONObject data = wxOpen.wxConfig(url);
			return ActResult.success(data);
		} catch (Exception e) {
			return ActResult.fail("处理失败，请稍后尝试");
		}
		
    }

	@RequestMapping("/jsonpWxConfig")
	public void jsonpWxConfig(HttpServletRequest request, HttpServletResponse response,String url) throws Exception  {
		JSONObject data = wxOpen.wxConfig(url);
		response.getWriter().write("var jsonpWxConfig="+data.toJSONString()+";");
	}
	
	@RequestMapping("/bridge")
    public ModelAndView bridge(String code,String state,ModelAndView model,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String rtn =wxOpen.getOpenId(code);
		JSONObject json = JSONObject.parseObject(rtn);
		String openId = json.getString("openid");
		// 绑定动作改在各绑定功能中处理，绑定入口都从获取openId开始
		UserWeixin user = userWeixinService.getOneModelByOpenId(openId);

		model.addObject("openId", openId);
		model.addObject("state", state);
		if(user==null) {
			// 未绑定时跳转至绑定页面
			model.setViewName("registOrLogin");
			return model;
		} else {
			// 已经绑定的场合跳转至中间页面，根据条件继续跳转
			Cookie cookie_uid = new Cookie("uid",user.getUserId()+"");
			cookie_uid.setPath("/");
			cookie_uid.setMaxAge(24 * 60 * 60 * 1000);
			response.addCookie(cookie_uid);

			Cookie cookie_wxOpen = new Cookie("wxOpen","1");
			cookie_wxOpen.setPath("/");
			cookie_wxOpen.setMaxAge(24 * 60 * 60 * 1000);
			response.addCookie(cookie_wxOpen);
			
			UserFactory userFactory = userService.getById(user==null?loginUser.getId():user.getUserId());
			if(userFactory != null) {
				// 防止意外
				Cookie cookie_userSupplierId = new Cookie("userSupplierId",userFactory.getSupplierId()+"");
				cookie_userSupplierId.setPath("/");
				cookie_userSupplierId.setMaxAge(24 * 60 * 60 * 1000);
				response.addCookie(cookie_userSupplierId);
			}
			model.setViewName("bridge");
			return model;
		}		
	}
	
	@RequestMapping("/hasBind")
    public ModelAndView hasBind(String code,String state,ModelAndView model,HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.debug(code);
		String rtn =wxOpen.getOpenId(code);
		JSONObject json = JSONObject.parseObject(rtn);
		String openId = json.getString("openid");
		logger.debug(openId);
		// 绑定动作改在各绑定功能中处理，绑定入口都从获取openId开始
		UserWeixin user = userWeixinService.getOneModelByOpenId(openId);

		model.addObject("openId", openId);
		model.addObject("state", state);
		if(user != null) {
			// 已经绑定的场合跳转至中间页面，根据条件继续跳转
			Cookie cookie_uid = new Cookie("uid",user.getUserId()+"");
			cookie_uid.setPath("/");
			cookie_uid.setMaxAge(24 * 60 * 60 * 1000);
			response.addCookie(cookie_uid);

			Cookie cookie_wxOpen = new Cookie("wxOpen","1");
			cookie_wxOpen.setPath("/");
			cookie_wxOpen.setMaxAge(24 * 60 * 60 * 1000);
			response.addCookie(cookie_wxOpen);
			
			UserFactory userFactory = userService.getById(user==null?loginUser.getId():user.getUserId());
			if(userFactory != null) {
				// 防止意外
				Cookie cookie_userSupplierId = new Cookie("userSupplierId",userFactory.getSupplierId()+"");
				cookie_userSupplierId.setPath("/");
				cookie_userSupplierId.setMaxAge(24 * 60 * 60 * 1000);
				response.addCookie(cookie_userSupplierId);
			}
		}
		String targetJsVesion = redisUtil.getData(RedisConstant.USER_SHARE_TARGET_JS_VERSION);
		if(targetJsVesion==null) targetJsVesion="";
		model.addObject("targetJsVesion", targetJsVesion);
		model.setViewName("hasBind");
		return model;
	}
	
	//
	@RequestMapping("/getOpenId")
    public String getOpenId(HttpServletRequest request, HttpServletResponse response,String code,String state) throws IOException {

		String rtn =wxOpen.getOpenId(code);
		JSONObject json = JSONObject.parseObject(rtn);
		String openId = json.getString("openid");
		String toUrl=getToUrl(state,openId);
		// 绑定动作改在各绑定功能中处理，绑定入口都从获取openId开始
		UserWeixin user = userWeixinService.getOneModelByOpenId(openId);
				
		if(user==null) {
			if(!"register".equals(state)){
				// 分享以外
				return "redirect:/user/toLogin?exp1="+openId+"&toUrl="+toUrl+"&type=W&msg="+URLEncoder.encode("绑定我的福利账号，享个性化服务","UTF-8"); 
			} 
		} else {

			Cookie cookie_uid = new Cookie("uid",user.getUserId()+"");
			cookie_uid.setPath("/");
			cookie_uid.setMaxAge(24 * 60 * 60 * 1000);
			response.addCookie(cookie_uid);

			Cookie cookie_wxOpen = new Cookie("wxOpen","1");
			cookie_wxOpen.setPath("/");
			cookie_wxOpen.setMaxAge(24 * 60 * 60 * 1000);
			response.addCookie(cookie_wxOpen);
			
			UserFactory userFactory = userService.getById(user==null?loginUser.getId():user.getUserId());
			if(userFactory != null) {
				// 防止意外
				Cookie cookie_userSupplierId = new Cookie("userSupplierId",userFactory.getSupplierId()+"");
				cookie_userSupplierId.setPath("/");
				cookie_userSupplierId.setMaxAge(24 * 60 * 60 * 1000);
				response.addCookie(cookie_userSupplierId);
			}

			// 已经绑定过直接跳转到活动页面
			if("register".equals(state)){
				// 注册时 已绑定用户，直接跳转到个人中心
				return "/user/page";//个人中心
			}
			
		}
		return "redirect:"+toUrl; 
    }
	
	/**
	 * 判断是否收藏当前店铺
	 * guziye
	 */
	@RequestMapping(value="checkSubscribe.user")
	@ResponseBody
	public ActResult<String> checkSubscribe(HttpServletRequest request, HttpServletResponse response){

		UserWeixin user = userWeixinService.getOneModelByUserId(loginUser.getId());
		if (user == null) {
			removeCookieUid(response);
			return ActResult.fail("获取不到关注信息");
		} else {
			return ActResult.success(user.getOpenId());
		}
	}


	@RequestMapping("/jsonpCheckSubscribe.user")
	public void jsonpCheckSubscribe(HttpServletRequest request, HttpServletResponse response) throws Exception  {
		UserWeixin user = userWeixinService.getOneModelByUserId(loginUser.getId());
		if (user == null) {
			removeCookieUid(response);
			response.getWriter().write("var jsonpCheckSubscribe='err';");
		} else {
			response.getWriter().write("var jsonpCheckSubscribe='ok';");
		}
	}

	@RequestMapping("/bindUser.user")
	@ResponseBody
    public ActResult<UserWeixin> bindUser(HttpServletRequest request, HttpServletResponse response,String openId) throws IOException {
		return doBind(response, openId,loginUser); 
    }

	private ActResult<UserWeixin> doBind(HttpServletResponse response, String openId,UserFactory login) {
		if(StringUtils.isEmpty(openId)) {
			return ActResult.fail("参数错误，绑定失败");
		}
		
		UserWeixin user = userWeixinService.getOneModelByOpenId(openId);
		if(user==null) {
			user = new UserWeixin();
			user.setAppId(WxOpenService.APP_ID);
			user.setOpenId(openId);
			user.setUserId(login.getId());
			userWeixinService.save(user);
			
			this.setTag(user, WxOpenService.TAG_ID_SHOP);
		}
		
		Cookie cookie_uid = new Cookie("uid",user.getUserId()+"");
		cookie_uid.setPath("/");
		cookie_uid.setMaxAge(24 * 60 * 60 * 1000);
		response.addCookie(cookie_uid);
		
		Cookie cookie_wxOpen = new Cookie("wxOpen","1");
		cookie_wxOpen.setPath("/");
		cookie_wxOpen.setMaxAge(24 * 60 * 60 * 1000);
		response.addCookie(cookie_wxOpen);
		
		if(null == login) {
			System.out.println("loginUser null:" + openId);
		} else {
			System.out.println(login);
		}
		
		if(login!=null) {
			UserFactory userFactory = userService.getById(login.getId());
			Cookie cookie_userSupplierId = new Cookie("userSupplierId",userFactory.getSupplierId()+"");
			cookie_userSupplierId.setPath("/");
			cookie_userSupplierId.setMaxAge(24 * 60 * 60 * 1000);
			response.addCookie(cookie_userSupplierId);			
		}
		return ActResult.success(user);
	}

	
	@RequestMapping("/bindAccount.user")
    public String bindAccount(HttpServletRequest request, HttpServletResponse response,String ticket,String exp1,String toUrl,String type) throws IOException {
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName();

		if(request.getServerPort() != 80 && request.getServerPort() != 443) {
			basePath+=":"+request.getServerPort()+path+"/";
		} else {
			basePath+=path+"/";
		}
		
		if(!StringUtils.isEmpty(toUrl)) {
			toUrl = toUrl.replace("____", "&").replace("****", "=");
		} else {
			toUrl = basePath+"index_m.htm";
		}
		
		//+":"+request.getServerPort()+path+"/";
			
		if(StringUtils.isEmpty(exp1)) {
			String rtn =URLEncoder.encode(basePath + "wx/getOpenId","utf-8");
			return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Constant.WX_OPEN_APPID+ "&redirect_uri="+rtn+"&response_type=code&scope=snsapi_base&state=3#wechat_redirect"+toUrl; 
		}
		this.doBind(response, exp1, loginUser);
		Long uid=loginUser.getId();
		if(toUrl.contains("openPage") || toUrl.contains("autoBuy")|| toUrl.contains("autoTicket")|| toUrl.contains("limitTicket")) {
			if(!toUrl.contains("uid=")) {
				if(!toUrl.contains("?")) {
					toUrl = toUrl+"?uid=" +uid;
				} else {
					toUrl = toUrl+"&uid=" +uid;
				}
			}
		}
		
		return "redirect:"+toUrl; 
    }

	private void setTag(UserWeixin user,String tagId){
		UserFactory uf = userService.getById(user.getUserId());
		if(uf!=null && uf.getType()!=null && uf.getType()==2){
			UserWeixin q = new UserWeixin();
			q.setUserId(user.getUserId());
			q.setLogout(0);
			
			List<UserWeixin> ls = userWeixinService.selectByModel(q);
			
			String openIds="";
			for (UserWeixin userWeixin : ls) {
				openIds += ","+userWeixin.getOpenId();
			}
			
			if(openIds.length()>0) {				
				wxOpen.setTag(openIds.substring(1), tagId, false, null);
			}
		}
	}

	@RequestMapping("apiUid")
	public void apiUid(HttpServletRequest request,HttpServletResponse response,@CookieValue(value="uid",required=false) String uid) throws Exception  {

		if(!StringUtils.isEmpty(uid)){
			response.getWriter().write("var api_uid='"+uid+"';");
		}	
	}

	@RequestMapping("apiOpenId")
	public void apiOpenId(HttpServletRequest request,HttpServletResponse response,@CookieValue(value="uid",required=false) String uid) throws Exception  {

		if(!StringUtils.isEmpty(uid)){
			UserWeixin user = userWeixinService.getOneModelByUserId(Long.valueOf(uid));
			if (user == null) {
				removeCookieUid(response);
			} else {
				response.getWriter().write("var api_openid='"+user.getOpenId()+"';");
			}
		}	
	}
	
	private String getToUrl(String state,String openId){
		if("1".equals(state)) {
			return  "/cart/page";//购物车
		} else if("3".equals(state)){
			return "/user/page";//个人中心
		} else if("4".equals(state)){
			return "/order/page?status=0";	//订单
		} else if("5".equals(state)){
		    return "/order/page?status=4";	//订单
		} else if("2".equals(state)){
			return "/pSearch/page";			//搜索
		} else if("0".equals(state)){
			return "/index_m.htm";			//首页
		} else if("register".equals(state)){
			return "/user/register?openId="+openId;				//注册
		} else if("shop7".equals(state)){
			return Constant.QIYE_API_URL + "app/suborder/page";	//店铺订单
		} else if("shop8".equals(state)){
			return Constant.QIYE_API_URL + "app/product/page";	//店铺商品
		} else if("shop9".equals(state)){
			return Constant.QIYE_API_URL + "app/shop/page";		//店铺中心
		}  else {
			return "/index_m.htm";											// 首页
		}
	}
	
	@RequestMapping("/logout.user")
	@ResponseBody
    public ActResult<String> logout(HttpServletRequest request, HttpServletResponse response,String uid) throws IOException {
		clearCookieAndBind(response, uid);
		return ActResult.success(true); 
    }
	
	@RequestMapping("/jsonpLogOut.user")
	public void jsonpLogOut(HttpServletRequest request,HttpServletResponse response,String uid) throws Exception  {
		clearCookieAndBind(response, uid);
		response.getWriter().write("var logout='ok';");
	}

	private void clearCookieAndBind(HttpServletResponse response, String uid) {
		removeCookieUid(response);
		removeCookieSupplierId(response);
		//查询当前用户的绑定信息
		UserWeixin user = userWeixinService.getOneModelByUserId(Long.valueOf(uid));
		if (user!=null) {
			user.setLogout(1);
			userWeixinService.saveOrUpdate(user);
		}
	}

	private void removeCookieUid(HttpServletResponse response) {
		Cookie cookie_uid = new Cookie("uid","");
		cookie_uid.setPath("/");
		cookie_uid.setMaxAge(0);
		response.addCookie(cookie_uid);
	}
	
	private void removeCookieSupplierId(HttpServletResponse response) {
		Cookie cookie_userSupplierId = new Cookie("userSupplierId","");
		cookie_userSupplierId.setPath("/");
		cookie_userSupplierId.setMaxAge(0);
		response.addCookie(cookie_userSupplierId);
	}
	
	public static void main(String[] args) {
		
	}
	/**
	 * 判断是否收藏当前店铺
	 * guziye
	 */
	@RequestMapping(value="checkUser.user")
	@ResponseBody
	public ActResult<String> checkUser(HttpServletRequest request, HttpServletResponse response,String openId){
		try {
			ActResult<JSONObject> act = wxOpen.getUserBaseInfo(openId);
			if(act.isSuccess()) {
				JSONObject userBaseInfo = act.getData();
				String subscribe = userBaseInfo.getString("subscribe");
				return ActResult.success(subscribe);
			}else {
				return ActResult.success("0");
			}
		} catch (Exception e) {
			return ActResult.fail("处理失败，请稍后尝试");
		}
	}
}
