package com.wode.factory.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.db.DBUtils;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.mapper.CheckReviewDao;
import com.wode.factory.model.CheckReview;
import com.wode.factory.service.CheckReviewService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("checkReviewService")
public class CheckReviewServiceImpl extends EntityServiceImpl<CheckReview,Long> implements CheckReviewService {

	@Autowired
	CheckReviewDao checkReviewMapper;
    @Autowired
    DBUtils dbUtils;
    
	@Override
	public EntityDao<CheckReview, Long> getDao() {
		return checkReviewMapper;
	}

	@Override
	public void deleteByAppr(Long apprId) {
		checkReviewMapper.deleteByAppr(apprId);
	}

	@Override
	public void saveCheckReviews(Long apprId, List<CheckReview> ls,String createUser) {
		this.deleteByAppr(apprId);
		
		Date now = new Date();
		for (CheckReview checkReview : ls) {
			checkReview.setCreateTime(now);
			checkReview.setCreateUser(createUser);
			
			this.insert(checkReview);
		}
	}

	@Override
	public List<CheckReview> selectByModel(CheckReview model) {
		return checkReviewMapper.selectByModel(model);
	}

	@Override
	public void insert(CheckReview model) {
		model.setId(dbUtils.CreateID());
		this.checkReviewMapper.insert(model);
	}
}
