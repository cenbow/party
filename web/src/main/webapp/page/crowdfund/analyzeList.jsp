<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>数据分析</title>
    <%@include file="../include/commonFile.jsp"%>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/lable/lable.css">
</head>
<%@include file="../include/header.jsp"%>
<body>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp"%>
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a href="${ctx}/activity/activity/zcActivityList.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">众筹管理 &gt; 数据分析</span>
                </div>
            </div>
            <div class="layui-tab-item layui-show" >
                <div class="layui-tab layui-tab-brief" lay-filter="zc">
                    <ul class="layui-tab-title" id="zc_tab">
                        <li <c:if test="${empty projectAnalyze.isSuccess}">class="layui-this"</c:if>>全部<span id="total_0" class="pl5">${count.all}</span>人</li>
                        <li <c:if test="${projectAnalyze.isSuccess == 0}">class="layui-this"</c:if>>众筹中<span id="total_0" class="pl5">${count.underway}</span>人</li>
                        <li <c:if test="${projectAnalyze.isSuccess == 1}">class="layui-this"</c:if>>众筹成功<span id="total_0" class="pl5">${count.success}</span>人</li>
                        <li <c:if test="${projectAnalyze.isSuccess == 3}">class="layui-this"</c:if>>退款中<span id="total_0" class="pl5">${count.refunding}</span>人</li>
                        <li <c:if test="${projectAnalyze.isSuccess == 4}">class="layui-this"</c:if>>退款成功<span id="total_0" class="pl5">${count.refunded}</span>人</li>
                    </ul>
                    <div class="layui-tab-content">
                        <form class="layui-form" action="${ctx}/crowdfund/analyze/list.do" id="myForm" method="post">
                            <input type="hidden" name="isSuccess" id="isSuccess" value="${projectAnalyze.isSuccess}"/>
                            <input type="hidden" name="targetId" id="targetId" value="${projectAnalyze.targetId}"/>
                            <input type="hidden" name="eventId" id="eventId" value="${projectAnalyze.eventId}"/>
                            <input type="hidden" name="pageNo" id="pageNo" />
                            <div class="f-search-bar">
                                <div class="search-container">
                                    <ul class="search-form-content">
                                        <li class="form-item-inline"><label class="search-form-lable" style="margin-left: 14px;">众筹者</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="authorName" autocomplete="off" class="layui-input" value="${projectAnalyze.authorName}"
                                                       placeholder="众筹者" style="width: 212px"
                                                >
                                            </div></li>
                                        <li class="form-item-inline"><label class="search-form-lable" style="margin-left: 28px;">联系电话</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="authorMobile" autocomplete="off" class="layui-input" value="${projectAnalyze.mobile}"
                                                       placeholder="联系电话" style="width: 212px"
                                                >
                                            </div>
                                        </li>
                                        <li class="form-item-inline"><label class="search-form-lable">排序方式</label>
                                            <div class="layui-input-inline">
                                                <select name="sort">
                                                    <option value="0" ${projectAnalyze.sort == 0 ? 'selected="selected"' : ''}>金额最多</option>
                                                    <option value="1" ${projectAnalyze.sort == 1 ? 'selected="selected"' : ''}>时间最近</option>
                                                </select>
                                            </div>
                                        </li>
                                    </ul>
                                    <ul class="search-form-content">
                                        <li class="form-item-inline"><label class="search-form-lable" style="margin-left: 28px;">渠道</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="parentName" autocomplete="off" class="layui-input" value="${projectAnalyze.parentName}"
                                                       placeholder="渠道" style="width: 212px"
                                                >
                                            </div>
                                        </li>
                                        <li class="form-item-inline"><label class="search-form-lable" style="margin-left: 14px;">加好友</label>
                                            <div class="layui-input-inline">
                                                <select name="isFriend">
                                                    <option value="">全部</option>
                                                    <option value="0" ${projectAnalyze.isFriend == 0 ? 'selected="selected"' : ''}>否</option>
                                                    <option value="1" ${projectAnalyze.isFriend == 1 ? 'selected="selected"' : ''}>是</option>
                                                </select>
                                            </div>
                                        </li>
                                        <li class="form-item-inline"><label class="search-form-lable">入群</label>
                                            <div class="layui-input-inline">
                                                <select name="isGroup">
                                                    <option value="">全部</option>
                                                    <option value="0" ${projectAnalyze.isGroup == 0 ? 'selected="selected"' : ''}>否</option>
                                                    <option value="1" ${projectAnalyze.isGroup == 1 ? 'selected="selected"' : ''}>是</option>
                                                </select>
                                            </div>
                                        </li>
                                    </ul>
                                    <ul class="search-form-content">
                                        <li class="form-item-inline"><label class="search-form-lable">筛选金额</label>
                                            <div class="layui-input-inline">
                                                <select name="operator">
                                                    <option value="0" <c:if test="${projectAnalyze.operator == 0}">selected</c:if>>等于</option>
                                                    <option value="1" <c:if test="${projectAnalyze.operator == 1}">selected</c:if>>小于</option>
                                                    <option value="2" <c:if test="${projectAnalyze.operator == 2}">selected</c:if>>大于</option>
                                                </select>
                                            </div>
                                            <div class="layui-input-inline">
                                                <input type="text" name="operatorNum" autocomplete="off" class="layui-input" value="${projectAnalyze.operatorNum}" placeholder="金额">
                                            </div>
                                        </li>
                                        <li class="form-item-inline">
                                            <div class="sub-btns">
                                                <a class="layui-btn layui-btn-danger" href="javascript:submitFunction('#myForm')">确定</a>
                                                <a class="layui-btn layui-btn-normal" href="javascript:resetFunction('#myForm')">重置</a>
                                            </div>
                                        </li>
                                    </ul>
                                    <ul class="search-form-content">
                                        <li class="form-item"><label class="search-form-lable">分析状态 </label>
                                            <div class="check-btn-inner" id="timeType">
                                                <a id="all" href="javascript:void(0);" onclick="setTimeType($(this),'','#myForm')" ${empty projectAnalyze.labelId ? 'class="active"' : ''}>全部</a>
                                                <c:forEach var="label" items="${labelList}">
                                                    <a href="javascript:void(0);" onclick="setTimeType($(this),'${label.id}','#myForm')" ${projectAnalyze.labelId == label.id ? 'class="active"' : ''}>${label.name}</a>
                                                </c:forEach>
                                                <input type="hidden" name="labelId" value="${projectAnalyze.labelId}" />
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </form>
                        <form class="layui-form" action="${ctx}/crowdfund/analyze/list.do" id="exportForm" method="post">
                        </form>
                        <ul class="num mt10">
                            <div class="l">
                                <li class="f16">众筹人数<span class="red">${page.totalCount}</span>人</li>
                            </div>
                            <div class="r">
                                <li style="cursor: pointer;">
                                    <a class="layui-btn layui-btn-danger layui-btn-small" id="btnExport">导出EXCEL</a>
                                </li>
                            </div>
                            <p class="cl"></p>
                        </ul>

                        <div class="list-content jfix-table-content">
                            <div class="jleft-fix-table no-hover-table">
                                <table class="layui-table jfix-table-wrap">
                                    <colgroup>
                                        <col width="120px">
                                        <col width="100px">
                                        <col width="100px">
                                        <col width="100px">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th>众筹者</th>
                                        <th>渠道</th>
                                        <th>分析状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="analyze" items="${list}">
                                        <tr <c:if test="${not empty analyze.style && analyze.style != 'white'}" >class="tr_${analyze.style}"</c:if> >
                                            <td class="jfix-td-item" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${analyze.authorId}','400px','470px')">
                                                <div class="table-member">
                                                    <div class="member-cell">
                                                    <div class="member-logo" style="background-image: url('${analyze.authorLogo}'),url(${ctx}/image/def_user_logo.png)"></div>
                                                    <div class="member-name ellipsis-1"><a class="tdl" title="${analyze.authorName}" href="javascript:void(0);" >${analyze.authorName}</a></div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="jfix-td-item">
                                                    ${analyze.parentName}
                                            </td>
                                            <td class="jfix-td-item">
                                                    ${analyze.labels}
                                            </td>
                                            <td  class="tb-opts jfix-td-item" style="width: 100px">
                                                <div class="comm-opts">
                                                    <a href="javascript:openDialogShow('数据查看','${ctx}/crowdfund/support/chartView.do?projectId=${analyze.id}','1050px','500px')" target="_self">
                                                        数据查看
                                                    </a>
                                                    <a href="javascript:openDialog('设置状态','${ctx}/crowdfund/analyze/labelView.do?projectId=${analyze.id}','400px','300px')" target="_self">
                                                        设置状态
                                                    </a>
                                                    <a href="javascript:records('${analyze.id}')" target="_self">
                                                        跟进记录
                                                    </a>
                                                    <c:if test="${analyze.isFriend == '否'}">
                                                        <a href="javascript:changeFriend('${analyze.id}', 1, '确定加好友？')" target="_self">
                                                            加好友
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${analyze.isFriend == '是'}">
                                                        <a href="javascript:changeFriend('${analyze.id}', 0, '确定删除友？')" target="_self">
                                                            删除好友
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${analyze.isGroup == '否'}">
                                                        <a href="javascript:changeGroup('${analyze.id}', 1, '确定加群？')" target="_self">
                                                            入群
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${analyze.isGroup == '是'}">
                                                        <a href="javascript:changeGroup('${analyze.id}', 0, '确定退群？')" target="_self">
                                                            退群
                                                        </a>
                                                    </c:if>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="cl jright-fix-table">
                                <table class="layui-table">
                                    <colgroup>
                                        <col width="100px">
                                        <col width="200px">
                                        <col width="150px">
                                        <col width="130px">
                                        <col width="90px">
                                        <col width="90px">
                                        <col width="90px">
                                        <col width="90px">
                                        <col width="100px">
                                        <col width="130px">
                                        <c:forEach items="${dateList}" var="date">
                                            <col width="130px">
                                        </c:forEach>
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th>区域</th>
                                        <th>公司</th>
                                        <th>职务</th>
                                        <th>联系电话</th>
                                        <th>加好友</th>
                                        <th>入群状态</th>
                                        <th>状态</th>
                                        <th>支持人数</th>
                                        <th>支持金额</th>
                                        <th>时间</th>
                                        <c:forEach items="${dateList}" var="date">
                                            <th>${date}</th>
                                        </c:forEach>
                                    </tr>
                                    </thead>
                                    <tbody >
                                    <c:forEach var="analyze" items="${list}">
                                        <tr <c:if test="${not empty analyze.style && analyze.style != 'white'}" >class="tr_${analyze.style}"</c:if>>
                                            <td >
                                                    ${analyze.cityName}
                                            </td>
                                            <td class=" ellipsis-1" title="${analyze.company}">
                                                    ${analyze.company}
                                            </td>

                                            <td>
                                                    ${analyze.jobTitle}
                                            </td>
                                            <td>
                                                    ${analyze.mobile}
                                            </td>
                                            <td>
                                                    ${analyze.isFriend}
                                            </td>
                                            <td>
                                                    ${analyze.isGroup}
                                            </td>
                                            <td>
                                                    ${analyze.isSuccess}
                                            </td>
                                            <td>
                                                <a href="javascript:void(0);" class="tdl favorerNum count-num" data-id="${analyze.id}" data-name="${analyze.authorName}">${analyze.favorerNum}</a>

                                            </td>
                                            <td>
                                                <a href="javascript:void(0);" class="tdl actualAmount count-num" data-id="${analyze.id}" data-name="${analyze.authorName}"> ${analyze.actualAmount}</a>

                                            </td>
                                            <td>
                                                <fmt:formatDate value="${analyze.createDate}" pattern="yyyy-MM-dd"/>
                                            </td>




                                            <c:forEach items="${dateList}" var="date">
                                                <td>
                                                        ${analyze.moneyMap[date]}
                                                </td>

                                            </c:forEach>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div id="page_content" class="page-container"></div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript">
    var laytpl = null;
    var laypage = null;
    var element = null;


    $(function() {
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');

        layui.use([ 'laytpl', 'laypage', 'element' ], function() {
            laytpl = layui.laytpl;
            laypage = layui.laypage;
            element = layui.element();


            element.on('tab(zc)', function(data) {
                if (data.index == 0){
                    $('#isSuccess').val('');
                    $('#myForm').submit();
                }
                else if (data.index == 1){
                    $('#isSuccess').val('0');
                    $('#myForm').submit();
                }
                else if (data.index == 2){
                    $('#isSuccess').val('1');
                    $('#myForm').submit();
                }
                else if (data.index == 3){
                    $('#isSuccess').val('3');
                    $('#myForm').submit();
                }
                else if (data.index == 4){
                    $('#isSuccess').val('4');
                    $('#myForm').submit();
                }
            });
        });

        $('.favorerNum').click(function () {
            var projectId = this.dataset.id;
            var name = this.dataset.name;
            window.location.href = '${ctx}/crowdfund/support/event/listForProjectId.do?eventId=${analyze.eventId}&projectId='+projectId;
        });

        $('.actualAmount').click(function () {
            var projectId = this.dataset.id;
            var name = this.dataset.name;
            window.location.href = '${ctx}/crowdfund/support/event/listForProjectId.do?eventId=${analyze.eventId}&projectId='+projectId;
        });

    })

    function openDialog(title, url, width, height, target) {
        layer.open({
            type : 2,
            area : [ width, height ],
            title : title,
            maxmin : true, //开启最大化最小化按钮
            content : url,
            btn : [ '确定', '关闭' ],
            yes : function(index, layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var inputForm = body.find('#inputForm');
                var top_iframe;
                if (target) {
                    top_iframe = target;//如果指定了iframe，则在改frame中跳转
                } else {
                    top_iframe = '_parent';//获取当前active的tab的iframe
                }
                inputForm.attr("target", top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示

                if (iframeWin.contentWindow.doSubmit()) {
                    setTimeout(function() {
                        top.layer.close(index);
                    }, 100);//延时0.1秒，对应360 7.1版本bug

                    setTimeout(function() {
                        window.location.reload();
                    }, 200);
                }
            },
            cancel : function(index) {
            }
        });
    }

    function changeFriend(id, status, info) {
        layer.confirm(info, {
            icon : 3,
            title : '系统提示'
        }, function(index) {
            $.post("${ctx}/crowdfund/analyze/changeFriend.do", {
                projectId : id, status : status},function(data) {
                if (data.success == true) {
                    layer.alert("操作成功", {
                        icon : 6
                    }, function(index) {
                        window.location.reload();
                    });
                } else {
                    layer.alert("操作失败", {
                        icon : 6
                    });
                }
            });
        });
    }

    function changeGroup(id, status, info) {
        layer.confirm(info, {
            icon : 3,
            title : '系统提示'
        }, function(index) {
            $.post("${ctx}/crowdfund/analyze/changeGroup.do", {
                projectId : id, status : status},function(data) {
                if (data.success == true) {
                    layer.alert("操作成功", {
                        icon : 6
                    }, function(index) {
                        window.location.reload();
                    });
                } else {
                    layer.alert("操作失败", {
                        icon : 6
                    });
                }
            });
        });
    }

    $("#btnExport").click(function () {
        layer.confirm('确认要导出Excel吗?', {
            icon : 3,
            title : '系统提示'
        }, function(index) {

            $('#myForm').attr('action', '${ctx}/crowdfund/analyze/listExport.do');
            $('#myForm').submit();
            $('#myForm').attr('action', '${ctx}/crowdfund/analyze/list.do');
            top.layer.close(index);
        });
    });

    function records(id) {
        window.location.href = '${ctx}/records/records/list.do?targetId='+id+'&activityId=${projectAnalyze.targetId}&eventId=${projectAnalyze.eventId}';
    }
</script>
</body>
</html>
