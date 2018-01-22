package com.wode.factory.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.DateUtils;
import com.wode.common.util.FileUtils;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.mapper.PaymentDao;
import com.wode.factory.mapper.RefundorderDao;
import com.wode.factory.mapper.ReturnorderDao;
import com.wode.factory.model.ExchangeOrders;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.ExchangeSuborderitem;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.model.InvoiceApply;
import com.wode.factory.model.IssuedInvoice;
import com.wode.factory.model.Payment;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.Returnorder;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.model.ShippingTemplate;
import com.wode.factory.model.ShippingTemplateRule;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.model.SupplierTemp;
import com.wode.factory.model.UserExchangeFavorites;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserShare;
import com.wode.factory.model.UserShareTicket;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.WxOpenService;
import com.wode.factory.service.EmpBenefitFlowService;
import com.wode.factory.service.ExchangeSuborderService;
import com.wode.factory.service.ExpressComService;
import com.wode.factory.service.InvoiceApplyService;
import com.wode.factory.service.IssuedInvoiceService;
import com.wode.factory.service.PaymentService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ShippingFreeRuleService;
import com.wode.factory.service.ShippingTemplateRuleService;
import com.wode.factory.service.ShippingTemplateService;
import com.wode.factory.service.SubOrderService;
import com.wode.factory.service.SupplierExchangeProductService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.SupplierTempService;
import com.wode.factory.service.UserExchangeTicketService;
import com.wode.factory.service.UserFactoryService;
import com.wode.factory.service.UserShareService;
import com.wode.factory.service.UserShareTicketService;
import com.wode.factory.vo.ExchangeSuborderVo;
import com.wode.factory.vo.ProductVO;
import com.wode.factory.vo.SupplierExchangeProductVo;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysRole;
import com.wode.sys.model.SysUser;
import com.wode.sys.service.SysRoleService;

@Controller
@RequestMapping("exchange")
@SuppressWarnings("unchecked")
public class ExchangeController {
	@Autowired
	private DBUtils dbUtils;
	@Resource
	private PaymentService paymentService;

	@Value("#{configProperties['tongji.download']}")
	private String downloadFilePatch;

	@Resource
	private ProductService productService;
	@Autowired
	private SupplierService supplierService;
	@Resource
	private SysUserMapper sysUserMapper;
	@Autowired
	private ShippingTemplateService shippingTemplateService;
	@Autowired
	private ShippingTemplateRuleService shippingTemplateRuleService;
	@Autowired
	private ShippingFreeRuleService shippingFreeRuleService;

	@Autowired
	private ExchangeSuborderService exchangeSuborderService;
	@Autowired
	private UserFactoryService userFactoryService;
	@Autowired
	private RedisUtil redisUtil;
	@Resource
	private SysRoleService sysRoleService;
	@Autowired
	EmpBenefitFlowService empBenefitFlowService;
	@Resource
	PaymentDao paymentDao;

