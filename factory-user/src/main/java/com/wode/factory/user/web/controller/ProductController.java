package com.wode.factory.user.web.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.factory.model.Attribute;
import com.wode.factory.model.ClientAccessLog;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.service.CategoryAttributeService;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.user.service.ClientAccessLogService;
import com.wode.factory.user.service.CurrencyService;
import com.wode.factory.user.service.EntParamCodeService;
import com.wode.factory.user.service.ProductService;
import com.wode.factory.user.service.RecommendProductService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.service.UserExchangeTicketService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.CookieUtils;
import com.wode.factory.user.util.IPUtils;
import com.wode.factory.user.util.SessonRedisUtil;
import com.wode.model.CommUser;
import com.wode.search.SearchParams;
import com.wode.search.WodeResult;
import com.wode.search.WodeSearchManager;
import com.wode.search.WodeSearcher;
import com.wode.search.util.StringComparator;

/**
 * 商品
 *
 * @author Bing King
 */
@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private WodeSearchManager wsm;

	@Autowired
	private RedisUtil redisUtil;

	@Resource(name = "productCategoryService")
	private ProductCategoryService catService;

	@Resource(name = "categoryAttributeService")
	private CategoryAttributeService attributeService;

	@Autowired
	private RecommendProductService recommendProductService;

	@Autowired
	private RecommendProductService recommendService;
    @Autowired
    private ClientAccessLogService clientAccessLogService;
	@Autowired
	private EntParamCodeService entParamCodeService;

    @Autowired
    private ProductService productService;
    
    @Qualifier("userService")
	@Autowired
	protected UserService userService;
    
    @Autowired
	private UserBalanceService userBalanceService;

	@Autowired
	private CurrencyService currencyService;
	
	@Autowired
	private UserExchangeTicketService userExchangeTicketService;
	
	private static final String fecthFields[] = {"image", "brand",
		    "name",
		    "salePrice",
		    "maxFucoin",
		    "price",
		    "productId",
		    "minSkuId",
		    "stock",
		    "allStock"};
    
	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
	
	private ProductCategory initCate(String cat, ModelMap model) {
		ProductCategory ret = null;
		String[] cats = cat.split(",");
		List<ProductCategory> trace = new ArrayList<ProductCategory>(3);
		if (cats.length==1 && StringUtils.isNotBlank(cat)) {
			trace = catService.findParents(Long.valueOf(cat));
			//trace.add(catService.findById(Long.valueOf(cat)));
		} else {
			if(StringUtils.isNotBlank(cats[0])) {
				trace = catService.findParents(Long.valueOf(cats[0]));
				for (int i = 1; i < cats.length && trace.size()<3; i++) {
					if(StringUtils.isNotBlank(cats[i])) {
						ProductCategory p=catService.findById(Long.valueOf(cats[i]));
						//父节点 在 列表中
						if(inList(trace,p.getPid())) {
							//当前节点不在列表中
							if(!inList(trace,p.getId())) {
								trace.add(p);
							}
						}
					}
				}
			}
		}

		if (trace != null && trace.size() > 0) {
			List<ProductCategory> children = catService.findSub(trace.get(0));
			if (children != null && children.size() > 0) {
				Map<ProductCategory, List<ProductCategory>> allCats = new HashMap<>();
				for (ProductCategory pc : children) {
					allCats.put(pc, catService.findSub(pc));
				}
				model.addAttribute("cats", allCats);
			}
			ret = trace.get(trace.size() - 1);
			model.put("path", trace);
		}

		return ret;
	}

	/**
	 * 根据id判断是否在列表中
	 * @param ls
	 * @param id
	 * @return
	 */
	private boolean inList(List<ProductCategory> ls,Long id) {

		for (ProductCategory productCategory : ls) {
			if(productCategory.getId().equals(id)) return true;
		}
		return false;
	}
	/**
	 * 末级列表页
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, @RequestParam String cat, ModelMap model) {
		String realCat = catService.getSearchCat(cat);
		ProductCategory category = initCate(realCat, model);

		if (category == null) {
			return "redirect:/";
		}

		String queryString = request.getQueryString().replace("cat="+cat, "cat="+realCat);
		WodeSearcher searcher = wsm.getSearcher();

		//按照分类读取属性
		List<Attribute> attributes = attributeService.findByCategory(category.getId());
		String[] fields = new String[attributes.size()];
		for (int i = 0; i < attributes.size(); i++) {
			if (attributes.get(i).getForSearch() == 1) {
				fields[i] = attributes.get(i).getName();
			}
		}

		WodeResult result = searcher.search(queryString, fields, true,false,false);
		if (result.getAggregations() != null) {
			model.addAttribute("brands", productService.sortArray(result.getAggregations().get("brand")));
			result.getAggregations().remove("brand");
			Map<String, String[]> aggregations = new LinkedHashMap<>();
			for (Map.Entry<String, String[]> entry : result.getAggregations().entrySet()) {
				String[] vals = entry.getValue();
				Arrays.sort(vals, new StringComparator());
				aggregations.put(entry.getKey(), vals);
			}
			model.addAttribute("aggregations", aggregations);
		}
//		String keyword = request.getParameter(SearchParams.Param.KEYWORD);
//		if (result.getHits()!=null && StringUtils.isBlank(request.getParameter(SearchParams.Param.SORTBY))) {
//			Collections.sort(result.getHits(), new HitComparator(keyword));
//		}
		//设置补贴下限
		//result.setCost(entParamCodeService.getBenefitSubsidy().doubleValue());
		model.addAttribute("result", result);
		model.addAttribute("redis", redisUtil);
		model.addAttribute("recommend", recommendProductService.findRecommendByCate(category.getId()));
		List<Product> hotProducts= recommendProductService.findHotSellByCate(category.getId());
		for (Product product : hotProducts) {
			if(product.getProductSpecificationslist() == null || product.getProductSpecificationslist().isEmpty()) {

				product.setShowPrice("<p class=\"p1\">内购价：<span><br />￥"+(StringUtils.isEmpty(product.getShowPrice())?product.getMinprice()+"":product.getMinprice())+"</span></p>");
			} else {
				ProductSpecifications sku = product.getProductSpecificationslist().get(0);
				product.setShowPrice("<p class=\"p1\">内购价：<span><br />￥"+sku.getPrice().subtract(sku.getMaxFucoin()).setScale(2, BigDecimal.ROUND_DOWN)+"</span><em>"
						+sku.getPrice().subtract(sku.getMaxFucoin()).multiply(new BigDecimal(10)).divide(sku.getPrice(), BigDecimal.ROUND_DOWN).setScale(0, BigDecimal.ROUND_DOWN)+"折</em></p>");
				product.setMinprice(sku.getPrice());
			}
		}
		model.addAttribute("hotProducts", hotProducts);
		StringBuffer url = new StringBuffer(request.getRequestURL());
		url.append("?cat=" + cat);
		if (StringUtils.isNotBlank(request.getParameter(SearchParams.Param.SALE_KBN))) {
			url.append("&saleKbn=").append(request.getParameter(SearchParams.Param.SALE_KBN));
		}
		if (StringUtils.isNotBlank(request.getParameter(SearchParams.Param.BRAND))) {
			url.append("&barnd=").append(request.getParameter(SearchParams.Param.BRAND));
		}
		if (StringUtils.isNotBlank(request.getParameter(SearchParams.Param.DISCOUNT))) {
			url.append("&discount=").append(request.getParameter(SearchParams.Param.DISCOUNT));
		}
		if (StringUtils.isNotBlank(request.getParameter(SearchParams.Param.SALE_PRICE))) {
			url.append("&salePrice=").append(request.getParameter(SearchParams.Param.SALE_PRICE));
		}
		url.append(result.getFiltersParam());
		model.addAttribute("url", url.toString());
		model.addAttribute("maxBenefit", entParamCodeService.getBenefitSubsidy());
		clientAccessLogService.saveNormal(ClientAccessLog.ACCESS_TYPE_SEARCH, cat.toString(), "分类", null,null,null,IPUtils.getClientAddress(request),result.getTotalNum().intValue(),result.getMaxScore());
		return "product/list";
	}

	/**
	 * 搜索页面
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search")
	public String search(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
			
		String keyword = request.getParameter(SearchParams.Param.KEYWORD);
		String allCat = request.getParameter(SearchParams.Param.ALL_CAT);
		if (StringUtils.isNotBlank(keyword) || StringUtils.isNotBlank(request.getParameter(SearchParams.Param.SALE_KBN))
				|| StringUtils.isNotBlank(request.getParameter(SearchParams.Param.DISCOUNT))
				|| StringUtils.isNotBlank(request.getParameter(SearchParams.Param.SALE_PRICE))
				|| StringUtils.isNotBlank(request.getParameter(SearchParams.Param.BRAND))
				|| StringUtils.isNotBlank(request.getParameter(SearchParams.Param.SUPPLIERID))
				|| StringUtils.isNotBlank(allCat)) {
			String queryString = request.getQueryString();
			WodeSearcher searcher = wsm.getSearcher();
			//固定2个属性
			String[] fields = new String[]{"品牌", "价格"};
			WodeResult result = searcher.search(queryString, fields, true,true,true);
			if (StringUtils.isNotBlank(keyword)) {
				model.addAttribute("keyword", keyword);
			}
			if (result.getHits()==null || result.getHits().size() == 0) {
				List<Product> productList = recommendService.random();
				
				model.addAttribute("productList", productList);
				return "product/noresult";
			}
//			if (StringUtils.isBlank(request.getParameter(SearchParams.Param.SORTBY))) {
//				Collections.sort(result.getHits(), new HitComparator(keyword));
//			}
			//设置补贴下限
			//result.setCost(entParamCodeService.getBenefitSubsidy().doubleValue());
			if (result.getAggregations() != null) {
				model.addAttribute("brands", productService.sortArray(result.getAggregations().get("brand")));
				result.getAggregations().remove("brand");
				Map<String, String[]> aggregations = new LinkedHashMap<>();
				for (Map.Entry<String, String[]> entry : result.getAggregations().entrySet()) {
					String[] vals = entry.getValue();
					Arrays.sort(vals, new StringComparator());
					aggregations.put(entry.getKey(), vals);
				}
				model.addAttribute("aggregations", aggregations);
			}
			model.addAttribute("result", result);
			model.addAttribute("catService", catService);
			try {
				StringBuffer url = new StringBuffer(request.getRequestURL());
				if (StringUtils.isNotBlank(keyword)) {
					url.append("?key=").append(URLEncoder.encode(keyword, "UTF-8"));
				}
				if (StringUtils.isNotBlank(allCat)) {
					url.append("?allCat=" + allCat);
				}
				if (StringUtils.isNotBlank(request.getParameter(SearchParams.Param.CATEGORY))) {
					url.append("&cat=").append(request.getParameter(SearchParams.Param.CATEGORY));
				}
				if (StringUtils.isNotBlank(request.getParameter(SearchParams.Param.SALE_KBN))) {
					if(url.indexOf("?")==-1) {
						url.append("?saleKbn=").append(request.getParameter(SearchParams.Param.SALE_KBN));
					} else {
						url.append("&saleKbn=").append(request.getParameter(SearchParams.Param.SALE_KBN));
					}
				}
				if (StringUtils.isNotBlank(request.getParameter(SearchParams.Param.DISCOUNT))) {
					if(url.indexOf("?")==-1) {
						url.append("?discount=").append(request.getParameter(SearchParams.Param.DISCOUNT));
					} else {
						url.append("&discount=").append(request.getParameter(SearchParams.Param.DISCOUNT));
					}
				}
				if (StringUtils.isNotBlank(request.getParameter(SearchParams.Param.SUPPLIERID))) {
					if(url.indexOf("?")==-1) {
						url.append("?supplierId=").append(request.getParameter(SearchParams.Param.SUPPLIERID));
					} else {
						url.append("&supplierId=").append(request.getParameter(SearchParams.Param.SUPPLIERID));
					}
				}
				url.append(result.getFiltersParam());
				model.addAttribute("url", url.toString());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (result.getHits() != null && result.getHits().size() > 0) {
				Long cateId = Long.valueOf(((Map) result.getHits().get(0).get("category")).get("id").toString());
				//通过分类id获取分类相关的商品
				List<Product> hotProducts= recommendProductService.findHotSellByCate(cateId);
				for (Product product : hotProducts) {
					if(product.getProductSpecificationslist() == null || product.getProductSpecificationslist().isEmpty()) {

						product.setShowPrice("<p class=\"p1\">内购价：<span><br />￥"+(StringUtils.isEmpty(product.getShowPrice())?product.getMinprice()+"":product.getMinprice())+"</span></p>");
					} else {
						ProductSpecifications sku = product.getProductSpecificationslist().get(0);
						if(sku == null) {
							product.setShowPrice("<p class=\"p1\">内购价：<span><br />￥"+(StringUtils.isEmpty(product.getShowPrice())?product.getMinprice()+"":product.getMinprice())+"</span></p>");
						} else {
							product.setShowPrice("<p class=\"p1\">内购价：<span><br />￥"+sku.getInternalPurchasePrice().setScale(2, BigDecimal.ROUND_DOWN)+"</span><em>"
									+sku.getInternalPurchasePrice().multiply(new BigDecimal(10)).divide(sku.getPrice(), BigDecimal.ROUND_DOWN).setScale(0, BigDecimal.ROUND_HALF_DOWN)+"折</em></p>");
							product.setMinprice(sku.getPrice());
						}
					}
				}
				
				model.addAttribute("recommend", recommendProductService.findRecommendByCate(cateId));
				model.addAttribute("hotProducts", hotProducts);
				 List hits = result.getHits();
			        for (int i = 0; i < hits.size(); i++) {
			        	Map map=(Map)hits.get(i);
			        	Integer tagFlg = MapUtils.getInteger(map, "tagFlg");
			        	if(tagFlg==null){
				        }else if (tagFlg==1) {
				        	map.put("maxFucoin", "0.01");
			        	}
			        }
				model.addAttribute("maxBenefit", entParamCodeService.getBenefitSubsidy());
			}
			UserFactory user = getUser(request, response);
			if (user !=null) {
				model.addAttribute("user", user);
			}
			clientAccessLogService.saveNormal(ClientAccessLog.ACCESS_TYPE_SEARCH, "search", keyword, null,null,CookieUtils.getUUID(request, response),IPUtils.getClientAddress(request),result.getTotalNum().intValue(),result.getMaxScore());
			return "product/search";
		} else {
			return "redirect:/";
		}
	}
	/**
	 * 搜索页面
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/jsonSearch")
	@ResponseBody
	public ActResult<List<HashMap<String, Object>>> jsonSearch(HttpServletRequest request, HttpServletResponse response) {
		String keyword = request.getParameter(SearchParams.Param.KEYWORD);
		if(!com.wode.common.util.StringUtils.isNullOrEmpty(keyword)){
			try {
				keyword = URLDecoder.decode(keyword,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		String allCat = request.getParameter(SearchParams.Param.ALL_CAT);
		if (StringUtils.isNotBlank(keyword) || StringUtils.isNotBlank(request.getParameter(SearchParams.Param.SALE_KBN))
				|| StringUtils.isNotBlank(request.getParameter(SearchParams.Param.DISCOUNT))
				|| StringUtils.isNotBlank(request.getParameter(SearchParams.Param.SALE_PRICE))
				|| StringUtils.isNotBlank(request.getParameter(SearchParams.Param.BRAND))
				|| StringUtils.isNotBlank(request.getParameter(SearchParams.Param.TAG_FLG))
				|| StringUtils.isNotBlank(allCat)) {
			String queryString = request.getQueryString();
			try {
				queryString = URLDecoder.decode(queryString,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			WodeSearcher searcher = wsm.getSearcher();
			//固定2个属性
			String[] fields = new String[]{"品牌", "价格"};
			WodeResult result = searcher.search(queryString, fields, false,false,true);
			
//			if (StringUtils.isBlank(request.getParameter(SearchParams.Param.SORTBY))) {
//				Collections.sort(result.getHits(), new HitComparator(keyword));
//			}
			//result.setCost(entParamCodeService.getBenefitSubsidy().doubleValue());
			ActResult<List<HashMap<String, Object>>> rtn = ActResult.success(result.getHits());
			rtn.setMsg(entParamCodeService.getBenefitSubsidy().toString());
			return rtn;
		} else {
			return ActResult.fail("参数错误");
		}
	}
	/**
	 * 搜索页面带分页结果
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/jsonPagingSearch")
	@ResponseBody
	public ActResult<Object> jsonPagingSearch(HttpServletRequest request, HttpServletResponse response) {
		String keyword = request.getParameter(SearchParams.Param.KEYWORD);
		if(!com.wode.common.util.StringUtils.isNullOrEmpty(keyword)){
			try {
				keyword = URLDecoder.decode(keyword,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		String allCat = request.getParameter(SearchParams.Param.ALL_CAT);
		if (StringUtils.isNotBlank(keyword) || StringUtils.isNotBlank(request.getParameter(SearchParams.Param.SALE_KBN))
				|| StringUtils.isNotBlank(request.getParameter(SearchParams.Param.DISCOUNT))
				|| StringUtils.isNotBlank(request.getParameter(SearchParams.Param.SALE_PRICE))
				|| StringUtils.isNotBlank(request.getParameter(SearchParams.Param.BRAND))
				|| StringUtils.isNotBlank(request.getParameter(SearchParams.Param.TAG_FLG))
				|| StringUtils.isNotBlank(allCat)) {
			String queryString = request.getQueryString();
			try {
				queryString = URLDecoder.decode(queryString,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			WodeSearcher searcher = wsm.getSearcher();
			if(StringUtils.isNotBlank(request.getParameter("pageSize"))) {
				searcher.setPageSize(Integer.valueOf(request.getParameter("pageSize")));
			}
			//固定2个属性
			String[] fields = new String[]{"品牌", "价格"};
			WodeResult result = searcher.search(queryString, fields, false,false,true);
			
//			if (StringUtils.isBlank(request.getParameter(SearchParams.Param.SORTBY))) {
//				Collections.sort(result.getHits(), new HitComparator(keyword));
//			}
			//result.setCost(entParamCodeService.getBenefitSubsidy().doubleValue());
			ActResult<Object> rtn = ActResult.success(result);
			rtn.setMsg(entParamCodeService.getBenefitSubsidy().toString());
			return rtn;
		} else {
			return ActResult.fail("参数错误");
		}
	}
	/**
	 * 搜索页面
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getDifferentgoods")
	@ResponseBody
	public ActResult<List<HashMap<String, Object>>> getDifferentgoods(HttpServletRequest request, HttpServletResponse response,String type,String comId,Double userMoney) {
		if(com.wode.common.util.StringUtils.isNullOrEmpty(type)) {
			return ActResult.fail("参数错误");
		}
		
		String queryString = null;
		if("1".equals(type)) {//全部商品(普通内购商品)
			queryString="saleKbn=8";
		}else if("2".equals(type)){//特省商品
			queryString="saleKbn=1";
		}else if("3".equals(type)){//换领商品
			//计算换领总额
//			List<UserExchangeTicket> userExchangeList = userExchangeTicketService.usingTicket(user.getId());
//			BigDecimal total = new BigDecimal(0.00);//换领币
//			if(null != userExchangeList && userExchangeList.size() >0) {
//				for (UserExchangeTicket userExchangeTicket : userExchangeList) {
//					total = total.add(userExchangeTicket.getEmpAvgAmount().subtract(userExchangeTicket.getActiveAmount()));
//				}
//			}
			queryString="saleKbn=2";
		}else if("4".equals(type)) {//试用
			queryString="saleKbn=5";
			
		}else if("5".equals(type)) {//自家专享
			queryString="saleKbn=4&supplierId=" +comId;
		}else if("7".equals(type)) {//自家商品
			queryString="supplierId=" + comId;
		}else if("6".equals(type)){//搜索现金券匹配的商品普通商品
			// 现金券
			if(userMoney==null) {
				userMoney=0D;
			}
//			List<UserBalance> list = userBalanceService.findByUser(user.getId());
//			for (UserBalance ub : list) {
//				Currency currency = currencyService.getById(ub.getCurrencyId());
//				if (currency != null) {
//					if("balance".equals(currency.getName())) {
//						userMoney = ub.getBalance().doubleValue();
//					}
//				}
//			}
			queryString="saleKbn=8&salePrice=0-"+userMoney;
		}
		if(com.wode.common.util.StringUtils.isNullOrEmpty(queryString)) {
			return ActResult.fail("参数错误");
		}else {			
			WodeSearcher searcher = wsm.getSearcher();
			searcher.setFetchSource(fecthFields, null);
			searcher.setPageSize(8);
			//固定2个属性
			String[] fields = new String[]{"品牌", "价格"};
			WodeResult result = searcher.search(queryString + ("1".equals(type)?"":"&sort=createDate_1"), fields, false,false,false);
			ActResult<List<HashMap<String, Object>>> rtn = ActResult.success(result.getHits());
			rtn.setMsg(entParamCodeService.getBenefitSubsidy().toString());
			return rtn;
		}
	}
	
	protected UserFactory getUser(HttpServletRequest request, HttpServletResponse response) {
		
		//已经取过
		UserFactory user=null;
		String uuid = CookieUtils.getUUID(request, response);
		//session 中取
		user = SessonRedisUtil.getLoginUser(uuid, redisUtil,userService);
		
		//session 中取不到
		if(user == null) {

			//通过userId取
			String uid = SessonRedisUtil.getLoginId(request, response, redisUtil);
			if(!StringUtils.isEmpty(uid)) {
				user = userService.getById(NumberUtil.toLong(uid));
			}
			
			//通过ticket取
			if(user == null) {
				String user_ticket= CookieUtils.getTicket(request) ;//获取cookie中ticket
				ActResult<UserFactory> act = getUserByTicket(user_ticket);
				if(act!=null && act.isSuccess()) {
					user=act.getData();
				}
			}
			
			//存入session
			SessonRedisUtil.setLoginId(uuid, user, redisUtil);
		}
		return user;
	}

	//通过ticket获取用户信息
	protected ActResult<UserFactory> getUserByTicket(String user_ticket) {
		if(!StringUtils.isEmpty(user_ticket)) {
			//共通用户中查询
			ActResult<CommUser> ar = us.hasLogin(user_ticket);
			if(ar.isSuccess()){
				CommUser cuser = ar.getData();
				//判断本地是否存在
				UserFactory user = userService.getById(cuser.getUserId());
				if(user==null){
					//不存在是copy到本地
					user = new UserFactory();
					user.setId(ar.getData().getUserId());
					user.setUserName(cuser.getUserName());
					user.setEmail(cuser.getUserEmail());
					user.setEnabled(cuser.getEnabled());
					user.setUsable(cuser.getUsable());
					user.setCreatTime(new Date());
					user.setNickName(cuser.getNickName());
					user.setEnabled(1);
					user.setType(1);
					user.setPhone(cuser.getUserPhone());
					userService.specialSave(user);
				}
				
				return ActResult.success(user);
			}
		}
		
		return null;
	}
}
