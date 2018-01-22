package com.wode.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.wode.common.constant.Constant;
import com.wode.factory.outside.service.MailService;
import com.wode.factory.outside.service.ServiceFactory;


@Component("emailUtil")
public class EmailUtil {
	/**
	 * 读取配置文件对象
	 */
	@Autowired
	@Qualifier("messageSource")
	private ReloadableResourceBundleMessageSource messageSource;
	/*//获取地址url
	private String basePath =messageSource.getMessage("admin.url", null, null, null).trim();
	//商品审核邮件标题
	private String productCheck =messageSource.getMessage("productCheck", null, null, null).trim();
	//商家审核邮件标题
	private String supplierCheck =messageSource.getMessage("supplierCheck", null, null, null).trim();*/
	
			
	private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);
	
	/**
	 * 商品审核发送邮件
	 * @param toEmail
	 * @param title
	 * @param subTitle
	 * @param type
	 * @param status
	 */
	public void sendProductCheckEmail(final String toEmail, final String title,final String subTitle,final String checkNotWhy, final Boolean status) {
		// 创建线程池
		ExecutorService executorService = Executors.newCachedThreadPool();
		// 添加线程进线程池执行线程
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				boolean sendMailResult = false;
				try {
					String basePath = messageSource.getMessage("admin.url", null, null, null).trim();
					String template = EmailUtil.checkTemplate(null, basePath,title,subTitle, checkNotWhy, "productCheck", status);
					MailService mailService = ServiceFactory.getMailService(Constant.OUTSIDE_SERVICE_URL);
					sendMailResult = mailService.sendMail(toEmail, messageSource.getMessage("productCheck", null, null, null).trim(), template, null,null, "myFacory", true, null);
				} catch (Exception e) {
					logger.error("\n sendProductCheckEmail exception	" + e.getMessage());
				}
				if (sendMailResult) {
					logger.info("\n send sendProductCheckEmail result:"
							+ sendMailResult + ",emailAddress:" + toEmail
							+ ",title:" + title+"("+subTitle+"),status:"+(status==true?"通过":"不通过")+",sendTime:"
							+ TimeUtil.getCurrentTime());
				}
			}
		});
		logger.info("\n		send sendProductCheckEmail end	：--------------");
	}
	
	/**商家审核发送邮件
	 * @param toEmail
	 * @param supplierCheckNotWhy
	 * @param type
	 * @param status
	 */
	public void sendSupplierCheckEmail(final String toEmail, final String checkNotWhy, final Boolean status) {
		// 创建线程池
		ExecutorService executorService = Executors.newCachedThreadPool();
		// 添加线程进线程池执行线程
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				boolean sendMailResult = true;
				try {
					String basePath = messageSource.getMessage("admin.url", null, null, null).trim();
					String template = EmailUtil.checkTemplate(null, basePath,null,null, checkNotWhy, "supplierCheck", status);
					MailService mailService = ServiceFactory.getMailService(Constant.OUTSIDE_SERVICE_URL);
					sendMailResult = mailService.sendMail(toEmail, messageSource.getMessage("supplierCheck", null, null, null).trim(), template, null,null, "myFacory", true, null);
				} catch (Exception e) {
					logger.error("\n sendSupplierCheckEmail exception	" + e.getMessage());
				}
				if (sendMailResult) {
					logger.info("\n send sendSupplierCheckEmail result:"
							+ sendMailResult + ",emailAddress:" + toEmail
							+ "," + (status==false?("supplierCheckNotWhy:"+checkNotWhy+","):"") +"status:"+(status==true?"通过":"不通过")+",sendTime:"
							+ TimeUtil.getCurrentTime());
				}
			}
		});
		logger.info("\n		send sendSupplierCheckEmail end	：--------------");
	}

	/**商家审核发送邮件
	 * @param toEmail
	 * @param supplierCheckNotWhy
	 * @param type
	 * @param status
	 */
	public void sendNewOrderEmail(final String toEmail, final String subOrderId, final String productNames) {
		// 创建线程池
		ExecutorService executorService = Executors.newCachedThreadPool();
		// 添加线程进线程池执行线程
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				boolean sendMailResult = true;
				try {
					String basePath = messageSource.getMessage("admin.url", null, null, null).trim();
					String template = EmailUtil.checkTemplate("我的福利--新订单通知", basePath,subOrderId,productNames, null, "newOrder", null);
					MailService mailService = ServiceFactory.getMailService(Constant.OUTSIDE_SERVICE_URL);
					sendMailResult = mailService.sendMail(toEmail, "我的福利--新订单通知", template, null,null, "myFacory", true, null);
				} catch (Exception e) {
					logger.error("\n sendSupplierCheckEmail exception	" + e.getMessage());
				}
				if (sendMailResult) {
					logger.error("\n send sendNewOrderEmail result:"
							+ sendMailResult + ",emailAddress:" + toEmail
							+ "订单信息:"+subOrderId+"  "+productNames +",sendTime:"
							+ TimeUtil.getCurrentTime());
				}
			}
		});
		logger.info("\n		send sendSupplierCheckEmail end	：--------------");
	}

	
	/**商家审核发送邮件
	 * @param toEmail
	 * @param supplierCheckNotWhy
	 * @param type
	 * @param status
	 */
	public void sendSupplierCheckEmailForUs(final String toEmail, final String checkNotWhy,final String context,final String linkUrl) {
		
		if(StringUtils.isEmpty(toEmail)) return;
		// 创建线程池
		ExecutorService executorService = Executors.newCachedThreadPool();
		// 添加线程进线程池执行线程
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					String basePath = messageSource.getMessage("admin.url", null, null, null).trim();
					String template = EmailUtil.checkTemplateForUs(null,basePath, checkNotWhy, context, linkUrl);
					MailService mailService = ServiceFactory.getMailService(Constant.OUTSIDE_SERVICE_URL);
					mailService.sendMail(toEmail, messageSource.getMessage("supplierCheck", null, null, null).trim(), template, null,null, "myFacory", true, null);
				} catch (Exception e) {
					logger.error("\n sendSupplierCheckEmail exception	" + e.getMessage());
				}
			}
		});
		logger.info("\n		send sendSupplierCheckEmail end	：--------------");
	}
	
	/**商家审核发送邮件
	 * @param toEmail
	 * @param msg
	 */
	public void sendSupplierExitCheckEmailForUs(final String toEmail,final String msg) {
		
		if(StringUtils.isEmpty(toEmail)) return;
		// 创建线程池
		ExecutorService executorService = Executors.newCachedThreadPool();
		// 添加线程进线程池执行线程
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					String basePath = messageSource.getMessage("admin.url", null, null, null).trim();
					String template = EmailUtil.checkTemplateForUs(null,basePath, null, msg, "http://wdwmananger.wd-w.com");
					MailService mailService = ServiceFactory.getMailService(Constant.OUTSIDE_SERVICE_URL);
					mailService.sendMail(toEmail, messageSource.getMessage("supplierCheck", null, null, null).trim(), template, null,null, "myFacory", true, null);
				} catch (Exception e) {
					logger.error("\n sendSupplierCheckEmail exception	" + e.getMessage());
				}
			}
		});
		logger.info("\n		send sendSupplierCheckEmail end	：--------------");
	}

	/**商家审核发送邮件
	 * @param toEmail
	 * @param supplierCheckNotWhy
	 * @param type
	 * @param status
	 */
	public void sendNewOrderEmailForUs(final String toEmail, final String subOrderId, final String productNames) {
		// 创建线程池
		ExecutorService executorService = Executors.newCachedThreadPool();
		// 添加线程进线程池执行线程
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				boolean sendMailResult = true;
				try {
					String basePath = messageSource.getMessage("admin.url", null, null, null).trim();
					String template = EmailUtil.checkTemplateForUs("我的福利--新订单通知", basePath,null,"您的商家有新订单需要处理", "http://wdwmananger.wd-w.com/orderList/detail/showlayer?id="+subOrderId);
					MailService mailService = ServiceFactory.getMailService(Constant.OUTSIDE_SERVICE_URL);
					sendMailResult = mailService.sendMail(toEmail, "我的福利--新订单通知", template, null,null, "myFacory", true, null);
				} catch (Exception e) {
					logger.error("\n sendSupplierCheckEmail exception	" + e.getMessage());
				}
				if (sendMailResult) {
					logger.error("\n send sendNewOrderEmail result:"
							+ sendMailResult + ",emailAddress:" + toEmail
							+ "订单信息:"+subOrderId+"  "+productNames +",sendTime:"
							+ TimeUtil.getCurrentTime());
				}
			}
		});
		logger.info("\n		send sendSupplierCheckEmail end	：--------------");
	}
	/**
	 * @param basePath 图片路径
	 * @param supplierCheckNotWhy 商家审核不通过原因
	 * @param type 类型(商家审核还是商品审核)  productCheck  supplierCheck
	 * @param status 审核通过、未通过 true false
	 * @return
	 */
	private static String checkTemplate(String mailTitle,String basePath,String title,String subTitle,String checkNotWhy,String type,Boolean status){
		StringBuilder text = new StringBuilder();
		text.append("<!doctype html>");
		text.append("<html>");
		text.append("<head>");
		text.append("<meta charset=\"utf-8\">");
		if(StringUtils.isEmpty(mailTitle)) {
			text.append("<title>我的网-发送邮箱</title>");
		} else {
			text.append("<title>").append(mailTitle).append("</title>");
		}
		text.append("</head>");
		text.append("<body>");
		text.append("<!--top begin-->");
		text.append("<div style=\"width:100%; height:90px; margin:0 auto; border-bottom:2px solid #eee;\">");
		text.append("<div style=\"width:690px; margin:0 auto; padding-top:30px; height:60px; overflow:hidden;\">");
		text.append("<div style=\"width:116px; height:50px; float:left;\"><a href=\"javascript:void(0);\"><img src=\"" + basePath + "/static/img/logo.png\" width=\"116\" height=\"50\"></a></div>");
		text.append("<div style=\"float:right; padding-top:10px;\">");
		text.append("<a style=\"font:14px Microsoft YaHei; color:#828282; text-decoration:none;\" href=\""+Constant.FACTORY_SUPPLIER_URL+"user/tosuppliermain.html\"><i style=\"width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(" + basePath + "/images/emailicon.png) no-repeat 0 0;\"></i>商家中心</a><em style=\"font:14px Microsoft YaHei; color:#828282; margin:0 10px;\">|</em>");
		text.append("<a style=\"font:14px Microsoft YaHei; color:#828282; text-decoration:none;\" href=\"javascript:void(0);\"><i style=\"width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(" + basePath + "/images/emailicon.png) no-repeat 0 -26px;\"></i>帮助中心</a>");
		text.append("</div>");
		text.append("</div>");
		text.append("</div>");
		text.append("<!--top end-->");
		text.append("<!--content begin-->");
		text.append("<div style=\"width:100%; margin:0 auto; text-align:center;\">");
		//商品审核通过
		if(type.equals("productCheck")&&status.equals(true)){
			//一层
			text.append("<h1 style=\"font:24px/36px Microsoft YaHei; color:#8fc31f; margin:50px 0 33px;\">您提交的商品"+title+"("+subTitle+")，上架审核已通过，并且开始向买家发售。</h1>");
			//二层
			text.append("<p style=\"font:14px/20px Microsoft YaHei; color:#434343;\">您可以在商家中心>商品管理>在售商品管理 中查看该商品信息，必要时可以做相应调整。如遇特殊情况，可以进行下架处理以便停止销售。</p>");
		//商品审核不通过
		}else if(type.equals("productCheck")&&status.equals(false)){
			//一层
			text.append("<h1 style=\"font:24px/36px Microsoft YaHei; color:#8fc31f; margin:50px 0 33px;\">您提交的商品"+title+"("+subTitle+")，上架审核未通过。</h1>");
			//二层
			text.append("<p style=\"font:14px/20px Microsoft YaHei; color:#434343;\">审核中遇到的问题如下,希望您能尽快配合我们解决.您可以在商家中心>商品管理>待售商品管理 中查看该商品信息"+title+"</p>");
			if(checkNotWhy!=null){
				text.append("<p style=\"font:14px/20px Microsoft YaHei; color:#434343; margin-bottom:12px;\">原因:"+checkNotWhy+"</p>");
			}
		//商家审核通过
		}else if(type.equals("supplierCheck")&&status.equals(true)){
			//一层
			text.append("<h1 style=\"font:24px/36px Microsoft YaHei; color:#8fc31f; margin:50px 0 33px;\">您好，您的商家入驻已完成审核</h1>");
			//二层
			text.append("<p style=\"font:14px/20px Microsoft YaHei; color:#434343;\">请尽快设置店铺、上传商品吧，买家都期待着您能给带来惊喜</p>");
			
		//商家审核不通过
		}else if(type.equals("supplierCheck")&&status.equals(false)){
			//一层
			text.append("<h1 style=\"font:24px/36px Microsoft YaHei; color:#8fc31f; margin:50px 0 33px;\">您好，很抱歉，您的商家入驻审核未通过</h1>");
			//二层
			text.append("<p style=\"font:14px/20px Microsoft YaHei; color:#434343;\">审核中遇到的问题如下,希望您能尽快配合我们解决。</p>");
			if(checkNotWhy!=null){
				text.append("<p style=\"font:14px/20px Microsoft YaHei; color:#434343; margin-bottom:12px;\">原因:"+checkNotWhy+"</p>");
			}

		}else if(type.equals("newOrder")){
			//新订单通知
			//一层
			text.append("<h1 style=\"font:24px/36px Microsoft YaHei; color:#8fc31f; margin:50px 0 33px;\">您的店铺有新的订单生成,请您尽快发货。订单id："+title+"</h1>");
			//二层
			text.append("<p style=\"font:14px/20px Microsoft YaHei; color:#434343;\"><a href='"+Constant.FACTORY_SUPPLIER_URL+"'>您可以在商家中心>订单管理>已售出的商品 中查看并处理此订单</a></p>");
			if(subTitle!=null){
				text.append("<p style=\"font:14px/20px Microsoft YaHei; color:#434343; margin-bottom:12px;\">商品信息:"+subTitle+"</p>");
			}
		}
		
		//三层
		text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">如有您遇到任何相关问题，可以查阅网站上的帮助中心，或直接联系我们，联系方式已网站内容为准。</p>");
		
		text.append("<div style=\"width:100%; margin:24px auto 0; border-top:1px solid #eee;\"></div>");
		text.append("<div style=\"width:690px; margin:0 auto; padding-top:20px; text-align:left;\">");
		text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">收到此邮件，说明您已是我的网尊贵的会员。</p>");
		text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">为确保您接受的服务信息不被当做垃圾邮件处理，请将service@wodechang.com添加为联系人。</p>");
		text.append("</div>");
		text.append("</div>");
		text.append("<!--content end-->");
		text.append("</body>");
		text.append("</html>");
		return text.toString();
	}
	/**
	 * @param basePath 图片路径
	 * @param supplierCheckNotWhy 商家审核不通过原因
	 * @param type 类型(商家审核还是商品审核)  productCheck  supplierCheck
	 * @param status 审核通过、未通过 true false
	 * @return
	 */
	private static String checkTemplateForUs(String mailTitle,String basePath,String checkNotWhy,String context,String linkUrl){
		StringBuilder text = new StringBuilder();
		text.append("<!doctype html>");
		text.append("<html>");
		text.append("<head>");
		text.append("<meta charset=\"utf-8\">");
		if(StringUtils.isEmpty(mailTitle)) {
			text.append("<title>我的网-发送邮箱</title>");
		} else {
			text.append("<title>").append(mailTitle).append("</title>");
		}
		text.append("</head>");
		text.append("<body>");
		text.append("<!--top begin-->");
		text.append("<div style=\"width:100%; height:90px; margin:0 auto; border-bottom:2px solid #eee;\">");
		text.append("<div style=\"width:690px; margin:0 auto; padding-top:30px; height:60px; overflow:hidden;\">");
		text.append("<div style=\"width:116px; height:50px; float:left;\"><a href=\"javascript:void(0);\"><img src=\"" + basePath + "/static/img/logo.png\" width=\"116\" height=\"50\"></a></div>");
		text.append("<div style=\"float:right; padding-top:10px;\">");
		text.append("<a style=\"font:14px Microsoft YaHei; color:#828282; text-decoration:none;\" href=\"" + basePath + "/user/tosuppliermain.html\"><i style=\"width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(" + basePath + "/images/emailicon.png) no-repeat 0 0;\"></i>商家中心</a><em style=\"font:14px Microsoft YaHei; color:#828282; margin:0 10px;\">|</em>");
		text.append("<a style=\"font:14px Microsoft YaHei; color:#828282; text-decoration:none;\" href=\"javascript:void(0);\"><i style=\"width:18px; height:18px; display:inline-block; vertical-align:middle; margin-right:6px; background:url(" + basePath + "/images/emailicon.png) no-repeat 0 -26px;\"></i>帮助中心</a>");
		text.append("</div>");
		text.append("</div>");
		text.append("</div>");
		text.append("<!--top end-->");
		text.append("<!--content begin-->");
		text.append("<div style=\"width:100%; margin:0 auto; text-align:center;\">");

		//一层
		text.append("<h1 style=\"font:24px/36px Microsoft YaHei; color:#8fc31f; margin:50px 0 33px;\">"+context+"</h1>");
		//二层
		text.append("<p style=\"font:14px/20px Microsoft YaHei; color:#434343;\"><a href=\"" + linkUrl + "\">点击这里，进行处理</a></p>");
		
		if(checkNotWhy!=null){
			text.append("<p style=\"font:14px/20px Microsoft YaHei; color:#434343; margin-bottom:12px;\">回退原因:"+checkNotWhy+"</p>");
		}
		
		//三层
		text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">如有您遇到任何相关问题，可以查阅网站上的帮助中心，或直接联系我们，联系方式已网站内容为准。</p>");
		
		text.append("<div style=\"width:100%; margin:24px auto 0; border-top:1px solid #eee;\"></div>");
		text.append("<div style=\"width:690px; margin:0 auto; padding-top:20px; text-align:left;\">");
		text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">收到此邮件，说明您已是我的网尊贵的会员。</p>");
		text.append("<p style=\"font:12px/20px Microsoft YaHei; color:#828282;\">为确保您接受的服务信息不被当做垃圾邮件处理，请将service@wodechang.com添加为联系人。</p>");
		text.append("</div>");
		text.append("</div>");
		text.append("<!--content end-->");
		text.append("</body>");
		text.append("</html>");
		return text.toString();
	}
}