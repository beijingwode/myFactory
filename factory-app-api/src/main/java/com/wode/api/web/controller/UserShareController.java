package com.wode.api.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.wode.api.facade.LoginFacade;
import com.wode.api.util.IPUtils;
import com.wode.api.util.MatrixToImageWriter;
import com.wode.common.constant.UserConstant;
import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.GroupBuy;
import com.wode.factory.model.GroupBuyProduct;
import com.wode.factory.model.GroupBuyUser;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.GroupSuborderitem;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.model.Shop;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.model.UserBlackEnvelope;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserIm;
import com.wode.factory.model.UserImGroup;
import com.wode.factory.model.UserShare;
import com.wode.factory.model.UserShareItem;
import com.wode.factory.model.UserShareTicket;
import com.wode.factory.model.UserWeixin;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.SmsService;
import com.wode.factory.outside.service.WxOpenService;
import com.wode.factory.service.GroupBuyProductService;
import com.wode.factory.service.GroupBuyService;
import com.wode.factory.service.GroupBuyUserService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsImageService;
import com.wode.factory.user.dao.SupplierDao;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.CartItem;
import com.wode.factory.user.model.SupplierTemp;
import com.wode.factory.user.model.UserContacts;
import com.wode.factory.user.model.UserShareAutoBuy;
import com.wode.factory.user.service.EntParamCodeService;
import com.wode.factory.user.service.GroupOrdersService;
import com.wode.factory.user.service.GroupSuborderItemService;
import com.wode.factory.user.service.PageDataService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.ShopService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.service.SuborderitemService;
import com.wode.factory.user.service.SupplierExchangeProductService;
import com.wode.factory.user.service.SupplierTempService;
import com.wode.factory.user.service.UserBlackEnvelopeService;
import com.wode.factory.user.service.UserContactsService;
import com.wode.factory.user.service.UserImGroupService;
import com.wode.factory.user.service.UserImService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.service.UserShareAutoBuyService;
import com.wode.factory.user.service.UserShareItemService;
import com.wode.factory.user.service.UserShareService;
import com.wode.factory.user.service.UserShareTicketService;
import com.wode.factory.user.service.UserWeixinService;
import com.wode.factory.user.util.AppOrWxPushUtil;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.EasemobIMUtils;
import com.wode.factory.user.vo.SkuVo;
import com.wode.factory.user.vo.UserShareProductItemVo;
import com.wode.factory.user.vo.UserShareVo;
import com.wode.factory.vo.GroupBuyUserVo;
import com.wode.factory.vo.GroupBuyVo;
import com.wode.factory.vo.ProductVo;
import com.wode.model.CommUser;

/**
 * 2015-6-17
 *
 * @author 谷子夜
 */
@Controller
@RequestMapping("/userShare")
@ResponseBody
@SuppressWarnings("unchecked")
public class UserShareController extends BaseController {
	@Autowired
	private UserService userService;

	@Autowired
	UserShareService userShareService;

	@Autowired
	private PageDataService pageDataService;

	@Autowired
	UserShareItemService userShareItemService;
	@Autowired
	private UserBlackEnvelopeService userBlackEnvelopeService;
	@Autowired
	private UserWeixinService userWeixinService;
	@Autowired
	private UserImService userImService;
	@Autowired
	private UserImGroupService userImGroupService;
	@Autowired
	private GroupBuyUserService groupBuyUserService;
	@Autowired
	RedisUtil redisUtil;

	@Autowired
	DBUtils dbUtils;

	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private ProductSpecificationsImageService productSpecificationsImageService;

	@Autowired
	private ProductService productService;
	@Qualifier("productSpecificationsService")
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
	@Autowired
	private LoginFacade loginFacade;
	@Autowired
	private ShopService shopService;
	@Autowired
	private UserContactsService userContactsService;
	
	@Autowired
	private GroupOrdersService groupOrdersService;
	@Autowired
	private GroupBuyService groupBuyService;
	@Autowired
	private GroupSuborderItemService groupSuborderItemService;
	@Autowired
	private GroupBuyProductService groupBuyProductService;
	@Autowired
	private EntParamCodeService entParamCodeService;
	@Autowired
	private SuborderService suborderService;
	@Autowired
	private SuborderitemService suborderitemService;
	@Autowired
	private SupplierTempService supplierTempService;
	@Autowired
	private UserShareAutoBuyService userShareAutoBuyService;
	@Autowired
	private UserShareTicketService userShareTicketService;
	@Autowired
	private SupplierExchangeProductService supplierExchangeProductService;
	private String qiyeApiUrl = Constant.QIYE_API_URL;

    public final static String MY_EVENT_TYPE_COMPANY = "company";
    public final static String MY_EVENT_TYPE_SHARE = "share";
    public final static String MY_EVENT_TYPE_AUTO_BUYC = "autoBuyC";
    public final static String MY_EVENT_TYPE_AUTO_BUYU = "autoBuyU";
    public final static String MY_EVENT_TYPE_TICKETE = "ticketE";
    public final static String MY_EVENT_TYPE_TICKETL = "ticketL";
    
	private static Logger logger = LoggerFactory.getLogger(UserShareController.class);
	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
	static SmsService sms = ServiceFactory.getSmsService(Constant.OUTSIDE_SERVICE_URL);
	static WxOpenService wxOpen = ServiceFactory.getWxOpenService(Constant.OUTSIDE_SERVICE_URL);
	/**
	 * 13.1	员工商品分享（邀请亲友用）
	 * 
	 * @param type	
	 * @param productId
	 * @param skuId
	 * @return
	 */
	@RequestMapping("share.user")
	@ResponseBody
	public ActResult<UserShare> share(Integer type,Long productId,Long skuId) {
		if(type==null) type=2;
		
		UserShare us =new UserShare();
		us.setId(dbUtils.CreateID());
		us.setUserId(loginUser.getId());
		us.setUserNick(loginUser.getNickName());
		us.setUserAvatar(loginUser.getAvatar());
		us.setUserType(loginUser.getEmployeeType());
		us.setShareType(0);
		us.setShareTitle("福利分享");
		//us.setShareMsg1(loginUser.getNickName());

		UserFactory uf = userService.getById(loginUser.getId());
		if (!StringUtils.isEmpty(uf.getSupplierId())) {
			Supplier sup = supplierDao.getById(uf.getSupplierId());
			us.setShareMsg1(sup.getComName());
		} else {
			us.setShareMsg1("我的福利员工账户");			
		}
		us.setShareMsg2("【我的福利】<span>"+loginUser.getNickName()+"</span>为您分享福利");	
		List<UserShareItem> items = makeItems(type, productId, skuId, us);
		
		if(items.size()==0) return ActResult.fail("分享内容为空，请添加商品后重试。");
		us.setShareItemCnt(items.size());
		if(us.getShareItemCnt()==1) {
			us.setShareMsg3("我发现一个超值的商品，快来看看吧，晚了就下架啦。");
		} else {
			us.setShareMsg3("这些商品真的好划算啊，不管你怎么看，反正我是这么认为的。别告诉别人。行吗？");
		}
		us.setShareFooter1("长按识别图中二维码");
		us.setShareFooter2("成为 " + loginUser.getNickName() + " 的亲友");
		us.setShareFooter3("让朋友省钱才是真朋友");
		us.setCreateTime(new Date());
		
		us.setShareUrl("http://"+Constant.SYSTEM_DOMAIN+"/userShare/page"+us.getId());
		us.setNextAction("http://"+Constant.SYSTEM_DOMAIN+"/userShare/toBind"+us.getId());
		userShareService.save(us);
		for (UserShareItem userShareItem : items) {
			userShareItemService.save(userShareItem);
		}
		return ActResult.success(us);
	}
	/**
	 * 团分享
	 * @param model
	 * @param type
	 * @param groupId
	 * @return
	 */
	@RequestMapping("groupShare.user")
	@ResponseBody
	public ActResult<UserShare> groupShare(Model model,Integer type,Long groupId) {
		if(type==null) type=6;// 6 团分享
		UserShare us =new UserShare();
		us.setId(dbUtils.CreateID());
		us.setUserId(loginUser.getId());
		us.setUserNick(loginUser.getNickName());
		us.setUserAvatar(loginUser.getAvatar());
		us.setUserType(loginUser.getEmployeeType());
		us.setShareType(6);
		us.setShareTitle("“一起购”邀请");
		us.setGroupId(groupId);
		GroupBuyVo groupBuyVo = groupBuyService.getById(groupId);
		if (!StringUtils.isEmpty(groupBuyVo.getShopName())) {
			us.setShareMsg1("我刚刚在<br /><span>【"+groupBuyVo.getShopName()+"】</span><br />中发起了一个购物团");
		}
		us.setShareMsg3("本来就出厂价，拼团还能省更多，快来跟我一起购吧~");	
		GroupOrders groupOrders = groupOrdersService.getByGroupIdAndUserIdObj(groupId,loginUser.getId());
		List<GroupSuborderitem> groupSuborderitemList  = null;
		GroupSuborderitem groupSuborderitem = null;
		if(groupOrders!=null) {
			groupSuborderitemList = groupSuborderItemService.findByOrderId(groupOrders.getOrderId());
		}
		if(groupSuborderitemList!=null) {
			groupSuborderitem = groupSuborderitemList.get(0);
		}
		if(groupSuborderitem==null) {
			//groupSuborderitem = groupBuyProductService.findByGroupId(groupId);
			List<GroupBuyProduct> groupBuyProductList = groupBuyProductService.findByGroupId(groupId);
			if(groupBuyProductList!=null && groupBuyProductList.size()>0) {
				groupSuborderitem = new GroupSuborderitem();
				groupSuborderitem.setProductId(groupBuyProductList.get(0).getProductId());
				//groupSuborderitem.setPrice(groupBuyProductList.get(0).getMarketPrice());
				groupSuborderitem.setSkuId(groupBuyProductList.get(0).getSkuId());
				groupSuborderitem.setPartNumber(groupBuyProductList.get(0).getSkuId()+"");;
			}
		}
		List<UserShareItem> items = makeItems(type, groupSuborderitem.getProductId(), groupSuborderitem.getSkuId(), us);
		if(items.size()==0) return ActResult.fail("分享内容为空，请添加商品后重试。");
		us.setShareItemCnt(items.size());
		if(us.getShareItemCnt()==1) {
			us.setShareMsg2("我发现一个超值的商品，快来看看吧，晚了就下架啦。");
		} else {
			us.setShareMsg2("这些商品真的好划算啊，不管你怎么看，反正我是这么认为的。别告诉别人。行吗？");
		}
		us.setShareFooter1("长按识别图中二维码,跟我一起购");
		us.setShareFooter2("一起省钱才是真朋友");
		us.setCreateTime(new Date());
		
		us.setShareUrl("http://"+Constant.SYSTEM_DOMAIN+"/userShare/page"+us.getId());
		userShareService.save(us);
		for (UserShareItem userShareItem : items) {
			userShareItemService.save(userShareItem);
		}
		us.setNextAction(wxOpen.getQRLink("share"+us.getId()));
		userShareService.update(us);
		Integer buyProductNum = groupSuborderItemService.getSuborderItemSum(groupId.toString(), groupSuborderitem.getProductId(), groupSuborderitem.getSkuId().toString());
		if(buyProductNum==null) buyProductNum = 0;
		model.addAttribute("buyProductNum", buyProductNum);//商品团内已购数量
		//model.addAttribute("groupSuborderitem", groupSuborderitem);
		model.addAttribute("us", us);
		return ActResult.success(model);
	}
	
