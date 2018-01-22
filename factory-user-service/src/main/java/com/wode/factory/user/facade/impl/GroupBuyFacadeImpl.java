package com.wode.factory.user.facade.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;

import com.wode.common.util.NumberUtil;
import com.wode.factory.model.GroupBuyProduct;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.user.facade.GroupBuyFacade;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.vo.ProductSpecificationsVo;
import com.wode.search.WodeSearchManager;

public class GroupBuyFacadeImpl implements GroupBuyFacade {
	@Autowired
    private WodeSearchManager wsm;
	@Autowired
	private Dao dao;
	@Autowired
	private ProductLadderService productLadderService;
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
	/**
	 * 创建可选商品
	 */
	@Override
	public List<GroupBuyProduct> createGroupBuyProduct(Long groupId, String productIds) {
		List<GroupBuyProduct> groupBuyProductList = null;
		if (!"".equals(productIds)) {
			groupBuyProductList = new ArrayList<>();
			String[] productIdarr = productIds.split(",");
			for (String productid : productIdarr) {
				Map<String, Object> indexMap = wsm.getIndexById(Long.parseLong(productid));
				GroupBuyProduct groupPro = new GroupBuyProduct();
				groupPro.setGroupId(groupId);
				groupPro.setProductId(NumberUtil.toLong(indexMap.get("productId")));
				groupPro.setProductName((String) indexMap.get("name"));
				ProductSpecificationsVo findByIdCache = productSpecificationsService.findByIdCache(
						NumberUtil.toLong(indexMap.get("minSkuId")), indexMap.get("productId").toString());
				groupPro.setMarketPrice(findByIdCache.getPrice());
				groupPro.setMaxFucoin(findByIdCache.getMaxFucoin());
				groupPro.setInternalPurchasePrice(findByIdCache.getInternalPurchasePrice());
				groupPro.setMinLimitNum(findByIdCache.getMinLimitNum());
				List<ProductLadder> ladderPriceList = productLadderService.getListBySkuid(findByIdCache.getId());
				BigDecimal minladderPrice = BigDecimal.ZERO;
				if (null != ladderPriceList && ladderPriceList.size() > 0) {
					if (ladderPriceList.get(0).getType() == 1) {// 折扣
						minladderPrice = ladderPriceList.get(0).getPrice().multiply(new BigDecimal(0.1))
								.multiply(findByIdCache.getPrice());
						minladderPrice = minladderPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
					} else {
						minladderPrice = ladderPriceList.get(0).getPrice();
					}
				}
				groupPro.setBestPrice(minladderPrice);
				groupPro.setSkuId(findByIdCache.getId());
				if (!"".equals(findByIdCache.getItemValues()) && findByIdCache.getItemValues() != null) {
					groupPro.setItemValues(findByIdCache.getItemValues());
				}
				groupPro.setImage((String) indexMap.get("image"));
				groupPro.setCreate_time(new Date());
				groupPro = dao.insert(groupPro);
				groupBuyProductList.add(groupPro);
			}
		}
		return groupBuyProductList;
	}
}
