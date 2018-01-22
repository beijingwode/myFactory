/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.RefundorderAttachment;
import com.wode.factory.model.Returnorder;
import com.wode.factory.model.ReturnorderAttachment;
import com.wode.factory.model.Returnorderitem;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.user.dao.RefundorderAttachmentDao;
import com.wode.factory.user.dao.RefundorderDao;
import com.wode.factory.user.dao.ReturnorderAttachmentDao;
import com.wode.factory.user.dao.ReturnorderDao;
import com.wode.factory.user.dao.ReturnorderItemDao;
import com.wode.factory.user.dao.SuborderDao;
import com.wode.factory.user.dao.SuborderitemDao;
import com.wode.factory.user.dao.UserDao;
import com.wode.factory.user.query.ReturnorderQuery;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.service.ReturnorderService;
import com.wode.factory.user.vo.SubOrderVo;

@Service("returnorderService")
public class ReturnorderServiceImpl extends BaseService<Returnorder, java.lang.Long> implements ReturnorderService {
	@Autowired
	@Qualifier("returnorderDao")
	private ReturnorderDao returnorderDao;

	@Autowired
	@Qualifier("returnorderItemDao")
	private ReturnorderItemDao returnorderItemDao;

	@Autowired
	@Qualifier("returnorderAttachmentDao")
	private ReturnorderAttachmentDao returnorderAttachmentDao;

	@Autowired
	@Qualifier("refundorderDao")
	private RefundorderDao refundorderDao;

	@Autowired
	@Qualifier("suborderDao")
	private SuborderDao suborderDao;

	@Autowired
	@Qualifier("suborderitemDao")
	private SuborderitemDao suborderitemDao;

	@Autowired
	@Qualifier("refundorderAttachmentDao")
	private RefundorderAttachmentDao refundorderAttachmentDao;
	
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
	
	@Autowired
	private Dao dao;
	

	public EntityDao getEntityDao() {
		return this.returnorderDao;
	}

	public PageInfo findPage(ReturnorderQuery query) {
		return returnorderDao.findPage(query);
	}

	public PageInfo getReturnOrders(Integer page, Long userId, Integer status) {
		ReturnorderQuery rq = new ReturnorderQuery();
		rq.setPageNumber(page == null ? 1 : page);
		rq.setStatus(status);
		rq.setUserId(userId);
		return findPage(rq);
	}

	@Override
	public boolean createReturnOrder(Returnorder returnOrder, Long subOrderItemId, String[] images) {
		boolean result = false;
		SuborderQuery query = new SuborderQuery();
		query.setUserId(returnOrder.getUserId());
		query.setSubOrderId(returnOrder.getSubOrderId());
		SubOrderVo subOrder = this.suborderDao.findOrderDetailById(query);//检查订单是否有效
		if (null != subOrder && (subOrder.getStatus() == 4) || (subOrder.getStatus() == 2)) {//已收货、或待确认收货都可以维权
			returnorderDao.save(returnOrder);//生成退货单
			if (!StringUtils.isNullOrEmpty(subOrderItemId)) {//退单项商品
				Suborderitem soi = suborderitemDao.getById(subOrderItemId);
				if (soi == null) {
					return false;
				}
				Returnorderitem returnOrderItem = new Returnorderitem();
				returnOrderItem.setReturnOrderId(returnOrder.getReturnOrderId());
				returnOrderItem.setPartNumber(soi.getPartNumber());
				returnOrderItem.setPrice(soi.getPrice());
				returnOrderItem.setNumber(soi.getNumber());
				returnOrderItem.setCreateTime(new Date());
				returnOrderItem.setSubOrderItemId(subOrderItemId);
				returnorderItemDao.save(returnOrderItem);//生成退货单项
			} else {
				List<Suborderitem> items = suborderitemDao.findBySubOrderId(subOrder.getSubOrderId());
				for (Suborderitem item : items) {//退整单商品
					Returnorderitem returnOrderItem = new Returnorderitem();
					returnOrderItem.setReturnOrderId(returnOrder.getReturnOrderId());
					returnOrderItem.setPartNumber(item.getPartNumber());
					returnOrderItem.setPrice(item.getPrice());
					returnOrderItem.setNumber(item.getNumber());
					returnOrderItem.setCreateTime(new Date());
					returnOrderItem.setSubOrderItemId(item.getSubOrderItemId());
					returnorderItemDao.save(returnOrderItem);//生成退货单项
				}
			}

			for (String image : images) {
				if (!image.trim().equals("")) {
					ReturnorderAttachment returnorderAttachment = new ReturnorderAttachment();
					returnorderAttachment.setReturnOrderId(returnOrder.getReturnOrderId());
					returnorderAttachment.setImage(image);
					returnorderAttachmentDao.save(returnorderAttachment);//生存凭证
				}
			}

//            Returnorderlog returnOrderLog = new Returnorderlog();
//            returnOrderLog.setReturnOrderId(returnOrder.getReturnOrderId());
//            returnOrderLog.setStatus(returnOrder.getStatus() + "");
//            returnOrderLog.setCreateTime(new Date());
//            returnorderlogDao.save(returnOrderLog);//生产退货单日志

			Refundorder refundOrder = new Refundorder();
			refundOrder.setReturnOrderId(returnOrder.getReturnOrderId());
			refundOrder.setRefundPrice(returnOrder.getReturnPrice());
			refundOrder.setStatus(1);
			refundOrder.setCreateTime(new Date());
			refundOrder.setUserId(returnOrder.getUserId());
			refundOrder.setLastTime(returnOrder.getLastTime());
			refundorderDao.save(refundOrder);//退款单

//            Refundorderlog refundOrderLog = new Refundorderlog();
//            refundOrderLog.setRefundOrderId(refundOrder.getRefundOrderId());
//            refundOrderLog.setStatus(refundOrder.getStatus() + "");
//            refundOrder.setCreateTime(new Date());
//            refundorderLogDao.save(refundOrderLog);//退款单日志

			subOrder.setStatus(3);
			subOrder.setReturnOrderId(returnOrder.getReturnOrderId());
			suborderDao.update(subOrder);//更改子单状态为 退货申请
			result = true;
		}
		return result;
	}


