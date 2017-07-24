<%@ page contentType="text/html;charset=UTF-8"%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="keywords" content="深圳前海同行者咨询有限公司,同行者,TXZ,txz,CEO户外,活动,创业者,活动报名">
<meta name="description" content="同行者，中国CEO户外活动与服务平台，提倡“户外社交激发创业正能量”，核心聚焦众创空间、企业级服务商、商协会、产业园区、创业大赛。在同城及全球范围，致力打造高端、富有深度和持续生命力的户外商务社交场景，开启健康正能量的户外生活方式。">
<meta name="format-detection" content="telephone=no">
<!-- 图标 -->
<link rel="icon" type="image/x-icon" href="${ctx}/image/favicon.ico" size="16x16 24x24 32x32 48x48">
<link rel="shortcut icon" type="image/x-icon" href="${ctx}/image/favicon.ico">
<!-- 作者：Juliana 时间：2016-02-16 描述：PC公共css-->
<link rel="stylesheet" href="${ctx}/themes/default/css/common/common.css">
<link rel="stylesheet" href="${ctxStatic}/layui-v1.0.7/css/layui.css">
<!-- cdn -->
<script type="text/javascript" charset="utf-8" src="//apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">
	if (!window.jQuery) {
		var s = document.createElement('script');
		s.src = '${ctxStatic}/jquery/jquery-2.1.4.min.js';
		document.body.appendChild(s);
	}
</script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/common.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/util.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/date-util.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/layui-v1.0.9_rls/layui.js"></script>
<script>
	$(function(){
		txz.addToTopScroll();
		
		$("input").keypress(function(e){
    		if(e.keyCode == 13){
    			e.preventDefault();
    		}
    	});
	})
</script>