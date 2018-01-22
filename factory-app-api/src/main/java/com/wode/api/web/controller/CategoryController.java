package com.wode.api.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.api.util.Constant;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.service.ProductCategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private ProductCategoryService categoryService;
	
	/**
	 * 获取根分类
	 */
	@RequestMapping("/root")
	public @ResponseBody ActResult<List<ProductCategory>> root() {
		
		return ActResult.success(categoryService.findRoot());
		
	}
	
	
//	@RequestMapping("/all")
//	public @ResponseBody ActResult<List<ProductCategory>> all() {
//		
//		return ActResult.success(categoryService.);
//		
//	}
	
	
	
	/**
	 * 获下一级分类
	 */
	@RequestMapping("/child")
	public @ResponseBody ActResult<List<ProductCategory>> child(Long pid) {
		ProductCategory pc=categoryService.findById(pid);
		return  ActResult.success(categoryService.findSub(pc));
		
	}
	
	@RequestMapping("/categoryInfo")
	public @ResponseBody List<ProductCategory> categoryInfo(Long pid){
		Long d = pid % 10;
		pid = pid-d;
		return getAppCategory(this.child(pid),pid,d);
	}

	private List<ProductCategory> getAppCategory(ActResult<List<ProductCategory>> act,Long pid,Long d){
		List<ProductCategory> list_pro = new ArrayList<ProductCategory>();
		List<Long> ids= categoryService.find3CategoryId();
		if(!StringUtils.isEmpty(act.getData())){
			for(ProductCategory pro : act.getData()){
				if(checkReturn(pid,d,pro.getId().intValue())) {
					List<ProductCategory> pc3s = this.child(pro.getId()).getData();
					filterSell(ids,pc3s);
					if(!pc3s.isEmpty()) {
						ProductCategory product = new ProductCategory();
						product.setId(pro.getId());
						product.setName(pro.getName());
						product.setBrotherOrderAll(pro.getBrotherOrderAll());
						product.setChildCount(pro.getChildCount());
						product.setPid(pro.getPid());
						product.setUrl(pro.getUrl());
						
						product.setChildrens(pc3s);
						product.setImage(pro.getImage());
						list_pro.add(product);
					}
				}
			}
		}
		
		return list_pro;
	}
	
	private void filterSell(List<Long> ids,List<ProductCategory> pc3s) {
		for (int i = pc3s.size()-1; i >-1; i--) {
			if(!ids.contains(pc3s.get(i).getId())) {
				pc3s.remove(i);
			}
		}
	}
	private boolean checkReturn(Long pid,Long d,int deep2Id) {
		if(d==0) return true;
		
		if(pid==40000L) {
			if(d==1) {
				//家居家具-40001
//				40100 |      1 | 厨具     
//				40200 |      2 | 家装建材 
//				40500 |      5 | 灯具     
//				41000 |     10 | 工具/资材
				if(deep2Id== 40100 || deep2Id== 40200 || deep2Id== 40500 || deep2Id== 41000) return false; 
			} else {
				//家装厨具-40002
//				40300 |      3 | 家纺     
//				40400 |      4 | 家具  
//				40600 |      6 | 生活日用   
//				40700 |      7 | 家居软饰 
//				40800 |      8 | 宠物生活 
//				40900 |      9 | 花卉绿植  
				if(deep2Id== 40300 || deep2Id== 40400 || deep2Id== 40600 || deep2Id== 40700 || deep2Id== 40800 || deep2Id== 40900) return false; 
			}
			
		} else if(pid==50000L) {
			if(d==1) {
				//男装女装-50001
//				50300 |      3 | 内衣    
//				50400 |      4 | 配饰    
//				50500 |      5 | 珠宝首饰
				if(deep2Id== 50300 || deep2Id== 50400|| deep2Id== 50500) return false; 
			} else {
				//内衣珠宝-50002
//				50100 |      1 | 女装    
//				50200 |      2 | 男装    
				if(deep2Id== 50100 || deep2Id== 50200) return false; 
			}
			
		} else if(pid==60000L) {
			if(d==1) {
				//个护化妆-60001
//				60600 |      6 | 香水彩妆
//				60700 |      7 | 清洁用品
				if(deep2Id== 60700) return false; 
			} else {
				//清洁用品-60002
//				60100 |      1 | 面部护肤
//				60200 |      2 | 洗发护发
//				60300 |      3 | 身体护肤
//				60400 |      4 | 口腔护理
//				60600 |      6 | 香水彩妆
				if(deep2Id== 60100 || deep2Id== 60200 || deep2Id== 60300 || deep2Id== 60400 || deep2Id== 60600) return false; 
			}
			
		} else if(pid==70000L) {
			if(d==1) {
				//鞋靴箱包-70001
//				70600 |      6 | 奢侈品  
//				70700 |      7 | 钟表    
//				70800 |      8 | 礼品    
				if(deep2Id== 70600 || deep2Id== 70700  || deep2Id== 70800 ) return false; 
			} else {
				//钟表奢侈-70002
//				70100 |      1 | 时尚女鞋
//				70200 |      2 | 流行男鞋
//				70300 |      3 | 潮流女包
//				70400 |      4 | 精品男包
//				70500 |      5 | 功能箱包
				if(deep2Id== 70100 || deep2Id== 70200 || deep2Id== 70300 || deep2Id== 70400 || deep2Id== 70500) return false; 
			}
			
		} else if(pid==110000L) {
			if(d==1) {
				//食品酒类-110001
//				110800 |      8 | 生鲜食品
//				110400 |      4 | 地方特产
				if(deep2Id== 110800 || deep2Id== 110400) return false; 
			} else {
				//生鲜特产-110002
//				110100 |      1 | 中外名酒
//				110200 |      2 | 进口食品
//				110300 |      3 | 休闲食品
//				110500 |      5 | 茗茶    
//				110600 |      6 | 饮料冲调
//				110700 |      7 | 粮油调味
				if(deep2Id== 110100 || deep2Id== 110200 || deep2Id== 110300 || deep2Id== 110500 || deep2Id== 110600 || deep2Id== 110700) return false; 
			}
			
		} else if(pid==120000L) {
			if(d==1) {
				//母婴用品-120001
//				121100 |     11 | 玩具乐器
				if(deep2Id== 121100) return false; 
			} else {
				//玩具乐器-120002
//				120100 |      1 | 奶粉    
//				120200 |      2 | 营养辅食
//				120300 |      3 | 尿裤湿巾
//				120400 |      4 | 洗护用品
//				120500 |      5 | 喂养用品
//				120800 |      8 | 寝居服饰
//				120900 |      9 | 妈妈专区
//				121000 |     10 | 童装童鞋
				if(deep2Id== 120100 || deep2Id== 120200 || deep2Id== 120300 || deep2Id== 120400 || deep2Id== 120500 || deep2Id== 120800 || deep2Id== 120900 || deep2Id== 121000) return false; 
			}
			
		} else if(pid==130000L) {
			if(d==1) {
				//图书音像-130001
//				131100 |     11 | 数字音乐
				if(deep2Id== 131100) return false; 
			} else {
				//电子图书-130002
//				130100 |      1 | 音像    
//				130200 |      2 | 少儿    
//				130300 |      3 | 教育    
//				130400 |      4 | 文艺    
//				130500 |      5 | 经管励志
//				130600 |      6 | 人文社科
//				130700 |      7 | 生活    
//				130800 |      8 | 科技    
//				130900 |      9 | 刊/原版 
				if(deep2Id== 130100 || deep2Id== 130200 || deep2Id== 130300 || deep2Id== 130400 || deep2Id== 130500 || deep2Id== 130600 || deep2Id== 130700 || deep2Id== 130800 || deep2Id== 130900) return false; 
			}
			
		}
		
		return true;
	}
	/**
	 * 功能：app登录
	 *
	 * @param userName
	 * @param passWord
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/categoryInfo.json")
	public ActResult<List<ProductCategory>> categoryInfoJson(Long pid) {

		Long d = pid % 10;
		pid = pid-d;
		
		ActResult<List<ProductCategory>> act = this.child(pid);
		if(!StringUtils.isEmpty(act.getData())){
			act.setData(getAppCategory(this.child(pid),pid,d));
		}
		
		return act;
	}

	
	/**
	 * 获取根分类
	 */
	@ResponseBody
	@RequestMapping("/root.json")
	public ActResult<List<ProductCategory>> rootJson() {
		return ActResult.success(categoryService.findRoot());
	}
	
	@RequestMapping()
	public String page(Model model,String localHtml) {
		if(StringUtils.isEmpty(localHtml)) {
			localHtml= Constant.pageFont;
		}
		List<ProductCategory> list_pro= null;
		//一级根节点
		ActResult<List<ProductCategory>> root = this.root();
		if(!StringUtils.isEmpty(root)){
			list_pro = this.categoryInfo(root.getData().get(0).getId());
		}
		model.addAttribute("localHtml", localHtml);
		model.addAttribute("root", root);
		model.addAttribute("categoryInfo", list_pro);
		return "sort";
	}
	
	
}
