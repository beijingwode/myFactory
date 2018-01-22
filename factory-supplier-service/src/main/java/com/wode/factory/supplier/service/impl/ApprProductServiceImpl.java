/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.ObjectUtils;
import com.wode.factory.company.query.UserExchangeTicketVo;
import com.wode.factory.company.service.EnterpriseUserService;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.ApprProduct;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.Inventory;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductAttribute;
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.model.ProductParameterValue;
import com.wode.factory.model.ProductQuestionnaire;
import com.wode.factory.model.ProductShipping;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.model.SpecificationValue;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.model.SupplierSpecification;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.ProductService;
import com.wode.factory.supplier.dao.ApprProductDao;
import com.wode.factory.supplier.dao.ProductDao;
import com.wode.factory.supplier.model.SupplierQuestionnaire;
import com.wode.factory.supplier.query.ProductSpecificationsQuery;
import com.wode.factory.supplier.query.SpecificationValueQuery;
import com.wode.factory.supplier.service.ApprProductService;
import com.wode.factory.supplier.service.InventoryService;
import com.wode.factory.supplier.service.ProductAttributeService;
import com.wode.factory.supplier.service.ProductDetailListService;
import com.wode.factory.supplier.service.ProductLadderService;
import com.wode.factory.supplier.service.ProductParameterValueService;
import com.wode.factory.supplier.service.ProductQuestionnaireService;
import com.wode.factory.supplier.service.ProductShippingService;
import com.wode.factory.supplier.service.ProductSpecificationValueService;
import com.wode.factory.supplier.service.ProductSpecificationsImageService;
import com.wode.factory.supplier.service.ProductSpecificationsService;
import com.wode.factory.supplier.service.QuestionnaireOptionService;
import com.wode.factory.supplier.service.QuestionnaireQuestionService;
import com.wode.factory.supplier.service.SpecificationValueService;
import com.wode.factory.supplier.service.SupplierExchangeProductService;
import com.wode.factory.supplier.service.SupplierQuestionnaireService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.service.SupplierSpecificationService;
import com.wode.factory.supplier.service.UserExchangeTicketService;

import cn.org.rapid_framework.page.Page;

@Service("apprProductService")
public class ApprProductServiceImpl extends BaseService<ApprProduct, java.lang.Long> implements  ApprProductService{
	@Autowired
	@Qualifier("apprProductDao")
	private ApprProductDao apprProductDao;
	@Autowired
	@Qualifier("productDao")
	private ProductDao productDao;
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;

	@Autowired
	private ProductShippingService productShippingService;	
	
	@Autowired
	private SupplierSpecificationService supplierSpecificationService;

	@Autowired
	private SpecificationValueService specificationValueService;
	
	@Autowired
	@Qualifier("productAttributeService")
	private ProductAttributeService productAttributeService;

	@Autowired
	@Qualifier("productParameterValueService")
	private ProductParameterValueService productParameterValueService;

	@Autowired
	@Qualifier("productSpecificationValueService")
	private ProductSpecificationValueService productSpecificationValueService;

	@Autowired
	@Qualifier("productDetailListService")
	private ProductDetailListService productDetailListService;

	@Autowired
	@Qualifier("productSpecificationsService")
	private ProductSpecificationsService productSpecificationsService;

	@Autowired
	@Qualifier("inventoryService")
	private InventoryService inventoryService;

	@Autowired
	@Qualifier("productSpecificationsImageService")
	private ProductSpecificationsImageService productSpecificationsImageService;
	
	@Resource
	private ProductService productService;
	
	@Resource
	private ApprProductService apprProductService;

	@Autowired
	private RedisUtil redis;

	@Resource
	private SupplierExchangeProductService supplierExchangeProductService;
	
	@Resource
	private EnterpriseUserService enterpriseUserService;
	@Autowired
	private ProductQuestionnaireService productQuestionnaireService;
	@Autowired
	private SupplierQuestionnaireService supplierQuestionnaireService;
	@Autowired
	private QuestionnaireQuestionService questionnaireQuestionService;
	@Autowired
	private QuestionnaireOptionService questionnaireOptionService;
	
	@Autowired
	private ProductLadderService productLadderService;
	
