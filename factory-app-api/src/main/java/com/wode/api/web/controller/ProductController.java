package com.wode.api.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.api.util.IPUtils;
import com.wode.common.util.ActResult;
import com.wode.factory.model.Attribute;
import com.wode.factory.model.ClientAccessLog;
import com.wode.factory.service.CategoryAttributeService;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.user.service.ClientAccessLogService;
import com.wode.factory.user.service.EntParamCodeService;
import com.wode.factory.user.service.InventoryService;
import com.wode.search.SearchParams;
import com.wode.search.WodeResult;
import com.wode.search.WodeSearchManager;
import com.wode.search.WodeSearcher;

/**
 * Created by Bing King on 2015/6/23.
 */
@Controller
@RequestMapping("/pSearch")
public class ProductController {

	@Autowired
	private WodeSearchManager wsm;

	@Resource(name = "productCategoryService")
	private ProductCategoryService catService;
	@Resource(name = "categoryAttributeService")
	private CategoryAttributeService attributeService;
    @Autowired
    private ClientAccessLogService clientAccessLogService;
	@Autowired
	private EntParamCodeService entParamCodeService;
	@Autowired
    private InventoryService inventoryService;
    /**
     * 检索  saleKbn=1特省  discount=f-t f-:大于f,-t:小于t,discount=x.y discount=x 大于3.0 小于3.9
     * 
     * @param request.QueryString
     * @return
     */
	@RequestMapping
	@ResponseBody
	public ActResult<WodeResult> search(HttpServletRequest request) {
		String keyword = request.getParameter(SearchParams.Param.KEYWORD);

		String queryString = request.getQueryString();
		WodeSearcher searcher = wsm.getSearcher();
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isNotBlank(pageSize)) {
			searcher.setPageSize(Integer.valueOf(pageSize));
		}

		//固定2个属性
		String[] fields = new String[]{"品牌", "价格"};
		WodeResult sr = searcher.search(queryString, fields,true, false,false);
		
//		if(sr.getHits() != null) {
//			if (StringUtils.isBlank(request.getParameter(SearchParams.Param.SORTBY))) {
//				Collections.sort(sr.getHits(), new HitComparator(keyword));
//			}
//		}
	        /*if (sr.getAggregations() != null) {
                model.addAttribute("brands", sr.getAggregations().get("brand"));
                sr.getAggregations().remove("brand");
                Map<String, SortedMap<String, String>> aggregations = new LinkedHashMap<>();
                for (Map.Entry<String, String[]> entry : sr.getAggregations().entrySet()) {
                    String[] vals = entry.getValue();
                    Map<String, String> agg = new HashMap<>();
                    for (int i = 0; i < vals.length; i++) {
                        agg.put(redisUtil.getData("default_key_rkey_" + String.valueOf(vals[i])), vals[i]);
                    }
                    aggregations.put(entry.getKey(), new TreeMap<>(agg));
                }
                model.addAttribute("aggregations", aggregations);
            }*/
		
