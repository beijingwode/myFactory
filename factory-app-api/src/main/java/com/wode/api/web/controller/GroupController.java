package com.wode.api.web.controller;


import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.nutz.dao.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.api.util.JPushUtils;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.GroupBuy;
import com.wode.factory.model.GroupBuyProduct;
import com.wode.factory.model.GroupBuyUser;
import com.wode.factory.model.GroupSuborderitem;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.model.Shop;
import com.wode.factory.model.Specification;
import com.wode.factory.model.UserDevice;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserImGroup;
import com.wode.factory.model.UserImGroupMember;
import com.wode.factory.model.UserShare;
import com.wode.factory.service.GroupBuyProductService;
import com.wode.factory.service.GroupBuyService;
import com.wode.factory.service.GroupBuyUserService;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsImageService;
import com.wode.factory.user.facade.GroupBuyFacade;
import com.wode.factory.user.facade.GroupOrdersFacade;
import com.wode.factory.user.service.GroupOrdersService;
import com.wode.factory.user.service.GroupSuborderItemService;
import com.wode.factory.user.service.InventoryService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.ShopService;
import com.wode.factory.user.service.UserContactsService;
import com.wode.factory.user.service.UserDeviceService;
import com.wode.factory.user.service.UserImGroupMemberService;
import com.wode.factory.user.service.UserImGroupService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.service.UserShareService;
import com.wode.factory.user.util.EasemobIMUtils;
import com.wode.factory.user.vo.ProductSpecificationsVo;
import com.wode.factory.vo.GroupBuyProductVo;
import com.wode.factory.vo.GroupBuyUserVo;
import com.wode.factory.vo.GroupBuyVo;
import com.wode.factory.vo.GroupOrdersVo;
import com.wode.factory.vo.SupplierShopVo;
import com.wode.search.WodeResult;
import com.wode.search.WodeSearchManager;
import com.wode.search.WodeSearcher;
/**
 * 团购
 * @author user
 *
 */

@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/group")
public class GroupController extends BaseController{
	
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
	
	@Autowired
	private ProductLadderService productLadderService;

	@Autowired
    private ShopService shopService;
	@Autowired
	private GroupBuyService groupBuyService;
	
	@Autowired
	private GroupBuyUserService groupBuyUserService;
	
	@Autowired
	private GroupOrdersService groupOrdersService;
	@Autowired
	private UserImGroupService userImGroupService;
	
	@Autowired
	private GroupSuborderItemService groupSuborderItemService;
	@Autowired
	private UserImGroupMemberService userImGroupMemberService;

	@Autowired
	private UserDeviceService userDeviceService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserContactsService userContactsService;
	@Autowired
	private GroupBuyProductService groupBuyProductService;
	@Autowired
	private GroupOrdersFacade groupOrdersFacade;
	@Autowired
	private ProductService productService;
	@Autowired
	ProductSpecificationsService specificationsService;
	
	@Autowired
    private WodeSearchManager wsm;
	
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private GroupBuyFacade groupBuyFacade;

	@Autowired
	private ProductSpecificationsImageService productSpecificationsImageService;
	
