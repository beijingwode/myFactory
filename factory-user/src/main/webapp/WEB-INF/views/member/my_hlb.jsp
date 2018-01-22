<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div class="user-panel">      	
            <div class="user-msg" style="padding:20px 0 0 20px;">           	
            	<p class="user-name" style="font-size:16px;">我的换领币</p>            	            	
                <div class="user-c" style="width:330px;margin-top:10px;">
                	<dl>
                    	<dt><i class="M-ye"></i></dt>
                        <dd>可用换领币：<span>${usable}</span></dd>
                    </dl>
                    <dl class="dl-none">
                    	<dt><i class="M-jf"></i></dt>
                        <dd>总计换领币：<span>${total}</span></dd>
                    </dl>
                    
                </div>
            </div>
 </div>
        <div class="hl_tit_li">
        	<ul>
        		<li id='v1' class="crr"><a href="/member/myhlOrder">欲领清单</a></li>
        		<li id='v2' ><a href="/member/myrenewal">调剂清单</a></li>
        	</ul>
        </div>
<script type="text/javascript">
	/* $(function(){
		$('.hl_tit_li ul li').on('click',function(){  
			 $(this).addClass("crr").siblings().removeClass("crr");
		}) 
	}); */
</script>        