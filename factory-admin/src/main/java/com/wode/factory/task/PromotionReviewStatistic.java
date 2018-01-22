package com.wode.factory.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wode.factory.model.PromotionProduct;
import com.wode.factory.service.PromotionProductService;



@Component
public class PromotionReviewStatistic {

	@Autowired
	PromotionProductService promotionProductService;
	/**
	 * 定时将促销商品状态为审核中的更改为未审核
	 * @author Liuc
	 *
	 */
	//每间隔半小时执行一次
//	@Scheduled(cron="0 0/30 * * * ? ") 
//	public void run() {
//		// 创建促销商品的查询条件实体
//		PromotionProduct promotionProduct = new PromotionProduct();
//		// 审核状态为1(审核中)
//		promotionProduct.setStatus(1);
//		// 所有审核状态为审核中的促销商品列表
//		List<PromotionProduct> reivewingList = promotionProductService.promotionProductList(promotionProduct);
//		// 判断审核中的促销商品列表
//		if(reivewingList != null && reivewingList.size() > 0) {
//			// 循环审核中的促销商品列表
//			for(PromotionProduct promotionProduct1 : reivewingList) {
//				// 审核状态为审核中的开始时间
//				Date startReviewingDate = promotionProduct1.getReviewingDate();
//				// 审核状态为审核中的开始时间 + 10分钟
//				Date addTenMinutes = new Date(startReviewingDate.getTime() + 1000*60*10);
//				// 当前系统时间
//				Date systemDate = new Date(System.currentTimeMillis());
//				// 当系统时间在审核状态为审核中的开始时间 + 10分钟之后时
//				if(systemDate.after(addTenMinutes)) {
//					// 创建促销商品的更新条件实体
//					PromotionProduct promotionProduct2 = new PromotionProduct();
//					// 促销商品ID
//					promotionProduct2.setId(promotionProduct1.getId());
//					// 审核状态为未审核
//					promotionProduct2.setStatus(0);
//					// 正在审核中的用户ID设置为空（null）
//					promotionProduct2.setReviewingUserId(null);
//					// 正在审核中的开始时间设置为空（null）
//					promotionProduct2.setReviewingDate(null);
//					// 更新日期
//					promotionProduct2.setModifyDate(systemDate);
//					// 更新数据库
//					promotionProductService.updateUnreviewOrReviewing(promotionProduct2);
//				}
//			}
//		}
//	}
	
	
	/**
	 * 修改  超过秒杀活动日期的商品状态为不通过
	 * 
	 * 每天早上00:01开始执行
	 */
	@Scheduled(cron = "0 01 00 * * ?") 
	public void run() {
		// 创建促销商品的查询条件实体
		PromotionProduct promotionProduct = new PromotionProduct();
		promotionProduct.setJoinStart(new Date());
		promotionProduct.setStatus(0);
		List<PromotionProduct> reivewingList = this.promotionProductService.findOverduePromotionProduct(promotionProduct);
		if(reivewingList.isEmpty()){
			
		}else{
			for(PromotionProduct pp:reivewingList){
				pp.setStatus(-1);//0待审核 1审核中 2通过 -1不通过 -2 已退出
				this.promotionProductService.updateUnreviewOrReviewing(pp);
			}
		}
		
		
	}
	
}