	@Autowired
	private UserExchangeTicketService  userExchangeTicketService;
	/**
	 * 新增商品或者修改商品
	 *
	 * @param map
	 */
	@Transactional
	public ApprProduct saveOrUpdateProduct(Map map) {
		Supplier supplier = (Supplier) map.get("supplier");//供应商对象
		////更新product基本信息
	    String apprid= (String) map.get("apprid");
		String productId = (String) map.get("productid");
		String status = (String) map.get("status");
		String name = (String) map.get("name");
		String fullName = (String) map.get("fullName");
		String promotion = (String) map.get("promotion");
		String marque = (String) map.get("marque");
		String roughWeight = (String) map.get("roughWeight");
		String length = (String) map.get("length");
		String height = (String) map.get("height");
		String barcode = (String) map.get("barcode");
		String netWeight = (String) map.get("netWeight");
		String width = (String) map.get("width");
		String bulk = (String) map.get("bulk");
		String province = (String) map.get("province");
		String town = (String) map.get("town");
		String county = (String) map.get("county");
		String produceaddress = (String) map.get("produceaddress");
		String carriage = (String) map.get("carriage");
		String introduction = (String) map.get("introduction");
		String introductionMobile = (String) map.get("introductionMobile");
		String afterService = (String) map.get("afterService");
		String stockLockType = (String) map.get("stockLockType");
		String categoryId = (String) map.get("categoryId");
		String sendProvince = (String) map.get("sendProvince");
		String sendTown = (String) map.get("sendTown");
		String sendAddress = (String) map.get("sendAddress");
		String brandId = (String) map.get("brandId");
		String specificationType = (String) map.get("specificationType");
		String savestate =(String)map.get("savestate");
		String rdFreightType = (String) map.get("rdFreightType");
		String rdUserFreight = (String) map.get("rdUserFreight");
		String shippingTemplateId = (String) map.get("shippingTemplateId");
		Integer limitCnt = (Integer)map.get("limitCnt");
		String areasName = (String) map.get("areasName");
		String areasCode = (String) map.get("areasCode");
		Integer saleKbn = (Integer)map.get("saleKbn");
		String saleNote = (String) map.get("saleNote");
		Integer divLevel = NumberUtil.toInteger(map.get("divLevel"));//换领-----员工分配范围级别
		Long questionnaireId = NumberUtil.toLong(map.get("questionnaireId"));
		BigDecimal empPrice = NumberUtil.toBigDecimal(map.get("empPrice"));
		if(5==saleKbn) {//如果是试用商品
			empPrice = NumberUtil.toBigDecimal(map.get("trialPrice"));
			if(BigDecimal.ZERO.compareTo(empPrice) != 0){
				saleNote = "购买并评价后返现"+empPrice.setScale(2, BigDecimal.ROUND_DOWN) + "元";
			}else{
				saleNote = "";
			}
		} else if(50==saleKbn) {
			saleKbn = 5;
			empPrice = BigDecimal.ZERO;
			saleNote = "回答问卷后，享受特惠";
		}
		Integer empCash = NumberUtil.toInteger(map.get("empCash"));
		Integer empLevel = NumberUtil.toInteger(map.get("empLevel"));
		
		//如果是修改就根据商品id获取商品信息
		ApprProduct product = this.apprProductDao.getById(new Long(apprid));
		
		if (product == null || product.getStatus()==2) {
			if(product!=null) productId = ""+product.getProductId();
			product = new ApprProduct();
			product.setId(null); //自动生成临时表id
			product.setProductId(new Long(productId));//设置在售商品id为临时表的productId或者自动生成的productId（新增商品）
		}
		product.setShopId((Long)map.get("shopId"));

		//这个是控制问题商品修改后点击保存变为待售商品
		if(product.getStatus()==null || product.getStatus()<2) {//如果等于空，或者不是在编辑状态时，
			product.setStatus(new Integer(status));
		}
		//设置上架状态为暂存
		product.setIsMarketable(0);
		
		//设置limit_kbn
		product.setLimitKbn(ObjectUtils.returnIsNotNullT(MapUtils.getInteger(map, "limitKbn"), 0));
		//设置锁定为空未锁定，换购商品才锁定
		product.setLocked(0);
		product.setName(name);
		product.setFullName(fullName);
		product.setPromotion(promotion);
		product.setMarque(marque);
		product.setRoughWeight(com.wode.common.util.StringUtils.isEmpty(roughWeight)?null:new BigDecimal(roughWeight));
		product.setLength(com.wode.common.util.StringUtils.isEmpty(length)?null:new BigDecimal(length));
		product.setHeight(com.wode.common.util.StringUtils.isEmpty(height)?null:new BigDecimal(height));
		product.setBarcode(barcode);
		product.setNetWeight(com.wode.common.util.StringUtils.isEmpty(netWeight)?null:new BigDecimal(netWeight));
		product.setWidth(com.wode.common.util.StringUtils.isEmpty(width)?null:new BigDecimal(width));
		product.setBulk(com.wode.common.util.StringUtils.isEmpty(bulk)?null:new BigDecimal(bulk));
		product.setProvince(new Long(province));
		product.setTown(new Long(town));
		product.setCounty(new Long(county));
		product.setProduceaddress(produceaddress);
		product.setIntroduction(introduction);
		product.setIntroductionMobile(introductionMobile);
		product.setStockLockType(new Integer(stockLockType));
		product.setAfterService(afterService);
		product.setCategoryId(new Long(categoryId));
		product.setSupplierId(supplier.getId());
		product.setQuestionnaireId(questionnaireId==null?-1L:questionnaireId);
		
		//用模板计算运费
		if("1".equals(rdFreightType) && "2".equals(rdUserFreight) && !com.wode.common.util.StringUtils.isEmpty(shippingTemplateId)) {
			product.setCarriage(BigDecimal.ZERO);
		} else {
			product.setCarriage(new BigDecimal(carriage.trim()));
			rdUserFreight="3";
		}
		
		if (product.getId() == null) {
			product.setCreateDate(new Date());
		}
		product.setUpdateDate(new Date());
		product.setSendProvince(new Long(sendProvince));
		product.setSendTown(new Long(sendTown));
		product.setSendAddress(sendAddress);
		product.setBrandId(new Long(brandId));
		product.setType("1");
		product.setModel("1");
		product.setLimitCnt(limitCnt==null?0:limitCnt);
		product.setAreasCode(areasCode==null?"0":areasCode);
		product.setAreasName(areasName==null?"全国":areasName);
		product.setSaleKbn(saleKbn);
		product.setSaleNote(saleNote);
		product.setDivLevel(divLevel);
		product.setEmpPrice(empPrice);
		product.setEmpCash(empCash);
		product.setEmpLevel(empLevel);
		product.setSavestate(new Integer(savestate));
		

		if(!product.getSaleKbn().equals(5)) {
			product.setQuestionnaireId(-1L);
		}
		// 问卷
		ProductQuestionnaire pq = null;
		if(NumberUtil.isGreaterZero(product.getQuestionnaireId())) {
			pq=productQuestionnaireService.getById(product.getQuestionnaireId());
			if(pq == null) {
				productQuestionnaireService.delTempByProductId(product.getProductId());
				
				// 通过模板生成商品问卷
				SupplierQuestionnaire sq = supplierQuestionnaireService.getById(product.getQuestionnaireId());
				if(sq != null) {
					pq = new ProductQuestionnaire();
					pq.setSupplierId(sq.getSupplierId());
					pq.setProductId(product.getProductId());
					pq.setProductName(product.getFullName());
					pq.setTemplateTitle(sq.getTemplateTitle());
					pq.setTestFlg(sq.getTestFlg());
					pq.setCreateDate(new Date());
					pq.setTemplateId(sq.getId());
					pq.setAnswerCnt(0);
					pq.setStatus(1);	// 1:待审核
					productQuestionnaireService.save(pq);
					
					questionnaireQuestionService.copyFromTemplate(sq.getId(), pq.getId());
					questionnaireOptionService.copyFromTemplate(sq.getId(), pq.getId());
	
					product.setQuestionnaireId(pq.getId());
				} else {
					product.setQuestionnaireId(-1L);
				}
			} else {
				if(pq.getStatus()>2) {
					pq.setEndDate(null);
					pq.setStatus(1);
				}
			}
		} else {
			productQuestionnaireService.delTempByProductId(product.getProductId());
		}
		
		this.apprProductDao.saveOrUpdate(product);  //这里才产生临时表的id，如何传递到
		
		Map delMap = new HashMap();
		delMap.put("productid", product.getId());
		//删除已有的属性
		productAttributeService.removeAllByProductid(delMap);
		////产品属性
		String[] attribute_result = (String[]) map.get("attribute_result");
		if (attribute_result != null && attribute_result.length > 0) {
			for (int i = 0; i < attribute_result.length; i++) {
				String attribute = attribute_result[i];
				if (attribute != null && !attribute.trim().equals("")) {
					String[] attributes = attribute.split("_",-1);
					ProductAttribute pa = new ProductAttribute();
					pa.setAttributeId(new Long(attributes[0]));
					//pa.setProductId(product.getProductId());
					pa.setProductId(product.getId());
					pa.setValue(attributes[2]);
					productAttributeService.save(pa);
				}
			}
		}

		//删除已有的属性
		productParameterValueService.removeAllByProductid(delMap);
		////产品参数
		String[] parameter_result = (String[]) map.get("parameter_result");
		if (parameter_result != null && parameter_result.length > 0) {
			for (int i = 0; i < parameter_result.length; i++) {
				String parameter = parameter_result[i];
				if (parameter != null && !parameter.trim().equals("")) {
					String[] parameters = parameter.split("_",-1);
					if(parameters.length < 2) continue;
					ProductParameterValue pa = new ProductParameterValue();
					pa.setParameterGroupId(new Long(parameters[0]));
					pa.setProductId(product.getId());
					//pa.setProductId(product.getProductId());					
					if (!parameters[2].equals("")) {
						pa.setParameterValue(parameters[2].replaceAll(" +", ""));
					}
					if (parameters[2].equals("")) continue;				
					productParameterValueService.save(pa);
				}
			}
		}

		//删除已有的规格
		////产品规格
		String[] specification_result = null;
		Map<String,String> mapSkuSpecification=new HashMap<String,String>();
		if("1".equals(specificationType)) {
			String[] skuSpecification = (String[]) map.get("skuSpecification");
			String[] skuSpecificationId = (String[]) map.get("skuSpecificationId");
			
			SupplierSpecification specification = mergeSkuSpecification(product, skuSpecification);
			
			String tmp = specification.getId()+"_";
			for (int i = 0; i < skuSpecificationId.length; i++) {
				mapSkuSpecification.put(skuSpecificationId[i], skuSpecification[i]);
				tmp = tmp+skuSpecification[i] + ",";
			}
			tmp = tmp.substring(0, tmp.length()-1);
			specification_result = new String[]{tmp};
		} else if("2".equals(specificationType)) {
			specification_result = (String[]) map.get("self_specification_result");
			
		} else {
			specification_result = (String[]) map.get("specification_result");//[82_白色,黑色, 83_36.5,37.5]
		}
		
		////产品规格
		productSpecificationValueService.removeAllByProductid(delMap);
		Map<String, Long> specificationMap = new HashMap<String, Long>();
		if (specification_result != null && specification_result.length > 0) {
			for (int i = 0; i < specification_result.length; i++) {
				String specification = specification_result[i];
				if (specification != null && !specification.trim().equals("")) {
					String[] specifications = specification.split("_",-1);
					Long specificationId = new Long(specifications[0]);
					String specificationValue = specifications[1];
					if(com.wode.common.util.StringUtils.isEmpty(specificationValue)) continue;
					String[] specificationValues = specificationValue.split(",");
					Map<String, Comparable> mapSpeTemp = new HashMap();
					mapSpeTemp.put("productId", product.getId());
					mapSpeTemp.put("specificationId", specificationId);
					for (int j = 0; j < specificationValues.length; j++) {
//						mapSpeTemp.put("specificationValue", specificationValues[j]);
//						List<ProductSpecificationValue> palist = productSpecificationValueService.findAllBymap(mapSpeTemp);
//
						ProductSpecificationValue pa = null;
//						if (palist != null && palist.size() > 0) {
//							pa = palist.get(0);
//						}
//						if (pa == null) {
							pa = new ProductSpecificationValue();
							pa.setSpecificationId(specificationId);
							pa.setProductId(product.getId());
							//pa.setProductId(product.getProductId());
							pa.setSpecificationValue(specificationValues[j]);
							pa.setIsDelete(0);
							pa.setOrders(j);
//						} else {
//							pa.setIsDelete(0);
//						}

						productSpecificationValueService.saveOrUpdate(pa);
						specificationMap.put(specificationValues[j], pa.getId());
					}
				}
			}
		}
		
		//删除已有的清单
		productDetailListService.removeAllByProductid(delMap);
		////商品清单
		String[] detaillist_result = (String[]) map.get("detaillist_result");
		if (detaillist_result != null && detaillist_result.length > 0) {
			for (int i = 0; i < detaillist_result.length; i++) {
				String detaillist = detaillist_result[i];
				if (detaillist != null && !detaillist.trim().equals("") && !detaillist.trim().equals("_")) {
					String[] detaillists = detaillist.split("_");
					ProductDetailList pa = new ProductDetailList();
					pa.setIsdelete(0);
					pa.setName(detaillists[0]);
					pa.setNum(new Integer(detaillists[1]));
					pa.setOrders(i);
					pa.setProductId(product.getId());
					//pa.setProductId(product.getProductId());
					productDetailListService.save(pa);
				}
			}
		}

		List<BigDecimal> pricelist = new ArrayList<BigDecimal>();//价格，冒泡排序使用
		Integer allnum = 0;//总库存
		//删除sku（set isDelete=1）
		delMap.put("isDelete", 1);
		productSpecificationsService.removeAllByProductid(delMap);

		Map specificationNameMap = new HashMap();
		Map<Long, ProductSpecificationValue> productSpecificationValueMap = new HashMap();
		specificationNameMap.put("productId", product.getId());
		List<ProductSpecificationValue> psvlist = productSpecificationValueService.findAllBymap(specificationNameMap);
		for (ProductSpecificationValue pv : psvlist) {
			if("1".equals(specificationType)) {
				pv.setSpecificationName("规格");
			}
			productSpecificationValueMap.put(pv.getId(), pv);
		}

		boolean changePrice = false;
		BigDecimal productPrice = BigDecimal.ZERO;
		ProductSpecificationsQuery query1 = new ProductSpecificationsQuery();
		//query1.setProductId(product.getProductId());
		query1.setProductId(product.getId());
		query1.setIsDelete(0);
		query1.setPageSize(100);
		Page<ProductSpecifications> oldSkus = productSpecificationsService.findPage(query1);
		
		Map<String,Long> mapItemValuesSkuId = new HashMap<String,Long>();
		
		Map<String, List<Long>> specificationsMap = new HashMap<String, List<Long>>();//Map<红色，[1,2]>
		////商品sku [白色,36.5_10000000_25.000_10000_12_12, 白色,37.5_10000000_25.000_10000_12_12, 黑色,36.5_10000000_25.000_10000_12_12, 黑色,37.5_10000000_25.000_10000_12_12]
		String[] specifications_result = (String[]) map.get("specifications_result");
		List<ProductSpecifications> skus = new ArrayList<ProductSpecifications>();
		List<Inventory> inventorys = new ArrayList<Inventory>();
		if (specifications_result != null && specifications_result.length > 0) {
			for (int i = 0; i < specifications_result.length; i++) {
				String specifications = specifications_result[i];
				if (specifications != null && !specifications.trim().equals("")) {
					String[] specificationss = specifications.split("_",-1);//添加参数-1可以把空值也取出来

					//pa.setStock(new Integer(specificationss[3]));
					//pa.setWarnnum(new Integer(specificationss[4]));

					if(!"".equals(specificationss[3])){
						allnum = allnum + new Integer(specificationss[3]);
					}
					
					String specificationss0 = specificationss[0];	
					String[] s0 = specificationss0.split(",");
					List<Long> s0list = new ArrayList<Long>();
					String ids = "";
					String values = "";
					String sValues = "";
					for (int j = 0; j < s0.length; j++) {
						String kikaku = s0[j];
						if("1".equals(specificationType)) {
							kikaku=mapSkuSpecification.get(kikaku);
						}
						s0list.add(specificationMap.get(kikaku));//{37.5=1108028757968267, 36.5=1108028756788618, 白色=1108027013039496, 黑色=1108027666630025}
					}
					Collections.sort(s0list, new Comparator<Long>() {
						public int compare(Long arg0, Long arg1) {
							if(arg0==null) return 1;
							return arg0.compareTo(arg1);
						}
					});
					//bubbleSortLong(s0list);
					for (Long ll : s0list) {
						ids = ids + ll + ",";
						ProductSpecificationValue pv = productSpecificationValueMap.get(ll);
						if("".equals(values)) {
							values="\""+pv.getSpecificationName()+"\":\""+pv.getSpecificationValue()+"\"";
							sValues=pv.getSpecificationValue();
						} else {
							values+=",\""+pv.getSpecificationName()+"\":\""+pv.getSpecificationValue()+"\"";
							sValues+="/"+pv.getSpecificationValue();
						}
					}
					if (ids != null && !ids.equals("")) {
						ids = ids.substring(0, ids.length() - 1);
					}
					
					ProductSpecifications pa = null;
					for (ProductSpecifications old : oldSkus.getResult()) {
						//if(("{"+values+"}").equals(old.getItemValues())) {
						if((values).equals(old.getItemValues())) {
							pa = old;
							break;
						}
					}
					
					if (pa !=null) {
						pa.setIsDelete(0);
						pa.setProductCode(specificationss[1]);
						//pa.setProductId(product.getId());
						if(!changePrice){//如果价格有变化
							if((pa.getPrice()!=null && !"".equals(specificationss[2])) && (pa.getMaxFucoin()!=null && !"".equals(specificationss[5]))) {
								if(pa.getPrice().compareTo(NumberUtil.toBigDecimal(specificationss[2]))==0 
										&& pa.getMaxFucoin().compareTo(NumberUtil.toBigDecimal(specificationss[5]))==0) {
									//价格没有变化
								} else {
									//价格改变
									changePrice=true;
								}
							} else {
								//价格改变
								changePrice=true;								
							}
						}
						
						pa.setPrice(NumberUtil.toBigDecimal(specificationss[2]));
						pa.setMaxFucoin(NumberUtil.toBigDecimal(specificationss[5]));
						if(specificationss.length >= 6 ){//如果等于6就直接添加新的值
							if(specificationss.length>6) {
								pa.setInternalPurchasePrice(NumberUtil.toBigDecimal(specificationss[6]));
								pa.setMinLimitNum(NumberUtil.toInteger(specificationss[7]));
							}else {
								pa.setInternalPurchasePrice(null);
								pa.setMinLimitNum(1);
							}
						}else{//不等于6用原件减优惠
							pa.setInternalPurchasePrice(pa.getPrice().subtract(pa.getMaxFucoin()));
							pa.setMinLimitNum(1);
						}
					} else {
						changePrice=true;
						
						pa = new ProductSpecifications();
						pa.setIsDelete(0);
						pa.setProductCode(specificationss[1]);
						pa.setPrice(NumberUtil.toBigDecimal(specificationss[2]));
						pa.setMaxFucoin(NumberUtil.toBigDecimal(specificationss[5]));
						pa.setItemValues("{"+values+"}");
						pa.setItemids(ids);
						pa.setProductId(product.getId());
						if(specificationss.length >= 6 ){//如果等于6就直接添加新的值
							if(specificationss.length>6) {
								pa.setInternalPurchasePrice(NumberUtil.toBigDecimal(specificationss[6]));
								pa.setMinLimitNum(NumberUtil.toInteger(specificationss[7]));
							}else {
								pa.setInternalPurchasePrice(null);
								pa.setMinLimitNum(1);
							}
						}else{//不等于6用原件减优惠
							pa.setInternalPurchasePrice(pa.getPrice().subtract(pa.getMaxFucoin()));
							pa.setMinLimitNum(1);
						}
						//pa.setProductId(product.getProductId());
					}
					if(pa.getPrice()!=null) {
						pricelist.add(pa.getPrice());
						
						if(pa.getMaxFucoin() != null) {
							productPrice = pa.getPrice().subtract(pa.getMaxFucoin());
						}
					}
					skus.add(pa);

					productSpecificationsService.saveOrUpdate(pa);

					//保存库存表
					Inventory inventory = new Inventory();
					Map inventoryMap = new HashMap();
					inventoryMap.put("productSpecificationsId", pa.getId());
					List<Inventory> inventoryList = inventoryService.findAllBymap(inventoryMap);
					if (inventoryList != null && inventoryList.size() > 0) {
						inventory = inventoryList.get(0);
						inventory.setQuantity(NumberUtil.toInteger(specificationss[3]));
						inventory.setWarnQuantity(NumberUtil.toInteger(specificationss[4]));
					} else {
						inventory = new Inventory();
						inventory.setLockQuantity(0);
						inventory.setProductSpecificationsId(pa.getId());
						inventory.setQuantity(NumberUtil.toInteger(specificationss[3]));
						inventory.setWarnQuantity(NumberUtil.toInteger(specificationss[4]));
					}

					inventory.setLockQuantity(0);
					inventory.setProductSpecificationsId(pa.getId());
					inventorys.add(inventory);
					inventoryService.saveOrUpdate(inventory);

					List<Long> idslist = specificationsMap.get(s0[0]);//获取规格值一，每个规格值一对应一条sku和图片
					if (idslist == null) {
						idslist = new ArrayList<Long>();
					}
					idslist.add(pa.getId());
					specificationsMap.put(s0[0], idslist);
					
					//将生产的skuid放进数组里
					mapItemValuesSkuId.put(sValues, pa.getId());
				}
			}
		}
		
		//删除阶梯价
		productLadderService.removeAllByProductid(delMap);
		if( null != map.get("ladder_num")){
			Object[] ladder_num = (Object[]) map.get("ladder_num");
			Object[] ladder_price = (Object[]) map.get("ladder_price");
			List<Integer> nums = new ArrayList<Integer>();
			List<BigDecimal> prices = new ArrayList<BigDecimal>();
			List<List<Long>> skuids = new ArrayList<List<Long>>();
			
			for(int i = 0 ; i < ladder_num.length;i++){
				nums.add(Integer.parseInt((String)ladder_num[i]));
				prices.add(new BigDecimal((String)ladder_price[i]));
				
				List<Long> skuid = new ArrayList<Long>();
				
				String[] ladder_box = (String[]) map.get("ladder_box_" + (i+1));
				for (int j = 0; j < ladder_box.length; j++) {
					skuid.add(mapItemValuesSkuId.get(ladder_box[j]));
				}
				skuids.add(skuid);
			}
			String ladder_type = (String) map.get("ladder_type");
			Integer type = Integer.valueOf(ladder_type);
			//装填阶梯价
			productLadderService.saveProductLadder(supplier.getId(), product.getId(), nums, prices, skuids,type);
		}
		product.setAllnum(allnum);

		if (pricelist != null && pricelist.size() > 0) {
			bubbleSort(pricelist);//价格排序
			product.setMinprice(pricelist.get(0));
			product.setShowPrice(pricelist.get(0) + "");
			product.setMaxprice(pricelist.get(pricelist.size() - 1));
		}

		String imageStr = "";//主图
		//sku图片
		String[] specifications_image_result = (String[]) map.get("specifications_image_result");
		if (specifications_image_result != null) {
			for (String s : specifications_image_result) {
				if (s != null && !s.equals("")) {
					String[] ss = s.split("_");
					String kikaku = ss[0]; //得到颜色值
					List<Long> idslist = specificationsMap.get(kikaku);
					String[] images = ss[1].split(",");
					if (idslist != null) {
						for (Long l : idslist) {
							Map psImageMap = new HashMap();
							psImageMap.put("specificationsId", l);
							productSpecificationsImageService.removeByMap(psImageMap);
							for (int i = 0; i < images.length; i++) {
								ProductSpecificationsImage psi = new ProductSpecificationsImage();
								psi.setCreateDate(new Date());
								psi.setOrders((i + 1));
								psi.setSupplyId(supplier.getId());
								psi.setUpdateDate(new Date());
								psi.setSpecificationsId(l);
								psi.setSource(images[i]);
								productSpecificationsImageService.save(psi);
								if (imageStr.equals("")) {
									imageStr = images[i];
								}
							}
						}
					}
				}
			}
		}
		product.setImage(imageStr);      //主图

		//默认不自动上架
		product.setIsMarketable(0);
		if(status.equals("1")){//如果是点击发布
			product.setUpdateDesc("新增商品");
			// 没有修改敏感信息
			Product product1=this.productDao.getById(product.getProductId());//查询出正式表数据
			
			if(!checkChangePrice(product1,shippingTemplateId, product,skus,inventorys)) {
				
				if(!NumberUtil.equals(product1.getQuestionnaireId(), product.getQuestionnaireId())) {
					// 更换问卷
					if(NumberUtil.isGreaterZero(product1.getQuestionnaireId())) {
						// 停用旧版
						ProductQuestionnaire pqOld = productQuestionnaireService.getById(product1.getQuestionnaireId());
						if(pqOld.getStatus()<3) {
							pqOld.setStatus(3);
							pqOld.setEndDate(new Date());
							
							productQuestionnaireService.update(pqOld);
						}
					}
					
					// 跳过审核步骤  启用新版
					if(pq != null) {
						if(pq.getStatus()<2) {
							pq.setStatus(2);
						}
					}
				}
				
				//跳过审核步骤
				// 临时表审核状态
				product.setStatus(2);		//自动审核通过
				//更新正式表，属性、参数、清单
				copyToProduct(product, product1);
				updateProductStock(product1, skus, inventorys);//更新旧版库存
				//更新缓存
				// 更新索引
				// 生成静态页
				product.setIsMarketable(1);  //自动做上架处理
			}
		}
		
		this.apprProductDao.saveOrUpdate(product);

		// 换领商品记录
//		SupplierExchangeProduct q = new SupplierExchangeProduct();
//		q.setProductId(product.getProductId());
//		q.setStatus(1);					// 待审核    1或者2
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("productId", product.getProductId());
		param.put("status", "1,2");
		List<SupplierExchangeProduct> l =supplierExchangeProductService.findListByMap(param);
		if(2 == product.getSaleKbn()) {
			SupplierExchangeProduct entity = null;
			if(l.isEmpty()) {
				entity = new SupplierExchangeProduct();
				entity.setSupplierId(product.getSupplierId());
				entity.setSaleKbn(3);
				entity.setSaleNote(product.getSaleNote());
				entity.setProductId(product.getProductId());
				entity.setStatus(1);						//1;待审核
				entity.setNotifyFlg("0");
				entity.setClearStatus(0); 				// 0:为清算
				entity.setOfflineFlg(0);					// 0:未知
			} else {
				entity = l.get(0);
			}
			
			entity.setProductName(product.getFullName());
			entity.setProductImg(product.getImage());
//			entity.setProductPrice(BigDecimal.ZERO);
//			entity.setProductCnt(product.getAllnum());
			entity.setEmpLevel(product.getDivLevel());
			
			EnterpriseUser record = new EnterpriseUser();
			record.setEnterpriseId(product.getSupplierId());
			record.setLogout(new Byte("0"));
			if(entity.getEmpLevel() != -1) {
				record.setWelfareLevel(entity.getEmpLevel());
			}
			//List<EnterpriseUser> us = enterpriseUserService.selectByModel(record);
			entity.setEmpCnt(0);
//			if(entity.getProductCnt() > entity.getEmpCnt()) {
//				// 商品数量>员工数量  每人获得整数
//				entity.setEmpAvgCnt(BigDecimal.valueOf(entity.getProductCnt()/entity.getEmpCnt()));
//			entity.setEmpAvgCnt(new BigDecimal(letter));
//			} else {
//				// 可以有小数
//				entity.setEmpAvgCnt(BigDecimal.valueOf(entity.getProductCnt()).divide(BigDecimal.valueOf(entity.getEmpCnt()),1, BigDecimal.ROUND_DOWN));
//			}
//			entity.setEmpAvgAmount(product.getEmpPrice());
			entity.setLimitType((Integer)map.get("limitType"));
			entity.setCreateDate(new Date());
			entity.setSellCnt(0);
			entity.setDistributeCnt(0);
			entity.setAllSaleAmount(BigDecimal.ZERO);
			entity.setShareAmount(BigDecimal.ZERO);
			
			supplierExchangeProductService.saveOrUpdate(entity);
		}
		
		
		// 问卷
		if(pq != null) {

			pq.setProductName(product.getFullName());
			pq.setProductImg(product.getImage());
			pq.setMinprice(product.getMinprice());
			pq.setMaxprice(product.getMaxprice());
			pq.setEmpPrice(product.getEmpPrice());
			pq.setProductCnt(product.getAllnum());
			
			productQuestionnaireService.update(pq);
		}
		return product;
		
		
	}

