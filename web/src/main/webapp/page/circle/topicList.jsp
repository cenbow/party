<%@page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>话题管理</title>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/table.css">
    <%@include file="../include/commonFile.jsp" %>
    <style type="text/css">
        .c-time-list-content .detail {
            width: 100% !important;
        }

        .bg-avater-big {
            width: 150px;
            height: 120px;
            display: inline-block;
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center;
            position: relative;
            border: #fff 2px solid;
            border-radius: 4px;
        }

        .info-container .member-logo {
            margin-bottom: 0;
            position: relative;
        }

        .vip-big {
            position: absolute;
            right: -6px;
            bottom: 0px;
            background-color: #fff;
            display: block;
            width: 16px;
            height: 16px;
            text-align: center;
            border-radius: 50%;
            color: #e8473f;
        }

        .vip-big .vip-big-font {
            font-size: 16px;
            position: absolute;
            color: #e8473f;
            top: 50%;
            left: 50%;
            -webkit-transform: translate(-50%, -47%);
            transform: translate(-50%, -47%);
        }

        .veritcal-center {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            -webkit-transform: translate(-50%, -50%);
        }

        .avater-big {
            width: 40px;
            height: 40px;
            display: inline-block;
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center;
            border-radius: 50%;
            position: relative;
            border: #fff 2px solid;
        }

        .huangguan-big {
            font-size: 16px !important;
            color: #ffc107 !important;
        }

        .tag-name {
            padding: 3px 5px;
            background: #f4615c;
            border-radius: 3px;
            margin-left: 5px;
            color: #fff;
        }
    </style>
