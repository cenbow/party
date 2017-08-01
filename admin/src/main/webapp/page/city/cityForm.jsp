<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>${city.id == null ? '添加' : '编辑'}开放城市</title>
    <%@include file="../include/commonFile.jsp" %>
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/list.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/common/form.css">
    <link rel="stylesheet" href="${ctx}/themes/default/css/ui/city/city_form.css">
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
                    <a href="javascript:history.back();" class="layui-btn layui-btn-radius layui-btn-danger"> <i class="iconfont icon-refresh btn-icon"></i>返回</a>
                </div>
                <div class="ovh">
                    <span class="title f20"><a href="${ctx}/city/list.do" class="">开放城市管理</a> > ${city.id == null ? '添加' : '编辑'}开放城市</span>
                </div>
            </div>
            <!-- 正文请写在这里 -->
            <div class="add-form-content">
            <form id="myForm" class="layui-form mt20" method="post" action="${ctx}/city/save.do">
                <input type="hidden" name="id" id="id" value="${city.id}"/>
                <div class="layui-form-item" id="name_input_content">
                    <label class="layui-form-label">名称<span class="f-verify-red">*</span></label>
                    <div class="layui-input-inline">
                        <input type="text" name="name" lay-verify="name" id="cityName" placeholder="请输入城市名称" class="layui-input" value="${city.name}">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">是否开放</label>
                        <div class="layui-input-inline">
                            <input type="radio" name="isOpen" value="1" title="是" ${city.isOpen != null && city.isOpen == '1' ? 'checked="checked"' : ''}>
                            <input type="radio" name="isOpen" value="0" title="否" ${city.isOpen == null || city.isOpen == '0' ? 'checked="checked"' : ''}>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">排序号</label>
                        <div class="layui-input-inline">
                            <input type="text" name="sort" lay-verify="sort" autocomplete="off" class="layui-input" value="${city.sort}">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <a href="javascript:void(0)" class="layui-btn layui-btn-danger" lay-submit lay-filter="*">立即提交</a>
                    </div>
                </div>
            </form>
            </div>
        </div>
    </section>
</div>
<!--底部-->
<%@include file="../include/footer.jsp" %>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/bootstrap3-typeahead/bootstrap3-typeahead.js"></script>
<script>
    $(function () {
        layui.use(['form'], function () {
            var form = layui.form();

            //自定义验证规则
            form.verify({
                name: function (value) {
                    if (value == "") {
                        return '请输入城市名称';
                    }else if(areaNameIsRepeate()){
                        return '已有相同名称的城市';
                    }
                },
                sort: function (value) {
                    if (!util.checkNumber(value)) {
                        return "请输入正确的数字";
                    }
                }
            });

            //监听提交
            form.on('submit', function (data) {
                function  submit() {
                    $(data.elem).removeAttr("lay-submit");
                    var action = $("#myForm").attr("action");
                    $.post(action, $('#myForm').serialize(), function (res) {
                        if (res.success) {
                            top.layer.msg('提交成功', {
                                icon: 1
                            }, function (index) {
                                history.back();
                            });
                        } else {
                            top.layer.msg('提交失败', {
                                icon: 2
                            });
                        }
                    });
                    return false;
                }
                if(!areaIsExit()){
                    layer.confirm('该城市与国标中的城市不匹配，确定提交么？',function () {
                        submit();
                    });
                }else {
                    submit();
                }
            });
        });
        function areaNameIsRepeate() {
            var isRepeate = false;
            $.ajax({
                type: 'POST',
                async: false, // 使用同步的方法
                data: {
                    name: $('#cityName').val(),
                    id : $('#id').val()
                },
                dataType: 'json',
                success: function (ret) {
                    isRepeate = !ret.success;
                },
                url: "${ctx}/city/validateName.do"
            });
            return isRepeate;
        }
        function  areaIsExit() {
            var isExit = false;
            $.ajax({
                type: 'POST',
                async: false, // 使用同步的方法
                data: {
                    value:$('#cityName').val(),
                    type : 'equal'
                },
                dataType: 'json',
                success: function (ret) {
                    var list = ret.data;
                    if(list.length > 0){
                        isExit = true;
                    }
                },
                url: "${ctx}/area/th_findByName.do"
            });
            return isExit;
        }
        $('#cityName').typeahead({
            source: function(query, process) {
                $.ajax({
                    url : "${ctx}/area/th_findByName.do",
                    type : "post",
                    dataType : "json",
                    data : {
                        value:$('#cityName').val(),
                        type : 'like'
                    },
                    success : function(ret) {
                        var list = ret.data;

                        var results = $.map(list, function(city) {
                            return city.name;
                        });
                        process(results);
                    }
                });
            },
            updater: function (item) {
               $('#cityName').val(item);
               return item;
            }
        });
        $('#name_input_content').delegate('li','click',function (e) {
            console.log($(this).text());
        })
    })

</script>
</body>
</html>