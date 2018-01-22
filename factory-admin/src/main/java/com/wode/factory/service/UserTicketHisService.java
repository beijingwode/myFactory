package com.wode.factory.service;

import java.util.List;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.UserTicketHis;

/**
 *
 */
public interface UserTicketHisService extends EntityService<UserTicketHis,Long> {

	public List<UserTicketHis> selectByModel(UserTicketHis query);
}