	private void copyToProduct(ApprProduct product, Product product1) {
		//更新正式表，属性、参数、清单
		BeanUtils.copyProperties(product, product1);//把临时表数据直接更新到正式表中
		product1.setId(product.getProductId());
		// 还要把appr的状态设置为1，需要审核
		product1.setStatus(2);
		product1.setIsMarketable(1);
		if(product1.getPromotion() ==null){
			product1.setPromotion("");
		}
		if(product1.getMarque()==null){
			product1.setMarque("");
		}
		if(product1.getBarcode()==null){
			product1.setBarcode("");
		}
		productDao.saveOrUpdate(product1);
		
		Map delMap = new HashMap();
		delMap.put("productid", product1.getId());
		//删除已有的属性
		productAttributeService.removeAllByProductid(delMap);
		//copy属性信息并关联正式表id
		List<ProductAttribute> pat = productAttributeService.getByProductId(product.getId());
		if (pat != null && pat.size() > 0) {
			for (ProductAttribute pvf : pat) {
				ProductAttribute pf = new ProductAttribute();
				BeanUtils.copyProperties(pvf,pf);
				pf.setId(null);
				// 把临时表中的product_id设置成临时表的id，然后保存，就不需要上面的
				pf.setProductId(product1.getId());
				productAttributeService.saveOrUpdate(pf);
			}
		}
		
		//删除已有的参数
		productParameterValueService.removeAllByProductid(delMap);
		// copy参数值并关联正式表id
		List<ProductParameterValue> psfv = productParameterValueService.getByProductId(product.getId());
		if (psfv != null && psfv.size() > 0) {
			for (ProductParameterValue pvf : psfv) {
				ProductParameterValue pf = new ProductParameterValue();
				BeanUtils.copyProperties(pvf,pf);
				pf.setId(null);
				// 把临时表中的product_id设置成临时表的id，然后保存，就不需要上面的
				pf.setProductId(product1.getId());
				productParameterValueService.saveOrUpdate(pf);
			}
		}
		//删除已有的清单
		productDetailListService.removeAllByProductid(delMap);
		// copy清单信息并关联正式表id
		List<ProductDetailList> pdl = productDetailListService.getByProductId(product.getId());
		if (pdl != null && pdl.size() > 0) {
			for (ProductDetailList pvf : pdl) {
				ProductDetailList pf = new ProductDetailList();
				BeanUtils.copyProperties(pvf, pf);
				pf.setId(null);
				// 把临时表中的product_id设置成临时表的id，然后保存，就不需要上面的
				pf.setProductId(product1.getId());
				productDetailListService.saveOrUpdate(pf);
			}
		}
	}

