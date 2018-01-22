package com.wode.factory.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.Feedback;
import com.wode.factory.service.FeedbackService;

/**
 * Created by zoln on 2015/11/16.
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackController {

	@Resource(name = "feedbackService")
	FeedbackService feedbackService;

	@RequestMapping
	public String toFeedback(Model model){
		return "feedback/base";
	}

	@RequestMapping("/list")
	public String list(@RequestParam Map<String, Object> params,Model model) {
		PageInfo page = feedbackService.findPage(params);
		model.addAttribute("page", page);
		return "feedback/list";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public String get(Long id, Model model) {
		Feedback feedback = feedbackService.getFeedback(id);
		model.addAttribute("feedback", feedback);
		return "feedback/single";
	}

}
