package com.wode.factory.user.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wode.common.util.NumberUtil;
import com.wode.factory.model.ClientAccessLog;
import com.wode.factory.model.Shop;
import com.wode.factory.model.StoreCategory;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserIm;
import com.wode.factory.user.dao.SupplierDao;
import com.wode.factory.user.service.ClientAccessLogService;
import com.wode.factory.user.service.EntParamCodeService;
import com.wode.factory.user.service.ShopService;
import com.wode.factory.user.service.UserImService;
import com.wode.factory.user.util.CookieUtils;
import com.wode.factory.user.util.IPUtils;
import com.wode.search.SearchParams;
import com.wode.search.WodeResult;
import com.wode.search.WodeSearchManager;
import com.wode.search.WodeSearcher;

/**
 * Created by Bing King on 2015/3/9.
 */
@Controller
@RequestMapping("/shop")
public class ShopController extends BaseController{
    @Autowired
    private ShopService shopService;

    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private WodeSearchManager wsm;
    @Autowired
    private ClientAccessLogService clientAccessLogService;
	@Autowired
	private UserImService userImService;
	@Autowired
	private EntParamCodeService entParamCodeService;
    
    
    @RequestMapping(value="/search/{shopid}", method=RequestMethod.GET)
    public String search(@PathVariable Long shopid, HttpServletRequest request, ModelMap model) {
        String keyword = request.getParameter(SearchParams.Param.KEYWORD);
        if (StringUtils.isNotBlank(keyword)) {
            String queryString = request.getQueryString()+"&shop="+shopid;
            WodeSearcher searcher = wsm.getSearcher();
            WodeResult result = searcher.search(queryString, null, false,false,true);
            model.addAttribute("result", result);
            model.addAttribute("keyword", keyword);
            try {
                StringBuffer url = new StringBuffer(request.getRequestURL());
                url.append("?key=").append(URLEncoder.encode(keyword, "UTF-8"));
                model.addAttribute("url", url.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Shop shop=shopService.getShopSettingById(shopid);
            List<StoreCategory> categoryList = shopService.getStoreCategories(shop.getSupplierId());
            model.addAttribute("categories", categoryList);
            model.addAttribute("shopName", shop.getShopname());
            model.addAttribute("ss", shop);
            List<String> banner=new ArrayList();
            if(com.wode.common.util.StringUtils.isEmpty(shop.getBanner())) {
                banner.add("https://img2.wd-w.com/upload/regular/wdc/default_banner.jpg");
            } else {
                String[] jpgs=shop.getBanner().split(",");
                if(jpgs.length==0) {
                    banner.add("https://img2.wd-w.com/upload/regular/wdc/default_banner.jpg");
                } else {
                    for(int i=0;i<jpgs.length;i++) {
                        banner.add(jpgs[i].trim());
                    }
                }
            }
            model.addAttribute("banner", banner);
            Supplier supplier = supplierDao.getById(shop.getSupplierId());
            model.addAttribute("supplier", supplier);
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
            return "/shop/search";
        } else {
            return "redirect:/";
        }
    }


    /**
     * 店铺首页/列表页
     * @param page  页码
     * @param cat   店铺分类ID
     * @return
     */
    @RequestMapping(value="/{shopid:\\d+}", method=RequestMethod.GET)
    public String index(HttpServletRequest request,HttpServletResponse response, @PathVariable Long shopid, Model model, @RequestParam(defaultValue = "1") Integer page, Long cat){
        Shop shop = shopService.getShopSettingById(shopid);
        List<StoreCategory> categoryList = shopService.getStoreCategories(shop.getSupplierId());
        WodeSearcher searcher = wsm.getSearcher();
        WodeResult result;
        String queryString;
        if (cat != null) {
            queryString = "shop=" + shopid + "&customCat=" + cat +"&page="+page;
            if(!queryString.contains("sort")) {
            	queryString = queryString+"&sort=sortNum_0";
            }
            result = searcher.search(queryString, null, false,false,false);
            model.addAttribute("cat", cat);
            for (StoreCategory sc : categoryList) {
                boolean flag = false;
                for (StoreCategory child : sc.getChildren()) {
                    if (child.getId().equals(cat)) {
                        model.addAttribute("parent", sc.getId());
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
        } else {
            queryString = "shop=" + shopid + "&page=" + page;
            if(!queryString.contains("sort")) {
            	queryString = queryString+"&sort=sortNum_0";
            }
            result = searcher.search(queryString, null, false,false,false);
        }
        model.addAttribute("result", result);
        model.addAttribute("page", page);
        model.addAttribute("url", request.getRequestURL().toString());
        model.addAttribute("categories", categoryList);
        model.addAttribute("ss", shop);

        model.addAttribute("shopName", shop.getShopname());
		UserIm shopIm= userImService.selectByShopAndSupplier(shop.getId(), shop.getSupplierId());
		model.addAttribute("shopImUser",shopIm==null?null:shopIm.getOpenimId());
		
        List<String> banner=new ArrayList();
        if(com.wode.common.util.StringUtils.isEmpty(shop.getBanner())) {
            banner.add("https://img2.wd-w.com/upload/regular/wdc/default_banner.jpg");
        } else {
            String[] jpgs=shop.getBanner().split(",");
            if(jpgs.length==0) {
                banner.add("https://img2.wd-w.com/upload/regular/wdc/default_banner.jpg");
            } else {
                for(int i=0;i<jpgs.length;i++) {
                    banner.add(jpgs[i].trim());
                }
            }
        }
        model.addAttribute("banner", banner);
        Supplier supplier = supplierDao.getById(shop.getSupplierId());
        model.addAttribute("supplier", supplier);
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
		UserFactory user = getUser(request, response);
		model.addAttribute("user", user);
		clientAccessLogService.saveNormal(ClientAccessLog.ACCESS_TYPE_SHOP, shopid.toString(), shop.getShopname(), null,null,CookieUtils.getUUID(request, response),IPUtils.getClientAddress(request));
        return "/shop/index";
    }
    
    
    /**
     * 店铺介绍页面
     * @param id    supplier id
     * @return
     */
    @RequestMapping(value="/{shopid}/intro", method=RequestMethod.GET)
    public String intro(@PathVariable Long shopid, Model model) {
    	 Shop shop=shopService.getShopSettingById(shopid);
         String shopName = shop.getShopname();
         if(shopName.contains("员工福利旗舰店")) {
         	shopName=shopName.replace("员工福利旗舰店", "<br />员工福利旗舰店");
         }
         model.addAttribute("shopName", shopName);
        model.addAttribute("ss", shop);
        List<String> banner=new ArrayList();
        if(com.wode.common.util.StringUtils.isEmpty(shop.getIntroImage())) {
            banner.add("https://img2.wd-w.com/upload/regular/wdc/default_banner.jpg");
        } else {
            String[] jpgs=shop.getIntroImage().split(",");
            if(jpgs.length==0) {
                banner.add("https://img2.wd-w.com/upload/regular/wdc/default_banner.jpg");
            } else {
                for(int i=0;i<jpgs.length;i++) {
                    banner.add(jpgs[i].trim());
                }
            }
        }
        model.addAttribute("banner", banner);
        Supplier supplier = supplierDao.getById(shop.getSupplierId());
        model.addAttribute("supplier", supplier);
		UserIm shopIm= userImService.selectByShopAndSupplier(shop.getId(), shop.getSupplierId());
		model.addAttribute("shopImUser",shopIm==null?null:shopIm.getOpenimId());
		
        return "/shop/intro";
    }
}
