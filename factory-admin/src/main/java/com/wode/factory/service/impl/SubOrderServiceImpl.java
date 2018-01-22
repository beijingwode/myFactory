/**
 *
 */
package com.wode.factory.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wode.common.constant.Constant;
import com.wode.common.constant.UserConstant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.EmailUtil;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.mapper.GroupBuyDao;
import com.wode.factory.mapper.ShopDao;
import com.wode.factory.mapper.SuborderDao;
import com.wode.factory.mapper.SuborderitemDao;
import com.wode.factory.model.GroupBuy;
import com.wode.factory.model.Orders;
import com.wode.factory.model.ProductECard;
import com.wode.factory.model.Shop;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.Supplier;
import com.wode.factory.outside.service.SmsService;
import com.wode.factory.service.OrderService;
import com.wode.factory.service.ProductECardService;
import com.wode.factory.service.SubOrderService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.UserBalanceService;
import com.wode.factory.vo.SuborderOrderVo;
import com.wode.sys.model.SysUser;
import com.wode.sys.service.SysUserService;
import com.wode.tongji.model.ManagerBusiness;
import com.wode.tongji.model.SmsTemplate;
import com.wode.tongji.service.ManagerBusinessService;


/**
 * <pre>
 * 功能说明: 子单服务实现
 * 日期:	2015年3月9日
 * 开发者:	宋艳垒
 *
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 2015年3月9日
 * </pre>
 */
@Service("subOrderService")
public class SubOrderServiceImpl implements SubOrderService {

	//private final static Logger log = LoggerFactory.getLogger(SubOrderServiceImpl.class);

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	protected UserBalanceService userBalanceService;

	@Autowired
	private SuborderDao subOrderDao;
	@Autowired
	private ShopDao shopDao;

	@Autowired
	@Qualifier("emailUtil")
	private EmailUtil emailUtil;
	@Autowired
	private OrderService orderService;
	@Autowired
	private SuborderitemDao suborderitemDao;
	@Autowired
	private SupplierService supplierService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private ManagerBusinessService managerBusinessService;

	@Autowired
	private ProductECardService productECardService;
	
	@Autowired
	private GroupBuyDao groupBuyDao;
	@Value("#{configProperties['factory.api.url']}")
	private  String apiUrl;

	private Set<String> interfaceUrl=new HashSet<String>();

	@Qualifier("creat_html_url")
	@Autowired
	public void setInterfaceUrl(String interfaceUrl) {
		String[] arr=interfaceUrl.split(",");
		for(String url:arr){
			if(!StringUtils.isEmpty(url)){
				this.interfaceUrl.add(url);
			}
		}

	}
	
