package com.wode.factory.supplier.api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.redis.RedisUtil;
import com.wode.common.result.Result;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.ApprProduct;
import com.wode.factory.model.Inventory;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.SupplierLog;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.CommentsService;
import com.wode.factory.supplier.facade.ProductFacade;
import com.wode.factory.supplier.service.ApprProductService;
import com.wode.factory.supplier.service.InventoryService;
import com.wode.factory.supplier.service.ProductService;
import com.wode.factory.supplier.service.ProductSpecificationsService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierLogService;
import com.wode.factory.supplier.service.UserService;
import com.wode.factory.supplier.util.Constant;

/**
 * 2015-6-17
 *
 * @author 谷子夜
 */
@Controller
@RequestMapping("/app/product")
@ResponseBody
public class AppProductController extends BaseController {
	@Autowired
	private UserService userService;

	@Autowired
	ShopService shopService;
	@Autowired
	CommentsService commentsService;
	@Autowired
	@Qualifier("apprProductService")
	private ApprProductService apprProductService;
	@Autowired
	private ProductFacade productFacade;
	@Resource
	private RedisUtil redis;
	@Autowired
	@Qualifier("productSpecificationsService")
	private ProductSpecificationsService productSpecificationsService;
	
	@Autowired
	@Qualifier("productService-supplier")
	private ProductService productService;
	@Autowired
	@Qualifier("supplierLogService")
	private SupplierLogService supplierLogService;
	@Autowired
	@Qualifier("inventoryService")
	private InventoryService inventoryService;
	/**
	 * 更新用户基本信息
	 *
	 * @param request
	 * @param gender
	 * @param nickName
	 * @return
	 */

	/**
	 * 商品list
	 **/
	@RequestMapping(value = "getProductlist.user", method = RequestMethod.GET)
	@NoCheckLogin
	public ActResult<String> productlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserFactory userFactory = userService.getById(loginUser.getId());
		ModelMap model = new ModelMap();
		Result result = new Result();

		String pages = request.getParameter("pages");
		String sizes = request.getParameter("sizes");
		Integer page = 1;
		Integer size = 10;
		if (pages == null || pages.equals("")) {
			pages = "1";
		}
		page = new Integer(pages);

		if (sizes == null || sizes.equals("")) {
			sizes = "10";
		}

		size = new Integer(sizes);

		if (size > 100) {
			size = 100;
		}

		String shopId = request.getParameter("shopId");
		String name = request.getParameter("name");
		String partnumber = request.getParameter("partnumber");
		String categoryid = request.getParameter("categoryid");
		String minprice = request.getParameter("minprice");
		String maxprice = request.getParameter("maxprice");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String status = request.getParameter("status");
		String isMarketable = request.getParameter("isMarketable");
		String selltype = request.getParameter("selltype");
		String barcode = request.getParameter("barcode");
		String stoId = request.getParameter("stoId");
		// 排序字段
		String sortname = request.getParameter("sortname");
		String pricesort = request.getParameter("pricesort");
		String allnumsort = request.getParameter("allnumsort");
		String createDatesort = request.getParameter("createDatesort");
		String sort = request.getParameter("sort");

		Map map = new HashMap();
		map.put("name", name);
		map.put("partnumber", partnumber);
		if (selltype == null || selltype.equals("")) {
			selltype = "waitcheck";
		}
		map.put("selltype", selltype);
		map.put("barcode", barcode);
		map.put("supplierId", userFactory.getSupplierId());