	/**
	 * 比较商品 销售区分、运费、sku 阶梯价是否发生变化
	 * @param shippingTemplateId
	 * @param product
	 * @param skus
	 * @param inventorys
	 * @return true:有变化/false:无变化
	 */
	private boolean checkChangePrice(Product product1,String shippingTemplateId, ApprProduct product,List<ProductSpecifications> skus ,List<Inventory> inventorys) {
		boolean b=false;
		String updateDesc="";
		if(product1 == null || product1.getIsMarketable() !=1) {//在售商品必须是上架状态才可以在没有修改敏感数据时，直接上架，屏蔽在售商品下架，没有修改敏感数据直接上架
			return true; //正式表对象为空的话可能是新增商品，无需检查直接返回
			
		}
		//
		//如果正式表有数据，就获取数据和新修改的数据比较

		//______________________销售区分______________________________
		if(!NumberUtil.equals(product1.getSaleKbn(), product.getSaleKbn())) {
			// 销售区分改变
			String temp="未设置";
			String temp1="未设置";
			if(product1.getSaleKbn()==1){
				temp="特省";
			}else if(product1.getSaleKbn()==2){
				temp="换领";
			}else if(product1.getSaleKbn()==4){
				temp="专享";
			}else if(product1.getSaleKbn()==5){
				temp="试用";
			}
			if(product.getSaleKbn()==1){
				temp1="特省";
			}else if(product.getSaleKbn()==2){
				temp1="换领";
			}else if(product.getSaleKbn()==4){
				temp1="专享";
			}else if(product.getSaleKbn()==5){
				temp1="试用";
			}
			updateDesc ="销售区分：从"+temp+"修改为"+temp1+",";
			//return true;
			b=true;
		}
		
		if(product1.getSaleKbn()==4 || product1.getSaleKbn()==5) {
			if(!NumberUtil.equals(product1.getEmpPrice(), product.getEmpPrice())) {
				// 试用、专享  员工特价改变
				updateDesc+="试用商品评价后返现或专享价格：从"+product1.getEmpPrice()+"元修改为"+product.getEmpPrice()+"元"+",";
				//return true;
				b=true;
			}
			//如果评价返现 而且俩个问题id不同 并且返现金额为0 
			if(product1.getSaleKbn()==5 && (product1.getQuestionnaireId().compareTo(product.getQuestionnaireId()) != 0 && BigDecimal.ZERO.compareTo(product1.getEmpPrice()) == 0 &&  BigDecimal.ZERO.compareTo(product.getEmpPrice()) == 0)){
				updateDesc+="评价问卷顺序改变了,";
				b=true;
			}
		}
		//员工等级
		if(product1.getSaleKbn()==4 || product1.getSaleKbn()==2){
			if(!NumberUtil.equals(product1.getEmpLevel(), product.getEmpLevel())){
				updateDesc+="员工等级改变"+",";
				//return true;
				b=true;
			}
		}
		//______________________销售区分______________________________
		
			
		//______________________运费改变______________________________
		//比较运费是否改变
		if(!NumberUtil.equals(product1.getCarriage(), product.getCarriage())){
			updateDesc+="运费改变"+",";
			//return true;
			b=true;
		}
		// 比较运费模板
		ProductShipping productShipping = new ProductShipping();
		//productShipping.setProductId(product.getProductId());
		productShipping.setProductId(product1.getId());
		List<ProductShipping> lst= productShippingService.selectByModel(productShipping);
		if(com.wode.common.util.StringUtils.isEmpty(shippingTemplateId) || "-1".equals(shippingTemplateId)) {//-1表示没有选择运费模板
			if(lst != null && !lst.isEmpty()) {
				// 删除运费模板
				updateDesc+="删除运费模板"+",";
				//return true;
				b=true;
			}
		} else {
			if(lst == null || lst.isEmpty()) {
				// 添加运费模板
				updateDesc+="添加运费模板"+",";
				//return true;
				b=true;
			} else {
				if(!lst.get(0).getTemplateId().equals(Long.valueOf(shippingTemplateId))) {
					// 改变运费模板
					updateDesc+="改变运费模板"+",";
					//return true;
					b=true;
				}
			}
		}
		//______________________运费改变______________________________

		//______________________SKU 价格、规格、库存______________________________
		boolean chageStock = false;
		ProductSpecificationsQuery query = new ProductSpecificationsQuery();
		query.setProductId(product1.getId());
		query.setIsDelete(0);
		query.setPageSize(100);
		Page<ProductSpecifications> oldSkus = productSpecificationsService.findPage(query);
		
		for (ProductSpecifications sku : skus) {	
			ProductSpecifications oldSku = null;
			for (ProductSpecifications old : oldSkus.getResult()) {
				if(sku.getItemValues().equals(old.getItemValues())) {
					oldSku = old;
					break;
				}
			}
			
			if(oldSku == null) {
				// sku 规格变化
				if(!updateDesc.contains("修改sku规格,")){
					
					updateDesc+="修改sku规格"+",";
				}
				//return true;
				b=true;
			} else {
				if(sku.getPrice() == null || sku.getMaxFucoin() == null) {
					// 价格变化
					if(!updateDesc.contains("修改sku价格,")){
						
						updateDesc+="修改sku价格"+",";
					}
					//return true;
					b=true;
				}
				
				if(sku.getPrice().compareTo(oldSku.getPrice())==0 
						&& sku.getMaxFucoin().compareTo(oldSku.getMaxFucoin())==0) {
					//价格没有变化
				} else {
					//价格改变
					if(!updateDesc.contains("修改sku价格,")){
						
						updateDesc+="修改sku价格"+",";
					}
					//return true;
					b=true;
				}
				//检查库存改变
				if(!chageStock) {
					Inventory inventory = null;
					Inventory oldInventory = null;
					
					// 确定新版sku 库存
					for (Inventory i : inventorys) {
						if(i.getProductSpecificationsId().equals(sku.getId())) {
							inventory = i;
							break;
						}
					}
	
					// 确定旧版sku 库存
					Map inventoryMap = new HashMap();
					inventoryMap.put("productSpecificationsId", oldSku.getId());
					List<Inventory> inventoryList = inventoryService.findAllBymap(inventoryMap);
					if (inventoryList != null && inventoryList.size() > 0) {
						oldInventory = inventoryList.get(0);
					} else {
						oldInventory = null;
					}
					
					if(inventory != null && oldInventory!=null) {
						if(!oldInventory.getQuantity().equals(inventory.getQuantity())) {
							// 库存改变
							chageStock = true;
						}
					} else {
						// 库存改变
						chageStock = true;
					}
				}
			}
		}
		
		if(!b && oldSkus.getResult().size()>skus.size()) {
			//价格改变
			updateDesc+="删除sku"+",";
			//return true;
			b=true;			
		}

		if(product1.getSaleKbn()==2 || product1.getSaleKbn()==4) {
			if(chageStock) {
				// 换领、专享  库存改变
				updateDesc+="修改换领或专享库存"+",";
				
				//return true;	
				b=true;
			}
		}
		
		//判断阶梯价格
		//查询老的阶梯价
		List<ProductLadder> oldLadder = productLadderService.getlistByProductid(product1.getId());
		List<ProductLadder> newLadder = productLadderService.getlistByProductid(product.getId());
		if(oldLadder == null){
			oldLadder = new ArrayList<ProductLadder>();
		}
		if(newLadder == null){
			newLadder = new ArrayList<ProductLadder>();
		}
		if(oldLadder.size() != newLadder.size()){
			updateDesc+="修改了阶梯价格"+",";
			b=true;
		}else{
			for (int i = 0; i < oldLadder.size(); i++) {
				ProductLadder oldProductLadde = oldLadder.get(i);
				ProductLadder newProductLadde = newLadder.get(i);
				if(oldProductLadde.getType()!=newProductLadde.getType()){
					updateDesc+="修改了阶梯价格类型"+",";
					b=true;
					break;
				}
				if(oldProductLadde.getNum() != newProductLadde.getNum() || 
						oldProductLadde.getPrice().compareTo(newProductLadde.getPrice()) !=0 ){
					updateDesc+="修改了阶梯价格"+",";
					b=true;
					break;
				}else{
					//如果规则没变要判断里面的skuid 数量和 skuid是否改变了
					if(dealSkuLadder(oldProductLadde.getSkuids(), newProductLadde.getSkuids(), oldSkus.getResult(), skus)){
						updateDesc+="修改了阶梯价格"+",";
						b=true;
						break;
					}
				}
			}
		}
		//______________________SKU 价格、规格、库存______________________________
		product.setUpdateDesc(updateDesc);
		//return false;
		
		return b;
	}

