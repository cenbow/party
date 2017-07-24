<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/page/include/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>上传万象优图</title>
    <link href="${ctxStatic}/layui-v1.0.7/css/layui.css" rel="stylesheet"/>
    <link href="${ctxStatic}/uploadCI/upload.css" rel="stylesheet"/>
    <style>
        .ci-img-list {
            width: 50%;
        }
    </style>
</head>
<body>
<div class="ci-upload-content">
    <input type="hidden" id="succFilesUrl"/>
    <div class="ci-selimg-content" style="margin-top: 20px;">
        <div id="selectBtn" class="layui-box layui-upload-button">
            <input id="upload_input" type="file" name="file" class="layui-upload-file" multiple> <span
                class="layui-upload-icon"><i class="layui-icon">&#xe61f;</i> 选择图片</span>
        </div>
        <div class="layui-box layui-upload-button" style="display: none;" id="upload_btn">
            <span class="layui-upload-icon"><i class="layui-icon">&#x1005;</i> 上传图片</span>
        </div>
    </div>
    <div id="upload_info"></div>
    <div class="ci-img-content">
        <div class="ci-img-list" id="">
            <fieldset class="layui-elem-field layui-field-title">
                <legend>待上传</legend>
                <div class="layui-field-box" id="file_drag_area" style="padding-right: 20px;">
                    <ul class="ci-priview-content" id="preview"></ul>
                </div>
            </fieldset>
            <!-- <li class="img-item" style="background-image: url(http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg);"><span class="file-name">图图</span><span class="status error">上传成功</span><i class="layui-icon del-icon">&#x1007;</i></li> -->
        </div>
        <div class="ci-split"></div>
        <div class="ci-img-list" id="">
            <fieldset class="layui-elem-field layui-field-title">
                <legend>已上传</legend>
                <div class="layui-field-box" style="padding-left: 10px;">
                    <ul id="success_place">

                    </ul>
                </div>
            </fieldset>
        </div>
    </div>
</div>
<!-- cdn -->
<script type="text/javascript" charset="utf-8" src="//apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxStatic}/layui-v1.0.7/layui.js"></script>
<script src="${ctx}/script/common/util.js"></script>
<script src="${ctxStatic}/uploadCI/resize.js"></script>
<script src="${ctxStatic}/uploadCI/uploadCIUtil.js"></script>
<script src="${ctxStatic}/uploadCI/uploadCI.js"></script>

<script type="text/javascript">
    if (!window.jQuery) {
        var s = document.createElement('script');
        s.src = '${ctxStatic}/jquery/jquery-2.1.4.min.js';
        document.body.appendChild(s);
    }
