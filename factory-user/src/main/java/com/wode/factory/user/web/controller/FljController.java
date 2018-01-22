package com.wode.factory.user.web.controller;

import com.wode.common.redis.RedisUtil;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.user.service.WelfarePriceService;
import com.wode.search.WodeResult;
import com.wode.search.WodeSearchManager;
import com.wode.search.WodeSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by zoln on 2015/9/29.
 */
@Controller
@RequestMapping("/flj")
public class FljController {

	@Autowired
	RedisUtil redisUtil;
	@Autowired
	private WelfarePriceService welfarePriceService;
	@Autowired
	private ProductCategoryService categoryService;
	@Autowired
	private WodeSearchManager wsm;

	@RequestMapping("index")
	public String index(Model model){
		//首页图片+促销活动商品
		Map map = this.welfarePriceService.getWelfarePriceHome(2);
		model.addAttribute("banners", map.get("welfare_focus_picture"));
		//一级分类
		model.addAttribute("root", categoryService.findRoot());
		String query_string1 = "?promotionId=0&sort=saleNum_1";
		WodeSearcher searcher = wsm.getSearcher();
		searcher.setPageSize(16);
		String[] fields = new String[]{"品牌","价格"};
		WodeResult sr = searcher.search(query_string1, fields, false,false,false);
		model.addAttribute("result", sr);

		return "/flj/index";
	}
}
