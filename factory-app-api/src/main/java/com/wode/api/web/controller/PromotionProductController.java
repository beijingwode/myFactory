package com.wode.api.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wode.api.facade.LoginFacade;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.model.PromotionProduct;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.BrandService;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsImageService;
import com.wode.factory.user.query.PromotionProductQuery;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.PromotionProductService;
import com.wode.factory.user.vo.ProductDetailVo;
import com.wode.factory.user.vo.PromotionProductVo;
import com.wode.factory.vo.ProductVo;

/**
 * APP活动接口
 * @author user
 *
 */
@Controller
@ResponseBody
@RequestMapping("/promotion")
public class PromotionProductController extends BaseController{

	@Autowired
	private PromotionProductService pps;
	 
	@Autowired
	private ProductService productService;

	@Autowired
	private ProductCategoryService productCateService;

	@Autowired
	private BrandService brandVoService;

	@Autowired
	private ProductSpecificationsService productSpecificationsService;

	@Autowired
	private ProductSpecificationsImageService  productSpecificationsImageService;
	
	@Autowired
	private RedisUtil redis;

	@Autowired
	private LoginFacade loginFacade;
	
	/**
	 * 当前秒杀商品
	 * guziye 
	 * @param model
	 * @param promotionId
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("panicBuy")
	public ActResult<Object> panicBuy(ModelMap model, Long promotionId, Integer pageSize, Integer pageNum,Integer hour,String ticket){
		if(StringUtils.isNullOrEmpty(hour)){
			hour = new Date().getHours();
		}
		if(pageSize==null || pageSize<1){
			pageSize = 10;
		}
		if(pageNum==null || pageNum<1){
			pageNum = 1;
		}
		Date date;
		String time;
		if(hour>24 || hour<0){
			return ActResult.fail("本阶段不存在活动");
		}
		if(hour<10){
			time = " 0"+hour+":00:00";
		}else{
			time = hour+":00:00";
		}
		UserFactory user = null;
		if(!StringUtils.isEmpty(ticket)){
			ActResult<UserFactory> act = loginFacade.hasLogin(ticket);
			if(act.isSuccess()){
				user=act.getData();
			}
		}
		String str = TimeUtil.getStringDateShort();
		date = TimeUtil.strToDate(str+" "+time);
		
		PromotionProductQuery query = new PromotionProductQuery();
		query.setPromotionId(promotionId);
		query.setPageSize(pageSize);
		query.setPageNumber(pageNum);
		query.setJoinStart(date);
		PageInfo<PromotionProductVo> page = pps.findPage(query);
		for(PromotionProductVo pv :page.getList()){
			try{
				Integer locked = 0;
				Integer stock = 0;
				String key = String.valueOf(pv.getPromotionId()+"_"+pv.getId());
				Map<String, String> map = redis.getMap(key);
				if(!StringUtils.isNullOrEmpty(map)){
					locked = Integer.valueOf(map.get("locked")==null?"0":map.get("locked"));
					stock = Integer.valueOf(map.get("stock")==null?"0":map.get("stock"));
				}
				if(locked>stock)
					pv.setStock(0);
				else
					pv.setStock(stock-locked);
				//判断是否关注
				if(!StringUtils.isNullOrEmpty(user)){
					Long startTime = pv.getJoinStart().getTime();
					pv.setIsAttention(redis.ismember(user.getId()+"_"+startTime,pv.getId()+""));
				}
			}catch(Exception e){
				e.printStackTrace();
				pv.setStock(0);
			}
		}
		model.put("page", page);
		return ActResult.success(model);
	}
	
	/**
	 * app活动商品详情
	 * guziye
	 */
	@RequestMapping("/detail")
	public ActResult<Object> productDetail(HttpServletRequest request,ModelMap model, Long promotionProductId) {
		PromotionProductQuery query = new PromotionProductQuery();
		query.setId(promotionProductId);
		PromotionProductVo pp = pps.findById(query);
		if(pp==null){
			return ActResult.fail("该活动商品不存在");
		}
		long productId = pp.getProductId();
		long specificationsId = pp.getSkuId();
		ProductVo pvo = productService.findById(productId, true);
		if (pvo == null) {
			return ActResult.fail(productId + "的商品不存在");
		}
		ProductDetailVo productDetailVo = new ProductDetailVo();
		BeanUtils.copyProperties(pvo, productDetailVo);
		ProductCategory pojo = new ProductCategory();
		//当活动表(PromotionProduct)中的运费字段为空或者为0的时候，设置商品的运费为0(活动商品默认是包邮的)
		if(StringUtils.isNullOrEmpty(pp.getCarriagePrice())||pp.getCarriagePrice().doubleValue()==0.00){
			productDetailVo.setCarriage(new BigDecimal(0));
		}
		//商品清单
		pojo.setId(productCateService.findById(pvo.getCategoryId()).getPid());
		productDetailVo.setSupplierShopVo(productService.findShopByProductIdCache(productId));
		productDetailVo.setPcList(productCateService.findSub(pojo));
		ProductBrand pb = brandVoService.selectById(pvo.getBrandId());
		if (pb!=null&&StringUtils.isNullOrEmpty(pb.getName())) {
			pb.setName(pb.getNameEn());
		}
		ProductSpecifications show =productSpecificationsService.findByIdCache(specificationsId,productId+"");//要显示的sku
		if (show==null) {
			return ActResult.fail("未找到SKU信息");
		}
		productDetailVo.setShowPrice(show.getPrice() + "");
		List<ProductSpecificationsImage> imgList = productSpecificationsImageService
				.findProductPicture(specificationsId,show.getProductId());
		model.addAttribute("defaultImage", imgList);
		//默认显示商品图片
		List<ProductSpecificationsImage> imageList = productSpecificationsImageService.findProductPicture(show.getId(),show.getProductId());
		
		Set<String> list = new HashSet<String>();
		if(show.getItemids() .indexOf(",")==-1){
			list.add(show.getItemids() );
		}else{
			String[] array=show.getItemids().split(",");
			list.addAll(Arrays.asList(array));
		}
		try{
			String key = String.valueOf(pp.getPromotionId()+"_"+pp.getId());
			Map<String, String> map = redis.getMap(key);
			Integer locked = Integer.valueOf(map.get("locked"));
			Integer stock = Integer.valueOf(map.get("stock"));
			if(locked>stock)
				pp.setStock(0);
			else
				pp.setStock(stock-locked);
		}catch(Exception e){
			e.printStackTrace();
			pp.setStock(0);
		}
		model.addAttribute("attribute",show.getItemValues());
		model.addAttribute("promotionProduct",pp);
		model.addAttribute("defaultImage", imageList);
		model.addAttribute("product", productDetailVo);
		model.addAttribute("productAttributeMap",productService.findAttr(productId, pvo.getCategoryId(), true));
		model.addAttribute("productAttributeList",productService.findPar(productId, pvo.getCategoryId(), true));
		model.addAttribute("parentsCateList", productCateService.findParents(pvo.getCategoryId()));
		model.addAttribute("productBrand", pb);
		return ActResult.success(model);
	}
	
