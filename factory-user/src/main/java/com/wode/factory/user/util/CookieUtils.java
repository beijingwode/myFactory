package com.wode.factory.user.util;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wode.common.util.StringUtils;

/**   
 * 类名称：CookieUtils   
 * 类描述：   cookie操作通用类
 * 创建人：gaoyj
 * 创建时间：2015-3-16 下午03:27:39   
 * 修改人：gaoyj
 * 修改时间：2015-3-16 下午03:27:39   
 * 修改备注：   
 * @version       
 */ 
public class CookieUtils {

	/**  
	 * @Title: get  
	 * @Description: TODO 根据参数key获取cookie的对应的值
	 * @param @param request
	 * @param @param key
	 * @param @return 
	 * @return String 
	 * @throws  
	 */  
	public static String get(HttpServletRequest request , String key){
		
		//获取cookie信息
		Cookie[] cookies = request.getCookies();
		String value="";
		if(cookies!=null) {
		for (int i = 0; i < cookies.length; i++) {
			if (key.equals(cookies[i].getName())) {
				value = cookies[i].getValue();
				break;
			}
		}
		}
		return value ;
	}
	
	/**  
	 * @Title: set  
	 * @Description: TODO 像cookie中插入
	 * @param @param response
	 * @param @param key
	 * @param @param value
	 * @param @param time 
	 * @return void 
	 * @throws  
	 */  
	public static void set(HttpServletResponse response,String key , String value ,Integer time){
		
		Cookie cookie_uid = new Cookie(key,value);
		cookie_uid.setPath("/");
		if(!StringUtils.isEmpty(time)){
			cookie_uid.setMaxAge(time);
		}
		response.addCookie(cookie_uid);
	}
	
	/**  
	 * @Title: setUUID  
	 * @Description: TODO  cookie中存入 uuid
	 * @param @param response
	 * @param @param key  
	 * @return void 
	 * @throws  
	 */  
	public static void setUUID(HttpServletResponse response,String key ){
		Cookie cookie_uid = new Cookie(key,UUID.randomUUID().toString());
		cookie_uid.setPath("/");
		response.addCookie(cookie_uid);

	}
	
	/**  
	 * @Title: getUUID  
	 * @Description: TODO 获取cookie中UUID标识,如果没有自动添加uuid
	 * @param @param request
	 * @param @return 
	 * @return String 
	 * @throws  
	 */  
	public static String getUUID(HttpServletRequest request ,HttpServletResponse response){
		
		//获取cookie信息
		Cookie[] cookies = request.getCookies();
		String uuid="";

		String key ="uuid";
		
		if(!StringUtils.isEmpty(cookies))
		{
			for (int i = 0; i < cookies.length; i++) {
				if (key.equals(cookies[i].getName())) {
					uuid = cookies[i].getValue();
					break;
				}
			}
		}
		if(StringUtils.isEmpty(uuid)){
			uuid = UUID.randomUUID().toString() ;
			Cookie cookie_uid = new Cookie(key,uuid);
			cookie_uid.setPath("/");
			cookie_uid.setMaxAge(365 * (24 * 60 * 60 * 1000));
			response.addCookie(cookie_uid);
		}
		return uuid ;
	}
	
	/**  
	 * @Title: getTicket  
	 * @Description: TODO 获取ticket串
	 * @param @param request
	 * @param @return 
	 * @return String 
	 * @throws  
	 */  
	public static String getTicket(HttpServletRequest request){
		
		//获取cookie信息
		Cookie[] cookies = request.getCookies();
		String user_ticket="";
		if(cookies!=null) {
			for (int i = 0; i < cookies.length; i++) {
				if ("user_ticket".equals(cookies[i].getName())) {
					user_ticket = cookies[i].getValue();
					break;
				}
			}
		}
		return user_ticket ;
	}

}
