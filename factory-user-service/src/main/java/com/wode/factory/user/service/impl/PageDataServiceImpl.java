package com.wode.factory.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.PageData;
import com.wode.factory.model.PageTypeSetting;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.user.dao.PageDataDao;
import com.wode.factory.user.service.PageDataService;
import com.wode.factory.user.vo.PageDataVo;
import com.wode.factory.user.vo.PageTypeSettingVo;
import com.wode.factory.user.vo.SkuVo;
import com.wode.search.WodeResult;
import com.wode.search.WodeSearchManager;
import com.wode.search.WodeSearcher;

@Service("pageDataService")
public class PageDataServiceImpl extends BaseService<PageTypeSetting, java.lang.Long> implements PageDataService {
	@Autowired
	@Qualifier("pageDataDao")
	private PageDataDao pageDataDao;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private WodeSearchManager wsm;
	@Override
	public EntityDao getEntityDao() {
		return this.pageDataDao;
	}
	@Autowired
	private ProductLadderService productLadderService;
	
	@Override
	public List<PageTypeSettingVo> findPageDataInfo(String page, String dataType, int channelId, int pageNum, int pageSize) {
		List<PageTypeSettingVo> pageTypes=this.pageDataDao.findPageDataInfo(page, dataType, channelId, pageNum, pageSize);
		for (PageTypeSettingVo pageTypeSettingVo : pageTypes) {
			List<PageDataVo> dels = new ArrayList<PageDataVo>();
			for (PageDataVo pageData : pageTypeSettingVo.getPageDataList()) {
				if (PageData.isLinkProduct(pageData.getLink())) {
					Map<String, String> map = redisUtil.getMap(RedisConstant.PRODUCT_PRE + pageData.getLink());
					Product p = JsonUtil.getObject(map.get(RedisConstant.PRODUCT_REDIS_INFO), Product.class);
					if(p!=null) {
						if(StringUtils.isEmpty(pageData.getImagePath())){
							pageData.setImagePath(p.getImage());
						}
						//pageData.setLink("/" + pageData.getLink() + ".html");
						pageData.setSaleKbn(p.getSaleKbn());
						pageData.setSaleNote(p.getSaleNote());
						pageData.setProName(p.getFullName());
						
						if(p.getProductSpecificationslist()!=null && !p.getProductSpecificationslist().isEmpty()) {
							ProductSpecifications sku = p.getProductSpecificationslist().get(0);
							if(sku==null || sku.getPrice() == null || sku.getMaxFucoin() == null) {
								dels.add(pageData);
								System.out.println(p.getId());
								continue;
							}
							pageData.setProPrice("内购:￥" +NumberUtil.removeTailZero(sku.getPrice().subtract(sku.getMaxFucoin()).setScale(2,BigDecimal.ROUND_DOWN)+""));
							pageData.setProSale(sku.getPrice().subtract(sku.getMaxFucoin()).multiply(new BigDecimal(10)).divide(sku.getPrice(),1,BigDecimal.ROUND_DOWN)+"折");
							pageData.setProDescription("电商:￥"+NumberUtil.removeTailZero(sku.getPrice().setScale(2,BigDecimal.ROUND_DOWN).toString()));
						}
					} else {
						dels.add(pageData);
					}
				}
			}
			if(!dels.isEmpty()) {
				pageTypeSettingVo.getPageDataList().removeAll(dels);
			}
		}
		return pageTypes;
	}


	@Override
	public List<PageTypeSettingVo> findPageDataInfo(String page, String dataType, int channelId) {
		return findPageDataInfo(page, dataType, channelId, 0, 0);
	}

	@Override
	public List<SkuVo> findAppIndexProducts(int pageNum, int pageSize) {

		List<PageTypeSettingVo> pageTypes = pageDataDao.findPageDataProducts("1", "guessLike", 2, pageNum, pageSize);
		if (pageTypes.size() > 0) {
			List<Map> ps = new ArrayList<Map>();
			List<PageDataVo> pageDatas = pageTypes.get(0).getPageDataList();
			String[] ids = new String[pageDatas.size()];
			PageDataVo pageData;
			for (int i = 0; i < pageDatas.size(); i++) {
				pageData = pageDatas.get(i);
				if (PageData.isLinkProduct(pageData.getLink())) {
					//ids[i] = RedisConstant.PRODUCT_PRE + pageData.getLink();
					ps.add(redisUtil.getMap(RedisConstant.PRODUCT_PRE + pageData.getLink()));
				}
			}
			List<SkuVo> skuVos = new ArrayList<>();
			Product product;
			List<ProductSpecifications> skus;
			ProductSpecifications sku;
			for (Map<String, String> p : ps) {
				product = JsonUtil.getObject(p.get(RedisConstant.PRODUCT_REDIS_INFO), Product.class);
				if (product == null) {
					continue;
				}
				skus = JsonUtil.getList(p.get(RedisConstant.PRODUCT_REDIS_SKU), ProductSpecifications.class);
				if (skus != null && skus.size() > 0) {
					sku = skus.get(0);
					if (ps == null) {
						continue;
					}
					SkuVo skuVo = new SkuVo();
					skuVo.setName(product.getName());
					skuVo.setPrice(sku.getPrice());
					skuVo.setCompanyTicket(sku.getMaxFucoin() == null ? new BigDecimal(0) : sku.getMaxFucoin());
					skuVo.setImage(product.getImage());
					skuVo.setProductId(product.getId());
					skuVo.setSkuId(sku.getId());
					skuVo.setSaleKbn(product.getSaleKbn());
					skuVo.setSaleNote(product.getSaleNote());
					if (!skuVos.contains(skuVo)) {
						skuVos.add(skuVo);
					}
				}
			}
			return skuVos;
		}
		return null;
	}

	@Override
	public List<SkuVo> findAppIndexProductsByElaSeach(int pageNum, int pageSize) {

		if(pageNum==0) pageNum=1;
		List<SkuVo> skuVos = new ArrayList<>();
		//根据曝光率,品牌等级,折扣,内购券排序
		String queryString="cat=10000,20000,30000,40000,50000,60000,70000,80000,90000,100000,110000,120000,130000&sort=createDate_1,brandLevel_0,discount_0,maxFucoin_1&page="+pageNum+"&saleKbn=8";
		wsm.setPageSize(pageSize);
		WodeSearcher searcher = wsm.getSearcher();
		//固定2个属性
		String[] fields = new String[]{"品牌", "价格"};
		WodeResult result = searcher.search(queryString, fields, false,false,false,"brand");
		if(result!=null && result.getHits()!=null){
			for (Map<String, Object> p : result.getHits()) {
				SkuVo skuVo = new SkuVo();
				skuVo.setName((String)p.get("name"));
				skuVo.setPrice(NumberUtil.toBigDecimal(p.get("price")));
				skuVo.setCompanyTicket(NumberUtil.toBigDecimal(p.get("maxFucoin")));
				skuVo.setImage((String)p.get("image"));
				skuVo.setProductId(NumberUtil.toLong(p.get("id")));
				skuVo.setSkuId(NumberUtil.toLong(p.get("minSkuId")));
				skuVo.setSaleKbn(NumberUtil.toInteger(p.get("saleKbn")));
				skuVo.setSaleNote("");
				skuVo.setSalePrice(NumberUtil.toBigDecimal(p.get("salePrice")));
				skuVo.setStock(NumberUtil.toInteger(p.get("stock")));
				skuVo.setAllStock(NumberUtil.toInteger(p.get("allStock")));
				if (!skuVos.contains(skuVo)) {
					skuVos.add(skuVo);
				}
			}
		}
		return skuVos;
	}
}
