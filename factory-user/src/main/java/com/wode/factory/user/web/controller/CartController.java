package com.wode.factory.user.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wode.common.constant.UserConstant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.ClientAccessLog;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.Promotion;
import com.wode.factory.model.ShippingAddress;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsImageService;
import com.wode.factory.user.facade.ShippingFacade;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.CartItem;
import com.wode.factory.user.service.CartService;
import com.wode.factory.user.service.ClientAccessLogService;
import com.wode.factory.user.service.InventoryService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.PromotionService;
import com.wode.factory.user.service.RecommendProductService;
import com.wode.factory.user.service.ShippingAddressService;
import com.wode.factory.user.service.ShopService;
import com.wode.factory.user.util.CartUtil;
import com.wode.factory.user.util.CookieUtils;
import com.wode.factory.user.util.IPUtils;
import com.wode.factory.user.util.SessonRedisUtil;
import com.wode.factory.user.util.ShippingUtil;
import com.wode.factory.user.vo.ProductSpecificationsVo;

/**
 * 购物车
 *
 * @author zhengxiongwu
 * @updata guziye : 增加企业券使用最高额度
 */
@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {

	@Qualifier("redis")
	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private ProductService productService;

	@Qualifier("inventoryService")
	@Autowired
	private InventoryService inventoryService;

	@Qualifier("productSpecificationsService")
	@Autowired
	private ProductSpecificationsService productSpecificationsService;

	@Autowired
	private RecommendProductService recommendService;

	@Autowired
	private PromotionService promotionService;

	@Qualifier("shippingAddressService")
	@Autowired
	private ShippingAddressService shippingAddressService;
	@Autowired
	private ShippingFacade shippingFacade;
	@Autowired
	private ClientAccessLogService clientAccessLogService;
	// 运费工具类
	@Autowired
	private ShippingUtil shippingUtil;

	@Autowired
	private ProductSpecificationsImageService productSpecificationsImageService;

	// 购物车工具类
	@Autowired
	private CartUtil cartUtil;
	// 阶梯价格
	@Autowired
	private ProductLadderService productLadderService;

	@Autowired
	private CartService cartService;
	
	@Autowired
    private ShopService shopService;

	private static final int CAR_COOKIE_EXPTIME = 60 * 60 * 24 * 30;// cookie 过期时间1个月
	private static final String CAR_COOKIE_PATH = "/";
	public static final int CAR_REDIS_EXPTIME = 60 * 60 * 24 * 30 * 6;// redis 过期时间6个月

	@RequestMapping(value = { "/add" })
	@ResponseBody
	public ActResult<Object> add(Long partNumber, Integer quantity, Integer totalQuantity, String pageKey,String fromType,HttpServletRequest request,
			HttpServletResponse response) {
		Cart cart = new Cart();
		UserFactory user = null;
		ActResult<Object> ret = new ActResult<Object>();
		ret.setSuccess(false);
		if (quantity == null) {
			ret.setMsg("购买数量不正确");
			return ret;
		}

		if (partNumber == null) {
			ret.setMsg("商品编码不正确");
			return ret;
		}

		Product product = productService.findBySku(partNumber);// 商品
		if (null == product || product.getIsMarketable() != 1) {
			return ActResult.fail("不存在,或已下架");
		}

		// 判断用户是否登录，若登录购物车从缓存中取，否则从cookie里面取
		user = getUser(request, response);
		// 查询企业限购
		if (user != null && productService.checkProductLimit(product, user)) {
			return ActResult.fail(product.getName() + "只允许企业级用户购买");
		}
		ShippingAddress defaultAddr = null;
		// 加载常用地址
		if (user != null) {
			List<ShippingAddress> shippingAddressList = shippingAddressService.findByUserId(user.getId());
			if (shippingAddressList != null && !shippingAddressList.isEmpty()) {
				defaultAddr = shippingAddressList.get(0);
			}
		}
		String supplierJsonStr = redisUtil.getMapData(RedisConstant.PRODUCT_PRE + product.getId(),
				RedisConstant.PRODUCT_REDIS_SUPPLIER);

		JSONObject supplier = null;// 供应商
		List<ProductSpecifications> skus = productService.findSku(product.getId());
		CartItem cartItem = new CartItem();
		supplier = JSONObject.parseObject(supplierJsonStr);
		ProductSpecifications sku = new ProductSpecifications();
		sku.setId(partNumber);
		int index = skus.indexOf(sku);

		if (index > -1) {
			sku = skus.get(index);
		} else {
			return ActResult.fail("价格已失效");
		}
		cartItem.setProductId(product.getId());
		cartItem.setProductName(product.getFullName());
		cartItem.setSaleKbn(product.getSaleKbn());
		cartItem.setImagePath(
				productSpecificationsImageService.findProductPicture(sku.getId(), product.getId()).get(0).getSource());
		cartItem.setSupplierId(product.getSupplierId());
		if(StringUtils.isEmpty(product.getShopname())) {
			cartItem.setSupplierName(product.getShopname());
		}
		if(StringUtils.isEmpty(cartItem.getSupplierName())) {
			cartItem.setSupplierName(shopService.getShopBySupplierId(product.getSupplierId()).get(0).getShopname());
		}
		cartItem.setPartNumber(partNumber + "");
		cartItem.setProductCode(sku.getProductCode());
		cartItem.setItemValues(sku.getItemValues());
		cartItem.setTime(System.currentTimeMillis());
		
		cartItem.setQuantity(quantity);			// 临时设定
		cartItem.setPrice(sku.getPrice());
		cartItem.setMaxCompanyTicket(sku.getMaxFucoin());
		// 内购价
		cartItem.setRealPrice(sku.getInternalPurchasePrice());
		BigDecimal freight = shippingFacade.chkLimitCntAndArea(product, quantity,
				defaultAddr == null ? null : defaultAddr.getAid(), null, (user != null ? user.getId() : null), "0");
		if (freight.compareTo(new BigDecimal(9999)) == 0) {
			return ActResult.fail("已超过限购数量,不能购买该商品了。或者减少购买数量");
		} /*
			 * else if(freight.compareTo(new BigDecimal(8888))==0){ return
			 * ActResult.fail("配送不到指定收货地址,不能购买该商品了，或者选择其他收货地址"); }
			 */
		cartItem.setFreight(freight);
		//设置来自页面
		cartItem.setPageKey(StringUtils.isEmpty(pageKey)?"cart":pageKey);
		//设置来自方式
		cartItem.setFromType(fromType);
		
		if (null != sku.getItemValues() && !"".equals(sku.getItemValues())) {
			String specificationJson = sku.getItemValues();
			cartItem.setSpecificationList(getSpecificationList(specificationJson));
		}

		String cartJson = "";
		// 判断用户是否登录，若登录购物车从缓存中取，否则从cookie里面取
		if (null == user) {// 未登录
			String uuid = CookieUtils.getUUID(request, response);
			String value = SessonRedisUtil.getData(UserConstant.CART_COOKIE + uuid, redisUtil);
			if (value != null && !value.trim().equals("") && !value.trim().equals("null")) {
				// 把cookie 写到购物车
				cart = JsonUtil.getObject(value, Cart.class);
			}
			cartItem= addCartItem(ret, cart, cartItem, "",product,sku,user);// 添加购物车
			SessonRedisUtil.setData(UserConstant.CART_COOKIE + uuid, JsonUtil.toJsonString(cart), redisUtil,
					CAR_COOKIE_EXPTIME);
		} else {// 用户登录，清空购物车cookie
			cartJson = redisUtil.getData(RedisConstant.CART_REDIS + "_" + user.getId());
			if (null != cartJson && !cartJson.trim().equals("") && !cartJson.trim().equals("null")) {
				cart = JsonUtil.getObject(cartJson, Cart.class);
			}
			cartItem= addCartItem(ret, cart, cartItem, "",product,sku,user);// 添加购物车
			redisUtil.setData(RedisConstant.CART_REDIS + "_" + user.getId(), JsonUtil.toJsonString(cart),
					CAR_REDIS_EXPTIME);
		}

		clientAccessLogService.saveProduct(ClientAccessLog.ACCESS_TYPE_CART, partNumber,
				sku.getProductName() + "：" + sku.getItemnames(), user == null ? null : user.getId(), user,
				sku.getProductId(), product, CookieUtils.getUUID(request, response), IPUtils.getClientAddress(request));

		JSONObject obj = new JSONObject();
		obj.put("totalNumber", cart.calculateTotalNumber());
		obj.put("totalPrice", cart.calculateTotalRealPrice());
		List<Product> list = recommendService.findRecommendByCate(product.getCategoryId());
		if (list.size() > 4) {
			list = list.subList(0, 4);
		}
		obj.put("perhapsNeedProduct", list);
		obj.put("realPrice", cartItem.getRealPrice());
		if (cartItem.getIsLadder()) {
			obj.put("isLadder", cartItem.getIsLadder());
		}
		obj.put("maxCompanyTicket", cartItem.getMaxCompanyTicket());
		ret.setData(obj);
		return ret;
	}

	@RequestMapping(value = { "/list" }, method = { RequestMethod.GET })
	public String list(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String cartJson = "";
		Cart cart = null;
		UserFactory user = null;
		// 判断用户是否登录，若登录购物车从缓存中取，否则从cookie里面取
		user = getUser(request, response);
		if (null == user) {// 未登录
			String uuid = CookieUtils.getUUID(request, response);
			String value = SessonRedisUtil.getData(UserConstant.CART_COOKIE + uuid, redisUtil);
			if (value != null && !value.trim().equals("") && !value.trim().equals("null")) {
				// 把cookie 写到购物车
				cart = JsonUtil.getObject(value, Cart.class);
			} else {
				List<Product> productList = recommendService.random();
				model.addAttribute("productList", productList);
				// model.addAttribute("user", user);
				return "cart_empty";
			}
		} else {// 用户登录，清空购物车cookie
			cartJson = redisUtil.getData(RedisConstant.CART_REDIS + "_" + user.getId());
			if (null != cartJson && !cartJson.trim().equals("") && !cartJson.trim().equals("null")) {
				cart = JsonUtil.getObject(cartJson, Cart.class);
			} else {
				List<Product> productList = recommendService.random();
				model.addAttribute("productList", productList);
				// model.addAttribute("user", user);
				return "cart_empty";
			}
		}
		String checkRet = checkProduct(cart, user);
		if (null == user) {// 未登录
			String uuid = CookieUtils.getUUID(request, response);
			SessonRedisUtil.setData(UserConstant.CART_COOKIE + uuid, JsonUtil.toJsonString(cart), redisUtil,
					CAR_COOKIE_EXPTIME);
		} else {// 用户登录
			redisUtil.setData(RedisConstant.CART_REDIS + "_" + user.getId(), JsonUtil.toJsonString(cart),
					CAR_REDIS_EXPTIME);
		}
		// 查询阶梯价格
		Map<String, String> ladderMap = new HashMap<String, String>();
		// 查询库存
		Map<Long, List<CartItem>> map = cart.groupBySupplier();
		Iterator<Map.Entry<Long, List<CartItem>>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Long, List<CartItem>> entry = it.next();
			List<CartItem> list = entry.getValue();
			for (CartItem cartItem : list) {
				if (!StringUtils.isEmpty(cartItem.getPromotionId())) {
					String key = String.valueOf(cartItem.getPromotionId() + "_" + cartItem.getPartNumber());
					try {
						Map<String, String> promotionMap = redisUtil.getMap(key);
						Integer locked = Integer.valueOf(promotionMap.get("locked"));
						Integer stock = Integer.valueOf(promotionMap.get("stock"));
						cartItem.setStock(stock - locked);
					} catch (Exception e) {
						e.printStackTrace();
						cartItem.setStock(0);
					}
				} else {
					Integer stock = inventoryService.getInventoryFromRedis(Long.valueOf(cartItem.getPartNumber()));
					stock = stock == null ? 0 : stock;
					if (stock < cartItem.getQuantity()) {
						cartItem.setQuantity(stock);
					}
					cartItem.setStock(stock);
				}
				ProductSpecifications sku = productSpecificationsService
						.findByIdCache(new Long(cartItem.getPartNumber()), cartItem.getProductId() + "");
				Product product = productService.findById(cartItem.getProductId(), false);
				boolean isLadder = productSpecificationsService.resetPrice(sku, product, user, false,
						cartItem.getQuantity());
				cartItem.setRealPrice(sku.getInternalPurchasePrice());
				cartItem.setPrice(sku.getPrice());
				cartItem.setCompanyTicket(sku.getMaxFucoin());
				cartItem.setIsLadder(isLadder);
				cartItem.setSupplierName(shopService.getShopBySupplierId(product.getSupplierId()).get(0).getShopname());
				ladderMap.put(cartItem.getPartNumber(),
						productLadderService.getStringBySkuid(new Long(cartItem.getPartNumber())));
			}
		}
		model.addAttribute("checkRet", checkRet);
		Map<Long, List<CartItem>> supplierCartList = cart.groupBySupplier();
		model.addAttribute("cart", cartUtil.setCartItemShopId(supplierCartList));
		model.addAttribute("user", user);
		model.addAttribute("ladderMap", ladderMap);
		model.addAttribute("freeMap", shippingUtil.getFreeString(supplierCartList, user != null ? user.getId() : null));
		return "cart";
	}

	@RequestMapping(value = { "/delete" })
	@ResponseBody
	public ActResult<Object> delete(String partNumber, HttpServletRequest request, HttpServletResponse response) {
		Cart cart = null;
		UserFactory user = null;
		ActResult<Object> ret = new ActResult<Object>();
		ret.setSuccess(false);

		String cartJson = "";
		// 判断用户是否登录，若登录购物车从缓存中取，否则从cookie里面取
		user = getUser(request, response);
		if (null == user) {// 未登录
			String uuid = CookieUtils.getUUID(request, response);
			String value = SessonRedisUtil.getData(UserConstant.CART_COOKIE + uuid, redisUtil);
			if (value != null && !value.trim().equals("") && !value.trim().equals("null")) {
				// 把cookie 写到购物车
				cart = JsonUtil.getObject(value, Cart.class);
			}
			if (cart != null) {
				ret.setSuccess(cart.deleteItem(partNumber));// 删除购物项
				if (cart.calculateTotalNumber() == 0) {
					SessonRedisUtil.delData(UserConstant.CART_COOKIE + uuid, redisUtil);
				} else {
					SessonRedisUtil.setData(UserConstant.CART_COOKIE + uuid, JsonUtil.toJsonString(cart), redisUtil,
							CAR_COOKIE_EXPTIME);
				}
			} else {
				SessonRedisUtil.setData(UserConstant.CART_COOKIE + uuid, "", redisUtil, CAR_COOKIE_EXPTIME);
			}
		} else {// 用户登录，清空购物车cookie
			cartJson = redisUtil.getData(RedisConstant.CART_REDIS + "_" + user.getId());
			if (null != cartJson && !cartJson.trim().equals("") && !cartJson.trim().equals("null")) {
				cart = JsonUtil.getObject(cartJson, Cart.class);
			}
			if (cart != null) {
				ret.setSuccess(cart.deleteItem(partNumber));// 删除购物项
				if (cart.calculateTotalNumber() == 0) {
					redisUtil.del(RedisConstant.CART_REDIS + "_" + user.getId());
				} else {
					redisUtil.setData(RedisConstant.CART_REDIS + "_" + user.getId(), JsonUtil.toJsonString(cart),
							CAR_REDIS_EXPTIME);
				}
			} else {
				redisUtil.del(RedisConstant.CART_REDIS + "_" + user.getId());
			}
		}
		JSONObject obj = new JSONObject();
		obj.put("totalNumber", cart.calculateTotalNumber());
		obj.put("totalPrice", cart.calculateTotalPrice());
		ret.setData(obj);
		return ret;
	}

	/**
	 * 添加购物项
	 *
	 */
	private CartItem addCartItem(ActResult<Object> ret, Cart cart, CartItem cartItem, String flag,Product product,ProductSpecifications sku,UserFactory user) {
		String partNumber = cartItem.getPartNumber();
		int quantity = cartItem.getQuantity();
		Integer availableNumber = 0;
		if (StringUtils.isNullOrEmpty(cartItem.getPromotionProductId()))
			availableNumber = inventoryService.getInventoryFromRedis(Long.parseLong(cartItem.getPartNumber()));// 可买数量
		else {
			Promotion promotion = promotionService.getById(cartItem.getPromotionId());
			ActResult<Object> ac = inventoryService.lockPromotionStock(cartItem.getPromotionProductId(),
					cartItem.getQuantity(), promotion);// 可买数量
			if (!ac.isSuccess()) {
				ret.setSuccess(false);
				ret.setMsg("库存不足");
				return cartItem;
			}
		}
		if (null == availableNumber || availableNumber < 1) {
			ret.setSuccess(false);
			ret.setMsg("库存不足");
			return cartItem;
		}
		if (cartItem.getQuantity() > availableNumber) {
			ret.setSuccess(false);
			ret.setMsg("库存不足");
			return cartItem;
		}

		CartItem inItem = cart.getItem(Long.valueOf(partNumber));// 购物车的产品项
		// 当为修改数量时
		if ((("updateNum").equals(flag)) && (inItem == null)) {
			ret.setSuccess(false);
			ret.setMsg("购物项不存在");
			return cartItem;
		}

		CartItem needSet = cartItem;
		if (inItem != null) {// 已购买
			// 库存检查
			if (("updateNum").equals(flag)) {
				if (quantity > availableNumber) {
					ret.setSuccess(false);
					ret.setMsg("库存不足");
					return cartItem;
				} else {
					cart.addItem(cartItem);
					needSet = cartItem;
					ret.setSuccess(true);
				}
			} else {
				if ((inItem.getQuantity() + quantity) > availableNumber) {
					ret.setSuccess(false);
					ret.setMsg("库存不足");
					return cartItem;
				}
				if ((inItem.getQuantity() + quantity) > 0) {// 防止数量为负数
					inItem.setQuantity(inItem.getQuantity() + quantity);
					needSet = inItem;
					ret.setSuccess(true);
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
		return needSet;
	}

	/**
	 * 将规格JSON数据转换成 list
	 *
	 * @param specificationJson
	 * @return
	 */
	private List<String> getSpecificationList(String specificationJson) {
		List<String> list = new ArrayList<String>();
		specificationJson = specificationJson.replace("{", "").replace("}", "").replace("\"", "").replace("\\", "");
		String[] strs = specificationJson.split(",");
		for (int i = 0; i < strs.length; i++) {
			// list.add(strs[i].substring(0,
			// strs[i].indexOf(":"))+"_"+strs[i].substring(strs[i].indexOf(":")+1));
			list.add(strs[i]);
		}
		return list;
	}

	// 判断是否有商品价格变化、下架商品
	private String checkProduct(Cart cart, UserFactory user) {
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

		Iterator<CartItem> it = cart.getAllItems().iterator();
		while (it.hasNext()) {
			CartItem item = it.next();
			Long skuId = Long.parseLong(item.getPartNumber());
			ProductSpecificationsVo sku = productSpecificationsService
					.findByIdCache(Long.parseLong(item.getPartNumber()), item.getProductId().toString());
			Product p = (sku == null) ? null : productService.findById(sku.getProductId(), false);
			if (p == null || sku == null || sku.getIsMarketable() != 1) {// 判断商品上下架，下架商品从购物车删除
				sb.append(item.getProductName() + " 商品已下架\n");
				it.remove();
				continue;
			}

			if (!skuId.equals(sku.getId())) {
				item.setPartNumber(sku.getId() + "");
				item.setProductCode(sku.getProductCode());
				item.setPrice(sku.getPrice());
				item.setMaxCompanyTicket(sku.getMaxFucoin());
			}
			productSpecificationsService.resetPrice(sku, p, user, false, item.getQuantity());
			// 内购券是否更新
			if (!sku.getMaxFucoin().equals(item.getMaxCompanyTicket())) {
				item.setMaxCompanyTicket(sku.getMaxFucoin());
			}
			Integer stock = inventoryService.getInventoryFromRedis(Long.valueOf(item.getPartNumber()));
			stock = stock == null ? 0 : stock;
			if (item.getQuantity() + 1 > stock) {
				sb.append("商品'" + item.getProductName() + "'库存仅剩" + stock + "\r\n");
				item.setQuantity(stock);
			}
			BigDecimal a = item.getPrice().setScale(2, BigDecimal.ROUND_HALF_DOWN);
			BigDecimal b = sku.getPrice().setScale(2, BigDecimal.ROUND_HALF_DOWN);
			if (a.compareTo(b) != 0 || item.getMaxCompanyTicket().compareTo(sku.getMaxFucoin()) != 0) {// 判断价格，购物车价格和数据库价格不想等，取数据库价格
				sb.append(item.getProductName() + " 价格有变化\n");
				item.setPrice(sku.getPrice());
				item.setMaxCompanyTicket(sku.getMaxFucoin());
			}
			// 装填内购价
			item.setRealPrice(sku.getInternalPurchasePrice());
		}

		return sb.toString();
	}

	@RequestMapping(value = { "/newAdd" })
	@ResponseBody
	public ActResult<Object> newAdd(Long partNumber, Integer quantity, Integer totalQuantity,String pageKey,String fromType,
			HttpServletRequest request, HttpServletResponse response) {
		Cart cart = new Cart();
		UserFactory user = null;
		ActResult<Object> ret = new ActResult<Object>();
		ret.setSuccess(false);
		if (quantity == null || quantity <= 0) {
			ret.setMsg("购买数量不正确");
			return ret;
		}
		if (partNumber == null) {
			ret.setMsg("商品编码不正确");
			return ret;
		}
		Product product = productService.findBySku(partNumber);// 商品
		if (null == product || product.getIsMarketable() != 1) {
			return ActResult.fail("不存在,或已下架");
		}
		// 判断用户是否登录，若登录购物车从缓存中取，否则从cookie里面取
		user = getUser(request, response);
		// 查询企业限购
		if (user != null && productService.checkProductLimit(product, user)) {
			return ActResult.fail(product.getName() + "只允许企业级用户购买");
		}
		ShippingAddress defaultAddr = null;
		// 加载常用地址
		if (user != null) {
			List<ShippingAddress> shippingAddressList = shippingAddressService.findByUserId(user.getId());
			if (shippingAddressList != null && !shippingAddressList.isEmpty()) {
				defaultAddr = shippingAddressList.get(0);
			}
		}
		String supplierJsonStr = redisUtil.getMapData(RedisConstant.PRODUCT_PRE + product.getId(),
				RedisConstant.PRODUCT_REDIS_SUPPLIER);

		JSONObject supplier = null;// 供应商
		List<ProductSpecifications> skus = productService.findSku(product.getId());
		CartItem cartItem = new CartItem();
		supplier = JSONObject.parseObject(supplierJsonStr);
		ProductSpecifications sku = new ProductSpecifications();
		sku.setId(partNumber);
		int index = skus.indexOf(sku);

		if (index > -1) {
			sku = skus.get(index);
		} else {
			return ActResult.fail("价格已失效");
		}
		boolean isLadder = productSpecificationsService.resetPrice(sku, product, user, false, quantity);
		cartItem.setProductId(product.getId());
		cartItem.setProductName(product.getFullName());
		cartItem.setSaleKbn(product.getSaleKbn());
		cartItem.setImagePath(
				productSpecificationsImageService.findProductPicture(sku.getId(), product.getId()).get(0).getSource());
		cartItem.setSupplierId(product.getSupplierId());
		if(StringUtils.isEmpty(product.getShopname())) {
			cartItem.setSupplierName(product.getShopname());
		}
		if(StringUtils.isEmpty(cartItem.getSupplierName())) {
			cartItem.setSupplierName(shopService.getShopBySupplierId(product.getSupplierId()).get(0).getShopname());
		}
		cartItem.setPartNumber(partNumber + "");
		cartItem.setProductCode(sku.getProductCode());
		cartItem.setItemValues(sku.getItemValues());
		cartItem.setTime(System.currentTimeMillis());
		// 内购价
		cartItem.setRealPrice(sku.getInternalPurchasePrice());
		BigDecimal freight = shippingFacade.chkLimitCntAndArea(product, quantity,
				defaultAddr == null ? null : defaultAddr.getAid(), null, (user != null ? user.getId() : null), "0");
		if (freight.compareTo(new BigDecimal(9999)) == 0) {
			return ActResult.fail("已超过限购数量,不能购买该商品了。或者减少购买数量");
		}
		cartItem.setFreight(freight);
		// 增加企业券使用最高额度 2015-6-12
		cartItem.setMaxCompanyTicket(sku.getMaxFucoin());

		cartItem.setQuantity(quantity);
		cartItem.setPrice(sku.getPrice());
		cartItem.setIsLadder(isLadder);

		if (null != sku.getItemValues() && !"".equals(sku.getItemValues())) {
			String specificationJson = sku.getItemValues();
			cartItem.setSpecificationList(getSpecificationList(specificationJson));
		}
		cartItem.setPageKey(pageKey);
		cartItem.setFromType(fromType);
		String cartJson = "";
		// 判断用户是否登录，若登录购物车从缓存中取，否则从cookie里面取
		if (null == user) {// 未登录
			String uuid = CookieUtils.getUUID(request, response);
			String value = SessonRedisUtil.getData(UserConstant.CART_COOKIE + uuid, redisUtil);
			if (value != null && !value.trim().equals("") && !value.trim().equals("null")) {
				// 把cookie 写到购物车
				cart = JsonUtil.getObject(value, Cart.class);
			}
			cartService.newAddCartItem(ret, cart, cartItem);// 添加购物车
			SessonRedisUtil.setData(UserConstant.CART_COOKIE + uuid, JsonUtil.toJsonString(cart), redisUtil,
					CAR_COOKIE_EXPTIME);
		} else {// 用户登录，清空购物车cookie
			cartJson = redisUtil.getData(RedisConstant.CART_REDIS + "_" + user.getId());
			if (null != cartJson && !cartJson.trim().equals("") && !cartJson.trim().equals("null")) {
				cart = JsonUtil.getObject(cartJson, Cart.class);
			}
			cartService.newAddCartItem(ret, cart, cartItem);// 添加购物车
			redisUtil.setData(RedisConstant.CART_REDIS + "_" + user.getId(), JsonUtil.toJsonString(cart),
					CAR_REDIS_EXPTIME);
		}

		clientAccessLogService.saveProduct(ClientAccessLog.ACCESS_TYPE_CART, partNumber,
				sku.getProductName() + "：" + sku.getItemnames(), user == null ? null : user.getId(), user,
				sku.getProductId(), product, CookieUtils.getUUID(request, response), IPUtils.getClientAddress(request));
		return ret;
	}
}
