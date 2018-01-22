package com.wode.api.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.util.ActResult;
import com.wode.factory.model.CollectionShop;
import com.wode.factory.model.Shop;
import com.wode.factory.user.dao.ShopSettingDao;
import com.wode.factory.user.service.CollectionShopService;

import cn.org.rapid_framework.page.Page;
/**
 * 店铺收藏
 * @author 谷子夜
 */
@Controller
@RequestMapping("/collectShop")
@ResponseBody
public class CollectShopController extends BaseController{
	
	@Autowired
	private CollectionShopService csService;
	
	@Autowired
	private ShopSettingDao ssDao;
	
	/**
	 * 判断是否收藏当前店铺
	 * guziye
	 */
	@RequestMapping(value="check.user")
	public ActResult<Boolean> selectCollectionShop(HttpServletRequest request,Long shopId){
		
		// 当用户已登录时,获取用户信息
		Boolean b = csService.selectOne(loginUser.getId(), shopId);
		if (b)
			return ActResult.success(true);
		else
			return ActResult.success(false);
	}
	
	/**
	 * 收藏当前店铺
	 * guziye
	 */
	@RequestMapping(value="add.user")
	public ActResult<String> collectionShop(HttpServletRequest request,Long shopId){
		Shop s = ssDao.getById(shopId);
		if(s==null){
			return ActResult.fail("店铺不存在");
		}
		Boolean b = csService.selectOne(loginUser.getId(), shopId);
		if (!b) {
			CollectionShop cs = new CollectionShop();
			cs.setUserId(loginUser.getId());
			cs.setShopId(shopId);
			cs.setCreatTime(new Date());
			cs = csService.save(cs);
			if (cs != null) {
				return ActResult.successSetMsg("店铺收藏成功");
			} else {
				return ActResult.fail("店铺收藏失败");
			}
		} else {
			return ActResult.fail("已收藏该店铺");
		}
	}
	
	/**
	 * 取消收藏店铺
	 * guziye
	 */
	@RequestMapping(value="delete.user")
	public ActResult<String> cancelCollectionShop(HttpServletRequest request,String shopIdList){
		try{
			// 初始化选中商品
			String[] selectShop = shopIdList.split(",");
			List<Long> li=new ArrayList<Long>();
			for (String str : selectShop) {
				long ShopId = Long.parseLong(str);
				li.add(ShopId);
			}
			Boolean b = csService.canelCollectionShop(loginUser.getId(), li);
			if(b){
				return ActResult.successSetMsg("取消收藏成功");
			}else{
				return ActResult.fail("异常错误，取消收藏失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			return ActResult.fail("参数错误，取消收藏失败");
		}
	}
	
	/**
	 * 功能：查询店铺收藏列表
	 * @param pages
	 * @param size
	 * @author 刘聪
	 * @since 2015-06-24
	 */
	@RequestMapping("list.user")
	public ActResult<Page<Shop>> list(Integer page, Integer pageSize) {
		// 查询店铺收藏列表
		return ActResult.success(csService.selectAll(loginUser.getId(), page, pageSize));
	}
	/**
	 * 微信店铺收藏
	 * @param uid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("page")
	public ModelAndView page(ModelAndView model,HttpServletRequest request){
		model.setViewName("collectionShop");
		return model;
	}
}
