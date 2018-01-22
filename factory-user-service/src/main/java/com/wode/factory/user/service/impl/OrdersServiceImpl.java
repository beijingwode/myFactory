/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nutz.dao.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wode.common.frame.base.BaseService;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.user.dao.OrdersDao;
import com.wode.factory.user.dao.ProductDao;
import com.wode.factory.user.dao.PromotionDao;
import com.wode.factory.user.dao.PromotionProductDao;
import com.wode.factory.user.dao.SuborderDao;
import com.wode.factory.user.dao.SuborderitemDao;
import com.wode.factory.user.dao.SuborderstatuslogDao;
import com.wode.factory.user.dao.SupplierCategoryDao;
import com.wode.factory.user.dao.UserBalanceDao;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.CartItem;
import com.wode.factory.user.service.OrdersService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.QuestionnaireAnswerService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.vo.ProductSpecificationsVo;

@Service("ordersService")
public class OrdersServiceImpl extends BaseService<Orders, java.lang.Long> implements OrdersService {
	@Override
	protected OrdersDao getEntityDao() {
		return this.ordersDao;
	}

	@Autowired
	@Qualifier("ordersDao")
	private OrdersDao ordersDao;

	@Autowired
	@Qualifier("suborderDao")
	private SuborderDao suborderDao;

	@Autowired
	ProductSpecificationsService specificationsService;

	@Autowired
	@Qualifier("suborderitemDao")
	private SuborderitemDao suborderitemDao;

	@Autowired
	@Qualifier("promotionDao")
	private PromotionDao promotionDao;

	@Autowired
	@Qualifier("suborderstatuslogDao")
	private SuborderstatuslogDao suborderstatuslogDao;
	
	@Qualifier("productSpecificationsService")
	@Autowired
	private ProductSpecificationsService productSpecificationsService;

    @Autowired
    private com.wode.factory.user.service.ProductService productService;
	@Autowired
	@Qualifier("productDao")
	private ProductDao productDao;

	@Autowired
	@Qualifier("userBalanceDao")
	private UserBalanceDao userBalanceDao;

	@Autowired
	@Qualifier("promotionProductDao")
	private PromotionProductDao promotionProductDao;

	@Autowired
	@Qualifier("supplierCategoryDao")
	private SupplierCategoryDao supplierCategoryDao;
	@Autowired
	private QuestionnaireAnswerService questionnaireAnswerService;
	
	@Autowired
	private Dao dao;
	
	@Qualifier("redis")
	@Autowired
	private RedisUtil redisUtil;
	
	private String qiyeApiUrl = Constant.QIYE_API_URL;

	private static Logger logger = LoggerFactory.getLogger(OrdersServiceImpl.class);
	 //阶梯价格
    @Autowired
    @Qualifier("factory-productLadderService")
    private ProductLadderService productLadderService;
	//private String qiyeApiUrl = "http://192.168.10.88:8080/companymanage/";
    
   
	//记录流水
	@SuppressWarnings("unchecked")
	@Override
	public ActResult<Object> recordFlow(Long orderId, UserFactory user) {
		Map<String, Object> comMap = new HashMap<String, Object>();
		comMap.put("empId", user.getId());
		comMap.put("orderId", orderId);
		comMap.put("updUser", user.getUserName());
		if(Long.valueOf("201712221700825").equals(user.getSupplierId())) {
			comMap.put("aceTicket", user.getEmail());
			comMap.put("aceUserId", user.getAddress());
		}
		String ticketResult = HttpClientUtil.sendHttpRequest("post", qiyeApiUrl + "api/payOrderTicket", comMap);
		return JsonUtil.getObject(ticketResult, ActResult.class);

		//return acTicket;
	}

	//记录余额流水
	@SuppressWarnings("unchecked")
	@Override
	public ActResult<Object> balanceFlow(Long orderId, String suborderId,BigDecimal absCash, UserFactory user) {
		Map<String, Object> comMap = new HashMap<String, Object>();
		comMap.put("empId", user.getId());
		comMap.put("absTicket", 0);
		comMap.put("desrc", "");
		comMap.put("updUser", user.getUserName());
		if (orderId != null) {
			comMap.put("key", orderId);
		}
		if (!StringUtils.isEmpty(suborderId)) {
			comMap.put("key", suborderId);
		}
		comMap.put("absCash", absCash);
		String balanceResult = HttpClientUtil.sendHttpRequest("post", qiyeApiUrl + "api/payOrder", comMap);
		return JsonUtil.getObject(balanceResult, ActResult.class);
		//return acBalance;
	}

	@Override
	public Orders findById(long userId, long orderId) {
		return ordersDao.findById(userId, orderId);
	}

	@Override
	public boolean isBought(Long userId, Long product) {
		int cnt = ordersDao.findBoughtCount(userId, product);
		return cnt>0;
	}
	@Override
	public GroupOrders findGroupOrdersById(Long orderId) {
		GroupOrders groupOrders = dao.fetch(GroupOrders.class,orderId);
    	return groupOrders;
	}

