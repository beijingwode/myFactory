package com.wode.factory.user.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.wode.common.constant.UserConstant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.MailService;
import com.wode.factory.outside.service.ServiceFactory;

/**
 * 自定义标签
 * @author Bing King
 *
 */
public class UserEmail {

	static MailService mail = ServiceFactory.getMailService(Constant.OUTSIDE_SERVICE_URL);
	
	public static ActResult<String> sendEmail(Long userId, String type, String email, UserFactory user,RedisUtil redisUtil) {
		ActResult<String> re = new ActResult<String>();
		
		UUID code = UUID.randomUUID();
		Map<String,String> map = new HashMap<String, String>();
		map.put("userId", userId.toString());
		map.put("code", code.toString());
		if(!StringUtils.isNullOrEmpty(email)) {
			map.put("email", email);
		}
		redisUtil.setData(UserConstant.BINDMAILFUNCTION+"_"+userId, map,60 * 60 * 24);//有效期一天
		StringBuilder text = new StringBuilder();
		Boolean result = true;
		if(StringUtils.isEquals(type, "register")){
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
			text.append("<h3 style='font:20px/36px Microsoft YaHei; color:#8fc31f;'>您正在使用此邮箱注册我的网账号，此链接24小时内有效，请及时验证。</h3>");
			text.append("<p style='width:300px; height:50px; margin:0 auto; text-align:center; background:#ff6161; margin-top:30px; border-radius:4px; behavior:url(iecss3/PIE.htc); position:relative; z-index:2;'>"
					+ "<a style='display:block; font:18px/50px Microsoft YaHei; color:#fff; text-decoration:none;' href='http://www.wd-w.com/user/registerCheckByEmail?userId="+userId+"&code="+code+"'>请点击这里完成注册</a></p>");
			text.append("<p style='margin-top:50px; font:14px Microsoft YaHei; color:#434343;'>如果您不能点击上面的链接，您可以将下面的链接复制到浏览器进行访问：</p>");
			text.append("<p style='margin:5px 0 16px;'>"
					+ "<a style='font:14px Microsoft YaHei; color:#2b8dff; text-decoration:none;' href='javascript:void(0);'>"
					+ "http://www.wd-w.com/user/registerCheckByEmail?userId="+userId+"&code="+code+"</a></p>");
			text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>为了保证您的账号安全，请在24小时内完成账号验证。</p>");
			text.append("<div style='width:100%; margin:24px auto 0; border-top:1px solid #eee;'></div>");
			text.append("<div style='width:690px; margin:0 auto; padding-top:20px; text-align:left;'>");
			text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>收到此邮件，说明您已是我的网尊贵的会员。</p>");
			text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>为确保您接受的服务信息不被当做垃圾邮件处理，请将service@wodechang.com添加为联系人。</p>");
			text.append("</div></div>");
			/*text = "这是一封注册绑定邮件，请点击以下链接确认：http://www.wd-w.com/user/registerCheckByEmail?userId="
					+userId+"&code="+code;*/
			result = mail.sendMail(user.getEmail(), "注册邮箱绑定", text.toString(), null, null, "muFactory", false, null);
		}else if(StringUtils.isEquals(type, "bind") && StringUtils.isEmail(email)){
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
			text.append("<h3 style='font:20px/36px Microsoft YaHei; color:#8fc31f;'>您正在使用此邮箱绑定我的网账号，此链接24小时内有效，请及时验证。</h3>");
			text.append("<p style='width:300px; height:50px; margin:0 auto; text-align:center; background:#ff6161; margin-top:30px; border-radius:4px; behavior:url(iecss3/PIE.htc); position:relative; z-index:2;'>"
					+ "<a style='display:block; font:18px/50px Microsoft YaHei; color:#fff; text-decoration:none;' href='http://www.wd-w.com/user/bindEmailSuccess?userId="+userId+"&code="+code+"&email="+email+"'>请点击这里完成绑定</a></p>");
			text.append("<p style='margin-top:50px; font:14px Microsoft YaHei; color:#434343;'>如果您不能点击上面的链接，您可以将下面的链接复制到浏览器进行访问：</p>");
			text.append("<p style='margin:5px 0 16px;'>"
					+ "<a style='font:14px Microsoft YaHei; color:#2b8dff; text-decoration:none;' href='javascript:void(0);'>"
					+ "http://www.wd-w.com/user/bindEmailSuccess?userId="+userId+"&code="+code+"&email="+email+"</a></p>");
			text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>为了保证您的账号安全，请在24小时内完成账号验证。</p>");
			text.append("<div style='width:100%; margin:24px auto 0; border-top:1px solid #eee;'></div>");
			text.append("<div style='width:690px; margin:0 auto; padding-top:20px; text-align:left;'>");
			text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>收到此邮件，说明您已是我的网尊贵的会员。</p>");
			text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>为确保您接受的服务信息不被当做垃圾邮件处理，请将service@wodechang.com添加为联系人。</p>");
			text.append("</div></div>");
			/*text = "这是一封邮箱绑定邮件，请点击以下链接确认：http://www.wd-w.com/user/bindEmailSuccess?userId="
					+userId+"&code="+code+"&email="+email;*/
			result = mail.sendMail(email, "绑定邮箱", text.toString(), null, null, "muFactory", false, null);
		}else if(StringUtils.isEquals(type, "enabled") && StringUtils.isEmail(email)){
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
			text.append("<h3 style='font:20px/36px Microsoft YaHei; color:#8fc31f;'>您正在使用此绑定邮箱激活我的网账号，此链接24小时内有效，请及时验证。</h3>");
			text.append("<p style='width:300px; height:50px; margin:0 auto; text-align:center; background:#ff6161; margin-top:30px; border-radius:4px; behavior:url(iecss3/PIE.htc); position:relative; z-index:2;'>"
					+ "<a style='display:block; font:18px/50px Microsoft YaHei; color:#fff; text-decoration:none;' href='http://www.wd-w.com/user/activation?userId="+userId+"&code="+code+"&email="+email+"'>请点击这里完成激活</a></p>");
			text.append("<p style='margin-top:50px; font:14px Microsoft YaHei; color:#434343;'>如果您不能点击上面的链接，您可以将下面的链接复制到浏览器进行访问：</p>");
			text.append("<p style='margin:5px 0 16px;'>"
					+ "<a style='font:14px Microsoft YaHei; color:#2b8dff; text-decoration:none;' href='javascript:void(0);'>"
					+ "http://www.wd-w.com/user/activation?userId="+userId+"&code="+code+"&email="+email+"</a></p>");
			text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>为了保证您的账号安全，请在24小时内完成账号验证。</p>");
			text.append("<div style='width:100%; margin:24px auto 0; border-top:1px solid #eee;'></div>");
			text.append("<div style='width:690px; margin:0 auto; padding-top:20px; text-align:left;'>");
			text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>收到此邮件，说明您已是我的网尊贵的会员。</p>");
			text.append("<p style='font:12px/20px Microsoft YaHei; color:#828282;'>为确保您接受的服务信息不被当做垃圾邮件处理，请将service@wodechang.com添加为联系人。</p>");
			text.append("</div></div>");
			/*text = "这是一封邮箱绑定邮件，请点击以下链接确认：http://www.wd-w.com/user/bindEmailSuccess?userId="
					+userId+"&code="+code+"&email="+email;*/
			result = mail.sendMail(email, "激活绑定邮箱", text.toString(), null, null, "muFactory", false, null);
		}
		if(!result){
			re.setSuccess(false);
			re.setMsg("邮件发送失败，请检查您的注册邮箱是否正常使用");
		}else{
			re.setData("/user/emailRegistrationSuccess?userId="+userId);
			re.setMsg("邮件已发送，请通过邮件进行帐号激活");
		}
		
		return re;
	}
}
