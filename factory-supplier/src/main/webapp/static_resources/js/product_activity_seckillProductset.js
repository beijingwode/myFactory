
$(document).ready(function(){
	selectedHeaderLi("hdgl_header");
	preferentialNumChange();
});
	//非空验证
function validatorForm(){
	var ret = 0;
	if($("#preferentialNum").val()==''){
		alert("秒杀优惠价格不能为空");
		ret++;
		return false;
	}else{
		if($("#joinQuantity").val()==''){
			alert("活动商品数量不能为空");
			ret++;
			return false;
		}else{
			if($("#maxQuantity").val()==''){
				alert("限购数量不能为空");
				ret++;
				return false;
			}
		}
	}
	
	if(ret>0){
		return false;
	}else{
		return true;
	}
}

function toSet(){
	if(validatorForm()){
		$("#sub_form").submit();
	}
};

function preferentialNumChange(){
	var preferentialNum = $("#preferentialNum").val();
	var preferentialType = $("#preferentialType").val();
	var oldPrice = $("#oldPrice").val();
	if(preferentialNum==''){
		preferentialNum = 1;
		$("#preferentialNum").val("1");
	}
	
	if(preferentialType=='2'){
	 if(preferentialNum<0.1||preferentialNum>=10){
		 $("#preferentialNum").val("1");
	 }
	 
	 var price = parseFloat($("#preferentialNum").val())*parseFloat(oldPrice)/10;
	 $("#priceSpan").html(price.toFixed(2));
   }else{
	   $("#priceSpan").html(preferentialNum); 
   }
}

function joinQuantityChange(){
	var joinQuantity = $("#joinQuantity").val();
	if(joinQuantity==''){
		$("#joinQuantity").val("1");
	}
	var quantity = $("#quantity").val();
	if(quantity==''){
		quantity ='0';
	}
	if(parseInt(joinQuantity)>parseInt(quantity)){
		$("#joinQuantity").val(quantity);
	}
}

function maxQuantityChange(){
	var maxQuantity = $("#maxQuantity").val();
	if(maxQuantity==''){
		$("#maxQuantity").val("1");
	}
	var joinQuantity = $("#joinQuantity").val();
	if(joinQuantity==''){
		joinQuantity=1;
	}
	
	if(parseInt(maxQuantity)>parseInt(joinQuantity)){
		$("#maxQuantity").val(joinQuantity);
	}
}