	/**
	 * 订单分享
	 * @param model
	 * @param type
	 * @param groupId
	 * @return
	 */
	@RequestMapping("orderShare.user")
	@ResponseBody
	public ActResult<UserShare> orderShare(Model model, Integer type, Long orderId) {
		if (type == null)
			type = 7;// 7订单分享
		UserShare us = new UserShare();
		us.setId(dbUtils.CreateID());
		us.setUserId(loginUser.getId());
		us.setUserNick(loginUser.getNickName());
		us.setUserAvatar(loginUser.getAvatar());
		us.setUserType(loginUser.getEmployeeType());
		us.setShareType(7);
		us.setShareTitle("我的福利");
		us.setGroupId(orderId);
		us.setShareMsg1("我发现一个超值的商品，快来看看吧，晚了就下架啦。");
		List<Suborder> suborderList = suborderService.findByOrderId(orderId);
		if(suborderList.size()<=0) {
			logger.error("订单异常,订单Id："+orderId);
			return ActResult.fail("未获取订单");
		}
		List<Suborderitem> suborderitemList = suborderitemService.findBySubOrderId(suborderList.get(0).getSubOrderId());
		List<UserShareItem> items = makeItems(type, suborderitemList.get(0).getProductId(),
				suborderitemList.get(0).getSkuId(), us);
		if (items.size() == 0)
			return ActResult.fail("分享内容为空，请添加商品后重试。");
		us.setShareItemCnt(items.size());
		if (us.getShareItemCnt() == 1) {
			us.setShareMsg2("我发现一个超值的商品，快来看看吧，晚了就下架啦。");
		} else {
			us.setShareMsg2("这些商品真的好划算啊，不管你怎么看，反正我是这么认为的。别告诉别人。行吗？");
		}
		us.setShareMsg3("好友"+loginUser.getNickName()+"邀请您一起享受海量员工内购价。");
		us.setShareFooter1("扫码绑定亲友！");
		us.setShareFooter2("一起省钱才是真朋友！");
		us.setShareFooter3("真内购，真便宜！还不快来一起省！");
		us.setCreateTime(new Date());

		us.setShareUrl("http://"+Constant.SYSTEM_DOMAIN+"/userShare/page" + us.getId());
		userShareService.save(us);
		for (UserShareItem userShareItem : items) {
			userShareItemService.save(userShareItem);
		}
		us.setNextAction("http://"+Constant.SYSTEM_DOMAIN+"/userShare/toBind" + us.getId());
		userShareService.update(us);
		//model.addAttribute("suborderitemList", suborderitemList);
		//model.addAttribute("us", us);
		return ActResult.success(us);
	}
	/**
	 * 好友邀请
	 * @param model
	 * @param type
	 * @return
	 */
	@RequestMapping("invitationMarketing.user")
	@ResponseBody
	public ActResult<UserShare> invitationMarketing(Model model,Integer type) {
		if(type==null) type=8;// 8 好友邀请
		UserShare us = new UserShare();
		us.setUserId(loginUser.getId());
		us.setShareType(8);
		List<UserShare> userShareList = userShareService.selectByModel(us);
		if(userShareList!=null&&userShareList.size()>0){
			us = userShareList.get(0);
			if((us.getWxTempQrUrl()==null||"".equals(us.getWxTempQrUrl()))
					|| (us.getWxTempLimitEnd()==null || us.getWxTempLimitEnd().getTime()<new Date().getTime())){
				
				String qrUrl = wxOpen.getQRLink("share"+us.getId(), WxOpenService.MAX_EXPIRE_SECONDS);
				Calendar now = Calendar.getInstance();
				now.add(Calendar.DAY_OF_MONTH, 29);
				us.setWxTempQrUrl(qrUrl);
				us.setWxTempLimitEnd(now.getTime());
				userShareService.update(us);
			}
		}else{
			us = null;
		}
		if(us==null){
			us = new UserShare();
			us.setId(dbUtils.CreateID());
			us.setUserId(loginUser.getId());
			us.setUserNick(loginUser.getNickName());
			us.setUserAvatar(loginUser.getAvatar());
			us.setUserType(loginUser.getEmployeeType());
			us.setShareType(8);
			us.setShareTitle("我的福利");
			if (!StringUtils.isEmpty(loginUser.getNickName())) {
				us.setShareMsg1("<span>"+loginUser.getNickName()+"</span>&nbsp;&nbsp;邀请您一起享受海量员工内购价~");
			}
			us.setShareMsg3("好友"+loginUser.getNickName()+"邀请您一起享受海量员工内购价。");
			us.setShareFooter3("朋友一生一起走 ，一起省钱一起嗨！");
			us.setCreateTime(new Date());
			us.setShareUrl("http://"+Constant.SYSTEM_DOMAIN+"/userShare/page"+us.getId());
			us.setNextAction("http://"+Constant.SYSTEM_DOMAIN+"/userShare/toBind"+us.getId());
			String qrUrl = wxOpen.getQRLink("share"+us.getId(), WxOpenService.MAX_EXPIRE_SECONDS);
			Calendar now = Calendar.getInstance();
			now.add(Calendar.DAY_OF_MONTH, 29);
			us.setWxTempQrUrl(qrUrl);
			us.setWxTempLimitEnd(now.getTime());
			us.setTargetActionUrl("http://"+Constant.SYSTEM_DOMAIN+"/index_m.htm?wxOpen=1");
			userShareService.save(us);
		}
		return ActResult.success(us);
	}

	/**
	 * 好友邀请
	 * @param model
	 * @param type
	 * @return
	 */
	@RequestMapping("/invitationMarketingPage.user")
	public ModelAndView page(Model model,HttpServletRequest request) {
		ActResult<UserShare> act = this.invitationMarketing(model, 8);
		
		return new ModelAndView("redirect:/userShare/page"+act.getData().getId());
	}
	/**
	 * 功能：员工用户亲友列表
	 * 员工账户亲友上限5人
	 * @return
	 */
	@RequestMapping("list.user")
	@ResponseBody
	public ActResult<List<UserShare>> findAll(HttpServletRequest request){
		UserShare query=new UserShare();
		query.setUserId(loginUser.getId());
		return ActResult.success(userShareService.selectByModel(query));
	}
	
