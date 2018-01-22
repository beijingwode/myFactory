package com.wode.search;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wode.search.util.Key2CatUtil;

/**
 * 搜索对象,用于用户搜索
 *
 * @author Bing King
 */
public class WodeSearcher {

	// 客户端对象
	private TransportClient client;

	// 索引schema
	private String index;
	private String keyIndex;

	// 索引类型
	private String type;

	// 关键字需匹配字段
	private String[] searchFields;

	// 查询结果返回字段
	private String[] sourceIncludes;

	// 查询结果不返回字段
	private String[] sourceExcludes;
	
	// 每次查询个数
	private Integer pageSize;

	protected WodeSearcher(TransportClient client, String index, String keyIndex, String type, String[] searchFields, Integer pageSize) {
		this.client = client;
		this.index = index;
		this.keyIndex = keyIndex;
		this.type = type;
		this.searchFields = searchFields;
		this.pageSize = pageSize;
	}

	protected void finalize() {
		if(client != null) {
//			client.close();
		}
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setSearchFields(String[] searchFields) {
		this.searchFields = searchFields;
	}

	public void setFetchSource(String[] includes, String[] excludes) {
		this.sourceIncludes = includes;
		this.sourceExcludes = excludes;
	}
	/**
	 * 搜索主方法
	 * 
	 * @param paramString	参数字符串
	 * @param fields		属性名称组成的数组
	 * @param countBrand	是否统计品牌
	 * @param countCategory	是否统计分类
	 * @param withHighLight 是否需要高亮
	 * @return
	 */
	public WodeResult search(String paramString, String[] fields, boolean countBrand,boolean countCategory,boolean withHighLight) {
		return this.search(paramString, fields, countBrand, countCategory, withHighLight,null);
	}
	/**
	 * 搜索主方法
	 * 
	 * @param paramString	参数字符串
	 * @param fields		属性名称组成的数组
	 * @param countBrand	是否统计品牌
	 * @param countCategory	是否统计分类
	 * @param withHighLight 是否需要高亮
	 * @return
	 */
	public WodeResult search(String paramString, String[] fields, boolean countBrand,boolean countCategory,boolean withHighLight,String collapse) {

		//关键字包特殊处理
//		paramString =paramString.replace("cat=&", "");
//		paramString =paramString.replace("key=包", "cat=70300,70400,70500").replace("key=%E5%8C%85", "cat=70300,70400,70500");
		paramString  = Key2CatUtil.convertUrl(paramString);

		//处理搜索词中的特殊字符--start
		String regEx="[/]";  
        Pattern p = Pattern.compile(regEx);     
        Matcher m = p.matcher(paramString);     
        paramString =  m.replaceAll("").trim(); 
        //---end
        
		WodeResult wodeResult = new WodeResult();
		wodeResult.setUri(paramString);
		SearchParams params = new SearchParams(paramString);
		wodeResult.setSelectedFilters(params.getFls());
		wodeResult.setBrand(params.getBrand());
		wodeResult.setCategory(params.getCategory());
		wodeResult.setSort(params.getSortParam());
		wodeResult.setP(params.getActivePage());
		wodeResult.setPageSize(pageSize);
		
		try {
			// --进行普通的搜索查询
			BoolQueryBuilder query = QueryBuilders.boolQuery(); 
			this.buildQuery(params, query);
			this.buildAllFilter(query, params, fields);
			
			SearchRequestBuilder request =client.prepareSearch(this.index).setTypes(type).setQuery(query);
			request.setFrom((params.getActivePage() - 1) * this.pageSize);
			request.setSize(this.pageSize);
			if(!StringUtils.isEmpty(collapse)) {
				request.setCollapse(new CollapseBuilder(collapse));
			}
			SortBuilder[] sbs = getSortBuilder(params);
			for (SortBuilder sb : sbs) {
				request.addSort(sb);
			}
			if(withHighLight) {
				if (searchFields != null) {
					HighlightBuilder hb = new HighlightBuilder();
					for (String field : searchFields) {
						if("barcode".equals(field) || "categoryName".equals(field) || "brand".equals(field)) continue;
						hb.field(field);
					}
					request.highlighter(hb);
				}
			}
			if(this.sourceIncludes!=null || this.sourceExcludes !=null) {
				request.setFetchSource(sourceIncludes, sourceExcludes);
			}
			addAggregations(request,params,countBrand,countCategory);

			SearchResponse response = request.execute().actionGet();
			wodeResult.setCategories(getCategoryAggregations(response));
			Map<String, String[]> aggregations = getParamAggregations(response);
			wodeResult.setAggregations(aggregations);
			wodeResult.setHits(getHitsWithHighlight(response,withHighLight));
			wodeResult.setTotalNum(response.getHits().getTotalHits());
			setMaxScore(wodeResult,response);
			wodeResult.setP(params.getActivePage());
			wodeResult.setPageSize(pageSize);
			
		} catch (Exception e) {
			return wodeResult;
		}
		return wodeResult;
	}

	/**
	 * 搜索主方法
	 *
	 * @param paramString 参数字符串
	 * @param fields      属性名称组成的数组
	 * @param isSubPage   是否是分类页
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> suggest(String key) {
		List<JSONObject> rtn = new ArrayList<JSONObject>();;
		try {
			CompletionSuggestionBuilder completionSuggestionBuilder = new CompletionSuggestionBuilder("suggest");
	        completionSuggestionBuilder.prefix(key).size(10);
	        
	        String[] includes={"title","cnt"};
			//client.prepares
			SearchRequestBuilder request = client.prepareSearch(keyIndex).setTypes(type).setFetchSource(includes, null)
					.suggest((new SuggestBuilder()).addSuggestion("key_suggest", completionSuggestionBuilder));

			SearchResponse response = request.execute().actionGet();
			JSONObject jo = JSONObject.parseObject(response.toString());
			JSONArray ary =jo.getJSONObject("suggest").getJSONArray("key_suggest").getJSONObject(0).getJSONArray("options");
			for (Object object : ary) {
				JSONObject o=(JSONObject)object;
				rtn.add(o.getJSONObject("_source"));
			}
		} catch (Exception e) {
		}
		return rtn;
	}

	private void setMaxScore(WodeResult wodeResult, SearchResponse result) {
		if(wodeResult.getTotalNum()==0) {
			wodeResult.setMaxScore(0D);
		} else if(wodeResult.getHits().isEmpty()){
			wodeResult.setMaxScore(0D);
		} else {
			wodeResult.setMaxScore(new Double(result.getHits().getMaxScore()));
			
		}
	}

	/**
	 * 提取高亮字段值
	 *
	 * @return
	 */
	protected List<HashMap<String, Object>> getHitsWithHighlight(SearchResponse result,boolean withHighLight) {
		SearchHit[] hits = result.getHits().getHits();
		List<HashMap<String, Object>> results = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < hits.length; i++) {
			SearchHit hit =hits[i];
			HashMap<String, Object> source = (HashMap<String, Object>) hit.getSource();
			if(withHighLight) {
				Map<String, HighlightField> highlights = hit.getHighlightFields();
				if (highlights != null) {
					for (String key : highlights.keySet()) {
						if (source.containsKey(key)) {
							HighlightField h = highlights.get(key);
							source.put(key, (h.fragments()[0]).toString());
						}
					}
				}
			}
			source.put("es_metadata_id","");
			results.add(source);
		}
		return results;
	}


