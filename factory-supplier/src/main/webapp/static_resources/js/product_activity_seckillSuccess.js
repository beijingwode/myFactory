

$(document).ready(function(){
	selectedHeaderLi("hdgl_header");
	
});


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
	 var price = parseFloat(preferentialNum)*parseFloat(oldPrice)/10;
	 $("#priceSpan").html(price);
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
	
	if(maxQuantity>joinQuantity){
		$("#maxQuantity").val(joinQuantity);
	}
}
