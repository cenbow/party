<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tag.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/css/ui/form/jquery-ui.css">
<style type="text/css">

	.ui-tooltip{
		background-color: #666!important;
		padding: 5px!important;
	}
	
	.ui-tooltip-content{
		font-size: 10px!important;
		color: white!important;
	}

    #event_template_form_items .layui-form-item:hover {
        cursor: pointer
    }

    .layui-form-checkbox[lay-skin=primary] {
        height: auto !important;
        line-height: normal !important;
        border: none !important;
        margin-right: 0;
        padding-right: 0;
        background: 0 0
    }

    .layui-form-checkbox i {
        position: absolute;
        right: 0;
        width: 30px;
        color: #fff;
        font-size: 20px;
        text-align: center;
    }

    .layui-icon {
        font-family: layui-icon !important;
        font-size: 16px;
        font-style: normal;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale
    }

    .layui-form-checkbox[lay-skin=primary] span {
        float: right;
        padding-right: 15px;
        line-height: 18px;
        background: 0 0;
        color: #666
    }

    .layui-form-checkbox[lay-skin=primary] i {
        position: relative;
        top: 0;
        width: 16px;
        line-height: 16px;
        border: 1px solid #d2d2d2;
        font-size: 12px;
        border-radius: 2px;
        background-color: #fff;
        -webkit-transition: .1s linear;
        transition: .1s linear
    }

    .layui-form-checkbox[lay-skin=primary]:hover i {
        border-color: #e8473f;
        color: #fff
    }

    #event_template_form_items .sortable-placeholder {
        border: 2px dashed #dcdcdc;
        background: #f6f6f6
    }

    .layui-form-checked[lay-skin=primary] i {
        border-color: #e8473f;
        background-color: #e8473f;
        color: #fff
    }

    .layui-checkbox-disbaled[lay-skin=primary] span {
        background: 0 0 !important
    }

    .layui-checkbox-disbaled[lay-skin=primary]:hover i {
        border-color: #d2d2d2
    }

    .layui-form-item .layui-form-checkbox[lay-skin=primary] {
        margin-top: 10px
    }

    .event-create-body {
        font-size: 14px;
        margin-bottom: 35px;
        background: #fff;
        *zoom: 1
    }

    .border {
        border: 1px solid #eee;
        padding-left: 0px;
        padding-right: 0px;
        border-radius: 4px;
    }

    fieldset legend {
        font-size: 20px;
        margin-left: 30px;
    }

    .layui-form .layui-inline {
        padding-left: 30px !important;
        margin-bottom: 15px !important;
    }

    .event-create-label a:focus, .event-create-label a:hover {
        text-decoration: none
    }

    .event-create-sign-form + .aside {
        float: left;
        width: 196px;
        margin: 10px;
        padding-top: 10px;
        padding-right: 10px;
        padding-left: 10px;
        border: 1px solid #e8e8e8;
        border-radius: 4px
    }

    .event-create-sign-form + .aside h3 {
        font-size: 14px;
        margin-top: 0;
        padding-bottom: 10px;
        padding-left: 10px;
        border-bottom: 1px solid #bbb
    }

    .event-create-sign-form + .aside .btn {
        font-size: 14px;
        height: 32px;
        padding-left: 10px;
        text-align: left;
        color: #5a5a5a;
        border: 1px solid #e7e7e7;
        border-radius: 0;
        background: #f6f6f6;
        *font-size: 12px
    }

    .event-create-sign-form + .aside .btn:focus, .event-create-sign-form + .aside .btn:hover {
        border-color: #e8473f;
        background: #e5e5e5
    }

    .event-create-sign-form + .aside .btn:active {
        border-color: #e8473f;
        background: #d2d2d2
    }

    .event-create-sign-form + .aside .btn span:first-child {
        float: left;
        margin-right: 10px
    }

    .event-create-sign-form-usual .btn {
        width: 94px;
    }

    .event-create-sign-form-custom .btn {
        width: 100%;
        margin-bottom: 10px;
        *display: block
    }

    .event-create-sign-form-usual ul {
        float: left
    }

    .event-create-sign-select > div {
        position: relative;
        float: left;
        margin-bottom: 10px;
    }

    .event-create-sign-form + .aside h3, .event-create-sign-form-usual,
    .event-create-sign-form-usual li {
        margin-bottom: 10px
    }

    .mr {
        margin-right: 8px
    }

    .options {
        padding-top: 10px;
        padding-left: 225px;
        width: 420px;
    }

    .icon-close-new:hover {
        -webkit-transition: .15s;
        -o-transition: .15s;
        transition: .15s;
        -webkit-transform: rotate(90deg);
        -o-transform: rotate(90deg);
        transform: rotate(90deg)
    }

    #event_template_form_items .layui-form-item {
        margin-bottom: 0px !important;
    }

    .icon-close-new {
        width: 18px;
        height: 18px;
        background-image: url(${ctx}/image/form/create_bg.32.png);
        background-position: -522px 0px;
        position: absolute;
        top: -6px;
        right: 2px;
        cursor: pointer
    }

    .create-event-label {
        padding: 5px;
        border-color: #e5e5e5;
        background: #f6f6f6;
        margin-top: 7px;
        border-width: 1px;
    }

    .icon-event-label-add {
        width: 18px;
        height: 18px;
        background-image: url(${ctx}/image/form/create_bg.32.png);
        background-position: 18px 0px;
        display: block
    }