</script>
<script>
    var uploadFile = new UploadFile('', '${ctx}/piccloud/getSign.do'), uploadUrl, uploadSign, succFiles = new Array();
    uploadFile.getSign('upload', null, function (ret) {
        uploadUrl = ret.data.url;
        uploadSign = ret.data.sign;
        initUploader();
    });
    $('#upload_input').click(function (e) {
        this.value = '';
    })
    function initUploader() {
        var params = {
            fileInput: $("#upload_input").get(0),
            dragDrop: $("#file_drag_area").get(0),
            upButton: $("#upload_btn").get(0),
            min: '${null==min?0:min}',
            max: '${null==max?1000:max}',
            filter: function (files) {
                var arrFiles = [];
                for (var i = 0, file; file = files[i]; i++) {
                    if (file.type.indexOf("image") == 0) {
                        var fileid = new Date().getTime() + i;
                        file.fileid = fileid;
                        file.uploadUrl = uploadUrl + '/' + fileid
                            + '?sign='
                            + encodeURIComponent(uploadSign),
                            arrFiles.push(file);
                    } else {
                        alert('文件"' + file.name + '"不是图片。');
                    }
                }
                return arrFiles;
            },
            onSelect: function (files) {
                var html = '', i = 0;
                var funAppendImage = function () {
                    file = files[i];
                    if (file) {
                        var reader = new FileReader()
                        reader.onload = function (e) {
                            html = html
                                + '<li id="upload_img_'
                                + file.index
                                + '" data-index="'
                                + file.index
                                + '" class="img-item" title="'
                                + file.name
                                + '" style="background-image: url('
                                + e.target.result
                                + ');">'
                                + '<i class="layui-icon del-icon" data-index="' + file.index + '">&#x1007;</i><span class="file-name">'
                                + file.name
                                + '</span><span class="status" style="display:none;">上传成功</span></li>'
                            i++;
                            funAppendImage();
                        }
                        reader.readAsDataURL(file);
                    } else {
                        $("#preview").html(html);
                        if (html) {
                            //删除方法
                            $(".del-icon")
                                .click(
                                    function () {
                                        uploadCI
                                            .funDeleteFile(files[parseInt($(
                                                this)
                                                .attr(
                                                    "data-index"))]);
                                        return false;
                                    });
                            //提交按钮显示
                            $("#upload_btn").show();
                        } else {
                            //提交按钮隐藏
                            $("#upload_btn").hide();
                        }
                    }
                };
                funAppendImage();
            },
            onDelete: function (file) {
                $("#upload_img_" + file.index).fadeOut();
                setTimeout(function () {
                    $("#upload_img_" + file.index).remove();
                    if (!uploadCI.fileFilter.length) {
                        $("#upload_btn").hide();
                    }
                }, 200);
            },
            onDragOver: function () {
                var target = this;
                if (target.id !== 'file_drag_area') {
                    target = $(target).closest('#file_drag_area');
                }
                $(target).addClass("upload_drag_hover");
            },
            onDragLeave: function () {
                var target = this;
                if (target.id !== 'file_drag_area') {
                    target = $(target).closest('#file_drag_area');
                }
                $(target).removeClass("upload_drag_hover");
            },
            onProgress: function (file, loaded, total) {
                var status = $("#upload_img_" + file.index + " .status"), percent = (loaded
                    / total * 100).toFixed(2)
                    + '%';
                status.addClass('success').removeClass('error').show()
                    .text(percent);
            },
            onSuccess: function (file, response) {
                var html = '<li id="success_img_'
                    + file.index
                    + '" data-index="'
                    + file.index
                    + '" class="img-item" title="'
                    + file.name
                    + '" download-url="'
                    + response.data.download_url
                    + '" style="background-image: url('
                    + response.data.download_url
                    + ');">'
                    + '<i class="layui-icon del-icon" data-index="' + file.index + '" style="display:none;">&#x1007;</i><span class="file-name">'
                    + file.name
                    + '</span><span class="status" style="">上传成功</span></li>'

                $('#success_place').append(html);
                file.downloadUrl = response.data.download_url;
                file.htmlStr = '<img data-width="' + file.width + '" data-height="' + file.height + '" data-ratio="' + file.ratio + '" src="' + file.downloadUrl + '" />';
                succFiles.push(file);
                var filesUrl = $('#succFilesUrl').val();
                $('#succFilesUrl').val(
                    $('#succFilesUrl').val()
                    + (util.isValid(filesUrl) ? '|' : '')
                    + response.data.download_url);
                $("#upload_btn").hide();
            },
            onFailure: function (file, response) {
                switch (response.code) {
                    case -1886:
                        response = {
                            "code": -1886,
                            "message": "SUCCESS",
                            "data": {
                                "download_url": uploadFile.downloadHost
                                + file.fileid,
                                "fileid": file.fileid
                            }
                        }
                        uploadCI.onSuccess(file, response);
                        uploadCI.funDeleteFile(file);
                        if (!uploadCI.fileFilter.length) {
                            //全部完毕
                            uploadCI.onComplete();
                        }
                        break;
                    default:
                        var status = $("#upload_img_" + file.index + " .status");
                        status.addClass('error').removeClass('success').show()
                            .text('上传失败');
                        break;
                }

            },
            onComplete: function () {
                //提交按钮隐藏
                $("#upload_btn").hide();
                //file控件value置空
                $("#file_image").val("");
                $("#upload_info").html("<p>当前图片全部上传完毕，可继续添加上传。</p>");
            },
            validateUpload: function () {
                if (this.fileFilter.length + succFiles.length > this.max) {
                    top.layer.msg('最多只允许上传' + this.max + '张图片');
                    return false;
                }
                if (this.fileFilter.length + succFiles.length < this.min) {
                    top.layer.msg('至少要上传' + this.max + '张图片');
                    return false;
                }
                return true;
            }
        };
        uploadCI = $.extend(uploadCI, params);
        uploadCI.init();
    }
    function doSubmit() {
        if (uploadCI.fileFilter.length > 0) {
            var a = confirm("您有未上传的图片，确认关闭么？");
            if (a == true) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
    function closeUpload() {
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        top.layer.close(index);
    }
    function getSucceFiles() {
        return succFiles;
    }
</script>
</body>
</html>