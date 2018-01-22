
var uid = GetUidCookie();
$(function(){
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	//顶部切换
	$(".top_tab li").each(function(index){
	$(this).click(function(){//先把所有的隐藏掉
		if(($(this).find("a")[0].title)==2){//仅退款
			if(typeof(returnStatus)!=undefined && returnStatus!=null && returnStatus!=''){
				if(returnStatus=='6'){
					showInfoBox("此订单已发出退货,不能修改类型");
					return;
				}
			}
		}
			$(".box_con").addClass("dis_none");
			$(".box_con:eq("+index+")").removeClass("dis_none");//在把对应的索引的显示
		
			$(".top_tab li").removeClass("clickthis");
			$(this).addClass("clickthis");
		});
	});
	
	//点击显示原因
	$(".tit1 a").toggle(
	  function(){
		$(".con1").show();
	  },
	  function(){
		$(".con1").hide();
	  }
	); 
	$(".tit2 a").toggle(
	  function(){
		$(".con2").show();
	  },
	  function(){
		$(".con2").hide();
	  }
	); 
	$(".tit3 a").toggle(
	  function(){
		$(".con3").show();
	  },
	  function(){
		$(".con3").hide();
	  }
	); 
	
	//原因选项
	$(".con1 ul li").each(function(index){
	$(this).click(function(){//先把所有的隐藏掉
			
			$(".con1 ul li").removeClass("li_this");
			$(this).addClass("li_this");
		});
	});
	
	$(".con2 ul li").each(function(index){
	$(this).click(function(){//先把所有的隐藏掉
			
			$(".con2 ul li").removeClass("li_this");
			$(this).addClass("li_this");
		});
	});
	
	$(".con3 ul li").each(function(index){
	$(this).click(function(){//先把所有的隐藏掉
			
			$(".con3 ul li").removeClass("li_this");
			$(this).addClass("li_this");
		});
	});
})
function go2Submit(){
	if(uid=="") return;
	var text=$(".clickthis a").text();
	var type='';
	var reason='';
	var goodsStatus='';
	var returnPrice='';
	var note=$("#note").val();
	note=encodeURIComponent(note);
	var imagePath1=$("input[name='pic_url']:eq(0)").val();
	var imagePath2=$("input[name='pic_url']:eq(1)").val();
	var imagePath3=$("input[name='pic_url']:eq(2)").val();
	if (text=='退货退款') {
		type=0;
		reason=$(".li_this").text();
		returnPrice=$("#returnPrice").val();
	}else if(text=='仅退款'){
		$(".con1 ul li").removeClass("li_this");
		type=1;
		goodsStatus=$(".li_this").val()+'';
		var text2=$(".li_this").text();
		reason=text2.slice(4);
		returnPrice=$("#returnPrice2").val();
	}
	/*var returnPrice=$("#returnPrice").val();
	var note=$("#note").val();*/
	if (type==1&&(goodsStatus!="1"&&goodsStatus!="-1")) {
			showInfoBox("请选择货物状态");
	}else if (reason==''||typeof(reason)==undefined) {
		showInfoBox("请选择退款原因");
	}else if(returnPrice==''||typeof(returnPrice)==undefined||parseFloat(returnPrice)>parseFloat(realPrice)){
		showInfoBox("请您查看退款金额");
	}else{
		if (goodsStatus=="-1") {
			goodsStatus=0;
		}
		if((refundOrderId!='' && refundOrderId)||(returnOrderId!='' && returnOrderId)){
			var status=$("#returnStatus").val();
			$.ajax({
				url :jsBasePath+'order/updateReturnOrder.user?uid='+uid+'&subOrderId='+subOrderId+'&returnOrderId='+returnOrderId+'&refundOrderId='+refundOrderId+'&returnPrice='+returnPrice+'&reason='+reason+'&note='+note
					+'&goodsStatus='+goodsStatus+'&type='+type+'&imagePath1='+imagePath1+'&imagePath2='+imagePath2+'&imagePath3='+imagePath3+'&status='+status,
				type : "GET",
				dataType: "json",  //返回json格式的数据  
				async: false,
				cache:false,
				success : function(data) {
					if (data.success) {
						history.back();
						//window.location=jsBasePath +'orderM?subOrderId='+subOrderId;
					}else{
						showInfoBox(data.msg);
					}
				}
			});
		}else{
			$.ajax({
				url :jsBasePath+'order/applyReturn.user?uid='+uid+'&subOrderId='+subOrderId+'&returnPrice='+returnPrice+'&reason='+reason+'&note='+note
					+'&goodsStatus='+goodsStatus+'&type='+type+'&imagePath1='+imagePath1+'&imagePath2='+imagePath2+'&imagePath3='+imagePath3,
				type : "GET",
				dataType: "json",  //返回json格式的数据  
				async: false,
				cache:false,
				success : function(data) {
					if (data.success) {
						history.back();
						//window.location=jsBasePath +'orderM?subOrderId='+subOrderId;
					}else{
						showInfoBox(data.msg);
					}
				}
			});
		}
	}
}
function go2Cancle(){
	history.back();
	//window.location=jsBasePath +'orderM?subOrderId='+subOrderId;
}

