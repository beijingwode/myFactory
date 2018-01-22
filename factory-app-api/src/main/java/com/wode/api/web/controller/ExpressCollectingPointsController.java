package com.wode.api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wode.common.util.ActResult;
import com.wode.factory.outside.service.ExpressService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.user.service.ExpressCollectingPointsService;
import com.wode.factory.user.util.Constant;

/**
 * 快递代收点
 *
 */
@Controller
@RequestMapping("/express")
public class ExpressCollectingPointsController extends BaseController{
    static ExpressService express = ServiceFactory.getExpressService(Constant.OUTSIDE_SERVICE_URL);
	/**
	 * ExpressApiUrl 配置在  service
	 * */
	@Autowired
	private ExpressCollectingPointsService expressCollectingPointsService;
	@RequestMapping(value = { "/collectingPoints" })
	@ResponseBody
	public ActResult<Object> collectingPoints(Double lng,Double lat,String address,Boolean status) {
		return expressCollectingPointsService.collectingPoints(lng, lat, address, status);
	}
	

	@RequestMapping(value="search", method=RequestMethod.GET)
    @ResponseBody
    public ActResult<JSONObject> express(String expressCom,String express_no,String user){	
		return ActResult.success(express.serch(expressCom, express_no, user));
	}
}
