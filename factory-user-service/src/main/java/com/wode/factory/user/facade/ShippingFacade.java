package com.wode.factory.user.facade;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.wode.factory.model.Product;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;

public interface ShippingFacade {

	public static Double MAX_FREE = 8000000D;

	//商品限购运费代码
	public static final BigDecimal productLimitCnt = new BigDecimal(9999);
	
	//商品区域限购运费代码
	public static final BigDecimal productAreaLimitCnt = new BigDecimal(8888);
	
	/**
	 * 限购判断 （数量及区域限购） 
	 * @param product
	 * @param cnt
	 * @param acode
	 * @param outRuleDes
	 * @param userId
	 * @param selfDelivery
	 * @return 9999:数量限购/8888:区域限购/0 不限购
	 */
	public BigDecimal chkLimitCntAndArea(Product product, Integer cnt,String acode, List<String> outRuleDes, Long userId, String selfDelivery);
	
	/**
	 * 判断是否限购
	 * @param shippingFee
	 * @return
	 */
	boolean hasLimit(BigDecimal shippingFee);
	/**
	 * 运费计算
	 * @param product 商品
	 * @param cnt 购买数量
	 * @param amount 物品总价
	 * @param acode 地址编码（通常为区县地址编码）
	 * @param outRuleDes 返回运费规则描述
	 * @return
	 */
	BigDecimal calculateFreight(Product product,Integer cnt,BigDecimal amount,String acode,List<String> outRuleDes,Long userId,String selfDelivery);
	
	/**
	 * 商家单位计算运费
	 * @param selfDelivery	1：自提
	 * @param addr	收货地址
	 * @param user  用户
	 * @param sid	商家id
	 * @param pids	商品ids
	 * @param productMap 商品map
	 * @param numMap	数量map
	 * @param amountMap	金额map
	 * @param freightMap 运费map (商品单位运费)
	 */
	void calculateSupplierShippingFee(String selfDelivery, String acode,UserFactory user, Long sid, List<Long> pids,Map<Long, Product> productMap,
			Map<Long, Integer> numMap, Map<Long, BigDecimal> amountMap, Map<Long, BigDecimal> freightMap,List<String> outRuleDes);
	

	/**
	 * 单个商品运费计算 不检查限购 并按商家单位计算
	 * @param selfDelivery
	 * @param quantity
	 * @param amount
	 * @param user
	 * @param outRuleDes
	 * @param p
	 * @param acode
	 * @return
	 */
	BigDecimal calculateSingleShippingFee(String selfDelivery, Integer quantity, BigDecimal amount, UserFactory user,
			List<String> outRuleDes, Product p, String acode);
	
	/**
	 * 运费计算
	 * @param product 商品
	 * @param cnt 购买数量
	 * @param acode 地址编码（通常为区县地址编码）
	 * @return
	 */
	String getACode(UserFactory user,String ipAddr);

	/**
	 * 判断全场包邮
	 * @param s
	 * @param cnt
	 * @param amount
	 * @param acode
	 * @param outRuleDes
	 * @return
	 */
	boolean checkSupplierFree(Supplier s, Integer cnt,BigDecimal amount, String acode,List<String> outRuleDes);
	
	/**
	 * 新版运费计算
	 * @param product
	 * @param cnt
	 * @param amount
	 * @param acode
	 * @param outRuleDes
	 * @param userId
	 * @param selfDelivery
	 * @return
	 */
//	BigDecimal newCalculateFreight(Product product,Integer cnt,BigDecimal amount,String acode,List<String> outRuleDes,Long userId,String selfDelivery);
	
	/**
	 * 通过商家id返回包邮信息
	 * @param supplierId
	 * @param userId
	 * @return
	 */
	public String getFreeString(Long supplierId, Long userId);

//	/**
//	 * 
//	 * 新版判断全场包邮
//	 * @param s
//	 * @param cnt
//	 * @param amount
//	 * @param acode
//	 * @param outRuleDes
//	 * @return
//	 * @return
//	 */
//	public boolean newCheckSupplierFree(Supplier s, Integer cnt, BigDecimal amount, String acode, List<String> outRuleDes);
//	/**
//	 * 商家单位运费计算
//	 * @param shippingTemplate
//	 * @param cnt
//	 * @param amount
//	 * @param acode
//	 * @return
//	 */
//	public BigDecimal supplierFreight(ShippingTemplate shippingTemplate,Integer cnt,BigDecimal amount,String acode);
//	
//	/**
//	 * 新版计算商家总邮费
//	 * @param lc
//	 * @param s
//	 * @param cnt
//	 * @param amount
//	 * @param acode
//	 * @return
//	 */
//	public Map<Long, BigDecimal> getNewShippingfee(List<Long> lc ,Long shopId, Integer cnt,BigDecimal amount, String acode, Long userId, String selfDelivery);
//	
	/**
	 * 显示商家区域包邮信息
	 * @param supplierList
	 * @param acode
	 * @return
	 */
	public Map<String, String> getFreeShippingMap(HashSet<Long> supplierList, String acode);

	public List<ShippingFreeRule> findParcelPostSomeAreas(Long supplierId);
}
