package com.wode.factory.user.facade.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.Area;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductTrialLimitGroup;
import com.wode.factory.model.ProductTrialLimitItem;
import com.wode.factory.model.ShippingAddress;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.model.ShippingTemplate;
import com.wode.factory.model.ShippingTemplateRule;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ShippingFreeRuleService;
import com.wode.factory.service.ShippingTemplateRuleService;
import com.wode.factory.service.ShippingTemplateService;
import com.wode.factory.user.dao.SupplierDao;
import com.wode.factory.user.facade.ShippingFacade;
import com.wode.factory.user.service.GroupSuborderItemService;
import com.wode.factory.user.service.ProductTrialLimitGroupService;
import com.wode.factory.user.service.ProductTrialLimitItemService;
import com.wode.factory.user.service.ShippingAddressService;
import com.wode.factory.user.service.SuborderitemService;
import com.wode.factory.user.util.AreaUtils;
import com.wode.factory.user.util.IPSeeker;
import com.wode.factory.vo.ProductVo;

@Service("shippingFacade")
public class ShippingFacadeImpl implements ShippingFacade {
	
	@Autowired
	private ProductTrialLimitItemService productTrialLimitItemService;
	@Autowired
	private ShippingTemplateRuleService shippingTemplateRuleService;
	@Autowired
	private ShippingFreeRuleService shippingFreeRuleService;
	@Autowired
	private ShippingAddressService shippingAddressService;
	@Autowired
	private SuborderitemService suborderitemService;

	@Autowired
	private SupplierDao supplierDao;

	@Autowired
	private ShippingTemplateService shippingTemplateService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private GroupSuborderItemService groupSuborderItemService;
	
	private static ResourceBundle res = ResourceBundle.getBundle("application");
	// 快递接口Domain
	public static String filePath = res.getString("tongji.ip.filePath");
	// 企业接口Domian
	public static String fileName = res.getString("tongji.ip.fileName");

	private static Logger logger = LoggerFactory.getLogger(ShippingFacadeImpl.class);
	
	@Autowired
	private RedisUtil redisUtil;
	
	
	@Override
	public BigDecimal chkLimitCntAndArea(Product product, Integer cnt,String acode, List<String> outRuleDes, Long userId, String selfDelivery) {

		String ruleDes = "";
		if (acode != null) {
			Area a = AreaUtils.getArea(NumberUtil.toInteger(acode));
			if (a != null) {
				ruleDes = a.getName();
			}
		}

		///////////////////////////////////////////////////////////////////////////////////////////
		// 查看ID限购
		BigDecimal limitCnt = productLimitCnt(product, cnt, outRuleDes, userId, ruleDes);
		// 如果等于限售返回限售标志
		if (limitCnt.compareTo(productLimitCnt) == 0) {
			if (outRuleDes != null) {
				outRuleDes.add(ruleDes);
			}
			return productLimitCnt;
		}
		///////////////////////////////////////////////////////////////////////////////////////////
		// 查看限购组限购
		if(redisUtil.getData(RedisConstant.GROUP_LIMIT_PRODUCT_PRE+product.getId()) != null) {
			Map<String,Object> map = new  HashMap<String,Object>();
			map.put("productId", product.getId());
			map.put("userId", userId);
			Integer totalCount = suborderitemService.getSuborderitemListByProductId(map);
			if(totalCount > 0){
				if (outRuleDes != null) {
					outRuleDes.add(ruleDes);
				}
				return productLimitCnt;
			}
		}
		///////////////////////////////////////////////////////////////////////////////////////////

		// 自提
		if ("1".equals(selfDelivery)) {
			return BigDecimal.ZERO;
		}

		// 地址代码处理
		acode = dealtAcode(acode);

		// 运费规则不为空
		// 城市地理位置编码
		String cityCode = acode.substring(0, 4) + "00";
		// 省地理位置编码
		String provinceCode = acode.substring(0, 2) + "0000";

		///////////////////////////////////////////////////////////////////////////////////////////
		// 查看区域限购
		if (!StringUtils.isEmpty(product.getAreasCode())) {
			if (!"0".equals(product.getAreasCode())) {
				if (product.getAreasCode().contains(cityCode) || product.getAreasCode().contains(provinceCode)) {
				} else {
					if (outRuleDes != null) {
						Area a = AreaUtils.getArea(NumberUtil.toInteger(provinceCode));
						if (a != null) {
							ruleDes = a.getName();
						}
						outRuleDes.add(ruleDes);
					}
					return productAreaLimitCnt;
				}
			}
		}
		///////////////////////////////////////////////////////////////////////////////////////////

		
		
		if(outRuleDes!=null) {
			outRuleDes.add(ruleDes);
		}
		return BigDecimal.ZERO;
	}
	
