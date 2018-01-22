package com.wode.factory.mapper;

import com.wode.factory.model.UserTicketHis;

/**
 * Created by zoln on 2015/7/24.
 */
public interface UserTicketHisDao extends  FactoryBaseDao<UserTicketHis> {

	void deleteBySupplierId(Long supplierId);
}