</head>
<!--头部-->
<%@include file="../include/header.jsp" %>
<div class="index-outside">
    <%@include file="../include/sidebar.jsp" %>
    <!--内容-->
    <section>
        <div class="section-main">
            <div class="c-time-list-header">
                <div class="r">
                    <a href="javascript:history.back();" class="layui-btn layui-btn-radius layui-btn-danger"> <i
                            class="iconfont icon-refresh btn-icon"></i>返回
                    </a>
                </div>
                <div class="ovh">
                    <span class="title f20">${circle.name}&nbsp;&gt;&nbsp;话题管理</span> <span class="f12">共<b id="totalCount">${page.totalCount}</b>条记录
					</span>
                </div>
            </div>
            <form class="layui-form" action="${ctx}/circle/topic/list.do" id="myForm" method="post">
                <input type="hidden" name="pageNo" id="pageNo"/>
                <input type="hidden" name="circle" value="${circle.id}"/>
                <div class="f-search-bar">
                    <div class="search-container">
                        <ul class="search-form-content">
                            <li class="form-item-inline"><label class="search-form-lable">内容</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="content" autocomplete="off" class="layui-input" value="${dynamic.content}" placeholder="请输入查询话题内容">
                                </div>
                            </li>
                            <li class="form-item-inline"><label class="search-form-lable">发布者</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="memberName" autocomplete="off" class="layui-input" value="${input.memberName}" placeholder="请输入查询话题发布者">
                                </div>
                            </li>
                            <li class="form-item-inline"><label class="search-form-lable">标签</label>
                                <div class="layui-input-inline">
                                    <select name="topicTagId">
                                        <option value="">选择标签</option>
                                        <c:forEach var="tag" items="${tags}">
                                            <option value="${tag.id}" ${topic.topicTagId == tag.id?'selected="selected"' : ''}>${tag.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </li>
                        </ul>
                        <ul class="search-form-content">
                            <li class="form-item"><label class="search-form-lable">发布时间</label>
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
                            <li class="form-item-inline">
                                <div class="sub-btns">
                                    <a class="layui-btn layui-btn-danger" href="javascript:submitFunction('#myForm')">确定</a>
                                    <a class="layui-btn layui-btn-normal" href="javascript:resetFunction('#myForm')">重置</a>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
            <div class="c-time-list-content">
                <ul id="view">
                    <c:if test="${page.totalCount == 0}">
                        <div class="f16 tc mt15">还没有数据</div>
                    </c:if>
                    <c:forEach var="dynamic" items="${dynamics}">
                        <li>
                            <div class="detail-content">
                                <div class="detail">
                                    <div class="act-title">
                                        <div class="user-infos l">
                                            <div class="avater-big l" id="logo" style="background-image: url('${dynamic.authorMember.logo}?imageMogr2/auto-orient/thumbnail/!50p'),url(${ctx}/image/def_user_logo.png);">
                                                <div class="vip-big" id="userStatus" ${dynamic.authorMember.userStatus != 1 ? 'style="display: none;"' : '' }>
                                                    <span class="iconfont icon-vip red vip-big-font"></span>
                                                </div>
                                            </div>
                                            <div class="l pt10 ml5"><span class="f16">${dynamic.authorMember.realname}</span><c:if test="${dynamic.tagName != null && dynamic.tagName != ''}"><span class="tag-name">${dynamic.tagName}</span></c:if></div>
                                            <div class="l" style="padding-top: 12px;"><span class="iconfont icon-huangguan huangguan-big" ${dynamic.authorMember.isExpert != 1 ? 'style="display: none;"' : '' } ></span></div>
                                            <div class="cl"></div>
                                        </div>
                                        <span class="status f16" style="height: 44px;line-height: 44px;"><fmt:formatDate value="${dynamic.createDate}" pattern="yyyy-MM-dd HH:mm"/></span>
                                        <div class="cl"></div>
                                    </div>
                                    <div class="act-title f16">
                                            ${dynamic.content}
                                    </div>
                                    <div class="act-title" id="dynamic_imgs">
                                        <c:forEach var="pic" items="${dynamic.picList}">
                                            <div class="bg-avater-big" data-url="${pic.picUrl}" style="background-image: url('${pic.picUrl}'),url(${ctx}/image/img_bg.png)"></div>
                                        </c:forEach>
                                    </div>
                                    <div class="act-title" style="margin-bottom: 0px;">
                                        <span class="mr20"><i class="iconfont icon-like btn-icon mr5"></i><b style="height: 20px; line-height: 20px;display: inline-block;">${dynamic.loveNum}</b></span>
                                        <span><i class="iconfont icon-comment1 btn-icon mr5"></i><b style="height: 20px; line-height: 20px; display: inline-block;">${dynamic.commentNum}</b></span>
                                    </div>
                                    <div class="opts-btns tb-opts" style="width:475px">
                                        <div class="comm-opts">
                                            <a class="qr-btn" href="javascript:void(0);"><i class="iconfont icon-qrcode btn-icon"></i> 二维码</a><span>|</span>
                                            <a href="${ctx}/circle/topic/form.do?id=${dynamic.id}&cId=${circle.id}"> <i class="iconfont icon-edit btn-icon"></i> 编辑</a><span>|</span>
                                            <a href="javascript:delTopic('${dynamic.id}');">
                                                <i class="iconfont icon-delete btn-icon"></i> 删除
                                            </a><span>|</span>
                                            <a href="${ctx}/circle/topicLove/loveList.do?refId=${dynamic.id }"><i class="iconfont icon-like btn-icon"></i>点赞管理</a><span>|</span>
                                            <a href="${ctx}/circle/topicCmt/commentList.do?refId=${dynamic.id}"> <i class="iconfont icon-peoplelist btn-icon"></i>
                                                评论管理
                                            </a>
                                            <c:if test="${dynamic.isTop == 1}">
                                            <a href="javascript:cancleTop('${dynamic.id}')"> <i class="iconfont icon-upblock btn-icon"></i>
                                                取消置顶
                                            </a>
                                            </c:if>
                                            <c:if test="${dynamic.isTop == 0}">
                                                <a href="javascript:toTop('${dynamic.id}')"> <i class="iconfont icon-top btn-icon"></i>
                                                    置顶
                                                </a>
                                            </c:if>
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
                                        <img class="download-img" src="${qr_code}/${dynamic.qrCodeUrl}"/>
                                        <div>
                                            <a class="download-qrcode" href="javascript:download('${ctx}','${dynamic.qrCodeUrl}')">下载二维码</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div id="page_content" class="page-container"></div>
        </div>
    </section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/list.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/script/common/table_option.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/uploadCI/uploadCIUtil.js"></script>
<script type="text/javascript">
    $(function () {
        showActive('${input.createStart}', '${input.createEnd}', '#timeType');
        //加载分页
        loadPage("page_content", '${page.totalPages}', '${page.page}', '#myForm');
        // 二维码弹窗显示/隐藏
        qrCodeDialog('.c-time-list-content', 'qr-btn', '.detail-content', '.bmDialog');
    })
    //置顶
    function toTop(id) {
        var url = '${ctx}/circle/topic/toTop.do?circle=${circle.id}&dynamic='+id;
        $.post(url, {}, function (data) {
            if (data.success == true) {
                layer.msg("设置成功", {
                    icon: 6,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function(){
                    location.reload();
                });
            } else {
                layer.alert("设置失败", {
                    icon: 6
                });
            }
        });
    }
    //取消置顶
    function cancleTop(id){
        var url = '${ctx}/circle/topic/cancleTop.do?circle=${circle.id}&dynamic='+id;
        $.post(url, {}, function (data) {
            if (data.success == true) {
                layer.msg("设置成功", {
                    icon: 6,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function(){
                    location.reload();
                });
            } else {
                layer.alert("设置失败", {
                    icon: 6
                });
            }
        });
    }
    function delTopic(id) {
        layer.confirm('确定要删除该话题么？', {
                icon: 3,
                title: '系统提示'
            }, function (index) {
                var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do');
                var fileIds = new Array();
                var $imgOs = $('#dynamic_imgs .bg-avater-big');
                for(var i=0; i<$imgOs.length;i++){
                    fileIds.push($($imgOs[i]).data('url').replace(/(.*\/)*([^.]+).*/ig, "$2"))
                }

                var i = 0;

                function del() {
                    var fileId = fileIds[i]
                    if (fileId) {
                        function cb() {
                            uploadFile.delQcloudPhoto(fileId, function (ret) {
                                i++;
                                del();
                            });
                        }

                        var dynamicId = $('input[name=id]').val();
                        if (dynamicId == null || dynamicId == '') {
                            cb();
                        } else {
                            $.ajax({
                                type: 'post',
                                url: '${ctx}/dynamic/dynamic/delDynamicPic.do',
                                data: {
                                    url: picUrl,
                                    dynamicId: dynamicId
                                },
                                dataType: 'json',
                                context: $('body'),
                                success: function (data) {
                                    cb();
                                },
                                error: function (xhr, type) {
                                    console.err('err', 'ajax错误', xhr, type);
                                }
                            })
                        }
                    } else {
                        var url = '${ctx}/circle/topic/delete.do?id='+id;
                            $.post(url, {}, function (data) {
                                if (data.success == true) {
                                    layer.msg("删除成功", {
                                        icon: 6,
                                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                                    }, function(){
                                        location.reload();
                                    });
                                } else {
                                    layer.alert("删除失败", {
                                        icon: 6
                                    });
                                }
                            });
                    }
                }
                del();
            }
        );
    }
</script>
</body>
</html>