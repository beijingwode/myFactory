package com.wode.factory.user.web.controller.member;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.UserConstant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.Account;
import com.wode.factory.model.Comments;
import com.wode.factory.model.Currency;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.ExchangeSuborderitem;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.Returnorder;
import com.wode.factory.model.ShippingAddress;
import com.wode.factory.model.Shop;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.SuborderLimitTicket;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserExchangeFavorites;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.service.CommentsService;
import com.wode.factory.user.dao.SupplierDao;
import com.wode.factory.user.facade.CommentsFacade;
import com.wode.factory.user.facade.ExchangeOrdersFacade;
import com.wode.factory.user.facade.OrdersFacade;
import com.wode.factory.user.model.BargainFlowVo;
import com.wode.factory.user.query.ExchangeSuborderQuery;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.service.AccountService;
import com.wode.factory.user.service.BargainFlowVoService;
import com.wode.factory.user.service.CollectionProductService;
import com.wode.factory.user.service.CollectionShopService;
import com.wode.factory.user.service.CurrencyService;
import com.wode.factory.user.service.EntParamCodeService;
import com.wode.factory.user.service.ExchangeSuborderService;
import com.wode.factory.user.service.ExchangeSuborderitemService;
import com.wode.factory.user.service.ExpressComService;
import com.wode.factory.user.service.InvoiceApplyService;
import com.wode.factory.user.service.OrdersService;
import com.wode.factory.user.service.ProductQuestionnaireService;
import com.wode.factory.user.service.ProductService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.QuestionnaireAnswerService;
import com.wode.factory.user.service.RecommendProductService;
import com.wode.factory.user.service.RefundorderService;
import com.wode.factory.user.service.ReturnorderService;
import com.wode.factory.user.service.ShippingAddressService;
import com.wode.factory.user.service.SuborderLimitTicketService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.service.SuborderitemService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.service.UserExchangeFavoritesService;
import com.wode.factory.user.service.UserExchangeTicketService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.CookieUtils;
import com.wode.factory.user.util.SessonRedisUtil;
import com.wode.factory.user.vo.ProductSpecificationsVo;
import com.wode.factory.user.vo.SubOrderItemVo2;
import com.wode.factory.user.vo.SubOrderVo;
import com.wode.factory.user.web.controller.BaseController;
import com.wode.model.CommUser;
import com.wode.search.WodeResult;
import com.wode.search.WodeSearchManager;
import com.wode.search.WodeSearcher;

@Controller
@RequestMapping({ "/member" })
public class MemberController extends BaseController {
	@Qualifier("collectionShopService")
	@Autowired
	private CollectionShopService csService;
	@Autowired
	private OrdersFacade ordersFacade;

	@Qualifier("collectionProductService")
	@Autowired
	private CollectionProductService cpService;

	@Autowired
	private ProductService productService;

	@Qualifier("userService")
	@Autowired
	private UserService userService;

	@Qualifier("ordersService")
	@Autowired
	private OrdersService ordersService;

	@Qualifier("suborderService")
	@Autowired
	private SuborderService suborderService;
	@Autowired
	private ExchangeSuborderService exchangeSuborderService;
	@Autowired
	private ExchangeSuborderitemService exchangeSuborderitemService;

	@Autowired
	private SuborderitemService suborderitemService;
	@Autowired
	private ShippingAddressService sas;

	@Qualifier("accountService")
	@Autowired
	private AccountService accountService;

	@Qualifier("commentsService")
	@Autowired
	private CommentsService commentsService;
	@Autowired
	private CommentsFacade commentsFacade;

	@Qualifier("redis")
	@Autowired
	private RedisUtil redisUtil;

	@Qualifier("returnorderService")
	@Autowired
	private ReturnorderService returnorderService;

	@Autowired
	private WodeSearchManager wsm;

	@Qualifier("recommendProductService")
	@Autowired
	private RecommendProductService recommendService;

	@Autowired
	RefundorderService refundorderService;

	@Autowired
	UserBalanceService userBalanceService;

	@Autowired
	CurrencyService currencyService;

	@Autowired
	private Dao dao;

	@Autowired
	private BargainFlowVoService bargainFlowVoService;
	@Autowired
	private EntParamCodeService entParamCodeService;
	@Autowired
	private ProductQuestionnaireService productQuestionnaireService;
	@Autowired
	private ExpressComService expressComService;
	@Autowired
	private com.wode.factory.service.ProductService pService;
	@Autowired
	private QuestionnaireAnswerService questionnaireAnswerService;

	@Qualifier("productSpecificationsService")
	@Autowired
	private ProductSpecificationsService psService;

	@Autowired
	private InvoiceApplyService invoiceApplyService;

	@Autowired
	private UserExchangeTicketService userExchangeTicketService;

	@Autowired
	private UserExchangeFavoritesService userExchangeFavoritesService;

	@Autowired
	private ExchangeOrdersFacade exOrdersFacade;

	@Autowired
	private SupplierDao supplierDao;
	
