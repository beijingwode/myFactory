package com.wode.factory.supplier.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.redis.RedisUtil;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.ActResult;
import com.wode.factory.model.Shop;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.CommentsService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.UserService;

/**
 * 2015-6-17
 *
 * @author 谷子夜
 */
@Controller
@RequestMapping("/app/shop")
@ResponseBody
public class AppShopController extends BaseController {
	@Autowired
	private UserService userService;

	@Autowired
	ShopService shopService;
	@Autowired
	CommentsService commentsService;
	
	@Autowired
	RedisUtil redisUtil;

	/**
	 * 更新用户基本信息
	 *
	 * @param request
	 * @param gender
	 * @param nickName
	 * @return
	 */
	@RequestMapping("updateIndex.user")
	@NoCheckLogin
	public ActResult<Object> updateLogoAndTop(HttpServletRequest request, Long shopId, String logo, String topImage) {
		UserFactory userFactory = userService.getById(loginUser.getId());
		Shop shop = shopService.getById(shopId);
		shop.setTopImage(topImage);
		shop.setLogo(logo);
		shopService.update(shop);
		return ActResult.success(userFactory);
	}
	
	@RequestMapping("page")
	@NoCheckLogin
	public ModelAndView page(HttpServletRequest request,ModelAndView model) {
		model.setViewName("wx_wd");
		return model;
	}
	
}
