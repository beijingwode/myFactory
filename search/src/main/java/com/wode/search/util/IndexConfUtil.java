package com.wode.search.util;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;


public class IndexConfUtil {
	/**
	 * Wode index Config
	 * @return
	 */
	public static XContentBuilder getIndexIkJson(){
		XContentBuilder json = null;
		try {
			json = XContentFactory.jsonBuilder()
			.startObject()
				.startObject("index")
					.startObject("analysis")
						.startObject("analyzer")
							.startObject("ik_analyzer")
								.field("type","custom")
								.field("tokenizer","ik_max_word")
								.startArray("filter")
									.value("word_delimiter")
								.endArray()
							.endObject()
							.startObject("ik_smart_analyzer")
								.field("type","custom")
								.field("tokenizer","ik_smart")
								.startArray("filter")
									.value("word_delimiter")
								.endArray()
							.endObject()
						.endObject()
					.endObject()
				.endObject()
			.endObject();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * Wode index Config
	 * @return
	 */
	public static XContentBuilder getWodeProductMapping(){
		XContentBuilder mapping = null;
		try {
			mapping = XContentFactory.jsonBuilder()
			.startObject()
				//开启倒计时功能
//				.startObject("_ttl")
//					.field("enabled",false)
//				.endObject()
				.startArray("dynamic_templates")
					.startObject()
						.startObject("dynamic_field")
							.field("match","*")
							.field("path_match","params.*")
							.startObject("mapping")
								.field("type","string")
								.field("index","not_analyzed")
							.endObject()
						.endObject()
					.endObject()
				.endArray()
				.startObject("properties")
					.startObject("id")
						.field("type","string")
						.field("store",true)
					.endObject()
					.startObject("name")
						.field("type","string")
						.field("index_options","docs")
						.startObject("norms")
							.field("enabled",false)
						.endObject()
						.field("store",true)
						.field("index","analyzed")
						.field("analyzer","ik_analyzer")
//						.field("search_analyzer","ik_smart_analyzer")
//						.startObject("fields")
//							.startObject("pinyin")
//								.field("type","text")
//								.field("store","no")
//								.field("term_vector","with_positions_offsets")
//								.field("analyzer","ik_pinyin_analyzer")
//								.field("boost",1)
//							.endObject()
//						.endObject()
					.endObject()
					.startObject("shopName")
						.field("type","string")
						.field("index_options","docs")
						.startObject("norms")
							.field("enabled",false)
						.endObject()
						.field("store",true)
						.field("index","analyzed")
						.field("analyzer","ik_smart_analyzer")
					.endObject()
					.startObject("brand")
						.field("type","string")
						.field("store",true)
						.field("index","not_analyzed")
						.startObject("fields")
							.startObject("keyword")
								.field("type","keyword")
							.endObject()
						.endObject()
					.endObject()
					.startObject("price")
						.field("type","double")
						.field("store",true)
					.endObject()
					.startObject("salePrice")
						.field("type","double")
						.field("store",true)
					.endObject()
					.startObject("discount")
						.field("type","double")
						.field("store",true)
					.endObject()
					.startObject("saleKbn")
						.field("type","string")
						.field("store",true)
					.endObject()
					.startObject("promotionId")
						.field("type","string")
					.endObject()
					.startObject("categoryName")
						.field("type","string")
						.field("index_options","docs")
						.startObject("norms")
							.field("enabled",false)
						.endObject()
						.field("store",true)
						.field("index","analyzed")
						.field("analyzer","ik_smart_analyzer")
					.endObject()
					.startObject("tagFlg")
						.field("type","long")
						.field("store",true)
					.endObject()
					.startObject("createDate")
						.field("type","date")
						.field("format","yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
					.endObject()
					.startObject("category")
						.startObject("properties")
							.startObject("id")
								.field("type","text")
								.field("store",true)
								.field("fielddata",true)
								.field("index","not_analyzed")
								.startArray("copy_to")
									.value("allCategory")
									.value("cat")
								.endArray()
							.endObject()
							.startObject("name")
								.field("type","string")
								.field("store",true)
								.field("index","not_analyzed")
							.endObject()
							.startObject("createDateString")
								.field("type","string")
							.endObject()
							.startObject("updateDateString")
								.field("type","string")
							.endObject()
							.startObject("ancestor")
								.field("type","string")
								.startArray("copy_to")
									.value("allCategory")
									.value("cat")
								.endArray()
							.endObject()
							.startObject("parent")
								.startObject("properties")
									.startObject("id")
										.field("type","text")
										.field("store",true)
										.field("fielddata",true)
										.field("index","not_analyzed")
										.startArray("copy_to")
											.value("allCategory")
											.value("cat")
										.endArray()
									.endObject()
									.startObject("name")
										.field("type","string")
										.field("store",true)
										.field("store",true)
										.field("index","not_analyzed")
									.endObject()
								.endObject()
							.endObject()
						.endObject()
					.endObject()
					.startObject("allCategory")
						.field("type","text")
					.endObject()
					.startObject("cat")
						.field("type","text")
					.endObject()
				.endObject()
			.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mapping;
	}
	
	public static XContentBuilder getProductSuggestMapping(){
		XContentBuilder mapping = null;
		try {
			mapping = XContentFactory.jsonBuilder()
			.startObject()
				//开启倒计时功能
//				.startObject("_ttl")
//					.field("enabled",false)
//				.endObject()
				.startArray("dynamic_templates")
					.startObject()
						.startObject("dynamic_field")
							.field("match","*")
							.field("path_match","params.*")
							.startObject("mapping")
								.field("type","string")
								.field("index","not_analyzed")
							.endObject()
						.endObject()
					.endObject()
				.endArray()
				.startObject("properties")
					.startObject("title")
						.field("type","keyword")
					.endObject()
					.startObject("cnt")
						.field("type","long")
						.field("store",true)
					.endObject()
					.startObject("suggest")
						.field("type","completion")
					.endObject()
				.endObject()
			.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mapping;
	}
}
