
var width = window.innerWidth;
var link_width = parseInt(width *584/641);

function addCssByStyle(){  
	
	width = window.innerWidth;
	link_width = parseInt(width *584/641);
	var auto_style = "";
	
	//top
	var top_height = parseInt(width *90/641);
	var caption_width = parseInt(width *189/641);
	auto_style +="#top {height:"+ top_height +"px;}\n";
	auto_style +="#top h1 {height:"+ top_height +"px;background-size: "+ caption_width +"px; z-index:999}";
	
	//a_aleft
	var a_aleft_width = parseInt(width *44/641);
	var a_aleft_height = parseInt(width *45/641);
	var a_aleft_top = parseInt(width *24/641);
	var a_aleft_left = parseInt(width *23/641);
	auto_style +="#top a.aleft{width:"+a_aleft_width+"px; height:"+a_aleft_height+"px;top:"+a_aleft_top+"px; left:"+a_aleft_left+"px; }\n";
	auto_style +="#top a.aleft_detail{width:"+parseInt(width *22/640)+"px; height:"+parseInt(width *40/640)+"px;top:"+parseInt(width *25/640)+"px; left:"+a_aleft_left+"px; }\n";
	
	//box
	var f = width / 641;
	var top_space_h = window.innerHeight - parseInt((695 + 90 + 98) * f);
	top_space_h = parseInt(top_space_h /2)-2;
	
	if(top_space_h < 20*f) {
		top_space_h = parseInt(20*f);;
	}
	var margin_top32 = parseInt(f * 32);
	auto_style +="#content {margin-top: "+top_space_h+"px;}\n";
	auto_style +=".content {padding:"+parseInt(f * 30)+"px 0 "+parseInt(f * 36)+"px;}\n";
	auto_style +="#beehive {max-width:100%;outline: none;border: 0;}\n";
	auto_style +=".tmar32 { margin-top:"+margin_top32+"px; }\n";
	auto_style +=".pic-item { margin-top:"+margin_top32+"px; }\n";
	auto_style +=".job-listwrap { width:"+parseInt(f * 450)+"px; ; margin:-"+parseInt(f * 48)+"px auto 0 "+parseInt(f * 150)+"px; }\n";
	auto_style +=".job-list li,.job-list-2 li {height:"+parseInt(f * 52)+"px;}\n";
	auto_style +=".img_job  {height:"+parseInt(f * 42)+"px; margin-top:"+parseInt(f * 10)+"px; }\n";
	auto_style +=".map_left { margin-top:"+margin_top32+"px; }\n";
	
	//footer
	var footer_height = parseInt(width *98/641);
	auto_style +="#footer {height:"+ footer_height +"px;}\n";
	auto_style +=".menu-bottom {height:"+ footer_height +"px; line-height:"+ footer_height +"px; }\n";
	auto_style +=".menu-bottom img {width:"+ link_width +"px;}\n";

	
    var doc=document;  
    var style=doc.createElement("style");  
    style.setAttribute("type", "text/css");  
  
    if(style.styleSheet){// IE  
        style.styleSheet.cssText = auto_style;  
    } else {// w3c  
        var cssText = doc.createTextNode(auto_style);  
        style.appendChild(cssText);  
    }  
  
    var heads = doc.getElementsByTagName("head");  
    if(heads.length)  
        heads[0].appendChild(style);  
    else  
        doc.documentElement.appendChild(style);  
} 

function drawHeader(){
	document.write('<h1><a href="#" class="aleft"></a></h1>\n');
}

