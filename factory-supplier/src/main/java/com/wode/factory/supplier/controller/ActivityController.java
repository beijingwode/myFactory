/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wode.common.db.DBUtils;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.redis.RedisUtil;
import com.wode.common.result.Result;
import com.wode.factory.company.service.EnterpriseService;
import com.wode.factory.company.service.EnterpriseUserService;
import com.wode.factory.company.service.UserShareService;
import com.wode.factory.supplier.query.SkuLadderVo;
import com.wode.factory.supplier.service.ProductLadderService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.UserService;
import com.wode.factory.supplier.util.ExcelUtil;
import com.wode.factory.supplier.util.UserInterceptor;

import cn.org.rapid_framework.page.Page;

@Controller
@RequestMapping("activity")
public class ActivityController extends BaseSpringController {
	@Autowired
	EnterpriseUserService enterpriseUserService;
	@Autowired
	EnterpriseService enterpriseService;
	@Autowired
	UserService userService;
	@Autowired
	UserShareService userShareService;

	@Qualifier("redis")
    @Autowired
    private RedisUtil redisUtil;
	
	@Autowired
	DBUtils dbUtils;
	@Autowired
	ExcelUtil excelUtil;
	HttpServletRequest re;
	private static Logger logger= LoggerFactory.getLogger(ActivityController.class);
	
	@Autowired
	@Qualifier("productLadderService")
	private ProductLadderService productLadderService;
	
    @Autowired
    @Qualifier("shopService")
    private ShopService shopService;

	@Value("#{configProperties['factory.api.url']}")
	private  String apiUrl;
	
	@RequestMapping(value="qicai/page")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,SkuLadderVo query) {
		
		ModelAndView mv = new ModelAndView();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if (userModel == null) {
			//会话中usermodel对象为空
			Result result = new Result();
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000");

			return mv;
		} else {
			query.setSupplierId(userModel.getSupplierId());
			
			PageInfo<SkuLadderVo> page = this.productLadderService.findPageVo(query);
			
			ModelAndView result = new ModelAndView("activity/qicai/page");
			result.addObject("page", page);
			result.addObject("query", query);
			return result;
		}
	}
 
}