	@Autowired
	UserShareService userShareService;
	/**
	 * 新建或修改团购单
	 * @param gb
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/createGroup.user")
	@ResponseBody
	public ActResult<GroupBuyVo> createGroup(GroupBuyVo gbVo,String productIds, HttpServletRequest request, HttpServletResponse response) {
		ActResult<GroupBuyVo> act = new ActResult<GroupBuyVo>();
		Long id = gbVo.getId();
		if(!StringUtils.isEmpty(gbVo.getGroupName())||!StringUtils.isEmpty(productIds)){
			act = groupBuyService.create(gbVo,loginUser,productIds);
			if(act.isSuccess()){
				List<GroupBuyProduct> groupBuyProductList = groupBuyFacade.createGroupBuyProduct(gbVo.getId(),productIds);
				act.getData().setGroupBuyProductList(groupBuyProductList);
			}
		}else{
			act.fail("参数错误");
		}
		if(id == null){
			String imgroupId = userContactsService.createGroupBuyGroup(gbVo.getUserId(), "" + gbVo.getId());
			if(!StringUtils.isEmpty(imgroupId)) {
				UserImGroup im= userImGroupService.getById(NumberUtil.toLong(imgroupId));
				if(im!=null) {
					try {
						List<UserImGroup> groups = new ArrayList<UserImGroup>();
						groups.add(im);
						String name =loginUser.getNickName() == null ? loginUser.getUserName() : loginUser.getNickName();
						String msg="@"+name+"团长好！"+"\n"+"团越多，省越多~"+"\n"+"拼团成功后，团长还能获取内购券奖励哦~"+"\n"+"点击邀请好友";
						if(groups!=null && !groups.isEmpty()) {
							EasemobIMUtils.shoppingGroupMessage(msg, groups, loginUser.getId(), null, name,"create");
						}
					} catch (Exception e) {
					}
				}
			}
		}
		return act;
	}
	
	@RequestMapping("/groupMemberAdd.user")
	@ResponseBody
	public ActResult<UserImGroup> groupMemberAdd(HttpServletRequest request,Long groupBuyId,String members){
		List<UserImGroupMember> adds = null;
		if(StringUtils.isEmpty(members)) {
			adds = new ArrayList<UserImGroupMember>();
		} else {
			adds = userImGroupMemberService.selectByImIds(members.split(","));
		}
		GroupBuy g = groupBuyService.getById(groupBuyId);
		if(g==null) {
			return ActResult.fail("一起购 团信息取得失败，请刷新后重试");
		} else {
			if(g.getLimitedTime()==1) {
				Date now = new Date();
				if(g.getEndTime().compareTo(now)<=0) {
					return ActResult.fail("一起够 团已经结束，不能再邀请队员了");
				}
			}
			if(adds!=null && !adds.isEmpty()) {
				Shop shop = shopService.getShopSettingById(g.getShopId());
				Map<String,String> ext = new HashMap<String,String>();
		    	ext.put("photoImage", loginUser.getAvatar());			//邀请人头像
		    	ext.put("invitedUserName", loginUser.getNickName());				//邀请人姓名
		    	ext.put("groupBuyId", groupBuyId+"");	//团id
		    	ext.put("shopName", shop.getShopname());	//店铺名称
		    	ext.put("shopId", shop.getId()+"");	//店铺id
				for (UserImGroupMember userImGroupMember : adds) {
					GroupBuyUserVo mem=groupBuyUserService.getByUserIdAndGroupId(userImGroupMember.getUserId(), g.getId()+"");
					if(mem==null) {
						GroupBuyUser m=new GroupBuyUser();
						m.setShopId(g.getShopId());
						m.setGroupId(g.getId());
						m.setUserId(userImGroupMember.getUserId());
						m.setCreateTime(new Date());
						m.setIsLadder(0);
						m.setIsAdd(0);
						m.setUserName(userImGroupMember.getNickname());
						m.setStatus(0);
						
						groupBuyUserService.save(m);
						
						//将团长邀请的成员直接加入到群聊中
//						userContactsService.createGroupBuyGroup(m.getUserId(), "" + m.getGroupId());
						
						//查询已经登录的设备
						UserDevice query = new UserDevice();
						query.setUserId(userImGroupMember.getUserId());
						query.setStatus("1");
						List<UserDevice> ls = userDeviceService.findByAttribute(query);
						if(ls!=null && !ls.isEmpty()) {
							for (UserDevice userDevice : ls) {
								try {
									JPushUtils.sendMessage("叮咚~您收到了一个“一起购”邀请!"+loginUser.getNickName()+"邀您一起省钱咯~", "一起购邀请", ext,
											JPushUtils.formatDriver(userDevice.getDeviceType()), "alias", null,userDevice.getAsname());
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
						}
					}
				}
				
				return ActResult.success("");
			} else {
				return ActResult.fail("操作失败，请刷新后重试。");
			}
		}
	}

	/**
	 * 用户点击立即参团
	 * @param userId
	 * @param groupBuyId
	 * @return
	 */
	@RequestMapping("/userMsgUpdate.user")
	@ResponseBody
	public ActResult<QueryResult> userMsgUpdate(Long groupId){
		//修改加入成员的团内状态
		groupBuyUserService.updateStatusByUserId(loginUser.getId(),groupId);
		//将成员直接加入到群聊中
		String imgroupId = userContactsService.createGroupBuyGroup(loginUser.getId(), "" + groupId);
		if(!StringUtils.isEmpty(imgroupId)) {
			UserImGroup im= userImGroupService.getById(NumberUtil.toLong(imgroupId));
			if(im!=null) {
				try {
					List<UserImGroup> groups = new ArrayList<UserImGroup>();
					groups.add(im);
					String name =loginUser.getNickName() == null ? loginUser.getUserName() : loginUser.getNickName();
					String msg="@"+name+"欢迎加入！"+"\n"+"团越多，省越多。"+"\n"+"拼团完成后，由团长统一收货。"+"\n"+"点击选购商品";
					if(groups!=null && !groups.isEmpty()) {
						EasemobIMUtils.shoppingGroupMessage(msg, groups, loginUser.getId(), null, name,"add");
					}
				} catch (Exception e) {
				}
			}
		}
		return ActResult.success("");
	}
	
