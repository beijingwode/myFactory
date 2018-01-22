package com.wode.api.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.wode.common.db.DBUtils;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.factory.model.Product;
import com.wode.factory.model.SupplierLimitTicket;
import com.wode.factory.model.SupplierLimitTicketSku;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserLimitTicket;
import com.wode.factory.model.UserWeixin;
import com.wode.factory.service.ProductService;
import com.wode.factory.user.facade.UserLimitTicketFacade;
import com.wode.factory.user.service.SupplierLimitTicketService;
import com.wode.factory.user.service.SupplierLimitTicketSkuService;
import com.wode.factory.user.service.UserLimitTicketService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.service.UserWeixinService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.vo.UserLimitTicketVo;

@Controller
@RequestMapping("/limitTicket")
@SuppressWarnings("unchecked")
public class UserLimitTicketController extends BaseController{
	@Autowired
	DBUtils dbUtils;
	@Autowired
	private SupplierLimitTicketService supplierLimitTicketService;
	@Autowired
	private SupplierLimitTicketSkuService supplierLimitTicketSkuService;
	@Autowired
	private UserLimitTicketService userLimitTicketService;
	@Autowired
	private UserWeixinService userWeixinService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserLimitTicketFacade userLimitTicketFacade;
	
	private static Logger logger = LoggerFactory.getLogger(UserLimitTicketController.class);
	
