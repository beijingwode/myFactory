package com.wode.search;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 搜索结果对象
 *
 * @author Bing King
 */
public class WodeResult {

	//搜索结果
	private List<HashMap<String, Object>> hits;

	//搜索当前页
	private int p;

	//总页数
	private int totalPage = 0;

	//总数
	private Long totalNum = 0L;

	//总数
	private Double maxScore;
	
	//每页显示数量
	private int pageSize = 20;

	//过滤参数
	private Map<String, String[]> aggregations;

	//已选的过滤条件(仅包含属性)
	private Map<String, String> selectedFilters;

	//分类统计
	private Map<String, String[]> categories;

	//已选品牌
	private String brand;

	//已选分类
	private String category;

	//当前执行搜索的uri(仅包含参数部分)
	private String uri;

	//排序
	private String sort;

	public List<HashMap<String, Object>> getHits() {
		return hits;
	}

	public void setHits(List<HashMap<String, Object>> hits) {
		this.hits = hits;
	}

	public Long getP() {
		return p > this.getTotalPage() ? this.getTotalPage() : p;
	}

	public void setP(Integer p) {
		this.p = p;
	}

	public Long getTotalPage() {
		return totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public Double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Double maxScore) {
		this.maxScore = maxScore;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Map<String, String[]> getAggregations() {
		return aggregations;
	}

	public void setAggregations(Map<String, String[]> aggregations) {
		this.aggregations = aggregations;
	}

	public Map<String, String> getSelectedFilters() {
		return selectedFilters;
	}

	public void setSelectedFilters(Map<String, String> selectedFilters) {
		this.selectedFilters = selectedFilters;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Map<String, String[]> getCategories() {
		return categories;
	}

	public void setCategories(Map<String, String[]> categories) {
		this.categories = categories;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * 获得包含过滤条件的url字符串,其中不包括关键字/分类
	 *
	 * @return
	 */
	public String getFiltersParam() {
		StringBuffer params = new StringBuffer();
		try {
			if (StringUtils.isNotBlank(this.brand)) {
				params.append("&brand=").append(URLEncoder.encode(this.brand, "UTF-8"));
			}
			if (this.selectedFilters != null && this.selectedFilters.size() > 0) {
				params.append("&fl=");
				int i = 1;
				for (Map.Entry<String, String> entry : this.selectedFilters.entrySet()) {
					if (StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())) {
						params.append(URLEncoder.encode(entry.getKey(), "UTF-8")).append(SearchParams.Param.VALUE_SEPARATOR).append(entry.getValue());
					}
					if (i < this.selectedFilters.size()) {
						params.append(SearchParams.Param.FILTERS_SEPARATOR);
						i++;
					}
				}
			}
			if (StringUtils.isNotBlank(this.sort)) {
				params.append("&sort=").append(this.sort);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params.toString();
	}

	/**
	 * 根据传入的条件, 得出相应的url
	 *
	 * @param url   原始URL
	 * @param name  参数名称
	 * @param value 参数值
	 * @return
	 */
	public String getQueryUrl(String url, String name, String value) {
		boolean isHaveParmas = url.indexOf("?") > -1;
		try {
			if (SearchParams.Param.FILTERS.equalsIgnoreCase(name)) {
				StringBuffer fl = new StringBuffer("fl=");
				if (this.selectedFilters != null) {
					String[] vals = value.split(SearchParams.Param.VALUE_SEPARATOR);
					if (vals.length == 2 && StringUtils.isNotBlank(vals[0]) && StringUtils.isNotBlank(vals[1])) {
						if (StringUtils.isNotBlank(this.selectedFilters.get(vals[0]))) {
							int i = 1;
							for (Map.Entry<String, String> entry : this.selectedFilters.entrySet()) {
								if (StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())) {
									if (entry.getKey().equalsIgnoreCase(vals[0])) {
										i++;
										continue;
									}
									fl.append(URLEncoder.encode(entry.getKey(), "UTF-8")).append(SearchParams.Param.VALUE_SEPARATOR).append(entry.getValue());
								}
								if (i < this.selectedFilters.size()) {
									fl.append(SearchParams.Param.FILTERS_SEPARATOR);
									i++;
								}
							}
						} else {
							for (Map.Entry<String, String> entry : this.selectedFilters.entrySet()) {
								if (StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())) {
									fl.append(URLEncoder.encode(entry.getKey(), "UTF-8")).append(SearchParams.Param.VALUE_SEPARATOR).append(entry.getValue());
								}
								fl.append(SearchParams.Param.FILTERS_SEPARATOR);
							}
							fl.append(value);
						}
					}
					if (fl.length() == 3) return url.replaceFirst("&fl=[^=^&]+", "");
					return url.replaceFirst("fl=[^=^&]+", fl.toString());
				} else {
					return url + "&fl=" + value;
				}
			} else {
				if (url.indexOf(name) > -1) {
					if (StringUtils.isBlank(value))
						return url.replaceFirst("&" + name + "=[^=^&]+", "");
					else
						return url.replaceFirst(name + "=[^=^&]+", name + "=" + value);
				} else {
					return url + (isHaveParmas ? "&" : "?") + name + "=" + value;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
