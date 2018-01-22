package com.wode.search.Entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 索引商品类
 */
public class ProductEntity extends HashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4691158489678339736L;
	private String productId;

	public void setMaxFucoin(BigDecimal maxFucoin) {
		this.put("maxFucoin", maxFucoin);
	}
	public void setShopId(Long shopId) {
		this.put("shopId", shopId);
	}
	public void setSupplierId(Long supplierId) {
		this.put("supplierId", supplierId);
	}

	public void setShopName(String shopName) {
		this.put("shopName", shopName);
	}

	public void setPrice(BigDecimal price) {
		this.put("price", price);
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.put("salePrice", salePrice);
	}
	
	public void setDiscount(double discount) {
		this.put("discount", discount);
	}

	public void setCategory(Category category) {
		this.put("category", category);
	}

	public void setBrand(String brand) {
		this.put("brand", brand);
	}

	public void setName(String name) {
		this.put("name", name);
	}

	public void setImage(String image) {
		this.put("image", image);
	}

	public void setId(String id) {
		this.put("id", id);
	}

	public void setPromotion(String promotion) {
		this.put("promotion", promotion);
	}

	public void setParams(Map<String, Object> params) {
		this.put("params", params);
	}

	public void setPromotionIds(String promotionIds) {
		this.put("promotionIds", promotionIds);
	}

	public void setRevNum(Long revNum) {
		this.put("revNum", revNum);
	}

	public void setSaleNum(Integer saleNum) {
		this.put("saleNum", saleNum);
	}

	public void setSortNum(Integer sortNum) {
		this.put("sortNum", sortNum);
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId=productId;
		this.put("productId", productId);
	}

	public void setSaleKbn(String saleKbn) {
		this.put("saleKbn", saleKbn);
	}

	public void setBrandLevel(Integer brandLevel) {
		this.put("brandLevel", brandLevel);
	}
	//按分类名称查询商品
	public void setCategoryName(String  categoryName) {
		this.put("categoryName", categoryName);
	}
	//浏览次数
	public void setViewCount(Long viewCount) {
		this.put("viewCount", viewCount);
	}
	// 内购价最低skuId
	public void setMinSkuId(Long minSkuId) {
		this.put("minSkuId", minSkuId);
	}
	// 标签 tagFlg 二进制占位符 XXXXXXXXXXXXXX企采
	public void setTagFlg(Integer tagFlg) {
		this.put("tagFlg", tagFlg);
	}

	// 创建日期 商品审核上架日期
	public void setCreateDate(Date createDate) {
		this.put("createDate", createDate);
	}
	//增加库存
	public void setStock(Integer stock) {
		if(null != stock ) {
			this.put("stock", stock);
		}else{
			this.put("stock", 0);
		}
	}
	//增加库存
	public void setAllStock(Integer stock) {
		if(null != stock ) {
			this.put("allStock", stock);
		}else{
			this.put("allStock", 0);
		}
	}
	
	public class Category {
		private String id;

		private String name;

		private Category parent;

		private String ancestor;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Category getParent() {
			return parent;
		}

		public void setParent(Category parent) {
			this.parent = parent;
		}

		public String getAncestor() {
			return ancestor;
		}

		public void setAncestor(String ancestor) {
			this.ancestor = ancestor;
		}
	}
}
