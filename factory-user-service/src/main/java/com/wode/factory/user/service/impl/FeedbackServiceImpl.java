package com.wode.factory.user.service.impl;

import com.wode.factory.model.Feedback;
import com.wode.factory.user.dao.FeedbackDao;
import com.wode.factory.user.service.FeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zoln on 2015/11/16.
 */
@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

	@Resource(name = "feedbackDao")
	FeedbackDao feedbackDao;

	@Override
	public int createFeedback(Feedback feedback) {
		return feedbackDao.insert(feedback);
	}
}
