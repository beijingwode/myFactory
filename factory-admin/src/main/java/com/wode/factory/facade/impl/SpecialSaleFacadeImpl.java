package com.wode.factory.facade.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.constant.Constant;
import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.DateUtils;
import com.wode.common.util.JsonUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.facade.EntBenefitFacade;
import com.wode.factory.facade.SpecialSaleFacade;
import com.wode.factory.model.EmpBenefitFlow;
import com.wode.factory.model.EntSeasonAct;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.SupplierLimitTicket;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserLimitTicket;
import com.wode.factory.service.EmpBenefitFlowService;
import com.wode.factory.service.EnterpriseUserService;
import com.wode.factory.service.OrderService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsService;
import com.wode.factory.service.SupplierCategoryService;
import com.wode.factory.service.SupplierLimitTicketService;
import com.wode.factory.service.SupplierLimitTicketSkuService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.SupplierSpecialSaleService;
import com.wode.factory.service.UserBalanceService;
import com.wode.factory.service.UserLimitTicketService;
import com.wode.sys.model.SysUser;

/**
 * Created by gaoyj on 2015/11/09.
 */
@Service("specialSaleFacade")
@Transactional("tm_factory")
public class SpecialSaleFacadeImpl implements SpecialSaleFacade {
	
	@Resource
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private EntBenefitFacade entBenefitFacade;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private SupplierCategoryService supplierCategoryService;
	@Autowired
	private EnterpriseUserService enterpriseUserService;
	@Autowired
	private SupplierSpecialSaleService supplierSpecialSaleService;
	@Autowired
	DBUtils dbUtils;
	@Autowired
	UserBalanceService userBalanceService;
	@Autowired
	EmpBenefitFlowService empBenefitFlowService;
	
	@Autowired
	private UserLimitTicketService userLimitTicketService;
	@Autowired
	private SupplierLimitTicketService supplierLimitTicketService;
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
	
	@Autowired
	private SupplierService supplierService;

