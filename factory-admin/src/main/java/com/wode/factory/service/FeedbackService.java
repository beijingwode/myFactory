package com.wode.factory.service;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.Feedback;

import java.util.Map;

/**
 * Created by zoln on 2015/11/16.
 */
public interface FeedbackService {
	PageInfo<Feedback> findPage(Map<String, Object> params);

	Feedback getFeedback(Long id);
}
