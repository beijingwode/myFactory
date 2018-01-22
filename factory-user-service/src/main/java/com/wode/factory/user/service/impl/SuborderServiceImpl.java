/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.Returnorder;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.SuborderLimitTicket;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.dao.ProductDao;
import com.wode.factory.user.dao.RefundorderDao;
import com.wode.factory.user.dao.ReturnorderDao;
import com.wode.factory.user.dao.SuborderDao;
import com.wode.factory.user.dao.SuborderitemDao;
import com.wode.factory.user.dao.UserDao;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.service.ExpressComService;
import com.wode.factory.user.service.SuborderLimitTicketService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.util.ShopPushUtil;
import com.wode.factory.user.vo.OrderTypeCountVO;
import com.wode.factory.user.vo.SubOrderVo;

@Service("suborderService")
public class SuborderServiceImpl extends BaseService<Suborder, java.lang.String> implements SuborderService {
	@Autowired
	@Qualifier("suborderDao")
	private SuborderDao suborderDao;
	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	@Qualifier("suborderitemDao")
	private SuborderitemDao suborderitemDao;

	@Autowired
	@Qualifier("productDao")
	private ProductDao productDao;

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
	
	@Autowired
	@Qualifier("returnorderDao")
	private ReturnorderDao returnorderDao;
	
	@Autowired
	@Qualifier("refundorderDao")
	private RefundorderDao refundorderDao;
	
	@Autowired
	UserBalanceService userBalanceService;

    @Autowired
    private ExpressComService expressComService;
	
    @Autowired
    private Dao dao;
	private Logger logger = LoggerFactory.getLogger(SuborderServiceImpl.class);
	
	public EntityDao getEntityDao() {
		return this.suborderDao;
	}

	@Override
	public PageInfo getOrderByUserId(Long userId,Long batchId, Integer status, Integer page) {
		return this.getOrderByUserId(userId, batchId, status, page, SuborderQuery.DEFAULT_PAGE_SIZE);
	}

	@Override
	public PageInfo getOrderByUserId(Long userId,Long relationId, Integer status, Integer page,Integer pageSize) {
		SuborderQuery query = new SuborderQuery();
		query.setUserId(userId);
		if (null != status) {
			query.setStatus(status);
		}
		query.setPageNumber(page);
		query.setRelationId(relationId);
		query.setPageSize(pageSize);
		return suborderDao.findPage(query);
	}
	
	public GroupSuborder findGroupSuborderObjbyId(String subOrderId){
		GroupSuborder groupSuborder = dao.fetch(GroupSuborder.class,subOrderId);
		return groupSuborder;
	}
	/**
	 * 查询不同状态下的订单列表
	 *
	 * @param userId
	 * @param status 订单状态
	 * 	（
	 * 		0、未支付（待支付），
	 * 		1、已支付（待发货），
	 * 		2、已发货（待收货），
	 * 		3、退单申请中，
	 * 		4、已收货（待评价），
	 * 		10、买家已评价，
	 * 		11、已退货完毕，
	 * 		-1、已取消，
	 *		311、退款/售后
	 *	）
	 * @param page
	 * @param size
	 * @author 刘聪
	 * @since 2015-06-23
	 */
	public PageInfo getSubordersList(Long userId, Integer status, Integer page, Integer size) {
		// 传递参数
		SuborderQuery query = new SuborderQuery();
		query.setUserId(userId);
		query.setStatus(status);
		query.setPageNumber(page);
		query.setPageSize(size);
		if(status != null && status == 4){
			query.setCommentStatus(0);
		}
		// 获取子订单列表
		PageInfo pi = null;
		// 当订单状态为退款/售后
		if(status != null && status == 311) {
			pi = suborderDao.findRefundOrderByUserId(query);
		} else {
			pi = suborderDao.findPage(query);
		}
		// 判断查询结果
		if (pi != null && pi.getList() != null && pi.getList().size() > 0) {
			List<SubOrderVo> list = pi.getList();
			// 循环子订单，根据每个子订单ID查询订单项目列表
			for (SubOrderVo sov : list) {
				// 查询订单项目列表
				List<Suborderitem> soivList = suborderDao.getSubOrderItemById(sov.getSubOrderId());
				// 把订单项目列表保存到子订单中
				sov.setSubOrderItems(soivList);
				List<Returnorder> returnorderList = null;
				List<Refundorder> refundorderList = null;
				if(sov.getReturnOrderId()!=null){
					returnorderList = new ArrayList<Returnorder>();
					returnorderList.add(returnorderDao.getById(sov.getReturnOrderId()));
				}
				if(sov.getRefundOrderId()!=null){
					refundorderList = new ArrayList<Refundorder>();
					refundorderList.add(refundorderDao.getById(sov.getRefundOrderId()));
				}
				sov.setReturnorderList(returnorderList);
				sov.setRefundorderList(refundorderList);
			}
		}
		return pi;
	}

//	/**
//	 * 查询退款/售后状态下的订单列表
//	 *
//	 * @param userId
//	 * @param page
//	 * @param size
//	 * @author 刘聪
//	 * @since 2015-07-09
//	 */
//	public PageInfo getRefundSubordersList(Long userId, Integer page, Integer size) {
//		// 传递参数
//		SuborderQuery query = new SuborderQuery();
//		query.setUserId(userId);
//		query.setPageNumber(page);
//		query.setPageSize(size);
//		// 获取子订单列表
//		PageInfo pi = suborderDao.findRefundOrderByUserId(query);
//		// 判断查询结果
//		if (pi != null && pi.getList() != null && pi.getList().size() > 0) {
//			List<SubOrderVo> list = pi.getList();
//			// 循环子订单，根据每个子订单ID查询订单项目列表
//			for (SubOrderVo sov : list) {
//				// 查询订单项目列表
//				List<SubOrderItemVo> soivList = suborderDao.getSubOrderItemById(sov.getSubOrderId());
//				// 把订单项目列表保存到子订单中
//				sov.setSubOrderItems(soivList);
//			}
//		}
//		return pi;
//	}