	/**
	 * 商品详情获取优惠券接口
	 * @param skuId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = {"/getProductTicket.user"})
	@ResponseBody
	public ActResult<Object> getProductTicket(Long skuId,HttpServletRequest request, HttpServletResponse response){
		List<SupplierLimitTicket> limitTicketList = new ArrayList<SupplierLimitTicket>();
		// 通过SKU 查看可用券
		List<SupplierLimitTicketSku> supplierLimitTicketSkuList = supplierLimitTicketSkuService.getBySkuId(skuId);
		for (SupplierLimitTicketSku supplierLimitTicketSku : supplierLimitTicketSkuList) {
			SupplierLimitTicket limitTicket = supplierLimitTicketService.getByIdAndDateStatus(supplierLimitTicketSku.getLimitTicketId());
			if(limitTicket!=null) {
				List<SupplierLimitTicketSku> supplierLimitTicketSkuList2 = supplierLimitTicketSkuService.getByLimitTicketId(limitTicket.getId());
				limitTicket.setSupplierLimitTicketSkuList(supplierLimitTicketSkuList2);
				limitTicketList.add(limitTicket);
			}
		}
		// 专享商品
		if(loginUser.getSupplierId() != null) {
			Product pro= productService.findBySku(skuId);
			if(pro!=null && pro.getSaleKbn()!=null && pro.getSaleKbn()==4) {
				limitTicketList.addAll(supplierLimitTicketService.selectLimit4BySupId(loginUser.getSupplierId()));
			}
		}

		//检查用户是否已领取
		for(int i=limitTicketList.size()-1;i>=0;i--) {
			SupplierLimitTicket limitTicket = limitTicketList.get(i);
			UserLimitTicket userLimitTicket = userLimitTicketService.getByLimitTicketIdAndUserId(limitTicket.getId(),loginUser.getId());
			if(userLimitTicket!=null) {
				// 已领取
				if(userLimitTicket.getStatus()==2 || userLimitTicket.getStatus()==3) {
					// 已用完或已过期 不要出现
					limitTicketList.remove(i);
				} else {
					// 标记已领取
					limitTicket.setFlag(1);
					limitTicket.setUserLimitTicketId(userLimitTicket.getId());
				}
			}else {
				// 未领取
				if(limitTicket.getStatus()==1) {
					// 标记未领取
					limitTicket.setFlag(0);
				} else {
					// 其他情况不能领取
					limitTicketList.remove(i);
				}
			}
		}
		return ActResult.success(limitTicketList);
	}
	/**
	 * 领取优惠券接口
	 * @param limitTicketId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = {"/receiveimitTicket.user"})
	@ResponseBody
	public ActResult<Object> receiveimitTicket(Long limitTicketId,HttpServletRequest request, HttpServletResponse response){
		SupplierLimitTicket limitTicket = supplierLimitTicketService.getById(limitTicketId);
		if(limitTicket==null) {
			return ActResult.fail("系统错误");
		}
		UserLimitTicketVo userLimitTicketVo = userLimitTicketService.selectUnusedById(limitTicket.getId().toString(), loginUser.getId().toString());
		if(userLimitTicketVo!=null) {
			return ActResult.fail("您已经领取过了");
		}
		if(!limitTicket.getSupplierId().equals(-1L) && !limitTicket.getSupplierId().equals(loginUser.getSupplierId())) {
			return ActResult.fail("抱歉，不能领取别人家的优惠券");
		}
		if(limitTicket.getTicketNum()<=limitTicket.getReceiveNum()) {
			return ActResult.fail("券都被抢光了哟~");
		}
		Date date = new Date();
		if(limitTicket.getLimitEnd().getTime()<date.getTime()) {
			return ActResult.fail("手慢了，已过使用期限领了也没用");
		}
		UserLimitTicket ult = new UserLimitTicket();
		ult.setId(dbUtils.CreateID());
		ult.setUserId(loginUser.getId());
		ult.setLimitTicketId(limitTicket.getId());
		ult.setUserNickname(loginUser.getNickName());
		ult.setOneceFlag(limitTicket.getOneceFlag());
		ult.setTicketType(limitTicket.getTicketType());
		ult.setLimitType(limitTicket.getLimitType()!=null?limitTicket.getLimitType():limitTicket.getTicketType());
		ult.setLimitKey(limitTicket.getLimitKey()!=null?limitTicket.getLimitKey():"");
		ult.setTicketTotal(limitTicket.getTicket());
		ult.setTicketBalance(limitTicket.getTicket());
		ult.setCashTotal(limitTicket.getCash());
		ult.setCashBalance(limitTicket.getCash());
		ult.setLimitStart(limitTicket.getLimitStart());
		ult.setLimitEnd(limitTicket.getLimitEnd());
		ult.setTicketNote(limitTicket.getTicketNote());
		ult.setNextAction(limitTicket.getNextAction());
		ult.setStatus(0);
		ult.setCreateDate(date);
		userLimitTicketService.save(ult);
		limitTicket.setReceiveNum(limitTicket.getReceiveNum()+1);
		supplierLimitTicketService.update(limitTicket);
		if(limitTicket.getReceiveNum()==limitTicket.getTicketNum()) {
			limitTicket.setStatus(2);
			supplierLimitTicketService.update(limitTicket);
		}
		return ActResult.success("");
	}
	/**
	 * 卡券领取页
	 * @param request
	 * @param supplierTicketId
	 * @param fromId
	 * @return
	 */
	@RequestMapping(value={"toPage{supplierTicketId}","toRecive{supplierTicketId}"})
	public ModelAndView toPage(HttpServletRequest request, @PathVariable Long supplierTicketId,String fromId) {
		ModelAndView result = new ModelAndView("limitTicket/ticket_page");
		SupplierLimitTicket slt = supplierLimitTicketService.getById(supplierTicketId);
		if(slt==null) {
			// 数据异常 直接跳到首页
			result.setViewName("redirect:"+"http://"+Constant.SYSTEM_DOMAIN+"/index_m.htm");
			return result;
		}
		SupplierLimitTicketSku slts = new SupplierLimitTicketSku();
		slts.setLimitTicketId(slt.getId());
		List<SupplierLimitTicketSku> sltsList = supplierLimitTicketSkuService.selectByModel(slts);
		String onload = "";
		if(request.getRequestURI().equals("toRecive")) {
			// 直接领取
			onload = "autoRecive";
		}
		result.addObject("slt", slt);
		for (SupplierLimitTicketSku supplierLimitTicketSku : sltsList) {
			supplierLimitTicketSku.setItemValues(supplierLimitTicketSku.getItemValues().replace("{", "").replace("\"", "").replace("}", ""));
		}
		result.addObject("sltsList", sltsList);
		result.addObject("fromId", fromId);
		result.addObject("onload", onload);
 		return result;
	}
	
