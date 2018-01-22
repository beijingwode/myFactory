package com.wode.api.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.wode.api.util.IPUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.ClientAccessLog;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.Promotion;
import com.wode.factory.model.PromotionProduct;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsImageService;
import com.wode.factory.user.facade.ShippingFacade;
import com.wode.factory.user.model.AppCartItemVo;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.CartItem;
import com.wode.factory.user.query.PromotionProductQuery;
import com.wode.factory.user.service.CartService;
import com.wode.factory.user.service.ClientAccessLogService;
import com.wode.factory.user.service.InventoryService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.PromotionProductService;
import com.wode.factory.user.service.PromotionService;
import com.wode.factory.user.service.ShopService;
import com.wode.factory.user.vo.ProductSpecificationsVo;

/**
 * 购物车
 * 
 * @author 刘聪
 * @since 2015-06-24
 *
 */
@Controller
@RequestMapping("/cart")
public class CartController extends BaseController{

	@Qualifier("redis")
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private ProductService productService;
	
	@Qualifier("inventoryService")
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private PromotionProductService promotionProductService;
	
	@Autowired
	private PromotionService promotionService;
	
	@Qualifier("productSpecificationsService")
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
    @Autowired
    private ClientAccessLogService clientAccessLogService;
	@Autowired
	private ProductSpecificationsImageService  productSpecificationsImageService;
	@Autowired
	private ShippingFacade shippingFacade;
	private static final int CAR_REDIS_EXPTIME = 60 * 60 * 24 * 30 * 6;//redis 过期时间6个月
	
	//阶梯价格
    @Autowired
    private ProductLadderService productLadderService;
    
    @Autowired
    private ShopService shopService;
    
    @Autowired 
    private CartService cartService;

