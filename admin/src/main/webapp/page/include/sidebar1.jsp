<%@ page contentType="text/html;charset=UTF-8"%>
<!--侧边栏-->
<aside>
	<i class="aside-bg"></i>
	<ul class="layui-nav layui-nav-tree aside-main" lay-filter="test">
		<li class="layui-nav-item"><a href="javascript:;">系统管理</a>
			<dl class="layui-nav-child">
				<dd>
					<a href="${ctx}/system/member/memberList.do" class="aside-nav " id="member_list" data-title="用户管理">用户管理</a>
				</dd>
				<dd>
					<a href="${ctx}/system/role/roleList.do" class="aside-nav" data-title="角色管理" id="role_list">角色管理</a>
				</dd>
			</dl></li>
		<li class="layui-nav-item"><a href="javascript:;">分销管理</a>
			<dl class="layui-nav-child">
				<dd>
					<a href="${ctx}/distribution/relation/list.do" class="aside-nav" id="relation_list" data-title="我的分销">分销管理</a>
				</dd>
			</dl></li>
		<li class="layui-nav-item"><a href="javascript:;">玩法管理</a>
			<dl class="layui-nav-child">
				<dd>
					<a href="${ctx}/goods/goods/bzGoodsList.do" class="aside-nav" data-title="标准玩法管理" id="bz_goods_list">标准玩法管理</a>
				</dd>
				<dd>
					<a href="${ctx}/goods/goods/dzGoodsList.do" class="aside-nav" data-title="定制玩法管理" id="dz_goods_list">定制玩法管理</a>
				</dd>
			</dl></li>
	</ul>
</aside>
<script type="text/javascript">
	layui.use('element', function() {
		var element = layui.element();

		//一些事件监听
		element.on('nav(test)', function(elem) {
			console.log(elem); //得到当前点击的DOM对象
		});
	});

	$(document).ready(
			function() {
				var url = window.location.href;
				if (url.indexOf('/system/member/memberList.do') != -1) {
					$('.aside-nav').removeClass('active');
					$('#member_list').addClass('active');
				}
				if (url.indexOf('/system/role/roleList.do') != -1) {
					$('.aside-nav').removeClass('active');
					$('#role_list').addClass('active');
				}
				if (url.indexOf('/distribution/relation') != -1 || url.indexOf('/distribution/apply/list') != -1
						|| url.indexOf('/distribution/order/list') != -1) {
					$('.aside-nav').removeClass('active');
					$('#relation_list').addClass('active');
				}
				if (url.indexOf('/activity/activity/activityList.do') != -1 || url.indexOf('/activity/activity/activityDetail.do') != -1
						|| url.indexOf('/activity/memberAct/memberActList.do') != -1) {
					$('.aside-nav').removeClass('active');
					$('#activity_list').addClass('active');
				}
				if (url.indexOf('/channel/channel/channelList.do') != -1 || url.indexOf('/channel/channel/channelDetail.do') != -1) {
					$('.aside-nav').removeClass('active');
					$('#channel_list').addClass('active');
				}
				if (url.indexOf('/article/article/articleList.do') != -1 || url.indexOf('/article/article/articleDetail.do') != -1) {
					$('.aside-nav').removeClass('active');
					$('#article_list').addClass('active');
				}
				if (url.indexOf('/subject/subject/subjectList.do') != -1 || url.indexOf('/subject/apply/applyList.do') != -1
						|| url.indexOf('/subject/subject/subjectDetail.do') != -1) {
					$('.aside-nav').removeClass('active');
					$('#subject_list').addClass('active');
				}
				if (url.indexOf('/activity/activity/zcActivityList.do') != -1 || url.indexOf('/activity/activity/zcActivityDetail.do') != -1) {
					$('.aside-nav').removeClass('active');
					$('#zc_activity_list').addClass('active');
				}

				if (url.indexOf('/order/order/orderList.do') != -1) {
					$('.aside-nav').removeClass('active');
					$('#order_list').addClass('active');
				}
				if (url.indexOf('/contact') != -1) {
					$('.aside-nav').removeClass('active');
					$('#contact_list').addClass('active');
				}
			});
</script>