	private SupplierSpecification mergeSkuSpecification(ApprProduct product, String[] skuSpecification) {
		SupplierSpecification record = new SupplierSpecification();
		record.setCategoryId(product.getCategoryId());
		record.setSupplierId(product.getSupplierId());
		record.setType(1);
		List<SupplierSpecification> lst = supplierSpecificationService.selectByModel(record);
		
		SupplierSpecification specification = null;
		if(lst == null || lst.isEmpty()) {
			specification = new SupplierSpecification();
			specification.setCategoryId(product.getCategoryId());
			specification.setSupplierId(product.getSupplierId());
			specification.setName("规格");
			specification.setOrders(1);
			specification.setType(1);
			supplierSpecificationService.save(specification);
		} else {
			specification = lst.get(0);
		}
		SpecificationValueQuery query = new SpecificationValueQuery();
		query.setPageSize(100);
		query.setSpecificationId(specification.getId());
		Page p = specificationValueService.findPage(query);
		Map<String,SpecificationValue> mv = new HashMap<String,SpecificationValue>();
		int maxOrder=0;
		for (Object object : p) {
			SpecificationValue sv = (SpecificationValue)object;
			mv.put(sv.getName(), sv);
			if(sv.getOrders() != null && sv.getOrders()>maxOrder) {
				maxOrder = sv.getOrders();
			}
		}
		
		for (String value : skuSpecification){
			if(!mv.containsKey(value)) {
				SpecificationValue sv = new SpecificationValue();
				sv.setName(value);
				sv.setOrders(++maxOrder);
				sv.setSpecificationId(specification.getId());
				
				specificationValueService.save(sv);
				
				mv.put(value, sv);
			}
		}
		return specification;
	}
	/**
	 * 获取商品列表（带分页）
	 *
	 * @param map
	 * @return
	 */
	public List<ApprProduct> findProductlistPage(Map map) {
		return apprProductDao.findProductlistPage(map);
	}

