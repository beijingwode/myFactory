package com.wode.api.web.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.util.ActResult;
import com.wode.factory.model.Feedback;
import com.wode.factory.user.service.FeedbackService;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

	@Resource(name = "feedbackService")
	FeedbackService feedbackService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public ActResult feedback(Feedback feedback) {
		if(feedback.getContent().length()<1){
			return ActResult.fail("请输入反馈意见");
		}
		feedback.setCreateDate(new Date());
		feedbackService.createFeedback(feedback);
		return ActResult.successSetMsg("感谢您的宝贵意见");
	}
}
