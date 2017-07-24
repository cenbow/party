<%@ page contentType="text/html;charset=UTF-8"%>
<!--侧边栏-->
<aside>
	<i class="aside-bg"></i>
	<div class="aside-main">
	<shiro:hasPermission name="activity:activity">
		<dl class="aside-dl">
			<dt class="aside-dt">活动管理</dt>
			<dd>
				<a href="${ctx}/activity/activity/activityList.do" class="aside-nav " data-title="我发布的活动" id="publish_list">我发布的活动</a>
			</dd>
		</dl>
	</shiro:hasPermission>

		<!--             <div class="aside-divide"></div> -->
		<!--             <dl class="aside-dl"> -->
		<!--                 <dt class="aside-dt">频道管理</dt> -->
		<%--                 <dd><a href="${ctx}/channel/channel/publishList.do" class="aside-nav " data-title="我发布的频道" id="channel_list">我发布的频道</a></dd> --%>
		<%--                 <dd><a href="${ctx}/article/article/publishList.do" class="aside-nav " data-title="我发布的文章" id="article_list">我发布的文章</a></dd> --%>
		<!--             </dl> -->
		<shiro:hasPermission name="subject:subject">
			<div class="aside-divide"></div>
			<dl class="aside-dl">
				<dt class="aside-dt">专题管理</dt>
				<dd>
					<a href="${ctx}/subject/subject/subjectList.do" class="aside-nav " data-title="我发布的专题" id="subject_list">我发布的专题</a>
				</dd>
			</dl>
		</shiro:hasPermission>
	
		<shiro:hasPermission name="crowdfund:crowdfund">
			<div class="aside-divide"></div>
			<dl class="aside-dl">
				<dt class="aside-dt">众筹管理</dt>
				<dd>
					<a href="${ctx}/activity/activity/zcActivityList.do" class="aside-nav " data-title="我发布的众筹项目" id="publish_zc_list">我发布的众筹项目</a>
				</dd>
				<dd>
					<a href="${ctx}/crowdfund/event/list.do" class="aside-nav " data-title="我发布的众筹事项" id="event_list">我发布的众筹事项</a>
				</dd>
			</dl>
		</shiro:hasPermission>
		
		<shiro:hasPermission name="sign:sign">
			<div class="aside-divide"></div>
			<dl class="aside-dl">
				<dt class="aside-dt">打卡管理</dt>
				<dd>
					<a href="${ctx}/sign/project/list.do" class="aside-nav " data-title="我发布的打卡项目" id="sign_project_list">我发布的打卡项目</a>
				</dd>
			</dl>
		</shiro:hasPermission>
		
		<shiro:hasPermission name="circle:circle">
			<div class="aside-divide"></div>
			<dl class="aside-dl">
				<dt class="aside-dt">圈子管理</dt>
				<dd>
					<a href="${ctx}/circle/list.do" class="aside-nav " data-title="我创建的圈子" id="circle_list">我创建的圈子</a>
				</dd>
			</dl>
		</shiro:hasPermission>
		
		<shiro:hasPermission name="gatherForm:gatherForm">
			<div class="aside-divide"></div>
			<dl class="aside-dl">
				<dt class="aside-dt">表单管理</dt>
				<dd class="aside-dd">
					<a href="${ctx}/gatherForm/project/list.do" class="aside-nav " data-title="我发布的项目" id="gather_form_project_list">我发布的项目</a>
				</dd>
			</dl>
		</shiro:hasPermission>
		
		<shiro:hasPermission name="gatherInfo:gatherInfo">
			<div class="aside-divide"></div>
			<dl class="aside-dl">
				<dt class="aside-dt">人员信息收集分组</dt>
				<dd class="aside-dd">
					<a href="${ctx}/gatherInfo/project/list.do" class="aside-nav " data-title="我发布的项目" id="gather_info_project_list">我发布的项目</a>
				</dd>
			</dl>
		</shiro:hasPermission>

		<shiro:hasPermission name="competition:competition">
			<div class="aside-divide"></div>
			<dl class="aside-dl">
				<dt class="aside-dt">赛事项目成绩管理</dt>
				<dd class="aside-dd">
					<a href="${ctx}/competition/project/list.do" class="aside-nav " data-title="项目管理" id="competition_project_list">我发布的赛事项目</a>
				</dd>
			</dl>
		</shiro:hasPermission>

		<div class="aside-divide"></div>
		<dl class="aside-dl">
			<dt class="aside-dt">消息管理</dt>
			<dd>
				<a href="${ctx}/notify/message/listAll.do" class="aside-nav " data-title="短信管理" id="sms_list">短信管理</a>
			</dd>
		</dl>
		<shiro:hasPermission name="qrcode:qrcode">
			<div class="aside-divide"></div>
			<dl class="aside-dl">
				<dt class="aside-dt">二维码生成</dt>
				<dd>
					<a href="${ctx}/qrcode/qrcode/toForm.do" class="aside-nav " data-title="二维码生成" id="qrcode_list">二维码生成</a>
				</dd>
			</dl>
		</shiro:hasPermission>
	</div>
