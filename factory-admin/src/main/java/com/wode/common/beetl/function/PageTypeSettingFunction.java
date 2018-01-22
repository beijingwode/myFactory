package com.wode.common.beetl.function;

import java.util.List;

import javax.annotation.Resource;

import com.wode.factory.mapper.PageTypeDao;
import org.springframework.stereotype.Component;

import com.wode.factory.model.PageTypeSetting;

@Component
public class PageTypeSettingFunction {
	@Resource
	private PageTypeDao pageTypeDao;

	public List<PageTypeSetting> getByChannelIdPageTypeTitle() {

		return this.pageTypeDao.selectByChannelId(null);
	}

}
