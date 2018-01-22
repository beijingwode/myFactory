
$(function(){
	    $("#comName").blur(function(){//用户名文本框失去焦点触发验证事件
	   	var comName = $("#comName").val();
	       if(/^[0-9]+$/.test(comName)||/^[A-Za-z]+$/.test(comName))//只处验证不能为空并且只能为英文或者数字或者下划线组成的２－１５个字符
	       {
	       	$("#error1").fadeIn();
	       }
	       else
	       {
	    	   $("#error1").fadeOut();
	    	   if(comName.length>0){
	    		   $.ajax({
		                cache: true,
		                type: "GET",
		                dataType: "json",
		                url:jsBasePath+"/supplier/chekout.json?comName="+comName,
		                async: false,
		                error: function(request) {
		                    //alert("Connection error");
		                    return false;
		                },
		                success: function(data) {
		                	if(data.result.errorCode!=0){
		                		$("#error3").fadeIn();
		                	}else{
		                		$("#error3").fadeOut();
		                	}
		                }
		            });
	    	   }
	       }
	       $("#bankPeople").val(comName);
	    });
	    $("#corNum").blur(function(){//用户名文本框失去焦点触发验证事件
		   	var corNum = $("#corNum").val();
			   	if(corNum!=null&&corNum.length!=0)
			   	{
			   		if(cidInfo(corNum))
				       {
				       	$("#error2").fadeIn();
				       }
				       else
				       {
				       	$("#error2").fadeOut();
				       }
			   	}
			   		return true;
			   		
			   	
		});
	    
	             
	    $("#comRegisternum").blur(function(){//用户名文本框失去焦点触发验证事件
		   	var comRegisternum = $("#comRegisternum").val();
		       if(comRegisternum.length<2||comRegisternum.length>30)
		       {
		    	$("#3313").find("[name=error]").fadeIn();
		       }
		       else
		       {
		    	$("#3313").find("[name=error]").fadeOut();
		    	/* 
		    	检查营业执照注册号是否有重复的
		    	if(comRegisternum.length>0){
		    		$.ajax({
		                cache: true,
		                type: "GET",
		                dataType: "json",
		                url:jsBasePath+"/supplier/chekout.json?comRegisternum="+comRegisternum,
		                async: false,
		                error: function(request) {
		                    //alert("Connection error");
		                    return false;
		                },
		                success: function(data) {
		                	if(data.result.errorCode!=0){
		                		$("#error4").fadeIn();
		                	}else{
		                		$("#error4").fadeOut();
		                	}
		                }
		            });
		    	} */
		       }

		});
	    
	     
	          
	    $("#orgNum1").blur(function(){//用户名文本框失去焦点触发验证事件
		   	var orgNum1 = $("#orgNum1").val();
		   	var orgNum2 = $("#orgNum2").val();
		       if(orgNum1!='' && orgNum1.length!=8)
		       {
		    	$("#3321").find("[name=error]").fadeIn();
		       }
		       else
		       {
		    	$("#3321").find("[name=error]").fadeOut();
		    	/* 
		    	 组织机构代码证编号验证
		    	if(orgNum1.length==8&&orgNum2.length==1){
		    		$.ajax({
		                cache: true,
		                type: "GET",
		                dataType: "json",
		                url:jsBasePath+"/supplier/chekout.json?orgNum1="+orgNum1+"&orgNum2="+orgNum2,
		                async: false,
		                error: function(request) {
		                    //alert("Connection error");
		                    return false;
		                },
		                success: function(data) {
		                	if(data.result.errorCode!=0){
		                		$("#error6").fadeIn();
		                	}else{
		                		$("#error6").fadeOut();
		                	}
		                }
		            });
		    	} */
		       }

		});
	   
	             
	    $("#orgNum2").blur(function(){//用户名文本框失去焦点触发验证事件
	    	var orgNum1 = $("#orgNum1").val();
		   	var orgNum2 = $("#orgNum2").val();
		       if(orgNum2!='' && orgNum2.length!=1)
		       {
		    	$("#3321").find("[name=error]").fadeIn();
		       }
		       else
		       {
		    	$("#3321").find("[name=error]").fadeOut();
		    	/* 
		    	组织机构代码证编号验证
		    	if(orgNum1.length==8&&orgNum2.length==1){
		    		$.ajax({
		                cache: true,
		                type: "GET",
		                dataType: "json",
		                url:jsBasePath+"/supplier/chekout.json?orgNum1="+orgNum1+"&orgNum2="+orgNum2,
		                async: false,
		                error: function(request) {
		                    //alert("Connection error");
		                    return false;
		                },
		                success: function(data) {
		                	if(data.result.errorCode!=0){
		                		$("#error6").fadeIn();
		                	}else{
		                		$("#error6").fadeOut();
		                	}
		                }
		            });
		    	} */
		       }

		});
	   
	             
	    $("#taxNum").blur(function(){//用户名文本框失去焦点触发验证事件
		   	var taxNum = $("#taxNum").val();
		       if(taxNum!='' && (/.*[\u4e00-\u9fa5]+.*$/.test(taxNum)||taxNum.length<15))
		       {
		    	$("#3325").find("[name=error]").fadeIn();
		       }
		       else
		       {
		    	$("#3325").find("[name=error]").fadeOut();
		    	/* 
		    	税务登记证编号
		    	if(taxNum.length>0){
		    		$.ajax({
		                cache: true,
		                type: "GET",
		                dataType: "json",
		                url:jsBasePath+"/supplier/chekout.json?taxNum="+taxNum,
		                async: false,
		                error: function(request) {
		                    //alert("Connection error");
		                    return false;
		                },
		                success: function(data) {
		                	if(data.result.errorCode!=0){
		                		$("#error5").fadeIn();
		                	}else{
		                		$("#error5").fadeOut();
		                	}
		                }
		            });
		    	} */
		       }
		});
	    $("#bankNum").blur(function(){//用户名文本框失去焦点触发验证事件
		   	var bankNum = $("#bankNum").val();
		       if(bankNum.length<6||bankNum.length>25)
		       {
		    	$("#3335").find("[name=error]").fadeIn();
		       }
		       else
		       {
		    	$("#3335").find("[name=error]").fadeOut();
		       }

		});

		 var  $imgs=$(".imgDiv img");
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

	function chekout(type){
		var comName = $("#comName").val();
		var corNum = $("#corNum").val();
		var taxNum = $("#taxNum").val();
		var comRegisternum = $("#comRegisternum").val();
		var orgNum1 = $("#orgNum1").val();
	   	var orgNum2 = $("#orgNum2").val();
		var fcn = true;
		var fcn2 = true;
		var fcn3 = true;
		var fcn4 = true;
		if(/^[0-9]+$/.test(comName)||/^[A-Za-z]+$/.test(comName))//只处验证不能为空并且只能为英文或者数字或者下划线组成的２－１５个字符
		{
			if(type==2) {
				$("#error1").fadeIn();
				return false;
			}
		} /*else if(cidInfo(corNum)){//验证身份证
			if(type==2) {
				$("#error2").fadeIn();
				return false;
			}
			
		}*/ else if(taxNum!='' && (/.*[\u4e00-\u9fa5]+.*$/.test(taxNum)||taxNum.length<15)){
			if(type==2) {
				$("#3325").find("[name=error]").fadeIn();
				$("#taxNum").focus();
				setTimeout("display()",6000);
				return false;
			}
			
		} else if($("input[name='busImgUrl']").val() == ''){
			if(type==2) {
				showInfoBox("请上传企业营业执照副本复印件！");
				setTimeout("display()",6000);
				return false;
			}
			
		} /*else if($("input[name='corImgUrl']").val() == ''){// 判断有没有身份证照片
			if(type==2) {
				showInfoBox("请上传法定代表人身份证正反面复印件！");
				setTimeout("display()",6000);
				return false;
			}
			
		} */else if($("input[name='bankImgUrl']").val() == ''){
			if(type==2) {
				showInfoBox("请上传银行开户许可证！");
				setTimeout("display()",6000);
				return false;
			}
			
		} else {
			if(comName.length>0){
	    		   $.ajax({
		                cache: true,
		                type: "GET",
		                dataType: "json",
		                url:jsBasePath+"/supplier/chekout.json?comName="+comName,
		                async: false,
		                error: function(request) {
		                    //alert("Connection error");
		                    return false;
		                },
		                success: function(data) {
		                	if(data.result.errorCode!=0){
		                		$("#error3").fadeIn();
		                		fcn = false;
		                	}else{
		                		$("#error3").fadeOut();
		                	}
		                }
		            });
	    	}
			
			$("#error1").fadeOut();
			$("#error2").fadeOut();

			if(!fcn){
				return false;
			} 
			return true;
		}
		return true;
	}

	var
	aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州"
	,53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};

	function cidInfo(sId){
		if($("#corCome").val()!=0) return false;
		var iSum=0;
	//var info="";
	if(!/^\d{17}(\d|x)$/i.test(sId))return true;
	sId=sId.replace(/x$/i,"a");
	if(aCity[parseInt(sId.substr(0,2))]==null)return true;
	sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));
	var d=new Date(sBirthday.replace(/-/g,"/"));
	if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return true;
	for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11);
	if(iSum%11!=1)return true;
	return false;
	}

	function papa(type){
		var corNum = $("#corNum").val();
	   	if(corNum!=null&&corNum.length!=0)
	   	{
	   		if(cidInfo(corNum))
		       {
	   			$("#sb").fadeIn();
	   			$("#error2").fadeIn();
	   			return false;
		       }
	   	}
		$("#recuitment_btn01").addClass("btngray").removeAttr("onclick");
		$("#recuitment_btn02").addClass("btngray").removeAttr("onclick");
		//setTimeout("_submit("+type+")",500);
		_submit(type);
	}

	function _submit(type){
		if(!chekout(type)){
			$("#recuitment_btn01").removeClass("btngray").attr("onclick","papa('1')");
			$("#recuitment_btn02").removeClass("btngray").attr("onclick","papa('2')");
			$("#sb").fadeIn();
			return false;
		}
		
		if(type=='1' || validatorForm()){
// 			$('#sub_form_supplierinfo').submit();
			$.ajax({
                cache: true,
                type: "POST",
                dataType: "json",
                url:jsBasePath+"/supplier/savesupplierinfo.json?doType="+type,
                data:$('#sub_form_supplierinfo').serialize(),// 你的formid
                async: false,
                error: function(request) {
                    //alert("Connection error");
                    return false;
                },
                success: function(data) {
                	if(data.result.errorCode==0){
                		if(type==2){
                			if($("#toShop").attr("checked") || $("#toShop").attr("checked")=="checked") {
                    			window.location.href=jsBasePath+"/supplier/torecruitmentstore.html";
                			}
                			window.location.href = jsBasePath+"/supplier/enterend.html";
                    	}
                		$("#sb").fadeOut();
                		$("#cg").fadeIn();
                		setTimeout("display()",6000);
                	}else{
                		$("#"+data.result.errorCode).find("[name=error]").fadeIn();
                		$("#"+data.result.key).focus();
                		$("#cg").fadeOut();
                		$("#sb").fadeIn();
                		setTimeout("display()",6000);
                		//alert("错误："+data.result.errorCode+"  原因："+data.result.message);
                	}
                }
            });
		}else{
			//alert("提示：请补充完成红色的区域!,待优化该弹出框");
		}
		$("#recuitment_btn01").removeClass("btngray").attr("onclick","papa('1')");
		$("#recuitment_btn02").removeClass("btngray").attr("onclick","papa('2')");
	}

	function display(){
		var $list = $("[name=error]");
		$list.each(function(i,val){
			$($list[i]).fadeOut();
		});
		$("#cg").fadeOut();
		$("#sb").fadeOut();
	}
	
	//校验邮箱
	function checkEmail(email){
		email=$.trim(email);
		if(email.length==0) return false;
		if(email.indexOf('.com.cn') !=-1 && email.indexOf('.com.cn') == email.length-7) email=email.substring(0,email.length-3);
		var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/; 
		return pattern.test(email);
	    //if(!pattern.test(email)){//只处验证不能为空并且只能为英文或者数字或者下划线组成的２－１５个字符
	    //	return false;
	    //}else{
	    //	return true;
	    //}
	}
	

	function selectShop(t) {

		if(jsMode=="edit") {
			return false;	
		}
	    
		if(t == '0') {
    		$("#toShop").removeAttr("checked");
    		
		} else {
    		$("#toShop").attr("checked","checked");
		}
		
		changeBtn($("#toShop"));
	}
	
	function changeBtn(obj) {
		if($(obj).attr("checked") || $(obj).attr("checked")=="checked") {
    		$("#recuitment_btn02").html("<a href=\"javascript:void(0);\">下一步，创建店铺</a>");    		
		} else {
    		$("#recuitment_btn02").html("<a href=\"javascript:void(0);\">提交</a>");
		}
	}

