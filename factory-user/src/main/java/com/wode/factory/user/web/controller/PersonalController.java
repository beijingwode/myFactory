package com.wode.factory.user.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.constant.UserConstant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.MailService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.UserEmail;
import com.wode.model.CommUser;

/**
 * User: zhengxiongwu
 */
@Controller
@RequestMapping("/personal")
public class PersonalController extends BaseController{
	
	
	@Qualifier("redis")
	@Autowired
	private RedisUtil redisUtil;
	
	@Qualifier("userService")
	@Autowired
	private UserService userService;

	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
	static MailService mail = ServiceFactory.getMailService(Constant.OUTSIDE_SERVICE_URL);
//	@RequestMapping("sendActivationEmail")
//	public void sendActivationEmail(String email){
//		String url = "http://localhost:8080/user/personal/bindingMail?userId="+100000+"&uuid="+UUID.randomUUID();
//		mail.sendSenderSimpleMessageMail("zhxw4851@sina.cn", "注册认证", "这是一封注册认证邮件，请点击以下链接确认： "+url);
//	}
	/**
	 * 用户通过邮箱修改密码时，发送验证邮件
	 * @param request
	 * @return
	 */
	@RequestMapping("emailPostfixs")
	@ResponseBody
	public ActResult<List<String>> emailPostfixs(HttpServletRequest request, HttpServletResponse response){
		return ActResult.success(userService.emailPostfixs());
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param userName
	 * @param password
	 * @param from
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("emailPostfixsJsonp")
	public void emailPostfixsJsonp(HttpServletRequest request,HttpServletResponse reponse,String jsonpcallback) throws Exception  {
		writeCustomJSON(jsonpcallback + "("+JsonUtil.toJson(userService.emailPostfixs())+")",reponse);
	}
	
	/**
	 * 用户通过邮箱注册时，发送验证邮件
	 * @param request
	 * @return
	 */
	@RequestMapping("sendRegisterEmail")
	@ResponseBody
	public ActResult<String> sendEmailForRegister(HttpServletRequest request,Long userId,String type,String email){
		ActResult<String> re = new ActResult<String>();
		UserFactory user = userService.getById(userId);
		if(user==null){
			ActResult<CommUser> ar= us.getUserById(userId);
			if(ar.isSuccess()) {
				UserFactory uf = new UserFactory();
				CommUser u = ar.getData();
				uf.setId(u.getUserId());
				uf.setUserName(u.getUserName());
				uf.setEmail(u.getUserEmail());
				uf.setEnabled(u.getEnabled());
				uf.setUsable(u.getUsable());
				uf.setCreatTime(new Date());
				uf.setNickName(u.getNickName());
				uf.setEnabled(0);
				uf.setType(1);
				uf.setPhone(u.getUserPhone());
				userService.specialSave(uf);
				user =uf;
			}
		}
		
		if(user==null){
			re.setMsg("用户不存在");
			re.setSuccess(false);
		}else if(user.getEnabled()==1 && StringUtils.isEquals(type, "register")){
			re.setSuccess(false);
			re.setMsg("用户已激活");
		}else{
			ActResult<CommUser> ar = us.findByEmail(email);
			if(ar.isSuccess()&&!StringUtils.isEquals(type, "enabled")){
				re.setMsg("该邮箱已被其他用户绑定");
				re.setSuccess(false);
			}else{
				re = UserEmail.sendEmail(userId, type, email, user, redisUtil);
			}
		}
		return re;
	}
	
	
	/**
	 * 用户通过邮箱修改密码时，发送验证邮件
	 * @param request
	 * @return
	 */
	@RequestMapping("sendSecurityEmail")
	@ResponseBody
	public ActResult<String> sendEmailForPassword(HttpServletRequest request, HttpServletResponse response){
		UserFactory user = getUser(request,response);
		ActResult<String> re = new ActResult<String>();
		if(user==null){
			re.setSuccess(false);
			re.setMsg("该用户不存在");
		}else{
//			if(user.getType()==1){
				UUID code = UUID.randomUUID();
				Map<String,String> map = new HashMap<String, String>();
				map.put("userId", user.getId().toString());
				map.put("code", code.toString());
				redisUtil.setData(UserConstant.FINDPWDBYEMAILFUNCTION+"_"+user.getId(), map,60 * 60 * 24);//有效期一天
				StringBuilder text = new StringBuilder();
				text.append("<body>");
				text.append("<div style='width:100%; height:90px; margin:0 auto; border-bottom:2px solid #eee;'>");
				text.append("<div style='width:690px; margin:0 auto; padding-top:30px; height:60px; overflow:hidden;'>");
				text.append("<div style='width:116px; height:50px; float:left;'><a href='http://www.wd-w.com/index.html'><img src='http://www.wd-w.com/images/logo.png' width='116' height='50'></a></div>");
				text.append("<div style='float:right; padding-top:10px;'>");
				text.append("<a style='font:14px Microsoft YaHei; color:#828282; text-decoration:none;' href='javascript:void(0);'>"
						+ "<i style='width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(images/emailicon.png) no-repeat 0 0;'></i>我的网</a>"
						+ "<em style='font:14px Microsoft YaHei; color:#828282; margin:0 10px;'>|</em>");
				text.append("<a style='font:14px Microsoft YaHei; color:#828282; text-decoration:none;' href='javascript:void(0);'>"
						+ "<i style='width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(images/emailicon.png) no-repeat 0 -26px;'></i>帮助中心</a></div></div></div>");
				text.append("<div style='width:100%; margin:0 auto; text-align:center;'>");
				text.append("<h2 style='font:18px/36px Microsoft YaHei; color:#434343; margin-top:50px;'>亲爱的我的网用户，您好：</h2>");
				text.append("<h3 style='font:20px/36px Microsoft YaHei; color:#8fc31f;'>您正在修改我的网账号密码，请确认，此链接24小时内有效，请及时验证。</h3>");
				text.append("<p style='width:300px; height:50px; margin:0 auto; text-align:center; background:#ff6161; margin-top:30px; border-radius:4px; behavior:url(iecss3/PIE.htc); position:relative; z-index:2;'>"
						+ "<a style='display:block; font:18px/50px Microsoft YaHei; color:#fff; text-decoration:none;' href='http://www.wd-w.com/user/checkByEmail?userId="+user.getId()+"&code="+code.toString()+"'>请点击这里完成确认</a></p>");
				text.append("<p style='margin-top:50px; font:14px Microsoft YaHei; color:#434343;'>如果您不能点击上面的链接，您可以将下面的链接复制到浏览器进行访问：</p>");
				text.append("<p style='margin:5px 0 16px;'>"
						+ "<a style='font:14px Microsoft YaHei; color:#2b8dff; text-decoration:none;' href='javascript:void(0);'>"
						+ "http://www.wd-w.com/user/checkByEmail?userId="+user.getId()+"&code="+code.toString()+"</a></p>");
				text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>为了保证您的账号安全，请在24小时内完成账号验证。</p>");
				text.append("<div style='width:100%; margin:24px auto 0; border-top:1px solid #eee;'></div>");
				text.append("<div style='width:690px; margin:0 auto; padding-top:20px; text-align:left;'>");
				text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>收到此邮件，说明您已是我的网尊贵的会员。</p>");
				text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>为确保您接受的服务信息不被当做垃圾邮件处理，请将service@wodechang.com添加为联系人。</p>");
				text.append("</div></div>");
				/*String text = "这是一封密码修改确认邮件，请点击以下链接确认：http://www.wd-w.com/user/checkByEmail?userId="
						+user.getId()+"&code="+code.toString();*/
				Boolean result = mail.sendMail(user.getEmail(), "密码修改确认",text.toString(),null,null,"myFactory",false,null);
				if(!result){
					re.setSuccess(false);
					re.setMsg("邮件发送失败，请检查您的注册邮箱是否正常使用");
				}else{
					re.setMsg("邮件已发送");
				}
//			}else{
//				re.setSuccess(false);
//				re.setMsg("非法用户");
//			}
		}
		return re;
	}
	
	/**
	 * 修改绑定邮箱
	 * @param request
	 * @return
	 */
	@RequestMapping("changeBindEmail")
	@ResponseBody
	public ActResult<String> bindingMail(HttpServletRequest request, HttpServletResponse response){
		UserFactory user = getUser(request,response);
		ActResult<String> re = new ActResult<String>();
		if(user==null){
			re.setSuccess(false);
			re.setMsg("该用户不存在");
		}else{
			UUID code = UUID.randomUUID();
			Map<String,String> map = new HashMap<String, String>();
			map.put("userId", user.getId().toString());
			map.put("code", code.toString());
			redisUtil.setData(UserConstant.BINDMAILFUNCTION+"_"+user.getId(), map,60 * 60 * 24);//有效期一天
			StringBuilder text = new StringBuilder();
			text.append("<body>");
			text.append("<div style='width:100%; height:90px; margin:0 auto; border-bottom:2px solid #eee;'>");
			text.append("<div style='width:690px; margin:0 auto; padding-top:30px; height:60px; overflow:hidden;'>");
			text.append("<div style='width:116px; height:50px; float:left;'><a href='http://www.wd-w.com/index.html'><img src='http://www.wd-w.com/images/logo.png' width='116' height='50'></a></div>");
			text.append("<div style='float:right; padding-top:10px;'>");
			text.append("<a style='font:14px Microsoft YaHei; color:#828282; text-decoration:none;' href='javascript:void(0);'>"
					+ "<i style='width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(images/emailicon.png) no-repeat 0 0;'></i>我的网</a>"
					+ "<em style='font:14px Microsoft YaHei; color:#828282; margin:0 10px;'>|</em>");
			text.append("<a style='font:14px Microsoft YaHei; color:#828282; text-decoration:none;' href='javascript:void(0);'>"
					+ "<i style='width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(images/emailicon.png) no-repeat 0 -26px;'></i>帮助中心</a></div></div></div>");
			text.append("<div style='width:100%; margin:0 auto; text-align:center;'>");
			text.append("<h2 style='font:18px/36px Microsoft YaHei; color:#434343; margin-top:50px;'>亲爱的我的网用户，您好：</h2>");
			text.append("<h3 style='font:20px/36px Microsoft YaHei; color:#8fc31f;'>您正在修改我的网绑定邮箱，请确认，此链接24小时内有效，请及时验证。</h3>");
			text.append("<p style='width:300px; height:50px; margin:0 auto; text-align:center; background:#ff6161; margin-top:30px; border-radius:4px; behavior:url(iecss3/PIE.htc); position:relative; z-index:2;'>"
					+ "<a style='display:block; font:18px/50px Microsoft YaHei; color:#fff; text-decoration:none;' href='http://www.wd-w.com/user/changeBindEmail?userId="+user.getId()+"&code="+code.toString()+"'>请点击这里完成确认</a></p>");
			text.append("<p style='margin-top:50px; font:14px Microsoft YaHei; color:#434343;'>如果您不能点击上面的链接，您可以将下面的链接复制到浏览器进行访问：</p>");
			text.append("<p style='margin:5px 0 16px;'>"
					+ "<a style='font:14px Microsoft YaHei; color:#2b8dff; text-decoration:none;' href='javascript:void(0);'>"
					+ "http://www.wd-w.com/user/changeBindEmail?userId="+user.getId()+"&code="+code.toString()+"</a></p>");
			text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>为了保证您的账号安全，请在24小时内完成账号验证。</p>");
			text.append("<div style='width:100%; margin:24px auto 0; border-top:1px solid #eee;'></div>");
			text.append("<div style='width:690px; margin:0 auto; padding-top:20px; text-align:left;'>");
			text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>收到此邮件，说明您已是我的网尊贵的会员。</p>");
			text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>为确保您接受的服务信息不被当做垃圾邮件处理，请将service@wodechang.com添加为联系人。</p>");
			text.append("</div></div>");
			
			/*String text = "这是一封修改绑定邮箱确认邮件，请点击以下链接确认：http://www.wd-w.com/user/changeBindEmail?userId="
					+user.getId()+"&code="+code.toString();*/
			Boolean result = mail.sendMail(user.getEmail(), "修改绑定邮箱确认",text.toString(),null,null,"myFactory",false,null);
			if(!result){
				re.setSuccess(false);
				re.setMsg("邮件发送失败，请检查您的注册邮箱是否正常使用");
			}else{
				re.setMsg("邮件已发送");
			}
		}
		return re;
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
}