	@Autowired
	private ReturnorderDao returnOrderDao;
	@Autowired
	private RefundorderDao refundOrderDao;
	@Autowired
	private ExpressComService expressComService;
	@Autowired
	private InvoiceApplyService invoiceApplyService;
	@Autowired
	private IssuedInvoiceService issuedInvoiceService;
	@Autowired
	private SubOrderService subOrderService;
	@Autowired
	private SupplierTempService supplierTempService;
	@Autowired
	private SupplierExchangeProductService supplierExchangeProductService;
	@Autowired
	private UserShareTicketService userShareTicketService;
	@Autowired
	private UserShareService userShareService;
	@Autowired
	private UserExchangeTicketService userExchangeTicketService;
	@RequestMapping("orders")
	public String toOrder(Model model) {
		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("supplierList", getSupplierList(query));
		return "manager/exchange/order-base";
	}
	/**
	 * 跳转换领商品销售页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("toExchangeProductSellPage")
	public String toExchangeProductSellPage(Model model,HttpSession session) {
		model.addAttribute("viewStatus", "view");
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
		model.addAttribute("mlist", list);
		model.addAttribute("uid", user.getId());
		return "manager/exchange/exchange_product_sell";
	}
	
	/**
	 * 获取换领商品销售列表
	 * @param params
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "exchangeProductSellList", method = RequestMethod.POST)
	public String exchangeProductSellLst(@RequestParam Map<String, Object> params, Model model, HttpSession session,
			HttpServletRequest request) {
		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}
		PageInfo<SupplierExchangeProductVo> page = supplierExchangeProductService.findInfoPageListEx(params);
		model.addAttribute("page", page);
		return "manager/exchange/exchange_product_sell_list";
	}
	
	
	/**
	 * 跳转虚拟换领币页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("toInventedPage")
	public String toInventedPage(Model model,HttpSession session) {
		model.addAttribute("viewStatus", "view");
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
		return "manager/exchange/exchange_m";
	}
	/**
	 * 获取列表
	 * @param params
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "inventedList", method = RequestMethod.POST)
	public String inventedLst(@RequestParam Map<String, Object> params, Model model, HttpSession session,
			HttpServletRequest request) {
		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}
		PageInfo<SupplierExchangeProductVo> page = supplierExchangeProductService.findInfoPageList(params);
		List<SupplierExchangeProductVo> list = page.getList();
		for (SupplierExchangeProductVo supplierExchangeProductVo : list) {
			Map prm = new HashMap();
			prm.put("ticketId", supplierExchangeProductVo.getId());
			prm.put("supplierId", supplierExchangeProductVo.getSupplierId());
			UserShareTicket ust = userShareTicketService.findByTicketIdBySuppliderId(prm);
			if(ust!=null) {
				supplierExchangeProductVo.setQueryLink(ust.getWxTempQrUrl());
				supplierExchangeProductVo.setUserShareTicketId(ust.getId());
				UserShare userShare = userShareService.getById(ust.getShareId());
				if(userShare!=null) {
					supplierExchangeProductVo.setUserNick(userShare.getUserNick());
				}
			}
		}
		model.addAttribute("page", page);
		return "manager/exchange/exchange_m_list";
	}
	/**
	 * 跳转领取换领币记录
	 * @param model
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping("toRecordPage")
	public String toRecordPage(Model model,HttpSession session,Long pid) {
		model.addAttribute("pid", pid);
		return "manager/exchange/exchange_record";
	}
	/**
	 * 获取领取换领币记录列表
	 * @param params
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "recordList", method = RequestMethod.POST)
	public String recordList(@RequestParam Map<String, Object> params,Model model,HttpSession session) {
		PageInfo<UserExchangeTicket> page = userExchangeTicketService.findPageInfo(params);
		List<UserExchangeTicket> list = page.getList();
		for (UserExchangeTicket userExchangeTicket : list) {
			UserFactory userFactory = userFactoryService.getById(userExchangeTicket.getUserId());
			String phone = "";
			if(userFactory!=null) {
				if(!StringUtils.isEmpty(userFactory.getPhone())) {
					phone = "****"+userFactory.getPhone().substring(userFactory.getPhone().length()-4);
				}
			}
			userExchangeTicket.setPhone(phone);
		}
		model.addAttribute("page", page);
		return "manager/exchange/exchange_record_list";
	}
	/**
	 * 生成领取链接
	 * @param id
	 * @param supplierId
	 * @param session
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/addQueryLink", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<Object> addQueryLink(Long id,Long supplierId,HttpSession session){
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		UserShare userShare = null;
		userShare = userShareService.getByUserId(supplierId);
		UserShareTicket ust = new UserShareTicket();
		if(userShare==null) {
			Long userShareId = supplierService.boundQRcode(supplierId);
			userShare = userShareService.getById(userShareId);
		}
		if(userShare!=null) {
			Map prm = new HashMap();
			prm.put("ticketId", id);
			prm.put("supplierId", supplierId);
			UserShareTicket userSt = userShareTicketService.findByTicketIdBySuppliderId(prm);
			if(userSt==null) {
				ust.setId(dbUtils.CreateID());
				ust.setShareId(userShare.getId());
				ust.setSupplierId(supplierId);
				ust.setShareType(userShare.getShareType());
				ust.setTicketId(id);
				ust.setSaleKbn(3);
				WxOpenService wxo = ServiceFactory.getWxOpenService(Constant.OUTSIDE_SERVICE_URL);
				String qrUrl = wxo.getQRLink("ticketE"+ust.getId(), WxOpenService.MAX_EXPIRE_SECONDS);
				Calendar now = Calendar.getInstance();
				now.add(Calendar.DAY_OF_MONTH, 29);
				ust.setWxTempQrUrl(qrUrl);
				ust.setWxTempLimitEnd(now.getTime());
				ust.setCreateUserName(user.getName());
				ust.setCreateTime(new Date());
				userShareTicketService.save(ust);
			}
		}else {
			ActResult.fail("系统错误");
		}
		return ActResult.success("");
	}
	/**
	 * 作废领取链接
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delQueryLink", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<String> delQueryLink(Long id){
		if(id==null) {
			ActResult.fail("系统错误");
		}
		userShareTicketService.removeById(id);
		return ActResult.success("");
	}
	/**
	 * 添加页面
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "toSetPage")
	public String toSetPage(Model model, HttpSession session,
			HttpServletRequest request) {
		List<SupplierTemp> tempList = supplierTempService.findAll();
		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("supplier", getSupplierList(query));
		model.addAttribute("tempList", tempList);
		return "manager/exchange/exchange_m_set";
	}
	/**
	 * 修改
	 * @param model
	 * @param id
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "toupdatePage")
	public String toupdatePage(Model model,Long id, HttpSession session,
			HttpServletRequest request) {
		Integer type = 0;
		List<SupplierTemp> tempList = supplierTempService.findAll();
		Map<String, Object> query = new HashMap<String, Object>();
		SupplierExchangeProduct sep = supplierExchangeProductService.getById(id);
		List<Supplier> supplierList = getSupplierList(query);
		for (Supplier supplier : supplierList) {
			if(supplier.getId().longValue()==sep.getSupplierId().longValue()) {
				type = 1;
			}
		}
		model.addAttribute("supplier", getSupplierList(query));
		model.addAttribute("tempList", tempList);
		model.addAttribute("sep", sep);
		model.addAttribute("type", type);
		return "manager/exchange/exchange_m_update";
	}
	/**
	 * 增加虚拟换领商品
	 * @param supplierId
	 * @param huanl
	 * @param productName
	 * @param firmLogo
	 * @param pirc
	 * @param startDate
	 * @param endDate
	 * @param aomcount
	 * @param session
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="/addexProduct", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<Object> addexProduct(Long supplierId,String huanl,String productName,String firmLogo,BigDecimal pirc,
			String startDate,String endDate,Integer aomcount, HttpSession session) throws ParseException {
		if(supplierId==null) {
			return ActResult.fail("系统错误");
		}
		SupplierExchangeProduct sep = new SupplierExchangeProduct();
		sep.setId(dbUtils.CreateID());
		sep.setSaleKbn(3);
		sep.setSaleNote(huanl);
		sep.setSupplierId(supplierId);
		sep.setProductId((long) -1);
		sep.setProductName(productName);
		sep.setEmpLevel(-1);
		sep.setEmpCnt(0);
		sep.setProductImg(firmLogo);
		sep.setProductPrice(pirc);
		sep.setEmpAvgAmount(pirc);
		sep.setProductCnt(aomcount);
		sep.setStatus(2);
		sep.setSellCnt(0);
		sep.setDistributeCnt(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sep.setLimitType(1);
		sep.setLimitStart(sdf.parse(startDate));
		sep.setLimitEnd(sdf.parse(endDate));
		sep.setCreateDate(new Date());
		supplierExchangeProductService.save(sep);
		return ActResult.success(null);
	}
	/**
	 * 修改虚拟换领商品
	 * @param id
	 * @param supplierId
	 * @param huanl
	 * @param productName
	 * @param firmLogo
	 * @param pirc
	 * @param startDate
	 * @param endDate
	 * @param aomcount
	 * @param session
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="/updateexProduct", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<Object> updateexProduct(Long id,Long supplierId,String huanl,String productName,String firmLogo,BigDecimal pirc,
			Integer aomcount, HttpSession session) throws ParseException {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		if(id==null) {
			return ActResult.fail("系统错误");
		}
		SupplierExchangeProduct sep = supplierExchangeProductService.getById(id);
		sep.setId(id);
		sep.setSaleNote(huanl);
		sep.setSupplierId(supplierId);
		sep.setProductName(productName);
		sep.setProductImg(firmLogo);
		sep.setProductPrice(pirc);
		sep.setEmpAvgAmount(pirc);
		sep.setProductCnt(aomcount);
		sep.setUpdateUser(user.getId());
		sep.setUpdateDate(new Date());
		supplierExchangeProductService.update(sep);
		return ActResult.success(null);
	}
	
	@RequestMapping(value = "tograceperiod")
	public String tograceperiod(Model model,Long id, HttpSession session,
			HttpServletRequest request) {
		model.addAttribute("id", id);
		return "manager/exchange/graceperiod";
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "exchangDelay", method = RequestMethod.POST)
	@ResponseBody
	public ActResult exchangDelay(HttpServletRequest request, HttpSession session,Long selId,Integer delay)
			throws Exception {
		
		// 在session中获取userModel
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser userModel = (SysUser)obj;
		if (userModel == null) {
			return ActResult.fail("请重新登录后，再试");
		} else {
			SupplierExchangeProduct ex =supplierExchangeProductService.getById(selId);

			Calendar c = Calendar.getInstance();
			c.setTime(ex.getLimitEnd());
			if(delay==5) {
				c.add(Calendar.DAY_OF_MONTH, 15);
			} else {
				c.add(Calendar.MONTH, delay);
			}
			ex.setLimitEnd(c.getTime());
			ex.setUpdateDate(new Date());
			ex.setUpdateUser(userModel.getId());
			supplierExchangeProductService.update(ex);
			
			UserExchangeTicket entity = new UserExchangeTicket();
			entity.setExchangeProductId(ex.getId());
			entity.setLimitEnd(ex.getLimitEnd());
			entity.setUpdateDate(new Date());
			entity.setUpdateUser(userModel.getId());
			userExchangeTicketService.updateEnds(entity);
			
			return ActResult.success(null);
		}
	}
	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "orderList", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params, Model model, HttpSession session,
			HttpServletRequest request) {
		PageInfo<ExchangeSuborder> page = exchangeSuborderService.getPageList(params);
		Map<Long, Supplier> map = new HashMap<Long, Supplier>();
		Map<Long, UserFactory> mapUser = new HashMap<Long, UserFactory>();
		for (ExchangeSuborder sub : page.getList()) {
			Supplier s = this.getSupplier(map, sub.getSupplierId());
			if (s != null) {
				sub.setSupplierName(s.getComName());
				sub.setManagerName(s.getManagerName());
			}

			UserFactory uf = this.getUser(mapUser, sub.getUserId());
			if (uf != null && uf.getSupplierId() != null) {
				Supplier e = this.getSupplier(map, uf.getSupplierId());
				if (e != null) {
					sub.setEnterpriseName(e.getComName());
				}
			}
		}
		model.addAttribute("page", page);
		return "manager/exchange/order-list";
	}

	/**
	 * 弹窗 到款确认
	 * 
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "toClose", method = RequestMethod.POST)
	public String toClose(Model model, String id, String todo) {
		ExchangeSuborder order = exchangeSuborderService.getById(id);
		model.addAttribute("order", order);
		if ("submit".equals(todo)) {
			return "manager/exchange/submitBox";
		} else {
			return "manager/exchange/closeBox";
		}
	}

	/**
	 * 
	 * 功能说明： 查询订单详细 日期: 2015年7月24日
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("detail")
	public String detail(Model model, String id, HttpSession session) {
		ExchangeSuborder vo = exchangeSuborderService.getById(id);

		ExchangeOrders order = exchangeSuborderService.getOrderById(vo.getOrderId());
		String phone = order.getMobile();
		if (!StringUtils.isEmpty(phone) && !"空".equals(phone)) {
			phone = phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length());
			order.setMobile(phone);
		}
		String m = order.getAddress();
		if (m != null && m.contains("(电话")) {
			m = m.substring(0, m.lastIndexOf("(电话"));
			order.setAddress(m);
		}
		if (vo.getThirdPay() == null) {
			vo.setThirdPay(BigDecimal.ZERO);
		}
		model.addAttribute("vo", vo);
		model.addAttribute("order", order);
		if (!StringUtils.isEmpty(vo.getNoto())) {// subOrder表中的noto不为空
			model.addAttribute("note", vo.getNoto());
		} else {
			model.addAttribute("note", order.getNote());
		}
		if (vo.getReturnOrderId() != null) {
			Returnorder returnorder = returnOrderDao.getById(vo.getReturnOrderId());
			if (returnorder != null) {
				model.addAttribute("returnSeason", returnorder.getReason());
			}
		} else {
			if (vo.getRefundOrderId() != null) {
				Refundorder refundorder = refundOrderDao.getById(vo.getRefundOrderId());
				if (refundorder != null) {
					model.addAttribute("returnSeason", refundorder.getReason());
				}
			}
		}

		Supplier s = supplierService.findByEmpId(order.getUserId());
		model.addAttribute("enterPriseName", s == null ? "" : s.getComName());

		model.addAttribute("items", exchangeSuborderService.selectItems(id));
		model.addAttribute("favorites", exchangeSuborderService.selectFavorites(vo.getUserId()));

		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser) obj;
		List<SysRole> roles = sysRoleService.findUserRoleListByUserId(user.getId());
		model.addAttribute("roles", roles);
		model.addAttribute("webUrl", Constant.FACTORY_WEB_URL);
		return "manager/exchange/detail";
	}

	/**
	 * 
	 * 功能说明： 查询订单详细 日期: 2015年7月24日
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("toMergeOrder")
	public String toMergeOrder(Model model, String ids, Long userId, HttpSession session) {
		String[] aryId = ids.split(",");
		List<ExchangeOrders> orders = new ArrayList<ExchangeOrders>();
		List<ExchangeSuborderitem> items = new ArrayList<ExchangeSuborderitem>();
		BigDecimal totalProduct = BigDecimal.ZERO;
		BigDecimal totalShipping = BigDecimal.ZERO;
		BigDecimal totalAdjustment = BigDecimal.ZERO;
		BigDecimal thirdPay = BigDecimal.ZERO;
		int cnt = 0;
		for (int i = 0; i < aryId.length; i++) {
			if (StringUtils.isEmpty(aryId[i]))
				continue;
			ExchangeSuborder vo = exchangeSuborderService.getById(aryId[i]);
			ExchangeOrders order = exchangeSuborderService.getOrderById(vo.getOrderId());
			order.setNote(vo.getNoto());
			orders.add(order);
			cnt++;

			totalProduct = totalProduct.add(vo.getTotalProduct());
			totalShipping = totalShipping.add(vo.getTotalShipping());
			totalAdjustment = totalAdjustment.add(vo.getTotalAdjustment());
			thirdPay = thirdPay.add(vo.getThirdPay() == null ? BigDecimal.ZERO : vo.getThirdPay());

			items.addAll(exchangeSuborderService.selectItems(aryId[i]));
		}

		for (ExchangeOrders order : orders) {
			String phone = order.getMobile();
			if (!StringUtils.isEmpty(phone) && !"空".equals(phone)) {
				phone = phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length());
				order.setMobile(phone);
			}
			String m = order.getAddress();
			if (m.contains("(电话")) {
				m = m.substring(0, m.lastIndexOf("(电话"));
				order.setAddress(m);
			}
		}
		Supplier s = supplierService.findByEmpId(userId);
		model.addAttribute("enterPriseName", s == null ? "" : s.getComName());
		model.addAttribute("orders", orders);

		model.addAttribute("totalProduct", totalProduct.subtract(totalShipping));
		model.addAttribute("totalShipping", totalShipping);
		model.addAttribute("totalAdjustment", totalAdjustment);
		model.addAttribute("thirdPay", thirdPay);
		model.addAttribute("cnt", cnt);

		model.addAttribute("items", items);

		List<UserExchangeFavorites> favorites = exchangeSuborderService.selectFavorites(userId);
		Map<Long, ShippingTemplate> map = new HashMap<Long, ShippingTemplate>();
		for (int i = favorites.size() - 1; i > -1; i--) {
			UserExchangeFavorites userExchangeFavorites = favorites.get(i);
			String pros = redisUtil.getMapData(RedisConstant.PRODUCT_PRE + userExchangeFavorites.getProductId(),
					RedisConstant.PRODUCT_REDIS_INFO);
			String skus = redisUtil.getMapData(RedisConstant.PRODUCT_PRE + userExchangeFavorites.getProductId(),
					RedisConstant.PRODUCT_REDIS_SKU);
			if (StringUtils.isEmpty(skus) || StringUtils.isEmpty(pros)) {
				favorites.remove(i);
			} else {
				ProductVO pro = JsonUtil.getObject(pros, ProductVO.class);
				// 邮费
				userExchangeFavorites.setStock(pro.getCarriage() == null ? 0 : pro.getCarriage().intValue()); // 包邮判断
				if (userExchangeFavorites.getStock() != 0) {
					userExchangeFavorites
							.setShippingTemplate(this.getShippingTemplate(map, userExchangeFavorites.getSupplierId()));
				}

				// 限购
				String limit = "";
				if (pro.getLimitKbn() != null && pro.getLimitKbn() == 3) {
					limit = "企业级用户可买<br />";
				}
				if (pro.getLimitCnt() != null && pro.getLimitCnt() != 0) {
					limit += "每用户限购" + pro.getLimitCnt() + "件<br />";
				}
				limit += "销售区域:" + pro.getAreasName();
				userExchangeFavorites.setItemValues(limit);

				// sku
				List<ProductSpecifications> listResProSpe = JsonUtil.getList(skus, ProductSpecifications.class);
				for (ProductSpecifications sku : listResProSpe) {
					sku.setQuantity(
							NumberUtil.toInteger(redisUtil.get(RedisConstant.REDIS_SKU_INVENTORY + sku.getId())));
				}
				listResProSpe.removeIf(sku -> sku.getQuantity().intValue()==0);
				userExchangeFavorites.setProductSpecificationslist(listResProSpe);
			}

		}
		model.addAttribute("favorites", favorites);

//		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		model.addAttribute("userId", userId);
		model.addAttribute("ids", ids);
		model.addAttribute("webUrl", Constant.FACTORY_WEB_URL);
		return "manager/exchange/merge-order";
	}

	/**
	 * 修改对账单状态值
	 * 
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/close")
	@ResponseBody
	public ActResult<Object> comfirmReturn(String id, String closeReason, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser) obj;
		ExchangeSuborder order = exchangeSuborderService.getById(id);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", order.getUserId());
		paramMap.put("subOrderId", order.getSubOrderId());
		paramMap.put("closeReason", "平台：" + closeReason);
		paramMap.put("updateBy", user.getId());
		paramMap.put("exchangeStatus", "3");

		String api = Constant.FACTORY_API_URL + "exchangeOrder/autoCancelOrder.tj";
		HttpClientUtil.sendHttpRequest("post", api, paramMap);

		return ActResult.success(null);
	}

	/**
	 * 修改对账单状态值
	 * 
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/submitOrder")
	@ResponseBody
	public ActResult<Object> mergeOrder(String id, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser) obj;
		ExchangeSuborder order = exchangeSuborderService.getById(id);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", order.getUserId());
		paramMap.put("subOrderId", order.getSubOrderId());
		paramMap.put("updateBy", user.getId());

		String api = Constant.FACTORY_API_URL + "exchangeOrder/submitOrder.tj";
		HttpClientUtil.sendHttpRequest("post", api, paramMap);

		return ActResult.success(null);
	}

	/**
	 * 调剂订单
	 * 
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/doMerge")
	@ResponseBody
	public ActResult<Object> doMerge(String sku_nums, Long orderId, String ids, Long userId, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser) obj;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("orderId", orderId);
		paramMap.put("subOrderIds", ids);
		paramMap.put("sku_nums", sku_nums);
		paramMap.put("updateBy", user.getId());

		String api = Constant.FACTORY_API_URL + "exchangeOrder/mergeOrder.tj";
		String response = HttpClientUtil.sendHttpRequest("post", api, paramMap);

		return JsonUtil.getObject(response, ActResult.class);
	}

	private List<Supplier> getSupplierList(Map<String, Object> query) {
		query.put("status", 2);
		query.put("pageNum", 1);
		query.put("pageSize", 5000);

		return supplierService.getPage(query).getList();
	}

	private Supplier getSupplier(Map<Long, Supplier> map, Long supplierId) {
		if (!map.containsKey(supplierId)) {
			Supplier s = supplierService.findByid(supplierId);
			if (s != null) {
				map.put(supplierId, s);
			}
		}
		return map.get(supplierId);
	}

	private ShippingTemplate getShippingTemplate(Map<Long, ShippingTemplate> map, Long supplierId) {
		if (!map.containsKey(supplierId)) {
			ShippingTemplate shippingTemplate = new ShippingTemplate();
			shippingTemplate.setSupplierId(supplierId);
			shippingTemplate.setVersion(2);
			shippingTemplate.setIsAudit(0);
			List<ShippingTemplate> shippingTemplateList = shippingTemplateService.selectByModel(shippingTemplate);
			if (null != shippingTemplateList && !shippingTemplateList.isEmpty()) {
				ShippingTemplate newShippingTemplate = shippingTemplateList.get(0);

				ShippingTemplateRule shippingTemplateRule = new ShippingTemplateRule();
				shippingTemplateRule.setTemplateId(newShippingTemplate.getId());
				newShippingTemplate.setRulelist(shippingTemplateRuleService.selectByModel(shippingTemplateRule));

				ShippingFreeRule record2 = new ShippingFreeRule();// 设置包邮规则
				record2.setTemplateId(newShippingTemplate.getId());
				newShippingTemplate.setFreelist(shippingFreeRuleService.selectByModel(record2));

				map.put(supplierId, newShippingTemplate);
			}
		}
		return map.get(supplierId);
	}

	private UserFactory getUser(Map<Long, UserFactory> map, Long userId) {
		if (!map.containsKey(userId)) {
			UserFactory uf = userFactoryService.getById(userId);
			if (uf != null) {
				map.put(userId, uf);
			}
		}
		return map.get(userId);
	}

	// 换领订单
	@RequestMapping("exchangeOrder")
	public String exchangeOrder(Model model, HttpSession session) {
		Map<String, Object> query = new HashMap<String, Object>();
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser) obj;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("officeId", 0);
		params.put("officeType", "");
		params.put("roles", "-108,-111");
		params.put("pageNum", 1);
		params.put("pageSize", 120);
		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		model.addAttribute("mlist", list);
		model.addAttribute("uid", user.getId());
		List<SysRole> roles = sysRoleService.findUserRoleListByUserId(user.getId());
		model.addAttribute("supplierList", getSupplierList(query));
		model.addAttribute("roles", roles);
		model.addAttribute("finance", 1);
		return "manager/exchange/exchange";
	}

	// 换领订单
	@RequestMapping("exchangeOrderFinance")
	public String exchangeOrderFinance(Model model, HttpSession session) {
		Map<String, Object> query = new HashMap<String, Object>();
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser) obj;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("officeId", 0);
		params.put("officeType", "");
		params.put("roles", "-108,-111");
		params.put("pageNum", 1);
		params.put("pageSize", 120);
		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		model.addAttribute("mlist", list);
		model.addAttribute("uid", user.getId());
		List<SysRole> roles = sysRoleService.findUserRoleListByUserId(user.getId());
		model.addAttribute("supplierList", getSupplierList(query));
		model.addAttribute("roles", roles);
		model.addAttribute("finance", 0);
		return "manager/exchange/exchange";
	}

	/**
	 * 换领订单列表
	 * 
	 * @param params
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "exchangelist", method = RequestMethod.POST)
	public String exchangelist(@RequestParam Map<String, Object> params, Model model, HttpSession session,
			HttpServletRequest request, Integer type) {
		String[] ary = request.getParameterValues("status");
		if (ary != null && ary.length > 0) {
			StringBuilder sb = new StringBuilder();
			for (String string : ary) {
				string = string.trim();
				if ("1".equals(string)) {
					params.put("payStatus", 1);
				} else {
					sb.append(string).append(",");
					if ("3".equals(string) || "5".equals(string)) {
						if ("3".equals(string)) {
							sb.append("13").append(",");
							sb.append("14").append(",");
						}
						sb.append("15").append(",");
						sb.append("16").append(",");
					}
				}
			}
			params.put("status", sb.toString());
		}
		String suborderId = (String) params.get("relationId");
		if (!StringUtils.isEmpty(suborderId)) {
			Suborder suborder = subOrderService.getById(suborderId);
			if (suborder != null) {
				if (suborder.getRelationId() == null) {// relationid等于空是匹配成功
					params.put("subOrderId", suborder.getSubOrderId());
				} else {// 非等于空是调剂成功
					params.remove("relationId");
					params.put("batchId", suborder.getRelationId());
				}
			}
		}

		PageInfo<ExchangeSuborderVo> page = exchangeSuborderService.getExSuborderList(params);
		List<ExchangeSuborderVo> vo = page.getList();
		for (ExchangeSuborderVo suborderOrderVo : vo) {
			if ("wxpay".equals(suborderOrderVo.getThirdType()) && suborderOrderVo.getThirdNo() != null) {
				String paywxType = paywxType(suborderOrderVo.getThirdNo());
				suborderOrderVo.setThirdType(paywxType);
			}
		}
		if (type != null) {
			if (type == 0) {
				model.addAttribute("finance", 1);
			} else {
				model.addAttribute("finance", 0);
			}
		}
		model.addAttribute("page", page);
		return "manager/exchange/exchange-list";
	}

	/**
	 * 换领订单详情
	 * 
	 * @param model
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping("{mode}/showteail")
	public String showteail(Model model, String id, HttpSession session) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subOrderId", id);
		params.put("pageNum", 1);
		params.put("pageSize", 2);
		PageInfo<ExchangeSuborderVo> page = exchangeSuborderService.getExSuborderList(params);
		ExchangeSuborderVo vo = page.getList().get(0);
		ExchangeOrders order = exchangeSuborderService.getOrderById(vo.getOrderId());
		String phone = order.getMobile();
		if (!StringUtils.isEmpty(phone) && !"空".equals(phone)) {
			phone = phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length());
			order.setMobile(phone);
		}
		String m = order.getAddress();
		if (m.contains("(电话")) {
			m = m.substring(0, m.lastIndexOf("(电话"));
			order.setAddress(m);
		}
		model.addAttribute("vo", vo);
		model.addAttribute("order", order);
		if (!StringUtils.isEmpty(vo.getNoto())) {// subOrder表中的noto不为空
			model.addAttribute("note", vo.getNoto());
		} else {
			model.addAttribute("note", order.getNote());
		}
		if (vo.getReturnOrderId() != null) {
			Returnorder returnorder = returnOrderDao.getById(vo.getReturnOrderId());
			if (returnorder != null) {
				model.addAttribute("returnSeason", returnorder.getReason());
			}
		} else {
			if (vo.getRefundOrderId() != null) {
				Refundorder refundorder = refundOrderDao.getById(vo.getRefundOrderId());
				if (refundorder != null) {
					model.addAttribute("returnSeason", refundorder.getReason());
				}
			}
		}
		Supplier s = supplierService.findByEmpId(order.getUserId());
		model.addAttribute("enterPriseName", s == null ? "" : s.getComName());

		model.addAttribute("items", exchangeSuborderService.selectItems(id));

		ExpressCompany ci = expressComService.getExpressComById(vo.getExpressType());
		if (ci != null) {
			model.addAttribute("expressName", ci.getName());
			model.addAttribute("expressCom", ci.getPinYin());
			if ("14660000000000000".equals(vo.getExpressType())) {
				String expressNo = vo.getExpressNo();
				if (StringUtils.isEmpty(expressNo)) {
					model.addAttribute("expressNo", "");
				} else {
					if (expressNo.length() > 4) {
						model.addAttribute("expressNo", expressNo.substring(0, 2) + "****"
								+ expressNo.substring(expressNo.length() - 2, expressNo.length()));
					} else {
						model.addAttribute("expressNo", "****");
					}
				}
			} else {
				model.addAttribute("expressNo", vo.getExpressNo());
			}
		}
		model.addAttribute("issuedInvoice", null);
		model.addAttribute("invoiceApply", null);
		if (vo.getInvoiceStatus() == 2) {
			IssuedInvoice issuedInvoice = issuedInvoiceService.getIssuedInvoice(id);
			model.addAttribute("issuedInvoice", issuedInvoice);
		} else {
			List<InvoiceApply> InvoiceApplyList = invoiceApplyService.getInvoiceApplyBySuborderId(id);
			if (InvoiceApplyList != null && !InvoiceApplyList.isEmpty()) {
				model.addAttribute("invoiceApply", InvoiceApplyList.get(0));
			}
		}
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser) obj;
		List<SysRole> roles = sysRoleService.findUserRoleListByUserId(user.getId());
		model.addAttribute("roles", roles);
		model.addAttribute("webUrl", Constant.FACTORY_WEB_URL);
		return "manager/exchange/exchange-detail";
	}

	public String paywxType(String tradeNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("way", "wxpay");
		params.put("status", "2");
		params.put("tradeNo", tradeNo);
		String appId = "";
		List<Payment> ls = paymentDao.findList(params);
		for (Payment pay : ls) {
			appId = pay.getAppId() == null ? "" : pay.getAppId();
			break;
		}
		if ("wx1b153767a3760be4".equals(appId)) {
			appId = "微信APP";
		} else if ("wxb62e121cbeffdddf".equals(appId)) {
			appId = "微信公众号";
		}
		return appId;
	}

	/**
	 * 弹窗 到款确认
	 * 
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "toConfirmPay", method = RequestMethod.POST)
	public String toConfirmPay(Model model, String id) {
		ExchangeSuborder byId = exchangeSuborderService.getById(id);
		model.addAttribute("order", byId);
		return "manager/exchange/confirmBox";
	}

	/**
	 * 修改对账单状态值
	 * 
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/confirmPay")
	@ResponseBody
	public ActResult<Object> comfirmReturn(String id, Integer payStatus, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser) obj;
		// Suborder order = subOrderService.getById(id);
		ExchangeSuborder order = exchangeSuborderService.getById(id);
		Date now = new Date();
		Payment payment = paymentService.findPaymentByOrderOrSuborderId(order.getOrderId(), order.getSubOrderId());
		if (payment != null) {
			payment.setPayConfirm(payStatus);
			payment.setPayConfirmDate(now);
			payment.setUpdUser(user.getName());
			payment.setUpdateTime(now);
			// 更新payment表
			paymentService.update(payment);
			// 更新subOrder表
			if (StringUtils.isEmpty(payment.getSubOrderId())) {
				// 通过orderid查suborder
				ExchangeSuborder ex = new ExchangeSuborder();
				ex.setOrderId(payment.getOrderId());
				List<ExchangeSuborder> selectByModel = exchangeSuborderService.selectByModel(ex);
				// suborder为list
				for (ExchangeSuborder suborder : selectByModel) {
					suborder.setPayConfirm(payStatus);
					suborder.setPayConfirmDate(now);
					suborder.setUpdateBy(user.getName());
					suborder.setUpdateTime(now);
					exchangeSuborderService.update(suborder);
				}
			} else {
				ExchangeSuborder suborder = exchangeSuborderService.getById(payment.getSubOrderId());
				suborder.setPayConfirm(payStatus);
				suborder.setPayConfirmDate(now);
				suborder.setUpdateBy(user.getName());
				suborder.setUpdateTime(now);
				exchangeSuborderService.update(suborder);
			}
		} else {
			order.setPayConfirm(payStatus);
			order.setPayConfirmDate(now);
			order.setUpdateBy(user.getName());
			order.setUpdateTime(now);
			exchangeSuborderService.update(order);
		}
		if (order.getRelationId() != null && order.getBatchId() == null) {
			Suborder byId = subOrderService.getById(order.getRelationId());
			// Payment payments =
			// paymentService.findPaymentByOrderOrSuborderId(byId.getOrderId(),byId.getSubOrderId());
			byId.setPayConfirm(payStatus);
			byId.setPayConfirmDate(now);
			byId.setUpdateBy(user.getName());
			byId.setUpdateTime(now);
			subOrderService.update(byId);
		} else if (order.getRelationId() == null && order.getBatchId() != null) {
			boolean whether = true;
			ExchangeSuborder ex = new ExchangeSuborder();
			ex.setBatchId(order.getBatchId());
			List<ExchangeSuborder> selectByModel = exchangeSuborderService.selectByModel(ex);
			for (ExchangeSuborder exchangeSuborder : selectByModel) {
				if (exchangeSuborder.getPayConfirm() != 1) {
					whether = false;
					break;
				}
			}
			if (whether == true) {
				List<Suborder> findByRelationId = subOrderService.findByRelationId(Long.valueOf(order.getBatchId()));
				for (Suborder suborder : findByRelationId) {
					suborder.setPayConfirm(payStatus);
					suborder.setPayConfirmDate(now);
					suborder.setUpdateBy(user.getName());
					suborder.setUpdateTime(now);
					subOrderService.update(suborder);
				}
			} else {
				List<Suborder> findByRelationId = subOrderService.findByRelationId(Long.valueOf(order.getBatchId()));
				for (Suborder suborder : findByRelationId) {
					suborder.setPayConfirm(0);
					suborder.setPayConfirmDate(now);
					suborder.setUpdateBy(user.getName());
					suborder.setUpdateTime(now);
					subOrderService.update(suborder);
				}
			}

		}
		return ActResult.success(null);
	}
	//导出支付账单
	@RequestMapping(value = "exportExcelFinance")
	@ResponseBody
	public void exportExcelFinance(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 50000);
		String[] ary = request.getParameterValues("status");
		if (ary != null && ary.length > 0) {
			StringBuilder sb = new StringBuilder();
			for (String string : ary) {
				string = string.trim();
				if ("1".equals(string)) {
					params.put("payStatus", 1);
				} else {
					sb.append(string).append(",");
					if ("3".equals(string) || "5".equals(string)) {
						if ("3".equals(string)) {
							sb.append("13").append(",");
							sb.append("14").append(",");
						}
						sb.append("15").append(",");
						sb.append("16").append(",");
					}
				}
			}
			params.put("status", sb.toString());
		}
		String suborderId = (String) params.get("relationId");
		if (!StringUtils.isEmpty(suborderId)) {
			Suborder suborder = subOrderService.getById(suborderId);
			if (suborder != null) {
				if (suborder.getRelationId() == null) {// relationid等于空是匹配成功
					params.put("subOrderId", suborder.getSubOrderId());
				} else {// 非等于空是调剂成功
					params.remove("relationId");
					params.put("batchId", suborder.getRelationId());
				}
			}
		}

		PageInfo<ExchangeSuborderVo> page = exchangeSuborderService.getExSuborderList(params);
		List<ExchangeSuborderVo> vo = page.getList();
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("换领单到款确认"); 
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        
        /**
         * 设置样式 start
         * */
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        /**设置样式  end
         * */
        List<String> headers= new ArrayList<String>();
        headers.add("换领订单ID");
        headers.add("支付状态");
        headers.add("付款时间");
        headers.add("取消时间");
        headers.add("匹配状态");
        headers.add("订单关联key");
        headers.add("商家名称");
        headers.add("应付金额 ");
        headers.add("含运费");
        headers.add("现金券抵扣");
        headers.add("支付金额");
        headers.add("支付方式");
        headers.add("支付流水号");
        headers.add("到款状态");

