package com.wode.api.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.nutz.lang.util.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.api.util.EasemobIMUtils;
import com.wode.api.util.IPUtils;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.ClientAccessLog;
import com.wode.factory.model.Comments;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.model.Specification;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserIm;
import com.wode.factory.service.BrandService;
import com.wode.factory.service.CommentsService;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsImageService;
import com.wode.factory.user.facade.ShippingFacade;
import com.wode.factory.user.model.CommentsCountVo;
import com.wode.factory.user.service.ClientAccessLogService;
import com.wode.factory.user.service.ExchangeSuborderitemService;
import com.wode.factory.user.service.InventoryService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.QuestionnaireAnswerService;
import com.wode.factory.user.service.ShopService;
import com.wode.factory.user.service.UserImService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.vo.ProductDetailVo;
import com.wode.factory.vo.ProductVo;
import com.wode.search.WodeSearchManager;

/**
 * 商品详情 
 * @author 谷子夜
 */
@Controller
@RequestMapping("/product")
@ResponseBody
public class ProductDetailController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(ProductDetailController.class);
	@Autowired
    private CommentsService commentsService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private ProductCategoryService  productCateService;
	
	@Autowired
	private BrandService brandVoService;
	
	@Autowired
	private ProductSpecificationsService  productSpecificationsService;
	
	@Autowired
	private ProductSpecificationsImageService  productSpecificationsImageService;
	
	@Autowired
	private InventoryService inventoryService;
    @Autowired
    private ClientAccessLogService clientAccessLogService;

	@Qualifier("userService")
	@Autowired
	private UserService userService;
    @Autowired
	private ShippingFacade shippingFacade;
	@Autowired
	private UserImService userImService;
	@Autowired
	private WodeSearchManager wsm;

	@Autowired
	private ExchangeSuborderitemService exchangeSuborderitemService;
	@Autowired
	private ProductLadderService productLadderService;
	
	/**
	 * 调查问卷
	 */
	@Autowired
	private QuestionnaireAnswerService questionnaireAnswerService;
	/**
	 * 商品sku
	 * guziye
	 */
	/*@RequestMapping("detail")
	@ResponseBody
	public ActResult<ProductSpecificationsVo> sku(String itemsIds){
		ProductSpecificationsVo ret= psService.selectProductSpecifications(itemsIds);
		return ActResult.success(ret);
	}*/
	
	/**
	 * 商品sku
	 * guziye
	 */
	/*@RequestMapping("selectStock")
	@ResponseBody
	public ActResult<Inventory> selectStock(Long partNumber,int quantity){
		Inventory inventory = inventoryService.findBySpecId(partNumber);
		
		if((inventory.getQuantity()-inventory.getLockQuantity())<quantity){
	    	return ActResult.fail("商品库存不足 "+quantity);
	    }
		return ActResult.success(inventory);
	}*/
	
	/**
	 * 商品评论List
	 * guziye
	 */
	/*@RequestMapping(value="comments/{pages}/{sizes}")
	@ResponseBody
	public ActResult<Page<CommentsVo>> commentList(Long productId,int index,@PathVariable Integer pages,@PathVariable Integer sizes){
		ActResult<Page<CommentsVo>> ar = new ActResult<Page<CommentsVo>>();
		ar = psService.selectProductComment(productId,index,pages,sizes);
		return ar;
	}*/
	/**
	 * app商品详情
	 * guziye
	 */
	@RequestMapping("/detail")
	public ActResult<Object> productDetail(HttpServletRequest request,ModelMap model, Long productId, Long specificationsId,Integer quantity,Long userId) {
		ProductVo pvo = productService.findById(productId, true);
		if (pvo == null) {
			return ActResult.fail(productId + "的商品不存在");
		}
		quantity = quantity==null?1:quantity;
		UserFactory user = userId==null?null:userService.getById(userId);
		ProductDetailVo productDetailVo = new ProductDetailVo();
		BeanUtils.copyProperties(pvo, productDetailVo);
		//临时处理 显示商品标题
		productDetailVo.setName(productDetailVo.getFullName());
		
		ProductCategory pojo = new ProductCategory();
		//商品清单
		pojo.setId(productCateService.findById(pvo.getCategoryId()).getPid());
		productDetailVo.setSmap(productService.findSku4ShowCache(productId));
		//productDetailVo.setSupplierShopVo(productService.findShopByProductIdCache(productId));
		productDetailVo.setSupplierShopVo(shopService.findShopByShopIdCache(pvo.getShopId()));
		productDetailVo.setPcList(productCateService.findSub(pojo));
		ProductBrand pb = brandVoService.selectById(pvo.getBrandId());
		if (pb!=null&&StringUtils.isEmpty(pb.getName())) {
			pb.setName(pb.getNameEn());
		}
		//默认选中规格策略
		ProductSpecifications show = null;//要显示的sku
		
		if(StringUtils.isEmpty(specificationsId)){
			// 查询最低价格的商品SKU集合
			List<ProductSpecifications> list = productService.findByMinpriceCache(productId, productDetailVo.getMinprice());
			if(list.size()>0){
				for (ProductSpecifications pss : list) {
					Integer inventoryFromRedis = inventoryService.getInventoryFromRedis(pss.getId());
					if (inventoryFromRedis>0) {
						show = pss;
						break;
					}
				}
				//show=list.get(0);
			}else
				return ActResult.fail("该规格商品不存在");
		}else{
			show = productSpecificationsService.findByIdCache(specificationsId,productId+"");
		}
		if (show==null) {//show不存在，则通过商品id获取所有sku信息
			List<ProductSpecifications> pssList = productSpecificationsService.findByProductId(productId+"");
			if (pssList!=null&&!pssList.isEmpty()) {
				for (ProductSpecifications psvo : pssList) {
					if (psvo.getQuantity()>0) {
						show=psvo;
						break;
					}
				}
				if (show==null) {//商品所有规格库存都为0的话
					show=pssList.get(0);
				}
			}
		}else {
			if( show.getStock()!=null && show.getStock()<=0) {
				List<ProductSpecifications> pssList = productSpecificationsService.findByProductId(productId+"");
				if (pssList!=null&&!pssList.isEmpty()) {
					for (ProductSpecifications psvo : pssList) {
						if (psvo.getQuantity()>0) {
							show=psvo;
							break;
						}
					}
				}
			}
		}
		
		if (show==null) {
			return ActResult.fail("该规格商品不存在");
		} else {
			//员工特享商品
			productSpecificationsService.resetPrice(show, pvo, user,true,quantity);
		}
		//默认显示商品图片
		productDetailVo.setShowPrice(show.getPrice()+"");
		List<ProductSpecificationsImage> imageList = productSpecificationsImageService.findProductPicture(show.getId(),show.getProductId());
		
		Set<String> list = new HashSet<String>();
		if(show.getItemids().indexOf(",")==-1){
			list.add(show.getItemids() );
		}else{
			String[] array=show.getItemids().split(",");
			list.addAll(Arrays.asList(array));
		}
		Map<String, String> strMap = new LinkedHashMap<String, String>();	
		Iterator<String> it=list.iterator();
		while (it.hasNext()) {
			String skuValueid=it.next();
			Specification s = productService.findByItemsValueCache(skuValueid);
			strMap.put(s.getName(), skuValueid);
		}
		List<ProductSpecifications> psList = productService.findSku(productId);
		Map<String,String> map = new HashMap<String, String>();
		Map<String,String> stockMap = new HashMap<String, String>();
		Map<String, List<ProductSpecificationValue>> Smap = productDetailVo.getSmap();
		List<String> ids = new ArrayList<String>();
		int size = productDetailVo.getSmap().keySet().size();
		if (size==1) {//一个规格
			for (List<ProductSpecificationValue> psvs : productDetailVo.getSmap().values()) {
				for (ProductSpecificationValue psv : psvs) {
					ids.add(psv.getId()+"");
				}
			}
		} else {
			List<ProductSpecificationValue> psvs1=null;
			List<ProductSpecificationValue> psvs2=null;
			int i=1;
			for (List<ProductSpecificationValue> psvs : productDetailVo.getSmap().values()) {
				if(i==1) {
					psvs1 = psvs;
				} else if(i==2) {
					psvs2 = psvs;
				}
				i++;
			}
			
			for (ProductSpecificationValue psv1 : psvs1) {
				for (ProductSpecificationValue psv2 : psvs2) {
					if(psv1.getId().compareTo(psv2.getId())>0) {
						ids.add(psv2.getId()+","+psv1.getId());
					} else {
						ids.add(psv1.getId()+","+psv2.getId());
					}
				}
			}
		}
		 
		for(ProductSpecifications ps : psList){
			if(!map.containsKey(ps.getItemids())){
				Integer repertory = inventoryService.getInventoryFromRedis(ps.getId());//每一个sku对应的库存
				stockMap.put(ps.getItemids(), repertory.toString());
				map.put(ps.getItemids(), ps.getId()+"");
			}
		}
		
		//试用商品检查问卷
		if(productDetailVo.getSaleKbn()!=null && productDetailVo.getSaleKbn()==5) {
			if(!NumberUtil.isGreaterZero(productDetailVo.getEmpPrice())) {
				//评价后购买
				if(productDetailVo.getQuestionnaireId() != -1 &&!this.questionnaireAnswerService.hasAnswer(productDetailVo.getQuestionnaireId(), user.getId())){
					productDetailVo.setIsQuestioned(false);
				}else{
					productDetailVo.setIsQuestioned(true);
				}
			}	
		}

		//计算运费
		List<String> outRuleDes = new ArrayList<String>();
		String acode= shippingFacade.getACode(user, IPUtils.getClientAddress(request));
		BigDecimal carriage = this.calculateShipping(quantity, show.getInternalPurchasePrice().multiply(NumberUtil.toBigDecimal(quantity)), user, outRuleDes, pvo, acode);
		
		Integer productCount = shopService.findProductCount(pvo.getSupplierId(),pvo.getShopId());
		Integer stock = inventoryService.getInventoryFromRedis(show.getId());
		
		model.addAttribute("maxFunction", show.getMaxFucoin()==null?0:show.getMaxFucoin());
		model.addAttribute("price", show.getPrice());
		model.addAttribute("minLimitNum", show.getMinLimitNum());
		model.addAttribute("purchasePrice", show.getInternalPurchasePrice()==null?0:show.getInternalPurchasePrice());
		model.addAttribute("cost", show.getCost());
		model.addAttribute("stock", stock==null?0:stock);
		Integer orderCount = 0;
		if(pvo.getSaleKbn()!=null && pvo.getSaleKbn()==2) {
			orderCount=exchangeSuborderitemService.getOrderCount(show.getId());
		}
		model.addAttribute("orderCount", orderCount);
		for (String string : ids) {
			if(!map.containsKey(string)) {
				map.put(string, "0");
			}
			if(!stockMap.containsKey(string)) {
				stockMap.put(string, "0");
			}
		}
		//增加促销信息
		Map<String,BigDecimal> salesPromotion = null;
		//如果是员工专享价格,则增加内购价和内购券
		if(show.getEmpPrice() !=null && show.getEmpPrice().compareTo(BigDecimal.ZERO) > 0) {
			ProductSpecifications  newRet  = productSpecificationsService.findByIdCache(show.getId(),null);
			model.addAttribute("price", newRet.getPrice());
			model.addAttribute("purchasePrice", newRet.getInternalPurchasePrice());
			model.addAttribute("BenefitSubsidy", show.getBenefitSubsidy());//福利补贴
		}else {
			//这里查询商品是否有阶梯价
			BigDecimal ladderPrice = productLadderService.getPriceBySkuidAndPrice(show.getId(), quantity);
			if(null != ladderPrice){
				model.addAttribute("maxFucoin",new BigDecimal(0.01));
				model.addAttribute("purchasePrice", ladderPrice);
			}
			salesPromotion = productLadderService.getSalePromotionMapBySkuid(show.getId());
		}
		model.addAttribute("skuMap",map);
		model.addAttribute("stockMap",stockMap);
		model.addAttribute("carriage",carriage);
		if(!outRuleDes.isEmpty()) model.addAttribute("carriageDes",outRuleDes.get(0));
		model.addAttribute("defaultImage", imageList);
		model.addAttribute("defaultSelectSKU", strMap);
		model.addAttribute("product", productDetailVo);
		
		
		//增加用户是否能购买此商品
		model.addAttribute("isCanBuy", true);
		if(productService.checkProductLimit(productDetailVo, user)){
			model.addAttribute("isCanBuy", false);
			model.addAttribute("isCanBuyMsg", "此商品仅限企业用户");
		}
		model.addAttribute("productAttributeMap",productService.findAttr(productId, pvo.getCategoryId(), true));
		model.addAttribute("productAttributeList",productService.findPar(productId, pvo.getCategoryId(), true));
		model.addAttribute("parentsCateList", productCateService.findParents(pvo.getCategoryId()));
		model.addAttribute("productBrand", pb);
		model.addAttribute("productCount",productCount==null?0:productCount);
		model.addAttribute("specificationsId",show.getId());
		UserIm shopIm= productDetailVo.getSupplierShopVo()==null?null:userImService.selectByShopAndSupplier(productDetailVo.getSupplierShopVo().getId(), productDetailVo.getSupplierShopVo().getSupplierId());
		model.addAttribute("shopImUser",shopIm==null?null:shopIm.getOpenimId());
		String shopStatus="";
		if (shopIm!=null) {
			 shopStatus = EasemobIMUtils.checkUserStatus(shopIm.getOpenimId());
		}else{
			shopStatus = "offline";
		}
		model.addAttribute("shopStatus", shopStatus);
		model.addAttribute("salesPromotion", salesPromotion);
		try {
			clientAccessLogService.saveProduct(ClientAccessLog.ACCESS_TYPE_PRODUCT, specificationsId, show.getProductName()+"："+show.getItemnames(), userId,user,show.getProductId(),pvo,null,IPUtils.getClientAddress(request));
			wsm.updateViewCntNum(show.getProductId());
		} catch (Exception e) {
			logger.info("商品异常，可能是商品已经下架或者未上线");
		}
		ProductVo findById = productService.findById(productId, true);
		List<ShippingFreeRule> findParcelPostSomeAreas = shippingFacade.findParcelPostSomeAreas(pvo.getSupplierId());
		String allSheng = "北京,天津,上海,重庆,河北,河南,云南,辽宁,黑龙江,湖南,安徽,山东,新疆,江苏,浙江,江西,湖北,广西,甘肃,山西,内蒙古,陕西,吉林,福建,贵州,广东,青海,西藏,四川,宁夏,海南,台湾,香港,澳门";
		String sheng = allSheng;
		List<Map<String,Object>> serviceDatasList = new ArrayList<Map<String,Object>>();
		String carriageDes = outRuleDes.get(0);
		// 限购
		if(findById.getAreasName()!=null&&!findById.getAreasName().equals("全国")){//区域限购
			String[] split = findById.getAreasName().split(" ");
			for (int i = 0; i < split.length; i++) {
				sheng = sheng.replaceAll(split[i]+",","");
			}
			Map<String,Object> serviceData = new HashMap<String,Object>();
			serviceData.put("title", "部分地区限购");
			serviceData.put("content", "以下地区不配送:"+sheng+"。");
			serviceDatasList.add(serviceData);
			if(findParcelPostSomeAreas!=null){
				Map<String,Object> serviceData2 = new HashMap<String,Object>();
				serviceData2.put("title", "包邮区域及条件");
				StringBuilder sb = new StringBuilder();
				for (ShippingFreeRule shippingFreeRule : findParcelPostSomeAreas) {
					String areaName = shippingFreeRule.getAreasName();
					if(allSheng.equals(areaName)) {
						areaName = "全国：";
					} else {
						areaName=shippingFreeRule.getAreasName()+"：";
					}
					if(shippingFreeRule.getCountTypeDes().equals("2")){
						if(sb.length()>0) sb.append("\n");
						sb.append(areaName+"订单金额（不含运费）满"+shippingFreeRule.getParam2()+"元免付邮费。");;
					}else if(shippingFreeRule.getCountTypeDes().equals("1")){
						if(sb.length()>0) sb.append("\n");
						sb.append(areaName+"订单满"+shippingFreeRule.getParam1()+"件免付邮费。");
					}else if(shippingFreeRule.getCountTypeDes().equals("3")){
						if(sb.length()>0) sb.append("\n");
						sb.append(areaName+"订单金额（不含运费）满"+shippingFreeRule.getParam2()+"元且满"+shippingFreeRule.getParam1()+"件免付邮费。");
					}
				}
				if(sb.length()>0) {
					serviceData2.put("content", sb.toString());
					serviceDatasList.add(serviceData2);
				}
			}
		}else{//销售区域全国
			if(findById.getCarriage().intValue()==0){//运费为0
				Map<String,Object> serviceData = new HashMap<String,Object>();
				serviceData = new HashMap<String,Object>();
				serviceData.put("title", "全国包邮");
				serviceData.put("content", "购买该商品，全国范围内免付邮费。");
				serviceDatasList.add(serviceData);
			}else{//有运费
				if(findParcelPostSomeAreas!=null){
					Map<String,Object> serviceData2 = new HashMap<String,Object>();
					serviceData2.put("title", "包邮区域及条件");
					serviceData2.put("content",null);
					StringBuilder sb = new StringBuilder();
					for (ShippingFreeRule shippingFreeRule : findParcelPostSomeAreas) {
						String areaName = shippingFreeRule.getAreasName();
						if(allSheng.equals(areaName)) {
							areaName = "全国:";
						} else {
							areaName=shippingFreeRule.getAreasName()+"：";
						}
						if(shippingFreeRule.getCountTypeDes().equals("2")){
							if(sb.length()>0) sb.append("\n");
							sb.append(areaName+"订单金额（不含运费）满"+shippingFreeRule.getParam2()+"元免付邮费。");
							//serviceData2.put("content", "以下地区包邮："+shippingFreeRule.getAreasName()+"。单笔订单金额（不含运费）满"+shippingFreeRule.getParam2()+"元免付邮费。");
						}else if(shippingFreeRule.getCountTypeDes().equals("1")){
							if(sb.length()>0) sb.append("\n");
							sb.append(areaName+"订单满"+shippingFreeRule.getParam1()+"件免付邮费。");
						}else if(shippingFreeRule.getCountTypeDes().equals("3")){
							if(sb.length()>0) sb.append("\n");
							sb.append(areaName+"订单金额（不含运费）满"+shippingFreeRule.getParam2()+"元且满"+shippingFreeRule.getParam1()+"件免付邮费。");
						}
					}
					if(sb.length()>0) {
						serviceData2.put("content", sb.toString());
						serviceDatasList.add(serviceData2);
					}
				}
			}
			
		}
		if(findById.getSelfType()==1){
			Map<String,Object> serviceData = new HashMap<String,Object>();
			serviceData.put("title", "线下专供");
			serviceData.put("content", "该商品仅限线下内购会购买，仅支持线下自提。");
			serviceDatasList.add(serviceData);
		}
		if(findById.getLimitKbn()==3){
			Map<String,Object> serviceData = new HashMap<String,Object>();
			serviceData.put("title", "仅限企业采购");
			serviceData.put("content", "该商品仅限企业级用户使用店铺账号登录购买。");
			serviceDatasList.add(serviceData);
		}
		if(findById.getLimitCnt()!=0){
			Map<String,Object> serviceData = new HashMap<String,Object>();
			serviceData.put("title", "数量限购");
			serviceData.put("content", "该商品每名用户限购数量为"+findById.getLimitCnt()+"件。");
			serviceDatasList.add(serviceData);
		}
		if(carriageDes.indexOf("元")!=-1&&carriageDes.indexOf("件")==-1){
			int indexOf = carriageDes.indexOf("满");
			int indexOf2 = carriageDes.indexOf("元");
			String substring = carriageDes.substring(indexOf+1,indexOf2);
			Map<String,Object> serviceData = new HashMap<String,Object>();
			serviceData.put("title", "满"+substring+"元包邮");
			serviceData.put("content", "当前收货地址：订单金额（不含运费）满"+substring+"元免付邮费。");
			serviceDatasList.add(serviceData);
		}
		if(show.getMaxFucoin()==null||show.getMaxFucoin().intValue()<0){
			if(show.getCost()!=null&&show.getCost().intValue()>0){
				Map<String,Object> serviceData = new HashMap<String,Object>();
				serviceData = new HashMap<String,Object>();
				serviceData.put("title", "免超额内购券");
				serviceData.put("content", "购买该商品所需内购券超过400部分由我的福利补贴。");
				serviceDatasList.add(serviceData);
			}
		}
		
		Map<String,Object> serviceData = new HashMap<String,Object>();
		serviceData = new HashMap<String,Object>();
		serviceData.put("title", "厂家直销");
		serviceData.put("content", "该商品从厂家直接发货，正品保真。");
		serviceDatasList.add(serviceData);
		serviceData = new HashMap<String,Object>();
		serviceData.put("title", "发票保证");
		serviceData.put("content", "购买该商品时，可在线开具发票。");
		serviceDatasList.add(serviceData);
		serviceData = new HashMap<String,Object>();
		serviceData.put("title", "7天无理由退货");
		serviceData.put("content", "购买该商品享受7天无理由退货服务，可在线申请退货。");
		serviceDatasList.add(serviceData);
		model.addAttribute("serviceDatas",serviceDatasList);
		return ActResult.success(model);
	}
	/**
	 * 查询SKU产品图片
	 * @param specificationsId
	 * @param model
	 * @return
	 */
	@RequestMapping("image")
	public ActResult<Object> getProductPicture(Long specificationsId,ModelMap model,Integer quantity,Long userId,HttpServletRequest request){
		Product p = productService.findBySku(specificationsId);
		List<ProductSpecificationsImage> list = productSpecificationsImageService.findProductPicture(specificationsId,p.getId());
		Integer stock = inventoryService.getInventoryFromRedis(specificationsId);
		ProductSpecifications ps = productSpecificationsService.findByIdCache(specificationsId,p.getId().toString());

		//员工特享商品
		UserFactory user = userId==null?null:userService.getById(userId);
		boolean isLadder = productSpecificationsService.resetPrice(ps, p, user,true,quantity);
		
		model.addAttribute("image", list);
		model.addAttribute("showPrice", ps.getPrice());
		model.addAttribute("maxFucoin", ps.getMaxFucoin());
		model.addAttribute("purchasePrice", ps.getInternalPurchasePrice());
		model.addAttribute("cost", ps.getCost());
		Integer orderCount = 0;
		if(p.getSaleKbn()!=null && p.getSaleKbn()==2) {
			orderCount=exchangeSuborderitemService.getOrderCount(ps.getId());
		}
		model.addAttribute("orderCount", orderCount);
		Map<String,BigDecimal> salesPromotion =null;
		//如果是员工专享价格,则增加内购价和内购券
		if(ps.getEmpPrice() !=null && ps.getEmpPrice().compareTo(BigDecimal.ZERO) > 0) {
			ProductSpecifications  newRet  = productSpecificationsService.findByIdCache(ps.getId(),null);
			model.addAttribute("showPrice", newRet.getPrice());
			model.addAttribute("purchasePrice", newRet.getInternalPurchasePrice());
			model.addAttribute("BenefitSubsidy", ps.getBenefitSubsidy());//内购补贴
		}else {
			if (isLadder) {
				model.addAttribute("isLadder", true);
			}
		}
		salesPromotion = productLadderService.getSalePromotionMapBySkuid(specificationsId);
		model.addAttribute("minLimitNum", ps.getMinLimitNum());
		model.addAttribute("stock", stock==null?0:stock);
		quantity = quantity==null?1:quantity;
		
		model.addAttribute("isLadder", false);
		
		//增加促销信息
		model.addAttribute("salesPromotion", salesPromotion);

		//计算运费
		List<String> outRuleDes = new ArrayList<String>();

		String acode= shippingFacade.getACode(user, IPUtils.getClientAddress(request));
		BigDecimal carriage = this.calculateShipping(quantity, ps.getInternalPurchasePrice().multiply(NumberUtil.toBigDecimal(quantity)), user, outRuleDes, p, acode);

		model.addAttribute("carriage",carriage);
		if(!outRuleDes.isEmpty()) model.addAttribute("carriageDes",outRuleDes.get(0));
		return ActResult.success(model);
	}
	/**
	 * 查询SKU产品图片
	 * @param specificationsId
	 * @param model
	 * @return
	 */
	@RequestMapping("selectStock")
	public ActResult<Object> selectStock(Long specificationsId,ModelMap model,Integer quantity,Long userId,HttpServletRequest request){
		//计算运费
		quantity = quantity==null?1:quantity;
		
		Product p = productService.findBySku(specificationsId);
		Integer stock = inventoryService.getInventoryFromRedis(specificationsId);
		if(stock < quantity){
			return ActResult.fail("库存不足");
		}
		ProductSpecifications ps = productSpecificationsService.findByIdCache(specificationsId,p.getId().toString());
		if(ps == null) return ActResult.fail("商品已下架 ");		
		if(ps.getMinLimitNum() > quantity) {
			return ActResult.fail("该商品 " + ps.getMinLimitNum() + "件以上起售");
		}

		//员工特享商品
		UserFactory user = userId==null?null:userService.getById(userId);
		boolean isLadder = productSpecificationsService.resetPrice(ps, p, user,true,quantity);
		
		model.addAttribute("isLadder",isLadder);
		model.addAttribute("maxFucoin",ps.getMaxFucoin());
		List<String> outRuleDes = new ArrayList<String>();

		String acode= shippingFacade.getACode(user, IPUtils.getClientAddress(request));
		BigDecimal carriage = this.calculateShipping(quantity, ps.getInternalPurchasePrice().multiply(NumberUtil.toBigDecimal(quantity)), user, outRuleDes, p, acode);	

		model.addAttribute("carriage",carriage);
		model.addAttribute("realPrice",ps.getInternalPurchasePrice());
		if(ps.getCost()!=null && ps.getCost().compareTo(BigDecimal.ZERO)>=0){
			model.addAttribute("cost",ps.getCost());
		}
		if(!outRuleDes.isEmpty()) model.addAttribute("carriageDes",outRuleDes.get(0));
		
		return ActResult.success(model);
	}
