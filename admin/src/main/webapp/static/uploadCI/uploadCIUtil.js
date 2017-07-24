/**
 * 万象优图上传文件相关
 */

function UploadFile(downloadHost, getSignUrl) {
    this.downloadHost = downloadHost
        || 'http://txzapp-10052192.image.myqcloud.com/';
    this.getSignUrl = getSignUrl || '/member/piccloud/getSign.do';
    this.maxSize= 0.5 * 1024 * 1024;// 图片大小限制
    this.maxWidth= 900;// 图片最大宽度限制
    this.quality = 80;//图片清晰度
};
UploadFile.prototype = {
    // 图片压缩入口
    initResizeBiz: function (oFile, cb) {
        var self = this;
        var reader = new FileReader(), formData = new FormData();
        reader.onload = function (e) {
            var data = e.target.result;
            // 加载图片获取图片真实宽度和高度
            var image = new Image();
            image.onload = function () {
                var width = image.width;
                var height = image.height;
                if (oFile.type !=='image/gif' && (oFile.size > self.maxSize || width > self.maxWidth)) {
                    var maxWidth = self.maxWidth, maxHeight = parseInt(self.maxWidth / width * height);
                    var ratio = (maxWidth / maxHeight).toFixed(2);
                    oFile['width'] = maxWidth;
                    oFile['height'] = maxHeight;
                    oFile['ratio'] = ratio;
                    console.log('图片大小' + oFile.size + ',正在进行压缩');
                    var base64Img = data;
                    // --执行resize。
                    var _ir = ImageResizer({
                        resizeMode: "auto",
                        dataSource: base64Img,
                        dataSourceType: "base64",
                        maxWidth: maxWidth, // 允许的最大宽度
                        maxHeight: maxHeight, // 允许的最大高度。
                        quality: self.quality, // 压缩清晰度
                        onTmpImgGenerate: function (img) {
                        },
                        success: function (resizeImgBase64, canvas) {
                            console.log('压缩成功');
                            var blob = convertCanvasToBlob("image/jpeg", canvas);
                            formData.append('FileContent', blob, oFile['name']);
                            typeof cb === 'function' && cb(formData);
                        }
                    });
                } else {
                    var ratio = (width / height).toFixed(2);
                    oFile['width'] = width;
                    oFile['height'] = height;
                    oFile['ratio'] = ratio;
                    formData.append('FileContent', oFile);
                    typeof cb === 'function' && cb(formData);
                }
            };
            image.src = data;
        };
        reader.readAsDataURL(oFile);
    },
    // 万象优图签名接口
    getSign: function (type, fileid, signCallback) {
        type = type || 'upload';
        function callBack(ret, err) {
            if (ret.success) {
                if (util.isValid(signCallback)) {
                    signCallback(ret, err);
                }
            }
        }

        var params = {
            type: type
        }
        if (type === 'del') {
            params['fileid'] = fileid;
        }

        var url = this.getSignUrl;
        ajaxRequest(url, 'get', params, callBack);
    },

    // 删除图片
    delQcloudPhoto: function (fileid, delCallback) {
        var self = this;
        console.log('正在删除文件' + fileid);
        function callback(ret, err) {
            if (ret.success) {
                console.log('获取删除文件签名成功');
                $.ajax({
                    url: ret.data.url + '/del?sign=' + encodeURIComponent(ret.data.sign),
                    type: 'POST',
                    cache: false,
                    dataType: 'json'
                }).done(function (ret) {
                    console.log(JSON.stringify(ret))
                    if (util.isValid(ret) && ret.code == 0) {
                        delCallback(ret)
                    }
                }).fail(function (err) {
                    if (err.responseJSON.code == -197) {
                        console.warn('该图片已被删除');
                        delCallback(err);
                    } else {
                        console.log(JSON.stringify(err))
                    }
                });
            } else {
                console.warn('获取删除文件签名失败');
            }
        }

        self.getSign('del', fileid, callback);
    },
    //上传单个图片方法
    uploadSinglePhoto: function (file, uploadBack) {
        var self = this;
        console.log('正在上传文件' + JSON.stringify(arguments));
        var fileid = new Date().getTime();

        function callback(ret, err) {
            if (ret.success) {
                ret = ret.data;
                self.initResizeBiz(file, function (formData) {
                    $.ajax({
                        url: ret.url + '/' + fileid + '?sign=' + encodeURIComponent(ret.sign),
                        type: 'POST',
                        cache: false,
                        data: formData,
                        dataType: 'json',
                        processData: false,
                        contentType: false
                    }).done(function (ret) {
                        if (util.isValid(ret) && ret.code == 0) {
                            console.log(JSON.stringify(ret))
                            uploadBack(ret);
                        }
                    }).fail(function (err) {
                        console.log(JSON.stringify(err))
                    });
                })
            }
        }

        self.getSign('upload', fileid, callback);
    },
    // 上传图片 后续请切勿使用该方法 该方法今后将被弃用
    uploadQcloudPhoto: function (fileQuery, uploadCallback, repeatIsDel) {
        var self = this;
        console.log('正在上传文件' + JSON.stringify(arguments));
        var fileid = $(fileQuery)[0].files[0].name;

        function callback(ret, err) {
            // fileid = fileid.substring(fileid.lastIndexOf("."),
            // fileid.length);
//			var date = new Date();
            // fileid = date.getFullYear() + "" + (date.getMonth() + 1) + ""
            // + date.getDate() + "" + date.getHours() + ""
            // + date.getMinutes() + "" + date.getSeconds() + fileid;
            var formData = new FormData();
            formData.append('FileContent', $(fileQuery)[0].files[0]);
            $.ajax({
                url: ret.url + '/' + fileid + '?sign=' + encodeURIComponent(ret.sign),
                type: 'POST',
                cache: false,
                data: formData,
                dataType: 'json',
                processData: false,
                contentType: false
            }).done(function (ret) {
                if (util.isValid(ret) && ret.code == 0) {
                    console.log(JSON.stringify(ret))
                    uploadCallback(ret)
                }
            }).fail(
                function (err) {
                    console.log(JSON.stringify(err))
                    if (err) {
                        console.log('上传失败' + ":" + JSON.stringify(err));
                        switch (JSON.parse(err.responseText).code) {
                            case -1886:
                                // 文件已存在
                                console.log('文件已存在');
                                // repeatIsDel = util.isValid(repeatIsDel) ?
                                // repeatIsDel: true;
                                repeatIsDel = true;
                                if (repeatIsDel) {
                                    function delCallback(ret, err) {
                                        if (ret)
                                            self.uploadQcloudPhoto(fileid,
                                                fileurl, uploadCallback);
                                    }

                                    self.delQcloudPhoto(fileid, delCallback);
                                } else {
                                    var ret = {};
                                    var data = {}
                                    data.download_url = self.downloadHost
                                        + fileid;
                                    ret.data = data;
                                    uploadCallback(ret);
                                }
                                break;
                        }
                    }
                })
        }

        self.getSign('upload', fileid, callback);
    }
};
function ajaxRequest(url, method, params, callBack) {
    var that = this;
    $.ajax({
        type: method,
        url: url,
        data: params,
        dataType: 'json',
        context: $('body'),
        success: function (data) {
            callBack(data);
        },
        error: function (xhr, type) {
            console.err('err', 'ajax错误', xhr, type);
        }
    })
}