		if (!StringUtils.isEmpty(shopId)) {
			map.put("shopId", new Long(shopId));
		}
		if (!StringUtils.isEmpty(categoryid)) {
			map.put("categoryid", new Long(categoryid));
		}
		if (!StringUtils.isEmpty(stoId)) {
			map.put("stoId", new Long(stoId));
		}
		if (!StringUtils.isEmpty(minprice)) {
			map.put("minprice", new Double(minprice));
		}
		if (!StringUtils.isEmpty(maxprice)) {
			map.put("maxprice", new Double(maxprice));
		}
		if (!StringUtils.isEmpty(starttime)) {
			map.put("starttime", starttime + " 00:00:00");
		}
		if (!StringUtils.isEmpty(endtime)) {
			map.put("endtime", endtime + " 23:59:59");
		}
		if (!StringUtils.isEmpty(status)) {
			map.put("status", new Integer(status));
		}
		if (!StringUtils.isEmpty(isMarketable)) {
			map.put("isMarketable", new Integer(isMarketable));
		}
		if (!StringUtils.isEmpty(pricesort)) {
			map.put("pricesort", new Integer(pricesort));
		}
		if (!StringUtils.isEmpty(allnumsort)) {
			map.put("allnumsort", new Integer(allnumsort));
		}
		if (!StringUtils.isEmpty(sort)) {
			map.put("sort", new Integer(sort));
		}
		if (StringUtils.isEmpty(createDatesort)) {
			createDatesort = "2";
		}
		map.put("createDatesort", new Integer(createDatesort));
		if (StringUtils.isEmpty(sortname)) {
			sortname = "createDatesort";
		}
		map.put("sortname", (sortname == null || sortname.equals("") ? "createDatesort" : sortname));
		model.addAttribute("pages", page);
		model.addAttribute("sizes", size);
		model.addAttribute("name", name);
		model.addAttribute("partnumber", partnumber);
		model.addAttribute("categoryid", categoryid);
		model.addAttribute("shopId", shopId);
		model.addAttribute("stoId", stoId);
		model.addAttribute("minprice", minprice);
		model.addAttribute("maxprice", maxprice);
		model.addAttribute("starttime", starttime);
		model.addAttribute("endtime", endtime);
		model.addAttribute("status", status);
		model.addAttribute("isMarketable", isMarketable);
		model.addAttribute("selltype", selltype);
		model.addAttribute("pricesort", pricesort);
		model.addAttribute("allnumsort", allnumsort);
		model.addAttribute("createDatesort", createDatesort);
		model.addAttribute("sort", sort);
		model.addAttribute("barcode", barcode);
		model.addAttribute("sortname", (sortname == null || sortname.equals("") ? "createDatesort" : sortname));
        
	/*	Integer total = productService.findProductlistPageCount(map);
		Integer startnum = (page - 1) * size;
		if (total > 0) {
			if (total < startnum) {
				startnum = total - size;
			}
			if (startnum < 0) {
				startnum = 0;
			}
			map.put("startnum", startnum);
			map.put("size", size);
			List<Product> list = productService.findProductlistPage(map);
			result.setPage(page);
			result.setSize(size);
			result.setTotal(total);
			result.setErrorCode("0");
			result.setMsgBody(list);
		} else {
			result.setErrorCode("1000");
		}*/
		Integer userType = userFactory.getType();
		if (userType==2 ||userType==3) {//商家用户
		 if(selltype.equals("reject") || selltype.equals("waitcheck") || selltype.equals("waitsell")){
	        	Integer total = apprProductService.findProductlistPageCount(map);
	        	Integer startnum = (page - 1) * size;
	        	if (total > 0) {
					if (total < startnum) {
						startnum = total - size;
					}
					if (startnum < 0) {
						startnum = 0;
					} 
					map.put("startnum", startnum);
					map.put("size", size);
					List<ApprProduct> list = apprProductService.findProductlistPage(map);
					result.setPage(page);
					result.setSize(size);
					result.setTotal(total);
					result.setErrorCode("0");
					result.setMsgBody(list);
	        	}else {
					result.setErrorCode("1000");
				}
			}else if(selltype.equals("selling")){
				Integer total = productService.findProductlistPageCount(map);
				Integer startnum = (page - 1) * size;
				if (total > 0) {
					if (total < startnum) {
						startnum = total - size;
					}
					if (startnum < 0) {
						startnum = 0;
					}
					map.put("startnum", startnum);
					map.put("size", size);
					List<Product> list = productService.findProductlistPage(map);
					setInternalPurchasePrice(list);
					result.setPage(page);
					result.setSize(size);
					result.setTotal(total);
					result.setErrorCode("0");
					result.setMsgBody(list);
				} else {
					result.setErrorCode("1000");
				}
			
			}
		
		}else{
			result.setErrorCode("1000");
		}
		model.addAttribute("result", result);

