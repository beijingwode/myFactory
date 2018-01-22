package com.wode.factory.user.web.controller;

import com.wode.factory.model.Feedback;
import com.wode.factory.user.service.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by wangbing on 2015/11/16.
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackController {

	@Resource(name = "feedbackService")
	FeedbackService feedbackService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String feedback(Feedback feedback) {
		feedback.setContent(HtmlUtils.htmlEscape(feedback.getContent()));
		feedback.setCreateDate(new Date());
		feedbackService.createFeedback(feedback);
		return "redirect:/feedback-signal.html";
	}
}
