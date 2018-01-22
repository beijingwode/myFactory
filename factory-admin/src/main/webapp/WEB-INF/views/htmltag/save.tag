@var formId = formId!'save-form'; //form表单id
@var reloadUrl = reloadUrl!'false'; //是否url刷新
@var reloadPage = reloadPage!'false'; //是否刷新当前页面
@var isHide = isHide!'no'; //是否隐藏按钮 默认no
@var subBtnId = subBtnId!(formId+'-save');
@var validId = validId!(strutil.replace(formId,"-",""));
@var config = config!'false';
@var data = data!'';
@var okText = okText!'确 定';

@if(isHide == "no"){
<div class="width-100 clearfix" tag-save-btn style="bottom: 0px;left: 0px;position:absolute;">
	<span class="btn btn-primary btn-sm bigger-110 width-50  pull-left"  id="${subBtnId}">
		<i class="ace-icon fa fa-floppy-o align-top bigger-125"></i> ${okText}
	</span>
	<span class="btn btn-yellow btn-sm bigger-110 width-50 pull-right" id="${formId}-cancel">
		<i class="ace-icon fa fa-times align-top  bigger-125"></i> 取 消
	</span>
</div>
@}
<script type="text/javascript">
$(function(){
	
var ${validId} = $("#${formId}").Validform({
		ajaxPost : true,
		beforeSubmit:function(curform){
			var loadi = layer.load(5,2);
			$("#${formId}").data('loadi',loadi);
		},
		callback:function(data){
			layer.close($("#${formId}").data('loadi'));
			if(data>0||data.success) {
				layer.msg('操作成功', 1, 1,function(){
					if("${reloadUrl}" == "none"){
						
					} else {
						if("${reloadUrl}" == "true"){
							location.reload();
						}else if ("${reloadPage}" == "true"){
							var $form = $("#search-form")
							var pageNoInput = $form.find('input[name="pageNum"]');
							if(pageNoInput && pageNoInput.length>0) {
								paging('search-form',pageNoInput.val());
							}
						}else{
							$curmenu.trigger('click');
						}
						layer.closeAll();
					}
				});
			}else if(data==-1){
				layer.msg('已经达到上限,请修改所属位置中的最大数量', 3, 1);
			}else if(data.msg||data<=0){
				layer.msg('<span class="red bigger-120">保存失败:'+data.msg+'</span>', 2, 8);
			}
		},
		tiptype : function(msg, o, cssctl) {
			if (!o.obj.is("form")) {
				if (o.type != 2) {
					tip.errorTip(msg,o.obj);
				}
			}
		},
		tipSweep : false,
	});

	$("#${subBtnId}").click(function() {
		if("${config}" == "true"){
			${tagBody!}
		}
		${validId}.submitForm(false);
		return false;
	}); 
	
	$("#${formId}-cancel").click(function(){
		layer.closeAll();
		return false;
	});
	
	$("#${formId} input,#${formId} textarea").blur(function(){
		layer.closeTips();
		return false;
	})

});
</script>