	/**
	 * 功能：员工用户亲友列表
	 * 员工账户亲友上限5人
	 * @return
	 */
	@RequestMapping("delete.user")
	@ResponseBody
	public ActResult<List<UserShare>> findAll(HttpServletRequest request,Long shareId){
		userShareService.removeById(shareId);
		return ActResult.success("");
	}
	
	
	@RequestMapping("/page{shareId}")
	public ModelAndView page(HttpServletRequest request, @PathVariable Long shareId) {
		ModelAndView result = null;
		UserShareVo vo = getUserShare(shareId);
		
		if(vo.getShare().getShareType()==8) {
			Cookie[] cookies = request.getCookies();
			String uid = "";
			if(cookies!=null && cookies.length>0) {
				for(Cookie cookie : cookies){
			        if(cookie.getName().equals("uid")){
			        	uid = cookie.getValue();
			        }
			     } 
			}
			// 个人中心好友邀请org.apache.catalina.core.ApplicationHttpRequest
			//if("org.apache.catalina.core.ApplicationHttpRequest".equals(request.getClass().getName())) {
			if(vo.getShare().getUserId().toString().equals(uid)) {
				result = new ModelAndView("invite_friends");
			} else {
				result = new ModelAndView("invite_friends2");
			}
			
		} else if(vo.getShare().getShareItemCnt() == 1) {
			result = new ModelAndView("userShare1");
		} else if(vo.getShare().getShareType()==9) {
			//result = new ModelAndView("companyShareN"); 
			result = new ModelAndView("redirect:"+vo.getShare().getNextAction());
		} else {
			result = new ModelAndView("userShareN");
		}
		if(vo.getShare().getShareType()==6) {//团购
			result = new ModelAndView("groupShare");
			List<UserShareProductItemVo> items = vo.getItems();
			for (UserShareProductItemVo userShareProductItemVo : items) {
				Integer suborderItemSum = groupSuborderItemService.getSuborderItemSum(vo.getShare().getGroupId().toString(),userShareProductItemVo.getItem().getKey1(), userShareProductItemVo.getItem().getKey2().toString());
				if(suborderItemSum==null) suborderItemSum = 0;
				userShareProductItemVo.setBuyProductNum(suborderItemSum);
			}
		}else if(vo.getShare().getShareType()==7){//订单
			List<Suborderitem> findByOrderId = suborderitemService.findByOrderId(vo.getShare().getGroupId().toString());
			vo.setNumber(findByOrderId.get(0).getNumber());
			result = new ModelAndView("ShopSavvy");
		}
		result.addObject("vo", vo);
		return result;
	}
	
	private UserShareVo getUserShare(Long id) {
		String jsonS = redisUtil.getData("REDIS_USER_SHARE_" + id);
		if(StringUtils.isEmpty(jsonS)) {
			UserShare us = userShareService.getById(id);
			if(us!=null) {
				UserShareVo rtn = new UserShareVo();
				rtn.setShare(us);

				List<UserShareProductItemVo> items = getItems(us);
				rtn.setItems(items);
				redisUtil.setData("REDIS_USER_SHARE_" + id, JsonUtil.toJson(rtn));
				return rtn;
			}
			return null;
		} else {
			return JsonUtil.getObject(jsonS,UserShareVo.class);
		}
	}

	private List<UserShareProductItemVo> getItems(UserShare us) {
		List<UserShareProductItemVo> items = new ArrayList<UserShareProductItemVo>();

		if(9==us.getShareType()){
			List<SkuVo> ls = pageDataService.findAppIndexProducts(1, 10);
			int index=0;
			for (SkuVo skuVo : ls) {
				UserShareProductItemVo vo = new UserShareProductItemVo();
				UserShareItem userShareItem = new UserShareItem();
				userShareItem.setUserId(us.getUserId());
				userShareItem.setShareId(us.getId());
				userShareItem.setOrders(++index);
				userShareItem.setKey1(skuVo.getProductId());
				userShareItem.setKey2(skuVo.getSkuId());
				vo.setItem(userShareItem);
				 				
				vo.setSaleKbn(skuVo.getSaleKbn()==null?0:skuVo.getSaleKbn());
				vo.setProductName(skuVo.getName());
				vo.setPrice(skuVo.getPrice());
				vo.setWelFare(skuVo.getCompanyTicket());
				vo.setRealPrice(skuVo.getPrice().subtract(skuVo.getCompanyTicket()));
				vo.setSale(vo.getRealPrice().multiply(BigDecimal.TEN).divide(vo.getPrice(), 2, BigDecimal.ROUND_DOWN).doubleValue());
				vo.setImage(skuVo.getImage());
				
				if(StringUtils.isEmpty(us.getImgUrl())) {
					us.setImgUrl(skuVo.getImage());
				}
				items.add(vo);
			}
		} else {
			UserShareItem q = new UserShareItem();
			q.setShareId(us.getId());
			List<UserShareItem> ls= userShareItemService.selectByModel(q);
			
			for (UserShareItem userShareItem : ls) {
				UserShareProductItemVo vo = new UserShareProductItemVo();
				vo.setItem(userShareItem);
				ProductVo p = productService.findById(userShareItem.getKey1(), false);
				ProductSpecifications sku =p.getProductSpecificationslist().get(0);
				
				vo.setSaleKbn(p.getSaleKbn()==null?0:p.getSaleKbn());
				vo.setProductName(p.getFullName());
				if(userShareItem.getKey2() != null) {
					sku = productSpecificationsService.findByIdCache(userShareItem.getKey2(), p.getId()+"");
				}
				vo.setPrice(sku.getPrice());
				vo.setWelFare(sku.getMaxFucoin());
				vo.setRealPrice(sku.getPrice().subtract(sku.getMaxFucoin()));
				vo.setSale(vo.getRealPrice().multiply(BigDecimal.TEN).divide(vo.getPrice(), 2, BigDecimal.ROUND_DOWN).doubleValue());
				List<ProductSpecificationsImage> list = productSpecificationsImageService.findProductPicture(sku.getId(), sku.getProductId());
				if(list!=null && !list.isEmpty()) {
					vo.setImage(list.get(0).getSource());
				}
				if(us.getShareItemCnt()==1) {
					List<String> imgs = new ArrayList<String>();
					for (ProductSpecificationsImage productSpecificationsImage : list) {
						imgs.add(productSpecificationsImage.getSource());
					}
					vo.setBanners(imgs);
				}
				items.add(vo);
			}
		}
		return items;
	}

	@RequestMapping("/toBind{shareId}")
	@ResponseBody
	public ModelAndView toBind(HttpServletRequest request, @PathVariable Long shareId,String type,String fuid) {
		ModelAndView result = new ModelAndView("toBind");
		if(StringUtils.isEmpty(type)) {
			type="";
		}
		String loginNextUrl="index_m.htm";
		if("9".equals(type)) {
			// 招商二维码 邀请临时账号
			result.addObject("fuid", shareId);			
		} else {
			// 正常用户分享
			result.addObject("shareId", shareId);
			if(StringUtils.isEmpty(fuid)) {
				UserShareVo vo = getUserShare(shareId);
				if(vo!=null) {
					// app 好友邀请分享
					result.addObject("fuid", vo.getShare().getUserId());
					if(vo.getShare().getShareType()==8){
						loginNextUrl="index_m.htm";
					}else{
						if(vo.getItems().size()==1) {
							UserShareItem item = vo.getItems().get(0).getItem();
							loginNextUrl="productm?productId="+item.getKey1();
							if(!StringUtils.isEmpty(item.getKey2())) {
								loginNextUrl += "&specificationsId="+item.getKey2();
							}
						}
					}
					result.addObject("type", "");
				}
			} else {
				result.addObject("fuid", fuid);
			}
		}

		result.addObject("loginNextUrl", loginNextUrl);
		result.addObject("type", type);
 		return result;
	}

	@RequestMapping("/userFriendBind{shareId}")
	@ResponseBody
	public ModelAndView userFriendBind(HttpServletRequest request, @PathVariable Long shareId,String type,Long fuid) {
		ModelAndView result = new ModelAndView();
		if(!StringUtils.isEmpty(shareId)) {
			result.addObject("shareId", shareId);
		}
		result.addObject("type", type);
		result.addObject("fuid", fuid);
		
		String empSupplierId = "";
		
		if("9".equals(type)) {
			// 招商二维码 邀请临时账号
			UserFactory emp = this.userService.getById(fuid);
			String userName = getUniqueName();
			result.addObject("userName", userName);
			result.addObject("userNick", emp.getNickName());
			result.addObject("userAvatar", emp.getAvatar());
			result.addObject("shareMsg1", "欢迎体验我的福利");
			result.setViewName("userFriendBind");
		} else if("2".equals(type)) {
			// 凑券包
			UserBlackEnvelope ure = userBlackEnvelopeService.getById(shareId);
			result.addObject("userNick", ure.getUserNike());
			result.addObject("userAvatar", ure.getUserAvatar());
			result.addObject("shareMsg1", "成为亲友互相帮忙");
			if(fuid!=null) {
				UserFactory emp = this.userService.getById(fuid);
				if(emp!=null && emp.getSupplierId()!=null) {
					empSupplierId=""+ emp.getSupplierId();
				}
			}
			result.setViewName("userFriendBind");
		} else if("4".equals(type)){
			UserFactory emp = this.userService.getById(fuid);
			result.addObject("userNick", emp.getNickName());
			result.addObject("userAvatar", emp.getAvatar());
			if(emp.getSupplierId()==null){
				result.addObject("shareMsg1", "欢迎体验我的福利");
			}else{
				result.addObject("shareMsg1", this.supplierDao.getById(emp.getSupplierId()).getComName()) ;
				empSupplierId=""+ emp.getSupplierId();
			}
			result.setViewName("userFriendBind");
		} else {
			// 正常用户分享
			UserShareVo vo = getUserShare(shareId);
			UserFactory emp = this.userService.getById(vo.getShare().getUserId());
			if(emp!=null && emp.getSupplierId()!=null) {
				empSupplierId=""+ emp.getSupplierId();
				result.addObject("shareMsg1", this.supplierDao.getById(emp.getSupplierId()).getComName());
			} else {
				result.addObject("shareMsg1", vo.getShare().getShareMsg1());
			}
			result.addObject("userNick", vo.getShare().getUserNick());
			result.addObject("userAvatar", vo.getShare().getUserAvatar());
			
			if(fuid==null) {
				result.addObject("fuid", vo.getShare().getUserId());
			}
			result.setViewName("bindphone");
		}
		result.addObject("empSupplierId", empSupplierId);
 		return result;
	}
	