	@Override
	public BigDecimal calculateFreight(Product product, Integer cnt, BigDecimal amount, String acode, List<String> outRuleDes, Long userId, String selfDelivery) {

		String ruleDes = "";
		if (amount == null)
			amount = BigDecimal.ZERO;
		if (acode != null) {
			Area a = AreaUtils.getArea(NumberUtil.toInteger(acode));
			if (a != null) {
				ruleDes = a.getName();
			}
		}

		///////////////////////////////////////////////////////////////////////////////////////////
		// 查看ID限购
		BigDecimal limitCnt = productLimitCnt(product, cnt, outRuleDes, userId, ruleDes);
		// 如果等于限售返回限售标志
		if (limitCnt.compareTo(productLimitCnt) == 0) {
			if (outRuleDes != null) {
				outRuleDes.add(ruleDes);
			}
			return productLimitCnt;
		}
		///////////////////////////////////////////////////////////////////////////////////////////
		
		// 查看限购组限购
		if(redisUtil.getData(RedisConstant.GROUP_LIMIT_PRODUCT_PRE+product.getId()) != null) {
			Map<String,Object> map = new  HashMap<String,Object>();
			map.put("productId", product.getId());
			map.put("userId", userId);
			Integer totalCount = suborderitemService.getSuborderitemListByProductId(map);
			if(totalCount > 0){
				if (outRuleDes != null) {
					outRuleDes.add(ruleDes);
				}
				return productLimitCnt;
			}
		}
		///////////////////////////////////////////////////////////////////////////////////////////

		// 自提
		if ("1".equals(selfDelivery)) {
			return BigDecimal.ZERO;
		}

		// 地址代码处理
		acode = dealtAcode(acode);

		// 运费规则不为空
		// 城市地理位置编码
		String cityCode = acode.substring(0, 4) + "00";
		// 省地理位置编码
		String provinceCode = acode.substring(0, 2) + "0000";

		///////////////////////////////////////////////////////////////////////////////////////////
		// 查看区域限购
		if (!StringUtils.isEmpty(product.getAreasCode())) {
			if (!"0".equals(product.getAreasCode())) {
				if (product.getAreasCode().contains(cityCode) || product.getAreasCode().contains(provinceCode)) {
				} else {
					if (outRuleDes != null) {
						Area a = AreaUtils.getArea(NumberUtil.toInteger(provinceCode));
						if (a != null) {
							ruleDes = a.getName();
						}
						outRuleDes.add(ruleDes);
					}
					return productAreaLimitCnt;
				}
			}
		}
		///////////////////////////////////////////////////////////////////////////////////////////

		// 包邮判断
		List<String> outSsRuleDes = new ArrayList<String>();
		boolean isFree = checkSupplierFree(supplierDao.getById(product.getSupplierId()), cnt, amount, acode, outSsRuleDes);
		String ssDesc = outSsRuleDes.get(0);
		logger.debug("checkSupplierFree：" + isFree);
		///////////////////////////////////////////////////////////////////////////////////////////
		// 获取运费包邮规则
		List<ShippingFreeRule> frees = shippingFreeRuleService.findByProduct(product.getId());
		///////////////////////////////////////////////////////////////////////////////////////////
		// 包邮规则适用
		if (frees != null && !frees.isEmpty()) {
			logger.debug("ShippingFreeRule：" + frees.size());
			ShippingFreeRule rule = null;
			// 匹配地址
			for (int i = 0; i < frees.size(); i++) {
				if (frees.get(i).getAreasCode().contains(cityCode) || frees.get(i).getAreasCode().contains(provinceCode)) {
					rule = frees.get(i);
					break;
				}
			}

			if (rule != null) {
				// 按金额包邮
				if ("2".equals(rule.getCountTypeDes())) {
					if (outRuleDes != null) {
						outRuleDes.add(ruleDes + " 满 " + rule.getParam2() + " 元包邮" + ssDesc);
						outRuleDes.add(" 满 " + rule.getParam2() + " 元包邮" + ssDesc);
					}
					if (amount.compareTo(rule.getParam2()) >= 0) {
						isFree = true;
					}
				} else {

					BigDecimal goods = BigDecimal.ZERO;
					// 计价方式
					switch (rule.getCountType()) {
					case "2":
						// 2:按重量
						goods = (product.getRoughWeight() == null ? BigDecimal.TEN : product.getRoughWeight()).multiply(NumberUtil.toBigDecimal(cnt));
						break;
					case "3":
						// 2:按体积 (m³)
						goods = (product.getBulk() == null ? BigDecimal.TEN : product.getBulk()).multiply(NumberUtil.toBigDecimal(cnt)).divide(new BigDecimal(1000 * 1000 * 1000));
						break;
					default:
						// 1:按件数
						goods = NumberUtil.toBigDecimal(cnt);
					}

					// 按计价方式
					if ("1".equals(rule.getCountTypeDes())) {
						if ("1".equals(rule.getCountType())) {
							// 1:按件数 (满X件包邮)
							if (outRuleDes != null) {
								outRuleDes.add(ruleDes + " 满 " + rule.getParam1() + " 件包邮" + ssDesc);
								outRuleDes.add(" 满 " + rule.getParam1() + " 件包邮" + ssDesc);
							}
							if (goods.compareTo(rule.getParam1()) >= 0) {
								isFree = true;
							}
						} else {
							// 2:按重量 2:按体积 (m³) (Xkg/Xm内包邮)
							if (outRuleDes != null) {
								outRuleDes.add(ruleDes + " 在" + rule.getParam1() + ("2".equals(rule.getCountType()) ? "kg" : "m³") + " 内包邮" + ssDesc);
								outRuleDes.add(" 在" + rule.getParam1() + ("2".equals(rule.getCountType()) ? "kg" : "m³") + " 内包邮" + ssDesc);
							}
							if (goods.compareTo(rule.getParam1()) <= 0) {
								isFree = true;
							}
						}
					}

					// 条件叠加方式
					if ("3".equals(rule.getCountTypeDes())) {
						if ("1".equals(rule.getCountType())) {
							// 1:按件数 (满X件且满X元包邮)
							if (outRuleDes != null) {
								outRuleDes.add(ruleDes + " 满 " + rule.getParam1() + " 件,且 " + rule.getParam2() + " 元以上 包邮" + ssDesc);
								outRuleDes.add(" 满 " + rule.getParam1() + " 件,且 " + rule.getParam2() + " 元以上 包邮" + ssDesc);
							}
							if (goods.compareTo(rule.getParam1()) >= 0 && amount.compareTo(rule.getParam2()) >= 0) {
								isFree = true;
							}
						} else {
							// 2:按重量 2:按体积 (m³) (Xkg/Xm内且满X元包邮)
							if (outRuleDes != null) {
								outRuleDes.add(ruleDes + " 在" + rule.getParam1() + " " + ("2".equals(rule.getCountType()) ? "kg" : "m³") + " 内,且 " + rule.getParam2() + " 元以上 包邮" + ssDesc);
								outRuleDes.add(" 在" + rule.getParam1() + " " + ("2".equals(rule.getCountType()) ? "kg" : "m³") + " 内,且 " + rule.getParam2() + " 元以上 包邮" + ssDesc);
							}
							if (goods.compareTo(rule.getParam1()) <= 0 && amount.compareTo(rule.getParam2()) >= 0) {
								isFree = true;
							}
						}
					}
				}
			} else {
				if (outRuleDes != null)
					outRuleDes.add(ruleDes + ssDesc);
			}
		} else {
			if (outRuleDes != null)
				outRuleDes.add(ruleDes + ssDesc);
		}

		// 符合包邮条件
		if (isFree) {
			logger.debug("符合包邮条件：" + isFree);
			return BigDecimal.ZERO;
		}

		///////////////////////////////////////////////////////////////////////////////////////////
		// 获取运费规则
		List<ShippingTemplateRule> rules = shippingTemplateRuleService.findByProduct(product.getId());
		///////////////////////////////////////////////////////////////////////////////////////////
		return dealSupplierFreeShipping(product, cnt, cityCode, provinceCode, rules);
	}

	

