package com.wode.search;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.sort.SortOrder;

import com.wode.search.util.UrlUtil;

/**
 * 搜索参数封装对象
 *
 * @author Bing King
 */
public class SearchParams {

    //关键字
    private String keyword;

    //当前页数
    private Integer activePage = 1;

    //分类参数
    private String category;

    //店铺自定义分类
    private String customCat;
    //特省
    private String saleKbn;
    //折扣
    private String discount;
    //内购价
    private String salePrice;
	// 标签 tagFlg 二进制占位符 XXXXXXXXXXXXXX企采
    private String tagFlg;

    //品牌参数
    private String brand;

    //排序字段
    private String[] sort;

    //排序方式
    private SortOrder[] order;

    //排序参数
    private String[] sortParams;

    private String sortParam;

    //店铺
    private String shop;

    //过滤条件
    private Map<String, String> fls;

    //促销ID
    private String promotionId;
    
    //商家id
    private String supplierId;

    public SearchParams(String paramString) {
        Map<String, String> params = UrlUtil.decodeParams(paramString);
        this.keyword = params.get(Param.KEYWORD);
        this.category = params.get(Param.CATEGORY);
        this.brand = params.get(Param.BRAND);
        this.shop = params.get(Param.SHOP);
        this.promotionId = params.get(Param.PROMOTION_ID);
        this.customCat = params.get(Param.CUSTOM_CAT);
        this.saleKbn = params.get(Param.SALE_KBN);
        this.discount = params.get(Param.DISCOUNT);
        this.salePrice = params.get(Param.SALE_PRICE);
        this.tagFlg = params.get(Param.TAG_FLG);
        this.supplierId =  params.get(Param.SUPPLIERID);
        if (params.containsKey(Param.ACTIVE_PAGE) && StringUtils.isNotBlank(params.get(Param.ACTIVE_PAGE)))
            this.activePage = Integer.valueOf(params.get(Param.ACTIVE_PAGE));
        if (params.containsKey(Param.SORTBY) && StringUtils.isNotBlank(params.get(Param.SORTBY))) {
            this.sortParam = params.get(Param.SORTBY);
            if (StringUtils.isNotBlank(sortParam)) {
                this.sortParams = params.get(Param.SORTBY).split(",");
                this.sort = new String[sortParams.length];
                this.order = new SortOrder[sortParams.length];
                String sortParam;
                for(int i = 0; i < sort.length; i++) {
                    sortParam = sortParams[i];
                    String[] sort_by = sortParam.split(Param.VALUE_SEPARATOR);
                    if (sort_by.length == 2) {
                        sort[i] = sort_by[0];
                        order[i] = sort_by[1].equals("0") ? SortOrder.ASC : SortOrder.DESC;
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(params.get(Param.FILTERS))) {
            String[] fls = params.get(Param.FILTERS).split(Param.FILTERS_SEPARATOR);
            if (fls.length > 0) {
                this.fls = new HashMap<String, String>();
                for (String fl : fls) {
                    if (StringUtils.isNotBlank(fl)) {
                        String[] f = fl.split(Param.VALUE_SEPARATOR);
                        if (f.length == 2) {
                            this.fls.put(f[0], f[1]);
                        }
                    }
                }
            }
        }
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getActivePage() {
        return activePage;
    }

    public void setActivePage(Integer activePage) {
        this.activePage = activePage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String[] getSort() {
        return sort;
    }

    public void setSort(String[] sort) {
        this.sort = sort;
    }

    public SortOrder[] getOrder() {
        return order;
    }

    public void setOrder(SortOrder[] order) {
        this.order = order;
    }

    public String getSortParam() {
        return sortParam;
    }

    public void setSortParam(String sortParam) {
        this.sortParam = sortParam;
    }

    public String[] getSortParams() {
        return sortParams;
    }

    public void setSortParams(String[] sortParams) {
        this.sortParams = sortParams;
    }

    public Map<String, String> getFls() {
        return fls;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public void setFls(Map<String, String> fls) {
        this.fls = fls;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getCustomCat() {
        return customCat;
    }

    public void setCustomCat(String customCat) {
        this.customCat = customCat;
    }

    public String getSaleKbn() {
		return saleKbn;
	}

	public void setSaleKbn(String saleKbn) {
		this.saleKbn = saleKbn;
	}

	public String getDiscount() {
		return discount;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getTagFlg() {
		return tagFlg;
	}

	public void setTagFlg(String tagFlg) {
		this.tagFlg = tagFlg;
	}

	
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}


	public class Param {
        //搜索关键字参数
        public static final String KEYWORD = "key";

        //当前页
        public static final String ACTIVE_PAGE = "page";

        //品牌
        public static final String BRAND = "brand";

        //分类参数
        public static final String CATEGORY = "cat";

        //排序
        public static final String SORTBY = "sort";

        //键值分隔符
        public static final String VALUE_SEPARATOR = "_";

        //多个过滤条件分隔符
        public static final String FILTERS_SEPARATOR = "@";

        //筛选条件
        public static final String FILTERS = "fl";

        //属性名称前缀
        public static final String PARAM_PREFIX = "params.";

        public static final String SHOP = "shop";

        public static final String PROMOTION_ID = "promotionId";
        
        public static final String ALL_CAT = "allCat";

        public static final String CUSTOM_CAT = "customCat";
        public static final String SALE_KBN = "saleKbn";
        public static final String DISCOUNT = "discount";
        public static final String SALE_PRICE = "salePrice";
        public static final String TAG_FLG = "tagFlg";
        public static final String SUPPLIERID = "supplierId";
    }

    class Fields {
        static final String CUSTOM_CAT = "customCat";
        static final String ALL_CATEGORY = "allCategory";
        static final String CATEGORY = "cat";
        static final String PARENT_CATEGORY = "parentCat";
        static final String BRAND = "brand";
        static final String BRAND_KEYWORD = "brand.keyword";
        static final String SHOP = "shopId";
        static final String PROMOTION_ID = "promotionId";
        static final String SALE_KBN = "saleKbn";
        static final String DISCOUNT = "discount";
        static final String SALE_PRICE = "salePrice";
        static final String SUPPLIERID = "supplierId";
        public static final String TAG_FLG = "tagFlg";
    }

}
