function uploadImg(target,fileId,url) {
	var spinner = new Spinner({color: '#3d9bce',width:20,radius:20}).spin(target[0]);
	$.ajaxFileUpload({
		url : url,
		type : 'post',
		secureuri : true, //一般设置为false
		fileElementId : fileId, // 上传文件的id、name属性名 
		dataType : 'json', //返回值类型，一般设置为json、application/json
		success : function(data, status) {
			//关闭spinner  
			spinner.spin();
			var imgPath = "http://" + data.data[0];
			$(".Img").attr("src", imgPath);
			$(".heddenImg").attr("value", imgPath);
			$("#firmlogo").attr("src", imgPath);
		}
	});
}

function uploadImg1(target,fileId,url) {
	var spinner = new Spinner({color: '#3d9bce',width:20,radius:20}).spin(target[0]);
	$.ajaxFileUpload({
		url : url,
		type : 'post',
		secureuri : true, //一般设置为false
		fileElementId : fileId, // 上传文件的id、name属性名 
		dataType : 'json', //返回值类型，一般设置为json、application/json
		success : function(data, status) {
			//关闭spinner  
			spinner.spin();
			var imgPath = "http://" + data.data[0];
			$(".Img").attr("src", imgPath);
			$(".heddenImg1").attr("value", imgPath);
			$("#firmlogo1").attr("src", imgPath);
		}
	});
}

function uploadImg2(target,fileId,url) {
	var spinner = new Spinner({color: '#3d9bce',width:20,radius:20}).spin(target[0]);
	$.ajaxFileUpload({
		url : url,
		type : 'post',
		secureuri : true, //一般设置为false
		fileElementId : fileId, // 上传文件的id、name属性名 
		dataType : 'json', //返回值类型，一般设置为json、application/json
		success : function(data, status) {
			//关闭spinner  
			spinner.spin();
			var imgPath = "http://" + data.data[0];
			$(".Img").attr("src", imgPath);
			$(".heddenImg2").attr("value", imgPath);
			$("#firmlogo2").attr("src", imgPath);
		}
	});
}