	/**
	 * 退货退款/仅退款申请
	 *
	 * @param type        申请状态（0：退货退款；1：仅退款）
	 * @param returnOrder
	 * @param images
	 * @return
	 */
	@Override
	public Map<String, Object> applyReturn(Integer type, Returnorder returnOrder,
	                                       Integer goodsStaus, List<String> imgPathList) {
		// 默认返回正常状态
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", true);
		// 创建子订单详细的查询条件（用户ID和子订单号）
		SuborderQuery query = new SuborderQuery();
		query.setUserId(returnOrder.getUserId());
		query.setSubOrderId(returnOrder.getSubOrderId());
		// 根据用户ID和子订单号查询子订单详细
		SubOrderVo subOrder = this.suborderDao.findOrderDetailById(query);
		//获取订单
		List<GroupSuborder> groupSuborderList = dao.query(GroupSuborder.class, Cnd.where("subOrderId", "=",query.getSubOrderId()));
		subOrder.setUpdateTime(new Date());//更新时间
		subOrder.setUpdateBy(userDao.getById(returnOrder.getUserId()).getUserName());//更新者姓名
		//判断是否为团订单
			if(null == groupSuborderList || groupSuborderList.size() ==0){
				// 当订单不存在时
				if (subOrder != null) {
					// 当申请退货退款时
					if (type == 0) {
						// 当状态为2（已发货）或4（已收货）可以申请退货退款 或为1（已支付）
						if ((subOrder.getStatus() == 2) || (subOrder.getStatus() == 4) ||(subOrder.getStatus() == 1)) {
							// 生成退货单
							returnorderDao.save(returnOrder);
							// 获取退后订单所包含的商品
							List<Suborderitem> items = suborderitemDao.findBySubOrderId(subOrder.getSubOrderId());
							// 退整个订单的商品
							for (Suborderitem item : items) {
								Returnorderitem returnOrderItem = new Returnorderitem();
								returnOrderItem.setReturnOrderId(returnOrder.getReturnOrderId());
								returnOrderItem.setPartNumber(item.getPartNumber());
								returnOrderItem.setPrice(item.getPrice());
								returnOrderItem.setNumber(item.getNumber());
								returnOrderItem.setCreateTime(returnOrder.getCreateTime());
								returnOrderItem.setSubOrderItemId(item.getSubOrderItemId());
								// 生成退货单项
								returnorderItemDao.save(returnOrderItem);
							}
							// 保存退货图片凭证
							if (imgPathList != null && imgPathList.size() > 0) {
								for (String imgPath : imgPathList) {
									if (!StringUtils.isNullOrEmpty(imgPath)) {
										ReturnorderAttachment returnorderAttachment = new ReturnorderAttachment();
										returnorderAttachment.setReturnOrderId(returnOrder.getReturnOrderId());
										returnorderAttachment.setImage(imgPath);
										returnorderAttachmentDao.save(returnorderAttachment);
									}
								}
							}
		//    	            // 生产退货单日志
		//    	            Returnorderlog returnOrderLog = new Returnorderlog();
		//    	            returnOrderLog.setReturnOrderId(returnOrder.getReturnOrderId());
		//    	            returnOrderLog.setStatus(returnOrder.getStatus() + "");
		//    	            returnOrderLog.setCreateTime(returnOrder.getCreateTime());
		//    	            returnorderlogDao.save(returnOrderLog);
							// 生成退款单
							Refundorder refundOrder = new Refundorder();
							refundOrder.setReturnOrderId(returnOrder.getReturnOrderId());
							refundOrder.setReason(returnOrder.getReason());
							refundOrder.setNote(returnOrder.getNote());
							refundOrder.setRefundPrice(returnOrder.getReturnPrice());
							refundOrder.setStatus(1);
							refundOrder.setCreateTime(returnOrder.getCreateTime());
							refundOrder.setUserId(returnOrder.getUserId());
							if(goodsStaus==null){
								refundOrder.setGoodsStatus(1);
							}else{
								refundOrder.setGoodsStatus(goodsStaus);
							}
							refundorderDao.save(refundOrder);
		//    	            // 生成退款单日志
		//    	            Refundorderlog refundOrderLog = new Refundorderlog();
		//    	            refundOrderLog.setRefundOrderId(refundOrder.getRefundOrderId());
		//    	            refundOrderLog.setStatus(refundOrder.getStatus() + "");
		//    	            refundOrder.setCreateTime(returnOrder.getCreateTime());
		//    	            refundorderLogDao.save(refundOrderLog);
							// 更改子单状态为退货申请中
							subOrder.setStatus(3);
							subOrder.setAfterserviceStatus(1);
							subOrder.setReturnOrderId(returnOrder.getReturnOrderId());
							subOrder.setRefundOrderId(refundOrder.getRefundOrderId());
							suborderDao.update(subOrder);
						} else {
							result.put("status", false);
							result.put("msg", "当前订单状态不可申请退货退款");
						}
					} else if (type == 1) {
						// 当申请仅退款时
						// 当状态为1（已支付）或2（已发货）或4（已收货）可以申请仅退款
						// 4（已收货）可以申请仅退款   -2015-11-26 gaoyj
						if ((subOrder.getStatus() == 2) || (subOrder.getStatus() == 4) ||(subOrder.getStatus() == 1)) {
							// 生成退款单
							Refundorder refundOrder = new Refundorder();
							refundOrder.setRefundPrice(returnOrder.getReturnPrice());
							refundOrder.setReason(returnOrder.getReason());
							if(goodsStaus==null){
								refundOrder.setGoodsStatus(1);
							}else{
								refundOrder.setGoodsStatus(goodsStaus);
							}
							refundOrder.setStatus(1);
							refundOrder.setCreateTime(returnOrder.getCreateTime());
							refundOrder.setNote(returnOrder.getNote());
							refundOrder.setLastTime(returnOrder.getLastTime());
							refundOrder.setUserId(returnOrder.getUserId());
							refundorderDao.save(refundOrder);
		//    	            // 生成退款单日志
		//    	            Refundorderlog refundOrderLog = new Refundorderlog();
		//    	            refundOrderLog.setRefundOrderId(refundOrder.getRefundOrderId());
		//    	            refundOrderLog.setStatus(refundOrder.getStatus() + "");
		//    	            refundOrderLog.setCreateTime(returnOrder.getCreateTime());
		//    	            refundorderLogDao.save(refundOrderLog);
							// 保存仅退款图片凭证
							if (imgPathList != null && imgPathList.size() > 0) {
								for (String imgPath : imgPathList) {
									if (!StringUtils.isNullOrEmpty(imgPath)) {
										RefundorderAttachment refundorderAttachment = new RefundorderAttachment();
										refundorderAttachment.setRefundOrderId(refundOrder.getRefundOrderId());
										refundorderAttachment.setImage(imgPath);
										refundorderAttachmentDao.save(refundorderAttachment);
									}
								}
							}
							// 更改子单状态为仅退款申请中
							subOrder.setStatus(5);
							subOrder.setAfterserviceStatus(1);
							subOrder.setRefundOrderId(refundOrder.getRefundOrderId());
							suborderDao.update(subOrder);
						} else {
							result.put("status", false);
							result.put("msg", "当前订单状态不可申请退款");
						}
					}
				} else {
					result.put("status", false);
					result.put("msg", "订单不存在");
				}
			}else{
				result.put("status", false);
				result.put("msg", "团购订单不能退货");
			}
		return result;
	}

