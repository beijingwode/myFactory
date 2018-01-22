package com.wode.factory.user.facade.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.Comments;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.CommentsService;
import com.wode.factory.user.dao.SupplierDao;
import com.wode.factory.user.facade.CommentsFacade;
import com.wode.factory.user.service.ProductService;
import com.wode.factory.user.service.QuestionnaireAnswerService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.service.SuborderitemService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.vo.SubOrderItemVo2;

@Service("commentsFacade")
public class CommentsFacadeImpl implements  CommentsFacade {

    @Autowired
    private SuborderitemService suborderitemService;

    @Autowired
    private SuborderService suborderService;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private UserService userService;
    
    @Autowired
    private CommentsService commentsService;
    @Autowired
    private QuestionnaireAnswerService questionnaireAnswerService;
    
	@Override
	@Transactional
	public boolean save(Comments comment,boolean isNormal,Long questionnareId,String answers) {
		boolean rtn=false;
		//子单项(商品单位) 评论状态更新
        Suborderitem suborderitem = suborderitemService.getById(comment.getSubOrderItemId());
        if(suborderitem.getCommentFlag() != null && suborderitem.getCommentFlag()==1)  {
        	rtn = false;
        } else {
        	rtn = true;
        	suborderitem.setUpdateTime(new Date());
        	suborderitem.setUpdateBy("commnet");
        	suborderitem.setCommentFlag(1);
        	suborderitemService.update(suborderitem);
        }

		//子单 评论状态更新
        Suborder suborder = suborderService.getById(comment.getSubOrderid());
        //查询是否有未评论子单项
		SubOrderItemVo2 vo = new SubOrderItemVo2();
        vo.setUserId(comment.getUserId());
        vo.setSubOrderId(comment.getSubOrderid());
        vo.setCommentFlag(0);
        com.github.pagehelper.PageInfo<SubOrderItemVo2> pList= suborderitemService.findForComment(vo,1,100);
        
        //子单项全部评论后，子单状态更新
        if(pList.getSize()==0) {
	        suborder.setCommentStatus(1);
	        suborderService.update(suborder);
        }
        
        if(rtn) {
	        comment.setSuborderitem(suborderitem);
	        comment.setSuborder(suborder);
        	if(isNormal) {
		        //更新评论数
		        comment.setProduct(productService.getById(comment.getProductId()));
		        UserFactory user = userService.getById(comment.getUserId());
		        comment.setUser(user);
		        if(user!=null && !"1".equals(comment.getAnonymous())) {
		        	String nickName = user.getNickName();
		        	String phone = user.getPhone();
		            if (!StringUtils.isEmpty(nickName) && !StringUtils.isEmpty(phone)) {
		    			if (nickName.equals(phone)) {
		    				comment.setAnonymous("1");
		    			}
		    		}
		        }
		        comment.setSupplier(supplierDao.getById(comment.getSupplyid()));
		        
				commentsService.saveToCacheAndMongo(comment);
			} else {
				questionnaireAnswerService.answerQuestion(answers, comment.getUserId(), questionnareId);
			}
        }
        
        return rtn;
	}

	@Override
	public void prizeForComment(Comments comment,EntParamCode commentPrize,String updName) {
		if(comment.getSuborderitem()!=null ) {
		    Suborderitem item = comment.getSuborderitem();
			String desc = "";
			if((commentPrize!=null && !"1".equals(commentPrize.getStopFlg())) || (item.getSaleKbn()!=null && item.getSaleKbn()==5) ) {
				BigDecimal absCash = BigDecimal.ZERO;
				BigDecimal absTicket = BigDecimal.ZERO;
				
				//平台奖励内购券
				if(commentPrize!=null && !"1".equals(commentPrize.getStopFlg())) {
					if(item.getCompanyTicket()!=null) {
						absTicket = NumberUtil.toBigDecimal(commentPrize.getValue());
						absTicket = absTicket.multiply(item.getCompanyTicket()).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_DOWN);
					}
					desc =commentPrize.getDescr();
				}
				
				//试用奖励现金券
				if(item.getSaleKbn()!=null && item.getSaleKbn()==5) {
					absCash = item.getEmpPrice().multiply(NumberUtil.toBigDecimal(item.getNumber()));
				}
				
				//异常数据处理
				if(absCash==null) absCash = BigDecimal.ZERO;
				if(absTicket==null) absTicket = BigDecimal.ZERO;
				
				//奖励数据有
				if(absCash.compareTo(BigDecimal.ZERO)>0 || absTicket.compareTo(BigDecimal.ZERO)>0) {
					try{
						if(StringUtils.isEmpty(desc)) {
							desc="感谢您的积极反馈，商品评价后获得奖励：现金券"+absCash+",内购券"+absTicket;
						} else {
							if(desc.contains("XXX")) {
								desc=desc.replace("XXX", absTicket+"");
							} else {
								desc=desc+",现金券"+absCash;
							}
							desc=desc+absTicket;
						}
						Map paramMap=new HashMap();
						paramMap.put("empId", comment.getUserId());
						paramMap.put("absCash", absCash);
						paramMap.put("absTicket", absTicket);
						paramMap.put("empName", updName);
						paramMap.put("key", item.getSubOrderId());
						paramMap.put("desrc",desc);
						paramMap.put("updName",updName);
		
						HttpClientUtil.sendHttpRequest("post", Constant.QIYE_API_URL+"api/commentPrize", paramMap);
					
					} catch (Exception e) {
						
					}
				}
			}
		}
	}
}
