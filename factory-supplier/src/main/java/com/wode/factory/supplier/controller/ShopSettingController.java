/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wode.common.db.DBUtils;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.redis.RedisUtilEx;
import com.wode.common.result.Result;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.stereotype.Token;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.ApprShop;
import com.wode.factory.model.ApprSupplier;
import com.wode.factory.model.Attachment;
import com.wode.factory.model.BrandProducttype;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.model.ProductBrandImage;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.Shop;
import com.wode.factory.model.StoreCategory;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierCategory;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.supplier.query.ProductQuery;
import com.wode.factory.supplier.query.StoreCategoryQuery;
import com.wode.factory.supplier.service.ApprShopService;
import com.wode.factory.supplier.service.ApprSupplierService;
import com.wode.factory.supplier.service.AttachmentService;
import com.wode.factory.supplier.service.BrandProducttypeService;
import com.wode.factory.supplier.service.ProductBrandImageService;
import com.wode.factory.supplier.service.ProductBrandService;
import com.wode.factory.supplier.service.ProductService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.StoreCategoryService;
import com.wode.factory.supplier.service.SupplierCategoryService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.util.UserInterceptor;

@Controller
@RequestMapping("shopSetting")
public class ShopSettingController extends BaseSpringController {
    //默认多列排序,example: username desc,createTime asc
    protected static final String DEFAULT_SORT_COLUMNS = null;
    @Autowired
    @Qualifier("shopService")
    private ShopService shopService;

	@Autowired
	@Qualifier("brandProducttypesService")
	private BrandProducttypeService brandProducttypesService;

    @Autowired
    @Qualifier("supplierService")
    private SupplierService supplierService;

	@Autowired
	private ApprShopService apprShopService;
	
    @Autowired
    @Qualifier("supplierCategoryService")
    private SupplierCategoryService supplierCategoryService;

    @Autowired
    @Qualifier("storeCategoryService")
    private StoreCategoryService storeCategoryService;

    @Autowired
    @Qualifier("productCategoryService")
    private ProductCategoryService productCategoryService;

    @Autowired
    @Qualifier("productService-supplier")
    private ProductService productService;

	@Autowired
	private ApprSupplierService apprSupplierService;
    @Autowired
    private ProductBrandService pbs;
	@Autowired
	private DBUtils dbUtils;

	@Autowired
	@Qualifier("attachmentService")
	private AttachmentService attachmentService;
	@Autowired
	@Qualifier("productBrandService")
	private ProductBrandService productBrandService;
	@Autowired
	private ProductBrandImageService productBrandImageService;
	@Autowired
	private RedisUtilEx redisUtilEx;
	
	/**
	 * 验证成功标志
	 */
	private final static String VALIDATE_SUCCESS = "true";
    public ShopSettingController() {
    }

    /**
     * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
     */
    @ModelAttribute
    public void init(ModelMap model, HttpServletRequest request) {
        model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
        Map<String, Object> map = new HashMap<String, Object>();
        com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
        Long supplierId = null;
        if (userModel != null) {
            Supplier supplier = supplierService.getById(userModel.getSupplierId());
            if (supplier != null) {
                supplierId = supplier.getId();
            }
        }
        if (supplierId == null) {
            supplierId = -1l;
        }
        map.put("supplierId", supplierId);
        List<StoreCategory> all = storeCategoryService.findAllBymap(map);
        List<StoreCategory> roots = new ArrayList<StoreCategory>();
        Iterator<StoreCategory> it = all.iterator();
        while (it.hasNext()) {
            StoreCategory sc = it.next();
            if (sc.getGrade() == 1) {
                roots.add(sc);
                it.remove();
            }
        }
        //设置2级
        it = roots.iterator();
        while (it.hasNext()) {
            StoreCategory root = it.next();
            Iterator<StoreCategory> it1 = all.iterator();
            while (it1.hasNext()) {
                StoreCategory sc = it1.next();
                if (sc.getParent().equals(root.getId())) {
                    root.getChildren().add(sc);
                    it1.remove();
                }
            }
        }
        model.put("cates", roots);

        Shop record = new Shop();
        record.setSupplierId(supplierId);
        model.put("shopList", shopService.selectByModel(record));
    }


