/**
 *
 */
package com.wode.factory.user.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.PageSetting;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.Specification;
import com.wode.factory.model.UserShare;
import com.wode.factory.service.BrandService;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.service.ProductService;
import com.wode.factory.user.service.ClientAccessLogService;
import com.wode.factory.user.service.InventoryService;
import com.wode.factory.user.service.PageSectionDataService;
import com.wode.factory.user.service.PageSetService;
import com.wode.factory.user.service.PageSettingService;
import com.wode.factory.user.service.ProductDetailListService;
import com.wode.factory.user.service.ShopService;
import com.wode.factory.user.service.UserShareService;
import com.wode.factory.user.util.FreeMarkerUtil;
import com.wode.factory.user.vo.PageSectionDataVo;
import com.wode.factory.user.vo.PageSettingVo;
import com.wode.factory.user.vo.ProductDetailVo;
import com.wode.factory.user.vo.ProductSpecificationsVo;
import com.wode.factory.vo.ProductVo;

/**
 * @author xiehaisheng
 */
@Controller
@RequestMapping("/creatHtml")
public class HtmlAction {
	@Autowired
	private PageSetService pageSetService;
	@Autowired
	private PageSectionDataService pageSectionDataService;
	@Autowired
	private PageSettingService pageSettingService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private ProductService productService;

	@Autowired
	private BrandService brandVoService;

	@Autowired
	private ProductCategoryService productCateService;

	@Autowired
	private ProductDetailListService productDetailListService;

	@Qualifier("redis")
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private DBUtils dbUtils;
	@Autowired
	private UserShareService userShareService;

	@Qualifier("pagePathMap")
	@Resource
	private Map<String, String> pagePathMap;
    @Autowired
    private ClientAccessLogService clientAccessLogService;
    
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductLadderService productLadderService;
	private static Logger logger = LoggerFactory.getLogger(HtmlAction.class);

