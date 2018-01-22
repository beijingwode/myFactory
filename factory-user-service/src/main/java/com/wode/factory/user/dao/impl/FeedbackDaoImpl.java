package com.wode.factory.user.dao.impl;

import com.wode.common.db.DBUtils;
import com.wode.factory.model.Feedback;
import com.wode.factory.user.dao.FeedbackDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * Created by zoln on 2015/11/16.
 */
@Repository("feedbackDao")
public class FeedbackDaoImpl extends SqlSessionDaoSupport implements FeedbackDao  {
	@Autowired
	DBUtils dbUtils;

	@Autowired
	@Qualifier("sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	@Override
	public int insert(Feedback feedback) {
		long pk = dbUtils.CreateID();
		feedback.setId(pk);
		return getSqlSession().insert("FeedbackMapper.insert", feedback);
	}
}
