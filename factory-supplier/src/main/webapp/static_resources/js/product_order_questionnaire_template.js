
var ten=["一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五","十六","十七","十八","十九","二十","二十一","二十二","二十三","二十四","二十五","二十六","二十七","二十八","二十九","三十","三十一","三十二","三十三","三十四","三十五","三十六","三十七","三十八","三十九","四十","四十一","四十二","四十三","四十四","四十五","四十六","四十七","四十八","四十九","五十","五十一","五十二","五十三","五十四","五十五","五十六","五十七","五十八","五十九","六十","六十一","六十二","六十三","六十四","六十五","六十六","六十七","六十八","六十九","七十","七十一","七十二","七十三","七十四","七十五","七十六","七十七","七十八","七十九","八十","八十一","八十二","八十三","八十四","八十五","八十六","八十七","八十八","八十九","九十","九十一","九十二","九十三","九十四","九十五","九十六","九十七","九十八","九十九"];
$(document).ready(function() {
	selectedHeaderLi("ddgl_header");
	
	var qs = $(".option_setting");
	if(qs.length>0) {
		refreshQuestion()
		hideAllQ();	
		showQ($(".option_setting:eq(0)"));
		
	} else {
		appendQustion(0);
	}
	initUld();
});


function addQuestion(){
	if(checkAllQ()) {
		hideAllQ();
		var qs = $(".option_setting");
		
		//voteWarnShow();
		if(qs.length>=99) {
			showInfoBox("最多只能添加99个问题");
			return;
		}
		
		appendQustion(qs.length);
		initUld();
	}
}



// 添加问题
function appendQustion(i) {
	var html = '';
	html += '<div class="vote_meta option_setting js_question" id="inpId">';
	
	// group
	html += ' <div class="vote_meta_title group">';
	html += '  <div class="vote_meta_title_opr">';
	html += '   <a href="javascript:togQuestion('+i+');" class="js_question_edit" data-tag="'+i+'">收起</a>';
	if(i>0) {
		html += '   <a href="javascript:delQuestion('+i+');" class="js_question_delete" data-tag="'+i+'">删除</a>';
	}
	html += '  </div>';
	html += '  <span class="vote_warn" style="display:none">问题填写完整才能添加下一个问题</span>';
	html += '  <span class="vote_num">问题'+ten[i]+'</span>';
	html += '  <span class="vote_question js_vote_question"></span>';
	html += ' </div>';
	
	// vote_meta_content
	html += ' <div class="vote_meta js_item_container vote_meta_content" style="display:">';
	
	// vote_meta_detail
	html += '  <div class="vote_meta_detail">';
	html += '   <div class="frm_control_group">';
	html += '    <label for="" class="frm_label">标题</label>';
	html += '    <div class="frm_controls">';
	html += '     <span class="frm_input_box with_counter counter_in append vote_title js_question_title count">';
	html += '      <input autofocus type="text" placeholder="" class="frm_input js_option_input" id="" maxlength="35" oninput="checkNum (this)" onpropertychange="checkNum (this)" name="questionTitle" value=""  ><em class="frm_input_append frm_counter">0/35</em>';
	html += '     </span>';
	html += '     <span class="frm_tips"></span>';
	html += '    </div>';
	html += '   </div>';
	html += '  </div>';
	
	// vote_meta_radio
	html += '  <div class="vote_meta_detail js_vote_type vote_meta_radio">';
	html += '   <div class="frm_control_group">';
	html += '    <div class="frm_controls vote_meta_radio">';
	html += '     <label class="vote_radio_label selected frm_radio_label" for="checkbox'+(i*2)+'">';
	html += '      <input name="selectType'+i+'" type="radio" value="1" class="vote_radio frm_radio" checked="checked" id="checkbox'+(i*2)+'">';
	html += '      <span type="label_content">单选</span>';
	html += '     </label>';
	html += '     <label class="vote_radio_label frm_radio_label" for="checkbox'+(i*2+1)+'">';
	html += '      <input name="selectType'+i+'" type="radio" value="2" class="vote_radio frm_radio" id="checkbox'+(i*2+1)+'">';
	html += '      <span type="label_content">多选</span>';
	html += '     </label>';
	html += '    </div>';
	html += '   </div>';
	html += '  </div>';

	// js_vote_option
	html += appendOptionStr(i,0);
	// js_vote_option
	html += appendOptionStr(i,1);
	// js_vote_option
	html += appendOptionStr(i,2);
	
	// tips_wrp
	html += '  <div class="vote_meta_detail tips_wrp">';
	html += '   <p id="voteAdd" class="tips_global option_tips">';
	html += '    <a href="javascript:appendOption('+i+');" class="js_add_item" data-tag="'+i+'">添加选项</a>';
	html += '   </p>';
	html += '   <p id="voteFull" class="tips_global option_tips" style="display:none;">选项已满，不可继续添加</p>';
	html += '  </div>';
	
	//
	html += ' </div>';

	html += '</div>';
	
	$(".js_question_container").append(html);
}