    /**
     * 进入新增页面
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @Token(save = true)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response, Shop shopSetting) throws Exception {
        return new ModelAndView("product/shopSetting/create", "shopSetting", shopSetting);
    }

    /**
     * 保存新增对象
     */
    @RequestMapping(value = "setshopname", method = RequestMethod.POST)
    @Token(remove = true)
    @NoCheckLogin
    public Result setshopname(HttpServletRequest request, HttpServletResponse response,ApprShop shop) throws Exception {
        Result result = new Result();
        com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);

        if (us == null) {
            result.setErrorCode("1000");
            result.setMessage("用户未登陆");
        } else {

    		Map<String, Object> regMap = validateRegister(shop, request);
    		//参数验证方法
    		if(VALIDATE_SUCCESS.equals(regMap.get("regRes"))) {

                Shop record = new Shop();
                record.setShopname(shop.getShopname());
                List<Shop> ls= shopService.selectByModel(record);
                if(ls==null || ls.isEmpty()){
                    //shopSettingService.setshopname(param);
                } else {
                	if(ls.size() > 1) {
	                    result.setErrorCode("0");
	                    result.setMessage("商铺名称已存在");
	                    return result;
                	} else {
                		if(!ls.get(0).getId().equals(shop.getOldId())){
    	                    result.setErrorCode("0");
    	                    result.setMessage("商铺名称已存在");
    	                    return result;
                		}
                	}
                }
                
    			shop.setSupplierId(us.getSupplierId());
				shop.setShopTel(
						!StringUtils.isNullOrEmpty(shop.getShopTel3())
						?shop.getShopTel1()+"-"+shop.getShopTel2()+"-"+shop.getShopTel3()
						:shop.getShopTel1()+"-"+shop.getShopTel2());
				shop.setCusTel(
						!StringUtils.isNullOrEmpty(shop.getCusTel3())
						?shop.getCusTel1()+"-"+shop.getCusTel2()+"-"+shop.getCusTel3()
						:shop.getCusTel1()+"-"+shop.getCusTel2());
				shop.setSerTel(
						!StringUtils.isNullOrEmpty(shop.getSerTel3())
						?shop.getSerTel1()+"-"+shop.getSerTel2()+"-"+shop.getSerTel3()
						:shop.getSerTel1()+"-"+shop.getSerTel2());
				
    			Supplier resupplier = supplierService.getById(us.getSupplierId());
    			//更新入住步骤
    			ApprSupplier apprS = apprSupplierService.getSupplierApprIng(us.getSupplierId());

				if(resupplier != null) {
					shop.setManagerId(resupplier.getManagerId());
					shop.setManagerName(resupplier.getManagerName());
				} else {

    				//设置招商经理
    				if(apprS!=null){
    					if(apprS.getManagerId() != null) {
    						shop.setManagerId(apprS.getManagerId());
    						shop.setManagerName(apprS.getManagerName());
    					}
    				}
				}
				
    			ApprShop appr= apprShopService.getShopApprIng(us.getSupplierId());
    			//未注册新增
    			if(appr==null){

    				shop.setShopId(dbUtils.CreateID());
    				shop.setCreatTime(new Date());
    				shop.setCreatUser(us.getId());
    				shop.setCreatName(StringUtils.isEmpty(us.getRealName())?us.getNickName():us.getRealName());
    				shop.setToEmail(us.getEmail());
    				shop.setApprType("0");
    				if(shop.getOldId() != null) {
        				shop.setUpdateDesc("修改店铺");
    				} else {
        				shop.setUpdateDesc("新增店铺");    					
    				}
    				shop.setStatus(0);
    				apprShopService.save(shop);	
    				
    				if(shop.getOldId() != null) {

    					//分类copy
    					supplierCategoryService.copyByShop(shop.getSupplierId(), shop.getShopId(), shop.getOldId());
    					//资质copy
    					attachmentService.copyByShop(shop.getSupplierId(), shop.getShopId(), shop.getOldId());
    					//品牌copy
    					productBrandService.copyByShop(shop.getSupplierId(), shop.getShopId(), shop.getOldId());
    						
    					//品牌资质copy
    					productBrandImageService.copyByShop(shop.getSupplierId(), shop.getShopId());
    					brandProducttypesService.copyByShop(shop.getSupplierId(), shop.getShopId());
    				}

    				
    			} else {
    				shop.setId(appr.getId());
    				shop.setShopId(appr.getShopId());
    				shop.setUserId(appr.getUserId());
    				shop.setStatus(appr.getStatus());
    				
    				apprShopService.update(shop);
    				
    				//修噶品牌类型
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("supplierId",shop.getSupplierId());
					map.put("shopId", shop.getShopId());
    				map.put("isDelete", 0);
					List<ProductBrand> productBrandlist = productBrandService.findAllBymap(map);
					for (ProductBrand brand : productBrandlist) {
						if(shop.getType() == 2) {
							brand.setNatural(0);
						} else {
							brand.setNatural(1);    							
						}
						productBrandService.update(brand);
					}
    			}
    			
    			if(apprS!=null && apprS.getEnterType()==1) {
    				apprS.setEnterType(2);
    				apprSupplierService.update(apprS);
    			}
    			
                result.setErrorCode("1");
                result.setMessage("操作成功");
    		}else{
    			Result re =  (Result) regMap.get("result");
    			result.setErrorCode(re.getErrorCode());
    			result.setMessage(re.getMessage());
    			result.setKey(re.getKey());
    		}
        }

