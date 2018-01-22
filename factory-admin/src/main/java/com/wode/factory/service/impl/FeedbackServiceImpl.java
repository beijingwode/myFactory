package com.wode.factory.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.FeedbackDao;
import com.wode.factory.model.Feedback;
import com.wode.factory.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zoln on 2015/11/16.
 */
@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	FeedbackDao feedbackDao;

	@Override
	public PageInfo<Feedback> findPage(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Feedback> list = feedbackDao.findListBySelective(params);
		return new PageInfo(list);
	}

	@Override
	public Feedback getFeedback(Long id) {
		return feedbackDao.findById(id);
	}
}