	/**
	 * 延长收货
	 *
	 * @param subOrderId 子订单ID
	 * @return
	 */
	public ActResult<Object> extendedReceipt(String subOrderId) {
		// 创建默认返回结果
		ActResult<Object> result = new ActResult<Object>();
		result.success("延长收货成功");
		// 获取根据子订单ID获取子订单信息
		Suborder subOrder = suborderDao.getById(subOrderId);
		// 当子订单不存在时
		if (subOrder == null) {
			result.fail("订单不存在");
		} else {
			// 当子订单状态不为2（已发货）状态时
			if (subOrder.getStatus() != 2) {
				result.fail("当前订单状态不可延长收货");
			} else {
				// 获取用户（非商家）延长收货的次数
				Integer userExetendCount = subOrder.getUserExetendCount();

				if (StringUtils.isNullOrEmpty(userExetendCount)) {
					userExetendCount = 0;
				}
				// 当用户（非商家）延长收货的次数不为0时
				if (userExetendCount != 0) {
					result.fail("延长收货失败，用户仅可延长收货一次");
				} else {
					// 获取最后确认时间
					Date lastTakeTime = subOrder.getLasttakeTime();
					// 创建最后确认时间日历
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(lastTakeTime);
					// 最后确认时间+3天
					calendar.add(Calendar.DAY_OF_MONTH, 3);

					// 修改最后确认时间
					subOrder.setLasttakeTime(calendar.getTime());
					// 用户（非商家）延长收货的次数+1
					subOrder.setUserExetendCount(userExetendCount + 1);
					// 修改数据
					suborderDao.update(subOrder);
					calendar.clear();
				}
			}
		}
		return result;
	}

