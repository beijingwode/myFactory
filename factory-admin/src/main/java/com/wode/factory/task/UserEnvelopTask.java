package com.wode.factory.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wode.common.constant.Constant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.UserBlackEnvelope;
import com.wode.factory.model.UserBlackEnvelopeItem;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserRedEnvelope;
import com.wode.factory.model.UserRedEnvelopeItem;
import com.wode.factory.service.UserBlackEnvelopeItemService;
import com.wode.factory.service.UserBlackEnvelopeService;
import com.wode.factory.service.UserFactoryService;
import com.wode.factory.service.UserRedEnvelopeItemService;
import com.wode.factory.service.UserRedEnvelopeService;


/**
 * 
 * <pre>
 * 功能说明: 库存同步(redis-->数据库)
 * 日期:	2015年8月12日
 * 开发者:	宋艳垒
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：谢海生
 *    修改日期： 2015年8月14日
 * </pre>
 */
@Component
public class UserEnvelopTask {

	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private UserRedEnvelopeService userRedEnvelopeService;
	@Autowired
	private UserBlackEnvelopeService userBlackEnvelopeService;
	
	@Autowired
	private UserRedEnvelopeItemService userRedEnvelopeItemService;
	@Autowired
	private UserBlackEnvelopeItemService userBlackEnvelopeItemService;
	@Autowired
	private UserFactoryService userFactoryService;
	
	Logger log=LoggerFactory.getLogger(UserEnvelopTask.class);
	/**
	 * 
	 * 功能说明：红包过期系统自动收回
	 * 日期:	2015年8月12日
	 * 开发者:宋艳垒
	 *
	 */
	@SuppressWarnings("rawtypes")
	@Scheduled(cron="0 0/5 * * * ?") 
	public void run() {
		UserRedEnvelope query = new UserRedEnvelope();
		query.setStatus(1);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, -1);
		query.setCreateTime(now.getTime());
		List<UserRedEnvelope> ls = userRedEnvelopeService.selectByModel(query);
		log.info("UserRedEnvelopTask start,"+ls.size()+" to deal");
		
