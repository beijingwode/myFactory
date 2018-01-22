 
  		var kbn;
    	$(document).ready(function(){
    		
    		selectshow($("input[name='brandType'][checked]").val());
    		
    		$("input").bind('blur',function(){
				$(this).removeClass("bctxt");	
			});
    		
    		if($.browser.msie ){
    			$("#filePicker1").prepend($("#tmp-upload_container").html());
    			$("#filePicker0").prepend($("#tmp-upload_container").html());
    			$("#filePicker2").prepend($("#tmp-upload_container").html());
    			$("#tmp-upload_container").html("");
    			//$(".sc").mousemove(function(e){
    				// $("#uploadFile").position({"top":e.pageX-10,"left": e.pageY-10});
    			//});
    		}else{
    			$("#filePicker0").click(function(){
    				kbn=0;
        			$("#uploadFile").click();
        		})
    			$("#filePicker1").click(function(){
    				kbn=1;
        			$("#uploadFile").click();
        		})
    			$("#filePicker2").click(function(){
    				kbn=2;
        			$("#uploadFile").click();
        		})
    		}
    		
    		if(supplierType!='2') {
    			showMust('0');
    		} else {
    			showMust('1');
    		}
    		

   		 var  $imgs=$(".hupimg img");
   		 $imgs.each(function(){
   			 var img = $(this);
   			 var imgsrc=img.attr("src");
   			 var url=imgsrc;

   			 if(isPdf(imgsrc)) {
   				 imgsrc=jsStaticResources+"images/pdf.png";
   			 }
   			 img.attr("src",imgsrc);
   			 img.attr("onclick","winOpen('"+url+"');");
   			});
		});
    	
    	function showMust(t) {
    		if(t == '1') {
        		$("#mustI").show()
    		} else {
        		$("#mustI").hide()
    		}
    	}
    	function fileUpload() {
    		 var elementIds=["flag"]; //flag为id、name属性名
    	    $.ajaxFileUpload({
    	        url: jsBasePath+'/upload/pic.json?folder='+jsSsId,
    	        type: 'post',
    	        secureuri: false, //一般设置为false
    	        fileElementId: "uploadFile", // 上传文件的id、name属性名
    	        elementIds:elementIds,
    	        dataType: 'json', //返回值类型，一般设置为json、application/json
    	        success: function(data, status){
    	        	if(data.success){
    	        		var imgsrc = data.data[0].original;
    	        		if(imgsrc.indexOf("http://") != 0) {
    	        			imgsrc = "http://"+imgsrc;
    	        		}
    	        		 var  img=$("#filePicker"+kbn).siblings(".hupimg").find("img");
    	        		 

    	    			 var url=imgsrc;
    	    			 if(isPdf(imgsrc)) {
    	    				 imgsrc=jsStaticResources+"images/pdf.png";
    	    			 } 
    	    			 
    	   				 if(kbn==0) {
    	   					 $("#logoImg").attr("src",imgsrc);
    	   					 $("#logo").val(imgsrc);  
    	   					 
    	   				 } else if(kbn==1) {

	    	   				 if(img.length==0){
	    	   					$($("#filePicker"+kbn).parent()).after("<div class=\"hupimg\" style=\"float:left;height:170px;margin-right:40px;\" ><img style='height:120px;' src='"+imgsrc+"' onclick=\"winOpen('"+url+"');\" /><br /><a class=\"sc\" href=\"javascript:void(0);\" onclick=\"del(this);\" style=\"float:none;margin:5px 0 0 5px;\">删除</a><input type=\"hidden\" name=\"brandurl\" value='"+url+"'/></div>");
	    	   				 }else {
	    	   					$($(img[0]).parent()).before("<div class=\"hupimg\" style=\"float:left;height:170px;margin-right:40px;\" ><img style='height:120px;' src='"+imgsrc+"' onclick=\"winOpen('"+url+"');\" /><br /><a class=\"sc\" href=\"javascript:void(0);\" onclick=\"del(this);\" style=\"float:none;margin:5px 0 0 5px;\">删除</a><input type=\"hidden\" name=\"brandurl\" value='"+url+"'/></div>");
	    	   				 }
    	   				 } else {
	    	   				 if(img.length==0){
	    	   					$($("#filePicker"+kbn).parent()).after("<div class=\"hupimg\" style=\"float:left;height:200px;margin-right:40px;\" ><img style='height:120px;' src='"+imgsrc+"' onclick=\"winOpen('"+url+"');\" /><br /><a class=\"sc\" href=\"javascript:void(0);\" onclick=\"del(this);\" style=\"float:none;margin:5px 0 0 5px;\">删除</a><input type=\"hidden\" name=\"brandImg\" value='"+url+"'/></div>");
	    	   				 }else {
	    	   					$($(img[0]).parent()).before("<div class=\"hupimg\" style=\"float:left;height:200px;margin-right:40px;\" ><img style='height:120px;' src='"+imgsrc+"' onclick=\"winOpen('"+url+"');\" /><br /><a class=\"sc\" href=\"javascript:void(0);\" onclick=\"del(this);\" style=\"float:none;margin:5px 0 0 5px;\">删除</a><input type=\"hidden\" name=\"brandImg\" value='"+url+"'/></div>");
	    	   				 }
    	   				 }
        				
    	        	}else{
    	        		showInfoBox(data.msg);
    	        	}
    	        },
    	        error: function(data, status, e){ 
    	            alert(e);
    	        }
    	    });
    	}

    	function isPdf(str) {
    		if(str==null || str=='' || str.length<5) return false;
    		
    		var ppostfix=str.substring(str.length-4);
    		return ppostfix==".pdf" || ppostfix==".PDF";
    	}
    	
    	function winOpen(url){
    		window.open(url);
    	}
    	
    	function del(obj) {
    		$($(obj).parent()).remove();
    	}
    	
    	function papa(){
    		$("#recuitment_btn01").addClass("btngray").removeAttr("onclick");
    		$("#recuitment_btn02").addClass("btngray").removeAttr("onclick");
    		setTimeout("submit()",500);
    	}
    	
    	function submit(){
    		if(chekout()){
   				$("#sub_form").submit();
    		}
    		$("#recuitment_btn01").removeClass("btngray").attr("onclick","papa()");
    		$("#recuitment_btn02").removeClass("btngray").attr("onclick","tosubmit()");
    	}
    	
    	function chekout(){
    			var mustCheck = true;
    			if($("#brandNo").val()==''){
    				//showInfoBox("商标注册号不能为空");
    				//$("#brandNo").focus();
    				$("#brandNo").addClass("bctxt");
    				mustCheck =  false;
    			}
    			if($("#name").val()==''&&$("#nameEn").val()==''){
    				//showInfoBox("品牌中文英文至少填写一样");
    				//$("#name").focus();
    				$("#name").addClass("bctxt");
    				mustCheck =  false;
    			}
    			if($("input[name='brandType']:checked").val()==0){
    				if($("#brandcreatedTm").val()==''){
    					//showInfoBox("商标申请日期不能为空");
    					//$("#brandcreatedTm").focus();
    					$("#brandcreatedTm").addClass("bctxt");
    					mustCheck =  false;
        			}
    			}
				if($("input[name='brandType']:checked").val()==1){
					if($("#begintimeR").val()==''){
						//showInfoBox("商标注册证有效期不能为空");
						//$("#begintimeR").focus();
						$("#begintimeR").addClass("bctxt");
						mustCheck =  false;
	    			}
	    			if($("#endtimeR").val()==''){
	    				//showInfoBox("商标注册证有效期不能为空");
	    				//$("#endtimeR").focus();
	    				$("#endtimeR").addClass("bctxt");
	    				mustCheck =  false;
	    			}
    			}
				if($("#natural").val() == "1" || supplierType == 2){//非旗舰店即代理商
					if($("#begintimeAuth").val()==''){
						//showInfoBox("商标注册证有效期不能为空");
						//$("#begintimeR").focus();
						$("#begintimeAuth").addClass("bctxt");
						mustCheck =  false;
	    			}
	    			if($("#endtimeAuth").val()==''){
	    				//showInfoBox("商标注册证有效期不能为空");
	    				//$("#endtimeR").focus();
	    				$("#endtimeAuth").addClass("bctxt");
	    				mustCheck =  false;
	    			}
				}
				if(!mustCheck) {
    				showInfoBox("请填写必须输入项目。");
					return false;
				}
				
				var s=''; 
      		  	$('input[name="category"]:checked').each(function(){ 
      		    	s+=$(this).val()+','; 
      		  	}); 
	      		if (s.length > 0) { 
	      		    //得到选中的checkbox值序列 
	      		    s = s.substring(0,s.length - 1); 
	      		}else{
	      			showInfoBox("请选择类目");
	      			$("#lm").addClass("bctxt");
	      			return false;
	      		}
	      		$("#categoryIds").val(s);
	      		
				if($("#logo").val()==''){
					showInfoBox("请上传品牌logo");
    				return false;
    			}
				//if($('input[name="brandurl"]').length <=0){
				if($("#brandurl").val()==''){
					showInfoBox("请上传商标受理通知书");
					$("#brandurl").focus();
    				return false;
    			}
				
				if($("#natural").val() == "1" || supplierType == 2) {
					if($('input[name="brandImg"]').length <=0){
	    				showInfoBox("请上传商标授权书。");
						return false;
					}
    			}
    		return true;
    		
    	}
    	
    	
    	/**
    	*跳转下一步
    	*/
    	function tosubmit(){
    		$("#recuitment_btn01").addClass("btngray").removeAttr("onclick");
    		$("#recuitment_btn02").addClass("btngray").removeAttr("onclick");
    		var returnUrl = jsReturnUrlv;
    		if(returnUrl == "") {
    			returnUrl = jsBasePath+"/supplier/torecruitmentnewbrand.html";
    		} else {
    			returnUrl = jsBasePath + returnUrl;
    		}
    		window.location.href = returnUrl;
    	}
    	
    	
    	
    	function selectshow(type){
    		if(type==undefined){
    			$("#radio2").attr("checked",'checked');
    			$("#rselects").show();
    			$("#tmselect").hide();
    		}else{
    			if(type==0){
    				$("#radio1").attr("checked",'checked');
        			$("#tmselect").show();
        			$("#rselects").hide();
        		}else if(type==1){
        			$("#radio2").attr("checked",'checked');
        			$("#rselects").show();
        			$("#tmselect").hide();
        		}
    		}
    	}
    	
    	function selectshow1(type){
    			if(type==0){
        			$("#tmselect").show();
        			$("#rselects").hide();
        		}else if(type==1){
        			$("#rselects").show();
        			$("#tmselect").hide();
        		}
    	}
    	
    	function checkall(obj){
   			if($(obj).attr("checked")!="checked"){
   				$(obj).parent().parent().parent().find("input[type=checkbox]").attr("checked",false);
   			}else{
   				$(obj).parent().parent().parent().find("input[type=checkbox]").attr("checked",true);
   			}
   			$("#lm").removeClass("bctxt");
    	}
    	
    	function hideclass(){
    		$("#lm").removeClass("bctxt");
    	}
    	
    	function showimg(imgname){
    		$("#img3").attr("src",jsStaticResources+"images/"+imgname);
    		$("#showimg").show();
    	}
    	
    	function hideimg(){
    		$("#showimg").hide();
    	}
