package com.wode.factory.user.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.ShippingAddress;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.model.ShippingTemplate;
import com.wode.factory.model.Supplier;
import com.wode.factory.service.ShippingFreeRuleService;
import com.wode.factory.service.ShippingTemplateService;
import com.wode.factory.user.dao.SupplierDao;
import com.wode.factory.user.facade.ShippingFacade;
import com.wode.factory.user.model.CartItem;
import com.wode.factory.user.service.ShippingAddressService;

@Component
public class ShippingUtil {

	/**
	 * 包邮模板
	 */
	@Autowired
	private ShippingFreeRuleService shippingFreeRuleService;

	@Qualifier("shippingAddressService")
	@Autowired
	private ShippingAddressService shippingAddressService;

	@Autowired
	private SupplierDao supplierDao;

	@Autowired
	private ShippingTemplateService shippingTemplateService;

	/**
	 * 根据用户id和商家id获取商家包邮策略
	 * 
	 * @param supplierCartList
	 * @param userId
	 * @return
	 */
	public Map<Long, Object> getFreeString(Map<Long, List<CartItem>> supplierCartList, Long userId) {

		Map<Long, Object> map = new HashMap<Long, Object>();
		if (null != supplierCartList && null != userId) {
			// 获取用户默认地址
			List<ShippingAddress> shippingAddressList = shippingAddressService.findByUserId(userId);
			if (null == shippingAddressList) {// 如果没有收获地址就返回
				return null;
			}
			String cityCode = "";
			String provinceCode = "";
			boolean hasDefualt = false;
			for (ShippingAddress shippingAddress : shippingAddressList) {
				Integer send = shippingAddress.getSend();
				if (null != send && send == 1) {
					String acode = shippingAddress.getAid();
					// 地址代码处理
					acode = acode + "000000";
					if (acode.length() > 6) {
						acode = acode.substring(0, 6);
					}

					// 运费规则不为空
					// 城市地理位置编码
					cityCode = acode.substring(0, 4) + "00";
					// 省地理位置编码
					provinceCode = acode.substring(0, 2) + "0000";
					hasDefualt=true;
					break;
				}
			}
			//查询完毕后如果没有默认的收货地址，用第一个
			if(!hasDefualt && !shippingAddressList.isEmpty() ){
				String acode = shippingAddressList.get(0).getAid();
				// 地址代码处理
				acode = acode + "000000";
				if (acode.length() > 6) {
					acode = acode.substring(0, 6);
				}

				// 运费规则不为空
				// 城市地理位置编码
				cityCode = acode.substring(0, 4) + "00";
				// 省地理位置编码
				provinceCode = acode.substring(0, 2) + "0000";
			} else {
				cityCode = "110100";
				provinceCode = "110000";
			}
			
			for (Long key : supplierCartList.keySet()) {
				// 如果查出来包邮策略，计算策略
				List<ShippingFreeRule> shippingList = shippingFreeRuleService.findBySupplier(key);
				// 如果没有新版查询下老板是否有全场包邮策略
				if (null == shippingList) {
					ShippingTemplate shippingTemplate = shippingTemplateService.selectTemplateBySupplierId(key);
					// 如果有新版的运费模板，没有设置新版的商品包邮，那么老版的商品包邮也不应该被使用
					if (null == shippingTemplate) {
						Supplier s = supplierDao.getById(key);
						if (null != s) {
							if (s.getShippingFree() > -1) {
								// 全场包邮以设置
								if (s.getShippingFree() > ShippingFacade.MAX_FREE) {
									shippingList = shippingFreeRuleService.findByTemplate(NumberUtil.toLong(s.getShippingFree()));
								} else {
									map.put(key, " 全场满" + s.getShippingFree() + "元包邮");
								}
							}
						}
					}
				}
				if (null != shippingList) {

					ShippingFreeRule rule = null;
					// 匹配地址
					for (int i = 0; i < shippingList.size(); i++) {
						if (shippingList.get(i).getAreasCode().contains(cityCode) || shippingList.get(i).getAreasCode().contains(provinceCode)) {
							rule = shippingList.get(i);
							break;
						}
					}

					if (rule != null) {
						// 按金额包邮
						if ("2".equals(rule.getCountTypeDes())) {
							map.put(key, " 满 " + rule.getParam2() + " 元包邮");
						} else {
							// 按计价方式
							if ("1".equals(rule.getCountTypeDes())) {
								// 1:按件数 (满X件包邮)
								map.put(key, " 满 " + rule.getParam1() + " 件包邮");
							}
							// 条件叠加方式
							if ("3".equals(rule.getCountTypeDes())) {
								// 1:按件数 (满X件且满X元包邮)
								map.put(key, " 满 " + rule.getParam1() + " 件,且 " + rule.getParam2() + " 元以上 包邮");
							}
						}
					}
				}
			}
			return map;
		} else {// 没有用户id查询商家所有的包邮条件
			for (Long key : supplierCartList.keySet()) {
				// 如果查出来包邮策略，计算策略
				List<ShippingFreeRule> shippingList = shippingFreeRuleService.findBySupplier(key);
				// 如果没有新版查询下老板是否有全场包邮策略
				if (null == shippingList) {
					ShippingTemplate shippingTemplate = shippingTemplateService.selectTemplateBySupplierId(key);
					// 如果有新版的运费模板，没有设置新版的商品包邮，那么老版的商品包邮也不应该被使用
					if (null == shippingTemplate) {
						Supplier s = supplierDao.getById(key);
						if (null != s) {
							if (s.getShippingFree() > -1) {
								// 全场包邮以设置
								if (s.getShippingFree() > ShippingFacade.MAX_FREE) {
									shippingList = shippingFreeRuleService.findByTemplate(NumberUtil.toLong(s.getShippingFree()));
								} else {
									map.put(key, " 全场满" + s.getShippingFree() + "元包邮");
								}
							}
						}
					}
				}
				if (null != shippingList) {

					ShippingFreeRule rule = null;
					// 匹配地址
					String result = "";
					for (int i = 0; i < shippingList.size(); i++) {

						rule = shippingList.get(i);
						if ("1".equals(rule.getCountTypeDes())) {
							// 1:按件数
							// 按金额包邮
							result += rule.getAreasName() + " 满" + rule.getParam1() + "件包邮、";

						} else if ("2".equals(rule.getCountTypeDes())) {
							// 按金额包邮
							result += rule.getAreasName() + " 满" + rule.getParam2() + "元包邮、";

						} else if ("3".equals(rule.getCountTypeDes())) {
							// 按金额包邮
							result += rule.getAreasName() + " 满" + rule.getParam1() + "件,且 " + rule.getParam2() + " 元以上 包邮、";
						}
					}
					// 按金额包邮
					map.put(key, result);

				}
			}
			return map;

		}

	}
	
