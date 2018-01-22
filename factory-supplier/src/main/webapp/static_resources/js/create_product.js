	var ue = UE.getEditor('editor');
	var uploadSku=null;

	var uploader ;
	

	 var map = new Map();
	 var mapImage = new Map();

	$(document).ready(function(){
		selectedHeaderLi("spgl_header");
		
		ajaxLoadNext('root','root');//格式化区域:默认加载省级
		ajaxLoadNextSend('root','rootSend');//格式化发货地区域：默认加载省级
		
		initProductAddress();//格式化产地
		initSendAddress();//格式化发货地
		setTimeout("setProductAddress()",500);////格式化产地
		setTimeout("setSendAddress()",500);////格式化产地
		initAttribute();//格式化属性
		initParameter();//格式化参数
		initSpecification();//格式化规格
		$("input").bind('change',function(){
			$(this).removeClass("bctxt");	
		});
		$("select").bind('change',function(){
			$(this).removeClass("bctxt");	
		});
		
		var options = {
	            success: function (data) {
	                //$("#responseText").text(data);
	                //showInfoBox(123);
	            }
	     };
		var productid=$("#productid").val();
		if(productid != null && productid!='') {
			ajaxGetSku(productid);
		} else {
			$("#rdType2").attr("checked","checked");
			specificationTypeChangge($("#rdType2"));
			$("#skuLoading").hide();
			ajaxGetLevelCnt();
		}
		//运费
		$("#rdFreightType0").click(function(){rdFreightTypeClick(0);});
		$("#rdFreightType1").click(function(){rdFreightTypeClick(1);});
		$("#rdFreightType2").click(function(){rdFreightTypeClick(2);});
		$("#rdFreightType3").click(function(){rdFreightTypeClick(3);});
		$("#rdlimitCnt1").click(function(){rdlimitCntClick(1);});
		$("#rdlimitCnt2").click(function(){rdlimitCntClick(2);});
		$("#rdAreas0").click(function(){rdAreasClick(0);});
		$("#rdAreas1").click(function(){rdAreasClick(1);});

		if($("#rdFreightType1").attr("checked") || $("#rdFreightType1").attr("checked") == "checked") {rdFreightTypeClick(1);}

		if($("#rdlimitCnt2").attr("checked") || $("#rdlimitCnt2").attr("checked") == "checked")	{
			rdlimitCntClick(2);
		} else {
			rdlimitCntClick(1);
		}
		if($("#areasCode").val()=="0" || $("#areasCode").val()=="") {
			rdAreasClick(0);
		} else {
			rdAreasClick(1);
		}
				
		if(saleKbn=="1") {
			$("#saleKbn").attr("checked","checked");
			selSaleKbn(1);
		} else if(saleKbn=="2") {
			$("#saleKbn2").attr("checked","checked");
			selSaleKbn(2);
		} else if(saleKbn=="4") {
			$("#saleKbn4").attr("checked","checked");
			selSaleKbn(4);
		} else if(saleKbn=="5") {
			if(parseFloat(empPrice)==0 && $("#saleNote").val().length == 10) {
				saleKbn="50";
			}
			if(saleKbn=="5"){
				$("#saleKbn5").attr("checked","checked");
				selSaleKbn(5);
			} else {
				$("#saleKbn50").attr("checked","checked");
				selSaleKbn(50);
			}
		}
		$("#bak_saleKbn").val(saleKbn);	
		 // ajaxSubmit
       $("#btnAjaxSubmit").click(function () {
           $("#sub_form").ajaxSubmit(options);
       });
		
		//initEditspan();
		
		// 初始化Web Uploader
		$("#filePicker").click(function(){
			$("#uploadingJqSelecter").val("");
			var hasLoadCount=$(".uploadimg_list>ul>li>img[name='loadimage']").length;
			if(hasLoadCount>=5){
				//showInfoBox("已上传完5张");
				$("#errorUploadSpan").text("已上传完5张图片").css("display","inline");
				return false;
			}
			$("#uploadFile").click();
		})
		
		//setTimeout("addimagebutton()",1000);
		setTimeout("initIntroduction()",1000);//编辑页面的富文本编辑框赋值
		
		$("#name").trigger("onkeyup");
		$("#fullName").trigger("onkeyup");
		$("#promotion").trigger("onkeyup");
		$('.mui_tip1').hover(function(){
			$('.tip1').show();	
		},function(){
			$('.tip1').hide();	
		})
		$('.mui_tip2').hover(function(){
			$('.tip2').show();	
		},function(){
			$('.tip2').hide();	
		});
		
		var ftop =$("#rdType1").offset().top-100;
		$("#shop_popup").attr("style","z-index:9999999;position:absolute!important;top:"+ftop+"px;margin-left:-350px;");
		
		initq();		
	});
	
	function initq() {
		var id=$("#questionnaireId").val();
		if(id>-1) {
			$("#qresult a").attr("href",jsBasePath+"/questionnaire/result.html?qId="+id);
			$("#qresult").show();			
		} else {
			$("#qresult").hide();
		}
	}
	
	function fileUpload() {
	   var jqSelecter = $("#uploadingJqSelecter").val();
	   if(jqSelecter=="") {
    	    $(".uploadimg_list>ul>li").each(function(i){
				if($(this).find("img[name='loadimage']").length<1){
					$(this).find("div").html("文件上传中...");
					return false;
				}
			});
	    } else {
	    	$("#"+jqSelecter).html("上传中...");
	    }
	    
	    $.ajaxFileUpload({
	        url: jsBasePath+'/upload/pic.json?folder='+jsSupplierId,
	        type: 'post',
	        secureuri: false, //一般设置为false
	        fileElementId: "uploadFile", // 上传文件的id、name属性名
	        dataType: 'json', //返回值类型，一般设置为json、application/json    	       
	        success: function(data, status){
	        	if(data.success){
	        		var imgsrc = data.data[0].original;
	        		if(imgsrc.indexOf("http://") != 0) {
	        			imgsrc = "http://"+imgsrc;
	        		}
	         	   if(jqSelecter=="") {
        				$(".uploadimg_list>ul>li").each(function(i){
        					if($(this).find("img").length<1){
        						$(this).html("<img src='"+imgsrc+"' name='loadimage' style='width:100%;height:100%'/>");
        						$(this).hover(function(){
        			    			var deletetxt="<div class='r_delete'><img src='"+jsStaticResources+"images/close.gif' width='12' height='12' alt='close' onclick='removethisImage("+i+");'><img src='"+jsStaticResources+"images/icon_left.png' class='icon_left' onclick='sortImage("+i+",-1);' /><img src='"+jsStaticResources+"images/icon_right.png' class='icon_right' onclick='sortImage("+i+",1);' /></div>";	
        			    			$(this).prepend(deletetxt);
        			    		},function(){
        			    			$(this).find('.r_delete').remove();
        			    		})	
        						return false;
        					}
        				});
        				$("#errorUploadSpan").css("display","none");
	         	   } else {
						$("#"+jqSelecter).html("<img src='"+imgsrc+"' style='width:40px;height:40px'/>");
						$("#"+jqSelecter).parent().find("[name="+jqSelecter.split("_")[0]+"]").val(imgsrc);
	         	   }
    				 //initDeltetClose();
	        	}else{
	        		$("#errorUploadSpan").text(data.msg).css("display","inline");
	        	}
	        },
	        error: function(data, status, e){ 
	            showInfoBox(e);
	        }
	    })
	}
	
	function buildBulk(){
		var height=$("#height").val();
		var width=$("#width").val();
		var length = $("#length").val();
		var bulk=0;
		if(height!=''&&width!=''&&length!=''){
			bulk = parseFloat(height)*parseFloat(width)*parseFloat(length);
			$("#bulk").val(bulk.toFixed(2));
		}
	}
	
	function maxFucoinCheck(obj){
		var doubleReg = /^-?\d+\.?\d{0,2}$/;
		var intReg = /^[\d]+$/;
		if(doubleReg.test(obj.value)){
			return ;
		}else if(intReg.test(obj.value)){
			return ;
		}else{
			obj.value = obj.value.substring(0,obj.value.length-1);
		}
	}
	
	function initDeltetClose(){
		var $list = $(".uploadimg_list ul li");
		$list.each(function(i,val){
				if($(this).find("img[name='loadimage']").length!=0){
					$(this).hover(function(){
		    			var deletetxt="<div class='r_delete'><img src='"+jsStaticResources+"images/close.gif' width='12' height='12' alt='close' onclick='removethisImage("+i+");'><img src='"+jsStaticResources+"images/icon_left.png' class='icon_left' onclick='sortImage("+i+",-1);' /><img src='"+jsStaticResources+"images/icon_right.png' class='icon_right' onclick='sortImage("+i+",1);' /></div>";	
		    			$(this).prepend(deletetxt);
		    		},function(){
		    			$(this).find('.r_delete').remove();
		    		})	
				}
		});
		
	}
	
	function removethisImage(i){
		$(".uploadimg_list").find("li[name='li_"+i+"']").html("<div>主图<br>800*800</div>");
		$($(".uploadimg_list").find("li[name='li_"+i+"']")).unbind('mouseenter mouseleave');
	}

	function sortImage(i,p){
		if(i+p<0 || i+p>=5) return;
		var self = $($(".uploadimg_list").find("li[name='li_"+i+"']")).find("img[name='loadimage']");
		if(self.length ==0) return;
		
		var src = self.attr("src");
		var bro = $($(".uploadimg_list").find("li[name='li_"+(i+p)+"']")).find("img[name='loadimage']");
		if(bro.length>0) {
			self.attr("src",bro.attr("src"));
			bro.attr("src",src);
		} else {
			self.parent().html("<div>主图<br>800*800</div></li>");
			$($(".uploadimg_list").find("li[name='li_"+i+"']")).unbind('mouseenter mouseleave');
			
			$(".uploadimg_list").find("li[name='li_"+(i+p)+"']").html("<img src=\""+src+"\" style=\"width:100%;height:100%\" name='loadimage'>");
			$(".uploadimg_list").find("li[name='li_"+(i+p)+"']").hover(function(){
    			var deletetxt="<div class='r_delete'><img src='"+jsStaticResources+"images/close.gif' width='12' height='12' alt='close' onclick='removethisImage("+(i+p)+");'><img src='"+jsStaticResources+"images/icon_left.png' class='icon_left' onclick='sortImage("+(i+p)+",-1);' /><img src='"+jsStaticResources+"images/icon_right.png' class='icon_right' onclick='sortImage("+(i+p)+",1);' /></div>";	
    			$(".uploadimg_list").find("li[name='li_"+(i+p)+"']").prepend(deletetxt);
    		},function(){
    			$(".uploadimg_list").find("li[name='li_"+(i+p)+"']").find('.r_delete').remove();
    		})	
		}
	}
	/**
	*编辑页面的富文本编辑框赋值
	*/
	function initIntroduction(){
		var editorTemp = $("#editorTemp").val();
		UE.getEditor('editor').setContent(editorTemp+"");
	}
	
	/**
	*格式规格编辑事件
	*/
    function initEditspan(){
        $("[name='editSpan']").click(function(){    
            $(this).html("<input type=\"text\" style=\"width:50px;height:20px;\" value=\""+ $(this).text() +"\"/>").find('input').focus().blur(function(){    
            	var thisval = $(this).val();
            	if($.trim(thisval)==''){
            		$(this).val($(this).parent("span").attr("value"));
            	}
				$(this).parent("span").prev().attr("value",$(this).val()).end().html($(this).val());
            });                        
        }).hover(function(){    
            //$(this).addClass('hover');    
        },function(){    
            //$(this).removeClass('hover');    
        });    
    }
	
	
	/**
	*格式化商品产地
	*/
	function initProductAddress(){
		var provincetemp = $("#provincetemp").val();
		var towntemp = $("#towntemp").val();
		if(provincetemp!=''){
			ajaxLoadNext(provincetemp,'province');//格式化区域:默认加载市
    		if(towntemp!=''){
    			ajaxLoadNext(towntemp,'town');//格式化区域:默认加载区
    		}
		}
	}
	
	//赋值
	function setProductAddress(){
		var provincetemp = $("#provincetemp").val();
		var towntemp = $("#towntemp").val();
		var countytemp = $("#countytemp").val();
		if(provincetemp!=''){
			$("#province").find("option[value='"+provincetemp+"']").attr("selected","selected");
    		if(towntemp!=''){
    			$("#town").find("option[value='"+towntemp+"']").attr("selected","selected");
        		if(countytemp!=''){
        			$("#county").find("option[value='"+countytemp+"']").attr("selected","selected");
        		}
    		}
		}
		$("#produceaddress").val($("#province").find("option[value='"+provincetemp+"']").text()+" "+$("#town").find("option[value='"+towntemp+"']").text()+" "+$("#county").find("option[value='"+countytemp+"']").text());
	}
	
	/**
	*格式化商品发货地
	*/
	function initSendAddress(){
		var sendProvincetemp = $("#sendProvincetemp").val();
		if(sendProvincetemp!=''){
			ajaxLoadNextSend(sendProvincetemp,'sendTown');//格式化区域:默认加载市
		}
	}
	
	/**
	*格式化商品发货地（赋值）
	*/
	function setSendAddress(){
		var sendProvincetemp = $("#sendProvincetemp").val();
		var sendTowntemp = $("#sendTowntemp").val();
		if(sendProvincetemp!=''){
			$("#sendProvince").find("option[value='"+sendProvincetemp+"']").attr("selected","selected");
    		if(sendTowntemp!=''){
    			$("#sendTown").find("option[value='"+sendTowntemp+"']").attr("selected","selected");
    		}
		}
		
		$("#sendAddress").val($("#sendProvince").find("option[value='"+sendProvincetemp+"']").text()+" "+$("#sendTown").find("option[value='"+sendTowntemp+"']").text());
	}
	/*校验商品名称不能为空
	 * */
	function validatorproductfullname(){
		var val = $("input[name = fullName]").val();
        if(val == '' || $.trim(val) == ''){

        	$("input[name = fullName]").addClass("bctxt");
			
        	showInfoBox("商品名称必须填写！再次提交！");
            return false;
        }else{
        	return true;
        }
	}
	/*
	 *校验商品名称不能重复 
	 */
	function validatorfullName(){     
	    var rtn=true;
		var fullName=$("#fullName").val();     
		var basePath = jsBasePath;
		$.ajax({
			url : basePath +'/product/validatorfullName.json?fullName='+fullName + "&apprId="+$("#apprid").val()+"&productId="+$("#productid").val() + "&productcopy=" + $("#productcopy").val(),
			type : "GET",
			async : false,
			dataType: "json",  //返回json格式的数据  
		    success: function(data){       
			    if(data==1){
			    	rtn= false;
			    }
		    }, error : function() {}           
		});
		return rtn;
	} 
	
	/*
	 弹出对话框，提示是否删除前次修改
	 */
	function popupDeleteAppr(proid){
		showConfirm(null,"还原在售信息，您确定要这要样做吗？","deleteAppr("+proid+")");
	}
	
	/*
	点击还原在售信息按钮就删除前次修改的数据
	*/
	function deleteAppr(proid){
		//var id=$("#apprid").val();
		var basePath = jsBasePath;
		
		$.ajax({
			url : basePath +'/product/deleteModifyAppr.json?productId='+proid,
			type : "GET",
			async : false,
			dataType: "json",  //返回json格式的数据  
		    success: function(data){       
			    if(data==1){
		    	window.location=basePath +'/product/toCreateProduct.html?productId='+proid+'&selltype=selling';	
			    	
			    }
		    }, error : function() {}           
		});
	}
	/**
	*submit
	*/
	function subForm(state,noConfirm){
		if(checkAllLadderSku()){
			return false;
		}
		if(checkLadderAndSaleKbn()){
			return false;
		}
		
		if($("#categoryId").val()=='' || $("#shopId").val()=='' || !$("#shopId").val() || !$("#categoryId").val()) {
			showInfoBox("商品类别不正确，请修改后再次提交");
			return false;
		}
		setIntroduction();

		specificationChanageAll();

		var strPrefix="";
		if($("#rdType2").prop("checked")) {strPrefix="self_";}
		var selltype =$("#selltype").val();
		$("#status").val(state);
		//var savestate =$("#savestate").val(state);//给字段赋值
		if($("#rdFreightType2").attr("checked")){
			setWBmust($("#shippingTemplateId").val());
		}
		
		var inputErr = 0;   //信息是否完整 0：完整/1：不完整
		/*
		 * 校验切换中文输入法鼠标点击能输入中文的文本框，但是此文本框必须输入数字
		 * */
		
		var $listnum = $("[isnum=1]");
		var ret = 0;
		$listnum.each(function(i,val){
			if(!$.trim($($listnum[i]).val())=='' && isNaN($($listnum[i]).val())){ //使用isNaN判断是否是非法数值
				$($listnum[i]).addClass("bctxt");
				ret++;
			}
		});
		if(ret>0){
			showInfoBox("红框区域数字输入不正确，请输入数字后，再次提交！")
			return false;
		}
		if(!validatorfullName()){
	    	$("#fullName").addClass("bctxt");
	    	showInfoBox("商品名称已存在，再次提交！")
			return false;
		}
		
		//促销活动非空验证
		var $listnull = $("[isnull=1]");
		var msg = 0;
		$listnull.each(function(i,val){
			if($.trim($($listnull[i]).val())==''|| $.trim($($listnull[i]).val())==0){ //判断是否是为空
				$($listnull[i]).addClass("bctxt");
				msg++;
			}
		});
		if(msg>0){
			showInfoBox("红框区域填写大于0的数字后，再次提交！")
			return false;
		}
        /*
         * 校验装箱清单名称或数量输入，必须输入另一个
         * 
         */
		var detaillist = $("input[name=detaillist_result]"); //说明书_7(一个或多个)
		for (var int = 0; int < detaillist.length; int++) {
			var val= $.trim($(detaillist[int]).val()); //获取每个值（说明书_7）
			if(val!='_' && val!='') { //输入后又删除了val为_ 
				if(val.indexOf("_")==0 || val.indexOf("_")==val.length-1) { //如果输入后又删除了val为_   val.indexOf("_")==0(0_7)  val.indexOf("_")==val.length-1（说明书_）
					$(detaillist[int]).parent().find("input").addClass("bctxt"); //parent()获得当前匹配元素集合中每个元素的父元素，使用选择器进行筛选是可选的。
					$(detaillist[int]).parent().find("input:eq(0)").focus();
					showInfoBox("清单名称和数量必须输入，再次提交！");
					return;
				}
			}
		}
		

		if(state==0) {
			//临时保存
			if(!validatorproductfullname()){
				return false;
			}

			if(!validatorForm()){
				inputErr=1; 	//1：不完整
			}
			/*校验装箱清单
			 */
			var det=0;
			var hasSpe2 = false;
			if($("#rdType1").prop("checked")) {
				//简略sku无需检查
			} else {
				var specification_results=$("[name="+strPrefix+"specification_result]");//获取的是规格
				if(specification_results.length > 0) {
					if($("#bak_kingaku1select").val()!="") {
						if($("#bak_kingaku1select").val() != $(specification_results[0]).val()){
							showInfoBox("规格改变后,请首先生成SKU信息，再次提交！");
							return false;
						}
						var selv1 = $("#bak_kingaku1select").val(); //1713214403052816_金色,蓝色,白色
						if(selv1.substring(selv1.length-1)=="_") { //没有勾选规格值
							showInfoBox("规格改变后,请首先生成SKU信息，再次提交！");
							return false;
						}
					}
				} else {
					inputErr=1;
				}
				var k2 = "";
				if(specification_results.length > 1) {
					hasSpe2=true;
					k2 = $(specification_results[1]).val();
				}
				if($("#bak_kingaku2select").val()!="") {
					if($("#bak_kingaku2select").val() != k2){
						showInfoBox("规格改变后,请首先生成SKU信息，再次提交！");
						return false;
					}
					if(k2.substring(k2.length-1)=="_") {
						showInfoBox("规格改变后,请首先生成SKU信息，再次提交！");
						return false;
					}
				}
			}
			
			var $list2 = $("[name=specifications_result]");
			if($list2.size()==0){
				inputErr=1;
			}else{
				if(hasSpe2 && $("#bak_kingaku2select").val()=="") {
					showInfoBox("规格改变后,请首先生成SKU信息，再次提交！");
					return false;
				}
				//判定上传图片是否完整
				var $list1 = $("[name=specifications_image_result]");
				var num1 = 0;
				$list1.each(function(){
					if($(this).val()==''){
						num1++
					}
				});
				if(num1>0){
					inputErr=1;
				}
				
				var ret = 0;
				var colors=",";
				if($("#rdType1").attr("checked") || $("#rdType1").attr("checked")=="checked") {
					var $list3 = $("[name=color]");
					$list3.each(function(){
						if($.trim($(this).val()) == ''){
							$(this).addClass("bctxt");
							$(this).focus();
							ret++;			
						}
					});

					if(ret>0){
						showInfoBox("简略SKU的规格不能为空！");
						return false;
					}					
					
					$list3.each(function(){
						if(colors.indexOf(","+$(this).val()+",") > -1){//判断2个规格值是否重复
							$(this).addClass("bctxt");
							ret++;								
						} else {								
							colors+=$(this).val()+",";
						}
					});
				}
				
				if(ret>0){
					showInfoBox("规格不能重复，请修改红色区域之后，再次提交！");
					return false;
				}

				if(!checkSaleKbn(true)) {
					return false;
				}
			}
			if(inputErr==0) {
				if(noConfirm != 1) {
					/*
					 * 判断没有改变敏感数据时临时保存的操作
					 */
					if(selltype =='selling'){
						if(!isChagePrice()){
							showConfirm(null,"您没有修改价格相关信息，商品可以直接上架，您确定发布该商品吗？点击【确定】发布此商品，点击【取消】将此商品保存到待售商品中，另行上架","subForm(1,0)","subForm(0,1)");
							return false;
						}
					}
				}
			}
		} else {
			//发布
			if(validatorForm()){
				if($("#rdType1").prop("checked")) {
					//简略sku无需检查
				} else {
					var specification_results=$("[name="+strPrefix+"specification_result]");//获取的是规格
					if(specification_results.length > 0) {
						if($("#bak_kingaku1select").val() != $(specification_results[0]).val()){
							showInfoBox("规格改变后,请首先生成SKU信息，再次提交！");
							return false;
						}
						var selv1 = $("#bak_kingaku1select").val(); //1713214403052816_金色,蓝色,白色
						if(selv1.substring(selv1.length-1)=="_") { //没有勾选规格值
							showInfoBox("规格改变后,请首先生成SKU信息，再次提交！");
							return false;
						}
					} else {
						showInfoBox("规格改变后,请首先生成SKU信息，再次提交！");
						return false;
					}
					var k2 = "";
					if(specification_results.length > 1) {
						k2 = $(specification_results[1]).val();
					}
					if($("#bak_kingaku2select").val() != k2){
						showInfoBox("规格改变后,请首先生成SKU信息，再次提交！");
						return false;
					}
					if(k2.substring(k2.length-1)=="_") {
						showInfoBox("规格改变后,请首先生成SKU信息，再次提交！");
						return false;
					}
				}
					
				var $list2 = $("[name=specifications_result]");
				if($list2.size()==0){
					showInfoBox("请首先生成SKU信息，再次提交！");
				}else{
					//判定上传图片是否完整
					var $list1 = $("[name=specifications_image_result]");
					var num1 = 0;
					$list1.each(function(){
						if($(this).val()==''){
							num1++
						}
					});
					if(num1>0){
						showInfoBox("请首先上传SKU图片之后，再次提交！");
						return false;
					}
					
					var ret = 0;
					var colors=",";
					if($("#rdType1").prop("checked")) {
						var $list3 = $("[name=color]");
						$list3.each(function(){
							if(colors.indexOf(","+$(this).val()+",") > -1){//判断2个规格值是否重复
								$(this).addClass("bctxt");
								ret++;								
							} else {								
								colors+=$(this).val()+",";								
								
							}
						});
					}
					
					if(ret>0){
						showInfoBox("规格不能重复，请修改红色区域之后，再次提交！");
						return false;
					}
					
					if(!checkSaleKbn(true)) {
						return false;
					}
				}
			}else{
				showInfoBox("请补充完整红色区域后，再次提交！");
				return false;
			}
		}
		//在提交订单时验证试用返现金额是否为0
		if(checkTrialPriceAndSale()){
			return false;
		}
       
		//在提交订单时验证包邮和运费模板
		if(exemptionFromPostageV()){
			return false;
		}
		
		$("#savestate").val(inputErr);
		$("#sub_form").submit();
		$("#sub_button1").addClass("heycolor").removeAttr("onclick");
		$("#sub_button0").addClass("heycolor").removeAttr("onclick");
	}
	
	function checkSaleKbn(showMsg) {
		// 特省理由
		if($("#saleKbn").prop("checked") || $("#saleKbn2").prop("checked"))	{
			if($.trim($("#saleNote").val())==''){
				if(showMsg) {
					if($("#saleKbn").prop("checked")) {
						showInfoBox("请输入特省理由！");
					} else {
						showInfoBox("请输入换领理由！");
					}
					$("#saleNote").focus();
				}
				return false;
			}
		}

		// 换领
		if($("#saleKbn2").prop("checked"))	{
//			exchanging
//			if("1" == $("#exchanging").val()) {
//				if(showMsg) {
//					showInfoBox("该商品之前的换领还未出结果，暂时不能再进行换领。");
//				}
//				return false;
//			}
			try{
				var d=parseInt($("input[name='divLevel']:checked").attr("data"));
				if(d==0) {
					if(showMsg) {
						showInfoBox("因没有添加员工，暂不能参加换领。");
					}
					return false;
				}
				

				var lsp = $("input[name='fproductprice']");
				if(lsp.length>0) {
					var price = parseFloat($(lsp[0]).val());
					for(var i=1;i<lsp.length;i++) {
						if(price != parseFloat($(lsp[i]).val())) {
							if(showMsg) {
								showInfoBox("参加换领的sku内购价必须相同。");
							}
							return false;
						}
					}
				}
				
			} catch(e) {
				return false;
			}
		}
		
		// 专享
		if($("#saleKbn4").prop("checked"))	{
			if($.trim($("#empPrice").val())==''){
				if(showMsg) {
					showInfoBox("请输入员工专享价格！");
					$("#empPrice").focus();
				}
				return false;
			} else {
				if(parseFloat($("#empPrice").val())<=0 || parseFloat($("#empPrice").val())>getMinPrice()) {
					if(showMsg) {
						showInfoBox("员工专享价格不能为负数！且必须大于0,不能超过sku内购价！");
						$("#empPrice").focus();
					}
					return false;
				}
			}
			
			if($.trim($("#empLevel").val()) == ''){
				if(showMsg) {
					showInfoBox("请输入员工人数！");
					$("#empLevel").focus();
				}
				return false;
			}
			
			var lsn = $("input[name='productnum']");
			var num = 0;
			try {
				for(var i=0;i<lsn.length;i++) {
					if($(lsn[i]).val() == '' ) {
						
					} else {
						num += parseInt($(lsn[i]).val())
					}
				}
			} catch(e) {}
			if($("#empLevel").val() > num){
				showInfoBox("员工人数不能超过库存");
				return false;
			}
		}
		
		// 试用
		if($("#saleKbn5").prop("checked"))	{
			if($.trim($("#trialPrice").val())==''){
				if(showMsg) {
					showInfoBox("请输入评价后返现金额！");
					$("#trialPrice").focus();
				}
				return false;
			} else {
				if(parseFloat($("#trialPrice").val())<0 || parseFloat($("#trialPrice").val())>getMinPrice()) {
					if(showMsg) {
						showInfoBox("评价后返现金额不能为负数,且必须大于等于0,不能超过sku内购价！");
						$("#trialPrice").focus();
					}
					return false;
				}
			}
		}
		

		// 试用
		if($("#saleKbn50").prop("checked"))	{
			if($("#questionnaireId").val()=='-1'){
				if(showMsg) {
					showInfoBox("请选择问卷！");
					$("#questionnaireId").focus();
				}
				return false;
			}
		}
		return true;
	}
    /*
     * 修改非敏感数据点击临时保存，弹出对话框选择是发布还是临时保存
     */
	function isChagePrice() {
		var salekbn =$("input:checkbox[name='saleKbn']:checked").val();
		if(salekbn == undefined){ //用户没有勾选任何salekbn
			salekbn="0";
		}
		if($("#bak_saleKbn").val() != salekbn){ //销售区分改变
			return true;
		}
	
		if($("#bak_saleKbn").val()==4) {
			if(parseFloat($("#bak_empPrice").val()) != parseFloat($("#empPrice").val())){//特享商品价格是否改变
				return true;
			}
			if($("#bak_empLevel").val() != $("input:radio[name='empLevel']:checked").val()){//特享商品员工等级是否改变
				return true;
			}
		}
		if($("#bak_saleKbn").val()==5) { 
			if(parseFloat($("#bak_trialPrice").val()) != parseFloat($("#trialPrice").val())){ //这是判断试用商品价格是否改变
				return true;
			}
		}
		if($("#bak_saleKbn").val()==2) { //换领商品修改评论或价格
			if($("#bak_saleNote").val() != $("#saleNote").val()){ 
				return true;
			}
			if($("#bak_divLevel").val() != $("input:radio[name='divLevel']:checked").val()){ 
				return true;
			}
		}
		if(parseFloat($("#bak_carriage").val()) != parseFloat($("#carriage").val())){ //运费
			return true;
		}
		var shippingTemplateId = $("#shippingTemplateId").val();
		if(shippingTemplateId!="-1" && shippingTemplateId!="*") {
			shippingTemplateId = shippingTemplateId.substring(0,shippingTemplateId.indexOf(","));
		} else {
			shippingTemplateId = ""; //因为回显过来的bak_shippingTemplateId，如果没有选择模板就是空串
		}
		if($("#bak_shippingTemplateId").val() != shippingTemplateId){
			return true;
		}

		var $list2 = $("[name=specifications_result]");
		var $oldSkus = $("[name=bak_specifications_result]");
		
		if($list2.length != $oldSkus.length) {
			return true;;
		}
		var changeStock = false;
		for(var i=0;i<$list2.length;i++) {
			var sku = $($list2[i]).val();
			var skuKK = sku.substring(0,sku.indexOf("_"));
			var oSku ="";
			for (var j = 0; j < $oldSkus.length; j++) {
				var tmp = $($oldSkus[j]).val();
				var oSkuKK = tmp.substring(0,tmp.indexOf("_"));
				if(skuKK == oSkuKK) {
					oSku = tmp;
					break;
				}
			}
			
			if(oSku == "") {//如果旧版的sku等于空，说明是新添加sku
				return true;;
			}
			
			var ary = sku.split('_');
			var oAry = oSku.split('_');

			if(ary.length != oAry.length) {
				return true;;
			}
			
			if(parseFloat(ary[2])!= parseFloat(oAry[2]) || parseFloat(ary[5])!= parseFloat(oAry[5])) {
				return true;;
			}
			
			if(!changeStock) {
				if(parseFloat(ary[3])!= parseFloat(oAry[3])) {
					changeStock= true;
				}
			}
		}
		
		if($("#bak_saleKbn").val()==2 || $("#bak_saleKbn").val()==4) {
			if(changeStock){
				return true;;
			}
		}
		
		return false;
	}
	//非空验证
	function validatorForm(){
		var $list = $("[ismust=1]");
		var ret = 0;
		$list.each(function(i,val){  
			if($($list[i]).attr("typename")=='select'){
				if($($list[i]).val()=='-1'){
					$($list[i]).addClass("bctxt");
					ret++;
				}
			}else if($($list[i]).attr("typename")=='input'){
				if($.trim($($list[i]).val())==''){
					$($list[i]).addClass("bctxt");
					ret++;
				}
			}else if($($list[i]).attr("typename")=='checkboxdiv'){
				if($($list[i]).find("input:checked").length==0){
					$($list[i]).addClass("bordercolor");
					ret++;
				}
			}
		});
		
		//运费
		if($("#rdFreightType1").attr("checked") || $("#rdFreightType1").attr("checked") == "checked") {
			if($("#rdFreightType2").attr("checked") || $("#rdFreightType2").attr("checked") == "checked") {
				if($("#shippingTemplateId").val() =='-1') {
					$("#shippingTemplateId").addClass("bctxt");
					ret++;
				}
			} else {
				if($("#carriage").val() =='') {
					$("#carriage").addClass("bctxt");
					ret++;
				}
			}
		}
		if(ret>0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	*格式化第一张sku图片
	*/
	function initSpecificationsImageFirst(){
		//判定上传图片是否完整
		var $list = $("[name=specifications_image_result]");
		var num = 0;
		$list.each(function(){
			if($(this).val()!=''){
				var image = $(this).val().split("_")[1].split(",")[0];
				$(this).parent().prev(".uploadimg").find("img").attr("src",image);
			}
		});
	}
	/**
	*编辑页面时候格式化属性
	*/
	function initAttribute(){
		var $list = $("[name=attribute_result]");
		if($list.length>0){
			$list.each(function (i,val){
				if($($list[i]).val()!=''){
					var  str = $($list[i]).val();
					var s = str.split("_");
					if(s[1]==1){
						$($list[i]).prev().val(s[2])
					}else if(s[1]==2){
						$($list[i]).prev().val(s[2])
					}else if(s[1]==3){
						var snew =s[2].split(",");
						for(var k=0;k<snew.length;k++){
							if(snew[k]!=''){
								$($list[i]).parent().find('#'+snew[k]).attr("checked","checked");
							}
						}
					}
					
				}
			});
		}
	}
	
	/**
	*编辑页面时候格式化参数
	*/
	function initParameter(){
		var $list = $("[name=parameter_result]");
		if($list.length>0){
			$list.each(function (i,val){
				if($($list[i]).val()!=''){
					var  str = $($list[i]).val();
					var s = str.split("_");
					if(s[1]==1){
						$($list[i]).prev().val(s[2])
					}else if(s[1]==2){
						$($list[i]).prev().val(s[2])
					}else if(s[1]==3){
						var snew =s[2].split(",");
						for(var k=0;k<snew.length;k++){
							if(snew[k]!=''){
								$($list[i]).parent().find('#'+snew[k]).attr("checked","checked");
							}
						}
					}
					
				}
			});
		}
	}
	
	/**
	*编辑页面时候格式化规格
	*/
	function initSpecification(){
		var $list = $("[name=specification_result]");
		if($list.length>0){
			$list.each(function (i,val){
				if($($list[i]).val()!=''){
					var  str = $($list[i]).val();
					var s = str.split("_");
					var snew =s[1].split(",");
					var ns= "";
					for(var k=0;k<snew.length;k++){
						if(snew[k]!=''){
							if($($list[i]).parent().find("[id='"+snew[k]+"']").length==1){
								$($list[i]).parent().find("[id='"+snew[k]+"']").attr("checked","checked");
								ns+=snew[k]+",";
							}else{
							}
						}
				   }
				    if(ns!="") {
				    	$($list[i]).val(s[0]+"_"+ns.substring(0,ns.length-1));
				    } else {
				    	$($list[i]).val("");
				    }
				}
			});
		}
		var $list = $("input[name=self_specification_result]");
		if($list.length>0){
			$list.each(function (i,val){
				if($($list[i]).val()!=''){
					var  str = $($list[i]).val();
					var s = str.split("_");
					var snew =s[1].split(",");
					var ns= "";
					for(var k=0;k<snew.length;k++){
						if(snew[k]!=''){
							if($($list[i]).parent().find("[id='"+snew[k]+"']").length==1){
								$($list[i]).parent().find("[id='"+snew[k]+"']").attr("checked","checked");
								ns+=snew[k]+",";
							}else{
							}
						}
				   }
					
				    if(ns!="") {
				    	$($list[i]).val(s[0]+"_"+ns.substring(0,ns.length-1));
				    } else {
				    	$($list[i]).val("");
				    }
				}
			});
		}
	}
	/*********************商品产地start***********************/   	
	/**
	*第一级省的单击事件
	*/
	function provinceOnchange(obj){
		var id=$(obj).val();
		var thisid = $(obj).attr("id");
		ajaxLoadNext(id,thisid);
		$("#county").html("<option   value='-1''>--请选择--</option>");//清空第三级县的选项
		if(thisid=='province'){
			$("#produceaddress").val($(obj).find("option:selected").text());
		}
	}
	
	
	/**
	*第二级市的单击事件
	*/
	function townOnchange(obj){
		var id=$(obj).val();
		var thisid = $(obj).attr("id");
		ajaxLoadNext(id,thisid);
		if(thisid=='town'){
			$("#produceaddress").val($("#province").find("option:selected").text()+" "+$(obj).find("option:selected").text());
		}
	}
	
	/**
	*第三级的单击事件
	*/
	function countyOnchange(obj){
		var thisid = $(obj).attr("id");
		if(thisid=='county'){
			$("#produceaddress").val($("#province").find("option:selected").text()+" "+$("#town").find("option:selected").text()+" "+$(obj).find("option:selected").text());
		}
	}
	/**
	*动态格式化下一级的类目
	*/
	function ajaxLoadNext(parentid,selectid){
        var data;
		if(selectid=='root'){
			selectid='province';
            data=getArea90(0,null);
		}else if(selectid=='province'){
			selectid='town';
            data=getArea90(2,parentid);
		}else if(selectid=='town'){
			selectid='county';
            data=getArea90(3,parentid);
		}

		var html = "<option  value='-1'>--请选择--</option>";

		for(var i=0;i<data.length;i++){
			html+='<option value="'+data[i].code+'">'+data[i].name+'</option>';
		}

		if(selectid=='county'){
			if(html == "<option  value='-1'>--请选择--</option>"){
				$("#"+selectid).attr("ismust","0").css("display","none");
			}else{
				$("#"+selectid).attr("ismust","1").css("display","inline");
			}
		}else{
			$("#county").attr("ismust","1").css("display","inline");
		}
		$("#"+selectid).html(html);
	}
	/*********************商品产地end***********************/
	
	/*********************发货地址start************************/
	/**
	*第一级省的单击事件
	*/
	function sendOnchange(obj,nextId){
		var id=$(obj).val();
		if(nextId=='sendTown'){
			ajaxLoadNextSend(id,nextId);
			$("#sendAddress").val($(obj).find("option:selected").text());
		}else if(nextId=='sendCounty'){
			if($(obj).find("option:selected").text()!='县'){//避免发货后地：“ 北京市 县” 的情况出现  如果第二级别，只有一个  县  则该字不显示
				$("#sendAddress").val($("#sendProvince").find("option:selected").text()+" "+$(obj).find("option:selected").text());
			}else{
				$("#sendAddress").val($("#sendProvince").find("option:selected").text());
			}
			
		}
	}
	
	/**
	*动态格式化下一级的类目
	*/
	/**
	*动态格式化下一级的类目
	*/
	function ajaxLoadNextSend(parentid,nextId){
        var data;
		if(nextId=='rootSend'){
			nextId='sendProvince';
            data=getArea90(0,null);
		} else {
            data=getArea90(2,parentid);
		}
		var html = "<option value='-1'>--请选择--</option>";

		for(var i=0;i<data.length;i++){
			html+='<option value="'+data[i].code+'">'+data[i].name+'</option>';
		}

		$("#"+nextId).html(html);
	}
	/*******************发货地址end**************************/
	/**
	*属性改变
	*/
	function attributeChanage(obj){
		var $thisobj = $(obj);
		if($thisobj.attr("typename")=='select'){
			var val = $thisobj.val();
			var $result =$thisobj.nextAll("[name='attribute_result']:first");
			var itemid =$result.attr("id");
			if(val=='-1'){
				$result.val("");
			}else{
				$result.val(itemid+"_1_"+val);
			}
		}else if($thisobj.attr("typename")=='input'){
			var val = $thisobj.val();
			var $result =$thisobj.nextAll("[name='attribute_result']:first");
			var itemid =$result.attr("id");
			$result.val(itemid+"_2_"+val);
		}else if($thisobj.attr("typename")=='checkbox'){
			if($thisobj.parents("div[typename='checkboxdiv']").find("input:checked").length>0){
				$thisobj.parents("div[typename='checkboxdiv']").removeClass("bordercolor");
			}
			var checkname = $thisobj.attr("name");
			var $checklist = $thisobj.parents(".add_model").find("[name='"+checkname+"']:checked");
			var vals = "";
			$checklist.each(function(i,val){
				vals = vals+$($checklist[i]).attr("id")+",";
			});
			
			if(vals!=""){
				vals = vals.substring(0,vals.length-1);
			}
			
			var val = vals;
			var $result =$thisobj.parents(".add_model").find("[name='attribute_result']");
			var itemid =$result.attr("id");
			$result.val(itemid+"_3_"+val);
		}
	}
	
	/**
	*参数改变
	*/
	function parameterChanage(obj){
		var $thisobj = $(obj);
		if($thisobj.attr("typename")=='select'){
			var val = $thisobj.val();
			var $result =$thisobj.nextAll("[name='parameter_result']:first");
			var itemid =$result.attr("id");
			if(val=='-1'){
				$result.val("");
			}else{
				$result.val(itemid+"_1_"+val);
			}
		}else if($thisobj.attr("typename")=='input'){
			var val = $thisobj.val();
			var $result =$thisobj.nextAll("[name='parameter_result']:first");
			var itemid =$result.attr("id");
			$result.val(itemid+"_2_"+val);
		}else if($thisobj.attr("typename")=='checkbox'){
			if($thisobj.parents("div[typename='checkboxdiv']").find("input:checked").length>0){
				$thisobj.parents("div[typename='checkboxdiv']").removeClass("bordercolor");
			}
			var checkname = $thisobj.attr("name");
			var $checklist = $thisobj.parents(".add_model").find("[name='"+checkname+"']:checked");
			var vals = "";
			$checklist.each(function(i,val){
				vals = vals+$($checklist[i]).attr("id")+",";
			});
			
			if(vals!=""){
				vals = vals.substring(0,vals.length-1);
			}
			
			var val = vals;
			var $result =$thisobj.parents(".add_model").find("[name='parameter_result']");
			var itemid =$result.attr("id");
			$result.val(itemid+"_3_"+val);
		}
	}

	/**
	*规格onclick
	*/
	function specificationChanage(obj){
		var $thisobj = $(obj);
	
		var valnew = $thisobj.next("span").text();
		if($.trim(valnew)==''){
			valnew = $thisobj.attr("value");
			$thisobj.next("span").text(valnew);
		}
		$thisobj.attr("id",valnew);
		/**
		var checkname = $thisobj.attr("name");
		var $checklist = $thisobj.parent().parent().find("[name='"+checkname+"']:checked");
		var vals = "";
		$checklist.each(function(i,val){
			vals = vals+$($checklist[i]).attr("id")+",";
		});
		
		if(vals!=""){
			vals = vals.substring(0,vals.length-1);
		}
		
		var val = vals;
		var $result =$thisobj.parent().nextAll("[name='specification_result']:first");
		var itemid =$result.attr("id");
		$result.val(itemid+"_"+val);
		*/
	}
	
	
	/**
	*规格onclick
	*/
	function specificationChanageAll(){
		var strPrefix="";
		if($("#rdType2").attr("checked") || $("#rdType2").attr("checked")=="checked") {strPrefix="self_";}

		$list = $("[id^='"+strPrefix+"specificationDiv_']");
		if($list.length>0){
			$list.each(function(i,val){
				
				var ary=$($list[i]).attr("id").split("_");
				var vid =ary[ary.length-1];
				$checklist =$($list[i]).find(".c_option").find("[name='"+vid+"']:checked");
				var vals = "";
				$checklist.each(function(i,val){
					vals = vals+$($checklist[i]).attr("id")+",";
				});
				
				if(vals!=""){
					vals = vals.substring(0,vals.length-1);
				}
				$($list[i]).find(".c_option").find("[name='"+strPrefix+"specification_result']:first").val(vid+"_"+vals);
			});
		}
	}
	/**
	*商品清单列表
	*/
	function detaillistNameChange(obj){
			$(obj).removeClass("bctxt");
			var $thisobj = $(obj);
			var name = $thisobj.val();
			var num = $thisobj.parents(".parameter_one").find("[name='num']").val();
			var $result =$thisobj.parents(".parameter_one").find("[name='detaillist_result']");
			$result.val(name+"_"+num);
	}
    function detaillistNumChange(obj){
    	$(obj).removeClass("bctxt");
		var $thisobj = $(obj);
		var num = $thisobj.val();
		var name = $thisobj.parents(".parameter_one").find("[name='name']").val();
		var $result =$thisobj.parents(".parameter_one").find("[name='detaillist_result']");
		$result.val(name+"_"+num);
	}
	
    /**
    *新增清单列表项
    */
    function newDetaillist(){
    	if($("#detaillistDiv").find(".num").length>=20){
    		showInfoBox("最多只能添加20项清单物品！");
    	}else{
    	var html='';
	    	html+='<div class="parameter_one">';
	    	html+='<div class="parameter_model">';
	    	html+='<div class="a_name">名称：</div>';
	    	html+='<input class="pubilc_input f218" name="name" type="text"  ismust="1" typename="input" value="" maxLength="20" onchange="detaillistNameChange(this)"/>';
	    	html+='</div>';
	    	html+='<div class="parameter_model">';
	    	html+='<div class="num">数量：</div>';
	    	html+='<input class="pubilc_input f88" type="text" name="num" isnum="1"  ismust="1" typename="input" value="" onchange="detaillistNumChange(this)"  maxLength="4" onkeyup="this.value=this.value.replace(/\\D/g,\'\')"/>';
	    	html+='<div class="a_delete" onclick="delthisparent(this);"><a href="javascript:void(0);">删除</a></div>';
	    	html+='</div>';
	    	html+='<input type="hidden" name="detaillist_result" value=""/>';
	    	html+='</div>';
	    	
	    	$("#detaillistDiv").append(html);
    	}
    }
    
    /**
    *移除清单项
    */
    function delthisparent(obj){
    	$(obj).parent().parent().remove();
    }

    function specificationTypeChangge(obj){
		var $list2 = $("[name=specifications_result]");
		var kbn=$(obj).val();
		if($list2.size()>0) {
			showConfirm(null,"切换规格模式，会清空下方SKU信息，您确定要这要样做吗？","showSkuTable("+kbn+")");
			return false;
		} else {
			showSkuTable(kbn);
			return true;
		}
    }
    function showSkuTable(kbn){
    	
    	if(kbn==1) {
       		var html = "";
       		
            html+='<tr>';
            html+='<th class="Upload">上传图片</th>';
            html+='<th class="Color">规格</th>';
            html+='<th class="Barcode">条形码</th>';
            html+='<th class="Price">电商价（元）</th>';
            html+='<th class="Price">内购价（元）</th>';
            html+='<th class="Inventory">库存</th>';
            html+='<th style="display:none;" class="Value">库存预警值</th>';
            html+='<th style="display:none;" class="Value">内购券</th>';        
            html+='<th class="Value">起售数量</th>';     
            html+='</tr>';
            html+='<tr>';
            html+='<td>&nbsp;</td>';
            html+='<td>&nbsp;</td>';
            html+='<td><input class="rule_input f108" type="text" id="all_barcode" maxLength="15" onkeyup="value=value.replace(/[^\\d|a-z|A-Z]/g,\'\');" value="" ><p style="width:90px;"><a onclick="copyTotheall(\'all_barcode\');"  href="javascript:void(0);">全部应用</a></p></td>';
            html+='<td><input class="rule_input f68" type="text" id="productprice" maxLength="8"  onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');" value="" ><p  style="width:90px;"><a onclick="copyTotheall(\'productprice\');"  href="javascript:void(0);">全部应用</a></p></td>';
            html+='<td><input class="rule_input f68" type="text" id="fproductprice" maxLength="8"  onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');" value="" ><p  style="width:90px;"><a onclick="copyTotheall(\'fproductprice\');"  href="javascript:void(0);">全部应用</a></p></td>';
            html+='<td><input class="rule_input f68" type="text" id="productnum"  maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" value="" ><p  style="width:90px;"><a onclick="copyTotheall(\'productnum\');"  href="javascript:void(0);">全部应用</a></p></td>';
            html+='<td style="display:none;" ><input class="rule_input f68" type="text" id="warnnum"  maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" value=""><p style="width:90px;"><a onclick="copyTotheall(\'warnnum\');"  href="javascript:void(0);">全部应用</a></p></td>';
            html+='<td style="display:none;" >&nbsp;</td>';
            html+='<td><input class="rule_input f68" type="text" id="minLimitNum"  maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" value=""><p style="width:90px;"><a onclick="copyTotheall(\'minLimitNum\');"  href="javascript:void(0);">全部应用</a></p></td>';
            html+='</tr>';
               
       		$("#specificationTable").html(html);
       		$('.pro_rule_wrap').show();

		 	$("#rdType1").attr("checked","checked");
       		addSpecificationRow();
    		
    		specificationChanageAll();
    		
    		//加载默认的sku信息
   		    map.each(function(key,value,index){   
   		       	$("#specificationTable").find("[id='"+key+"']").val(value);
   		    });
	   		initSpecificationsResult();
	   		
	   		//加载默认的图片
	   		mapImage.each(function(key,value,index){   
   		       	$("#specificationTable").find("[forname='"+key+"']").find("[name='specifications_image_result']").val(value);
   		    });
	   		initSpecificationsImageFirst();
		 	$("#btn_sku_add").show();
		 	
	   		$("#role_btn").hide()
	   	    $("div[id^='specificationDiv_']").hide();
	   		$("#self_role_btn").hide()
	   	    $("div[id^='self_specificationDiv_']").hide();
    	} else {
    		$("#specificationTable").html("");
    		$('.pro_rule_wrap').hide();
		 	$("#btn_sku_add").hide();
		 	
		 	if(kbn==2) {
    		 	$("#rdType2").attr("checked","checked");	
		   	    $("div[id^='specificationDiv_']").hide();
		   		$("#role_btn").hide()
		   		
		   	    $("div[id^='self_specificationDiv_']").show();
		   		$("#self_role_btn").show()
		   		
		 	} else {
    		 	$("#rdType0").attr("checked","checked");

		   		$("#self_role_btn").hide()
		   	    $("div[id^='self_specificationDiv_']").hide();
		   		
		   	    $("div[id^='specificationDiv_']").show();
		   		$("#role_btn").show();
		 	}
    	}
    }
    function addSpecificationRow() {
		var row="";
		rowCnt++;
		row+='<tr class="last_row">';
		row+='<td><div class="uploadimg"><img src="'+jsStaticResources+'images/uploadimg.jpg" width="87" height="50" alt="uploadimg"></div><div class="uploadbtn" forname="'+rowCnt+'" onclick="uploadDivdisplay(this);"><a href="javascript:void(0);">上传图片</a><input name="specifications_image_result" type="hidden" value=""/></div></td>';
		row+='<td style="position:relative">'+'<div class="del_role_btn" onclick="javasrciput:removeSpecificationRow(this);" onmouseover="delMouseover(this);" onmouseout="delMouseout(this);"><a href="javascript:void(0);">X 删除 </a></div>'
			+'<input type="hidden" name="color_id" value="'+rowCnt+'"/>'
			+'<input class="rule_input f108" type="text" name="color"  ismust="1"   typename="input"   maxLength="15" value="" onkeyup="clearNgText3(this)"></td>';
		row+='<td><input class="rule_input f108" type="text" name="barcode"  ismust="1"   typename="input"   maxLength="15" value=""  onkeyup="value=value.replace(/[^\\d|a-z|A-Z\-]/g,\'\');specificationsChange(this)"></td>';
		row+='<td><input class="rule_input f68" type="text" name="productprice" isnum="1" ismust="1"   typename="input"    maxLength="8" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');" onblur="specificationsChange(this);" value=""></td>';
		row+='<td><input class="rule_input f68" type="text" name="fproductprice" isnum="1" ismust="1"   typename="input"    maxLength="8" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');specificationsChange(this)"  value=""></td>';
		row+='<td><input class="rule_input f68" type="text" name="productnum" isnum="1" ismust="1"   typename="input"   maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\');specificationsChange(this)" value=""></td>';
		row+='<td style="display:none;"><input class="rule_input f68" type="text" name="warnnum" isnum="1" ismust="1"   typename="input"   maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\');specificationsChange(this)" value="1"></td>';
		row+='<td style="display:none;"><input type="hidden" name="maxFucoin" value=""><span name="lblMaxFucoin"></span><input type="hidden" id="'+rowCnt+'" name="specifications_result" value="'+rowCnt+'_____"/></td>';
		row+='<td><input class="rule_input f68" type="text" name="minLimitNum"  isnum="1" ismust="1" typename="input"   maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\');specificationsChange(this)" value="1"></td>';
		row+='</tr>';
		
		$("#specificationTable").append(row);

		refreshActivityQicaiRow();
    }
    function removeSpecificationRow(obj) {
    	$(obj).parent().parent().remove(); 
    }
    function removeSpecificationRow2(obj) {
    	var $tr = $(obj).parent().parent();
    	var td1 = $tr.find("[rowspan]");
    	if(td1.length>0) {
    		var $tr2 = $tr.next();
    		var $tds=$tr.children("td");
    		var $td2s=$tr2.children("td");
    		
    		for(var i=0;i<7;i++) {
    			if(i==0) {
        			$($tds[i+2]).html($($td2s[i]).html());
    			} else if(i==6) {
        			$($tds[i+2]).children("span").html($($td2s[i]).children("span").html());
    			}
    			
    			if(i>0) {
    				var ipt1s = $($tds[i+2]).children("input");
    				var ipt2s = $($td2s[i]).children("input");
    				for(var j=0;j<ipt1s.length;j++) {
    					$(ipt1s[j]).val($(ipt2s[j]).val());
    				}
    			}
    		}
    		var rowspan = $($tds[0]).prop("rowspan");
    		rowspan = rowspan-1;
    		$($tds[0]).prop("rowspan",rowspan);
    		$($tds[1]).prop("rowspan",rowspan);
    		$tr2.remove();
    		
    		if(rowspan==1) {
    			$($tds[8]).children("div").remove();
    		}
    	} else {
    		var $tr2=$tr;
    		while(true) {
    			$tr2 = $tr2.prev();
    	    	var td2 = $tr2.find("[rowspan]");
    	    	if(td2.length>0) {
    	    		break;
    	    	}
    		}
    		var $tds=$tr2.children("td");
    		var rowspan = $($tds[0]).prop("rowspan");
    		rowspan = rowspan-1;
    		$($tds[0]).prop("rowspan",rowspan);
    		$($tds[1]).prop("rowspan",rowspan);
    		$tr.remove();
    		
    		if(rowspan==1) {
    			$($tds[8]).children("div").remove();
    		}
    	}
    	
    	refreshActivityQicaiRow();
    }
	/**
	*生成sku  table
	*/
	function createspecificationlist(obj,strPrefix){
		//生成map
		$tableresultlist = $("#specificationTable").find("[name='specifications_result']");
		$tableresultlist.each(function(){
			if($(this).val()!=''){
				map.put($(this).attr("id"),$(this).val()); //这个map是用来生成一条新的sku时，显示之前sku信息（ id="白色,55" value="白色,55_4___9_0"）
			}
		});
		
		//生成map
		$tableimglist = $("#specificationTable").find("[name='specifications_image_result']");
		$tableimglist.each(function(){
			if($(this).val()!=''){
				mapImage.put($(this).parent().attr("forname"),$(this).val());
			}
		});
		    		
		var $list = $(obj).parents(".add_info").find("div[id^='"+strPrefix+"specificationDiv_']");
		var speDiv0="";
		var speDiv1="";
		var html = "";
		if($list.length>0&&$list.length<3){
    		if($list.length==1){//如果是一个规格
    			speDiv0=$list[0];
    			var id0 = $(speDiv0).attr("id").replace(strPrefix+'specificationDiv_','');//获取规格id
    			var name0 = $(speDiv0).attr("name"); //获取规格名称
        		var $list0 = $(speDiv0).find("[name='"+id0+"']:checked");//获取规格值列表
        		if($list0.length==0){
        			showInfoBox("规格：'"+name0+"'至少选择一项");
        		}else{
            		var html = "";
            		
                    html+='<tr>';
                    html+='<th class="Upload">上传图片</th>';
                    html+='<th class="Color">'+name0+'</th>';
                    html+='<th class="Barcode">条形码</th>';
                    html+='<th class="Price">电商价（元）</th>';
                    html+='<th class="Price">内购价（元）</th>';
                    html+='<th class="Inventory">库存</th>';
                    html+='<th style="display:none;" class="Value">库存预警值</th>';
                    html+='<th  style="display:none;"  class="Value">内购券</th>';	    
                    html+='<th class="Value">起售数量</th>';	
                    html+='</tr>';
                    html+='<tr>';
                    html+='<td>&nbsp;</td>';
                    html+='<td>&nbsp;</td>';
                    html+='<td><input class="rule_input f108" type="text" id="all_barcode"  maxLength="15" onkeyup="value=value.replace(/[^\\d|a-z|A-Z]/g,\'\');" value="" ><p style="width:90px;"><a onclick="copyTotheall(\'all_barcode\');"  href="javascript:void(0);">全部应用</a></p></td>';
                    html+='<td><input class="rule_input f68" type="text" id="productprice"  maxLength="8"  onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');" value="" ><p  style="width:90px;"><a onclick="copyTotheall(\'productprice\');"  href="javascript:void(0);">全部应用</a></p></td>';
                    html+='<td><input class="rule_input f68" type="text" id="fproductprice"  maxLength="8"  onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');" value="" ><p  style="width:90px;"><a onclick="copyTotheall(\'fproductprice\');"  href="javascript:void(0);">全部应用</a></p></td>';
                    html+='<td><input class="rule_input f68" type="text" id="productnum"  maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" value="" ><p  style="width:90px;"><a onclick="copyTotheall(\'productnum\');"  href="javascript:void(0);">全部应用</a></p></td>';
                    html+='<td  style="display:none;" ><input class="rule_input f68" type="text" id="warnnum"  maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" value="1"><p style="width:90px;"><a onclick="copyTotheall(\'warnnum\');"  href="javascript:void(0);">全部应用</a></p></td>';
                    html+='<td  style="display:none;" >&nbsp;</td>';      
                    html+='<td><input class="rule_input f68" type="text" id="minLimitNum"  maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" value="1"><p style="width:90px;"><a onclick="copyTotheall(\'minLimitNum\');"  href="javascript:void(0);">全部应用</a></p></td>';
                    html+='</tr>';
            		$list0.each(function(i,val){
            			html+='<tr>';
            			html+='<td><div class="uploadimg"><img src="'+jsStaticResources+'images/uploadimg.jpg" width="87" height="50" alt="uploadimg"></div><div class="uploadbtn" forname="'+$($list0[i]).next("span").text()+'" onclick="uploadDivdisplay(this);"><a href="javascript:void(0);">上传图片</a><input name="specifications_image_result" type="hidden" value=""/></div></td>';
            			html+='<td>'+$($list0[i]).next("span").text()+'</td>';
            			html+='<td><input class="rule_input f108" type="text" name="barcode"  ismust="1"   typename="input"   maxLength="15" value=""  onkeyup="value=value.replace(/[^\\d|a-z|A-Z\-]/g,\'\');specificationsChange(this)"></td>';
            			html+='<td><input class="rule_input f68" type="text" name="productprice" isnum="1" ismust="1"   typename="input"    maxLength="8" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');" onblur="specificationsChange(this)"  value=""></td>';
            			html+='<td><input class="rule_input f68" type="text" name="fproductprice" isnum="1" ismust="1"   typename="input"    maxLength="8" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');specificationsChange(this)"  value=""></td>';
            			html+='<td><input class="rule_input f68" type="text" name="productnum" isnum="1" ismust="1"   typename="input"   maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\');specificationsChange(this)" value=""></td>';
            			html+='<td style="display:none;"><input class="rule_input f68" type="text" name="warnnum" isnum="1" ismust="1"   typename="input"   maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\');specificationsChange(this)" value="1"></td>';
            			html+='<td style="display:none;"><input type="hidden" name="maxFucoin" value=""><span name="lblMaxFucoin"></span><input type="hidden" id="'+$($list0[i]).next("span").text()+'" name="specifications_result" value="'+$($list0[i]).next("span").text()+ '_____"/></td>';	  
            			html+='<td><input class="rule_input f68" type="text" name="minLimitNum" isnum="1" ismust="1"   typename="input"   maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\');specificationsChange(this)" value="1"></td>';
            			html+='</tr>';
            		});
            		$("#specificationTable").html(html);
            		$('.pro_rule_wrap').show();

        		}
    		}else if($list.length==2){//如果是二个规格
    			speDiv0=$list[0];
    			speDiv1=$list[1];
    			var id0 = $(speDiv0).attr("id").replace(strPrefix+'specificationDiv_','');
    			var id1 = $(speDiv1).attr("id").replace(strPrefix+'specificationDiv_','');
    			var name0 = $(speDiv0).attr("name");
    			var name1 = $(speDiv1).attr("name");
        		var $list0 = $(speDiv0).find("[name='"+id0+"']:checked");
        		var $list1 = $(speDiv1).find("[name='"+id1+"']:checked");
        		if($list0.length==0){
        			showInfoBox("规格：'"+name0+"'至少选择一项");
        			return false;
        		}
        		if($list1.length==0){
        			showInfoBox("规格：'"+name1+"'至少选择一项");
        			return false;
        		}else{
            		var list1length = $list1.length;
            		
					var html = "";
                    html+='<tr>';
                    html+='<th class="Upload">上传图片</th>';
                    html+='<th class="Color">'+name0+'</th>';
                    html+='<th class="Size">'+name1+'</th>';
                    html+='<th class="Barcode">条形码</th>';
                    html+='<th class="Price">电商价（元）</th>';
                    html+='<th class="Price">内购价（元）</th>';
                    html+='<th class="Inventory">库存</th>';
                    html+='<th  style="display:none;"  class="Value">库存预警值</th>';
                    html+='<th  style="display:none;"  class="Value">内购券</th>';	
                    html+='<th class="Value">起售数量</th>';	
                    html+='</tr>';
                    html+='<tr>';
                    html+='<td>&nbsp;</td>';
                    html+='<td>&nbsp;</td>';
                    html+='<td>&nbsp;</td>';
                    html+='<td><input class="rule_input f108" type="text" id="all_barcode"  maxLength="15" onkeyup="value=value.replace(/[^\\d|a-z|A-Z]/g,\'\');" value="" ><p style="width:90px;"><a onclick="copyTotheall(\'all_barcode\');"  href="javascript:void(0);">全部应用</a></p></td>';
                    html+='<td><input class="rule_input f68" type="text" id="productprice" maxLength="8"  onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,\'$1$2.$3\');"   value=""><p  style="width:90px;"><a onclick="copyTotheall(\'productprice\');"  href="javascript:void(0);">全部应用</a></p></td>';
                    html+='<td><input class="rule_input f68" type="text" id="fproductprice"  maxLength="8"  onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');" value="" ><p  style="width:90px;"><a onclick="copyTotheall(\'fproductprice\');"  href="javascript:void(0);">全部应用</a></p></td>';
                    html+='<td><input class="rule_input f68" type="text" id="productnum" maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" value=""><p style="width:90px;"><a onclick="copyTotheall(\'productnum\');"  href="javascript:void(0);">全部应用</a></p></td>';
                    html+='<td  style="display:none;" ><input class="rule_input f68" type="text" id="warnnum" maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" value=""><p style="width:90px;"><a onclick="copyTotheall(\'warnnum\');"  href="javascript:void(0);">全部应用</a></p></td>';
                    html+='<td  style="display:none;" >&nbsp;</td>';	 
                    html+='<td><input class="rule_input f68" type="text" id="minLimitNum"  maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" value="1"><p style="width:90px;"><a onclick="copyTotheall(\'minLimitNum\');"  href="javascript:void(0);">全部应用</a></p></td>';
                    html+='</tr>';
            		$list0.each(function(i,vali){
            			html+='<tr class="last_row">';
            			html+='<td rowspan="'+list1length+'"><div class="uploadimg"><img src="'+jsStaticResources+'images/uploadimg.jpg" width="87" height="50" alt="uploadimg"></div><div class="uploadbtn" forname="'+$($list0[i]).next("span").text()+'" onclick="uploadDivdisplay(this);"><a href="javascript:void(0);" >上传图片</a><input name="specifications_image_result" type="hidden" value=""/></div></td>';
            			html+='<td rowspan="'+list1length+'">'+$($list0[i]).next("span").text()+'</td>';
            			$list1.each(function(j,valj){
            				html+='<td>'+$($list1[j]).next("span").text()+'</td>';
                			html+='<td><input class="rule_input f108" type="text" name="barcode"  ismust="1"   typename="input" maxLength="15" value=""  onkeyup="value=value.replace(/[^\\d|a-z|A-Z\-]/g,\'\');specificationsChange(this)"></td>';
                			html+='<td><input class="rule_input f68" type="text" name="productprice" isnum="1" ismust="1"   typename="input" maxLength="8" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');" onblur="specificationsChange(this);" value=""></td>';
                			html+='<td><input class="rule_input f68" type="text" name="fproductprice" isnum="1" ismust="1"   typename="input" maxLength="8" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');specificationsChange(this)" value=""></td>';
                			html+='<td><input class="rule_input f68" type="text" name="productnum"  isnum="1" ismust="1"   typename="input" maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\');specificationsChange(this)" value=""></td>';
                			html+='<td style="display:none;"><input class="rule_input f68" type="text" name="warnnum" isnum="1" ismust="1"   typename="input" maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\');specificationsChange(this)" value="1"></td>';
                			html+='<td style="display:none;position: relative"><input type="hidden" name="maxFucoin" value=""><span name="lblMaxFucoin"></span><input type="hidden" id="'+$($list0[i]).next("span").text()+","+$($list1[j]).next("span").text()+'" name="specifications_result" value="'+$($list0[i]).next("span").text()+","+$($list1[j]).next("span").text()+ '_____"/>';    			
            				html+='</td>';
            				html+='<td>';
            				if(list1length>1) {
            					html += ' <div class=""  onmouseover="delMouseover(this);" onmouseout="delMouseout(this);" onclick="javasrciput:removeSpecificationRow2(this);">';
            					html += '  <a href="javascript:void(0);">X 断码</a>';
            					html += ' </div>';
            				}
            				html+='<input class="rule_input f68" type="text" name="minLimitNum" isnum="1" ismust="1"   typename="input" maxLength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\');specificationsChange(this)" value="1"></td>';
                			if(j<list1length-1){
                				html+='</tr>';
                				html+='<tr>';
                			}
            			})
            			html+='</tr>';
            		});
            		
            		$("#specificationTable").html(html);
            		$('.pro_rule_wrap').show();	
            		$('.pro_rule_wrap .add_new').hide();
            		
        		}
    		}
		} else {
			showInfoBox("该品类暂时没有标准规格。请联系客服人员，或选择简略SKU模式。");
			return false;
		}
		specificationChanageAll();//保存选中的规格值
	
		var specification_results=$("[name="+strPrefix+"specification_result]");//获取规格id_规格值列表
		if(specification_results.length > 0) { //一个规格
    		$("#bak_kingaku1select").val($(specification_results[0]).val());//为生成skutable的bak_kingaku1select赋值bak_kingaku1select  1711172955047404_白色,金色,绿色
		} else {
    		$("#bak_kingaku1select").val("");
		}
		if(specification_results.length > 1) { //二个规格
    		$("#bak_kingaku2select").val($(specification_results[1]).val());
		} else {
    		$("#bak_kingaku2select").val("");
		}
		//加载默认的sku信息
	    map.each(function(key,value,index){
	       	$("#specificationTable").find("[id='"+key+"']").val(value); //获取之前sku的数据
	    });
   		initSpecificationsResult();
   		
   		//加载默认的图片
   		mapImage.each(function(key,value,index){   
	       	$("#specificationTable").find("[forname='"+key+"']").find("[name='specifications_image_result']").val(value);
	    });
   		initSpecificationsImageFirst();
   		
   		refreshActivityQicaiRow();
   		
	}
	
	/**
	*第二次生成sku后则默认加载第一次的sku信息
	*/
	function initSpecificationsResult(){
		$tableresultlist = $("#specificationTable").find("[name='specifications_result']");
		$tableresultlist.each(function(){
			if($(this).val()!=''){
				var values = $(this).val().split("_"); //value="白色,55_4___9_0"
				if(values[4] == "" || values[4] == null){
					values[4] =1;
				}
				$(this).parent().parent().find("[name='barcode']").val(values[1]).end().find("[name='productprice']").val(values[2]).end().find("[name='productnum']").val(values[3]).end().find("[name='warnnum']").val(values[4]).end().find("[name='maxFucoin']").val(values[5]).end().find("[name='lblMaxFucoin']").html(values[5]);
				if(values[2] != '' && values[5]!='' ){
					var productprice = parseFloat(values[2]);
					var maxFucoin = parseFloat(values[5]);
					$(this).parent().parent().find("[name='fproductprice']").val((productprice-maxFucoin).toFixed(2));
				}
			}
		});
		
	}
	/**
	*全部应用
	*/
	function copyTotheall(id){
		var val = $("#"+id).val();
		if("all_barcode"==id) id="barcode";
		$("#specificationTable [name='"+id+"']").val(val);
		//生成后台需要的数据
		specificationsAllChange();
	}
	
	/**
	*skuchange
	*/
	function specificationsChange(obj){
		$(obj).val($.trim($(obj).val()));
		$(obj).removeClass("bctxt");
		
		var $tr =$(obj).parents("tr");
		var color = $tr.find("[name=color]").val();
		var barcode = $tr.find("[name=barcode]").val();
		var productprice = $tr.find("[name=productprice]").val();
		var productnum = $tr.find("[name=productnum]").val();
		var warnnum = $tr.find("[name=warnnum]").val();
		var fproductprice = $tr.find("[name=fproductprice]").val();
		var minLimitNum = $tr.find("[name=minLimitNum]").val();//起购数量
		var maxFucoin = 0 //$tr.find("[name=maxFucoin]").val();
		if(productprice!=''&&fproductprice!=''){
			if(parseFloat(fproductprice)>parseFloat(productprice)){
				$tr.find("[name=fproductprice]").val(productprice);
				fproductprice = parseFloat(productprice);
				maxFucoin = 0;
				$tr.find("[name=maxFucoin]").val(0);
			} else {
				maxFucoin = parseFloat(productprice)-parseFloat(fproductprice);
				maxFucoin = maxFucoin.toFixed(2);
				$tr.find("[name=maxFucoin]").val(maxFucoin);
				$tr.find("[name=lblMaxFucoin]").html(maxFucoin);
			}
		}
		
		//if(barcode!=""&&productprice!=''&&productnum!=''&&warnnum!=''&&maxFucoin!=''){
			var names = $tr.find("[name=specifications_result]").attr("id");
			$tr.find("[name=specifications_result]").val(names+"_"+barcode+"_"+productprice+"_"+productnum+"_"+warnnum+"_"+maxFucoin + "_" + fproductprice + "_" + minLimitNum);
		/*}else{
			$tr.find("[name=specifications_result]").val("");
		}*/
		
		calDiv();
		calTrial();
	}
	
	
	/**
	*skuchange
	*/
	function specificationsAllChange(){
		var $trlist =$("#specificationTable tr");
		if($trlist!=null&&$trlist!=''&&$trlist.length>0){
			$trlist.each(function(i,val){
				var $tr =$($trlist[i]);
				var barcode = $tr.find("[name=barcode]").val();
	    		var productprice = $tr.find("[name=productprice]").val();
	    		var productnum = $tr.find("[name=productnum]").val();
	    		var warnnum = $tr.find("[name=warnnum]").val();
	    		var fproductprice = $tr.find("[name=fproductprice]").val();
	    		var minLimitNum = $tr.find("[name=minLimitNum]").val();//起购数量
	    		var maxFucoin = 0 //$tr.find("[name=maxFucoin]").val();
	    		if(productprice!=''&&fproductprice!=''){
	    			if(parseFloat(fproductprice)>parseFloat(productprice)){
	    				$tr.find("[name=fproductprice]").val(productprice);
	    				fproductprice = parseFloat(productprice);
	    				maxFucoin = 0;
	    			} else {
	    				maxFucoin = parseFloat(productprice)-parseFloat(fproductprice);
	    				maxFucoin = maxFucoin.toFixed(2);
	    				$tr.find("[name=maxFucoin]").val(maxFucoin);
	    				$tr.find("[name=lblMaxFucoin]").html(maxFucoin);
	    			}
	    		}
	    		
	    		//if(barcode!=""&&productprice!=''&&productnum!=''&&warnnum!=''&&maxFucoin!=''){
	    			var names = $tr.find("[name=specifications_result]").attr("id");
	    			$tr.find("[name=specifications_result]").val(names+"_"+barcode+"_"+productprice+"_"+productnum+"_"+warnnum+"_"+maxFucoin + "_" + fproductprice + "_" + minLimitNum);
	    		/*}else{
	    			$tr.find("[name=specifications_result]").val("");
	    		}*/
			});
		}
	}
	
	/**
	*隐藏域中放置UEditor商品介绍的内容
	*/
	function setIntroduction(){
		$("#introduction").val(UE.getEditor('editor').getContent());
	}
	
	function thisWordnum(obj){
		clearNgText2(obj);
		var val = $(obj).val();
		var maxnum = $(obj).attr("maxLength");
		var length = val.length;
		var num =maxnum-length;
		if(num<=0){
			num=0;
		}
		$(obj).parent(".add_model").find("[name=wordnum]").text(num);
	}
	/**
	 * js清除系统禁止文字
	 * 禁止文字包含 ` , $ & = | { } [ ] ' :  \ " < > 
	 */
	function clearNgText2(obj) {
		var val = obj.value;
		val = val.replace("`","").replace(",","")
			.replace("$","").replace("&","").replace("=","").replace("|","")
			.replace("{","").replace("}","").replace("[","").replace("]","")
			.replace("'","").replace(":","").replace("\\","")
			.replace("\"","").replace("<","").replace(">","");
		obj.value = val;
	}
	/**
	 * 简略sku规格输入规则，允许输入点号
	 * @param obj
	 */
	function clearNgText3(obj) {
		var val = obj.value;
		val = val.replace("`","").replace(",","").replace("#","")
			.replace("$","").replace("&","").replace("=","").replace("|","")
			.replace("{","").replace("}","").replace("[","").replace("]","")
			.replace("'","").replace(":","").replace("\\","")
			.replace("\"","").replace("<","").replace(">","");
		obj.value = val;
	}
	
	
	function  uploadDivdisplay(obj){
		//var	$resultlist = $(obj).parents("tr").find("[name=specifications_result]:first");
		var $imageresult = $(obj).find("[name='specifications_image_result']");
		var urllist = "";
		var imghtml="";
		
		if($imageresult.val()!=""){
			urllist = $imageresult.val().split("_")[1].split(",");
		}
		for(var i=0;i<5;i++){
			if(i<urllist.length){
				imghtml +="<li name='li_"+i+"'><img src=\""+urllist[i]+"\" style=\"width:100%;height:100%\" name='loadimage'></li>";
			}else{
				imghtml +="<li name='li_"+i+"'><div>主图<br>800*800</div></li>";
			}
		}
		
		$(".uploadimg_list>ul").html(imghtml);
		$(".uploadimg_box").show();
		$(".popup_bg_new").show();
		var forname = $(obj).attr("forname");
		$(".uploadimg_box").find("[name='forname']").val(forname);
		
		 initDeltetClose();
	}
	
	/**
	*确定选择图片
	*/
	function submitImgs(obj){
		var forname = $(obj).parent().find("[name='forname']").val();
		var $specifications_image = $("[forname='"+forname+"']").find("[name='specifications_image_result']");
		var $list = $(".uploadimg_list").find("ul>li>img[name='loadimage']");
		if($list.length==0){
			$("#errorUploadSpan").text("至少上传一张主图").css("display","inline");
		}else{
    		var images="";
    		for(var i=0;i<5;i++) {
    			images=images+$list.eq(i%$list.length).attr("src")+",";
    		}
    		if(images!=''){
    			
    			$("[forname='"+forname+"']").prev(".uploadimg").children("img").attr("src",$list.eq(0).attr("src"));
    			images = images.substring(0,images.length-1);
    			$("[forname='"+forname+"']").find("[name='specifications_image_result']").val(forname+"_"+images);
    		}
    		
    		$(".uploadimg_box").hide();
    		$(".popup_bg_new").hide();
		}
	}
	
	function uploadingClose(){
		$(".uploadimg_box").hide();
		$(".popup_bg_new").hide();
	}
	
	function pcOrmobileShow(i){
		if(i==1){
			$("#pc_content_div").show();
			$("#mobile_content_div").hide();
			$("#pc_content_li").removeClass("active").addClass("active");
			$("#mobile_content_li").removeClass("active");
		}else{
			$("#pc_content_div").hide();
			$("#mobile_content_div").show();
			$("#mobile_content_li").removeClass("active").addClass("active");
			$("#pc_content_li").removeClass("active");
		}
	}
	
	/**
	*	内容复制
	*/
	function copyPctomobile(){
		var content = UE.getEditor('editor').getContent();
		$("#mobileContentDiv").html(content);
	}
	
	/*添加sku 删除 背景变色*/
	function delMouseover(obj){
		$(obj).parent().parent().css("background","#fdfbfb");
	}
	
	function delMouseout(obj){
		$(obj).parent().parent().css("background","#fff");
	}

	/**
	*弹出层修改规格
	**/

	/**
	*弹出层修改规格
	**/
	var kingakuImgCnt=3;
	function ajaxUpdateSpecification(){
		$(".popup_bg").show();
		$("#shop_popup").show();
		$("#divLoading").show();
		$("#divkingaku").hide();
		var err=$("#ajaxErrMsg").html("");
		
		var basePath = jsBasePath;
		$.ajax({
			url : basePath +'/product/ajaxGetSelfSpecification.json?supplierId='+jsSupplierId+'&categoryId='+jsCategoryId+'&productId='+$("#productid").val(),
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: true,
			success : function(data) {
				var html='';

				if (data.result.errorCode == 0) {
					var supplierSpecificationlist = data.result.msgBody;

					//规格1
					if (supplierSpecificationlist.length > 0) {
						$("#kingaku1").val(supplierSpecificationlist[0].name);
						var blnImg = false;

						var html1 = "";
						for (var i = 0; i < supplierSpecificationlist[0].valuelist.length; i++) {
							var imgUrl="";
							if(supplierSpecificationlist[0].valuelist[i].image
									&& supplierSpecificationlist[0].valuelist[i].image != "") {
								imgUrl = supplierSpecificationlist[0].valuelist[i].image;
							}
							
							var readonly="";
							html1 += '<div class="add_row">';
							if(supplierSpecificationlist[0].valuelist[i].usedCont>0) {
								html1 += '<div><a href="javascript:void(0);" class="del_rule ft_lt">&nbsp;&nbsp;</a></div>';
								readonly=" readonly";
								$("#kingaku2chk").hide();
								$("#kingaku2chk").attr("readonly","readonly");
								$("#kingaku1").attr("readonly","readonly");
								
							} else {
								html1 += '<div onclick="javascript:delKValue(this);"><a href="javascript:void(0);" class="del_rule ft_lt" title="删除">X</a></div>';								
							}
							html1 += '<input type="hidden" name="valuelist1id" value="'+ supplierSpecificationlist[0].valuelist[i].id +'" />';
							html1 += '<input type="hidden" name="valuelist1img" value="'+ imgUrl +'" />';
							html1 += '<input type="text" value="'+ supplierSpecificationlist[0].valuelist[i].name +'" class="rule_input f108" name="kingaku1VLname"  onkeyup="clearNgText2(this);" onfocus="clearErr(this);"'+readonly+' />';
							if (imgUrl != "") {
								html1 += '<div class="img_png40" id="valuelist1img_'+ (kingakuImgCnt++) +'"  onclick="uploadKingkuImg(this.id)"><img src="'+imgUrl+ '" style="width:40px;height:40px"/></div>';
								blnImg = true;
							} else {
								html1 += '<div class="img_png40" style="display: none;" id="valuelist1img_'+ (kingakuImgCnt++) +'" onclick="uploadKingkuImg(this.id)"><img src="'+jsStaticResources+'images/img_png40.png" /></div>';
							}
							html1 += '</div>\n';
						}
						if(blnImg){
							$("#chkImg1").val("1");
							$("#chkImgA1").html("－图片");
						}
					
						$("#kingaku1Vls").html(html1);
					}

					//规格2
					if (supplierSpecificationlist.length == 2) {
						
						$("#kingaku2chk").attr("checked","checked");
						$("#kingaku2").show();
						$("#kingaku2ImgChk").show();
						$("#kingaku2VlTitile").show();
						$("#kingaku2Vls").show();
						$("#kingaku2").val(supplierSpecificationlist[1].name);
													
						var blnImg = false;
						var html2 = "";
						for (var i = 0; i < supplierSpecificationlist[1].valuelist.length; i++) {
							var imgUrl="";
							if(supplierSpecificationlist[1].valuelist[i].image
									&& supplierSpecificationlist[1].valuelist[i].image != "") {
								imgUrl = supplierSpecificationlist[1].valuelist[i].image;
							}

							var readonly="";
							html2 += '<div class="add_row">';
							if(supplierSpecificationlist[1].valuelist[i].usedCont>0) {
								html2 += '<div><a href="javascript:void(0);" class="del_rule ft_lt">&nbsp;&nbsp;</a></div>';	
								readonly=" readonly";
								$("#kingaku2chk").attr("readonly","readonly");
								$("#kingaku2chk").hide();
								
								$("#kingaku2").attr("readonly","readonly");
							} else {
								html2 += '<div onclick="javascript:delKValue(this);"><a href="javascript:void(0)" class="del_rule ft_lt" title="删除">X</a></div>';
							}
							
							html2 += '<input type="hidden" name="valuelist2id" value="'+ supplierSpecificationlist[1].valuelist[i].id +'" />';
							html2 += '<input type="hidden" name="valuelist2img" value="'+ imgUrl +'" />';
							html2 += '<input type="text" value="'+ supplierSpecificationlist[1].valuelist[i].name +'" class="rule_input f108" name="kingaku2VLname" onkeyup="clearNgText2(this);" onfocus="clearErr(this);"'+readonly+' />';
							if (imgUrl != "") {
								html2 += '<div class="img_png40" id="valuelist2img_'+ (kingakuImgCnt++) +'" onclick="uploadKingkuImg(this.id)"><img src="'+imgUrl+ '" style="width:40px;height:40px"/></div>';
								blnImg = true;
							} else {
								html2 += '<div class="img_png40" style="display: none;" id="valuelist2img_'+ (kingakuImgCnt++) +'" onclick="uploadKingkuImg(this.id)"><img src="'+jsStaticResources+'images/img_png40.png" /></div>';
							}
							html2 += '</div>\n';
						}

						if(blnImg){
							$("#chkImg2").val("1");
							$("#chkImgA2").html("－图片");
						}

						$("#kingaku2Vls").html(html2);
					} else {
						
						$("#kingaku2chk").removeAttr("checked");
						$("#kingaku2").hide();
						$("#kingaku2ImgChk").hide();
						$("#kingaku2VlTitile").hide();
						$("#kingaku2Vls").hide();
						$("#kingaku2").val("尺寸");
						
						var html2 = "";
						html2 += '<div class="add_row">';
						html2 += '<div onclick="javascript:delKValue(this);"><a href="javascript:void(0)" class="del_rule ft_lt" title="删除">X</a></div>';
						html2 += '<input type="text" value="" class="rule_input f108" name="kingaku2VLname" onkeyup="clearNgText2(this);" onfocus="clearErr(this);" />';
						html2 += '<input type="hidden" name="valuelist2id" />';
						html2 += '<input type="hidden" name="valuelist2img" />';
						html2 += '<div class="img_png40" style="display: none;" id="valuelist2img_1"  onclick="uploadKingkuImg(this.id)"><img src="'+jsStaticResources+'images/img_png40.png"/></div>';
						html2 += '</div>';
				
						$("#kingaku2Vls").html(html2);
					}
				}

	    		$("#divLoading").hide();
	    		$("#divkingaku").show();
			},
			error : function() {}
		});
	}
	

	//需要图片切换
	function changeImg(objId,hidId,kbn) {
		var hid = $("#"+hidId);
		if(hid.val() == "1") {
			hid.val("0");
			$("#kingaku"+kbn+"Vls").find(".img_png40").hide();
			$("#kingaku"+kbn+"Vls").find("[name=valuelist" + kbn+"img").val("");
			$("#"+objId).html("＋图片");
		} else {
			hid.val("1");
			$("#kingaku"+kbn+"Vls").find(".img_png40").show();
			$("#"+objId).html("－图片");
		}
	}
	
	//需要图片切换
	function kingaku2Change(obj) {
		if(obj.checked) {
			$("#kingaku2").show();
			$("#kingaku2ImgChk").show();
			$("#kingaku2VlTitile").show();
			$("#kingaku2Vls").show();
		} else {
			$("#kingaku2").hide();
			$("#kingaku2ImgChk").hide();
			$("#kingaku2VlTitile").hide();
			$("#kingaku2Vls").hide();
		}
	}
	
	//添加规格
	function addKValue(kbn) {

		var html1="";								
		html1 += '<div class="add_row">';
		html1 += '<div onclick="javascript:delKValue(this);"><a href="javascript:void(0);" class="del_rule ft_lt" title="删除">X</a></div>';
		html1 += '<input type="hidden" name="valuelist' + kbn+'id" />';
		html1 += '<input type="hidden" name="valuelist' + kbn+'img" />';
		html1 += '<input type="text" class="rule_input f108" name="kingaku' + kbn+'VLname"  onkeyup="clearNgText2(this);" onfocus="clearErr(this);" />';
		
		var disp="";
		if($("#chkImg" + kbn).val()=="1") {
			disp="";
		} else {
			disp=' style="display: none;"';
		}
		html1 += '<div class="img_png40"'+disp+' id="valuelist'+kbn+'img_'+(kingakuImgCnt++)+'" onclick="uploadKingkuImg(this.id)"><img src="'+jsStaticResources+'images/img_png40.png" /></div>';
		html1 += '</div>\n';
		$("#kingaku"+kbn+"Vls").append(html1);
	}

	
	//添加规格
	function delKValue(obj) {
		$(obj).parent().remove();
	}
	
	function clearErr(obj) {
		$(obj).removeClass("bctxt");
	}
	
	function uploadKingkuImg(objId) {
		$("#uploadingJqSelecter").val(objId);
		$("#uploadFile").click();
	}
	/**
	*submit
	*/
	function kingakuSubmit(){
		var err=$("#ajaxErrMsg");
		err.html("");
		var num = 0,ret=0,cnt=0;
		var colors=",";
		//输入检查
		var $list = $("#kingaku1Vls").find("[name=kingaku1VLname]");
		$list.each(function(){
			if($(this).val()==''){
				$(this).addClass("bctxt");
				num++
			} else {
				cnt++;
				if(colors.indexOf(","+$(this).val()+",") > -1){
   					$(this).addClass("bctxt");
   					ret++;
   				} else {
   					colors+=$(this).val()+",";
   				}
			}
		});

		if(num > 0) {err.html("请补充完整红色区域后，再次提交！"); return;}
		if(ret > 0) {err.html("规格不能重复，请修改红色区域之后，再次提交！"); return;}

		//图片检查
		if($("#chkImg1").val()=="1") {
			
			var $list2 = $("#kingaku1Vls").find("[name=valuelist1img]");
			num=0;
			$list2.each(function(){
				if($(this).val()==''){
					num++
				}
			});
			if(num>0) {err.html("请上传全部图片后，再次提交！"); return;}
		}

		if(cnt == 0) {err.html("规格值至少需要一个,请添加后,再次提交！"); return;}
		
		//规格2检查
		if($("#kingaku2chk").prop("checked")) {
			//输入检查
			var $list = $("#kingaku2Vls").find("[name=kingaku2VLname]");
			
			if($list.length==0) {err.html("请规格2添加规格值后，再次提交！"); return;}
			
			$list.each(function(){
				if($(this).val()==''){
					$(this).addClass("bctxt");
					num++
				} else {
					cnt++;
					if(colors.indexOf(","+$(this).val()+",") > -1){
	   					$(this).addClass("bctxt");
	   					ret++;
	   				} else {
	   					colors+=$(this).val()+",";
	   				}
				}
			});

			if(num > 0) {err.html("请补充完整红色区域后，再次提交！"); return;}
			if(ret > 0) {err.html("规格不能重复，请修改红色区域之后，再次提交！"); return;}

			//图片检查
			if($("#chkImg2").val()=="1") {
				var $list2 = $("#kingaku2Vls").find("[name=valuelist2img]");
				$list2.each(function(){
					if($(this).val()==''){
						num++
					}
				});
				if(num > 0) {err.html("请上传全部图片后，再次提交！"); return;}
			}

			if(cnt == 0) {err.html("规格值至少需要一个,请添加后,再次提交！"); return;}
		}
		$.ajax({
			url : jsBasePath+'/product/ajaxSaveSelfSpecification.json?supplierId='+jsSupplierId+'&categoryId='+jsCategoryId,
			type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: true,
		    data:$("#kingaku_form").serialize(),
			success : function(data) {
				if(data.length >0) {
					var html="";
					var itmValue1="";
					if($("#"+data[0].id).length >0) {
						itmValue1=$("#"+data[0].id).val();
					}
					
					var itmValue2="";
					if(data.length >1) {
						if($("#"+data[1].id).length >0) {
							itmValue2=$("#"+data[1].id).val();	
						}
					}
					
					$("[id^='self_specificationDiv_']").remove();
					
					html+='<div class="color_choose" id="self_specificationDiv_'+data[0].id+'" name="'+data[0].name+'">';
					html+='<div class="c_title"><b class="out">*</b>'+data[0].name+':</div>';
					html+='<div class="c_option">';
					for(var i=0;i<data[0].valuelist.length;i++){
						html+='<div class="colorlie">';
						html+='<input id="'+data[0].valuelist[i].name+'" class="check_input" type="checkbox" value="'+data[0].valuelist[i].name+'" name="'+data[0].id+'" onclick="specificationChanage(this);">';
						html+='<span name="editSpan" value="'+data[0].valuelist[i].name+'" contenteditable="true" onblur="specificationChanage($(this).prev());">'+data[0].valuelist[i].name+'</span>';
						html+='</div>';
					}
					if(itmValue1 == "") {
						itmValue1 = data[0].id+"_";
					}
					html+='<input type="hidden" id="'+data[0].id+'" ismust="0" name="self_specification_result" value="'+itmValue1+'"/><br/>';
					html+='</div>';
					html+='</div>';

					if(data.length >1) {
						jqDivId2 = "#self_specificationDiv_"+data[1].id;
						html+='<div class="color_choose" id="self_specificationDiv_'+data[1].id+'" name="'+data[1].name+'">';
						html+='<div class="c_title"><b class="out">*</b>'+data[1].name+':</div>';
						html+='<div class="c_option">';
						for(var i=0;i<data[1].valuelist.length;i++){
							html+='<div class="colorlie">';
							html+='<input id="'+data[1].valuelist[i].name+'" class="check_input" type="checkbox" value="'+data[1].valuelist[i].name+'" name="'+data[1].id+'" onclick="specificationChanage(this);">';
							html+='<span name="editSpan" value="'+data[1].valuelist[i].name+'" contenteditable="true" onblur="specificationChanage($(this).prev());">'+data[1].valuelist[i].name+'</span>';
							html+='</div>';
						}
						
						if(itmValue2 == "") {
							itmValue2 = data[1].id+"_";
						}
						html+='<input type="hidden" id="'+data[1].id+'" ismust="0" name="self_specification_result" value="'+itmValue2+'"/><br/>';
						html+='</div>';
						html+='</div>';
					}
					$("#rdType2").parent().after(html);
					initSpecification();
					if(itmValue1 != "") {
						createspecificationlist($("#selfCreatebtn"), 'self_');
					}
					hiddenObjById('shop_popup');
				}
				
			}, error : function() {
		    }  
		});
	}
	
	/**
	 * 运费选择模式变化
	 * @param kbn
	 */
	function rdFreightTypeClick(kbn) {
		switch(kbn) {
		case 0:
			$("#divFreight").hide();
			$("#carriage").val("0");
			break;
		case 1:
			$("#divFreight").show();
			if($("#carriage").val() != 0) {
				$("#rdFreightType3").attr("checked","checked");
				$("#shippingTemplateId").hide();
			} else {
				$("#rdFreightType2").attr("checked","checked");
				$("#shippingTemplateId").show();
			}
			break;
		case 2:
			$("#shippingTemplateId").show();
			$("#carriage").val("0");
			break;
		default:
			$("#shippingTemplateId").hide();
		}
		if(kbn==2) {
			setWBmust($("#shippingTemplateId").val());
		} else {
			setWBmust("");
		}
	}
	
	function templateChange(obj) {
		if(obj.value == "*") {
			window.open(jsBasePath+'/shippingAddress/template_edit.html');
		} else {
			setWBmust(obj.value);
		}
	}
	
	function questionnaireChange(obj) {
		//当试用并没有返现金额不允许选择调查问卷
		var trialPrice = $("#trialPrice").val();
		var saleKbnCheck = $("#saleKbn5").attr("checked"); 
		if(saleKbnCheck && trialPrice &&  Number(trialPrice) == 0){
			$("#questionnaireId").val("-1");
			showInfoBox("当评价后返现金额等于0时,不能选择调查问卷");
		}else{
			initq();
		}
	}
	
	function checkTrialPriceAndSale(){
		//当试用并没有返现金额不允许选择调查问卷
		var trialPrice = $("#trialPrice").val();
		var saleKbnCheck = $("#saleKbn5").attr("checked"); 
		if(saleKbnCheck && trialPrice &&  Number(trialPrice) == 0 && $("#questionnaireId").val() != "-1"){
			$("#questionnaireId").val("-1");
			showInfoBox("当评价后返现金额等于0时,不能选择调查问卷");
			return true;
		}
	}
	
	function selSaleKbn(kbn) {
		if(kbn==1) {
			$("#saleKbn2").removeAttr("checked");
			$("#saleKbn4").removeAttr("checked");
			$("#saleKbn5").removeAttr("checked");
			$("#saleKbn50").removeAttr("checked");
		} else if(kbn==2) {
			$("#saleKbn").removeAttr("checked");
			$("#saleKbn4").removeAttr("checked");
			$("#saleKbn5").removeAttr("checked");
			$("#saleKbn50").removeAttr("checked");
		} else if(kbn==4) {
			$("#saleKbn").removeAttr("checked");
			$("#saleKbn2").removeAttr("checked");
			$("#saleKbn5").removeAttr("checked");
			$("#saleKbn50").removeAttr("checked");
		} else if(kbn==5) {
			$("#saleKbn").removeAttr("checked");
			$("#saleKbn2").removeAttr("checked");
			$("#saleKbn4").removeAttr("checked");
			$("#saleKbn50").removeAttr("checked");
		} else if(kbn==50) {
			$("#saleKbn").removeAttr("checked");
			$("#saleKbn2").removeAttr("checked");
			$("#saleKbn4").removeAttr("checked");
			$("#saleKbn5").removeAttr("checked");
		}

		if($("#saleKbn2").prop("checked")) {
			$("#divDiv").show();//处理的是员工范围
			$("#divLimit").show();//展示换领期限

			$("#limit"+ limitType).prop("checked",true);
			//calDiv();//处理员工范围平均数
		} else {
			$("#divDiv").hide();
			$("#divLimit").hide();
		}
		
		if($("#saleKbn4").prop("checked")) {
			$("#empDiv").show();
			$("#empPriceDiv").show();
			$("#empCntDiv").show();
			$("#saleNote").val("");//理由清空
		} else {
			$("#empDiv").hide();
			$("#empPriceDiv").hide();
			$("#empCntDiv").hide();
		}
		
		if($("#saleKbn5").prop("checked")) {
			$("#trialPriceDiv").show();
			calTrial();
		} else {
			$("#trialPriceDiv").hide();
		}
				
		if($("#saleKbn50").prop("checked")) {
			$("#trialPriceBefore").show();
		} else {
			$("#trialPriceBefore").hide();
		}

		if($("#saleKbn").prop("checked") || $("#saleKbn2").prop("checked")) {
			if($("#saleKbn").prop("checked")) {
				$("#saleNoteDiv .name").html('<b class="out">&nbsp;</b>特省理由：');
			} else {
				$("#saleNoteDiv .name").html('<b class="out">&nbsp;</b>换领理由：');
			}
			
			$("#saleNoteDiv").show();
		} else {
			$("#saleNoteDiv").hide();
		}

		if($("#saleKbn5").prop("checked") || $("#saleKbn50").prop("checked")) {
			$("#trialTemplateDiv").show();
		} else {
			$("#trialTemplateDiv").hide();
		}
	}
	
	function setWBmust(val) {
		var type = "";
		var index = val.indexOf(",")
		if(index>-1) {
			type=val.substring(index+1);
		}
		$("#length").removeClass("bctxt");
		$("#width").removeClass("bctxt");
		$("#height").removeClass("bctxt");
		$("#bulk").removeClass("bctxt");
		$("#roughWeight").removeClass("bctxt");
		$("#netWeight").removeClass("bctxt");
		
		if(type=='2') {
			//按重量
			$("#roughWeight").attr("ismust","1");
			$("#roughWeight").prev().children(".out").html("*");
			$("#netWeight").attr("ismust","1");
			$("#netWeight").prev().children(".out").html("*");

			$("#length").removeAttr("ismust");
			$("#length").prev().children(".out").html("");
			$("#width").removeAttr("ismust");
			$("#width").prev().children(".out").html("");
			$("#height").removeAttr("ismust");
			$("#height").prev().children(".out").html("");
			$("#bulk").removeAttr("ismust");
			$("#bulk").prev().children(".out").html("");

            $(".tg a").html("-运费相关信息");
            $(".add_after_tg").show();
            
		} else if(type=='3') {
			//按体积
			$("#length").attr("ismust","1");
			$("#length").prev().children(".out").html("*");
			$("#width").attr("ismust","1");
			$("#width").prev().children(".out").html("*");
			$("#height").attr("ismust","1");
			$("#height").prev().children(".out").html("*");
			$("#bulk").attr("ismust","1");
			$("#bulk").prev().children(".out").html("*");

			$("#roughWeight").removeAttr("ismust");
			$("#roughWeight").prev().children(".out").html("");
			$("#netWeight").removeAttr("ismust");
			$("#netWeight").prev().children(".out").html("");

            $(".tg a").html("-运费相关信息");
            $(".add_after_tg").show();
		} else {
			//其他
			$("#length").removeAttr("ismust");
			$("#length").prev().children(".out").html("");
			$("#width").removeAttr("ismust");
			$("#width").prev().children(".out").html("");
			$("#height").removeAttr("ismust");
			$("#height").prev().children(".out").html("");
			$("#bulk").removeAttr("ismust");
			$("#bulk").prev().children(".out").html("");
			$("#roughWeight").removeAttr("ismust");
			$("#roughWeight").prev().children(".out").html("");
			$("#netWeight").removeAttr("ismust");
			$("#netWeight").prev().children(".out").html("");
		}
	}
	
	/**
	*弹出层修改规格
	**/
	function ajaxGetShippingTemplates(){
		
		var basePath = jsBasePath;
		var val = $("#shippingTemplateId").val();
		var NUM = 8000000.00;
		$.ajax({
			url : basePath +'/product/ajaxGetShippingTemplates.json?supplierId='+jsSupplierId,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: true,
			success : function(data) {
				var html='';
				var freehtml='';

				if (data.success) {
					var list = data.data.shipptemplatelist;
					//var shippingFree = data.msg;
					var shippingFree = data.data.shippingFree;
					var list1 = data.data.freerulelist;
					freehtml+='<table  border="0px" cellpadding="0" cellspacing="0" style="width:650px;margin-left:100px"><thead><tr><th style="width:350px">选择地区</th><th style="width:300px">设置包邮条件</th></tr></thead>';
					freehtml+='<tbody>';
					//遍历freerulelist获取数据信息
					for(var i=0;i<list1.length;i++){
						freehtml+='<tr>';
						freehtml+='<td style="width:250px;">'+list1[i].areasName+'</td>';
						freehtml+='<td>';
						if(list1[i].countTypeDes ==1){
							freehtml+='满 &nbsp;'+list1[i].param1+'&nbsp;件包邮';
						}else if(list1[i].countTypeDes ==2){
							freehtml+='满 &nbsp;'+list1[i].param2+'&nbsp;元包邮';
						}else if(list1[i].countTypeDes ==3){
							freehtml+='满 &nbsp;'+list1[i].param1+'&nbsp;件,且&nbsp;'+list1[i].param2+'&nbsp;元以上 包邮';
						}
						freehtml+='</td>';
						freehtml+='</tr>';
					}
					freehtml+='</tbody></table>';
					
					
					
					var html = '<option value="-1">--请选择--</option>';

					for(var i=0;i<list.length;i++){
						var tmp = list[i].id+','+list[i].countType;
						if(tmp==val){
							html+='<option value="'+tmp+'" selected>'+list[i].name+'</option>';
						} else {
							html+='<option value="'+tmp+'">'+list[i].name+'</option>';
						}
					}
					html+='<option value="*">添加新模板*</option>';
					$("#shippingTemplateId").html(html);
					setWBmust($("#shippingTemplateId").val());
					
					if(shippingFree==undefined || shippingFree=='' || parseFloat(shippingFree)==-1) {
						$("#shippingFree").html("全场包邮未设置");
						$(".tab_box").html("");
						$(".tab_box").hide();
					} else if(parseFloat(shippingFree) < NUM){
						$("#shippingFree").html('全场满&nbsp;<em style="font-style:normal;font-size:16px;color:#ff0000">'+shippingFree+'</em>&nbsp;元包邮');
						$(".tab_box").html("");
						$(".tab_box").hide();
					} else {
						if ($(".tab_box").is(":hidden")) {
							$("#shippingFree").html('<div class="add_shippfree"><a href="javascript:void(0);" onclick="clickshippfree()" style="color: #2b8dff">+全场包邮策略</a></div>');
						} else {
							$("#shippingFree").html('<div class="add_shippfree"><a href="javascript:void(0);" onclick="clickshippfree()" style="color: #2b8dff">-全场包邮策略</a></div>');
						}
						$(".tab_box").html(freehtml);
						
					}
				}
			},
			error : function() {}
		});
	}

	/**
	*弹出层修改规格
	**/
	function ajaxGetQuestionnaires(questionnaireId){
		
		var basePath = jsBasePath;
		var val = $("#questionnaireId").val();
		$.ajax({
			url : basePath +'/questionnaire/ajaxGetQuestionnaires.json?supplierId='+jsSupplierId+'&questionnaireId='+questionnaireId,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: true,
			success : function(data) {
				var html='';
				var freehtml='';

				if (data.success) {
					var list = data.data;
										
					var html = '<option value="-1">--不使用问卷 普通评论--</option>';

					for(var i=0;i<list.length;i++){
						if(list[i].id==val){
							html+='<option value="'+list[i].id+'" selected>'+list[i].templateTitle+'</option>';
						} else {
							html+='<option value="'+list[i].id+'">'+list[i].templateTitle+'</option>';
						}
					}
					$("#questionnaireId").html(html);
					
					initq();
				}
			},
			error : function() {}
		});
	}
	
	var editTrId="";
	function showArea(obj,strPrefix) {
		writeCityBox();
		
		var ftop =$(obj).offset().top+40;
		var ftop1 =$(obj).offset().top-80;
		editTrId=$(obj).parent().parent().attr("id");
		
		var allSelectCode=$("#areasCode").val();
		initCityCheckBok(allSelectCode,allSelectCode);
		$(".city_box").attr("style","top:"+ftop+"px;");
		$(".city_box1").attr("style","top:"+ftop1+"px;");
		$(".city_box").show();
	}
	

	//改变checkbox可选状态及选中状态
	function initCityCheckBok(allSelectCode,currtSelectCode) {
		//回复最初状态
		var checkboxs=$(".city_box").find("[type='checkbox']");
		checkboxs.removeAttr("disabled");
		checkboxs.removeAttr("checked");

		//设置checkbox可选状态及选中状态
		var ary = allSelectCode.split(",");
		for(var i=0;i<ary.length;i++){
			var checkbox=$(".city_box").find("[value='"+ary[i]+"']");
			
			if(checkbox.length > 0) {
				//本行是否已选
				if(currtSelectCode.indexOf(ary[i])>-1) {
					//选中
					if(ary[i].substring(2) == '0000') {
						//省选中 
						checkbox.parent().find("[type='checkbox']").attr("checked","checked");
					} else {
						checkbox.attr("checked","checked");
					}
				} else {
					//选中
					if(ary[i].substring(2) == '0000') {
						//省选中 
						checkbox.parent().find("[type='checkbox']").attr("disabled","disabled");
					} else {
						checkbox.attr("disabled","disabled");
					}
				}
			}
		}
		
		//设置省checkbox可选状态及选中状态
		var pros= $("#city_box>div>dl>dd>ul>li>input");
		pros.each(function(i,val){
			refreshPro(this);
		});

		//设置区域checkbox可选状态及选中状态
		var areas= $("#city_box>div>dl>dt>span>input");
		areas.each(function(i,val){
			refreshArea(this);
		});
	}

	/**
	 * 运费选择模式变化
	 * @param kbn
	 */
	function rdlimitCntClick(kbn) {
		if(kbn==1){
			$("#limitCnt").val("0");
			$("#limitCnt").attr("readonly",true);
		} else {
			$("#limitCnt").removeAttr("readonly");
		}
	}

	/**
	 * 运费选择模式变化
	 * @param kbn
	 */
	function rdAreasClick(kbn) {
		if(kbn==0){
			$("#areasCode").val("0");
			$("#areasName").val("");
			$("#pAreasName").html("编辑");
			$("#pAreasName").hide();
		} else {
			$("#pAreasName").show();
		}
	}
	//计算省选中及数量
	function refreshPro(obj) {
		var citys = $(obj).next().next().next().next().find("[type='checkbox']");
		
		var selCnt=0;
		//城市不可选，则省不可选
		for(var j=0;j<citys.length;j++) {
			var city=$(citys[j]);
			if(city.attr("checked") || city.attr("checked")=="checked") {
				selCnt++;
			}
			if(city.attr("disabled") || city.attr("disabled")=="disabled") {
				$(obj).attr("disabled","disabled");
			}
		}
		
		//设置选择数量
		if(selCnt>0) {
			$(obj).next().next().html("("+selCnt+")");
			if(selCnt == citys.length) {
				$(obj).attr("checked","checked");
			} else {
				$(obj).removeAttr("checked");
			}
		} else {
			$(obj).next().next().html("");
			$(obj).removeAttr("checked");
		}
	}

	//计算省选中及数量
	function refreshArea(obj) {
		var prosp = $($(obj).parent().parent().next().children()[0]).children();
		
		var selCnt=0;
		//城市不可选，则省不可选
		for(var j=0;j<prosp.length;j++) {
			var pro=$($(prosp[j]).children()[0]);
			if(pro.attr("checked") || pro.attr("checked")=="checked") {
				selCnt++;
			}
			if(pro.attr("disabled") || pro.attr("disabled")=="disabled") {
				$(obj).attr("disabled","disabled");
			}
		}
		
		//设置选择数量
		if(selCnt == prosp.length) {
			$(obj).attr("checked","checked");
		} else {
			$(obj).removeAttr("checked");
		}
	}

	//城市选择
	function selectCity(obj) {

		if($(obj).attr("checked") || $(obj).attr("checked")=="checked") {
			$(obj).attr("checked","checked")
		} else {
			$(obj).removeAttr("checked");
		}
		refreshPro($(obj).parent().parent().parent().prev().prev().prev().prev());
		refreshArea($($(obj).parent().parent().parent().parent().parent().parent().prev().children()[0]).children()[0]);
	}

	function selectPro(obj) {
		if($(obj).attr("checked") || $(obj).attr("checked")=="checked") {
			$(obj).next().next().next().next().find("[type='checkbox']").attr("checked","checked");
		} else {
			$(obj).next().next().next().next().find("[type='checkbox']").removeAttr("checked");
		}
		
		refreshPro(obj);
		refreshArea($($(obj).parent().parent().parent().prev().children()[0]).children()[0]);
	}

	function selectArea(obj) {
		if($(obj).attr("checked") || $(obj).attr("checked")=="checked") {
			$(obj).parent().parent().next().find("[type='checkbox']").attr("checked","checked");
		} else {
			$(obj).parent().parent().next().find("[type='checkbox']").removeAttr("checked");
		}
		
		var prosp = $($(obj).parent().parent().next().children()[0]).children();
		for(var j=0;j<prosp.length;j++) {
			refreshPro($(prosp[j]).children()[0]);
		}
		
		refreshArea(obj);
	}
	
	function saveArea() {
		var currtSelectCode="";
		var currtSelectName="";
		var checkboxs=$(".city_box").find("[checked='checked']");
		checkboxs.each(function(i,val){
			var val = $(this).val();
			if(val.length==6 && currtSelectCode.indexOf(val) == -1  && currtSelectCode.indexOf(val.substring(0,2)+'0000') == -1) {
				currtSelectCode += val + ",";
				currtSelectName += $(this).next().html() + " ";
			}
		});
		
		$("#pAreasName").html(currtSelectName);
		$("#areasName").val(currtSelectName);
		$("#areasCode").val(currtSelectCode);
	}
	

	function ajaxGetSku(productid){
		
		var basePath = jsBasePath;
		$.ajax({
			url : basePath +'/product/ajaxGetSku.json?productId='+productid,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: true,
			success : function(data) {
				var specificationType = data.specificationType;
				var skuCnt =data.skuCnt; //获取controller返回的数据
				
				$("#rdType"+specificationType).attr("checked","checked");
				specificationTypeChangge($("#rdType"+specificationType));
				if(skuCnt ==0){//如果没有填写sku隐藏图片加载，返回
					$("#skuLoading").hide();
					return;
				}

				var names = data.names;
				var smap = data.smap;
				var skus = data.skus;
				var imgs = data.imgs;
				var inventorys = data.inventorys;
				var is2 = names.length==2;
				var html = '';
				var bakhtml = '';
				if(is2) { //有2个规格
					var lv1= smap[names[0]];
					var lv2= smap[names[1]];

					rowCnt =  lv2.length * lv1.length + 1;
					html += '<tr><th class="Upload">上传图片</th><th class="Color">' +names[0]+ '</th><th class="Size">' +names[1]+ '</th><th class="Barcode">条形码</th><th class="Price">电商价（元）</th><th class="Price">内购价（元）</th><th class="Inventory">库存</th><th style="display:none;" class="Value">库存预警值</th><th style="display:none;" class="Value">内购券</th><th class="Value">起售数量</th></tr>';
					html += '<tr>';
					html += '<td>&nbsp;</td>';
					html += '<td>&nbsp;</td>';
					html += '<td>&nbsp;</td>';
					html += '<td><input class="rule_input f108" type="text" id="all_barcode" maxLength="15" onkeyup="value=value.replace(/[^\\d|a-z|A-Z]/g,\'\');" value="">';
					html += ' <p style="width: 90px;">';
					html += '  <a onclick="copyTotheall(\'all_barcode\');" href="javascript:void(0);">全部应用</a>';
					html += ' </p>';
					html += '</td>';
					html += '<td><input class="rule_input f68" type="text" id="productprice"  maxLength="8" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,\'$1$2.$3\');" value="">';
					html += ' <p style="width: 90px;">';
					html += '  <a onclick="copyTotheall(\'productprice\');" href="javascript:void(0);">全部应用</a>';
					html += '</p>';
					html += '</td>';
					html += '<td><input class="rule_input f68" type="text" id="fproductprice"  maxLength="8" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,\'$1$2.$3\');" value="">';
					html += ' <p style="width: 90px;">';
					html += '  <a onclick="copyTotheall(\'fproductprice\');" href="javascript:void(0);">全部应用</a>';
					html += ' </p>';
					html += '</td>';
					html += '<td><input class="rule_input f68" type="text" id="productnum"  maxLength="8" onkeyup="this.value=this.value.replace(/\D/g,\'\')" value="">';
					html += ' <p style="width: 90px;">';
					html += '  <a onclick="copyTotheall(\'productnum\');" href="javascript:void(0);">全部应用</a>';
					html += ' </p>';
					html += '</td>';
					html += '<td style="display:none;"><input class="rule_input f68" type="text" id="warnnum"  maxLength="8" onkeyup="this.value=this.value.replace(/\D/g,\'\')" value="">';
					html += ' <p style="width: 90px;">';
					html += '  <a onclick="copyTotheall(\'warnnum\');" href="javascript:void(0);">全部应用</a>';
					html += ' </p>';
					html += '</td>';
					html += '<td><input class="rule_input f68" type="text" id="minLimitNum"  maxLength="8" onkeyup="this.value=this.value.replace(/\D/g,\'\')" value="">';
					html += ' <p style="width: 90px;">';
					html += '  <a onclick="copyTotheall(\'minLimitNum\');" href="javascript:void(0);">全部应用</a>';
					html += ' </p>';
					html += '</td>';
					html += '<td style="display:none;" >&nbsp;</td>';
					html += '</tr>';
					
					for(var j=0;j<lv1.length;j++) {
						var items=new Array();
						var v2s=new Array();
						var indexItem = 0;
						for(var k=0;k<lv2.length;k++) {
							var item = skus[","+lv1[j]+","+lv2[k]];
							if(item) {
								items[indexItem] = item;
								v2s[indexItem] = lv2[k];
								indexItem++;
							}
						}
						
						var img = imgs[items[0].id];
						var imgVal="";
						if(img){
							imgVal=lv1[j]+ '_' + img;
						}
						html += '<tr class="last_row">';
						html += '<td rowspan="'+items.length+'">';
						html += ' <div class="uploadimg"><img src="'+jsStaticResources +'images/uploadimg.jpg" width="87" height="50" alt="uploadimg"></div>';
						html += ' <div class="uploadbtn" forname="'+lv1[j]+'" onclick="uploadDivdisplay(this);">';
						html += '  <a href="javascript:void(0);">上传图片</a>';
						html += '  <input name="specifications_image_result" type="hidden" value="'+imgVal + '" />';
						html += ' </div>';
						html += '</td>';
						html += '<td rowspan="'+items.length+'">'+lv1[j]+'</td>';
						
						for(var k=0;k<items.length;k++) {
							var item = items[k];
							if(item.maxFucoin ==undefined){
								item.maxFucoin="";
							}
							if(item.price ==undefined){
								item.price="";
							}
							var fproductprice="";
							if(item.price!="") {
								if(item.maxFucoin!="") {
									fproductprice = (item.price-item.maxFucoin).toFixed(2);
								} else {
									fproductprice = (item.price).toFixed(2);
								}
							}
							var stock = inventorys[item.id];
							if(stock.quantity == null || stock.quantity ==undefined){
								stock.quantity="";
							}
							if(stock.warnQuantity ==null || stock.warnQuantity==undefined){
								stock.warnQuantity="";
							}
							if(k>0) {
								html += '<tr>';
							}

							html += '<td>'+v2s[k]+'</td>';
							html += '<td>';
							html += ' <input class="rule_input f108" type="text" name="barcode"  ismust="1" typename="input" maxLength="15" value="'+item.productCode+'" onkeyup="value=value.replace(/[^\\d|a-z|A-Z\-]/g,\'\');specificationsChange(this)">';
							html += '</td>';
							html += '<td>';
							html += ' <input class="rule_input f68" type="text" name="productprice" isnum="1" ismust="1" typename="input" maxLength="8" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,\'$1$2.$3\');" onblur="specificationsChange(this)" value="'+item.price + '">';
							html += '</td>';
							html += '<td>';
							html += ' <input class="rule_input f68" type="text" name="fproductprice" isnum="1" ismust="1" typename="input" maxLength="8" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,\'$1$2.$3\');specificationsChange(this)" value="'+fproductprice + '">';
							html += '</td>';
							html += '<td>';
							html += ' <input class="rule_input f68" type="text" name="productnum" isnum="1" ismust="1" typename="input" maxLength="8" onkeyup="this.value=this.value.replace(/\D/g,\'\');specificationsChange(this)" value="'+stock.quantity + '">';
							html += '</td>';
							html += '<td style="display:none;">';
							html += ' <input class="rule_input f68" type="text" name="warnnum"  isnum="1" ismust="1" typename="input" maxLength="8" onkeyup="this.value=this.value.replace(/\D/g,\'\');specificationsChange(this)" value="'+stock.warnQuantity + '">';
							html += '</td>';
							html += '<td style="display:none;position: relative">';
							html += ' <input type="hidden" name="maxFucoin" value="'+item.maxFucoin+'"><span name="lblMaxFucoin">'+item.maxFucoin+'</span><input type="hidden" id="'+(lv1[j]+","+v2s[k])+'" name="specifications_result" value="'+(lv1[j]+","+v2s[k]) + '_' + item.productCode+ '_'+item.price + '_' + stock.quantity + '_' +stock.warnQuantity + '_' +item.maxFucoin+'_'+fproductprice+ '_' +  item.minLimitNum +'" />';
							html += '</td>';
							html += '<td>';
							if(items.length>1){
								html += ' <div class="" onmouseover="delMouseover(this);" onmouseout="delMouseout(this);" onclick="javasrciput:removeSpecificationRow2(this);">';
								html += '  <a href="javascript:void(0);">X 断码</a>';
								html += ' </div>';
							}
							html += ' <input class="rule_input f68" type="text" name="minLimitNum"  isnum="1" ismust="1" typename="input" maxLength="8" onkeyup="this.value=this.value.replace(/\D/g,\'\');specificationsChange(this)" value="'+item.minLimitNum + '">';
							html += '</td>';
							html += '</tr>';
							bakhtml += '<input type="hidden" name="bak_specifications_result" value="'+(lv1[j]+","+v2s[k]) + '_' + item.productCode+ '_'+item.price + '_' + stock.quantity + '_' +stock.warnQuantity + '_' +item.maxFucoin+ '_' + fproductprice + '_' + item.minLimitNum + '" />';
						}
					}
				} else {//一个规格
					var lv1= smap[names[0]];
					rowCnt =  lv1.length + 1;
					html += '<tr><th class="Upload">上传图片</th><th class="Color">' +names[0]+ '</th><th class="Barcode">条形码</th><th class="Price">电商价（元）</th><th class="Price">内购价（元）</th><th class="Inventory">库存</th><th style="display:none;" class="Value">库存预警值</th><th style="display:none;" class="Value">内购券</th><th class="Value" >起售数量</th></tr>';
					html += '<tr>';
					html += '<td>&nbsp;</td>';
					html += '<td>&nbsp;</td>';
					html += '<td><input class="rule_input f108" type="text" id="all_barcode"  maxLength="15" onkeyup="value=value.replace(/[^\\d|a-z|A-Z\-]/g,\'\');" value="">';
					html += ' <p style="width: 90px;">';
					html += '  <a onclick="copyTotheall(\'all_barcode\');" href="javascript:void(0);">全部应用</a>';
					html += ' </p>';
					html += '</td>';
					html += '<td><input class="rule_input f68" type="text" id="productprice" maxLength="8"  onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,\'$1$2.$3\');" value="">';
					html += ' <p style="width: 90px;">';
					html += '  <a onclick="copyTotheall(\'productprice\');" href="javascript:void(0);">全部应用</a>';
					html += '</p>';
					html += '</td>';
					html += '<td><input class="rule_input f68" type="text" id="fproductprice" maxLength="8"   onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,\'$1$2.$3\');" value="">';
					html += ' <p style="width: 90px;">';
					html += '  <a onclick="copyTotheall(\'fproductprice\');" href="javascript:void(0);">全部应用</a>';
					html += ' </p>';
					html += '</td>';
					html += '<td><input class="rule_input f68" type="text" id="productnum" maxLength="8"   onkeyup="this.value=this.value.replace(/\D/g,\'\')" value="">';
					html += ' <p style="width: 90px;">';
					html += '  <a onclick="copyTotheall(\'productnum\');" href="javascript:void(0);">全部应用</a>';
					html += ' </p>';
					html += '</td>';
					html += '<td style="display:none;"><input class="rule_input f68" type="text" id="warnnum" maxLength="8"   onkeyup="this.value=this.value.replace(/\D/g,\'\')" value="1">';
					html += ' <p style="width: 90px;">';
					html += '  <a onclick="copyTotheall(\'warnnum\');" href="javascript:void(0);">全部应用</a>';
					html += ' </p>';
					html += '</td>';
					html += '<td style="display:none;">&nbsp;</td>';
					html += '<td><input class="rule_input f68" type="text" id="minLimitNum" maxLength="8"   onkeyup="this.value=this.value.replace(/\D/g,\'\')" value="">';
					html += ' <p style="width: 90px;">';
					html += '  <a onclick="copyTotheall(\'minLimitNum\');" href="javascript:void(0);">全部应用</a>';
					html += ' </p>';
					html += '</td>';
					html += '</tr>';
					
					//简略sku
					if(specificationType=="1") {
						for(var j=0;j<lv1.length;j++) {
							var item = skus[','+ lv1[j]];
							if(item.maxFucoin ==undefined){
								item.maxFucoin="";
							}
							if(item.price ==undefined){
								item.price="";
							}
							var stock = inventorys[item.id];
							if(stock.quantity == null || stock.quantity ==undefined){
								stock.quantity="";
							}
							if(stock.warnQuantity ==null || stock.warnQuantity==undefined){
								stock.warnQuantity="";
							}
							var fproductprice="";
							if(item.price!="") {
								if(item.maxFucoin!="") {//过滤0等于空串为true
									fproductprice = (item.price-item.maxFucoin).toFixed(2);
								} else {
									fproductprice = (item.price).toFixed(2);
								}
							}
							
							var img = imgs[item.id];
							var imgVal="";
							if(img){
								imgVal=(j+1)+'_'+img;
							}
							html += '<tr class="last_row">';
							html += '<td>';
							html += ' <div class="uploadimg"><img src="'+jsStaticResources +'images/uploadimg.jpg" width="87" height="50" alt="uploadimg"></div>';
							html += ' <div class="uploadbtn" forname="'+(j+1)+'" onclick="uploadDivdisplay(this);">';
							html += '  <a href="javascript:void(0);">上传图片</a>';
							html += '  <input name="specifications_image_result" type="hidden" value="'+imgVal+ '" />';
							html += ' </div>';
							html += '</td>';
							html += '<td style="position: relative">';
							html += ' <div class="del_role_btn" onmouseover="delMouseover(this);" onmouseout="delMouseout(this);" onclick="javasrciput:removeSpecificationRow(this);">';
							html += '  <a href="javascript:void(0);">X 删除</a>';
							html += ' </div>';
							html += ' <input type="hidden" name="color_id" value="'+(j+1)+'" />';
							html += ' <input class="rule_input f108" type="text" name="color" ismust="1"  typename="input" maxLength="15" value="'+lv1[j]+'" onkeyup="clearNgText3(this)">';
							html += '</td>';
							html += '<td>';
							html += ' <input class="rule_input f108" type="text" name="barcode"   ismust="1" typename="input" maxLength="15" value="'+item.productCode+'" onkeyup="value=value.replace(/[^\\d|a-z|A-Z\-]/g,\'\');specificationsChange(this)">';
							html += '</td>';
							html += '<td>';
							html += ' <input class="rule_input f68" type="text" name="productprice" isnum="1"  ismust="1" typename="input" maxLength="8" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,\'$1$2.$3\');" onblur="specificationsChange(this)" value="'+item.price + '">';
							html += '</td>';
							html += '<td>';
							html += ' <input class="rule_input f68" type="text" name="fproductprice" isnum="1" ismust="1" typename="input" maxLength="8" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,\'$1$2.$3\');specificationsChange(this)" value="'+fproductprice + '">';
							html += '</td>';
							html += '<td>';
							html += ' <input class="rule_input f68" type="text" name="productnum" isnum="1" ismust="1" typename="input" maxLength="8" onkeyup="this.value=this.value.replace(/\D/g,\'\');specificationsChange(this)" value="'+stock.quantity + '">';
							html += '</td>';
							html += '<td style="display:none;">';
							html += ' <input class="rule_input f68" type="text" name="warnnum" isnum="1" ismust="1" typename="input" maxLength="8" onkeyup="this.value=this.value.replace(/\D/g,\'\');specificationsChange(this)" value="'+stock.warnQuantity + '">';
							html += '</td>';
							html += '<td  style="display:none;">';
							html += ' <input type="hidden" name="maxFucoin" value="'+item.maxFucoin+'"><span name="lblMaxFucoin">'+item.maxFucoin+'</span><input type="hidden" id="'+(j+1)+'" name="specifications_result" value="'+(j+1)+ '_' + item.productCode+ '_'+item.price + '_' + stock.quantity + '_' +stock.warnQuantity + '_' +item.maxFucoin+'_'+fproductprice+ '_' +  item.minLimitNum +'" />';
							html += '</td>';
							html += '<td>';
							html += ' <input class="rule_input f68" type="text" name="minLimitNum" isnum="1" ismust="1" typename="input" maxLength="8" onkeyup="this.value=this.value.replace(/\D/g,\'\');specificationsChange(this)" value="'+item.minLimitNum + '">';
							html += '</td>';
							html += '</tr>';
							bakhtml += '<input type="hidden" name="bak_specifications_result" value="'+(j+1)+ '_' + item.productCode+ '_'+item.price + '_' + stock.quantity + '_' +stock.warnQuantity + '_' +item.maxFucoin+'_'+fproductprice+ '_' + item.minLimitNum + '" />';
						}
					} else {
						for(var j=0;j<lv1.length;j++) {
							var item = skus[','+ lv1[j]];
							var stock = inventorys[item.id];
							if(item.maxFucoin ==undefined){
								item.maxFucoin="";
							}
							if(item.price ==undefined){
								item.price="";
							}
							var stock = inventorys[item.id];
							if(stock.quantity == null || stock.quantity ==undefined){
								stock.quantity="";
							}
							if(stock.warnQuantity ==null || stock.warnQuantity==undefined){
								stock.warnQuantity="";
							}
							var img = imgs[item.id];
							var fproductprice = "";
							if(item.price!="") {
								if(item.maxFucoin!="") {
									fproductprice = (item.price-item.maxFucoin).toFixed(2);
								} else {
									fproductprice = (item.price).toFixed(2);
								}
							}
							var imgVal="";
							if(img) {
								imgVal = lv1[j]+'_'+img;
							}
							
							html += '<tr class="last_row">';
							html += '<td>';
							html += ' <div class="uploadimg"><img src="'+jsStaticResources +'images/uploadimg.jpg" width="87" height="50" alt="uploadimg"></div>';
							html += ' <div class="uploadbtn" forname="'+lv1[j]+'" onclick="uploadDivdisplay(this);">';
							html += '  <a href="javascript:void(0);">上传图片</a>';
							html += '  <input name="specifications_image_result" type="hidden" value="'+imgVal+ '" />';
							html += ' </div>';
							html += '</td>';
							html += '<td style="position: relative">'+lv1[j]+'</td>';
							html += '<td>';
							html += ' <input class="rule_input f108" type="text" name="barcode"  ismust="1" typename="input" maxLength="15" value="'+item.productCode+'" onkeyup="value=value.replace(/[^\\d|a-z|A-Z\-]/g,\'\');specificationsChange(this)">';
							html += '</td>';
							html += '<td>';
							html += ' <input class="rule_input f68" type="text" name="productprice" isnum="1" ismust="1" typename="input" maxLength="8" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,\'$1$2.$3\');" onblur="specificationsChange(this)" value="'+item.price + '">';
							html += '</td>';
							html += '<td>';
							html += ' <input class="rule_input f68" type="text" name="fproductprice" isnum="1"  ismust="1" typename="input" maxLength="8" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,\'$1$2.$3\');specificationsChange(this)" value="'+fproductprice + '">';
							html += '</td>';
							html += '<td>';
							html += ' <input class="rule_input f68" type="text" name="productnum" isnum="1" ismust="1" typename="input" maxLength="8" onkeyup="this.value=this.value.replace(/\D/g,\'\');specificationsChange(this)" value="'+stock.quantity + '">';
							html += '</td>';
							html += '<td style="display:none;">';
							html += ' <input class="rule_input f68" type="text" name="warnnum" isnum="1" ismust="1" typename="input" maxLength="8" onkeyup="this.value=this.value.replace(/\D/g,\'\');specificationsChange(this)" value="'+stock.warnQuantity + '">';
							html += '</td>';
							html += '<td style="display:none;">';
							html += ' <input type="hidden" name="maxFucoin" value="'+item.maxFucoin+'"><span name="lblMaxFucoin">'+item.maxFucoin+'</span><input type="hidden" id="'+lv1[j]+'" name="specifications_result" value="'+lv1[j]+ '_' + item.productCode+ '_'+item.price + '_' + stock.quantity + '_' +stock.warnQuantity + '_' +item.maxFucoin+'_'+fproductprice+ '_' +item.minLimitNum +'" />';
							html += '</td>';
							html += '<td>';
							html += ' <input class="rule_input f68" type="text" name="minLimitNum" isnum="1" ismust="1" typename="input" maxLength="8" onkeyup="this.value=this.value.replace(/\D/g,\'\');specificationsChange(this)" value="'+item.minLimitNum + '">';
							html += '</td>';
							html += '</tr>';
							bakhtml += '<input type="hidden" name="bak_specifications_result" value="'+lv1[j] + '_' + item.productCode+ '_'+item.price + '_' + stock.quantity + '_' +stock.warnQuantity + '_' +item.maxFucoin+'_'+fproductprice+ '_' +item.minLimitNum +'" />';
						}
					}
				}
				//处理阶梯价
				dealLadder(names, skus, smap, productid);
				$("#skuLoading").hide();
				$("#specificationTable").html(html);
				$("#bak_sku").html(bakhtml);
				$('.pro_rule_wrap').show();	
				initSpecification();
				
				initSpecificationsImageFirst();//格式化上传的默认图片
				var strPrefix="";
				specificationChanageAll();
				if($("#rdType2").attr("checked") || $("#rdType2").attr("checked")=="checked") {strPrefix="self_";}
				var specification_results=$("[name="+strPrefix+"specification_result]");
				if(specification_results.length > 0) {
					$("#bak_kingaku1select").val($(specification_results[0]).val());
				} else {
					$("#bak_kingaku1select").val("");
				}
				if(specification_results.length > 1) {
					$("#bak_kingaku2select").val($(specification_results[1]).val());
				} else {
					$("#bak_kingaku2select").val("");
				}

				ajaxGetLevelCnt();
				calTrial();
					
				
			},
			error : function() {
				alert(1);
			}
		});
	}
	//处理更新换领币和员工件数
	function exchangeLetter(price1){
		var lsp = $("input[name='fproductprice']");
		var price = 0;
		try {
			for(var i=0;i<lsp.length;i++) {
				if($(lsp[i]).val() != '') {
					price = parseFloat($(lsp[i]).val());
				}
			}
		} catch(e) {}
		var num = new Number(price1);
		$("#empPrice1").val(num.toFixed(2));
		$("#spanLetter").html((price1/price).toFixed(1));
	}
	//处理专享商品更新每张内购现金卷
	function exchangeCashPrice(empPrice){
		var lsp = $("input[name='fproductprice']");
		if(lsp.length>0) {
			var price = parseFloat($(lsp[0]).val());
			for(var i=1;i<lsp.length;i++) {
				if(price != parseFloat($(lsp[i]).val())) {
					showInfoBox("参加专享的sku内购价必须相同。");
					return false;
				}
			}
		}
		var price = 0;
		try {
			for(var i=0;i<lsp.length;i++) {
				if($(lsp[i]).val() != '') {
					price = parseFloat($(lsp[i]).val());
				}
			}
		} catch(e) {}
		if(parseFloat(empPrice) > price){
			showInfoBox("专享价不能超过sku内购价");
			$("#empPrice").val("0.00");
			return false;
		}
		var cashPrice = (price-empPrice).toFixed(2);
		$("#cashPrice").html(cashPrice);
		var empLevel = $("#empLevel").val();
		if(empLevel != ''){
			$("#wealPrice").html((empLevel*cashPrice).toFixed(2));
		}
	}
	//处理专享商品更新员工人数福利卷金额
	function exchangeWealPrice(empCnt){
		var lsn = $("input[name='productnum']");
		var num = 0;
		try {
			for(var i=0;i<lsn.length;i++) {
				if($(lsn[i]).val() == '' ) {
					
				} else {
					num += parseInt($(lsn[i]).val())
				}
			}
		} catch(e) {}
		if(empCnt > num){
			showInfoBox("员工人数不能超过库存");
			return false;
		}
		var cashPrice = $("#cashPrice").html();
		$("#wealPrice").html((empCnt*cashPrice).toFixed(2));
	}
	
	function ajaxGetLevelCnt(){
		//处理换领商品换领币回显和员工件数回显
		var lsp = $("input[name='fproductprice']");
		var price = 0;
		try {
			for(var i=0;i<lsp.length;i++) {
				if($(lsp[i]).val() != '') {
					price = parseFloat($(lsp[i]).val());
				}
			}
		} catch(e) {}
		var empPrice1 = new Number($("#empPrice1").val());
		if(empPrice1 != ''){
//			$("#empPrice1").val(empPrice1.toFixed(2));
			$("#spanLetter").html((empPrice1/price).toFixed(1));
		}
		
		//处理专享商品更新每张内购现金卷
		var empPrice = new Number($("#empPrice").val());
		if(empPrice != ''){
			$("#cashPrice").html((price-empPrice).toFixed(2));
		}
		//处理专享商品更新员工人数福利卷金额
		var empCnt = $("#empLevel").val();
		var cashPrice = $("#cashPrice").html();
		if(empCnt != '' && cashPrice != ''){
			$("#wealPrice").html((empCnt*cashPrice).toFixed(2));
		}
		
		var basePath = jsBasePath;
		$.ajax({
			url : basePath +'/company/emp/ajaxGetEmpLevelCount.json',
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: true,
			success : function(data) {
				var ls = data.lsc;
				var al = 0;
				var html = '';
				var html2 = '';
				for(var i=0;i<ls.length;i++) {
					al += ls[i].levelCount;
					//html += '<div style="width:100px;float:left;padding-left:14px;"><input type="radio" name="divLevel" onchange="calDiv();" id="divLevel'+ls[i].welfareLevel+'" value="'+ls[i].welfareLevel+'" data="'+ls[i].levelCount+'" style="vertical-align: middle;margin: -2px 4px 1px 0;"><label for="divLevel'+ls[i].welfareLevel+'">'+ls[i].welfareLevel+'级（'+ls[i].levelCount+'）</label></div>';
					html2 += '<div style="width:100px;float:left;padding-left:14px;"><input type="radio" name="empLevel" id="empLevel'+ls[i].welfareLevel+'" value="'+ls[i].welfareLevel+'" data="'+ls[i].levelCount+'" style="vertical-align: middle;margin: -2px 4px 1px 0;"><label for="divLevel'+ls[i].welfareLevel+'">'+ls[i].welfareLevel+'级（'+ls[i].levelCount+'）</label></div>';
				}
				if(ls.length>4) {
					//html += '<br />';
					html2 += '<br />';
				}
				html = '<div style="float:left;padding-left:14px;"><input type="radio" name="divLevel"  onchange="calDiv();" id="divLevel-1" value="-1" data="'+al+'" checked style="vertical-align: middle;margin: -2px 4px 1px 0;"><label for="divLevel-1" >所有员工（'+al+'）</label></div>'
					+'<div class="bj_text" style="float: left; margin-left: 20px; display: inline">'
					+'<a class="add_new" href="'+jsBasePath+'/company/emp/page.html" target="_blank">查看</a>'
					+'</div><br />';
//				html += '<p style="padding-left: 14px; line-height: 30px; color: #acadad;" id="calP">换领商品总数<em></em>个，平均每人获<em></em>个，合换领币<em></em>元</p>';
				

				html2 = '<div style="float:left;padding-left:14px;"><input type="radio" name="empLevel" id="empLevel-1" value="-1" data="'+al+'" checked style="vertical-align: middle;margin: -2px 4px 1px 0;"><label for="empLevel-1" >所有员工（'+al+'）</label></div>'
					+'<div class="bj_text" style="float: left; margin-left: 20px; display: inline">'
					+'<a class="add_new" href="'+jsBasePath+'/company/emp/page.html" target="_blank">查看</a>'
					+'</div><br />' + html2;
				html2 += '<br /><p style="padding-left: 14px; line-height: 30px; color: #acadad;"><input type="checkbox" name="empCash" id="empCash" value="1"" /> <label for="empCash">未购买此商品的员工可获得现金券（差价）补偿</label></p>';
				
				
//				$("#divDivCnt").html(html);
				$("#empDivCnt").html(html2);
				
				if(divLevel != '') {
					setDivLevelCnt(divLevel);
				} else {
					setDivLevelCnt('-1');
				}

				if(empLevel != '') {
					setEmpLevelCnt(empLevel);
				} else {
					setEmpLevelCnt('-1');
				}
				

				if(empCash == '1' ) {
					$("#empCash").attr("checked","checked");
				}
				
				
			},
			error : function() {
				alert(1);
			}
		});
	}

	function setDivLevelCnt(v){
		$("#divLevel"+v).attr("checked","checked");
		calDiv();
	}

	function setEmpLevelCnt(v){
		$("#empLevel"+v).attr("checked","checked");
	}

	function calDiv(){
		var lsp = $("input[name='fproductprice']");
		var lsn = $("input[name='productnum']");
		
		var price = 0;
		var cnt = 0;
		try {
			for(var i=0;i<lsp.length;i++) {
				if($(lsp[i]).val() == '' || $(lsn[i]).val() == '' ) {
					
				} else {
					var num = parseInt($(lsn[i]).val())
					cnt += num;
					price = parseFloat($(lsp[i]).val());
				}
			}
			var d=parseInt($("input[name='divLevel']:checked").attr("data"));
			var html = "";
			if(d!=0) {
				var c = 0;
				if(cnt > d) {
					c = parseInt(cnt/d);
				} else {
					c = parseFloat(cnt) /d;
				}
				html = "换领商品总数<em>"+cnt+"</em>个，平均每人获<em>"+parseFloat(c.toFixed(2))+"</em>个，合换领币<em>"+parseFloat(price*c).toFixed(2)+"</em>元";
				
			} else {
				html = "换领商品总数<em>"+cnt+"</em>个，平均每人获<em>，但因没有添加员工，暂不能参加换领。";
			}
			$("#calP").html(html);
		} catch(e) {}		
	}
	

	function calTrial(){
		var html = "";
		var min = getMinPrice();
		var trialPrice = $("#trialPrice").val();
		var shopP;
		if(trialPrice=='') {
			shopP = min;
		} else {
			var ftrialPrice = parseFloat(trialPrice);
			if(ftrialPrice<0 || ftrialPrice>min) {
				shopP = min;
			} else {
				shopP = (min-ftrialPrice);
			}
		}
		if(isNaN(shopP)) {
			html = "试用商品的实际售价约为<em>0.00</em>元";
		} else {
			html = "试用商品的实际售价约为<em>"+shopP.toFixed(2)+"</em>元";
		}
		$("#calT").html(html);
	}
	
	function getMinPrice(){
		var lsp = $("input[name='fproductprice']");
		
		var min = 0.0;
		try {
			min = parseFloat($(lsp[0]).val());
			for(var i=1;i<lsp.length;i++) {
				if($(lsp[i]).val() == '') {
					
				} else {
					var p =  parseFloat($(lsp[i]).val());
					if(min>p) {
						min=p;
					}
				}
			}
		} catch(e) {alert(e);}	

		return min;
	}
	//更新包邮值
	function exemptionFromPostageClick(){
		if($('#rdFreightTypeCheckBox').attr('checked')){
			//如果选中修改包邮为0
			$("#newCarriage").val("0");
		}else{
			$("#newCarriage").val("1");
		}
	}
	//包邮验证
	function exemptionFromPostageV(){
		var newCarriage = $("#newCarriage").val();
		var newShippingTemplateId = $("#newShippingTemplateId").val();
		if(newCarriage && Number(newCarriage) > 0){
			if(!newShippingTemplateId){
				var shippingTemplateCreateUrl = $("#shippingTemplateCreate").attr("href");
				showConfirm("请设置运费模板","请设置运费模板之后再添加商品,点击确认去设置。",'window.open("'+ shippingTemplateCreateUrl +'")' ,null );
				return true;
			}
		}
		return false;
	}
	//刷新用户模板
	/**
	*弹出层修改规格
	**/
	function ajaxGetAllShippingTemplates(){
		
		var basePath = jsBasePath;
		$.ajax({
			url : basePath +'/shippingAddress/ajaxGetShippingTemplate',
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: true,
			success : function(data) {
				var html='';
				if (data.success) {
					if(data.data){
						html+='<div style="width: 600px">';
						html+='<div class="tem_tit" style="width: auto">';		
								html+='最后编辑时间：';
								html+= formatDate(data.data.updateTime);
								html+='</div>';
								html+='<div class="tab_box" style="width: auto">';
								html+='<table style="width: auto" border="0px" cellpadding="0" cellspacing="0">';
								html+='	<thead>';
								html+='	<tr>';
								html+='		<th style="width: 250px;">销售区域</th>';
								html+='		<th>首件(个)</th>';
								html+='		<th>运费（元）</th>';
								html+='		<th>续件(个)</th>';
								html+='		<th>运费（元）</th>';
								html+='	</tr>';
								html+='</thead>';
								html+='<tbody>';
								if(data.data.rulelist){
									for(var i =0 ; i < data.data.rulelist.length;i++){
										var rule = data.data.rulelist[i];
										html+='		<tr>';
										html+='			<td style="width: 250px;">'+rule.areasName+'</td>';
										html+='			<td>'+rule.firstCnt+'</td>';
										html+='			<td>'+rule.firstPrice+'</td>';
										html+='			<td>'+rule.plusCnt+'</td>';
										html+='			<td>'+rule.plusPrice+'</td>';
										html+='		</tr>';
									}
								}

								html+='	</tbody>';
								html+='</table>';
								if(data.data.freelist){
									html+='<table border="0px" cellpadding="0" cellspacing="0" style="width: auto; margin-top: 6px;">';
									html+='	<thead>';
									html+='		<tr>';
									html+='			<th style="width: 250px;">销售区域</th>';
									html+='			<th style="width: 350px;">包邮条件</th>';
									html+='		</tr>';
									html+='</thead>';
									html+='<tbody>';
									for(var i = 0; i < data.data.freelist.length; i ++){
										var rule = data.data.freelist[i];
										html+='		<tr>';
										html+='			<td style="width: 250px;">'+rule.areasName+'</td>';
										html+='			<td style="width: 350px;">';
										if(rule.countTypeDes=='2'){
											html+='满 &nbsp;' + rule.param2 + '&nbsp;元包邮';
										}
										if(rule.countTypeDes=='1'){
											html+='满 &nbsp;' + rule.param1 + '&nbsp;件包邮';
										}
										if(rule.countTypeDes=='3'){
											html+='满 &nbsp;' + rule.param1 + '&nbsp;件,且&nbsp;' + rule.param2 + '&nbsp;元以上 包邮';
										}
										html+='	</td>';
										html+='		</tr>';
									}
									html+='	</tbody>';
									html+='</table>';
								}
								html+='</div>';
								html+='</div>';
						$("#bak_shippingTemplateId").val(data.data.id);
						$("#newShippingTemplateId").val(data.data.id);
					}else{
						html+='<div class="add_new" style="float: left; margin-left: 20px">';
						html+='<a target="_blank" id="shippingTemplateCreate" href="${basePath}/shippingAddress/template_edit.html?templateId=0" style="color: #2b8dff">设置</a>';
					    html+='</div>';
					}
					$("#ajaxGetAllShippingTemplates").html(html);
					
				}
			},
			error : function() {}
		});
	}
	
	//时间转换 
	function   formatDate(now)   {
	    var   now= new Date(now);     
	    var   year=now.getFullYear();     
	    var   month=now.getMonth()+1;     
	    var   date=now.getDate();     
	    var   hour=now.getHours();
	    var   minute=now.getMinutes();     
	    return   year+"-"+fixZero(month,2)+"-"+fixZero(date,2)+" "+fixZero(hour,2)+":"+fixZero(minute,2); 
	}  
	//时间如果为单位数补0 
	function fixZero(num,length){     
	    var str=""+num;
	    var len=str.length;     
	    var s="";
	    for(var i=length;i-->len;){         
	        s+="0";
	    }
	    return s+str;
	}

	//促销活动
	function clickActivityQicai(){
		if($("#chkActivityQicai").prop("checked")) {
			$("#tab_box_qicai").show();
		} else {
			$("#tab_box_qicai").hide();
		}
	}
	
	//处理显示阶梯价
	function dealLadder(names, skus, smap,productid) {
		
		if(names){
			$.ajax({
				url : jsBasePath +'/productLadder/ajaxGetLadder.json?productId='+productid,
				type : "GET",
				dataType: "json",  //返回json格式的数据  
			    async: true,
				success : function(data) {
					
					if(data.data){
						var html = "";
						var isDiscount = false;
						if(data.data[0].type==1){
							isDiscount=true;
						}
						for(var z = 0; z < data.data.length ; z ++){
							html += '<tr>';
							html += '   <td><input  name="ladder-num-'+(z+1)+'" value = "'+data.data[z].num+'" style="width:50px;height:22px;" isnum="1" isnull="1" type="text" maxlength="8" onkeyup="this.value=this.value.replace(/\\D/g,\'\');" />件以上，内购价<input name="ladder-price-'+(z+1)+'" value = "'+data.data[z].price+'" style="width:50px;height:22px;" isnum="1" isnull="1" type="text" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');" /> 元/件</td>';
							html += '   <td></td>';
							html += '   <td><div onclick="removeActivityQicaiRow(this);"><a href="javascript:;">删除</a></div></td>';
							html += ' </tr>';
						}
						if(isDiscount){
							html = html.replace(/内购价/g,"").replace(/元\/件/g,"折");
							$("#chkActivityDiscount").prop("checked",true);
							$("#chkActivityDiscount").val(1);
						}	
						$("#dealLadderTable").html(html);
						$("#activityQicaiRowCnt").val(data.data.length);
						$("#chkActivityQicai").prop("checked",true);
						refreshActivityQicaiRow();

						var mapSkuValue = {};
						for(var key in skus) {
							mapSkuValue[skus[key].id+""]=key.substring(1).replace(",","/");
						}

						var mapRowSku = new Map();
						for(var z = 0; z < data.data.length ; z ++){
							var skuids = data.data[z].skuids.split(",");
							for(var j=0;j<skuids.length;j++) {
								if(skuids[j]!=null && skuids[j]!="") {
									var v = mapSkuValue[skuids[j]];
									if(v != null && v!="") {
										mapRowSku.put("ladder-box-"+(z+1)+"_"+v,"ladder-box-"+(z+1));
									}									
								}
							}
						}
						//加载默认的sku信息
				   	 	mapRowSku.each(function(key,value,index){
				   	 		var skus=$("#dealLadderTable").find("input[name='"+value+"']");
					 		skus.each(function(){
								if($(this).val()==key.substring(value.length+1)){
									$(this).prop("checked",true);
								}
					 		});
					 		
					       	$("#specificationTable").find("[id='"+key+"']").val(value); //获取之前sku的数据
					    });
				   	 	
				   	 $("#tab_box_qicai").show();
					}
				}
			});
		
		}
	}
	
	
	
	//判断是否有换领和试用的商品而且选择了阶梯价
	function checkLadderAndSaleKbn(){
		//如果勾选了阶梯价
		if($("#chkActivityQicai").attr("checked")){
			//并且选择了试用，换领就提示不能选择
			if($("#saleKbn5").attr("checked") || $("#saleKbn2").attr("checked") || $("#saleKbn50").attr("checked") ){
				showInfoBox("试用、换领商品不能设置阶梯价，请重新设置");
				return true;
			}
		}
	}