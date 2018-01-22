package com.wode.factory.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.db.DBUtils;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierLimitTicket;
import com.wode.factory.model.SupplierLimitTicketSku;
import com.wode.factory.model.SupplierTemp;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.WxOpenService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsImageService;
import com.wode.factory.service.ProductSpecificationsService;
import com.wode.factory.service.SupplierLimitTicketService;
import com.wode.factory.service.SupplierLimitTicketSkuService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.SupplierTempService;
import com.wode.factory.vo.SupplierLimitTicketVo;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysUser;

@Controller
@RequestMapping("supplierLimitTicket")
@SuppressWarnings("unchecked")
public class SupplierLimitTicketController {
	@Autowired
	private DBUtils dbUtils;
	@Resource
	private SysUserMapper sysUserMapper;
	@Autowired
	private SupplierTempService supplierTempService;
	@Autowired
	private SupplierService supplierService;
	@Resource
	private ProductService productService;
	@Resource
	private ProductSpecificationsService productSpecificationsService;
	@Autowired
	private ProductSpecificationsImageService productSpecificationsImageService;
	@Autowired
	private SupplierLimitTicketService supplierLimitTicketService;
	@Autowired
	private SupplierLimitTicketSkuService supplierLimitTicketSkuService;

	@RequestMapping(value="/addQueryLink", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<Object> addQueryLink(Long id,HttpSession session){
		SupplierLimitTicket limitTicket = supplierLimitTicketService.getById(id);
		WxOpenService wxo = ServiceFactory.getWxOpenService(Constant.OUTSIDE_SERVICE_URL);
		String qrUrl = wxo.getQRLink("ticketL"+limitTicket.getId(), WxOpenService.MAX_EXPIRE_SECONDS);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, 29);
		limitTicket.setWxTempQrUrl(qrUrl);
		limitTicket.setWxTempLimitEnd(now.getTime());
		supplierLimitTicketService.update(limitTicket);
		return ActResult.success("");
	}
	@RequestMapping("page{ticketLimitType}")
	public String toInventedPage(Model model,HttpSession session, @PathVariable String ticketLimitType) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("officeId", 0);
		params.put("officeType", "");
		params.put("roles", "-108,-111");
		params.put("pageNum", 1);
		params.put("pageSize", 120);
		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		List<SupplierTemp> tempList = supplierTempService.findAll();
		model.addAttribute("tempList", tempList);
		model.addAttribute("mlist", list);
		model.addAttribute("uid", user.getId());
		
