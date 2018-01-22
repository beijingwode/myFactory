/**
 * 图片选择
 */
function fileSelect(i,that) {
	$("#avatar" + $(that).attr("data-index")).click();
    //$("#avatar").click(); 
    $("#picLieq"+$(that).attr("data-index")).val(i); 
}
function updateImg(self) {
	var file = self.files[0];
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
                var formData = new FormData();
                formData.append("file", dataURItoBlob(base641),"upload1.jpg");
            	uploadImg(formData,$(self).attr("data-index"));
            });
        })(file);
    }else{
    	var formData = new FormData();
    	formData.append('file', file);
    	uploadImg(formData,$(self).attr("data-index"));
    }
}
function uploadImg(data,flag){
	$.ajax({
		url : jsBasePath + 'order/uploadReturnEvidence.user?uid=' + $("#userId").val(),
		type : "POST",
		async : true,
		contentType : false, //这个一定要写
		processData : false,
		cache : false,
		data : data,
		success : function(data) {
			if (data.success) {
				var i= $("#picLieq"+flag).val();
				var imgPath = data.data;
				var img = [];
				img = imgPath.split(",");
				var html ='';
				for(var j=0;j<img.length;j++){
					html +='<input type="hidden"  name="pic_url" value="'+img[j]+'">';
					html +='<img src="'+img[j]+'" alt="" onclick="fileSelect('+i+',this)" data-index="'+flag+'">';
				}
				$("#feedback_pic"+flag+" li:eq("+i+")").html(html);

				html ='';
				html +='<div class="pic_up">';
				if(i=="0") {
					html +='<a href="javascript:void(0);" onclick="fileSelect(1,this)" data-index="'+flag+'"><span>+</span><p>上传凭证<br />最多3张</p></a>';
				} else if(i=="1") {
					html +='<a href="javascript:void(0);" onclick="fileSelect(2,this)" data-index="'+flag+'"><span>+</span><p>上传凭证<br />最多3张</p></a>';
				}
				html +='</div>';
				
				if(i=="0") {
					if($("#feedback_pic"+flag+"  li:eq(1)").html() == "") {
						$("#feedback_pic"+flag+"  li:eq(1)").html(html);
					}
				} else if(i=="1") {
					if($("#feedback_pic"+flag+"  li:eq(2)").html() == "") {
						$("#feedback_pic"+flag+"  li:eq(2)").html(html);
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