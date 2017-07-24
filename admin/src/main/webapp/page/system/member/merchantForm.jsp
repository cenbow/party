<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>绑定合作商信息</title>
<%@include file="../../include/commonFile.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/system/member/member_form.css">
<style type="text/css">
.cover-content .cover-img {
	width: 80px !important;
	height: 80px !important;
}
.layui-tab-brief>.layui-tab-title .layui-this{
	color: #e8473f!important;
}
.layui-tab-brief>.layui-tab-title .layui-this:after{
	border-bottom-color: #e8473f!important;
}

#mchForm .layui-form-item .layui-input-inline{
		width: 280px!important;
	}
</style>
</head>
<!--头部-->
<%@include file="../../include/header.jsp"%>
<div class="index-outside">
	<%@include file="../../include/sidebar.jsp"%>
	<!--内容-->
	<section>
		<div class="section-main">
			<div class="c-time-list-header">
				<div class="r">
					<a href="${ctx}/system/member/partnerList.do" class="layui-btn layui-btn-radius layui-btn-danger">
						<i class="iconfont icon-refresh btn-icon"></i>返回
					</a>
				</div>
				<div class="ovh">
					<span class="title f20">
						合作商管理&nbsp;&gt;&nbsp;${member.realname}&nbsp;&gt;&nbsp;绑定商户信息
					</span>
				</div>
			</div>
			<form id="mchForm" class="layui-form mt20" method="post" action="${ctx}/system/member/bindMerchant.do">
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">公众号名称<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" class="layui-input" name="officialAccountName" lay-verify="required" placeholder="公众号名称" value="${memberMerchant.officialAccountName}" />
							<input type="hidden" name="id" value="${memberMerchant.id}" />
							<input type="hidden" name="memberId" value="${member.id}" />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">公众号ID<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" class="layui-input" name="officialAccountId" lay-verify="required" placeholder="公众号ID" value="${memberMerchant.officialAccountId}" />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">公众号Secret<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" class="layui-input" name="officialAccountSecret" lay-verify="required" placeholder="公众号Secret" value="${memberMerchant.officialAccountSecret}" />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">商户号名称<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" class="layui-input" name="merchantName" lay-verify="required" placeholder="商户号名称" value="${memberMerchant.merchantName}" />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">商户号ID<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" class="layui-input" name="merchantId" lay-verify="required" placeholder="商户号ID" value="${memberMerchant.merchantId}" />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">商户号ApiKey<span class="f-verify-red">*</span></label>
						<div class="layui-input-inline">
							<input type="text" class="layui-input" name="merchantApiKey" lay-verify="required" placeholder="商户号ApiKey" value="${memberMerchant.merchantApiKey}" />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">是否启用</label>
						<div class="layui-input-inline">
							<input type="radio" name="openStatus" value="1" title="是" ${memberMerchant != null && memberMerchant.openStatus == '1' ? 'checked="checked"' : ''} />
							<input type="radio" name="openStatus" value="0" title="否" ${memberMerchant == null || memberMerchant.openStatus == '0' ? 'checked="checked"' : ''} />
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
						<a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="mchForm">立即保存</a>
						<a href="${ctx}/system/member/partnerList.do" class="layui-btn layui-btn-primary">取消</a>
					</div>
				</div>
			</form>
		</div>
	</section>
</div>
<!--底部-->
<%@include file="../../include/footer.jsp"%>
<script>
    $(function () {
        layui.use([ 'form', 'laydate', 'element' ], function() {
            var form = layui.form(), laydate = layui.laydate(), element = layui.element();

            //自定义验证规则
            form.verify({

            });

            //监听提交 绑定商户信息
            form.on('submit(mchForm)', function(data) {
                $(data.elem).removeAttr("lay-submit");
                var action = $("#mchForm").attr("action");
                $.post(action, $('#mchForm').serialize(), function(res) {
                    if (res.success) {
                        layer.msg("商户信息保存成功", {
                            icon : 1
                        }, function(index) {
                            window.location.href="${ctx}/system/member/partnerList.do";
                        });
                    }
                    else {
                        layer.msg(res.description, {
                            icon :2
                        })
                    }
                });
                return false;
            });
        });
    })

</script>
</body>
</html>