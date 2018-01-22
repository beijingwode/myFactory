/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.user.dao.SuborderDao;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.vo.OrderTypeCountVO;
import com.wode.factory.user.vo.SubOrderVo;

@Repository("suborderDao")
public class SuborderDaoImpl extends BaseDao<Suborder, java.lang.String> implements SuborderDao {

    @Override
    public String getIbatisMapperNamesapce() {
        return "SuborderMapper";
    }

    public void saveOrUpdate(Suborder entity) {
        if (entity.getSubOrderId() == null)
            save(entity);
        else
            update(entity);
    }

	public PageInfo findPage(SuborderQuery query) {
        List list = getSqlSession().selectList(getIbatisMapperNamesapce() + ".findPage", query, new RowBounds(query.getPageNumber(), query.getPageSize()));
        return new PageInfo(list);
    }
	
	/**
     * 查询退款/售后状态下的订单列表
     * @param query
     * @author 刘聪
     * @since 2015-07-09
     */
	public PageInfo findRefundOrderByUserId(SuborderQuery query) {
		List list = getSqlSession().selectList(getIbatisMapperNamesapce() + ".findRefundOrderByUserId", query, new RowBounds(query.getPageNumber(), query.getPageSize()));
        return new PageInfo(list);
	}
	
	/**
     * 查询不同状态下的订单个数
     * @param query
     * @author 刘聪
     * @since 2015-06-19
     */
	public int findOrderByUserIdCount(SuborderQuery query) {
		String sqlComment = "SELECT count(*)"
			 	  + " FROM t_suborder sub"
			 	  + " INNER JOIN t_orders o"
			 	  + " ON sub.orderId = o.orderId"
			 	  + " INNER JOIN t_supplier s"
			 	  + " ON sub.supplierId = s.id"
			 	  + " WHERE o.userId = " + query.getUserId()
			 	  + " AND sub.deleteFlag = 0"
			 	  + (query.getStatus() == null ? " " : " AND sub.status = " + query.getStatus())
				  + (query.getCommentStatus() == null ? " " : " AND sub.commentStatus = " + query.getCommentStatus());
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findOrderByUserIdCount", sqlComment);
	}

	@Override
	public List<OrderTypeCountVO> getOrderCountByUserId(SuborderQuery query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".getOrderCountByUserId", query);
	}
	/**
     * 查询订单项目列表
     *
     * @param userId
     * @param status
     * @param page
     * @author 刘聪
     * @since 2015-06-23
     */
    public List<Suborderitem> getSubOrderItemById(String subOrderId) {
    	return getSqlSession().selectList(getIbatisMapperNamesapce() + ".selectSubOrderItemById", subOrderId);
    }
	
    @Override
    public SubOrderVo findOrderDetailById(SuborderQuery query) {
        return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".findOrderDetailById", query);
    }

    @Override
    public Suborder querySubOrder(SuborderQuery query) {
        return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".querySubOrder", query);
    }

    @Override
    public List<Suborder> findByOrderId(long orderId) {
        return getSqlSession().selectList(getIbatisMapperNamesapce() + ".findByOrderId", orderId);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public PageInfo findReturnableOrders(SuborderQuery query) {
        List list = getSqlSession().selectList(getIbatisMapperNamesapce() + ".findReturnableOrders", query, new RowBounds(query.getPageNumber(), query.getPageSize()));
        return new PageInfo(list);
    }

	@Override
	public List<SubOrderVo> findSubOrdersByOrderId(SuborderQuery query) {
		 return getSqlSession().selectList(getIbatisMapperNamesapce() + ".findSubOrdersByOrderId", query);
	}


}