	/**
	 * 添加到购物车（同时适用于按“+”“-”号修改的购买数量）
	 * @param ticket
	 * @param specificationId SKUID
	 * @param quantity 购买数量
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public ActResult<Object> add(String ticket, Long specificationId,Integer quantity, String mobileId,HttpServletRequest request,Integer totalQuantity,String pageKey,String fromType) {
		return addOrUpdateNum("add", ticket, specificationId, quantity,
				mobileId,request,totalQuantity,pageKey,fromType);
	}

	@RequestMapping("/adds")
	@ResponseBody
	public ActResult<Object> adds(String ticket, String specificationIdAndQuantity, String mobileId,HttpServletRequest request,String pageKey,String fromType) {
		String[] productUseTick = specificationIdAndQuantity.split(",");
		// 更新购物车中选中商品的企业券使用量
		for (String str : productUseTick) {
			String[] strs = str.split("_");
			long skuId = Long.parseLong(strs[0]);
			Integer quantity =new Integer(strs[1]);
			try{
				newAddNum(ticket, skuId, quantity, mobileId, request, null,pageKey,fromType);
				//addOrUpdateNum("add", ticket, skuId, quantity,mobileId,request,null);
			} catch(Exception e) {
				
			}
		}
		return  ActResult.success(null);
	}
	
	/**
	 * 修改数量（仅适用于直接填写购买数量，不适用于按“+”“-”号修改的购买数量）
	 * @param ticket
	 * @param specificationId SKUID
	 * @param quantity 购买数量
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateNum")
	@ResponseBody
	public ActResult<Object> updateNum(String ticket, Long specificationId,
			Integer quantity, String mobileId,Integer isPromotion,HttpServletRequest request) {
		return addOrUpdateNum("updateNum", ticket, specificationId, quantity,
				mobileId,request,null,null,null);
	}
	
	/**
	 * 购物车列表
	 * @param model
	 * @param ticket
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	@ResponseBody
	public ActResult<Object> list(HttpServletRequest request, ModelMap model, String ticket,String mobileId){	
		String cartJson = "";
		Cart cart = null;
		//判断用户是否登录，若登录购物车从缓存中取，否则APP自取
		UserFactory user = getLoginUser(request);
		// 当用户未登录时,获取用户信息
		if(null == user){//未登录
			cartJson = redisUtil.getData(RedisConstant.CART_APP+"_"+mobileId);
			if(!StringUtils.isEmpty(cartJson)){
				cart = JsonUtil.getObject(cartJson, Cart.class);
				redisUtil.setData(RedisConstant.CART_APP+"_"+mobileId, JsonUtil.toJsonString(cart), CAR_REDIS_EXPTIME);
			}else{
				return ActResult.fail("");
			}
		}else{//用户登录
			cartJson = redisUtil.getData(RedisConstant.CART_REDIS+"_"+user.getId());
			if(!StringUtils.isEmpty(cartJson)){
				cart = JsonUtil.getObject(cartJson, Cart.class);
				redisUtil.setData(RedisConstant.CART_REDIS+"_"+user.getId(), JsonUtil.toJsonString(cart), CAR_REDIS_EXPTIME);
			}else{
				return ActResult.fail("");
			}
		} 
		String checkRet = checkProduct(cart);
		List<AppCartItemVo> list = new ArrayList<AppCartItemVo>();
		if(cart!=null){
			Map<Long, List<CartItem>> map = cart.groupBySupplier();
			//查询阶梯价格
			Map<String,String> ladderMap = new HashMap<String,String>();
			//定义商家返回的包邮信息
			Map<String, Object> freeMap = new HashMap<String, Object>();
			Iterator<Entry<Long, List<CartItem>>> it = map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<Long, List<CartItem>> entry = it.next();
				freeMap.put(""+entry.getKey(), shippingFacade.getFreeString(entry.getKey(), loginUser.getId()));
				AppCartItemVo av = new AppCartItemVo();
				av.setSupplierId(entry.getKey());
				av.setSupplierName(shopService.getShopBySupplierId(av.getSupplierId()).get(0).getShopname());
				av.setCartItemList(entry.getValue());
				String proJson = redisUtil.getMapData(RedisConstant.PRODUCT_PRE+entry.getValue().get(0).getProductId(), "base");
				if(!StringUtils.isEmpty(proJson)) {
					Product p  = JsonUtil.getObject(proJson, Product.class);
					if(p!=null) {
						av.setShopId(p.getShopId());
					}
				}
				for(CartItem ci : entry.getValue()){
					if(!StringUtils.isEmpty(ci.getPromotionId())){
						PromotionProductQuery query = new PromotionProductQuery();
						query.setStatus(2);
						query.setPromotionId(ci.getPromotionId());
						query.setSkuId(Long.parseLong(ci.getPartNumber()));
						PromotionProduct pp = promotionProductService
								.findByAttribute(query);
						if (pp != null) {
							// 活动价格
							ci.setPrice(pp.getPrice());
						}
					}
					ci.setIsLadder(false);
                	BigDecimal ladderPrice  = productLadderService.getPriceBySkuidAndPrice(new Long(ci.getPartNumber()), ci.getQuantity());
                	if(null != ladderPrice){
                		ci.setRealPrice(ladderPrice);
                		ci.setIsLadder(true);
                		ci.setMaxCompanyTicket(new BigDecimal("0.01"));
                	}
                    
					ladderMap.put(ci.getPartNumber(), productLadderService.getStringBySkuid(new Long(ci.getPartNumber())));
				}
				list.add(av);
			}
			//刷新购物车
			if(null == user){//未登录
				redisUtil.setData(RedisConstant.CART_APP+"_"+mobileId, JsonUtil.toJsonString(cart), CAR_REDIS_EXPTIME);
			}else{//用户登录
				redisUtil.setData(RedisConstant.CART_REDIS+"_"+user.getId(), JsonUtil.toJsonString(cart), CAR_REDIS_EXPTIME);
			} 
			model.addAttribute("freeMap", freeMap);
			//优惠信息
			model.addAttribute("ladderMap", ladderMap);
		}
		model.addAttribute("cart", list);
		model.addAttribute("checkRet", checkRet);
		return ActResult.success(model);
	}

	@RequestMapping(value = {"/page"})
    public ModelAndView page(HttpServletRequest request, ModelAndView model) {
		model.setViewName("cart");
		return model;
	}
	/**
	 * 删除购物车
	 * @param ticket
	 * @param specificationId SKUID
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ActResult<Object> delete(HttpServletRequest request,String ticket, String specificationId, String mobileId) {
		Cart cart = null;
		ActResult<Object> ret = new ActResult<Object>();
		ret.setSuccess(false);
		String cartJson = "";
		//判断用户是否登录，若登录购物车从缓存RedisConstant.CART_REDIS中取，否则从RedisConstant.CART_APP里面取
		UserFactory user = getLoginUser(request);
		// 当用户未登录时,获取用户信息
		if(null == user){//未登录
			cartJson = redisUtil.getData(RedisConstant.CART_APP+"_"+mobileId);
			if(!StringUtils.isEmpty(cartJson)){
				cart = JsonUtil.getObject(cartJson, Cart.class);
			}
			ret.setSuccess(cart.deleteItem(specificationId));//删除购物项
			if(cart.calculateTotalNumber() == 0){
				redisUtil.del(RedisConstant.CART_APP+"_"+mobileId);
			}else{
				redisUtil.setData(RedisConstant.CART_APP+"_"+mobileId, JsonUtil.toJsonString(cart), CAR_REDIS_EXPTIME);
			}
		}else{//用户登录
			cartJson = redisUtil.getData(RedisConstant.CART_REDIS+"_"+user.getId());
			if(!StringUtils.isEmpty(cartJson)){
				cart = JsonUtil.getObject(cartJson, Cart.class);
			}
			ret.setSuccess(cart.deleteItem(specificationId));//删除购物项
			if(cart.calculateTotalNumber() == 0){
				redisUtil.del(RedisConstant.CART_REDIS+"_"+user.getId());
			}else{
				redisUtil.setData(RedisConstant.CART_REDIS+"_"+user.getId(), JsonUtil.toJsonString(cart), CAR_REDIS_EXPTIME);
			}
		}
		if(ret.isSuccess()) {
			ret.setMsg("删除购物车成功");
		} else {
			ret.setMsg("删除购物车失败");
		}
		return ret;
	}
	
	/**
	 * 获取购物车商品数量
	 */
	@RequestMapping("getCartNum")
	@ResponseBody
	public ActResult<Object> getCartNum(HttpServletRequest request,
			ModelMap model, String ticket, String mobileId) {
		ActResult<Object> ret = new ActResult<Object>();
		String cartJson = "";
		Cart cart = null;
		int num = 0;
		List<CartItem> cartItems = null;
		// 判断用户是否登录，若登录购物车从缓存RedisConstant.CART_REDIS中取，否则从RedisConstant.CART_APP里面取
		UserFactory user = getLoginUser(request);
		if (null == user) {// 用户未登录
			cartJson = redisUtil.getData(RedisConstant.CART_APP + "_"+ mobileId);
			if (!StringUtils.isEmpty(cartJson)) {
				cart = JsonUtil.getObject(cartJson, Cart.class);
			}
		} else {
			cartJson = redisUtil.getData(RedisConstant.CART_REDIS + "_" + loginUser.getId());
			if (!StringUtils.isEmpty(cartJson)) {
				cart = JsonUtil.getObject(cartJson, Cart.class);
			}
		}
		if (null != cart) {
			num = cart.calculateTotalNumber();
			cartItems = cart.getAllItems();
		}
		model.addAttribute("number", num);
		model.addAttribute("cartItems", cartItems);
		ret.setData(model);
		return ret;
	}
	