/**
 * 图片选择
 */
function fileSelect(i) {
    $("#avatar").click(); 
    $("#picLieq").val(i); 
}
function updateImg(self) {
	var file = self.files[0];
	if (file.size > 1024 * 1024 * 2) {
        console.log("图片大于2M，开始进行压缩...");
        (function(img) {
            var mpImg = new MegaPixImage(img);
            var resImg = document.getElementById("resultImage");
            resImg.file = img;
            mpImg.render(resImg, { maxWidth: 500, maxHeight: 500, quality: 1 }, function() {
                //var base64 = getBase64Image(resImg);
                var base641 = resImg.src;
               // console.log("base64", base64.length, simpleSize(base64.length), base641.length, simpleSize(base641.length));
                var formData = new FormData();
                formData.append("file", dataURItoBlob(base641),"upload.jpg");
            	uploadImg(formData);
            });
        })(file);
    }else{
    	var formData = new FormData();
    	formData.append('file', file);
    	uploadImg(formData);
    }
}
function dataURItoBlob(dataUrl) {
    var byteString = atob(dataUrl.split(',')[1]);

    var mimeString = dataUrl.split(',')[0].split(':')[1].split(';')[0];

    var ab = new ArrayBuffer(byteString.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }
    return new Blob([ab], { type: 'image/jpeg' });
}
function uploadImg(data){
	if(uid=='') return;
	//var formData = new FormData();
    //formData.append("file", data);
	$.ajax({
		url : jsBasePath + 'order/uploadReturnEvidence.user?uid=' + uid,
		type : "POST",
		async : true,
		contentType : false, //这个一定要写
		processData : false,
		cache : false,
		data : data,
		success : function(data) {
			if (data.success) {
				var i= $("#picLieq").val();
				var imgPath = data.data;
				var html ='';
					html +='<input type="hidden"  name="pic_url" value="'+imgPath+'">';
					html +='<img src="'+imgPath+'" alt="" onclick="fileSelect('+i+')">';
				$(".feedback_pic li:eq("+i+")").html(html);
				html ='';
				html +='<div class="pic_up">';
				if(i=="0") {
					html +='<a href="javascript:fileSelect(1);"><span>+</span><p>上传凭证<br />最多3张</p></a>';
				} else if(i=="1") {
					html +='<a href="javascript:fileSelect(2);"><span>+</span><p>上传凭证<br />最多3张</p></a>';
				}
				html +='</div>';
				
				if(i=="0") {
					if($(".feedback_pic li:eq(1)").html() == "") {
						$(".feedback_pic li:eq(1)").html(html);
					}
				} else if(i=="1") {
					if($(".feedback_pic li:eq(2)").html() == "") {
						$(".feedback_pic li:eq(2)").html(html);
					}
				}
			} else {
				showInfoBox(data.msg);
			}
		},
		error : function(e) {
			showInfoBox("图片大小不能超过200k ");
		}
	});
}
