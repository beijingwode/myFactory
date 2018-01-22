package com.wode.factory.task;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.interpreter.Expression;
import com.wode.factory.interpreter.JdExpression;
import com.wode.factory.interpreter.ThirdPriceContext;
import com.wode.factory.interpreter.TmallExpression;
import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.model.ThirdPriceVo;
import com.wode.factory.service.ProductThirdPriceService;

/**
 * 功能说明: 结算定时任务类
 * 包括：
 * 		订单自动结算
 * 日期:	2015年11月9日
 * 开发者:	高永久
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 2015年3月20日
 */
@Component
public class ThirdPriceFetchTask {


	@Autowired
    private ProductThirdPriceService productThirdPriceService;
	
    private final Logger logger = LoggerFactory.getLogger(ThirdPriceFetchTask.class);

	/**
	 * 结算，每天凌晨3点5跑
	 * 2016-07-21
	 */
//	@Scheduled(cron = "0 5 3 * * ?")
//	public void checkReceivesProduct() {
//		logger.debug("自动比价开始");
//		int s_cnt =0;
//		int e_cnt =0;
//		int skip =0;
//		
//		////////////////////////////////////////////////////////////////////
//		//查询执行任务
//		List<ProductThirdPrice> lst = productThirdPriceService.select5DaysAgo(null);
//		////////////////////////////////////////////////////////////////////
//		
//		////////////////////////////////////////////////////////////////////
//		//循环执行任务
//		for (ProductThirdPrice third : lst) {
//			try{
//				
//				////////////////////////////////////////////////////////////
//				//更新执行状态
//				////////////////////////////////////////////////////////////
//	
//				String url=third.getItemUrl();
//				if(url!=null) url = url.trim();
//				if(StringUtils.isEmpty(url)) {
//					third.setUrlStatus(-1);
//					e_cnt++;
//				} else {
//					ThirdPriceContext ec = new ThirdPriceContext();
//					ec.setObj(third);
//					Expression e=null;
//					if("jd".equals(third.getThirdType())) {
//						//京东
//						e= new JdExpression();
//					} else {
//						//天猫
//						//e= new TmallExpression();
//						skip++;
//						continue;
//					}
//					e.Interpret(ec);
//					if(third.getUrlStatus()<0) {
//						e_cnt++;
//					} else {
//						s_cnt++;
//					}
//				}
//				////////////////////////////////////////////////////////////
//			} catch(Exception e) {
//				e.printStackTrace();
//				e_cnt++;
//				third.setUrlStatus(-1);
//			}
//			third.setUpdateDate(new Date());
//			productThirdPriceService.update(third);
//		}
//		////////////////////////////////////////////////////////////////////
//		logger.info("定时任务：自动比价处理完成！共处理 " + lst.size() + " 条数据。成功" + s_cnt + "条，失败"+e_cnt +"条。跳过"+skip +"条。");
//	}
	
	public static void main(String[] args) {
		ProductThirdPrice third = new ProductThirdPrice();
		third.setItemUrl("http://item.jd.com/1186673.html");
		ThirdPriceContext ec = new ThirdPriceContext();
		ec.setObj(third);
		Expression e=new JdExpression();
		e.Interpret(ec);
	}
}