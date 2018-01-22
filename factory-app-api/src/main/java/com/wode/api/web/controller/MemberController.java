package com.wode.api.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.api.util.Constant;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.service.ExpressComService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.vo.SubOrderVo;

@Controller
public class MemberController extends BaseController {
    @Qualifier("suborderService")
    @Autowired
    private SuborderService suborderService;
    @Qualifier("expressComService")
    @Autowired
    private ExpressComService expressComService;
    
    // 订单详情
    @RequestMapping("/orderLogistics")
    public ModelAndView orderDetail(HttpServletRequest request, String subOrderId, Long userId) {
        ModelAndView result = null;
        SuborderQuery query = new SuborderQuery();
        query.setUserId(userId);
        query.setSubOrderId(subOrderId);
        SubOrderVo subOrder = this.suborderService.findOrderDetailById(query);
        result = new ModelAndView("logistics");
        //result.addObject("subOrder", subOrder);

	    result.addObject("comIcon", "images/ls_p1.png");
	    if(!StringUtils.isEmpty(subOrder.getExpressType())){
		    ExpressCompany ci = expressComService.getExpressComById(subOrder.getExpressType());
		    if(ci!=null) {
			    result.addObject("comName", ci.getName());
			    result.addObject("comIcon", "images/brand/icon_" + ci.getJaneSpell() + ".png");
			    result.addObject("expressCom", ci.getPinYin());
			    result.addObject("expressNo", subOrder.getExpressNo());
			    result.addObject("searchId", userId);
		    }
	    }
        return result;
    }

    // 订单详情
    @RequestMapping("/logistics")
    public ModelAndView logistics(HttpServletRequest request, String expressType, String expressNo, Long userId) {
        ModelAndView result = new ModelAndView("logistics");
	    result.addObject("comIcon", "images/ls_p1.png");
		result.addObject("localHtml", Constant.pageFont);
	    if(!StringUtils.isEmpty(expressType)){
		    ExpressCompany ci = expressComService.getExpressComById(expressType);
		    if(ci!=null) {
			    result.addObject("comName", ci.getName());
			    result.addObject("comIcon", "images/brand/icon_" + ci.getJaneSpell() + ".png");
			    result.addObject("expressCom", ci.getPinYin());
			    result.addObject("expressNo", expressNo);
			    result.addObject("searchId", userId);
		    }
	    }
        return result;
    }
}
