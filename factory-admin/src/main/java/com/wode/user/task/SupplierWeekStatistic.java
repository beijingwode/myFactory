package com.wode.user.task;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.EnterpriseUserDao;
import com.wode.factory.mapper.ProductBrandMapper;
import com.wode.factory.mapper.ProductDao;
import com.wode.factory.mapper.ProductSpecificationsDao;
import com.wode.factory.mapper.SupplierMapper;
import com.wode.factory.mapper.SupplierWeekStatisticalDao;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierWeekStatistical;


@Service
public class SupplierWeekStatistic {
	@Autowired
	private SupplierMapper supplierMapper;
	@Autowired
	private ProductBrandMapper productBrandMapper;
	@Autowired
	private ProductDao productMapper;
	@Autowired
	private ProductSpecificationsDao productSpecificationsMapper;
	@Autowired
	private EnterpriseUserDao enterpriseUserMapper;
	@Autowired
	private SupplierWeekStatisticalDao supplierWeekStatisticalMapper;
	
	/**
	 * 添加月统计信息
	 */
	public void run() {
		//查询所有商家数据
		List<Supplier> supplierList = supplierMapper.findAllSupplier();
		SupplierWeekStatistical supplierWeekStatistical;
		if (supplierList.size()>0 && !supplierList.isEmpty()) {
			//获取当前系统时间
			Calendar date = Calendar.getInstance();
			for (Supplier s : supplierList) {
				supplierWeekStatistical = new com.wode.factory.model.SupplierWeekStatistical();
				//设置创建时间
				supplierWeekStatistical.setCreatTime(date.getTime());
				//设置周
				supplierWeekStatistical.setDay(date.getTime());
				Long supplierId = s.getId();
				supplierWeekStatistical.setSupplierId(supplierId);
				//获取经营品牌数
				Integer supplierBrandNumber = productBrandMapper.getCountBySupplier(supplierId);
				supplierWeekStatistical.setSupplierBrandNumber(supplierBrandNumber.longValue());
				//获取在售经营品牌数
				Integer salBrandNumber = productBrandMapper.getCountBySupplierForSale(supplierId);
				supplierWeekStatistical.setSalBrandNumber(salBrandNumber.longValue());
				//设置上架商品数
				Integer productNumber = productMapper.getCountBySupplier(supplierId);
				supplierWeekStatistical.setProductNumber(productNumber.longValue());
				//设置商品折扣率
				if (productNumber.equals(0)) {
					supplierWeekStatistical.setProductDiscount(new BigDecimal(10));
				}else{
					//得到在售商品sku集合
					BigDecimal productDiscount =(BigDecimal) productSpecificationsMapper.findlistBySupplierId(supplierId);
					supplierWeekStatistical.setProductDiscount(productDiscount);
				}
				//设置已注册员工数
				Integer peopleNumber = enterpriseUserMapper.findEnterprisePeopleNumber(supplierId);
				supplierWeekStatistical.setPeopleNumber(peopleNumber.longValue());
				//设置已注册活跃员工数
				Integer activePeopleCnt = enterpriseUserMapper.findEnterpriseActivePeopleCnt(supplierId);
				supplierWeekStatistical.setActivePeopleCnt(activePeopleCnt.longValue());
				//查询上周数据
				List<SupplierWeekStatistical> oldList= supplierWeekStatisticalMapper.selectBySupplierId(supplierId);
				Long olsupplierBrandNumber =0L;
				Long oldsalBrandNumber =0L;
				Long oldproductNumber =0L;
				Long oldpeopleNumber =0L;
				Long oldactivePeopleCnt =0L;
				if (oldList.isEmpty()){
					//设置新增数据
					setNewNumber(supplierWeekStatistical, supplierBrandNumber, salBrandNumber, productNumber, peopleNumber, activePeopleCnt, olsupplierBrandNumber, oldsalBrandNumber, oldproductNumber, oldpeopleNumber, oldactivePeopleCnt);
				}else{
					 olsupplierBrandNumber = oldList.get(0).getSupplierBrandNumber();
					 oldsalBrandNumber = oldList.get(0).getSalBrandNumber();
					 oldactivePeopleCnt = oldList.get(0).getActivePeopleCnt();
					 oldpeopleNumber = oldList.get(0).getPeopleNumber();
					 oldproductNumber = oldList.get(0).getProductNumber();
					//设置新增数据
					setNewNumber(supplierWeekStatistical, supplierBrandNumber, salBrandNumber, productNumber, peopleNumber, activePeopleCnt, olsupplierBrandNumber, oldsalBrandNumber, oldproductNumber, oldpeopleNumber, oldactivePeopleCnt);
					//修改上周数据
					oldList.get(0).setUseFlg(0);
					this.supplierWeekStatisticalMapper.update(oldList.get(0));
				}
				//保存最新数据
				supplierWeekStatistical.setUseFlg(1);
				this.supplierWeekStatisticalMapper.insert(supplierWeekStatistical);
			}
		}
	}
	/**
	 * 设置新增数量
	 * @param supplierWeekStatistical
	 * @param supplierBrandNumber
	 * @param salBrandNumber
	 * @param productNumber
	 * @param peopleNumber
	 * @param activePeopleCnt
	 * @param olsupplierBrandNumber
	 * @param oldsalBrandNumber
	 * @param oldproductNumber
	 * @param oldpeopleNumber
	 * @param oldactivePeopleCnt
	 */
	private void setNewNumber(SupplierWeekStatistical supplierWeekStatistical, Integer supplierBrandNumber,
			Integer salBrandNumber, Integer productNumber, Integer peopleNumber, Integer activePeopleCnt,
			Long olsupplierBrandNumber, Long oldsalBrandNumber, Long oldproductNumber, Long oldpeopleNumber,
			Long oldactivePeopleCnt) {
		//设置新增经营品牌数
		supplierWeekStatistical.setNewsupplierBrandNumber(supplierBrandNumber.longValue()- olsupplierBrandNumber);
		//设置新增在售经营品牌数
		supplierWeekStatistical.setNewsalBrandNumber(salBrandNumber.longValue() - oldsalBrandNumber);
		//设置新增上架商品数
		supplierWeekStatistical.setNewproductNumber(productNumber.longValue() - oldproductNumber);
		//设置新增已注册员工数
		supplierWeekStatistical.setNewpeopleNumber(peopleNumber.longValue() - oldpeopleNumber);
		//设置新增活跃员工数
		supplierWeekStatistical.setNewactivePeopleCnt(activePeopleCnt.longValue() - oldactivePeopleCnt);
	}

}
