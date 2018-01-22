/**
 * 
 */
package com.wode.factory.facade;

import java.util.List;

import com.wode.common.util.ActResult;
import com.wode.factory.model.EmpBenefitFlow;
import com.wode.factory.model.Product;
import com.wode.factory.model.SaleDetail;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserTicketHis;


/**
 * 
 * <pre>
 * 功能说明: 账期自动执行
 * 日期:	2015年11月09日
 * 开发者:高永久
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 * </pre>
 */
public interface ProductExchangeFacade{
	
	/**
	 * 换领商品锁定及换领币分配
	 * @param pro
	 * @param updName
	 * @return
	 */
	public ActResult<List<UserTicketHis>> ExchangeProuctCheck(Product pro,String updName);
	
	/**
	 * 换领订单结算
	 * @param supplierId
	 * @param exchangeSales
	 */
	public void ExchangeOrderCount(Long supplierId,List<SaleDetail> exchangeSales);

	/**
	 * 换领订单结算
	 * @param supplierId
	 * @param exchangeSales
	 */
	public List<EmpBenefitFlow> stopExchangeAndShare(SupplierExchangeProduct sep);
	/**
	 * 换领订单结算
	 * @param supplierId
	 * @param exchangeSales
	 */
	public List<UserExchangeTicket> ExchangeShareNotify(SupplierExchangeProduct sep);
	/**
	 * 换领商品锁定及换领币分配
	 * @param pro
	 * @param updName
	 * @return
	 */
	public ActResult<List<EmpBenefitFlow>> ClearLimitTicket(SupplierExchangeProduct ticket,String updName);
	
}
