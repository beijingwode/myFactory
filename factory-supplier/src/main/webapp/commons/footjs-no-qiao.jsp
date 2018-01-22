<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript">
    function shopcarNumSuccess(data){
        if(data.errorCode == '0')
            $('#shopcarNumber').html(data.msgBody);
    }

    function gotoshopcarlist(){
        window.location.href = "${productdomain}shopCar/list.html?version="+new Date().getTime();
    }

    $(function(){
        ajaxFindShopCarNum("${productdomain}",shopcarNumSuccess);
        if(!isLogin()){
            $('#login_li').removeClass('hidden');
            $('#reg_li').removeClass('hidden');
            $('#drop-login').hide();
        }
        else{
            loginChange();
        }
    });
    function loginChange(){
        $('#login_li').addClass('hidden');
        $('#reg_li').addClass('hidden');
        $('#drop-login').show();
        $('#username_a').html(decodeURI(getCookie("uname")));
        $('#username_b').html(decodeURI(getCookie("uname")));
        $('#welcome_div').show();
        $('#welcome_div2').show();
        $.ajax({
            type:'get',
            url:'${userdomain}user/getUsername.json',
            async:true,
            dataType : "jsonp",
            jsonp: "jcallback",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(默认为:callback)
            success:function(data){
                if(data.errorCode == 0 && data.msgBody != ''){
                    //$('#username_a').html(data.msgBody);
                    if(data.msgBody.avatar != null && data.msgBody.avatar != ''){
                        if(data.msgBody.avatar.indexOf("http://") == 0) {
                            $('#userpic_user').attr('src', data.msgBody.avatar);
                        } else {
                            $('#userpic_user').attr('src','${url}'+data.msgBody.avatar);
                        }
                        $('#userpic_user').show();
                        $('#userpic_default').hide();
                    }
                }
            }
        });
    }
</script>
<script language="javascript" type="text/javascript">
    $(function(){
        $(".gotoTop").hide();
        $(".online-advice").fadeIn(100);
        $(window).scroll(function(){
            var sheight = $(window).height()-$(window).height()/4;
            if ($(window).scrollTop()>sheight){
                $(".gotoTop").fadeIn(500);
            }
            else
            {
                $(".gotoTop").fadeOut(500);
            }
        });
        $(".gotoTop").click(function(){
            $('body,html').animate({scrollTop:0},1000);
            return false;
        });
        $('.online-item').hover(function(){
            if($(this).hasClass('gotoTop')){
                return;
            }
            if($(this).hasClass('qrcode')){
                $('.ft-txtyel1').show();
                return;
            }
            $(this).stop().animate({right:'120px'}, {duration: 500});
        },function(){
            if($(this).hasClass('qrcode')) {
                $('.ft-txtyel1').hide();
                return;
            }
            $(this).stop().animate({right:'0px'}, {duration: 500});
        });
        initMyshopcarlist();

        /*
         * 我购物车弹出层
         */
        $('#myshopcarLi').hover(function(){
            $('#myshopcarDiv').show();
        },function(){
            $('#myshopcarDiv').hide();
        });

    });
    function onreturnurl(type){
        if(type == 1){
            location.href='${userdomain }user/toRegister.html?returl='+window.location.href;
        }
        else{
            location.href='${userdomain }user/toLogin.html?returl='+window.location.href;;
        }
    }


    /**
     * 获取首页弹出层的购物车
     */
    function ajaxMyShopcarlist(basePath){
        $.ajax({
            url : basePath +'shopCar/ajaxlist.json',
            type : "GET",
            dataType: "jsonp",  //返回json格式的数据
            jsonp: 'jcallback', //默认callback
            async: true,
            success : function(data) {
                if(data.errorCode==0){
                    var html = data.msgBody;
                    $('#myshopcarDiv').html(html);
                }
            },
            error: function(data){
            }
        });
    }

    function gotoMyShopcar(dotype){
        window.location.href = "${productdomain }shopCar/list.html?dotype="+dotype;
    }

    function initMyshopcarlist(){
        //加载购物车
        var basePath = '${productdomain}';
        ajaxMyShopcarlist(basePath);
    }

    function ajaxDeleteDiv(obj){
        //$(obj).parent().parent(".list_shop1").remove();
        initMyshopcarlist();
        ajaxFindShopCarNum("${productdomain}",shopcarNumSuccess);
    }

</script>
<div id="" style="overflow: hidden; height: 0px;">
    <script type="text/javascript">
        var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
        document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fb7375b7d48c41cdcddc3408aafd00eef' type='text/javascript'%3E%3C/script%3E"));
    </script>
</div>