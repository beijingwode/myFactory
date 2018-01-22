function getShareTarget(shareId,jsBasePath){
	 if(shareId){
        if(0==1){
      <#if pageMap["shares"]??>
      <#assign shares =pageMap["shares"] >
	  <#list shares as p>
		} else if(${p.id?c!} ==shareId){
            return "${p.targetActionUrl!}";
	  </#list>
      </#if>
        } else {
        	return jsBasePath+"neigou.html";
        }
    } else {
    	return jsBasePath+"index_m.htm";
    }
}

function go2ShareTarget(shareId,jsBasePath){
	window.location = getShareTarget(shareId,jsBasePath);
}