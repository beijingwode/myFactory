/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Payment;

public interface PaymentDao extends  EntityDao<Payment,Long>{
	public void saveOrUpdate(Payment entity);
	public Payment getByTradeNo(String way,String tradeNo);

}
