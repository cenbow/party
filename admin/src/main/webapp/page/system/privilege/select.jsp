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
                nocheckInherit : true,
                chkStyle: "radio",
                radioType: "all"
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
                name:"${not empty privilege.parentId ? privilege.name:'根目录'}"
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

        var node = tree.getNodeByParam("id", "${myPrivilege.id}");
        try {
            tree.checkNode(node, true, false);
        } catch (e) {

        }
        // 默认展开全部节点
        tree.expandAll(true);

    });

    function doSubmit(){
        var treeObj = $.fn.zTree.getZTreeObj("menuTree");
        var nodes = treeObj.getCheckedNodes(true);
        console.log(nodes);
        if (nodes.length == 0){
            alert("请选择一项");
        }
        var node = nodes[0];

        var parent = {id:node.id, name:node.name};
        return parent;
    }
</script>
</html>