	/**
	 * 获取仅统计分类的SearchSourceBuilder
	 *
	 * @param params 搜索参数的封装对象
	 * @param fields 需要聚合统计的属性数组
	 * @return SearchSourceBuilder
	 */
	private SearchSourceBuilder getCategorySearchSourceBuilder(SearchParams params, String[] fields) {
		SearchSourceBuilder ssb = new SearchSourceBuilder();
//		AndFilterBuilder filters = buildParamFilter(params, fields);
//		ssb.query(buildQuery(params, filters));
		ssb.aggregation(AggregationBuilders.terms(SearchParams.Fields.PARENT_CATEGORY).field(SearchParams.Fields.PARENT_CATEGORY)
				.subAggregation(AggregationBuilders.terms(SearchParams.Fields.CATEGORY).field(SearchParams.Fields.CATEGORY)));
		return ssb;
	}

	/**
	 * 追加aggregation参数
	 *
	 * @param ssb               SearchSourceBuilder
	 * @param fields            需要聚合统计的属性数组
	 * @param isIncludeCategory 是否需要包含分类聚合
	 */
	private void addAggregations(SearchRequestBuilder request, SearchParams params, boolean countBrand,boolean countCategory) {
		// 添加属性字段
//		if (fields != null && fields.length > 0) {
//			for (String field : fields) {
//				if (StringUtils.isNotBlank(field))
//					ssb.aggregation(AggregationBuilders.terms(field).field(SearchParams.Param.PARAM_PREFIX + field).order(Terms.Order.term(true)));
//			}
//		}
		// 品牌
		if (countBrand) {
			request.addAggregation(AggregationBuilders.terms(SearchParams.Fields.BRAND).field(SearchParams.Fields.BRAND_KEYWORD).size(150));
		}
		if (countCategory) {
			request.addAggregation(AggregationBuilders.terms(SearchParams.Fields.PARENT_CATEGORY).field("category.parent.id")
					.subAggregation(AggregationBuilders.terms(SearchParams.Fields.CATEGORY).field("category.id")));
		}
	}