	@Override
	public Returnorder getReturnOrdersById(Long returnOrderId) {
		return returnorderDao.getReturnOrdersById(returnOrderId);
	}

	@Override
	public Map<String, Object> updateReturn(Integer type, Returnorder returnOrder, Long refundOrderId,
			Integer goodsStatus, List<String> imgPathList) {
		// 默认返回正常状态
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", true);
		// 创建子订单详细的查询条件（用户ID和子订单号）
		SuborderQuery query = new SuborderQuery();
		query.setUserId(returnOrder.getUserId());
		query.setSubOrderId(returnOrder.getSubOrderId());
		// 根据用户ID和子订单号查询子订单详细
		SubOrderVo subOrder = this.suborderDao.findOrderDetailById(query);
				
		// 当订单不存在时
		if (subOrder != null) {
			Long returnOrderId = returnOrder.getReturnOrderId();
			if(returnOrderId!=null){
				if (type == 0) {//没有修改
					Returnorder old = returnorderDao.getById(returnOrderId);
						if(3==old.getStatus()){
							old.setStatus(0);
							subOrder.setStatus(3);
						}else if(6==old.getStatus()){
							old.setStatus(4);
							subOrder.setStatus(13);
						}
						old.setNote(returnOrder.getNote());
						old.setReturnPrice(returnOrder.getReturnPrice());
						old.setReason(returnOrder.getReason());
						returnorderDao.update(old);
						// 保存退货图片凭证
						if (imgPathList != null && imgPathList.size() > 0) {
							returnorderAttachmentDao.deleteByReturnOrderId(returnOrderId);
							for (String imgPath : imgPathList) {
								if (!StringUtils.isNullOrEmpty(imgPath)) {
									ReturnorderAttachment returnorderAttachment = new ReturnorderAttachment();
									returnorderAttachment.setReturnOrderId(returnOrder.getReturnOrderId());
									returnorderAttachment.setImage(imgPath);
									returnorderAttachmentDao.save(returnorderAttachment);
								}
							}
						}
						//退款单
						Refundorder oldRefunOrder = refundorderDao.getById(subOrder.getRefundOrderId());
						oldRefunOrder.setRefundPrice(returnOrder.getReturnPrice());
						oldRefunOrder.setReason(returnOrder.getReason());
						oldRefunOrder.setNote(returnOrder.getNote());
						refundorderDao.update(oldRefunOrder);
						suborderDao.update(subOrder);
						
				}else if(type==1){//修改类型
					//清空旧数据
					returnorderDao.deleteById(returnOrderId);
					refundorderDao.deleteById(subOrder.getRefundOrderId());
					returnorderAttachmentDao.deleteByReturnOrderId(returnOrderId);
				
					//保存新数据
					Refundorder refundOrder = new Refundorder();
					refundOrder.setRefundPrice(returnOrder.getReturnPrice());
					refundOrder.setReason(returnOrder.getReason());
					if (goodsStatus==null) {
						refundOrder.setGoodsStatus(1);
					}else{
						refundOrder.setGoodsStatus(goodsStatus);
					}
					refundOrder.setStatus(1);
					refundOrder.setCreateTime(returnOrder.getCreateTime());
					refundOrder.setNote(returnOrder.getNote());
					refundOrder.setLastTime(returnOrder.getLastTime());
					refundOrder.setUserId(returnOrder.getUserId());
					refundorderDao.save(refundOrder);
					if (imgPathList != null && imgPathList.size() > 0) {
						for (String imgPath : imgPathList) {
							if (!StringUtils.isNullOrEmpty(imgPath)) {
								RefundorderAttachment refundorderAttachment = new RefundorderAttachment();
								refundorderAttachment.setRefundOrderId(refundOrder.getRefundOrderId());
								refundorderAttachment.setImage(imgPath);
								refundorderAttachmentDao.save(refundorderAttachment);
							}
						}
					}
					//修改suborder
					subOrder.setRefundOrderId(refundOrder.getRefundOrderId());
					subOrder.setReturnOrderId(refundOrder.getReturnOrderId());
					subOrder.setStatus(5);
					suborderDao.update(subOrder);
				}
			}
			if(refundOrderId!=null){
				if(type==1){//无修改类型
					Refundorder oldRefun = refundorderDao.getById(refundOrderId);
					oldRefun.setRefundPrice(returnOrder.getReturnPrice());
					oldRefun.setReason(returnOrder.getReason());
					if(goodsStatus==null){
						oldRefun.setGoodsStatus(1);
					}else{
						oldRefun.setGoodsStatus(goodsStatus);
					}
					oldRefun.setStatus(1);
					oldRefun.setNote(returnOrder.getNote());
					oldRefun.setLastTime(returnOrder.getLastTime());
					oldRefun.setUserId(returnOrder.getUserId());
					refundorderDao.update(oldRefun);
					if (imgPathList != null && imgPathList.size() > 0) {
						refundorderAttachmentDao.deleteByRefundOrderId(refundOrderId);
						for (String imgPath : imgPathList) {
							if (!StringUtils.isNullOrEmpty(imgPath)) {
								RefundorderAttachment refundorderAttachment = new RefundorderAttachment();
								refundorderAttachment.setRefundOrderId(oldRefun.getRefundOrderId());
								refundorderAttachment.setImage(imgPath);
								refundorderAttachmentDao.save(refundorderAttachment);
							}
						}
					}
					subOrder.setStatus(5);
					suborderDao.update(subOrder);
				}else{//修改类型
					//清空旧数据
					refundorderDao.deleteById(subOrder.getRefundOrderId());
					refundorderAttachmentDao.deleteByRefundOrderId(refundOrderId);
					
					//保存新数据
					returnOrder.setStatus(0);
					returnorderDao.save(returnOrder);
					//生成退货凭证
					if (imgPathList != null && imgPathList.size() > 0) {
						for (String imgPath : imgPathList) {
							if (!StringUtils.isNullOrEmpty(imgPath)) {
								ReturnorderAttachment returnorderAttachment = new ReturnorderAttachment();
								returnorderAttachment.setReturnOrderId(returnOrder.getReturnOrderId());
								returnorderAttachment.setImage(imgPath);
								returnorderAttachmentDao.save(returnorderAttachment);
							}
						}
					}
					// 生成退款单
					Refundorder refundOrder = new Refundorder();
					refundOrder.setReturnOrderId(returnOrder.getReturnOrderId());
					refundOrder.setReason(returnOrder.getReason());
					refundOrder.setNote(returnOrder.getNote());
					refundOrder.setRefundPrice(returnOrder.getReturnPrice());
					refundOrder.setStatus(1);
					if(goodsStatus==null){
						refundOrder.setGoodsStatus(1);
					}else{
						refundOrder.setGoodsStatus(goodsStatus);
					}
					refundOrder.setCreateTime(returnOrder.getCreateTime());
					refundOrder.setUserId(returnOrder.getUserId());
					refundorderDao.save(refundOrder);
					//修改suborder
					subOrder.setStatus(3);
					subOrder.setRefundOrderId(refundOrder.getRefundOrderId());
					subOrder.setReturnOrderId(returnOrder.getReturnOrderId());
					suborderDao.update(subOrder);
				}
			}
		} else {
			result.put("status", false);
			result.put("msg", "订单不存在");
		}
		return result;
	}
}