//添加选项 html
function appendOptionStr(i,j) {
	var html = '';
	// js_vote_option
	html += '  <div class="vote_meta_detail js_vote_option" >';
	html += '   <div class="frm_control_group">';
	html += '    <div class="frm_label">选项'+ten[j]+'</div>';
	html += '    <div class="frm_controls">';
	html += '     <div class="frm_ht">';
	html += '      <span class="frm_input_box with_counter counter_in append count">';
	html += '       <input type="text" placeholder="" class="frm_input js_option_input" name="option'+j+'" value="" id="" maxlength="35" oninput="checkNum (this)" onpropertychange="checkNum (this)"><em class="frm_input_append frm_counter">0/35</em>';
	html += '      </span>';
	html += '      <div class="upload_area webuploader-container">';
	html += '       <a class="btn btn_upload js_vote_upload_btn webuploader-pick" id="js_upload_'+i+'_'+j+'">上传图片</a>';
	if(j>1) {
	 html += '       <a href="javascript:delOptin('+i+','+j+');" class="link_delete js_delete_item" data-tag="'+i+'" data-item="'+j+'">删除选项</a>';
	}
	html += '       <span class="frm_tips"></span>';
	html += '      </div>';
	html += '     </div>';
	html += '     <div class="js_img_container img_container" id="c_js_upload_'+i+'_'+j+'" style="display:none">';
	html += '      <span class="img_panel">';
	html += '       <span class="js_img_preview preview bg_img poi" data-src="" style="background-image:url();"></span>';
	html += '      </span>';
	html += '      <a href="javascript:;" class="link_dele" id="js_delete_'+i+'_'+j+'">删除</a>';
	html += '     </div>';
	html += '    </div>';
	html += '   </div>';
	html += '  </div>';
	
	return html
}

// 添加选项
function appendOption(i) {
	
	var q = $(".option_setting:eq("+i+")");
	
	var js_vote_options = q.find(".js_vote_option");
	var tips_wrp = q.find(".tips_wrp");
	
	if(js_vote_options.length>=10) {
		showInfoBox("最多只能添加10个选项");
		return;
	}
	
	tips_wrp.before(appendOptionStr(i,js_vote_options.length));
	initUld();
}

//删除选项
function delOptin(i,j) {
	var q = $(".option_setting:eq("+i+")");
	q.find(".js_vote_option:eq("+j+")").remove();
	refreshOption(i);
	initUld();
}

// 刷新选项，重新排序
function refreshOption(i) {
	var q = $(".option_setting:eq("+i+")");
	var js_vote_options = q.find(".js_vote_option");
	
	for(j=0;j<js_vote_options.length;j++) {
		var js_vote_option = $(js_vote_options[j]);
		js_vote_option.find(".frm_label").html("选项"+ten[j]);
		js_vote_option.find(".js_option_input").attr("name" ,"option"+j);
		js_vote_option.find(".webuploader-pick").attr("id" ,'js_upload_'+i+'_'+j);
		if(j>1) {
		 js_vote_option.find(".js_delete_item").attr("href" ,'javascript:delOptin('+i+','+j+');');
		 js_vote_option.find(".js_delete_item").attr("data-tag" ,i);
		 js_vote_option.find(".js_delete_item").attr("data-item" ,j);
		}
		js_vote_option.find(".img_container").attr("id" ,'c_js_upload_'+i+'_'+j);
		js_vote_option.find(".link_dele").attr("id" ,'js_delete_'+i+'_'+j);
	}
}


//删除问题
function delQuestion(i) {
	$(".option_setting:eq("+i+")").remove();
	refreshQuestion();

	initUld();
}

