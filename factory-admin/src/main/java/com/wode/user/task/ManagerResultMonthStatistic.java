package com.wode.user.task;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.util.NumberUtil;
import com.wode.common.util.TimeUtil;
import com.wode.factory.mapper.ProductDao;
import com.wode.factory.mapper.StatisticalBenefitDao;
import com.wode.factory.mapper.StatisticalExchangeProductDao;
import com.wode.factory.mapper.StatisticalFirstOrderDao;
import com.wode.factory.mapper.StatisticalManagerResultDao;
import com.wode.factory.mapper.StatisticalSaleDao;
import com.wode.factory.mapper.StatisticalTrialProductDao;
import com.wode.factory.mapper.SuborderitemDao;
import com.wode.factory.mapper.SupplierExchangeProductDao;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.StatisticalBenefit;
import com.wode.factory.model.StatisticalExchangeProduct;
import com.wode.factory.model.StatisticalFirstOrder;
import com.wode.factory.model.StatisticalManagerResult;
import com.wode.factory.model.StatisticalSale;
import com.wode.factory.model.StatisticalTrialProduct;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.service.ProductSpecificationsService;
import com.wode.factory.service.SubOrderService;
import com.wode.factory.service.SupplierService;


@Service
public class ManagerResultMonthStatistic {
	@Autowired
	StatisticalFirstOrderDao statisticalFirstOrderDao;
	@Autowired
	SupplierService supplierService;
	@Autowired
	SupplierExchangeProductDao supplierExchangeProductDao;
	@Autowired
	ProductDao productDao;
	@Autowired
	ProductSpecificationsService productSpecificationsService;
	@Autowired
	StatisticalBenefitDao statisticalBenefitDao;
	@Autowired
	StatisticalSaleDao statisticalSaleDao;
	@Autowired
	StatisticalExchangeProductDao statisticalExchangeProductDao;
	@Autowired
	StatisticalTrialProductDao statisticalTrialProductDao;
	@Autowired
	StatisticalManagerResultDao statisticalManagerResultDao;
	@Autowired
	SuborderitemDao suborderitemDao;
	
