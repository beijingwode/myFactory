package com.wode.factory.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.util.StringUtils;
import com.wode.factory.mapper.SpecificationDao;
import com.wode.factory.mapper.SpecificationValueDao;
import com.wode.factory.model.SpecificationValue;
import com.wode.factory.service.SpecificationService;
import com.wode.factory.vo.SpecificationVo;
@Service("specificationService")
public class SpecificationServiceImpl implements SpecificationService{
	@Autowired
	private SpecificationDao specificationDao;
	@Autowired
	private SpecificationValueDao specificationValueDao;
	
	/* 查询规格信息
	 * @see com.wode.web.tongji.service.SpecificationService#selectSpecification(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public PageInfo<SpecificationVo> selectSpecification(Integer pages, Integer size,Long categoryId) {
		// TODO Auto-generated method stub  
		Map<String,Long> map = new HashMap<String, Long>();
		map.put("categoryId", categoryId);
		PageHelper.startPage(pages,size);
		List<SpecificationVo> speVo = this.specificationDao.selectSpecification(map);
		return new PageInfo(speVo);
	}
	/* 根据规格id查询规格信息
	 * @see com.wode.web.tongji.service.SpecificationService#selectById(java.lang.Long)
	 */
	@Override
	public SpecificationVo selectById(Long id) {
		// TODO Auto-generated method stub
		return this.specificationDao.selectById(id);
	}
	/* 根据id删除规格及规格值信息
	 * @see com.wode.web.tongji.service.SpecificationService#deleteById(java.lang.Long)
	 */
	@Override
	public Integer deleteById(Long id) {
		// TODO Auto-generated method stub
		List<SpecificationValue> spe = this.specificationValueDao.selectBySpecificationId(id);
		if(!spe.isEmpty()){
			this.specificationValueDao.deleteBySpecificationId(id);
		}
		return this.specificationDao.deleteById(id);
	}

	/* 删除规格信息
	 * @see com.wode.web.tongji.service.SpecificationService#updateSpecificationVo(com.wode.web.tongji.vo.SpecificationVo)
	 */
	@Override
	public Integer updateSpecificationVo(SpecificationVo speVo) {
		// TODO Auto-generated method stub
		speVo.setUpdateDate(new Date());
		return this.specificationDao.updateById(speVo);
	}

