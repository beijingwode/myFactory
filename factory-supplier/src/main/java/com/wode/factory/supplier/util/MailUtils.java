package com.wode.factory.supplier.util;

import static com.wode.common.constant.UserConstant.BINDMAILFUNCTION;
import static com.wode.common.constant.UserConstant.CHANGEEMAILFUNCTION;
import static com.wode.common.constant.UserConstant.FINDPWDBYEMAILFUNCTION;
import static com.wode.common.constant.UserConstant.FINDPWDBYPHONEFUNCTION;
import static com.wode.common.constant.UserConstant.PASSWORD;
import static com.wode.common.constant.UserConstant.REGEMAILFUNCTION;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.wode.common.redis.RedisUtilEx;
import com.wode.common.util.ActResult;
import com.wode.common.util.EncryptUtils;
import com.wode.common.util.StringUtils;
import com.wode.factory.outside.service.MailService;
import com.wode.factory.outside.service.ServiceFactory;

@Service("mail")
public class MailUtils {

	@Resource
	private RedisUtilEx redisEx;

	static MailService us = ServiceFactory.getMailService(Constant.OUTSIDE_SERVICE_URL);
	/**
	 * 读取配置文件对象
	 */
	@Autowired
	@Qualifier("messageSource")
	private ReloadableResourceBundleMessageSource messageSource;
	
	/**
	 * 发送邮件并将有效链接key存入redis
	 * @param toEmail
	 * @param subject
	 * @param text
	 * @return boolean
	 * @throws UnsupportedEncodingException 
	 */
	public ActResult<String> sendMailAndRedis(String toEmail, String suffix, String basePath, String userId, String funcType, int expireTime) throws UnsupportedEncodingException {

		//生成32位字母的id
		String randomId = StringUtils.randomStr(32);
		//邮件名称
		String subject = "";
		//邮件内容模板application.properties
		//String text = this.reader("wode.text", PRONAME).trim();
		String text = "";
		//激活账号或重置密码链接
		String emailHref = "";
		//邮件标题
		//String title = "";
		//邮件内容
		//String content = "";
		//邮件对应链接文字内容
		//String emailHrefText = "";
		//功能类型
		String function = "";
		//找回密码功能
		if (FINDPWDBYPHONEFUNCTION.equals(funcType) || FINDPWDBYEMAILFUNCTION.equals(funcType)) {
			function = "resetpwd";
			//emailHref = basePath + "user/toResetPwdByMailFind" + randomId + ".html";
			//content = this.reader("wode.resetpwd.content", PRONAME).trim();
			//content = messageSource.getMessage("wode.resetpwd.content", null, null, null).trim();
			randomId = toEmail;
			emailHref = StringUtils.randomNum() + "";
			userId = userId + "_" + emailHref;
			//绑定邮箱功能或邮箱注册功能
			text = retext(funcType, basePath, toEmail, emailHref);
		} else if (BINDMAILFUNCTION.equals(funcType) || REGEMAILFUNCTION.equals(funcType)) {
			function = "activeAccount";
			//拼装激活或绑定链接地址，将邮箱地址加密后做encode处理，防止url传输出现错误
			//由于加密算法加密后可能出现“/”，所以更改参数接收方式，将参数改为在链接后拼接参数方式接收
			emailHref = basePath + "user/toActivatingMail" + randomId + "/" + funcType + ".html?toEmail=" +
					URLEncoder.encode(EncryptUtils.encrypt(toEmail, PASSWORD), "UTF-8");
			//针对绑定邮箱和邮箱注册功能，将其redis对应邮件记录的key-value中的value存储格式为：userId_email
			userId = userId + "_" + toEmail;
			//content = this.reader("wode.activeAccount.content", PRONAME).trim()
			//		.replace("{emailAddress}", toEmail);
			//content = messageSource.getMessage("wode.activeAccount.content", null, null, null).trim().replace("{emailAddress}", toEmail);
			//text = text.replace("{title}", title).replace("{content}", content).replace("{emailHref}", emailHref).replace("{emailHrefText}", emailHrefText);
			text = retext(funcType, basePath, toEmail, emailHref);
		} else if (CHANGEEMAILFUNCTION.equals(funcType)) {
			subject = "更换邮箱";
			//拼装激活或绑定链接地址，将邮箱地址加密后做encode处理，防止url传输出现错误
			//由于加密算法加密后可能出现“/”，所以更改参数接收方式，将参数改为在链接后拼接参数方式接收
			emailHref = basePath + "user/changeBindEmail" + randomId + "/" + funcType + ".html";
			
			//针对绑定邮箱和邮箱注册功能，将其redis对应邮件记录的key-value中的value存储格式为：userId_email
			//userId = userId;
			//content = this.reader("wode.activeAccount.content", PRONAME).trim()
			//		.replace("{emailAddress}", toEmail);
			//content = messageSource.getMessage("wode.activeAccount.content", null, null, null).trim().replace("{emailAddress}", toEmail);
			//text = text.replace("{title}", title).replace("{content}", content).replace("{emailHref}", emailHref).replace("{emailHrefText}", emailHrefText);
			text = retext(funcType, basePath, toEmail, emailHref);
		}
		//subject = this.reader("wode." + function + ".subject", PRONAME).trim();
		//title = this.reader("wode." + function + ".title", PRONAME).trim();
		//emailHrefText = this.reader("wode." + function + ".emailHrefText", PRONAME).trim();
		if (!CHANGEEMAILFUNCTION.equals(funcType)){
			subject = messageSource.getMessage("wode." + function + ".subject", null, null, null).trim();
		}
		//title = messageSource.getMessage("wode." + function + ".title", null, null, null).trim();
		//emailHrefText = messageSource.getMessage("wode." + function + ".emailHrefText", null, null, null).trim();

		//redis存储不同功能邮件对应key-value
		boolean redisResult = setSendMailLog(randomId + suffix, userId, expireTime);
//		if (!redisResult) {
//			return redisResult;
//		} else {
			//发送邮件(判断注册功能类型为注册时，替换邮件发送器为qq的mailsender)
//			if (REGEMAILFUNCTION.equals(funcType)) {
//				mailUtil.setGunMailSender(qqMailSender);
//			}
			boolean sendMailResult = us.sendMail(toEmail, subject, text, null, null, "myFactory", false, null);
			//logger.info("\nsendMailAndRedis	function:	" + funcType + "	set redis result: " + sendMailResult + "	emailaddress	" + toEmail);
			
			if(sendMailResult) {
				return ActResult.successSetMsg(userId);
			} else {
				return ActResult.fail("发送邮件失败，");	
			}
//		}
	}
	

