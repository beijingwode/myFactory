/**
 * 
 */
package com.wode.factory.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.common.util.ActResult;
import com.wode.factory.model.SaleBill;
import com.wode.factory.vo.ReceiptVo;
import com.wode.factory.vo.SaleBillDetailVo;


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
public interface SaleBillService extends EntityService<SaleBill,Long>{
	
	/**
	 * 功能说明：向数据库插入数据
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SupplierCloseCmd
	 */
	public void insert(SaleBill entity);

	/**
	 * 功能说明：通过明细合计出账单
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param id(明细插入时id)
	 */
	public SaleBill sumBydetail(Long id);
	/**
	 * @param 		
	 * @param page		页数
	 * @param pageSize  每页显示条数
	 * @return
	 */
	PageInfo<SaleBill> getPage(Map<String, Object> params);
	/**
	 * 修改账单状态值
	 * @param saleBill
	 * @return
	 */
	public ActResult<Object> updateSaleBillStatus(List<SaleBill> saleBill,BigDecimal amount,String code,Long updUser);
	/**
	 * 根据对账单id查询发票信息
	 * @param id
	 * @return
	 */
	public ActResult<ReceiptVo> getReceiptInfo(List<Long> id);
	/**
	 * 根据对账单id生成关联结算单
	 * @param id
	 * @return
	 */
	public ActResult<SaleBill> makeSailBill(Integer relationType,List<Long> id);
	/**
	 * 根据对账单id查询对账单及订单详情
	 * @param id
	 * @return
	 */
	public SaleBillDetailVo getSaleBillDetail(Long id);

	/**
	 * 分页条件查询对账单信息
	 * @param map
	 * @return
	 */
	public List<SaleBill> getByIds(String ids);
}