</style>
<fieldset class="event-create-body border">
    <legend>信息收集内容</legend>
    <div id="edit_template_items" class="event-create-sign-form"
         style="min-height: 518px; min-width: 650px; float: left;">
        <div id="event_template_form_items" style="padding-top: 10px">
            <c:forEach var="field" items="${fields}" varStatus="status">
                <div class="layui-form-item ${deleteStatus == true ? 'deleteStatus' : ''}" id="efi_${status.index}" data-id="${field.id}">
                    <div class="layui-inline">
                        <div class="layui-input-inline" style="width: 75px !important; height: 38px!important;">
                            <input type="hidden" name="items[${status.index}].id" value="${field.id}"/>
                            <input type="hidden" name="items[${status.index}].type" value="${field.type}"/>
                            <input type="hidden" name="items[${status.index}].sort" value="${field.sort}"/>
                            <input type="hidden" name="items[${status.index}].category" value="${field.category}"/>
                            <c:choose>
                                <c:when test="${not empty field.required && field.required == 'true'}">
                                    <input type="checkbox" checked="checked" lay-skin="primary" name="items[${status.index}].required" title="必填" value="true"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" lay-skin="primary" name="items[${status.index}].required" title="必填" value="true"/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="layui-input-inline" style="width: 130px !important;">
                            <input type="text" class="layui-input" name="items[${status.index}].title" value="${field.title}" lay-verify="required"/>
                        </div>
                        <div class="layui-input-inline" style="width: 300px !important;">
                            <input type="text" class="layui-input" name="items[${status.index}].description" value="${field.description}" placeholder="提示信息写在这里！"/>
                        </div>
                        <c:if test="${deleteStatus == true}">
                            <div class="layui-input-inline" style="width: 50px !important;">
                                <a onclick="removeEventFormItem(this)" href="javascript:void(0);" style="line-height: 35px">
                                    <i class="iconfont icon-delete btn-icon" style="font-size: 21px"></i>
                                </a>
                            </div>
                        </c:if>
                        <div class="cl"></div>
                        <c:if test="${field.type == 'radio' || field.type == 'checkbox' || field.type == 'select'}">
                            <div class="options">
                                <div class="event-create-sign-select" id="efis_${status.index}">
                                    <c:forEach var="option" items="${field.subitems}" varStatus="ss">
                                        <div class="sub-item" data-id="${option.id}">
                                            <input type="hidden" name="items[${status.index}].subitems[${ss.index}].id" value="${option.id}"/>
                                            <input type="hidden" name="items[${status.index}].subitems[${ss.index}].sort" value="${ss.count}"/>
                                            <div class="layui-input-inline" style="width: 110px">
                                                <input type="text" class="layui-input" lay-verify="required" name="items[${status.index}].subitems[${ss.index}].name" value="${option.name}"/>
                                            </div>
                                            <c:if test="${deleteStatus == true}">
												<span name="event_form_item_ctrl" class="icon-close-new" onclick="javascript:removeEventFormItemValue(this);"></span>
                                            </c:if>
                                        </div>
                                    </c:forEach>
                                    <button type="button" class="btn create-event-label" onclick="javascript:addEventFormItemValue('${status.index}', this, false)">
                                        <span name="event_form_item_ctrl" class="icon-event-label-add"></span>
                                    </button>
                                    <p class="cl" style="margin-bottom: 0px !important; display: block !important;"></p>
                                </div>
                            </div>
                            <div class="cl"></div>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="aside" id="edit_template_tools" style="max-width: 205px; float: right;">
        <h3>常用栏位</h3>
        <div class="event-create-sign-form-usual clearfix">
            <ul class="mr">
                <li>
                    <button type="button" class="btn" onclick="javascript:addEventFormCommonItem(0);">
                        <span>姓名</span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn" onclick="javascript:addEventFormCommonItem(1);">
                        <span>性别</span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn" onclick="javascript:addEventFormCommonItem(2);">
                        <span>公司</span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn" onclick="javascript:addEventFormCommonItem(3);">
                        <span>职位</span>
                    </button>
                </li>
            </ul>
            <ul>
                <li>
                    <button type="button" class="btn" onclick="javascript:addEventFormCommonItem(4);">
                        <span>手机</span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn" onclick="javascript:addEventFormCommonItem(5);">
                        <span>微信</span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn" onclick="javascript:addEventFormCommonItem(6);">
                        <span>QQ</span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn" onclick="javascript:addEventFormCommonItem(7);">
                        <span>邮箱</span>
                    </button>
                </li>
            </ul>
        </div>
        <div class="event-create-sign-form-usual clearfix">
            <h3>自定义栏位</h3>
            <ul class="mr">
                <li>
                    <button type="button" class="btn" onclick="javascript:addEventFormEmptyItem(0);">
                        <span>单行文本框</span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn" onclick="javascript:addEventFormEmptyItem(1);">
                        <span>数字输入框</span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn" onclick="javascript:addEventFormEmptyItem(2);">
                        <span>日期选择框</span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn" onclick="javascript:addEventFormEmptyItem(6);">
                        <span>下拉选择框</span>
                    </button>
                </li>
            </ul>
            <ul>
                <li>
                    <button type="button" class="btn" onclick="javascript:addEventFormEmptyItem(3);">
                        <span>多行文本框</span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn" onclick="javascript:addEventFormEmptyItem(4);">
                        <span>单选按钮框</span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn" onclick="javascript:addEventFormEmptyItem(5);">
                        <span>多选按钮框</span>
                    </button>
                </li>
            </ul>
        </div>
    </div>
    <div class="cl"></div>
