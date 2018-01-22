package com.wode.factory.company.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.util.ActResult;
import com.wode.common.util.JsonUtil;

public class AuthInterceptor implements HandlerInterceptor{
	
	private static Set<String> allowIPs=new HashSet();
	
	private static Set<String> allowNet=new HashSet();
	
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	/**
	 * 控制器执行完，生成视图之前可以执行
	 */
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}

	/**
	 * Action处理之前执行  
	 */
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		String ip=getIpAddr(arg0);
		boolean flag=allowIPs.contains(ip);
		if(!flag){
			for(String net:allowNet){
				Pattern netpattern=java.util.regex.Pattern.compile(net);
				java.util.regex.Matcher match=netpattern.matcher(ip);
				if(match.matches()){
					flag=true;
					break;
				}
			}
		}
		
		if(!flag){
			ActResult ret=new ActResult();
			ret.setSuccess(false);
			ret.setMsg("非法的来源:"+ip);
			String json=JsonUtil.toJson(ret);
			writeCustomJSON(json,arg1);
			return false;
		}
		return true;
	}

	
	
	/**
	 * 输出json
	 * @param data
	 * json字符
	 * @param response
	 */
	protected void  writeCustomJSON(String data,HttpServletResponse response){
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(data);
		    pw.flush();
		    pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	
	
	/**
	 * 功能：获取客户端IP地址
	 * @param request
	 * @return
	 */
	public  String getIpAddr(HttpServletRequest request) {
		
	       String ip = request.getHeader("x-forwarded-for");
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	       }
	       if(ip.contains(",")) {
	    	   ip=ip.substring(0, ip.indexOf(",")).trim();
	       }
	       return ip;
	  }

	
	public static void setAllowIPs(Set<String> allowIPs) {
		AuthInterceptor.allowIPs = allowIPs;
	}

	public static void setAllowNet(Set<String> allowNet) {
		AuthInterceptor.allowNet = allowNet;
	}
}
