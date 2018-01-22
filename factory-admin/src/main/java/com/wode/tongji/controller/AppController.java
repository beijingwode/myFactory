/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.tongji.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.facade.SupplierCloseFacade;
import com.wode.factory.mapper.BalanceMonthStatisticalDao;
import com.wode.factory.mapper.EntSeasonActDao;
import com.wode.factory.mapper.SuborderitemDao;
import com.wode.factory.mapper.UserBalanceMapper;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductHidden;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.WxOpenService;
import com.wode.factory.service.ProductECardService;
import com.wode.factory.service.ProductHiddenService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsService;
import com.wode.factory.service.ProductThirdPriceService;
import com.wode.search.WodeSearchManager;
import com.wode.tongji.mapper.PvDayStatisticalMapper;
import com.wode.tongji.model.App;
import com.wode.tongji.service.AppService;
import com.wode.user.task.CommentsDataTask;
import com.wode.user.task.ManagerResultMonthStatistic;
import com.wode.user.task.ProductPvDayOnline;
import com.wode.user.task.PvDayStatistic;
import com.wode.user.task.TestUserDayClear;

@Controller
@RequestMapping("app")
public class AppController{
	@Resource
	private AppService appService;

	@Resource
	private PvDayStatistic pvDayStatistic;
	@Resource
	private SupplierCloseFacade supplierCloseFacade;

	@Autowired
	BalanceMonthStatisticalDao balanceMothStatisticalMapper;
	@Autowired
	EntSeasonActDao entSeasonActMapper;
	@Autowired
	UserBalanceMapper userBalanceMapper;
	@Autowired
	ManagerResultMonthStatistic managerResultMonthStatistic;
	@Value("#{configProperties['factory.api.url']}")
	private  String apiUrl;
	
	@Autowired
	private WodeSearchManager wsm;
	@Autowired
	PvDayStatisticalMapper pvDayStatisticalMapper;
	@Autowired
    private ProductThirdPriceService productThirdPriceService;
	
	@Autowired
	private ProductECardService productECardService;
	@Autowired
	private TestUserDayClear testUserDayClear;
	
    private final Logger logger = LoggerFactory.getLogger(AppController.class);

	static WxOpenService wxOpen = ServiceFactory.getWxOpenService(Constant.OUTSIDE_SERVICE_URL);
	@Autowired
	private SuborderitemDao suborderitemDao;
	
	@Autowired
	ProductPvDayOnline productPvDayOnline;
	
	@Autowired
	CommentsDataTask commentsDataTask;
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
	
	@Autowired
	private ProductHiddenService productHiddenService;
	
