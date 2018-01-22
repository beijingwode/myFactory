package com.wode.factory.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.GroupBuy;
import com.wode.factory.model.GroupBuyUser;
import com.wode.factory.model.Shop;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.GroupBuyService;
import com.wode.factory.vo.GroupBuyVo;

@Service("groupBuyService")
public class GroupBuyServiceImpl implements GroupBuyService {
	
	private static Logger logger = LoggerFactory.getLogger(GroupBuyServiceImpl.class);
	@Autowired
	private Dao dao;
	@Override
	public ActResult<GroupBuyVo> create(GroupBuyVo gbVo,UserFactory loginUser,String productIds) {
		if(StringUtils.isEmpty(gbVo.getAid()) || StringUtils.isEmpty(gbVo.getAddress()) || StringUtils.isEmpty( gbVo.getUserName())
				||StringUtils.isEmpty(gbVo.getPhoneNum()) || gbVo.getShopId()==null ||(gbVo.getLimitedTime()==1 && gbVo.getDays() == null)){
			return ActResult.fail("参数错误");
		}

		if(gbVo.getId()==null) {//新增
			gbVo.setUserId(loginUser.getId());
			gbVo.setStatus(1);// 表示开团不成功 未支付
			
			Calendar date = Calendar.getInstance();
			gbVo.setCreateTime(date.getTime());// 开始时间
			if(gbVo.getLimitedTime()==0){//如果创建团选择不限时默认500
				gbVo.setDays(500);
			}
			if(gbVo.getDays()!=null){
				date.add(Calendar.DAY_OF_MONTH, gbVo.getDays());
				gbVo.setEndTime(date.getTime());// 结束时间
			}
			//设置团长头像
			if(!StringUtils.isEmpty(loginUser.getAvatar())){
				gbVo.setUserAvatar(loginUser.getAvatar());
			}
			//设置团长昵称
			if(!StringUtils.isEmpty(loginUser.getNickName())){
				gbVo.setCommanderName(loginUser.getNickName());
			}else {
				gbVo.setCommanderName(loginUser.getPhone());;
			}
			//设置店铺名称
			gbVo.setShopName(dao.fetch(Shop.class,Cnd.where("id", "=", gbVo.getShopId())).getShopname());
			gbVo.setJoinNum(0);
			gbVo.setOrderStatus(0);//创建团购 订单未生成
			gbVo = dao.insert(gbVo);
			saveGroupBuyUser(gbVo,loginUser);
		}
		return ActResult.success(gbVo);
	}

	private void saveGroupBuyUser(GroupBuyVo gbVo, UserFactory loginUser) {
		GroupBuyUser groupBuyUser = new GroupBuyUser();
		groupBuyUser.setGroupId(gbVo.getId());
		groupBuyUser.setUserId(loginUser.getId());
		groupBuyUser.setCreateTime(gbVo.getCreateTime());
		groupBuyUser.setIsAdd(0);//新建团购成员信息 0表示未支付未参加团购
		groupBuyUser.setIsLadder(1);//1 表示为团长
		groupBuyUser.setUserName(loginUser.getNickName());
		groupBuyUser.setShopId(gbVo.getShopId());//店铺id
		groupBuyUser.setStatus(1);//未激活
		groupBuyUser = dao.insert(groupBuyUser);
		
	}

	@Override
	public GroupBuyVo getGroupBuyById(String groupId) {
		
		if(null != groupId) {
			return dao.fetch(GroupBuyVo.class,Long.parseLong(groupId));
		}
		
		return null;
	}


	@Override
	public void update(GroupBuyVo groupBuyVo) {
		if(groupBuyVo!=null && groupBuyVo.getId()!=null) {
			dao.update(groupBuyVo);
		}
	}

