package com.wode.factory.mapper;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.CheckReview;

/**
 * Created by zoln on 2015/7/24.
 */
public interface CheckReviewDao extends  EntityDao<CheckReview,Long> {
	
	public void deleteByAppr(Long apprId);

	void insert(CheckReview model);
	List<CheckReview> selectByModel(CheckReview model);
}
