<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../include/tag.jsp"%>
<html>
<head>
<title>角色授权</title>
<%@include file="../../include/commonFile.jsp"%>
<style type="text/css">
body {
	min-width: auto !important;
	background-color: white !important;
}
</style>
</head>
<body>
	<form id="inputForm" action="${ctx}/system/role/saveRoleAuth.do" method="post">
		<div id="menuTree" class="ztree" style="margin-top: 3px; float: left;"></div>
		<input type="hidden" name="privilegeIds" id="privilegeIds" /> <input type="hidden" name="roleId" value="${roleId}" />
	</form>
</body>
<link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var setting = {
			check : {
				enable : true,
				nocheckInherit : true
			},
			view : {
				selectedMulti : false
			},
			data : {
				simpleData : {
					enable : true
				}
			}
		};

		// 用户-菜单
		var zNodes = [
			<c:forEach items="${privileges}" var="privilege">
				{
					id:"${privilege.id}", 
					pId:"${not empty privilege.parentId ? privilege.parentId : 0}", 
					name:"${not empty privilege.parentId ? privilege.name:'权限列表'}"
				},
			</c:forEach>
		];

		// 初始化树结构
		var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
		// 不选择父节点
		tree.setting.check.chkboxType = {
			"Y" : "ps",
			"N" : "s"
		};
		// 默认选择节点

		<c:forEach items="${myPrivileges}" var="privilege">
		var node = tree.getNodeByParam("id", "${privilege.id}");
		try {
			tree.checkNode(node, true, false);
		} catch (e) {

		}
		</c:forEach>

		// 默认展开全部节点
		tree.expandAll(true);

	});
	
	function doSubmit(){
		var treeObj = $.fn.zTree.getZTreeObj("menuTree");
		var nodes = treeObj.getCheckedNodes(true);
		var ids = new Array();
		for (var i = 0; i < nodes.length; i++) {
			ids.push(nodes[i].id);
		}
		$("#privilegeIds").val(ids.join(","));
		
		var flag = false;
		
		$.ajax({
			url : $(inputForm).attr("action"),
			type : "POST",
			dataType : "JSON",
			async : false,
			data : $(inputForm).serialize(),
			async : false,
			success : function(res){
				top.layer.msg("分配成功");
				flag = res.success;
			}
		});
		return flag;
	}
</script>
</html>