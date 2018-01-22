package com.wode.search;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest.AliasActions;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wode.search.Entity.ProductEntity;
import com.wode.search.util.IndexConfUtil;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 搜索相关管理类, 用于建立索引,删除索引 及 获取搜索对象
 * 
 * @author Bing King
 *
 */
public class WodeSearchManager {

	// 客户端对象
	private SearchClientFactory searchClientFactory;

	// 索引schema
	private String index;
	private String keyIndex;

	// 索引类型
	private String type;

	private TransportClient _client;
	// 搜索对象
	// private WodeSearcher searcher;

	// 关键字搜索的字段
	private String[] searchFields = {};

	// 每次查询个数
	private Integer pageSize = 20;

	private static Logger logger = LoggerFactory.getLogger(WodeSearchManager.class);

	protected void finalize() throws Throwable {
		if(_client != null) {
			logger.info("TransportClient close");
			_client.close();
		}
		super.finalize();
	}
	
	private TransportClient getClient(){
		if(_client==null){
			_client = searchClientFactory.getClient();
		}
		return _client;
	}
	/**
	 * 创建索引，需指定版本号，版本号需要连续，以便处理别名及导入数据
	 * @param index
	 * @param v
	 */
	public void createIndex(String index, int v) {
		try {
			XContentBuilder json = IndexConfUtil.getIndexIkJson();
			getClient().admin().indices().prepareCreate(index + "_v" + v).setSource(json).get();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设定type mapping,目前创建两个索引wode,product_key 每个索引含有一个type,product,以方便版本升级
	 * @param index
	 * @param v
	 */
	public void putMapping(String index, int v) {
		XContentBuilder jsonm = null;
		if ("wode".equals(index)) {
			jsonm = IndexConfUtil.getWodeProductMapping();
		} else {
			jsonm = IndexConfUtil.getProductSuggestMapping();
		}
		PutMappingRequest mapping = Requests.putMappingRequest(index + "_v" + v).type(type).source(jsonm);
		try {
			getClient().admin().indices().putMapping(mapping).actionGet();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 版本升级，软升级，新建一个版本，并解除旧版别名，新版增加别名，索引使用时以别名为准
	 * @param index
	 * @param v
	 */
	public void versionUp(String index,int v){
	    try {

    		IndicesAliasesRequest request = new IndicesAliasesRequest();
    		if(v>1) {
    			request.addAliasAction(AliasActions.remove().alias(index).index(index+"_v" + (v-1)));
    		}
    		request.addAliasAction(AliasActions.add().alias(index).index(index+"_v" + v));
    		
            getClient().admin().indices().aliases(request).actionGet();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 删除索引
	 * @param index
	 * @param v
	 */
	public void dropIndex(String index, int v) {
		try {

			getClient().admin().indices().prepareDelete(index + "_v" + v).get();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新索引关键字联想，从商品库中导出品牌、品类关键词，导入到key库中以便输入时快速联想，以后会增加热词
	 */
	public void refreshKey(){
	    try {
            
            DeleteByQueryAction.INSTANCE.newRequestBuilder(getClient())
            	.filter(QueryBuilders.matchAllQuery()) 
            	.source(keyIndex)                                 
            	.get();   
            
            SearchResponse response = getClient().prepareSearch(index)
                    .setTypes(type)
                    .setQuery(QueryBuilders.matchAllQuery())                 // Query
                    .setSize(1)
                    .addAggregation(AggregationBuilders.terms(SearchParams.Fields.BRAND).field(SearchParams.Fields.BRAND_KEYWORD).size(15000))
                    .addAggregation(AggregationBuilders.terms(SearchParams.Fields.PARENT_CATEGORY).field("category.parent.name").size(15000)
                    		.subAggregation(AggregationBuilders.terms(SearchParams.Fields.CATEGORY).field("category.name").size(15000))
                    		)
                    .get();
            Map<String, Long> keywords = new HashMap<String, Long>();
            
            getCategoryAggregations(response,keywords);
            getParamAggregations(response,keywords);

            BulkRequestBuilder bulkRequestBuilder = getClient().prepareBulk();
            for (String key : keywords.keySet()) {
                JSONObject jo =new JSONObject();
                jo.put("title", key);
                jo.put("cnt", keywords.get(key));
                JSONArray inputs= new JSONArray();
                JSONObject jo1 = new JSONObject();
                jo1.put("input", key);
                JSONObject jo2 = new JSONObject();
                jo2.put("input", getPingYin(key));
                JSONObject jo3 = new JSONObject();
                jo3.put("input", converterToFirstSpell(key));
                inputs.add(jo1);
                inputs.add(jo2);
                inputs.add(jo3);
                jo.put("suggest", inputs);
                
                
                bulkRequestBuilder.add(getClient().prepareIndex(keyIndex, type).setSource(jo.toJSONString(),XContentType.JSON));
			}
            

		    bulkRequestBuilder.execute().actionGet();

		    bulkRequestBuilder.request().requests().clear();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
	 * 更新索引关键字联想，从商品库中导出品牌、品类关键词，导入到key库中以便输入时快速联想，以后会增加热词
	 */
	public void removeAllWodeProduct(){
	    try {
            
            DeleteByQueryAction.INSTANCE.newRequestBuilder(getClient())
            	.filter(QueryBuilders.matchAllQuery()) 
            	.source(index)                                 
            	.get();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 更新商品库热迁移，将数据从旧版导出，并插入新版
	 * @param version
	 */
	public void refreshWodeData(int oldVersion){
	    try {
	    	removeAllWodeProduct();
            
            SearchResponse response = getClient().prepareSearch("wode_v"+oldVersion)
                    .setTypes(type)
                    .setQuery(QueryBuilders.matchAllQuery())                 // Query
                    .setSize(9999)
                    .get();

            BulkRequestBuilder bulkRequestBuilder = getClient().prepareBulk();
            SearchHit[] hits =response.getHits().getHits();
            for (int i = 0; i < hits.length; i++) {
    			SearchHit hit =hits[i];
    			HashMap<String, Object> source = (HashMap<String, Object>) hit.getSource();

    			bulkRequestBuilder.add(getClient().prepareIndex(index, type).setId((String)source.get("id")).setSource(JSONObject.toJSONString(source),XContentType.JSON));

    		}
		    bulkRequestBuilder.execute().actionGet();

		    bulkRequestBuilder.request().requests().clear();

            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	/**
	 * 建立索引
	 * 
	 * @param o
	 *            List集合,或其他可转换为json的非集合类
	 * @return
	 */
	public String buildIndex(Object o) {

		BulkRequestBuilder bulkRequestBuilder = getClient().prepareBulk();
		if (o instanceof List) {
			List<Object> indexData = (List<Object>) o;
			for (Object content : indexData) {
				ProductEntity p = (ProductEntity) content;
				bulkRequestBuilder.add(getClient().prepareIndex(getIndex(), getType()).setId(p.getProductId())
						.setSource(JSON.toJSONString(p), XContentType.JSON));
			}
		} else {
			ProductEntity p = (ProductEntity) o;
			bulkRequestBuilder.add(getClient().prepareIndex(getIndex(), getType()).setId(p.getProductId())
					.setSource(JSON.toJSONString(p), XContentType.JSON));
		}

		int tryTime = 1;
		while (tryTime < 4) {
			try {

				BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();

				bulkRequestBuilder.request().requests().clear();

				if (bulkResponse.hasFailures()) {

					// TODO:
					return bulkResponse.buildFailureMessage();
				} else {
					return null;
				}

			} catch (Exception e) {
				e.printStackTrace();
				tryTime++;
				if (e instanceof SocketTimeoutException) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				} else {
					return e.getLocalizedMessage();
				}
			}

		}

		return "budil index error and try " + tryTime + " times!!";
	}

	/**
	 * 删除索引
	 * 
	 * @return
	 */
	public String deleteIndex(Long id) {

		DeleteResponse r = getClient().prepareDelete(getIndex(), getType(), String.valueOf(id)).execute().actionGet();
		if (r.getResult() == Result.DELETED) {
			return "success";
		}
		return "failed";
	}

	/**
	 * 删除索引
	 * 
	 * @return
	 */
	public Map<String, Object> getIndexById(Long id) {

		GetResponse r = getClient().prepareGet(getIndex(), getType(), String.valueOf(id)).execute().actionGet();
		return r.getSource();
	}
	
	/**
	 * 更新数量值+1
	 */
	public void updateCountForIndex(Long id, String column, Integer offset) {
		getClient().prepareUpdate(getIndex(), getType(), String.valueOf(id))
				.setScript(new Script("ctx._source." + column + " += " + offset.toString())).get();
	}

	/**
	 * 更新属性值
	 */
	public void updateValueForIndex(Long id, String column, Integer value) {
		getClient().prepareUpdate(getIndex(), getType(), String.valueOf(id))
				.setScript(new Script("ctx._source." + column + " = " + value)).get();
	}

	/**
	 * 更新评论数+1
	 * 
	 * @param id
	 */
	public void updateCommentNum(Long id) {
		updateCountForIndex(id, "revNum", 1);
	}

	/**
	 * 更新销售量
	 * 
	 * @param id
	 */
	public void updateSaleNum(Long id, Integer offset) {
		updateCountForIndex(id, "saleNum", offset);
	}

	/**
	 * 更新浏览次数+1
	 * 
	 * @param id
	 */
	public void updateViewCntNum(Long id) {
		updateCountForIndex(id, "viewCount", 1);
	}

	/**
	 * 更新商品品牌等级
	 * 
	 * @param proId
	 * @param result
	 */
	public void updateBrandLevel(Long proId, Integer value) {
		updateValueForIndex(proId, "brandLevel", value);
	}


	/**
	 * 更新商品品牌等级
	 * 
	 * @param proId
	 * @param result
	 */
	public void resetStock(Long proId,Long skuId, Integer value) {
		Map<String, Object> old= this.getIndexById(proId);
		if(old!=null) {
			if(skuId.equals(old.get("minSkuId"))) {
				updateValueForIndex(proId, "stock", value);
			}
		}
	}

	/**
	 * 获取搜索对象
	 * @return
	 */
	public WodeSearcher getSearcher(){
		return new WodeSearcher(this.getClient(),this.index,this.keyIndex,this.type,this.searchFields,this.pageSize);
//		if (searcher == null) {
//			searcher = new WodeSearcher(this.client,this.index,this.type,this.searchFields,this.pageSize);
//			return searcher;
//		} else {
//			return searcher;
//		}
	}
	
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(String[] searchFields) {
		this.searchFields = searchFields;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public SearchClientFactory getSearchClientFactory() {
		return searchClientFactory;
	}

	public void setSearchClientFactory(SearchClientFactory searchClientFactory) {
		this.searchClientFactory = searchClientFactory;
	}

	public String getKeyIndex() {
		return keyIndex;
	}

	public void setKeyIndex(String keyIndex) {
		this.keyIndex = keyIndex;
	}

	/**
	 * 在搜索结果中提取分类的aggregations
	 *
	 * @param result
	 * @return
	 */
	private static void getCategoryAggregations(SearchResponse response,Map<String, Long> keywords) {
		if (response.getAggregations() != null) {
			Map<String, Aggregation> map = response.getAggregations().asMap();

			Aggregation cats = map.get(SearchParams.Fields.PARENT_CATEGORY);
			if (cats != null) {

				List<StringTerms.Bucket> aggs = ((StringTerms) cats).getBuckets();

				if (aggs != null && aggs.size() > 0) {
					for (int i = 0; i < aggs.size(); i++) {
						String key = aggs.get(i).getKeyAsString();
						if(keywords.containsKey(key)) {
							keywords.put(key, keywords.get(key)+aggs.get(i).getDocCount());							
						} else {
							keywords.put(key, aggs.get(i).getDocCount());
						}
						
						Map<String, Aggregation> map3 = aggs.get(i).getAggregations().asMap();
						Aggregation cats3 = map3.get(SearchParams.Fields.CATEGORY);
						List<StringTerms.Bucket> aggs3 = ((StringTerms) cats3).getBuckets();
						for (int j = 0; j < aggs3.size(); j++) {
							String key3 = aggs3.get(j).getKeyAsString();
							if(keywords.containsKey(key3)) {
								keywords.put(key3, keywords.get(key)+aggs3.get(j).getDocCount());							
							} else {
								keywords.put(key, aggs.get(j).getDocCount());
							}
						}
					}
				}
//
//				return agggs;
			}
		}
	}
	
	/**
	 * 在搜索结果中提取品牌/属性的aggregations
	 *
	 * @param result
	 * @return
	 */
	private void getParamAggregations(SearchResponse response,Map<String, Long> keywords) {
		Map<String, Long> agggs = new HashMap<String,Long>();
		if (response.getAggregations() != null) {
			Map<String, Aggregation> map = response.getAggregations().asMap();
			for (String key1 : map.keySet()) {
				if (key1.equalsIgnoreCase(SearchParams.Fields.PARENT_CATEGORY))
					continue;
				List<StringTerms.Bucket> aggs = ((StringTerms) map.get(key1)).getBuckets();

				if (aggs != null && aggs.size() > 0) {
					for (int i = 0; i < aggs.size(); i++) {
						String key = aggs.get(i).getKeyAsString();
						if(keywords.containsKey(key)) {
							keywords.put(key, keywords.get(key)+aggs.get(i).getDocCount());							
						} else {
							keywords.put(key, aggs.get(i).getDocCount());
						}
					}
				}
			}
		}
	}
	/**
     * 将字符串中的中文转化为拼音,英文字符不变
     *
     * @param inputString 汉字
     * @return
     */
	private String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String output = "";
        if (inputString != null && inputString.length() > 0
                && !"null".equals(inputString)) {
            char[] input = inputString.trim().toCharArray();
            try {
                for (int i = 0; i < input.length; i++) {
                    if (java.lang.Character.toString(input[i]).matches(
                            "[\\u4E00-\\u9FA5]+")) {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(
                                input[i], format);
                        output += temp[0];
                    } else
                        output += java.lang.Character.toString(input[i]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        } else {
            return "*";
        }
        return output;
    }

    /**
     * 汉字转换位汉语拼音首字母，英文字符不变
     *
     * @param chines 汉字
     * @return 拼音
     */
	private String converterToFirstSpell(String chines) {
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                	String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                	if(pinyins ==null) {
                		System.out.println(chines);
                	} else {
                		pinyinName += pinyins[0].charAt(0);
                	}
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }
}
