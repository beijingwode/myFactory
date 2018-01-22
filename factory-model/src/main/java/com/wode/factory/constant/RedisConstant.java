package com.wode.factory.constant;
/**
 * 常量类
 * redis的key
 * 
 * @author haisheng
 *
 */
public final class RedisConstant {
	/**
	 * 订阅频道 创建，更新，取消，支付，催促
	 */
	public final static String SUBSCRIBE_CHANNEL_ORDER_CREATE="SUBSCRIBE_CHANNEL_ORDER_CREATE";
	public final static String SUBSCRIBE_CHANNEL_ORDER_UPDATE="SUBSCRIBE_CHANNEL_ORDER_UPDATE";
	public final static String SUBSCRIBE_CHANNEL_ORDER_CANCEL="SUBSCRIBE_CHANNEL_ORDER_CANCEL";
	public final static String SUBSCRIBE_CHANNEL_ORDER_PAY="SUBSCRIBE_CHANNEL_ORDER_PAY";
	public final static String SUBSCRIBE_CHANNEL_ORDER_URGED="SUBSCRIBE_CHANNEL_ORDER_URGED";
	
	/**
	 * 订阅频道 微信相关
	 */
	public final static String SUBSCRIBE_CHANNEL_USER_BIND="SUBSCRIBE_CHANNEL_USER_BIND";
	
	/*
	 * 用户信息
	 */
	public final static String FACTORY_USER_MAP = "FACTORY_USER_MAP";
	
	/**
	 * 限购商品KEY
	 */
	public final static String GROUP_LIMIT_PRODUCT_PRE = "limit_product_";
    /**
	 * 商品KEY
	 */
	public final static String PRODUCT_PRE = "PRODUCT_";
	 
	/**
	 * 商品信息KEY
	 */
	public final static String PRODUCT_REDIS_INFO = "base";
	/**
	 * 商品图片KEY
	 */
	public final static String PRODUCT_REDIS_IMAGE = "image";
	/**
	 * 商品供应商KEY
	 */
	public final static String PRODUCT_REDIS_SUPPLIER = "supplier";
	/**
	 * 商品规格KEY
	 */
	public final static String PRODUCT_REDIS_SKU = "sku";
	/**
	 * sku库存Key
	 */
	public final static String REDIS_SKU_INVENTORY = "sku_";
	/**
	 * itemids 到sku
	 */
	public final static String REDIS_ITEMSIDS_SKU_MAP = "ITEMSID_MAP";
	
	/**
	 * 商品SKU阶梯价
	 */
	public final static String PRODUCT_REDIS_SKU_LADDER = "sku_ladder";
	
	/**
	 * 记录用户登录时购物车信息
	 */
	public final static String CART_REDIS = "cart";
	/**
	 * 记录用户未登录时购物车信息App用
	 */
	public final static String CART_APP = "cartApp";
	/**
	 * 记录商品SKU对应的商品ID
	 */
	public final static String PRODUCT_SPE_ID_FOR_PRO_ID = "skuId2ProuctId";
	/**
	 * 记录商品评论相关信息
	 */
	public final static String COMMENTS = "comments";	
    /**
	 * 商品KEY
	 */
	public final static String REDIS_USER_SHIPPING_ADDRESS = "USER_SHIPPING_ADDRESS_";
	/**
	 * 内购价厂家统计信息
	 */
	public final static String FLJ = "FLJ";
	
	public final static String HOT_SELL = "RECOMMEND_HOT_SELL_PRODUCT";
	
	/**
	 * 记录商品评论相关信息
	 */
	public final static String PUST_ORDER_URGED = "push_urged_delivery_";
	public final static String PUST_ORDER_SUPPLIER = "push_order_supplier_";
	
	//需要更新缓存索引的商品
	public final static String UPDATE_PRODUCT = "update_product";
	//库存变化的sku
	public final static String Inventory_CHANGE = "Inventory_CHANGE";
	//活动商品下单成功后，付款完成前记录订单生成时间
	public final static String PROMOTION_ORDERS = "Promotion_Orders";
	//活动商品关注
	public final static String PROMOTIONPRODUCT_CARE = "PromotionProduct_Care_";
	//抢券
	public final static String RECEIVE_SSS_CASH = "RECEIVE_SSS_CASH";
	//openapi 校验
    public final static String OPEN_API_CHECK = "OPEN_API_CHECK";
    
    // 公众号关注推送
    public final static String WX_OPEN_MESSAGE_MAP = "WX_OPEN_MESSAGE_MAP";
    
    // 商家绑定二维码JS最新版本号
    public final static String USER_SHARE_TARGET_JS_VERSION = "USER_SHARE_TARGET_JS_VERSION";
}
