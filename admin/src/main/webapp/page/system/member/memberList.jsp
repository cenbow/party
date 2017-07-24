<%@page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>用户管理</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/system/member/member_list.css">
    <%@include file="../../include/commonFile.jsp" %>
</head>
<!--头部-->
<%@include file="../../include/header.jsp" %>
<div class="index-outside">
    <%@include file="../../include/sidebar.jsp" %>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a href="${ctx}/system/member/memberForm.do" class="layui-btn layui-btn-radius layui-btn-danger">
                        <i class="iconfont icon-add btn-icon"></i>添加用户
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">用户管理</span>
                    <span class="f12">共<b>${page.totalCount}</b>条记录</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/system/member/memberList.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo" value="${page.page}"/>
                <div class="f-search-bar">
                    <div class="search-container">

                        <ul class="search-form-content">
                            <li class="form-item-inline"><label class="search-form-lable">昵称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="realname" autocomplete="off" class="layui-input" value="${member.realname}" placeholder="请输入查询昵称">
                                </div>
                            </li>
                            <li class="form-item-inline"><label class="search-form-lable">手机号</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="mobile" autocomplete="off" class="layui-input" value="${member.mobile}" placeholder="请输入查询手机号">
                                </div>
                            </li>
                            <li class="form-item-inline"><label class="search-form-lable">状态</label>
                                <div class="layui-input-inline">
                                    <select name="status">
                                        <option value="">全部</option>
                                        <option value="1" ${status == "1" ? 'selected="selected"' : ''}>已认证</option>
                                        <option value="0,2,3" ${status == "0,2,3" ? 'selected="selected"' : ''}>未认证</option>
                                    </select>
                                </div>
                            </li>
                        </ul>
                        <ul class="search-form-content">
                            <li class="form-item">
                                <label class="search-form-lable">用户身份</label>
                                <div class="check-btn-inner">
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),'','#myForm')" ${empty memberType ? 'class="active"' : ''}>全部</a>
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),'userStatus:1','#myForm')" ${memberType == 'userStatus:1' ? 'class="active"' : ''}>认证</a>
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),'isExpert:1','#myForm')" ${memberType == 'isExpert:1' ? 'class="active"' : ''}>达人</a>
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),'isPartner:1','#myForm')" ${memberType == 'isPartner:1' ? 'class="active"' : ''}>合作商</a>
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),'isDistributor:1','#myForm')" ${memberType == 'isDistributor:1' ? 'class="active"' : ''}>分销商</a>
                                    <input type="hidden" name="memberType" value="${memberType}"/>
                                </div>
                                <div class="cl"></div>
                            </li>
                            <li class="form-item-inline">
                                <div class="sub-btns">
                                    <a class="layui-btn layui-btn-danger" href="javascript:submitFunction('#myForm')">确定</a>
                                    <a class="layui-btn layui-btn-normal" href="javascript:resetFunction('#myForm')">重置</a>
                                </div>
                            </li>
                        </ul>
                        <ul class="search-form-content">

                            <li class="form-item"><label class="search-form-lable">注册时间</label>
                                <div class="check-btn-inner" id="timeType">
                                    <a id="all" href="javascript:void(0);" onclick="setTimeType($(this),0,'#myForm')" ${empty input.timeType || input.timeType == 0 ? 'class="active"' : ''}>全部</a>
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),1,'#myForm')" ${input.timeType == 1 ? 'class="active"' : ''}>今天</a>
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),2,'#myForm')" ${input.timeType == 2 ? 'class="active"' : ''}>本周内</a>
                                    <a href="javascript:void(0);" onclick="setTimeType($(this),3,'#myForm')" ${input.timeType == 3 ? 'class="active"' : ''}>本月内</a>
                                    <input type="hidden" name="timeType" value="${input.timeType}"/>
                                </div>
                                <div class="layui-inline">
                                    <div class="layui-input-inline">
                                        <input class="layui-input" type="text" name="createStart" value="${input.createStart}" placeholder="开始日" onclick="layui.laydate({elem: this})">
                                    </div>
                                    -
                                    <div class="layui-input-inline">
                                        <input class="layui-input" type="text" name="createEnd" value="${input.createEnd}" placeholder="截止日" onclick="layui.laydate({elem: this})">
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
            <div class="list-content">
                <div class="cl">
                    <table class="layui-table">
                        <colgroup>
                            <col width="250">
                            <col width="100">
                            <col width="140">
                            <col width="70">
                            <col width="280">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>昵称</th>
                            <th>手机号</th>
                            <th>注册时间</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="member" items="${members}">
                            <tr>
                                <td class="table-member" onclick="openDialogShow('用户名片','${ctx}/system/member/memberView.do?id=${member.id}','400px','470px')">
                                    <div class="member-logo" style="background-image: url('${member.logo}'),url(${ctx}/image/def_user_logo.png)"></div>
                                    <div class="member-name ellipsis-1"><a class="blue" title="${member.realname}" href="javascript:void(0);">${member.realname}</a></div>
                                </td>
                                <td>
                                        ${member.mobile}
                                </td>
                                <td>
                                    <fmt:formatDate value="${member.createDate}" pattern="yyyy-MM-dd HH:mm"/>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${member.userStatus == 0 || member.userStatus == 2 || member.userStatus == 3}">
                                            未认证
                                        </c:when>
                                        <c:when test="${member.userStatus == 1}">
                                            已认证
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td class="tb-opts">
                                    <div class="comm-opts">
                                        <a href="${ctx}/system/member/memberForm.do?id=${member.id}" target="_self">
                                            编辑
                                        </a>
                                            <%-- 											<a href="javascript:deleteObj('确定要删除吗？','${member.id}');" target="_self"> --%>
                                        <!-- 												删除 -->
                                        <!-- 											</a> -->
                                        <c:if test="${member.userStatus == 0 || member.userStatus == 3}">
                                            <a class="green" href="javascript:verify('确认要通过吗？','1','${member.id}')">通过</a>
                                            <a class="red" href="javascript:verify('确认要拒绝吗？','2','${member.id}')">拒绝</a>
                                        </c:if>
                                        <c:if test="${member.isDistributor == 0}">
                                            <a class="green" href="javascript:distributionSwitch('${member.id}', 1)">开启分销</a>
                                        </c:if>
                                        <c:if test="${member.isDistributor == 1}">
                                            <a class="red" href="javascript:distributionSwitch('${member.id}', 0)">关闭分销</a>
                                        </c:if>
                                        <a target="_self" href="javascript:openDialog('分配角色','${ctx}/system/role/distributionRole.do?memberId=${member.id}&type=0','400px','300px')">
                                            分配角色
                                        </a>
                                        <c:choose>
                                            <c:when test="${member.userStatus == 1 }">
                                                <c:choose>
                                                    <c:when test="${member.isAdmin == 1 }">
                                                        <a href="javascript:adminSwitch('${member.id}', 0)">取消管理员</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="javascript:adminSwitch('${member.id}', 1)">设为管理员</a>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${member.isExpert == 1 }">
                                                        <a href="javascript:expertSwitch('${member.id}', 0)">取消达人</a>
                                                        <a href="${ctx}/dynamic/dynamic/helpPublish.do?memberId=${member.id}">代发动态</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="javascript:expertSwitch('${member.id}', 1)">设为达人</a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                        </c:choose>
                                        <a target="_self"  href="javascript:openDialog('解绑授权账户','${ctx}/system/member/getAuthList.do?memberId=${member.id}&type=0','500px','400px')">解绑授权</a>
                                    </div>

                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div id="page_content" class="page-container"></div>
        </div>
    </section>
