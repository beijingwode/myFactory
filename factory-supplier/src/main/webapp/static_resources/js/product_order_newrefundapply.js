    function sub_() {
        $("#sub_form").submit();
    }

    $(document).ready(function () {
        selectedHeaderLi("ddgl_header");
        var Refundstat = jsRefundorderStatus;
        var Returnstat = jsReturnorderStatus;
        var isReturnOrder = isReturnOrder;
    });

    function cancelButton(id) {
        $("#" + id).hide();
        $(".popup_bg").hide();
    }
    /**
     * 拒绝退款
     */
    function refuseAlert(state) {
        //$("#delivertime").find("[name='refundOrderId']").val(id);
    	$("#state").val(state);
        $("#delivertime").show();
        $(".popup_bg").show();
    }
    /**
     * 拒绝延迟收货
     */
    function refuse() {
    	var state=$("#state").val();
        var refuseNote = $("#delivertime").find("[name='delivertimeSelect']").find("option:selected").val();
        var otherrefuseNote = $("#delivertime").find("[name='supplierRefuseReason']").val();
        if(refuseNote == null || refuseNote == ""){
        	if(otherrefuseNote==null || otherrefuseNote==''){
        		alert("请输入拒绝理由");
        		return;
        	}
        }
        var reason = refuseNote+otherrefuseNote;
        var type='';
        if(state=='good'){
        	type=1;
        }else if(state=='money'){
        	type=2;
        }else if(state=='goodAndmoney'){
    		type =3;
    	}
        $.ajax({
            url: jsBasePath+"/returnorder/deal.json?returnOrderId=" + jsReturnorderId + "&refundOrderId=" + jsRefundorderId + "&action=2&type="+type+"&reason="+reason,
            type: "GET",
            dataType: "json",
            async: true,
            success: function (data) {
                window.location.reload();
            },
            error: function () {
            }
        });
    }
    /**
     * 同意退货
     */
    function agreeReturn(state){
    	var type = '';
    	var returnedAddress ='';
    	if(state=='good'){
    		type =1;
    		returnedAddress=$("#returnedAddress").val();
    	}else if(state=='money'){
    		type =2;
    	}else if(state=='goodAndmoney'){
    		type =3;
    	}
    	if(type==1 && returnedAddress==''){
    		alert("添加退货地址");
    		return;
    	}
    	if(type==''){
    		alert("缺少参数,请联系客服!");
    		return;
    	}
    	 $.ajax({
             url: jsBasePath+"/returnorder/deal.json?returnOrderId=" + jsReturnorderId + "&refundOrderId=" + jsRefundorderId + "&action=1&type="+type+"&returnedAddress="+returnedAddress,
             type: "GET",
             dataType: "json",
             async: true,
             success: function (data) {
                 window.location.reload();
             },
             error: function () {
             }
         });
    }
    /**
     * 查看物流
     */
    function showlistlogInfoAlert() {
        var jsExpressCom ='';
        var jsSearchId ='';
        var expressNo='';
        jsExpressCom=$("#expressCom").val();
        jsSearchId=$("#searchId").val();
        jsExpressNo =$("#expressNo").val();
        if(jsExpressCom!=''&& jsExpressCom!=null){
        	//var content = '"sname":"express.ExpressSearch","com":"'+jsExpressCom+'","express_no":"'+jsExpressNo+'","user":'+jsSearchId+',"version":"v2"';
        	var content = '"sname":"express.ExpressSearch","com":"'+jsExpressCom+'","express_no":"'+jsExpressNo+'","version":"v2"';
            $.ajax({
                url: 'http://kuaidi.wo-de.com/express/busJsonp.do?content={' + content + '}&token=',
                dataType: 'jsonp',
                jsonp: 'jsonpcallback',
                success: function (json) {
                	var ary = eval(json.body.history)
        	    	var lis="";
        	    	for(var i=0; i<ary.length; i++)  
    	    	  	{  
    	    	     	lis += "<li><span>"+ary[i].dealDate+"</span>"+ary[i].des+"</li>";
    	    	  	}
        	    	if(lis!="") {
        	    		$("#listLogInfo").html(lis);
        	    	}
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    //var htm = $(".flow_right").html();
                    //$(".flow_right").html("<p>物流动态</p>" + htm);
                }
            });
        } else {
            //var htm = $(".flow_right").html();
            //$(".flow_right").html("<p>物流动态</p>" + htm);
        }
        $("#showlistlogInfo").show();
        $(".popup_bg").show();
        //$(".wl_xx").show();
    }
    function go2Sign(){
    	$.ajax({
            url: jsBasePath+"/returnorder/updateGoodsStatus.json?returnOrderId=" + jsReturnorderId,
            type: "GET",
            dataType: "json",
            async: true,
            success: function (data) {
                window.location.reload();
            },
            error: function () {
            }
        });
    }