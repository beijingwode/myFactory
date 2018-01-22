package com.wode.api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.util.ActResult;
import com.wode.factory.user.service.EntParamCodeService;
import com.wode.factory.user.service.PageDataService;


@Controller
@RequestMapping("/recommendProduct")
@ResponseBody
public class RecommendProductController {

	@Autowired
	private PageDataService pageDataService;

	@Autowired
	private EntParamCodeService entParamCodeService;
	
	@RequestMapping
    public ActResult index(Long type, Long categoryId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {

//		ActResult act = ActResult.success(pageDataService.findAppIndexProducts(page, pageSize));
		ActResult act = ActResult.success(pageDataService.findAppIndexProductsByElaSeach(page, pageSize));
		act.setMsg(entParamCodeService.getBenefitSubsidy().toString());
		return act;
	}
	
	
}
