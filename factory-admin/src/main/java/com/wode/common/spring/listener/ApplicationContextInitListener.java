package com.wode.common.spring.listener;

import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.wode.common.beetl.utils.BeetlUtils;
import com.wode.common.constant.Constant;
import com.wode.common.redis.RedisUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.service.impl.OrderJedisListenter;
import com.wode.factory.service.impl.WxJedisListenter;
import com.wode.sys.model.SysResource;
import com.wode.sys.service.SysResourceService;
import com.wode.tongji.service.TaskDefinitionService;

@Component
public class ApplicationContextInitListener implements ApplicationListener<ContextRefreshedEvent>, ServletContextAware {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private ServletContext servletContext;

	@Resource
	private SysResourceService sysResourceService;
	
	@Resource
	private TaskDefinitionService taskDefinitionService;
	
	@Resource
	private OrderJedisListenter orderJedisListenter;
	@Resource
	private WxJedisListenter wxJedisListenter;

	@Resource
	private RedisUtil redis;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		ApplicationContext parentContext = ((ContextRefreshedEvent) event)
				.getApplicationContext().getParent();
		
		// 子容器初始化时(spring-mvc)
		if (null != parentContext) {
			
			/*RequestMappingHandlerMapping rmhp = event.getApplicationContext()
					.getBean(RequestMappingHandlerMapping.class);
			
			Map<RequestMappingInfo, HandlerMethod> map = rmhp.getHandlerMethods();
			
			Iterator<RequestMappingInfo> iterator = map.keySet().iterator();
			
			while(iterator.hasNext()){
				RequestMappingInfo info = iterator.next();
				Set<String> set = info.getPatternsCondition().getPatterns();
			}*/
			
			String ctxPath = servletContext.getContextPath();
			
			//读取全部资源
			LinkedHashMap<String, SysResource> AllResourceMap = sysResourceService.getAllResourcesMap();
			BeetlUtils.addBeetlSharedVars(Constant.CACHE_ALL_RESOURCE,AllResourceMap);
			
			//初始化任务调度
			taskDefinitionService.initTask();
			
			initProductListenter();
			
			initUserListenter();
			logger.info("初始化系统资源:(key:" + Constant.CACHE_ALL_RESOURCE
					+ ",value:Map<资源url, SysResource>)");
		}
		
	}
	
	public void initProductListenter(){
		Thread t=new Thread(new Runnable(){
			@Override
			public void run() {
				redis.getConnection().psubscribe(orderJedisListenter,new String[]{
						RedisConstant.SUBSCRIBE_CHANNEL_ORDER_CREATE,
						RedisConstant.SUBSCRIBE_CHANNEL_ORDER_UPDATE,
						RedisConstant.SUBSCRIBE_CHANNEL_ORDER_CANCEL,
						RedisConstant.SUBSCRIBE_CHANNEL_ORDER_PAY,
						RedisConstant.SUBSCRIBE_CHANNEL_ORDER_URGED});
			}
			
		});
		t.start();
	}

	public void initUserListenter(){
		Thread t=new Thread(new Runnable(){
			@Override
			public void run() {
				redis.getConnection().psubscribe(wxJedisListenter,new String[]{
						RedisConstant.SUBSCRIBE_CHANNEL_USER_BIND});
			}
			
		});
		t.start();
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