	/**
	 * 改变旧的状态为新状态值
	 */
	@Override
	public int updateOrderSataus(Integer status, int newStatus, int day) {
		int i = 0;
		List<Suborder> subOrderList = new ArrayList<Suborder>();
		Map<String, Object> map = new HashMap<String, Object>();
		//自动确认收货
		if (status == 2) {
			// 先处理已发货，但没有自动确认收货的订单
			map.put("status", status);
			map.put("lasttakeTime", new Date());
			subOrderList = subOrderDao.findByStatusAndCreate(map);
			for (Suborder subOrder : subOrderList) {
				subOrder.setCloseReason("因您超时未处理，交易自动确认收货");
				subOrder.setUpdateBy(UserConstant.COMFROM);
				subOrder.setStatus(newStatus);
				subOrder.setTakeTime(new Date());
				subOrder.setCommentStatus(0);
				subOrderDao.update(subOrder);
				
				dealGroupOrder(subOrder);
			}
			
			// 再先处理未发货的自提订单
			map.put("status", 1);
			map.put("payTime", TimeUtil.addDay(new Date(), -14));
			subOrderList = subOrderDao.findSelfDeliveryPayTime(map);
			for (Suborder subOrder : subOrderList) {
				subOrder.setCloseReason("因您超时未处理，交易自动确认收货");
				subOrder.setUpdateBy(UserConstant.COMFROM);
				subOrder.setStatus(newStatus);
				subOrder.setTakeTime(new Date());
				subOrder.setCommentStatus(0);
				subOrderDao.update(subOrder);
				
				dealGroupOrder(subOrder);
			}
			
			
		}
		
		//自动取消订单
		if (status == 0) {
			map.put("status", status);
			map.put("createTime", TimeUtil.addDay(new Date(), day));
			subOrderList = subOrderDao.findByStatusAndCreate(map);
			for (Suborder subOrder : subOrderList) {
				Map<String,Object> paramMap = new HashMap<String,Object>();
				Orders order = orderService.findById(subOrder.getOrderId());
				paramMap.put("userId", order.getUserId());
				paramMap.put("subOrderId", subOrder.getSubOrderId());
				paramMap.put("closeReason", "因超时未付款，交易自动关闭");


				for(String apiurl:interfaceUrl){
					try {
						String response = HttpClientUtil.sendHttpRequest("post", apiurl.replace("creatHtml","member/autoCancelOrder"), paramMap);
						ActResult as = JsonUtil.getObject(response, ActResult.class);
						if (as.isSuccess()) {
							i++;
							break;
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return i;
	}
//
//	@Override
//	public int commission() {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("status", 3);//确认收货以及之后的状态
//		map.put("takeTimeBegin", TimeUtil.getPreviousMonthFirst());//上个月第一天
//		map.put("takeTimeEnd", TimeUtil.getCurrentMonthFirst());//本月第一天
//		List<Suborder> list = subOrderDao.findForCommission(map);
//		int i = 0;
//		for (Suborder sub : list) {
//			Commission commission = new Commission();
//			commission.setCreatTime(new Date());
//			commission.setSubOrderId(sub.getSubOrderId());
//			Supplier supplier = supplierService.findByid(sub.getSupplierId());
//			Orders order = orderService.getOrderDetailWithItems(sub.getOrderId());
//			if (supplier == null) {
//				i += 1;
//				log.error("[商品所属商家" + sub.getSupplierId() + "未查到数据]子单id=" + sub.getSubOrderId());
//				continue;
//			}
//			if (order == null) {
//				i += 1;
//				log.error("[商品所属订单" + sub.getOrderId() + "未查到数据]子单id=" + sub.getSubOrderId());
//				continue;
//			}
//			commission.setSupplierId(supplier.getId());
//			commission.setSupplierName(supplier.getComName());
//			UserFactory user = userFactoryDao.getById(order.getUserId());
//			if (user == null) {
//				i += 1;
//				log.error("[下单用户" + order.getUserId() + "未查到数据]订单id=" + order.getOrderId());
//				continue;
//			}
//			commission.setSubOrderUserId(user.getId());
//			commission.setSubOrderUserName(user.getNickName());
//			Map<String, Object> map1 = new HashMap<String, Object>();
//			map.put("subOrderId", sub.getSubOrderId());
//			List<Suborderitem> subOrderItemList = suborderitemDao.findBySubId(map1);
//			String productAll = "";
//			BigDecimal total = new BigDecimal(0);
//			int j = 0;
//			for (Suborderitem subItem : subOrderItemList) {
//				productAll += subItem.getProductId() + ",";
//				Product product = productDao.getById(subItem.getProductId());
//				if (product != null) {
//					ProductCategory productCategory = productCategoryDao.getParentCategoryByid(product.getCategoryId());
//					if (productCategory != null) {
//						//商品类目佣金比例（取值计算最后要除以100）
//						float commissionScale = productCategory.getCommissionScale() == null ? 1f : productCategory.getCommissionScale();
//						//子单项总价
//						BigDecimal totalProduct = subItem.getPrice().multiply(new BigDecimal(subItem.getNumber() == null ? 1 : subItem.getNumber()));
//						//子单项佣金
//						BigDecimal thisCommission = totalProduct.multiply(new BigDecimal(commissionScale)).divide(new BigDecimal(100));
//						//子单佣金累计
//						total = total.add(thisCommission);
//					} else {
//						j += 1;
//						log.error("[商品类目" + product.getCategoryId() + "未查到数据]商品id=" + product.getId());
//						continue;
//					}
//				} else {
//					j += 1;
//					log.error("[子单商品" + subItem.getProductId() + "未查到数据]子单项id=" + subItem.getSubOrderItemId());
//					continue;
//				}
//			}
//			commission.setProductIds(productAll);
//			commission.setCommission(total);
//			commissionDao.insert(commission);
//			log.error("[计算" + TimeUtil.getPreviousMonthFirst() + "到" + TimeUtil.getCurrentMonthFirst() + "佣金子单" + sub.getSubOrderId() + "，有误子单项" + j + "条]");
//		}
//		log.error("[计算" + TimeUtil.getPreviousMonthFirst() + "到" + TimeUtil.getCurrentMonthFirst() + "间佣金子单" + list.size() + "条，有误子单" + i + "条]");
//		return list.size();
//	}

	private void dealGroupOrder(Suborder subOrder) {
		if("1".equals(subOrder.getOrderType())) {
			// 团购订单确认 自动确认收货
			Long groupBuyId=subOrder.getRelationId();
			if(groupBuyId!=null) {
				GroupBuy group= groupBuyDao.getById(groupBuyId);
				if(group!=null) {
					group.setOrderStatus(4);
					groupBuyDao.update(group);
					
					// 推送消息
					Map<String,Object> paramPush=new HashMap<String,Object>();
					paramPush.put("groupBuyId", groupBuyId);
					paramPush.put("orderStatus", 4);	
					try{
						HttpClientUtil.sendHttpRequest("post", apiUrl+"user/pushGroupBuyMsg", paramPush);
					} catch(Exception ex) {
					}
				}
			}
		}
	}

	@Override
	public void updateToClose(String subOrderId, Long saleBillId) {
		Map<String,Object> map = new HashMap<String,Object>();

        map.put("saleBillId", saleBillId);
        map.put("subOrderId", subOrderId);
		subOrderDao.updateToClose(map);
	}

	@Override
	public void updateToStockUp(String subOrderId, Integer stockUp) {
		Map<String,Object> map = new HashMap<String,Object>();

        map.put("subOrderId", subOrderId);
        map.put("stockUp", stockUp);
		subOrderDao.updateToStockUp(map);
	}

	@Override
	public Suborder getById(String subOrderId) {
		return subOrderDao.getById(subOrderId);
	}

	@Override
	public void update(Suborder so) {
		subOrderDao.update(so);
	}

	@Override
	public List<Suborder> getsuborderIdByOrderId(Long orderId) {
		return subOrderDao.getsuborderIdByOrderId(orderId);
	}

	@Override
	public List<Suborder> findByStatusAndPayTime(Integer status, Integer stockUp, Date payTime) {
		Map<String,Object> map = new HashMap<String,Object>();

        map.put("status", status);
        map.put("stockUp", stockUp);
        map.put("payTime", payTime);
		return subOrderDao.findByStatusAndPayTime(map);
	}

	@Override
	public List<Suborder> findByProductId(Long productId) {
		
		return subOrderDao.findByProductId(productId);
	}

	@Override
	public List<Suborder> findByRelationId(Long groupId) {
		return subOrderDao.findByRelationId(groupId);
	}

	@Override
	public void pushOrderPayAndECard(String subOrderId,SmsTemplate t,SmsService sms,String outId) {
		if(StringUtils.isEmpty(subOrderId)) return;
		
		Suborder suborder = this.getById(subOrderId);
		if(suborder!=null) {
			
			StringBuffer sbPNames = new StringBuffer();
			List<Suborderitem> items= suborderitemDao.findBySubIdForView(subOrderId);
			for (Suborderitem suborderitem : items) {
				if(this.eCardSelfMotionDeliver(suborder, suborderitem.getProductId())) {
					// 电子卡券自动发货
					return;
				} else {
					sbPNames.append(suborderitem.getProductName()).append(",");
				}
			}

			if(sbPNames.length()>0) {
				// 以商家为单位 1小时内不再重复发送短信及邮件（含招商、运营）
				String d = redisUtil.getData(RedisConstant.PUST_ORDER_SUPPLIER + suborder.getSupplierId());
				if(!StringUtils.isEmpty(d)) {
					return;
				}
				redisUtil.setData(RedisConstant.PUST_ORDER_SUPPLIER + suborder.getSupplierId(), "1", 1*60*60);
				sbPNames.delete(sbPNames.length()-1, sbPNames.length());
				//向商家店铺负责人 发送短信
				Shop shop=shopDao.getById(suborder.getShopId());
				if(shop!= null && t!=null && !"1".equals(t.getStopFlg())) {
					String pnames=sbPNames.length()>15?(sbPNames.substring(0, 12)+"..."):(sbPNames.toString());
					if(!StringUtils.isEmpty(shop.getShopPhone()) && shop.getShopPhone().length()==11) {

						JSONObject joPra=new JSONObject();
						joPra.put("product", pnames);
						sms.sendSms(shop.getShopPhone(), t.getSignature(), t.getCode(), "myFactory", joPra.toJSONString(),outId, true, null);
					}
				}

				//向商家店铺负责人 发送邮件
				if(shop!=null && !StringUtils.isEmpty(shop.getShopEmail())) {
					emailUtil.sendNewOrderEmail(shop.getShopEmail(), subOrderId, sbPNames.toString());
				}
				
				//向招商经理发送邮件
				Supplier supplier = supplierService.findByid(suborder.getSupplierId());
				if(!StringUtils.isEmpty(supplier.getManagerId())) {
					SysUser manager = sysUserService.selectByPrimaryKey(supplier.getManagerId());
					if(manager!=null) {
						if(!StringUtils.isEmpty(manager.getEmail())) {
							emailUtil.sendNewOrderEmailForUs(manager.getEmail(), subOrderId, sbPNames.toString());
						}
						
						// 向 运营发出邮件
						ManagerBusiness mb = managerBusinessService.getById(manager.getId());
						if(mb!=null && !StringUtils.isEmpty(mb.getBusinessEmail())){
							emailUtil.sendNewOrderEmailForUs(mb.getBusinessEmail(), subOrderId, sbPNames.toString());										
						}
					}
				}
			}
		}
	}
	
	/**
	 * 判断自动发货的电子卡券
	 * @param suborder
	 * @param productId
	 * @return
	 */
	private boolean eCardSelfMotionDeliver(Suborder suborder,Long productId) {
		ProductECard query = new ProductECard();
		query.setSendType("1");
		query.setProductId(productId);
		List<ProductECard> list = productECardService.selectByModel(query);
		String name = "电子卡券";
		if (list.size() > 0) {
			ProductECard productECard = list.get(0);
			String cardPws = productECard.getCardPws();
			String[] split = cardPws.split("\n");
			String randomcardPws = "";
			if (null != split && split.length > 0) {
				if (split.length == 1) {
					randomcardPws = split[0];
				} else {
					int max = split.length - 1;
					int min = 0;
					Random random = new Random();
					int s = random.nextInt(max) % (max - min + 1) + min;
					randomcardPws = split[s];
					randomcardPws = randomcardPws.replace("\r","");
				}
			}
			String ecard = "{\"url\":\"" + productECard.getCardPage() + "\",\"pw\":\"" + randomcardPws + "\"}";
					
			suborder.setExpressType("14660000000000000");
			suborder.setSendTime(new Date());
			suborder.setUpdateTime(new Date());
			suborder.setE_cardInfo(ecard);
			suborder.setStatus(2);
			suborder.setUpdateBy("自动发货");
			suborder.setLasttakeTime(TimeUtil.addDay(new Date(), 15));//自动收货时间
			this.update(suborder);
			
			String msg = "您购买的商品："+productECard.getProductName()+"商家已确认。卡券密码："+randomcardPws+"，请在使用时出示，并注意妥善保管,订单编号("+suborder.getSubOrderId()+")";
			
			Orders order = orderService.findById(suborder.getOrderId());
			
			// app 推送
			pushMsg(order.getUserId(),"订单已发货",msg);
			// 微信推送
			pushWxOrderSend(order.getUserId(), suborder.getSubOrderId(), productECard.getProductName(), "全部", name, randomcardPws);
			return true;
		} else {
			return false;
		}
	}
	

	private void pushMsg(Long uid,String title,String msg) {

		Map<String,Object> paramPush=new HashMap<String,Object>();
		paramPush.put("title", title);
		paramPush.put("msg", msg);
		//app 推送
		paramPush.put("userId", uid);
		HttpClientUtil.sendHttpRequest("post", apiUrl+"user/pushMsg", paramPush);
	}
	
	private void pushWxOrderSend(Long uid,String subOrderId,String productName,String num,String expressName,String expressNo) {

		Map<String,Object> paramPush=new HashMap<String,Object>();
		paramPush.put("subOrderId", subOrderId);
		paramPush.put("productName", productName);
		paramPush.put("num", num);
		paramPush.put("expressName", expressName);
		paramPush.put("expressNo", expressNo);

		paramPush.put("type", "order_send");
		paramPush.put("userId", uid);
		
		HttpClientUtil.sendHttpRequest("post", apiUrl+"wx/templateMsgSend", paramPush);
	}

	@Override
	public void urgedDelivery(String subOrderId) {
		// 以商家为单位30分钟内不再反复催促
		String d = redisUtil.getData(RedisConstant.PUST_ORDER_URGED + subOrderId);
		if(!StringUtils.isEmpty(d)) {
			return;
		}
		redisUtil.setData(RedisConstant.PUST_ORDER_URGED + subOrderId, "1", 30*60);
		
		Map<String,Object> paramPush=new HashMap<String,Object>();
		paramPush.put("title", "发货催促");
		paramPush.put("msg", "客户希望尽快收到商品(XXXXX)，您赶紧给他发货吧，她挺着急的，订单id("+subOrderId+")");
		//app 推送
		paramPush.put("subOrderId", subOrderId);
		HttpClientUtil.sendHttpRequest("post", Constant.FACTORY_SUPPLIER_URL+"app/user/pushMsg4order", paramPush);
	}

	@Override
	public List<SuborderOrderVo> findNoComment(Map<String, Object> map) {
		return subOrderDao.findNoComment(map);
	}

	@Override
	public void delete(String id) {
		subOrderDao.delete(id);
	}
}
