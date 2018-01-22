package com.wode.factory.service;

import java.util.List;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.CheckReview;

/**
 *
 */
public interface CheckReviewService extends EntityService<CheckReview,Long> {

	public void deleteByAppr(Long apprId);
	public void insert(CheckReview model);

	List<CheckReview> selectByModel(CheckReview model);
	public void saveCheckReviews(Long apprId,List<CheckReview> ls,String createUser);
}
