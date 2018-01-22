/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.service;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.model.GradeMsgVo;
import com.wode.factory.model.UserPrizeRecord;
public interface UserPrizeRecordService extends FactoryEntityService<UserPrizeRecord>{

	List<GradeMsgVo> findGradeByGroup(Map<String, Object> map);

}