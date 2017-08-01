<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>选择供应商</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<%@include file="../include/commonFile.jsp"%>
<style type="text/css">

</style>
</head>
<!--头部-->
<div class="index-outside">
	<!--内容-->
	<section>
		<div class="section-main" style="    padding: 15px;">
			<form class="layui-form" action="${ctx}/goods/goods/selectThirdParty.do" id="myForm" method="post">
				<input type="hidden" name="pageNo" id="pageNo" />
				<div class="f-search-bar">
					<div class="search-container">
						<ul class="search-form-content">
							<li class="form-item-inline"><label class="search-form-lable">名称</label>
								<div class="layui-input-inline">
									<input type="text" name="comName" autocomplete="off" class="layui-input" value="${sysRole.name}" placeholder="请输入查询名称" style="width: 250px;">
								</div>
							</li>
							<li class="form-item-inline">
								<div class="sub-btns">
									<a class="layui-btn layui-btn-danger" id="submitBtn">确定</a> <a class="layui-btn layui-btn-normal" id="resetBtn">重置</a>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</form>
			<div class="c-time-list-content" style="padding-top: 0px;">
				<input type="hidden" id="thirdPartyId" />
				<input type="hidden" id="thirdPartyName" />
				<ul>
					<table class="layui-table layui-form">
						<colgroup>
							<col width="100px">
							<col>
						</colgroup>
						<thead>
							<tr>
								<th></th>
								<th>供应商</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="thirdParty" items="${thirdParties}">
								<tr>
									<td><input type="radio" name="id" value="${thirdParty.id}" data-name="${thirdParty.comName}" lay-filter="thirdparty" title="&nbsp;"></td>
									<td>${thirdParty.comName}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</ul>
			</div>
			<div id="page_content" class="page-container"></div>
		</div>
	</section>
</div>
<!--底部-->
<script type="text/javascript">


	$(function() {
        layui.use([ 'form', 'laydate', 'laypage' ], function() {
            var form = layui.form(), laydate = layui.laydate, laypage = layui.laypage;
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

            form.on('radio(thirdparty)', function(data){
                $("#thirdPartyId").val(data.value);
                $("#thirdPartyName").val($(data.elem).attr("data-name"));
            });
        });
		$("#submitBtn").click(function() {
			$("#myForm").submit();
		});

		$("#resetBtn").click(function() {
			$("#myForm input[type=text]").val("");
			$("#myForm select").find("option:eq(0)").attr("selected", true);
			$(".check-btn-inner").find("a").removeClass("active");
			$(".check-btn-inner").find("a:eq(0)").addClass("active")
			$(".check-btn-inner [name=timeType]").val("");
		});
	});

	function openDialog(title, url, width, height, target) {
		layer.open({
			type : 2,
			area : [ width, height ],
			title : title,
			maxmin : true, //开启最大化最小化按钮
			content : url,
			btn : [ '确定', '关闭' ],
			yes : function(index, layero) {
				var body = layer.getChildFrame('body', index);
				var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
				var inputForm = body.find('#inputForm');
				var top_iframe;
				if (target) {
					top_iframe = target;//如果指定了iframe，则在改frame中跳转
				} else {
					top_iframe = '_parent';//获取当前active的tab的iframe 	        	 
				}
				inputForm.attr("target", top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示

				if (iframeWin.contentWindow.doSubmit()) {
					setTimeout(function() {
						top.layer.close(index);
					}, 100);//延时0.1秒，对应360 7.1版本bug

					setTimeout(function() {
						window.location.reload();
					}, 200);
				}

			},
			cancel : function(index) {
			}
		});
	}
	
	function doSubmit(){
		var thirdPartyId = $("#thirdPartyId").val();
		if(thirdPartyId == ""){
			layer.msg("请先选择供应商", {
				icon : 6
			});
			return false;
		}
		return true;
	}
</script>
</body>
</html>