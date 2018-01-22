package com.wode.factory.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.CmsChannel;
import com.wode.factory.model.CmsContent;
import com.wode.factory.user.dao.CmsChannelDao;
@Repository("cmsChannelDao")
public class CmsChannelDaoImpl extends BaseDao<CmsChannel,java.lang.Long> implements CmsChannelDao {
	@Override
	public String getIbatisMapperNamesapce() {
		return "CmsChannelMapper";
	}
	
	@Override
	public void saveOrUpdate(CmsChannel entity) throws DataAccessException {}

	/* 查询全部栏目信息
	 * @see com.wode.common.frame.base.BaseDao#findAll()
	 */
	@Override
	public List<CmsChannel> findAllCmsChannel() {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findPage");
	}

	/* 分页查询栏目对应的内容系信息
	 * @see com.wode.factory.user.dao.CmsChannelDao#findPageInfo(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<CmsContent> findPageInfo(Integer channdlId, Integer pages,
			Integer size) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("channdlId", channdlId);
		map.put("start", pages==1?0:(pages*size-size));
		map.put("size", size);
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findPageInfo", map);
	}

}
