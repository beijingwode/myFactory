
    	$(document).ready(function(){
    		
		});
    	
    	
    	function checkcss(){
    		if(!$('#checkbox').is(':checked')) {
    			$("#sub").removeClass().addClass("recuitment_btn04");
    		}else{
    			$("#sub").removeClass().addClass("recuitment_btn01");
    		}
    	}
    	
    	function papa(type){
    		$("#sub").addClass("btngray").removeAttr("onclick");
    		$("#recuitment_btn02").addClass("btngray").removeAttr("onclick");
    		setTimeout("submit("+type+")",500);
    	}
    	
    	/**
    	*跳转添加品牌
    	*/
    	function submit(type){
    		if(type==2){
    			window.location.href = jsBasePath+"/supplier/torecruitmentnewbrand.html";
    		}else{
    			//if(!$('#checkbox').is(':checked')) {
        		//	return false;
        		//}
    			window.location.href = jsBasePath+"/supplier/enterend.html";
    		}
    		$("#sub").removeClass("btngray").attr("onclick","papa('1')");
    		$("#recuitment_btn02").removeClass("btngray").attr("onclick","papa('2')");
    	}
    	
    	
    	/**
    	 * 表单提交
    	 */
    	function formSubmit(page){
    		if(page!=undefined){
    			$("#pages").val(page);
    		}else{
    			$("#pages").val(1);
    		}
    		$("#sub_form").submit();
    	}
    	
    	
    	/**
    	 * 快速跳转
    	 */
    	function gotoPage(){
    		var pagenum = $("#pagenum").val();
    		formSubmit(pagenum);
    	}
    	
