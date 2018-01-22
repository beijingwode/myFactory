/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.elasticsearch.cluster.metadata.MetaDataIndexTemplateService.RemoveListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.Currency;
import com.wode.factory.model.PageData;
import com.wode.factory.model.PageSectionData;
import com.wode.factory.model.PageSetting;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.Shop;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.dao.PageSectionDataDao;
import com.wode.factory.user.dao.SupplierDao;
import com.wode.factory.user.service.CurrencyService;
import com.wode.factory.user.service.InventoryService;
import com.wode.factory.user.service.PageSectionDataService;
import com.wode.factory.user.service.PageSettingService;
import com.wode.factory.user.service.ShopService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.service.UserExchangeTicketService;
import com.wode.factory.user.vo.PageDataVo;
import com.wode.factory.user.vo.PageSectionDataVo;
import com.wode.factory.user.vo.PageTypeSettingVo;
import com.wode.search.WodeResult;
import com.wode.search.WodeSearchManager;
import com.wode.search.WodeSearcher;

@Service("pageSectionDataService")
public class PageSectionDataServiceImpl extends FactoryEntityServiceImpl<PageSectionData> implements  PageSectionDataService{
	@Autowired
	private PageSectionDataDao dao;
	@Autowired
	private PageSettingService pageSettingService;

	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
    private InventoryService inventoryService;

	@Override
	public PageSectionDataDao getDao() {
		return dao;
	}
	
	@Autowired
	CurrencyService currencyService;
	@Autowired
	UserBalanceService userBalanceService;
	@Autowired
	private UserExchangeTicketService userExchangeTicketService;
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private WodeSearchManager wsm;
	@Autowired
    private ShopService shopService;
	
	private static final String fecthFields[] = {"image", "brand",
		    "name",
		    "salePrice",
		    "maxFucoin",
		    "price",
		    "productId",
		    "minSkuId",
		    "stock",
		    "allStock"};
	@Override
	public Long getId(PageSectionData entity) {
		return entity.getId();
	}

	@Override
	public void setId(PageSectionData entity, Long id) {
		entity.setId(id);
	}

	@Override
	public List<PageSectionDataVo> selectByPageId(Long pageId) {
		return getDao().selectByPageId(pageId);
	}

