<meta http-equiv="content-type" content="text/html; charset=utf-8">
           <ul>
            	<#if categoriesList??>
						<#list categoriesList as category>
                	<li class="mt">
                	<h2>
	                	<#if category.url?? &&  category.url?length gt 0>
	                	 <a href="/${category.url}">${category.name!}</a>
	                	<#else>
	                	 <a href="/channel${category.id?c}.html">${category.name!}</a>
	                	</#if>
                        	<#assign key ="${category.rootId!}${category.brotherOrderAll!}" >
							<#if category.rootId??>
								<#assign key ="${category.rootId?c}${category.brotherOrderAll!}" >
							</#if>
							</h2>
                        	<#if categoriesMap??>
							<#if categoriesMap[key]??>	
							<div class="menu_player">
							<#assign secondCategories =categoriesMap[key] >
									<#list secondCategories as secondCategory>
			                        	<div class="menu_one_list">
			                                <h3><a href="/product/list?cat=${category.id?c},${secondCategory.id?c}" target="_blank" style="color:#666;font-weight:600">${secondCategory.name!}</a></h3>
											<div class="menu_label_list">	
											<#assign key ="${secondCategory.rootId!}${secondCategory.brotherOrderAll!}" >
											<#if secondCategory.rootId??>
												<#assign key ="${secondCategory.rootId?c}${secondCategory.brotherOrderAll!}" >
											</#if>
											<#if categoriesMap[key]??>	
												<#assign thirdCategories =categoriesMap[key] >
				                                    <ul>
														<#list thirdCategories as thirdCategory>
				                                        	<li class="current">
				                                        		<a href="/product/list?cat=${category.id?c},${secondCategory.id?c},${thirdCategory.id?c}">${thirdCategory.name!}</a>
				                                        		<#if ((thirdCategory_index+1) lt thirdCategories?size) >
																	<em>|</em>
																</#if>
															</li>
														</#list> 
				                                    </ul>
 											</#if>
											</div>
			                            </div>
									</#list> 
								</div>
                            </#if>
						 </#if>
                    </li>
                    </#list> 
				</#if>
              </ul>
