/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.GradeMsgVo;
import com.wode.factory.model.UserPrizeRecord;
import com.wode.factory.user.dao.UserPrizeRecordDao;

@Repository("userPrizeRecordDao")
public class UserPrizeRecordDaoImpl extends  FactoryBaseDaoImpl<UserPrizeRecord> implements UserPrizeRecordDao{

    @Override
    public String getIbatisMapperNamesapce() {
        return "UserPrizeRecordMapper";
    }

	@Override
	public List<GradeMsgVo> findGradeByGroup(Map<String, Object> map) {
		return getSqlSession().selectList(getIbatisMapperNamesapce() + ".findGradeByGroup",map);
	}
}