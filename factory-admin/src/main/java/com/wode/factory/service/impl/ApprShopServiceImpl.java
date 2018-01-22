package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.mapper.ApprShopDao;
import com.wode.factory.model.ApprShop;
import com.wode.factory.service.ApprShopService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("ApprShopServiceImpl")
public class ApprShopServiceImpl extends EntityServiceImpl<ApprShop,Long> implements ApprShopService {

	@Autowired
	ApprShopDao apprShopMapper;

	@Override
	@Transactional(readOnly = true)
	public PageInfo<ApprShop> findApprShop(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ApprShop> ordersList = apprShopMapper.findApprShop(params);
		return new PageInfo(ordersList);
	}

	public List<ApprShop> findApprShopEmpty(Map<String, Object> map) {
		return apprShopMapper.findApprShopEmpty(map);
	}
	@Override
	public EntityDao<ApprShop, Long> getDao() {
		return apprShopMapper;
	}

	@Override
	public void insertShop(ApprShop appr) {
		apprShopMapper.insertShop(appr);
	}
	@Override
	public void updateShop(ApprShop appr) {
		apprShopMapper.updateShop(appr);
	}
}
