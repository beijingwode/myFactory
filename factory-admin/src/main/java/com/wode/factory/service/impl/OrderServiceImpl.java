package com.wode.factory.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.base.ExportExcel;
import com.wode.common.util.ActResult;
import com.wode.factory.mapper.OrdersMapper;
import com.wode.factory.mapper.SuborderDao;
import com.wode.factory.mapper.SuborderitemDao;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.service.OrderService;
import com.wode.factory.vo.SuborderOrderVo;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("orderServiceImpl")
@Transactional("tm_factory")
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrdersMapper ordersMapper;

	@Autowired
	SuborderDao suborderDao;
	
	@Autowired
	SuborderitemDao suborderitemDao;
	
	@Value("#{configProperties['tongji.download']}")
	private  String downloadFilePatch;

	@Override
	@Transactional(readOnly = true)
	public Orders getOrderDetailWithItems(Long id) {
		Assert.notNull(id);
		return ordersMapper.getOrderByIdWithItems(id);
	}

	@Override
	public Orders findById(Long id) {
		Assert.notNull(id);
		return ordersMapper.getById(id);
	}

	@Override
	public void update(Orders order) {
		Assert.notNull(order);
		ordersMapper.update(order);
	}
	
	public ActResult createOrderXls(Map<String, Object> map){
        List<Orders> list = ordersMapper.findOrdersBySelective(map);
        ExportExcel<Orders> exportExl = new ExportExcel<Orders>();
        OutputStream out;
		try {
			 out = new FileOutputStream(downloadFilePatch+"orders.xls");
			 String[] headers = ordersMapper.getHeaders();
		     exportExl.exportExcel("订单列表", headers, list, out, "yyyy-MM-dd");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    return ActResult.successSetMsg("生成成功");
	}

	@Override
	@Transactional(readOnly = true)
	public PageInfo<SuborderOrderVo> getSuborderList(Map<String, Object> params) {

		PageHelper.startPage(params);
		List<SuborderOrderVo> list = ordersMapper.getSuborderList(params);
		return new PageInfo<SuborderOrderVo>(list);
	}

	@Override
	public BigDecimal getSumCashPayByProduct(Long productId,Date firstDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		params.put("firstDate", firstDate);
		return suborderDao.getSumCashPayByProduct(params);
	}

	@Override
	public Long getNoClearOrderByProduct(Long productId,Date startDate,Date endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return suborderDao.getNoClearOrderByProduct(params);
	}

	
	
	@Override
	public List<Orders> findOwnOrders(Long supplierId,Long productId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", supplierId);
		params.put("productId", productId);
		return ordersMapper.findOwnOrders(params);
	}

	@Override
	public void save(Orders orders,Suborder suborder, List<Suborderitem> suborderitems) {
		ordersMapper.insert(orders);
		suborderDao.insert(suborder);
		for (Suborderitem suborderitem : suborderitems) {
			suborderitemDao.insert(suborderitem);
		}
	}

	@Override
	public List<Orders> findByUserId(Long userId) {
		return ordersMapper.findByUserId(userId);
	}

	@Override
	public void delete(Long id) {
		ordersMapper.delete(id);
	} 
}
