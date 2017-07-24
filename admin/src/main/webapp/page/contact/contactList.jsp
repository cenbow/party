<%@page import="java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>用户管理</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/system/member/member_list.css">
<%@include file="../include/commonFile.jsp"%>
</head>
<!--头部-->
<%@include file="../include/header.jsp"%>
<div class="index-outside">
	<%@include file="../include/sidebar.jsp"%>
	<!--内容-->
	<section>
		<div class="section-main">
			<div class="c-time-list-header">
				<div class="ovh">
					<span class="title f18"><a href="${ctx}/contact/memberList.do" class="blue">通讯录管理</a>>${member.realname }的通讯录</span>
					<span class="f12">共<b>${page.totalCount}</b>条记录</span>
				</div>
			</div>
			<form class="layui-form" action="${ctx}/contact/contactList.do" id="myForm" method="post">
				<input type="hidden" name="pageNo" id="pageNo" />
				<input type="hidden" name="memberId" id="memberId" value="${contact.memberId }"/>
				<div class="f-search-bar">
					<div class="search-container">						
						<ul class="search-form-content">
							<li class="form-item-inline"><label class="search-form-lable">全称</label>
								<div class="layui-input-inline">
									<input type="text" name="fullName" autocomplete="off" class="layui-input" value="${contact.fullName}" placeholder="请输入">
								</div>
							</li>
							<li class="form-item-inline"><label class="search-form-lable">电话号码</label>
								<div class="layui-input-inline">
									<input type="text" name="phones" autocomplete="off" class="layui-input" value="${contact.phones}" placeholder="请输入">
								</div>
							</li>	
							<li class="form-item-inline"><label class="search-form-lable">状态</label>
								<div class="layui-input-inline">
									<select name="status">
										<option value="">全部</option>
										<option value="1" ${contact.status == "1" ? 'selected="selected"' : ''}>未使用</option>
										<option value="0" ${contact.status == "0" ? 'selected="selected"' : ''}>已使用</option>
									</select>
								</div>
							</li>
							<li  class="form-item-inline">
								<div class="sub-btns">
									<a class="layui-btn layui-btn-danger" id="submitBtn">确定</a> <a class="layui-btn layui-btn-normal" id="resetBtn">重置</a>
								</div>
							</li>			
						</ul>
						
					</div>
				</div>
			</form>
			<div class="list-content">
				<div class="cl">
					<table class="layui-table">
						<colgroup>
							<col width="150">
							<col width="250">
							<col width="150">
							<col width="100">
						</colgroup>
						<thead>
							<tr>
								<th>全称</th>
								<th>电话号码</th>
								<th>公司名称</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="contact" items="${contacts}">
								<tr>
									<td>
										<div class="<c:if test='${contact.status == 0 }'>gray</c:if>">${contact.fullName}</div>
									</td>
									<td>
										<div class="<c:if test='${contact.status == 0 }'>gray</c:if>">${contact.phones}</div>
									</td>
									<td>
										<div class="<c:if test='${contact.status == 0 }'>gray</c:if>">${contact.company}</div>
									</td>
									<td>
										<c:if test="${contact.status == 1 }">
										<div class="layui-btn layui-btn-mini layui-btn-danger" onclick="changeStatus('${contact.id}','0')">标记使用</div>
										</c:if>
										<c:if test="${contact.status == 0 }">
										<div class="layui-btn layui-btn-mini layui-btn-normal" onclick="changeStatus('${contact.id}','1')">标记有效</div>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div id="page_content" class="page-container"></div>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript">
	$(function() {
        layui.use([ 'form', 'laytpl', 'laydate', 'laypage' ], function() {
            var form = layui.form(), laydate = layui.laydate, laytpl = layui.laytpl, laypage = layui.laypage;
            var $ = layui.jquery; //重点处
            laypage({
                cont : 'page_content',
                pages : '${page.totalPages}',
                curr : function() {
                    return '${page.page}';
                }(),
                skip : true,
                skin : '#ea5952',
                jump : function(obj, first) {
                    if (!first) {
                        $("#myForm").find("#pageNo").val(obj.curr);
                        $("#myForm").submit();
                    }
                }
            });
        });
		$("#submitBtn").click(function() {
			$("#myForm").submit();
		});

		$("#resetBtn").click(function() {
			$("#myForm input[type=text]").val("");
			$("#myForm select").find("option:eq(0)").attr("selected",true);
			$(".check-btn-inner").find("a").removeClass("active");
			$(".check-btn-inner").find("a:eq(0)").addClass("active")
		});

	});
	
	function changeStatus(id,status){
		$.post("${ctx}/contact/changeStatus.do", {
			status : status,
			id : id
		}, function(data) {
			if (data.success == true) {
				window.location.reload();
			}
		})
	}



</script>
</body>
</html>