	/**
	 * 关注秒杀商品列表
	 * @param model
	 * @param promotionId
	 * @return
	 */
	@RequestMapping("careList.user")
	public ActResult<Object> cancelCarePromotionProduct(HttpServletRequest request,ModelMap model, Integer hour){
		if(StringUtils.isNullOrEmpty(hour)){
			hour = new Date().getHours();
		}
		String time;
		if(hour>24 || hour<0){
			return ActResult.fail("本阶段不存在活动");
		}
		if(hour<10){
			time = " 0"+hour+":00:00";
		}else{
			time = hour+":00:00";
		}
		Long startTime = TimeUtil.strToDate(time).getTime();
		Set<String> set = redis.getAllSet(loginUser.getId()+"_"+startTime);
		Iterator<String> it = set.iterator();
		List<PromotionProductVo> list = new ArrayList<PromotionProductVo>();
		while (it.hasNext()) {
		  String str = it.next();
		  PromotionProductQuery query = new PromotionProductQuery();
		  query.setId(Long.parseLong(str));
		  PromotionProductVo pv = pps.findOneCare(query);
		  try{
				Integer locked = 0;
				Integer stock = 0;
				String key = String.valueOf(pv.getId()+"_"+pv.getSkuId());
				Map<String, String> map = redis.getMap(key);
				if(!StringUtils.isNullOrEmpty(map)){
					locked = Integer.valueOf(map.get("locked")==null?"0":map.get("locked"));
					stock = Integer.valueOf(map.get("stock")==null?"0":map.get("stock"));
				}
				if(locked>stock)
					pv.setStock(0);
				else
					pv.setStock(stock-locked);
			}catch(Exception e){
				e.printStackTrace();
				pv.setStock(0);
			}
		  list.add(pv);
		}
		model.put("list", list);
		return ActResult.success(model);
	}
	
