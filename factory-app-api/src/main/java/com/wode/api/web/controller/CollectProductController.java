package com.wode.api.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.factory.model.CollectionProduct;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.user.service.CollectionProductService;
import com.wode.factory.user.service.EntParamCodeService;
import com.wode.factory.user.service.ProductService;
import com.wode.factory.user.service.ProductSpecificationsService;
/**
 * 商品收藏
 * @author 谷子夜
 */
@Controller
@RequestMapping("/collectProduct")
@ResponseBody
public class CollectProductController extends BaseController{
	
	@Autowired
	private CollectionProductService cpService;
	
	@Autowired
	private ProductService productService;
	@Autowired
	private com.wode.factory.service.ProductService pService;
	@Autowired
	private EntParamCodeService entParamCodeService;
	@Autowired
	private ProductSpecificationsService  productSpecificationsService;
	
	/**
	 * 判断是否收藏当前商品
	 * guziye
	 */
	@RequestMapping(value="check.user")
	public ActResult<Boolean> selectCollectionProduct(HttpServletRequest request,Long productId){
		Boolean b = cpService.selectOne(loginUser.getId(), productId);
		if (b)
			return ActResult.success(true);
		else
			return ActResult.success(false);
	}
	
	/**
	 * 收藏当前商品
	 * guziye
	 */
	@RequestMapping(value="add.user")
	public ActResult<String> collectionProduct(HttpServletRequest request,Long productId){
		Product p = productService.getById(productId);
		if(p==null || p.getStatus()!=2){
			return ActResult.fail("商品不存在");
		}
		Boolean b = cpService.selectOne(loginUser.getId(), productId);
		if (!b) {
			CollectionProduct cp = new CollectionProduct();
			cp.setUserId(loginUser.getId());
			cp.setProductId(productId);
			cp.setCreatTime(new Date());
			cp = cpService.save(cp);
			if (cp != null) {
				return ActResult.successSetMsg("商品收藏成功");
			} else {
				return ActResult.fail("商品收藏失败");
			}
		} else {
			return ActResult.fail("已收藏该商品");
		}
	}
	
	/**
	 * 取消收藏当前商品
	 * guziye
	 */
	@RequestMapping(value="delete.user")
	public ActResult<String> cancelCollectionProduct(HttpServletRequest request,String productIdList){
		try{
			// 初始化选中商品
			String[] selectProduct = productIdList.split(",");
			List<Long> li=new ArrayList<Long>();
			for (String str : selectProduct) {
				long productId = Long.parseLong(str);
				li.add(productId);
			}
			Boolean b = cpService.canelCollectionProduct(loginUser.getId(),li);
			if(b){
				return ActResult.successSetMsg("取消收藏商品成功");
			}else{
				return ActResult.fail("取消收藏商品失败，请重试");
			}
		}catch(Exception e){
			e.printStackTrace();
			return ActResult.fail("参数错误，取消收藏商品失败");
		}
	}

	/**
	 * 功能：查询商品收藏列表
	 * @param pages
	 * @param size
	 * @author 刘聪
	 * @since 2015-06-24
	 */
	@RequestMapping("list.user")
	public ActResult<PageInfo<Product>> list(Integer page, Integer pageSize) {
		
		// 查询商品收藏列表
		PageInfo<Product> pageList=cpService.selectAll(loginUser.getId(), page, pageSize);
		for (Product product : pageList.getList()) {
			List<ProductSpecifications> plist = pService.findByMinpriceCache(product.getId(),NumberUtil.toBigDecimal(product.getShowPrice()));
			if (plist.size()>0) {
				ProductSpecifications suk = plist.get(0);
				if(suk!=null &&suk.getInternalPurchasePrice() != null){
					//员工特享商品
					productSpecificationsService.resetPrice(suk, product, loginUser,true,null);
					product.setShowPrice(suk.getInternalPurchasePrice()+"");
				}
				product.setMarketPrice(suk.getPrice());
				//product.setShowPrice(suk.getPrice().subtract(suk.getMaxFucoin()==null?BigDecimal.ZERO:suk.getMaxFucoin())+"");
				product.setMaxprice(suk.getMaxFucoin()==null?BigDecimal.ZERO:suk.getMaxFucoin());
			} else {
				product.setMaxprice(BigDecimal.ZERO);
			}
		}
		ActResult<PageInfo<Product>> rst = ActResult.success(pageList);
		rst.setMsg(entParamCodeService.getBenefitSubsidy().toString());
		return  rst;
	}
	
	/**
	 * 微信店铺收藏页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("page")
	public ModelAndView page(ModelAndView model,HttpServletRequest request){
		model.setViewName("collectionProduct");
		return model;
	}
}