	@Autowired
    private SuborderLimitTicketService suborderLimitTicketService;

	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);

	private static Logger logger = LoggerFactory.getLogger(MemberController.class);

	@RequestMapping("/myrights")
	public String myRights(HttpServletRequest request, HttpServletResponse response, Model model, Integer page,
			Integer status) {
		UserFactory user = getUser(request, response);
		PageInfo pageInfo = returnorderService.getReturnOrders(page, user.getId(), status);
		model.addAttribute("page", pageInfo);
		model.addAttribute("status", status);
		model.addAttribute("menu", "rights");
		return "/member/myrights";
	}

	@RequestMapping("/myrights/list")
	public String myRightsList(HttpServletRequest request, HttpServletResponse response, Model model, Integer page,
			Integer status) {
		UserFactory user = getUser(request, response);
		PageInfo pageInfo = suborderService.findReturnableOrders(page, user.getId());
		model.addAttribute("page", pageInfo);
		model.addAttribute("menu", "rights");
		return "/member/myrights2";
	}

	@RequestMapping("/center")
	public String center(HttpServletRequest request, HttpServletResponse response, ModelMap map, Integer page) {
		UserFactory user = getUser(request, response);

		List<UserBalance> list = userBalanceService.findByUser(user.getId());
		for (UserBalance ub : list) {
			Currency currency = currencyService.getById(ub.getCurrencyId());
			if (currency != null) {
				map.put(currency.getName(), ub.getBalance());
			}
		}
		map.put("user", user);

		page = page == null ? 1 : page;
		PageInfo<BargainFlowVo> pageInfo = bargainFlowVoService.findByQuery(user.getId(), 0L, null, page, 8);
		// 根据操作代码取得操作属性
		Map<String, EntParamCode> mapCode = entParamCodeService.getBenefitFlowCode();
		for (BargainFlowVo bargainFlowVo : pageInfo.getList()) {
			String code = bargainFlowVo.getOpCode();
			EntParamCode epc = mapCode.get(code);
			bargainFlowVo.setValue(getFlg(epc.getValue()));
			bargainFlowVo.setIconUrl(getText(code));
		}
		map.put("page", pageInfo);
		map.put("menu", "mybalance");

		return "/member/center";
	}

	// 财富
	@RequestMapping("/mybalance")
	public ModelAndView mybalance(HttpServletRequest request, HttpServletResponse response, SuborderQuery query) {
		UserFactory user = getUser(request, response);
		Account account = accountService.findByUserId(user.getId());
		ModelAndView result = new ModelAndView("/member/mybalance");
		result.addObject("account", account);
		result.addObject("menu", "mybalance");
		return result;
	}

	// 我的评论
	@RequestMapping("/mycomments")
	public ModelAndView mycomments(HttpServletRequest request, HttpServletResponse response, Integer page,
			Boolean state) {
		page = page == null ? 1 : page;
		UserFactory user = getUser(request, response);
		SubOrderItemVo2 vo = new SubOrderItemVo2();
		vo.setUserId(user.getId());
		if (state != null) {
			vo.setCommentFlag(state ? 1 : 0);
		}
		PageInfo<SubOrderItemVo2> pList = suborderitemService.findForComment(vo, page, 10);
		List<Comments> imgList = new ArrayList<Comments>();
		for (SubOrderItemVo2 subOrderItemVo2 : pList.getList()) {
			if (subOrderItemVo2.getCommentFlag() == 1) {
				Comments c = new Comments();
				c.setSubOrderItemId(subOrderItemVo2.getSubOrderItemId());
				c.setUserId(user.getId());

				List<Comments> cs = commentsService.findComments(c, 1, 1);
				imgList.addAll(cs);
				if (cs != null && cs.size() > 0) {
					subOrderItemVo2.setComment(cs.get(0));
					subOrderItemVo2.setCount(0L);
				} else {
					subOrderItemVo2.setCount(1L);
				}

			} else {
				Long qid = productQuestionnaireService.getQuestionnaireId(subOrderItemVo2.getProductId());
				if (NumberUtil.isGreaterZero(qid)) {
					subOrderItemVo2.setCount(qid);
				} else {
					subOrderItemVo2.setCount(0L);
				}
			}
		}
		ModelAndView result = new ModelAndView("/member/mycomments");
		result.addObject("page", pList);
		result.addObject("menu", "mycomments");
		result.addObject("state", state);
		result.addObject("imgList", imgList);
		return result;
	}

	// 我的内购券
	@RequestMapping("/myticket")
	public ModelAndView myTicket(HttpServletRequest request, HttpServletResponse response, Integer page,
			Boolean state) {
		page = page == null ? 1 : page;
		UserFactory user = getUser(request, response);
		PageInfo<BargainFlowVo> pageInfo = bargainFlowVoService.findByQuery(user.getId(), 1L, null, page, 8);
		// 根据操作代码取得操作属性
		Map<String, EntParamCode> mapCode = entParamCodeService.getBenefitFlowCode();
		for (BargainFlowVo bargainFlowVo : pageInfo.getList()) {
			String code = bargainFlowVo.getOpCode();
			EntParamCode epc = mapCode.get(code);
			bargainFlowVo.setValue(getFlg(epc.getValue()));
			bargainFlowVo.setIconUrl(getText(code));
		}
		ModelAndView result = new ModelAndView("/member/myticket");
		result.addObject("page", pageInfo);
		result.addObject("menu", "myticket");
		result.addObject("state", state);
		result.addObject("userId", getUser(request, response).getId());

		return result;
	}

	/**
	 * 添加评论方法
	 * 
	 * @param request
	 * @param response
	 * @param comments
	 * @param questionnareId
	 * @param answers
	 * @return
	 */
	@RequestMapping("/saveComment")
	@ResponseBody
	public String saveComment(HttpServletRequest request, HttpServletResponse response, Comments comments,
			Long questionnareId, String answers) {
		UserFactory user = getUser(request, response);
		String nickName = user.getNickName();
		String phone = user.getPhone();
		if (!StringUtils.isNullOrEmpty(nickName) && !StringUtils.isNullOrEmpty(phone)) {
			if (!nickName.equals(phone)) {
				comments.setAnonymous("0");
			}
		}
		if (comments != null && comments.getSubOrderItemId() != null) {
			comments.setUserId(user.getId());
			comments.setCreatTime(new Date());
			comments.setStatus(1);

			boolean isNormal = true;
			if (NumberUtil.isGreaterZero(questionnareId)) {
				isNormal = false;
			} else {
				comments.setCommentDegree(comments.getStar1());
				comments.setText(HtmlUtils.htmlEscape(comments.getText()));
				isNormal = true;
			}
			boolean cmt = commentsFacade.save(comments, isNormal, questionnareId, answers);
			if (cmt) {
				if (isNormal) {
					try {
						wsm.updateCommentNum(comments.getProductId());
					} catch (Exception e) {
						logger.info("商品评论数更新异常，可能是商品已经下架");
					}
				}
				commentsFacade.prizeForComment(comments, entParamCodeService.getAppFirstPrizeCode().get("002"),
						user.getNickName());
			}
		} else {
			questionnaireAnswerService.answerQuestion(answers, user.getId(), questionnareId);
		}
		return "success";
	}

	@RequestMapping("/ordereviews")
	public String orderReviews(HttpServletRequest request, HttpServletResponse response, String order, Model model,
			Long itemId) {
		if (order == null || order.trim().length() == 0) {
			return "redirect:myorders";
		}
		UserFactory user = getUser(request, response);

		SubOrderItemVo2 vo = new SubOrderItemVo2();
		vo.setUserId(user.getId());
		vo.setSubOrderId(order);
		vo.setCommentFlag(0);
		PageInfo<SubOrderItemVo2> pList = suborderitemService.findForComment(vo, 1, 10);
		List<SubOrderItemVo2> normalComments = new ArrayList<SubOrderItemVo2>();
		List<Long> qids = new ArrayList<Long>();
		List<SubOrderItemVo2> qItems = new ArrayList<SubOrderItemVo2>();

		for (SubOrderItemVo2 subOrderItemVo2 : pList.getList()) {
			if (itemId != null && !itemId.equals(subOrderItemVo2.getSubOrderItemId()))
				continue;
			if (subOrderItemVo2.getSaleKbn() == 5 && NumberUtil.isGreaterZero(subOrderItemVo2.getEmpPrice())) {
				// 试用
				Long qid = productQuestionnaireService.getQuestionnaireId(subOrderItemVo2.getProductId());
				if (NumberUtil.isGreaterZero(qid)) {
					qids.add(qid);
					qItems.add(subOrderItemVo2);
					continue;
				}
			}
			Map<String, Object> score = commentsService.getProductScore(subOrderItemVo2.getProductId());
			subOrderItemVo2.setScore(Double.valueOf(score.get("goodsRatings").toString()));
			subOrderItemVo2.setCount(Long.valueOf(score.get("totalCount").toString()));

			normalComments.add(subOrderItemVo2);
		}

		if (normalComments.size() == 0) {
			if (qids.isEmpty()) {
				return "redirect:orderDetail?subOrderId=" + order;
			} else {
				model.addAttribute("comment", qItems.get(0));
				model.addAttribute("questionnare", productQuestionnaireService.getExById(qids.get(0)));
				return "member/sy_eviews";
			}
		}
		model.addAttribute("list", normalComments);
		return "member/ordereviews";
	}

	@RequestMapping("/questionnaire{qId}")
	public String questionnaire(@PathVariable Long qId, Model model, String itemIds, Integer quantity) {

		model.addAttribute("q", productQuestionnaireService.getExById(qId));
		if (!StringUtils.isNullOrEmpty(itemIds)) {
			ProductSpecificationsVo po = psService.selectProductSpecifications(itemIds);
			if (null != po) {
				// 转换规格string为map
				String skuvalues = po.getItemValues().substring(1, po.getItemValues().length() - 1);
				String[] skuArray = skuvalues.split(",");
				Map<String, String> map = new HashMap<String, String>();
				for (String string : skuArray) {
					String[] stringArray = string.split(":");
					map.put(stringArray[0].replace("\"", ""), stringArray[1].replace("\"", ""));
				}
				model.addAttribute("sku", map);
				model.addAttribute("skuId", po.getId());
			}
		}
		if (null != quantity) {
			model.addAttribute("quantity", quantity);
		}

		return "member/sy_eviews2";
	}

	// 我的订单
	@RequestMapping("/myorders")
	public ModelAndView myorders(HttpServletRequest request, HttpServletResponse response, Integer page,
			Integer status) {
		page = page == null ? 1 : page;
		UserFactory user = getUser(request, response);
		PageInfo pageInfo = this.suborderService.getOrderByUserId(user.getId(), null, status, page);
		ModelAndView result = new ModelAndView("/member/myorders");
		result.addObject("page", pageInfo);
		result.addObject("status", status);
		result.addObject("menu", "myorders");
		result.addObject("user", user);

		return result;
	}

	//
	// @RequestMapping(value = {"/urgedDelivery"})
	// public String urgedDelivery(HttpServletRequest request, String subOrderId) {
	// UserFactory user = getUser(request);
	// SuborderQuery query = new SuborderQuery();
	// query.setUserId(user.getId());
	// query.setSubOrderId(subOrderId);
	// SubOrderVo subOrder = this.suborderService.findOrderDetailById(query);
	// subOrder.setUrgeNumber(subOrder.getUrgeNumber()+1);
	// subOrder.setUrgeTime(new Date());
	// this.suborderService.update(subOrder);
	// return "redirect:/member/orderDetail?subOrderId=" + subOrderId;
	// }

	@RequestMapping(value = { "/paySuccess" })
	public String paySuccess(ModelMap model, HttpServletRequest request, HttpServletResponse response, Long orderId,
			String subOrderId, String payType) {
		UserFactory user = getUser(request, response);
		if ("5".equals(payType)) {
			ExchangeSuborderitem que2 = new ExchangeSuborderitem();
			que2.setOrderId(orderId);
			que2.setSubOrderId(subOrderId);
			List<ExchangeSuborderitem> items = exchangeSuborderitemService.selectByModel(que2);
			items.get(0)
					.setItemValues(items.get(0).getItemValues().replace("\"", "").replace("{", "").replace("}", ""));
			model.addAttribute("item", items.get(0));

			/*ExchangeSuborder subOrder = null;

			if (!StringUtils.isNullOrEmpty(subOrderId)) {
				subOrder = exchangeSuborderService.getById(subOrderId);
			} else {
				ExchangeSuborder q = new ExchangeSuborder();
				q.setOrderId(Long.valueOf(orderId));
				q.setUserId(user.getId());
				subOrder = exchangeSuborderService.selectByModel(q).get(0);
			}

			// 自动添加换领清单
			Integer cnt = userExchangeFavoritesService.autoAddFavorites(user.getId(), subOrder);*/

			WodeSearcher searcher = wsm.getSearcher();
			searcher.setFetchSource(UserExchangeFavoritesService.fecthFields, null);
			searcher.setPageSize(8);
			// 固定2个属性
			String[] fields = new String[] { "品牌", "价格" };
			WodeResult result = searcher.search("saleKbn=2&sort=createDate_1", fields, false, false, false);
			model.addAttribute("productList", result.getHits());
			model.addAttribute("cnt", 0);

			return "/exchange/paySuccess";

		} else {
			Orders order = null;
			if (orderId != null) {
				order = ordersService.findById(user.getId(), orderId);
				invoiceApplyService.addInvoiceByOrderId(orderId);
				if (null == order) {
					return "redirect:/index.html";
				}
			}
			Suborder suborder = null;
			if (subOrderId != null) {
				SuborderQuery query = new SuborderQuery();
				query.setUserId(user.getId());
				query.setSubOrderId(subOrderId);
				suborder = this.suborderService.findOrderDetailById(query);
				invoiceApplyService.addInvoiceBySubOrderId(subOrderId);
				if (null == suborder) {
					return "redirect:/index.html";
				}
			}
			List<Product> productList = recommendService.random();
			model.addAttribute("productList", productList);
			model.addAttribute("order", order);
			model.addAttribute("suborder", suborder);
			return "/member/paySuccess";
		}
	}

	@RequestMapping(value = { "/urgedDelivery/{subOrderId}" })
	@ResponseBody
	public ActResult urgedDelivery(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String subOrderId) {
		UserFactory user = getUser(request, response);
		return this.suborderService.urgedDelivery(user, subOrderId);

	}

	// 订单详情
	@RequestMapping("/orderDetail")
	public ModelAndView orderDetail(HttpServletRequest request, HttpServletResponse response, String subOrderId) {
		UserFactory user = getUser(request, response);
		ModelAndView result = null;
		SuborderQuery query = new SuborderQuery();
		query.setUserId(user.getId());
		query.setSubOrderId(subOrderId);
		SubOrderVo subOrder = this.suborderService.findOrderDetailById(query);

		result = new ModelAndView("/member/orderDetail");
		result.addObject("subOrder", subOrder);
		result.addObject("user", user);
		SuborderLimitTicket suborderLimitTicket =null;
		Boolean flag = false;
		BigDecimal totalBenefitTicket = BigDecimal.ZERO;
		if (subOrder != null) {
			List<Suborderitem> subOrderItems = subOrder.getSubOrderItems();
			for (Suborderitem suborderitem : subOrderItems) {
				if ((suborderitem.getBenefitType() != null) && (suborderitem.getBenefitType() == 3)) {// 换领
					flag = true;
					totalBenefitTicket = totalBenefitTicket.add(suborderitem.getBenefitTicket());
				}
			}
			if(subOrder.getLimitTicketCnt()!=null && subOrder.getLimitTicketCnt()>0) {
				suborderLimitTicket= suborderLimitTicketService.findBySuborderId(subOrder.getSubOrderId());
			}
		}
		result.addObject("suborderLimitTicket", suborderLimitTicket);
		result.addObject("flag", flag);
		result.addObject("totalBenefitTicket", totalBenefitTicket);
		Boolean isAftermarketOrder = false;
		if (subOrder != null) {
			if (subOrder.getStatus() == 3 || subOrder.getStatus() == 5 || subOrder.getStatus() == 11
					|| subOrder.getStatus() == 12 || subOrder.getStatus() == -11 || subOrder.getStatus() == -12
					|| subOrder.getStatus() == 13 || subOrder.getStatus() == 15 || subOrder.getStatus() == 14
					|| subOrder.getStatus() == 16) {
				isAftermarketOrder = true;
			}
		}
		result.addObject("isAftermarketOrder", isAftermarketOrder);
		if (!StringUtils.isEmpty(subOrder.getE_cardInfo())) {
			result.addObject("express", "电子卡券");
			result.addObject("expressCom", "");
			JSONObject jo = JSONObject.parseObject(subOrder.getE_cardInfo());
			if (!StringUtils.isEmpty(jo.get("pw"))) {
				result.addObject("expressNo", jo.getString("pw").trim());
			} else {
				result.addObject("expressNo", "");
			}
			String url = jo.getString("url");

			if (!StringUtils.isEmpty(url)) {
				result.addObject("eCardUrl", url);
			} else {
				result.addObject("eCardUrl", "");
			}

		} else {
			result.addObject("eCardUrl", "");
			if (!StringUtils.isEmpty(subOrder.getExpressType())) {
				ExpressCompany ci = expressComService.getExpressComById(subOrder.getExpressType());
				if (ci != null) {
					result.addObject("express", ci.getName());
					result.addObject("expressCom", ci.getPinYin());
					result.addObject("expressNo", subOrder.getExpressNo());
					result.addObject("searchId", user.getId());
				}
			}
		}
		return result;
	}

	// 跳转至确认订单页面
	@RequestMapping("/toConfirmOrder")
	public ModelAndView toConfirmOrder(HttpServletRequest request, HttpServletResponse response, String subOrderId) {
		UserFactory user = getUser(request, response);
		ModelAndView result = null;
		SuborderQuery query = new SuborderQuery();
		query.setUserId(user.getId());
		query.setSubOrderId(subOrderId);
		SubOrderVo subOrder = this.suborderService.findOrderDetailById(query);
		result = new ModelAndView("/member/confirmOrder");
		result.addObject("subOrder", subOrder);
		if (!StringUtils.isEmpty(subOrder.getExpressType())) {
			ExpressCompany ci = expressComService.getExpressComById(subOrder.getExpressType());
			if (ci != null) {
				result.addObject("expressCom", ci.getPinYin());
				result.addObject("expressNo", subOrder.getExpressNo());
				result.addObject("searchId", user.getId());
			}
		}
		return result;
	}

	// 跳转至退货申请页面
	@RequestMapping("/toReturnOrder")
	public ModelAndView toReturnOrder(HttpServletRequest request, HttpServletResponse response, String subOrderId) {
		UserFactory user = getUser(request, response);
		ModelAndView result = null;
		SuborderQuery query = new SuborderQuery();
		query.setUserId(user.getId());
		query.setSubOrderId(subOrderId);
		SubOrderVo subOrder = this.suborderService.findOrderDetailById(query);
		result = new ModelAndView("/member/newreturnOrder");
		result.addObject("subOrder", subOrder);
		List<Suborderitem> subOrderItems = subOrder.getSubOrderItems();
		Boolean flag = false;
		BigDecimal totalBenefitTicket = new BigDecimal("0");
		if (subOrderItems != null && !subOrderItems.isEmpty()) {
			for (Suborderitem suborderitem : subOrderItems) {
				if ((suborderitem.getBenefitType() != null) && (suborderitem.getBenefitType() == 3)) {// 换领
					flag = true;
					totalBenefitTicket = totalBenefitTicket.add(suborderitem.getBenefitTicket());
				}
			}
		}
		SuborderLimitTicket suborderLimitTicket =null;
		if(subOrder.getLimitTicketCnt()!=null && subOrder.getLimitTicketCnt()>0) {
			suborderLimitTicket= suborderLimitTicketService.findBySuborderId(subOrder.getSubOrderId());
		}
		result.addObject("suborderLimitTicket", suborderLimitTicket);
		result.addObject("flag", flag);
		result.addObject("totalBenefitTicket", totalBenefitTicket);

		result.addObject("trialReturn", this.suborderitemService.getTrialReturnByOrder(subOrderId));

		/*
		 * //查询物流信息 if(!StringUtils.isEmpty(subOrder.getExpressType())){ ExpressCompany
		 * ci = expressComService.getExpressComById(subOrder.getExpressType());
		 * if(ci!=null) { result.addObject("expressCom", ci.getPinYin());
		 * result.addObject("expressNo", subOrder.getExpressNo());
		 * result.addObject("searchId", user.getId()); } }
		 */
		Returnorder returnOrder = null;
		Refundorder refunOrder = null;
		if (null != subOrder.getReturnOrderId()) {
			// returnOrder = returnorderService.getById(subOrder.getReturnOrderId());
			returnOrder = returnorderService.getReturnOrdersById(subOrder.getReturnOrderId());
			result.addObject("returnOrder", returnOrder);
			if (returnOrder.getStatus() == 2) {// 卖家同意退货
				Map<String, ExpressCompany> map_e = expressComService.getExpressCompanys();
				map_e.remove("14660000000000000");
				map_e.remove("14660000000000001");
				result.addObject("expressCompanys", map_e.values());
			}
			if (!StringUtils.isEmpty(returnOrder.getExpressType())) {
				ExpressCompany ci = expressComService.getExpressComById(returnOrder.getExpressType());
				if (ci != null) {
					result.addObject("compInfo", ci);
					result.addObject("expressCom", ci.getPinYin());
					result.addObject("searchId", returnOrder.getUserId());
				}
			}

		}
		if (returnOrder == null && null != subOrder.getRefundOrderId()) {
			// refunOrder = refundorderService.getById(subOrder.getRefundOrderId());
			refunOrder = refundorderService.getRefundordersById(subOrder.getRefundOrderId());
			// List<RefundorderAttachment> attachmentList =
			// refundorderAttachmentService.findByRefundOrderId(subOrder.getRefundOrderId());

			result.addObject("refundOrder", refunOrder);
		}
		result.addObject("user", user);
		return result;
	}

	// 跳转至退货申请页面
	@RequestMapping("/createReturnOrder")
	public String applyReturn(HttpServletRequest request, HttpServletResponse response, Integer type,
			Returnorder returnOrder, Integer goodsStatus, String[] images) {
		if (type == null || type > 1) {
			return "redirect:/index.html";
		}
		UserFactory user = getUser(request, response);
		returnOrder.setUserId(user.getId());
		returnOrder.setStatus(0);
		returnOrder.setCreateTime(new Date());
		returnOrder.setLastTime(TimeUtil.addDay(returnOrder.getCreateTime(), 7));
		// 退货退款/仅退款申请
		Map<String, Object> result = returnorderService.applyReturn(type, returnOrder, goodsStatus,
				Arrays.asList(images));
		// TODO 应显示失败原因
		if (!(boolean) result.get("status")) {
			return "redirect:/index.html";
		}
		return "redirect:/member/toReturnOrder?subOrderId=" + returnOrder.getSubOrderId();
	}

	/*
	 * public String createReturnOrder1(HttpServletRequest request, String
	 * subOrderId,BigDecimal returnPrice,String expressType,String expressNo,String
	 * reason,String note,String [] images) { UserFactory user = getUser(request);
	 * Returnorder returnOrder = new Returnorder();
	 * returnOrder.setSubOrderId(subOrderId); returnOrder.setUserId(user.getId());
	 * returnOrder.setReturnPrice(returnPrice);
	 * returnOrder.setExpressType(expressType); returnOrder.setExpressNo(expressNo);
	 * returnOrder.setReason(reason); returnOrder.setNote(note);
	 * returnOrder.setStatus(0); returnOrder.setCreateTime(new Date());
	 * returnOrder.setLastTime(TimeUtil.addDay(returnOrder.getCreateTime(), 7));
	 * boolean result =
	 * returnorderService.createReturnOrder(returnOrder,null,images); if(!result){
	 * return "redirect:/index.html"; } return
	 * "redirect:/member/toReturnOrder?subOrderId=" + subOrderId; }
	 */
	//
	// @RequestMapping(value={"/createReturnOrder"})
	// @ResponseBody
	// public ActResult<Object> add(Long partNumber, Integer quantity,
	// HttpServletRequest request, HttpServletResponse response){
	// ActResult<Object> result = new ActResult<Object>();
	// return result;
	// }

	// //确认收货
	@RequestMapping("/confirmOrder")
	public String confirmOrder(HttpServletRequest request, HttpServletResponse response, String subOrderId) {
		UserFactory user = getUser(request, response);
		ordersFacade.updateOrderStatus4(user, subOrderId);
		return "redirect:/member/orderDetail?subOrderId=" + subOrderId;
	}

	// 取消订单
	@RequestMapping("/cancelOrder")
	public String cancelOrder(HttpServletRequest request, HttpServletResponse response, String subOrderId,
			String closeReason) {
		UserFactory user = getUser(request, response);
		try {
			//处理ace用户消费
			if(Long.valueOf("201712221700825").equals(user.getSupplierId())) {
				Cookie[] cookies = request.getCookies();
				Map<String, Cookie> map = cn.org.rapid_framework.web.util.CookieUtils.toMap(cookies);
				Cookie cookie = map.get("cookie_aceTicket");
				user.setEmail(cookie.getValue());
			}
			ordersFacade.cancel(user, subOrderId, closeReason,true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/member/orderDetail?subOrderId=" + subOrderId;
	}

	// 超时取消订单
	@RequestMapping("/autoCancelOrder")
	@NoCheckLogin
	@ResponseBody
	public ActResult<String> autoCancelOrder(HttpServletRequest request, Long userId, String subOrderId,
			String closeReason) {
		try {
			UserFactory user = userService.getById(userId);
			if (user == null) {
				return ActResult.fail("");
			}
			return ordersFacade.cancel(user, subOrderId, closeReason,true);
		} catch (Exception e) {
			e.printStackTrace();
			return ActResult.fail("");
		}
	}

	// 删除订单
	@RequestMapping("/deleteOrder")
	public String deleteOrder(HttpServletRequest request, HttpServletResponse response, String subOrderId) {
		UserFactory user = getUser(request, response);

		this.suborderService.delete(user, subOrderId);
		return "redirect:/member/myorders";
	}

	/**
	 * 个人中心店铺收藏列表
	 *
	 * @param request
	 * @param map
	 * @param pages
	 * @return
	 */
	@RequestMapping("/personalStore")
	public String store(HttpServletRequest request, HttpServletResponse response, ModelMap map, Integer pages) {
		if (pages == null || pages == 0)
			pages = 1;
		int size = 5;
		PageInfo<Shop> page = csService.selectAll(getUser(request, response).getId(), pages, size);
		Map<Long, List<Product>> productMap = new HashMap<Long, List<Product>>();
		List<Product> plist = new ArrayList<Product>();
		if (page.getList().size() > 0) {
			List<Shop> list = page.getList();
			for (Shop ss : list) {
				plist = productService.selectByShop(ss.getSupplierId(), ss.getId());
				if (plist.size() > 0) {
					productMap.put(ss.getId(), plist);
				}
			}
		}
		UserFactory user = getUser(request, response);
		map.put("user", user);
		map.put("page", page);
		map.put("productMap", productMap);
		map.put("menu", "personalStore");
		return "/member/personal_onstore";
	}

	/**
	 * 个人中心商品收藏列表
	 *
	 * @param request
	 * @param map
	 * @param pages
	 * @return
	 */
	@RequestMapping("/personalProduct")
	public String product(HttpServletRequest request, HttpServletResponse response, ModelMap map, Integer pages) {
		if (pages == null)
			pages = 1;
		int size = 10;
		PageInfo<Product> page = cpService.selectAll(getUser(request, response).getId(), pages, size);
		List<Product> list = page.getList();
		if (!list.isEmpty() && list.size() > 0) {
			for (Product product : list) {
				List<ProductSpecifications> plist = pService.findByMinpriceCache(product.getId(),
						NumberUtil.toBigDecimal(product.getShowPrice()));
				if (plist.size() > 0) {
					ProductSpecifications suk = plist.get(0);
					product.setShowPrice(suk.getInternalPurchasePrice().toString());
					product.setMaxprice(suk.getMaxFucoin() == null ? BigDecimal.ZERO : suk.getMaxFucoin());
				} else {
					product.setMaxprice(BigDecimal.ZERO);
				}
			}
		}
		map.put("page", page);
		map.put("maxBenefit", entParamCodeService.getBenefitSubsidy());
		map.put("menu", "personalProduct");
		return "/member/personal_onshopping";
	}

	/**
	 * 个人中心个人信息
	 *
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/personalInformation")
	public String information(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		UserFactory user = getRuntimeUser(request, response);
		map.put("user", user);
		map.put("menu", "personalInformation");
		return "/member/personal_information";
	}

	/**
	 * 个人中心安全设置
	 *
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/security")
	public String security(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		UserFactory user = getUser(request, response);
		String phone = "";
		if (!StringUtils.isNullOrEmpty(user.getPhone()))
			phone = StringUtils.subString(user.getPhone(), 3, 7, " **** ");
		map.put("phone", phone);
		map.put("user", user);
		map.put("menu", "security");
		return "/member/personal_security";
	}

	/**
	 * 个人中心密码修改第一步
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/securityPassword")
	public String securityPassword(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			String type) {
		UserFactory user = getUser(request, response);
		String returnUrl = null;
		String phoneOrEmail = null;
		if (StringUtils.isNullOrEmpty(type))
			returnUrl = "/member/personal_security_password";
		else if (type.equals("phone")) {
			returnUrl = "/member/personal_security_phone1";
			phoneOrEmail = StringUtils.subString(user.getPhone(), 3, 7, " **** ");
		} else if (type.equals("email")) {
			returnUrl = "/member/personal_security_email";
			phoneOrEmail = StringUtils.subString(user.getEmail(), 3, 7, " **** ");
		}
		map.put("user", user);
		map.put("phoneOrEmail", phoneOrEmail);
		return returnUrl;
	}

	/**
	 * 个人中心密码修改第二步(验证短信码)
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkByPhone")
	@ResponseBody
	public ActResult<String> securityPasswordByPhone(HttpServletRequest request, HttpServletResponse response,
			String code) {
		UserFactory user = getUser(request, response);
		ActResult<String> re = new ActResult<String>();
		String str = redisUtil.getData(UserConstant.PRODUCT_PHONECODE + "_" + user.getPhone());
		if (!StringUtils.isEquals(code, str)) {
			re.setSuccess(false);
			re.setMsg("请输入正确的验证码");
		}
		return re;
	}

	/**
	 * 个人中心密码修改第三步(验证短信码后跳转页面)
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/modifyByPhone")
	public String modifyPasswordByPhone(String code, ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		UserFactory user = getUser(request, response);
		map.put("code", code);
		map.put("userName", user.getPhone());
		return "/member/personal_security_phone2";
	}

	/**
	 * 个人中心密码修改第二步（发送邮件成功之后跳转）
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/sendEmailSuccessForPassword")
	public String sendEmailSuccess(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		UserFactory user = getUser(request, response);
		map.put("user", user);
		return "/member/personal_security_sendemail_success";
	}

	/**
	 * 个人中心密码修改成功跳转
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/modifySuccess")
	public String modifySuccess(HttpServletRequest request) {
		return "/member/personal_security_password_success";
	}

	/**
	 * 个人中心邮箱绑定
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/bindEmail")
	public String userBindEmail(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		UserFactory user = getUser(request, response);
		map.put("type", "bind");
		map.put("user", user);
		return "/personal_security_bind_email";
	}

	/**
	 * 个人中心手机绑定
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/bindPhone")
	public String userBindPhone(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		map.put("uid", getUser(request, response).getId());
		return "/member/personal_bind_phone";
	}

	/**
	 * 个人中心手机成功绑定
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/bindSuccess")
	public String userBindPhoneSuccess(HttpServletRequest request, HttpServletResponse response, String type,
			Long uid) {
		UserFactory user = getUser(request, response);
		if (user.getId().equals(uid)) {
			ActResult<CommUser> art = us.getUserById(user.getId());

			if (art.isSuccess()) {
				if ("bindEmail".equals(type)) {
					if (!StringUtils.isEquals(art.getData().getUserEmail(), user.getEmail())) {
						user.setEmail(art.getData().getUserEmail());
						userService.saveOrUpdate(user);
						EnterpriseUser emp = userService.getEmpById(user.getId());
						if (emp != null) {
							emp.setEmail(art.getData().getUserEmail());
							userService.updEmp(emp);
						}
						SessonRedisUtil.setLoginId(CookieUtils.getUUID(request, response), user, redisUtil);
					}
				} else {
					if (!StringUtils.isEquals(art.getData().getUserPhone(), user.getPhone())) {
						user.setPhone(art.getData().getUserPhone());
						userService.saveOrUpdate(user);
						EnterpriseUser emp = userService.getEmpById(user.getId());
						if (emp != null) {
							emp.setPhone(art.getData().getUserPhone());
							userService.updEmp(emp);
						}
						SessonRedisUtil.setLoginId(CookieUtils.getUUID(request, response), user, redisUtil);
					}
				}
			}
		}

		if ("bindEmail".equals(type)) {
			return "/member/personal_security__bind_email_success";
		} else {
			return "/member/personal_bind_phone_success";
		}
	}

	/**
	 * 个人中心手机更改绑定发送验证码之后
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkPhoneSuccess")
	public String checkBindPhoneSuccess(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		UserFactory user = getUser(request, response);
		map.put("uid", user.getId());
		map.put("phone", user.getPhone());

		return "/member/personal_change_bind_phone";
	}

	/**
	 * 个人中心收货地址管理
	 *
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/userAddress")
	public String receiverAddress(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		UserFactory user = getUser(request, response);
		List<ShippingAddress> list = null;
		list = sas.findByUserId(user.getId());
		map.put("saList", list);
		map.put("user", user);
		map.put("menu", "userAddress");
		return "/member/personal_receiveraddress";
	}

	private String getFlg(String value) {
		String flg = "-";
		if ("1".equals(value)) {
			flg = "+";
		}
		return flg;
	}

	private String getText(String code) {
		String flg = "收";
		if ("203".equals(code)) {
			flg = "买";
		} else if ("202".equals(code) || "222".equals(code)) {
			flg = "退";
		} else if ("205".equals(code) || "206".equals(code) || "221".equals(code) || "220".equals(code)) {
			flg = "赠";
		} else if ("204".equals(code) || "207".equals(code)) {
			flg = "返";
		} else if ("216".equals(code)) {
			flg = "发";
		} else if ("217".equals(code)) {
			flg = "收";
		} else if ("290".equals(code)) {
			flg = "派";
		} else if ("292".equals(code) || "294".equals(code) || "296".equals(code)) {
			flg = "奖";
		} else if ("200".equals(code)) {
			flg = "储";
		} else if ("298".equals(code)) {
			flg = "邀";
		}
		return flg;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sendReturnOrder")
	@ResponseBody
	public ActResult<String> sendReturnOrder(HttpServletRequest request, HttpServletResponse response,
			Long returnOrderId, String expressType, String expressNo) {
		ActResult<String> ac = new ActResult<String>();
		Returnorder returnorder = returnorderService.getById(returnOrderId);
		if (returnorder != null) {
			if (!StringUtils.isNullOrEmpty(expressType) && !StringUtils.isNullOrEmpty(expressNo)) {
				returnorder.setExpressType(expressType);
				returnorder.setExpressNo(expressNo);
				returnorder.setGoodsStatus(0);
				returnorder.setStatus(4);
				returnorderService.update(returnorder);
				ac.successSetMsg("退货成功");
			} else {
				ac.fail("缺少物流信息");
			}
		} else {
			ac.fail("查询不到此退货单");
		}
		return ac;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateReturnOrder")
	public String updateReturnOrder(HttpServletRequest request, HttpServletResponse response, Integer type,
			Returnorder returnOrder, Long refundOrderId, Integer goodsStatus, String[] images) {
		if (type == null || type > 1) {
			return "redirect:/index.html";
		}
		UserFactory user = getUser(request, response);
		returnOrder.setUserId(user.getId());
		// returnOrder.setStatus(0);
		returnOrder.setCreateTime(new Date());
		returnOrder.setLastTime(TimeUtil.addDay(returnOrder.getCreateTime(), 7));
		// 退货退款/仅退款申请
		if (images == null) {
			images = new String[3];
		}
		Map<String, Object> result = returnorderService.updateReturn(type, returnOrder, refundOrderId, goodsStatus,
				Arrays.asList(images));
		// TODO 应显示失败原因
		if (!(boolean) result.get("status")) {
			return "redirect:/index.html";
		}
		return "redirect:/member/toReturnOrder?subOrderId=" + returnOrder.getSubOrderId();
	}

	// 已选商品
	@RequestMapping("/getWishOrder")
	@ResponseBody
	public ActResult getWishOrder(HttpServletRequest request, HttpServletResponse response, Integer page,
			Integer pageSize) {
		if (page == null || page == 0) {
			page = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 6;
		}
		ActResult result = new ActResult();
		UserFactory user = getUser(request, response);
		UserExchangeFavorites userExchangeFavorites = new UserExchangeFavorites();
		userExchangeFavorites.setUserId(user.getId());
		// 根据用户id获取心愿单
		List<UserExchangeFavorites> selectByModel = userExchangeFavoritesService.selectByModel(userExchangeFavorites);
		Pager pager = dao.createPager(page, pageSize);
		pager.setRecordCount(selectByModel.size());
		int toIndex = page * pageSize;
		if (toIndex > selectByModel.size()) {
			toIndex = selectByModel.size();
		}
		int fromIndex = (page-1)*pageSize;
		while(fromIndex >= toIndex && fromIndex>0) {
			page--;
			fromIndex = (page-1)*pageSize;
		}
		result.setData(new QueryResult(selectByModel.subList(fromIndex, toIndex), pager));
		result.setSuccess(true);
		return result;
	}

	// 可选商品
	@RequestMapping("/getselectableOrder")
	@ResponseBody
	public ActResult getselectableOrder(HttpServletRequest request, HttpServletResponse response, Integer page,
			Integer pageSize) {
		if (page == null || page == 0) {
			page = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 6;
		}
		ActResult result = new ActResult();
		UserFactory user = getUser(request, response);
		result.setData(userExchangeFavoritesService.getSelectable(page, pageSize, user.getId()));
		result.setSuccess(true);
		return result;
	}

	// 我的换领
	@RequestMapping("/myrenewal")
	public ModelAndView myrenewal(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = new ModelAndView("/member/hlwishlist");
		UserFactory user = getUser(request, response);
		// 计算换领总额
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal balance = BigDecimal.ZERO;
		List<UserExchangeTicket> ets = getExchangeTicket(user.getId());
		for (UserExchangeTicket userExchangeTicket : ets) {
			total = total.add(userExchangeTicket.getEmpAvgAmount());
			balance = balance.add(userExchangeTicket.getUsedAmount()); // 暂时记录已使用
		}
		result.addObject("usable", total.subtract(balance));
		result.addObject("total", total);
		result.addObject("menu", "myhlOrder");
		return result;
	}

	@RequestMapping(value = { "/getExchageTicket" })
	@ResponseBody
	public ActResult getExchageTicket(HttpServletRequest request, HttpServletResponse response) {
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal balance = BigDecimal.ZERO;
		UserFactory user = getUser(request, response);
		List<UserExchangeTicket> ets = getExchangeTicket(user.getId());
		for (UserExchangeTicket userExchangeTicket : ets) {
			total = total.add(userExchangeTicket.getEmpAvgAmount());
			balance = balance.add(userExchangeTicket.getUsedAmount()); // 暂时记录已使用
		}
		Map<String, Object> rtnDate = new HashMap<String, Object>();
		rtnDate.put("total", total);
		rtnDate.put("balance", total.subtract(balance));

		// 查询购物团列表
		return ActResult.success(rtnDate);
	}

	// 移除已选商品
	@RequestMapping("/delectSelected")
	@ResponseBody
	public ActResult delectSelected(Long id) {
		userExchangeFavoritesService.removeById(id);
		return ActResult.success(true);
	}

	// 添加可购商品
	@RequestMapping("/selectedProduct")
	@ResponseBody
	public ActResult selectedProduct(HttpServletRequest request, HttpServletResponse response, Long productId) {
		UserFactory user = getUser(request, response);
		return ActResult.success(userExchangeFavoritesService.addFavorites(user.getId(), productId));
	}

	// 删除订单
	@RequestMapping("/deleteExOrder")
	public String deleteExOrder(HttpServletRequest request, HttpServletResponse response, String subOrderId) {
		UserFactory user = getUser(request, response);
		exchangeSuborderService.delete(user, subOrderId);
		return "redirect:/member/myhlOrder";
	}

	// 换领清单
	@RequestMapping("/myhlOrder")
	public ModelAndView myhlOrder(HttpServletRequest request, HttpServletResponse response, Integer status,
			Integer exchangeStatus, Integer page, Integer pageSize) {
		ModelAndView result = new ModelAndView("/member/my_hl");
		UserFactory user = getUser(request, response);
		if (page == null || page == 0) {
			page = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 10;
		}
		// 计算换领总额
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal balance = BigDecimal.ZERO;
		List<UserExchangeTicket> ets = getExchangeTicket(user.getId());
		for (UserExchangeTicket userExchangeTicket : ets) {
			total = total.add(userExchangeTicket.getEmpAvgAmount());
			balance = balance.add(userExchangeTicket.getUsedAmount()); // 暂时记录已使用
		}
		result.addObject("usable", total.subtract(balance));
		result.addObject("total", total);
		result.addObject("menu", "myhlOrder");
		ExchangeSuborderQuery query = new ExchangeSuborderQuery();
		query.setUserId(user.getId());
		query.setStatus(status);
		query.setPageNumber(page);
		query.setPageSize(pageSize);
		query.setStatus(status);
		query.setExchangeStatus(exchangeStatus);
		// 查询不同状态订单列表
		PageInfo<ExchangeSuborder> findPage = exchangeSuborderService.findPage(query);
		result.addObject("page", findPage);
		result.addObject("exchangeStatus", exchangeStatus);
		result.addObject("status", status);
		return result;
	}

	// 取消匹配
	@RequestMapping("/cancelExOrder")
	@ResponseBody
	public ActResult<String> cancelExOrder(HttpServletRequest request, HttpServletResponse response, String subOrderId,
			String closeReason) {
		UserFactory user = getUser(request, response);
		ActResult<String> ac = new ActResult<String>();
		try {
			ac = exOrdersFacade.cancel(user, subOrderId, closeReason, -1,true);
		} catch (Exception e) {
			e.printStackTrace();
			return ActResult.fail(ac.getMsg());
		}
		return ac;
	}

	private List<UserExchangeTicket> getExchangeTicket(Long userId) {
		return userExchangeTicketService.usingTicket(userId);
	}

	// 调剂详情页
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findAdJust")
	@ResponseBody
	public ModelAndView findAdJust(HttpServletRequest request, HttpServletResponse response, Long batchId) {
		ModelAndView result = new ModelAndView();
		result.setViewName("member/adjust_details");
		UserFactory user = getUser(request, response);
		// 计算换领总额
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal balance = BigDecimal.ZERO;
		List<UserExchangeTicket> ets = getExchangeTicket(user.getId());
		for (UserExchangeTicket userExchangeTicket : ets) {
			total = total.add(userExchangeTicket.getEmpAvgAmount());
			balance = balance.add(userExchangeTicket.getUsedAmount()); // 暂时记录已使用
		}
		result.addObject("usable", total.subtract(balance));
		result.addObject("total", total);
		ExchangeSuborder q = new ExchangeSuborder();
		q.setBatchId(batchId);
		q.setUserId(user.getId());
		List<ExchangeSuborder> exSubOrders = exchangeSuborderService.selectByModel(q);
		for (ExchangeSuborder exchangeSuborder : exSubOrders) {
			Supplier s = supplierDao.getById(exchangeSuborder.getSupplierId());
			if (s != null) {
				exchangeSuborder.setSupplierName(s.getComName());
			} else {
				exchangeSuborder.setSupplierName("");
			}
			exchangeSuborderService.selectItems4Set(exchangeSuborder);
		}
		com.github.pagehelper.PageInfo pageOrder = suborderService.getOrderByUserId(user.getId(), batchId, null, 1,
				100);

		result.addObject("exSubOrders", exSubOrders);
		result.addObject("subOrders", pageOrder.getList());
		result.addObject("menu", "myhlOrder");
		// 查询不同状态订单列表
		return result;
	}
}
