package com.wode.factory.facade.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.factory.facade.SupplierSaleCodeManageFacade;
import com.wode.factory.model.SupplierSaleCodeManage;
import com.wode.factory.service.SupplierSaleCodeManageService;

@Service("SupplierSaleCodeManageFacade")
@Transactional("tm_factory")
public class SupplierSaleCodeManageFacadeImpl implements SupplierSaleCodeManageFacade {

	@Autowired
	private SupplierSaleCodeManageService ssms;
	
	@Override
	@Transactional
	public String getSaleCode(Long supplierId) {
		Calendar c = Calendar.getInstance();
		
		//当前月
		int yearMonth = c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH) + 1;
		String rtn = yearMonth + "";
		
		SupplierSaleCodeManage entity = ssms.getById(supplierId); 
		//该企业没有管理数据
		if(entity == null) {
			//自动生成一条管理数据
			entity = new SupplierSaleCodeManage();
			entity.setSupplierId(supplierId);	//企业id
			entity.setYearMonth(yearMonth);		//当前年月
			entity.setCode(1);					//从1开始
			rtn = rtn + "0001";					//从1开始
			//保存到DB
			ssms.insert(entity);
		} else {
			if(yearMonth == entity.getYearMonth()) {
				//当前年月已有数据，code累计
				entity.setCode(entity.getCode()+1);
				rtn = rtn + String.format("%04d", entity.getCode()); //code累计
				//保存到DB
				ssms.update(entity);
			} else {
				entity.setYearMonth(yearMonth);		//当前年月
				entity.setCode(1);					//从1开始
				rtn = rtn + "0001";					//从1开始
				//保存到DB
				ssms.update(entity);
			}
		}
		
		return rtn;
	}

}