	@Override
	public List<PageTypeSettingVo> findPageDataForApp(String pageKey) {
		PageSetting query = new PageSetting();
		query.setPageKey(pageKey);
		query.setChannel(2);
		List<PageSetting> ls = pageSettingService.selectByModel(query);
		PageSetting pageSetting = null;
		if(!ls.isEmpty()) {
			pageSetting=ls.get(0);
		} else {
			return null;
		}

		List<PageTypeSettingVo> pageTypes=this.dao.findPageDataForApp(pageSetting.getId());
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
							pageData.setProPrice("内购:￥" +NumberUtil.removeTailZero(sku.getInternalPurchasePrice().setScale(2,BigDecimal.ROUND_DOWN).toString()));
							pageData.setProSale(sku.getInternalPurchasePrice().multiply(new BigDecimal(10)).divide(sku.getPrice(),1,BigDecimal.ROUND_DOWN)+"折");
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
	public List<PageTypeSettingVo> findIndexPageData(UserFactory uf) {
		PageSetting query = new PageSetting();
		query.setPageKey("newIndex");
		query.setChannel(2);
		List<PageSetting> ls = pageSettingService.selectByModel(query);
		PageSetting pageSetting = null;
		if(!ls.isEmpty()) {
			pageSetting=ls.get(0);
		}
		List<PageDataVo> cashAreaPageDataList = null;// 现金区数据
		List<PageDataVo> exchangeAreaPageDataList = null;// 换领区数据
		List<PageDataVo> answerAreaPageDataList = null;// 试用区数据
		List<PageDataVo> saveMoreAreaPageDataList = null;// 特省区数据

		List<PageTypeSettingVo> pageTypes = this.dao.findPageDataForApp(pageSetting.getId());
		for (PageTypeSettingVo pageTypeSettingVo : pageTypes) {
			// List<PageDataVo> pageDataList = pageTypeSettingVo.getPageDataList();
			if ("CashArea".equals(pageTypeSettingVo.getName())) {// 存在现金专区
				cashAreaPageDataList = delPageData(pageTypeSettingVo.getPageDataList());
			} else if ("AnswerArea".equals(pageTypeSettingVo.getName())) {// 存在换领专区
				answerAreaPageDataList = delPageData(pageTypeSettingVo.getPageDataList());
			} else if ("ExchangeArea".equals(pageTypeSettingVo.getName())) {// 存在试用专区位置
				exchangeAreaPageDataList = delPageData(pageTypeSettingVo.getPageDataList());
			} else if ("SaveMoreArea".equals(pageTypeSettingVo.getName())) {// 存在特省专区位置
				saveMoreAreaPageDataList = delPageData(pageTypeSettingVo.getPageDataList());
			} else {// 都没有

			}
		}
		PageTypeSettingVo  OwnexclusivePageTypeSettingVo = null;//自家专享
		//用户所有金额及券
		PageDataVo userTicketPageData= new PageDataVo();
		Currency currencyTicket = currencyService.findByName("companyTicket");
		Currency currencyBalance = currencyService.findByName("balance");
		UserBalance userTickect = null;//内购券
		UserBalance userCashBalance = null;//现金券
		List<UserBalance> list = userBalanceService.findByUser(uf.getId());
		for (UserBalance ub : list) {
			if(ub.getCurrencyId().equals(currencyTicket.getId())) {
				userTickect=ub;
			} else if(ub.getCurrencyId().equals(currencyBalance.getId())) {
				userCashBalance=ub;
			}
		}
		if (StringUtils.isEmpty(userTickect))
			userTicketPageData.setEx3Value("0");
		else
			userTicketPageData.setEx3Value(userTickect.getBalance() == null ? "0" : userTickect.getBalance().toString());
		if (StringUtils.isEmpty(userCashBalance))
			userTicketPageData.setEx1Value("0");
		else
			userTicketPageData.setEx1Value(userCashBalance.getBalance() == null ? "0" : userCashBalance.getBalance().toString());
		BigDecimal userExchange = BigDecimal.ZERO;
		List<UserExchangeTicket> ets = userExchangeTicketService.usingTicket(uf.getId());
		for (UserExchangeTicket userExchangeTicket : ets) {
			userExchange=userExchange.add(userExchangeTicket.getEmpAvgAmount().subtract(userExchangeTicket.getUsedAmount()));	// 暂时记录已使用
		}
		userTicketPageData.setEx2Value(userExchange.toString());
		userTicketPageData.setTitle("user_ticket_have");
		//用户类型
		if(1==uf.getEmployeeType()) {//员工用户
			//公司
			if (!StringUtils.isEmpty(uf.getSupplierId())) {
				if(uf.getSupplierId().equals(1940165284890424L)) {
					userTicketPageData.setEx4Value("中国惠普");
					userTicketPageData.setEx5Value("1940165284890424");
				} else {
					Supplier sup = supplierDao.getById(uf.getSupplierId());
					List<Shop> shopList = shopService.getShopBySupplierId(uf.getSupplierId());
					if(sup==null) {
						userTicketPageData.setEx4Value("我的福利");
					}else {
						userTicketPageData.setEx4Value((sup.getNickName()==null)||("".equals(sup.getNickName())) ? sup.getComName() : sup.getNickName());
					}
					userTicketPageData.setEx5Value((shopList == null)|| (shopList.size()==0)? "" : shopList.get(0).getId().toString());
				}
				//自家专享
				OwnexclusivePageTypeSettingVo = new PageTypeSettingVo();//自家专享
				OwnexclusivePageTypeSettingVo.setName("Ownexclusive");
				OwnexclusivePageTypeSettingVo.setTitle("Ownexclusive");
				List<PageDataVo> ownexClusiveProductList = getDifferentgoods("saleKbn=4&supplierId=" +uf.getSupplierId(),8);
				if(ownexClusiveProductList !=null && 8>ownexClusiveProductList.size()) {//专享商品有8个
					List<PageDataVo> companyProductList = getDifferentgoods("supplierId="+uf.getSupplierId(),8);//企业下所有商品
					if(companyProductList!=null&& companyProductList.size()>0) {
						for (PageDataVo pageDataVo : ownexClusiveProductList) {
							companyProductList.removeIf(s -> s.getLink().equals(pageDataVo.getLink()));
						}
						ownexClusiveProductList.addAll(companyProductList);//合并
					}
				}
				if(ownexClusiveProductList.size()>=8) {
					OwnexclusivePageTypeSettingVo.setPageDataList(ownexClusiveProductList.subList(0, 8));//只取8个数据
				}else {
					OwnexclusivePageTypeSettingVo.setPageDataList(ownexClusiveProductList);
				}
			}
		}else{//非员工用户
			OwnexclusivePageTypeSettingVo = new PageTypeSettingVo();//自家专享
			OwnexclusivePageTypeSettingVo.setName("Ownexclusive");
			OwnexclusivePageTypeSettingVo.setTitle("Ownexclusive");
			OwnexclusivePageTypeSettingVo.setPageDataList(null);
			userTicketPageData.setEx4Value("我的福利");
		}
		//用户信息位置
		PageTypeSettingVo  userTicketPageTypeSettingVo = new PageTypeSettingVo();
		userTicketPageTypeSettingVo.setName("user_ticket_have");
		userTicketPageTypeSettingVo.setTitle("user_ticket_have");
		List<PageDataVo> userTicketPageDataList = new ArrayList<>();
		userTicketPageDataList.add(userTicketPageData);
		userTicketPageTypeSettingVo.setPageDataList(userTicketPageDataList);
		//现金专区
		if (cashAreaPageDataList == null) {
		} else {
			if (!StringUtils.isEmpty(userCashBalance) && NumberUtil.isGreaterZero(userCashBalance.getBalance())) {
				cashAreaPageDataList = dealShowPageData("saleKbn=8&salePrice=0-" + userCashBalance.getBalance()+"&sort=createDate_1", 8,
						cashAreaPageDataList);
				}
		}
		//试用专区
		if (answerAreaPageDataList == null) {
		} else {
			if (answerAreaPageDataList.size() < 11) {
				answerAreaPageDataList = dealShowPageData("saleKbn=5", 8, answerAreaPageDataList);
			}
		}
		//换领专区
		if (exchangeAreaPageDataList == null) {
		} else {
			if (NumberUtil.isGreaterZero(userExchange)) {
				if (exchangeAreaPageDataList.size() < 11) {
					exchangeAreaPageDataList = dealShowPageData("saleKbn=2", 8, exchangeAreaPageDataList);
				}
			} else {
			}
		}
		//特省专区
		if (saveMoreAreaPageDataList == null) {
		} else {
			if (saveMoreAreaPageDataList.size() < 11) {
				saveMoreAreaPageDataList = dealShowPageData("saleKbn=1", 8, saveMoreAreaPageDataList);
			}
		}
		pageTypes.add(0, userTicketPageTypeSettingVo);//用户信息位置
		if(OwnexclusivePageTypeSettingVo!=null) {
			pageTypes.add(4, OwnexclusivePageTypeSettingVo);//专享信息位置
		}
		return pageTypes;
	}
	/**
	 * 处理指定数据替换搜索位置
	 * @param queryString 查询条件
	 * @param PageSize 查询个数
	 * @param cashAreaPageDataList 指定数据集合
	 * @return
	 */
	private List<PageDataVo> dealShowPageData(String queryString,int PageSize, List<PageDataVo> cashAreaPageDataList) {
		List<PageDataVo> differentgoods = getDifferentgoods(queryString,PageSize);
		for (PageDataVo cashAreaPageData : cashAreaPageDataList) {
			/*for (PageDataVo pageDataVo : differentgoods) {
				if(pageDataVo.getEx6Value().equals(cashAreaPageData.getEx6Value())) {
					RemoveList.add(pageDataVo);
				}
			}*/
			differentgoods.removeIf(s -> s.getEx6Value().equals(cashAreaPageData.getEx6Value()));
		}
		cashAreaPageDataList.addAll(differentgoods);
		Collections.sort(cashAreaPageDataList, new Comparator<PageDataVo>() {
			@Override
			public int compare(PageDataVo o1, PageDataVo o2) {
				if (o1.getEx6Value() == null)
					return 1;
				if (o2.getEx6Value() == null)
					return -1;
				return o1.getEx6Value().compareTo(o2.getEx6Value());
			}
     	});
		return cashAreaPageDataList;
	}

	private List<PageDataVo> delPageData(List<PageDataVo> pageDataList) {
		List<PageDataVo> dels = new ArrayList<PageDataVo>();
		int i = 0;
		for (PageDataVo pageData : pageDataList) {
			if (PageData.isLinkProduct(pageData.getLink())) {
				Map<String, String> productMap = redisUtil.getMap(RedisConstant.PRODUCT_PRE + pageData.getLink());
				Product p = JsonUtil.getObject(productMap.get(RedisConstant.PRODUCT_REDIS_INFO), Product.class);
				if(p!=null) {
					if(StringUtils.isEmpty(pageData.getImagePath())){
						pageData.setImagePath(p.getImage());
					}
					pageData.setSaleKbn(p.getSaleKbn());
					pageData.setSaleNote(p.getSaleNote());
					pageData.setProName(p.getFullName());
					if(pageData.getEx6Value()==null) {//商品但是没有设置位置默认设置叠加
						pageData.setEx6Value((i++)+"");
					}
					if(p.getProductSpecificationslist()!=null && !p.getProductSpecificationslist().isEmpty()) {
						ProductSpecifications sku = p.getProductSpecificationslist().get(0);
						if(sku==null || sku.getPrice() == null || sku.getMaxFucoin() == null) {
							dels.add(pageData);
							System.out.println(p.getId());
							continue;
						}
						pageData.setProPrice(NumberUtil.removeTailZero(sku.getInternalPurchasePrice().setScale(2,BigDecimal.ROUND_DOWN).toString()));
						pageData.setProSale(sku.getInternalPurchasePrice().multiply(new BigDecimal(10)).divide(sku.getPrice(),1,BigDecimal.ROUND_DOWN)+"折");
						pageData.setProDescription(NumberUtil.removeTailZero(sku.getPrice().setScale(2,BigDecimal.ROUND_DOWN).toString()));
						pageData.setStock(inventoryService.getInventoryFromRedis(sku.getId()));
						pageData.setMarketPrice(sku.getPrice().toString());//电商价
						pageData.setMaxFucoin(sku.getMaxFucoin().toString());//内购券
						pageData.setMinSkuId(sku.getId().toString());
						int allStock=0;
						for (ProductSpecifications productSku : p.getProductSpecificationslist()) {
							allStock = allStock+inventoryService.getInventoryFromRedis(sku.getId());
						}
						pageData.setAllStock(allStock);
					}
				} else {
					dels.add(pageData);
				}
			}
		}
		if(!dels.isEmpty()) {
			pageDataList.removeAll(dels);
		}
		return pageDataList;
	}
	
	private List<PageDataVo> getDifferentgoods(String queryString, int PageSize) {
		WodeSearcher searcher = wsm.getSearcher();
		searcher.setFetchSource(fecthFields, null);
		searcher.setPageSize(PageSize);
		//固定2个属性
		String[] fields = new String[]{"品牌", "价格"};
		WodeResult result = searcher.search(queryString +"&sort=createDate_1", fields, false,false,false);
		List<PageDataVo> pageDataList = new ArrayList<PageDataVo>();
		if(!result.getHits().isEmpty()) {
			for (int i = 0; i < result.getHits().size(); i++) {
				PageDataVo pageDataVo = new PageDataVo();
				//库存
				pageDataVo.setStock(Integer.valueOf(result.getHits().get(i).get("stock").toString()));
				//主图
				pageDataVo.setImagePath(result.getHits().get(i).get("image").toString());
				//指定位置
				pageDataVo.setEx6Value(String.valueOf(i));
				//商品id
				pageDataVo.setLink(result.getHits().get(i).get("productId").toString());
				//商品名称
				pageDataVo.setProName(result.getHits().get(i).get("name").toString());
				//商品内购价
				pageDataVo.setProPrice(result.getHits().get(i).get("salePrice").toString());
				//商品市场价
				pageDataVo.setMarketPrice(result.getHits().get(i).get("price").toString());
				//商品内购券
				pageDataVo.setMaxFucoin(result.getHits().get(i).get("maxFucoin").toString());
				//商品规格id
				pageDataVo.setMinSkuId(result.getHits().get(i).get("minSkuId").toString());
				//商品总库存
				pageDataVo.setAllStock(Integer.valueOf(result.getHits().get(i).get("allStock").toString()));
				
				pageDataList.add(pageDataVo);
			}
		}
		if(pageDataList!=null && pageDataList.size()>0) {
			if(pageDataList.size()>=4 && pageDataList.size()<6) {//取前四个
				pageDataList = pageDataList.subList(0, 4);
			}else if(pageDataList.size()>=6 && pageDataList.size()<7) {//取前6个
				pageDataList = pageDataList.subList(0, 6);
			}
		}
		return pageDataList;
	}
}