	@Override
	public String getACode(UserFactory user, String ipAddr) {
		String acode = "131028";
		if (user != null) {
			// 加载常用地址
			List<ShippingAddress> shippingAddressList = shippingAddressService.findByUserId(user.getId());
			if (shippingAddressList != null && !shippingAddressList.isEmpty()) {
				acode = shippingAddressList.get(0).getAid();
			}
		} else {
			IPSeeker ip = IPSeeker.CreateInstance(fileName, filePath);
			String str = ip.getCountry(ipAddr);
			if (str != null) {
				int pro = str.indexOf("省");
				if (pro > 0) {
					str = str.substring(pro + 1);
				}
				int city = str.indexOf("市");
				if (city > 0) {
					str = str.substring(0, city + 1);
				} else {
					city = str.indexOf("区");
					if (city > 0) {
						str = str.substring(0, city + 1);
					} else {
						city = str.indexOf("县");
						if (city > 0) {
							str = str.substring(0, city + 1);
						}
					}
				}

				Integer code = AreaUtils.getACode(str);
				if (code != null && code > 10000) {
					acode = code + "";
				}
			}
		}

		return acode;
	}

	@Override
	public boolean checkSupplierFree(Supplier s, Integer cnt, BigDecimal amount, String acode, List<String> outRuleDes) {
		if (amount == null)
			amount = BigDecimal.ZERO;
		acode = dealtAcode(acode);

		// 运费规则不为空
		// 城市地理位置编码
		String cityCode = acode.substring(0, 4) + "00";
		// 省地理位置编码
		String provinceCode = acode.substring(0, 2) + "0000";
		// 包邮判断
		boolean isFree = false;
		String ssDesc = "";
		if (s.getShippingFree() > -1) {
			// 全场包邮以设置
			if (s.getShippingFree() > MAX_FREE) {
				// 匹配包邮策略
				List<ShippingFreeRule> frees = shippingFreeRuleService.findByTemplate(NumberUtil.toLong(s.getShippingFree()));
				ShippingFreeRule rule = null;
				for (ShippingFreeRule shippingFreeRule : frees) {
					if (shippingFreeRule.getAreasCode().contains(cityCode) || shippingFreeRule.getAreasCode().contains(provinceCode)) {
						rule = shippingFreeRule;
						break;
					}
				}

				if (rule != null) {
					if ("1".equals(rule.getCountTypeDes())) {
						// 1:按件数
						BigDecimal goods = NumberUtil.toBigDecimal(cnt);
						// 按金额包邮
						ssDesc = " 全场满" + rule.getParam1() + "件包邮";
						if (goods.compareTo(rule.getParam1()) >= 0) {
							isFree = true;
						}

					} else if ("2".equals(rule.getCountTypeDes())) {
						// 按金额包邮
						ssDesc = " 全场满" + rule.getParam2() + "元包邮";
						if (amount.compareTo(rule.getParam2()) >= 0) {
							isFree = true;
						}

					} else if ("3".equals(rule.getCountTypeDes())) {

						// 条件叠加方式
						BigDecimal goods = NumberUtil.toBigDecimal(cnt);
						// 按金额包邮
						ssDesc = " 全场满" + rule.getParam1() + "件,且 " + rule.getParam2() + " 元以上 包邮";
						if (goods.compareTo(rule.getParam1()) >= 0 && amount.compareTo(rule.getParam2()) >= 0) {
							isFree = true;
						}
					}
				}
			} else {
				// 全场满金额包邮
				if (amount.doubleValue() >= s.getShippingFree()) {
					isFree = true;
				}

				ssDesc = " 全场满" + s.getShippingFree() + "元包邮";
			}
		}
		if (outRuleDes != null)
			outRuleDes.add(ssDesc);
		return isFree;
	}
	
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
	private BigDecimal newCalculateFreight(Product product, Integer cnt, BigDecimal amount, String acode, List<String> outRuleDes, Long userId, String selfDelivery) {
		// 判断是否有新的运费模板
		ShippingTemplate shippingTemplate = shippingTemplateService.selectTemplateBySupplierId(product.getSupplierId());
		if (null == shippingTemplate) {
			// 没有走原有逻辑
			return calculateFreight(product, cnt, amount, acode, outRuleDes, userId, selfDelivery);
		}
		logger.debug("tongyiceshi newCalculateFreight shippingTemplate is {}" ,shippingTemplate);
		String ruleDes = "";
		if (amount == null)
			amount = BigDecimal.ZERO;
		if (acode != null) {
			Area a = AreaUtils.getArea(NumberUtil.toInteger(acode));
			if (a != null) {
				ruleDes = a.getName();
			}
		}

		acode = dealtAcode(acode);
		
		// 运费规则不为空
		// 城市地理位置编码
		String cityCode = acode.substring(0, 4) + "00";
		// 省地理位置编码
		String provinceCode = acode.substring(0, 2) + "0000";
		// 查看ID限购
		BigDecimal limitCnt = productLimitCnt(product, cnt, outRuleDes, userId, ruleDes);
		// 如果等于限售返回限售标志
		if (limitCnt.compareTo(productLimitCnt) == 0) {
			if (outRuleDes != null) {
				String desc = getFreeDes(shippingTemplate.getId(), cityCode, provinceCode);
				outRuleDes.add(ruleDes+desc);//增加用户地址
				outRuleDes.add(desc);//增加商家包邮信息
			}
			return productLimitCnt;
		}
		
		// 查看限购组限购
		if(redisUtil.getData(RedisConstant.GROUP_LIMIT_PRODUCT_PRE+product.getId()) != null) {
			Map<String,Object> map = new  HashMap<String,Object>();
			map.put("productId", product.getId());
			map.put("userId", userId);
			Integer totalCount = suborderitemService.getSuborderitemListByProductId(map);
			if(totalCount > 0){
				if (outRuleDes != null) {
					outRuleDes.add(ruleDes);
				}
				return productLimitCnt;
			}
		}
		///////////////////////////////////////////////////////////////////////////////////////////
		
		// 自提
		if ("1".equals(selfDelivery)) {
			if (outRuleDes != null) {
				String desc = getFreeDes(shippingTemplate.getId(), cityCode, provinceCode);
				outRuleDes.add(ruleDes+desc);//增加用户地址
				outRuleDes.add(desc);//增加商家包邮信息
			}
			return BigDecimal.ZERO;
		}


		///////////////////////////////////////////////////////////////////////////////////////////
		// 查看区域限购
		if (!StringUtils.isEmpty(product.getAreasCode())) {
			if (!"0".equals(product.getAreasCode())) {
				if (product.getAreasCode().contains(cityCode) || product.getAreasCode().contains(provinceCode)) {
				} else {
					if (outRuleDes != null) {
						Area a = AreaUtils.getArea(NumberUtil.toInteger(provinceCode));
						if (a != null) {
							ruleDes = a.getName();
						}
						String desc = getFreeDes(shippingTemplate.getId(), cityCode, provinceCode);
						outRuleDes.add(ruleDes+desc);//增加用户地址
						outRuleDes.add(desc);//增加商家包邮信息
					}
					return productAreaLimitCnt;
				}
			}
		}
		///////////////////////////////////////////////////////////////////////////////////////////

		// 包邮判断
		if (product.getCarriage().compareTo(BigDecimal.ZERO) == 0) {
			if (outRuleDes != null) {
				String desc = getFreeDes(shippingTemplate.getId(), cityCode, provinceCode);
				outRuleDes.add(ruleDes+desc);//增加用户地址
				outRuleDes.add(desc);//增加商家包邮信息
			}
			return BigDecimal.ZERO;
		}
		String ssDesc = "";
		///////////////////////////////////////////////////////////////////////////////////////////
		// 获取运费包邮规则
		List<ShippingFreeRule> frees = shippingFreeRuleService.findByTemplate(shippingTemplate.getId());
		///////////////////////////////////////////////////////////////////////////////////////////
		// 包邮规则适用
		if (frees != null && !frees.isEmpty()) {
			logger.debug("ShippingFreeRule：" + frees.size());
			ShippingFreeRule rule = null;
			// 匹配地址
			for (int i = 0; i < frees.size(); i++) {
				if (frees.get(i).getAreasCode().contains(cityCode) || frees.get(i).getAreasCode().contains(provinceCode)) {
					rule = frees.get(i);
					break;
				}
			}

			if (rule != null) {
				// 按金额包邮
				if ("2".equals(rule.getCountTypeDes())) {
					if (outRuleDes != null) {
						outRuleDes.add(ruleDes + " 满 " + rule.getParam2() + " 元包邮" + ssDesc);
						outRuleDes.add(" 满 " + rule.getParam2() + " 元包邮" + ssDesc);
					}
					if (amount.compareTo(rule.getParam2()) >= 0) {
						logger.debug("符合包邮条件：");
						return BigDecimal.ZERO;
					}
				} else {

					BigDecimal goods = BigDecimal.ZERO;
					// 计价方式
					// 1:按件数
					goods = NumberUtil.toBigDecimal(cnt);

					// 按计价方式
					if ("1".equals(rule.getCountTypeDes())) {
						// 1:按件数 (满X件包邮)
						if (outRuleDes != null) {
							outRuleDes.add(ruleDes + " 满 " + rule.getParam1() + " 件包邮" + ssDesc);
							outRuleDes.add(" 满 " + rule.getParam1() + " 件包邮" + ssDesc);
						}
						if (goods.compareTo(rule.getParam1()) >= 0) {
							logger.debug("符合包邮条件：");
							return BigDecimal.ZERO;
						}
					}
					// 条件叠加方式
					if ("3".equals(rule.getCountTypeDes())) {
						// 1:按件数 (满X件且满X元包邮)
						if (outRuleDes != null) {
							outRuleDes.add(ruleDes + " 满 " + rule.getParam1() + " 件,且 " + rule.getParam2() + " 元以上 包邮" + ssDesc);
							outRuleDes.add(" 满 " + rule.getParam1() + " 件,且 " + rule.getParam2() + " 元以上 包邮" + ssDesc);
						}
						if (goods.compareTo(rule.getParam1()) >= 0 && amount.compareTo(rule.getParam2()) >= 0) {
							logger.debug("符合包邮条件：");
							return BigDecimal.ZERO;
						}
					}
				}
			} else {
				if (outRuleDes != null)
					outRuleDes.add(ruleDes + ssDesc);
			}
		} else {
			if (outRuleDes != null)
				outRuleDes.add(ruleDes + ssDesc);
		}

		///////////////////////////////////////////////////////////////////////////////////////////
		// 获取运费规则
		List<ShippingTemplateRule> rules = shippingTemplateRuleService.findByTemplate(shippingTemplate.getId());
		///////////////////////////////////////////////////////////////////////////////////////////
		return dealSupplierFreeShipping(product, cnt, cityCode, provinceCode, rules);
	}