        /**
         * 
         * 设置订单详情表头 start
         * */
        HSSFRow row = sheet.createRow((int) 0); 
        for (int i = 0; i < headers.size(); i++) {
        	HSSFCell cell = row.createCell(i);
            //设置值
            cell.setCellValue(headers.get(i));  
            //设置样式
            cell.setCellStyle(style);
		}
        /** 设置订单详情表头 end
         * */
        int currentRow = 0;
		for (ExchangeSuborderVo p : vo) {
			if ("wxpay".equals(p.getThirdType()) && p.getThirdNo() != null) {
				String paywxType = paywxType(p.getThirdNo());
				p.setThirdType(paywxType);
			}
			
			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 

            //headers.add("换领订单ID");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getSubOrderId()); 
            //headers.add("支付状态");
            if(p.getStatus()==0) {
	            row.createCell(col++).setCellValue("未支付");
            } else if(p.getStatus()==1 || p.getStatus()==2 || p.getStatus()==4){
	            row.createCell(col++).setCellValue("已支付");
            } else {
	            row.createCell(col++).setCellValue("");
            }
            //headers.add("付款时间");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getPayTime()==null?"":DateUtils.formatDate(p.getPayTime(),"yyyy-MM-dd")); //创建时间
            //headers.add("取消时间");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getCancelTime()==null?"":DateUtils.formatDate(p.getCancelTime(),"yyyy-MM-dd")); //创建时间
            //headers.add("匹配状态");
            if(p.getExchangeStatus()==0){
            	row.createCell(col++).setCellValue("未支付");
            }else if(p.getExchangeStatus()==1){
            	row.createCell(col++).setCellValue("匹配中");
            }else if(p.getExchangeStatus()==2){
            	row.createCell(col++).setCellValue("匹配成功");
            }else if(p.getExchangeStatus()==3){
            	row.createCell(col++).setCellValue("匹配失败 已退款");
            }else if(p.getExchangeStatus()==4){
            	row.createCell(col++).setCellValue("匹配失败 调剂成功");
            }else if(p.getExchangeStatus()==-1){
            	row.createCell(col++).setCellValue("已关闭");
            } else {
	            row.createCell(col++).setCellValue("");
            }
            //headers.add("订单关联key");
            if(p.getExchangeStatus()==2){
                row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getRelationId());
            }else if(p.getExchangeStatus()==4){
                row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getBatchId()+"");
            } else {
	            row.createCell(col++).setCellValue("");
            }
            //headers.add("商家名称");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getSupplierName()); 
            //headers.add("应付金额 ");
            row.createCell(col++).setCellValue(p.getRealPrice().doubleValue()); 
            //headers.add("含运费");
            row.createCell(col++).setCellValue(p.getTotalShipping().doubleValue()); 
            //headers.add("现金券抵扣");
            row.createCell(col++).setCellValue(p.getCashPay()==null?0:p.getCashPay().doubleValue()); 
            //headers.add("支付金额");
            row.createCell(col++).setCellValue(p.getThirdPay()==null?0:p.getThirdPay().doubleValue()); 
            //headers.add("支付方式");
            if("zhifubao".equals(p.getThirdType())){
                row.createCell(col++).setCellValue("支付宝");
            }else if("pingtaiyue".equals(p.getThirdType())){
                row.createCell(col++).setCellValue("现金券");
            }else if("unionpay".equals(p.getThirdType())){
                row.createCell(col++).setCellValue("银联");
            } else {
	            row.createCell(col++).setCellValue(p.getThirdType());
            }
            //headers.add("支付流水号");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getThirdNo()); 
            //headers.add("到款状态");
            if(p.getPayConfirm()==null || p.getPayConfirm()==0){
                row.createCell(col++).setCellValue("未到款");
            } else {
	            row.createCell(col++).setCellValue("未到款");
            }
		}
        
        // 第六步，将文件存到指定位置  
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ FileUtils.getFileNameForSave(request,"换领单到款确认"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		
		try {
			wb.write(response.getOutputStream());
			wb.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
