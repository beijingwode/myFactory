package com.wode.factory.user.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.ClientAccessLog;
import com.wode.factory.model.CollectionProduct;
import com.wode.factory.model.CollectionShop;
import com.wode.factory.model.Comments;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.Shop;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserIm;
import com.wode.factory.service.BrandService;
import com.wode.factory.service.CommentsService;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.service.ProductService;
import com.wode.factory.user.facade.ShippingFacade;
import com.wode.factory.user.model.ProductHot;
import com.wode.factory.user.service.ClientAccessLogService;
import com.wode.factory.user.service.CollectionProductService;
import com.wode.factory.user.service.CollectionShopService;
import com.wode.factory.user.service.EntParamCodeService;
import com.wode.factory.user.service.ExchangeSuborderitemService;
import com.wode.factory.user.service.InventoryService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.QuestionnaireAnswerService;
import com.wode.factory.user.service.RecommendProductService;
import com.wode.factory.user.service.ShopService;
import com.wode.factory.user.service.UserImService;
import com.wode.factory.user.util.CookieUtils;
import com.wode.factory.user.util.IPUtils;
import com.wode.factory.user.vo.ProductSpecificationsVo;
import com.wode.factory.vo.BrandVo;
import com.wode.factory.vo.CommentsVo;
import com.wode.search.WodeSearchManager;

/**
 * 商品详情web层
 * User: guziye
 */
