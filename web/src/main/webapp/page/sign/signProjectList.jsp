<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>我发布的打卡项目</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <%@include file="../include/commonFile.jsp"%>
    <style type="text/css">
        .f-def-dialog .f-dialog-content {
            width: 400px !important;
            height: 300px !important;
        }

        .f-dialog-content .dialog-detail {
            width: auto !important;
        }

        .dialog-detail .download-img {
            margin-top: 0px !important;
        }
    </style>
</head>
<!--头部-->
<%@include file="../include/header.jsp"%>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp"%>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a href="${ctx}/sign/project/view.do" class="layui-btn layui-btn-radius layui-btn-danger"> <i
                            class="iconfont icon-add btn-icon"></i>发布打卡项目
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">打卡项目管理</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/sign/project/list.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo" />
            </form>
            <div class="c-time-list-content">
                <ul>
                    <c:if test="${page.totalCount == 0}">
                        <div class="f16 tc mt15">还没有数据，请点击右上角的“发布打卡项目”，发布和编辑内容</div>
                    </c:if>
                    <c:forEach var="project" items="${list}">
                        <li>
                            <div class="detail-content">
                                <a href="${ctx}/sign/project/view.do?id=${project.id}" target="_self" class="act-logo"
                                   style="background-image: url('${project.projectLogo}'),url(${ctx}/image/img_bg.png)"></a>
                                <div class="detail">
                                    <div class="act-title">
                                        <a title="${project.title}" href="${ctx}/sign/project/view.do?id=${project.id}" target="_self"
                                           class="title f18 ell db">${project.title}</a>
                                    </div>
                                    <div>
                                        <p class="act-price">
                                            截止时间：
                                            <fmt:formatDate value="${project.endTime}" pattern="yyyy-MM-dd HH:mm" />
                                        </p>
                                        <p class="act-price">
											<span style="margin-right: 10px;">报名费用：
                                                 	<c:if test="${project.price == 0.0}">
                                                        <b class="active-red">免费</b>
                                                    </c:if>
                                                   	<c:if test="${project.price > 0.0}">
                                                              <b class="active-red"> ${project.price}</b>元/位
                                                    </c:if>
											</span> <span>报名人数：<b class="active-red">${project.applyNum}</b>人<span> <span>发布者：<b class="active-red">${project.authorName}</b><span>
                                        </p>

                                        <p class="act-price">
                                            开始时间：
                                            <fmt:formatDate value="${project.startTime}" pattern="yyyy-MM-dd HH:mm" />
                                        </p>
                                    </div>
                                    <div class="opts-btns tb-opts"  style="width:475px;">
                                        <div class="comm-opts">
                                            <a class="qr-btn" href="javascript:void(0);"><i class="iconfont icon-qrcode btn-icon"></i> 二维码</a>
                                            <a href="${ctx}/sign/project/view.do?id=${project.id}"> <i class="iconfont icon-edit btn-icon"></i>
                                                编辑
                                            </a>
                                            <a href="${ctx}/sign/group/list.do?projectId=${project.id}"> <i class="iconfont icon-group btn-icon"></i>
                                                小组管理
                                            </a>
                                            <a href="${ctx}/sign/member/list.do?projectId=${project.id}"> <i class="iconfont icon-people btn-icon"></i>
                                                人员审核
                                            </a>
                                            <a href="javascript:deleteProject('${project.id}')">
                                                <i class="iconfont icon-delete btn-icon"></i> 删除
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="f-def-dialog bmDialog">
                                <div class="f-dialog-shadow"></div>
                                <div class="f-dialog-content">
                                    <span class="close-icon"><i class="iconfont icon-close"></i></span>
                                    <div class="dialog-detail">
                                        <p class="f16 gray">扫码二维码可预览分享</p>
                                        <div style="width: 150px; margin: 0px auto; margin-top: 10px">
                                            <div class="l mr20">
                                                <div class="f18">打卡二维码</div>
                                                <img class="download-img" src="${qr_code}/${project.qrCode }" />
                                                <%--<div>
                                                    <a class="download-qrcode" href="javascript:download('${ctx}','${project.qrCode}')">下载二维码</a>
                                                </div>
                                                <div>
                                                    <a class="download-qrcode looklink" href="javascript:lookLink('','', '查看报名二维码链接')">查看报名链接</a>
                                                </div>--%>
                                            </div>
                                            <div class="cl"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="page-content">
                <c:if test="${page.totalCount > 0}">
                    <div class="l page-totalcount"><span class="f14 red">共<b id="totalCount">${page.totalCount}</b>条记录</span></div>
                </c:if>
                <div id="page_content" class="page-container"></div>
            </div>
        </div>
    </section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript">

    // 二维码弹窗显示/隐藏
    qrCodeDialog('.c-time-list-content', 'qr-btn', '.detail-content', '.bmDialog');
    $(function () {
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
    })
    function deleteProject(id) {
        layer.confirm('确定删除项目？', {
            icon : 3,
            title : '系统提示'
        }, function(index) {
            $.post("${ctx}/sign/project/delete.do", {
                id : id
            }, function(data) {
                if (data.success == true) {
                    layer.alert("删除成功", {
                        icon : 6
                    }, function(index) {
                        window.location.reload();
                    });
                } else {
                    layer.alert(data.description, {
                        icon : 2
                    });
                }
            });
        });
    };
</script>
</body>
</html>