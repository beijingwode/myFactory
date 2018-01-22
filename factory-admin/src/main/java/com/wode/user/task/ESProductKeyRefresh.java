package com.wode.user.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.search.WodeSearchManager;


@Service
public class ESProductKeyRefresh {
	@Autowired
	private WodeSearchManager wsm;
	
	/**
	 * 添加周统计信息
	 */
	public void run() {
		wsm.refreshKey();
	}
}