	/**
	 * 查询不同状态下的订单个数
	 *
	 * @param userId
	 * @param status
	 * @author 刘聪
	 * @since 2015-06-19
	 */
	public int getOrderByUserIdCount(Long userId, Integer status, Integer commentStatus) {
		SuborderQuery query = new SuborderQuery();
		query.setUserId(userId);
		query.setCommentStatus(commentStatus);
		if (null != status) {
			query.setStatus(status);
		}
		return suborderDao.findOrderByUserIdCount(query);
	}
	

    /**
     * 查询不同状态下的订单个数
     * @param userId
     * @param status
     * @param statusFlag 评论状态
     * @author 刘聪
     * @since 2015-06-19
     */
	public List<OrderTypeCountVO> getOrderCountByUserId(Long userId, Integer status, Integer commentStatus) {
		SuborderQuery query = new SuborderQuery();
		query.setUserId(userId);
		if (null != status) {
			query.setStatus(status);
		}
		if (null != commentStatus) {
			query.setCommentStatus(commentStatus);
		}
		return suborderDao.getOrderCountByUserId(query);
	}
	
	/**
	 * 查询订单项目列表
	 *
	 * @author 刘聪
	 * @since 2015-06-23
	 */
	public List<Suborderitem> getSubOrderItemById(String subOrderId) {
		return suborderDao.getSubOrderItemById(subOrderId);
	}

	@Override
	public SubOrderVo findOrderDetailById(SuborderQuery query) {
		// 获取订单详细
		SubOrderVo sov = suborderDao.findOrderDetailById(query);
		// 当订单详细不为空时
		if(sov != null) {
			// 获取物流信息
			if(!StringUtils.isEmpty(sov.getExpressType())) {
			    ExpressCompany ci = expressComService.getExpressComById(sov.getExpressType());
			    if(ci!=null) {
//			    	sov.setExpressName(ci.getName());
			    	sov.setExpress(ci);
			    }
				/**
				 * 订单快递信息由前端自己调用接口访问
				 * 
				 * Map map = getTogistics(sov.getExpressType(), sov.getExpressNo(), query.getUserId(), 2);
				if(map != null && !StringUtils.isEmpty(map.get("listlogInfo"))) {
					sov.setListlogInfo((List<LogisticsInfo>)map.get("listlogInfo"));
				}*/
			}
			// 距离自动确认收货时间还有*天*小时
			if(!StringUtils.isEmpty(sov.getLasttakeTime())) {
				// 当前时间
				Date sysDate = new Date();
				// 当前时间与获取的最后确认发货时间相比较
				// 当最后确认发货时间>=当前时间时
				if(sov.getLasttakeTime().after(sysDate) || sov.getLasttakeTime().equals(sysDate)) {
					// 一天的毫秒数
					long dayMillisecond = 1000*60*60*24;
					// 一小时的毫秒数
					long hourMillisecond = 1000*60*60;
					// 获得两个时间的毫秒时间差
					long timeDifferenceMillisecond = sov.getLasttakeTime().getTime() - sysDate.getTime();
					// 时间差：天
					long day = timeDifferenceMillisecond/dayMillisecond;
					// 时间差：小时
					long hour = (timeDifferenceMillisecond%dayMillisecond)/hourMillisecond;
					// 保存距离自动确认收货时间还有day天hour小时
					sov.setDistanceAutomaticConfirm(day + "天" + hour + "小时");
				}
			}
		}
		return sov;
	}

	@Override
	public Suborder querySubOrder(SuborderQuery query) {
		return suborderDao.querySubOrder(query);
	}