</div>
<!--底部-->
<%@include file="../../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript">

    showActive('${input.createStart}', '${input.createEnd}', '#timeType');
    $(function () {
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    })
    function deleteObj(content, targetId) {
        layer.confirm(content, {icon: 3, title: '系统提示'}, function (index) {
            $.post("${ctx}/system/member/delete.do", {id: targetId}, function (data) {
                if (data.success == true) {
                    layer.msg("删除成功", {icon: 6});
                    submitFunction('#myForm')
                    layer.close(index);
                }
            });
        });
    }

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

    function verify(content, status, targetId) {
        layer.confirm(content, {icon: 3, title: '系统提示'}, function (index) {
            $.post('${ctx}/system/member/verify.do', {id: targetId, userStatus: status}, function (res) {
                if (res.success) {
                    layer.close(index);
                    submitFunction('#myForm')
                }
            });
        });
    }

    function distributionSwitch(id, isDistribution) {
        var content;
        if (isDistribution == 1) {
            content = '确认开启分销？';
        }
        else if (isDistribution == 0) {
            content = '确认关闭分销？';
        }
        layer.confirm(content, {icon: 3, title: '系统提示'}, function (index) {
            $.post('${ctx}/system/member/distribution/switch.do', {id: id, isDistributor: isDistribution}, function (res) {
                if (res.success) {
                    layer.close(index);
                    submitFunction('#myForm')
                }
            });
        });
    }

    function adminSwitch(id, isAdmin) {
        var content;
        if (isAdmin == 1) {
            content = '确认设为管理员？';
        } else if (isAdmin == 0) {
            content = '确认取消管理员？';
        }
        layer.confirm(content, {icon: 3, title: '系统提示'}, function (index) {
            $.post('${ctx}/system/member/setAdmin.do', {id: id, isAdmin: isAdmin}, function (res) {
                if (res.success) {
                    layer.msg('操作成功', {icon: 1}, function (index) {
                        layer.close(index);
                        submitFunction('#myForm')
                    });
                } else {
                    layer.msg('操作失败', {icon: 2}, function (index) {
                        layer.close(index);
                        submitFunction('#myForm')
                    });
                }
            });
        });
    }

    function expertSwitch(id, isExpert) {
        var content;
        if (isExpert == 1) {
            content = '确认设为达人？';
        } else if (isExpert == 0) {
            content = '确认取消达人？';
        }
        layer.confirm(content, {icon: 3, title: '系统提示'}, function (index) {
            $.post('${ctx}/system/member/setExpert.do', {id: id, isExpert: isExpert}, function (res) {
                if (res.success) {
                    layer.msg('操作成功', {icon: 1}, function (index) {
                        layer.close(index);
                        submitFunction('#myForm')
                    });
                } else {
                    layer.msg('操作失败', {icon: 2}, function (index) {
                        layer.close(index);
                        submitFunction('#myForm')
                    });
                }
            });
        });
    }

</script>
</body>
</html>