	@Override
	public String getFreeString(Long supplierId, Long userId) {

		String result = "";
		if (null != supplierId && null != userId) {
			// 获取用户默认地址
			List<ShippingAddress> shippingAddressList = shippingAddressService.findByUserId(userId);
			if (null == shippingAddressList) {// 如果没有收获地址就返回
				return result;
			}
			String cityCode = "";
			String provinceCode = "";
			for (ShippingAddress shippingAddress : shippingAddressList) {
				Integer send = shippingAddress.getSend();
				if (send == 1) {
					String acode = shippingAddress.getAid();
					acode = dealtAcode(acode);

					// 运费规则不为空
					// 城市地理位置编码
					cityCode = acode.substring(0, 4) + "00";
					// 省地理位置编码
					provinceCode = acode.substring(0, 2) + "0000";
					break;
				}
			}
			// 如果查出来包邮策略，计算策略
			List<ShippingFreeRule> shippingList = shippingFreeRuleService.findBySupplier(supplierId);
			// 如果没有新版查询下老板是否有全场包邮策略
			if (null == shippingList) {
				ShippingTemplate shippingTemplate = shippingTemplateService.selectTemplateBySupplierId(supplierId);
				// 如果有新版的运费模板，没有设置新版的商品包邮，那么老版的商品包邮也不应该被使用
				if (null == shippingTemplate) {
					Supplier s = supplierDao.getById(supplierId);
					if (null != s) {
						if (s.getShippingFree() > -1) {
							// 全场包邮以设置
							if (s.getShippingFree() > ShippingFacade.MAX_FREE) {
								shippingList = shippingFreeRuleService.findByTemplate(NumberUtil.toLong(s.getShippingFree()));
							} else {
								result = " 全场满" + s.getShippingFree() + "元包邮";
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
						result = " 满 " + rule.getParam2() + " 元包邮";
					} else {
						// 按计价方式
						if ("1".equals(rule.getCountTypeDes())) {
							// 1:按件数 (满X件包邮)
							result = " 满 " + rule.getParam1().intValue() + " 件包邮";
						}
						// 条件叠加方式
						if ("3".equals(rule.getCountTypeDes())) {
							// 1:按件数 (满X件且满X元包邮)
							result = " 满 " + rule.getParam1().intValue() + " 件,且 " + rule.getParam2() + " 元以上 包邮";
						}
					}
				}
			}
			return result;
		} else {// 没有用户id查询商家所有的包邮条件

			// 如果查出来包邮策略，计算策略
			List<ShippingFreeRule> shippingList = shippingFreeRuleService.findBySupplier(supplierId);
			// 如果没有新版查询下老板是否有全场包邮策略
			if (null == shippingList) {
				ShippingTemplate shippingTemplate = shippingTemplateService.selectTemplateBySupplierId(supplierId);
				// 如果有新版的运费模板，没有设置新版的商品包邮，那么老版的商品包邮也不应该被使用
				if (null == shippingTemplate) {
					Supplier s = supplierDao.getById(supplierId);
					if (null != s) {
						if (s.getShippingFree() > -1) {
							// 全场包邮以设置
							if (s.getShippingFree() > ShippingFacade.MAX_FREE) {
								shippingList = shippingFreeRuleService.findByTemplate(NumberUtil.toLong(s.getShippingFree()));
							} else {
								result = "满" + s.getShippingFree() + "元包邮";
							}
						}
					}
				}
			}

			if (null != shippingList) {

				ShippingFreeRule rule = null;
				// 匹配地址
				for (int i = 0; i < shippingList.size(); i++) {
					rule = shippingList.get(i);
					if (rule != null) {
						if("1".equals(rule.getCountTypeDes())) {
    						// 1:按件数
    						//按金额包邮
							result += rule.getAreasName()+" 满" + rule.getParam1().intValue() + "件包邮、";
    						
    					} else if("2".equals(rule.getCountTypeDes())) {
    						//按金额包邮
    						result += rule.getAreasName()+" 满" + rule.getParam2() + "元包邮、";
    						
    					} else if("3".equals(rule.getCountTypeDes())) {
    						//按金额包邮
    						result += rule.getAreasName()+" 满" + rule.getParam1().intValue() + "件,且 "+rule.getParam2()+" 元以上 包邮、";
    					}
					}
				}

			}
			return result;

		}
	}

	/**
	 * 
	 * 新版判断全场包邮
	 * @param s
	 * @param cnt
	 * @param amount
	 * @param acode
	 * @param outRuleDes
	 * @return
	 * @return
	 */
	private boolean newCheckSupplierFree(Supplier s, Integer cnt, BigDecimal amount, String acode, List<String> outRuleDes) {
		logger.info("cnt is {} amount is {}", cnt , amount);
		ShippingTemplate shippingTemplate = shippingTemplateService.selectTemplateBySupplierId(s.getId());
		if (null == shippingTemplate) {
			// 没有走原有逻辑
			return checkSupplierFree(s, cnt, amount, acode, outRuleDes);
		}
		if (amount == null)
			amount = BigDecimal.ZERO;
		acode = dealtAcode(acode);

		// 运费规则不为空
		// 城市地理位置编码
		String cityCode = acode.substring(0, 4) + "00";
		// 省地理位置编码
		String provinceCode = acode.substring(0, 2) + "0000";
		// 包邮判断
		boolean isFree = false;
		String ssDesc = "";
		// 匹配包邮策略
		logger.info("shippingTemplate is {} ", shippingTemplate.getId());
		List<ShippingFreeRule> frees = shippingFreeRuleService.findByTemplate(shippingTemplate.getId());
		if (null != frees && frees.size() > 0) {
			ShippingFreeRule rule = null;
			for (ShippingFreeRule shippingFreeRule : frees) {
				if (shippingFreeRule.getAreasCode().contains(cityCode) || shippingFreeRule.getAreasCode().contains(provinceCode)) {
					rule = shippingFreeRule;
					break;
				}
			}
			if (rule != null) {
				if ("1".equals(rule.getCountTypeDes())) {
					// 1:按件数
					BigDecimal goods = NumberUtil.toBigDecimal(cnt);
					// 按金额包邮
					ssDesc = " 全场满" + rule.getParam1().intValue() + "件包邮";
					if (goods.compareTo(rule.getParam1()) >= 0) {
						isFree = true;
					}
					
				} else if ("2".equals(rule.getCountTypeDes())) {
					// 按金额包邮
					ssDesc = " 全场满" + rule.getParam2() + "元包邮";
					if (amount.compareTo(rule.getParam2()) >= 0) {
						isFree = true;
					}
					
				} else if ("3".equals(rule.getCountTypeDes())) {
					
					// 条件叠加方式
					BigDecimal goods = NumberUtil.toBigDecimal(cnt);
					// 按金额包邮
					ssDesc = " 全场满" + rule.getParam1().intValue() + "件,且 " + rule.getParam2() + " 元以上 包邮";
					if (goods.compareTo(rule.getParam1()) >= 0 && amount.compareTo(rule.getParam2()) >= 0) {
						isFree = true;
					}
				}
			}
		}
		if (outRuleDes != null)
			outRuleDes.add(ssDesc);
		logger.info("isFree is {} ", isFree);
		return isFree;

	}

	/**
	 * 新版计算商家总邮费
	 * @param lc
	 * @param s
	 * @param cnt
	 * @param amount
	 * @param acode
	 * @return
	 */
//	private Map<Long, BigDecimal> getNewShippingfee(List<Long> lc, Long shopId, Integer cnt, BigDecimal amount, String acode, Long userId, String selfDelivery) {
//		ShippingTemplate shippingTemplate = shippingTemplateService.selectTemplateBySupplierId(shopId);
//		if (null == shippingTemplate) {
//			// 没有返回空
//			return null;
//		}
//		// 这里返回重新拼接后的根据商品id查询的商品运费金额
//		Map<Long, BigDecimal> map = null;
//		if (null != lc && lc.size() > 0) {
//			map = new HashMap<Long, BigDecimal>();
//			for (Long long1 : lc) {
//				map.put(long1, newCalculateFreightWithoutLimit(productService.getById(long1), cnt, amount, acode, null, userId, selfDelivery));
//			}
//		}
//		return map;
//	}

	/**
	 * 商品ID限购逻辑
	 * 
	 * @param product
	 * @param cnt
	 * @param outRuleDes
	 * @param userId
	 * @param ruleDes
	 */
	private BigDecimal productLimitCnt(Product product, Integer cnt, List<String> outRuleDes, Long userId, String ruleDes) {
		if (product.getLimitCnt() != null && product.getLimitCnt() > 0) {
			Integer ordered = 0;
			Integer groupOrdered = 0;//团购订单中商品数量
			if (userId != null && userId > 1) {
				ordered = suborderitemService.getCountByUserAndProduct(product.getId(), userId);
				groupOrdered = groupSuborderItemService.getCountByUserAndProduct(product.getId(), userId);
				if (ordered == null)
					ordered = 0;
				if (groupOrdered == null) groupOrdered=0;
			}

			if (cnt + ordered + groupOrdered> product.getLimitCnt()) {
				return productLimitCnt;
			}
		}
		return BigDecimal.ZERO;
	}
	
	/**
	 * 处理地址代码
	 * @param acode
	 * @return
	 */
	private String dealtAcode(String acode) {
		acode = acode + "000000";
		if (acode.length() > 6) {
			acode = acode.substring(0, 6);
		}
		return acode;
	}
	
	/**
	 * 商家单位运费计算
	 * @param shippingTemplate
	 * @param cnt
	 * @param amount
	 * @param acode
	 * @return
	 */
	private BigDecimal supplierFreight(ShippingTemplate shippingTemplate,Integer cnt,BigDecimal amount,String acode){
//		String ruleDes = "";
//		if (acode != null) {
//			Area a = AreaUtils.getArea(NumberUtil.toInteger(acode));
//			if (a != null) {
//				ruleDes = a.getName();
//			}
//		}

		acode = dealtAcode(acode);
		
		// 运费规则不为空
		// 城市地理位置编码
		String cityCode = acode.substring(0, 4) + "00";
		// 省地理位置编码
		String provinceCode = acode.substring(0, 2) + "0000";
		
		///////////////////////////////////////////////////////////////////////////////////////////
		// 获取运费规则
		List<ShippingTemplateRule> rules = shippingTemplateRuleService.findByTemplate(shippingTemplate.getId());
		///////////////////////////////////////////////////////////////////////////////////////////
		if (rules != null && !rules.isEmpty()) {
			logger.debug("ShippingTemplateRule：" + rules.size());

			ShippingTemplateRule rule = rules.get(0);
			// 匹配地址
			for (int i = 1; i < rules.size(); i++) {
				if (rules.get(i).getAreasCode().contains(cityCode) || rules.get(i).getAreasCode().contains(provinceCode)) {
					rule = rules.get(i);
					break;
				}
			}

			BigDecimal goods = BigDecimal.ZERO;
//			// 计价方式
//			switch (rule.getCountType()) {
//			case "2":
//				// 2:按重量
//				goods = product.getRoughWeight().multiply(NumberUtil.toBigDecimal(cnt));
//				break;
//			case "3":
//				// 2:按体积 (m³)
//				goods = product.getBulk().multiply(NumberUtil.toBigDecimal(cnt)).divide(new BigDecimal(1000 * 1000 * 1000));
//				break;
//			default:
				// 1:按件数
				goods = NumberUtil.toBigDecimal(cnt);
//				break;
//			}

			// 初始运费
			BigDecimal shipping = rule.getFirstPrice();
			goods = goods.subtract(rule.getFirstCnt());

			if (NumberUtil.isGreaterZero(rule.getPlusCnt())) {
				// 超过首重
				while (goods.compareTo(BigDecimal.ZERO) > 0) {
					// 续重费用增加
					shipping = shipping.add(rule.getPlusPrice());
					// 续重数量减少
					goods = goods.subtract(rule.getPlusCnt());
				}
			}

			logger.debug("shipping：" + shipping);
			return shipping;
		} else {
			return BigDecimal.ZERO;
		}
	}
	
	/**
	 * 处理商家运费模板
	 * @param product
	 * @param cnt
	 * @param cityCode
	 * @param provinceCode
	 * @param rules
	 * @return
	 */
	private BigDecimal dealSupplierFreeShipping(Product product, Integer cnt, String cityCode, String provinceCode, List<ShippingTemplateRule> rules) {
		if (rules != null && !rules.isEmpty()) {
			logger.debug("ShippingTemplateRule：" + rules.size());

			ShippingTemplateRule rule = rules.get(0);
			// 匹配地址
			for (int i = 1; i < rules.size(); i++) {
				if (rules.get(i).getAreasCode().contains(cityCode) || rules.get(i).getAreasCode().contains(provinceCode)) {
					rule = rules.get(i);
					break;
				}
			}

			BigDecimal goods = BigDecimal.ZERO;
			// 计价方式
			switch (rule.getCountType()) {
			case "2":
				// 2:按重量
				goods = product.getRoughWeight().multiply(NumberUtil.toBigDecimal(cnt));
				break;
			case "3":
				// 2:按体积 (m³)
				goods = product.getBulk().multiply(NumberUtil.toBigDecimal(cnt)).divide(new BigDecimal(1000 * 1000 * 1000));
				break;
			default:
				// 1:按件数
				goods = NumberUtil.toBigDecimal(cnt);
				break;
			}

			// 初始运费
			BigDecimal shipping = rule.getFirstPrice();
			goods = goods.subtract(rule.getFirstCnt());

			if (NumberUtil.isGreaterZero(rule.getPlusCnt())) {
				// 超过首重
				while (goods.compareTo(BigDecimal.ZERO) > 0) {
					// 续重费用增加
					shipping = shipping.add(rule.getPlusPrice());
					// 续重数量减少
					goods = goods.subtract(rule.getPlusCnt());
				}
			}

			logger.debug("shipping：" + shipping);
			return shipping;
		} else {
			// 该商品未设置运费规则
			///////////////////////////////////////////////////////////////////////////////////////////
			// 直接计算运费
			// 运费=商品运费*数量
			if (cnt != null && product.getCarriage() != null) {
				return product.getCarriage().multiply(NumberUtil.toBigDecimal(cnt));
			}
			///////////////////////////////////////////////////////////////////////////////////////////

		}
		return BigDecimal.ZERO;
	}
	
	/**
	 * 获取商家全场包邮描述
	 * @param shippingTemplateId
	 * @param cityCode
	 * @param provinceCode
	 * @return
	 */
	private String getFreeDes(Long shippingTemplateId, String cityCode, String provinceCode) {
		String s = "";
		// 获取运费包邮规则
		List<ShippingFreeRule> frees = shippingFreeRuleService.findByTemplate(shippingTemplateId);
		///////////////////////////////////////////////////////////////////////////////////////////
		// 包邮规则适用
		if (frees != null && !frees.isEmpty()) {
			logger.debug("ShippingFreeRule：" + frees.size());
			ShippingFreeRule rule = null;
			// 匹配地址
			for (int i = 0; i < frees.size(); i++) {
				if (frees.get(i).getAreasCode().contains(cityCode) || frees.get(i).getAreasCode().contains(provinceCode)) {
					rule = frees.get(i);
					break;
				}
			}
			if (rule != null) {
				// 按金额包邮
				if ("2".equals(rule.getCountTypeDes())) {
					s = " 满 " + rule.getParam2() + " 元包邮";
				} else {

					// 按计价方式
					if ("1".equals(rule.getCountTypeDes())) {
						// 1:按件数 (满X件包邮)
						s = " 满 " + rule.getParam1().intValue() + " 件包邮";
					}
					// 条件叠加方式
					if ("3".equals(rule.getCountTypeDes())) {
						// 1:按件数 (满X件且满X元包邮)
						s = " 满 " + rule.getParam1().intValue() + " 件,且 " + rule.getParam2() + " 元以上 包邮";
					}
				}
			}
		}
		return s;
	}



	@Override
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
									map.put(long1+"", " 满 " + rule.getParam1().intValue() + " 件包邮");
								}
								// 条件叠加方式
								if ("3".equals(rule.getCountTypeDes())) {
									// 1:按件数 (满X件且满X元包邮)
									map.put(long1+"", " 满 " + rule.getParam1().intValue() + " 件,且 " + rule.getParam2() + " 元以上 包邮");
								}
							}
						}
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * 不管商品限购逻辑
	 * @param product
	 * @param cnt
	 * @param amount
	 * @param acode
	 * @param outRuleDes
	 * @param userId
	 * @param selfDelivery
	 * @return
	 */
