package com.wode.factory.mapper;

import com.wode.factory.model.Feedback;

import java.util.List;
import java.util.Map;

/**
 * Created by zoln on 2015/11/16.
 */
public interface FeedbackDao {

	List<Feedback> findListBySelective(Map<String, Object> map);

	Feedback findById(Long id);
}