		return ActResult.success(model);
	}
	/**
	 * 设置内购价
	 * @param list
	 */
	private void setInternalPurchasePrice(List<Product> list) {
		for (Product product : list) {
			List<ProductSpecifications> psList = productSpecificationsService.getlistByProductid(product.getId());
			if(psList!=null && !psList.isEmpty()){
				product.setProductSpecificationslist(psList);
			}
		}
	}

	/**
	 * ajax删除商品
	 **/
	@RequestMapping(value = "getProductCheckById.user", method = RequestMethod.GET)
	@NoCheckLogin
	public ActResult<String>  getProductCheckById(HttpServletRequest request, HttpServletResponse response,Long productId) throws Exception {

		Map map = new HashMap();
		/*map.put("productId", productId);
		Product product = productService.getProductCheckById(map);*/
		ApprProduct  product=null;
		product=apprProductService.getById(new Long(productId));
		if(product !=null){
		//这里需要使用productid才能从审核表中取出审核不通过信息
		   map.put("productId", new Long(product.getProductId()));
		}
		//审核不通过提交过来的肯定是临时表id
		product = apprProductService.getProductCheckById(map);
		Result result = new Result();
		result.setErrorCode("0");
		result.setMsgBody(product);
		return ActResult.success(result);
	}
	
	/**
	 * ajax获取商品价格
	 **/
	@RequestMapping(value = "ajaxGetProductForUpdate.user", method = RequestMethod.GET)
	@NoCheckLogin
	public ActResult<String> ajaxGetProductForUpdate(Long id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		List<Product> prolist =null;
		//如果获取传过来的是正式表id
		Product ptemp =productService.getById(new Long(id));
		if( ptemp != null){
			//判读临时表中是否在审核该商品
			ApprProduct ptemp2 =apprProductService.selectProductIdAndStatus(ptemp.getId());
			if(ptemp2==null){
				Long categoryId = ptemp.getCategoryId();
				map.put("categoryId", new Long(categoryId));
				map.put("productId", new Long(id));
				prolist = productService.getProductByMap(map);	
			}else{
				Long categoryId = ptemp2.getCategoryId();
				map.put("categoryId", new Long(categoryId));
				map.put("productId", ptemp2.getId()); //之所以要改这个，是因为apprproductmapper.xml中getProductByMap方法是以id来查询
				prolist = apprProductService.getProductByMap(map);
			}
			
		} else {
			// 传递过来的是临时表的id就直接走下边的逻辑
			ApprProduct ptemp1 = apprProductService.getById(new Long(id));
			if (ptemp1 != null) {
				Long categoryId = ptemp1.getCategoryId();
				map.put("categoryId", new Long(categoryId));
				map.put("productId", new Long(id));
				prolist = apprProductService.getProductByMap(map);

			}
		}
		Product pro = new Product();
		if (prolist != null) {
			pro = prolist.get(0);
		}
		Map<Long, String> specificationValueMap = new HashMap<Long, String>();
		if (pro != null && pro.getProductSpecificationValuelist() != null) {
			for (int i = 0; i < pro.getProductSpecificationValuelist().size(); i++) {
				specificationValueMap.put(pro.getProductSpecificationValuelist().get(i).getId(), pro.getProductSpecificationValuelist().get(i).getSpecificationValue());
			}
		}

		if (pro != null && pro.getProductSpecificationslist() != null) {
			for (int i = 0; i < pro.getProductSpecificationslist().size(); i++) {
				String itemids = pro.getProductSpecificationslist().get(i).getItemids();
				String[] idstemp = itemids.split(",");
				String itemnames = "";
				for (String idtemp : idstemp) {
					itemnames = itemnames + specificationValueMap.get(new Long(idtemp)) + "/";
				}
				if (!itemnames.equals("")) {
					itemnames = itemnames.substring(0, itemnames.length() - 1);
				}
				pro.getProductSpecificationslist().get(i).setItemnames(itemnames);
				//处理sku库存===缓存取值
				String stock = redis.getData(RedisConstant.REDIS_SKU_INVENTORY + pro.getProductSpecificationslist().get(i).getId());
				if (stock != null && !stock.equals("")){
					pro.getProductSpecificationslist().get(i).setStock(Integer.valueOf(stock));
				}
			}
		}
		pro.setProductAttributelist(null);
		pro.setProductDetaillist(null);
		pro.setProductParameterValuelist(null);
		pro.setProductSpecificationValuelist(null);

		return ActResult.success(pro);
	}

	/**
	 * ajax修改商品价格
	 **/
	@RequestMapping(value = "ajaxSpecificationsChange.user", method = RequestMethod.POST)
	@NoCheckLogin
	public ActResult<String> ajaxSpecificationsChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<BigDecimal> pricelist = new ArrayList<BigDecimal>();// 价格，冒泡排序使用
		Integer allnum = 0;// 总库存
		String productid = request.getParameter("productid");
		String selltypenew = request.getParameter("selltypenew");
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		UserFactory userFactory = userService.getById(loginUser.getId());
		int inventoryC = 0;
		////商品sku
		String[] specifications_result = request.getParameterValues("specifications_result");
		boolean changePrice = false;
		List<ProductSpecifications> lsSku = new ArrayList<ProductSpecifications>();
		List<Inventory> lsStock = new ArrayList<Inventory>();
		if (specifications_result != null && specifications_result.length > 0) {
			for (int i = 0; i < specifications_result.length; i++) {
				String specifications = specifications_result[i];
				if (specifications != null && !specifications.trim().equals("")) {
					String[] specificationss = specifications.split("_");
					Long specificationsid = new Long(specificationss[0]);
					ProductSpecifications pa = productSpecificationsService.getById(specificationsid);
					if (pa != null) {
						String ret = "修改商品（id='" + pa.getProductId() + "')的sku (id='" + pa.getId() + "')的价格库存预警值等属性：";
						ret += "价格由￥" + pa.getPrice() + " 修改为" + specificationss[1] + ";";
						if(!changePrice){
							if(pa.getPrice().compareTo(new BigDecimal(specificationss[1]))==0) {
								//价格没有变化 ...原方法不变
								if (specificationss.length == 5) {
									if(pa.getMaxFucoin().compareTo(new BigDecimal(specificationss[4]))!=0) {
										//价格改变
										changePrice=true;
									}
								} else if(specificationss.length == 6){
									//这里有几种情况首先第一点 1.原内购价可能为null。2.原内购价为null，就要进行计算，计算如果相等，就不是修改
									if (null == pa.getInternalPurchasePrice()){
										
										if (pa.getMaxFucoin().compareTo(new BigDecimal(specificationss[4])) != 0) { //最大内购券改变，说明内购价改变
											// 价格改变
											changePrice = true;
										}
									}else{
										if (new BigDecimal(specificationss[5]).compareTo(pa.getInternalPurchasePrice()) != 0){//内购价改变
											// 价格改变
											changePrice = true;
										}
									}
								} else	{
								
									if(pa.getMaxFucoin().compareTo(BigDecimal.ZERO)!=0) {
										//价格改变
										changePrice=true;
									}									
								}
							} else {
								//价格改变
								changePrice=true;
							}
						}
						pa.setPrice(new BigDecimal(specificationss[1]));
						if (specificationss.length == 5 || specificationss.length == 6) {
							if (new BigDecimal(specificationss[4]).compareTo(new BigDecimal(specificationss[1])) == 1) {
								if (new BigDecimal(specificationss[1]).compareTo(new BigDecimal(1)) == 1) {
									pa.setMaxFucoin(new BigDecimal(specificationss[1]));//new Integer(specificationss[1])
								} else {
									pa.setMaxFucoin(new BigDecimal(0));//0
								}
							} else {
								pa.setMaxFucoin(new BigDecimal(specificationss[4]));//new Integer(specificationss[4])
							}
						} else  {
							pa.setMaxFucoin(new BigDecimal(0));//0
						}
						if( specificationss.length == 6 ){ //没传的话算下储存,也就是
							pa.setInternalPurchasePrice(new BigDecimal(specificationss[5]));//增加内购价
						}else{
							pa.setInternalPurchasePrice(pa.getPrice().subtract(pa.getMaxFucoin()));
						}

						lsSku.add(pa);
						Map map = new HashMap();
						map.put("productSpecificationsId", pa.getId());
						List<Inventory> inventorylist = inventoryService.findAllBymap(map);
						Inventory inventory = null;
						if (inventorylist != null && inventorylist.size() > 0) {
							inventory = inventorylist.get(0);
						}
						if (inventory == null) {
							inventory = new Inventory();
						}
						ret += "库存由" + inventory.getQuantity() + "修改为" + specificationss[2] + ";";
						ret += "库存预警值由" + inventory.getWarnQuantity() + "修改为" + specificationss[3] + ";";
						inventory.setProductSpecificationsId(pa.getId());
						inventoryC = inventory.getQuantity();
						inventory.setQuantity(new Integer(specificationss[2]));
						inventoryC = inventory.getQuantity()-inventoryC;
						inventory.setWarnQuantity(new Integer(specificationss[3]));
						//inventoryService.saveOrUpdate(inventory);
						lsStock.add(inventory);
						//日志
						SupplierLog log = new SupplierLog();
						log.setUserId(userFactory.getId());
						log.setUsername(userFactory.getUserName());
						log.setAct("修改商品（id='" + pa.getProductId() + "')的sku (id='" + pa.getId() + "')的价格库存预警值等属性");
						log.setTime(new Date());
						log.setResult(ret);
						supplierLogService.save(log);
						pricelist.add(pa.getPrice());
						allnum = allnum + new Integer(specificationss[2]);
					}
				}
			}
		}
		
		if (productid != null && !productid.equals("")) {
			ApprProduct product = null;
           
			// 假如传过来的是临时表的id，product是有数据的
			product = apprProductService.getById(new Long(productid));
            if(product !=null && product.getStatus()<2){
            	 //如果是临时表的sku，直接更新生成 
				for (ProductSpecifications sku : lsSku) {
					productSpecificationsService.saveOrUpdate(sku);
				}
				for (Inventory inven : lsStock) {
					inventoryService.saveOrUpdate(inven);
					// 更新缓存（正式表数据放缓存中），前端静态页面自动去redis中取出更新后的库存并显示，无需重新生成静态页
					String key = RedisConstant.REDIS_SKU_INVENTORY + inven.getProductSpecificationsId();
					redis.del(key);
					redis.setData(key, String.valueOf(inven.getQuantity()));
					redis.removeSet(RedisConstant.Inventory_CHANGE, String.valueOf(inven.getProductSpecificationsId()) + "");
				}
				if(changePrice) {//如果快捷修改sku价格改变，需要审核
					if(product.getUpdateDesc() == null || !product.getUpdateDesc().contains("sku价格或者库存改变")) {					
						product.setUpdateDesc((product.getUpdateDesc()==null?"":product.getUpdateDesc())+"sku价格或者库存改变,");
					}
				}
			} else {
				// 防止没有审核完成就重新修改，读取临时表的数据
				product = apprProductService.selectProductIdAndStatus(new Long(productid));
				
				if(product !=null){
                	 //如果是临时表的sku，直接更新生成 
					for (ProductSpecifications sku : lsSku) {
						productSpecificationsService.saveOrUpdate(sku);
					}
					for (Inventory inven : lsStock) {
						inventoryService.saveOrUpdate(inven);
						// 更新缓存（正式表数据放缓存中），前端静态页面自动去redis中取出更新后的库存并显示，无需重新生成静态页
						String key = RedisConstant.REDIS_SKU_INVENTORY + inven.getProductSpecificationsId();
						redis.del(key);
						redis.setData(key, String.valueOf(inven.getQuantity()));
						redis.removeSet(RedisConstant.Inventory_CHANGE, String.valueOf(inven.getProductSpecificationsId()) + "");
					}
					if(changePrice) {//如果快捷修改sku价格改变，需要审核
						if(product.getUpdateDesc() == null || !product.getUpdateDesc().contains("sku价格或者库存改变")) {					
							product.setUpdateDesc((product.getUpdateDesc()==null?"":product.getUpdateDesc())+"sku价格或者库存改变,");
						}
					}
				} else {
					// skuMap中保存着sku新旧的对应关系
					Map<Long, ProductSpecifications> skuMap = new HashMap<Long, ProductSpecifications>();
					// stockMap中保存着库存新旧的对应关系
					Map<Long, Inventory> stockMap = new HashMap<Long, Inventory>();
					if(changePrice) {//如果快捷修改sku价格改变，需要审核
						product = productFacade.productToapprProduct(new Long(productid),1,skuMap, stockMap);
						product.setUpdateDesc("sku价格或者库存改变");
						// 遍历要修改的正式表的sku
						for (ProductSpecifications sku : lsSku) {
							// 获取新生成的sku对象和老sku的对应关系
							ProductSpecifications n = skuMap.get(sku.getId());
							// copy sku to new
							n.setPrice(sku.getPrice());
							n.setMaxFucoin(sku.getMaxFucoin());
							// 把新的sku对象插入数据库中，内容为正式表的sku数据
							productSpecificationsService.saveOrUpdate(n);
						}
						for (Inventory inv : lsStock) {
							Inventory in = stockMap.get(inv.getId());
							// in.setLockQuantity(inv.getLockQuantity());
							in.setQuantity(inv.getQuantity());
							in.setWarnQuantity(inv.getWarnQuantity());
							inventoryService.saveOrUpdate(in);
						}
					} else {//直接更新正式表数据
						// 查询正式表商品
						Product productOld = productService.getById(new Long(productid));
						// 更新正式表商品总库存
						productOld.setAllnum(allnum);
						productService.update(productOld);
						
						for (Inventory in : lsStock) { //更新旧版库存
							inventoryService.saveOrUpdate(in);

							// 更新缓存（正式表数据放缓存中），前端静态页面自动去redis中取出更新后的库存并显示，无需重新生成静态页
							String key = RedisConstant.REDIS_SKU_INVENTORY + in.getProductSpecificationsId();
							redis.del(key);
							redis.setData(key, String.valueOf(in.getQuantity()));
							redis.removeSet(RedisConstant.Inventory_CHANGE, String.valueOf(in.getProductSpecificationsId()) + "");
						}
						
						this.refreshProduct(new Long(productid), false); //更新缓存和索引，false表示不生成静态页						
					}
				}
			}
			// 根据临时表product_id调用selectProductIdAndStatus获取status状态小于2
			if (product != null) {
				product.setAllnum(allnum);
				product.setUpdateDate(new Date());//快捷修改更新修改时间
				if (pricelist != null && pricelist.size() > 0) {
					bubbleSort(pricelist);// 价格排序
					product.setMinprice(pricelist.get(0));
					product.setShowPrice(pricelist.get(0) + "");
					product.setMaxprice(pricelist.get(pricelist.size() - 1));
					product.setShowPrice(pricelist.get(0) + "");
				}
				this.apprProductService.saveOrUpdate(product);
			}
		}
		result.setErrorCode("0");
		return ActResult.success(result);
	}

	/**
	 * ajax上架商品
	 **/
	@RequestMapping(value = "ajaxSellOn.user")
	@NoCheckLogin
	public ActResult ajaxSellOn(HttpServletRequest request) throws Exception {
		UserFactory userFactory = userService.getById(loginUser.getId());
		String ids = request.getParameter("ids");
		List<Long> idslist = new ArrayList<Long>();
		for (String s : ids.split(",")) {
			idslist.add(new Long(s));	
		}
		ActResult<List<Long>> ret = apprProductService.sellOn(idslist, userFactory);
		if (ret.isSuccess()) {
			for(Long pid : ret.getData()){//这里直接返回没有修改过敏感数据的正式表对象的id，更合理
				this.refreshProduct(pid,true);//更新缓存，索引和静态页
			}
			//日志
			SupplierLog log = new SupplierLog();
			log.setUserId(userFactory.getId());
			log.setUsername(userFactory.getUserName());
			if (idslist != null && idslist.size() > 0) {
				log.setAct("商品 (ids=" + ids + ") 上架");
			}
			log.setTime(new Date());
			log.setResult("success");
			supplierLogService.save(log);
		}
		return ret;
	}


	/**
	 * ajax下架商品
	 **/
	@RequestMapping(value = "ajaxSellOff.user")
	@NoCheckLogin
	public ActResult ajaxSellOff(HttpServletRequest request) throws Exception {
		UserFactory userFactory = userService.getById(loginUser.getId());
		String ids = request.getParameter("ids");
		List<Long> idslist = new ArrayList<Long>();
		for (String s : ids.split(",")) {
			idslist.add(new Long(s));	
		}
		//日志
		SupplierLog log = new SupplierLog();
		log.setUserId(userFactory.getId());
		log.setUsername(userFactory.getUserName());
		if (idslist != null && idslist.size() > 0) {
			log.setAct("商品 (ids=" + idslist.toString() + ") 下架");
		}
		log.setTime(new Date());
		log.setResult("success");
		supplierLogService.save(log);
		if (ids != null) {
			for (String s : ids.split(",")) {
				Product product =productService.getById(new Long(s));
				if (product != null) {
					// skuMap中保存着sku新旧的对应关系
					Map<Long, ProductSpecifications> skuMap = new HashMap<Long, ProductSpecifications>();
					// stockMap中保存着库存新旧的对应关系
					Map<Long, Inventory> stockMap = new HashMap<Long, Inventory>();
					ApprProduct appr = productFacade.productToapprProduct(new Long(s),0, skuMap, stockMap);
				}
			}
		}
		ActResult ret = productService.sellOff(idslist, userFactory);
		if(ret.isSuccess()) {
			for (Long long1 : idslist) {
				this.destroyProduct(long1);
			}
		}
		return ret;
	}

	/**
	 * 冒泡排序
	 *
	 */
	public static void bubbleSort(List<BigDecimal> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = 1; j < list.size() - i; j++) {
				BigDecimal a = new BigDecimal(0);
				if ((list.get(j - 1)).compareTo(list.get(j)) > 0) {   //比较两个整数的大小
					a = list.get(j - 1);
					list.set((j - 1), list.get(j));
					list.set(j, a);
				}
			}
		}
	}
	/**
	 * 微信商品管理页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "page")
	@NoCheckLogin
	public ModelAndView page(HttpServletRequest request,ModelAndView model){
		model.setViewName("wx_prdouct_manage");
		return model;
	}
	/**
	 * 微信修改商品信息页面
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateProduct")
	@NoCheckLogin
	public ModelAndView productDetail(HttpServletRequest request,ModelAndView model,String id){
		model.addObject("id", id);
		model.setViewName("wx_product_detail");
		return model;
	}
	/**
	 * 根据商品id 更新缓存、索引、静态页
	 * @param productId
	 */
	public void refreshProduct(Long productId,boolean crreateHtml) {
		ActResult<String> ret = ActResult.success(null);
		Map paramMap = new HashMap();
		paramMap.put("productId", productId);
		try {
			HttpClientUtil.sendHttpRequest("post", Constant.CACHE_API_URL + "/product/rebuild/"+productId, paramMap); //更新缓存和索引
			if(crreateHtml) {
				HttpClientUtil.sendHttpRequest("post", Constant.CREATHTML_API_URL + "/product", paramMap);//生成静态页
			}
		} catch (Exception e) {
		}
	}
	/**
	 * 根据商品id 更新缓存、索引、静态页
	 * @param productId
	 */
	public void destroyProduct(Long productId) {
		ActResult<String> ret = ActResult.success(null);
		Map paramMap = new HashMap();
		paramMap.put("productId", productId);
		try {
			HttpClientUtil.sendHttpRequest("post", Constant.CACHE_API_URL + "/product/destroy/"+productId, paramMap); //更新缓存和索引
		} catch (Exception e) {
		}
	}
}