	@RequestMapping("/recive{supplierTicketId}.user")
	public ModelAndView recive(HttpServletRequest request, @PathVariable Long supplierTicketId,String ticketType) {
		ModelAndView result = new ModelAndView();
		boolean flag = true;
		SupplierLimitTicket slt = supplierLimitTicketService.getById(supplierTicketId);
		if(slt==null) {
			// 数据异常 直接跳到首页
			result.setViewName("redirect:"+"http://"+Constant.SYSTEM_DOMAIN+"/index_m.htm");
			return result;
		}
		SupplierLimitTicketSku slts = new SupplierLimitTicketSku();
		slts.setLimitTicketId(slt.getId());
		List<SupplierLimitTicketSku> sltsList = supplierLimitTicketSkuService.selectByModel(slts);
		String nextAction = "";	// 立即使用
		String moreLink = "";	// 查看更多
		String errMsg = "";
		Integer type = 0;
		moreLink = "http://"+Constant.SYSTEM_DOMAIN+"/limitTicket/page.user?uid="+loginUser.getId();

		if(!slt.getSupplierId().equals(-1L) && !slt.getSupplierId().equals(loginUser.getSupplierId())) {
			errMsg = "抱歉，不能领取别人家的优惠券";
			flag = false;
			type=1;
		}
		UserLimitTicketVo userLimitTicketVo = null;
		if(flag) {
			userLimitTicketVo = userLimitTicketService.selectUnusedById(slt.getId().toString(), loginUser.getId().toString());
			if(userLimitTicketVo!=null) {
				errMsg ="您已经领取过了";
				flag = false;
				type=2;
			}
		}
		if(flag) {
			if(slt.getTicketNum()<=slt.getReceiveNum()) {
				errMsg ="券都被抢光了哟~";
				type=3;
				flag = false;
			}
		}
		Date date = new Date();
		if(flag) {
			if(slt.getLimitEnd().getTime()<date.getTime()) {
				errMsg ="手慢了，已过使用期限领了也没用";
				flag = false;
				type=4;
			}
		}
		UserLimitTicket ult = new UserLimitTicket();
		if(flag) {
			ult.setId(dbUtils.CreateID());
			ult.setUserId(loginUser.getId());
			ult.setLimitTicketId(slt.getId());
			ult.setUserNickname(loginUser.getNickName());
			ult.setOneceFlag(slt.getOneceFlag());
			ult.setTicketType(slt.getTicketType());
			ult.setLimitType(slt.getLimitType()!=null?slt.getLimitType():slt.getTicketType());
			ult.setLimitKey(slt.getLimitKey()!=null?slt.getLimitKey():"");
			ult.setTicketTotal(slt.getTicket());
			ult.setTicketBalance(slt.getTicket());
			ult.setCashTotal(slt.getCash());
			ult.setCashBalance(slt.getCash());
			ult.setLimitStart(slt.getLimitStart());
			ult.setLimitEnd(slt.getLimitEnd());
			ult.setTicketNote(slt.getTicketNote());
			ult.setNextAction(slt.getNextAction());
			ult.setStatus(0);
			ult.setCreateDate(date);
			userLimitTicketService.save(ult);
			slt.setReceiveNum(slt.getReceiveNum()+1);
			supplierLimitTicketService.update(slt);
			if(slt.getReceiveNum()==slt.getTicketNum()) {
				slt.setStatus(2);
				supplierLimitTicketService.update(slt);
			}
			nextAction = slt.getNextAction();
		}
		result.addObject("moreLink", moreLink);
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		String limits = String.format("有效日期：%s - %s", df.format(slt.getLimitStart()), df.format(slt.getLimitEnd()));
		result.addObject("limits", limits);
		result.addObject("errMsg", errMsg);
		result.addObject("slt", slt);
		if(userLimitTicketVo==null) {
			result.addObject("ult", ult);
		}else {
			result.addObject("ult", userLimitTicketVo);
		}
		for (SupplierLimitTicketSku supplierLimitTicketSku : sltsList) {
			supplierLimitTicketSku.setItemValues(supplierLimitTicketSku.getItemValues().replace("{", "").replace("\"", "").replace("}", ""));
		}
		result.addObject("sltsList", sltsList);
		result.addObject("type", type);
		result.setViewName("limitTicket/ticket_recive_result");
 		return result;
	}

