package com.wode.api.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.FLJModel;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.user.service.WelfarePriceService;
import com.wode.search.WodeResult;
import com.wode.search.WodeSearchManager;
import com.wode.search.WodeSearcher;

@Controller
@RequestMapping("flj")
@ResponseBody
public class SupplierController extends BaseController{
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	private WelfarePriceService welfarePriceService;
	@Autowired
	private ProductCategoryService categoryService;
	@Autowired
	private WodeSearchManager wsm;
     
	/**
	 * 内购价商家列表
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * 2015-7-10
	 * @author 谷子夜
	 */
	@RequestMapping("companyList")
	public ActResult<Object> findSupplier(ModelMap model,Integer pageNum,Integer pageSize){
		if(pageSize==null || pageSize<1){
			return ActResult.fail("每页显示必须大于0");
		}
		if(!StringUtils.isNullOrEmpty(pageNum)){
			if(pageNum<1)
				return ActResult.fail("页码必须大于0");
		}
		String supplierJsion = redisUtil.getData(RedisConstant.FLJ);
		List<FLJModel> list = JsonUtil.getList(supplierJsion, FLJModel.class);
//		int totalPage = list.size()/pageSize+1;
		int totalPage = list.size()%pageSize>0?list.size()/pageSize+1:list.size()/pageSize;//总页码
		if(pageNum==null || pageNum==0){
			if(list.size()>pageSize){
				list = list.subList(0, pageSize);
			}
		}else{
			if(totalPage>pageNum){
//				list = list.subList(pageNum*(pageSize-1), pageNum*pageSize);
				list = list.subList((pageNum-1)*pageSize, pageNum*pageSize);
			}else{
				list = list.subList((totalPage-1)*pageSize,list.size());
//				list = list.subList(totalPage*(pageSize-1), list.size());
			}
		}
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("list", list);
		return ActResult.success(model);
	}
	
	/**
	 * 功能说明：员工内购价首页信息
	 * 日期:	2015年7月13日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @return
	 */
	@RequestMapping("index")
	public ActResult<Object> index(){
		//首页图片+促销活动商品
		Map map = this.welfarePriceService.getWelfarePriceHome(2);
		//一级分类
		map.put("root",categoryService.findRoot());
		
		String query_string1 = "?promotionId=0&sort=saleNum_1";
		WodeSearcher searcher = wsm.getSearcher();
		searcher.setPageSize(15);
		String[] fields = new String[]{"品牌","价格"};
		WodeResult sr = searcher.search(query_string1, fields, false,false,false);
		map.put("skus", sr.getHits());
		ActResult act = new ActResult();
		if(map.isEmpty()){
			act = ActResult.fail("没有数据");
		}else{
			act = ActResult.success(map);
		}
		return act;
	}
}