	@Override
	@Transactional
	public void SpecialSaleCheck(Product pro, SysUser user) {
		List<EnterpriseUser> rtn = new ArrayList<EnterpriseUser>();
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("productId", pro.getId());
		List<ProductSpecifications> productSpecificationslist = productSpecificationsService.findlistByProductid(pro.getId());
		//专享商品处理
		if(pro.getSaleKbn()!=null && pro.getSaleKbn() == 4) {//普通变专享    或者     专享变专享
			//自动发放优惠卷
			map.put("productId", pro.getId());
			SupplierLimitTicket supplierLimitTicket = supplierLimitTicketService.findInfoByMap(map);
			if(supplierLimitTicket == null) {//普通到专享-无--插入
				supplierLimitTicket = new SupplierLimitTicket();
				supplierLimitTicket.setSupplierId(pro.getSupplierId());
				supplierLimitTicket.setCompanyName(supplierService.findByid(pro.getSupplierId()).getComName());
				supplierLimitTicket.setOneceFlag(2);
				supplierLimitTicket.setTicketType(4);
				supplierLimitTicket.setLimitType(4);
				supplierLimitTicket.setLimitKey("4");
				supplierLimitTicket.setTicket(BigDecimal.ZERO);
				setMsg(supplierLimitTicket,productSpecificationslist,pro,1);//处理开始时间、结束时间、现金券总额、券数量
				supplierLimitTicket.setTicketNote("购买专享商品抵扣用");
				supplierLimitTicket.setNextAction("http://www.wd-w.com/product/search?key=&saleKbn=4");//跳转路径
				supplierLimitTicket.setRegisteFlg("1");
				supplierLimitTicket.setRegisteNormalPrzie("1");
				supplierLimitTicket.setRegisteAutoPlus("1");
//				supplierLimitTicket.setRegisteAction("");//下一步操作 一般为注册，也可以是领取成功
				supplierLimitTicket.setReceiveNum(0);
				supplierLimitTicket.setUsedNum(0);
				supplierLimitTicket.setStatus(1);
				supplierLimitTicket.setCreateDate(new Date());
				supplierLimitTicket.setCreateUser(user.getId());
				supplierLimitTicket.setCreateUserName(user.getName());
				supplierLimitTicket.setExp1(String.valueOf(pro.getId()));
				supplierLimitTicketService.save(supplierLimitTicket);
			}else {//专享到专享
				Integer status = supplierLimitTicket.getStatus();
				Integer ticketNum = supplierLimitTicket.getTicketNum();//卷数量
				Integer receiveNum = supplierLimitTicket.getReceiveNum();//领取数量
				if(3 == status) {//普通到专享--------复活并更新金额
					if(ticketNum > receiveNum) {
						supplierLimitTicket.setStatus(1);//发放中
					}else{
						supplierLimitTicket.setStatus(2);//全部发放
					}
					setMsg(supplierLimitTicket,productSpecificationslist,pro,1);//处理开始时间、结束时间、现金券总额、券数量
				}else{
					setMsg(supplierLimitTicket,productSpecificationslist,pro,0);//处理开始时间、结束时间、现金券总额、券数量
				}
				updateSupplierLimitTicket(supplierLimitTicket,user);//更新supplierLimitTicket
				
				//处理用户获得的优惠卷map
				map.clear();
				map.put("limitTicketId", supplierLimitTicket.getId());
				List<UserLimitTicket> list = userLimitTicketService.getUserLimitTicketByMap(map);
				for (UserLimitTicket userLimitTicket : list) {
					if(3 == userLimitTicket.getStatus()) {
						if(userLimitTicket.getCashTotal().compareTo(userLimitTicket.getCashBalance()) == 0) {
							userLimitTicket.setStatus(0);//未使用
						}
						if(userLimitTicket.getCashTotal().subtract(userLimitTicket.getCashBalance()).compareTo(BigDecimal.ZERO) > 0) {
							userLimitTicket.setStatus(1);//部分使用
						}
						if(userLimitTicket.getCashBalance().compareTo(BigDecimal.ZERO) == 0) {
							userLimitTicket.setStatus(2);//已用完
						}
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						try {
							userLimitTicket.setLimitStart(sdf.parse(sdf.format(new Date())));
							userLimitTicket.setLimitEnd(DateUtils.addMonths(sdf.parse(sdf.format(new Date())), 3));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					//计算金额
					BigDecimal newBalance = supplierLimitTicket.getCash().subtract(userLimitTicket.getCashTotal()).add(userLimitTicket.getCashBalance());
					BigDecimal cash_total = BigDecimal.ZERO;
					BigDecimal cash_balance = BigDecimal.ZERO;
					if(newBalance.compareTo(BigDecimal.ZERO) >= 0) {
						cash_total = supplierLimitTicket.getCash();
						cash_balance = newBalance;
					}else {
						cash_total = supplierLimitTicket.getCash().subtract(newBalance);
						cash_balance = BigDecimal.ZERO;
					}
					userLimitTicket.setCashTotal(cash_total);
					userLimitTicket.setCashBalance(cash_balance);
					updateUserLimitTicket(userLimitTicket,user);//更新userLimitTicket
				}
			}
		}else {//专享到普通
			map.clear();
			map.put("productId", pro.getId());
			SupplierLimitTicket supplierLimitTicket = supplierLimitTicketService.findInfoByMap(map);
			if(supplierLimitTicket != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				supplierLimitTicket.setStatus(3);
				try {
					supplierLimitTicket.setLimitEnd(sdf.parse(sdf.format(new Date())));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				updateSupplierLimitTicket(supplierLimitTicket,user);//更新supplierLimitTicket
				
				//处理用户获得的优惠卷---------失效
				//直接更新用户优惠卷状态和使用期限结束日期
				map.clear();
				map.put("limitTicketId", supplierLimitTicket.getId());
				List<UserLimitTicket> list = userLimitTicketService.getUserLimitTicketByMap(map);
				for (UserLimitTicket userLimitTicket : list) {
					userLimitTicket.setStatus(3);
					try {
						userLimitTicket.setLimitEnd(sdf.parse(sdf.format(new Date())));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					updateUserLimitTicket(userLimitTicket,user);//更新userLimitTicket
				}
			}
		}
	}
	
	
	/**
	 * 更新用户优惠劵
	 * @param userLimitTicket
	 * @param user
	 */
	public void updateUserLimitTicket(UserLimitTicket userLimitTicket,SysUser user) {
		userLimitTicket.setUpdateDate(new Date());
		userLimitTicket.setUpdateUser(user.getId());
		userLimitTicketService.update(userLimitTicket);
	}
	/**
	 * 更新商家优惠劵
	 * @param supplierLimitTicket
	 * @param user
	 */
	public void updateSupplierLimitTicket(SupplierLimitTicket supplierLimitTicket,SysUser user) {
		supplierLimitTicket.setUpdateDate(new Date());
		supplierLimitTicket.setUpdateUser(user.getId());
		supplierLimitTicketService.update(supplierLimitTicket);
	}
	
	/**
	 * 优化
	 * @param supplierLimitTicket
	 * @param productSpecificationslist 
	 * @param pro
	 * @param pro1
	 * @param type 
	 */
	public void setMsg(SupplierLimitTicket supplierLimitTicket,List<ProductSpecifications> productSpecificationslist, Product pro1, Integer type) {
		if(type == 1) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				supplierLimitTicket.setLimitStart(sdf.parse(sdf.format(new Date())));
				supplierLimitTicket.setLimitEnd(DateUtils.addMonths(sdf.parse(sdf.format(new Date())), 3));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		supplierLimitTicket.setTicketNum(pro1.getEmpLevel());
		supplierLimitTicket.setCash(productSpecificationslist.get(0).getInternalPurchasePrice().subtract(pro1.getEmpPrice()));
	}

	@Transactional
	public int updateEntBalance(Long flowId,Long entId, int year, int season,
			 BigDecimal cashSum,BigDecimal ticketSum, String updUser) {

		////////////////////////////////////////////////////////////////////////////////////////
		//季度福利总额取得
		EntSeasonAct esa = entBenefitFacade.getEntSeasonAct(entId, year, season, updUser);
		if(esa == null) return 0;
		if(!"1".equals(esa.getUseFlg())) return -1001;
		////////////////////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////////////////////
		//企业账目
		int rtn = entBenefitFacade.dealEntBenefit(entId, "117", esa, cashSum.abs(),
				ticketSum.abs(), esa.getId(), flowId, "员工特享商品所得差额发放给员工", updUser,false);

		//余额不足
		if(rtn <= 0) return rtn;
		////////////////////////////////////////////////////////////////////////////////////////
		
		return 1;
	}

	/**
	 * 向员工现金券以抵扣回收
	 * @param flw
	 * @return
	 */
	private int dealEmpBenefit(EmpBenefitFlow flw) {
		
		////////////////////////////////////////////////////////////////////////////////////////
		//计算余额
		//现金余额
		UserBalance ubCash = userBalanceService.findByUserAndCurrencyId(flw.getEmpId(), 0L);
		//现金余额
		UserBalance ubTicket = userBalanceService.findByUserAndCurrencyId(flw.getEmpId(), 1L);
		
		if(ubCash==null||ubTicket==null) return 0;
		//现金余额 = 现金余额+本次操作现金*操作符号
		BigDecimal cashB = ubCash.getBalance().add(flw.getCash());
		//内购券余额 = 内购券余额+本次操作内购券*操作符号
		BigDecimal ticketB = ubTicket.getBalance();
		////////////////////////////////////////////////////////////////////////////////////////
		

		////////////////////////////////////////////////////////////////////////////////////////
		//本季度额度生成 流水记录
		flw.setId(dbUtils.CreateID());				//id
		flw.setTicketBalance(ticketB);				//内购券余额= 上季度 剩余内购券总额
		flw.setCashBalance(cashB);					//现金余额	=上季度剩余现金总额

		//DB更新
		empBenefitFlowService.insert(flw);
		////////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////////////////////////
		//余额处理
		//现金
		ubCash.setBalance(cashB);
		userBalanceService.saveOrUpdate(ubCash);
		////////////////////////////////////////////////////////////////////////////////////////

		//正常处理
		return 1;
	}


	private ProductSpecifications getSku(Long proId) {
		String jsonSku = redisUtil.getMapData(RedisConstant.PRODUCT_PRE + proId, RedisConstant.PRODUCT_REDIS_SKU);
		List<ProductSpecifications> skus = JsonUtil.getList4Json(jsonSku, ProductSpecifications.class);
		ProductSpecifications sku = null;
		for (ProductSpecifications productSpecifications : skus) {
			if(!lessThan(sku,productSpecifications)) {
				sku=productSpecifications;
			}
		}
		return sku;
	}
	private boolean lessThan(ProductSpecifications fisrt,ProductSpecifications second) {
		if(fisrt==null) return false;
		if(second==null) return true;
		if(fisrt.getPrice()==null || BigDecimal.ZERO.compareTo(fisrt.getPrice())==0) return false;
		if(second.getPrice()==null || BigDecimal.ZERO.compareTo(second.getPrice())==0) return true;
		
		BigDecimal firstCoin = BigDecimal.ZERO;
		if(fisrt.getMaxFucoin()!=null) {
			firstCoin=fisrt.getMaxFucoin();
		}
		BigDecimal secondCoin = BigDecimal.ZERO;
		if(second.getMaxFucoin()!=null) {
			secondCoin=second.getMaxFucoin();
		}
		return fisrt.getPrice().subtract(firstCoin).compareTo(second.getPrice().subtract(secondCoin)) < 0;
	}
}
