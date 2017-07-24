<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/tag.jsp" %>
<tr>
    <c:set var="thatCount" value="${fn:length(fn:split(cHelp.parentIds, ','))}"></c:set>
    <c:choose>
        <c:when test="${cHelp.parentId == '0'}">
            <td><strong>${cHelp.serialNumber}、${cHelp.title}</strong></td>
        </c:when>
        <c:otherwise>
            <td><div style="text-indent: ${(thatCount - 1) * 20}px">${cHelp.serialNumber}、${cHelp.title}</div></td>
        </c:otherwise>
    </c:choose>
    <td><fmt:formatDate value="${cHelp.updateDate}" pattern="yyyy-MM-dd HH:mm"/></td>
    <td class="tb-opts">
        <div class="comm-opts">
            <a href="${ctx}/help/help/toForm.do?id=${cHelp.id}&parentId=${cHelp.parentId}" class="green" target="_self">编辑</a>
            <a href="javascript:deleteObj('确定要删除吗？', '${ctx}/help/help/${cHelp.id}/delete.do', '该父级下面有子级，请先删除子级')" class="red" target="_self">删除</a>
            <c:if test="${cHelp.parentId == '0'}">
                <a href="${ctx}/help/help/toForm.do?parentId=${cHelp.id}" class="gray" target="_self">新增子级教程</a>
            </c:if>
        </div>
    </td>
    <c:if test="${fn:length(cHelp.childrens) > 0}">
        <c:forEach var="ccHelp" items="${cHelp.childrens}">
            <c:set var="cHelp" value="${ccHelp}" scope="request"/>
            <jsp:include page="treeInclude.jsp"></jsp:include>
        </c:forEach>
    </c:if>
</tr>