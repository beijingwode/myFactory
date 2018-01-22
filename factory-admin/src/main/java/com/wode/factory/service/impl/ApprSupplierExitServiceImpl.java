package com.wode.factory.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.util.SeasonUtil;
import com.wode.factory.facade.EntBenefitFacade;
import com.wode.factory.mapper.ApprProductDao;
import com.wode.factory.mapper.ApprShopDao;
import com.wode.factory.mapper.ApprSupplierDao;
import com.wode.factory.mapper.ApprSupplierExitDao;
import com.wode.factory.mapper.BrandProducttypeDao;
import com.wode.factory.mapper.EnterpriseUserDao;
import com.wode.factory.mapper.InventoryDao;
import com.wode.factory.mapper.ProductAttributeMapper;
import com.wode.factory.mapper.ProductBrandMapper;
import com.wode.factory.mapper.ProductDao;
import com.wode.factory.mapper.ProductDetailListMapper;
import com.wode.factory.mapper.ProductECardDao;
import com.wode.factory.mapper.ProductLadderDao;
import com.wode.factory.mapper.ProductParameterValueMapper;
import com.wode.factory.mapper.ProductSpecificationValueMapper;
import com.wode.factory.mapper.ProductSpecificationsDao;
import com.wode.factory.mapper.ProductSpecificationsImageDao;
import com.wode.factory.mapper.ProductThirdPriceDao;
import com.wode.factory.mapper.PromotionProductDao;
import com.wode.factory.mapper.ShippingFreeRuleDao;
import com.wode.factory.mapper.ShippingTemplateDao;
import com.wode.factory.mapper.ShippingTemplateRuletDao;
import com.wode.factory.mapper.ShopDao;
import com.wode.factory.mapper.SuborderDao;
import com.wode.factory.mapper.SupplierCategoryMapper;
import com.wode.factory.mapper.SupplierCloseCmdDao;
import com.wode.factory.mapper.SupplierDurationVoDao;
import com.wode.factory.mapper.SupplierExchangeProductDao;
import com.wode.factory.mapper.SupplierMapper;
import com.wode.factory.mapper.UserExchangeTicketDao;
import com.wode.factory.mapper.UserShareDao;
import com.wode.factory.mapper.UserTicketHisDao;
import com.wode.factory.model.ApprSupplierExit;
import com.wode.factory.model.EntSeasonAct;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Supplier;
import com.wode.factory.service.ApprSupplierExitService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("apprSupplierExitService")
public class ApprSupplierExitServiceImpl extends FactoryEntityServiceImpl<ApprSupplierExit> implements ApprSupplierExitService {
	@Autowired
	ApprSupplierExitDao dao;
	@Autowired
	EntBenefitFacade entBenefitFacade;
	@Autowired
	SuborderDao subOrderDao;
	
	//无订单
	//结算系===================
	@Autowired
	SupplierDurationVoDao supplierDurationVoDao;//商家结算设置 
	@Autowired
	SupplierCloseCmdDao supplierCloseCmdDao;//结算周期CMD t_supplier_close_cmd
	