//	private BigDecimal newCalculateFreightWithoutLimit(Product product, Integer cnt, BigDecimal amount, String acode, List<String> outRuleDes, Long userId, String selfDelivery) {
//		// 判断是否有新的运费模板
//		ShippingTemplate shippingTemplate = shippingTemplateService.selectTemplateBySupplierId(product.getSupplierId());
//		if (null == shippingTemplate) {
//			// 没有走原有逻辑
//			return null;
//		}
//		if (amount == null)
//			amount = BigDecimal.ZERO;
//		String ruleDes = "";
//		if (acode != null) {
//			Area a = AreaUtils.getArea(NumberUtil.toInteger(acode));
//			if (a != null) {
//				ruleDes = a.getName();
//			}
//		}
//
//		acode = dealtAcode(acode);
//		
//		// 运费规则不为空
//		// 城市地理位置编码
//		String cityCode = acode.substring(0, 4) + "00";
//		// 省地理位置编码
//		String provinceCode = acode.substring(0, 2) + "0000";
//		// 自提
//		if ("1".equals(selfDelivery)) {
//			if (outRuleDes != null) {
//				String desc = getFreeDes(shippingTemplate.getId(), cityCode, provinceCode);
//				outRuleDes.add(ruleDes+desc);//增加用户地址
//				outRuleDes.add(desc);//增加商家包邮信息
//			}
//			return BigDecimal.ZERO;
//		}
//		///////////////////////////////////////////////////////////////////////////////////////////
//		// 获取运费包邮规则
//		List<ShippingFreeRule> frees = shippingFreeRuleService.findByTemplate(shippingTemplate.getId());
//		///////////////////////////////////////////////////////////////////////////////////////////
//		// 包邮规则适用
//		if (frees != null && !frees.isEmpty()) {
//			logger.debug("ShippingFreeRule：" + frees.size());
//			ShippingFreeRule rule = null;
//			// 匹配地址
//			for (int i = 0; i < frees.size(); i++) {
//				if (frees.get(i).getAreasCode().contains(cityCode) || frees.get(i).getAreasCode().contains(provinceCode)) {
//					rule = frees.get(i);
//					break;
//				}
//			}
//
//			if (rule != null) {
//				// 按金额包邮
//				if ("2".equals(rule.getCountTypeDes())) {
//					if (amount.compareTo(rule.getParam2()) >= 0) {
//						logger.debug("符合包邮条件：");
//						return BigDecimal.ZERO;
//					}
//				} else {
//
//					BigDecimal goods = BigDecimal.ZERO;
//					// 计价方式
//					// 1:按件数
//					goods = NumberUtil.toBigDecimal(cnt);
//
//					// 按计价方式
//					if ("1".equals(rule.getCountTypeDes())) {
//						if (goods.compareTo(rule.getParam1()) >= 0) {
//							logger.debug("符合包邮条件：");
//							return BigDecimal.ZERO;
//						}
//					}
//					// 条件叠加方式
//					if ("3".equals(rule.getCountTypeDes())) {
//						// 1:按件数 (满X件且满X元包邮)
//						if (goods.compareTo(rule.getParam1()) >= 0 && amount.compareTo(rule.getParam2()) >= 0) {
//							logger.debug("符合包邮条件：");
//							return BigDecimal.ZERO;
//						}
//					}
//				}
//			} 
//		}
//
//		///////////////////////////////////////////////////////////////////////////////////////////
//		// 获取运费规则
//		List<ShippingTemplateRule> rules = shippingTemplateRuleService.findByTemplate(shippingTemplate.getId());
//		///////////////////////////////////////////////////////////////////////////////////////////
//		return dealSupplierFreeShipping(product, cnt, cityCode, provinceCode, rules);
//	}

	@Override
	public void calculateSupplierShippingFee(String selfDelivery, String acode, UserFactory user, Long sid,
			List<Long> pids, Map<Long, Product> productMap, Map<Long, Integer> numMap, Map<Long, BigDecimal> amountMap,
			Map<Long, BigDecimal> freightMap,List<String> outRuleDes) {

		List<Long> newProduct = new ArrayList<Long>();
		BigDecimal amount = BigDecimal.ZERO;
		Integer cnt = 0;
		for (Long pid : pids) {
			//如果已经算出来包邮的了就不让他进入到运费模板的运算中
			if(!hasLimit(freightMap.get(pid))){
				cnt=cnt+numMap.get(pid);
			    amount=amount.add(amountMap.get(pid));
				newProduct.add(pid);
			}
		}
		
		ShippingTemplate shippingTemplate = shippingTemplateService.selectTemplateBySupplierId(sid);
		// 判断是否有商家级运费模板
		if(shippingTemplate !=null){
			//////////////////////////////////////////////////////////////////////////////////////////
			// 商家全场包邮判断
			boolean isSupplierFree=false;
			if(!newProduct.isEmpty()) {
				Supplier s = supplierDao.getById(sid);
				isSupplierFree = this.newCheckSupplierFree(s, cnt, amount, acode, outRuleDes);
			}
			//////////////////////////////////////////////////////////////////////////////////////////
			
			if(isSupplierFree) {
				//商家包邮
//						for (Long pid : newProduct) {
//							freightMap.put(pid, BigDecimal.ZERO);
//						}
			} else {
				//////////////////////////////////////////////////////////////////////////////////////////
				// 商家单位计算运费 ，并将总运费计入第一个商品
				// 计算运费
				newProduct = new ArrayList<Long>();
				amount = BigDecimal.ZERO;
				cnt = 0;
				
				for (Long pid : pids) {
					//如果已经算出来包邮的了就不让他进入到运费模板的运算中
					if(!hasLimit(freightMap.get(pid))){
						Product p=productMap.get(pid);
						if (BigDecimal.ZERO.compareTo(p.getCarriage()) != 0) {
						    cnt=cnt+numMap.get(pid);
						    amount=amount.add(amountMap.get(pid));
							newProduct.add(pid);
						}
					}
				}

				// 第一个商品放置运费
				if(!newProduct.isEmpty()) {
					if(!"1".equals(selfDelivery)){
					    freightMap.put(newProduct.get(0), this.supplierFreight(shippingTemplate, cnt, amount, acode));
					}else{
						freightMap.put(newProduct.get(0), BigDecimal.ZERO);
					}
				}
				//////////////////////////////////////////////////////////////////////////////////////////
			}
		} else {
			//旧版商家运费计算
			
			//////////////////////////////////////////////////////////////////////////////////////////
			// 商家全场包邮判断
			boolean isSupplierFree=false;
			if(!newProduct.isEmpty()) {
				Supplier s = supplierDao.getById(sid);
				isSupplierFree = this.newCheckSupplierFree(s, cnt, amount, acode, outRuleDes);
			}
			//////////////////////////////////////////////////////////////////////////////////////////
			if(isSupplierFree) {
				//商家包邮
//						for (Long pid : newProduct) {
//							freightMap.put(pid, BigDecimal.ZERO);
//						}
			} else {
				//////////////////////////////////////////////////////////////////////////////////////////
				// 商品单位计算运费

				// 第一个商品放置运费
				if(!newProduct.isEmpty()) {
					for (Long pid : newProduct) {
						freightMap.put(pid, this.newCalculateFreight(productMap.get(pid), numMap.get(pid),amountMap.get(pid),acode,outRuleDes,user==null?null:user.getId(),selfDelivery));
					}
				}
				//////////////////////////////////////////////////////////////////////////////////////////
			}
		}
		if(outRuleDes!=null && !outRuleDes.isEmpty()) {
			String ruleDes = "";
			if (acode != null) {
				Area a = AreaUtils.getArea(NumberUtil.toInteger(acode));
				if (a != null) {
					ruleDes = a.getName();
				}
			}
			outRuleDes.set(0, ruleDes + outRuleDes.get(0));
		}
		
	}

	@Override
	public boolean hasLimit(BigDecimal shippingFee) {
		return (productLimitCnt.compareTo(shippingFee) == 0 || productAreaLimitCnt.compareTo(shippingFee) == 0 );
	}

	@Override
	public BigDecimal calculateSingleShippingFee(String selfDelivery, Integer quantity, BigDecimal amount, UserFactory user,
			List<String> outRuleDes, Product p, String acode) {

		Map<Long,BigDecimal> freightMap = new HashMap<Long,BigDecimal>();
		freightMap.put(p.getId(), BigDecimal.ZERO);
		
		Map<Long,Product> productMap = new HashMap<Long,Product>();
		Map<Long,Integer> numMap = new HashMap<Long,Integer>();
		Map<Long,BigDecimal> amountMap = new HashMap<Long,BigDecimal>();
		List<Long> pids = new ArrayList<Long>();
		pids.add(p.getId());
		productMap.put(p.getId(),p);
		numMap.put(p.getId(), quantity);
		amountMap.put(p.getId(), amount);
		
		//不自提才进行运费计算
		if(!"1".equals(selfDelivery)){
			
			this.calculateSupplierShippingFee(selfDelivery, acode, user, p.getSupplierId(), pids, productMap, numMap, amountMap, freightMap,outRuleDes);
		}
		
		return freightMap.get(p.getId());
	}

	@Override
	public List<ShippingFreeRule> findParcelPostSomeAreas(Long supplierId) {
		ShippingTemplate shippingTemplate = shippingTemplateService.selectTemplateBySupplierId(supplierId);
		if(shippingTemplate!=null){
			List<ShippingFreeRule> list = shippingFreeRuleService.findByTemplateOrCountTypeDes(shippingTemplate.getId());
			return list;
		}
		return null;
	}
}