	/**
	 * 确认订单获取优惠券接口
	 * @param skuIds
	 * @return
	 */
	@RequestMapping(value = {"/getBySkuIdsTicket.user"})
	@ResponseBody
	public ActResult<Object> getBySkuIdsTicket(String skuIds){
		if(StringUtils.isEmpty(skuIds)) {
			return ActResult.fail("系统错误");
		}
		Map<Long,SupplierLimitTicket> map = new HashMap<Long,SupplierLimitTicket>();
		List<SupplierLimitTicket> limitTicketList = new ArrayList<SupplierLimitTicket>();
		String[] skuIdArr = skuIds.split(",");
		for (String skuid : skuIdArr) {
			List<SupplierLimitTicketSku> supplierLimitTicketSkuList = supplierLimitTicketSkuService.getBySkuId(Long.valueOf(skuid));
			for (SupplierLimitTicketSku supplierLimitTicketSku : supplierLimitTicketSkuList) {
				SupplierLimitTicket limitTicket = supplierLimitTicketService.getByIdAndDateStatus(supplierLimitTicketSku.getLimitTicketId());
				if(limitTicket!=null) {
					List<SupplierLimitTicketSku> supplierLimitTicketSkuList2 = supplierLimitTicketSkuService.getByLimitTicketId(limitTicket.getId());
					limitTicket.setSupplierLimitTicketSkuList(supplierLimitTicketSkuList2);
					if(!map.containsKey(limitTicket.getId())) {
						map.put(limitTicket.getId(),limitTicket);
					}
				}
			}
			// 专享商品
			if(loginUser.getSupplierId() != null) {
				Product pro= productService.findBySku(Long.valueOf(skuid));
				if(pro!=null && pro.getSaleKbn()!=null && pro.getSaleKbn()==4) {
					List<SupplierLimitTicket> selectLimit4BySupId = supplierLimitTicketService.selectLimit4BySupId(loginUser.getSupplierId());
					for (SupplierLimitTicket supplierLimitTicket : selectLimit4BySupId) {
						if(!map.containsKey(supplierLimitTicket.getId())) {
							map.put(supplierLimitTicket.getId(), supplierLimitTicket);
						}
					}
				}
			}
			//检查用户是否已领取
			Iterator<Long> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				Long ticketId = (Long) iterator.next();
				UserLimitTicket userLimitTicket = userLimitTicketService.getByLimitTicketIdAndUserId(ticketId,loginUser.getId());
				if(userLimitTicket!=null) {
					// 已领取
					if(userLimitTicket.getStatus()==2 || userLimitTicket.getStatus()==3) {
						// 已用完或已过期 不要出现
						map.remove(ticketId);
					} else {
						// 标记已领取
						SupplierLimitTicket supplierLimitTicket = map.get(ticketId);
						supplierLimitTicket.setFlag(1);
						supplierLimitTicket.setUserLimitTicketId(userLimitTicket.getId());
						map.put(supplierLimitTicket.getId(), supplierLimitTicket);
					}
				}else {
					// 未领取
					SupplierLimitTicket supplierLimitTicket = map.get(ticketId);
					if(supplierLimitTicket.getStatus()==1) {
						// 标记未领取
						supplierLimitTicket.setFlag(0);
						map.put(supplierLimitTicket.getId(), supplierLimitTicket);
					} else {
						// 其他情况不能领取
						map.remove(ticketId);
					}
				}
			}
		}
		Iterator<Long> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			Long ticketId = (Long) iterator.next();
			limitTicketList.add(map.get(ticketId));
		}
		return ActResult.success(limitTicketList);
	}
	

	/**
	 * 确认订单获取优惠券接口
	 * @param skuIds
	 * @return
	 */
	@RequestMapping(value = {"/getWithOutTicketBuySkuId.user"})
	@ResponseBody
	public ActResult<Object> getWithOutTicketBuySkuId(String skuIds){
		if(StringUtils.isEmpty(skuIds)) {
			return ActResult.fail("系统错误");
		}
		Long supplierId=-1L;
		if(loginUser.getSupplierId() != null) {
			supplierId=loginUser.getSupplierId();
		}
		List<SupplierLimitTicket> list = supplierLimitTicketService.getWithOutTicketMap(supplierId, loginUser.getId(), null, skuIds);
		for (SupplierLimitTicket supplierLimitTicket : list) {
			if(supplierLimitTicket.getLimitType()!=2) continue;
			List<SupplierLimitTicketSku> supplierLimitTicketSkuList2 = supplierLimitTicketSkuService.getByLimitTicketId(supplierLimitTicket.getId());
			supplierLimitTicket.setSupplierLimitTicketSkuList(supplierLimitTicketSkuList2);
		}
		String[] skuIdArr = skuIds.split(",");
		for (String skuid : skuIdArr) {
			// 专享商品
			if(loginUser.getSupplierId() != null) {
				Product pro= productService.findBySku(Long.valueOf(skuid));
				if(pro!=null && pro.getSaleKbn()!=null && pro.getSaleKbn()==4) {
					list.addAll(supplierLimitTicketService.getWithOutTicketMap(supplierId, loginUser.getId(), 4, null));
					break;
				}
			}
		}
		return ActResult.success(list);
	}

	/**
	 * 确认订单获取优惠券接口
	 * @param skuIds
	 * @return
	 */
	@RequestMapping(value = {"/getUsableTickits.user"})
	@ResponseBody
	public ActResult<Object> getUsableTickits(String skuIds){
		if(StringUtils.isEmpty(skuIds)) {
			return ActResult.fail("系统错误");
		}
		boolean hasLimit4 = false;
		/**
		 * ***********************************提取要购买的商品信息**********************************************
		 * */
		String[] ary = skuIds.split(",");
		List<Long> lsId = new ArrayList<Long>();
		for (String string : ary) {
			Long skuId = NumberUtil.toLong(string);
			lsId.add(skuId);
			Product p =  productService.findBySku(skuId);
			if(p.getSaleKbn() == 4) {
				hasLimit4=true;
			}
		}
		if(loginUser.getSupplierId() == null) {
			hasLimit4=false;
		}
		List<UserLimitTicket> list = userLimitTicketFacade.getUsableTickits(loginUser.getId(), lsId, hasLimit4);
		for (UserLimitTicket userLimitTicket2 : list) {
			if(userLimitTicket2.getLimitType()!=2) continue;
			SupplierLimitTicketSku slts = new SupplierLimitTicketSku();
			slts.setLimitTicketId(userLimitTicket2.getLimitTicketId());
			List<SupplierLimitTicketSku> sltsList = supplierLimitTicketSkuService.selectByModel(slts);
			userLimitTicket2.setSupplierLimitTicketSkuList(sltsList);
		}
		
		return ActResult.success(list);
	}
	/**
	 * 计算使用优惠券
	 * @param limitTicketIds 	null/"":默认选择，0:不使用，XXXX,XXXX 选中优惠券id(t_user_limit_ticket)
	 * @param sku_cashs		skuId1_0,skuId_50
	 * @param sku_tickets	skuId1_10,skuId_20
	 * @param model			
	 * @param request
	 * @return				{amount:抵扣总额，
	 * 						 cash:抵扣现金，
	 * 						 ticket:抵扣内购券，
	 * 						 names:券名称，
	 * 						 limitTicketIds:使用优惠券ids,0:不使用
	 * 						 keyInfo:skuId1_ticketId1_0_100,skuId2_ticketId1_1_200
	 * 						}
	 */
	@RequestMapping(value = {"/calculateLimitTicket.user"})
	@ResponseBody
	public ActResult<ModelMap> calculateLimitTicket(String sku_cashs_tickets,String limitTicketIds, HttpServletRequest request) {
		ModelMap model = new ModelMap();
		List<Long> skuIds = new ArrayList<Long>();
		Map<Long,BigDecimal> mapCashs = new HashMap<Long,BigDecimal>();
		Map<Long,BigDecimal> mapTicket = new HashMap<Long,BigDecimal>();
		Map<Long,Product> mapProduct = new HashMap<Long,Product>();
		boolean hasLimit4 = false;
		/**
		 * ***********************************提取要购买的商品信息**********************************************
		 * */
		String[] ary = sku_cashs_tickets.split(",");
		for (String string : ary) {
			String[] ary2 = string.split("_");
			Long skuId = NumberUtil.toLong(ary2[0]);
			skuIds.add(skuId);
			mapCashs.put(skuId, NumberUtil.toBigDecimal(ary2[1]));
			mapTicket.put(skuId, NumberUtil.toBigDecimal(ary2[2]));
			Product p =  productService.findBySku(skuId);
			if(p.getSaleKbn() == 4) {
				hasLimit4=true;
			}
			mapProduct.put(skuId, p);
		}
		if(loginUser.getSupplierId() == null) {
			hasLimit4=false;
		}
		
		/**
		 * ***********************************获取每个sku可用优惠券**********************************************
		 * */
		List<UserLimitTicket> ults = null;
		if(StringUtils.isEmpty(limitTicketIds) || "0".equals(limitTicketIds)) {
			// 获取所有可以用优惠券
			ults = userLimitTicketFacade.getUsableTickits(loginUser.getId(), skuIds, hasLimit4);
		} else {
			// 获取选中优惠券
			ults = userLimitTicketFacade.getUsableTickits(loginUser.getId(), limitTicketIds);
		}
		
		//无优惠券可用
		if(ults.isEmpty()) {
			return ActResult.fail("无优惠券可用");
		}
		
		// 不使用优惠券
		if("0".equals(limitTicketIds)) {
			model.addAttribute("amount", 0);
			model.addAttribute("cash", 0);
			model.addAttribute("ticket", 0);
			model.addAttribute("names", ults.size() + "张可用");
			model.addAttribute("limitTicketIds", "0");
			model.addAttribute("keyInfo", "");
			return ActResult.success(model);
		}
		
		
		Map<Long,UserLimitTicket> mapUlt = new HashMap<Long,UserLimitTicket>();
		StringBuilder keyInfo = new StringBuilder();
		limitTicketIds = "";
		String names = "";

		/**
		 * ***********************************每个sku 按分优惠券**********************************************
		 * */
		BigDecimal allTicket = BigDecimal.ZERO;//内购优惠券
		BigDecimal allCash = BigDecimal.ZERO;//现金优惠券
		for (Long skuId : skuIds) {
			BigDecimal skuCash = mapCashs.get(skuId);
			BigDecimal skuTicket = mapTicket.get(skuId);
			
			for (UserLimitTicket userLimitTicket : ults) {
				if(!NumberUtil.isGreaterZero(skuCash) && !NumberUtil.isGreaterZero(skuTicket)) {
					// sku 全部抵扣
					break;
				}
				
				if(!userLimitTicket.getLimitKey().contains(skuId+"")) {
					// 非sku 限制
					Product p =  mapProduct.get(skuId);
					if(p.getSaleKbn()==null || p.getSaleKbn()!=4 ) {
						// 且非专享商品
						continue;
					}
				}

				if(!NumberUtil.isGreaterZero(userLimitTicket.getCashBalance()) && !NumberUtil.isGreaterZero(userLimitTicket.getTicketBalance())) {
					// 优惠券 全部抵扣
					continue;
				}
				
				if(NumberUtil.isGreaterZero(skuCash) && NumberUtil.isGreaterZero(userLimitTicket.getCashBalance())) {
					// 使用现金抵扣 内购券全免
					BigDecimal cash=null;
					BigDecimal ticket=skuTicket;
					
					if(skuCash.compareTo(userLimitTicket.getCashBalance())>0) {
						// 优惠券完全抵扣
						cash = userLimitTicket.getCashBalance();
						userLimitTicket.setCashBalance(BigDecimal.ZERO);
						skuCash = skuCash.subtract(cash);
					} else {
						// sku完全抵扣
						cash = skuCash;
						skuCash=BigDecimal.ZERO;
						userLimitTicket.setCashBalance(userLimitTicket.getCashBalance().subtract(cash));
					}
					
					// 记录使用优惠券
					if(!mapUlt.containsKey(userLimitTicket.getId())) {
						mapUlt.put(userLimitTicket.getId(), userLimitTicket);
					}
					// 现金抵扣时 sku内购券完全抵扣
					skuTicket = BigDecimal.ZERO;

					allCash=allCash.add(cash);
					allTicket=allTicket.add(ticket);
					keyInfo.append(skuId+"_"+userLimitTicket.getId()+ "_"+cash+"_"+ticket+",");
					
				} else {
					// 抵扣内购券
					if(NumberUtil.isGreaterZero(skuTicket) && NumberUtil.isGreaterZero(userLimitTicket.getTicketBalance())) {

						BigDecimal cash=BigDecimal.ZERO;
						BigDecimal ticket=null;
						if(skuTicket.compareTo(userLimitTicket.getTicketBalance())>0) {
							// 优惠券完全抵扣
							ticket = userLimitTicket.getTicketBalance();
							userLimitTicket.setTicketBalance(BigDecimal.ZERO);
							skuTicket = skuTicket.subtract(ticket);
						} else {
							// sku完全抵扣
							ticket = skuTicket;
							skuTicket=BigDecimal.ZERO;
							userLimitTicket.setTicketBalance(userLimitTicket.getTicketBalance().subtract(ticket));
						}
						// 记录使用优惠券
						if(!mapUlt.containsKey(userLimitTicket.getId())) {
							mapUlt.put(userLimitTicket.getId(), userLimitTicket);
						}
						
						allCash=allCash.add(cash);
						allTicket=allTicket.add(ticket);
						keyInfo.append(skuId+"_"+userLimitTicket.getId()+ "_"+cash+"_"+ticket+",");
					}
				}
			}
		}
		model.addAttribute("amount", allCash.add(allTicket));
		model.addAttribute("cash", allCash);
		model.addAttribute("ticket", allTicket);
		for (UserLimitTicket ult : mapUlt.values()) {
			limitTicketIds += ult.getId()+",";
			if(ult.getTicketType() == 1) {
				if(!names.contains("内购抵扣券")) {
					names += "内购抵扣券,";
				}
			} else if(ult.getTicketType() == 2) {
				if(!names.contains("免费体验券")) {
					names += "免费体验券,";
				}
			} else if(ult.getTicketType() == 3) {
				if(!names.contains("通用现金券")) {
					names += "通用现金券,";
				}
			} else if(ult.getTicketType() == 4) {
				if(!names.contains("专用现金券")) {
					names += "专用现金券,";
				}
			}
		}
		if(names.length()>1) {
			names=names.substring(0, names.length()-1);
		}
		model.addAttribute("names", names);
		if(limitTicketIds.length()>1) {
			limitTicketIds=limitTicketIds.substring(0, limitTicketIds.length()-1);
		}
		model.addAttribute("limitTicketIds", limitTicketIds);
		if(keyInfo.length()>1) {
			keyInfo.delete(keyInfo.length()-1, keyInfo.length());
		}
		model.addAttribute("keyInfo", keyInfo.toString());
		return ActResult.success(model);
	}

	@RequestMapping("/wxEventLink")
	public ModelAndView wxEventLink(String eventKey,String openId,ModelAndView model,HttpServletRequest request, HttpServletResponse response) {

		// 绑定动作改在各绑定功能中处理，绑定入口都从获取openId开始
		UserWeixin user = userWeixinService.getOneModelByOpenId(openId);
		model.addObject("openId", openId);
		logger.debug("eventKey="+eventKey+"&openId="+openId);
		String eventStart=UserShareController.MY_EVENT_TYPE_TICKETL;
		
		Long supplierTicketId=NumberUtil.toLong(eventKey.substring(eventStart.length()));
		Long shareId= -1L;
		String nextUrl = "";

		SupplierLimitTicket slt = supplierLimitTicketService.getById(supplierTicketId);
		if(slt!=null) {
			if(slt.getRegisteFlg().equals("1")) {
				shareId = slt.getSupplierId();
			}
			nextUrl = "http://"+Constant.SYSTEM_DOMAIN+"/limitTicket/recive"+slt.getId()+".user";
		} else {
			// 数据异常 直接跳到首页
			model.setViewName("redirect:"+"http://"+Constant.SYSTEM_DOMAIN+"/index_m.htm");
			return model;
		}
		
		model.addObject("shareId", shareId);
		model.addObject("type", "W");
		
		if(user != null) {
			// 已经绑定的场合跳转至中间页面，根据条件继续跳转
			Cookie cookie_uid = new Cookie("uid",user.getUserId()+"");
			cookie_uid.setPath("/");
			cookie_uid.setMaxAge(24 * 60 * 60 * 1000);
			response.addCookie(cookie_uid);
	
			Cookie cookie_wxOpen = new Cookie("wxOpen","1");
			cookie_wxOpen.setPath("/");
			cookie_wxOpen.setMaxAge(24 * 60 * 60 * 1000);
			response.addCookie(cookie_wxOpen);
			
			UserFactory userFactory = userService.getById(user==null?loginUser.getId():user.getUserId());
			if(userFactory != null) {
				// 防止意外
				Cookie cookie_userSupplierId = new Cookie("userSupplierId",userFactory.getSupplierId()+"");
				cookie_userSupplierId.setPath("/");
				cookie_userSupplierId.setMaxAge(24 * 60 * 60 * 1000);
				response.addCookie(cookie_userSupplierId);
			}
		}
		model.addObject("nextUrl", nextUrl);
		model.addObject("state", "reciveTicket");
		model.setViewName("wxEventLink");
		return model;
	}
	
	/**
	 * 获取优惠券列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = {"/getTicketList.user"})
	@ResponseBody
	public ActResult<Object> getTicketList(HttpServletRequest request, HttpServletResponse response){
		List<UserLimitTicketVo> userLimitTicketList = userLimitTicketService.getByUserId(loginUser.getId());
		for (UserLimitTicketVo userLimitTicket : userLimitTicketList) {
			SupplierLimitTicketSku slts = new SupplierLimitTicketSku();
			slts.setLimitTicketId(userLimitTicket.getLimitTicketId());
			List<SupplierLimitTicketSku> sltsList = supplierLimitTicketSkuService.selectByModel(slts);
			userLimitTicket.setSupplierLimitTicketSkuList(sltsList);
		}		
		return ActResult.success(userLimitTicketList);
	}
	@RequestMapping(value = {"/page.user"})
    public ModelAndView page(HttpServletRequest request, ModelAndView model) {
		model.setViewName("limitTicket/my_stamps");//创建页面
		return model;
	}
	@RequestMapping(value = {"/getOneselfTicketPage.user"})
    public ModelAndView getOneselfTicketPage(String limitTicketIds,String skuIds,HttpServletRequest request, ModelAndView model) { 
    	model.addObject("limitTicketIds",limitTicketIds);
    	model.addObject("skuIds",skuIds);
		model.setViewName("limitTicket/select_stamps_order");//创建页面
		return model;
	}
	/**
	 * 确认订单获取可用优惠券接口
	 * @param limitTicketIds
	 * @return
	 */
	@RequestMapping(value = {"/getBylimitTicketIds.user"})
	@ResponseBody
	public ActResult<Object> getBylimitTicketIds(String limitTicketIds){
		if(StringUtils.isEmpty(limitTicketIds)) {
			ActResult.fail("系统错误");	
		}
		String[] limitTicketIdArr = limitTicketIds.split(",");
		List<UserLimitTicket> list = new ArrayList<UserLimitTicket>();
		for (String limitTicketId : limitTicketIdArr) {
			UserLimitTicket userLimitTicket = userLimitTicketService.getByLimitTicketIdAndUserId(Long.valueOf(limitTicketId), loginUser.getId());
			if(userLimitTicket!=null) {
				SupplierLimitTicketSku slts = new SupplierLimitTicketSku();
				slts.setLimitTicketId(userLimitTicket.getLimitTicketId());
				List<SupplierLimitTicketSku> sltsList = supplierLimitTicketSkuService.selectByModel(slts);
				userLimitTicket.setSupplierLimitTicketSkuList(sltsList);
				list.add(userLimitTicket);
			}
		}
		return ActResult.success(list);
	}
	
	
}
