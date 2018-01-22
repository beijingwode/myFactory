
    $(document).ready(function () {
        selectedHeaderLi("wddp_header");

    	if(jsEditId!="0" && jsSsId != jsEditId) {
    		$(".add_new").removeAttr("onclick");
    		$(".add_new").html('<a href="javascript:void(0);">其他店铺修改中</a>');
    	} else if(jsApprStatus>0) {
    		$(".add_new").removeAttr("onclick");
    		$(".add_new").html('<a href="javascript:void(0);">审核中</a>');
    	} else {
    		if(jsApprType=='0') {
    			//店铺修改中
        		$("#editCategory").removeAttr("onclick");
        		$("#editCategory").html('<a href="javascript:void(0);">店铺修改中</a>');
        		$("#editCategory").removeAttr("onclick");
        		$("#editCategory").html('<a href="javascript:void(0);">店铺修改中</a>');

        		$("#editShop").html('<a href="javascript:void(0);">继续修改</a>');
    		} else if(jsApprType=='1') {
    			//修改分类中
        		$("#editShop").removeAttr("onclick");
        		$("#editShop").html('<a href="javascript:void(0);">分类修改中</a>');
        		$("#editBrand").removeAttr("onclick");
        		$("#editBrand").html('<a href="javascript:void(0);">分类修改中</a>');

        		$("#editCategory").html('<a href="javascript:void(0);">继续修改</a>');
    		} else if(jsApprType=='2') {
    			//修改品牌中
        		$("#editShop").removeAttr("onclick");
        		$("#editShop").html('<a href="javascript:void(0);">品牌修改中</a>');
        		$("#editCategory").removeAttr("onclick");
        		$("#editCategory").html('<a href="javascript:void(0);">品牌修改中</a>');

        		$("#editBrand").html('<a href="javascript:void(0);">继续修改</a>');
    		} 
    	}
    });
    
    function edit(type){
    	if(jsEditId!="0" && jsSsId != jsEditId) {
    		showInfoBox("其他店铺正在编辑／审核中，暂时不能修改此店铺.")
    	} else if(jsApprStatus>'0') {
    		showInfoBox("审核中，请耐心等候。")
    	} else {
			window.location.href=jsBasePath+"/supplier/torecruitmentstore.html?id="+jsSsId+"&apprType="+type;
    	}
    	
    }
