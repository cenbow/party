/**
 * 表格操作 2016-12-29 Juliana
 */
(function (W, doc) {
    "use strict";
    W.tbOption = {
        _getStyle: function (dom, attr) {
            return (dom.currentStyle ? dom.currentStyle[attr] : getComputedStyle(dom, false)[attr]);
        },
        _myAddEvent: function (obj, ev, fn) {
            if (obj.attachEvent) {
                obj.attachEvent("on" + ev, fn);
            }
            else {
                obj.addEventListener(ev, fn, false);
            }
        },
        _appendHTML: function (o, html) {
            var divTemp = document.createElement("div"), nodes = null
                , fragment = document.createDocumentFragment();
            divTemp.innerHTML = html;
            nodes = divTemp.childNodes;
            for (var i = 0, length = nodes.length; i < length; i += 1) {
                fragment.appendChild(nodes[i].cloneNode(true));
            }
            o.appendChild(fragment);
            nodes = null;
            fragment = null;
        },
        init: function () {
            var that = this;
            that._appendHTML(doc.querySelector('body'), '<div class="out-fix-opts" style="display: none;"></div>')
            doc.querySelectorAll('.tb-opts').forEach(function (tb) {
                var opts = tb.querySelectorAll('.comm-opts a'),
                    cmOpt = tb.querySelector('.comm-opts'),
                    optsW = 0,//所有选项加起来的长度
                    tbW = tb.clientWidth - parseInt(that._getStyle(tb, 'paddingLeft')) - parseInt(that._getStyle(tb, 'paddingRight')),
                    showStr = "",//显示的html字符串
                    hideStr = "";//隐藏的html字符串
                opts.forEach(function (opt) {
                    optsW += opt.clientWidth;
                });
                if (optsW > tbW) {
                    //组装
                    var realW = 50;//所有选项加起来的实际长度
                    hideStr = '<div class="opt-more" href="#">' +
                        '<a class="more-btn" href="#"><span>更多</span><i class="more iconfont icon-unfold"></i></a>' +
                        '<ul class="options-content">';
                    opts.forEach(function (opt) {
                        realW += opt.clientWidth;
                        if (realW < tbW) {
                            showStr += opt.outerHTML;
                        } else {
                            hideStr += '<li>' + opt.outerHTML;
                            +'</li>';
                        }
                    });
                    hideStr += '</ul>' +
                        '</div>';
                    if (showStr !== '') {
                        cmOpt.innerHTML = showStr;
                    } else {
                        var _parentElement = cmOpt.parentNode;
                        if (_parentElement) {
                            _parentElement.removeChild(cmOpt);
                        }
                    }
                    if (showStr == '') {
                        hideStr = hideStr.replace('opt-more', 'opt-more before-none');
                        hideStr = hideStr.replace("更多", "操作");
                    }
                    tb.insertAdjacentHTML('beforeend', hideStr);

                    that._myAddEvent(tb.querySelector('.more-btn'), 'mouseover', function (e) {
                        var hideOpts = this.parentNode.querySelector('.options-content');
                        var ofixopts = doc.querySelector('.out-fix-opts'), moreH = this.scrollHeight, moreW = this.scrollWidth;
                        ofixopts.innerHTML = hideOpts.outerHTML;
                        var rect = this.getBoundingClientRect();
                        ofixopts.style.left = rect.left + 'px';
                        ofixopts.style.top = (rect.bottom + window.scrollY) + 'px';
                        ofixopts.style.display = 'block';
                    });
                    that._myAddEvent(tb.querySelector('.more-btn'), 'mouseout', function (e) {
                        var ofixopts = doc.querySelector('.out-fix-opts');
                        ofixopts.style.display = 'none';
                    });
                    that._myAddEvent(doc.querySelector('.out-fix-opts'), 'mouseover', function (e) {
                        var ofixopts = doc.querySelector('.out-fix-opts');
                        ofixopts.style.display = 'block';
                    });
                    that._myAddEvent(doc.querySelector('.out-fix-opts'), 'mouseout', function (e) {
                        var ofixopts = doc.querySelector('.out-fix-opts');
                        ofixopts.style.display = 'none';
                    });
                }
            });
        }
    }
    tbOption.init();
}(window, document));
(function (W, doc) {
    "use strict";
    W.fixTable = {
        _getStyle: function (dom, attr) {
            return (dom.currentStyle ? dom.currentStyle[attr] : getComputedStyle(dom, false)[attr]);
        },
        init: function () {
            var that = this;
            var fixTableCtns = doc.getElementsByClassName('jfix-table-content');
            if (!fixTableCtns || fixTableCtns.length == 0)
                return;

           for(var i=0;i<fixTableCtns.length;i++){
               var fixTableCtn = fixTableCtns[i];
                var leftTable = fixTableCtn.querySelector('.jleft-fix-table'),
                    rightTable = fixTableCtn.querySelector('.jright-fix-table');
               //对比左右高度修改tr高度
                var leftTableTr = leftTable.querySelectorAll('tbody tr'),rightTableTr = rightTable.querySelectorAll('tbody tr');
                rightTable.style.marginLeft='-1px';
                leftTableTr.forEach(function (tr,index) {
                    var ltrH = tr.clientHeight,rtrH = rightTableTr[index].clientHeight;
                    if(ltrH>rtrH){
                        rightTableTr[index].style.height=ltrH+'px';
                    }else{
                        tr.style.height=rtrH+'px';
                    }
                });
            }
        }
    }
    fixTable.init();
}(window, document));