		if(sr.getHits() != null){
			setQCProduct(sr.getHits());
			setSYProduct(sr.getHits());
		}
		clientAccessLogService.saveNormal(ClientAccessLog.ACCESS_TYPE_SEARCH, "search", keyword, null,null,null,IPUtils.getClientAddress(request),sr.getTotalNum().intValue(),sr.getMaxScore());
		ActResult<WodeResult> rtn = ActResult.success(sr);
		rtn.setMsg(entParamCodeService.getBenefitSubsidy().toString());
		return rtn;

	}
	/**
	 * 企采商品设置内购券
	 * @param hits
	 */
	private void setQCProduct(List<HashMap<String, Object>> hits) {
        for (int i = 0; i < hits.size(); i++) {
        	Map map=(Map)hits.get(i);
        	Integer tagFlg = MapUtils.getInteger(map, "tagFlg");
        	if(tagFlg==null){
	        }else if (tagFlg==1) {
	        	map.put("maxFucoin", "0.01");
        	}
        }
	}

	/**
	 * 分类检索 
	 * 
	 * @param cat 可以是一二三级分类id
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/category")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, @RequestParam("id") String cat, String page, String pageSize) {
		Map<String, Object> result = new HashMap();
		String queryString = request.getQueryString();
		if (cat != null) {
			String realCat = catService.getSearchCat(cat);
			queryString = queryString.replace("id="+cat, "id="+realCat);
			Integer cpage = page == null || Integer.valueOf(page) < 1 ? 1 : Integer.valueOf(page);
			queryString = queryString + "&cat=" + realCat + "&page=" + String.valueOf(cpage);
			WodeSearcher searcher = wsm.getSearcher();
			if (pageSize != null && Integer.valueOf(pageSize) > 0) {
				searcher.setPageSize(Integer.valueOf(pageSize));
			}
			//按照分类读取属性
			List<Attribute> attributes = attributeService.findByCategory(Long.valueOf(realCat.split(",")[0]));
			String[] fields = new String[attributes.size()];
			for (int i = 0; i < attributes.size(); i++) {
				if (attributes.get(i).getForSearch() == 1) {
					fields[i] = attributes.get(i).getName();
				}
			}

			WodeResult sr = searcher.search(queryString, fields,true, false,false);
//			if(sr.getHits() != null) {
//				String keyword = request.getParameter(SearchParams.Param.KEYWORD);
//				if (StringUtils.isBlank(request.getParameter(SearchParams.Param.SORTBY))) {
//					Collections.sort(sr.getHits(), new HitComparator(keyword));
//				}
//			}
			if(sr.getHits() != null){
				setQCProduct(sr.getHits());
				setSYProduct(sr.getHits());
			}
			clientAccessLogService.saveNormal(ClientAccessLog.ACCESS_TYPE_SEARCH, cat.toString(), "分类", null,null,null,IPUtils.getClientAddress(request),sr.getTotalNum().intValue(),sr.getMaxScore());
			result.put("result", sr);
			result.put("message", "success");
			Map<String,Integer> map = new HashMap<String,Integer>();
			map.put("totalPage", sr.getTotalPage().intValue());
			map.put("totalNum", sr.getTotalNum().intValue());
			result.put("data", map);
			result.put("msg", entParamCodeService.getBenefitSubsidy().toString());
			return result;
		} else {
			result.put("message", "无分类信息!");
			return result;
		}
	}

	/**
	 * 试用商品设置内购券
	 * @param hits
	 */
	private void setSYProduct(List<HashMap<String, Object>> hits) {
        for (int i = 0; i < hits.size(); i++) {
        	Map map=(Map)hits.get(i);
        	Integer salekbn = MapUtils.getInteger(map, "saleKbn");
        	Long skuId = MapUtils.getLong(map, "minSkuId");
        	if(salekbn==null){
	        }else if (salekbn==5 && skuId!=null) {
	        	map.put("stock", inventoryService.getInventoryFromRedis(skuId));
        	}
        }
	}
	
	@RequestMapping("/promotion")
	@ResponseBody
	public Map<String, Object> getPromotionProducts(HttpServletRequest request, @RequestParam("type") String promotionId, Integer page, Integer pageSize) {
		Map<String, Object> result = new HashMap();
		String queryString = request.getQueryString();
		if (StringUtils.isNotBlank(promotionId)) {
			queryString = queryString + "&promotionId=" + promotionId;
			WodeSearcher searcher = wsm.getSearcher();
			if (pageSize != null && pageSize > 0) {
				searcher.setPageSize(pageSize);
			}
			String[] fields = new String[]{"品牌", "价格"};
			WodeResult sr = searcher.search(queryString, fields, false, false,false);
			if (sr.getHits().size() > 0) {
				setQCProduct(sr.getHits());
				result.put("result", sr);
				result.put("message", "success");
			} else {
				result.put("message", "fail");
			}
			return result;
		} else {
			result.put("message", "fail");
			return result;
		}
	}
	
	@RequestMapping("/page")
	public ModelAndView page(HttpServletRequest request, String params,String fromPageKey) {
		ModelAndView result = new ModelAndView("pSearch");
		String pageKey="search";
		if(!StringUtils.isEmpty(fromPageKey)) {
			pageKey=fromPageKey;
		}
		result.addObject("params",params==null?null:params.replace("____", "&").replace("$$$$", "=").replace("****", "="));
		result.addObject("pageKey",pageKey);
		return result;
	}
}
