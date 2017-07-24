/**
 * 列表公用的脚本
 */

/**
 * 下载二维码
 */
function download(ctx, path) {
	window.location.href = ctx + "/fileupload/download.do?filePath=" + path;
}

/**
 * 查询表单提交
 * 
 * @param myForm
 */
function submitFunction(myForm) {
	$(myForm).find("[name=timeType]").val("");
	$(myForm).submit();
}

/**
 * 查询表单重置
 * 
 * @param myForm
 */
function resetFunction(myForm) {
	$(myForm).find("input[type=text]").val("");
	$(myForm).find("select").find("option:eq(0)").attr("selected", true);
	$(myForm).find(".check-btn-inner").find("a").removeClass("active");
	$(myForm).find(".check-btn-inner").find("a:eq(0)").addClass("active")
	$(myForm).find(".check-btn-inner").find("[type=hidden]").val("");
}

/**
 * 二维码弹窗显示/隐藏
 * 
 * @param container
 * @param obj
 */
function qrCodeDialog(container, clazz, obj, dialogName, type) {
	$(container).delegate(obj, 'click', function(e) {
		var $target = $(e.target);

		if ($target.hasClass(clazz)) {
			if(type == undefined){
				var dialog = $target.closest(obj).siblings(dialogName);
				$(dialog).fadeIn();
			} else if(type == "2"){
				var dialog = $target.closest(container).find(dialogName);
				$(dialog).fadeIn();
			} else if(type == "3"){
				var dialog = $target.closest(obj).find(dialogName);
				$(dialog).fadeIn();
			}
		}
	});

	$(container).delegate(dialogName + '.close-icon', 'click', function(e) {
		$(this).closest('.f-def-dialog').fadeOut();
	});
}

/**
 * 例如“发布时间”块
 * 
 * @param obj
 * @param value
 * @param myForm
 */
function setTimeType(obj, value, myForm) {
	$(obj).parent(".check-btn-inner").find("a").removeClass("active");
	$(obj).addClass("active");
	$(obj).parent(".check-btn-inner").find("[type=hidden]").val(value);
	$(myForm).find("[name=createStart]").val("");
	$(myForm).find("[name=createEnd]").val("");
	$(myForm).submit();
}

function showActive(startDate, endDate, container){
	if(startDate != "" || endDate != ""){
		$(container).find("a").removeClass("active");
	}
}

/**
 * 加载分页
 * 
 * @param container
 *            分页容器
 * @param page
 *            分页对象
 */
function loadPage(container, totalPage, currPage, myForm, callback) {
	layui.use([ 'form', 'laydate', 'laypage' ], function() {
		var form = layui.form(), laydate = layui.laydate, laypage = layui.laypage;
		var $ = layui.jquery; // 重点处
		laypage({
			cont : container,
			pages : totalPage,
			curr : function() {
				return currPage;
			}(),
			skip : true,
			skin : '#ea5952',
			jump : function(obj, first) {
				if (!first) {
					$(myForm).find("#pageNo").val(obj.curr);
					if(callback == undefined){
						$(myForm).submit();
					}else{
						typeof callback == 'function' && callback.apply(this);
					}
				}
			}
		});
	});
}

/**
 * 查看弹窗
 * 
 * @param title
 *            标题
 * @param url
 *            链接
 * @param width
 *            宽
 * @param height
 *            高
 * @param target
 *            对象
 */
function openDialogShow(title, url, width, height, target) {
	layer.open({
		type : 2,
		area : [ width, height ],
		title : title,
		maxmin : false, // 开启最大化最小化按钮
		content : url,
		btn : [ '关闭' ],
		yes : function(index, layero) {
			setTimeout(function() {
				top.layer.close(index);
			}, 100);// 延时0.1秒，对应360 7.1版本bug
		}
	});
}
function openDialogNoButton(title, url, width, height, target) {
    layer.open({
        type : 2,
        area : [ width, height ],
        title : title,
        maxmin : false, // 开启最大化最小化按钮
        content : url
    });
}
/**
 * 删除
 * 
 * @param content
 *            弹窗内容
 * @param url
 *            请求
 * @param title
 *            提示语
 */
function deleteObj(content, url, title, myForm) {
	layer.confirm(content, {
		icon : 3,
		title : '系统提示'
	}, function(index) {
		$.post(url, {}, function(data) {
			if (data.success == true) {
				layer.msg("删除成功", {
					icon : 1,
					time: 1000
				}, function(index) {
					if (myForm == undefined) {
						location.reload();
					} else {
						submitFunction(myForm);
					}
				});
			} else {
				if (data.description != null) {
					layer.msg(title, {
						icon : 6
					});
				} else {
					layer.msg("删除失败", {
						icon : 6
					});
				}
			}
		});
	});
}

$(function() {
	layui.use(['form'], function () {
        var form = layui.form();
        form.render('select');
        form.render('checkbox');
        form.render('radio');
	});
});