	/**
	 * 分页查询 我的一起购列表
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("query.user")
	@ResponseBody
	public ActResult<QueryResult> query(Integer fromType,Integer page, Integer pageSize,HttpServletRequest request, HttpServletResponse response) {
		if (page == null || page == 0) {
			page = 1;
		}
		if (pageSize ==null || pageSize < 1) {
			return ActResult.fail("每页显示数据不能为0");
		}
		QueryResult rtn = groupBuyService.query(fromType,loginUser.getId(), page, pageSize);
		for (Object obj : rtn.getList()) {
			GroupBuyVo groupBuyVo = (GroupBuyVo)obj;
			//获取团的可够商品
			List<GroupBuyProduct> groupBuyProductlist = groupBuyProductService.findByGroupId(groupBuyVo.getId());
			groupBuyVo.setGroupBuyProductList(groupBuyProductlist);
			if("1".equals(groupBuyVo.getJoinStatus())) {
				// 已参团
				
				// 设置群聊信息
				UserImGroup im = userImGroupService.getById(NumberUtil.toLong(groupBuyVo.getIm_groupId()));;
				if(im!=null) {
					groupBuyVo.setUserImImGroupId(im.getImGroupId());
					groupBuyVo.setUserImGroupname(im.getGroupname());	
				}
				GroupOrdersVo vo = new GroupOrdersVo();
				List<GroupOrdersVo> ls = groupOrdersService.getByGroupIdAndUserId(groupBuyVo.getId(), loginUser.getId());
				if(ls != null && !ls.isEmpty()) {
					vo.setGroupSuborderitemList(groupSuborderItemService.findByOrderId(ls.get(0).getOrderId()));
					groupBuyVo.setGroupOrder(vo);
				}
			}
		}
		
		// 查询购物团列表
		return ActResult.success(rtn);
	}
	
	/**
	 * 通过店铺id获取团购邀请数量
	 * @param shopId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getApplyByShop.user")
	@ResponseBody
	public ActResult<Object> getApplyByShop(Long shopId,String productIds,HttpServletRequest request, HttpServletResponse response) {
		return ActResult.success(groupBuyUserService.getApplyByShop(shopId,loginUser.getId(),productIds));
	}
	
	/**
	 * 通过用户id获取所有的一起团数量
	 * @param shopId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getAllCount.user")
	@ResponseBody
	public ActResult<Object> getAllCount(HttpServletRequest request, HttpServletResponse response) {
		return ActResult.success(groupBuyUserService.getAllCount(loginUser.getId()));
	}
	
	/**
	 * 微信创建团购页面
	 * @return
	 */
	@RequestMapping(value = {"/page.user"})
    public ModelAndView page(HttpServletRequest request, ModelAndView model,Long shopId,String fromWay,String productIds) {
		model.addObject("shopId", shopId);
		model.addObject("fromWay", fromWay);
		model.addObject("productIds", productIds);
		model.addObject("nickName", loginUser.getNickName());
		model.setViewName("group/tuan_mass_set");//创建页面
		return model;
	}
	@RequestMapping(value = {"/chooseGroupBuy.user"})
    public ModelAndView chooseGroupBuy(HttpServletRequest request, ModelAndView model,Long shopId,String sku_nums,String productIds) {
		model.addObject("shopId", shopId);
		model.addObject("sku_nums", sku_nums);
		model.addObject("productIds",productIds);
		model.addObject("userType", loginUser.getEmployeeType());
		model.setViewName("groupBuyList");//选择页面
		return model;
	}
	/**
	 * 通过群id查询订单
	 * @param imId
	 * @return
	 */
	@RequestMapping(value = {"/queryOrders.user"})
	@ResponseBody
	public ActResult<Object> queryOrders(Long imId){
		GroupBuy buy =  groupOrdersService.getByBuyId(imId);
		List<GroupOrdersVo> byGroupIdAndUserId = groupOrdersService.getByGroupIdAndUserId(buy.getId(),loginUser.getId());
		return ActResult.success(byGroupIdAndUserId);
	}
	/**
	 * 跳转到我的订单
	 * @param request
	 * @param model
	 * @param groupId
	 * @return
	 */
	/*@RequestMapping(value = {"/page_my_order.user"})
	public ModelAndView myOrdersPage(HttpServletRequest request,ModelAndView model,Long groupId){
		model.addObject("groupId",groupId);
		model.setViewName("my_order");
		return model;
	}*/
	/**
	 * 我的订单
	 * @param request
	 * @param model
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = {"/my_order.user"})
	@ResponseBody
	public ActResult<Object> myOrders(Long groupId,Long imGroupId){
		if(groupId==null){
			GroupBuyVo groupBuy = groupOrdersService.findByBuyId(imGroupId,groupId);
			if(groupBuy!=null){
				List<GroupBuyProduct> groupBuyProductList = groupBuyProductService.findByGroupId(groupBuy.getId());
				groupBuy.setGroupBuyProductList(groupBuyProductList);
				List<GroupOrdersVo> byGroupIDAndUserId = groupOrdersService.getByGroupIdAndUserId(groupBuy.getId(), loginUser.getId());
				List<GroupOrdersVo> GroupOrdersList = mains(byGroupIDAndUserId,groupBuy.getId(),groupBuy);
				groupBuy.setGroupOrdersList(GroupOrdersList);
				return ActResult.success(groupBuy);
			}else{
				return ActResult.fail("参数错误");
			}
			/*for (GroupOrdersVo groupOrders : byGroupIDAndUserId) {
				groupOrders.setGroupBuy(groupBuy);
			}*/
		}else{
			GroupBuyVo groupBuy = groupOrdersService.findByBuyId(imGroupId,groupId);
			List<GroupBuyProduct> groupBuyProductList = groupBuyProductService.findByGroupId(groupBuy.getId());
			groupBuy.setGroupBuyProductList(groupBuyProductList);
			List<GroupOrdersVo> byGroupIDAndUserId = groupOrdersService.getByGroupIdAndUserId(groupId, loginUser.getId());
			for (GroupOrdersVo groupOrders : byGroupIDAndUserId) {
				List<GroupSuborderitem> groupSuborderitemList = groupSuborderItemService.findByOrderId(groupOrders.getOrderId());
				//groupOrders.setGroupBuy(groupBuy);
				groupOrders.setGroupSuborderitemList(groupSuborderitemList);
			}
			List<GroupOrdersVo> GroupOrdersList = mains(byGroupIDAndUserId,groupId,groupBuy);
			groupBuy.setGroupOrdersList(GroupOrdersList);
			return ActResult.success(groupBuy);
		}
	}
	
	/**
	 * 创建团时根据店铺id,获取店铺全部可够商品(团长操作)
	 * @param shopId
	 * @return
	 */
	@RequestMapping(value = {"/getShopProducts.user"})
	@ResponseBody
	public ActResult<Object> getShopProducts(Long shopId){
		WodeSearcher searcher = wsm.getSearcher();
		searcher.setPageSize(500);
        String queryString = "shop=" + shopId + "&saleKbn=0X&page=" + 1;
        WodeResult result = searcher.search(queryString, null, false,false,false);
        List<HashMap<String, Object>> hits = result.getHits();
        for (int i = 0; i < hits.size(); i++) {
        	Map map=(Map)hits.get(i);
        	String productId = MapUtils.getString(map, "id");
        	Long skuId = MapUtils.getLong(map, "minSkuId");
        	//最小阶梯价
        	List<ProductLadder> listBySkuid = productLadderService.getListBySkuid(skuId);
        	if(listBySkuid != null && listBySkuid.size() > 0  ){
        		map.put("priceEx", listBySkuid.get(0).getPrice());
        	}
        	//起售数量
//        	ProductSpecifications productSpecifications = productSpecificationsService.findByIdCache(skuId,productId);
//        	if(productSpecifications != null){
//        		map.put("min_limit_num", productSpecifications.getMinLimitNum());
//        	}
        }
		return ActResult.success(result);
	} 
	
	/**
	 * 查询团内可够商品(团员操作)
	 * @param groupId
	 * @param showSkuConut
	 * @return
	 */
	@RequestMapping(value = {"/getGroupBuyProducts.user"})
	@ResponseBody
	public ActResult<Object> getGroupBuyProducts(Long groupId,String showSkuConut){
		if(groupId == null){
			return ActResult.fail("操作失败，请刷新后重试。");
		}
		GroupBuyVo groupBuyVo = groupBuyService.getById(groupId);
		SupplierShopVo shop = shopService.findShopByShopIdCache(groupBuyVo.getShopId());
		List<GroupBuyProduct> groupBuyProductlist = groupBuyProductService.findByGroupId(groupId);
		if(shop!=null)groupBuyProductlist.get(0).setExp4(shop.getShopname());
		if("1".equals(showSkuConut)){
			for (GroupBuyProduct groupBuyProduct : groupBuyProductlist) {
				//获取团内已购商品规格的数量值
				Integer suborderItemSum = groupSuborderItemService.getSuborderItemSum(groupBuyProduct.getGroupId().toString(), groupBuyProduct.getProductId(), groupBuyProduct.getSkuId().toString());
				groupBuyProduct.setPurchasedNum(suborderItemSum);
			}
		}
		return ActResult.success(groupBuyProductlist);
	}
	
	/**
	 * 获取指定商品详情
	 * @param groupId
	 * @param productId
	 * @param skuId
	 * @return
	 */
	@RequestMapping(value = {"/getGroupBuyProductNumber.user"})
	@ResponseBody
	public ActResult<Object> getGroupBuyProducts(Long groupId,Long productId,Long skuId){
		if(groupId == null || productId == null){
			return ActResult.fail("操作失败，请刷新后重试。");
		}
		GroupBuyProductVo groupBuyProductVo = new GroupBuyProductVo();
		groupBuyProductVo.setProductId(productId);
		groupBuyProductVo.setSmap(productService.findSku4ShowCache(productId));
		Map<String,String> skuMap = new HashMap<String, String>();
		Map<String,String> stockMap = new HashMap<String, String>();
		List<ProductSpecifications> psList = productService.findSku(productId);
		List<String> ids = new ArrayList<String>();
		int size = groupBuyProductVo.getSmap().keySet().size();
		if (size==1) {//一个规格
			for (List<ProductSpecificationValue> psvs : groupBuyProductVo.getSmap().values()) {
				for (ProductSpecificationValue psv : psvs) {
					ids.add(psv.getId()+"");
				}
			}
		} else {
			List<ProductSpecificationValue> psvs1=null;
			List<ProductSpecificationValue> psvs2=null;
			int i=1;
			for (List<ProductSpecificationValue> psvs : groupBuyProductVo.getSmap().values()) {
				if(i==1) {
					psvs1 = psvs;
				} else if(i==2) {
					psvs2 = psvs;
				}
				i++;
			}
			
			for (ProductSpecificationValue psv1 : psvs1) {
				for (ProductSpecificationValue psv2 : psvs2) {
					if(psv1.getId().compareTo(psv2.getId())>0) {
						ids.add(psv2.getId()+","+psv1.getId());
					} else {
						ids.add(psv1.getId()+","+psv2.getId());
					}
				}
			}
		}
		 
		for(ProductSpecifications ps : psList){
			if(!skuMap.containsKey(ps.getItemids())){
				Integer repertory = inventoryService.getInventoryFromRedis(ps.getId());//每一个sku对应的库存
				stockMap.put(ps.getItemids(), repertory.toString());
				skuMap.put(ps.getItemids(), ps.getId()+"");
			}
		}
		
		for (String string : ids) {
			if(!skuMap.containsKey(string)) {
				skuMap.put(string, "0");
			}
			if(!stockMap.containsKey(string)) {
				stockMap.put(string, "0");
			}
		}
		//默认选中规格策略
		Map<String, String> strMap = new LinkedHashMap<String, String>();
		ProductSpecifications show = productSpecificationsService.findByIdCache(skuId,productId.toString()+"");
		Set<String> list = new HashSet<String>();
		if(show.getItemids().indexOf(",")==-1){
			list.add(show.getItemids() );
		}else{
			String[] array=show.getItemids().split(",");
			list.addAll(Arrays.asList(array));
		}
		Iterator<String> it=list.iterator();
		while (it.hasNext()) {
			String skuValueid=it.next();
			Specification s = productService.findByItemsValueCache(skuValueid);
			strMap.put(s.getName(), skuValueid);
		}
		//获取每个sku团已购数量
		Map<String, Integer> skuPurchasedNumMap = new HashMap<String, Integer>();
		for (ProductSpecifications productSpecifications : psList) {
			Integer suborderItemSum = groupSuborderItemService.getSuborderItemSum(groupId.toString(), productId, productSpecifications.getId().toString());
			skuPurchasedNumMap.put(productSpecifications.getId().toString(), suborderItemSum);
		}
		groupBuyProductVo.setSkuMap(skuMap);
		groupBuyProductVo.setStockMap(stockMap);
		groupBuyProductVo.setStrMap(strMap);
		groupBuyProductVo.setSkuPurchasedNumMap(skuPurchasedNumMap);
		return ActResult.success(groupBuyProductVo);
	}
	
	/**
	 * 根据所选可够商品sku获取阶梯价和团购价以及团内已购数量
	 * @param skuId
	 * @param productId
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = {"/getProductSkuNumberAndPrice.user"})
	@ResponseBody
	public ActResult<Object> getProductSkuNumberAndPrice(Long skuId,Long productId,Long groupId,Integer quantity){
		if(skuId==null|| productId==null || groupId==null ||quantity ==null) {
			return ActResult.fail("参数错误");
		}
		Integer suborderItemSum = groupSuborderItemService.getSuborderItemSum(groupId.toString(), productId, skuId.toString());
		Integer suborderItemSums = suborderItemSum +quantity; 
		List<ProductLadder> ladderList = productLadderService.getListBySkuid(skuId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("suborderItemSum", suborderItemSum);//团内已购数量
		ProductSpecificationsVo productSpecificationsVo = productSpecificationsService.findByIdCache(skuId, productId.toString());
		Product p = productService.findBySku(skuId);
		UserFactory user = userService.getById(loginUser.getId());
		productSpecificationsService.resetPrice(productSpecificationsVo,p,user,false,suborderItemSums);
		map.put("internalPurchasePrice", productSpecificationsVo.getInternalPurchasePrice());//内购价
		map.put("stock", inventoryService.getInventoryFromRedis(skuId));//库存
		map.put("ladderPrice", productSpecificationsVo.getInternalPurchasePrice());//阶梯价
		map.put("minLimitNum", productSpecificationsVo.getMinLimitNum());//起售数量
		map.put("productId", productId);
		map.put("skuId", skuId);
		map.put("groupId", groupId);
		map.put("itemValues", productSpecificationsVo.getItemValues());
		map.put("marketPrice", productSpecificationsVo.getPrice());//市场价
		map.put("maxFucoin", productSpecificationsVo.getMaxFucoin());//福利卷
		List<ProductSpecificationsImage> productImage = productSpecificationsImageService.findProductPicture(productSpecificationsVo.getId(), productId);
		map.put("imageSource", productImage.get(0).getSource());//规格图片集
		// 还差多少件到达阶梯价
		if (ladderList!= null && ladderList.size() > 0) {
			//根据数量排序
			Collections.sort(ladderList, new Comparator<ProductLadder>() {
				public int compare(ProductLadder o1, ProductLadder o2) {
	                return o1.getNum().compareTo(o2.getNum());
	            }
	     	});
			ProductLadder productLadder = null;
			for (ProductLadder pLadder : ladderList) {
				if (suborderItemSums < pLadder.getNum()) {
					productLadder = pLadder;
					break;
				} 
			}
			if(productLadder == null) productLadder = ladderList.get(ladderList.size()-1);
			
			map.put("badNumber", productLadder.getNum() - suborderItemSums);
			BigDecimal ladderPrice = BigDecimal.ZERO;
			if(1==productLadder.getType()){//折扣
				ladderPrice = productLadder.getPrice().multiply(new BigDecimal(0.1)).multiply(productSpecificationsVo.getPrice());
				ladderPrice = ladderPrice.setScale(2,BigDecimal.ROUND_HALF_UP);
			}else{
				ladderPrice =  productLadder.getPrice();
			}
			map.put("ladderPrice", ladderPrice);
		} else {
			map.put("badNumber", 0);
			map.put("ladderPrice", 0);
		}
		return ActResult.success(map);
	}
	
	/**
	 * 查询团员订单
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/tuan_order.user"})
	@ResponseBody
	public ActResult<Object> tuanOder(Long groupId){
		List<GroupOrdersVo>  list = groupOrdersService.getByGroupID(groupId,loginUser.getId());
		GroupBuyVo buyVo = groupBuyService.getById(groupId);
		BigDecimal groupSaveAmonut = (buyVo.getAllTotalPrice().add(buyVo.getAllTotalShipping())).subtract((buyVo.getTotalPrice().add(buyVo.getTotalShipping())));

		Map<Long,Integer> allSkuNum = new HashMap<Long,Integer>();		//group 内sku数量
		Map<Long,Long> skuProduct = new HashMap<Long,Long>();		
		Map<Long,Map<Long,Integer>> userSkuNum = new HashMap<Long,Map<Long,Integer>>(); 	//orders 内sku数量
		for (GroupOrdersVo groupOrders : list) {
			UserFactory user = userService.getById(groupOrders.getUserId());
			GroupBuyUserVo byUserIdAndGroupId = groupBuyUserService.getByUserIdAndGroupId(user.getId(), groupId.toString());
			List<GroupSuborderitem> groupSuborderitemList = groupSuborderItemService.findByOrderId(groupOrders.getOrderId());
			// 合计group 内sku数量，orders 内sku数量
			Map<Long,Integer> skuNum = new HashMap<Long,Integer>();		//orders 内sku数量
			for (GroupSuborderitem groupSuborderitem : groupSuborderitemList) {
				if(!skuNum.containsKey(groupSuborderitem.getSkuId())) {
					skuNum.put(groupSuborderitem.getSkuId(), 0);
				}
				if(!allSkuNum.containsKey(groupSuborderitem.getSkuId())) {
					allSkuNum.put(groupSuborderitem.getSkuId(), 0);
				}
				skuNum.put(groupSuborderitem.getSkuId(), skuNum.get(groupSuborderitem.getSkuId())+groupSuborderitem.getNumber());
				allSkuNum.put(groupSuborderitem.getSkuId(), allSkuNum.get(groupSuborderitem.getSkuId())+groupSuborderitem.getNumber());
				skuProduct.put(groupSuborderitem.getSkuId(), groupSuborderitem.getProductId());
				
			}
			userSkuNum.put(groupOrders.getOrderId(), skuNum);
			
			groupOrders.setUserAvatar(user.getAvatar());
			groupOrders.setCommanderName(user.getNickName());
			groupOrders.setPhoneNum(user.getPhone());
			groupOrders.setGroupSuborderitemList(groupSuborderitemList);
			groupOrders.setIsLadder(byUserIdAndGroupId.getIsLadder());
			groupOrders.setGroupSaveAmonut(groupSaveAmonut);
		}

		// 计算orders节省金额= 商品节省金额（内购价-最终阶梯价）+运费节省金额
		for (GroupOrdersVo groupOrders : list) {
			BigDecimal saveProductAmonut= BigDecimal.ZERO;
			Map<Long,Integer> skuNum = userSkuNum.get(groupOrders.getOrderId());
			for (Long skuId : skuNum.keySet()) {
				// 根据团内sku 总数量计算阶梯价
				BigDecimal ladderPrice = productLadderService.getPriceBySkuidAndPrice(skuId, allSkuNum.get(skuId));
				if(ladderPrice != null){
					// 阶梯价格*订单内sku个数 计算最终金额
					BigDecimal ladderPrices = ladderPrice.multiply(new BigDecimal(skuNum.get(skuId)));
					// 内购价**订单内sku个数 计算单独购买所需金额
					ProductSpecificationsVo sku = productSpecificationsService.findByIdCache(skuId, skuProduct.get(skuId).toString());
					BigDecimal neigoujia = sku.getInternalPurchasePrice().multiply(new BigDecimal(skuNum.get(skuId)));
					saveProductAmonut = saveProductAmonut.add(neigoujia.subtract(ladderPrices));
				}
			}
			// 计算每orders运费节省金额
			BigDecimal subtract = groupOrders.getTotalShipping().subtract(groupOrders.getNowShipping());
			saveProductAmonut = saveProductAmonut.add(subtract);
			groupOrders.setSaveProductAmonut(saveProductAmonut);
		}

		Map<String, Object> postageStrategy = groupOrdersFacade.postageStrategy(groupId);
		String msg="";
		if(postageStrategy.get("priceNumMatchExpress")!=null) {
			msg+="priceNumMatchExpress="+postageStrategy.get("priceNumMatchExpress")+",";
		}
		if(postageStrategy.get("buyNumMatchExpress")!=null) {
			msg+="buyNumMatchExpress="+postageStrategy.get("buyNumMatchExpress")+",";
		}
		//查询差多少包邮
		ActResult result = new ActResult();
		result.setData(list);
		result.setMsg(msg);
		return result;
	}
	
	/**
	 * 跳转到团员订单页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/goTuanProduct.user"})
	public ModelAndView goTuanProduct(ModelAndView model,Long groupId){
		model.addObject("groupId",groupId);
		model.setViewName("tuan_order");
		return model;
	}
	/**
	 * 跳转到团可购商品页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/goTuanCheckProduct.user"})
	public ModelAndView goTuanCheckProduct(ModelAndView model,Long shopId){
		model.addObject("shopId",shopId);
		model.setViewName("group/tuan_buy");
		return model;
	}
	/**
	 * 跳转我的一起购列表页面
	 */
	@RequestMapping(value = {"/page_ibuy.user"})
	public ModelAndView iBuyItTogetherPage(HttpServletRequest request,ModelAndView model){
		model.setViewName("I_buy_it_together");
		return model;
	}

	/**
	 * 购物团详情
	 * @param request
	 * @param response
	 * @param groupId
	 * @return
	 */
	/*@RequestMapping("detail.user")
	@ResponseBody
	public ActResult<GroupBuyVo> detail(HttpServletRequest request, HttpServletResponse response,Long groupId) {
		if(groupId==null) return ActResult.fail("参数错误");
		return ActResult.success(groupBuyService.getById(groupId));
	}*/
	/**
	 * 查询当前店铺可以选择的购物团列表
	 * @param page
	 * @param pageSize
	 * @param shopId
	 * @return
	 */
	@RequestMapping("groupShopList.user")
	@ResponseBody
	public ActResult<Object> groupShopList(Long shopId,HttpServletRequest request, HttpServletResponse response,String sku_nums,String productIds) {
		// 查询购物团列表
		if(shopId==null) return ActResult.fail("参数错误");
		List<String> nums = new ArrayList<String>();
		List<String> skus = new ArrayList<String>();
		Boolean flag =false;
		if(!StringUtils.isEmpty(sku_nums)) {
			flag =true;
		}
		Long[] skuIds = null;
		Integer[] numarrays = null;
		if(flag) {
			String[] arySkuf = sku_nums.substring(0, sku_nums.length()).split(",");
			for (String str : arySkuf) {
				String[] strs = str.split("_");
				skus.add(strs[0]);
				nums.add(strs[1]);
			}
			skuIds =new Long[skus.size()];
			numarrays =new Integer[skus.size()];
			for (int i = 0; i < nums.size(); i++) {
				skuIds[i] = Long.parseLong(skus.get(i));
				numarrays[i] = Integer.parseInt(nums.get(i));
			}
		}
		List<GroupBuyVo> list = groupBuyUserService.getGroupBuyList(loginUser.getId(),shopId,productIds);
		if(null != list) {
			for (GroupBuyVo groupBuyVo : list) {
				if(flag) {
					groupBuyVo.setSaveShippingFee(groupOrdersService.getSaveShippingFee(loginUser,groupBuyVo.getId(), skuIds, numarrays));
				}
			}
			for (GroupBuyVo groupBuyVo : list) {
				if(groupBuyVo.getLimitedTime() == 1){
					groupBuyVo.setCountDown(printDifference(new Date(),groupBuyVo.getEndTime()));
				}
			}
		}
		
		return ActResult.success(list);
	}
	/**
	 * 获取团内可购商品
	 * @param imGroupId
	 * @return
	 */
	@RequestMapping("groupProductCount.user")
	@ResponseBody
	public ActResult groupProductCount(String imGroupId){
		ActResult result = new ActResult();
		Integer count = 0;
		if(StringUtils.isEmpty(imGroupId)){
			result.fail("参数错误");
		}else {
			UserImGroup uig = new UserImGroup();
			Date date = new Date();
			uig.setImGroupId(imGroupId);
			List<UserImGroup> selectByModel = userImGroupService.selectByModel(uig);
			for (UserImGroup userImGroup : selectByModel) {
				GroupBuyVo findByBuyId = groupOrdersService.findByBuyId(userImGroup.getId(),null);
				if(findByBuyId.getStatus()==1 || findByBuyId.getOrderStatus()==0){
					if(findByBuyId.getLimitedTime()==1){
						if(findByBuyId.getEndTime().getTime()>date.getTime()){
							count = groupBuyProductService.findByCount(findByBuyId.getId());
						}else{
							count = 0;
						}
					}else{
						count = groupBuyProductService.findByCount(findByBuyId.getId());
					}
				}else{
					count = 0;
				}
			}
		}
		result.setData(count);
		return result;
	}
	@RequestMapping(value = { "/test.user" })
	@ResponseBody
	public ActResult<Object> test(Long productId) throws NumberFormatException, IllegalAccessException, InvocationTargetException {
		ActResult<Object> result = new ActResult<Object>(); 
		Integer count = groupSuborderItemService.getCountByUserAndProduct(productId, loginUser.getId());
		result.setData(count);
		return result;
	}
	/**
	 * 计算时间差返回天时分
	 * @param startDate
	 * @param endDate
	 * @return
	 */
    private static String printDifference(Date startDate, Date endDate){
        long different = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        //天
        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;
        //时
        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        //分
        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;
        //秒
        long elapsedSeconds = different / secondsInMilli;
        return elapsedDays+"天"+elapsedHours+"时"+elapsedMinutes+"分";
    }
    @RequestMapping(value = {"/toCanBuy.user"})
	public ModelAndView toCanBuy(Long groupId,Integer type,Long shareId,HttpServletRequest request,ModelAndView model){
    	if(type == null){
    		type = 0;
    	}
    	if(shareId != null){
    		UserShare vo = userShareService.getById(shareId);
    		if(vo != null){
    			model.addObject("userNick", vo.getUserNick());
    			model.addObject("userAvatar", vo.getUserAvatar());
    		}
    	}
    	model.addObject("groupId", groupId);
    	model.addObject("type", type);
		model.setViewName("group/can_buy");
		return model;
	}
    @RequestMapping(value = { "/detail.user" })
	public ModelAndView detail(HttpServletRequest request,ModelAndView model,Long productId,Long skuId){
    	model.addObject("productId", productId);
    	model.addObject("specificationsId", skuId);
    	model.setViewName("group/tuan_product");
		return model;
	}
    
    /**
     * 计算处理商品阶梯价(包含已省金额)
     * @param lists
     * @param groupId
     * @param buyVo
     * @return
     */
    private List<GroupOrdersVo> mains(List<GroupOrdersVo> lists,Long groupId,GroupBuyVo buyVo){
    	Map<Long,Integer> allSkuNum = new HashMap<Long,Integer>();		//group 内sku数量
		Map<Long,Long> skuProduct = new HashMap<Long,Long>();		
		Map<Long,Map<Long,Integer>> userSkuNum = new HashMap<Long,Map<Long,Integer>>(); 	//orders 内sku数量
		for (GroupOrdersVo groupOrders : lists) {
			UserFactory user = userService.getById(groupOrders.getUserId());
			GroupBuyUserVo byUserIdAndGroupId = groupBuyUserService.getByUserIdAndGroupId(user.getId(), groupId.toString());
			List<GroupSuborderitem> groupSuborderitemList = groupSuborderItemService.findByOrderId(groupOrders.getOrderId());
			// 合计group 内sku数量，orders 内sku数量
			Map<Long,Integer> skuNum = new HashMap<Long,Integer>();		//orders 内sku数量
			for (GroupSuborderitem groupSuborderitem : groupSuborderitemList) {
				if(!skuNum.containsKey(groupSuborderitem.getSkuId())) {
					skuNum.put(groupSuborderitem.getSkuId(), 0);
				}
				if(!allSkuNum.containsKey(groupSuborderitem.getSkuId())) {
					allSkuNum.put(groupSuborderitem.getSkuId(), 0);
				}
				skuNum.put(groupSuborderitem.getSkuId(), skuNum.get(groupSuborderitem.getSkuId())+groupSuborderitem.getNumber());
				skuProduct.put(groupSuborderitem.getSkuId(), groupSuborderitem.getProductId());
				
			}
			userSkuNum.put(groupOrders.getOrderId(), skuNum);
		}
		for (Long skuId : allSkuNum.keySet()) {
			Integer sum = groupSuborderItemService.getSuborderItemSum(groupId.toString(),skuProduct.get(skuId),skuId.toString());
			allSkuNum.put(skuId, sum);
		}

		// 计算orders节省金额= 商品节省金额（内购价-最终阶梯价）+运费节省金额
		for (GroupOrdersVo groupOrders : lists) {
			BigDecimal saveProductAmonut= BigDecimal.ZERO;
			Map<Long,Integer> skuNum = userSkuNum.get(groupOrders.getOrderId());
			for (Long skuId : skuNum.keySet()) {
				// 根据团内sku 总数量计算阶梯价
				BigDecimal ladderPrice = productLadderService.getPriceBySkuidAndPrice(skuId, allSkuNum.get(skuId));
				if(ladderPrice != null){
					// 阶梯价格*订单内sku个数 计算最终金额
					BigDecimal ladderPrices = ladderPrice.multiply(new BigDecimal(skuNum.get(skuId)));
					// 内购价**订单内sku个数 计算单独购买所需金额
					ProductSpecificationsVo sku = productSpecificationsService.findByIdCache(skuId, skuProduct.get(skuId).toString());
					BigDecimal neigoujia = sku.getInternalPurchasePrice().multiply(new BigDecimal(skuNum.get(skuId)));
					saveProductAmonut = saveProductAmonut.add(neigoujia.subtract(ladderPrices));
				}
			}
			// 计算每orders运费节省金额
			BigDecimal subtract = groupOrders.getTotalShipping().subtract(groupOrders.getNowShipping());
			saveProductAmonut = saveProductAmonut.add(subtract);
			groupOrders.setSaveProductAmonut(saveProductAmonut);
		}
		return lists;
    }
}