	/**
	 * 普通账号进行审核
	 * @param request
	 * @param result
	 * @param empId
	 * @return
	 */
	@RequestMapping("bind{shareId}")
	@ResponseBody
	public ActResult<String> normalUserCheck(HttpServletRequest request, HttpServletResponse response,
			@PathVariable Long shareId, String userName, String phone, String code, String memo, String type, Long uid,Long fuid,
			String openId) {

		if(uid == null) {
			if(StringUtils.isEmpty(userName) && StringUtils.isEmpty(phone) ) {
				return ActResult.fail("用户名，必须输入");
			}
			String old = redisUtil.getData("phoneFactory_"+phone);
			if(!StringUtils.isEmpty(phone) && old==null) return ActResult.fail("验证码错误，请重新获取验证码。");
			
			if(!StringUtils.isEmpty(phone) && !old.equals(code)) return ActResult.fail("验证码错误，请输入正确的验证码。");
		}

		UserFactory emp = null;
		UserShareVo vo = null;
		String password = userName;
		if("9".equals(type)) {
			password = "1234";
			emp = this.userService.getById(fuid);
		} else if("2".equals(type)) {
			password = phone;
			UserBlackEnvelope ure = userBlackEnvelopeService.getById(shareId);
			if(ure!=null) {
				//普通用户的用户信息
				emp = this.userService.getById(ure.getUserId());
			} else {
				return ActResult.fail("邀请信息不存在");
			}			
		} else if("4".equals(type)){
			password = phone;
			emp = this.userService.getById(fuid);
			
		} else {
			password = phone;
			vo = getUserShare(shareId);
			if(vo==null){
				return ActResult.fail("邀请信息不存在");
			} else {
				//普通用户的用户信息
				emp = this.userService.getById(vo.getShare().getUserId());
			}
		}
		
		//若当前登录账户为员工账户，则不能申请成为其他账户亲友
		if(emp==null){
			return ActResult.fail("该亲友账户不存在");
		}
		
		UserFactory userFactory = null;
		UserContacts query = new UserContacts();
		query.setUserId(emp.getId());
		query.setFirendType("1");
		List<UserContacts> list = userContactsService.selectByModel(query);
		if(uid == null) {
			ActResult<UserFactory> ar = null;
			if(!StringUtils.isEmpty(phone)) {
				ar = userService.findByPhone(phone);
			} else {
				ar = userService.findByPhone(userName);
			}
			userFactory = ar.getData();
			if(!ar.isSuccess()) {
				if(StringUtils.isEmpty(userName)) {
					userName = phone;
				}
				//向共通注册
				ActResult<CommUser> actUser = this.registerCommonByPhone2(userName,password,phone,memo, 1, request);
				if(actUser.isSuccess()) {
					UserFactory uf = new UserFactory();
					uf.setId(actUser.getData().getUserId());
					uf.setUserName(actUser.getData().getUserName());
					uf.setEmail(actUser.getData().getUserEmail());
					uf.setEnabled(actUser.getData().getEnabled());
					uf.setUsable(actUser.getData().getUsable());
					uf.setCreatTime(new Date());
					uf.setNickName(StringUtils.isEmpty(memo)?actUser.getData().getNickName():memo);
					uf.setEnabled(1);
					uf.setType(1);
					uf.setPhone(actUser.getData().getUserPhone());
					uf.setGender("m");
					uf.setLoginTime(new Date());
					userFactory=uf;
					userService.specialSave(uf);
					
					if(!"9".equals(type)) {
						EntParamCode fistPrize = entParamCodeService.getAppFirstPrizeCode().get("202");
						if(fistPrize!=null && !"1".equals(fistPrize.getStopFlg())) {
							String note = StringUtils.isEmpty(fistPrize.getDescr())?"和亲友一起享受员工内购福利，平台奖励内购券：":fistPrize.getDescr();
							//getReward(emp,userFactory,fistPrize,note);//亲友双方获取奖励
							if (list!=null) {
								int i = list.size()+1;
								if(i==1 || i==2) {
									this.sendUserFuli(emp, "100", note, "auto", null);
								}else if(i==3 || i==4) {
									this.sendUserFuli(emp, "150", note, "auto", null);
								}else if(i>=5) {
									this.sendUserFuli(emp, "200", note, "auto", null);
								}
							}
							this.sendUserFuli(userFactory,fistPrize.getValue(),note,"auto",null);
							this.sendUserFuli(userFactory,"300","平台向您发放内购券300元！您可以登陆 我的福利商城购买内购产品，或分享给亲友，享受大牌员工福利！","auto",null);
						}
					}
				} else {
					return ActResult.fail("您输入的手机号还没有注册，无法添加为亲友");
				}
			}
		} else {
			userFactory = this.userService.getById(uid);
		}
		
		//wx号绑定
		if(!StringUtils.isEmpty(openId) && !"null".equals(openId)) {
			UserWeixin user = userWeixinService.getOneModelByOpenId(openId);
			if(user==null) {
				user = new UserWeixin();
				user.setAppId(WxOpenService.APP_ID);
				user.setOpenId(openId);
				user.setUserId(userFactory.getId());
				userWeixinService.save(user);
			}
		}

		ActResult<String> rtn=ActResult.success(userFactory.getId());
		if(userFactory.getEmployeeType()==0 && emp.getEmployeeType()==0) {
			rtn.setMsg("很抱歉，您暂不能添加非员工用户为好友");
			return rtn;
		}
		
		//双方亲友数量是否达到上限
		///////////////////////////////////////////////////////////////////////////////
		query.setUserId(userFactory.getId());
		query.setFirendType("1");
		List<UserContacts> list2 = userContactsService.selectByModel(query);
		//普通账号亲友数量是否已达到上限
		for (UserContacts userFriend : list2) {
			if(emp.getId().equals(userFriend.getContactsId())) {
				rtn.setMsg("绑定成功,立刻体验");
				return rtn;
			}
		}
		//员工账户亲友上限50人
		if(list.size()>=50){
			rtn.setMsg("亲友数已达上限（50人），不能再添加了");
			return rtn;
		}
		
		//普通用户的亲友 uq = new UserFriendQuery();
		if(list.size()>=50) {
			rtn.setMsg("对方账号亲友已达上限");
			return rtn;
		}
		
		make2contachs(emp,userFactory);
		
		loginFacade.doPushNotify(emp.getId(), null, "亲友确认",userFactory.getNickName()+"("+userFactory.getPhone()+"),接受了您的亲友邀请，给他点内购券增加友好度吧。");
		
		
		if(vo!=null && vo.getShare().getShareType()!=null && vo.getShare().getShareType()==6 ) {
			// 如果没有参加 则填入信息
			GroupBuy gb = groupOrdersService.findByBuyId(null, vo.getShare().getGroupId());
			addGroupBuyUser(gb, userFactory);
		}
		Cookie cookie_uid = new Cookie("uid",userFactory.getId()+"");
		cookie_uid.setPath("/");
		cookie_uid.setMaxAge(24 * 60 * 60 * 1000);
		response.addCookie(cookie_uid);
		////////////////////////////////////
		rtn.setMsg("绑定成功,立刻体验");
		return rtn;
	}
	
	private void sendUserFuli(UserFactory emp, String limitm, String note,String updName,String key) {
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("limitm", limitm);
		paramMap.put("desrc", note);
		if(emp!=null) {
			paramMap.put("empId", emp.getId());
			paramMap.put("empName", emp.getNickName());
			paramMap.put("updName",updName);
		}
		if(key!=null) {
			paramMap.put("key", key);
		}
		HttpClientUtil.sendHttpRequest("post", qiyeApiUrl+"api/fisrtPrize", paramMap);
	}
	@RequestMapping("/toCompanyBind{shareId}")
	@ResponseBody
	public ModelAndView toCompanyBind(HttpServletRequest request, @PathVariable Long shareId,String fromId) {
		ModelAndView result = new ModelAndView("toCompanyBind");
		result.addObject("shareId", shareId);
		result.addObject("fromId", fromId);
		String targetJsVesion = redisUtil.getData(RedisConstant.USER_SHARE_TARGET_JS_VERSION);
		if(targetJsVesion==null) targetJsVesion="";
		result.addObject("targetJsVesion", targetJsVesion);
 		return result;
	}
	