	@Override
	public List<GroupBuyVo> findGroupBuyByShopAndUserId(Long shopId, Long userId){
		List<GroupBuyVo> result = new ArrayList<GroupBuyVo>();
		List<GroupBuyUser> groupBuyUserList = dao.query(GroupBuyUser.class, Cnd.where("shop_id","=",shopId).and("user_id","=",userId).and("status", "=", "0"));
		if(null !=groupBuyUserList && !groupBuyUserList.isEmpty()) {
			for (GroupBuyUser groupBuyUser : groupBuyUserList) {
				result.add(dao.fetch(GroupBuyVo.class,groupBuyUser.getGroupId()));
			}
		}
		
		return result;
	}
	/**
	 * 购物团详情
	 */
	@Override
	public GroupBuyVo getById(Long groupId) {
		return dao.fetch(GroupBuyVo.class, Cnd.where("id", "=", groupId));
	}

	@Override
	public QueryResult query(Integer fromType, Long userId, Integer page, Integer pageSize) {
		Pager pager = dao.createPager(page, pageSize);
		StringBuilder fromWhere = new StringBuilder();
		fromWhere.append(" FROM t_group_buy g INNER JOIN t_group_buy_user gu on (gu.group_id=g.id)");
		fromWhere.append(" WHERE gu.user_id = @userId ");
		fromWhere.append("   AND ((gu.`status`=1) OR (gu.`status`=0 AND g.order_status=0 AND (g.limited_time = 0 OR (g.limited_time = 1 AND g.end_time> sysdate()))))");
	
		Sql sqlCount = Sqls.create("SELECT count(1) " + fromWhere.toString());
		sqlCount.params().set("userId",userId);
		sqlCount.setCallback(Sqls.callback.integer());
		dao.execute(sqlCount);
		int cnt = sqlCount.getInt();
		
		pager.setRecordCount(cnt);
		StringBuilder sb = new StringBuilder();
		// aid 暂时作为是否已参团标致
		sb.append(" SELECT g.id,g.shop_id,g.num,g.days,g.user_name,g.phone_num,g.address,g.`comment`,g.end_time,g.user_id,g.`status`,gu.`status` joinStatus,g.im_group_id,g.user_avatar,g.shop_name,g.join_num,g.commander_name,g.order_status,g.group_name,g.limited_time,g.all_total_price,g.total_price,all_total_shipping ");
		sb.append(fromWhere.toString());
//		sb.append(" ORDER BY gu.`status` desc ,g.end_time desc,g.create_time asc");
		sb.append(" ORDER BY (CASE WHEN (g.`status`=0 OR g.`status`=1) AND g.order_status=0 AND gu.`status`=1 THEN 100 ");
		sb.append("   WHEN (g.`status`=0 OR g.`status`=1) AND g.order_status=0 AND gu.`status`=0 THEN 101 ");
		sb.append("   WHEN g.order_status IN (1,2,4) THEN 102");
		sb.append("   WHEN (g.`status`=-1 OR g.`status`=-2) AND g.order_status<>-1 THEN 103");
		sb.append("   WHEN g.order_status=-1 THEN 104");
		sb.append("   ELSE 999 END) ");
		sb.append("  ,g.create_time DESC");
		
		Sql sql = Sqls.create(sb.toString());
		sql.params().set("userId",userId);
		sql.setCallback(Sqls.callback.entities());
		sql.setPager(pager);
		sql.setEntity(dao.getEntity(GroupBuyVo.class));
		dao.execute(sql);
		List<GroupBuyVo> sList = sql.getList(GroupBuyVo.class);
		return new QueryResult(sList, pager);
	}
	
	@Override
	public GroupBuy findByImGroupId(Long id) {
		
		return dao.fetch(GroupBuy.class,Cnd.where("im_group_id","=",id));
	}
	@Override
	public BigDecimal calculateSaveProductAmonut(Long groupId) {
		GroupBuyVo buyVo = dao.fetch(GroupBuyVo.class, Cnd.where("id", "=", groupId));
		if(buyVo == null){
			return null;
		}
		BigDecimal saveAmount = (buyVo.getAllTotalPrice().add(buyVo.getAllTotalShipping())).subtract((buyVo.getTotalPrice().add(buyVo.getTotalShipping())));
		return saveAmount;
	}

}
