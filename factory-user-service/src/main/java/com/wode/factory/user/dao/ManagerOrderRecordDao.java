package com.wode.factory.user.dao;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.FactoryBaseDao;
import com.wode.factory.model.ManagerOrderRecord;

public interface ManagerOrderRecordDao  extends  FactoryBaseDao<ManagerOrderRecord>{

	List<ManagerOrderRecord> getManagerOrderRecordList(Map<String, Object> query);

}