</fieldset>
<script src="${ctx}/script/form/jquery-1.12.4.js"></script>
<script src="${ctx}/script/form/jquery-ui.js"></script>
<script>
    var form = null;
    $(function () {

        layui.use(['form', 'laydate'], function () {
            form = layui.form(), laydate = layui.laydate;
        });

        // 拖拽排序
        $("#event_template_form_items").sortable({
        	items : ".deleteStatus",
            placeholder: 'sortable-placeholder',
            opacity: 0.5,
            forcePlaceholderSize: true
        });
        $("#event_template_form_items").disableSelection();
        
        $(document).tooltip({
        	items : "#event_template_form_items .deleteStatus",
        	content : function(){
        		return "拖动栏目排序";
        	},
        	position : {
        		my: "left center", 
        		at: "left center",
        		using : function(position, feedback ) {
        			if(feedback.target.element.find(".sub-item").length > 0){
        				position = {
   	       					left : 530,
   	       					top : position.top - feedback.target.element.find(".options").height() / 2 - 13 
   	        			}
        			} else {
	        			position = {
	       					left : 530,
	       					top : position.top - 10
	        			}
        			}
       	          $( this ).css( position );
       	        }
        	}
        });
    });


    // 常用栏位JSON
    var formCommonItems = [
        {"Type": "input", "Category": "FIELD_REALNAME", "Required": false, "Title": "姓名", "Subitems": [], "Description": null, "Value": null, "TypeTitle": "单行文本框"},
        {"Type": "radio", "Category": "FIELD_GENDER", "Required": false, "Title": "性别", "Subitems": ["男", "女"], "Description": null, "Value": null, "TypeTitle": "单选按钮框"},
        {"Type": "input", "Category": "FIELD_COMPANY", "Required": false, "Title": "公司", "Subitems": [], "Description": null, "Value": null, "TypeTitle": "单行文本框"},
        {"Type": "input", "Category": "FIELD_OFFICE", "Required": false, "Title": "职位", "Subitems": [], "Description": null, "Value": null, "TypeTitle": "单行文本框"},
        {"Type": "tel", "Category": "FIELD_MOBILE", "Required": false, "Title": "手机", "Subitems": [], "Description": null, "Value": null, "TypeTitle": "单行文本框"},
        {"Type": "input", "Category": "FIELD_WX", "Required": false, "Title": "微信", "Subitems": [], "Description": null, "Value": null, "TypeTitle": "单行文本框"},
        {"Type": "qq", "Category": "FIELD_QQ", "Required": false, "Title": "QQ", "Subitems": [], "Description": null, "Value": null, "TypeTitle": "单行文本框"},
        {"Type": "email", "Category": "FIELD_EMAIL", "Required": false, "Title": "邮箱", "Subitems": [], "Description": null, "Value": null, "TypeTitle": "单行文本框"}
    ];

    // 自定义栏位JSON
    var formEmptyItems = [
        {"Type": "input", "Category": "", "Required": false, "Title": "", "Subitems": [], "Description": null, "Value": null, "TypeTitle": "单行文本框"},
        {"Type": "number", "Category": "", "Required": false, "Title": "", "Subitems": [], "Description": null, "Value": null, "TypeTitle": "数字输入框"},
        {"Type": "date", "Category": "", "Required": false, "Title": "", "Subitems": [], "Description": null, "Value": null, "TypeTitle": "日期选择框"},
        {"Type": "textarea", "Category": "", "Required": false, "Title": "", "Subitems": [], "Description": null, "Value": null, "TypeTitle": "多行文本框"},
        {"Type": "radio", "Category": "", "Required": false, "Title": "", "Subitems": [], "Description": null, "Value": null, "TypeTitle": "单选按钮框"},
        {"Type": "checkbox", "Category": "", "Required": false, "Title": "", "Subitems": [], "Description": null, "Value": null, "TypeTitle": "多选按钮框"},
        {"Type": "select", "Category": "", "Required": false, "Title": "", "Subitems": [], "Description": null, "Value": null, "TypeTitle": "下拉选择框"}
    ];

    // 添加选项
    function addEventFormItemValue(index, that, flag) {
        var itemsHtml = renderEventFormItemValues(index, that, flag);
        var parent = $(that).closest(".event-create-sign-select");
        var subItem = $(parent).find(".sub-item:last");
        if (subItem.length == 0) {
            var addBtn = $(parent).find(".create-event-label");
            $(addBtn).before(itemsHtml);
        } else {
            $(subItem).after(itemsHtml);
        }
    }

    // 拼接选项模板
    function renderEventFormItemValues(i, that, addNew, tmpItem) {
        var itemsHtml = '';
        if (tmpItem != undefined && tmpItem.Subitems != null && tmpItem.Subitems.length > 0) {
            for (var j = 0; j < tmpItem.Subitems.length; j++) {
                itemsHtml += '<div class="sub-item">';
                itemsHtml += '<div class="layui-input-inline" style="width:110px">';
                itemsHtml += '<input type="hidden" name="items[' + i + '].subitems[' + j + '].sort" value="' + (j + 1) + '" />';
                itemsHtml += '<input type="text" class="layui-input" lay-verify="required" name="items[' + i + '].subitems[' + j + '].name" value="' + (tmpItem.Subitems[j] == null ? "" : tmpItem.Subitems[j]) + '"/>';
                itemsHtml += '</div>';
                itemsHtml += '<span name="event_form_item_ctrl" class="icon-close-new" onclick="javascript:removeEventFormItemValue(this);"></span>';
                itemsHtml += '</div>';
            }
        } else {
            j = $(that).closest(".event-create-sign-select").find(".sub-item").length;
            itemsHtml += '<div class="sub-item">';
            itemsHtml += '<input type="hidden" name="items[' + i + '].subitems[' + j + '].sort" />';
            itemsHtml += '<div class="layui-input-inline" style="width:110px">';
            itemsHtml += '<input type="text" class="layui-input" lay-verify="required" name="items[' + i + '].subitems[' + j + '].name" />';
            itemsHtml += '</div>';
            itemsHtml += '<span name="event_form_item_ctrl" class="icon-close-new" onclick="javascript:removeEventFormItemValue(this);"></span>';
            itemsHtml += '</div>';
        }

        if (addNew == true) {
            itemsHtml += '<button type="button" class="btn create-event-label" onclick="javascript:addEventFormItemValue(' + i + ', this, false)">' +
                '<span name="event_form_item_ctrl" class="icon-event-label-add"></span></button>';
            itemsHtml += '<p class="cl" style="margin-bottom:0px!important; display:block!important;"></p>';
        }
        return itemsHtml;
    }

    // 创建栏位模板
    function createTemplateFormItem(item) {
        if (item == null)
            return null;
        var result = {
            Type: item.Type,
            Category: item.Category,
            Required: false,
            Title: item.Title,
            Description: item.Description,
            TypeTitle: item.TypeTitle,
            Subitems: new Array()
        };
        if (item.Subitems != null && item.Subitems.length > 0) {
            for (var i = 0; i < item.Subitems.length; i++) {
                result.Subitems.push(item.Subitems[i]);
            }
        }
        return result;
    }

    // 检查表单字段格式
    function checkField() {
        var flag = true;
        var length = $("#event_template_form_items .layui-form-item").length;
        if (length == 0) {
            layer.msg("请添加要收集的信息字段", {
                icon: 2
            });
            flag = false;
            return false;
        }

        $("#event_template_form_items .layui-form-item").each(
            function (i, i_ele) {
                var type = $(i_ele).find("input[type=hidden][name$=type]")
                    .val();
                if (type == "radio" || type == "checkbox"
                    || type == "select") {
                    length = $(i_ele).find(".sub-item").length;
                    if (length == 0) {
                        layer.msg("请添加该字段的选项", {
                            icon: 2
                        });
                        flag = false;
                        return false;
                    }
                }
            });

        return flag;
    }

    // 重置下标
    function resetIndex() {
        $("#event_template_form_items .layui-form-item").each(function (i, i_ele) {
            // 重置栏位
            $(i_ele).find(".layui-inline").children(".layui-input-inline").each(function (j, j_ele) {
                $(j_ele).find("[name^=items]").each(function (k, k_ele) {
                    var name = $(k_ele).attr("name");
                    var start = name.substring(0, name.indexOf("[") + 1);
                    var end = name.substring(name.indexOf("]"), name.length);
                    var newName = start + i + end;
                    $(k_ele).attr("name", newName);
                    if (name.indexOf("sort") != -1) {
                        $(k_ele).val(i + 1);
                    }
                });
            });

            // 重置选项
            $(i_ele).find(".layui-inline .event-create-sign-select").children(".sub-item").each(function (j, j_ele) {
                $(j_ele).find("[name^=items]").each(function (k, k_ele) {
                    var name = $(k_ele).attr("name");
                    var start = name.substring(0, name.indexOf("[") + 1);
                    var end = name.substring(name.indexOf("]"), name.length);
                    var newName = start + i + end;
                    $(k_ele).attr("name", newName);
                    name = $(k_ele).attr("name");
                    start = name.substring(0, name.lastIndexOf("[") + 1);
                    end = name.substring(name.lastIndexOf("]"), name.length);
                    newName = start + j + end;
                    $(k_ele).attr("name", newName);
                    if (name.indexOf("sort") != -1) {
                        $(k_ele).val(j + 1);
                    }
                });
            });
        });
    }

    // 删除栏位
    function removeEventFormItem(that) {
        var item = (that).closest(".layui-inline").closest(".layui-form-item");
        var length = $(item).find(".sub-item").length;
        if (length > 0) {
            $(item).find(".sub-item").each(function (index, ele) {
                removeEventFormItemValue(ele);
            });
        }

        var fieldId = $(item).attr("data-id");

        function cb() {
            $(item).remove();
        }

        if (fieldId == "") {
            cb();
        } else {
            $.ajax({
                type: 'post',
                url: '${ctx}/gatherForm/form/deleteField.do',
                data: {
                    id: fieldId
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
    }

    // 删除选项
    function removeEventFormItemValue(that) {
        var item = $(that).closest(".sub-item");
        var optionId = $(item).attr("data-id");

        function cb() {
            $(item).remove();
        }

        if (optionId == "") {
            cb();
        } else {
            $.ajax({
                type: 'post',
                url: '${ctx}/gatherForm/form/deleteOption.do',
                data: {
                    id: optionId
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
    }

    // 添加常用栏位
    function addEventFormCommonItem(index) {
        if (formCommonItems != null && formCommonItems.length > index
            && index >= 0) {
            var commonItem = formCommonItems[index];
            if (commonItem != null) {
                renderEventFormTemplate(createTemplateFormItem(commonItem));
            }
        }
    }

    // 添加自定义栏位
    function addEventFormEmptyItem(index) {
        if (formEmptyItems != null && formEmptyItems.length > index
            && index >= 0) {
            var emptyItem = formEmptyItems[index];
            if (emptyItem != null) {
                renderEventFormTemplate(createTemplateFormItem(emptyItem));
            }
        }
    }

    // 拼接模板
    function renderEventFormTemplate(tmpItem) {
        var index = $("#event_template_form_items .layui-form-item").length;
        var itemsHtml = "";
        var title = tmpItem.Title == "" ? tmpItem.TypeTitle : tmpItem.Title;
        itemsHtml += '<div class="layui-form-item deleteStatus" id="efi_' + index + '" data-id="">';
        itemsHtml += '<div class="layui-inline">';
        itemsHtml += '<div class="layui-input-inline" style="width: 75px!important; height: 38px!important; ">';
        itemsHtml += '<input type="hidden" name="items[' + index + '].type" value="' + tmpItem.Type + '" />';
        itemsHtml += '<input type="hidden" name="items[' + index + '].sort" value="' + tmpItem.Sort + '" />';
        itemsHtml += '<input type="hidden" name="items[' + index + '].category" value="' + tmpItem.Category + '" />';
        itemsHtml += '<input type="checkbox" lay-skin="primary" name="items[' + index + '].required" title="必填" value="true" />';
        itemsHtml += '</div>';
        itemsHtml += '<div class="layui-input-inline" style="width: 130px!important;">';
        itemsHtml += '<input type="text" class="layui-input" title="' + title + '" placeholder="' + title + '" name="items[' + index + '].title" value="' + title + '" lay-verify="required" />';
        itemsHtml += '</div>';
        itemsHtml += '<div class="layui-input-inline" style="width: 300px!important;">';
        itemsHtml += '<input type="text" name="items[' + index + '].description" class="layui-input" value="' + (tmpItem.Description == null ? "" : tmpItem.Description) + '" placeholder="提示信息写在这里！"/>';
        itemsHtml += '</div>';
        itemsHtml += '<div class="layui-input-inline" style="width: 50px!important;">';
        itemsHtml += '<a onclick="removeEventFormItem(this)" href="javascript:void(0);" style="line-height: 35px"><i class="iconfont icon-delete btn-icon" style="font-size: 21px"></i></a>';
        itemsHtml += '</div>';
        itemsHtml += '<div class="cl"></div>';

        if (tmpItem.Type == "radio" || tmpItem.Type == "checkbox" || tmpItem.Type == "select") {
            itemsHtml += '<div class="options">';
            itemsHtml += '<div class="event-create-sign-select" id="efis_' + index + '">';
            itemsHtml += renderEventFormItemValues(index, this, true, tmpItem);
            itemsHtml += '</div>';
            itemsHtml += '</div>';
            itemsHtml += '<div class="cl"></div>';
        }
        itemsHtml += '</div>';
        itemsHtml += '</div>';

        $("#event_template_form_items").append(itemsHtml);
        form.render('checkbox');
    }
</script>