	/**
	 * 添加月统计信息
	 */
	public void run() {
		SimpleDateFormat si = new SimpleDateFormat("yyyy-MM");
		//获取当前系统时间
		Calendar date = Calendar.getInstance();
		Date now = date.getTime();
		//将月份减一
		date.set(Calendar.MONTH, date.get(Calendar.MONTH) - 1);
		String lastMonth = si.format(date.getTime());
		
		Map<Long,Supplier> mapSupplier = new HashMap<Long,Supplier>();
		Map<Long,StatisticalManagerResult> mapResult = new HashMap<Long,StatisticalManagerResult>();
		///////////////////////////////////////////////////////////////////////////////////////////////////
		//			统计员工首单
		///////////////////////////////////////////////////////////////////////////////////////////////////
		List<StatisticalFirstOrder> firstOrders = statisticalFirstOrderDao.countByTakeDate(lastMonth);
		for (StatisticalFirstOrder statisticalFirstOrder : firstOrders) {
			// 设置统计年月
			statisticalFirstOrder.setMonth(lastMonth);
			statisticalFirstOrder.setCreateTime(now);
						
			//设置企业信息
			Supplier enterpise = this.getSupplier(mapSupplier, statisticalFirstOrder.getEnterpriseId());
			if(enterpise!=null) {
				statisticalFirstOrder.setEnterpriseName(enterpise.getComName());
				statisticalFirstOrder.setEnterpriseManager(enterpise.getManagerId());
				
				// 员工首单合计
				StatisticalManagerResult result = this.getResult(mapResult, enterpise.getManagerId(), lastMonth, now);
				result.setEmpOrderCnt(result.getEmpOrderCnt()+1);
				result.setEmpOrderAmount(result.getEmpOrderAmount().add(statisticalFirstOrder.getRealPrice()));
			}
						
			//设置商家信息
			Supplier supplier = this.getSupplier(mapSupplier, statisticalFirstOrder.getSupplierId());
			if(supplier!=null) {
				statisticalFirstOrder.setSupplierName(supplier.getComName());
				statisticalFirstOrder.setSupplierManager(supplier.getManagerId());
				
				// 商家首单合计
				StatisticalManagerResult result = this.getResult(mapResult, supplier.getManagerId(), lastMonth, now);
				result.setSupplierOrderCnt(result.getSupplierOrderCnt()+1);
				result.setSupplierOrderAmount(result.getSupplierOrderAmount().add(statisticalFirstOrder.getRealPrice()));
			}
			
			// 保存到DB
			statisticalFirstOrderDao.insert(statisticalFirstOrder);
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////////
		//			统计换领商品
		///////////////////////////////////////////////////////////////////////////////////////////////////
		SupplierExchangeProduct query=new SupplierExchangeProduct();
		// 中换领开始时间符合查询条件
		query.setProductImg(lastMonth);
		List<SupplierExchangeProduct> supplierExchangeProducts = supplierExchangeProductDao.selectByModel(query);
		Map<Long,StatisticalExchangeProduct> mapSep = new HashMap<Long,StatisticalExchangeProduct>();
		for (SupplierExchangeProduct supplierExchangeProduct : supplierExchangeProducts) {
			if(supplierExchangeProduct.getLimitEnd()!=null && supplierExchangeProduct.getLimitEnd().before(TimeUtil.addDay(supplierExchangeProduct.getLimitStart(),7))) {
				// 换领期间>=7
				continue;
			}
			
			// 换领期间>=7
			StatisticalExchangeProduct sep = mapSep.get(supplierExchangeProduct.getSupplierId());
			if(sep ==null) {
				sep = new StatisticalExchangeProduct();
				
				// 设置统计年月
				sep.setMonth(lastMonth);
				sep.setCreateTime(now);
							
				//设置商家信息
				sep.setSupplierId(supplierExchangeProduct.getSupplierId());
				Supplier supplier = this.getSupplier(mapSupplier, sep.getSupplierId());
				if(supplier!=null) {
					sep.setSupplierName(supplier.getComName());
					sep.setSupplierManager(supplier.getManagerId());
				}
				sep.setProductTypeCnt(0);
				sep.setProductCnt(0);
				sep.setEmpCnt(0);
				sep.setProductAmount(BigDecimal.ZERO);
				
				mapSep.put(supplierExchangeProduct.getSupplierId(), sep);
			}
			
			//合并商家换领统计
			//商品种类++，参与员工数++，商品总数++，总金额++
			sep.setProductTypeCnt(sep.getProductTypeCnt()+1);
			sep.setProductCnt(sep.getProductCnt()+supplierExchangeProduct.getProductCnt());
			sep.setEmpCnt(sep.getEmpCnt()+supplierExchangeProduct.getEmpCnt());
			sep.setProductAmount(sep.getProductAmount().add(supplierExchangeProduct.getProductPrice().multiply(NumberUtil.toBigDecimal(supplierExchangeProduct.getProductCnt()))));
		}
		
		// 保存到DB
		for (StatisticalExchangeProduct sep : mapSep.values()) {
			// 换领商品合计
			StatisticalManagerResult result = this.getResult(mapResult, sep.getSupplierManager(), lastMonth, now);
			result.setExchangeAmount(result.getExchangeAmount().add(sep.getProductAmount()));

			statisticalExchangeProductDao.insert(sep);
		}
		

		///////////////////////////////////////////////////////////////////////////////////////////////////
		//			统计试用商品
		///////////////////////////////////////////////////////////////////////////////////////////////////	
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("saleKbnTJ", 5);
		params.put("dateTJ", lastMonth);
		List<Product> products= productDao.findList(params);
		Map<Long,StatisticalTrialProduct> mapStp = new HashMap<Long,StatisticalTrialProduct>();
		for (Product product : products) {
			List<ProductSpecifications> skus = productSpecificationsService.findlistByProductid(product.getId());
			if(skus==null||skus.isEmpty()) continue;
			//TODO	
			StatisticalTrialProduct stp = mapStp.get(product.getSupplierId());
			if(stp ==null) {
				stp = new StatisticalTrialProduct();
				
				// 设置统计年月
				stp.setMonth(lastMonth);
				stp.setCreateTime(now);
							
				//设置商家信息
				stp.setSupplierId(product.getSupplierId());
				Supplier supplier = this.getSupplier(mapSupplier, stp.getSupplierId());
				if(supplier!=null) {
					stp.setSupplierName(supplier.getComName());
					stp.setSupplierManager(supplier.getManagerId());
				}
				stp.setProductTypeCnt(0);
				stp.setProductCnt(0);
				stp.setOnlineAmoung(BigDecimal.ZERO);
				stp.setRealAmount(BigDecimal.ZERO);
				stp.setBreakAmount(BigDecimal.ZERO);
				stp.setAvgOnlinePrice(BigDecimal.ZERO);
				stp.setAvgRealPrice(BigDecimal.ZERO);
				stp.setAvgShipping(BigDecimal.ZERO);
				
				mapStp.put(product.getSupplierId(), stp);
			}
			//合并试用换领统计
			//商品种类++，商品总数++，电商金额++，内购金额++，运费++，优惠总金额++
			stp.setProductTypeCnt(stp.getProductTypeCnt()+1);
			for (ProductSpecifications sku : skus) {
				stp.setProductCnt(stp.getProductCnt()+sku.getStock());
				stp.setOnlineAmoung(stp.getOnlineAmoung().add(sku.getPrice().multiply(NumberUtil.toBigDecimal(sku.getStock()))));
				stp.setRealAmount(stp.getRealAmount().add(sku.getInternalPurchasePrice().multiply(NumberUtil.toBigDecimal(sku.getStock()))));
				// 暂时保存全部金额
				stp.setAvgShipping(product.getCarriage()==null?BigDecimal.ZERO:(stp.getAvgShipping().add(product.getCarriage().multiply(NumberUtil.toBigDecimal(sku.getStock())))));
				// 暂时保存优惠金额
				stp.setBreakAmount(stp.getBreakAmount().add(product.getEmpPrice().multiply(NumberUtil.toBigDecimal(sku.getStock()))));
			}
			//处理当月销售的数量统计(分为付款减库存和拍下减库存)及商品总数
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("productId", product.getId());
			map.put("stockLockType", product.getStockLockType());//（拍下减库存和付款减库存）两种情况处理
			List<Suborderitem> subs = suborderitemDao.findSubCountByProId(map);
			for (Suborderitem subItem : subs) {
				stp.setProductCnt(stp.getProductCnt()+subItem.getNumber());
				stp.setOnlineAmoung(stp.getOnlineAmoung().add(subItem.getPrice().multiply(NumberUtil.toBigDecimal(subItem.getNumber()))));
				stp.setRealAmount(stp.getRealAmount().add(subItem.getInternalPurchasePrice().multiply(NumberUtil.toBigDecimal(subItem.getNumber()))));
				// 暂时保存全部金额
				stp.setAvgShipping(product.getCarriage()==null?BigDecimal.ZERO:(stp.getAvgShipping().add(product.getCarriage().multiply(NumberUtil.toBigDecimal(subItem.getNumber())))));
				// 暂时保存优惠金额
				stp.setBreakAmount(stp.getBreakAmount().add(product.getEmpPrice().multiply(NumberUtil.toBigDecimal(subItem.getNumber()))));
			}
		}
		
		// 保存到DB
		for (StatisticalTrialProduct stp : mapStp.values()) {
			//商品优惠总额=（电商-实际支付）*数量，实际支付=运费+（内购价-返利）
			stp.setBreakAmount(stp.getOnlineAmoung().subtract(
					stp.getAvgShipping().add(stp.getRealAmount().subtract(stp.getBreakAmount()))));
			// 计算平均值
			stp.setAvgOnlinePrice(stp.getOnlineAmoung().divide(NumberUtil.toBigDecimal(stp.getProductCnt()), 2, BigDecimal.ROUND_DOWN));
			stp.setAvgRealPrice(stp.getRealAmount().divide(NumberUtil.toBigDecimal(stp.getProductCnt()), 2, BigDecimal.ROUND_DOWN));
			stp.setAvgShipping(stp.getAvgShipping().divide(NumberUtil.toBigDecimal(stp.getProductCnt()), 2, BigDecimal.ROUND_DOWN));
			
			// 商家首单合计
			StatisticalManagerResult result = this.getResult(mapResult, stp.getSupplierManager(), lastMonth, now);
			result.setTrailAmount(result.getTrailAmount().add(stp.getBreakAmount()));
			
			statisticalTrialProductDao.insert(stp);
		}

		///////////////////////////////////////////////////////////////////////////////////////////////////
		//			生日礼金,过节费统计
		///////////////////////////////////////////////////////////////////////////////////////////////////	
		List<StatisticalBenefit> statisticalBenefits = statisticalBenefitDao.countByDate(lastMonth);
		for (StatisticalBenefit statisticalBenefit : statisticalBenefits) {
			// 设置统计年月
			 statisticalBenefit.setMonth(lastMonth);
			 statisticalBenefit.setCreateTime(now);
							
			//设置企业信息
			Supplier enterpise = this.getSupplier(mapSupplier, statisticalBenefit.getEnterpriseId());
			if(enterpise!=null) {
				statisticalBenefit.setEnterpriseName(enterpise.getComName());
				statisticalBenefit.setEnterpriseManager(enterpise.getManagerId());
				
				if(NumberUtil.isGreaterZero(statisticalBenefit.getCashAmount())) {
					// 商家首单合计
					StatisticalManagerResult result = this.getResult(mapResult, enterpise.getManagerId(), lastMonth, now);
					if("1".equals(statisticalBenefit.getExBenefitType())) {
						// 生日礼金
						result.setBirthDayCnt(result.getBirthDayCnt()+statisticalBenefit.getEmpCnt());
						result.setBirthDayAmount(result.getBirthDayAmount().add(statisticalBenefit.getCashAmount()));
					} else if("2".equals(statisticalBenefit.getExBenefitType())) {
						// 过节费
						result.setFestivalCnt(result.getFestivalCnt()+statisticalBenefit.getEmpCnt());
						result.setFestivalAmount(result.getFestivalAmount().add(statisticalBenefit.getCashAmount()));						
					}
				}				
			}
			
			statisticalBenefitDao.insert(statisticalBenefit);
		}

		///////////////////////////////////////////////////////////////////////////////////////////////////
		//			销售额统计
		///////////////////////////////////////////////////////////////////////////////////////////////////	
		List<StatisticalSale> statisticalSales = statisticalSaleDao.countByDate(lastMonth);
		for (StatisticalSale statisticalSale : statisticalSales) {
			// 设置统计年月
			statisticalSale.setMonth(lastMonth);
			statisticalSale.setCreateTime(now);
			
			//设置商家信息
			Supplier supplier = this.getSupplier(mapSupplier, statisticalSale.getSupplierId());
			if(supplier!=null) {
				statisticalSale.setSupplierName(supplier.getComName());
				statisticalSale.setSupplierManager(supplier.getManagerId());

				// 商家首单合计
				StatisticalManagerResult result = this.getResult(mapResult, statisticalSale.getSupplierManager(), lastMonth, now);
				result.setOrderCnt(result.getOrderCnt()+ statisticalSale.getOrderCnt());
				result.setOrderAmount(result.getOrderAmount().add(statisticalSale.getRealPrice()));
				result.setOrderShipping(result.getOrderShipping().add(statisticalSale.getShipping()));
			}
			
			statisticalSaleDao.insert(statisticalSale);
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////////
		//			汇总结果保存到DB
		///////////////////////////////////////////////////////////////////////////////////////////////////
		for (StatisticalManagerResult result : mapResult.values()) {
			statisticalManagerResultDao.insert(result);
		}
	}

	/**
	 * 获取商家信息
	 * @param mapSupplier
	 * @param supplierId
	 */
	private Supplier getSupplier(Map<Long, Supplier> mapSupplier, Long supplierId) {
		if(supplierId != null) {
			Supplier enterprise = mapSupplier.get(supplierId);
			if(enterprise == null) {
				enterprise = supplierService.findByid(supplierId);
				if(enterprise!=null) {
					mapSupplier.put(supplierId, enterprise);
				}
			}
			
			return enterprise;
		}
		
		return null;
	}
	

	/**
	 * 获取商家信息
	 * @param mapSupplier
	 * @param supplierId
	 */
	private StatisticalManagerResult getResult(Map<Long, StatisticalManagerResult> mapResult, Long managerId,String month,Date now) {
	
		StatisticalManagerResult result = mapResult.get(managerId);
		if(result == null) {
			result = new StatisticalManagerResult();
			result.setMonth(month);
			result.setCreateTime(now);
			result.setManagerId(managerId);
			
			result.setEmpOrderCnt(0);
			result.setEmpOrderAmount(BigDecimal.ZERO);
			result.setExchangeAmount(BigDecimal.ZERO);
			result.setTrailAmount(BigDecimal.ZERO);
			result.setBirthDayCnt(0);
			result.setBirthDayAmount(BigDecimal.ZERO);
			result.setFestivalCnt(0);
			result.setFestivalAmount(BigDecimal.ZERO);
			result.setOrderCnt(0);
			result.setOrderAmount(BigDecimal.ZERO);
			result.setOrderShipping(BigDecimal.ZERO);
			result.setSupplierOrderCnt(0);
			result.setSupplierOrderAmount(BigDecimal.ZERO);
			
			mapResult.put(managerId, result);
		}
		
		return result;

	}
}
