/**
 * 通用 2017-02-23 Juliana
 */


(function (W, U) {
        "use strict";
        W.serverUrl = '/web';
        W.txz = {
            CONSTANT: {
                serverUrl: '/web',
                loginUrl: '/web/pLogin.do'
            },
            saveStatus: true,//保存状态
            addToTopScroll: function () {
                var n = $('#top_back');
                $(window).scroll(function () {
                    $(window).scrollTop() > 700 ? n.css('display', 'block') : n.css('display', 'none');
                })

                n.click(function () {
                    return $("html,body").animate({scrollTop: 0});
                })
            },
            ajaxRequest: function (opts) {
                var that = this;
                opts = $.extend({
                    method: 'post',
                    data: {},
                    saveCache: false, //当登录失效时是否保存至缓存
                    cacheKey: ''//缓存key
                }, opts);
                $.ajax({
                    type: opts.method,
                    url: opts.url,
                    data: opts.params,
                    dataType: 'json',
                    context: $('body'),
                    success: function (data) {
                        if (util.getStorage(opts.cacheKey + '_f_cache')) {
                            util.removeStorage(opts.cacheKey + '_f_cache');
                        }
                        typeof opts.callBack === 'function' && opts.callBack(data);
                    },
                    error: function (xhr, type) {
                        console.warn('err', 'ajax错误', xhr, type);
                        //用户登录状态失效
                        if (xhr.status == 401) {
                            var msg = '您的登录状态已失效，即将跳转登录页面';
                            if (!txz.saveStatus && opts.saveCache) {//缓存data
                                util.setStorage(opts.cacheKey + '_f_cache', opts.params);
                                msg += ',当前表单信息已保存，请登录后再次进入页面查看。';
                                txz.saveStatus = true;
                            }
                            layui.use('layer', function () {
                                var layer = layui.layer;
                                layer.open({
                                    content: msg
                                    , btn: ['确定']
                                    , yes: function (index, layero) {
                                        location.href = that.CONSTANT.loginUrl;
                                    }
                                });
                            });
                        }
                    }
                })
            },
            loadFCache: function (fCacheKey, formO, layerForm) {
                if (util.getStorage(fCacheKey + '_f_cache')) {
                    layui.use('layer', function () {
                        var layer = layui.layer;
                        layer.open({
                            content: '您有未保存的草稿，是否加载？'
                            , btn: ['加载', '下次提醒', '不再提醒']
                            , yes: function (index, layero) {
                                var cacheData = util.getStorage(fCacheKey + '_f_cache');
                                // util.removeStorage(fCacheKey + '_f_cache');
                                typeof loadCacheData === 'function' && loadCacheData(cacheData);
                                layer.close(layer.index);
                            }, btn2: function (index, layero) {
                                layer.close(layer.index);
                            }, btn3: function (index, layero) {
                                util.removeStorage(fCacheKey + '_f_cache');
                                layer.close(layer.index);
                            }
                        });
                    });
                }
            }
        }
    }
    (window, undefined)
)
;