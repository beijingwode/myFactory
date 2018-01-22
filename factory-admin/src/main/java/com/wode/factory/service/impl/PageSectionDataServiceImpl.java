package com.wode.factory.service.impl;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.PageSectionDataDao;
import com.wode.factory.model.PageSectionData;
import com.wode.factory.service.PageSectionDataService;
@Service("pageSectionDataService")
public class PageSectionDataServiceImpl extends FactoryEntityServiceImpl<PageSectionData> implements PageSectionDataService {
	
	@Autowired
	PageSectionDataDao dao;
	
	@Override
	public PageSectionDataDao getDao() {
		return dao;
	}
	
	@Override
	public Long getId(PageSectionData entity) {
		return -1L;
	}

	@Override
	public void setId(PageSectionData entity, Long id) {
	}


	@Override
	public PageInfo<PageSectionData> findPage(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<PageSectionData> list = dao.selectPage(params);
		Comparator<PageSectionData> comparator = new Comparator<PageSectionData>() {
            public int compare(PageSectionData s1, PageSectionData s2) {
            	String pageTitle1 = s1.getPageTitle();
            	String pageTitle2 = s2.getPageTitle();
            	String a = s1.getSectionTitle(); 
            	String b = s2.getSectionTitle();
            	if (!pageTitle1.equals(pageTitle2)) {
                    return Collator.getInstance(Locale.CHINESE).compare(pageTitle1,
                    		pageTitle2);
                }else if(!a.equals(b)){
                	  return Collator.getInstance(Locale.CHINESE).compare(a,
                              b);
                }else{
                	if(s1.getOrders() != null && s2.getOrders() != null){
                		return s1.getOrders() - s2.getOrders();
                	}else{
                		return 0;
                	}
                	
                }
            }
		};
		Collections.sort(list,comparator);
		return new PageInfo<PageSectionData>(list);
	}

	@Override
	public void copyPageData(Map<String, Object> params) {
		dao.copyPageData(params);
	}


}
