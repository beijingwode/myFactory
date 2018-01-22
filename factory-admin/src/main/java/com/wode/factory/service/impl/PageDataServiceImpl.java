package com.wode.factory.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.util.StringUtils;
import com.wode.factory.mapper.PageDataDao;
import com.wode.factory.mapper.PageTypeDao;
import com.wode.factory.model.PageData;
import com.wode.factory.model.PageTypeSetting;
import com.wode.factory.service.PageDataService;

@Service("pageDataService")
public class PageDataServiceImpl implements PageDataService{
	
	@Autowired
	private PageDataDao pageDataDao;
	@Autowired
	private PageTypeDao pageTypeDao;

	public PageInfo<PageData> selectByChannelId(Integer channelId, Integer pages,
			Integer size) {
		PageHelper.startPage(pages,size);
		List<PageData> li = this.pageDataDao.selectByChannelId(channelId);
		return new PageInfo<PageData>(li);
	}

	/* 删除
	 * @see com.wode.tongji.service.PageSettingService#deleteByPageId(java.lang.String[])
	 */
	@Override
	public Integer deleteByPageId(Integer id) {
		
		if(StringUtils.isEmpty(id)){
			return 0;
		}else{
			return this.pageDataDao.deleteByPageId(id);
		}
	}

	/* 根据id级联查询
	 * @see com.wode.tongji.service.PageSettingService#selectById(java.lang.Integer)
	 */
	@Override
	public PageData selectById(Integer id) {
		return this.pageDataDao.selectById(id);
	}

	@Override
	public Integer updatePageSetting(String imgUrl,PageData pd,Long oldPageTypeId) {
		if(StringUtils.isEmpty(pd.getId())||StringUtils.isEmpty(oldPageTypeId)||StringUtils.isEmpty(pd.getPageTypeId())){
			return 0;
		}else{
			PageData page = new PageData();
			//pageTypeId  两个id相同，表示所属位置没有进行修改
			if(pd.getPageTypeId().equals(oldPageTypeId)){
				page.setPageTypeId(pd.getPageTypeId());
			/**
			 * 两个id不相同的话，表示所属位置发生改变，需要验证所属位置中最大数量是否满足条件
			 * 如果所属位置最大数量不满足条件，将返回错误信息
			 * */
			}else{
				//pageTypeId(页面位置)对应的数据总数
				Integer totalNumber = this.pageDataDao.selectCountByPageTypeId(pd.getPageTypeId());
				//pageTypeId(页面位置)对应的数据
				PageTypeSetting pts = this.pageTypeDao.getById(pd.getPageTypeId());
				if(totalNumber>=pts.getMaxNum()){
					return -1;
				}else{
					page.setPageTypeId(pd.getPageTypeId());
				}
			}
			page.setId(pd.getId());
			page.setTitle(pd.getTitle());
			page.setOrders(pd.getOrders());
			page.setLink(pd.getLink());
			page.setUpdateDate(new Date());
			page.setImagePath(imgUrl);
			page.setPage(pd.getPage());
			return this.pageDataDao.updateById(page);
		}
	}

	@Override
	public Integer insertPageSetting(String pageTypeIdAndPid,String page,
			Integer orders, String title, String link, String imagePath) {
		PageData pageSet = new PageData();
		pageSet.setCreateDate(new Date());
		pageSet.setPageTypeId(Long.valueOf(pageTypeIdAndPid));
		pageSet.setOrders(orders);
		pageSet.setTitle(title);
		pageSet.setLink(link);
		pageSet.setImagePath(imagePath);
		pageSet.setPage(page);

		return this.pageDataDao.insertPageSetting(pageSet);
	}

	@Override
	public PageInfo<PageData> selectByPageTypeId(Integer pageTypeId,Integer channelId,Integer pages,Integer size) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pageTypeId", pageTypeId);
		map.put("channelId", channelId);
		PageHelper.startPage(pages,size);
		List<PageData> listPageDate = this.pageDataDao.selectByPageTypeId(map);
		return new PageInfo<PageData>(listPageDate);
	}

	@Override
	public PageInfo<PageData> selectList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<PageData> listPageDate = this.pageDataDao.getList(params);
		return new PageInfo<PageData>(listPageDate);
	}

	@Override
	public Integer selectCountByLink(String link) {
		return pageDataDao.selectCountByLink(link);
	}

}
