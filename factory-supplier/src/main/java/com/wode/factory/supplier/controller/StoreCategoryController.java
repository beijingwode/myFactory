/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.result.Result;
import com.wode.common.stereotype.Token;
import com.wode.factory.model.ProductStoreCategory;
import com.wode.factory.model.Shop;
import com.wode.factory.model.StoreCategory;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.ProductService;
import com.wode.factory.supplier.query.ProductQuery;
import com.wode.factory.supplier.query.StoreCategoryQuery;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.StoreCategoryService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.util.UserInterceptor;

@Controller
@RequestMapping("category")
public class StoreCategoryController extends BaseSpringController {
    //默认多列排序,example: username desc,createTime asc
    protected static final String DEFAULT_SORT_COLUMNS = null;
    @Autowired
    @Qualifier("storeCategoryService")
    private StoreCategoryService storeCategoryService;
    @Autowired
    @Qualifier("shopService")
    private ShopService shopService;
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
    private final String LIST_ACTION = "redirect:/storeCategory/list.html";

    public StoreCategoryController() {
    }

    /**
     * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
     */
    @ModelAttribute
    public void init(ModelMap model,HttpServletRequest request) {
        model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
        UserFactory us = UserInterceptor.getSessionUser(request,shopService);
        Shop record = new Shop();
        record.setSupplierId(us.getSupplierId());
        model.put("shopList", shopService.selectByModel(record));
    }

    /**
     * @param request
     * @param proIds
     * @param catIds
     * @return
     */
    @RequestMapping("saveCats")
    @ResponseBody
    public String saveProductStoreCategory(HttpServletRequest request, String proIds, String catIds,Long id) {
        String[] proids = proIds.split(",");
        String[] catids = catIds.split(",");
        UserFactory us = UserInterceptor.getSessionUser(request,shopService);
        Long supplierId = us.getSupplierId();
        for(String proid : proids) {
            ProductQuery query = new ProductQuery();
            query.setSupplierId(supplierId);
            query.setId(Long.valueOf(proid));
            List<ProductStoreCategory> psc = new ArrayList<>();
            for(String catid : catids) {
                ProductStoreCategory productStoreCategory = new ProductStoreCategory();
                productStoreCategory.setProductId(Long.valueOf(proid));
                productStoreCategory.setStoreCategoryId(Long.valueOf(catid));
                productStoreCategory.setSupplierId(supplierId);
                psc.add(productStoreCategory);
            }
            storeCategoryService.saveOrUpdateProductStoreCategory(query, psc);
        }
        return "success";
    }

    @RequestMapping(value = "list")
    public ModelAndView list(HttpServletRequest request, StoreCategoryQuery query) {
        UserFactory us = UserInterceptor.getSessionUser(request,shopService);
        query.setId(null);
        query.setSortColumns("orders");
        query.setSupplierId(us.getSupplierId());
        PageInfo<?> page = this.storeCategoryService.findPage(query);
        ModelAndView result = new ModelAndView("product/shopsetting/category");
        result.addObject("supplierId", us.getSupplierId());
        result.addObject("page", page);
        
        StoreCategoryQuery scq = new StoreCategoryQuery();
        scq.setSupplierId(us.getSupplierId());
        scq.setPageSize(1000);
	    scq.setSortColumns("orders");
        PageInfo<?> stcs = storeCategoryService.findPage(scq);
        result.addObject("allCats", stcs.getList());
        return result;
    }

   

    /**
     * 进入新增页面
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @Token(save = true)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response, StoreCategory storeCategory) throws Exception {
        return new ModelAndView("product/storeCategory/create", "storeCategory", storeCategory);
    }

    /**
     * 保存新增对象
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @Token(remove = true)
    @ResponseBody
    public String save(String cats, String ups) throws Exception {
        List<StoreCategory> news = JSON.parseArray(cats, StoreCategory.class);
        List<StoreCategory> update = JSON.parseArray(ups, StoreCategory.class);
        for (StoreCategory sc : news) {
            sc.setCreateDate(new Date());
            sc.setUpdateDate(new Date());
            storeCategoryService.save(sc);
        }
        for (StoreCategory sc : update) {
            StoreCategory storeCategory = storeCategoryService.getById(sc.getId());
            storeCategory.setUpdateDate(new Date());
            storeCategory.setName(sc.getName());
            storeCategory.setOrders(sc.getOrders());
            storeCategory.setIsExpanding(sc.getIsExpanding());
            storeCategoryService.update(storeCategory);
        }
        return "success";
    }

    /**
     * 进入更新页面
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    @Token(save = true)
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.lang.Long id = new java.lang.Long(request.getParameter("id"));
        StoreCategory storeCategory = storeCategoryService.getById(id);
        return new ModelAndView("product/storeCategory/edit", "storeCategory", storeCategory);
    }

    /**
     * 保存更新对象
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @Token(remove = true)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.lang.Long id = new java.lang.Long(request.getParameter("id"));

        StoreCategory storeCategory = storeCategoryService.getById(id);
        bind(request, storeCategory);
        storeCategoryService.update(storeCategory);
        return new ModelAndView(LIST_ACTION);
    }

    /**
     * 删除对象
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(String id) {
        String[] ids = id.split(",");
        Integer success = 0;
        for(String d : ids) {
            success += storeCategoryService.delete(Long.valueOf(d));
        }
        return success.toString();
    }
    
    
	/**
	 * 获取商品类型的子节点
	 */
	@RequestMapping(value="ajaxGetStoreCategoryList",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView ajaxGetStoreCategoryList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		Long supplierId = null;
		if(userModel!=null){
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			if(supplier!=null){
				supplierId = supplier.getId();
			}
		}
		if(supplierId==null){
			supplierId = -1l;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("supplierId", supplierId);
		map.put("grade",2);
		List<StoreCategory> list = storeCategoryService.findAllBymap(map);
		
		Result result = new Result();
		result.setErrorCode("0");
		result.setMsgBody(list);
		return new ModelAndView("","result",result);
	}

}

