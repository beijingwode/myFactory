package com.wode.factory.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.CmsContent;
import com.wode.factory.user.dao.CmsContentDao;
@Repository("cmsContentDao")
public class CmsContentDaoImpl extends BaseDao<CmsContent,java.lang.Long> implements CmsContentDao {
	@Override
	public String getIbatisMapperNamesapce() {
		return "CmsContentMapper";
	}

	/* 分页查询栏目对应的内容系信息
	 * @see com.wode.factory.user.dao.CmsChannelDao#findPageInfo(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public List<CmsContent> findPageInfo(Map<String,Object> map) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findPageInfo", map);
	}

	@Override
	public void saveOrUpdate(CmsContent entity) throws DataAccessException {}

	@Override
	public List<CmsContent> getBychannelId(Long channelId,Long id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("channelId", channelId);
		map.put("id", id);
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".getBychannelId", map);
	}

	@Override
	public Long findByChannelIdCount(CmsContent cmsContent) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findPage_count", cmsContent);
	}

}
