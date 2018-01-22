package com.wode.api.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wode.api.util.EasemobIMUtils;
import com.wode.api.util.IPUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.factory.model.ClientAccessLog;
import com.wode.factory.model.CollectionShop;
import com.wode.factory.model.Shop;
import com.wode.factory.model.StoreCategory;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserIm;
import com.wode.factory.service.CommentsService;
import com.wode.factory.user.dao.SupplierDao;
import com.wode.factory.user.service.ClientAccessLogService;
import com.wode.factory.user.service.CollectionShopService;
import com.wode.factory.user.service.EntParamCodeService;
import com.wode.factory.user.service.ShopService;
import com.wode.factory.user.service.UserImService;
import com.wode.search.WodeResult;
import com.wode.search.WodeSearchManager;
import com.wode.search.WodeSearcher;
/**
 * 店铺相关信息
 * @author 谷子夜
 */
@Controller
@RequestMapping("/shop")
public class ShopController extends BaseController{

	@Autowired
    private ShopService shopService;
	@Autowired
    private SupplierDao supplierDao;
    @Autowired
    @Qualifier("commentsService")
    private CommentsService commentsService;
	@Autowired
	private CollectionShopService csService;
    @Autowired
    private ClientAccessLogService clientAccessLogService;
	@Autowired
	private UserImService userImService;
    @Autowired
    private WodeSearchManager wsm;
	@Autowired
	private EntParamCodeService entParamCodeService;
	@Autowired
	private RedisUtil redisUtil;
	/**
     * 店铺首页
     * @param page  页码
     * @param cat   店铺分类ID
     * @return
     */
    @RequestMapping(value="shopProduct", method=RequestMethod.GET)
    @ResponseBody
    public ActResult<ModelMap> index(Long shopId, Integer page, Long storeCategoryId,ModelMap model){
    	if(page==null || page==0){
			page=1;
		}
        PageInfo pageInfo;
        Shop shop=shopService.getShopSettingById(shopId);
        List<CollectionShop> list = csService.selectByShopId(shopId);
        
        List<StoreCategory> categoryList = shopService.getStoreCategories(shop.getSupplierId());
        if (storeCategoryId!=null) {
            pageInfo = shopService.getProductsByStoreCategory(storeCategoryId, page);
            model.addAttribute("cat", storeCategoryId);
            for(StoreCategory sc : categoryList) {
            	boolean flag=false;
                for(StoreCategory child : sc.getChildren()) {
                    if (child.getId().equals(storeCategoryId)) {
                    	model.addAttribute("parent", sc.getId());
                        flag=true;
                        break ;
                    }
                }
                if(flag){
                	break;
                }
            }
        } else {
            pageInfo = shopService.getProductsByShop(shop.getSupplierId(), shopId, page);
        }
        model.addAttribute("collectionShopCount", list.size());
        model.addAttribute("page", pageInfo);
        model.addAttribute("categories", categoryList);
        model.addAttribute("shopDetail", shop);
        Supplier supplier = supplierDao.getById(shop.getSupplierId());
        model.addAttribute("supplier", supplier);
		UserIm shopIm= userImService.selectByShopAndSupplier(shopId, shop.getSupplierId());
		model.addAttribute("shopImUser",shopIm==null?null:shopIm.getOpenimId());
		ActResult<ModelMap> rtn = ActResult.success(model);
		rtn.setMsg(entParamCodeService.getBenefitSubsidy().toString());
        return rtn;
    }
	/**
	 * 店铺详细信息	
	 * @param model
	 * @param shopId
	 * @return
	 */
    @RequestMapping(value="detail", method=RequestMethod.GET)
    @ResponseBody
    public ActResult<String> info(ModelMap model, Long shopId,HttpServletRequest request){
    	Shop shop=shopService.getShopSettingById(shopId);
        if(shop!=null){
        	model.addAttribute("shop", shop);
            Supplier supplier = supplierDao.getById(shop.getSupplierId());
        	model.addAttribute("supplier", supplier);
        	//店铺的相关评价（目前无规则，暂时写死）
        	//'服务评级'
        	Map<String, Object> map= commentsService.getSupplierScore(shop.getSupplierId());

        	model.addAttribute("shopDescription", map.get(CommentsService.CACHE_MAP_KEY_GOODS_AVG));
        	model.addAttribute("shopService",  map.get(CommentsService.CACHE_MAP_KEY_SERVICE_AVG));
        	model.addAttribute("deliverySpeed", map.get(CommentsService.CACHE_MAP_KEY_LOGISTICS_AVG));

    		UserIm shopIm= userImService.selectByShopAndSupplier(shopId, shop.getSupplierId());
    		model.addAttribute("shopImUser",shopIm==null?null:shopIm.getOpenimId());
        	model.addAttribute("free",(shop.getShippingFree()==null||shop.getShippingFree()==-1)?"":"全场满"+shop.getShippingFree()+"元包邮");
    		
    		clientAccessLogService.saveNormal(ClientAccessLog.ACCESS_TYPE_SHOP, shopId.toString(), shop.getShopname(), null,null,null,IPUtils.getClientAddress(request));
        	return ActResult.success(model);
        }else{
        	return ActResult.fail("店铺不存在");
        }
    }
    

