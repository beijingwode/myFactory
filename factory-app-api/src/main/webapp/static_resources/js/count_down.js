if($("#miaoShaEndDate").length > 0 && $("#miaoShaEndDate").val()!=""){
	Countdown();
}
var day,hour,min,sec;
function Countdown() {
    count()
};
  
		function count() {
			//var _this = this,
               // start = new Date().getTime();
			handleCount(timer);
            var timer = setInterval(function() {
                handleCount(timer);
            }, 1000);

            //_this.handleCount(timer);
		}
		
		function handleCount(timer) {
            //var remain = this.getRemain();
            //this.handleHtml();
            var EndTime_id_val=$("#miaoShaEndDate").val();
    		var arr = EndTime_id_val.split("-");
    		var EndTime= new Date(arr[0],parseInt(arr[1])-1,arr[2],arr[3],arr[4],arr[5]);
            var start = new Date().getTime(),
                end = EndTime.getTime();
               
            var remain = end - start,
                remain = remain <= 0 ? 0 : remain,
                day = Math.floor(remain / (24 * 60 * 60 * 1000)),
                hour = Math.floor(remain / (60 * 60 * 1000) % 24),
                min = Math.floor(remain / (60 * 1000) % 60),
                sec = Math.floor(remain / 1000 % 60);

            day = addZero(day);//天
            hour = addZero(hour);//时
            min = addZero(min);//分
            sec = addZero(sec);//秒
            
            this.separateStr(day,"day");
            this.separateStr(hour,"hour");
            this.separateStr(min,"min");
            this.separateStr(sec,"sec");
            
            if (remain <= 0) {
                clearInterval(timer);
                $(".countdown").hide();
            }
        }
		
        function getRemain() {
        	var EndTime_id_val=$("#miaoShaEndDate").val();
    		var arr = EndTime_id_val.split("-");
    		var EndTime= new Date(arr[0],parseInt(arr[1])-1,arr[2],arr[3],arr[4],arr[5]);
            var start = new Date().getTime(),
                end = EndTime.getTime();
               
            var remain = end - start,
                remain = remain <= 0 ? 0 : remain,
                day = Math.floor(remain / (24 * 60 * 60 * 1000)),
                hour = Math.floor(remain / (60 * 60 * 1000) % 24),
                min = Math.floor(remain / (60 * 1000) % 60),
                sec = Math.floor(remain / 1000 % 60);

            day = addZero(day);//天
            hour = addZero(hour);//时
            min = addZero(min);//分
            sec = addZero(sec);//秒

            return remain;
        }
        function addZero(num, digit) {
            var numStr = num.toString(),
                digit = digit ? digit : 2;

            for (var i = numStr.length; i < digit; i++) {
                numStr = '0' + numStr;
            };

            return numStr;
		}
        function handleHtml() {
        	this.separateStr(day,"day");
            this.separateStr(hour,"hour");
            this.separateStr(min,"min");
            this.separateStr(sec,"sec");
        }
        function separateStr(str,special) {
        	/*if(str==0){
        		str="00";
        	}*/
            var arry = str.toString().split('');
            for (var i = 0; i < arry.length; i++) {
            	$("#"+special+i).html(arry[i])
            };

        }

/*html.append("<div class=\"bit\">0</div>");
html.append("<div class=\"bit\" style=\"margin:0\">1</div>");

html.append("<div class=\"bit\">0</div>");
html.append("<div class=\"bit\" style=\"margin:0\">0</div>");

html.append("<div class=\"bit\">1</div>");
html.append("<div class=\"bit\" style=\"margin:0\">6</div>");

html.append("<div class=\"bit red_bg\">5</div>");
html.append("<div class=\"bit red_bg\" style=\"margin:0\">0</div> ");*/