//刷新问题，重新排序
function refreshQuestion() {
	var qs = $(".option_setting");
	for(i=0;i<qs.length;i++) {
		var option_setting = $(qs[i]);
		option_setting.find(".js_question_edit").attr("href" ,'javascript:togQuestion('+i+');');
		option_setting.find(".js_question_edit").attr("data-tag" ,i);

		if(i>0) {
		option_setting.find(".js_question_delete").attr("href" ,'javascript:delQuestion('+i+');');
		option_setting.find(".js_question_delete").attr("data-tag" ,i);
		}
		option_setting.find(".vote_num").html("问题"+ten[i]);
		option_setting.find(".frm_radio_label:eq(0)").attr("for","checkbox"+(i*2));
		option_setting.find(".frm_radio:eq(0)").attr("name","selectType"+(i));
		option_setting.find(".frm_radio:eq(0)").attr("id","checkbox"+(i*2));
		option_setting.find(".frm_radio_label:eq(1)").attr("for","checkbox"+(i*2+1));
		option_setting.find(".frm_radio:eq(1)").attr("name","selectType"+(i));
		option_setting.find(".frm_radio:eq(1)").attr("id","checkbox"+(i*2+1));
		
		refreshOption(i);

		option_setting.find(".js_add_item").attr("href","javascript:appendOption("+i+");");
		option_setting.find(".js_add_item").attr("data-tag",i);
	}
}

function initUld() {
	
	var btns = $(".js_vote_upload_btn");
	for(i=0;i<btns.length;i++) {
		$(btns[i]).attr("href" ,"javascript:toUpload('"+$(btns[i]).attr("id")+"');");
	}

	var dels = $(".link_dele");
	for(i=0;i<dels.length;i++) {
		$(dels[i]).attr("href" ,"javascript:delImg('"+$(dels[i]).attr("id")+"');");
	}

	
}
//隐藏切换
function togQuestion(i) {
	var q = $(".option_setting:eq("+i+")");

	if(q.find(".js_item_container").is(":hidden")){
		hideAllQ();	
		
		showQ(q);
	} else {
		hideQ(q);
	}
}

function hideAllQ() {
	var qs = $(".option_setting:visible");
	for(i=0;i<qs.length;i++) {
		hideQ($(qs[i]));
	}
}

function hideQ(q){
	q.find(".js_item_container").hide();
	q.find(".js_vote_question").html(q.find(".js_option_input").val());
	q.find(".js_question_edit").html("编辑");
	q.addClass("close_vote");
	$(".vote_meta_title").css({"border-bottom":"none"});
} 

function showQ(q){
	q.find(".js_item_container").show();
	q.find(".js_vote_question").html("");
	q.find(".js_question_edit").html("收起");
	q.removeClass("close_vote");
	$(".vote_meta_title").css({"border-bottom":"1px solid #e7e7eb"});
} 

var upd_id;
function toUpload(id) {
	upd_id = id;
	$("#uploadFile").click();
}

function delImg(id) {
	id=id.replace("js_delete_","js_upload_");	
	$("#"+id+"").html("上传图片");
	
	var img = $("#c_"+id);
	img.hide();
	img.find(".js_img_preview").attr("data-src","");
	img.find(".js_img_preview").attr("style","background-image:url();");
}

function fileUpload() {
	 var elementIds=["flag"]; //flag为id、name属性名
	 var ftype = $("#uploadFile").val();
	 if(ftype.length > 4) {
		 ftype = ftype.substring(ftype.length-3,ftype.length);
		 if(ftype=="pdf"||ftype=="PDF"||ftype=="Pdf") {
			 showInfoBox("请选择图片文件");
			 return;
		 }
	 } else {
		 showInfoBox("请选择图片文件");
		 return;
	 }
	 
   $.ajaxFileUpload({
       url: jsBasePath+'/upload/pic.json?folder='+jsUserId,
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
       		
   			 var url=imgsrc;
   			 if(isPdf(imgsrc)) {
   				 imgsrc=jsStaticResources+"images/pdf.png";
   			 } 
   			 
   			$("#"+upd_id+"").html("重新上传");
   			var img = $("#c_"+upd_id);
   			img.show();
   			img.find(".js_img_preview").attr("data-src",url);
   			img.find(".js_img_preview").attr("style","background-image:url('"+url+"');");
				
       	}else{
       		showInfoBox(data.msg);
       	}
       },
       error: function(data, status, e){ 
           alert(e);
       }
   });
}