	public List<Suborder> findByOrderId(long orderId) {
		return suborderDao.findByOrderId(orderId);
	}

	public ActResult urgedDelivery(UserFactory user, String suborderId) {
		SuborderQuery query = new SuborderQuery();
		query.setUserId(user.getId());
		query.setSubOrderId(suborderId);
		SubOrderVo subOrder = this.findOrderDetailById(query);
		if (subOrder == null) {
			return ActResult.fail("无权操作");
		}
		if (subOrder.getUrgeNumber() < 1 && TimeUtil.hourBetweenTime(subOrder.getCreateTime(), new Date()) < 24) {
			return ActResult.fail("下单24小时后能操作");
		}
		if (subOrder.getUrgeTime() != null && TimeUtil.hourBetweenTime(subOrder.getUrgeTime(), new Date()) < 24) {
			return ActResult.fail("24小时内只能操作一次");
		}
		subOrder.setUrgeNumber(subOrder.getUrgeNumber() + 1);
		subOrder.setUrgeTime(new Date());
		this.update(subOrder);

		List<String> ls =new ArrayList<String>();
		ls.add(suborderId);
		ShopPushUtil.pushMsg4order(redisUtil,ls,ShopPushUtil.PUSH_TYPE_ORDER_URGED);
		return ActResult.success(null);
	}

	@Override
	public PageInfo findReturnableOrders(Integer page, Long userId) {
		page = page == null ? 1 : page;
		SuborderQuery sq = new SuborderQuery();
		sq.setUserId(userId);
		sq.setPageNumber(page);
		return suborderDao.findReturnableOrders(sq);
	}

	public ActResult delete(UserFactory user, String subOrderId) {
		SuborderQuery query = new SuborderQuery();
		query.setUserId(user.getId());
		query.setSubOrderId(subOrderId);
		SubOrderVo subOrder = this.findOrderDetailById(query);
		if (subOrder == null) {
			logger.error(user.toString() + "删除订单失败:无权操作");
			return ActResult.fail("无权操作");
		}
		/*if(subOrder.getStatus()==-1){//订单已关闭(已删除)
			return ActResult.fail("订单已删除");
		}*/
		if (subOrder.getStatus() != -1) {
			if(subOrder.getStatus()!=4 && subOrder.getStatus()!=11 && subOrder.getStatus()!=12 &&
					subOrder.getStatus()!=-11 && subOrder.getStatus()!=-12){
				logger.error(user.toString() + "删除订单失败:订单状态=" + subOrder.getStatus());
				return ActResult.fail("状态有误");
			}	
		}

		if (subOrder != null) {
			subOrder.setDeleteFlag(true);
			this.update(subOrder);
		}

		return ActResult.success(subOrder);

	}

	@Override
	public List<SubOrderVo> findSubOrdersByOrderId(Long userId, Long orderId) {
		SuborderQuery query = new SuborderQuery();
		query.setUserId(userId);
		query.setOrderId(orderId);
		return suborderDao.findSubOrdersByOrderId(query);
	}

	@Override
	public  List<GroupSuborder> findGroupSuborderbyId(Long orderId) {
		
		return dao.query(GroupSuborder.class, Cnd.where("orderId", "=", orderId));
	}

	@Override
	public void updateGroupSbuorder(GroupSuborder groupSuborder) {
		dao.update(groupSuborder);
	}

//	@Override
//	public Map getTogistics(String expressType, String expressNo, Long userId,
//			int type) {
//		Map resMap = new HashMap();
//		//物流信息
//		Map comMap=new HashMap();
//		comMap.put("id", expressType);
//		String comStr = HttpClientUtil.sendHttpRequest("post", kuaidiApiUrl+"express/getByExpressId.do", comMap);
//		CompInfo ci = JsonUtil.getObject(comStr, CompInfo.class);
//		if(type == 2){
//			resMap.put("compInfo", ci);
//		}
//		if(ci != null && ci.getBody()!= null){
//			ExpressCompany ec = ci.getBody();
//			Map param = new HashMap();
//			Map paramMap=new HashMap();
//			paramMap.put("sname", "express.ExpressSearch");
//			if(ec != null){
//				paramMap.put("com", ec.getPinYin());
//			}
//			paramMap.put("express_no", expressNo);
//			paramMap.put("user", userId);
//			paramMap.put("version","v2");
//			param.put("content", JsonUtil.toJson(paramMap));
//			String response=HttpClientUtil.sendHttpRequest("post", kuaidiApiUrl+"SimpleJsonApi/bus.do", param);
//			Response retRes = JsonUtil.getObject(response, Response.class);
//			if(retRes != null && retRes.getBody()!=null){
//				Body body = retRes.getBody();
//				List<LogisticsInfo> ListlogInfo = body.getHistory();
//				resMap.put("listlogInfo", ListlogInfo);
//			}
//		}
//		return resMap;
//	}
}


