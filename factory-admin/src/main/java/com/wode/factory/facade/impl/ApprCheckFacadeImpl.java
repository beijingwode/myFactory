package com.wode.factory.facade.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.facade.ApprCheckFacade;
import com.wode.factory.facade.SupplierCloseFacade;
import com.wode.factory.model.ApprShop;
import com.wode.factory.model.ApprSupplier;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierDuration;
import com.wode.factory.service.ApprShopService;
import com.wode.factory.service.ApprSupplierService;
import com.wode.factory.service.AttachmentService;
import com.wode.factory.service.ProductBrandService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.SupplierCategoryService;
import com.wode.factory.service.SupplierDurationVoService;
import com.wode.factory.service.SupplierService;

/**
 * Created by gaoyj on 2015/11/09.
 */
@Service("apprCheckFacade")
@Transactional("tm_factory")
public class ApprCheckFacadeImpl implements ApprCheckFacade {

	@Autowired
	private SupplierService supplierService;

	@Autowired
	ApprSupplierService apprSupplierService;

	@Autowired
	SupplierCategoryService supplierCategoryService;
	@Autowired
	ProductBrandService productBrandService;
	@Autowired
	AttachmentService attachmentService;
	@Autowired
	SupplierDurationVoService supplierDurationVoService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private DBUtils dbUtils;
	@Autowired
	SupplierCloseFacade supplierCloseFacade;
	@Autowired
	ApprShopService apprShopService;
	@Autowired
	private RedisUtil redis;
	
	@Override
	public void apprToSupplier(ApprSupplier appr) {
		
		//////////////////////////////////////////////////////////////////
		//保存商家信息
		Supplier old =supplierService.findByid(appr.getSupplierId());
		if(old==null) {
			appr.setCreatTime(new Date());			//入驻时间以审核为准
			apprSupplierService.insertSupplier(appr);
		} else {
			apprSupplierService.updateSupplier(appr);
		}
		//////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////
		//保存商家账期
		SupplierDuration sd = this.supplierDurationVoService.getById(appr.getSupplierId());
		
		//账单只能进行修改一次
		if(StringUtils.isNullOrEmpty(sd)){
			sd = new SupplierDuration();
			sd.setId(dbUtils.CreateID());
			sd.setSupplierId(appr.getSupplierId());
			sd.setSaleDurationKey(appr.getSaleDurationKey());
			sd.setCreateTime(new Date());
			sd.setStartTime(appr.getStartTime());
			sd.setCreateUserid(appr.getManagerChkId());
			sd.setFinanceCode(supplierDurationVoService.getNewFinanceCode());
			this.supplierDurationVoService.saveSupplierDuration(sd);

			//添加或更新  企业结算命令表
			this.supplierCloseFacade.makeFistDuration(appr.getSupplierId(), sd.getStartTime());
			
		}else {
			sd.setSaleDurationKey(appr.getSaleDurationKey());
			sd.setCreateUserid(appr.getManagerChkId());
			sd.setCreateTime(new Date());
			this.supplierDurationVoService.update(sd);
		}
		//////////////////////////////////////////////////////////////////
		

		//////////////////////////////////////////////////////////////////
		//更新审核状态
		apprSupplierService.update(appr);
		//////////////////////////////////////////////////////////////////
		
		redis.del("REDIS_USER_SUPPLIER_"+appr.getSupplierId());
		redis.del("REDIS_SUPPLIER_"+appr.getSupplierId());
	}