</aside>
<script type="text/javascript">
	$(document).ready(
			function() {
				var url = window.location.href;
				$('.aside-nav').removeClass('active');
				if ((url.indexOf('/activity/activity') != -1 && url.toLowerCase().indexOf("zc") == -1) 
						|| url.indexOf('/activity/memberAct/memberActList.do') != -1
						|| url.indexOf('/distribution/relation') != -1) {
					$('#publish_list').addClass('active');
				}
				if (url.indexOf('/subject/subject') != -1 || url.indexOf("/subject/apply") != -1 || (url.indexOf("/article") && url.indexOf("applyId=") != -1)) {
					$('#subject_list').addClass('active');
				}

				if ((url.indexOf('/activity/activity') != -1 && url.toLowerCase().indexOf("zc") != -1)
						|| url.indexOf('crowdfund/support/listForDistributorId') != -1
						|| url.indexOf("crowdfund/support/listForProjectId") != -1
					    || url.indexOf("crowdfund/support/listForRepresent") != -1
						|| url.indexOf('/crowdfund/project/listForDistributorId.do') != -1
						|| url.indexOf("/crowdfund/analyze/list.do") != -1
						||url.indexOf("/notify/message/list.do") != -1
						|| url.indexOf("/crowdfund/target/messageView") != -1) {
					$('#publish_zc_list').addClass('active');
				}
				
				if (url.indexOf("/order/order") != -1) {
					$('#order_list').addClass('active');
				}
				if (url.indexOf("/crowdfund/event") != -1 || url.indexOf("represen/listForEvent") != -1 || url.indexOf("/project/listForEvent") != -1
						|| url.indexOf("support/event/listForDistributorId") != -1
						|| url.indexOf("project/event/listForDistributorId") != -1
				        || url.indexOf("support/event/listForRepresent") != -1
						|| url.indexOf("support/event/listForProjectId") != -1) {
					$('#event_list').addClass('active');
				}
				
				if (url.indexOf("qrcode/qrcode/toForm") != -1){
					$('#qrcode_list').addClass('active');
				}
				
				if (url.indexOf("circle/list") != -1 || url.indexOf("circle/form") != -1 || url.indexOf("circle/member/list") != -1 
						|| url.indexOf("circle/member/form") != -1 ||  url.indexOf("circle/tag/list") != -1 || url.indexOf("circle/apply/list") != -1){
					$('#circle_list').addClass('active');
				}
				if (url.indexOf("notify/message/listAll") != -1 || url.indexOf("/notify/message/viewAll") != -1){
					$('#sms_list').addClass('active');
				}
				if (url.indexOf("/sign/") != -1){
					$('#sign_project_list').addClass('active');
				}
				
				if (url.indexOf("/gatherInfo/project/") != -1 || url.indexOf("/gatherInfo/member/") != -1 || url.indexOf("/gatherInfo/group/") != -1){
					$('#gather_info_project_list').addClass('active');
				}
				
				if (url.indexOf("/competition") != -1){
					$('#competition_project_list').addClass('active');
				}
				
				if (url.indexOf("/gatherForm/") != -1){
					$('#gather_form_project_list').addClass('active');
				}
				if (url.indexOf("label/label") != -1){
					$('#label_list').addClass('active');
				}
			});
</script>