	/**
	 * 获取商品列表总条数（带分页）
	 *
	 * @param map
	 * @return
	 */
	public Integer findProductlistPageCount(Map map) {
		return apprProductDao.findProductlistPageCount(map);
	}


	@Override
	protected EntityDao getEntityDao() {
		return this.apprProductDao;
	}

	/**
	 * 冒泡排序
	 *
	 */
	public static void bubbleSort(List<BigDecimal> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = 1; j < list.size() - i; j++) {
				BigDecimal a;
				if ((list.get(j - 1)).compareTo(list.get(j)) > 0) {   //比较两个整数的大小
					a = list.get(j - 1);
					list.set((j - 1), list.get(j));
					list.set(j, a);
				}
			}
		}
	}

	@Override
	public List<Product> getProductByMap(Map map) {
		List<Product> products = apprProductDao.getProductByMap(map);
		return setSkuStockFromRedis(products);
	}
	
	public List<Product> setSkuStockFromRedis(List<Product> products) {
		List<ProductSpecifications> skus;
		for (Product product : products) {
			skus = product.getProductSpecificationslist();
			if (skus.size() > 0) {
				for (ProductSpecifications sku : skus) {
					String stock = redis.getData(RedisConstant.REDIS_SKU_INVENTORY + sku.getId());
					if (StringUtils.isNotBlank(stock))
						sku.setStock(Integer.valueOf(stock));
				}
			}
		}
		return products;
	}

	@Override
	public void updateProductByids(Map map) {
		apprProductDao.updateProductByids(map);
		
	}

	@Override
	public ApprProduct getProductandstauts(ApprProduct product) {
		
		return apprProductDao.getProductandstauts(product);
	}

	@Override
	public ApprProduct selectProductIdAndStatus(Long productId) {
		
		return apprProductDao.selectProductIdAndStatus(productId);
	}

	@Override
	public ApprProduct selectByProductId(Long productId) {
		// TODO Auto-generated method stub
		return apprProductDao.selectByProductId(productId);
	}

	

	/**
	 * 获取实体类对象，包含评价不通过信息
	 *
	 * @return
	 */
	@Override
	public ApprProduct getProductCheckById(Map map) {
		return apprProductDao.getProductCheckById(map);
	}
 
	/**
	 * 临时表没有下架的需求但是有上架的需求
	 * 
	 * @param productId
	 * @param user
	 * @return
	 */
	public ActResult sellOn(List<Long> productId, UserFactory user) {
		ActResult ret = ActResult.success(null);
		List<Long> idslist = new ArrayList<Long>();
		for (Long pid : productId) {
			//Product p = this.getById(pid);
			ApprProduct p = this.getById(pid);
			if(p.getSavestate()!=null && p.getSavestate()==1) {
				//信息不完整 不能上架
				ret.setMsg("信息不完整的商品不能做上架处理");
				continue;
			}
			if (!user.getSupplierId().equals(p.getSupplierId())) {
				ret.setMsg(ret.getMsg() + p.getName() + "无权限操作;");
				ret.setSuccess(false);
				continue;
			}
			p.setStatus(1); //如果上架操作
			p.setUpdateDate(new Date());//上架产生待审核数据，需要更新修改时间
			//查询正式表中有没有数据
			Product pro=productDao.getById(p.getProductId());
			if(pro!=null && pro.getIsMarketable()==1){
				//获取新版模板id
				ProductShipping productShipping = new ProductShipping();
				productShipping.setProductId(p.getId());
				List<ProductShipping> lst= productShippingService.selectByModel(productShipping);
				String shippingTemplateId=""; //直接定义成string，阻止了long转成string的空字符串（技巧）
				if(lst != null && !lst.isEmpty()) {
					shippingTemplateId=lst.get(0).getTemplateId().toString();
				}
				//获取新版skus
				ProductSpecificationsQuery query = new ProductSpecificationsQuery();
				query.setProductId(p.getId());
				query.setIsDelete(0);
				query.setPageSize(100);
				Page<ProductSpecifications> page = productSpecificationsService.findPage(query);
				
				List<ProductSpecifications> skus = page.getResult();
				List<Inventory> inventorys = new ArrayList<Inventory>();
				//遍历sku列表获取对应的库存列表
				for (ProductSpecifications pa : skus) {
					Map inventoryMap = new HashMap();
					inventoryMap.put("productSpecificationsId", pa.getId());
					List<Inventory> inventoryList = inventoryService.findAllBymap(inventoryMap);
					if (inventoryList != null && inventoryList.size() > 0) {
						inventorys.add(inventoryList.get(0));
					}
				}
				if(!checkChangePrice(pro,shippingTemplateId, p,skus,inventorys)) { //判断是否修改了敏感数据，如果没有
					//跳过审核步骤
					// 临时表审核状态
					p.setStatus(2);		//自动审核通过
					//更新正式表，属性、参数、清单
					copyToProduct(p, pro);
					//—————————————————————— 更新旧版库存————————————————————————————————
					updateProductStock(pro, skus, inventorys);
					//—————————————————————— 更新旧版库存————————————————————————————————
					//更新缓存
					// 更新索引
					// 生成静态页
					idslist.add(p.getProductId());//传递正式表id
					
				}
			}
			apprProductDao.saveOrUpdate(p);
		}
		ret.setData(idslist);
		
		return ret;
	}

	private void updateProductStock(Product pro, List<ProductSpecifications> skus, List<Inventory> inventorys) {
		ProductSpecificationsQuery query1 = new ProductSpecificationsQuery();
		query1.setProductId(pro.getId());
		query1.setIsDelete(0);
		query1.setPageSize(100);
		Page<ProductSpecifications> oldSkus = productSpecificationsService.findPage(query1);
		//根据规格值判断新版的sku和旧版sku是否一致
		for (ProductSpecifications sku : skus) {	
			ProductSpecifications oldSku = null;
			for (ProductSpecifications old : oldSkus.getResult()) {
				if(sku.getItemValues().equals(old.getItemValues())) {
					oldSku = old;
					break;
				}
			}
			Inventory inventory = null;
			// 确定新版sku 库存
			for (Inventory i : inventorys) {
				if(i.getProductSpecificationsId().equals(sku.getId())) {
					inventory = i;
					break;
				}
			}
			//更新sku的条形码(字母或数字)
			if(!oldSku.getProductCode().equals(sku.getProductCode())){
				oldSku.setProductCode(sku.getProductCode());
				productSpecificationsService.saveOrUpdate(oldSku);
			}
			//更新sku起售数量
			if(oldSku.getMinLimitNum().compareTo(sku.getMinLimitNum()) !=0 ){
				oldSku.setMinLimitNum(sku.getMinLimitNum());
				productSpecificationsService.saveOrUpdate(oldSku);
			}
			
			// 确定旧版sku 库存
			Map inventoryMap1 = new HashMap();
			inventoryMap1.put("productSpecificationsId", oldSku.getId());
			List<Inventory> inventoryList = inventoryService.findAllBymap(inventoryMap1);
			if (inventoryList != null && inventoryList.size() > 0) {
				inventoryList.get(0).setQuantity(inventory.getQuantity());
				inventoryList.get(0).setWarnQuantity(inventory.getWarnQuantity());
				// 保存到DB
				inventoryService.saveOrUpdate(inventoryList.get(0));
				// 更新缓存（正式表数据放缓存中）
				String key = RedisConstant.REDIS_SKU_INVENTORY + inventoryList.get(0).getProductSpecificationsId();
				redis.del(key);
				redis.setData(key, String.valueOf(inventoryList.get(0).getQuantity()));
				redis.removeSet(RedisConstant.Inventory_CHANGE, String.valueOf(inventoryList.get(0).getProductSpecificationsId()) + "");
			}
			
			// copy主图
			Map psImageMap = new HashMap();
			psImageMap.put("specificationsId", oldSku.getId());
			//删除旧主图
			productSpecificationsImageService.removeByMap(psImageMap);
			
			// 新主图覆盖旧主图
			List<ProductSpecificationsImage> images= productSpecificationsImageService.getByProductId(sku.getId());
			for (int i = 0; i < images.size(); i++) {
				ProductSpecificationsImage psi = new ProductSpecificationsImage();
				psi.setCreateDate(images.get(i).getCreateDate());
				psi.setOrders(images.get(i).getOrders());
				psi.setSupplyId(images.get(i).getSupplyId());
				psi.setUpdateDate(images.get(i).getUpdateDate());
				psi.setSpecificationsId(oldSku.getId());
				psi.setSource(images.get(i).getSource());
				productSpecificationsImageService.save(psi);
			}
		}
	}
	//删除临时表中的数据
	@Override
	public void deleteById(Long id) {
		apprProductDao.deleteById(id);
		
	}

	@Override
	public List<ApprProduct> getNotDeleteProductByFullName(ApprProduct product) {
		
		return apprProductDao.getNotDeleteProductByFullName(product);
	}

	@Override
	public Long getSupplierapprFullname(Map map) {
		
		return apprProductDao.getSupplierapprFullname(map);
	}
	
	/**
	 * 处理阶梯价
	 * @param oldSkus
	 * @param newSkus
	 * @return
	 */
    private boolean dealSkuLadder(String oldSkus,String newSkus,List<ProductSpecifications> oldProductSpecifications,List<ProductSpecifications> newProductSpecifications){
    	//分割出来所有老的skuid
    	String[] oldArray = oldSkus.split(",");
    	String[] newArray = newSkus.split(",");
    	//长度不一样一定是有修改了
    	if(newArray.length != oldArray.length){
    		return true;
    	}else{
    		List<ProductSpecifications> oldRealHasSku = getPlistByskus(oldArray, oldProductSpecifications);
    		List<ProductSpecifications> newRealHasSku = getPlistByskus(newArray, newProductSpecifications);
    		for (ProductSpecifications productSpecifications : newRealHasSku) {
    			boolean flag =false;//定义是否sku改变的中间标志
    			for (ProductSpecifications oldps : oldRealHasSku) {
    				if(oldps.getItemValues().equals(productSpecifications.getItemValues())){
    					flag = true;//证明此sku在阶梯价中没变
    					break;
    				}
    			}
    			if(!flag){//如果上个循环没查到改变证明sku不存在，证明这个sku有改变返回true
    				return true;
    			}
			}
    	}
    	
    	return false;
    }

    /**
     * 返回 productSpecifications list 也就是sku里面  包含 skus[]里面的值
     * 例如 list 可能有5 个元素  但是skus里面只有3个skuid 。
     * 那么返回的list 就是三个元素
     * 
     * @param skus
     * @param productSpecifications
     * @return
     */
    private List<ProductSpecifications> getPlistByskus(String[] skus,List<ProductSpecifications> productSpecifications){
    	 List<ProductSpecifications> result = new ArrayList<ProductSpecifications>();
    	for (int i = 0; i < skus.length; i++) {
    		
    		for (ProductSpecifications productSpecifications2 : productSpecifications) {
				if(productSpecifications2.getId().compareTo(new Long(skus[i])) == 0){
					result.add(productSpecifications2);
				}
			}
			
		}
    	
    	return result;
    }


}



