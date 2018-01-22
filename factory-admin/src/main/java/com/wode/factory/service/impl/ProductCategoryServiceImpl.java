package com.wode.factory.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.mapper.ProductCategoryDao;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.vo.CategoryBrandVo;
import com.wode.factory.vo.ProductBrandVo;
import com.wode.factory.vo.ProductCategoryVo;

@Service("productCategoryService")
public class ProductCategoryServiceImpl implements ProductCategoryService, InitializingBean {
	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Autowired
	private RedisUtil redisUtil;

	private Map<String, String> map = new HashMap<String, String>();

	private Logger log = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

	@Override
	public List<ProductCategoryVo> getProductCategoryTree() {
		List<ProductCategoryVo> list_pro = this.productCategoryDao.selectProductCategoryTree();
		return list_pro;
	}

	@Override
	public ProductCategoryVo selectById(Long id) {
		ProductCategoryVo vo = new ProductCategoryVo();
		if (!StringUtils.isEmpty(id))
			vo = this.productCategoryDao.selectByPrimarkey(id);
		return vo;
	}

	@Override
	public PageInfo<ProductCategoryVo> selectProductCategory(Long rootId, String brotherOrderAll, String name, Integer pages, Integer size) {
		List<ProductCategoryVo> page = null;
		Boolean rootBoo = StringUtils.isEmpty(rootId);
		Boolean brotherBoo = StringUtils.isEmpty(brotherOrderAll);
		Boolean nameBoo = StringUtils.isEmpty(name);
		if ((nameBoo && rootBoo && brotherBoo) || (!nameBoo && rootBoo && brotherBoo)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			PageHelper.startPage(pages, size);
			page = this.productCategoryDao.selectProductCategory(map);
		} else if (!rootBoo && !brotherBoo) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rootId", rootId);
			map.put("brotherOrderAll", brotherOrderAll);
			PageHelper.startPage(pages, size);
			page = this.productCategoryDao.selectByNode(map);
		}
		return new PageInfo<ProductCategoryVo>(page);
	}

	@Override
	public Integer save(ProductCategory pro) {
		//父类id为空，表示添加一级标题
		if (StringUtils.isEmpty(pro.getPid())) {
			pro.setDeep(1);
			pro.setBrotherOrderAll(1 + "");

		} else {
			ProductCategory parent = this.productCategoryDao.selectByPrimarkey(pro.getPid());
			if (parent.getChildCount() == null) {
				parent.setChildCount(1);
			} else {
				parent.setChildCount(parent.getChildCount() + 1);
			}
			pro.setDeep(parent.getDeep() + 1);
			int childOrder = parent.getChildCount() + 1;
			pro.setBrotherOrderAll(parent.getBrotherOrderAll() + "_" + childOrder);
			pro.setPid(parent.getId());
			if (parent.getRootId() == null && parent.getDeep() == 1) {
				parent.setRootId(parent.getId());
			}
			pro.setRootId(parent.getRootId());
			this.productCategoryDao.updateProductCategory(parent);
		}
		pro.setCreateDate(new Date());

		pro.setChildCount(0);

		Integer i = this.productCategoryDao.insertProductCategory(pro);
		//更新缓存
		reCache();
		return i;
	}

	@Override
	public Integer update(ProductCategory vo) {
		vo.setUpdateDate(new Date());
		Integer i = this.productCategoryDao.updateProductCategory(vo);
		//更新缓存
		reCache();
		return i;
	}


	@Override
	public ActResult delete(Long id) {
		ProductCategory pc = productCategoryDao.selectByPrimarkey(id);

		if (pc == null) {
			return ActResult.fail("分类不存在");
		} else {

			List<ProductCategory> list = this.productCategoryDao.selectByPid(pc.getId());
			if (list != null && list.size() > 0) {
				return ActResult.fail("请先删除子分类");
			}
			long count = productCategoryDao.findProductCount(id);
			if (count > 0) {
				return ActResult.fail("有商品关联");
			}

			Integer del = productCategoryDao.delete(pc);

			//更新缓存
			reCache();
			return ActResult.success(null);

		}
	}


	@Override
	public Integer deleteBatchProductCategory(Long id, Long rootId,
	                                          String brotherOrderAll) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rootId", rootId);
		map.put("brotherOrderAll", brotherOrderAll);
		List<ProductCategoryVo> list_vo = this.productCategoryDao.selectByNode(map);
		if (list_vo.isEmpty()) {
			return 0;
		} else {
			//删除缓存信息,并且更新父节点的   子节点数量的字段
			for (ProductCategoryVo vo : list_vo) {
				if (id.equals(vo.getId())) {
					ProductCategory pro = this.productCategoryDao.selectByPrimarkey(vo.getPid());
					if (pro != null && pro.getChildCount() > 0) {
						pro.setChildCount(pro.getChildCount() - 1);
						this.productCategoryDao.updateProductCategory(pro);
					}
					break;
				}

			}
			Integer del = this.productCategoryDao.deleteBatch(list_vo);
			if (del > 0) {
				//更新缓存
				reCache();
				return 1;
			} else {
				return 0;
			}
		}
	}


	/**
	 * 功能说明：更新缓存
	 * 日期:	2015年7月3日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 */
	private synchronized void reCache() {
		if (!map.isEmpty()) {
			map.clear();
		}
		log.info("reload category  data in redis");

		/**
		 * deep == 1的节点信息
		 * */
		List<ProductCategory> listroot = this.productCategoryDao.selectByDeep(1);

		//缓存root级别分类(缓存第一节点的信息)
		cacheRoot(listroot);
		/**
		 * 缓存所有的单个类(所有节点的信息)
		 *
		 * 查询全部的商品分类信息，不需要连接查询
		 * */
		List<ProductCategory> list = this.productCategoryDao.selectAll();
		Map<String, String> singleMapCategorys = new HashMap<String, String>();
		for (ProductCategory pc : list) {
			singleMapCategorys.put(pc.getId() + "", JsonUtil.toJsonString(pc));
		}
		redisUtil.setData("PRODUCT_CATEGORY", singleMapCategorys);

		//缓存子级别分类(缓存每个节点的信息)
		cacheLastLevelCategory(listroot);
		redisUtil.setData("PRODUCT_CATEGORY_CHILD", map);

	}

	/**
	 * 功能说明：递归缓存子节点、叶子节点商品分类
	 * 日期:	2015年7月3日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param list
	 */
	private void cacheLastLevelCategory(List<ProductCategory> list) {

		for (ProductCategory pc : list) {
			//判断是否有子节点
			if (pc.getChildCount() != null && pc.getChildCount() > 0) {
				//根据父节点查询子节点
				List<ProductCategory> listChild = this.productCategoryDao.selectByPid(pc.getId());
				//缓存子节点selectLastLevel
				String childKey = pc.getRootId() + pc.getBrotherOrderAll();
				if (!StringUtils.isEmpty(listChild)) {
					map.put(childKey, JSON.toJSONString(listChild));
				}
				//递归查询
				if (!StringUtils.isEmpty(listChild)) {
					cacheLastLevelCategory(listChild);
				}
			}
		}
	}

	/**
	 * 功能说明：缓存root类别(一级节点数据)
	 * 日期:	2015年7月3日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param list 根据deep查询，并升序排序的信息
	 */
	private void cacheRoot(List<ProductCategory> list) {
		if (list != null && list.size() > 0) {
			redisUtil.setData("PRODUCT_CATEGORY_ROOT", JsonUtil.toJsonString(list));
		}
	}


	public List<ProductCategory> findLastLevel(Long rootId) {
		return productCategoryDao.findLastLevel(rootId);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.reCache();
	}


	@Override
	public ProductCategory getParentCategoryByid(Long categoryId) {
		return productCategoryDao.getParentCategoryByid(categoryId);
	}


	@Override
	public List<ProductCategory> findByDeep(int i) {
		return productCategoryDao.selectByDeep(i);
	}

	@Override
	public PageInfo<CategoryBrandVo> findCountList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<CategoryBrandVo> list = productCategoryDao.findCountList(params);
		return new PageInfo<CategoryBrandVo>(list);
	}

	@Override
	public PageInfo<CategoryBrandVo> findPCountList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<CategoryBrandVo> list = productCategoryDao.findPCountList(params);
		return new PageInfo<CategoryBrandVo>(list);
	}
	@Override
	public List<ProductCategory> selectByPid(Long pId) {
		return productCategoryDao.selectByPid(pId);
	}

}
