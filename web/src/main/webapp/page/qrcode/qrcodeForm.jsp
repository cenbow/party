<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>二维码生成</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
<%@include file="../include/commonFile.jsp"%>
<style type="text/css">
/**输入框样式**/

.main_text {
	width: 100%;
	outline: 0;
	height: 272px;
	resize: none;
	font-size: 16px;
}

.b-a {
	border: 1px solid rgba(120, 130, 140, .25);
}

/**生成按钮样式**/
.submit {
	margin: 35px 0 10px
}

.btn {
	font-weight: 500;
	outline: 0 !important;
	border-width: 0;
	line-height: 1.4;
}

.btn {
	font-weight: 500;
	outline: 0 !important;
	border-width: 0;
}

.btn {
	display: inline-block;
	font-size: 16px;
	font-weight: 400;
	line-height: 1.5;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	-ms-touch-action: manipulation;
	touch-action: manipulation;
	cursor: pointer;
	user-select: none;
	border: 1px solid transparent;
}

.btn.green:focus, .btn.green:hover {
	background-color: #43a047;
	color: rgba(255, 255, 255, 0.87);
}

.text-center {
	text-align: center;
}

/**显示二维码框样式**/
.qrbox {
	position: relative;
	width: 100%;
}

.qrcode {
	position: relative;
	border-radius: 3px;
	width: 280px;
	margin: 0 auto 8px
}

.qrimage-wrap {
	width: 280px;
	height: auto;
	padding: 20px 0;
	margin: auto
}

.qrimage-wrap table {
	margin: auto
}

.qrimage-wrap td {
	margin: 0
}

.qrimage-wrap img {
	max-width: 230px;
	max-height: 230px;
	display: inline-block;
	margin: auto;
	vertical-align: middle
}

.qrcode .qrcode-download {
	margin-top: 35px
}
</style>
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
					<span class="title f20">二维码生成</span>
				</div>
			</div>
			<div class="mt20">
				<div class="qrgenerate-box l" id="trigger-type" style="width: 50%">
					<textarea id="content" class="main_text b-a" name="text" placeholder="请输入二维码内容"></textarea>
					<div class="submit text-center">
						<a href="javascript:void(0);" class="layui-btn layui-btn-radius layui-btn-danger" id="create">
							<i class="iconfont icon-add btn-icon"></i>生成二维码
						</a>
					</div>
				</div>
				<div class="qrbox l" id="qrfun-box" style="width: 50%">
					<table class="qrimage-wrap b-a text-center" id="qrcode_kuang">
						<tbody>
							<tr>
								<td width="200px" height="272px;">
									<span class="f-16 text-darkgrey">左侧输入内容<br>点击生成二维码 </span>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="qrcode" style="display: none;" id="qrcode_image">
						<div class="qrimage-wrap white b-a text-center">
							<table>
								<tbody>
									<tr>
										<td width="230px" height="230px">
											<img id="qrimage" src="">
											<input type="hidden" id="fullPath" />
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="qrcode-download text-center">
							<a href="javascript:void(0);" class="layui-btn layui-btn-radius layui-btn-danger" id="download">
								<i class="iconfont icon-pulldown btn-icon"></i>下载二维码
							</a>
						</div>
					</div>
				</div>
				<div class="cl"></div>
			</div>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript">
		$(function() {
			$("#create").click(function() {
				var content = $("#content").val();
				if (content == "") {
					top.layer.msg("请先输入二维码内容", {
						icon : 6
					});
				} else {
					$.post("${ctx}/qrcode/qrcode/create.do", {
						content : content
					}, function(data) {
						if (data != "") {
							$("#qrcode_image").show();
							$("#qrcode_kuang").hide();
							$("#qrimage").attr("src", '${qr_code}' + data);
							$("#fullPath").val(data);
						}
					})
				}
			});

			$("#download").click(function() {
				var fullPath = $("#fullPath").val();
				window.location.href =  "${ctx}/fileupload/download.do?filePath=" + fullPath;
			});
		});
	</script>
</body>
</html>