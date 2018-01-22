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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.constant.UserConstant;
import com.wode.common.db.DBUtils;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.result.Result;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.stereotype.Token;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.company.service.EntParamCodeService;
import com.wode.factory.company.service.EnterpriseService;
import com.wode.factory.model.ApprShop;
import com.wode.factory.model.ApprSupplier;
import com.wode.factory.model.Attachment;
import com.wode.factory.model.BrandProducttype;
import com.wode.factory.model.Enterprise;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.model.ProductBrandImage;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.Shop;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierCategory;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.supplier.service.ApprShopService;
import com.wode.factory.supplier.service.ApprSupplierService;
import com.wode.factory.supplier.service.AttachmentService;
import com.wode.factory.supplier.service.BrandProducttypeService;
import com.wode.factory.supplier.service.CommissionSettingService;
import com.wode.factory.supplier.service.ProductBrandImageService;
import com.wode.factory.supplier.service.ProductBrandService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierCategoryService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.service.UserService;
import com.wode.factory.supplier.util.UserInterceptor;

@Controller
@RequestMapping("supplier")
public class SupplierController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 

	private final static String PROTOCOL_FILE_VESION = "我的网平台联盟协议 V3.pdf";
	/**
	 * 验证成功标志
	 */
	private final static String VALIDATE_SUCCESS = "true";

	@Autowired
	private DBUtils dbUtils;
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	@Autowired
	@Qualifier("enterpriseService")
	private EnterpriseService enterpriseService;
	
	@Autowired
	@Qualifier("productCategoryService")
	private ProductCategoryService productCategoryService;
	
	@Autowired
	@Qualifier("supplierCategoryService")
	private SupplierCategoryService supplierCategoryService;
	
	@Autowired
	@Qualifier("attachmentService")
	private AttachmentService attachmentService;
	
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	@Autowired
	private ApprShopService apprShopService;
	
	@Autowired
	@Qualifier("commissionSettingService")
	private CommissionSettingService commissionSettingService;
	
	@Autowired
	@Qualifier("productBrandService")
	private ProductBrandService productBrandService;
	
	@Autowired
	@Qualifier("brandProducttypesService")
	private BrandProducttypeService brandProducttypesService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	private ProductBrandImageService productBrandImageService;

	@Autowired
	private ApprSupplierService apprSupplierService;

	@Autowired
	private EntParamCodeService entParamCodeService;
	public SupplierController() {
	}

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	/** 
	 * 入驻流程入口
	 **/
	@RequestMapping(value="enterMain",method=RequestMethod.GET)
	@NoCheckLogin
	public ModelAndView enterMain(HttpServletRequest request,HttpServletResponse response) throws Exception {
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			//查询是否注册商家
			Supplier supplier = supplierService.getById(us.getSupplierId());
			if(supplier!=null) {
				mv.setViewName("redirect:/user/tosuppliermain.html");				
			} else {
				ApprSupplier appr = apprSupplierService.getSupplierApprIng(us.getSupplierId());
				/**
				 * 如果商家审核未通过。将会跳转到入住流程第四步,签订合同。确保商家可以修改入住信息。再次提交进行审核
				 * status  -1审核未通过
				 * entertype 4 入住第四步
				 * */
				ApprShop shop= apprShopService.getShopApprIng(us.getSupplierId());
				if(null!=appr&&appr.getStatus()==-1){
					appr.setEnterType(1);
					this.apprSupplierService.update(appr);
					
					if(shop == null) {
						mv.setViewName("redirect:/supplier/tocreatesupplierinfo.html");	
						return mv;						
					} 
				}
				//判断入驻进度跳转不同页面
				if(appr==null){
					mv.setViewName("redirect:/supplier/tocreatesupplierinfo.html");
				}else{
					if(appr.getEnterType()==1){
						mv.setViewName("redirect:/supplier/torecruitmentstore.html");
					}else if(appr.getEnterType()==0){
						mv.setViewName("redirect:/supplier/tocreatesupplierinfo.html");
					}else if(appr.getEnterType()==2){
						mv.setViewName("redirect:/supplier/torecruitmenttype.html");
					}else if(appr.getEnterType()==3){
						mv.setViewName("redirect:/supplier/torecruitmentnewbrand.html");
					}else if(appr.getEnterType()==4){
						if(shop!=null) {
							mv.setViewName("redirect:/supplier/torecruitmentcontract.html");
						} else {
							mv.setViewName("redirect:/supplier/tocreatesupplierinfo.html");							
						}
					}else if(appr.getEnterType()==5){
						mv.setViewName("redirect:/user/tosuppliermain.html");
					}
				}
			}
		}
		return mv;
		
	}
	
	
	/** 
	 * 进入新增页面
	 **/
	@RequestMapping(value="tocreatesupplierinfo",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView create(HttpServletRequest request,HttpServletResponse response,Supplier supplier,String mode) throws Exception {
		
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		mv.setViewName("product/supplier/recruitmentinfo");
		mv.addObject("banks",entParamCodeService.getBanks());
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Supplier resupplier = supplierService.getById(us.getSupplierId());
			ApprSupplier appr = apprSupplierService.getSupplierApprIng(us.getSupplierId());

			mv.addObject("mode", mode);
			//商家电话等信息拼装分解
			if(appr!=null){
				if(!StringUtils.isEmpty(appr.getOrgNum()) && !"-".equals(appr.getOrgNum().trim())) {
					String[] ary = appr.getOrgNum().split("-");
					if(ary.length > 0) {
						appr.setOrgNum1(ary[0]);
						if(ary.length > 1)	appr.setOrgNum2(ary[1]);
					} else {
						appr.setOrgNum1("");
						appr.setOrgNum2("");						
					}
				} else {
					appr.setOrgNum1("");
					appr.setOrgNum2("");
				}
				
				if(appr.getComTel()!=null) {
					String[] ary = appr.getComTel().split("-");
					if(ary.length > 0) {
						appr.setComTel1(ary[0]);
						if(ary.length > 1) {
							appr.setComTel2(ary[1]);
							if(ary.length > 2) appr.setComTel3(ary[2]);
						}
					} else {
						appr.setComTel1("");
						appr.setComTel2("");
						appr.setComTel3("");						
					}
				}

				if(appr.getComPortraiture()!=null) {
					String[] ary = appr.getComPortraiture().split("-");
					if(ary.length > 0) {
						appr.setComPortraiture1(ary[0]);
						if(ary.length > 1) {
							appr.setComPortraiture2(ary[1]);
							if(ary.length > 2) appr.setComPortraiture3(ary[2]);
						} 
					} else {
						appr.setComPortraiture1("");
						appr.setComPortraiture2("");
						appr.setComPortraiture3("");						
					}
				}
					
				mv.addObject("supplier",appr);
				
			} else if(resupplier!=null) {
				if(!StringUtils.isEmpty(resupplier.getOrgNum()) && !"-".equals(resupplier.getOrgNum().trim())) {
					String[] ary = resupplier.getOrgNum().split("-");
					if(ary.length > 0) {
						resupplier.setOrgNum1(ary[0]);
						if(ary.length > 1)	resupplier.setOrgNum2(ary[1]);
					} else {
						appr.setOrgNum1("");
						appr.setOrgNum2("");						
					}
				} else {
					resupplier.setOrgNum1("");
					resupplier.setOrgNum2("");
				}
				

				if(resupplier.getComTel()!=null) {
					String[] ary = resupplier.getComTel().split("-");
					if(ary.length > 0) {
						resupplier.setComTel1(ary[0]);
						if(ary.length > 1) {
							resupplier.setComTel2(ary[1]);
							if(ary.length > 2) resupplier.setComTel3(ary[2]);
						}
					} else {
						resupplier.setComTel1("");
						resupplier.setComTel2("");
						resupplier.setComTel3("");						
					}
				}

				if(resupplier.getComPortraiture()!=null) {
					String[] ary = resupplier.getComPortraiture().split("-");
					if(ary.length > 0) {
						resupplier.setComPortraiture1(ary[0]);
						if(ary.length > 1) {
							resupplier.setComPortraiture2(ary[1]);
							if(ary.length > 2) resupplier.setComPortraiture3(ary[2]);
						} 
					} else {
						resupplier.setComPortraiture1("");
						resupplier.setComPortraiture2("");
						resupplier.setComPortraiture3("");						
					}
				}
				
				mv.addObject("supplier", resupplier!=null?resupplier:null);
			} else {
				mv.addObject("supplier",null);
			}
		}
		
		return mv;
	}
	
	/** 
	 * 保存新增对象
	 **/
	@NoCheckLogin
	@RequestMapping(value="savesupplierinfo",method=RequestMethod.POST)
	public Result savesupplierinfo(HttpServletRequest request,HttpServletResponse response,ApprSupplier supplier,String toShop,String doType) throws Exception {
		Result result = new Result();
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		if(us==null){
			result.setErrorCode("1000");
			result.setMessage("用户未登陆");
		}else{
			//判断是否已注册过商家
			ApprSupplier appr = apprSupplierService.getSupplierApprIng(us.getSupplierId());
			Supplier resupplier = supplierService.getById(us.getSupplierId());
			//未注册新增
			if(appr==null){
				Map<String, Object> regMap = validateRegister(supplier, request);
				//参数验证方法
				if("1".equals(doType) || VALIDATE_SUCCESS.equals(regMap.get("regRes"))) {
//					supplier.setUserId(us.getId());
					supplier.setSupplierId(us.getSupplierId());
					supplier.setOrgNum(supplier.getOrgNum1()+"-"+supplier.getOrgNum2());
					supplier.setComTel(supplier.getComTel1()+"-"+supplier.getComTel2()+"-"+supplier.getComTel3());
					supplier.setComPortraiture(supplier.getComPortraiture1()+"-"+supplier.getComPortraiture2()+"-"+supplier.getComPortraiture3());

					supplier.setCreatTime(new Date());
					supplier.setCreatUser(us.getId());
					supplier.setCreatName(StringUtils.isEmpty(us.getRealName())?us.getNickName():us.getRealName());
					supplier.setToEmail(us.getEmail());
					supplier.setPlatformUseFee(0F);
					supplier.setCashDeposit(0F);
					
					if(resupplier != null) {
						supplier.setManagerId(resupplier.getManagerId());
						supplier.setManagerName(resupplier.getManagerName());
					}
					if("1".equals(doType)) {
						supplier.setEnterType(0);
					} else {
						if("1".equals(toShop) || "on".equals(toShop)) {
							supplier.setEnterType(1);
						} else {
							supplier.setEnterType(4);
						}
					}
					supplier.setStatus(0);
					//协议名称 只保存 不能更改
					supplier.setProtocolFile(PROTOCOL_FILE_VESION);
					ApprSupplier instersu = apprSupplierService.save(supplier);
					
					//us.setSupplierId(instersu.getId());
					if(instersu.getId()>0){
						//保存用户企业id,然后将企业id放到session中
						//this.userService.update(us);
						HttpSession session = request.getSession();
						session.setAttribute(UserConstant.USER_SESSION, us);
						result.setErrorCode("0");
						result.setMessage("注册成功");
					}else{
						result.setErrorCode("3000");
						result.setMessage("插入失败");
					}
				}else{
					Result re =  (Result) regMap.get("result");
					result.setErrorCode(re.getErrorCode());
					result.setMessage(re.getMessage());
					result.setKey(re.getKey());
				}
			//已注册执行修改
			}else{
				Map<String, Object> regMap = validateRegister(supplier, request);
				if("1".equals(doType) || VALIDATE_SUCCESS.equals(regMap.get("regRes"))) {
					bind(request,appr);
					appr.setOrgNum(supplier.getOrgNum1()+"-"+supplier.getOrgNum2());
					appr.setComTel(supplier.getComTel1()+"-"+supplier.getComTel2()+"-"+supplier.getComTel3());
					appr.setComPortraiture(supplier.getComPortraiture1()+"-"+supplier.getComPortraiture2()+"-"+supplier.getComPortraiture3());
					if(resupplier != null) {
						appr.setManagerId(resupplier.getManagerId());
						appr.setManagerName(resupplier.getManagerName());

						appr.setPlatformUseFee(resupplier.getPlatformUseFee());
						appr.setCashDeposit(resupplier.getCashDeposit());
					} else {
						appr.setPlatformUseFee(0F);
						appr.setCashDeposit(0.1F);
					}
					if("1".equals(doType)) {
						appr.setEnterType(0);
					} else {
						if("1".equals(toShop) || "on".equals(toShop)) {
							appr.setEnterType(1);
						} else {
							appr.setEnterType(4);
						}
					}
					appr.setStatus(0);
					apprSupplierService.update(appr);	
					result.setErrorCode("0");
					result.setMessage("修改成功");
				}else{
					Result re =  (Result) regMap.get("result");
					result.setErrorCode(re.getErrorCode());
					result.setMessage(re.getMessage());
					result.setKey(re.getKey());
				}
			}
		}

		return result;
	}

	
	/** 
	 * 创建店铺
	 **/
	@RequestMapping(value="torecruitmentstore",method=RequestMethod.GET)
	public ModelAndView torecruitmentstore(HttpServletRequest request,HttpServletResponse response,Long id,String apprType) throws Exception {
		ModelAndView mv =new ModelAndView();
		mv.setViewName("product/supplier/recruitmentstore");
		//获取当前用户session
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		Result result = new Result();
		//判断用户是否登陆
		if(us == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			ApprShop appr= apprShopService.getShopApprIng(us.getSupplierId());

			if(appr != null) {
				apprType= appr.getApprType();
				id = appr.getOldId();
			}
			if(!StringUtils.isEmpty(id) && "1".endsWith(apprType)) {
				mv.setViewName("redirect:/supplier/torecruitmenttype.html?id="+id +"&apprType="+apprType);
				return mv;
			} else if(!StringUtils.isEmpty(id) && "2".endsWith(apprType)) {
				mv.setViewName("redirect:/supplier/torecruitmentnewbrand.html?id="+id +"&apprType="+apprType);
				return mv;
			}
			
			if(appr == null) {
				if(id==null) {
					appr = new ApprShop();
					//获取商家信息
					Supplier supplier = supplierService.getById(us.getSupplierId());
					if(supplier == null) {
						ApprSupplier s = apprSupplierService.getSupplierApprIng(us.getSupplierId());

						appr.setCusTel(s.getComTel());
						appr.setCusContact(s.getCorName());
						appr.setCusEmail(us.getEmail());
						appr.setCusPhone(us.getPhone());
						appr.setSerTel(s.getComTel());
						appr.setSerContact(s.getCorName());
						appr.setSerEmail(us.getEmail());
						appr.setSerPhone(us.getPhone());
						appr.setShopTel(s.getComTel());
						appr.setShopContact(s.getCorName());
						appr.setShopEmail(us.getEmail());
						appr.setShopPhone(us.getPhone());
						
						appr.setShopTel1(appr.getShopTel()!=null?appr.getShopTel().split("-")[0]:"");
						appr.setShopTel2(appr.getShopTel()!=null?appr.getShopTel().split("-")[1]:"");
						appr.setShopTel3(appr.getShopTel()!=null&&appr.getShopTel().split("-").length==3?appr.getShopTel().split("-")[2]:"");
						appr.setCusTel1(appr.getCusTel()!=null?appr.getCusTel().split("-")[0]:"");
						appr.setCusTel2(appr.getCusTel()!=null?appr.getCusTel().split("-")[1]:"");
						appr.setCusTel3(appr.getCusTel()!=null&&appr.getCusTel().split("-").length==3?appr.getCusTel().split("-")[2]:"");
						appr.setSerTel1(appr.getSerTel()!=null?appr.getSerTel().split("-")[0]:"");
						appr.setSerTel2(appr.getSerTel()!=null?appr.getSerTel().split("-")[1]:"");
						appr.setSerTel3(appr.getSerTel()!=null&&appr.getSerTel().split("-").length==3?appr.getSerTel().split("-")[2]:"");
						mv.addObject("shopSetting", appr);
						mv.addObject("hasPre", "1");
						mv.addObject("property", s.getProperty());
					} else {
				        Shop record = new Shop();
				        record.setSupplierId(supplier.getId());
						List<Shop> olds= shopService.selectByModel(record);
						
						if(olds==null || olds.isEmpty()) {
							appr.setCusTel(supplier.getComTel());
							appr.setCusContact(supplier.getCorName());
							appr.setCusEmail(us.getEmail());
							appr.setCusPhone(us.getPhone());
							appr.setSerTel(supplier.getComTel());
							appr.setSerContact(supplier.getCorName());
							appr.setSerEmail(us.getEmail());
							appr.setSerPhone(us.getPhone());
							appr.setShopTel(supplier.getComTel());
							appr.setShopContact(supplier.getCorName());
							appr.setShopEmail(us.getEmail());
							appr.setShopPhone(us.getPhone());
							
							appr.setShopTel1(appr.getShopTel()!=null?appr.getShopTel().split("-")[0]:"");
							appr.setShopTel2(appr.getShopTel()!=null?appr.getShopTel().split("-")[1]:"");
							appr.setShopTel3(appr.getShopTel()!=null&&appr.getShopTel().split("-").length==3?appr.getShopTel().split("-")[2]:"");
							appr.setCusTel1(appr.getCusTel()!=null?appr.getCusTel().split("-")[0]:"");
							appr.setCusTel2(appr.getCusTel()!=null?appr.getCusTel().split("-")[1]:"");
							appr.setCusTel3(appr.getCusTel()!=null&&appr.getCusTel().split("-").length==3?appr.getCusTel().split("-")[2]:"");
							appr.setSerTel1(appr.getSerTel()!=null?appr.getSerTel().split("-")[0]:"");
							appr.setSerTel2(appr.getSerTel()!=null?appr.getSerTel().split("-")[1]:"");
							appr.setSerTel3(appr.getSerTel()!=null&&appr.getSerTel().split("-").length==3?appr.getSerTel().split("-")[2]:"");
							mv.addObject("shopSetting", appr);
						} else {

							Shop old = olds.get(0);
							old.setShopTel1(old.getShopTel()!=null?old.getShopTel().split("-")[0]:"");
							old.setShopTel2(old.getShopTel()!=null?old.getShopTel().split("-")[1]:"");
							old.setShopTel3(old.getShopTel()!=null&&old.getShopTel().split("-").length==3?old.getShopTel().split("-")[2]:"");
							old.setCusTel1(old.getCusTel()!=null?old.getCusTel().split("-")[0]:"");
							old.setCusTel2(old.getCusTel()!=null?old.getCusTel().split("-")[1]:"");
							old.setCusTel3(old.getCusTel()!=null&&old.getCusTel().split("-").length==3?old.getCusTel().split("-")[2]:"");
							old.setSerTel1(old.getSerTel()!=null?old.getSerTel().split("-")[0]:"");
							old.setSerTel2(old.getSerTel()!=null?old.getSerTel().split("-")[1]:"");
							old.setSerTel3(old.getSerTel()!=null&&old.getSerTel().split("-").length==3?old.getSerTel().split("-")[2]:"");

							mv.addObject("shopSetting", old);
						}
						mv.addObject("hasPre", "0");
						mv.addObject("property", supplier.getProperty());
					}
					
				} else {
					Supplier supplier = supplierService.getById(us.getSupplierId());
					Shop old = shopService.getById(id);
					old.setShopTel1(old.getShopTel()!=null?old.getShopTel().split("-")[0]:"");
					old.setShopTel2(old.getShopTel()!=null?old.getShopTel().split("-")[1]:"");
					old.setShopTel3(old.getShopTel()!=null&&old.getShopTel().split("-").length==3?old.getShopTel().split("-")[2]:"");
					old.setCusTel1(old.getCusTel()!=null?old.getCusTel().split("-")[0]:"");
					old.setCusTel2(old.getCusTel()!=null?old.getCusTel().split("-")[1]:"");
					old.setCusTel3(old.getCusTel()!=null&&old.getCusTel().split("-").length==3?old.getCusTel().split("-")[2]:"");
					old.setSerTel1(old.getSerTel()!=null?old.getSerTel().split("-")[0]:"");
					old.setSerTel2(old.getSerTel()!=null?old.getSerTel().split("-")[1]:"");
					old.setSerTel3(old.getSerTel()!=null&&old.getSerTel().split("-").length==3?old.getSerTel().split("-")[2]:"");
					mv.addObject("shopSetting", old);
					mv.addObject("hasPre", "0");
					mv.addObject("property", supplier.getProperty());
				}
				
			} else {
				appr.setShopTel1(appr.getShopTel()!=null?appr.getShopTel().split("-")[0]:"");
				appr.setShopTel2(appr.getShopTel()!=null?appr.getShopTel().split("-")[1]:"");
				appr.setShopTel3(appr.getShopTel()!=null&&appr.getShopTel().split("-").length==3?appr.getShopTel().split("-")[2]:"");
				appr.setCusTel1(appr.getCusTel()!=null?appr.getCusTel().split("-")[0]:"");
				appr.setCusTel2(appr.getCusTel()!=null?appr.getCusTel().split("-")[1]:"");
				appr.setCusTel3(appr.getCusTel()!=null&&appr.getCusTel().split("-").length==3?appr.getCusTel().split("-")[2]:"");
				appr.setSerTel1(appr.getSerTel()!=null?appr.getSerTel().split("-")[0]:"");
				appr.setSerTel2(appr.getSerTel()!=null?appr.getSerTel().split("-")[1]:"");
				appr.setSerTel3(appr.getSerTel()!=null&&appr.getSerTel().split("-").length==3?appr.getSerTel().split("-")[2]:"");
				mv.addObject("shopSetting", appr);
				id = appr.getOldId();
				ApprSupplier s = apprSupplierService.getSupplierApprIng(us.getSupplierId());
				mv.addObject("hasPre", s==null?"0":"1");
				mv.addObject("property", s==null?supplierService.getById(us.getSupplierId()).getProperty():s.getProperty());
			}
			mv.addObject("oldId", id);
		}
		
		return mv;
	}
	
	/** 
	 * 选择类目页面
	 **/
	@RequestMapping(value="torecruitmenttype",method=RequestMethod.GET)
	public ModelAndView torecruitmenttype(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv =new ModelAndView();
		mv.setViewName("product/supplier/recruitmenttype");
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			//获取分类类表
			List<ProductCategory> productCategoryList = productCategoryService.findRoot();
			//获取商家信息
			ApprShop appr= apprShopService.getShopApprIng(us.getSupplierId());
			Long shopId = null;
			Long apprId = null;
			if(appr==null) {
				shopId = Long.parseLong(request.getParameter("id"));
			} else {
				shopId = appr.getShopId();
				apprId = appr.getId();
			}
			//获取已经保存的分类信息
			List<SupplierCategory> supplierCategoryList = supplierCategoryService.getBySupplierId(us.getSupplierId(),shopId);
			List<Long> param = new ArrayList<Long>();
			for (SupplierCategory supplierCategory : supplierCategoryList) {
				param.add(supplierCategory.getCategoryId());
			}
			//获取以保存的分类信息全部数据
			Collection<ProductCategory> supplierCategoryLists = productCategoryService.findParentsByGroup(param);
			mv.addObject("productCategoryList", productCategoryList);
			mv.addObject("supplierCategoryLists", supplierCategoryLists);
			mv.addObject("ss", appr==null?shopService.getById(shopId):appr);
			mv.addObject("shopId", shopId);
			mv.addObject("oid", appr==null?shopId:appr.getOldId());
			mv.addObject("apprId", apprId);
			
			mv.addObject("apprType", request.getParameter("apprType"));
			mv.addObject("scsize", supplierCategoryLists!=null?supplierCategoryLists.size():0);
		}
		
		return mv;
	}
	
	/** 
	 * 上传资质页面
	 **/
	@RequestMapping(value="torecruitmentnewbrand",method=RequestMethod.GET)
	public ModelAndView torecruitmentnewbrand(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("product/supplier/recruitmentnewbrand");
		Map<String,Object> map = new HashMap<String,Object>();
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			//获取商家信息
			ApprShop appr= apprShopService.getShopApprIng(userModel.getSupplierId());
			if(appr==null) {
				appr=this.makeApprShop(userModel, Long.parseLong(request.getParameter("id")));
			}
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			map.put("supplierId",userModel.getSupplierId());
			map.put("shopId", appr.getShopId());
			
			//获取全部资质信息
			List<Attachment> list = attachmentService.findAll(map);
			//获取全部品牌
			map.put("isDelete", 0);
			List<ProductBrand> productBrandlist = productBrandService.findAllBymap(map);
			for (ProductBrand productBrand : productBrandlist) {
				if(productBrand != null )
				{
					ProductBrandImage record = new ProductBrandImage();
					record.setSupplierId(userModel.getSupplierId());
					record.setBrandId(productBrand.getId());
					productBrand.setImageList(productBrandImageService.selectByModel(record));
				}
			}
			
			Attachment attachment1 = new Attachment();
			Attachment attachment2 = new Attachment();
			Attachment attachment3 = new Attachment();
			Attachment attachment4 = new Attachment();
			Attachment attachment5 = new Attachment();
			//Attachment attachment6 = new Attachment();
			List<Attachment> attachment6 = new ArrayList<Attachment>();
			List<Attachment> attachment7 = new ArrayList<Attachment>();
			//Attachment attachment7 = new Attachment();
			Attachment attachment8 = new Attachment();
			if(list!=null&&list.size()>0){
				for(Attachment a:list){
					if(a.getRelatedId()==1){
						attachment1 = a;
					}else if(a.getRelatedId()==2){
						attachment2 = a;
					}else if(a.getRelatedId()==3){
						attachment3 = a;
					}else if(a.getRelatedId()==4){
						attachment4 = a;
					}else if(a.getRelatedId()==5){
						attachment5 = a;
					}else if(a.getRelatedId()==6){
						attachment6.add(a);
					}else if(a.getRelatedId()==7){
						attachment7.add(a);
					}else if(a.getRelatedId()==8){
						attachment8 = a;
					}else{
					}
				}
			}
			mv.addObject("attachment1",attachment1);
			mv.addObject("attachment2",attachment2);
			mv.addObject("attachment3",attachment3);
			mv.addObject("attachment4",attachment4);
			mv.addObject("attachment5",attachment5);
			mv.addObject("attachment6",attachment6);
			mv.addObject("attachment7",attachment7);
			mv.addObject("attachment8",attachment8);

			mv.addObject("shopId",  appr.getShopId());
			mv.addObject("apprType", appr.getApprType());
			mv.addObject("supplierType",supplier==null?(appr==null?("2"):(appr.getType()==2?"1":"2")):supplier.getProperty());
			
			mv.addObject("ss", appr);
			mv.addObject("productBrandlist",productBrandlist);
			mv.addObject("bs",productBrandlist!=null?productBrandlist.size():0);
		}
		return mv;
	}
	@RequestMapping(value="cancelEdit",method=RequestMethod.GET)
	public ModelAndView cancelEdit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			//获取商家信息
			ApprShop appr= apprShopService.getShopApprIng(userModel.getSupplierId());
			//if("2".equals(appr.getApprType()) && 0==appr.getStatus()) {
				clearApprShop(appr);
			//}
			if(appr!=null && appr.getOldId()!=null){
				mv.setViewName("redirect:/shopSetting/categoryBrand.html?id="+appr.getOldId());
			} else {
				mv.setViewName("redirect:/user/tosuppliermain.html");				
			}
			
		}
		
		return mv;
	}
	
	private ApprShop makeApprShop(UserFactory us, Long shopId) {
		ApprShop appr = new ApprShop();
		Shop shop = shopService.getById(shopId);
		Supplier resupplier = supplierService.getById(us.getSupplierId());
		
		appr.setId(dbUtils.CreateID());
		appr.setApprType("2");
		appr.setStatus(0);
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
		appr.setUpdateDesc("修改品牌");

		apprShopService.save(appr);

		//资质copy
		attachmentService.copyByShop(appr.getSupplierId(), appr.getShopId(), appr.getOldId());
		//品牌copy
		productBrandService.copyByShop(appr.getSupplierId(), appr.getShopId(), appr.getOldId());
		//分类copy
		supplierCategoryService.copyByShop(appr.getSupplierId(), appr.getShopId(), appr.getOldId());
			
		//品牌资质copy
		productBrandImageService.copyByShop(appr.getSupplierId(), appr.getShopId());
		brandProducttypesService.copyByShop(appr.getSupplierId(), appr.getShopId());
		
		return appr;
	}

	private void clearApprShop(ApprShop appr) {
		if(appr==null) return;
		apprShopService.removeById(appr.getId());

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("supplierId",appr.getSupplierId());
		map.put("shopId", appr.getShopId());
		//获取全部资质信息
		attachmentService.removeAllBySupplierid(map);;
		supplierCategoryService.removeBySupplierId(appr.getSupplierId(), appr.getShopId());

		//品牌资质copy
		productBrandImageService.deleteByShop(appr.getShopId());

		brandProducttypesService.deleteByShop(appr.getShopId());
		
		productBrandService.deleteByShop(appr.getShopId());
	}
	/** 
	 * 上传资质页面
	 **/
	@RequestMapping(value="createAptitude",method=RequestMethod.POST)
	@NoCheckLogin
	public ModelAndView createAptitude(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		String flag = request.getParameter("flag");
		//获取当前用户session
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		//判断当前用户是否登陆
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			ApprShop appr= apprShopService.getShopApprIng(userModel.getSupplierId());

			//删除全部资质
			Map<String,Object> delMap = new HashMap<String,Object>();
			delMap.put("supplierId", appr.getSupplierId());
			delMap.put("shopId", appr.getShopId());
			attachmentService.removeAllBySupplierid(delMap);
			
			//资质附件
			String[] aptitude_result = request.getParameterValues("aptitude_result");
			if(aptitude_result!=null&&aptitude_result.length>0){
				for(int i=0;i<aptitude_result.length;i++){
					String str = aptitude_result[i];
					if(str!=null&&!str.trim().equals("")){
						String[] strs = str.split("_");
						Attachment att = new Attachment();
						att.setCreateDate(new Date());
						att.setName(strs[0]);
						att.setRelatedId(new Long(strs[1]));
						att.setSupplierId(appr.getSupplierId());
						att.setShopId(appr.getShopId());
						att.setUrl(strs[2]);
						//保存资质
						attachmentService.save(att);
					}
				}
			}

		    if(!StringUtils.isEmpty(appr.getOldId())) {
            	List<ProductBrand> ls = productBrandService.getAddBrands(appr.getSupplierId(), appr.getShopId(), appr.getOldId());
            	String add = ";新增品牌：";
            	for (ProductBrand productBrand : ls) {
            		add += productBrand.getName()+"("+ productBrand.getNameEn() +"),";
				}
            	if(!appr.getUpdateDesc().contains(add)) {
            		appr.setUpdateDesc(appr.getUpdateDesc() + add);
            	}
    			if("2".equals(appr.getApprType())) {
    				appr.setStatus(1);
    			}
        		apprShopService.update(appr);            	
            }
		
			
			//更新入住步骤
			ApprSupplier apprS = apprSupplierService.getSupplierApprIng(userModel.getSupplierId());
			if(apprS!=null && apprS.getEnterType()==3) {
				apprS.setEnterType(4);
				apprSupplierService.update(apprS);
			}

			if("2".equals(appr.getApprType())) {
				mv.setViewName("redirect:/shopSetting/categoryBrand.html?id="+appr.getOldId());
			} else {
				//根据所选flag是否跳转页面
				if("1".equals(flag)){
					mv.setViewName("redirect:/supplier/torecruitmentnewbrand.html");
				}else if("2".equals(flag)){
					mv.setViewName("redirect:/supplier/torecruitmentcontract.html");
				}
			}
		}
		return mv;
	}
	
	/** 
	 * 添加品牌页面
	 **/
	@RequestMapping(value="tocreatebrand",method=RequestMethod.GET)
	public ModelAndView tocreatebrand(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String returnUrl = request.getParameter("returnUrl");
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//获取当前用户session
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		//判断是否登陆
		if(us == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			
			mv.setViewName("product/supplier/createbrand");
			//获取商家信息
			ApprShop appr= apprShopService.getShopApprIng(us.getSupplierId());

			Supplier supplier = supplierService.getById(us.getSupplierId());
			mv.addObject("supplierType",supplier==null?(appr==null?(""):(appr.getType()==2?"1":"2")):supplier.getProperty());
			//获取所保存的类目
			List<SupplierCategory> supplierCategoryList = supplierCategoryService.getBySupplierId(appr.getSupplierId(),appr.getShopId());
			List<Long> param = new ArrayList<Long>();
			for (SupplierCategory supplierCategory : supplierCategoryList) {
				param.add(supplierCategory.getCategoryId());
			}
			//获得所选择类目全部数据
			Collection<ProductCategory> supplierCategoryLists = productCategoryService.findParentsByGroup(param);
			//当前是否是编辑  是就查出来放进前天展示
			ProductBrand productBrand = productBrandService.getById(id!=null?new Long(id):new Long(0));
			Map<String,Object> map = new HashMap<String, Object>();
			if(id!=null){
				map.put("brandId", new Long(id));
				map.put("supplierId", us.getSupplierId());
				map.put("shopId", appr.getShopId());
			}else{
				map.put("brandId", new Long(0));
				map.put("supplierId", us.getSupplierId());
				map.put("shopId", appr.getShopId());
			}
			//获取当前品牌以保存的数据
			List<BrandProducttype> brandProducttypeList = brandProducttypesService.findAllByMap(map);

			mv.addObject("ss", appr);
			mv.addObject("productBrand", productBrand);
			mv.addObject("brandProducttypeList", brandProducttypeList);
			mv.addObject("supplierCategoryLists", supplierCategoryLists);

			if(productBrand != null )
			{
				ProductBrandImage record = new ProductBrandImage();
				record.setSupplierId(us.getSupplierId());
				record.setBrandId(productBrand.getId());

				mv.addObject("brandImages", productBrandImageService.selectByModel(record));
			}
			mv.addObject("returnUrl", returnUrl);
		}
		
		return mv;
	}
	
	/** 
	 * 确认合同页面
	 **/
	@RequestMapping(value="torecruitmentcontract")
	public ModelAndView torecruitmentcontract(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv =new ModelAndView();
		mv.setViewName("product/supplier/recruitmentcontract");
		//获取当前用户session
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		Result result = new Result();
		//判断用户是否登陆
		if(us == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			//分页参数
			String pages = request.getParameter("pages");
			String sizes = request.getParameter("sizes");
			//分页参数初始化
			Integer page=1;
			Integer size=10;
			if(pages==null||pages.equals("")){
				pages = "1";
			}
			page = new Integer(pages);
			if(sizes == null || sizes.equals("")){
				sizes="10";
			}
			size= new Integer(sizes);
			if(size>100){
				size=100;
			}
			Map<String,Object> map = new HashMap<String,Object>();
			

			ApprShop appr= apprShopService.getShopApprIng(us.getSupplierId());
			ApprSupplier apprS = apprSupplierService.getSupplierApprIng(us.getSupplierId());
			//获取总数
			/*			Integer total = commissionSettingService.getBySupplerIdCount(supplier!=null?supplier.getId():new Long(0));
			//Integer total = supplierCategoryService.getBySupplerIdCount(supplier!=null?supplier.getId():new Long(0));
			//分页参数计算
			Integer startnum=(page-1)*size;
			if(total>0){
				if(total<startnum){
					startnum=total-size;
				}
				if(startnum<0){
					startnum = 0;
				}
				map.put("startnum", startnum);
				map.put("size",size);
				map.put("supplerId",supplier!=null?supplier.getId():new Long(0));
				//分页查询合同信息
				//List<CommissionSetting> csList = commissionSettingService.getBySupplerId(map);
				Map newmap = new HashMap();
				newmap.put("supplerId",supplier!=null?supplier.getId():new Long(0));
				List<SupplierCategory> cslist = supplierCategoryService.getlistByMap(newmap);
				mv.addObject("csList", cslist);
				result.setPage(page);
				result.setSize(size);
				result.setTotal(total);
				result.setErrorCode("0");
				
			}else{
				result.setErrorCode("1000");
			}*/
			Map newmap = new HashMap();
			newmap.put("supplierId",appr.getSupplierId());
			newmap.put("shopId",appr.getShopId());
			//TODO
			//newmap.put("shopId", shopId);
			List<SupplierCategory> cslist = supplierCategoryService.getlistByMap(newmap);
			mv.addObject("csList", cslist);
			
			if(apprS != null) {
				mv.addObject("comName", apprS.getComName());
				mv.addObject("cashDeposit", apprS.getCashDeposit());
			} else {;
				//获取商家信息
				Supplier supplier = supplierService.getById(us.getSupplierId());
				mv.addObject("comName", supplier!=null?supplier.getComName():"");
				mv.addObject("cashDeposit", supplier!=null?supplier.getCashDeposit():"");
				
			}
			mv.addObject("ss", appr);
			mv.addObject("mode", "平台模式");
			Date now = new Date();
			Date end = TimeUtil.addDay(now, 365);
			
			mv.addObject("begin", now);
			mv.addObject("end", end);
			/*mv.addObject("pages",page);
			mv.addObject("sizes",size);
			mv.addObject("result",result);*/
		}
		return mv;
	}
	
	
	/** 
	 * 确认合同
	 **/
	@RequestMapping(value="enterend",method=RequestMethod.GET)
	@NoCheckLogin
	public ModelAndView enterend(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv =new ModelAndView();
		mv.setViewName("redirect:/user/tosuppliermain.html");
		//获取当前用户session
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		Result result = new Result();
		//判断用户是否登陆
		if(us == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			ApprShop apprS= apprShopService.getShopApprIng(us.getSupplierId());
			if(apprS !=null) {
				apprS.setStatus(1);
				apprShopService.update(apprS);
			}
			
			//获取商家信息
			ApprSupplier appr = apprSupplierService.getSupplierApprIng(us.getSupplierId());
			//变更商家入驻状态
			if(appr!=null && appr.getEnterType()==4){
				appr.setEnterType(5);
				appr.setStatus(1);
				apprSupplierService.update(appr);
				
				/**
				 * 商家入驻完毕,需要填写注册企业相关信息
				 * */
				Enterprise ent= new Enterprise();
				ent.setName(appr.getComName());
				this.enterpriseService.updateOrInsertEnterprise(ent, appr.getSupplierId());
			}
			
		}
		
		return mv;
	}
	
	/** 
	 * 验证Supplier信息完整度
	 **/
	private Map<String, Object> validateRegister(ApprSupplier supplier,
			HttpServletRequest request) {
		Result result = new Result();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isNullOrEmpty(supplier.getComName())) {
			result.setKey("comName");
			result.setErrorCode("3311");
			result.setMessage("请填公司名称！");
		} else if(StringUtils.isNullOrEmpty(supplier.getComRegisternum())||supplier.getComRegisternum().length()<2) {
			result.setKey("comRegisternum");
			result.setErrorCode("3313");
			result.setMessage("营业执照注册号不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getBusState())) {
			result.setKey("busState");
			result.setErrorCode("3314");
			result.setMessage("营业执照所在地不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getBusCity())) {
			result.setKey("busCity");
			result.setErrorCode("3315");
			result.setMessage("营业执照所在地不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getBusAddress())) {
			result.setKey("busAddress");
			result.setErrorCode("3316");
			result.setMessage("营业执照所在地不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getRegisteredCapital())) {
				result.setKey("registeredCapital");
				result.setErrorCode("3317");
				result.setMessage("公司注册资本不能为空！");
//		} else if(StringUtils.isNullOrEmpty(supplier.getBusScope())) {
//			result.setKey("busScope");
//			result.setErrorCode("3318");
//			result.setMessage("营业执照经营范围不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getBusBegintime())) {
			result.setKey("busBegintime");
			result.setErrorCode("3319");
			result.setMessage("营业执照有效期不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getBusEndtime())) {
			result.setKey("busEndtime");
			result.setErrorCode("3320");
			result.setMessage("营业执照有效期不能为空！");
//		} else if(StringUtils.isNullOrEmpty(supplier.getOrgNum1())||supplier.getOrgNum1().length()!=8) {
//			result.setKey("orgNum1");
//			result.setErrorCode("3321");
//			result.setMessage("组织机构代码证编号不能为空！");
//		} else if(StringUtils.isNullOrEmpty(supplier.getOrgNum2())||supplier.getOrgNum2().length()!=1) {
//			result.setKey("orgNum2");
//			result.setErrorCode("3321");
//			result.setMessage("组织机构代码证编号不能为空！");
//		} else if(StringUtils.isNullOrEmpty(supplier.getOrgBegintime())) {
//			result.setKey("orgBegintime");
//			result.setErrorCode("3323");
//			result.setMessage("组织机构代码证有效期不能为空！");
//		} else if(StringUtils.isNullOrEmpty(supplier.getOrgEndtime())) {
//			result.setKey("orgEndtime");
//			result.setErrorCode("3324");
//			result.setMessage("组织机构代码证有效期不能为空！");
//		} else if(StringUtils.isNullOrEmpty(supplier.getTaxNum())||supplier.getTaxNum().length()!=15) {
//			result.setKey("taxNum");
//			result.setErrorCode("3325");
//			result.setMessage("税务登记证编号不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getIstaxpayer())) {
			result.setKey("istaxpayer");
			result.setErrorCode("3326");
			result.setMessage("是否为一般纳税人不能为空！");
		} /*else if(StringUtils.isNullOrEmpty(supplier.getCorCome())) {
			result.setKey("corCome");
			result.setErrorCode("3327");
			result.setMessage("法人代表归属地不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getCorName())) {
			result.setKey("corName");
			result.setErrorCode("3328");
			result.setMessage("法人代表姓名不能为空！");
		} *//*else if(StringUtils.isNullOrEmpty(supplier.getCorNum())) {
			result.setKey("corNum");
			result.setErrorCode("3329");
			result.setMessage("身份证号不能为空！");
		}*/ else if(StringUtils.isNullOrEmpty(supplier.getBankPeople())) {
			result.setKey("bankPeople");
			result.setErrorCode("3330");
			result.setMessage("银行开户名不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getBankId())) {
			result.setKey("bankId");
			result.setErrorCode("3331");
			result.setMessage("开户行不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getBankState())) {
			result.setKey("bankState");
			result.setErrorCode("3332");
			result.setMessage("开户行所在地不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getBankCity())) {
			result.setKey("bankCity");
			result.setErrorCode("3333");
			result.setMessage("开户行所在地不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getBankName())) {
			result.setKey("bankName");
			result.setErrorCode("3334");
			result.setMessage("开户行支行名称不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getBankNum())) {
			result.setKey("bankNum");
			result.setErrorCode("3335");
			result.setMessage("银行账号不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getComState())) {
			result.setKey("comState");
			result.setErrorCode("3336");
			result.setMessage("公司办公地址不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getComCity())) {
			result.setKey("comCity");
			result.setErrorCode("3337");
			result.setMessage("公司办公地址不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getComAddress())) {
			result.setKey("comAddress");
			result.setErrorCode("3338");
			result.setMessage("公司办公地址不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getComPc())) {
			result.setKey("comPc");
			result.setErrorCode("3339");
			result.setMessage("公司邮编不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getComTel1())||StringUtils.isNullOrEmpty(supplier.getComTel2())) {
			result.setKey("comTel");
			result.setErrorCode("3340");
			result.setMessage("公司固定电话不能为空！");
		} else if(StringUtils.isNullOrEmpty(supplier.getComPortraiture1())||StringUtils.isNullOrEmpty(supplier.getComPortraiture2())) {
			result.setKey("comPortraiture");
			result.setErrorCode("3341");
			result.setMessage("公司传真不能为空！");
//		} else if(StringUtils.isNullOrEmpty(supplier.getShopContact())) {
//			result.setKey("shopContact");
//			result.setErrorCode("3342");
//			result.setMessage("店铺负责人姓名不能为空！");
//		} else if(StringUtils.isNullOrEmpty(supplier.getShopTel1())||StringUtils.isNullOrEmpty(supplier.getShopTel2())) {
//			result.setKey("shopTel");
//			result.setErrorCode("3343");
//			result.setMessage("店铺固定电话不能为空！");
//		} else if(!StringUtils.isPhoneNumber(supplier.getShopPhone())) {
//			result.setKey("shopPhone");
//			result.setErrorCode("3344");
//			result.setMessage("店铺手机不能为空！");
//		} else if(!mailPat.matcher(supplier.getShopEmail()).matches()) {
//			result.setKey("shopEmail");
//			result.setErrorCode("3345");
//			result.setMessage("店铺邮箱不正确！");
//		} else if(StringUtils.isNullOrEmpty(supplier.getCusContact())) {
//			result.setKey("cusContact");
//			result.setErrorCode("3346");
//			result.setMessage("售后负责人姓名不能为空！");
//		} else if(StringUtils.isNullOrEmpty(supplier.getCusTel1())||StringUtils.isNullOrEmpty(supplier.getCusTel2())) {
//			result.setKey("cusTel");
//			result.setErrorCode("3347");
//			result.setMessage("售后固定电话不能为空！");
//		} else if(!StringUtils.isPhoneNumber(supplier.getCusPhone())) {
//			result.setKey("cusPhone");
//			result.setErrorCode("3348");
//			result.setMessage("售后手机不能为空！");
//		} else if(!mailPat.matcher(supplier.getCusEmail()).matches()) {
//			result.setKey("cusEmail");
//			result.setErrorCode("3349");
//			result.setMessage("售后邮箱不正确！");
//		} else if(StringUtils.isNullOrEmpty(supplier.getSerContact())) {
//			result.setKey("serContact");
//			result.setErrorCode("3350");
//			result.setMessage("客服负责人姓名不能为空！");
//		} else if(StringUtils.isNullOrEmpty(supplier.getSerTel1())||StringUtils.isNullOrEmpty(supplier.getSerTel2())) {
//			result.setKey("serTel");
//			result.setErrorCode("3351");
//			result.setMessage("客服固定电话不能为空！");
//		} else if(!StringUtils.isPhoneNumber(supplier.getSerPhone())) {
//			result.setKey("serPhone");
//			result.setErrorCode("3352");
//			result.setMessage("客服手机不能为空！");
//		} else if(!mailPat.matcher(supplier.getSerEmail()).matches()) {
//			result.setKey("serEmail");
//			result.setErrorCode("3353");
//			result.setMessage("客服邮箱不正确！");
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
	
	/**
	 * 验证唯一
	 **/
	@RequestMapping(value="chekout")
	@NoCheckLogin
	public ModelAndView chekout(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Result result = new Result();
		String comName = request.getParameter("comName");
		String comRegisternum = request.getParameter("comRegisternum");
		String taxNum = request.getParameter("taxNum");
		String orgNum1 = request.getParameter("orgNum1");
		String orgNum2 = request.getParameter("orgNum2");
		Map<String, Object> map = new HashMap<String, Object>();
		if(!StringUtils.isNullOrEmpty(comName)){
			map.put("comName", comName);
		}
		if(!StringUtils.isNullOrEmpty(comRegisternum)){
			map.put("comRegisternum", comRegisternum);
		}
		if(!StringUtils.isNullOrEmpty(taxNum)){
			map.put("taxNum", taxNum);
		}
		if(!StringUtils.isNullOrEmpty(orgNum1)&&!StringUtils.isNullOrEmpty(orgNum2)){
			map.put("orgNum", orgNum1+"-"+orgNum2);
		}
		List<Supplier> us = supplierService.getBymap(map);
		if(us.isEmpty()||us.size()==0){
			result.setErrorCode("0");
		}else{
			com.wode.factory.model.UserFactory userFactory = UserInterceptor.getSessionUser(request,shopService);
			if(userFactory.getId().equals(us.get(0).getUserId())){
				result.setErrorCode("0");
			}else{
				result.setErrorCode("1000");
			}
		}
		return new ModelAndView("","result",result);
	}
	/**
	 * 增加企业logo
	 * @param firmLogo
	 * @param supplierId
	 */
	@RequestMapping(value="addfirmLogo")
	public void addfirmLogo(String firmLogo,Long supplierId){
		Supplier supplier = new Supplier();
		supplier.setId(supplierId);
		supplier.setFirmLogo(firmLogo);
		if(supplier!=null){
		supplierService.updateFirmLogo(supplier);
		}
	}
	
}

