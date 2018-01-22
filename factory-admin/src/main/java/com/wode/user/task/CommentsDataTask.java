package com.wode.user.task;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.factory.mapper.ProductDao;
import com.wode.factory.mapper.SupplierMapper;
import com.wode.factory.model.Comments;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.SubOrderService;
import com.wode.factory.service.UserFactoryService;
import com.wode.factory.service.impl.SuborderItemServiceImpl;
import com.wode.factory.vo.SuborderOrderVo;

@Service
public class CommentsDataTask {
	@Autowired
	private SubOrderService subOrderService;
	@Autowired
	private SuborderItemServiceImpl SuborderItemService;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private UserFactoryService userFactoryService;
	@Autowired
	private SupplierMapper supplierMapper;
	
	@Value("#{configProperties['factory.api.url']}")
	private  String apiUrl;
	
	private final Logger logger = LoggerFactory.getLogger(CommentsDataTask.class);
	/**
	 * 添加商品历史数据每天统计信息
	 */
	public void run() {
		// 获取当前系统时间
		Calendar date = Calendar.getInstance();
		// 设置创建时间 将月份减3表示当前时间的三个月时间
		date.add(Calendar.DAY_OF_MONTH, -15);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("takeTime", date.getTime());//确认收货时间
		map.put("status", 4);//订单状态已收货
		map.put("commentStatus", 0);//为未评价
		AutoComment(map);
		
		
	}
	/**
	 * 系统自动评价
	 * @param map
	 */
	private void AutoComment(Map<String, Object> map) {
		logger.info("..............select suborder start................");
		List<SuborderOrderVo> subOrderList = subOrderService.findNoComment(map);
		logger.info("..............select suborder end................");
		String api = apiUrl + "order/comments/saveComments.tj";
		//String api = "http://192.168.10.27:8080/api/" + "order/comments/saveComments.tj";
		Map<String, Object> paramPush = new HashMap<String, Object>();
		if(subOrderList!=null && subOrderList.size()>0) {
			Suborderitem suborderItem = new Suborderitem();
			for (SuborderOrderVo suborder : subOrderList) {
				suborderItem.setSubOrderId(suborder.getSubOrderId());
				logger.info("..............select suborderitem start................");
				List<Suborderitem> suborderItemList = SuborderItemService.selectByModel(suborderItem);
				logger.info("..............select suborderitem end................");
				if(suborderItemList!=null && suborderItemList.size()>0) {
					for (Suborderitem subItem : suborderItemList) {
						//未评价 且不是试用返现商品
						if((subItem.getCommentFlag()==null || subItem.getCommentFlag()==0) 
								&& (subItem.getSaleKbn()==null || subItem.getSaleKbn() != 5)) {
							
							paramPush.put("anonymous", "1");
							paramPush.put("productId", subItem.getProductId());
							paramPush.put("creatTime", new Date());
							paramPush.put("star1", 5);
							paramPush.put("star2", 5);
							paramPush.put("star3", 5);
							paramPush.put("text", "");
							paramPush.put("userId", suborder.getUserId());
							paramPush.put("commentDegree", 5);
							paramPush.put("subOrderid", suborder.getSubOrderId());
							paramPush.put("subOrderItemId", subItem.getSubOrderItemId());
							paramPush.put("productId", subItem.getProductId());
							paramPush.put("supplyid", suborder.getSupplierId());
							paramPush.put("attributeJson", subItem.getItemValues());
							try {
								
								logger.info("..............save comment start................");
								String result = HttpClientUtil.sendHttpRequest("post", api, paramPush);
								logger.info("..............save comment end................");
								ActResult as = JsonUtil.getObject(result, ActResult.class);
								if(!as.isSuccess()){
									logger.info(""+as.getMsg());
						        }
							} catch (Exception ex) {
								System.out.println(ex.toString());
							}
						}
					}
				}
			}
		}
	}
}
