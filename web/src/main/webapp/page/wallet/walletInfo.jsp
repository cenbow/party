<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<div class="f-search-bar" style="border: 1px solid #dbdee5; padding: 16px; border-radius: 5px;">
	<div class="search-container">
		<p class="mb5">可用余额：</p>
		<div>
			<div style="width: auto; float: left; margin-right: 10px;">
				<span style="font-size: 24px; font-family: '微软雅黑';"><fmt:formatNumber pattern="#,###.##" value="${totalPayment}"></fmt:formatNumber></span>&nbsp;元
			</div>
			<a href="${ctx}/wallet/withdrawals/withdrawalForm.do" class="layui-btn layui-btn-danger layui-btn-small" style="padding-left: 15px; padding-right: 15px;">提现</a>
		</div>
	</div>
</div>