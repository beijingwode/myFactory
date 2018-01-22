package com.wode.factory.service.impl;

import java.util.Date;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.util.StringUtils;
import com.wode.factory.model.GroupBuyProduct;
import com.wode.factory.model.GroupBuyUser;
import com.wode.factory.service.GroupBuyProductService;
import com.wode.factory.service.GroupBuyUserService;
import com.wode.factory.vo.GroupBuyUserVo;
import com.wode.factory.vo.GroupBuyVo;

@Service("groupBuyUserServiceImpl")
public class GroupBuyUserServiceImpl implements GroupBuyUserService {
	
	private static Logger logger = LoggerFactory.getLogger(GroupBuyUserServiceImpl.class);
	
	@Autowired
	private Dao dao;
	
	@Autowired
	private GroupBuyProductService groupBuyProductService;
	@Override
	public Integer getAllCount(Long userId) {
		return dao.count(GroupBuyUserVo.class, Cnd.where("user_id", "=", userId));
	}

	@Override
	public GroupBuyUserVo getByUserIdAndGroupId(Long userId, String groupId) {
		if(userId!=null && !StringUtils.isEmpty(groupId)) {
			return dao.fetch(GroupBuyUserVo.class, Cnd.where("user_id", "=", userId).and("group_id","=",groupId));
		}
		return null;
	}

	@Override
	public Integer getApplyByShop(Long shopId,Long userId, String productIds) {
		String[] split = null;
		if(!StringUtils.isEmpty(productIds)){
			split = productIds.split(",");
		}
		List<GroupBuyVo> sList = getChooseGroupBuyList(shopId, userId, split);
		if(sList!=null && !sList.isEmpty()) {
			return sList.size();
		}else {
			return 0;
		}
	}

	private List<GroupBuyVo> getChooseGroupBuyList(Long shopId, Long userId, String[] productIds) {
		StringBuilder sb = new StringBuilder();
		// aid 暂时作为是否已参团标致
		sb.append(" SELECT g.* ");
		sb.append(" FROM t_group_buy g ");
		sb.append(" INNER JOIN (");
		sb.append(" SELECT gp.group_id,COUNT(1) p_cnt ");						// 当前店铺，当前用户
		sb.append(" FROM t_group_buy_product gp  ");			// 团已经建立  未下单，
		sb.append(" INNER JOIN t_group_buy_user gu ON (gu.group_id = gp.group_id) ");			// 团已经建立  未下单，
		sb.append(" WHERE gu.user_id = @userId ");
		sb.append(" AND gp.product_id  IN (");
		sb.append(productIds[0]);
		for(int i=1;i<productIds.length;i++) {
			sb.append(",").append(productIds[i]);
		}
		sb.append(" ) ");
		sb.append(" GROUP BY gp.group_id ");
		sb.append(" ) gup ON(gup.group_id = g.id)");
		sb.append(" WHERE	g.shop_id = @shopId ");						// 当前店铺，当前用户
		sb.append(" AND g.`status` = 1 ");			// 团已经建立  未下单，
		sb.append(" AND g.order_status = 0 ");
		sb.append(" AND (g.limited_time = 0 OR (g.limited_time = 1 AND g.end_time> sysdate()))");
		sb.append(" AND gup.p_cnt=@size ");
		sb.append(" ORDER BY g.create_time DESC ");
		Sql sql = Sqls.create(sb.toString());
		sql.params().set("shopId",shopId);
		sql.params().set("userId",userId);
//		sql.params().set("productIds",productIds);
		sql.params().set("size",productIds.length);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(GroupBuyVo.class));
		dao.execute(sql);
		List<GroupBuyVo> sList = sql.getList(GroupBuyVo.class);
		return sList;
	}
	
	/**
	 * 获取受邀请的购物团列表
	 */
	@Override
	public List<GroupBuyVo> getGroupBuyList(Long userId, Long shopId,String productIds) {
		String[] split = null;
		if(!StringUtils.isEmpty(productIds)){
			split = productIds.split(",");
		}
		List<GroupBuyVo> sList = getChooseGroupBuyList(shopId, userId, split);
		if(sList!=null && !sList.isEmpty()) {
			for (GroupBuyVo groupBuyVo : sList) {
				List<GroupBuyProduct> groupBuyProductList = groupBuyProductService.findByGroupId(groupBuyVo.getId());
				groupBuyVo.setGroupBuyProductList(groupBuyProductList);
			}
			return sList;
		}else {
			return null;
		}
	}

//	@Override
//	public Object test(Long id) {
//		List<GroupBuyUserVo> list = dao.query(GroupBuyUserVo.class, Cnd.where("user_id", "=", id));
//		for (GroupBuyUser groupBuyUser : list) {
//			List<GroupOrders> query = dao.query(GroupOrders.class, Cnd.where("group_id","=",groupBuyUser.getGroupId()));
//			for (GroupOrders groupOrders : query) {
//				List<GroupSuborder> query2 = dao.query(GroupSuborder.class, Cnd.where("orderId","=",groupOrders.getOrderId()));
//				for (GroupSuborder groupSuborder : query2) {
//					List<GroupSuborderitem> query3 = dao.query(GroupSuborderitem.class, Cnd.where("subOrderId","=",groupSuborder.getSubOrderId()));
//				}
//			}
//		}
//		return null;
//	}

	@Override
	public GroupBuyUser save(GroupBuyUser entity) {
		return dao.insert(entity);
	}

	@Override
	public void updateStatusByUserId(Long userId, Long groupId) {
		GroupBuyUser groupBuyUser = dao.fetch(GroupBuyUser.class, Cnd.where("user_id", "=", userId).and("group_id", "=", groupId));
		groupBuyUser.setStatus(1);
		dao.update(groupBuyUser);
	}

	@Override
	public void update(GroupBuyUserVo groupBuyUserVo) {
		dao.update(groupBuyUserVo);
	}

}
