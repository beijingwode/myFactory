    $(document).ready(function () {
        selectedHeaderLi("jsgl_header");
        
        var statusTemp = $("#statusTemp").val();
        $("#status").val(statusTemp);
        

		$("#shop_popup").attr("style","z-index:9999999;position:absolute!important;margin-left:-350px;");
		$("#return_popup").attr("style","width:500px;z-index:9999999;position:absolute!important;margin-left:-50px!important;");
		$("#send_return_popup").attr("style","width:500px;z-index:9999999;position:absolute!important;margin-left:-50px!important;");
    });

	/**
	 * 快速跳转
	 */
	function gotoPage(){
		var pagenum = $("#pagenum").val();
		formSubmit(pagenum);
	}

	/**
	 * 表单提交
	 */
	function formSubmit(page){
		if(page!=undefined){
			$("#pageNumber").val(page);
		}else{
			$("#pageNumber").val(1);
		}
		$("#sub_form").submit();
	}
	
    /**
     * 重置
     */
    function formReset() {
        //$("#sub_form").reset();
        //document.getElementById("sub_form").reset();
        $("#sub_form").find("input[type!='hidden']").val("");
        $("#sub_form").find("select").find("option:first").attr("selected", "selected");
    }

    
    function toReturn(id) {
		$(".popup_bg").show();
		
		$("#returnId").val(id);
		$("#returnNote").val("");
		$("#ajaxErrMsg_return").html("");
		$("#return_popup").show();
    }

    function toSendReturn(id) {
		$(".popup_bg").show();
		
		$("#sendReturnId").val(id);
		$("#sendReturnNo").val("");
		$("#ajaxErrMsg_send_return").html("");
		$("#send_return_popup").show();
    }
    
    function showExpressInfo(expressType,expressNo) {
		$(".popup_bg").show();
		$("#shop_popup").show();
		$("#divLoading").show();
		$("#divExpress").hide();

		$.ajax({
			url : jsBasePath+'/saleBill/getExpressCom.json?expressType='+expressType,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: true,
			success : function(data) {
				var html='';

				if (data.result.errorCode == 0) {
					var expressCom = data.result.msgBody.split(",");

		    		$("#divExpress").html('<div class="es_box"><p class="es"><span>快递公司：<span>'+expressCom[0]+'</p><p class="es"><span>运单号：<span>'+expressNo+'</p></div><p class="dt">物流动态</p>');
		    		$("#divExpress").show();
		    		
		    		var content = '"sname":"express.ExpressSearch","com":"'+expressCom[1]+'","express_no":"'+expressNo+'","version":"v2"';
		            $.ajax({
		                url: 'http://kuaidi.wo-de.com/express/busJsonp.do?content={' + content + '}&token=',
		                dataType: 'jsonp',
		                jsonp: 'jsonpcallback',
		                success: function (json) {
		                    var ary = eval(json.body.history)
		                    var lis = "";
		                    if (ary.length > 0) {
		                        lis = '<p class="cur">' + ary[0].dealDate + '<span>' + ary[0].des + '</span></p>';
		                        for (var i = 1; i < ary.length; i++) {
		                            lis += '<p>' + ary[i].dealDate + '<span>' + ary[i].des + '</span></p>';
		                            ;
		                        }
		                        $("#divExpress").html($("#divExpress").html()+lis);
		        	    		$("#divLoading").hide();
		                    }
		                },
		                error: function (XMLHttpRequest, textStatus, errorThrown) {
		    	    		$("#divLoading").hide();
		                }
		            });
				}

			},
			error : function() {}
		});
    }
    

    function returnSubmit() {

        if ($("#returnNote").val() == '') {
    		$("#ajaxErrMsg_return").html("请输入回退理由。");
            return false;
        }
        
        $.ajax({
            url: jsBasePath+'/saleBill/apprReturn.json?id='+$("#returnId").val()+'&returnNote='+$("#returnNote").val(),
            type: "GET",
            dataType: "json",  //返回json格式的数据
            async: true,
            success: function (data) {
                if (data.result.errorCode == 0) {
                	hiddenObjById('return_popup');
                    showInfoBox("申请已提交，请等待平台确认。");

            		setTimeout("formSubmit(1)",1500);////格式化产地
                } else {
                    showInfoBox("已过退回期限，只能回退当月发票。");
                }
            }, error: function () {
            }
        });
        return false;
    }


    function sendReturn() {
        if ($("#sendReturnNo").val() == '') {
    		$("#ajaxErrMsg_send_return").html("请输入回退运单号。");
            return false;
        }
        
        $.ajax({
            url: jsBasePath+'/saleBill/sendReturn.json?id='+$("#sendReturnId").val()+'&returnExpressType='+$("#returnExpressType").val()+'&returnExpressNo='+$("#sendReturnNo").val(),
            type: "GET",
            dataType: "json",  //返回json格式的数据
            async: true,
            success: function (data) {
                if (data.result.errorCode == 0) {
                	hiddenObjById('send_return_popup');
                	
                    showInfoBox("发票已回退，请等待平台处理。");

            		setTimeout("formSubmit(1)",1500);////格式化产地
                } else {
                    showInfoBox("确认失败");
                }
            }, error: function () {
            }
        });
        return false;
    }