	@Autowired
	private ProductService productService;
	private Set<String> interfaceUrl=new HashSet<String>();
	@Qualifier("creat_html_url")
	@Autowired
	public void setInterfaceUrl(String interfaceUrl) {
		String[] arr=interfaceUrl.split(",");
		for(String url:arr){
			if(!StringUtils.isEmpty(url)){
				this.interfaceUrl.add(url);
			}
		}
	}
	/**
	 * 删除
	* @param id 用户id
	* @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="test")
	public @ResponseBody Integer test(String yesTerday) throws ParseException{
		commentsDataTask.run();
		return 1;
	}

	/**
	 * 删除
	* @param id 用户id
	* @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="pv")
	public @ResponseBody Integer pv(String yesTerday) throws ParseException{
		pvDayStatistic.runReal(yesTerday);
		return 1;
	}
	/**
	 * 删除
	* @param id 用户id
	* @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="birth")
	public @ResponseBody Integer banlance() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, -3);	//3天未获得评价的试用商品
		List<Suborderitem> lst=suborderitemDao.findTrialByDate(TimeUtil.dateToStr(date.getTime()));
		Map<Long,Suborderitem> mapUser = new HashMap<Long,Suborderitem>();
		if (lst!=null && !lst.isEmpty()) {
			Map<String,Object> paramWX = new HashMap<String,Object>();
			paramWX.put("type", "trial_comment");
			//遍历
			for (Suborderitem o : lst) {
				if(!mapUser.containsKey(o.getSkuId())) {
					paramWX.put("subOrderId", o.getSubOrderId());
					paramWX.put("productName", o.getProductName());
					paramWX.put("amount", o.getRealPay());
					paramWX.put("prize", o.getPrice());
					paramWX.put("userId", o.getSkuId());
					
					try {
						HttpClientUtil.sendHttpRequest("post", apiUrl + "wx/templateMsgSend", paramWX);
						mapUser.put(o.getSkuId(), o);
					} catch (Exception ex) {

					}
				}
			}
		}
		return 1;
	}
	/**
	 * 删除
	* @param id 用户id
	* @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="wxOpenSetTag")
	@NoCheckLogin
	public @ResponseBody Integer thirdPrice(String openIds,String tagId) {
		wxOpen.setTag(openIds, tagId, true, null);
		return 1;
	}
	
	/**
	 * 清除用户信息(慎用)
	* @param id 用户id
	* @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="testUserDayClear")
	public @ResponseBody Integer testUserDayClear(Long userId) {
		testUserDayClear.clearUserById(userId);
		return 1;
	}

	
	/**
	 * 删除
	* @param id 用户id
	* @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="thirdPrice")
	@NoCheckLogin
	public @ResponseBody List<ProductThirdPrice> thirdPrice(String third) {
		
		////////////////////////////////////////////////////////////////////
		//查询执行任务
		return productThirdPriceService.select5DaysAgo(third);
		////////////////////////////////////////////////////////////////////
	}
	
	/**
	 * 对比商品价格
	* @param id 用户id
	* @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="updateThirdPrice")
	@NoCheckLogin
	public @ResponseBody Integer updateThirdPrice(Long id,Integer urlStatus, BigDecimal lastPrice) {
		
		ProductThirdPrice ptp =productThirdPriceService.getById(id);
		if(ptp==null) {
			return -1;
		} else {
			if(urlStatus!=null) ptp.setUrlStatus(urlStatus);
			if(lastPrice!=null) ptp.setLastPrice(lastPrice);
			ptp.setUpdateDate(new Date());
			Date date = new Date();
			productThirdPriceService.update(ptp);
			if(ptp.getPrice()!=null) {//基准价为空 不做比价
				if(ptp.getLastPrice()!=null) {//最新电商价为空 不做比价
					if(ptp.getLastPrice().compareTo(ptp.getPrice())==-1) {//判断最新电商价是否小于基准价（此商品危险）
						List<ProductSpecifications> productList = productSpecificationsService.getlistByProductid(ptp.getProductId());
						for (ProductSpecifications productSpecifications : productList) {
							if(ptp.getLastPrice().compareTo(productSpecifications.getInternalPurchasePrice())==-1) {//判断最新电商价是否小于平台内购价
								ProductHidden productHidden = productHiddenService.getById(productSpecifications.getProductId());
								if(productHidden!=null) {//判断此商品是否已经隐藏（已隐藏做修改操作，未隐藏做save操作）
									productHidden.setInternalPurchasePrice(productSpecifications.getInternalPurchasePrice());
									productHidden.setLastPrice(ptp.getLastPrice());
									productHidden.setUpdateTime(date);
									productHiddenService.update(productHidden);
								}else {
									wsm.deleteIndex(productSpecifications.getProductId());//将此商品从索引库移除
									ProductHidden ph = new ProductHidden();
									ph.setId(productSpecifications.getProductId());
									Product product = productService.getById(productSpecifications.getProductId());
									ph.setProductName(product.getName());
									ph.setInternalPurchasePrice(productSpecifications.getInternalPurchasePrice());
									ph.setLastPrice(ptp.getLastPrice());
									ph.setHiddenDate(date);
									ph.setUpdateTime(date);
									productHiddenService.save(ph);
								}
							}
						}
					}
				}
			}
			return 1;
		}
	}
	
	@RequestMapping
	public String toAapp(Model model) {
		return "sys/app/app";
	}
	
	@RequestMapping("download/{filename}/")
	public void download(@PathVariable String filename,HttpServletResponse response) {
		App app=appService.selectByUrl("http://img2.wd-w.com/upload/apk/"+filename);
		if(app!=null){
			app.setDownloadTimes(app.getDownloadTimes()==null?1:app.getDownloadTimes()+1);
			appService.saveOrUpdate(app);
			response.setHeader("Content-Disposition","attachment;");  
			response.setHeader("Content-Type","application/octet-stream");  
			response.setHeader("X-Accel-Redirect","/upload/regular/apk/"+filename);  
			 
		}
		
	}
	
	
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		PageInfo<App> page = appService.findAllApp(params);
		model.addAttribute("page", JSON.toJSON(page));
		return "sys/app/app-list";
	}
	
	/**
	 * 保存
	* @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute App app,HttpServletRequest request){
		return appService.saveOrUpdate(app);
	}
	
	/**
	 * 删除
	* @param id 用户id
	* @return
	 */
	@RequestMapping(value="delete",method = RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return appService.deleteApp(id);
	}
	
	/**
	 * 弹窗
	* @param id app的id
	* @param mode 模式
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id,@PathVariable("mode") String mode, Model model){
		App app = appService.selectByPrimaryKey(id);;
		model.addAttribute("app", app);
		return mode.equals("detail")?"sys/app/app-detail":"sys/app/app-save";
	}
}