		for(UserRedEnvelope ure : ls){
			Date upd = new Date();
			try{
				List<UserRedEnvelopeItem> items= getEnvelopeItems(ure.getId());
				List<UserRedEnvelopeItem> closes= getClosedItems(items);
				
				//全部领取
				if(closes.size()==0) {
					ure.setStatus(4);
					ure.setUpdateTime(upd);
					userRedEnvelopeService.update(ure);
					
					redisUtil.del("REDIS_ENVELOPE_" + ure.getId());
					continue;
				}
				
				BigDecimal amout = BigDecimal.ZERO;
				for (UserRedEnvelopeItem userRedEnvelopeItem : closes) {
					amout=amout.add(userRedEnvelopeItem.getPrice());
				}
				
				//金额及内购券返还
			    Map<String,Object> comMap=new HashMap<String,Object>();
				BigDecimal absCash =BigDecimal.ZERO;
				BigDecimal absTicket =BigDecimal.ZERO;
				if(ure.getCurrencyId()==0L) absCash= amout;
				if(ure.getCurrencyId()==1L) absTicket= amout;
				
				String desrc = "";
				if(0L == ure.getToType()) {
					UserFactory q= new UserFactory();
					q.setId(ure.getToId());
					q.setEmployeeType(-1); //全部数据
					//单个发送
					UserFactory friendUser =null;
					List<UserFactory> us =userFactoryService.selectByModel(q); 
					if(us!=null && !us.isEmpty()){
						friendUser = us.get(0);
					}
						 
					if(friendUser==null){
						desrc="您于"+TimeUtil.dateToStr(ure.getCreateTime(),"yyyy-MM-dd HH:mm:ss") +"发送的"+ure.getAmount()+"元红包，";
					} else {
						desrc="您向"+friendUser.getNickName() +"发送的"+ure.getAmount()+"元红包，";
					}
					
				} else if(1L == ure.getToType()) {
					//向群发送
//					if(1L==null){
						desrc="您于"+TimeUtil.dateToStr(ure.getCreateTime(),"yyyy-MM-dd HH:mm:ss") +"发送的"+ure.getAmount()+"元红包，";
//					} else {
//						desrc="您向"+"群名称XXX"+" 群发送的"+ure.getAmount()+"元红包，";
//					}
					
				} else {
				}
				
				if(closes.size() == items.size()) {
					desrc += "对方没有领取，全部退回到您的账户中.";
				} else {
					desrc += "其中" + amout + "元没有领取，退回到您的账户中.";					
				}
				
				comMap.put("opCode", "222");
				comMap.put("empId", ure.getUserId());
				comMap.put("absCash", absCash);
				comMap.put("absTicket", absTicket);
				comMap.put("key", ure.getId());
				comMap.put("desrc",desrc);
				comMap.put("updUser", "系统自动处理");
				
				Map<String,Object> paramWX = new HashMap<String,Object>();
				paramWX.put("type", "balace");
				paramWX.put("cId", ure.getCurrencyId());
				paramWX.put("date", TimeUtil.getStringDateShort());
				paramWX.put("amount", amout);
				
			    String ticketResult = HttpClientUtil.sendHttpRequest("post", Constant.FACTORY_SUPPLIER_URL+"api/dealEmpBenefit", comMap);
			    ActResult acTicket = JsonUtil.getObject(ticketResult, ActResult.class);
		        if(acTicket.isSuccess()){
		        	Map<String,Object> paramPush=new HashMap<String,Object>();
		    		paramPush.put("title", "红包退回");
		    		paramPush.put("msg", desrc);			
	    			try{
	    				//app 推送
	    				paramPush.put("userId", ure.getUserId());
	    				HttpClientUtil.sendHttpRequest("post", Constant.FACTORY_API_URL+"user/pushMsg", paramPush);

	    				//微信推送
	    				paramWX.put("userId", ure.getUserId());
	    				paramWX.put("note", desrc);
	    				HttpClientUtil.sendHttpRequest("post", Constant.FACTORY_API_URL + "wx/templateMsgSend", paramWX);
	    			} catch(Exception ex) {
	    				
	    			}

					for (UserRedEnvelopeItem userRedEnvelopeItem : closes) {
						userRedEnvelopeItem.setStatus(3);
						userRedEnvelopeItem.setUpdateTime(upd);
						userRedEnvelopeItem.setFlowCd(acTicket.getData().toString());
						
						userRedEnvelopeItemService.update(userRedEnvelopeItem);
					}
	    			

					ure.setStatus(6);
					ure.setUpdateTime(upd);
					userRedEnvelopeService.update(ure);
					
					redisUtil.del("REDIS_ENVELOPE_" + ure.getId());
					redisUtil.del("REDIS_ENVELOPE_ITEMS_" + ure.getId());
		        } else {
					log.error(acTicket.getMsg());
		        }
			} catch(Exception e) {
				log.error(e.getMessage());
			}
		}
	}

	
	/**
	 * 
	 * 功能说明：红包过期系统自动收回
	 * 日期:	2015年8月12日
	 * 开发者:宋艳垒
	 *
	 */
	@Scheduled(cron="0 0/5 * * * ?") 
	public void runBlack() {
		UserBlackEnvelope query = new UserBlackEnvelope();
		query.setStatus(1);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, -1);
		query.setCreateTime(now.getTime());
		List<UserBlackEnvelope> ls = userBlackEnvelopeService.selectByModel(query);
		log.info("UserRedEnvelopTask start,"+ls.size()+" to deal");
		
		for(UserBlackEnvelope ure : ls){
			Date upd = new Date();
			try{
				List<UserBlackEnvelopeItem> items= getBlackEnvelopeItems(ure.getId());
				List<UserBlackEnvelopeItem> closes= getBlackClosedItems(items);
				
				//全部领取
				if(closes.size()==0) {
					ure.setStatus(4);
					ure.setUpdateTime(upd);
					userBlackEnvelopeService.update(ure);
					
					redisUtil.del("REDIS_ENVELOPE_" + ure.getId());
					continue;
				}
				
				ure.setStatus(6);
				ure.setUpdateTime(upd);
				userBlackEnvelopeService.update(ure);
				
				redisUtil.del("REDIS_ENVELOPE_" + ure.getId());
				redisUtil.del("REDIS_ENVELOPE_ITEMS_" + ure.getId());

			} catch(Exception e) {
				log.error(e.getMessage());
			}
		}
	}
	
	private List<UserRedEnvelopeItem> getClosedItems(List<UserRedEnvelopeItem> list) {
		List<UserRedEnvelopeItem> rtn = new ArrayList<UserRedEnvelopeItem>();
		for (UserRedEnvelopeItem userRedEnvelopeItem : list) {
			if(userRedEnvelopeItem.getStatus()==0) {
				rtn.add(userRedEnvelopeItem);
			}
		}
		return rtn;
	}
	
	private List<UserRedEnvelopeItem> getEnvelopeItems(Long envelopeId) {
		String jsonS=redisUtil.getData("REDIS_ENVELOPE_ITEMS_" + envelopeId);
		if(StringUtils.isEmpty(jsonS)) {
			UserRedEnvelopeItem query = new UserRedEnvelopeItem();
			query.setEnvelopeId(envelopeId);
			List<UserRedEnvelopeItem> items = userRedEnvelopeItemService.selectByModel(query);
			
			redisUtil.setData("REDIS_ENVELOPE_ITEMS_" + envelopeId, JsonUtil.toJsonString(items), 60*60*6); //6小时缓存
			return items;
		} else {
			return JsonUtil.getList(jsonS, UserRedEnvelopeItem.class);
		}
	}
	

	private List<UserBlackEnvelopeItem> getBlackClosedItems(List<UserBlackEnvelopeItem> list) {
		List<UserBlackEnvelopeItem> rtn = new ArrayList<UserBlackEnvelopeItem>();
		for (UserBlackEnvelopeItem userRedEnvelopeItem : list) {
			if(userRedEnvelopeItem.getStatus()==0) {
				rtn.add(userRedEnvelopeItem);
			}
		}
		return rtn;
	}
	
	private List<UserBlackEnvelopeItem> getBlackEnvelopeItems(Long envelopeId) {
		String jsonS=redisUtil.getData("REDIS_ENVELOPE_ITEMS_" + envelopeId);
		if(StringUtils.isEmpty(jsonS)) {
			UserBlackEnvelopeItem query = new UserBlackEnvelopeItem();
			query.setEnvelopeId(envelopeId);
			List<UserBlackEnvelopeItem> items = userBlackEnvelopeItemService.selectByModel(query);
			
			redisUtil.setData("REDIS_ENVELOPE_ITEMS_" + envelopeId, JsonUtil.toJsonString(items), 60*60*6); //6小时缓存
			return items;
		} else {
			return JsonUtil.getList(jsonS, UserBlackEnvelopeItem.class);
		}
	}
}
