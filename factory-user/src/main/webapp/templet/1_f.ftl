    <!--1F begin-->
    	<#if textList??>
    	<div class="if_title">
        	<h2><span>${f_index}F</span>&nbsp;${textList[0].title!}</h2>
            <ul class="if_r_list">
            	<#list textList as text>
					<#if text_index gt 0>
					<li><a href="${text.link!}" target="_blank">${text.title!}</a>
					<#if text_has_next>
						<em>|</em>
					</#if>
					</li>
					</#if>
				</#list>  
            </ul>
        </div>
        </#if>
		
		<#if imageTextList??>
        <div class="pro_wrap">
        	<div class="pro_info">
        		<#if imageList?? && (imageList?size>0) >
					<div class="pro_info_img"><img src="${imageList[0].imagePath!}" width="208" height="208" alt="${imageList[0].title!}"></div>
				</#if>
                <div class="pro_info_list">
                	<ul>
                		<#assign x=(imageTextList?size+1)/2 >
						<#list 1..x as i>
							<#if i lt 9>
								<li>
                        			<span><a href="${imageTextList[(i-1)*2].link!}" target="_blank">${imageTextList[(i-1)*2].title!}</a></span>
                        			<#if (((i-1)*2+1) lt imageTextList?size) >
										<span><a href="${imageTextList[(i-1)*2+1].link!}">${imageTextList[(i-1)*2+1].title!}</a></span>	
									</#if>
                       			</li>
							</#if>
						</#list>
                    </ul>
                </div>
            </div>
			
		  <div class="pro_tab_box">
            <div class="pro_play">
            	<div class="pro_focus">
                	<ul>
                		<#if imageList??>
                		<#list imageList as image>
							<#if (image_index gt 0) && (image_index lt 4)>
								<li>
		                        	<h2><a href="${image.link!}" target="_blank"><img src="${image.imagePath!}" width="363" height="498" alt="${image.title!}"></a></h2>
		                            <div class="slideother">
		                            	<p>${image.title!}</p>
		                            </div>
		                        </li>
							</#if>	
						</#list>  
						</#if>
                    </ul>
                </div>            	
            </div>
            
            <div class="pro_list_wrap">
            	<div class="pro_list">
                	<ul>
                		<#list imageList as image>
							<#if image_index gt 3>
								<li>
									<#if image.saleKbn ??>
									<#if image.saleKbn == 1>
									<div class="picon"><img src="images/picon1.png" /></div>
									</#if>
									<#if image.saleKbn == 2>
									<div class="picon"><img src="images/picon_c1.png" /></div>
									</#if>
									<#if image.saleKbn == 4>
									<div class="picon"><img src="images/picon_z1.png" /></div>
									</#if>
									<#if image.saleKbn == 5>
									<div class="picon"><img src="images/picon_t1.png" /></div>
									</#if>
									</#if>
			                		<div class="pro_list_img">
			                			<a href="${image.link!}" target="_blank"><img src="${image.imagePath!}" alt="${image.title!}"></a>
			                		</div>
									<h2>${image.proName!}</h2>
			                		<p class="p1"><span>${image.proPrice!}</span><em>${image.proSale!}</em></p>
			                		<p class="p2">${image.proDescription!}</p>
								</li>
							</#if>	
						</#list>  
                    </ul>
                </div>
            </div>
          </div>
            
    		<#if productList??>
            <#list productList as products>
              <div class="pro_tab_box dis">
	            <div class="pro_list_wrap pro_list_wrap_990">
	              <div class="pro_list">
	               	<ul>
                	  <#list products as image>
						<li>
							<#if image.saleKbn ??>
							<#if image.saleKbn == 1>
							<div class="picon"><img src="images/picon1.png" /></div>
							</#if>
							<#if image.saleKbn == 2>
							<div class="picon"><img src="images/picon_c1.png" /></div>
							</#if>
							<#if image.saleKbn == 4>
							<div class="picon"><img src="images/picon_z1.png" /></div>
							</#if>
							<#if image.saleKbn == 5>
							<div class="picon"><img src="images/picon_t1.png" /></div>
							</#if>
							</#if>
	                		<div class="pro_list_img">
	                			<a href="${image.link!}" target="_blank"><img src="${image.imagePath!}" alt="${image.title!}"></a>
	                		</div>
							<h2>${image.proName!}</h2>
	                		<p class="p1"><span>${image.proPrice!}</span><em>${image.proSale!}</em></p>
	                		<p class="p2">${image.proDescription!}</p>
						</li>
					  </#list>  
					</ul>
	              </div>
	            </div>	            
              </div><!-- pro_tab_box -->
			</#list>  
            </#if>
          
        </div>
        </#if>
        
    	<#if brandList??>
		<div class="brands">
			<ul>
        		<#list brandList as image>
				<li class="fore${image_index+1!}">
					<a href="${image.link!}" title="" target="_blank"><img src="${image.imagePath!}" alt="" height="40" width="100"></a>
				</li>
				</#list>
			</ul>
		</div>
        </#if>
    <!--1F end-->
    