	/**
	 * 添加购物项
	 * @param product 商品
	 * @param quantity 数量
	 */
	private void addCartItem(ActResult<Object> ret, Cart cart, CartItem cartItem, String flag, Integer totalQuantity,
			Product product, ProductSpecifications sku,UserFactory user) {
		
		String partNumber = cartItem.getPartNumber();
		int quantity = cartItem.getQuantity();
		Integer availableNumber = 0;
		// 返回前台的resultMap
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isEmpty(cartItem.getPromotionProductId()))
			availableNumber = inventoryService.getInventoryFromRedis(Long.parseLong(cartItem.getPartNumber()));// 可买数量
		else {
			Promotion promotion = promotionService.getById(cartItem.getPromotionId());
			ActResult<Object> ac = inventoryService.lockPromotionStock(cartItem.getPromotionProductId(),
					cartItem.getQuantity(), promotion);// 可买数量
			if (!ac.isSuccess()) {
				ret.setSuccess(false);
				ret.setMsg("库存不足");
				return;
			}
		}
		if (null == availableNumber || availableNumber < 1) {
			ret.setSuccess(false);
			ret.setMsg("库存不足");
			return;
		}
		if (cartItem.getQuantity() > availableNumber) {
			ret.setSuccess(false);
			ret.setMsg("库存不足");
			return;
		}

