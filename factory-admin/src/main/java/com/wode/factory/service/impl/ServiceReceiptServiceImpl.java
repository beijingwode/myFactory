package com.wode.factory.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.util.StringUtils;
import com.wode.factory.mapper.ServiceReceiptDao;
import com.wode.factory.model.ServiceReceipt;
import com.wode.factory.service.ServiceReceiptService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("serviceReceiptService")
public class ServiceReceiptServiceImpl extends EntityServiceImpl<ServiceReceipt,Long> implements ServiceReceiptService {

	@Autowired
	ServiceReceiptDao serviceReceiptMapper;
//
//	@Override
//	@Transactional(readOnly = true)
//	public PageInfo<ServiceReceipt> findServiceReceipt(Map<String, Object> params) {
//		PageHelper.startPage(params);
//		List<ServiceReceipt> ordersList = ServiceReceiptMapper.findServiceReceipt(params);
//		return new PageInfo(ordersList);
//	}

	@Override
	public ServiceReceiptDao getDao() {
		return serviceReceiptMapper;
	}

	@Override
	public void insert(ServiceReceipt entity) {
		serviceReceiptMapper.insert(entity);
	}

	@Override
	public PageInfo<ServiceReceipt> getPage(Map<String, Object> params) {

		PageHelper.startPage(params);
		List<ServiceReceipt> listSaleBill = this.getDao().findPageList(params);
		return new PageInfo(listSaleBill);
	}
}