	@RequestMapping("/index")	
	@ResponseBody
	public ActResult createIndex(HttpServletRequest request, String pram, Boolean preView) {
		ServletContext context = request.getSession().getServletContext();
		ActResult<String> ret = ActResult.fail("");

		try {
			List<PageSettingVo> list = pageSetService.findByPidAndChannel(1,1);//加载所有首页设置
			Map<String, List<PageSettingVo>> pageMap = new HashMap<String, List<PageSettingVo>>();
			Map<String, List<List<PageSettingVo>>> productMap = new HashMap<String, List<List<PageSettingVo>>>();
			Map<String, List<PageSettingVo>> pageMapIndex = new HashMap<String, List<PageSettingVo>>();
			List<PageSettingVo> pageList = null;
			List<PageSettingVo> pageListIndex = null;
			List<List<PageSettingVo>> productList = null;
			PageSettingVo pageVo = null;
			for (int i = 0; i < list.size(); i++) {
				pageVo = list.get(i);
				//单品图片处理
				if((pageVo.getName().endsWith("f_image") || pageVo.getName().endsWith("f_product_index") || pageVo.getName().equals("special_product")  || pageVo.getName().equals("index_brand_product")) 
						&& !StringUtils.isEmpty(pageVo.getLink())) {
					
					if(!pageVo.getLink().contains("/") && !pageVo.getLink().endsWith(".html")) {
						try{
							Map<String, String> map = redisUtil.getMap(RedisConstant.PRODUCT_PRE + pageVo.getLink());
							Product p = JsonUtil.getObject(map.get(RedisConstant.PRODUCT_REDIS_INFO), Product.class);
							if(p!=null) {
								if(StringUtils.isEmpty(pageVo.getImagePath())){
									pageVo.setImagePath(p.getImage());
								}
								pageVo.setLink("/" + p.getId() + ".html?pageKey=index");
								pageVo.setSaleKbn(p.getSaleKbn());
								pageVo.setSaleNote(p.getSaleNote());

								if(p.getProductSpecificationslist()!=null && !p.getProductSpecificationslist().isEmpty()) {
									ProductSpecifications sku = p.getProductSpecificationslist().get(0);
									//新版内购价
									BigDecimal internalPurchasePrice = sku.getInternalPurchasePrice();
									//阶梯价格
									List<ProductLadder> productLadderlist = productLadderService.getListBySkuid(sku.getId());
									if(productLadderlist != null && !productLadderlist.isEmpty()){
										BigDecimal productLadderPrice = productLadderlist.get(0).getPrice();//最小的阶梯价格
										if(internalPurchasePrice.compareTo(productLadderPrice)==1){
											internalPurchasePrice = productLadderPrice;
										}
										
									}
									if(internalPurchasePrice !=null){
										pageVo.setProPrice("内购价：￥"+internalPurchasePrice.setScale(2,BigDecimal.ROUND_DOWN));
									}else{
										pageVo.setProPrice("内购价：￥" + sku.getPrice().subtract(sku.getMaxFucoin()).setScale(2,BigDecimal.ROUND_DOWN));
									}
									pageVo.setProSale(internalPurchasePrice.multiply(new BigDecimal(10)).divide(sku.getPrice(),1,BigDecimal.ROUND_DOWN)+"折");
									pageVo.setProDescription("电商价：￥"+sku.getPrice().setScale(2,BigDecimal.ROUND_DOWN).toString());
		
								}
								if(pageVo.getName().equals("special_product")) {
									pageVo.setProName(p.getImage());
								}
								if(StringUtils.isEmpty(pageVo.getTitle())){
									pageVo.setTitle(p.getFullName());
								}
							} else {
								continue;
							}
						} catch(Exception e) {
							logger.debug(pageVo.getLink()+":商品获取失败");
							continue;
						}
					} else {
						String link = pageVo.getLink();
						if(!StringUtils.isEmpty(link)) {
							pageVo.setLink(link.replace("http://wd-w.com", ""));
						}
					}
				} else {
					String link = pageVo.getLink();
					if(!StringUtils.isEmpty(link)) {
						pageVo.setLink(link.replace("http://wd-w.com", ""));
					}
				}
				if(pageVo.getName().endsWith("f_product_index")) {
					if(pageVo.getTitle()==null) continue;
					String[] proInfoArray = pageVo.getTitle().split("-");
					if (!StringUtils.isEmpty(proInfoArray) && proInfoArray.length > 3) {
						pageVo.setProName(proInfoArray[0]);
						
						if(StringUtils.isEmpty(pageVo.getProPrice())) {
							pageVo.setProPrice(proInfoArray[1]);
							pageVo.setProDescription(proInfoArray[2]);
						}
						
						if (pageMapIndex.containsKey(pageVo.getName()+"@"+proInfoArray[3])) {
							pageListIndex = pageMapIndex.get(pageVo.getName()+"@"+proInfoArray[3]);
						} else {
							pageListIndex = new ArrayList<PageSettingVo>();
							pageMapIndex.put(pageVo.getName()+"@"+proInfoArray[3], pageListIndex);
						}
						pageListIndex.add(pageVo);
					} else {
						continue;
					}					
				} else {
					if(pageVo.getName().endsWith("f_image")){
						if(pageVo.getTitle()==null) continue;
						String[] proInfoArray = pageVo.getTitle().split("-");
						if (!StringUtils.isEmpty(proInfoArray) && proInfoArray.length > 2) {
							pageVo.setProName(proInfoArray[0]);
							if(StringUtils.isEmpty(pageVo.getProPrice())) {
								pageVo.setProPrice(proInfoArray[1]);
								pageVo.setProDescription(proInfoArray[2]);
							}
						}
					}

					if (pageMap.containsKey(pageVo.getName())) {
						pageList = pageMap.get(pageVo.getName());
						pageList.add(pageVo);
					} else {
						pageList = new ArrayList<PageSettingVo>();
						pageList.add(pageVo);
						pageMap.put(pageVo.getName(), pageList);
					}
				}				
			}
			
			List<String> lstIndexKey = new ArrayList<String>();
			lstIndexKey.addAll(pageMapIndex.keySet());
			Collections.sort(lstIndexKey, new Comparator<String>() {
				public int compare(String arg0, String arg1) {
					if(arg0==null) return -1;
					return arg0.compareTo(arg1);
				}
			});
			for (String string : lstIndexKey) {
				String key = string.substring(0, string.indexOf("@"));

				if (productMap.containsKey(key)) {
					productList = productMap.get(key);
				} else {
					productList = new ArrayList<List<PageSettingVo>>();
					productMap.put(key, productList);
				}
				productList.add(pageMapIndex.get(string));
			}

			//Map<String, List<ProductCategory>> categoriesMap = new HashMap<String, List<ProductCategory>>();//2、3级分类集合
			List<ProductCategory> categoriesList = productCateService.findRoot();//1级分类

//			if (null != categoriesList && categoriesList.size() > 0) {
//				for (ProductCategory cate : categoriesList) {
//					List<ProductCategory> secondCates = productCateService.findSub(cate);//2级分类
//					categoriesMap.put(cate.getRootId() + cate.getBrotherOrderAll(), secondCates);
//					for (ProductCategory secondCate : secondCates) {
//						List<ProductCategory> thirdCates = productCateService.findSub(secondCate);//3级分类
//						categoriesMap.put(secondCate.getRootId() + secondCate.getBrotherOrderAll(), thirdCates);
//					}
//				}
//			} else {
//				throw new Exception("商品分类信息为空");
//			}

			// 品牌处理
			List<PageSettingVo> indexBrands =pageMap.get("index_brand");
			List<JSONObject> lsBrand = new ArrayList<JSONObject>();
			if(indexBrands != null) {
				for(int i=0;i<indexBrands.size();i++) {
					JSONObject jo = new JSONObject();
					jo.put("img", indexBrands.get(i).getImagePath());
					jo.put("ttl", indexBrands.get(i).getTitle());
					jo.put("link", indexBrands.get(i).getLink());
					lsBrand.add(jo);
				}
			}
			String jsBrands = "<script type=\"text/javascript\">var brandJson="+JsonUtil.toJsonString(lsBrand)+";</script>";
			
			Map<String, Object> data = new HashMap<String, Object>();
			String templatePath = "templet/1.ftl";
			Map<String,Object> pageParam = new HashMap<String,Object>();
			data.put("pageParam", pageParam);
			data.put("productMap", productMap);
			data.put("indexBrands", jsBrands);
			data.put("pageMap", pageMap);
			//data.put("categoriesMap", categoriesMap);
			data.put("categoriesList", categoriesList);
			data.put("version", dbUtils.CreateID()+"");
			String htmlPath = pagePathMap.get("index");
			if (StringUtils.isEmpty(htmlPath)) {
				htmlPath = context.getRealPath("/");
			}

			if (!StringUtils.isEmpty(pram)) {
				htmlPath += "_" + pram;
			}
			if (preView != null && preView) {
				htmlPath += "_preView";
			}
			
			String str = FreeMarkerUtil.createString(context, data, "UTF-8", templatePath);
			if (!StringUtils.isEmpty(str)) {
				FreeMarkerUtil.createHTML(context, data, templatePath, htmlPath + "main.html");
				ret.setSuccess(true);
				ret.setData(htmlPath);
				ret.setMsg("生成首页成功");
				
				String[] aryCss = {"","blue_btm","green_btm","yellow_btm","pink_btm","gray_btm","purple_btm","orange_btm","olive_btm","LightBlue_btm","peru_btm"};
				for (int i = 1; i < 11; i++) {
					if(pageMap.containsKey(i+"f_text")) {
						Map<String, Object> f_data = new HashMap<String, Object>();
						f_data.put("f_index", i);
						f_data.put("f_css", aryCss[i]);
						f_data.put("textList", pageMap.get(i+"f_text"));
						f_data.put("imageList", pageMap.get(i+"f_image"));
						f_data.put("imageTextList", pageMap.get(i+"f_image_text"));
						f_data.put("productList", productMap.get(i+"f_product_index"));
						f_data.put("brandList", pageMap.get(i+"f_brand"));
						
						FreeMarkerUtil.createHTML(context, f_data, "templet/1_f.ftl", htmlPath +"index_"+i+"f.html");
					}
				}
			}
		} catch (Exception e) {
			ret.setMsg("系统异常:" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return ret;
	}


	/**
	 * 生成目录文件
	 */
	@RequestMapping("/category")
	public
	@ResponseBody
	ActResult createCatalog(HttpServletRequest request) {
		ServletContext context = request.getSession().getServletContext();
		ActResult<String> ret = new ActResult<String>();
		ret.setSuccess(false);
		try {
			Map<String, List<ProductCategory>> categoriesMap = new HashMap<String, List<ProductCategory>>();//2、3级分类集合
			List<ProductCategory> categoriesList = productCateService.findRoot();//1级分类

			if (null != categoriesList && categoriesList.size() > 0) {
				for (ProductCategory cate : categoriesList) {
					List<ProductCategory> secondCates = productCateService.findSub(cate);//2级分类
					categoriesMap.put(cate.getRootId() + cate.getBrotherOrderAll(), secondCates);
					for (ProductCategory secondCate : secondCates) {
						List<ProductCategory> thirdCates = productCateService.findSub(secondCate);//3级分类
						categoriesMap.put(secondCate.getRootId() + secondCate.getBrotherOrderAll(), thirdCates);
					}
				}
			} else {
				throw new Exception("商品分类信息为空");
			}

			Map<String, Object> data = new HashMap<String, Object>();
			String templatePath = "templet/category.ftl";
			Map<String,Object> pageParam = new HashMap<String,Object>();
			data.put("pageParam", pageParam);
			data.put("categoriesMap", categoriesMap);
			data.put("categoriesList", categoriesList);
			String htmlPath = pagePathMap.get("category");
			if (StringUtils.isEmpty(htmlPath)) {
				htmlPath = context.getRealPath("/");
			}
			htmlPath += "category.html";
			String str = FreeMarkerUtil.createString(context, data, "UTF-8", templatePath);
			if (!StringUtils.isEmpty(str)) {
				FreeMarkerUtil.createHTML(context, data, templatePath, htmlPath);
				ret.setSuccess(true);
				ret.setData(htmlPath);
				ret.setMsg("生成分类文件成功");
			}
		} catch (Exception e) {
			ret.setMsg("系统异常:" + e.getLocalizedMessage());
			ret.setMsg(e.getLocalizedMessage());
		}
		return ret;
	}


	/**
	 * 生成静态产品详情页
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/product")
	public
	@ResponseBody
	ActResult productDetail(HttpServletRequest request, Long productId) {
		ServletContext context = request.getSession().getServletContext();
		ActResult<String> ret = new ActResult<String>();
		ret.setSuccess(false);
		ret.setData("");
		try {

			ProductVo pvo = productService.findById(productId, true);

			if (pvo == null) {
				ret.setMsg(productId + "的商品不存在");
				return ret;

			}

			ProductDetailVo productDetailVo = new ProductDetailVo();


			List<ProductDetailList> pdlList = new ArrayList<ProductDetailList>();

			BeanUtils.copyProperties(pvo, productDetailVo);

			productDetailVo.setSmap(productService.findSku4Show(productId));

			productDetailVo.setPsiMap(productService.findSkuImg(productId));

			productDetailVo.setSupplierShopVo(shopService.findShopByShopIdCache(pvo.getShopId()));

			ProductBrand pb = brandVoService.selectById(pvo.getBrandId());

			if (StringUtils.isEmpty(pb.getName())) {
				pb.setName(pb.getNameEn());
			}

			pdlList = productDetailListService.selectByProductId(productId);

			//查询最低价格的商品SKU集合
			List<ProductSpecifications> psList = productService.findByMinprice(productId, productDetailVo.getMinprice());
			if (StringUtils.isEmpty(psList)) {
				ret.setMsg("失败:SKU为空");
				return ret;
			}
			Map<String, String> strMap = new LinkedHashMap<String, String>();
			int order = 0;
			String itemids = "";
			for (int i = 0; i < psList.size(); i++) {
				itemids += psList.get(i).getItemids() + ",";
			}
			String[] subitems = itemids.split(",");
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < subitems.length; i++) {
				if (!list.contains(subitems[i])) {
					list.add(subitems[i]);
				}
			}

			for (int i = 0; i < list.size(); i++) {
				Specification s = productService.findByItemsValue(list.get(i));
				List<ProductSpecificationValue> psvList = productService.findSKUValue(productId, s.getId());
				for (ProductSpecificationValue psv : psvList) {
					if (list.get(i).equals(psv.getId().toString())) {
						if (StringUtils.isEmpty(strMap.get(s.getName()))) {
							strMap.put(s.getName(), list.get(i));
							order = StringUtils.isEmpty(psv.getOrders()) ? 0 : psv.getOrders();
						}
						if (order > psv.getOrders()) {
							strMap.put(s.getName(), list.get(i));
							order = StringUtils.isEmpty(psv.getOrders()) ? 0 : psv.getOrders();
						}
					}
					continue;
				}
			}

			Map<String, Object> data = new HashMap<String, Object>();
			String templatePath = "templet/product_detail.ftl";
			Map<String,Object> pageParam = new HashMap<String,Object>();
			data.put("pageParam", pageParam);
			data.put("mainPriceSKU", strMap);
			data.put("product", productDetailVo);
			data.put("smap", productDetailVo.getSmap());
			data.put("pamap", productService.findAttr(productId, pvo.getCategoryId(), true));
			data.put("paramap", productService.findPar(productId, pvo.getCategoryId(), true));
			data.put("supplierShop", productDetailVo.getSupplierShopVo());
			data.put("cateid", productDetailVo.getCategoryId());

			data.put("imgmap", productDetailVo.getPsiMap());
			data.put("clist", productCateService.findParents(pvo.getCategoryId()));
			data.put("productBrand", pb);
			data.put("pdlList", pdlList);
			String limitText = "";
			if(pvo.getLimitCnt()!=null && pvo.getLimitCnt()>0) {
				limitText="每用户限购"+pvo.getLimitCnt()+"件,";				
			} 
			if(pvo.getAreasCode()!=null && !"0".equals(pvo.getAreasCode())) {
				limitText +="销售区域："+pvo.getAreasName();				
			}
			if(!"".equals(limitText)){
				limitText="<li> <span class=\"sm_metatit\">限&nbsp;&nbsp;购：</span>"+
						"<div class=\"sm_postage\">"+limitText+"</div></li>";
			}			
			data.put("limitText", limitText);
			
			
			String htmlPath = pagePathMap.get("product");
			if (StringUtils.isEmpty(htmlPath)) {
				htmlPath = context.getRealPath("/");
			}
			htmlPath += productId + ".html";
			String str = FreeMarkerUtil.createString(context, data, "UTF-8", templatePath);
			if (!StringUtils.isEmpty(str)) {
				FreeMarkerUtil.createHTML(context, data, templatePath, htmlPath);
				ret.setSuccess(true);
				ret.setData(htmlPath);
				ret.setMsg("生成产品详情页成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.setMsg("失败:" + e.getLocalizedMessage());
		}
		return ret;
	}
	
	/**
	 * 作废页面生成404错误页面
	 * @param request
	 * @param pid
	 * @param pram
	 * @param preView
	 * @return
	 */
	@RequestMapping("/destroyPage{pid}")	
	@ResponseBody
	public ActResult destroyPage(HttpServletRequest request, @PathVariable Long pid, String pram, Boolean preView) {
		ServletContext context = request.getSession().getServletContext();
		ActResult<String> ret = ActResult.fail("");

		try {
			PageSetting page =pageSettingService.getById(pid);
			
			Map<String, Object> data = new HashMap<String, Object>();
			
			String templatePath = "templet/error1.ftl";
			Map<String,Object> pageParam = new HashMap<String,Object>();
			data.put("pageParam", pageParam);
			
			pageParam.put("page_key", page.getPageKey());
			String htmlPath = pagePathMap.get("index");
			if(page.getUrl().contains("api.wd-w.com") || page.getUrl().contains("m.wd-w.com")) {
				pageParam.put("page_url", "http://api.wd-w.com/index_m.htm");
				htmlPath = pagePathMap.get("index_m");
			}else {
				pageParam.put("page_url", "http://www.wd-w.com");
			}
			boolean previewflag = false;
			if (StringUtils.isEmpty(htmlPath)) {
				htmlPath = context.getRealPath("/");
			}
			htmlPath += page.getPageKey();
			
			if (!StringUtils.isEmpty(pram)) {
				htmlPath += "_" + pram;
			}
			
			String str = FreeMarkerUtil.createString(context, data, "UTF-8", templatePath);
			if (!StringUtils.isEmpty(str)) {
				if(("index".equals(page.getPageKey())||"newIndex".equals(page.getPageKey())) && 2==page.getChannel() && !previewflag){
					htmlPath = htmlPath.replace("index", "index_m").replace("newIndex","index_m");
					FreeMarkerUtil.createHTML(context, data, templatePath, htmlPath + ".htm");
				}else{
					FreeMarkerUtil.createHTML(context, data, templatePath, htmlPath + ".html");
				}
				ret.setSuccess(true);
				ret.setData(htmlPath);
				ret.setMsg(page.getTitle() + " 页面生成成功");
				
			}
		} catch (Exception e) {
			ret.setMsg("系统异常:" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return ret;
	}
	
	@RequestMapping("/page{pid}")	
	@ResponseBody
	public ActResult createPage(HttpServletRequest request, @PathVariable Long pid, String pram, Boolean preView) {
		ServletContext context = request.getSession().getServletContext();
		ActResult<String> ret = ActResult.fail("");

		try {
			PageSetting page =pageSettingService.getById(pid);
			List<PageSectionDataVo> list= pageSectionDataService.selectByPageId(pid);
			
			Map<String, List<PageSectionDataVo>> pageMap = new HashMap<String, List<PageSectionDataVo>>();

			Map<String, Object> data = new HashMap<String, Object>();
			List<PageSectionDataVo> pageList = null;
			PageSectionDataVo pageVo = null;
			for (int i = 0; i < list.size(); i++) {
				pageVo = list.get(i);
				//单品图片处理
				if(pageVo.isLinkProduct()) {
				
					try{
						Map<String, String> map = redisUtil.getMap(RedisConstant.PRODUCT_PRE + pageVo.getProductId());
						Product p = JsonUtil.getObject(map.get(RedisConstant.PRODUCT_REDIS_INFO), Product.class);
						if(p!=null) {
							setDefaultSku(pageVo, p,page.getPageKey());
							
							if(StringUtils.isEmpty(pageVo.getTitle())){
								pageVo.setTitle(p.getFullName());
							}
							
							if("show_brand".equals(pageVo.getEx1Value()) || "show_brand".equals(pageVo.getEx2Value()) || "show_brand".equals(pageVo.getEx3Value())
									|| "show_brand".equals(pageVo.getEx4Value())|| "show_brand".equals(pageVo.getEx5Value())|| "show_brand".equals(pageVo.getEx6Value())) {
								pageVo.setProBrand(p.getBrandName());
							}
						} else {
							continue;
						}
					} catch(Exception e) {
						logger.debug(pageVo.getLink()+":商品获取失败");
						continue;
					}
				}
				
				// 通过分类ID 获取分类信息
				if("categoryId".equals(pageVo.getSectionName())) {
					if(!data.containsKey("category")) {
						if(!StringUtils.isEmpty(pageVo.getEx1Value())) {

							ProductCategory category = productCateService.findById(Long.parseLong(pageVo.getEx1Value()));
							Map<String, List<ProductCategory>> categoriesMap = new HashMap<String, List<ProductCategory>>();//2、3级分类集合
							if (null != category) {
								List<ProductCategory> secondCates = productCateService.findSub(category);//2级分类
								categoriesMap.put(category.getRootId() + category.getBrotherOrderAll(), secondCates);
								for (ProductCategory secondCate : secondCates) {
									List<ProductCategory> thirdCates = productCateService.findSub(secondCate);//3级分类
									categoriesMap.put(secondCate.getRootId() + secondCate.getBrotherOrderAll(), thirdCates);
								}
								
								data.put("category", category);
								data.put("categoriesMap", categoriesMap);
							} else {
								throw new Exception("商品分类信息为空");
							}
						}
					}
					continue;
				}

				if (pageMap.containsKey(pageVo.getSectionName())) {
					pageList = pageMap.get(pageVo.getSectionName());
					pageList.add(pageVo);
				} else {
					pageList = new ArrayList<PageSectionDataVo>();
					pageList.add(pageVo);
					pageMap.put(pageVo.getSectionName(), pageList);
				}			
			}
			
			String templatePath = "templet/"+ page.getFtlFile();
			Map<String,Object> pageParam = new HashMap<String,Object>();
			data.put("pageParam", pageParam);
			pageParam.put("page_key", page.getPageKey());
			pageParam.put("page_ex1Value", page.getEx1Value());
			pageParam.put("page_ex2Value", page.getEx2Value());
			pageParam.put("page_ex3Value", page.getEx3Value());
			pageParam.put("page_ex4Value", page.getEx4Value());
			if(!StringUtils.isEmpty(page.getSupplierId())) {
				pageParam.put("supplier_id", page.getSupplierId()+"");
				pageParam.put("supplier_name", page.getSupplierName());
			}
			
			data.put("pageMap", pageMap);
			String htmlPath = pagePathMap.get("index");
			if(page.getUrl().contains("api.wd-w.com") || page.getUrl().contains("m.wd-w.com")) {
				htmlPath = pagePathMap.get("index_m");
			}
			boolean previewflag = false;
			if (StringUtils.isEmpty(htmlPath)) {
				htmlPath = context.getRealPath("/");
			}
			htmlPath += page.getPageKey();
			
			if (!StringUtils.isEmpty(pram)) {
				htmlPath += "_" + pram;
			}
			if (preView != null && preView) {
				htmlPath += "_preView";
				previewflag =true;
			}
			if(previewflag){
				pageParam.put("page_preview", "1");
			} else {
				pageParam.put("page_preview", "0");
			}
			
			boolean hasMore =false;
			if(pageMap.containsKey("pc_more_button")) {
				hasMore=true;
				// 搜索更多
				List<PageSectionDataVo> datas = pageMap.get("pc_more_button");
				pageParam.put("hasMore", datas.get(0).getTitle());
				datas.get(0).setTitle(datas.get(0).getTitle().replace("搜", "更多"));
			}
			String str = FreeMarkerUtil.createString(context, data, "UTF-8", templatePath);
			if (!StringUtils.isEmpty(str)) {
				if(("index".equals(page.getPageKey())||"newIndex".equals(page.getPageKey())) && 2==page.getChannel() && !previewflag){
					htmlPath = htmlPath.replace("index", "index_m").replace("newIndex","index_m");
					FreeMarkerUtil.createHTML(context, data, templatePath, htmlPath + ".htm");
				}else{
					FreeMarkerUtil.createHTML(context, data, templatePath, htmlPath + ".html");
				}
				ret.setSuccess(true);
				ret.setData(htmlPath);
				ret.setMsg(page.getTitle() + " 页面生成成功");
				
				if(hasMore) {
					FreeMarkerUtil.createHTML(context, data, templatePath.replace(".ftl", "_more.ftl"), htmlPath+"_more.html");
				}
			}
		} catch (Exception e) {
			ret.setMsg("系统异常:" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping("/shareTargetJs")	
	@ResponseBody
	public ActResult shareTargetJs(HttpServletRequest request) {
		ServletContext context = request.getSession().getServletContext();
		ActResult<String> ret = ActResult.fail("");

		try {
			UserShare query=new UserShare();
			query.setShareType(9);
			query.setTargetActionUrl("NOT NULL");
			
			List<UserShare> shares= userShareService.selectByModel(query);
			for (int i = shares.size()-1; i >=0 ; i--) {
				if(StringUtils.isEmpty(shares.get(0).getTargetActionUrl())) {
					shares.remove(i);
				}
			}
			
			String templatePath = "templet/m_share_target_js.ftl";
			String htmlPath = pagePathMap.get("index_m");
			if (StringUtils.isEmpty(htmlPath)) {
				htmlPath = context.getRealPath("/");
			}

			Map<String, List<UserShare>> pageMap = new HashMap<String, List<UserShare>>();
			pageMap.put("shares", shares);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("pageMap", pageMap);
			String str = FreeMarkerUtil.createString(context, data, "UTF-8", templatePath);
			if (!StringUtils.isEmpty(str)) {
				FreeMarkerUtil.createHTML(context, data, templatePath, htmlPath + "static_resources/js/share_target.js");
				ret.setSuccess(true);
				ret.setData(htmlPath);
				ret.setMsg("share target js生成成功");
				redisUtil.setData(RedisConstant.USER_SHARE_TARGET_JS_VERSION, dbUtils.CreateID()+"");
			}
		} catch (Exception e) {
			ret.setMsg("系统异常:" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return ret;
	}
	
	private void setDefaultSku(PageSectionDataVo pageVo, Product p,String pageKey) {

		ProductSpecifications sku =null;
		
		if("skuId".equals(pageVo.getEx1Name()) || "skuId".equals(pageVo.getEx2Name()) || "skuId".equals(pageVo.getEx3Name())) {
			String skuId = "";
			if("skuId".equals(pageVo.getEx1Name())) {
				skuId = pageVo.getEx1Value();
			} else if("skuId".equals(pageVo.getEx2Name())) {
				skuId = pageVo.getEx2Value();
			} else if("skuId".equals(pageVo.getEx3Name())) {
				skuId = pageVo.getEx3Value();
			}
			
			if(!StringUtils.isEmpty(skuId)) {

				String jsonS=redisUtil.getMapData(RedisConstant.PRODUCT_PRE+p.getId(), RedisConstant.PRODUCT_REDIS_SKU);
				if(!StringUtils.isEmpty(jsonS)){
					List<ProductSpecificationsVo> skus=JsonUtil.getList(jsonS, ProductSpecificationsVo.class);
					for (ProductSpecificationsVo productSpecifications : skus) {
						if(skuId.equals(productSpecifications.getId().toString())) {
							sku=productSpecifications;
							break;
						}
					}
				}
			}
		}
		
		if(sku==null) {
			if(p.getProductSpecificationslist()!=null && !p.getProductSpecificationslist().isEmpty()) {
				sku = p.getProductSpecificationslist().get(0);
			}
		}
		String pageStock="";//线下指定商品库存
		if("stock".equals(pageVo.getEx1Name()) || "stock".equals(pageVo.getEx2Name()) || "stock".equals(pageVo.getEx3Name()) 
				|| "stock".equals(pageVo.getEx4Name())|| "stock".equals(pageVo.getEx5Name())|| "stock".equals(pageVo.getEx6Name())) {
			if("stock".equals(pageVo.getEx1Name())) {
				pageStock = pageVo.getEx1Value();
			} else if("stock".equals(pageVo.getEx2Name())) {
				pageStock = pageVo.getEx2Value();
			} else if("stock".equals(pageVo.getEx3Name())) {
				pageStock = pageVo.getEx3Value();
			}else if("stock".equals(pageVo.getEx4Name())) {
				pageStock = pageVo.getEx4Value();
			}else if("stock".equals(pageVo.getEx5Name())) {
				pageStock = pageVo.getEx5Value();
			}else if("stock".equals(pageVo.getEx6Name())) {
				pageStock = pageVo.getEx6Value();
			}
		}
		if(StringUtils.isEmpty(pageStock)) {//默认取第六个值为库存
			pageStock = pageVo.getEx6Value();
		}
		if(sku!=null) {
			//新版内购价
			BigDecimal internalPurchasePrice = sku.getInternalPurchasePrice();
			//阶梯价格
			List<ProductLadder> productLadderlist = productLadderService.getListBySkuid(sku.getId());
			if(productLadderlist != null && !productLadderlist.isEmpty()){
				BigDecimal productLadderPrice = productLadderlist.get(0).getPrice();//最小的阶梯价格
				if(internalPurchasePrice.compareTo(productLadderPrice)==1){
					internalPurchasePrice = productLadderPrice;
				}
			}
			pageVo.setProPrice(sku.getPrice().setScale(2,BigDecimal.ROUND_DOWN).toString());
			pageVo.setProSalePrice(internalPurchasePrice.setScale(2,BigDecimal.ROUND_DOWN).toString());
			pageVo.setProDiscount(internalPurchasePrice.multiply(new BigDecimal(10)).divide(sku.getPrice(),1,BigDecimal.ROUND_DOWN).toString());

			if(StringUtils.isEmpty(pageVo.getImagePath())){
				pageVo.setImagePath(sku.getMainImage());
			}
			pageVo.setMaxFucoin(sku.getMaxFucoin().setScale(2,BigDecimal.ROUND_DOWN).toString());
			pageVo.setQuantity(inventoryService.getInventoryFromRedis(sku.getId()));
			
			pageVo.setLink("http://www.wd-w.com/" + p.getId() + ".html?skuId="+sku.getId()+"&pageKey="+pageKey+"&pageStock="+pageStock);
			
		} else {

			if(StringUtils.isEmpty(pageVo.getImagePath())){
				pageVo.setImagePath(p.getImage());
			}
			
			pageVo.setLink("http://www.wd-w.com/" + p.getId() + ".html?pageKey="+pageKey+"&pageStock="+pageStock);
		}
		pageVo.setSaleKbn(p.getSaleKbn());
		pageVo.setSaleNote(p.getSaleNote());
		pageVo.setProName(p.getFullName());
		
		
		
	}
	
	/**
     * 功能说明：每日pv
     * 日期:	2015年5月13日
     * 开发者:gaoyj
     * 版本号:1.0
     *
     * @param date 日期 (yyyy-MM-dd)
     * @return
     */
	@RequestMapping("getDayPvCnt")
	@ResponseBody
	public ActResult<Long[]> getDayPvCnt(String date) throws Exception{
		return ActResult.success(clientAccessLogService.getDayPvCnt(date));
	}

	/**
     * 功能说明：每日pv
     * 日期:	2015年5月13日
     * 开发者:gaoyj
     * 版本号:1.0
     *
     * @param date 日期 (yyyy-MM-dd)
     * @return
     */
	@RequestMapping("getDaySearchKeyCnt")
	@ResponseBody
	public ActResult getDaySearchKeyCnt(String date) throws Exception{
		return ActResult.success(clientAccessLogService.getDaySearchKeyCnt(date));
	}


	private ProductSpecifications getMinSku(Long pid) {
		List<ProductSpecifications> skus = productService.findSku(pid);
		if(skus == null || skus.isEmpty()) return null;
		Collections.sort(skus, new Comparator<ProductSpecifications>(){

			@Override
			public int compare(ProductSpecifications o1, ProductSpecifications o2) {

				BigDecimal o1p = o1.getPrice();
				BigDecimal o2p = o2.getPrice();

				BigDecimal o1f = o1.getMaxFucoin();
				BigDecimal o2f = o2.getMaxFucoin();
				
				if(o1p==null || o1f==null) {
					return 1;
				}
				if(o2p==null || o2f==null) {
					return -1;
				}
				
				BigDecimal o1z = o1f.divide(o1p, 2,BigDecimal.ROUND_DOWN);
				BigDecimal o2z = o2f.divide(o2p, 2,BigDecimal.ROUND_DOWN);
													
				return o2z.compareTo(o1z);
			}});
		
		ProductSpecifications sku = skus.get(0);
		return sku;
	}

}
