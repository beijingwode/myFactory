package com.wode.factory.user.facade;

import com.wode.factory.model.Comments;
import com.wode.factory.model.EntParamCode;

public interface CommentsFacade {

	boolean save(Comments comment,boolean isNormal,Long questionnareId,String answers);
    
    void prizeForComment(Comments comment,EntParamCode commentPrize,String updName);
}
