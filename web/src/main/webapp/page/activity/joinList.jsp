<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>我参与的活动</title>
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/activity/act_list.css">
<%@include file="../include/commonFile.jsp" %>
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp" %>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="my-act-list-header">
            	<span class="title f20">我参与的活动</span>
            	<span class="f12">共<b id="totalCount"></b>条记录</span>
            </div>
            <div class="my-act-list-content">
                <ul id="view">
<%--                 	<c:forEach var="memberAct" items="${memberActs}"> --%>
<!-- 	                    <li> -->
<!-- 	                        <div class="act-detail-content"> -->
<%-- 	                        	<div class="act-logo" style="background-image: url('${memberAct.activity.pic}'),url(${ctx}/image/test/act_list_logo.jpg)"></div> --%>
<!-- 	                            <div class="act-detail"> -->
<!-- 	                                <div class="act-title"> -->
<%-- 	                                    <c:set var="nowTime" value="<%=new Date() %>"/> --%>
<%-- 	                                    <c:choose> --%>
<%-- 	                                    	<c:when test="${memberAct.activity.startTime > nowTime}"> --%>
<!-- 	                                    		<span class="status f20 red">未开始</span> -->
<%-- 	                                    	</c:when> --%>
<%-- 	                                    	<c:when test="${memberAct.activity.endTime > nowTime}"> --%>
<!-- 	                                    		<span class="status f20 red">进行中</span> -->
<%-- 	                                    	</c:when> --%>
<%-- 	                                    	<c:when test="${memberAct.activity.endTime < nowTime}"> --%>
<!-- 	                                    		<span class="status f20 dark">已结束</span> -->
<%-- 	                                    	</c:when> --%>
<%-- 	                                    </c:choose> --%>
<%-- 	                                    <a onclick="redirect('${ctx}/activity/activity/activityDetail.do?actId=${memberAct.activity.id}')" class="title f18">${memberAct.activity.title}</a> --%>
<!-- 	                                </div> -->
<!-- 	                                <p class="start-time"> -->
<%-- 	                               		活动时间：<fmt:formatDate value="${memberAct.activity.startTime}" pattern="yyyy-MM-dd hh:mm" /> --%>
<!-- 	                                </p> -->
<!--                                 	<p class="act-price"> -->
<%--                                 		<span style="margin-right: 10px;">报名费用：<b style="color: active-red"><fmt:formatNumber value="${memberAct.payment}" pattern="0" /></b>元/位</span> --%>
<!--                                 	</p> -->
<!--                                 	<p class="start-time"> -->
<!-- 	                               		活动地点： -->
<!-- 	                                </p> -->
<!-- 	                            </div> -->
<!-- 	                        </div> -->
<!-- 	                    </li> -->
<%-- 					</c:forEach> --%>
                </ul>
            </div>
        </div>
    </section>
</div>

<!--底部-->
<%@include file="../include/footer.jsp" %>
<script id="demo" type="text/html">
{{#  layui.each(d, function(index, item){ }}
<li>
	<div class="act-detail-content">
		<div class="act-logo" style="background-image: url('{{ item.activity.pic }}'),url(${ctx}/image/img_bg.png)"></div>
		<div class="act-detail">
			<div class="act-title">
				{{#  if(item.activity.startTime > (new Date())){ }}
					<span class="status f20 red">未开始</span>
				{{#  }else if(item.activity.endTime > (new Date())){ }}
					<span class="status f20 red">进行中</span>
				{{#  }else if(item.activity.endTime < (new Date())){ }}
					<span class="status f20 dark">已结束</span>
				{{#  } }}
				<a onclick="redirect('${ctx}/activity/activity/activityDetail.do?actId={{ item.activity.id }}')" class="title f18">{{ item.activity.title }}</a>
			</div>
			<p class="start-time">
				开始时间：{{ getDateStr(item.activity.startTime).Format('yyyy-MM-dd hh:mm') }}
			</p>
			<p class="act-price">
				<span style="margin-right: 10px;">报名费用：<b style="color: active-red">{{ item.payment }}</b>元/位</span>
			</p>
			<p class="start-time">
				活动地点：{{ item.activity.area }} {{ item.activity.place }}
			</p>
		</div>
	</div>
</li>
{{#  }); }}
</script>
<script type="text/javascript">
	function redirect (url) {
		window.location.href = url;
	}
	
	var pageNo = 1;
	var noMoreData = false;
	$(function () {
        layui.use('laytpl', function() {
            var laytpl = layui.laytpl;

            function fillData(){
                if(noMoreData){
                    return;
                }

                var url = "${ctx}/activity/activity/joinListJson.do";
                var params = {
                    pageNo : pageNo
                };

                function callBack(data) {

                    $("#totalCount").text(data.page.totalCount);

                    var container = $(".my-act-list-content");
                    if (pageNo == 1) {
                        view.innerHTML = "";
                    }
                    if (pageNo == 1 && data.datas.length == 0) {
                        $(container).append("<div class='no-more-data f18'>还没有数据.</div>");
                        noMoreData = true;
                        return;
                    }
                    if (pageNo > 1 && data.datas.length == 0) {
                        $("#loadMore").hide();
                        $(container).append("<div class='no-more-data f18'>没有更多数据了.</div>");
                        noMoreData = true;
                        return;
                    }
                    var getTpl = demo.innerHTML;
                    laytpl(getTpl).render(data.datas, function(html) {
                        view.innerHTML += html;
                    });

                    var loadMore = $("#loadMore");
                    if(loadMore.length == 0){
                        $(container).append("<div id='loadMore' class='load-more-data f18'>点击加载更多.</div>");
                    }
                }

                $.post(url, params, function(data) {
                    callBack(data);
                });
            }

            fillData();

            $(".my-act-list-content").on("click",'div#loadMore', function () {
                if (!noMoreData) {
                    pageNo++;
                    fillData();
                }
            });
        });
    })

</script>
</body>
</html>