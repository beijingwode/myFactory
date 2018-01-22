//左右滑动
var mySwiper = new Swiper('.swiper-container',{
slidesPerView : 'auto',//'auto'
//slidesPerView : 3.7,
})

//倒计时
function GetRTime(){
	
	if($("#miaoShaEndDate").length > 0 && $("#miaoShaEndDate").val()!="" ){

		var EndTime_id_val=$("#miaoShaEndDate").val();
		var arr = EndTime_id_val.split("-");
		//var end = arr[0]+"-"+arr[1]+"-"+arr[2]+" "+arr[3]+":"+arr[4]+":"+arr[5];
		//var eTime = Date.parse(arr[0],);
			
		var EndTime= new Date(arr[0],parseInt(arr[1])-1,arr[2],arr[3],arr[4],arr[5]);
		var NowTime = new Date();

		if(EndTime>NowTime) {
		
			//if(EndTime<NowTime){			
			//	return false;
			//}
			var t =EndTime.getTime() - NowTime.getTime();
		    //var d=Math.floor(t/1000/60/60/24);
		    var h=Math.floor(t/1000/60/60);
		    var m=Math.floor(t/1000/60%60);
		    var s=Math.floor(t/1000%60);
		    
		   
		    if(h<10){
		    	$(".count_down #hour").html("0"+h);
		    }else if(h>99){
		    	$(".count_down #hour").html(h.toString().substring(0,2));
		    }else{
		    	$(".count_down #hour").html(h);
		    }
		    if(m<10){
		    	$(".count_down #min").html("0"+m);
		    }else{
		    	$(".count_down #min").html(m);
		    }
		    
		    if(s<10){
		    	$(".count_down #sec").html("0"+s);
		    }else{
		    	$(".count_down #sec").html(s);
		    }

			setTimeout("GetRTime()",1000);
		} else {
			//00
			
			$(".count_down #hour").html("00");
			$(".count_down #min").html("00");
			$(".count_down #sec").html("00");
			//clear
			$(".count_down").hide();
		}
		
	} else {
		//00
		
		$(".count_down ul li #hour").html("00");
		$(".count_down ul li #min").html("00");
		$(".count_down ul li #sec").html("00");
		//clear
		$(".count_down").hide();
	}
};

if($("#miaoShaEndDate").length > 0 && $("#miaoShaEndDate").val()!=""){
	GetRTime();
}