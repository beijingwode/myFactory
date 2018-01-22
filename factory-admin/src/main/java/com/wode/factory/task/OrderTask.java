package com.wode.factory.task;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wode.common.db.DBUtils;
import com.wode.common.util.EmailUtil;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.Suborder;
import com.wode.factory.service.InventoryService;
import com.wode.factory.service.SubOrderService;
import com.wode.sys.service.SysUserService;
import com.wode.tongji.service.ManagerBusinessService;

/**
 * 功能说明: 订单相关定时任务类
 * 包括：
 * 订单未支付定时取消
 * 收货未确认定时确认
 * 退货未确认定时确认
 * 商户佣金收取跑数据
 * 日期:	2015年3月20日
 * 2015年11月2日
 * 开发者:	谷子夜
 * <p/>
 * 历史记录
 * 修改内容：
 * 修改人员：
 * 修改日期： 2015年3月20日
 */
@Component
public class OrderTask {

	@Autowired
	DBUtils dbUtils;

	@Autowired
	private SubOrderService subOrderService;

	@Autowired
	InventoryService invSer;
	@Autowired
	@Qualifier("emailUtil")
	private EmailUtil emailUtil;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private ManagerBusinessService managerBusinessService;
	/**
	 * 活动下单超过20分钟未支付，自动取消
	 */
//	private static Long intervalTime = (long) (20 * 60 * 1000);

	/**
	 * 下单超过1天未支付，进行自动取消
	 */
	public static int cancleDay = -1;

	private final Logger logger = LoggerFactory.getLogger(OrderTask.class);

	/**
	 * 取消订单 订单下单超过24小时未支付时自动改变订单状态为取消,每天3点执行
	 */
	@Scheduled(cron = "0 0/30 * * * ?")
	public void cancleOrder() {
		logger.debug("自动取消订单开始");
		int i = subOrderService.updateOrderSataus(0, -1, cancleDay);
		logger.info("定时任务：自动取消订单成功！ 共更新  " + i + " 条数据");

	}

	/**
	 * 确认收货 订单已发货超过15天未确认收货时自动改变订单状态为确认收货(以lasttakeTime时间，即最后确认时间为准)，每30分钟跑一次
	 * 2015-3-23
	 */
	@Scheduled(cron = "0 0/30 * * * ?")
	public void checkReceivesProduct() {
		logger.debug("自动确认收货开始");
		int i = subOrderService.updateOrderSataus(2, 4, 0);
		logger.info("定时任务：自动确认收货成功！共更新  " + i + " 条数据");
	}

	/**
	 * 自动备货开始，支付成功12小时后，商家未备货时，平台自动更新订单状态，每30分钟跑一次
	 * 2015-3-23
	 */
	@Scheduled(cron = "0 0/30 * * * ?")
	public void autoStockUp() {
		logger.debug("自动备货开始");
		int s=0,e=0;
		List<Suborder> os =  subOrderService.findByStatusAndPayTime(1, 0, TimeUtil.addDateMinut(new Date(), -720));
		for (Suborder suborder : os) {
			try{
				subOrderService.updateToStockUp(suborder.getSubOrderId(), 1);
				s++;
			} catch(Exception ex) {
				logger.info("自动备货处理异常，订单id: " + suborder.getSubOrderId());
				ex.printStackTrace();
				e++;
			}
		}
		logger.info("定时任务：自动备货完成！共更新  " + os.size() + " 条数据,成功："+s+"条，失败："+e+"条");
	}
	
	/**
	 * 确认退货 买家点退货开始计时，三天内卖家未点击回应，则自动默认为已同意退货
	 * （更新 三张表：t_suborder、t_refundorder、t_returnorder状态值status改变，退款给用户）
	 */
//	@Scheduled(cron = "0 57 23 * * ?")
//	public void agreeReturnProduct() {
//		logger.debug("自动确认退货开始");
//
//		int ret = 0;
//		//TODO 暂用退货单来做，实际引用退款单处理
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("status", 0);
//		map.put("lastTime", new Date());
//		List<Returnorder> returnorderList = returnOrderDao.findByStrtusAndLasttime(map);
//
//		logger.info("定时任务：自动确认同意退货处理完毕： " + returnorderList.size());
//	}
}