	@Override
	public void updateGroupOrder(GroupOrders groupOrders) {
		dao.update(groupOrders);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActResult<Cart> mergeBuyCart(String fromType,UserFactory user, List<Long> skuIds,
			Map<Long, Integer> sku_nums, Map<Long,BigDecimal> sku_realPrices,Map<Long, BigDecimal> sku_freights, 
			Map<Long, String> sku_images,Map<Long, String> sku_pageKeys, Map<Long, String> sku_froms, 
			Map<Long,Integer> sku_groups,
			String useExchangeTicket,String useExchangeCash, String useExchangeSelf,
			List<ProductSpecifications> outSkus) {
				
		Cart cart = new Cart();
		Set<Long> list = new HashSet<Long>();
		cart.setSelectProduct(list);
		for (Long skuId : skuIds) {
			Integer num = sku_nums.get(skuId);
			// 直接从数据库查询sku信息
			ProductSpecificationsVo sku = this.specificationsService.findByIdCache(skuId,null);
			outSkus.add(sku);
            if(null == sku){
            	return ActResult.fail("商品不存在,或已下架,请返回购物车后再次购买");
            }
            Integer groupProductSum = 0;
            if(sku_groups!=null && sku_groups.containsKey(skuId)) {
            	groupProductSum=sku_groups.get(skuId);
            }
			Product product = this.productService.getById(sku.getProductId());
			

			//////////////////////////////////////////////////////////////////////////////////////////////
			//试用商品检查问卷
			if(product.getSaleKbn()!=null && product.getSaleKbn()==5) {
				if(!NumberUtil.isGreaterZero(product.getEmpPrice())) {
					//评价后购买
					if(product.getQuestionnaireId() != -1 && !this.questionnaireAnswerService.hasAnswer(product.getQuestionnaireId(), user.getId())){
						ActResult<Cart> rtn = ActResult.fail(product.getFullName()+",需要先回答商家问卷才能下单");						
						cart.setErrKey(product.getQuestionnaireId());
						rtn.setData(cart);
						return rtn;
					}
				}	
			}
			
			Boolean isLadder = productSpecificationsService.resetPrice(sku, product, user,false,num+groupProductSum);
			
			String supplierJsonStr = redisUtil.getMapData(RedisConstant.PRODUCT_PRE + product.getId(), RedisConstant.PRODUCT_REDIS_SUPPLIER);
			if (StringUtils.isEmpty(supplierJsonStr)) {
				logger.error("商品" + product.getId() + "供应商不存在");
				return ActResult.fail("商品供应商不存在");
			}
		
			Supplier supplier = JSONObject.parseObject(supplierJsonStr, Supplier.class);
			list.add(skuId);
			CartItem cartItem = new CartItem();
			cartItem.setQuantity(sku_nums.get(skuId));
			cartItem.setPartNumber(skuId + "");
			cartItem.setProductCode(sku.getProductCode());
			cartItem.setItemValues(sku.getItemValues());
			cartItem.setPrice(sku.getPrice());
			cartItem.setSupplierId(supplier.getId());
			cartItem.setSupplierName(supplier.getComName());
			cartItem.setShopId(product.getShopId());
			cartItem.setProductId(product.getId());
			cartItem.setProductName(product.getFullName());
			cartItem.setSaleKbn(product.getSaleKbn());
			cartItem.setImagePath(sku_images.get(skuId));
			cartItem.setMaxCompanyTicket(sku.getMaxFucoin() == null ? BigDecimal.ZERO : sku.getMaxFucoin());
			cartItem.setCompanyTicket(cartItem.getMaxCompanyTicket().multiply(NumberUtil.toBigDecimal(num)));
			cartItem.setRealPrice(sku.getInternalPurchasePrice());
			if(sku_realPrices.containsKey(sku.getId())) {
				//比对内购价
				if(sku.getInternalPurchasePrice().compareTo(sku_realPrices.get(sku.getId())) != 0){
					return ActResult.fail("商家修改了:"+sku.getProductName()+"商品的内购价格,请重新下单");
				}
			}
			cartItem.setRealAmount(sku.getInternalPurchasePrice().multiply(NumberUtil.toBigDecimal(num)));

			cartItem.setBenefitAmount(BigDecimal.ZERO);
			cartItem.setBenefitTicket(BigDecimal.ZERO);
			cartItem.setBenefitSelf(BigDecimal.ZERO);
			if(useExchangeTicket == null) useExchangeTicket="";
			if(useExchangeCash == null) useExchangeCash="";
			if(useExchangeSelf == null) useExchangeSelf="";
			String[] productUseTick = useExchangeTicket.split(",");
			String[] productExchangeCash = useExchangeCash.split(",");
			String[] productExchangeSelf = useExchangeSelf.split(",");
			
			for (int i = 0; i < productUseTick.length; i++) {
				String[] strs = productUseTick[i].split("_");
				if ((""+skuId).equals(strs[0])) {
					BigDecimal exchangeTicket =new BigDecimal(strs[1]);
					String[] cashs = productExchangeCash[i].split("_");
					String[] selfs = productExchangeSelf[i].split("_");
					BigDecimal exchangeCash =new BigDecimal(cashs[1]);
					BigDecimal exchangeSelf =new BigDecimal(selfs[1]);
					if (NumberUtil.isGreaterZero(exchangeTicket)) {
						cartItem.setBenefitAmount(exchangeCash);
						cartItem.setBenefitTicket(exchangeTicket);
						cartItem.setBenefitSelf(exchangeSelf);
					}
					
					break;
				}
			}
			if (null != sku.getItemValues() && !"".equals(sku.getItemValues())) {
				String specificationJson = sku.getItemValues();
				cartItem.setSpecificationList(getSpecificationList(specificationJson));
			}
			cartItem.setIsLadder(isLadder);
			cartItem.setFreight(sku_freights.get(skuId));
			cartItem.setBuyFlag(true);
			cartItem.setPageKey(sku_pageKeys.get(skuId));
			cartItem.setFromType(sku_froms.get(skuId)+"_"+fromType);
			cart.addItem(cartItem);
		}
				
		return ActResult.success(cart);
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
			list.add(strs[i].substring(0, strs[i].indexOf(":")) + "_" + strs[i].substring(strs[i].indexOf(":") + 1));
		}
		return list;
	}
}
