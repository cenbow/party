<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>二维码生成</title>
	<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
	<link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
	<%@include file="../include/commonFile.jsp" %>
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
<%@include file="../include/header.jsp" %>
<div class="index-outside">
	<%@include file="../include/sidebar.jsp" %>
	<!--内容-->
	<section>
		<div class="section-main">
			<div class="c-time-list-header">
				<div class="ovh">
					<span class="title f20">二维码生成</span>
				</div>
			</div>
			<div class="mt20" style="display: flex">
				<div class="qrgenerate-box" id="trigger-type" style="flex: 1">
					<textarea id="content" class="main_text b-a" name="text" placeholder="请输入内容"></textarea>
					<div class="submit text-center">
						<a href="javascript:void(0);" class="layui-btn layui-btn-danger mr15" id="create">
							<i class="iconfont icon-add btn-icon"></i> 生成二维码
						</a>
						<a href="javascript:void(0);" class="layui-btn layui-btn-danger mr15" id="convert">
							<i class="iconfont icon-link btn-icon"></i> 转换短连接
						</a>
						<a href="javascript:void(0);" class="layui-btn layui-btn-primary" id="resetBtn">
							<i class="iconfont icon-refresh btn-icon"></i> 清空内容
						</a>
					</div>
				</div>
				<div class="qrbox" id="qrfun-box" style="flex: 1">
					<div>
						<table class="qrimage-wrap b-a text-center" id="qrcode_kuang">
							<tbody>
							<tr>
								<td width="200px" height="272px;">
									<span class="f-16 text-darkgrey">左侧输入内容<br>点击生成二维码 </span>
								</td>
							</tr>
							</tbody>
						</table>
					</div>
					<div class="qrcode" style="display: none;" id="qrcode_image">
						<div class="qrimage-wrap white b-a text-center">
							<table>
								<tbody>
								<tr>
									<td width="230px" height="230px">
										<img id="qrimage" src="">
										<input type="hidden" id="fullPath"/>
									</td>
								</tr>
								</tbody>
							</table>
						</div>
						<div class="qrcode-download text-center">
							<a href="javascript:void(0);" class="layui-btn layui-btn-radius layui-btn-danger"
							   id="download">
								<i class="iconfont icon-pulldown btn-icon"></i>下载二维码
							</a>
						</div>
					</div>
					<div class="qrcode" style="display: none;" id="shortLink">
						<table class="qrimage-wrap b-a text-center">
							<tbody>
							<tr>
								<td width="200px" height="272px;">
									<span class="f-16 text-darkgrey" id="linkText"></span>
								</td>
							</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript">
    $(function () {
        $("#create").click(function () {
            var content = $("#content").val();
            if (content == "") {
                top.layer.msg("请先输入内容", {
                    icon: 6
                });
            } else {
                $.post("${ctx}/qrcode/qrcode/create.do", {
                    content: content
                }, function (data) {
                    if (data != "") {
                        $("#qrcode_image").siblings("div").hide();
                        $("#qrcode_image").show();
                        $("#qrimage").attr("src", '${qr_code}' + data);
                        $("#fullPath").val(data);
                    }
                })
            }
        });

        $("#convert").click(function () {
            var content = $("#content").val();
            var reg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
            if (content == "") {
                top.layer.msg("请先输入内容", {
                    icon: 6
                })
            } else if (!reg.test(content)) {
                top.layer.msg("输入的内容不是网址", {
                    icon: 6
                })
            } else {
                $.post("${ctx}/qrcode/qrcode/convertLink.do", {"longUrl": content}, function (res) {
                    if (res.success) {
                        var link = res.data;
                        $("#linkText").text(link);
                        $("#shortLink").siblings("div").hide();
                        $("#shortLink").show();
                    } else {
                        top.layer.msg("转换失败", {icon: 6});
                    }
                })
            }
        });

        $("#resetBtn").click(function () {
            $("#content").val("");
        });

        $("#download").click(function () {
            var fullPath = $("#fullPath").val();
            window.location.href = "${ctx}/fileupload/download.do?filePath=" + fullPath;
        });
    });
</script>
</body>
</html>