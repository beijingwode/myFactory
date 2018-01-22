/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.dao;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.FactoryBaseDao;
import com.wode.factory.model.GradeMsgVo;
import com.wode.factory.model.UserPrizeRecord;

public interface UserPrizeRecordDao extends FactoryBaseDao<UserPrizeRecord>{

	List<GradeMsgVo> findGradeByGroup(Map<String, Object> map);

}