	/**
	 * 返回商家对应对应的包邮信息
	 * @param supplierList
	 * @param acode
	 * @return
	 */
	public Map<String, String> getFreeShippingMap(HashSet<Long> supplierList, String acode) {
		Map<String, String> map = new HashMap<String, String>();
		if (null != supplierList) {
			for (Long long1 : supplierList) {
				map.put(long1+"", "");
				if (null != acode && !acode.equals("1")) {
					acode = acode + "000000";
					if (acode.length() > 6) {
						acode = acode.substring(0, 6);
					}
					// 运费规则不为空
					// 城市地理位置编码
					String cityCode = acode.substring(0, 4) + "00";
					// 省地理位置编码
					String provinceCode = acode.substring(0, 2) + "0000";
					// 如果查出来包邮策略，计算策略
					List<ShippingFreeRule> shippingList = shippingFreeRuleService.findBySupplier(long1);
					// 如果没有新版查询下老板是否有全场包邮策略
					if (null == shippingList) {
						ShippingTemplate shippingTemplate = shippingTemplateService.selectTemplateBySupplierId(long1);
						// 如果有新版的运费模板，没有设置新版的商品包邮，那么老版的商品包邮也不应该被使用
						if (null == shippingTemplate) {
							Supplier s = supplierDao.getById(long1);
							if (null != s) {
								if (s.getShippingFree() > -1) {
									// 全场包邮以设置
									if (s.getShippingFree() > ShippingFacade.MAX_FREE) {
										shippingList = shippingFreeRuleService.findByTemplate(NumberUtil.toLong(s.getShippingFree()));
									} else {
										map.put(long1+"", " 全场满" + s.getShippingFree() + "元包邮");
									}
								}
							}
						}
					}
					if (null != shippingList) {

						ShippingFreeRule rule = null;
						// 匹配地址
						for (int i = 0; i < shippingList.size(); i++) {
							if (shippingList.get(i).getAreasCode().contains(cityCode) || shippingList.get(i).getAreasCode().contains(provinceCode)) {
								rule = shippingList.get(i);
								break;
							}
						}
						if (rule != null) {
							// 按金额包邮
							if ("2".equals(rule.getCountTypeDes())) {
								map.put(long1+"", " 满 " + rule.getParam2() + " 元包邮");
							} else {
								// 按计价方式
								if ("1".equals(rule.getCountTypeDes())) {
									// 1:按件数 (满X件包邮)
									map.put(long1+"", " 满 " + rule.getParam1() + " 件包邮");
								}
								// 条件叠加方式
								if ("3".equals(rule.getCountTypeDes())) {
									// 1:按件数 (满X件且满X元包邮)
									map.put(long1+"", " 满 " + rule.getParam1() + " 件,且 " + rule.getParam2() + " 元以上 包邮");
								}
							}
						}
					}
				}
			}
		}
		return map;
	}

}
