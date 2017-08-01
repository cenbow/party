<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
    var navItemH = 45;
    $(function () {
        var url = window.location.href;
        $('.aside-nav').each(function () {
            var item = this;
            var tags = $(this).data('tag').split('|');
            tags.forEach(function (tag) {
                if (url.indexOf(tag) != -1) {
                    $(item).addClass('active');
                    $(item).closest('.aside-dl').addClass('aside-open');
                    navItemH = item.clientHeight;
                    $(item).parent().height($(item).parent().height());
                    return false;
                }
            })
        });
        $(".aside-dd").filter(function(index) {
            return !$(this).closest('.aside-dl').hasClass('aside-open');
        }).css('height', 0);
        $('.aside-dt').click(function (e) {
            var dl = $(this).parent();
            $('.aside-dd').css('height', 0);
            $('.aside-open').removeClass('aside-open');
            dl.find('.aside-dd').css('height', dl.find('.aside-dd').children().length * navItemH);
            dl.addClass('aside-open');
        })
    })

</script>
<!--侧边栏-->
<aside>
	<i class="aside-bg"></i>
	<div class="aside-main">
		<dl class="aside-dl">
			<dt class="aside-dt">系统管理<i class="iconfont icon-right"></i></dt>
			<dd class="aside-dd">
				<a href="${ctx}/system/member/memberList.do" data-tag="system/member/member" class="aside-nav " id="member_list" data-title="用户管理">用户管理</a>
				<a href="${ctx}/system/member/partnerList.do" data-tag="system/member/partner|system/member/merchantForm|order/order/memberOrderList" class="aside-nav " id="hz_list" data-title="合作商管理">合作商管理</a>
				<a href="${ctx}/system/member/distributorList.do" data-tag="system/member/distributor" class="aside-nav " id="fx_list" data-title="分销商管理">分销商管理</a>
				<a href="${ctx}/system/role/roleList.do" data-tag="system/role/roleList"class="aside-nav " data-title="角色管理" id="role_list">角色管理</a>
				<a href="${ctx}/system/privilege/list.do" data-tag="system/privilege/list|system/privilege/view"class="aside-nav " data-title="权限管理" id="privilege_list">权限管理</a>
				<a href="${ctx}/charge/package/packageList.do" data-tag = "charge/package" class = "aside-nav" data-title="产品套餐管理" id="package_list">产品套餐管理</a>
				<a href="${ctx}/charge/privilege/list.do" data-tag = "charge/privilege" class = "aside-nav" data-title="产品权限管理" id="pro_privilege_list">产品权限管理</a>
			</dd>
		</dl>
		<div class="aside-divide"></div>
		<dl class="aside-dl">
			<dt class="aside-dt">综合管理<i class="iconfont icon-right"></i></dt>
			<dd class="aside-dd">
				<a href="${ctx}/subject/subject/subjectList.do" data-tag="/subject/apply|/subject/subject" class="aside-nav " data-title="专题管理" id="subject_list">专题管理</a>
				<a href="${ctx}/dynamic/dynamic/dynamicList.do" data-tag="/dynamic/dynamic/|/dynamic/love|/dynamic/comment/" class="aside-nav " data-title="动态管理" id="dynamic_list">动态管理</a>
				<a href="${ctx}/resource/resource/resourceList.do" data-tag="/resource/resource" class="aside-nav " data-title="资源管理" id="resource_list">资源管理</a>
				<a href="${ctx}/contact/memberList.do" class="aside-nav " data-tag="/contact" data-title="通讯录管理" id="contact_list">通讯录管理</a>
				<a href="${ctx}/ad/list.do" class="aside-nav "data-tag="/admin/ad"  data-title="广告管理" id="ad_list">广告管理</a>
				<a href="${ctx}/city/list.do" class="aside-nav "data-tag="/admin/city"  data-title="开放城市管理" id="city_list">开放城市管理</a>
				<a href="${ctx}/qrcode/qrcode/toForm.do" data-tag="/qrcode/qrcode" class="aside-nav " data-title="二维码管理" id="qrcode_list">二维码管理</a>
				<a href="${ctx}/help/help/list.do" data-tag = "help/help" class = "aside-nav" data-title="教程管理" id="level_list">教程管理</a>
			</dd>
		</dl>

		<div class="aside-divide"></div>
		<dl class="aside-dl">
			<dt class="aside-dt">活动管理<i class="iconfont icon-right"></i></dt>
			<dd class="aside-dd">
				<a href="${ctx}/activity/activity/activityList.do" data-tag="/activity/activity/act|/activity/memberAct/memberActList" class="aside-nav " data-title="活动管理" id="activity_list">活动管理</a>
				<a href="${ctx}/activity/memberAct/applyList.do" data-tag="/activity/memberAct/applyList" class="aside-nav " data-title="活动管理" id="apply_list">报名管理</a>
			</dd>
		</dl>

		<div class="aside-divide"></div>
		<dl class="aside-dl">
			<dt class="aside-dt">众筹管理<i class="iconfont icon-right"></i></dt>
			<dd class="aside-dd">
				<a href="${ctx}/activity/activity/zcActivityList.do" data-tag="/activity/activity/zc|crowdfund/project/listForDistributorId|crowdfund/support/listForDistributorId|crowdfund/support/listForProjectId|crowdfund/support/listForRepresent|/crowdfund/analyze/list.do|notify/message/list.do|notify/message/view.do|crowdfund/target/messageView.do" class="aside-nav " data-title="众筹项目管理" id="zc_activity_list">众筹项目管理</a>
				<a href="${ctx}/crowdfund/event/list.do" data-tag="/crowdfund/event/list|crowdfund/represen/listForEvent|crowdfund/project/listForEvent|crowdfund/support/event/listForProjectId|crowdfund/project/event/listForDistributorId|crowdfund/project/event/listForDistributorId|crowdfund/support/event/listForDistributorId|crowdfund/support/event/listForRepresent" class="aside-nav " data-title="众筹事项管理" id="event_list">众筹事项管理</a>
				<a href="${ctx}/label/label/list.do" data-tag="/label/label" class="aside-nav " data-title="众筹分析状态管理" id="label_list">众筹分析状态管理</a>
			</dd>
		</dl>
		<div class="aside-divide"></div>
		<dl class="aside-dl">
			<dt class="aside-dt">打卡管理<i class="iconfont icon-right"></i></dt>
			<dd class="aside-dd">
				<a href="${ctx}/sign/project/list.do" data-tag="/sign/project|/sign/group|/sign/member" class="aside-nav " data-title="打卡项目管理" id="sign_project_list">打卡项目管理</a>
			</dd>
		</dl>
		<div class="aside-divide"></div>
		<dl class="aside-dl">
			<dt class="aside-dt">玩法管理<i class="iconfont icon-right"></i></dt>
			<dd class="aside-dd">
				<a href="${ctx}/goods/goods/bzGoodsList.do" data-tag="/goods/goods/bzGoodsList|/goods/goods/bzGoodsForm|order/order/goodsOrderList.do?type=0" class="aside-nav " data-title="标准玩法管理" id="bz_goods_list">标准玩法管理</a>
				<a href="${ctx}/goods/goods/dzGoodsList.do" data-tag="/goods/goods/dzGoodsList|/goods/goods/dzGoodsForm|goods/goods/goodsDetail.do|order/order/goodsOrderList.do?type=1" class="aside-nav " data-title="定制玩法管理" id="dz_goods_list">定制玩法管理</a>
			</dd>
		</dl>
		<div class="aside-divide"></div>
		<dl class="aside-dl">
			<dt class="aside-dt">订单管理<i class="iconfont icon-right"></i></dt>
			<dd class="aside-dd">
				<a href="${ctx}/order/order/orderList.do"data-tag="/order/order/orderList"  class="aside-nav " data-title="订单管理" id="order_list">订单管理</a>
			</dd>
		</dl>
		<div class="aside-divide"></div>
		<dl class="aside-dl">
			<dt class="aside-dt">提现管理<i class="iconfont icon-right"></i></dt>
			<dd class="aside-dd">
				<a href="${ctx}/wallet/withdrawals/withdrawalList.do" data-tag="/wallet/withdrawals" class="aside-nav " id="withdrawal_list" data-title="提现管理">提现管理</a>
			</dd>
		</dl>
		<div class="aside-divide"></div>
		<dl class="aside-dl">
			<dt class="aside-dt">分销管理<i class="iconfont icon-right"></i></dt>
			<dd class="aside-dd">
				<a href="${ctx}/distribution/relation/list.do" data-tag="/distribution/relation|/distribution/apply/list|/order/order/distributorLis" class="aside-nav " id="relation_list" data-title="我的分销">分销管理</a>
			</dd>
		</dl>
		<div class="aside-divide"></div>
		<dl class="aside-dl">
			<dt class="aside-dt">圈子管理<i class="iconfont icon-right"></i></dt>
			<dd class="aside-dd">
				<a href="${ctx}/circle/list.do" class="aside-nav " data-tag="/circle/list|circle/form|/circle/tag/list|circle/member/list|circle/apply/list|circle/member/form|circle/topic" data-title="圈子管理" id="">圈子管理</a>
				<a href="${ctx}/circle/type/list.do" class="aside-nav " data-tag="/circle/type/list|/circle/type/form" data-title="圈子类型管理">圈子类型管理</a>
			</dd>
		</dl>
		<div class="aside-divide"></div>
		<dl class="aside-dl">
			<dt class="aside-dt">频道管理<i class="iconfont icon-right"></i></dt>
			<dd class="aside-dd">
				<a href="${ctx}/channel/channel/channelList.do" data-tag="/channel/channel" class="aside-nav " data-title="频道管理" id="channel_list">频道管理</a>
				<a href="${ctx}/article/article/articleList.do" data-tag="/article/article" class="aside-nav " data-title="文章管理" id="article_list">文章管理</a>
			</dd>
		</dl>
		<div class="aside-divide"></div>
		<dl class="aside-dl">
			<dt class="aside-dt">表单管理<i class="iconfont icon-right"></i></dt>
			<dd class="aside-dd">
				<a href="${ctx}/gatherForm/project/list.do" data-tag="gatherForm" class="aside-nav " data-title="项目管理">项目管理</a>
			</dd>
		</dl>
		<div class="aside-divide"></div>
		<dl class="aside-dl">
			<dt class="aside-dt">人员信息收集分组<i class="iconfont icon-right"></i></dt>
			<dd class="aside-dd">
				<a href="${ctx}/gatherInfo/project/list.do" data-tag="gatherInfo" class="aside-nav " data-title="项目管理">项目管理</a>
			</dd>
		</dl>
		<div class="aside-divide"></div>
		<dl class="aside-dl">
			<dt class="aside-dt">赛事项目成绩管理<i class="iconfont icon-right"></i></dt>
			<dd class="aside-dd">
				<a href="${ctx}/competition/project/list.do" data-tag="competition" class="aside-nav " data-title="项目管理">赛事项目管理</a>
			</dd>
		</dl>
		<div class="aside-divide"></div>
		<dl class="aside-dl">
			<dt class="aside-dt">消息管理<i class="iconfont icon-right"></i></dt>
			<dd class="aside-dd">
				<a href="${ctx}/notify/channel/list.do" class="aside-nav "data-tag="/notify/channel/list.do|/notify/channel/view"  data-title="消息事件" id="notify_channel">消息通道</a>
				<a href="${ctx}/notify/event/list.do" class="aside-nav "data-tag="/notify/event/list|/notify/channel/listForEven|/notify/event/view"
				   data-title="消息事件" id="notify_event">消息事件</a>
				<a href="${ctx}/notify/wechat/list.do" class="aside-nav "data-tag="/notify/wechat/list|//notify/wechat/view"
				   data-title="微信模板消息" id="notify_wechat">微信模板消息</a>
				<a href="${ctx}/notify/message/listAll.do" class="aside-nav "data-tag="notify/message/listAll|/notify/message/viewAll."
				   data-title="短信管理" id="sms_wechat">短信管理</a>
			</dd>
		</dl>
	</div>
</aside>

