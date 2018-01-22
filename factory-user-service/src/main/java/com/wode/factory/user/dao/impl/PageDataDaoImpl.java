package com.wode.factory.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.PageTypeSetting;
import com.wode.factory.user.dao.PageDataDao;
import com.wode.factory.user.vo.PageTypeSettingVo;
@Repository("pageDataDao")
public class PageDataDaoImpl extends BaseDao<PageTypeSetting,java.lang.Long> implements PageDataDao {
	@Override
	public String getIbatisMapperNamesapce() {
		return "PageTypeSettingMapper";
	}

	@Override
	public void saveOrUpdate(PageTypeSetting entity) throws DataAccessException {}
	
	@Override
	public List<PageTypeSettingVo> findPageDataInfo(String page, String type,int channelId, int pageNum, int pageSize) {
		Map<String,Object> map = new HashMap();
		map.put("page", page);
		map.put("channelId", channelId);
		map.put("type", type);
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findPageDataInfo", map, new RowBounds(pageNum, pageSize==0?500:pageSize));
	}


	@Override
	public List<PageTypeSettingVo> findPageDataInfo(String page, String type, int channelId) {
		return findPageDataInfo(page, type, channelId, 0, 0);
	}

	@Override
	public List<PageTypeSettingVo> findPageDataProducts(String page, String type, int channelId, int pageNum,
			int pageSize) {
		Map<String,Object> map = new HashMap();
		map.put("page", page);
		map.put("channelId", channelId);
		map.put("type", type);
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findPageDataProducts", map, new RowBounds(pageNum, pageSize));
	}

}
