//商家中心-我的店铺-左侧多级菜单
$(document).ready(function (){
  $(".left_store_list ul li").menu();
});

(function($) {
    $.fn.menu = function(b) {
        var c,
        item,
        httpAdress;
        b = jQuery.extend({
            Speed: 220,
            autostart: 1,
            autohide: 0
        },
        b);
        c = $(this);
        item = c.children("ul").parent("li").children("a");
        httpAdress = window.location;
        item.addClass("inactive");
        function _item() {
            var a = $(this);
            if (b.autohide) {
                a.parent().parent().find(".active").parent("li").children("ul").slideUp(b.Speed / 1.2, 
                function() {
                    $(this).parent("li").children("a").removeAttr("class");
                    $(this).parent("li").children("a").attr("class", "inactive");
                   
                })
            }
            if (a.attr("class") == "inactive") {
                a.parent("li").children("ul").slideDown(b.Speed, 
                function() {
                    a.removeAttr("class");
                    a.addClass("active");
					//a.find('.icon01').attr("class", "icon01_s");
					a.find('.icon02').attr("class", "icon02_s");
					a.find('.icon03').attr("class", "icon03_s");
                })
            }
            if (a.attr("class") == "active") {
                a.removeAttr("class");
                a.addClass("inactive");
                a.parent("li").children("ul").slideUp(b.Speed);
				//a.find('.icon01_s').attr("class", "icon01");
				a.find('.icon02_s').attr("class", "icon02");
				a.find('.icon03_s').attr("class", "icon03");
				
            }
        }
        item.unbind('click').click(_item);
        if (b.autostart) {
            c.children("a").each(function() {
                if (this.href == httpAdress) {
                    $(this).parent("li").parent("ul").slideDown(b.Speed, 
                    function() {
                        $(this).parent("li").children(".inactive").removeAttr("class");
                        $(this).parent("li").children("a").addClass("active");
                        //$(this).children("li").addClass("active");
                        //$(this).addClass("active").parent().siblings().find("a").removeClass("active");

                    });
                    $(this).parent("li").addClass("active");	
                }
            })
        }
    }
})(jQuery);