function submit(f) {
	
	if(!checkAllQ()) {
		return;
	}
	
	$(".js_complete_bnt").attr("href","javascript:;");
    var data = {};
    data.id=$("input[name='id']").val();
    data.templateTitle=$("input[name='templateTitle']").val();
    data.testFlg=0;
    
    var listQuestion = [];
    var qs = $(".option_setting");
	for(i=0;i<qs.length;i++) {
		var option_setting = $(qs[i]);
		var q = {};
		q.questionTitle = option_setting.find("input[name='questionTitle']").val();
		q.selectType = option_setting.find("input[name='selectType"+i+"']:checked").val();
		q.orders = i;
		var listOption = [];
		

		var js_vote_options = option_setting.find(".js_vote_option");
		
		for(j=0;j<js_vote_options.length;j++) {
			var js_vote_option = $(js_vote_options[j]);
			var o = {};
			o.optionTitle = js_vote_option.find("input[name='option"+j+"']").val();
			o.image = js_vote_option.find(".js_img_preview").attr("data-src");
			o.orders = j;
			listOption.push(o);
		}
		q.listOption = listOption;
		listQuestion.push(q);
	}
    data.listQuestion=listQuestion;
    $.ajax({
        url: jsBasePath+'/questionnaire/aJaxSaveTemplate.json',
        type: "POST",
        dataType: "json",  //返回json格式的数据
        data: {"jsonStr":JSON.stringify(data)},  //返回json格式的数据
        async: true,
        success: function (data) {
        	if (data.success) {
                showInfoBox("保存成功");
                setTimeout(gotoList, 1500);
            } else {
                showInfoBox(data.msg);            	
            	$(".js_complete_bnt").attr("href","javascript:submit();");
            }
        }, error: function () {
        	$(".js_complete_bnt").attr("href","javascript:submit();");
        }
    });
    return;
}

/**
 * 检查问题输入
 * @param q
 * @returns {Boolean}
 */

function checkQuestionInput(q) {
	var rtn =true;

	// 检查问题标题
	var t = q.find("input[name='questionTitle']");
	t.val($.trim(t.val()));
	if(t.val() == ""){
		// 标题为空时显示错误提示
		if($(t.parent()).siblings(".frm_msg").length==0) {
			$(t.parent()).after('<p class="frm_msg fail" style="display: block;"><span for="question_title" class="frm_msg_content" style="display: inline;">请填写问题的标题</span></p>');
		}
		
		rtn=false;
	} else {
		// 删除错误提示
		$(t.parent()).siblings(".frm_msg").remove();
	}
	
	// 循环检查问题选项
	var ls = q.find(".js_option_input");
	for(var i=0;i<ls.length;i++) {
		var o = $(ls[i]);
		o.val($.trim(o.val()));
		if(o.val() == ""){
			// 选项为空时显示错误提示
			if($(o.parent()).siblings(".frm_msg").length==0) {
				$(o.parent()).next().after('<p class="frm_msg fail" style="display: block;"><span for="option1" class="frm_msg_content" style="display: inline;">请填写问题的选项</span></p>');
			}
			rtn=false;
		} else {
			// 删除错误提示
			$(o.parent()).siblings(".frm_msg").remove();
		}
	}
	
	
	
	
	if(!rtn) {
		// 内容不全时显示错误提示
		q.find(".vote_warn").show();
	} else {
		// 隐藏错误提示
		q.find(".vote_warn").hide();
	}
	
	return rtn;
}

/**
 * 检查所有输入项
 * @returns {Boolean}
 */
function checkAllQ() {
	var rtn=true;
	// 检查模板名称
	var f = $(".frm_control_group");
	var frm = f.find("input[name='templateTitle']");
	frm.val($.trim(frm.val()));
	if(frm.val() == ""){
		// 标题为空时显示错误提示
		if($(frm.parent()).siblings(".frm_msg").length==0) {
			$(frm.parent()).after('<p class="frm_msg fail" style="display: block;"><span for="question_title" class="frm_msg_content" style="display: inline;">请填写模板名称</span></p>');
		}
		
		rtn=false;
	} else {
		// 删除错误提示
		$(frm.parent()).siblings(".frm_msg").remove();
		
	}
	if(!rtn) return false;
	
	// 循环检查所有问题
	var qs = $(".option_setting");
	for(var i=0;i<qs.length;i++) {
		rtn = checkQuestionInput($(qs[i]));
	}
	
	return rtn;
}
function gotoList() {
	window.location = jsBasePath+'/questionnaire/templates.html';
}
function isPdf(str) {
	if(str==null || str=='' || str.length<5) return false;
	
	var ppostfix=str.substring(str.length-4);
	return ppostfix==".pdf" || ppostfix==".PDF";
}


function checkNum(obj){
	var i = obj.value.length;
	var em=$(obj).next();
	if (i<36) {
		em.html(i+"/35");
	}else{
		return;
	}
}