	/**
	 * 构造Query
	 *
	 * @param params  搜索参数封装对象
	 * @param filters 过滤器
	 * @return
	 */
	private BoolQueryBuilder buildQuery(SearchParams params, BoolQueryBuilder query) {
		if (StringUtils.isNotBlank(params.getKeyword())) {
			query.must(getKeywordQueryBuilder(params.getKeyword()));
		} else {
			query.must(QueryBuilders.matchAllQuery());
		}
		return query;
	}

	/**
	 * 获取关键字搜索的builder, 注入搜索的字段
	 *
	 * @param keyword 关键字
	 * @return
	 */
	private QueryStringQueryBuilder getKeywordQueryBuilder(String keyword) {
		QueryStringQueryBuilder sqb = QueryBuilders.queryStringQuery(keyword);
		if (searchFields != null && searchFields.length > 0) {
			int i=1;
			for (String field : searchFields) {
				//sqb.field(field);
				sqb.field(field, 1f/i);
				i=i*10;
			}
			//sqb.field("name.pinyin");
		}
		
		return sqb;
	}


	/**
	 * 构造包含全部条件的过滤器, 包括分类过滤
	 *
	 * @param params 搜索参数封装对象
	 * @param fields 需要聚合统计的属性数组
	 * @return
	 */
	private BoolQueryBuilder buildAllFilter(BoolQueryBuilder query,SearchParams params, String[] fields) {
		query =  buildParamFilter(query, params, fields);
		if (StringUtils.isNotBlank(params.getCategory())) {
			String[] cats =params.getCategory().split(",");
			query.filter(QueryBuilders.termsQuery(SearchParams.Fields.ALL_CATEGORY, cats));
		}
		if (StringUtils.isNotBlank(params.getPromotionId())) {
			query.filter(QueryBuilders.termQuery(SearchParams.Fields.PROMOTION_ID, params.getPromotionId()));
		}
		if (StringUtils.isNotBlank(params.getCustomCat())) {
			query.filter(QueryBuilders.termQuery(SearchParams.Fields.CUSTOM_CAT, params.getCustomCat()));
		}
		return query;
	}