		CartItem inItem = cart.getItem(Long.valueOf(partNumber));// 购物车的产品项
		// 当为修改数量时
		if ((("updateNum").equals(flag)) && (inItem == null)) {
			ret.setSuccess(false);
			ret.setMsg("购物项不存在");
			return;
		}


		CartItem needSet = cartItem;
		if (inItem != null) {// 已购买
			// 库存检查
			if (("updateNum").equals(flag)) {
				if (quantity > availableNumber) {
					System.out.println("库存不足:" + inItem.getPartNumber() + "," + quantity + ">" + availableNumber);
					ret.setSuccess(false);
					ret.setMsg("库存不足件");
					return;
				} else {
					cart.addItem(cartItem);
					needSet = cartItem;
					ret.setSuccess(true);
				}
			} else {
				if ((inItem.getQuantity() + quantity) > availableNumber) {
					ret.setSuccess(false);
					ret.setMsg("库存不足");
					System.out.println("库存不足:" + inItem.getPartNumber() + "," + inItem.getQuantity() + " +" + quantity
							+ ">" + availableNumber);
					return;
				}
				if ((inItem.getQuantity() + quantity) > 0) {// 防止数量为负数
					inItem.setQuantity(inItem.getQuantity() + quantity);
					ret.setSuccess(true);
					needSet = inItem;
				}
			}
		} else {// 未购买
			cart.addItem(cartItem);
			needSet = cartItem;
			ret.setSuccess(true);
		}
		
		//员工特享商品
		Boolean isLadder = productSpecificationsService.resetPrice(sku, product, user,false,needSet.getQuantity());
		needSet.setMaxCompanyTicket(sku.getMaxFucoin().equals(null)?new BigDecimal(0):sku.getMaxFucoin());
		needSet.setPrice(sku.getPrice());
		needSet.setRealPrice(sku.getInternalPurchasePrice());//内购价
		needSet.setIsLadder(isLadder);
		cartItem=needSet;

