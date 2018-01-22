package com.wode.factory.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.wode.common.redis.RedisUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.Inventory;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.service.InventoryService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsService;
import com.wode.factory.service.PromotionProductService;
import com.wode.factory.vo.PromotionProductVo;
import com.wode.search.WodeSearchManager;


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
public class InventoryTask {

	@Autowired
	InventoryService invSer;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private PromotionProductService promotionProductService;
	
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
	
	@Autowired
	private ProductService productService;
	@Autowired
	private WodeSearchManager wsm;
	
	Logger log=LoggerFactory.getLogger(InventoryTask.class);
	
	/**
	 * 
	 * 功能说明：redis中的库存同步到数据库
	 * 日期:	2015年8月12日
	 * 开发者:宋艳垒
	 *
	 */
	@Scheduled(cron="0 0/5 * * * ?") 
	public void run() {
		
		Set<String> changes=redisUtil.getAllSet(RedisConstant.Inventory_CHANGE);
		log.info("InventoryTask start,"+changes.size()+" to save");
		
		for(String inv : changes){
			try{
				String skuKey = RedisConstant.REDIS_SKU_INVENTORY+inv;
				String quantity =  redisUtil.getData(skuKey);
				if(quantity != null){
					int intQua = Integer.parseInt(quantity);
					Inventory entity=new Inventory();
					entity.setProductSpecificationsId((Long.valueOf(inv)));
					entity.setQuantity(intQua);
					invSer.updateBySkuId(entity);
				}
				//更新商品总库存剩余
				ProductSpecifications ps = productSpecificationsService.getById(Long.parseLong(inv));
				String skuString = redisUtil.getMapData(RedisConstant.PRODUCT_PRE+ps.getProductId(), RedisConstant.PRODUCT_REDIS_SKU);
				List<ProductSpecifications> list = JSONObject.parseArray(skuString, ProductSpecifications.class);
				int total = 0;
				for(ProductSpecifications productSpecifications : list){
					String inventory =  redisUtil.getData(RedisConstant.REDIS_SKU_INVENTORY+productSpecifications.getId())==null?"0":redisUtil.getData(RedisConstant.REDIS_SKU_INVENTORY+productSpecifications.getId());
					total+=Integer.parseInt(inventory);
				}
				//Product product = productService.getById(ps.getProductId());
				productService.updateAllNum(ps.getProductId(),total);
				if(quantity != null){
					wsm.resetStock(ps.getProductId(), ps.getId(), Integer.parseInt(quantity));
				}
			} catch(Exception e) {
				log.error(e.getMessage() + "   sku=" + inv);
			}
			redisUtil.removeSet(RedisConstant.Inventory_CHANGE, inv);
		}
		
		
	}
	
	/**
	 * 
	 * 功能说明：调配普通销售与活动销售库存
	 * 日期:	2015年8月17日
	 * 开发者:宋艳垒
	 *
	 */
	@Scheduled(cron="0 0 0 * * ?")
	public void allocateStock() {
		Map<String, Object> params = new HashMap<String, Object>();
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date yesterday = cal.getTime();
		params.put("startCreateDate", yesterday);

		cal.add(Calendar.DATE, +2);
		Date today = cal.getTime();
		params.put("endCreateDate", today);

		List<PromotionProductVo> listPro = promotionProductService.findByCreateDate(params);
		for(PromotionProductVo ppvo:listPro){
			invSer.allocateStock(ppvo.getSkuId(), ppvo.getId(), false);
		}
	}

	public static void main(String[] args) {
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss").format(cal.getTime());
		System.out.println(yesterday);

		cal.add(Calendar.DATE, +2);
		String today = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss").format(cal.getTime());
		System.out.println(today);
	}
}
