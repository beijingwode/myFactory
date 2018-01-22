package com.wode.factory.company.facade;

public interface DeleteApprRelationFacade {
	/**
     * 临时表数据删除后，要把关联的sku等垃圾信息删除
     */
	public void deleteApprRelation(Long apprid);
}