		map.put("isLadder", isLadder);
		map.put("realPrice", needSet.getRealPrice());
		map.put("maxCompanyTicket", needSet.getMaxCompanyTicket());
		ret.setData(map);
	}

	/**
	 * 将规格JSON数据转换成 list
	 * @param specificationJson
	 * @return
	 */
	private List<String> getSpecificationList(String specificationJson){
		List<String> list =new ArrayList<String>();
		specificationJson = specificationJson.replace("{", "").replace("}", "").replace("\"", "").replace("\\", "");
		String [] strs = specificationJson.split(",");
		for(int i = 0; i < strs.length;i++){
			//list.add(strs[i].substring(0, strs[i].indexOf(":"))+"_"+strs[i].substring(strs[i].indexOf(":")+1));
			list.add(strs[i]);
		}
		return list;
	}
	
	/**
	 * 判断是否有商品价格变化、下架商品
	 * @param cart
	 * @return
	 */
	private String checkProduct(Cart cart) {
		if (null == cart) {
			return "";
		}
		StringBuffer sb = new StringBuffer();

		// 循环内部有删除操作必须用Iterator遍历
		Collections.sort(cart.getAllItems(), new Comparator<CartItem>() {
			@Override
			public int compare(CartItem arg0, CartItem arg1) {
				if (arg0.getTime() == null)
					return 1;
				if (arg1.getTime() == null)
					return -1;

				if (arg0.getTime() < arg1.getTime())
					return 1;
				return -1;
			}
		});

		// 循环内部有删除操作必须用Iterator遍历
		Iterator<CartItem> it1 = cart.getAllItems().iterator();
		while (it1.hasNext()) {
			CartItem item = it1.next();
			Long skuId = Long.parseLong(item.getPartNumber());
			ProductSpecificationsVo sku = productSpecificationsService.findByIdCache(skuId,
					item.getProductId().toString());

			Product p = (sku==null)?null:productService.findById(sku.getProductId(), false);
			if (p==null || sku == null || sku.getIsMarketable() != 1) {// 判断商品上下架，下架商品从购物车删除
				sb.append(item.getProductName() + " 商品已下架\n");
				it1.remove();
				continue;
			}
			
			if (!skuId.equals(sku.getId())) {
				item.setPartNumber(sku.getId() + "");
				item.setProductCode(sku.getProductCode());
				item.setPrice(sku.getPrice());
				item.setMaxCompanyTicket(sku.getMaxFucoin());
			}
			
			Integer stock = inventoryService.getInventoryFromRedis(Long.valueOf(item.getPartNumber()));
			stock = stock == null ? 0 : stock;
			if (item.getQuantity() + 1 > stock) {
				sb.append("商品'" + item.getProductName() + "'库存仅剩" + stock + "\r\n");
				System.out.println(sb);
				item.setQuantity(stock);
			}
			if(item.getQuantity()==0 && stock>0){//购物车某个商品库存为0时，购物车中商品数量为0，当商家库存恢复后，商品数量默认为1.
				item.setQuantity(1);
			}
			productSpecificationsService.resetPrice(sku,p, loginUser,false,item.getQuantity());
			BigDecimal a = item.getPrice().setScale(2, BigDecimal.ROUND_HALF_DOWN);
			BigDecimal b = sku.getPrice().setScale(2, BigDecimal.ROUND_HALF_DOWN);
			if (a.compareTo(b) != 0
					|| item.getMaxCompanyTicket().compareTo(sku.getMaxFucoin()) != 0) {// 判断价格，购物车价格和数据库价格不想等，取数据库价格
				sb.append(item.getProductName() + " 价格有变化\n");
				item.setPrice(sku.getPrice());
				item.setMaxCompanyTicket(sku.getMaxFucoin());
			}
			//这里重置下内购价
			item.setRealPrice(sku.getInternalPurchasePrice());
		}

		return sb.toString();
	}
	
	/**
	 * 添加到购物车、修改数量
	 */
	@SuppressWarnings("unchecked")
	public ActResult<Object> addOrUpdateNum(String flag, String ticket, Long specificationId,
			Integer quantity,String mobileId,HttpServletRequest request,Integer totalQuantity,String pageKey,String fromType) {
		
		
		Cart cart = new Cart();
		ActResult<Object> ret = new ActResult<Object>();
		if ((quantity == null || quantity.equals(0)) || (("updateNum").equals(flag) && quantity < 1)){
			return ActResult.fail("购买数量不正确");
		}
		if (specificationId == null){
			return ActResult.fail("商品编码不正确");
		}
		//商品
		Product product = productService.findBySku(specificationId);
		if(null == product || product.getIsMarketable() != 1){
			return ActResult.fail("不存在,或已下架");
		}
		String supplierJsonStr = redisUtil.getMapData(RedisConstant.PRODUCT_PRE+product.getId(), RedisConstant.PRODUCT_REDIS_SUPPLIER);
		//供应商
		JSONObject supplier = null;
		CartItem cartItem = null;
		
		cartItem = new CartItem();
		supplier = JSONObject.parseObject(supplierJsonStr);
		ProductSpecifications sku = productSpecificationsService.findByIdCache(specificationId,product.getId().toString());
//		if(!specificationId.equals(sku.getId())) {
//			specificationId = sku.getId();
//		}
		if(sku == null || sku.getIsDelete() == 1){
			return ActResult.fail("价格已失效");
		}
		//员工特享商品
		UserFactory user = getLoginUser(request);
		//productSpecificationsService.resetPrice(sku, product, user,false,null ==totalQuantity?quantity:totalQuantity);
		cartItem.setProductId(product.getId());
		cartItem.setProductName(product.getFullName());
		cartItem.setSaleKbn(product.getSaleKbn());
		cartItem.setImagePath(productSpecificationsImageService.findProductPicture(sku.getId(), product.getId()).get(0).getSource());
		cartItem.setSupplierId(product.getSupplierId());
		if(StringUtils.isEmpty(product.getShopname())) {
			cartItem.setSupplierName(product.getShopname());
		}
		if(StringUtils.isEmpty(cartItem.getSupplierName())) {
			cartItem.setSupplierName(shopService.getShopBySupplierId(product.getSupplierId()).get(0).getShopname());
		}
		cartItem.setPartNumber(sku.getId()+"");
		cartItem.setProductCode(sku.getProductCode());
		cartItem.setItemValues(sku.getItemValues());
		cartItem.setFreight(product.getCarriage());
		cartItem.setTime(System.currentTimeMillis());
		//增加企业券使用最高额度  2015-6-12
		cartItem.setQuantity(quantity);					//臨時設定
		cartItem.setPrice(sku.getPrice());				//臨時設定
		cartItem.setMaxCompanyTicket(sku.getMaxFucoin().equals(null)?new BigDecimal(0):sku.getMaxFucoin());	//臨時設定
		cartItem.setRealPrice(sku.getInternalPurchasePrice());//内购价	//臨時設定
		if(null != sku.getItemValues()&& !"".equals(sku.getItemValues())){
			String specificationJson = sku.getItemValues();
			cartItem.setSpecificationList(getSpecificationList(specificationJson));
		}
		if(!StringUtils.isEmpty(pageKey)){
			cartItem.setPageKey(pageKey);
		}
		cartItem.setFromType(fromType);
		String cartJson = "";
		// 当用户未登录时,获取设备信息
		if(null == user){//未登录
			cartJson = redisUtil.getData(RedisConstant.CART_APP+"_"+mobileId);
			if(!StringUtils.isEmpty(cartJson)){
				//把数据写到购物车
				cart = JsonUtil.getObject(cartJson, Cart.class);
			}
			addCartItem(ret, cart, cartItem, flag, totalQuantity,product,sku, user);//添加购物车
			redisUtil.setData(RedisConstant.CART_APP+"_"+mobileId, JsonUtil.toJsonString(cart), CAR_REDIS_EXPTIME);
		}else{//用户登录
			cartJson = redisUtil.getData(RedisConstant.CART_REDIS+"_"+user.getId());
			if(!StringUtils.isEmpty(cartJson)){
				cart = JsonUtil.getObject(cartJson, Cart.class);
			}
			addCartItem(ret, cart, cartItem, flag, totalQuantity,product, sku,user);//添加购物车
			redisUtil.setData(RedisConstant.CART_REDIS+"_"+user.getId(), JsonUtil.toJsonString(cart), CAR_REDIS_EXPTIME);
		}
		// 当添加购物项成功时
		if(("add").equals(flag) && ret.isSuccess()) {
			ret.setMsg("添加购物车成功");
			clientAccessLogService.saveProduct(ClientAccessLog.ACCESS_TYPE_CART, sku.getId(), sku.getProductName()+"："+sku.getItemnames(),user==null?null:user.getId(),user,sku.getProductId(),product,mobileId,IPUtils.getClientAddress(request));
		}
		return ret;
	}
	

	@RequestMapping("activityLog")
	public void activityLog(String app,String url,HttpServletRequest request,HttpServletResponse response) throws Exception  {
		//员工特享商品
		UserFactory user = getLoginUser(request);
		clientAccessLogService.saveActivity(app, url, user==null?null:user.getId(),user, null,IPUtils.getClientAddress(request));		
	}
	
	@RequestMapping("/newAdd")
	@ResponseBody
	public ActResult<Object> newAdd(String ticket, Long specificationId,Integer quantity, String mobileId,HttpServletRequest request,Integer totalQuantity,String pageKey,String fromType) {
		return newAddNum(ticket, specificationId, quantity,
				mobileId,request,totalQuantity,pageKey,fromType);
	}

	private ActResult<Object> newAddNum(String ticket, Long specificationId, Integer quantity,
			String mobileId, HttpServletRequest request, Integer totalQuantity, String pageKey,String fromType) {
		Cart cart = new Cart();
		ActResult<Object> ret = new ActResult<Object>();
		if ((quantity == null || quantity.equals(0))){
			return ActResult.fail("购买数量不正确");
		}
		if (specificationId == null){
			return ActResult.fail("商品编码不正确");
		}
		//商品
		Product product = productService.findBySku(specificationId);
		if(null == product || product.getIsMarketable() != 1){
			return ActResult.fail("不存在,或已下架");
		}
		String supplierJsonStr = redisUtil.getMapData(RedisConstant.PRODUCT_PRE+product.getId(), RedisConstant.PRODUCT_REDIS_SUPPLIER);
		//供应商
		JSONObject supplier = null;
		CartItem cartItem = null;
		cartItem = new CartItem();
		supplier = JSONObject.parseObject(supplierJsonStr);
		ProductSpecifications sku = productSpecificationsService.findByIdCache(specificationId,product.getId().toString());
		if(sku == null || sku.getIsDelete() == 1){
			return ActResult.fail("价格已失效");
		}
		//员工特享商品
		UserFactory user = getLoginUser(request);
		boolean isLadder = productSpecificationsService.resetPrice(sku, product, user,false,null ==totalQuantity?quantity:totalQuantity);
		cartItem.setProductId(product.getId());
		cartItem.setProductName(product.getFullName());
		cartItem.setSaleKbn(product.getSaleKbn());
		cartItem.setImagePath(productSpecificationsImageService.findProductPicture(sku.getId(), product.getId()).get(0).getSource());
		cartItem.setSupplierId(product.getSupplierId());
		if(StringUtils.isEmpty(product.getShopname())) {
			cartItem.setSupplierName(product.getShopname());
		}
		if(StringUtils.isEmpty(cartItem.getSupplierName())) {
			cartItem.setSupplierName(shopService.getShopBySupplierId(product.getSupplierId()).get(0).getShopname());
		}
		cartItem.setPartNumber(sku.getId()+"");
		cartItem.setProductCode(sku.getProductCode());
		cartItem.setItemValues(sku.getItemValues());
		cartItem.setFreight(product.getCarriage());
		cartItem.setTime(System.currentTimeMillis());
		//增加企业券使用最高额度  2015-6-12
		cartItem.setMaxCompanyTicket(sku.getMaxFucoin().equals(null)?new BigDecimal(0):sku.getMaxFucoin());
		cartItem.setQuantity(quantity);
		cartItem.setPrice(sku.getPrice());
		cartItem.setRealPrice(sku.getInternalPurchasePrice());//内购价
		cartItem.setIsLadder(isLadder);
		
		if(null != sku.getItemValues()&& !"".equals(sku.getItemValues())){
			String specificationJson = sku.getItemValues();
			cartItem.setSpecificationList(getSpecificationList(specificationJson));
		}
		if(!StringUtils.isEmpty(pageKey)) {
			cartItem.setPageKey(pageKey);
		}
		if(!StringUtils.isEmpty(fromType)) {
			cartItem.setFromType(fromType);
		}
		String cartJson = "";
		// 当用户未登录时,获取设备信息
		if(null == user){//未登录
			cartJson = redisUtil.getData(RedisConstant.CART_APP+"_"+mobileId);
			if(!StringUtils.isEmpty(cartJson)){
				//把数据写到购物车
				cart = JsonUtil.getObject(cartJson, Cart.class);
			}
			cartService.newAddCartItem(ret, cart, cartItem);//添加购物车
			redisUtil.setData(RedisConstant.CART_APP+"_"+mobileId, JsonUtil.toJsonString(cart), CAR_REDIS_EXPTIME);
		}else{//用户登录
			cartJson = redisUtil.getData(RedisConstant.CART_REDIS+"_"+user.getId());
			if(!StringUtils.isEmpty(cartJson)){
				cart = JsonUtil.getObject(cartJson, Cart.class);
			}
			cartService.newAddCartItem(ret, cart, cartItem);//添加购物车
			redisUtil.setData(RedisConstant.CART_REDIS+"_"+user.getId(), JsonUtil.toJsonString(cart), CAR_REDIS_EXPTIME);
		}
		return ret;
	}
	
}