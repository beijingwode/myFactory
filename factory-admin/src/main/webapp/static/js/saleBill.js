

/**
 * 修改对账单的状态值
 * @param data  list集合数据
 * @param url  url路径
 */
function updateSaleBillStatus(data,url){
	$.ajax({
		dataType:'json',
		type:'POST',
		url:url,
		contentType: 'application/json',
		async: true,
		data:JSON.stringify(data),
		success:function(data){
			if(data.success){
				layer.msg('操作成功', 3, 1,function(){
					location.reload();
					layer.closeAll();
				});
			}else{
				layer.msg('修改失败', 3, 1,function(){
					location.reload();
					layer.closeAll();
				});
			}
		}
	});
}