	//商品系==================
	//商品收藏 t_collection_product
	@Autowired
	PromotionProductDao promotionProductDao;//活动商品 t_promotion_product
	//商品标签 t_product_tag
	//商品内部分类 t_product_store_category
	@Autowired
	ProductSpecificationsDao productSpecificationsDao;//sku价格 t_product_specifications
	@Autowired
	ProductSpecificationsImageDao productSpecificationsImageDao;//sku主图 t_product_specifications_image
	@Autowired
	InventoryDao inventoryDao;//sku库存
	@Autowired
	ProductSpecificationValueMapper productSpecificationValueMapper; //商品已选规格值t_product_specification_value
	@Autowired
	ProductParameterValueMapper productParameterValueMapper;//商品参数t_product_parameter_value
	//商品图 t_product_image
	@Autowired
	ProductDetailListMapper productDetailListMapper;//商品附件清单t_product_detail_list
	@Autowired
	ProductAttributeMapper productAttributeMapper;//商品属性t_product_attribute
	@Autowired
	ProductBrandMapper productBrandMapper;//商品品牌t_product_brand
	@Autowired
	ProductECardDao productECardDao;//电子卡券信息t_product_e_card
	@Autowired
	ProductThirdPriceDao productThirdPriceDao;//第三方比价  t_product_third_price
	//试用问券问题  t_questionnaire_question
	//问券选项 t_questionnaire_option
	//问券结果 t_questionnaire_answer
	//商品问券t_product_questionnaire
	@Autowired
	ProductLadderDao productLadderDao;//阶梯价格  t_product_ladder
	@Autowired
	ProductDao productDao;//商品表t_product 
	@Autowired
	ApprProductDao apprProductDao;//商品临时表t_appr_product
	@Autowired
	ShippingTemplateDao shippingTemplateDao;//运费包邮规则t_shipping_free_rule
	@Autowired
	ShippingTemplateRuletDao shippingTemplateRuletDao;//运费模板规则 t_shipping_template_rule
	@Autowired
	ShippingFreeRuleDao shippingFreeRuleDao;//运费模板t_shipping_template
	
	//商家及店铺==============
	//商家发货地址t_supplier_address
	//常用快递公司t_supplier_express
	//外部对接密码表t_supplier_app_security
	@Autowired
	UserExchangeTicketDao userExchangeTicketDao;//员工换领券t_user_exchange_ticket
	@Autowired
	UserTicketHisDao userTicketHisDao;//换领券使用记录t_user_ticket_his
	@Autowired
	SupplierExchangeProductDao supplierExchangeProductDao;//换领商品记录t_supplier_exchange_product
	//店铺收藏t_collection_shop
	//店铺活动 t_shop_promotion
	//店铺设置t_shop_setting
	@Autowired
	ShopDao shopDao;//店铺表t_shop
	@Autowired
	BrandProducttypeDao brandProducttypeDao;//品牌品类关联表 t_brand_producttype
	@Autowired
	SupplierCategoryMapper supplierCategoryMapper;//商家经营分类t_supplier_category
	@Autowired
	SupplierMapper supplierMapper;//商家表t_supplier
	@Autowired
	ApprSupplierDao apprSupplierDao;//商家临时表（审核用） t_appr_supplier
	@Autowired
	ApprShopDao apprShopDao;//店铺临时表t_appr_shop
	//商家分享项 t_user_share_item
	@Autowired
	UserShareDao userShareDao;//商家分享表 
	//员工用户表t_user修改状态
	@Autowired
	EnterpriseUserDao enterpriseUserDao;//企业员工表t_enterprise_user
	
	
	
	
	@Override
	public ApprSupplierExitDao getDao() {
		return dao;
	}


	@Override
	public Long getId(ApprSupplierExit entity) {
		return entity.getId();
	}

	@Override
	public void setId(ApprSupplierExit entity, Long id) {
		entity.setId(id);
	}


	@Override
	public List<ApprSupplierExit> findCountsByMap(Map<String, Object> map) {
		return dao.findCountsByMap(map);
	}