//	/**
//	 * 查询具体商品SKU价格与企业券最大使用量
//	 * @param productId
//	 * @param specificationsId
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping("info")
//	public ActResult<Object> specificationsInfo(Long productId,Long specificationsId,ModelMap model){
//		List<ProductSpecifications> specificationsList = productService.findSku(productId);
//		for(ProductSpecifications ps : specificationsList){
//			if(specificationsId.equals(ps.getId())){
//				model.addAttribute("maxCompanyTicket", ps.getMaxFucoin());
//				model.addAttribute("price", ps.getPrice());
//				break;
//			}
//		}
//		if(model.size()<1){
//			return ActResult.fail("价格获取失败");
//		}
//		return ActResult.success(model);
//	}

	/**
	 * 分页显示商品评论
	 * @param productId
	 * @param page
	 * @param pageSize
	 * @returncommentDegree
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/comments")
	public ActResult<PageInfo> commentsList(ModelMap model,Long productId, Integer page, Integer pageSize) {
		Map<String, Object> score = commentsService.getProductScore(productId);
		CommentsCountVo ccv = new CommentsCountVo();
		
		ccv.setPraiseCount(NumberUtil.toInteger(score.get(CommentsService.CACHE_MAP_KEY_PRAISE_CNT)));
		ccv.setNomalCount(NumberUtil.toInteger(score.get(CommentsService.CACHE_MAP_KEY_NOMAL_CNT)));
		ccv.setBadCount(NumberUtil.toInteger(score.get(CommentsService.CACHE_MAP_KEY_BAD_CNT)));
		ccv.setGoodsRatings(NumberUtil.toDouble(score.get(CommentsService.CACHE_MAP_KEY_GOODS_AVG)));
		ccv.setServiceRatings(NumberUtil.toDouble(score.get(CommentsService.CACHE_MAP_KEY_SERVICE_AVG)));
		ccv.setLogisticsRatings(NumberUtil.toDouble(score.get(CommentsService.CACHE_MAP_KEY_LOGISTICS_AVG)));
		model.addAttribute("commentRatings", ccv);
    	Comments q = new Comments();
		q.setProductId(productId);
    	model.addAttribute("comments", commentsService.findPageComments(q, page, pageSize));
		return ActResult.success(model);
	}
	
	@RequestMapping("getLadder")
	@ResponseBody
	public Map<String,String> getLadder(Long partNumber, Integer quantity) {

		Map<String,String> map = new HashMap<String,String>();
		BigDecimal ladderPrice = productLadderService.getPriceBySkuidAndPrice(partNumber, quantity);
		if (null != ladderPrice) {
			map.put("price", ""+ladderPrice);
			map.put("isLadder", ""+true);
			map.put("maxFucoin", "0.01");
			return map;
		}else{
			ProductSpecifications ps = productSpecificationsService.findByIdCache(partNumber,null);
			map.put("price", ps.getInternalPurchasePrice()+"");
			map.put("isLadder", ""+false);
			map.put("maxFucoin", ps.getMaxFucoin()+"");
			return map;
		}
	}
	
	private BigDecimal calculateShipping(Integer quantity, BigDecimal amount, UserFactory user,
			List<String> outRuleDes, Product p, String acode) {
		// 先检查限购
		BigDecimal carriage = shippingFacade.chkLimitCntAndArea(p, quantity, acode, outRuleDes, user == null ? null : user.getId(), null);

		if(!shippingFacade.hasLimit(carriage)){
			outRuleDes.clear();
			
			carriage = shippingFacade.calculateSingleShippingFee("0", quantity, amount, user, outRuleDes, p, acode);
		}
		
		return carriage;
	}
}