	@RequestMapping("/companyBindPage{shareId}")
	@ResponseBody
	public ModelAndView companyBindPage(HttpServletRequest request, @PathVariable Long shareId,String fromId,String openId,String afterDo) {
		ModelAndView result = new ModelAndView("companyBind");
		if (shareId!=null) {
			UserShareVo vo = getUserShare(shareId);
			if (vo!=null) {
				// 惠普特别显示
				if(shareId.equals(2069777758193405L)) {
					result = new ModelAndView("companyBindHP");
				}
					
				result.addObject("vo", vo);
				result.addObject("comName", vo.getShare().getUserNick());//公司名称
				result.addObject("shareId", shareId);
//				String userName = getUniqueName();
//				result.addObject("userName", userName); 
				result.addObject("companyType", "company");
				result.addObject("openId", openId);
				result.addObject("afterDo", afterDo);				
				
			}else{//说明shareId
				Supplier sup = this.supplierDao.getById(shareId);
				if (sup!=null) {//说明shareId为商家ID
					result.addObject("shareId", shareId);
					String comName = sup.getComName();
					if(!StringUtils.isEmpty(sup.getNickName())) {
						comName=sup.getNickName();
					}
					result.addObject("comName", comName);//公司名称
					result.addObject("companyType", "workmate");
					result.addObject("openId", openId);
				} else {
					SupplierTemp supt = supplierTempService.getById(shareId);
					if (supt!=null) {//说明shareId为商家ID
						result.addObject("shareId", shareId);
						result.addObject("comName", supt.getComName());//公司名称
						result.addObject("companyType", "company");
						result.addObject("openId", openId);
					}
				}
			}
		}
		if (!StringUtils.isEmpty(fromId)) {
			result.addObject("fromId", fromId);
		}
		return result;
	}
	/**
	 * 普通账号进行审核
	 * @param request
	 * @param result
	 * @param empId
	 * @return
	 */
	@RequestMapping("companyBind{shareId}")
	@ResponseBody
	public ActResult<String> companyBind(HttpServletRequest request, @PathVariable Long shareId, String code,
			EnterpriseUser emp, String fromId, String companyType, String openId, Long motoShareId,String afterDo) {

		String unSafe="1";
		// 如果输入登陆账号（一般为自动生成，且手机号码输入不正确，自动清空手机号）
		if(!StringUtils.isEmpty(emp.getUserName())){
			if(!StringUtils.isPhoneNumber(emp.getPhone())) {
				emp.setPhone("");
			}
		}
		// 登陆账号或者手机号必须输入
		if(StringUtils.isEmpty(emp.getUserName()) && StringUtils.isEmpty(emp.getPhone())  
				&& StringUtils.isEmpty(emp.getEmail())  && StringUtils.isEmpty(emp.getEmpNumber())) {
			return ActResult.fail("请输入登陆账号、员工号或正确的手机号");
		}
		
		// 手机号输入后，检查验证码
		if(!StringUtils.isEmpty(emp.getPhone()) && !StringUtils.isEmpty(code)) {
			String old = redisUtil.getData("phoneFactory_"+emp.getPhone());
			if(old==null) return ActResult.fail("验证码错误，请重新获取验证码。");
		
			if(!old.equals(code)) return ActResult.fail("验证码错误，请输入正确的验证码。");
			
			unSafe="0";
		}

		UserShare vo = userShareService.getById(shareId);
//		if(vo==null){
//			if (StringUtils.isEmpty(fromId)) {
//				return ActResult.fail("邀请信息已不存在，请向商家索取最新的要求链接");
//			}
//		}
		
		//普通用户的用户信息
		Long enterpriseId=null;
		if (vo!=null) {
			Supplier sup = this.supplierDao.getById(vo.getUserId());
			if(sup!=null) {
				enterpriseId=sup.getId();
			} else {
				SupplierTemp supt = supplierTempService.getById(vo.getUserId());
				if(supt!=null) {
					enterpriseId=supt.getId();
				}
			}
		}else{
			Supplier sup = this.supplierDao.getById(shareId);
			if(sup!=null) {
				enterpriseId=sup.getId();
			} else {
				SupplierTemp supt = supplierTempService.getById(shareId);
				if(supt!=null) {
					enterpriseId=supt.getId();
				}
			}
		}

		//若当前登录账户为员工账户，则不能申请成为其他账户亲友
		if(enterpriseId==null){
			return ActResult.fail("企业信息不存在");
		}

		Map<String, Object> comMap = new HashMap<String, Object>();
		
		// 登陆账号为输入时，已手机号为登陆账号
		if(StringUtils.isEmpty(emp.getUserName())){
			emp.setUserName(emp.getPhone());
		}
		comMap.put("userName", emp.getUserName());
		comMap.put("phone", emp.getPhone());
		comMap.put("name", emp.getName());
		comMap.put("email", emp.getEmail());
		comMap.put("seniority",emp.getSeniority());
		comMap.put("enterpriseId",enterpriseId);
		comMap.put("fromId", fromId);
		comMap.put("empNumber", emp.getEmpNumber());
		comMap.put("unSafe", unSafe);

		String ticketResult = HttpClientUtil.sendHttpRequest("post", com.wode.factory.user.util.Constant.QIYE_API_URL + "api/addEmployee", comMap);
		ActResult<String> art = JsonUtil.getObject(ticketResult, ActResult.class);
		
		if(art.isSuccess()) {
			Long userId= NumberUtil.toLong(art.getData());
			//wx号绑定
			if(!StringUtils.isEmpty(openId) && !"null".equals(openId) && art.isSuccess()) {
				UserWeixin user = userWeixinService.getOneModelByOpenId(openId);
				if(user==null) {
					user = new UserWeixin();
					user.setAppId(WxOpenService.APP_ID);
					user.setOpenId(openId);
					user.setUserId(NumberUtil.toLong(art.getData()));
					userWeixinService.save(user);
					
					this.setTag(user, WxOpenService.TAG_ID_SHOP);
				}
			}
			if("need_push500".equals(art.getMsg())) {//首次注册500内购券
				//String msg = "公司向您发放内购券500元！您可以登陆 我的福利商城购买内购产品，或分享给亲友，享受大牌员工福利！";
				//app推送
				//loginFacade.doPushNotify(userId, null, "公司发放福利", msg);
				//微信推送
				//loginFacade.doPushWxBanlanceMsg(userId, "500", TimeUtil.getStringDateShort(), msg, "1");
				String msg = userId+"_userBind_500ticket_wx";
				AppOrWxPushUtil.pushMsgAll(redisUtil, msg, AppOrWxPushUtil.PUSH_TYPE_USER_BIND);
				//清空Msg
				art.setMsg("");
			}
			// 同事添加后，自动加入好友列表
			if(art.isSuccess()) {
				if(!StringUtils.isEmpty(fromId)) {
					make2contachs(this.userService.getById(NumberUtil.toLong(fromId)),this.userService.getById(userId));
				}
			}
			
			if(motoShareId!=null) {
				UserShare us = userShareService.getById(motoShareId);
				if(us!=null && us.getShareType()!=null && us.getShareType()==6 ) {
					// 如果没有参加 则填入信息
					GroupBuy gb = groupOrdersService.findByBuyId(null, us.getGroupId());
					addGroupBuyUser(gb, this.userService.getById(userId));
				}
			}
			
			if(!"autoTicket".equals(afterDo) && vo!=null) {
				// 自动发放换领币
				userShareTicketService.autoTicket(vo.getId(), this.userService.getEmpById(userId));
			}
		} 
		return art;
	}