	@Override
	public PageInfo selectByMap(Map<String, Object> paramMap,String userName) {
		PageHelper.startPage(paramMap);
		List<ApprSupplierExit> pageInfo = dao.selectByMap(paramMap);
		//在售商品数\订单总数\未结订单总数\未结对账单数\现金券余额
		for (ApprSupplierExit apprSuppExit : pageInfo) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("supplierId", apprSuppExit.getSupplierId());
			List<ApprSupplierExit> counts = dao.findCountsByMap(map);
			apprSuppExit.setProductCnt(counts.get(0).getProductCnt());
			apprSuppExit.setOrderCnt(counts.get(1).getOrderCnt());
			apprSuppExit.setUnCloseOrderCnt(counts.get(2).getUnCloseOrderCnt());
			apprSuppExit.setUnClosebillCnt(counts.get(3).getUnClosebillCnt());
			EntSeasonAct esa = entBenefitFacade.getEntSeasonAct(apprSuppExit.getSupplierId(), SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(),userName);
			if(esa != null){
				apprSuppExit.setCashBalance(esa.getAllCashSum().subtract(esa.getGiveCashSum()));
			}
		}
		return new PageInfo(pageInfo);
	}


	/**
	 * 退出执行
	 * 开启商家相关信息删除操作
	 */
	@Override
	public void deleteSupplierCorrelationMsg(Long supplierId) {
		List<Suborder> list = subOrderDao.getMsgBySupplierId(supplierId);
		if(list.size() == 0){//没有订单数
			//删除商家结算设置 
			supplierDurationVoDao.deleteBySupplierId(supplierId);
			//删除结算周期CMD
			supplierCloseCmdDao.deleteBySupplierId(supplierId);
			
			//删除sku库存
			inventoryDao.deleteBySupplierId(supplierId);
			//删除sku价格
			productSpecificationsDao.deleteBySupplierId(supplierId);
			//删除sku主图
			productSpecificationsImageDao.deleteBySupplierId(supplierId);
			//删除商品已选规格值t_product_specification_value
			productSpecificationValueMapper.deleteBySupplierId(supplierId);
			
			commonResource(supplierId);
			//删除商家表t_supplier
			supplierMapper.deleteBySupplierId(supplierId);
		}else{//有订单数
			//删除sku库存
			inventoryDao.deleteBySupplierId(supplierId);
			commonResource(supplierId);
			//修改商家表中的状态
			Supplier supplier = new Supplier();
			supplier.setId(supplierId);
			supplier.setStatus(-2);
			supplierMapper.updateSupplierStatus(supplier);
		}
	}
	
	public void commonResource(Long supplierId){
		//删除商品参数t_product_parameter_value
		productParameterValueMapper.deleteBySupplierId(supplierId);
		//删除商品图 t_product_image
		productDetailListMapper.deleteProductImageBySupplierId(supplierId);
		//删除商品附件清单t_product_detail_list
		productDetailListMapper.deleteBySupplierId(supplierId);
		//删除商品属性t_product_attribute
		productAttributeMapper.deleteBySupplierId(supplierId);
		//删除商品收藏
		promotionProductDao.deleteCollectionProductBySupplierId(supplierId);
		//删除活动商品
		promotionProductDao.deleteBySupplierId(supplierId);
		//删除商品标签
		promotionProductDao.deleteProductTagBySupplierId(supplierId);
		//删除商品内部分类 t_product_store_category
		promotionProductDao.deleteProductStoreCategoryBySupplierId(supplierId);
		//删除商家分享项 t_user_share_item
		apprShopDao.deleteUserShareItemBySupplierId(supplierId);
		//删除商家分享表 
		userShareDao.deleteBySupplierId(supplierId);
		//删除商品品牌t_product_brand
		productBrandMapper.deleteBySupplierId(supplierId);
		//删除电子卡券信息t_product_e_card
		productECardDao.deleteBySupplierId(supplierId);
		//删除第三方比价  t_product_third_price
		productThirdPriceDao.deleteBySupplierId(supplierId);
		//删除试用问券问题  t_questionnaire_question
		productThirdPriceDao.deleteQuestionnaireQuestionBySupplierId(supplierId);
		//删除问券选项 t_questionnaire_option
		productThirdPriceDao.deleteQuestionnaireOptionBySupplierId(supplierId);
		//删除问券结果 t_questionnaire_answer
		productThirdPriceDao.deleteQuestionnaireAnswerBySupplierId(supplierId);
		//删除商品问券t_product_questionnaire
		productThirdPriceDao.deleteProductQuestionnaireBySupplierId(supplierId);
		//删除阶梯价格  t_product_ladder
		productLadderDao.deleteBySupplierId(supplierId);
		//删除商品表t_product
		productDao.deleteBySupplierId(supplierId);
		//删除商品临时表t_appr_product
		apprProductDao.deleteBySupplierId(supplierId);
		//删除运费模板规则 t_shipping_template_rule
		shippingTemplateRuletDao.deleteBySupplierId(supplierId);
		//删除运费包邮规则t_shipping_free_rule
		shippingFreeRuleDao.deleteBySupplierId(supplierId);
		//删除运费模板t_shipping_template
		shippingTemplateDao.deleteBySupplierId(supplierId);
		//删除商家发货地址t_supplier_address
		shippingFreeRuleDao.deleteSupplierAddressBySupplierId(supplierId);
		//删除常用快递公司t_supplier_express
		shippingFreeRuleDao.deleteSupplierExpressBySupplierId(supplierId);
		//删除外部对接密码表t_supplier_app_security
		shippingFreeRuleDao.deleteSupplierAppSecurityBySupplierId(supplierId);
		//删除换领券使用记录t_user_ticket_his
		userTicketHisDao.deleteBySupplierId(supplierId);
		//删除员工换领券t_user_exchange_ticket
		userExchangeTicketDao.deleteBySupplierId(supplierId);
		//删除换领商品记录t_supplier_exchange_product
		supplierExchangeProductDao.deleteBySupplierId(supplierId);
		//删除店铺收藏t_collection_shop
		shopDao.deleteCollectionShopBySupplierId(supplierId);
		//删除店铺活动 t_shop_promotion
		shopDao.deleteShopPromotionBySupplierId(supplierId);
		//删除店铺设置t_shop_setting
		shopDao.deleteShopSettingBySupplierId(supplierId);
		//删除店铺表
		shopDao.deleteBySupplierId(supplierId);
		//删除品牌品类关联表 t_brand_producttype
		brandProducttypeDao.deleteBySupplierId(supplierId);
		//删除商家经营分类t_supplier_category
		supplierCategoryMapper.deleteBySupplierId(supplierId);
		//删除商家临时表（审核用） t_appr_supplier
		apprSupplierDao.deleteBySupplierId(supplierId);
		//删除店铺临时表t_appr_shop
		apprShopDao.deleteBySupplierId(supplierId);
		//修改员工用户表t_user修改状态
		userShareDao.updateUserBySupplierId(supplierId);
		//删除企业员工表t_enterprise_user
		enterpriseUserDao.deleteBySupplierId(supplierId);
		
	}


	@Override
	public PageInfo getApprSupplierList(Map<String, Object> params,String userName) {
		PageHelper.startPage(params);
		if(params.get("status").equals("6")){
			String status = "2,3,4";
			params.put("status", status);
		}
		List<ApprSupplierExit> pageInfo = dao.getApprSupplierList(params);
		//在售商品数\订单总数\未结订单总数\未结对账单数\现金券余额
		for (ApprSupplierExit apprSuppExit : pageInfo) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("supplierId", apprSuppExit.getSupplierId());
			List<ApprSupplierExit> counts = dao.findCountsByMap(map);
			apprSuppExit.setProductCnt(counts.get(0).getProductCnt());
			apprSuppExit.setOrderCnt(counts.get(1).getOrderCnt());
			apprSuppExit.setUnCloseOrderCnt(counts.get(2).getUnCloseOrderCnt());
			apprSuppExit.setUnClosebillCnt(counts.get(3).getUnClosebillCnt());
			EntSeasonAct esa = entBenefitFacade.getEntSeasonAct(apprSuppExit.getSupplierId(), SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(),userName);
			if(esa != null){
				apprSuppExit.setCashBalance(esa.getAllCashSum().subtract(esa.getGiveCashSum()));
			}
		}
		return new PageInfo(pageInfo);
	}


	@Override
	public ApprSupplierExit getApprSupplierExitBySupplierId(Long supplierId) {
		return dao.getApprSupplierExitBySupplierId(supplierId);
	}
}
