package com.wode.api.job;

//import org.apache.commons.logging.LogFactory;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wode.api.util.JPushUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.SortList;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.FLJModel;
import com.wode.factory.model.FLJProductModel;
import com.wode.factory.model.UserDevice;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.ShopService;
import com.wode.factory.user.service.UserDeviceService;

@Component
public class CreateJob {
	 /** Logger */
	private static final Logger logger = LoggerFactory.getLogger(CreateJob.class);
	
	
	
	@Autowired
    private ShopService shopService;
	
	@Autowired
	private ProductSpecificationsService  productSpecificationsService;
    
	private SortList<FLJModel> sl = new SortList<FLJModel>();
	
	@Qualifier("redis")
    @Autowired
    private RedisUtil redisUtil;

	@Autowired
	private UserDeviceService userDeviceService;

	@Scheduled(cron="00 00 00 * * ?")
    public void doStatisticsSell() {
    	logger.info("begin to read "); 
    	//获取全部有效店铺
    	List<FLJModel> shopList = shopService.findAllShop();
    	
    	for (FLJModel shopSetting : shopList) {
    		//获取商品数量
    		int num = shopService.findProductCount(shopSetting.getSupplierId(),shopSetting.getId());
    		shopSetting.setProductNum(num);
    		//获取前三销量商品数据
    		List<FLJProductModel> productSpecificationsList = productSpecificationsService.findTop3(shopSetting.getSupplierId());
    		shopSetting.setProductSpecifications(productSpecificationsList);
		}
    	//厂家按照销量排序
    	sl.Sort(shopList, "getProductNum", "desc");
    	
    	//记录信息
    	redisUtil.setData(RedisConstant.FLJ, JsonUtil.toJson(shopList));
    	
    	//System.out.println(redisUtil.get(RedisConstant.FLJ));
    	
    }
    
    /**
     * 秒杀到时推送通知
     */
    @Scheduled(cron="0 55 * * * ?")
    public void push() {
    	logger.info("begin to read ");
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
    	String str = df.format(calendar.getTime());
    	Long time = TimeUtil.strToDate(str).getTime();
    	Boolean b = true;
    	while(b){
    		String s = redisUtil.spop(RedisConstant.PROMOTIONPRODUCT_CARE+time);
    		
    		if(StringUtils.isNullOrEmpty(s)){
    			b=false;
    		}else{
        		UserDevice query = new UserDevice();
        		query.setUserId(Long.parseLong(s));
        		List<UserDevice> ls = userDeviceService.findByAttribute(query);
        		for (UserDevice userDevice : ls) {
        			JPushUtils.sendNotification("您关注的活动商品马上开始进行发售，请注意抢购", "活动开始提醒", null, JPushUtils.formatDriver(userDevice.getDeviceType()), "alias", userDevice.getAsname());					
				}
    		}
    	}
    	
    }
    
}
