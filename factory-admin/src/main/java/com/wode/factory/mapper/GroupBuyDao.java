package com.wode.factory.mapper;

import java.util.List;

import com.wode.factory.model.GroupBuy;

/**
 * Created by zoln on 2015/7/24.
 */
public interface GroupBuyDao  {

	/**
	 * 查询结束的团
	 * @return
	 */
	public List<GroupBuy> findEndGroupBuy();
	
	/**
	 * 查询需要退运费的团
	 * @return
	 */
	public List<GroupBuy> findNeedRefundShipp();
	/**
	 * 更新
	 * @return
	 */
	public int update(GroupBuy entity);

	/**
	 * 获取库存
	 * @param id
	 * @return
	 */
	public GroupBuy getById(Long id);
}