	/**
	 * 建造仅包含属性,品牌的过滤器
	 *
	 * @param params 搜索参数封装对象
	 * @param fields 需要聚合统计的属性数组
	 * @return
	 */
	private BoolQueryBuilder buildParamFilter(BoolQueryBuilder query, SearchParams params, String[] fields) {
		Map<String, String> selectedFilters = params.getFls();
		if (selectedFilters != null && selectedFilters.size() > 0)
			for (Map.Entry<String, String> map : selectedFilters.entrySet()) {
				int flag = org.apache.commons.lang3.ArrayUtils.indexOf(fields, map.getKey());
				if (flag > -1)
					fields[flag] = null;
				
				query.filter(QueryBuilders.termQuery(map.getKey(), map.getValue()));
			}
		if (StringUtils.isNotBlank(params.getBrand())) {
			query.filter(QueryBuilders.termQuery(SearchParams.Fields.BRAND, params.getBrand()));
		}
		if (StringUtils.isNotBlank(params.getShop())) {
			query.filter(QueryBuilders.termQuery(SearchParams.Fields.SHOP, params.getShop()));
		}
		if (null != params.getSupplierId()) {
			query.filter(QueryBuilders.termQuery(SearchParams.Fields.SUPPLIERID, params.getSupplierId()));
		}
		if (StringUtils.isNotBlank(params.getSaleKbn())) {
			
			if("0".equals(params.getSaleKbn())) {
				query.filter(QueryBuilders.termsQuery(SearchParams.Fields.SALE_KBN, "1","2","3"));				
			} else if("8".equals(params.getSaleKbn())){
				query.filter(QueryBuilders.termsQuery(SearchParams.Fields.SALE_KBN, "0","3"));		
			}else if("0X".equals(params.getSaleKbn())) {
				query.filter(QueryBuilders.termsQuery(SearchParams.Fields.SALE_KBN, "0","3","1"));
			}else {
				query.filter(QueryBuilders.termQuery(SearchParams.Fields.SALE_KBN, params.getSaleKbn()));
			}
		}
		if (StringUtils.isNotBlank(params.getTagFlg())) {
			String pFlg = params.getTagFlg();
			if("1".equals(pFlg)) {
				pFlg = "X1";
			}
			pFlg=pFlg.toUpperCase();
			List<String> pflgS = new ArrayList<String>();
			pflgS.add(pFlg);
			for(int i=0;i<2;i++) {
				for (int j=pflgS.size()-1;j>-1;j--) {
					String string=pflgS.get(j);
					if(string.charAt(i)=='X') {
						pflgS.add(string.replaceFirst("X", "0"));
						pflgS.add(string.replaceFirst("X", "1"));
						
						pflgS.remove(j);
					} 
				}
			}

			// 标签 tagFlg 二进制占位符 XXXXXXXXXXXXX企业级限购企采 
			// 企采:  XXXXXX1
			// 企业级限购 :XXXXX1X
			List<String> cs = new ArrayList<String>();
			for (String string : pflgS) {
				cs.add(Integer.parseInt(string, 2)+"");
			}
			
			// tagFlg=1 为 企采， （暂定 tagFlg 应按位判断，增加其他时在做修改）
			query.filter(QueryBuilders.termsQuery(SearchParams.Fields.TAG_FLG, cs.toArray()));
		}
		if (StringUtils.isNotBlank(params.getDiscount())) {
			double from=0;
			double to=10;
			String dicount = params.getDiscount();
			if(dicount.indexOf("-")==-1) {
				if(dicount.contains(".")) {
					from=Double.valueOf(dicount);
					to=from;
				} else {
					from=Double.valueOf(dicount);
					to=from+0.9;
				}
			} else if(dicount.indexOf("-")==0) {
				to=Double.valueOf(dicount.substring(1));
			} else if(dicount.indexOf("-")==dicount.length()-1) {
				from=Double.valueOf(dicount.substring(0,dicount.length()-1));
			} else {
				String[] ary = dicount.split("-");
				from=Double.valueOf(ary[0]);
				to=Double.valueOf(ary[1]);
			}
			query.filter(QueryBuilders.rangeQuery(SearchParams.Fields.DISCOUNT).from(from).to(to));
		}
		//内购价
		if (StringUtils.isNotBlank(params.getSalePrice())) {
			double from=0;
			double to=9999999;
			String salePrice = params.getSalePrice();
			if(salePrice.indexOf("-")==-1) {
				if(salePrice.contains(".")) {
					from=Double.valueOf(salePrice);
					to=from;
				} else {
					from=Double.valueOf(salePrice);
					to=from+0.9;
				}
			} else if(salePrice.indexOf("-")==0) {
				to=Double.valueOf(salePrice.substring(1));
			} else if(salePrice.indexOf("-")==salePrice.length()-1) {
				from=Double.valueOf(salePrice.substring(0,salePrice.length()-1));
			} else {
				String[] ary = salePrice.split("-");
				from=Double.valueOf(ary[0]);
				to=Double.valueOf(ary[1]);
			}

			query.filter(QueryBuilders.rangeQuery(SearchParams.Fields.SALE_PRICE).from(from).to(to));
		}
		return query;
	}
	/**
	 * 构造排序
	 *
	 * @param params 搜索参数封装对象
	 * @return
	 */
	private SortBuilder[] getSortBuilder(SearchParams params) {
		SortBuilder[] sortBuilders;
		String[] sorts = params.getSort();
		if (sorts != null && sorts.length > 0) {
			sortBuilders = new SortBuilder[sorts.length];
			for (int i = 0; i < sorts.length; i++) {
				sortBuilders[i] = SortBuilders.fieldSort(sorts[i]).order(params.getOrder()[i]);
			}
		} else {
			sortBuilders = new SortBuilder[4];
			sortBuilders[0] = SortBuilders.scoreSort().order(SortOrder.DESC);
			sortBuilders[1] = SortBuilders.fieldSort("brandLevel").order(SortOrder.ASC);
			sortBuilders[2] = SortBuilders.fieldSort("discount").order(SortOrder.ASC);
			sortBuilders[3] = SortBuilders.fieldSort("maxFucoin").order(SortOrder.DESC);
		}
		return sortBuilders;
	}