@Controller
@RequestMapping("")
public class ProductDetailController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(ProductDetailController.class);
	@Autowired
	private UserImService userImService;
	
	@Qualifier("productSpecificationsService")
	@Autowired
	private ProductSpecificationsService psService;
	
	@Qualifier("collectionShopService")
	@Autowired
	private CollectionShopService csService;
	
	@Qualifier("collectionProductService")
	@Autowired
	private CollectionProductService cpService;
	
	@Autowired
    private InventoryService inventoryService;
	
    @Autowired
    private ShopService shopService;
    @Autowired
    private CommentsService commentsService;

	@Autowired
	private ProductService productService;
    @Autowired
	private ShippingFacade shippingFacade;
    @Autowired
    private ClientAccessLogService clientAccessLogService;

	@Autowired
	private RecommendProductService recommendProductService;

	@Autowired
	private ProductCategoryService productCateService;
	@Autowired
	private BrandService brandVoService;
	@Autowired
	private WodeSearchManager wsm;
	
	@Autowired
	private ProductLadderService productLadderService;
	
	@Autowired
	private EntParamCodeService entParamCodeService;
	/**
	 * 调查问卷
	 */
	@Autowired
	private QuestionnaireAnswerService questionnaireAnswerService;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
	@Autowired
	private ExchangeSuborderitemService exchangeSuborderitemService;
	/**
	 * 商品sku
	 * guziye
	 */
	@RequestMapping("detail")
	@ResponseBody
	public ActResult<ProductSpecificationsVo> sku(String itemsIds,Long skuId,Integer quantity,HttpServletRequest request, HttpServletResponse response){
		ProductSpecificationsVo ret = null;
		ProductSpecificationsVo realRet = psService.selectProductSpecifications(itemsIds);//判断itemids和 这个skuid是否有关系，如果没有以itemdis为准
		if(skuId!=null){
			ret = psService.findByIdCache(skuId,null);
		}else{
			ret = psService.selectProductSpecifications(itemsIds);
		}
		//如果productid不相等，将用skuid查询出来的sku 替换为 itemsIds 查询出来的sku
		
		if(ret == null || (realRet!=null && ret.getProductId().compareTo(realRet.getProductId()) != 0)){
			ret = realRet;
		}
		if(ret !=null) {
			UserFactory user =getUser(request,response);
			Integer stock = inventoryService.getInventoryFromRedis(ret.getId());
			stock = stock == null ? 0 : stock;
			ret.setStock(stock);
			
			//计算运费
			quantity = quantity==null?1:quantity;
			List<String> outRuleDes = new ArrayList<String>();
			Product p=productService.findById(ret.getProductId(), false);
			
			if(null == p){
				ProductSpecificationsVo data=new ProductSpecificationsVo();
				data.setStock(0);
				return ActResult.success(data);
			}
			
			ret.setLimit(productService.checkProductLimit(p, user));
			if(ret.getLimit()){
				ret.setLimitMsg("该商品仅限企业用户购买！");
			}
			psService.resetPrice(ret, p, user,true,quantity);
			
			
			ret.setSaleKbn(p.getSaleKbn());
			ret.setSaleNote(p.getSaleNote());
			ret.setIsMarketable(p.getIsMarketable());

			//如果是员工专享价格,则增加内购价和内购券
			if(ret.getEmpPrice() !=null && ret.getEmpPrice().compareTo(BigDecimal.ZERO) > 0) {
				ProductSpecificationsVo newRet  = psService.findByIdCache(ret.getId(),null);
				ret.setInternalPurchasePrice(newRet.getInternalPurchasePrice());
				ret.setPrice(newRet.getPrice());
			}
			String acode= shippingFacade.getACode(user, IPUtils.getClientAddress(request));
			BigDecimal carriage= calculateShipping(quantity, ret, user, outRuleDes, p, acode);

			logger.debug("carriage :"+carriage);
			ret.setCarriage(carriage);
			
			if(!outRuleDes.isEmpty()) {
				ret.setCarriageDes(outRuleDes.get(0));
				//增加全场包邮描述
				if(outRuleDes.size() > 1 && null != outRuleDes.get(1)){
					ret.setFreeDes(outRuleDes.get(1));
				}
			}

			//增加促销信息
			ret.setSalesPromotion(productLadderService.getStringBySkuid(ret.getId()));
			//试用商品检查问卷
			if(ret.getSaleKbn()!=null && ret.getSaleKbn()==5) {
				if(!NumberUtil.isGreaterZero(p.getEmpPrice())) {
					//评价后购买
					if(p.getQuestionnaireId() != -1 &&!this.questionnaireAnswerService.hasAnswer(p.getQuestionnaireId(), user == null ? null : user.getId())){
						ret.setIsQuestioned(false);
					}else{
						ret.setIsQuestioned(true);
					}
				}	
				ret.setQuestionId(String.valueOf(p.getQuestionnaireId()));
			}
			Integer orderCount = 0;
			if(p.getSaleKbn()!=null && p.getSaleKbn()==2) {
				orderCount=exchangeSuborderitemService.getOrderCount(ret.getId());
			}
			ret.setOrderCount(orderCount);
			try {
				//查询详情页,浏览次数加1
				clientAccessLogService.saveProduct(ClientAccessLog.ACCESS_TYPE_PRODUCT, ret.getId(), ret.getProductName()+"："+ret.getItemnames(), user==null?null:user.getId(),user,ret.getProductId(),null,CookieUtils.getUUID(request, response),IPUtils.getClientAddress(request));
				wsm.updateViewCntNum(ret.getProductId());
			} catch (Exception e) {
				logger.info("商品异常，可能是商品已经下架或者未上线");
			}
			logger.debug("carriageDes :"+ret.getCarriageDes());

			return ActResult.success(ret);
		} else {
			ProductSpecificationsVo data=new ProductSpecificationsVo();
			data.setStock(0);
			return ActResult.success(data);
		}
	}
	
	/**
	 * 商品sku
	 * guziye
	 */
	@RequestMapping("selectStock")
	@ResponseBody
	public ActResult<String> selectStock(Long partNumber,Integer quantity,HttpServletRequest request, HttpServletResponse response){
		Integer stock = inventoryService.getInventoryFromRedis(partNumber);
		stock = stock == null ? 0 : stock;
		quantity = quantity == null ? 1 : quantity;
		if(stock<quantity){
	    	return ActResult.fail("商品库存不足 "+quantity);
	    }
		//计算运费
		UserFactory user =getUser(request,response);
		ProductSpecificationsVo ret= psService.findByIdCache(partNumber,null);
		if(ret == null) return ActResult.fail("商品已下架 ");
		if(ret.getMinLimitNum() > quantity){
			return ActResult.fail("该商品 " + ret.getMinLimitNum() + "件以上起售");
		}
		if(!partNumber.equals(ret.getId())) {
			partNumber=ret.getId();
		}
		Product p=productService.findById(ret.getProductId(), false);
		psService.resetPrice(ret, p, user,true,quantity);
		
		List<String> outRuleDes = new ArrayList<String>();

		String acode= shippingFacade.getACode(user, IPUtils.getClientAddress(request));
		BigDecimal carriage= calculateShipping(quantity, ret, user, outRuleDes, p, acode);
		ret.setCarriage(carriage);
		if(carriage.compareTo(new BigDecimal(9999))==0) {
			return ActResult.fail("已超过限购数量,不能购买该商品了。或者减少购买数量");
		} 
		/*else if(carriage.compareTo(new BigDecimal(8888))==0){
			return ActResult.fail("配送不到指定收货地址,不能购买该商品了，或者选择其他收货地址");
		} */
		String rtn = carriage +"####";
		if(!outRuleDes.isEmpty()) {
			rtn+=outRuleDes.get(0);
			if(outRuleDes.size()>1 && null != outRuleDes.get(1)) {
				rtn= rtn + "####" + outRuleDes.get(1);
			}
		}
		
		return ActResult.success(rtn);
	}
	
	/**
	 * 商品评论List
	 * guziye
	 */
	@RequestMapping(value="comments/{pages}/{sizes}")
	@ResponseBody
	public ActResult<PageInfo<CommentsVo>> commentList(Long productId,int index,@PathVariable Integer pages,@PathVariable Integer sizes){
		ActResult<PageInfo<CommentsVo>> ar = new ActResult<PageInfo<CommentsVo>>();
		Comments model = new Comments();
		model.setProductId(productId);
		switch(index) {
		case 1:
			model.setCommentDegree(5);
			break;
		case 2:
			model.setCommentDegree(3);
			break;
		case 3:
			model.setCommentDegree(1);
			break;
		}
		//PageInfo<Comments> data =commentsService.findPageComments(model, pages, sizes);
		ar.setData(commentsService.findPageComments(model, pages, sizes));
		return ar;
	}

	/**
	 * 商品评论List
	 * guziye
	 */
	@RequestMapping(value="categoryList/{categoryId}")
	@ResponseBody
	public ActResult<List<ProductHot>> categoryList(@PathVariable Long categoryId){
		List<ProductHot> rtn = new ArrayList<ProductHot>();
		List<Product> hotList = recommendProductService.findHotSellByCate(categoryId);
		for (Product product : hotList) {
			ProductHot hot = new ProductHot();
			hot.setId(product.getId());
			hot.setImage(product.getImage());
			hot.setName(product.getName());
			if(product.getProductSpecificationslist() == null || product.getProductSpecificationslist().isEmpty()) {
				hot.setShowPrice("<p class=\"p1\">内购价：<span>￥"+(StringUtils.isEmpty(product.getShowPrice())?product.getMinprice()+"":product.getMinprice())+"</span></p>");
				hot.setMinprice(product.getMinprice());
			} else {
				ProductSpecifications sku = product.getProductSpecificationslist().get(0);
				hot.setShowPrice("<p class=\"p1\">内购价：<span>￥"+sku.getPrice().subtract(sku.getMaxFucoin()).setScale(2, BigDecimal.ROUND_DOWN)+"</span><em>"
						+sku.getPrice().subtract(sku.getMaxFucoin()).multiply(new BigDecimal(10)).divide(sku.getPrice(), BigDecimal.ROUND_DOWN).setScale(1, BigDecimal.ROUND_DOWN)+"折</em></p>");
				hot.setMinprice(sku.getPrice());
			}
			
			rtn.add(hot);
		}
		return ActResult.success(rtn);
	}
	
	/**
	 * 商品评论List
	 * guziye
	 */
	@RequestMapping(value="bvList/{categoryId}")
	@ResponseBody
	public ActResult<List<JSONObject>> bvList(@PathVariable Long categoryId){
		List<JSONObject> rtn = new ArrayList<JSONObject>();
		for (BrandVo bv : brandVoService.selectByCategory(categoryId)) {
			JSONObject jo = new JSONObject();
			jo.put("brandId", null);
			jo.put("brandName", bv.getBrandName());
			
			rtn.add(jo);
		}
		return ActResult.success(rtn);
	}

	/**
	 * 商品评论List
	 * guziye
	 */
	@RequestMapping(value="pcList/{categoryId}")
	@ResponseBody
	public ActResult<List<JSONObject>> pcList(@PathVariable Long categoryId){
		List<JSONObject> rtn = new ArrayList<JSONObject>();
		ProductCategory pojo = new ProductCategory();
		pojo.setId(productCateService.findById(categoryId).getPid());
		
		for (ProductCategory pc : productCateService.findSub(pojo)) {
			JSONObject jo = new JSONObject();
			jo.put("id", pc.getId());
			jo.put("name", pc.getName());
			
			rtn.add(jo);
		}
		return ActResult.success(rtn);
	}
	
	/**
	 * 判断是否收藏当前店铺
	 * guziye
	 */
	@RequestMapping(value="selectCollectionShop")
	@ResponseBody
	public Boolean selectCollectionShop(HttpServletRequest request, HttpServletResponse response,Long shopId){
		Boolean b = false;

		UserFactory user = getUser(request,response);
		if(user==null){
			b = false;
		}else{
			b = csService.selectOne(user.getId(),shopId);
		}
		return b;
	}
	
	/**
	 * 收藏当前店铺
	 * guziye
	 */
	@RequestMapping(value="collectionShop")
	@ResponseBody
	public ActResult<CollectionShop> collectionShop(HttpServletRequest request, HttpServletResponse response,Long shopId){
		ActResult<CollectionShop> ar = new ActResult<CollectionShop>();
		UserFactory user = getUser(request,response);
		if(user==null){
			ar.setSuccess(false);
		}else{
			CollectionShop cs = new CollectionShop();
			Boolean b = csService.selectOne(user.getId(),shopId);
			if(!b){
				cs.setUserId(user.getId());
				cs.setShopId(shopId);
				cs.setCreatTime(new Date());
				cs = csService.save(cs);
				if(cs!=null){
					ar.setMsg("店铺收藏成功");
				}else{
					ar.setSuccess(false);
					ar.setMsg("店铺收藏失败");
				}
			}else{
				ar.setMsg("已收藏该店铺");
			}
		}
		return ar;
	}
	
	/**
	 * 取消收藏当前店铺
	 * guziye
	 */
	@RequestMapping(value="canelCollectionShop")
	@ResponseBody
	public ActResult<CollectionShop> cancelCollectionShop(HttpServletRequest request, HttpServletResponse response,Long shopId){
		ActResult<CollectionShop> ar = new ActResult<CollectionShop>();
		UserFactory user = getUser(request,response);
		if(user==null){
			ar.setSuccess(false);
		}else{
			Boolean a = csService.selectOne(user.getId(),shopId);
			if(!a){
				ar.setSuccess(false);
				ar.setMsg("未收藏该店铺");
			}else{
				Boolean b = csService.canelCollectionShop(user.getId(),shopId);
				if(b){
					ar.setMsg("取消收藏店铺成功");
				}else{
					ar.setSuccess(b);
					ar.setMsg("取消失败，请重试");
				}
			}
		}
		return ar;
	}
	
	/**
	 * 判断是否收藏当前商品
	 * guziye
	 */
	@RequestMapping(value="selectCollectionProduct")
	@ResponseBody
	public Boolean selectCollectionProduct(HttpServletRequest request, HttpServletResponse response,Long productId){
		Boolean b = false;
		UserFactory user = getUser(request,response);
		if(user==null){
			b = false;
		}else{
			b = cpService.selectOne(user.getId(),productId);
		}
		return b;
	}
	
	/**
	 * 收藏当前商品
	 * guziye
	 */
	@RequestMapping(value="collectProduct")
	@ResponseBody
	public ActResult<CollectionProduct> collectionProduct(HttpServletRequest request, HttpServletResponse response,Long productId){
		ActResult<CollectionProduct> ar = new ActResult<CollectionProduct>();
		UserFactory user = getUser(request,response);
		if(user==null){
			ar.setSuccess(false);
		}else{
			CollectionProduct cp = new CollectionProduct();
			Boolean b = cpService.selectOne(user.getId(),productId);
			if(!b){
				cp.setUserId(user.getId());
				cp.setProductId(productId);
				cp.setCreatTime(new Date());
				cp = cpService.save(cp);
				if(cp!=null){
					ar.setMsg("商品收藏成功");
				}else{
					ar.setSuccess(false);
					ar.setMsg("商品收藏失败");
				}
			}else{
				ar.setMsg("已收藏该商品");
			}
		}
		return ar;
	}
	
	/**
	 * 取消收藏当前商品
	 * guziye
	 */
	@RequestMapping(value="canelCollectionProduct")
	@ResponseBody
	public ActResult<CollectionProduct> cancelCollectionProduct(HttpServletRequest request, HttpServletResponse response,Long productId){
		ActResult<CollectionProduct> ar = new ActResult<CollectionProduct>();
		UserFactory user=getUser(request,response);
		if(user==null){
			ar.setSuccess(false);
			return ar;
		}
		
		Boolean b =true;
		if(productId!=null){
			 b = cpService.canelCollectionProduct(user.getId(),productId);
		}else{
			String[] pids=request.getParameterValues("productIds[]");
			if(pids!=null&&pids.length>0){
				List<Long> li=new ArrayList();
				for(String pid:pids){
					li.add(Long.valueOf(pid));
				}
				b = cpService.canelCollectionProduct(user.getId(),li);
			}
			 
		}
		ar.setSuccess(b);
		if(b){
			ar.setMsg("删除成功");
		}else{
			ar.setMsg("删除失败，请重试");
		}
		return ar;
	}
	
	/**
	 * 店铺详细信息	
	 * @param model
	 * @param shopId
	 * @return
	 */
    @RequestMapping(value="shopDetail")
    @ResponseBody
    public ActResult info(HttpServletRequest request,ModelMap model, Long shopId){
        Shop shop=shopService.getShopSettingById(shopId);
        if(shop!=null){
        	//'服务评级'
        	Map<String, Object> map= commentsService.getSupplierScore(shop.getSupplierId());

        	model.addAttribute("shopDescription", map.get(CommentsService.CACHE_MAP_KEY_GOODS_AVG));
        	model.addAttribute("shopService",  map.get(CommentsService.CACHE_MAP_KEY_SERVICE_AVG));
        	model.addAttribute("deliverySpeed", map.get(CommentsService.CACHE_MAP_KEY_LOGISTICS_AVG));

        	model.addAttribute("qq", shop.getQq());
        	model.addAttribute("tel1",shop.getShopPhone());
        	model.addAttribute("tel2",shop.getShopTel());
        	model.addAttribute("tel2",shop.getShopTel());
        	model.addAttribute("shopName",shop.getShopname());
        	
        	String ssDesc = shippingFacade.getFreeString(shop.getSupplierId(), null);
    		
        	model.addAttribute("free","商家全场：" + ssDesc);

    		UserIm shopIm= userImService.selectByShopAndSupplier(shop.getId(), shop.getSupplierId());
    		model.addAttribute("shopImUser",shopIm==null?null:shopIm.getOpenimId());
    		
        	return ActResult.success(model);
        }else{
        	return ActResult.fail("店铺不存在");
        }
    }
    
    @RequestMapping("getLadder")
	@ResponseBody
	public Map<String,String> getLadder(Long partNumber, Integer quantity) {

		Map<String,String> map = new HashMap<String,String>();
		ProductSpecifications ps = psService.findByIdCache(partNumber,null);
		BigDecimal ladderPrice = productLadderService.getPriceBySkuidAndPrice(partNumber, quantity);
		if (null != ladderPrice) {
			map.put("price", ""+ladderPrice);
			map.put("isLadder", ""+true);
			map.put("maxFucoin", "0.01");
			map.put("oldPrice", ""+ps.getPrice());
			return map;
		}else{
			BigDecimal big=entParamCodeService.getBenefitSubsidy();
			map.put("price", ps.getInternalPurchasePrice()+"");
			map.put("isLadder", ""+false);
			if(big.compareTo(ps.getMaxFucoin())<=0) {
				ps.setMaxFucoin(big);
			}
			map.put("maxFucoin", ps.getMaxFucoin()+"");
			map.put("oldPrice", ""+ps.getPrice());
			return map;
		}
	}
    
	private BigDecimal calculateShipping(Integer quantity, ProductSpecificationsVo ret, UserFactory user,
			List<String> outRuleDes, Product p, String acode) {
		
		// 先检查限购
		BigDecimal carriage = shippingFacade.chkLimitCntAndArea(p, quantity, acode, outRuleDes, user == null ? null : user.getId(), null);

		if(!shippingFacade.hasLimit(carriage)){
			outRuleDes.clear();
			
			carriage = shippingFacade.calculateSingleShippingFee("0", quantity, ret.getInternalPurchasePrice().multiply(NumberUtil.toBigDecimal(quantity)), user, outRuleDes, p, acode);
		}
		
		return carriage;
	}
}    
