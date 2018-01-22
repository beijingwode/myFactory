@var editUrl=editUrl!; //  示例menu/edit/showlayer
@var delUrl=delUrl!;
@var addUrl=addUrl!;
@var treeData=treeData!;
@var form=form!'search-form';
@var searchBtn=searchBtn!'search-btn';
@var searchInput=searchInput!'search-input';
@var searchAllBtn=searchAllBtn!'search-all-btn';
@var height = height!'0';
@var width = width!'50%';
@var reloadUrl = reloadUrl!false;
@var rootNodeName = rootNodeName!"全部";

<script type="text/javascript">
	var setting = {
		view:{
			expandSpeed:100,
			selectedMulti : false,
			addHoverDom:addHoverDom,
			removeHoverDom: removeHoverDom,
			fontCss:function(treeId, treeNode) {
				return (!!treeNode.highlight) ? {"font-weight":"bold","color":"red"} : {"font-weight":"normal","color":"#333"};
			}
		},
		edit: {
			enable: true,
			editNameSelectAll: true,
			showRemoveBtn: function(treeId,treeNode){
				return treeNode.level == 0 ? false:true;
			},
			showRenameBtn: function(treeId,treeNode){
				return treeNode.level == 0 ? false:true;
			},
			removeTitle : "删除",
			renameTitle : "编辑"
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentId"
			}
		},
		callback: {
			onClick: onClickNode,
			beforeRemove:beforeRemove,
			beforeEditName: beforeEditName,
			beforeDrag: function(){return false;}
		}
	};
	
	//编辑
	function beforeEditName(treeId, treeNode) {
		$.cuslayer({
			mode:'page',
			title:(treeNode.name)+'编辑',
			height:"${height}",
			width:"${width}",
			url:"${ctxPath!}/"+"${editUrl}",
			data:{"id":treeNode.id,"parentId":treeNode.getParentNode().id}
		});
		return false;
	}
	
	//删除
	function beforeRemove(treeId, treeNode){
		var id = treeNode.id;
		var rootId = treeNode.rootId;
		var brotherOrderAll = treeNode.brotherOrderAll;
		$.cuslayer({
			mode:'del',
			msg:'<span class="red bigger-120">你确定删除<'+treeNode.name+'>吗?</span>',
			title:'删除操作',
			url:"${ctxPath!}/"+'${delUrl}',
			data:{"id":id},
			reloadurl:${reloadUrl}
		});
		return false;
	}
	
	//划过显示添加按钮,添加
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='添加' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			$.cuslayer({
				mode:'page',
				title:'添加分类',
				height:"${height}",
				width:"${width}",
				url:"${ctxPath!}/"+'${addUrl}',
				data:{"parentId":treeNode.id}
			});
			return false;
		});
	};
	
	//移除添加按钮
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
	};
	
	//节点点击事件
	function onClickNode(event, treeId, treeNode) {
		$("#${form}").find("input[name=name]").val("");
		var treeObj = $.fn.zTree.getZTreeObj("treeMenu");
		$("#${form}").find("input[name=rootId]").val(treeNode.rootId);
		$("#${form}").find("input[name=brotherOrderAll]").val(treeNode.brotherOrderAll);
		paging("${form}",1); //刷新表单
		for(var i=0, l=nodeList.length; i<l; i++) {
			nodeList[i].highlight = false;				
			treeObj.updateNode(nodeList[i]);
		}
	};
	
	var key = $("#${searchInput}"),nodeList = [];
	function searchNode(e) {
		// 取得输入的关键字的值
		var value = $.trim(key.get(0).value);
		
		// 按名字查询
		var keyType = "name";
		if (key.hasClass("empty")) {
			value = "";
		}
		
		// 如果要查空字串，就退出不查了。
		if (value === "") {
			return;
		}
		updateNodes(false);
		nodeList = treeObj.getNodesByParamFuzzy(keyType, value);
		updateNodes(true);
	};
	function updateNodes(highlight) {
		for(var i=0, l=nodeList.length; i<l; i++) {
			nodeList[i].highlight = highlight;				
			treeObj.updateNode(nodeList[i]);
			treeObj.expandNode(nodeList[i].getParentNode(), true, false, false);
		}
	};
	
	var treeObj;
	$(function(){
		var treeData;
		eval('treeData= ${treeData};');
		
		for(var i=0;i<treeData.length;i++) {
			treeData[i].name=decodeURI(treeData[i].name);
			if(treeData[i].parentName){
				treeData[i].parentName=decodeURI(treeData[i].parentName);
			}
		}
				
		@if(auth.hasAllDataScope() || auth.isSuper()){
			var root = {name:"${rootNodeName}",open:true};
			treeData[treeData.length] = root;
		@}
		//$("#treeMenu") == 初始化dom容器。setting == 配置数据。treeData == zTree 的节点数据
		$.fn.zTree.init($("#treeMenu"), setting,treeData);
		// 根据treeMenu 获取 zTree 对象的方法
		treeObj = $.fn.zTree.getZTreeObj("treeMenu");
		//页面的搜索查询
		$("#${searchAllBtn}").click(function(){
			$("#${form}").find("input[name=rootId]").val("");
			$("#${form}").find("input[name=name]").val("");
			$("#${form}").find("input[name=brotherOrderAll]").val("");
			paging("${form}",1);
			var node = treeObj.getNodeByParam("id", 0);
			treeObj.selectNode(node,false);
			if(undefined != nodeList) {
				for(var i=0, l=nodeList.length; i<l; i++) {
					nodeList[i].highlight = false;				
					treeObj.updateNode(nodeList[i]);
				}
			}
		}).trigger("click");
		//搜索按钮
		$("#${searchBtn}").click(function(e){
			$("#${form}").find("input[name=rootId]").val("");
			$("#${form}").find("input[name=brotherOrderAll]").val("");
			var treeObj = $.fn.zTree.getZTreeObj("treeMenu");
			treeObj.cancelSelectedNode();
			paging("${form}",1);
			searchNode(e);
		});
		
	});
	
</script>