	/**
	 * 功能说明：自动结算
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param supplierId 供应商Id
	 * @param execDate 首次结算日
	 */
	@Override
	public void apprToShop(ApprShop appr) {
		//////////////////////////////////////////////////////////////////
		Long shopId = null;
		//保存店铺信息
		if(StringUtils.isEmpty(appr.getOldId())) {
			apprShopService.insertShop(appr);
			shopId=appr.getShopId();
		} else {
			//更新分类
			supplierCategoryService.changShop(appr.getOldId(), appr.getId());		//旧版更新成临时
			supplierCategoryService.changShop(appr.getShopId(), appr.getOldId());	//新版更新到店铺
			supplierCategoryService.changShop(appr.getId(), appr.getShopId());		//临时更新到历史

			//更新品牌
			productBrandService.changShop(appr.getOldId(), appr.getId());		//旧版更新成临时
			productBrandService.changShop(appr.getShopId(), appr.getOldId());	//新版更新到店铺
			productBrandService.changShop(appr.getId(), appr.getShopId());		//临时更新到历史
			
			//更新资质
			attachmentService.changShop(appr.getOldId(), appr.getId());		//旧版更新成临时
			attachmentService.changShop(appr.getShopId(), appr.getOldId());	//新版更新到店铺
			attachmentService.changShop(appr.getId(), appr.getShopId());		//临时更新到历史
			//店铺信息更新
			apprShopService.updateShop(appr);
			
			//更新商品品牌
			productService.changeBrand(appr.getSupplierId(), appr.getOldId());
			

			shopId=appr.getOldId();
		}
		//////////////////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////////////////
		//更新审核状态
		apprShopService.update(appr);
		//////////////////////////////////////////////////////////////////
		
		//更新品牌审核时间
		productBrandService.setCreateDate(shopId, new Date());
	}
	
	private Supplier mergeSupplier(ApprSupplier appr) {
		Supplier s = new Supplier();
		
		s.setId(appr.getId());
		//s.setUserId(appr.getUserId());
		s.setComPortraiture(appr.getComPortraiture());
		s.setComTel(appr.getComTel());
		s.setComName(appr.getComName());
		s.setComPersonnum(appr.getComPersonnum());
		s.setComRegisternum(appr.getComRegisternum());
		s.setComPc(appr.getComPc());
		s.setComState(appr.getComState());
		s.setComCity(appr.getComCity());
		s.setComAddress(appr.getComAddress());
		s.setComAdd(appr.getComAdd());
		s.setReferee(appr.getReferee());
		s.setIndustry(appr.getIndustry());
		s.setProperty(appr.getProperty());
		s.setBusState(appr.getBusState());
		s.setBusCity(appr.getBusCity());
		s.setBusAddress(appr.getBusAddress());
		s.setRegisteredCapital(appr.getRegisteredCapital());
		s.setBusScope(appr.getBusScope());
		s.setBusBegintime(appr.getBusBegintime());
		s.setBusEndtime(appr.getBusEndtime());
		s.setBusImgUrl(appr.getBusImgUrl());
		s.setOrgNum(appr.getOrgNum());
		s.setOrgBegintime(appr.getOrgBegintime());
		s.setOrgEndtime(appr.getOrgEndtime());
		s.setOrgImgUrl(appr.getOrgImgUrl());
		s.setTaxNum(appr.getTaxNum());
		s.setTaxImgUrl(appr.getTaxImgUrl());
		s.setIstaxpayer(appr.getIstaxpayer());
		s.setIstaxpayerImgUrl(appr.getIstaxpayerImgUrl());
		s.setCorCome(appr.getCorCome());
		s.setCorName(appr.getCorName());
		s.setCorNum(appr.getCorNum());
		s.setCorImgUrl(appr.getCorImgUrl());
		s.setBankPeople(appr.getBankPeople());
		s.setBankId(appr.getBankId());
		s.setBankState(appr.getBankState());
		s.setBankCity(appr.getBankCity());
		s.setBankName(appr.getBankName());
		s.setBankNum(appr.getBankNum());
		s.setBankImgUrl(appr.getBankImgUrl());
		s.setEnterType(appr.getEnterType());
		s.setCashDeposit(appr.getCashDeposit());
		s.setPlatformUseFee(appr.getPlatformUseFee());
		s.setProtocolFile(appr.getProtocolFile());
		s.setManagerId(appr.getManagerId());
		s.setManagerName(appr.getManagerName());
		
		return s;
	}

}