        return result;
    }

    /**
     * 保存分类信息
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @Token(remove = true)
    @NoCheckLogin
    public Result save(HttpServletRequest request, HttpServletResponse response, String categoryids) throws Exception {

        Result result = new Result();
        com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
        if (us == null) {
            result.setErrorCode("1000");
            result.setMessage("用户未登陆");
        } else {
        	boolean categoryOnly=false;
			ApprShop appr= apprShopService.getShopApprIng(us.getSupplierId());
            if (appr == null) {
            	categoryOnly = true;
            	appr = makeApprShop(us, Long.parseLong(request.getParameter("oid")));
            }
                
            supplierCategoryService.removeBySupplierId(appr.getSupplierId(),appr.getShopId());
            if (!"".equals(categoryids)) {
            	supplierCategoryService.insertBatch(appr.getSupplierId(),appr.getShopId(), categoryids);
            }

            if(!StringUtils.isEmpty(appr.getOldId())) {
            	List<SupplierCategory> ls = supplierCategoryService.getAddCategorys(appr.getSupplierId(), appr.getShopId(), appr.getOldId());
            	String add = ";新增分类：";
            	for (SupplierCategory supplierCategory : ls) {
            		add += supplierCategory.getCategoryName()+",";
				}
            	if(appr.getUpdateDesc()==null || !appr.getUpdateDesc().contains(add)) {
            		appr.setUpdateDesc(appr.getUpdateDesc() + add);
            	}
    			if("1".equals(appr.getApprType())) {
    				appr.setStatus(1);
    			}
        		apprShopService.update(appr);            	
            }
            result.setErrorCode("0");
            result.setMessage("修改成功");
            

			//更新入住步骤
            if(!categoryOnly) {
				ApprSupplier apprS = apprSupplierService.getSupplierApprIng(us.getSupplierId());
				if(apprS!=null && apprS.getEnterType()==2) {
					apprS.setEnterType(3);
					apprSupplierService.update(apprS);
				}
            }
        }
        return result;
    }

	private ApprShop makeApprShop(UserFactory us, Long shopId) {
		ApprShop appr = new ApprShop();
		Shop shop = shopService.getById(shopId);
		Supplier resupplier = supplierService.getById(us.getSupplierId());
		
		appr.setId(dbUtils.CreateID());
		appr.setApprType("1");
		appr.setStatus(1);
		appr.setShopId(dbUtils.CreateID());
		appr.setOldId(shop.getId());
		appr.setUserId(shop.getUserId());
		appr.setSupplierId(shop.getSupplierId());
		appr.setLogo(shop.getLogo());
		appr.setShopname(shop.getShopname());
		appr.setIntroduce(shop.getIntroduce());
		appr.setBanner(shop.getBanner());
		appr.setType(shop.getType());
		appr.setTopImage(shop.getTopImage());
		appr.setBrandIntroduce(shop.getBrandIntroduce());
		appr.setIntroImage(shop.getIntroImage());
		appr.setCusTel(shop.getCusTel());
		appr.setCusPhone(shop.getCusPhone());
		appr.setCusEmail(shop.getCusEmail());
		appr.setCusContact(shop.getCusContact());
		appr.setSerTel(shop.getSerTel());
		appr.setSerPhone(shop.getSerPhone());
		appr.setSerEmail(shop.getSerEmail());
		appr.setSerContact(shop.getSerContact());
		appr.setShopTel(shop.getShopTel());
		appr.setShopPhone(shop.getShopPhone());
		appr.setShopEmail(shop.getShopEmail());
		appr.setShopContact(shop.getShopContact());
		appr.setQq(shop.getQq());
		appr.setCreatTime(new Date());
		appr.setCreatUser(us.getId());
		appr.setCreatName(StringUtils.isEmpty(us.getRealName())?us.getNickName():us.getRealName());
		appr.setToEmail(us.getEmail());
		appr.setManagerId(resupplier.getManagerId());
		appr.setManagerName(resupplier.getManagerName());
		appr.setUpdateDesc("修改分类");

		apprShopService.save(appr);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("supplierId",appr.getSupplierId());
		map.put("shopId", shopId);
		//获取全部资质信息
		List<Attachment> list = attachmentService.findAll(map);
		//获取全部品牌
		map.put("isDelete", 0);
		List<ProductBrand> productBrandlist = productBrandService.findAllBymap(map);

		//资质copy
		for (Attachment i : list) {
			i.setId(dbUtils.CreateID());
			i.setShopId(appr.getShopId());
			attachmentService.save(i);
		}
		
		//品牌copy
		for (ProductBrand i : productBrandlist) {
			
			//品牌资质copy
			ProductBrandImage record = new ProductBrandImage();
			record.setSupplierId(appr.getSupplierId());
			record.setBrandId(i.getId());
			List<ProductBrandImage> s = productBrandImageService.selectByModel(record);

			Map<String,Object> map2 = new HashMap<String, Object>();
			map2.put("brandId", i.getId());
			map2.put("supplierId",appr.getSupplierId());
			List<BrandProducttype> l = brandProducttypesService.findAllByMap(map2);
			
			i.setOldId(i.getId());
			i.setId(dbUtils.CreateID());
			i.setShopId(appr.getShopId());
			productBrandService.save(i);
			
			for (ProductBrandImage productBrandImage : s) {
				productBrandImage.setId(dbUtils.CreateID());
				productBrandImage.setBrandId(i.getId());
				productBrandImageService.save(productBrandImage);
			}


			for (BrandProducttype brandProducttype : l) {
				brandProducttype.setId(dbUtils.CreateID());
				brandProducttype.setBrandId(i.getId());
				brandProducttypesService.save(brandProducttype);				
			}
			
		}
		return appr;
	}

    /**
     * 进入更新页面
     */
    @RequestMapping(value = {"", "edit"}, method = RequestMethod.GET)
    @Token(save = true)
    public String edit(HttpServletRequest request, ModelMap model,Long id) throws Exception {
        com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
        Shop shop = null;
        
        if(id == null) {
        	Shop p = new Shop();
        	p.setSupplierId(us.getSupplierId());
        	shop = shopService.selectByModel(p).get(0);
        } else {
        	shop = shopService.getById(id);
        }
        		
        		

        Shop record = new Shop();
        record.setSupplierId(us.getSupplierId());
        model.put("shopList", shopService.selectByModel(record));
        
        Supplier supplier = supplierService.getById(us.getSupplierId());
        model.addAttribute("ss", shop);
        model.addAttribute("sp", supplier);
        StoreCategoryQuery scq = new StoreCategoryQuery();
        scq.setSupplierId(us.getSupplierId());
        scq.setPageSize(1000);
        PageInfo<?> stcs = storeCategoryService.findPage(scq);
        Map map = new HashMap<>();
        map.put("supplierId", supplier.getId());
        map.put("shopId",shop.getId());
        map.put("isDelete",0);
        List<ProductBrand> brands = pbs.findAllBymap(map);
        model.addAttribute("brands", brands);
        model.addAttribute("allCats", stcs.getList());
        return "product/shopsetting/edit";
    }

    /**
     * 保存更新对象
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @Token(remove = true)
    public String update(HttpServletRequest request) throws Exception {
    	com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
    	Supplier supplier = supplierService.getById(userModel.getSupplierId());
        Long shopsettingId = Long.valueOf(request.getParameter("shopsettingId"));
        Shop shop = shopService.getById(shopsettingId);
        String introduce = request.getParameter("introduce");
        shop.setIntroduce(introduce);
        String logo = request.getParameter("logo");
        if(StringUtils.isEmpty(supplier.getFirmLogo())){
        	supplier.setId(supplier.getId());
        	supplier.setFirmLogo(logo);
        	supplierService.updateFirmLogo(supplier);
        }
        shop.setLogo(logo);
	    String qq = request.getParameter("qq");	    
	    if(org.apache.commons.lang3.StringUtils.isNotBlank(qq)) {
	    	shop.setQq(Long.valueOf(qq));
	    }
        String phone = request.getParameter("shopPhone");
        shop.setShopPhone(phone);
        shopService.update(shop);
        //修改完，删除缓存
        redisUtilEx.delKey("shop_getShopSettingById_["+shopsettingId+"]");
        return "redirect:/shopSetting.html?id="+shopsettingId;
    }

    /**
     * 店铺页面信息
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "pageset", method = RequestMethod.GET)
    @Token(save = true)
    public String pageSet(HttpServletRequest request, ModelMap model,Long id) {
        com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
        Shop shop = shopService.getById(id);
        model.addAttribute("ss", shop);

        List<String> banners=new ArrayList<String>();
        if(com.wode.common.util.StringUtils.isEmpty(shop.getBanner())) {
        } else {
        	String[] jpgs=shop.getBanner().split(",");
        	if(jpgs.length==0) {
        	} else {
        		for(int i=0;i<jpgs.length;i++) {
        			banners.add(jpgs[i].trim());
        		}
        	}
        }
        model.addAttribute("banners", banners);

        List<String> introImages=new ArrayList<String>();
        if(com.wode.common.util.StringUtils.isEmpty(shop.getIntroImage())) {
        } else {
        	String[] jpgs=shop.getIntroImage().split(",");
        	if(jpgs.length==0) {
        	} else {
        		for(int i=0;i<jpgs.length;i++) {
        			introImages.add(jpgs[i].trim());
        		}
        	}
        }
        model.addAttribute("introImages", introImages);
        
        StoreCategoryQuery scq = new StoreCategoryQuery();
        scq.setSupplierId(us.getSupplierId());
        scq.setPageSize(1000);
        PageInfo<?> stcs = storeCategoryService.findPage(scq);
        model.addAttribute("allCats", stcs.getList());
        return "product/shopsetting/pageset";
    }
    
    /**
     * 店铺页面信息
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "categoryBrand", method = RequestMethod.GET)
    public String categoryBrand(HttpServletRequest request, ModelMap model,Long id) {
        com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
        Shop shop = shopService.getById(id);

		//获取商家信息
		ApprShop appr= apprShopService.getShopApprIng(us.getSupplierId());
		Long editId = 0L;
		if(appr!=null) editId=appr.getOldId();
		
		Map newmap = new HashMap();
		newmap.put("supplierId",appr==null?shop.getSupplierId():appr.getSupplierId());
		newmap.put("shopId",shop==null?appr.getShopId():shop.getId());
		List<SupplierCategory> cslist = supplierCategoryService.getlistByMap(newmap);
		model.addAttribute("csList", cslist);
		
		//获取商家信息
		Supplier supplier = supplierService.getById(us.getSupplierId());
		model.addAttribute("comName", supplier!=null?supplier.getComName():"");
		model.addAttribute("cashDeposit", supplier!=null?supplier.getCashDeposit():"");
				
		model.addAttribute("ss", shop);
		model.addAttribute("mode", "平台模式");
		Date now = new Date();
		Date end = TimeUtil.addDay(now, 365);
		
		model.addAttribute("begin", now);
		model.addAttribute("end", end);
		model.addAttribute("editId",editId==null?0:editId);
		model.addAttribute("apprStatus",appr==null?0:appr.getStatus());
		//更新入住步骤
		ApprSupplier apprS = apprSupplierService.getSupplierApprIng(us.getSupplierId());
		model.addAttribute("apprType",appr==null?"-1":appr.getApprType());
		model.addAttribute("apprS",apprS==null?0:1);
		
        return "product/shopsetting/categoryBrand";
    }
    
    /**
     * 更新店铺页面
     */
    @RequestMapping(value = "setpage", method = RequestMethod.POST)
    @Token(remove = true)
    public String setPage(Shop ss) throws Exception {
        Shop shop = shopService.getById(ss.getId());
        shop.setTopImage(ss.getTopImage());
        shop.setBanner(ss.getBanner());
        shop.setBrandIntroduce(ss.getBrandIntroduce());
        shop.setIntroImage(ss.getIntroImage());
        shopService.update(shop);
        //修改完，删除缓存
        redisUtilEx.delKey("shop_getShopSettingById_["+ss.getId()+"]");
        return "redirect:pageset.html?id="+ss.getId();
    }

    @RequestMapping("products")
    public String products(HttpServletRequest request, Model model, ProductQuery query, String mark) {
        UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
        StoreCategoryQuery scq = new StoreCategoryQuery();
        scq.setSupplierId(userModel.getSupplierId());
        scq.setPageSize(1000);
        PageInfo<?> stcs = storeCategoryService.findPage(scq);
        model.addAttribute("allCats", stcs.getList());
        PageInfo<?> page;
        query.setSupplierId(userModel.getSupplierId());
        if ("all".equals(mark)) {
            page = storeCategoryService.getProductsBySupplierId(query);
        } else if ("TMP".equals(mark)) {
            page = storeCategoryService.getProductsByStoreCategory(query);
        } else {
            try {
                Long sid = Long.valueOf(mark);
                query.setId(sid);
                StoreCategory storeCategory = storeCategoryService.getById(sid);
                model.addAttribute("sc", storeCategory);
                if (storeCategory.getParent() != null) {
                    StoreCategory parent = storeCategoryService.getById(storeCategory.getParent());
                    model.addAttribute("parent", parent);
                }
                page = storeCategoryService.getProductsByStoreCategory(query);
            } catch (Exception e) {
                return "redirect:products.html?mark=all";
            }
        }
        model.addAttribute("mark", mark);
        model.addAttribute("page", page);
        model.addAttribute("query", query);
        return "product/shopsetting/products";
    }

    /**
     * 商品list
     */
    @RequestMapping(value = "findProductlistForCenter")
    public ModelAndView productlistForCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Result result = new Result();
        ModelAndView mv = new ModelAndView();
        //在session中获取userModel
        com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
        String funcName = "全部商品";
        Supplier supplier = supplierService.getById(userModel.getSupplierId());
        String pages = request.getParameter("pages");
        String sizes = request.getParameter("sizes");
        Integer page = 1;
        Integer size = 10;
        mv.setViewName("product/shopsetting/productlistforcenter");
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

        String name = request.getParameter("name");
        String minprice = request.getParameter("minprice");
        String maxprice = request.getParameter("maxprice");
        String selltype = request.getParameter("selltype");

        //排序字段
        String sortname = request.getParameter("sortname");
        String pricesort = request.getParameter("pricesort");
        String createDatesort = request.getParameter("createDatesort");

        if (StringUtils.isEmpty(sortname)) {
            sortname = "createDatesort";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("supplierId", supplier.getId());
        map.put("name", name);
        map.put("sortname", sortname);
        map.put("selltype", selltype);
        if (!StringUtils.isEmpty(minprice)) {
            map.put("minprice", new Double(minprice));
        }
        if (!StringUtils.isEmpty(maxprice)) {
            map.put("maxprice", new Double(maxprice));
        }
        if (!StringUtils.isEmpty(pricesort)) {
            map.put("pricesort", new Integer(pricesort));
        }
        if (StringUtils.isEmpty(createDatesort)) {
            createDatesort = "2";
        }
        if (!StringUtils.isEmpty(request.getParameter("NoStoId"))) {
            map.put("NoStoId", true);
            funcName = "未分类商品";

        }
        map.put("createDatesort", new Integer(createDatesort));
        mv.addObject("pages", page);
        mv.addObject("sizes", size);
        mv.addObject("name", name);
        mv.addObject("minprice", minprice);
        mv.addObject("maxprice", maxprice);
        mv.addObject("sortname", sortname);
        mv.addObject("pricesort", pricesort);
        mv.addObject("createDatesort", createDatesort);
        mv.addObject("selltype", selltype);

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
            result.setPage(page);
            result.setSize(size);
            result.setTotal(total);
            result.setErrorCode("0");
            result.setMsgBody(list);
        } else {
            result.setErrorCode("1000");
        }
        mv.addObject("result", result);
        mv.addObject("funcName", funcName);
        return mv;
    }

    /**
     * 通过三级ID查询父  并且保存   ajax
     */
    @RequestMapping(value = "findbyajax")
    @NoCheckLogin
    public ModelAndView findbyajax(HttpServletRequest request, HttpServletResponse response, String categoryids, String nowids) throws Exception {
        Result result = new Result();
        ModelAndView mv = new ModelAndView();
        String[] categoryid = categoryids.split(",");
        String[] nowid = nowids.split(",");
        List<Long> param = new ArrayList<Long>();
        if (nowid == null || "".equals(nowid[0])) {
            for (String newid : categoryid) {
                if (!"".equals(newid)) {
                    param.add(new Long(newid));
                }
            }
        } else {
            for (String nid : nowid) {
                if (!"".equals(nid)) {
                    param.add(new Long(nid));
                }
            }
            for (String newid : categoryid) {
                if (!"".equals(newid)) {
                    if (!"".equals(nowids)) {
                        if (!param.contains(new Long(newid))) {
                            param.add(new Long(newid));
                        }
                    }else{
                    	param.add(new Long(newid));
                    }
                }
            }
        }
        Collection<ProductCategory> supplierCategoryLists = productCategoryService.findParentsByGroup(param);
        if (supplierCategoryLists != null && supplierCategoryLists.size() > 0) {
            result.setErrorCode("0");
        } else {
            result.setErrorCode("1000");
        }
        mv.addObject("supplierCategoryLists", supplierCategoryLists);
        mv.addObject("scsize", supplierCategoryLists != null ? supplierCategoryLists.size() : 0);
        mv.addObject("result", result);
        return mv;
    }


	/** 
	 * 验证Supplier信息完整度
	 **/
	private Map<String, Object> validateRegister(ApprShop shop,
			HttpServletRequest request) {
		Result result = new Result();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isNullOrEmpty(shop.getShopname())) {
            result.setErrorCode("2000");
            result.setMessage("店铺名称必须输入");
		} else if(StringUtils.isNullOrEmpty(shop.getShopContact())) {
			result.setKey("shopContact");
			result.setErrorCode("3342");
			result.setMessage("店铺负责人姓名不能为空！");
		} else if(StringUtils.isNullOrEmpty(shop.getShopTel1())||StringUtils.isNullOrEmpty(shop.getShopTel2())) {
			result.setKey("shopTel");
			result.setErrorCode("3343");
			result.setMessage("店铺固定电话不能为空！");
		} else if(!StringUtils.isPhoneNumber(shop.getShopPhone())) {
			result.setKey("shopPhone");
			result.setErrorCode("3344");
			result.setMessage("店铺手机不能为空！");
		} else if(StringUtils.isNullOrEmpty(shop.getCusContact())) {
			result.setKey("cusContact");
			result.setErrorCode("3346");
			result.setMessage("售后负责人姓名不能为空！");
		} else if(StringUtils.isNullOrEmpty(shop.getCusTel1())||StringUtils.isNullOrEmpty(shop.getCusTel2())) {
			result.setKey("cusTel");
			result.setErrorCode("3347");
			result.setMessage("售后固定电话不能为空！");
		} else if(!StringUtils.isPhoneNumber(shop.getCusPhone())) {
			result.setKey("cusPhone");
			result.setErrorCode("3348");
			result.setMessage("售后手机不能为空！");
		} else if(StringUtils.isNullOrEmpty(shop.getSerContact())) {
			result.setKey("serContact");
			result.setErrorCode("3350");
			result.setMessage("客服负责人姓名不能为空！");
		} else if(StringUtils.isNullOrEmpty(shop.getSerTel1())||StringUtils.isNullOrEmpty(shop.getSerTel2())) {
			result.setKey("serTel");
			result.setErrorCode("3351");
			result.setMessage("客服固定电话不能为空！");
		} else if(!StringUtils.isPhoneNumber(shop.getSerPhone())) {
			result.setKey("serPhone");
			result.setErrorCode("3352");
			result.setMessage("客服手机不能为空！");
		} else {
			result.setErrorCode("0");
			resultMap.put("regRes", "true");
			resultMap.put("result", result);
			return resultMap;
		}
		resultMap.put("regRes", "false");
		resultMap.put("result", result);
		return resultMap;
	}
}

