package com.wode.factory.user.service.impl;

import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.db.DBUtils;
import com.wode.factory.model.GroupSuborderitem;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.Shop;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.user.service.GroupSuborderItemService;

@Service("groupSuborderItemService")
public class GroupSuborderItemServiceImpl implements GroupSuborderItemService {
	@Autowired
	private Dao dao;
	
	@Autowired
    DBUtils dbUtils;
	
	@Override
	public void save(GroupSuborderitem groupSuborderitem) {
		groupSuborderitem.setSubOrderItemId(dbUtils.CreateID());
		dao.insert(groupSuborderitem);
	}

	@Override
	public void update(GroupSuborderitem subOrderItem) {
		dao.update(subOrderItem);
	}

	@Override
	public List<GroupSuborderitem> findBySubOrderId(String subOrderId) {
		return dao.query(GroupSuborderitem.class, Cnd.where("subOrderId","=",subOrderId));
	}

	@Override
	public Shop findByShopId(Long shopId) {
		return dao.fetch(Shop.class,Cnd.where("id","=",shopId));
	}

	@Override
	public GroupSuborderitem findBySubOrderIdObj(String subOrderId) {
		return dao.fetch(GroupSuborderitem.class,Cnd.where("subOrderId", "=", subOrderId));
	}

	@Override
	public List<GroupSuborderitem> findByOrderId(Long orderId) {
		return dao.query(GroupSuborderitem.class, Cnd.where("order_id","=",orderId));
	}
	/**
	 * 通过productId和用户id获取团购中该商品数量
	 */
	@Override
	public Integer getCountByUserAndProduct(Long productId, Long userId) {
		Sql sqlCount = Sqls.create("SELECT sum(number) FROM t_group_suborderitem soi INNER JOIN t_group_orders o ON (o.orderId = soi.order_id)"
				+ "INNER JOIN t_group_suborder s ON (s.subOrderId = soi.subOrderId AND s.`status` != - 1 AND s.`status` != 0) WHERE soi.productId =@pid AND o.userId = @uid");
		sqlCount.params().set("pid",productId);
		sqlCount.params().set("uid",userId);
		sqlCount.setCallback(Sqls.callback.integer());
		dao.execute(sqlCount);
		Integer cnt = sqlCount.getInt();
		return cnt;
	}

	/**
	 * /**
	 * 根据团id和商品id以及skuid获取商品的总数量
	 * @param groupId productId skuId
	 * @return
	 */
	@Override
	public Integer getSuborderItemSum(String groupId, Long productId, String skuId) {
		Sql sqlCount = Sqls.create("SELECT sum(number) FROM t_group_suborderitem soi INNER JOIN t_group_orders o ON (o.orderId = soi.order_id)"
				+ "INNER JOIN t_group_suborder s ON (s.subOrderId = soi.subOrderId AND s.`status` = 1) WHERE soi.productId =@pid AND soi.skuId =@skuId AND o.group_id = @groupId");
		sqlCount.params().set("pid",productId);
		sqlCount.params().set("skuId",skuId);
		sqlCount.params().set("groupId",groupId);
		sqlCount.setCallback(Sqls.callback.integer());
		dao.execute(sqlCount);
		Integer cnt = sqlCount.getInt();
		return cnt;
	}

	@Override
	public List<GroupSuborderitem> findBySkuIdAndGroupId(Long groupId, Long skuId) {
		
		return null;
	}


}
