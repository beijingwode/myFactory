    function removeActivityQicaiRow(obj) {
    	$(obj).parent().parent().remove(); 
    }
    
    var skuItemValues;
    function addActivityQicaiRow() {
    	refreshActivityQicaiRow();
    	var isDiscount = 1;
    	if($("#chkActivityDiscount").is(':checked')){
    		isDiscount = 2;
    	}
    	var activityQicaiRowCnt = $("#activityQicaiRowCnt").val();
    	activityQicaiRowCnt++;
    	$("#activityQicaiRowCnt").val(activityQicaiRowCnt);
    	
    	var html = "";
    	html +='<tr>';
    	html +='<td><input name="ladder-num-'+activityQicaiRowCnt+'" style="width: 50px;height:22px;" type="text" isnum="1" isnull="1" onkeyup="this.value=this.value.replace(/\\D/g,\'\');"  />件以上，内购价<input name="ladder-price-'+activityQicaiRowCnt+'" style="width: 50px;height:22px;" isnum="1" isnull="1" type="text" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');" /> 元/件</td>';
    	var td="";
		for(j=0;j<myArray.length;j++) {
			td +='<input onclick="onCheckLadderSkuValue(this)" name="ladder-box-'+activityQicaiRowCnt+'" type="checkbox" value="'+ myArray[j] +'" id="chk_qc_'+activityQicaiRowCnt+'_'+j+'" checked="checked">';
			td +='<label for="chk_qc_'+activityQicaiRowCnt+'_'+j+'">'+myArray[j]+'</label>&nbsp;&nbsp;';
		}
		html +='<td>'+td+'</td>';
		html +='<td><div onclick="removeActivityQicaiRow(this);"><a href="javascript:;">删除</a></div></td>';
    	html +='<tr>';
    	
    	html +="</tr>";
    	
    	if(isDiscount==2){
    		html = html.replace("内购价","").replace("元/件","折");
    	}
    	$("#dealLadderTable").append(html);
    }
    

	function refreshActivityQicaiRow() {
   	 	myArray=getSkuItemValues();
   	 	var mapRowSku = new Map();
   	 	var activityQicaiRowCnt = $("#activityQicaiRowCnt").val();
   	 	
   	 	for(i=1;i<=activityQicaiRowCnt;i++) {
   	 		var skus=$("#dealLadderTable").find("input[name='ladder-box-"+i+"']:checked");
   	 		skus.each(function(){
				if($(this).val()!=''){
					mapRowSku.put("ladder-box-"+i+"_"+$(this).val(),"ladder-box-"+i);
				}
   	 		});
   	 	}
   	 	for(i=1;i<=activityQicaiRowCnt;i++) {
   	 		var ruleTd=$("#dealLadderTable").find("input[name='ladder-num-"+i+"']").parent();
   	 		if(ruleTd.length>0) {
   	 			var html="";
   	 			for(j=0;j<myArray.length;j++) {
   	   	 			html +='<input  onclick="onCheckLadderSkuValue(this)" name="ladder-box-'+i+'" type="checkbox" value="'+ myArray[j] +'" id="chk_qc_'+i+'_'+j+'">';
   	   	 			html +='<label for="chk_qc_'+i+'_'+j+'">'+myArray[j]+'</label>&nbsp;&nbsp;';
   	 			}
   	 			$(ruleTd).next().html(html);
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
    }
    
	/**
	*skuchange
	*/
	function getSkuItemValues(){
		var myArray=new Array();
		var aIndex = -1;
		var isSimple = $("#rdType1").prop("checked");
				
		var $trlist =$("#specificationTable tr");
		if($trlist!=null&&$trlist!=''&&$trlist.length>0){
			$trlist.each(function(i,val){
				if(i>=2) {
					aIndex++;
					var $tr =$($trlist[i]);
					if(!isSimple) {
						myArray[aIndex] =  $tr.find("[name=specifications_result]").attr("id");
						myArray[aIndex] =  myArray[aIndex].replace(",","/")
					} else {
						myArray[aIndex] = $tr.find("[name=color]").val();
					}
				}
			});
		}
		return myArray;
	}
	
    function onCheckLadderSkuValue(obj){
    	var flag = true;
    	$(obj).siblings().each(function(){
    		if ($(this).attr('checked')) {
    			flag =false;
    		}
    	});
    	if(flag){
    		$(obj).attr("checked", true);
    		showInfoBox("设置阶梯价时必须选择一个sku！");
    	}
    }
    
    //查询sku是否都没有选中都没选中需要给提示
	function checkAllLadderSku(){
		var allFlag = false;
		if($("#dealLadderTable").find("tr").length !=0){//这种情况是显示了阶梯价
			$("#dealLadderTable").find("tr").each(function(){//循环所有的tr行去找，tr行中td第二列中所有的复选框
				    if($(this).find("td").eq(1).find("input[type='checkbox']").length !=0 || $(this).find("td").length !=0){//如果有复选框的则进入循环没有的不循环
				    	var flag = false;//默认都没选中标志位
				    	$(this).find("td").eq(1).find("input[type='checkbox']").each(function(){
				    		if ($(this).attr('checked')) {//如果有选中的标志位置为true
				    			flag =true;
				    		}
				    	});
				    	if(!flag){//如果等于false 证明这一行都没有选中的，给提示
				    		allFlag = true;
				    		showInfoBox("设置阶梯价时必须选择一个sku！");
				    		return true;
				    	}
				    }
	    	});
		}
		return allFlag;
	}
	
	function clickActivityDiscount(){
		if($("#chkActivityDiscount").prop("checked")) {
			$("#chkActivityDiscount").val(1);
			if($("#dealLadderTable").find("tr").length !=0){
				$("#dealLadderTable").find("tr").each(function(){
					var html=$(this).find("td:first").html();
					if(html && html.indexOf("内购价")!=-1){
						html = html.replace("内购价","").replace("元/件","折");
						$(this).find("td:first").html(html);
					}
				});
			}
		} else {
			$("#chkActivityDiscount").val(0);
			if($("#dealLadderTable").find("tr").length !=0){
				$("#dealLadderTable").find("tr").each(function(){
					var html=$(this).find("td:first").html();
					if(html && html.indexOf("件以上，")!=-1 && html.indexOf("折")!=-1){
						html = html.replace("件以上，","件以上，内购价").replace("折","元/件");
						$(this).find("td:first").html(html);
					}
				});
			}
		}
	}