	/**
	 * 在搜索结果中提取品牌/属性的aggregations
	 *
	 * @param result
	 * @return
	 */
	private Map<String, String[]> getParamAggregations(SearchResponse response) {
		Map<String, String[]> agggs = new HashMap<String, String[]>();
		if (response.getAggregations() != null) {
			Map<String, Aggregation> map = response.getAggregations().asMap();
			for (String key : map.keySet()) {
				if (key.equalsIgnoreCase(SearchParams.Fields.PARENT_CATEGORY))
					continue;
				List<StringTerms.Bucket> aggs = ((StringTerms) map.get(key)).getBuckets();

				if (aggs != null && aggs.size() > 0) {
					String[] values = new String[aggs.size()];
					for (int i = 0; i < values.length; i++) {
						values[i] = aggs.get(i).getKeyAsString();
					}
					agggs.put(key, values);
				}
			}
		}
		return agggs;
	}

	/**
	 * 在搜索结果中提取分类的aggregations
	 *
	 * @param result
	 * @return
	 */
	private Map<String, String[]> getCategoryAggregations(SearchResponse response) {
		if (response.getAggregations() != null) {
			Map<String, Aggregation> map = response.getAggregations().asMap();

			Aggregation parents = map.get(SearchParams.Fields.PARENT_CATEGORY);
			if (parents != null) {

				Map<String, String[]> agggs = new HashMap<String, String[]>();
				List<StringTerms.Bucket> aggs = ((StringTerms) parents).getBuckets();

				if (aggs != null && aggs.size() > 0) {
					for (Terms.Bucket bucket : aggs) {
						Map<String, Aggregation> map3 = bucket.getAggregations().asMap();
						Aggregation cats = map3.get(SearchParams.Fields.CATEGORY);
						List<StringTerms.Bucket> aggs3 = ((StringTerms) cats).getBuckets();
						String[] values = new String[aggs3.size()];

						for (int i = 0; i < aggs3.size(); i++) {
							values[i] = aggs3.get(i).getKeyAsString();
						}	
						
						agggs.put(bucket.getKeyAsString(), values);
					}
				}

				return agggs;
			}
		}
		return null;
	}
	
	public static void main(String[] ary) {
		try {
			System.out.println(URLDecoder.decode("key=%E5%8C%85","UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
