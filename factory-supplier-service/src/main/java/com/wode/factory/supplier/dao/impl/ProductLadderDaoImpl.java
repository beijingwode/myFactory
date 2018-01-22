package com.wode.factory.supplier.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseQuery;
import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.supplier.dao.ProductLadderDao;
import com.wode.factory.supplier.query.SkuLadderVo;


@Repository("productLadderDao")
public class ProductLadderDaoImpl extends BasePageDaoImpl<ProductLadder> implements ProductLadderDao {

	
	@Override
	public void saveOrUpdate(ProductLadder entity) throws DataAccessException {
		if(entity.getId() == null) {
			save(entity);
		}
		else{
			update(entity);
		}
	}
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductLadderMapper";
	}

	@Override
	public void removeAllByProductid(Map<String, String> map) {
		this.getSqlSession().delete(getIbatisMapperNamesapce()+".removeAllByProductid",map);
	}
	

	@Override
	public List<ProductLadder> getList(Map<String, String> map) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getList",map);
	}

	@Override
	public Long getId(ProductLadder model) {
		return model.getId();
	}

	@Override
	public PageInfo<SkuLadderVo> findPageVo(BaseQuery query) {

		List<SkuLadderVo> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".findPage",query,new RowBounds(query.getPageNumber(), query.getPageSize()));
		return new PageInfo<SkuLadderVo>(list);
	}
}