	@RequestMapping(value="page")
    public ModelAndView page(Long shopId, ModelAndView model,HttpServletRequest request) {
		
		model.addObject("shopId", shopId);
		model.setViewName("shop");
		return model;
	}
	
	@RequestMapping(value="shopIndex", method=RequestMethod.GET)
    @ResponseBody
    public ActResult<ModelMap> shopIndex(Long shopId,Long storeCategoryId,@RequestParam(defaultValue = "1") Integer page,ModelMap model){
		Shop shop = shopService.getShopSettingById(shopId);
        List<StoreCategory> categoryList = shopService.getStoreCategories(shop.getSupplierId());
        WodeSearcher searcher = wsm.getSearcher();
        WodeResult result;
        String queryString;
        List<CollectionShop> list = csService.selectByShopId(shopId);
        if (storeCategoryId != null) {
            queryString = "shop=" + shopId + "&customCat=" + storeCategoryId +"&page="+page;
            if(!queryString.contains("sort")) {
            	queryString = queryString+"&sort=sortNum_0";
            }
            result = searcher.search(queryString, null, false,false,false);
            model.addAttribute("storeCategoryId", storeCategoryId);
            for (StoreCategory sc : categoryList) {
                boolean flag = false;
                for (StoreCategory child : sc.getChildren()) {
                    if (child.getId().equals(storeCategoryId)) {
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
            queryString = "shop=" + shopId + "&page=" + page;
            if(!queryString.contains("sort")) {
            	queryString = queryString+"&sort=sortNum_0";
            }
            result = searcher.search(queryString, null, false,false,false);
        }
        model.addAttribute("result", result);
        model.addAttribute("page", page);
        model.addAttribute("categories", categoryList);
        model.addAttribute("shopDetail", shop);
        model.addAttribute("collectionShopCount", list.size());
        Supplier supplier = supplierDao.getById(shop.getSupplierId());
        model.addAttribute("supplier", supplier);
        UserIm shopIm= userImService.selectByShopAndSupplier(shopId, shop.getSupplierId());
		model.addAttribute("shopImUser",shopIm==null?null:shopIm.getOpenimId());
		String shopStatus="";
		if (shopIm!=null) {
			 shopStatus = EasemobIMUtils.checkUserStatus(shopIm.getOpenimId());
		}else{
			shopStatus = "offline";
		}
		model.addAttribute("shopStatus", shopStatus);
		ActResult<ModelMap> rtn = ActResult.success(model);
		rtn.setMsg(entParamCodeService.getBenefitSubsidy().toString());
        return rtn;
	}
	
}