	public String retext(String funcType, String basePath, String toEmail, String emailHrefs) {
		StringBuilder text = new StringBuilder();
		if (FINDPWDBYPHONEFUNCTION.equals(funcType) || FINDPWDBYEMAILFUNCTION.equals(funcType)) {
			text.append("<!doctype html>");
			text.append("<html>");
			text.append("<head>");
			text.append("<meta charset=\"utf-8\">");
			text.append("<title>我的网-发送邮箱</title>");
			text.append("</head>");
			text.append("<body>");
			text.append("<!--top begin-->");
			text.append("<div style=\"width:100%; height:90px; margin:0 auto; border-bottom:2px solid #eee;\">");
			text.append("<div style=\"width:690px; margin:0 auto; padding-top:30px; height:60px; overflow:hidden;\">");
			text.append("<div style=\"width:116px; height:50px; float:left;\"><a href=\"javascript:void(0);\"><img src=\"" + basePath + "/images/logo.png\" width=\"116\" ></a></div>");
			text.append("<div style=\"float:right; padding-top:10px;\">");
			text.append("<a style=\"font:14px Microsoft YaHei; color:#828282; text-decoration:none;\" href=\"" + basePath + "/user/tosuppliermain.html\"><i style=\"width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(" + basePath + "/images/emailicon.png) no-repeat 0 0;\"></i>商家中心</a><em style=\"font:14px Microsoft YaHei; color:#828282; margin:0 10px;\">|</em>");
			text.append("<a style=\"font:14px Microsoft YaHei; color:#828282; text-decoration:none;\" href=\"javascript:void(0);\"><i style=\"width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(" + basePath + "/images/emailicon.png) no-repeat 0 -26px;\"></i>帮助中心</a>");
			text.append("</div>");
			text.append("</div>");
			text.append("</div>");
			text.append("<!--top end-->");
			text.append("<!--content begin-->");
			text.append("<div style=\"width:100%; margin:0 auto; text-align:center;\">");
			text.append("<h1 style=\"font:24px/36px Microsoft YaHei; color:#8fc31f; margin:50px 0 33px;\">您好，请在校验码输入框中输入" + emailHrefs + "<br>以完成操作</h1>");
			text.append("<p style=\"font:14px/20px Microsoft YaHei; color:#434343;\">您好，您正在使用我的网商家平台找回密码/安全设置功能！</p>");
			text.append("<p style=\"font:14px/20px Microsoft YaHei; color:#434343; margin-bottom:12px;\">注意：此操作可能会修改您的密码、手机号、登录邮箱。如非本人操作，请及时登录并修改密码以保证账户安全</p>");
			text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">（工作人员不会向您索取此校验码，请勿泄漏！）</p>");
			text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">为了保证您的账号安全，请在24小时内完成账号验证。</p>");
			text.append("<div style=\"width:100%; margin:24px auto 0; border-top:1px solid #eee;\"></div>");
			text.append("<div style=\"width:690px; margin:0 auto; padding-top:20px; text-align:left;\">");
			text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">收到此邮件，说明您已是我的网尊贵的会员。</p>");
			text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">为确保您接受的服务信息不被当做垃圾邮件处理，请将service@wodechang.com添加为联系人。</p>");
			text.append("</div>");
			text.append("</div>");
			text.append("<!--content end-->");
			text.append("</body>");
			text.append("</html>");
		} else if (BINDMAILFUNCTION.equals(funcType) || REGEMAILFUNCTION.equals(funcType)) {
			text.append("<!doctype html>");
			text.append("<html>");
			text.append("<head>");
			text.append("<meta charset=\"utf-8\">");
			text.append("<title>我的网-发送邮箱</title>");
			text.append("</head>");
			text.append("<body>");
			text.append("<!--top begin-->");
			text.append("<div style=\"width:100%; height:90px; margin:0 auto; border-bottom:2px solid #eee;\">");
			text.append("<div style=\"width:690px; margin:0 auto; padding-top:30px; height:60px; overflow:hidden;\">");
			text.append("<div style=\"width:116px; height:50px; float:left;\"><a href=\"javascript:void(0);\"><img src=\"" + basePath + "/images/logo.png\" width=\"116\" ></a></div>");
			text.append("<div style=\"float:right; padding-top:10px;\">");
			text.append("<a style=\"font:14px Microsoft YaHei; color:#828282; text-decoration:none;\" href=\"" + basePath + "/user/tosuppliermain.html\"><i style=\"width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(" + basePath + "/images/emailicon.png) no-repeat 0 0;\"></i>商家中心</a><em style=\"font:14px Microsoft YaHei; color:#828282; margin:0 10px;\">|</em>");
			text.append("<a style=\"font:14px Microsoft YaHei; color:#828282; text-decoration:none;\" href=\"javascript:void(0);\"><i style=\"width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(" + basePath + "/images/emailicon.png) no-repeat 0 -26px;\"></i>帮助中心</a>");
			text.append("</div>");
			text.append("</div>");
			text.append("</div>");
			text.append("<!--top end-->");
			text.append("<!--content begin-->");
			text.append("<div style=\"width:100%; margin:0 auto; text-align:center;\">");
			text.append("<h2 style=\"font:18px/36px Microsoft YaHei; color:#434343; margin-top:50px;\">感谢您在我的网商家平台注册，您已经填完所有的信息<span style=\"font:14px/36px Microsoft YaHei; color:#434343;\">（请注意核对）</span>：</h2>");
			text.append("<h3 style=\"font:20px/36px Microsoft YaHei; color:#8fc31f;\">您的注册邮箱为：" + toEmail + "</h3>");
			text.append("<p style=\"width:300px; height:50px; margin:0 auto; text-align:center; background:#ff6161; margin-top:30px; border-radius:4px; behavior:url(" + basePath + "/iecss3/PIE.htc); position:relative; z-index:2;\"><a style=\"display:block; font:18px/50px Microsoft YaHei; color:#fff; text-decoration:none;\" href=\"" + emailHrefs + "\">重要，请点击这里完成注册</a></p>");
			text.append("<p style=\"margin-top:50px; font:14px Microsoft YaHei; color:#434343;\">如果您不能点击上面的链接，您可以将下面的链接复制到浏览器进行访问：</p>");
			text.append("<p style=\"margin:5px 0 16px;\"><a style=\"font:14px Microsoft YaHei; color:#2b8dff; text-decoration:none;\" href=\"" + emailHrefs + "\">" + emailHrefs + "</a></p>");
			text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">为了保证您的账号安全，请在24小时内完成账号验证。</p>");
			text.append("<div style=\"width:100%; margin:24px auto 0; border-top:1px solid #eee;\"></div>");
			text.append("<div style=\"width:690px; margin:0 auto; padding-top:20px; text-align:left;\">");
			text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">收到此邮件，说明您已是我的网尊贵的会员。</p>");
			text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">为确保您接受的服务信息不被当做垃圾邮件处理，请将service@wodechang.com添加为联系人。</p>");
			text.append("</div>");
			text.append("</div>");
			text.append("<!--content end-->");
			text.append("</body>");
			text.append("</html>");
		} else if (CHANGEEMAILFUNCTION.equals(funcType)) {
			text.append("<!doctype html>");
			text.append("<html>");
			text.append("<head>");
			text.append("<meta charset=\"utf-8\">");
			text.append("<title>我的网-发送邮箱</title>");
			text.append("</head>");
			text.append("<body>");
			text.append("<!--top begin-->");
			text.append("<div style=\"width:100%; height:90px; margin:0 auto; border-bottom:2px solid #eee;\">");
			text.append("<div style=\"width:690px; margin:0 auto; padding-top:30px; height:60px; overflow:hidden;\">");
			text.append("<div style=\"width:116px; height:50px; float:left;\"><a href=\"javascript:void(0);\"><img src=\"" + basePath + "/images/logo.png\" width=\"116\" ></a></div>");
			text.append("<div style=\"float:right; padding-top:10px;\">");
			text.append("<a style=\"font:14px Microsoft YaHei; color:#828282; text-decoration:none;\" href=\"" + basePath + "/user/tosuppliermain.html\"><i style=\"width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(" + basePath + "/images/emailicon.png) no-repeat 0 0;\"></i>商家中心</a><em style=\"font:14px Microsoft YaHei; color:#828282; margin:0 10px;\">|</em>");
			text.append("<a style=\"font:14px Microsoft YaHei; color:#828282; text-decoration:none;\" href=\"javascript:void(0);\"><i style=\"width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(" + basePath + "/images/emailicon.png) no-repeat 0 -26px;\"></i>帮助中心</a>");
			text.append("</div>");
			text.append("</div>");
			text.append("</div>");
			text.append("<!--top end-->");
			text.append("<!--content begin-->");
			text.append("<div style=\"width:100%; margin:0 auto; text-align:center;\">");
			text.append("<h2 style='font:18px/36px Microsoft YaHei; color:#434343; margin-top:50px;'>亲爱的我的网用户，您好：</h2>");
			text.append("<h3 style='font:20px/36px Microsoft YaHei; color:#8fc31f;'>您正在修改我的网绑定邮箱，请确认，此链接24小时内有效，请及时验证。</h3>");
			text.append("<p style=\"width:300px; height:50px; margin:0 auto; text-align:center; background:#ff6161; margin-top:30px; border-radius:4px; behavior:url(" + basePath + "/iecss3/PIE.htc); position:relative; z-index:2;\"><a style=\"display:block; font:18px/50px Microsoft YaHei; color:#fff; text-decoration:none;\" href=\"" + emailHrefs + "\">重要，请点击这里完成确认</a></p>");
			text.append("<p style=\"margin-top:50px; font:14px Microsoft YaHei; color:#434343;\">如果您不能点击上面的链接，您可以将下面的链接复制到浏览器进行访问：</p>");
			text.append("<p style=\"margin:5px 0 16px;\"><a style=\"font:14px Microsoft YaHei; color:#2b8dff; text-decoration:none;\" href=\"" + emailHrefs + "\">" + emailHrefs + "</a></p>");
			text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">为了保证您的账号安全，请在24小时内完成账号验证。</p>");
			text.append("<div style=\"width:100%; margin:24px auto 0; border-top:1px solid #eee;\"></div>");
			text.append("<div style=\"width:690px; margin:0 auto; padding-top:20px; text-align:left;\">");
			text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">收到此邮件，说明您已是我的网尊贵的会员。</p>");
			text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">为确保您接受的服务信息不被当做垃圾邮件处理，请将service@wodechang.com添加为联系人。</p>");
			text.append("</div>");
			text.append("</div>");
			text.append("<!--content end-->");
			text.append("</body>");
			text.append("</html>");
		}
		return text == null ? "" : text.toString();
	}
	

	public String getMsgOrMailData(String key) {
		return redisEx.GetSearchResult(key);
	}
	
	public boolean setSendMailLog(String key, String value, int expireTime) {
		return redisEx.SetSearchResult(key, value, expireTime);
	}

	public void delMsgOrMailData(String key) {
		redisEx.delKey(key);
	}
}
