<%@ page contentType="text/html;charset=UTF-8"%>
<!-- 合作伙伴 -->
<div class="partner-black">
	<div class="partner-content">
		<div class="partner">
			<div class="partner-header">部分合作伙伴</div>
			<ul class="partner-icon-list">
				<c:forEach var="resource" items="${index.resources}" varStatus="status">
					<c:if test="${status.count % 8 == 0}">
						<li class="l" style="margin-right: 0px;"><a href="javascript:void(0)"><div class='img' style="background-image: url('${resource.pic}'),url(${ctx}/image/img_bg.png)"></div></a></li>
					</c:if>
					<c:if test="${status.count % 8 != 0}">
						<li class="l"><a href="javascript:void(0)"><div class='img' style="background-image: url('${resource.pic}'),url(${ctx}/image/img_bg.png)"></div></a></li>
					</c:if>
				</c:forEach>
				<li class="cl" style="margin-bottom: 0px;margin-right: 0px;"></li>
			</ul>
		</div>
	</div>
</div>