	/**
	 * 关注秒杀商品
	 * @param model
	 * @param promotionId
	 * @return
	 */
	@RequestMapping("care.user")
	public ActResult<Object> carePromotionProduct(HttpServletRequest request,Long promotionProductId){
		if(StringUtils.isNullOrEmpty(promotionProductId)){
			return ActResult.fail("请选择活动商品");
		}
		PromotionProductQuery query = new PromotionProductQuery();
		query.setJoinEnd(new Date());
		query.setId(promotionProductId);
		PromotionProduct promotionProduct = pps.findByCare(query);
		if(StringUtils.isNullOrEmpty(promotionProduct)){
			return ActResult.fail("活动商品已过期");
		}
		//获取参与活动开始时间
		Long startTime = promotionProduct.getJoinStart().getTime();  
		//redis sadd方法相当于java中Set方法    
		/**
		 * 
		 * 关注该商品
		 * 
		 * */
		redis.addToSet(loginUser.getId()+"_"+startTime, promotionProductId+""); 
		Long seconds = TimeUtil.compareTimeToLong(new Date(), promotionProduct.getJoinEnd());
		//设置有效时间     loginUser.getId()+"_"+startTime
		redis.setTime(loginUser.getId()+"_"+startTime, seconds.intValue());
		//推送信息的key值是  PromotionProduct_Care_+参与活动的开始时间,value值是用户的userId
		/**
		 * 
		 * 为关注该商品的用户推送通知
		 * */
		redis.addToSet(RedisConstant.PROMOTIONPRODUCT_CARE+startTime, loginUser.getId()+"");
		/**
		 * 该方法可以set进重复值
		 * 
		 * redis.rpush(RedisConstant.PROMOTIONPRODUCT_CARE+startTime, loginUser.getUserId()+"");
		 * */
		return ActResult.success("关注成功");
	}
	
	/**
	 * 取消关注秒杀商品
	 * @param model
	 * @param promotionId
	 * @return
	 */
	@RequestMapping("cancelCare.user")
	public ActResult<Object> cancelCarePromotionProduct(HttpServletRequest request,Long promotionProductId){
		if(StringUtils.isNullOrEmpty(promotionProductId)){
			return ActResult.fail("请选择活动商品");
		}
		PromotionProduct promotionProduct = pps.getById(promotionProductId);
		if(StringUtils.isNullOrEmpty(promotionProduct)){
			return ActResult.fail("活动商品不存在");
		}
		Long startTime = promotionProduct.getJoinStart().getTime();
		redis.removeSet(loginUser.getId()+"_"+startTime, promotionProductId+"");
		Set<String> promotionProductIdSet = redis.getAllSet(loginUser.getId()+"_"+startTime);
		//如果不存在被关注的商品，将会删除推送对应的缓存信息
		if(promotionProductIdSet.isEmpty()){
			//删除通知redis  TODO
			redis.removeSet(RedisConstant.PROMOTIONPRODUCT_CARE+startTime, loginUser.getId()+"");
		}
		return ActResult.success("取消关注成功");
	}
}