function drawHeaderDetail(){
	document.write('<h1><a href="about_mobile.html?app=1" class="aleft_detail"></a></h1>\n');
}
function drawBeehive(){
	width = window.innerWidth;
	var itemCoords=adjust(width * 0.98);
    var content = document.getElementById("content");
	
	var auto_image = '<img src="static_resources/img/fw.png" usemap="#Map" id="beehive">\n';
	
	auto_image +='<map name="Map" id="cribMap">\n';
	auto_image +='<area shape="poly" coords="' + itemCoords[0] + '"  onclick="jump(\'music\')" style="cursor:pointer;" >';
	auto_image +='<area shape="poly" coords="' + itemCoords[1] + '"  onclick="jump(\'navigation\')" style="cursor:pointer;">';
	auto_image +='<area shape="poly" coords="' + itemCoords[2] + '"  onclick="jump(\'work\')"style="cursor:pointer;">';

	auto_image +='<area shape="poly" coords="' + itemCoords[3] + '"  onclick="jump(\'game\')"style="cursor:pointer;">';
	auto_image +='<area shape="poly" coords="' + itemCoords[4] + '"  onclick="jump(\'express\')"style="cursor:pointer;">';
	auto_image +='<area shape="poly" coords="' + itemCoords[5] + '"  onclick="jump(\'group\')"style="cursor:pointer;">';
	auto_image +='<area shape="poly" coords="' + itemCoords[6] + '"  onclick="jump(\'pic\')"style="cursor:pointer;">';

	auto_image +='<area shape="poly" coords="' + itemCoords[7] + '"  onclick="jump(\'wd-w\')" style="cursor:pointer;" >';
	auto_image +='<area shape="poly" coords="' + itemCoords[8] + '"  onclick="javascript:void(0);" style="cursor:pointer;">';
	auto_image +='<area shape="poly" coords="' + itemCoords[9] + '"  onclick="jump(\'desigen\')"style="cursor:pointer;">';

	auto_image +='<area shape="poly" coords="' + itemCoords[10] + '"  onclick="jump(\'card\')"style="cursor:pointer;">';
	auto_image +='<area shape="poly" coords="' + itemCoords[11] + '"  onclick="jump(\'bbs\')"style="cursor:pointer;">';
	auto_image +='<area shape="poly" coords="' + itemCoords[12] + '"  onclick="jump(\'city\')"style="cursor:pointer;">';
	auto_image +='<area shape="poly" coords="' + itemCoords[13] + '"  onclick="jump(\'video\')"style="cursor:pointer;">';

	auto_image +='<area shape="poly" coords="' + itemCoords[14] + '"  onclick="jump(\'doctor\')" style="cursor:pointer;" >';
	auto_image +='<area shape="poly" coords="' + itemCoords[15] + '"  onclick="jump(\'travel\')" style="cursor:pointer;">';
	auto_image +='<area shape="poly" coords="' + itemCoords[16] + '"  onclick="jump(\'edu\')"style="cursor:pointer;">';
	auto_image +='</map>\n';
	
    content.innerHTML=auto_image;
}

function drawFooter(){
	
	width = window.innerWidth;
	link_width = parseInt(width *584/641);

    var footer = document.getElementById("footer");
	var itemCoords=new Array("0,0,110,62",
					"110,0, 240,62",
					"340,0, 470,62",
					"470,0, 584,62");
					
					//"245,0, 340,45",
	
	if(link_width < 584){
		for (var i=0;i<itemCoords.length;i++) {
			itemCoords[i] = adjustPosition(584, link_width,itemCoords[i]);
		}
	}
	
	var auto_image ='<div class="menu-bottom">';
	
	auto_image +='<img src="static_resources/images/bottom.png" usemap="#f_map" >\n';
	auto_image +='<map name="f_map">\n';
	for (var i=0;i<itemCoords.length;i++) {
		auto_image +='<area shape="rect" coords="'+ itemCoords[i] + '" onclick="goHome('+i+')">\n';
	}
	auto_image +='</map>';
	auto_image +='</div>';
    footer.innerHTML=auto_image;
}

//获取MAP中元素属�?
function adjust(img_width) {
	var itemCoords=new Array("156,1, 83,49,  83,120,  156,168, 228,120, 228,49",
	"314,1, 240,49, 240,120, 314,168, 386,120, 386,49",
	"472,1, 398,49, 398,120, 472,168, 544,120, 544,49",
	"77,132,  5,180,   5,253,   77,300,  149,253, 149,180",
	"235,132, 163,180, 163,253, 235,300, 307,253, 307,180",
	"393,132, 321,180, 321,253, 393,300, 465,253, 465,180",
	"551,132, 479,180, 479,253, 551,300, 623,253, 623,180",
	"156,264, 83,312,  83,385,  156,432, 228,385, 228,312",
	"314,264, 240,312, 240,385, 314,432, 386,385, 386,312",
	"472,264, 398,312, 398,385, 472,432, 544,385, 544,312",
	"77,394,  5,442,   5,515,   77,562,  149,515, 149,442",
	"235,394, 163,442, 163,515, 235,562, 307,515, 307,442",
	"393,394, 321,442, 321,515, 393,562, 465,515, 465,442",
	"551,394, 479,442, 479,515, 551,562, 623,515, 623,442",
	"156,527, 83,577, 83,644, 156,695, 228,647, 228,572",
	"314,527, 240,577, 240,644, 314,695, 386,647, 386,572",
	"472,527, 398,577, 398,644, 472,695, 544,647, 544,572");
	
	if(img_width < 621){
		for (var i=0;i<itemCoords.length;i++) {
			itemCoords[i] = adjustPosition(621, img_width,itemCoords[i]);
		}
	}
	return itemCoords;
}

function adjustPosition(designWidth, img_width,position) {
    var p = img_width / designWidth;
    var each = position.split(",");
    for (var i = 0; i < each.length; i++) {
        each[i] = Math.round(parseInt(each[i]) * p).toString();//x坐标
        i++;
        each[i] = Math.round(parseInt(each[i]) * p).toString();//y坐标
    }
    var newPosition = "";
    for (var i = 0; i < each.length; i++) {
        newPosition += each[i];
        if (i < each.length - 1) {
            newPosition += ",";
        }
    }
    return newPosition;
}