	@RequestMapping("/wxEventLink")
	public ModelAndView wxEventLink(String eventKey,String openId,ModelAndView model,HttpServletRequest request, HttpServletResponse response) {

		if(eventKey.startsWith(MY_EVENT_TYPE_TICKETL)) {
			// 领取卡券，直接跳转至卡券Controller处理
			model.setViewName("redirect:"+"http://"+Constant.SYSTEM_DOMAIN+"/limitTicket/wxEventLink?eventKey="+eventKey+"openId="+openId);
			return model;
		}
		// 绑定动作改在各绑定功能中处理，绑定入口都从获取openId开始
		UserWeixin user = userWeixinService.getOneModelByOpenId(openId);
		model.addObject("openId", openId);
		logger.debug("eventKey="+eventKey+"&openId="+openId);
		String eventStart="";
		
		if(eventKey.startsWith(MY_EVENT_TYPE_COMPANY)) {
			eventStart = MY_EVENT_TYPE_COMPANY;
		} else if(eventKey.startsWith(MY_EVENT_TYPE_TICKETE)) {
			eventStart = MY_EVENT_TYPE_TICKETE;
		} else if(eventKey.startsWith(MY_EVENT_TYPE_SHARE)) {
			eventStart = MY_EVENT_TYPE_SHARE;
		} else if (eventKey.startsWith(MY_EVENT_TYPE_AUTO_BUYC)) {
			eventStart = MY_EVENT_TYPE_AUTO_BUYC;
		} else if(eventKey.startsWith(MY_EVENT_TYPE_AUTO_BUYU)) {
			eventStart = MY_EVENT_TYPE_AUTO_BUYU;
		}
		
		Long shareId= -1L;
		String nextUrl = "";
		GroupBuy gb = null;
		
		if(MY_EVENT_TYPE_COMPANY.equals(eventStart) || MY_EVENT_TYPE_SHARE.equals(eventStart)) {
			shareId = NumberUtil.toLong(eventKey.substring(eventStart.length()));
		} else if(MY_EVENT_TYPE_AUTO_BUYC.equals(eventStart)) {
			Long autoBuyId = NumberUtil.toLong(eventKey.substring(eventStart.length()));
			UserShareAutoBuy ab = userShareAutoBuyService.getById(autoBuyId);
			shareId = ab.getShareId();

			nextUrl = "http://"+Constant.SYSTEM_DOMAIN+"/autoBuy/share"+shareId+".user?skuId="+ab.getSkuId()+"&autoBuyId="+autoBuyId+"&type="+eventStart;
		} else if(MY_EVENT_TYPE_TICKETE.equals(eventStart)) {
			Long autoBuyId = NumberUtil.toLong(eventKey.substring(eventStart.length()));
			UserShareTicket st = userShareTicketService.getById(autoBuyId);
			if(st!=null) {
				shareId = st.getShareId();
				nextUrl = "http://"+Constant.SYSTEM_DOMAIN+"/autoTicket/share"+shareId+".user?ticketType="+st.getSaleKbn()+"&autoBuyId="+autoBuyId;
			} else {
				// 数据异常 直接跳到首页
				model.setViewName("redirect:"+"http://"+Constant.SYSTEM_DOMAIN+"/index_m.htm");
				return model;
			}
		}
		
		UserShare vo = userShareService.getById(shareId);
		if(vo == null) {
			// 数据异常 直接跳到首页
			model.setViewName("redirect:"+"/index_m.htm");
			return model;
		}
		model.addObject("shareId", shareId);
		model.addObject("type", vo.getShareType());
		if(MY_EVENT_TYPE_SHARE.equals(eventStart)) {
			gb = groupOrdersService.findByBuyId(null, vo.getGroupId());
			if(gb == null || gb.getShopId()==null) {
				// 数据异常 直接跳到首页
				model.setViewName("redirect:"+"/index_m.htm");
				return model;
			}
			List<GroupBuyProduct> groupBuyProductList = groupBuyProductService.findByGroupId(vo.getGroupId());
			if(groupBuyProductList.size() > 0){
				nextUrl = "http://"+Constant.SYSTEM_DOMAIN+"/group/toCanBuy.user?groupId="+vo.getGroupId()+"&type="+0+"&shareId="+shareId;
			}else{
				nextUrl = "http://"+Constant.SYSTEM_DOMAIN+"/shop/page?shopId="+gb.getShopId();
			}
		} else if(MY_EVENT_TYPE_COMPANY.equals(eventStart)){
			nextUrl = "http://"+Constant.SYSTEM_DOMAIN+"/neigou.html";
			if(!StringUtils.isEmpty(vo.getTargetActionUrl())) {
				nextUrl = vo.getTargetActionUrl();
			}
		}		 
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

				if(MY_EVENT_TYPE_SHARE.equals(eventStart)) {
					// 如果没有参加 则填入信息
					addGroupBuyUser(gb, userFactory);
				}
				
				if(!vo.getUserId().equals(userFactory.getSupplierId())) {
					if(MY_EVENT_TYPE_AUTO_BUYC.equals(eventStart)) {
						// 非该企业员工 不进行自动购买，跳转活动页
						nextUrl = "http://"+Constant.SYSTEM_DOMAIN+"/index_m.htm";
						if(!StringUtils.isEmpty(vo.getTargetActionUrl())) {
							nextUrl = vo.getTargetActionUrl();
						}
					}
				} else {
					if(vo != null && !MY_EVENT_TYPE_TICKETE.equals(eventStart)) {
						// 自动发放换领币
						userShareTicketService.autoTicket(vo.getId(), this.userService.getEmpById(userFactory.getId()));
					}
				}
			}
		}
		model.addObject("nextUrl", nextUrl);
		if(MY_EVENT_TYPE_SHARE.equals(eventStart)) {
			model.addObject("state", "friendBind");
		} else if(MY_EVENT_TYPE_COMPANY.equals(eventStart) || eventKey.startsWith(MY_EVENT_TYPE_AUTO_BUYC)) {
			model.addObject("state", "companyBind");
		} else if(MY_EVENT_TYPE_TICKETE.equals(eventStart)) {
			model.addObject("state", "autoTicket");
		}
		model.setViewName("wxEventLink");
		return model;
	}
	
	/**
	 * 添加团购邀请
	 * @param gb
	 * @param userFactory
	 */
	private void addGroupBuyUser(GroupBuy gb, UserFactory userFactory) {
		if(gb!=null) {
			GroupBuyUserVo mem=groupBuyUserService.getByUserIdAndGroupId(userFactory.getId(), gb.getId()+"");
			if(mem==null) {
				GroupBuyUser m=new GroupBuyUser();
				m.setShopId(gb.getShopId());
				m.setGroupId(gb.getId());
				m.setUserId(userFactory.getId());
				m.setCreateTime(new Date());
				m.setIsLadder(0);
				m.setIsAdd(0);
				m.setUserName(userFactory.getNickName());
				m.setStatus(1);
				groupBuyUserService.save(m);
				//将成员直接加入到群聊中
				String imgroupId = userContactsService.createGroupBuyGroup(userFactory.getId(), "" + gb.getId());
				if(!StringUtils.isEmpty(imgroupId)) {
					UserImGroup im= userImGroupService.getById(NumberUtil.toLong(imgroupId));
					if(im!=null) {
						try {
							List<UserImGroup> groups = new ArrayList<UserImGroup>();
							groups.add(im);
							String name =userFactory.getNickName() == null ? userFactory.getUserName() : userFactory.getNickName();
							String msg="@"+name+"欢迎加入！"+"\n"+"团越多，省越多。"+"\n"+"拼团完成后，由团长统一收货。"+"\n"+"点击选购商品";
							if(groups!=null && !groups.isEmpty()) {
								EasemobIMUtils.shoppingGroupMessage(msg, groups, userFactory.getId(), null, name,"add");
							}
						} catch (Exception e) {
						}
					}
				}
			}
		}
	}
	
	private List<UserShareItem> makeItems(Integer type, Long productId, Long skuId, UserShare us) {
		List<UserShareItem> items = new ArrayList<UserShareItem>();
		
		ProductSpecifications sku = null;
		if(type==1) {
			UserShareItem item = new UserShareItem();
			item.setUserId(us.getUserId());
			item.setShareId(us.getId());
			item.setOrders(1);
			item.setKey1(productId);
			item.setKey2(skuId);
			
			items.add(item);

			ProductVo p = productService.findById(productId, false);
			sku =p.getProductSpecificationslist().get(0);
			
			if(skuId != null) {
				sku = productSpecificationsService.findByIdCache(skuId, p.getId()+"");
			}
		}else if(type==6){
			UserShareItem item = new UserShareItem();
			item.setUserId(us.getUserId());
			item.setShareId(us.getId());
			item.setOrders(1);
			item.setKey1(productId);
			item.setKey2(skuId);
			
			items.add(item);

			ProductVo p = productService.findById(productId, false);
			sku =p.getProductSpecificationslist().get(0);
			
			if(skuId != null) {
				sku = productSpecificationsService.findByIdCache(skuId, p.getId()+"");
			}
			
		}else if(type==7){
			UserShareItem item = new UserShareItem();
			item.setUserId(us.getUserId());
			item.setShareId(us.getId());
			item.setOrders(1);
			item.setKey1(productId);
			item.setKey2(skuId);
			
			items.add(item);

			ProductVo p = productService.findById(productId, false);
			sku =p.getProductSpecificationslist().get(0);
			
			if(skuId != null) {
				sku = productSpecificationsService.findByIdCache(skuId, p.getId()+"");
			}
			
		} else {

			String cartJson = redisUtil.getData(RedisConstant.CART_REDIS + "_" + us.getUserId());
			if (!StringUtils.isEmpty(cartJson)) {
				Cart redisCart = JsonUtil.getObject(cartJson, Cart.class);

				int index=0;
				for (CartItem cart : redisCart.getAllItems()) {//

					if(index==0) {

						ProductVo p = productService.findById(cart.getProductId(), false);
						sku =p.getProductSpecificationslist().get(0);
						
						if(skuId != null) {
							sku = productSpecificationsService.findByIdCache(Long.valueOf(cart.getPartNumber()), p.getId()+"");
						}
					}
					UserShareItem item = new UserShareItem();
					item.setUserId(us.getUserId());
					item.setShareId(us.getId());
					item.setOrders(index);
					item.setKey1(cart.getProductId());
					item.setKey2(Long.valueOf(cart.getPartNumber()));
					
					items.add(item);
				}
			}			
		}
		if(sku!=null) {
			List<ProductSpecificationsImage> list = productSpecificationsImageService.findProductPicture(sku.getId(), sku.getProductId());
			if(list!=null && !list.isEmpty()) {
				 us.setImgUrl(list.get(0).getSource());
			}
		}
		return items;
	}
	

	/**注册共通的用户，用手机号注册
	 * @param phone
	 * @return
	 */
	private ActResult<CommUser> registerCommonByPhone2(String userName,String password,String phone,String memo,int userType, HttpServletRequest request){
		
		ActResult<CommUser> old= us.findByPhone(StringUtils.isEmpty(phone)?userName:phone);
		
		if(old.isSuccess()) return old;
		
		CommUser user = new CommUser();
		user.setUserName(StringUtils.isEmpty(userName)?phone:userName);
		user.setNickName(StringUtils.isEmpty(memo)?user.getUserName():memo);
		user.setPassword(StringUtils.isEmpty(password)?user.getUserName():password);
		user.setUserPhone(phone);
		if(user.getNickName().equals(user.getUserPhone())) {
			String nickName=phone;
			if(nickName.length()>4) {
				nickName="1***"+nickName.substring(nickName.length()-4);
				user.setNickName(nickName);
			}
		}
		user.setUsable(1);
		user.setUserType(userType);
		user.setEnabled(1);//用户默认是被激活的
		user.setUserCom(UserConstant.COMFROM);
		user.setUserIp(IPUtils.getClientAddress(request));
		return us.insert(user);

	}

	@RequestMapping("getQr")
	@SuppressWarnings("rawtypes")
	public void getQrForLogin(HttpServletRequest request, HttpServletResponse response,String text) throws WriterException, IOException{
		int width = 300;   
        int height = 300;
		Hashtable hints= new Hashtable();   
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        String format = "png";   
        if (text.contains("____")) {
			 text = text.replace("____","&");
		}
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);  
        MatrixToImageWriter.writeToStream(bitMatrix, format, response.getOutputStream());  
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("downLoadQr")
	public void downLoadQr(HttpServletRequest request, HttpServletResponse response,String text) throws WriterException, IOException{
		int width = 247;   
        int height = 247;
        Hashtable hints= new Hashtable();   
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        String format = "jpg";   
        if (text.contains("____")) {
			 text = text.replace("____","&");
		}
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints); 
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ getFileNameForSave(request,"我的福利员工绑定二维码.jpg"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		
        MatrixToImageWriter.writeToStreamEx(bitMatrix, format, response.getOutputStream());  
	}

	
	@SuppressWarnings("rawtypes")
	@RequestMapping("downLoadQrEmp500Ticket")
	public void downLoadQrEmp500Ticket(HttpServletRequest request, HttpServletResponse response,
			String text,String companyName,String limitEnd,String exchange) throws WriterException, IOException{
		int width = 120;   
        int height = 120;
        Hashtable hints= new Hashtable();   
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        String format = "png";   
        if (text.contains("____")) {
			 text = text.replace("____","&");
		}
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints); 
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ getFileNameForSave(request,"我的福利员工绑定二维码.png"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");

		// 去除0元换领币
		if("0".equals(exchange) || "0.00".equals(exchange)) {
			exchange="";
		}
        MatrixToImageWriter.writeToStreamEmp500Ticket(bitMatrix, format, response.getOutputStream(),companyName,limitEnd,exchange);  
	}
	
	/**
	 * 点击确认登录后处理
	 * @param request
	 * @param uuid
	 * @param ticket
	 * @return
	 */
	@RequestMapping("sendVerCodeSms")
	@ResponseBody
	public ActResult<String> sendVerCodeSms(HttpServletRequest request, String phone){
		String contnt = StringUtils.getRandomNum();
		try {
			String old = redisUtil.getData("phoneFactory_"+phone);
			//半小时内验证码相同
			if(!StringUtils.isEmpty(old)) {
				contnt=old;
			}
			redisUtil.setData("phoneFactory_"+phone, contnt, 60*30);
			contnt = "验证码"+contnt+"（有效期30分钟）。如非本人操作，请忽略此短信。";
			/** 
			 * signature  短信签名
			 * soure   短信来源
			 * ip   为空
			 * signature,ver_code1+contnt+ver_code2   短信内容
			 * */
			sms.sendSms(phone, "我的网", contnt, "factoryAPI", false, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActResult.success("");
	}
	

	/**
	 * 分页显示商品评论
	 * @param productId
	 * @param page
	 * @param pageSize
	 * @returncommentDegree
	 */
	@RequestMapping("/copyLink")
	public ActResult<Object> copyLink(ModelMap model,String url) {
		int index = url.indexOf('?');
		if(index==-1) {
			String shareKey = "userShare/page";
			index=url.indexOf(shareKey); 
			if(index == -1) {
				return ActResult.fail("链接内容无法识别");
			} else {
				String sharePage = url.substring(index+shareKey.length());
				UserShareVo sv= getUserShare(Long.parseLong(sharePage));
				if(sv==null)return ActResult.fail("链接内容无法识别");
				if(sv.getItems()!=null && sv.getItems().size()>0){
					UserShareProductItemVo item = sv.getItems().get(0);
					model.addAttribute("productId", item.getItem().getKey1());
					model.addAttribute("specificationsId", item.getItem().getKey2());
					model.addAttribute("productName", item.getProductName());
					model.addAttribute("image", item.getImage());
					model.addAttribute("price", item.getPrice());
					model.addAttribute("realPrice", item.getRealPrice());
					model.addAttribute("welFare", item.getWelFare());
				}
				model.addAttribute("userId", sv.getShare().getUserId());
				
				return ActResult.success(model);
			}
		} else {
			Map<String,String> map = new HashMap<String,String>();
			String q=url.substring(index+1);
			String[] qs=q.split("&");
			for (int i = 0; i < qs.length; i++) {
				String[] ary=qs[i].split("=");
				if(ary.length>=2) {
					map.put(ary[0],ary[1]);
				}
			}
			
			if(!map.containsKey("productId")) {
				return ActResult.fail("链接内容无法识别");				
			}
			
			Long proId = Long.parseLong(map.get("productId"));
			Long skuId=null;
			if(map.containsKey("specificationsId")) {
				skuId = Long.parseLong(map.get("specificationsId"));
			}

			ProductVo pvo = productService.findById(proId, true);
			if(pvo==null) {
				return ActResult.fail("链接内容无法识别");
			}

			//默认选中规格策略
			ProductSpecifications show =new ProductSpecifications();//要显示的sku
			
			if(StringUtils.isEmpty(skuId)){
				show = pvo.getProductSpecificationslist().get(0);
			}else{
				show = productSpecificationsService.findByIdCache(skuId,proId+"");
			}
			if (show==null) {
				return ActResult.fail("链接内容无法识别");
			}

			model.addAttribute("productId", proId);
			model.addAttribute("specificationsId", show.getId());
			model.addAttribute("productName", pvo.getFullName());
			List<ProductSpecificationsImage> list = productSpecificationsImageService.findProductPicture(show.getId(),proId);
			model.addAttribute("image", list.get(0).getSource());
			model.addAttribute("price", show.getPrice());
			model.addAttribute("realPrice",show.getPrice().subtract(show.getMaxFucoin()).setScale(2, BigDecimal.ROUND_DOWN));
			model.addAttribute("welFare", show.getMaxFucoin());
			model.addAttribute("userId", map.get("userId"));
			return ActResult.success(model);
		}
	}
	/**
	 * 微信分享历史
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/page.user")
	public ModelAndView shareList(HttpServletRequest request,ModelAndView model){
		model.setViewName("my_share");
		return model;
	}
	private String getFileNameForSave(HttpServletRequest request,String filename) throws UnsupportedEncodingException {

		String userAgent = request.getHeader("user-agent");
		String new_filename = java.net.URLEncoder.encode(filename, "UTF-8");
		// 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
		String rtn = "filename=\"" + new_filename + "\"";
		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
			// IE浏览器，只能采用URLEncoder编码
			if (userAgent.indexOf("msie") != -1) {
				rtn = "filename=\"" + new_filename + "\"";
			}
			// Opera浏览器只能采用filename*
			else if (userAgent.indexOf("opera") != -1) {
				rtn = "filename*=UTF-8''" + new_filename;
			}
			// Safari浏览器，只能采用ISO编码的中文输出
			else if (userAgent.indexOf("safari") != -1) {
				rtn = "filename=\"" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "\"";
			}
			// Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
			else if (userAgent.indexOf("applewebkit") != -1) {
				new_filename = MimeUtility.encodeText(filename, "UTF8", "B");
				rtn = "filename=\"" + new_filename + "\"";
			}
			// FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
			else if (userAgent.indexOf("mozilla") != -1) {
				rtn = "filename*=UTF-8''" + new_filename;
			}
		}
		return rtn;
	}
	
	private void setTag(UserWeixin user,String tagId){
		UserFactory uf = userService.getById(user.getUserId());
		if(uf!=null && uf.getType()!=null && uf.getType()==2){
			UserWeixin q = new UserWeixin();
			q.setUserId(user.getUserId());
			q.setLogout(0);
			
			List<UserWeixin> ls = userWeixinService.selectByModel(q);
			String openIds="";
			for (UserWeixin userWeixin : ls) {
				openIds += ","+userWeixin.getOpenId();
			}
			
			if(openIds.length()>0) {				
				wxOpen.setTag(openIds.substring(1), tagId, false, null);
			}
		}
	}
	/**
	 * 自动转换
	 * @return
	 */
	@RequestMapping("atuoConverSionUrl")
	public ActResult<String> atuoConverSionUrl(HttpServletRequest request, String url){
		ActResult<String> actResult = new ActResult<String>();
		if (!StringUtils.isEmpty(url)) {
			if (url.contains("userShare/page")) {
				int i = url.indexOf("page");
				String shareId = url.substring(i+4);
				UserShareVo userShare = getUserShare(Long.parseLong(shareId));
				if (userShare!=null) {
					UserShare share = userShare.getShare();
					UserFactory userFactory = userService.getById(Long.valueOf(share.getUserId()));
					List<Shop> shopList = null;
					if (userFactory!=null) {
						shopList = shopService.getShopBySupplierId(userFactory.getSupplierId());
					}else{
						shopList = shopService.getShopBySupplierId(share.getUserId());
					}
					if (shopList!=null && !shopList.isEmpty()) {
						Long shopId = shopList.get(0).getId();
						url = "http://"+Constant.SYSTEM_DOMAIN+"/shop/page?shopId="+shopId;
						actResult.setData(url);
					}
				}
			}
		}
		return actResult;
	}
	


	private void make2contachs(UserFactory emp, UserFactory user) {
		if(emp==null || user ==null) return;
		if(emp.getId().equals(user.getId())) return;
		
		UserContacts query;
		Date now=new Date();

		////////////////////////////////
		// 增加自己的联系人
		query = new UserContacts();
		query.setUserId(user.getId());
		query.setContactsId(emp.getId());
		UserContacts uc1 = userContactsService.selectOneByModel(query);
		
		if(!StringUtils.isEmpty(uc1)){
			if(!StringUtils.isEmpty(uc1.getContactsMemo())) {
				uc1.setContactsMemo(emp.getNickName());
			}
			if("0".equals(uc1.getFirendType())) {
				uc1.setFirendType("1");
				uc1.setCreateTime(now);
			}
			if(StringUtils.isEmpty(uc1.getContactsImId())) {
				UserIm q = new UserIm();
				q.setUserId(uc1.getContactsId());
				List<UserIm> lst = userImService.selectByModel(q);
				if(lst!=null && !lst.isEmpty()) {
					uc1.setContactsImId(lst.get(0).getOpenimId());
				}
			}
			
			userContactsService.update(uc1);
		} else {
			uc1 = new UserContacts();
			uc1.setUserId(user.getId());
			uc1.setContactsId(emp.getId());
			uc1.setContactsMemo(emp.getNickName());
			uc1.setCreateTime(now);
			uc1.setApprFrom("weixin");				
			UserIm q = new UserIm();
			q.setUserId(uc1.getContactsId());
			List<UserIm> lst = userImService.selectByModel(q);
			if(lst!=null && !lst.isEmpty()) {
				uc1.setContactsImId(lst.get(0).getOpenimId());
			}
			uc1.setFirendType("1");
			
			userContactsService.save(uc1);
		}
		////////////////////////////////

		////////////////////////////////
		// 增加对方的联系人
		query = new UserContacts();
		query.setUserId(emp.getId());
		query.setContactsId(user.getId());
		UserContacts uc2 = userContactsService.selectOneByModel(query);
		
		if(!StringUtils.isEmpty(uc2)){
			if("0".equals(uc2.getFirendType())) {
				uc2.setFirendType("1");
				uc2.setCreateTime(now);
			}
			if(StringUtils.isEmpty(uc2.getContactsImId())) {
				UserIm q = new UserIm();
				q.setUserId(uc2.getContactsId());
				List<UserIm> lst = userImService.selectByModel(q);
				if(lst!=null && !lst.isEmpty()) {
					uc2.setContactsImId(lst.get(0).getOpenimId());
				}
			}
			
			userContactsService.update(uc2);
		} else {
			uc2 = new UserContacts();
			uc2.setUserId(emp.getId());
			uc2.setContactsId(user.getId());			
			uc2.setContactsMemo(user.getNickName());
			uc2.setCreateTime(now);
			uc2.setApprFrom("weixin");				
			UserIm q = new UserIm();
			q.setUserId(uc2.getContactsId());
			List<UserIm> lst = userImService.selectByModel(q);
			if(lst!=null && !lst.isEmpty()) {
				uc2.setContactsImId(lst.get(0).getOpenimId());
			}
			uc2.setFirendType("1");
			
			userContactsService.save(uc2);
		}
	}

	private static String getUniqueName() {
		Long time = System.currentTimeMillis()/100;
		byte[] ary = new byte[10];
		int i=0;
		while(time>25) {
			Long c = time%25;
			c=c+97;
			ary[i] = (byte)c.intValue();
			i++;
			time=time/25;
		}
		time=time+97;
		ary[i] = (byte)time.intValue();
		
		String rtn = new String(ary);
		rtn=rtn.trim();

		ActResult<CommUser> act= us.findByEmail(rtn);
		if(!act.isSuccess()) return rtn;
		
		return getUniqueName();
	}
	@RequestMapping("/queryQrPage.user")
	public ModelAndView merchanPage(HttpServletRequest request,Long supplierId,String comName) {
		ModelAndView result = new ModelAndView();
		result.addObject("userId",loginUser.getId());
		UserShare userShare = new UserShare();
		userShare.setUserId(supplierId);
		List<UserShare> userShareList = userShareService.selectByModel(userShare);
		if(userShareList.size()>0){
			BigDecimal cont = BigDecimal.ZERO;
			List<SupplierExchangeProduct> sepList = supplierExchangeProductService.findProductByShareId(userShareList.get(0).getId());
			if(sepList.size()>0) {
				for (SupplierExchangeProduct supplierExchangeProduct : sepList) {
					cont = cont.add(supplierExchangeProduct.getProductPrice());
				}
			}
			result.addObject("cont", cont);
			result.addObject("userShare", userShareList.get(0));
			result.setViewName("qr_code");
		}else{
			result.addObject("supplierId", supplierId);
			result.addObject("comName", comName);
			result.setViewName("generate_qr_code");
		}
		return result;
	}
	/**
	 * 生成员工邀请二维码
	 * @param model
	 * @param supplierId
	 * @param type
	 * @param targetUrl
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("boundQRcode.user")
	public @ResponseBody ActResult<String> boundQRcode(Model model,Long supplierId,Integer type,String targetUrl) {
		Map paramMap = new HashMap();
		paramMap.put("id", supplierId);
		//生成share
		String shareId = HttpClientUtil.sendHttpRequest("post", Constant.CACHE_API_URL +"/supplierList/boundQRcode" ,paramMap);
		ActResult acTicket = JsonUtil.getObject(shareId, ActResult.class);
		if(!acTicket.isSuccess()){
			return ActResult.fail("系统错误");
		}
		paramMap.put("shareId",acTicket.getData());
		if(type!=null&&type==0){//生成微信关注二维码
			//生成微信关注链接
			String wxQr = HttpClientUtil.sendHttpRequest("post", Constant.CACHE_API_URL +"/supplierList/change2wxTempQrCode" ,paramMap);
			ActResult wxQrResult = JsonUtil.getObject(wxQr, ActResult.class);
			if(!wxQrResult.isSuccess()){
				return ActResult.fail("系统错误");
			}
		}
		if(targetUrl!=null&&!"".equals(targetUrl)){//判断是否有跳转路径
			paramMap.put("targetUrl",targetUrl);
			//增加跳转路径
			String resultLink = HttpClientUtil.sendHttpRequest("post", Constant.CACHE_API_URL +"/supplierList/saveTargetActionUrl" ,paramMap);
			ActResult linkResult = JsonUtil.getObject(resultLink, ActResult.class);
			if(!linkResult.isSuccess()){
				return ActResult.fail("系统错误");
			}
		}
		return acTicket;
	}
	/**
	 * 作废连接
	 * @param shareId
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("/delCode.user")
	@ResponseBody
	public ActResult<String> delCode(Long shareId,Long supplierId) {
		if(shareId!=null){
			UserShareTicket ust = new UserShareTicket();
			ust.setShareId(shareId);
			List<UserShareTicket> stList = userShareTicketService.selectByModel(ust);
			if(stList!=null&&stList.size()>0) {
				for (UserShareTicket userShareTicket : stList) {
					userShareTicketService.removeById(userShareTicket.getId());
				}
			}
			userShareService.removeById(shareId);
			return ActResult.success(supplierId);
		}else{
			return ActResult.fail("系统错误");
		}
	}
	/**
	 * 分享邀请成功调用
	 * @param shareId
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("/shareSuccess.user")
	@ResponseBody
	public ActResult<String> shareSuccess(HttpServletRequest request,HttpServletResponse response) {
		//先从缓存获取分享数据，有不操作，无操作。
		String data = redisUtil.getData("shareSuccess_"+loginUser.getId());
		if (StringUtils.isEmpty(data)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				// 当前时间
				Calendar calendar = Calendar.getInstance();
				long newTime = calendar.getTime().getTime();// 当前毫秒数
				calendar.add(Calendar.DATE, 1);// 加一天
				String afterDate = sdf.format(calendar.getTime());
				long endTime = sdf.parse(afterDate).getTime();
				int a = new Long(endTime).intValue();
				int b = new Long(newTime).intValue();
				int c = (a - b) / 1000;
				redisUtil.setData("shareSuccess_" + loginUser.getId(), c + "", c);
				this.sendUserFuli(loginUser, "30.00", "平台向您发放内购券30.00元！您可以登陆 我的福利商城购买内购产品，或分享给亲友，享受大牌员工福利！", "auto", null);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return ActResult.success("分享成功,奖励已发放");
		}else {
			return ActResult.fail("");
		}
		
	}
}
