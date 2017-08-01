<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>数据分析</title>
    <%@include file="../include/commonFile.jsp" %>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/lable/lable.css">
</head>
<%@include file="../include/header.jsp" %>
<body>
<div class="index-outside">
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
            <div class="layui-tab-item layui-show">
                <div class="layui-tab layui-tab-brief" lay-filter="zc">
                    <ul class="layui-tab-title" id="zc_tab">
                        <li <c:if test="${empty projectAnalyze.isSuccess}">class="layui-this"</c:if>>全部<span id="total_0" class="pl5">${count.all}</span>人</li>
                        <li <c:if test="${projectAnalyze.isSuccess == 0}">class="layui-this"</c:if>>众筹中<span id="total_0" class="pl5">${count.underway}</span>人</li>
                        <li <c:if test="${projectAnalyze.isSuccess == 1}">class="layui-this"</c:if>>众筹成功<span id="total_0" class="pl5">${count.success}</span>人</li>
                        <li <c:if test="${projectAnalyze.isSuccess == 3}">class="layui-this"</c:if>>退款中<span id="total_0" class="pl5">${count.refunding}</span>人</li>
                        <li <c:if test="${projectAnalyze.isSuccess == 4}">class="layui-this"</c:if>>退款成功<span id="total_0" class="pl5">${count.refunded}</span>人</li>
                    </ul>
                    <div class="layui-tab-content">
                        <div class="l-tab-inner">
                            <a class="form-tab-show" href="javascript:javascript:void(0);" onclick="$(this).parent().toggleClass('active');">展开<i class="iconfont icon-unfold"></i><i class="iconfont icon-fold"></i></a>
                            <form class="layui-form" action="${ctx}/crowdfund/analyze/list.do" id="exportForm" method="post">
                            </form>
                            <form class="layui-form" action="${ctx}/crowdfund/analyze/list.do" id="myForm" method="post">
                                <input type="hidden" name="isSuccess" id="isSuccess" value="${projectAnalyze.isSuccess}"/>
                                <input type="hidden" name="targetId" id="targetId" value="${projectAnalyze.targetId}"/>
                                <input type="hidden" name="eventId" id="eventId" value="${projectAnalyze.eventId}"/>
                                <input type="hidden" name="pageNo" id="pageNo"/>
                                <div class="f-search-bar">
                                    <div class="search-container">
                                        <div class="l-toggle-inner">
                                            <ul class="search-form-content">
                                                <li class="form-item-inline"><label class="search-form-lable" style="margin-left: 14px;">众筹者</label>
                                                    <div class="layui-input-inline">
                                                        <input type="text" name="authorName" autocomplete="off" class="layui-input" value="${projectAnalyze.authorName}"
                                                               placeholder="众筹者" style="width: 212px"
                                                        >
                                                    </div>
                                                </li>
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
                                            </ul>
                                        </div>
                                        <ul class="search-form-content">
                                            <li class="form-item"><label class="search-form-lable">分析状态 </label>
                                                <div class="check-btn-inner" id="timeType">
                                                    <a id="all" href="javascript:void(0);" onclick="setTimeType($(this),'','#myForm')" ${empty projectAnalyze.labelId ? 'class="active"' : ''}>全部</a>
                                                    <c:forEach var="label" items="${labelList}">
                                                        <a href="javascript:void(0);" onclick="setTimeType($(this),'${label.id}','#myForm')" ${projectAnalyze.labelId == label.id ? 'class="active"' : ''}>${label.name}</a>
                                                    </c:forEach>
                                                    <input type="hidden" name="labelId" value="${projectAnalyze.labelId}"/>
                                                </div>
                                            </li>
                                            <li class="form-item-inline">
                                                <div class="sub-btns">
                                                    <a class="layui-btn layui-btn-danger" href="javascript:submitFunction('#myForm')">确定</a>
                                                    <a class="layui-btn layui-btn-normal" href="javascript:resetFunction('#myForm')">重置</a>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>


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
                                    <table class="jfix-table jfix-table-wrap">
                                        <colgroup>
                                            <col width="100px">
                                            <col width="60px">
                                            <col width="90px">
                                            <col width="100px">
                                            <col width="60px">
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th>众筹者</th>
                                            <th>渠道</th>
                                            <th>分析状态</th>
                                            <th>跟进记录</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="analyze" items="${list}">
                                            <tr data-id="${analyze.id}"
                                                <c:if test="${not empty analyze.style && analyze.style != 'white'}">class="tr_${analyze.style}"</c:if> >
                                                <td class="jfix-td-item" style="width: 100px;" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${analyze.authorId}','400px','470px')">
                                                    <div class="member-cell">
                                                        <div class="member-logo" style="background-image: url('${analyze.authorLogo}'),url(${ctx}/image/def_user_logo.png)"></div>
                                                        <div class="member-name ellipsis-1"><a class="tdl" title="${analyze.authorName}" href="javascript:void(0);">${analyze.authorName}</a></div>
                                                    </div>
                                                </td>
                                                <td class="jfix-td-item">
                                                    <c:if test="${analyze.parentName} != '同行者'">
                                                        ${analyze.parentName}
                                                    </c:if>
                                                </td>
                                                <td class="jfix-td-item">
                                                        <%-- <select name="" id="" class="j-table-sel" onchange="alert(this.value);">
                                                             <c:forEach var="label" items="${labelList}">
                                                                 <option value="${label.id}" ${analyze.labels == label.name ? 'selected"' : ''}>${label.name}</option>
                                                             </c:forEach>
                                                         </select>--%>
                                                        <%--     ${analyze.labels}--%>
                                                    <div class="j-table-sel-warp" data-id="${analyze.id}" data-name="label">
                                                        <div class="sel-text"></div>
                                                        <ul class="j-table-sel">
                                                            <c:forEach var="label" items="${labelList}">
                                                                <li onchange="changeLabel" data-value="${label.id}" class="<c:if test="${analyze.labelId == label.id}">active</c:if>">${label.name}</li>
                                                            </c:forEach>
                                                        </ul>
                                                    </div>
                                                </td>
                                                <td class="jfix-td-item  ellipsis-1" name="record">
                                                        ${analyze.recentlyRecord}
                                                </td>
                                                <td  class="tb-opts jfix-td-item" style="width: 100px">
                                                    <div class="comm-opts">
                                                        <a href="javascript:openDialogShow('数据查看','${ctx}/crowdfund/support/chartView.do?projectId=${analyze.id}','1050px','500px')" target="_self">
                                                            数据查看
                                                        </a>
                                                        <a href="javascript:void(0);" onclick="changeRecords('${analyze.id}','新增跟进记录','${ctx}/records/records/asynView.do?projectId=${analyze.id}','1050px','500px')" target="_self">
                                                            新增跟进记录
                                                        </a>
                                                        <a href="javascript:records('${analyze.id}')" target="_self">
                                                            跟进记录
                                                        </a>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="cl  jright-fix-table">
                                    <table class="jfix-table ">
                                        <colgroup>
                                            <col width="60px">
                                            <col width="100px">
                                            <col width="100px">
                                            <col width="90px">
                                            <col width="60px">
                                            <col width="60px">
                                            <col width="60px">
                                            <col width="60px">
                                            <col width="60px">
                                            <col width="60px">
                                            <c:forEach items="${dateList}" var="date">
                                                <col width="60px">
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
                                            <th>支持数</th>
                                            <th>支持金额</th>
                                            <th>时间</th>
                                            <c:forEach items="${dateList}" var="date">
                                                <th>${date}</th>
                                            </c:forEach>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="analyze" items="${list}" varStatus="status">
                                            <tr data-id="${analyze.id}" <c:if test="${not empty analyze.style && analyze.style != 'white'}">class="tr_${analyze.style}"</c:if>>
                                                <td>
                                                        ${analyze.cityName}
                                                </td>
                                                <td class="ellipsis-1" title="${analyze.company}">
                                                        ${analyze.company}
                                                </td>

                                                <td>
                                                        ${analyze.jobTitle}
                                                </td>
                                                <td>
                                                        ${analyze.mobile}
                                                </td>
                                                <td>
                                                    <div class="j-table-sel-warp" data-id="${analyze.id}" data-name="isFriend">
                                                        <div class="sel-text"></div>
                                                        <ul class="j-table-sel" data-id="${analyze.id}">
                                                            <li onchange="changeFriend" data-value="1" class="<c:if test="${analyze.isFriend == '是'}">active</c:if>">是</li>
                                                            <li onchange="changeFriend" data-value="0" class="<c:if test="${analyze.isFriend == '否'}">active</c:if>">否</li>
                                                        </ul>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="j-table-sel-warp" data-id="${analyze.id}" data-name="isGroup">
                                                        <div class="sel-text"></div>
                                                        <ul class="j-table-sel" data-id="${analyze.id}">
                                                            <li onchange="changeGroup" data-value="1" class="<c:if test="${analyze.isGroup == '是'}">active</c:if>">是</li>
                                                            <li onchange="changeGroup" data-value="0" class="<c:if test="${analyze.isGroup == '否'}">active</c:if>">否</li>
                                                        </ul>
                                                    </div>
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
                                                    <fmt:formatDate value="${analyze.createDate}" pattern="yy/MM/dd"/>
                                                </td>

                                                <c:forEach items="${dateList}" var="date" varStatus="dateStatus">
                                                    <td <c:if test="${analyze.isSuccess != '众筹成功' && (analyze.moneyMap[dateList[dateStatus.index+1]] == analyze.moneyMap[date] || analyze.moneyMap[dateList[dateStatus.index-1]] == analyze.moneyMap[date]) }">class="td_gray"</c:if>>
                                                         ${analyze.moneyMap[date]}
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                                <div class="page-content">
                                    <c:if test="${page.totalCount > 0}">
                                        <div class="l page-totalcount"><span class="f14 red">共<b id="totalCount">${page.totalCount}</b>条记录</span></div>
                                    </c:if>
                                    <div id="page_content" class="page-container"></div>
                                    <c:if test="${page.totalCount > 20}">
                                        <div class="ui-page-size">
                                            <div class="dib">每页显示</div>
                                            <div class="layui-input-inline">
                                                <select name="pageSize" lay-filter="limit">
                                                    <option value="">请选择</option>
                                                    <option value="20" ${page.limit == 20 ? 'selected="selected"' : ''}>20</option>
                                                    <option value="50" ${page.limit == 50 ? 'selected="selected"' : ''}>50</option>
                                                    <option value="100" ${page.limit == 100 ? 'selected="selected"' : ''}>100</option>
                                                </select>
                                            </div>
                                            <div class="dib">条</div>
                                        </div>
                                    </c:if>
                                </div>
                          </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<%@include file="../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript">
    var laytpl = null;
    var laypage = null;
    var element = null;


    $(function () {
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');

        layui.use(['laytpl', 'laypage', 'element'], function () {
            laytpl = layui.laytpl;
            laypage = layui.laypage;
            element = layui.element();


            element.on('tab(zc)', function (data) {
                if (data.index == 0) {
                    $('#isSuccess').val('');
                    $('#myForm').submit();
                }
                else if (data.index == 1) {
                    $('#isSuccess').val('0');
                    $('#myForm').submit();
                }
                else if (data.index == 2) {
                    $('#isSuccess').val('1');
                    $('#myForm').submit();
                }
                else if (data.index == 3) {
                    $('#isSuccess').val('3');
                    $('#myForm').submit();
                }
                else if (data.index == 4) {
                    $('#isSuccess').val('4');
                    $('#myForm').submit();
                }
            });
        });

        layui.use([ 'form'], function() {
            var form  = layui.form();
            form.on('select(limit)', function(data) {
                submitFunction('#myForm');
            });
        });

        $('.favorerNum').click(function () {
            var projectId = this.dataset.id;
            var name = this.dataset.name;
            window.location.href = '${ctx}/crowdfund/support/event/listForProjectId.do?eventId=${analyze.eventId}&projectId=' + projectId;
        });

        $('.actualAmount').click(function () {
            var projectId = this.dataset.id;
            var name = this.dataset.name;
            window.location.href = '${ctx}/crowdfund/support/event/listForProjectId.do?eventId=${analyze.eventId}&projectId=' + projectId;
        });

    })

    function openDialog(title, url, width, height, target) {
        layer.open({
            type: 2,
            area: [width, height],
            title: title,
            maxmin: true, //开启最大化最小化按钮
            content: url,
            btn: ['确定', '关闭'],
            yes: function (index, layero) {
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
                    setTimeout(function () {
                        top.layer.close(index);
                    }, 100);//延时0.1秒，对应360 7.1版本bug

                    setTimeout(function () {
                        window.location.reload();
                    }, 200);
                }
            },
            cancel: function (index) {
            }
        });
    }

    function changeRecords(id,title, url, width, height, target) {
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

                var value = iframeWin.contentWindow.doSubmit();
                if (util.isValid(value)) {
                    $('tr[data-id="'+id+'"]').find("td[name='record']").text(value);
                    setTimeout(function() {
                        top.layer.close(index);
                    }, 100);//延时0.1秒，对应360 7.1版本bug
                }
            },
            cancel : function(index) {
            }
        });
    }

    function changeLabel(data) {
        $.post('${ctx}/crowdfund/analyze/labelSave.do', {
            id: data.value,
            projectId: data.id
        }, function (res) {
            if (res.success) {
                var $changeTrs = $('tr[data-id="' + data.id + '"]');
                for (var i = 0; i < $changeTrs.length; i++) {
                    $($changeTrs[i]).prop('class', 'tr_' + res.data);
                }
                top.layer.msg('提交成功', {icon: 1}, function (index) {
                });
            } else {
                top.layer.msg(res.description, {icon: 2});
            }
        });
    }

    function changeFriend(data) {
        $.post("${ctx}/crowdfund/analyze/changeFriend.do", {
            projectId: data.id, status: data.value
        }, function (data) {
            if (data.success == true) {
            } else {
                layer.alert("操作失败", {
                    icon: 6
                });
            }
        });
    }

    function changeGroup(data) {
        $.post("${ctx}/crowdfund/analyze/changeGroup.do", {
            projectId: data.id, status: data.value
        }, function (data) {
            if (data.success == true) {
            } else {
                layer.alert("操作失败", {
                    icon: 6
                });
            }
        });
    }

    $("#btnExport").click(function () {
        layer.confirm('确认要导出Excel吗?', {
            icon: 3,
            title: '系统提示'
        }, function (index) {

            $('#myForm').attr('action', '${ctx}/crowdfund/analyze/listExport.do');
            $('#myForm').submit();
            $('#myForm').attr('action', '${ctx}/crowdfund/analyze/list.do');
            top.layer.close(index);
        });
    });

    function records(id) {
        window.location.href = '${ctx}/records/records/list.do?targetId=' + id + '&activityId=${projectAnalyze.targetId}&eventId=${projectAnalyze.eventId}';
    }
</script>
</body>
</html>
