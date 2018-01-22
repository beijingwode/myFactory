package com.wode.tongji.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.tongji.mapper.AppMapper;
import com.wode.tongji.model.App;
import com.wode.tongji.service.AppService;

@Service("appService")
public class AppServiceImpl implements AppService{

	@Resource
	private AppMapper appMapper;
	
	@Override
	public PageInfo<App> findAllApp(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<App> list = appMapper.findPageInfo(params);
		return new PageInfo<App>(list);
	}

	@Override
	public int saveOrUpdate(App app) {
		if(null==app.getId()){
			return appMapper.insertSelective(app);
		}else{
			return appMapper.updateByPrimaryKeySelective(app);
		}
	}

	@Override
	public int deleteApp(Long id) {
		int i = appMapper.deleteByPrimaryKey(id);
		return i;
	}

	@Override
	public App selectByPrimaryKey(Long id) {
		return appMapper.selectByPrimaryKey(id);
	}

	@Override
	public App selectByUrl(String url) {
		return appMapper.selectByUrl(url);
	}
	
}