	/* 添加规格信息
	 * @see com.wode.web.tongji.service.SpecificationService#insertSpecificationVo(com.wode.web.tongji.vo.SpecificationVo)
	 */
	@Override
	public Integer insertSpecificationVo(SpecificationVo speVo) {
		// TODO Auto-generated method stub
		speVo.setCreatedDate(new Date());
		return this.specificationDao.insertSpecification(speVo);
	}
	/* 根据规格id查询规格值信息
	 * @see com.wode.web.tongji.service.SpecificationService#selectSpecificationValue(java.lang.Long)
	 */
	@Override
	public List<SpecificationValue> selectSpecificationValue(Long specificationId) {
		// TODO Auto-generated method stub
		return this.specificationValueDao.selectBySpecificationId(specificationId);
	}
	/* 修改规格值全部的信息
	 * @see com.wode.factory.service.SpecificationService#updateAllSpecificationValue(java.lang.String[], java.lang.String[], java.lang.String[])
	 */
	@Override
	public Integer updateSpecificationValue(String[] add, String[] update,
			String[] del,Long speId) {
		// TODO Auto-generated method stub
		List<SpecificationValue> delSpeValueList=null;
		List<SpecificationValue> addSpeValueList =null;
		List<SpecificationValue> updateSpeValueList = null;
		if(del.length>0){
			delSpeValueList = this.deleteSpeValue(del);
		}
		if(add.length>0){
			addSpeValueList = this.addSpeValue(add,speId);
			
		}
		if(update.length>0){
			updateSpeValueList = this.updateSpeValue(update,speId);
		}
		
		if((del.length>0&&delSpeValueList==null)||(add.length>0&&addSpeValueList==null)||(update.length>0&&updateSpeValueList==null)){
			return -1;
		}
		/**
		 * 执行批量增删改
		 * */
		if(!StringUtils.isEmpty(delSpeValueList))
			this.specificationValueDao.deleteBatchByPrimaryKey(delSpeValueList);
		if(!StringUtils.isEmpty(addSpeValueList))
			this.specificationValueDao.insertBatchSpecification(addSpeValueList);
		if(!StringUtils.isEmpty(updateSpeValueList))
			this.specificationValueDao.updateBatchSpecification(updateSpeValueList);
		
		return 1;
	}
	/**
	 * 功能说明：修改
	 * 日期:	2015年7月29日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param update  id-orders-name,id-orders-name
	 * @return
	 */
	private List<SpecificationValue> updateSpeValue(String[] update,Long speId){
		List<SpecificationValue> updateSpeValueList = new ArrayList<SpecificationValue>();
		Integer updateLength = update.length;
		for (int i = 0; i < updateLength; i++) {
			String[] updateString = update[i].split("-");
			Integer updateSubLength = updateString.length;
			// 数组中都是由id和orders和name组成
			SpecificationValue updateSpeValue = new SpecificationValue();
			// id和orders和name都不空。规格id(speId) 也不能为空
			if (updateSubLength == 3 && !StringUtils.isEmpty(speId)) {
				// id
				if (!StringUtils.isEmpty(updateString[0]))
					updateSpeValue.setId(Long.valueOf(updateString[0]));
				// orders
				if (!StringUtils.isEmpty(updateString[1]))
					updateSpeValue.setOrders(Integer.valueOf(updateString[1]));
				// name
				if (!StringUtils.isEmpty(updateString[2]))
					updateSpeValue.setName(String.valueOf(updateString[2]));

				updateSpeValue.setSpecificationId(speId);

				updateSpeValueList.add(updateSpeValue);
				continue;
			} else {
				updateSpeValueList = null;
			}
		}
		return updateSpeValueList;
	}
	/**
	 * 功能说明：添加规格值
	 * 日期:	2015年7月29日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param add  orders-name,orders-name
	 * @return
	 */
	private List<SpecificationValue> addSpeValue(String[] add,Long speId){
		List<SpecificationValue> addSpeValueList = new ArrayList<SpecificationValue>();
		Integer addLength = add.length;
		for (int i = 0; i < addLength; i++) {
			String[] addString = add[i].split("-");
			Integer addSubLength = addString.length;
			// 数组中都是由orders和name组成
			SpecificationValue addSpeValue = new SpecificationValue();
			// orders和name都不空
			if (addSubLength == 2&& !StringUtils.isEmpty(speId)) {
				// orders
				if (!StringUtils.isEmpty(addString[0]))
					addSpeValue.setOrders(Integer.valueOf(addString[0]));
				// name
				if (!StringUtils.isEmpty(addString[1]))
					addSpeValue.setName(String.valueOf(addString[1]));

				addSpeValue.setSpecificationId(speId);
				addSpeValueList.add(addSpeValue);
				continue;
			} else {
				addSpeValueList = null;
			}
		}
		return addSpeValueList;
	}
	
	/**
	 * 功能说明：删除规格值信息
	 * 日期:	2015年7月29日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param del
	 * @return
	 */
	private List<SpecificationValue> deleteSpeValue(String[] del){
		List<SpecificationValue> delSpeValueList= new ArrayList<SpecificationValue>();
		for(int i = 0;i< del.length;i++){
			if(!del[i].isEmpty()){
				SpecificationValue speV= new SpecificationValue();
				try {
					speV.setId(Long.valueOf(del[i]));
				} catch (NumberFormatException e) {
					// TODO: handle exception
					delSpeValueList=null;
				}
				delSpeValueList.add(speV);
			}
		}
		return delSpeValueList;
	}
	
}