var select_zizhi;

		$(document).ready(function(){
			ajaxLoadNext('root','root');
			ajaxLoadNext1('root','root');
			ajaxLoadNext2('root','root');

			$("input").bind('change',function(){
				$(this).removeClass("bctxt");
			});
			$("select").bind('change',function(){
				$(this).removeClass("bctxt");
			});
			
	    	$(document).ready(function(){    		
	 			   		
	    		$(".sc").click(function(){
	    			select_zizhi=$(this).attr("id")
	    			$("#uploadFile").click();
	    		})
	    		
			});
	    	
	    	var t= jsProperty;
	    	if(''==t || '0'==t) {
		    	selectShop(0);
	    	} else {
		    	selectShop(1);	    		
	    	}
	    	
	    	setSelecter(false);
		});

		function autoWrite(obj,cus,ser){
			var o = $(obj).val();
			if($("#"+cus).val()==''){
				$("#"+cus).val(o);
			}

			if($("#"+ser).val()==''){
				$("#"+ser).val(o);
			}

		}
		/**
    	*格式化商品产地
    	*/
    	function initProductAddress(){
    		var provincetemp = $("#busState").val();
    		if(provincetemp!=''){
    			ajaxLoadNext(provincetemp,'busState');//格式化区域:默认加载市
    			setTimeout("initProductAddresss()",300);
    		}
    	}

    	/**
    	*格式化商品产地
    	*/
    	function initProductAddresss(){
    		var towntemp = $("#busCity").val();
       		if(towntemp!=''){
       			ajaxLoadNext(towntemp,'busCity');//格式化区域:默认加载区
       		}
    	}

    	//赋值
    	function setProductAddress(){
    		var provincetemp = jsBusState;
    		var towntemp = jsBusCity;
    		var countytemp = jsBusAddress;
    		if(provincetemp!=''){
    			$("#busState").find("option[text='"+provincetemp+"']").attr("selected","selected");
        		if(towntemp!=''){
        			$("#busCity").find("option[text='"+towntemp+"']").attr("selected","selected");
            		if(countytemp!=''){
            			$("#busAddress").find("option[text='"+countytemp+"']").attr("selected","selected");
            		}
        		}
    		}
    	}

    	/**
    	*格式化商品产地
    	*/
    	function initProductAddress1(){
    		var bankState = $("#bankState").val();
       		if(bankState!=''){
       			ajaxLoadNext1(bankState,'bankState');//格式化区域:默认加载区
       		}
    	}

    	//赋值
    	function setProductAddress1(){
    		var bankState = jsBankState;
    		var bankCity = jsBankCity;
    		if(bankState!=''){
    			$("#bankState").find("option[text='"+bankState+"']").attr("selected","selected");
        		if(bankCity!=''){
        			$("#bankCity").find("option[text='"+bankCity+"']").attr("selected","selected");
        		}
    		}
    	}


    	/**
    	*格式化商品产地
    	*/
    	function initProductAddress2(){
    		var comState = $("#comState").val();
       		if(comState!=''){
       			ajaxLoadNext2(comState,'comState');//格式化区域:默认加载区
       			setTimeout("initProductAddresss2()",300);
       		}
    	}

    	/**
    	*格式化商品产地
    	*/
    	function initProductAddresss2(){
    		var towntemp = $("#comCity").val();
       		if(towntemp!=''){
       			ajaxLoadNext2(towntemp,'comCity');//格式化区域:默认加载区
       		}
    	}

    	//赋值
    	function setProductAddress2(){
    		var comState = jsComState;
    		var comCity = jsComCity;
    		var comAdd = jsComAdd;
    		if(comState!=''){
    			$("#comState").find("option[text='"+comState+"']").attr("selected","selected");
        		if(comCity!=''){
        			$("#comCity").find("option[text='"+comCity+"']").attr("selected","selected");
        			if(comAdd!=''){
            			$("#comAdd").find("option[text='"+comAdd+"']").attr("selected","selected");
            		}
        		}
    		}
    	}

		//非空验证
		function validatorForm(){
			var $list = $("[ismust=1]");
			var ret = 0;
			$list.each(function(i,val){
				if($($list[i]).get(0).tagName  =='SELECT'){
					if($($list[i]).val()=='-1'){
						$($list[i]).addClass("bctxt");
						ret++;
					}
				}else if($($list[i]).get(0).tagName  =='INPUT'){
					if($($list[i]).val()==''){
						$($list[i]).addClass("bctxt");
						ret++;
					}
				}else if($($list[i]).get(0).tagName  =='TEXTAREA'){
					if($($list[i]).val()==''){
						$($list[i]).addClass("bctxt");
						ret++;
					}
				}

			});

			if(ret>0){
				showInfoBox("请补充完整红色区域后，再次提交！");
				return false;
			}else{
				return true;
			}
		}

    	function thisWordnum(obj){
    		var val = $(obj).val();
    		var length = val.length;
    		$(obj).parent(".store_nw").find("[name=wordnum]").text(length+"/300");
    	}


		/*********************商品产地start***********************/
    	/**
    	*第一级省的单击事件
    	*/
    	function provinceOnchange(obj){
    		var id=$(obj).val();
    		var thisid = $(obj).attr("id");
			ajaxLoadNext(id,thisid);
			$("#busAddress").html("<option   value='-1'>--请选择--</option>");//清空第三级县的选项
			if(id != '-1'){
				$("[name='"+thisid+"']").val($(obj).find("option:selected").text());
			}
    	}


    	/**
    	*第二级市的单击事件
    	*/
    	function townOnchange(obj){
    		var id=$(obj).val();
    		var thisid = $(obj).attr("id");
			ajaxLoadNext(id,thisid);
			if(id != '-1'){
				$("[name='"+thisid+"']").val($(obj).find("option:selected").text());
			}
    	}

    	/**
    	*第三级的单击事件
    	*/
    	function countyOnchange(obj){
    		var thisid = $(obj).attr("id");
    		if(thisid != '-1'){
				$("[name='"+thisid+"']").val($(obj).find("option:selected").text());
			}
    	}
    	/**
    	*动态格式化下一级的类目
    	*/
    	function ajaxLoadNext(parentid,selectid){
    		var bus = "";
            var data;
    		if(selectid=='root'){
    			selectid='busState';
    			bus=jsBusState;
                data=getArea90(0,null);
    		}else if(selectid=='busState'){
    			selectid='busCity';
    			bus=jsBusCity;
                data=getArea90(2,parentid);
    		}else if(selectid=='busCity'){
    			selectid='busAddress';
    			bus=jsBusAddress;
                data=getArea90(3,parentid);
    		}
    		var basePath = jsBasePath;

			var html = "<option  value='-1'>--请选择--</option>";
			var selected = 'selected="selected"';

			for(var i=0;i<data.length;i++){
				html+='<option value="'+data[i].code+'" '+(bus!=null&&bus==data[i].name?selected:"")+'>'+data[i].name+'</option>';
			}

			$("#"+selectid).html(html);

			if(selectid=='busState'){
				setTimeout("initProductAddress()",300);
				setTimeout("setProductAddress()",600);
			}


    	}
   		/*********************商品产地end***********************/


   		/*********************银行start***********************/
    	/**
    	*第一级省的单击事件
    	*/
    	function bankStateOnchange(obj){
    		var id=$(obj).val();
    		var thisid = $(obj).attr("id");
			ajaxLoadNext1(id,thisid);
			//$("#busAddress").html("<option   value='-1'>--请选择--</option>");//清空第三级县的选项
			if(id != '-1'){
				$("[name='"+thisid+"']").val($(obj).find("option:selected").text());
			}
    	}


    	/**
    	*第二级市的单击事件
    	*/
    	function bankCityOnchange(obj){
    		var id=$(obj).val();
    		var thisid = $(obj).attr("id");
			if(id != '-1'){
				$("[name='"+thisid+"']").val($(obj).find("option:selected").text());
			}
    	}

    	/**
    	*动态格式化下一级的类目
    	*/
    	function ajaxLoadNext1(parentid,selectid){
    		var bus = "";
            var data;
    		if(selectid=='root'){
    			selectid='bankState';
    			bus=jsBankState;
                data=getArea90(0,null);
    		}else if(selectid=='bankState'){
    			selectid='bankCity';
    			bus=jsBankCity;
                data=getArea90(2,parentid);
    		}
    		var basePath = jsBasePath;

			var html = "<option  value='-1'>--请选择--</option>";
			var selected = 'selected="selected"';

			for(var i=0;i<data.length;i++){
				html+='<option value="'+data[i].code+'" '+(bus!=null&&bus==data[i].name?selected:"")+'>'+data[i].name+'</option>';
			}

			$("#"+selectid).html(html);

			if(selectid=='bankState'){
				setTimeout("initProductAddress1()",400);
				setTimeout("setProductAddress1()",700);
			}

    	}

    	/*********************银行end***********************/


    	/*********************公司start***********************/
    	/**
    	*第一级省的单击事件
    	*/
    	function comStateOnchange(obj){
    		var id=$(obj).val();
    		var thisid = $(obj).attr("id");
			ajaxLoadNext2(id,thisid);
			//$("#busAddress").html("<option   value='-1'>--请选择--</option>");//清空第三级县的选项
			if(id != '-1'){
				$("[name='"+thisid+"']").val($(obj).find("option:selected").text());
			}
    	}


    	/**
    	*第二级市的单击事件
    	*/
    	function comCityOnchange(obj){
//     		var id=$(obj).val();
//     		var thisid = $(obj).attr("id");
// 			if(id != '-1'){
// 				$("[name='"+thisid+"']").val($(obj).find("option:selected").text());
// 			}
			var id=$(obj).val();
    		var thisid = $(obj).attr("id");
    		ajaxLoadNext2(id,thisid);
			if(id != '-1'){
				$("[name='"+thisid+"']").val($(obj).find("option:selected").text());
			}
    	}

    	/**
    	*第三级的单击事件
    	*/
    	function comAddOnchange(obj){
    		var thisid = $(obj).attr("id");
    		if(thisid != '-1'){
				$("[name='"+thisid+"']").val($(obj).find("option:selected").text());
			}
    	}

    	/**
    	*动态格式化下一级的类目
    	*/
    	function ajaxLoadNext2(parentid,selectid){
    		var bus = "";
            var data;
    		if(selectid=='root'){
    			selectid='comState';
    			bus=jsComState;
                data=getArea90(0,null);
    		}else if(selectid=='comState'){
    			selectid='comCity';
    			bus=jsComCity;
                data=getArea90(2,parentid);
    		}else if(selectid=='comCity'){
    			selectid='comAdd';
    			bus=jsComAdd;
                data=getArea90(3,parentid);
    		}

			var html = "<option  value='-1'>--请选择--</option>";
			var selected = 'selected="selected"';

			for(var i=0;i<data.length;i++){
				html+='<option value="'+data[i].code+'" '+(bus!=null&&bus==data[i].name?selected:"")+'>'+data[i].name+'</option>';
			}

			$("#"+selectid).html(html);

			if(selectid=='comState'){
				setTimeout("initProductAddress2()",400);
				setTimeout("setProductAddress2()",700);
			}

    	}

    	/*********************公司end***********************/

    	function changeimg(imgname){
    		$("#img1").attr("src",jsStaticResources+"images/"+imgname);
    	}
    	function changeimg2(obj){
    		if($(obj).val() == '0'){
    			$("#img2").attr("src",jsStaticResources+"images/identity.gif");
    			$("#nw_name").html("<i></i> 身份证：");
    		}else if($(obj).val() == '1'){
    			$("#img2").attr("src",jsStaticResources+"images/info_01.gif");
    			$("#nw_name").html("<i></i> 往来内地通行证号码：");
    		}else if($(obj).val() == '2'){
    			$("#img2").attr("src",jsStaticResources+"images/info_02.gif");
    			$("#nw_name").html("<i></i> 往来大陆通行证号码：");
    		}else if($(obj).val() == '3'){
    			$("#img2").attr("src",jsStaticResources+"images/info_03.gif");
    			$("#nw_name").html("<i></i> 护照号码：");
    		}
    	}
    	function showimg(src){
    		$("#img3").attr("src",jsStaticResources+'images/'+ src);
    		$("#showimg").show();
    	}

    	function showimg2(){
    		$("#img3").attr("src",$("#img2").attr("src"));
    		$("#showimg").show();
    	}

    	function hideimg(){
    		$("#showimg").hide();
    	}

    	function preView(select_zizhi,imgsrc){
			 var hid = $("input[name="+select_zizhi+ "]");
			 hid.val(imgsrc);

			 var  img=hid.siblings(".imgDiv");
			 var url=imgsrc;
			 if(isPdf(imgsrc)) {
				 imgsrc=jsStaticResources+"images/pdf.png";
			 } 
			 
			 if(img.length<1){
				 hid.after("<br /><div class='imgDiv'><img src='"+imgsrc+"' onclick=\"winOpen('"+url+"');\"><div class='sc_del' onclick=\"clearImg('" +select_zizhi+ "');\">删除</div></div>");
			 }else{
				 img.find("img").attr("src",imgsrc);
				 img.find("img").attr("onclick","winOpen('"+url+"');");
				 img.show();
			 }
    	}

    	function isPdf(str) {
    		if(str==null || str=='' || str.length<5) return false;
    		
    		var ppostfix=str.substring(str.length-4);
    		return ppostfix==".pdf" || ppostfix==".PDF";
    	}
    	
    	function winOpen(url){
    		window.open(url);
    	}
    	
    	function clearImg(select_zizhi){
			 var hid = $("input[name="+select_zizhi+ "]");
			 hid.val("");

			 var  img=hid.siblings(".imgDiv");
			 img.hide();
    	}
    	
    	function fileUpload() {
    		
    	    $.ajaxFileUpload({
    	        url: jsBasePath+'/upload/pic.json?folder='+jsId,
    	        type: 'post',
    	        secureuri: false, //一般设置为false
    	        fileElementId: "uploadFile", // 上传文件的id、name属性名
    	        dataType: 'json', //返回值类型，一般设置为json、application/json
    	        elementIds: 1, //传递参数到服务器
    	        success: function(data, status){
    	        	if(data.success){
    	        		var imgPath = data.data[0].original;
    	        		if(imgPath.indexOf("http://") != 0) {
    	        			imgPath = "http://"+imgPath;
    	        		}
    	        		preView(select_zizhi,imgPath);
    	        		//alert("图片上传成功");
    	        	}else{
    	        		showInfoBox(data.msg);
    	        	}
    	        },
    	        error: function(data, status, e){ 
    	            alert(e);
    	        }
    	    });
    	}
    	
    	function selectBank(obj) {
    		var $selecter=$(obj);
    		if($selecter.val() != -1) {
    			$("#bankId").val($selecter.val());
    		}
    	}
    	
    	function setSelecter(bln){
    		var index=-1;
    		var selecter=$("#bankBankSelecter").get(0);
    		var val=$("#bankId").val();
    		if(bln && val.indexOf("银") ==-1) {
    			val=val+"银行";
    		}
    		for(var i=0;i<selecter.options.length;i++){
    			if(selecter.options[i].text == val) {
    				index = i;
    				selecter.options[i].selected = true;      
    				$("#bankId").val(val);  
    				break;  
    			}
    		}
    		
    		if(index==-1) {
				selecter.options[selecter.options.length-1].selected = true;
    		}
    	}