		Integer ticketType =2;
		Integer limitType =2;
		if(!StringUtils.isEmpty(ticketLimitType)) {
			ticketType = NumberUtil.toInteger(ticketLimitType.substring(0, 1));
			limitType = NumberUtil.toInteger(ticketLimitType.substring(1));
		}
		if(ticketLimitType==null) {
			ticketType=2;
		}
		model.addAttribute("ticketType", ticketType);
		model.addAttribute("limitType", limitType+"");
		model.addAttribute("limitTypes", limitTypes(ticketType));
		return "manager/limitTicket/ticket";
	}
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String inventedLst(@RequestParam Map<String, Object> params, Model model, HttpSession session,
			HttpServletRequest request) {
		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}
		PageInfo<SupplierLimitTicketVo> page = supplierLimitTicketService.findInfoPageList(params);
		model.addAttribute("page", page);
		Integer ticketType =NumberUtil.toInteger(params.get("ticketType"));
		Integer limitType =NumberUtil.toInteger(params.get("limitType"));
		model.addAttribute("ticketType", ticketType);
		model.addAttribute("limitType", limitType);
		model.addAttribute("apiUrl", Constant.FACTORY_API_URL);
		return "manager/limitTicket/ticket_list";
	}
	
	@RequestMapping(value = "toSetPage")
	public String toSetPage(Model model, HttpSession session,Integer ticketType,Integer limitType,
			HttpServletRequest request) {
		List<SupplierTemp> tempList = supplierTempService.findAll();
		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("supplier", getSupplierList(query));
		model.addAttribute("tempList", tempList);
		model.addAttribute("ticketType", ticketType);
		model.addAttribute("limitType", limitType+"");
		model.addAttribute("limitTypes", limitTypes(ticketType));
		model.addAttribute("webUrl", Constant.FACTORY_WEB_URL);
		return "manager/limitTicket/ticket_set";
	}
	@RequestMapping(value = "toUpdatePage")
	public String toUpdatePage(Long id,Model model, HttpSession session,
			HttpServletRequest request) {
		Integer type = 0;
		List<SupplierTemp> tempList = supplierTempService.findAll();
		Map<String, Object> query = new HashMap<String, Object>();
		SupplierLimitTicket limitTicket = supplierLimitTicketService.getById(id);

		Integer ticketType =limitTicket.getTicketType();
		Integer limitType =limitTicket.getLimitType();
		List<Supplier> supplierList = getSupplierList(query);
		for (Supplier supplier : supplierList) {
			if(supplier.getId().longValue()==limitTicket.getSupplierId().longValue()) {
				type = 1;
			}
		}
		SupplierLimitTicketSku slts = new SupplierLimitTicketSku();
		slts.setLimitTicketId(limitTicket.getId());
		List<SupplierLimitTicketSku> limitTicketSku = supplierLimitTicketSkuService.selectByModel(slts);
		model.addAttribute("supplier", getSupplierList(query));
		model.addAttribute("tempList", tempList);
		model.addAttribute("type", type);
		model.addAttribute("limitTicket", limitTicket);
		model.addAttribute("limitTicketSku", limitTicketSku);
		model.addAttribute("ticketType", ticketType);
		model.addAttribute("limitType", limitType+"");
		model.addAttribute("limitTypes", limitTypes(ticketType));
		model.addAttribute("webUrl", Constant.FACTORY_WEB_URL);
		return "manager/limitTicket/ticket_update";
	}
	@RequestMapping("addAutoBuySku")
	@NoCheckLogin
	public @ResponseBody ActResult<JSONObject> addAutoBuySku(Long skuId, Integer skuNum) {
		if (skuNum == null || skuNum < 1)
			skuNum = 1;

		ProductSpecifications sku = productSpecificationsService.getById(skuId);
		if (sku == null) {
			return ActResult.fail("skuId 不存在请重新输入");
		}
		Product p = productService.getById(sku.getProductId());
		if (p == null) {
			return ActResult.fail("商品不存在请重新输入");
		}
		List<ProductSpecificationsImage> imgs = productSpecificationsImageService
				.findlistByProductSpecificationsid(skuId);
		Map map = new HashMap();
		map.put("skuId", skuId);
		map.put("skuNum", skuNum);
		map.put("productName", p.getFullName());
		map.put("productId", p.getId());
		map.put("itemValues", sku.getItemValues());
		if (!StringUtils.isEmpty(imgs)) {
			map.put("imgs", imgs.get(0).getSource());
		}
		map.put("internalPurchasePrice", sku.getInternalPurchasePrice());
		map.put("maxFucoin", sku.getMaxFucoin());
		JSONObject rtn = new JSONObject();
		rtn.put("data", map);
		return ActResult.success(rtn);
	}
	
	@RequestMapping(value="/addLimitTicket", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<Object> addexProduct(Long supplierId,String supplierName,Integer oneceFlag,Integer ticketType,Integer limitType,
			String registeflg,String registenormalprzie,String registeautoplus,String skuId,BigDecimal ticket,BigDecimal cash,Integer ticketNum,
			String startDate,String endDate,String nextAction,String ticketNote,Integer skuNum, HttpSession session) throws ParseException {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		if (skuNum == null || skuNum < 1) {
			skuNum = 1;
		}
		SysUser user = (SysUser)obj;
		if(supplierId==null) {
			return ActResult.fail("系统错误");
		}
		SupplierLimitTicket slt = new SupplierLimitTicket();
		slt.setId(dbUtils.CreateID());
		slt.setSupplierId(supplierId);
		if(supplierId==-1) {
			slt.setCompanyName("我的福利 联合内购");
		}else {
			slt.setCompanyName(supplierName);
		}
		slt.setOneceFlag(oneceFlag);
		slt.setTicketType(ticketType);
		slt.setLimitType(limitType);
		String[] skus = skuId.split(",");
		String limitKet = "";
		for (String string : skus) {
			limitKet+="skuId_"+string+",";
		}
		if(ticketType==4 && limitType==4) {
			// 专享商品
			limitKet="4";
		}
		slt.setLimitKey(limitKet);
		slt.setRegisteFlg(registeflg);
		slt.setRegisteNormalPrzie(registenormalprzie);
		slt.setRegisteAutoPlus(registeautoplus);
		slt.setTicket(ticket);
		slt.setCash(cash);
		slt.setTicketNum(ticketNum);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		slt.setLimitStart(sdf.parse(startDate));
		slt.setLimitEnd(sdf.parse(endDate));
		slt.setNextAction(nextAction);
		slt.setTicketNote(ticketNote);
		slt.setStatus(1);
		slt.setCreateDate(new Date());
		slt.setCreateUser(user.getId());
		slt.setCreateUserName(user.getName());
		slt.setReceiveNum(0);
		supplierLimitTicketService.save(slt);
		if(!"".equals(skuId)) {
			String[] split = skuId.split(",");
			for (String id : split) {
				ProductSpecifications sku = productSpecificationsService.getById(Long.valueOf(id));
				if (sku == null) {
					return ActResult.fail("skuId 不存在请重新输入");
				}
				Product p = productService.getById(sku.getProductId());
				if (p == null) {
					return ActResult.fail("商品不存在请重新输入");
				}
				List<ProductSpecificationsImage> imgs = productSpecificationsImageService
						.findlistByProductSpecificationsid(Long.valueOf(id));
				SupplierLimitTicketSku slts = new SupplierLimitTicketSku();
				slts.setId(dbUtils.CreateID());
				slts.setProductId(sku.getProductId());
				slts.setProductName(p.getFullName());
				slts.setLimitTicketId(slt.getId());
				slts.setSkuId(Long.valueOf(id));
				slts.setSkuNum(skuNum);
				slts.setItemValues(sku.getItemValues());
				slts.setPrice(sku.getPrice());
				slts.setSalePrice(sku.getInternalPurchasePrice());
				slts.setTicket(sku.getMaxFucoin());
				slts.setImage(imgs.get(0).getSource());
				supplierLimitTicketSkuService.save(slts);
			}
		}
		return ActResult.success(null);
	}
	
	@RequestMapping(value="/updateLimitTicket", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<Object> updateLimitTicket(Long ticketid,Long supplierId,String supplierName,Integer oneceFlag,Integer ticketType,Integer limitType,
			String registeflg,String registenormalprzie,String registeautoplus,String skuId,BigDecimal ticket,BigDecimal cash,Integer ticketNum,
			String startDate,String endDate,String nextAction,String ticketNote,Integer skuNum, HttpSession session) throws ParseException {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		if (skuNum == null || skuNum < 1) {
			skuNum = 1;
		}
		SysUser user = (SysUser)obj;
		if(supplierId==null) {
			return ActResult.fail("系统错误");
		}
		SupplierLimitTicket slt = supplierLimitTicketService.getById(ticketid);
		slt.setId(ticketid);
		slt.setSupplierId(supplierId);
		if(supplierId==-1) {
			slt.setCompanyName("我的福利 联合内购");
		}else {
			slt.setCompanyName(supplierName);
		}
		slt.setOneceFlag(oneceFlag);
		slt.setTicketType(ticketType);
		slt.setLimitType(limitType);
		String[] skus = skuId.split(",");
		String limitKet = "";
		for (String string : skus) {
			limitKet+="skuId_"+string+",";
		}
		if(ticketType==4 && limitType==4) {
			// 专享商品
			limitKet="4";
		}
		slt.setLimitKey(limitKet);
		slt.setRegisteFlg(registeflg);
		slt.setRegisteNormalPrzie(registenormalprzie);
		slt.setRegisteAutoPlus(registeautoplus);
		slt.setTicket(ticket);
		slt.setCash(cash);
		slt.setTicketNum(ticketNum);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		slt.setLimitStart(sdf.parse(startDate));
		slt.setLimitEnd(sdf.parse(endDate));
		slt.setNextAction(nextAction);
		slt.setTicketNote(ticketNote);
		slt.setUpdateDate(new Date());
		slt.setUpdateUser(user.getId());
		supplierLimitTicketService.update(slt);
		SupplierLimitTicketSku sl = new SupplierLimitTicketSku();
		sl.setLimitTicketId(slt.getId());
		List<SupplierLimitTicketSku> ticketSku = supplierLimitTicketSkuService.selectByModel(sl);
		for (SupplierLimitTicketSku supplierLimitTicketSku : ticketSku) {
			supplierLimitTicketSkuService.removeById(supplierLimitTicketSku.getId());
		}
		if(!"".equals(skuId)) {
			String[] split = skuId.split(",");
			for (String id : split) {
				ProductSpecifications sku = productSpecificationsService.getById(Long.valueOf(id));
				if (sku == null) {
					return ActResult.fail("skuId 不存在请重新输入");
				}
				Product p = productService.getById(sku.getProductId());
				if (p == null) {
					return ActResult.fail("商品不存在请重新输入");
				}
				List<ProductSpecificationsImage> imgs = productSpecificationsImageService
						.findlistByProductSpecificationsid(Long.valueOf(id));
				SupplierLimitTicketSku slts = new SupplierLimitTicketSku();
				slts.setId(dbUtils.CreateID());
				slts.setProductId(sku.getProductId());
				slts.setProductName(p.getFullName());
				slts.setLimitTicketId(slt.getId());
				slts.setSkuId(Long.valueOf(id));
				slts.setSkuNum(skuNum);
				slts.setItemValues(sku.getItemValues());
				slts.setPrice(sku.getPrice());
				slts.setSalePrice(sku.getInternalPurchasePrice());
				slts.setTicket(sku.getMaxFucoin());
				slts.setImage(imgs.get(0).getSource());
				supplierLimitTicketSkuService.save(slts);
			}
		}
		return ActResult.success(null);
	}
	private List<Supplier> getSupplierList(Map<String, Object> query) {
		query.put("status", 2);
		query.put("pageNum", 1);
		query.put("pageSize", 5000);

		return supplierService.getPage(query).getList();
	}
	
	private List<JSONObject> limitTypes(Integer ticketType) {
		List<JSONObject> rnt = new ArrayList<JSONObject>();

		//1:通用无限制
		JSONObject jo1 = new JSONObject();
		jo1.put("val", "1");
		jo1.put("txt", "通用无限制");

		//2:商品（单品/活动页）
		JSONObject jo2 = new JSONObject();
		jo2.put("val", "2");
		jo2.put("txt", "商品（单品/活动页）");

		//3:品类
		JSONObject jo3 = new JSONObject();
		jo3.put("val", "3");
		jo3.put("txt", "品类");

		//4:专享
		JSONObject jo4 = new JSONObject();
		jo4.put("val", "4");
		jo4.put("txt", "专享");
		
		//5:试用频道
		JSONObject jo5 = new JSONObject();
		jo5.put("val", "5");
		jo5.put("txt", "试用频道");

		//6:品牌 
		JSONObject jo6 = new JSONObject();
		jo6.put("val", "6");
		jo6.put("txt", "品牌");
		
		//7:商家
		JSONObject jo7 = new JSONObject();
		jo7.put("val", "7");
		jo7.put("txt", "商家");
		
		if(ticketType==1) {
			//1:内购抵扣券
			
			//1:通用无限制
			rnt.add(jo1);
			
			//2:商品（单品/活动页）
			rnt.add(jo2);
			
			//3:品类
			rnt.add(jo3);
			
			//6:品牌 
			rnt.add(jo6);
			
			//7:商家
			rnt.add(jo7);
			
		} else if (ticketType==2) {
			//2：体验券（免品）
			
			//2:商品（单品/活动页）
			rnt.add(jo2);
			
			//5:试用频道
			rnt.add(jo5);
			
		} else if (ticketType==3) {
			//3:通用现金券

			//1:通用无限制
			rnt.add(jo1);
			
		} else if (ticketType==4) {
			//4:专用现金券
			
			//2:商品（单品/活动页）
			rnt.add(jo2);
			
			//3:品类
			rnt.add(jo3);

			//4:专享
			rnt.add(jo4);
			
			//6:品牌 
			rnt.add(jo6);
			
			//7:商家
			rnt.add(jo7);
		}
		
		return rnt;
	}
}
