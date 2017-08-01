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
        init: function (warp) {
            var that = this;
            that._appendHTML(doc.querySelector('body'), '<div class="out-fix-opts" style="display: none;"></div>');
            warp = warp || doc;
            var tbOpts = warp.querySelectorAll('.tb-opts');
            for (var i = 0; i < tbOpts.length; i++) {
                var tb = tbOpts[i];
                var opts = tb.querySelectorAll('.comm-opts a'),
                    cmOpt = tb.querySelector('.comm-opts'),
                    optsW = 0,//所有选项加起来的长度
                    tbW = tb.clientWidth - parseInt(that._getStyle(tb, 'paddingLeft')) - parseInt(that._getStyle(tb, 'paddingRight')),
                    showStr = "",//显示的html字符串
                    hideStr = "";//隐藏的html字符串

                for (var j = 0; j < opts.length; j++) {
                    var opt = opts[j];
                    optsW += opt.clientWidth;
                }
                if (optsW > tbW) {
                    //组装
                    var realW = 50;//所有选项加起来的实际长度
                    hideStr = '<div class="opt-more" href="#">' +
                        '<a class="more-btn" href="#"><span>更多</span><i class="more iconfont icon-unfold"></i></a>' +
                        '<ul class="options-content">';
                    for (var j = 0; j < opts.length; j++) {
                        var opt = opts[j];
                        realW += opt.clientWidth;
                        if (realW < tbW) {
                            showStr += opt.outerHTML;
                        } else {
                            hideStr += '<li>' + opt.outerHTML;
                            +'</li>';
                        }
                    }
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
                        ofixopts.style.left = (rect.left - 50) + 'px';
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
            }
        }
    }
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
            if (!fixTableCtns || fixTableCtns.length == 0) {
                tbOption.init();
                return;
            }

            for (var i = 0; i < fixTableCtns.length; i++) {
                var fixTableCtn = fixTableCtns[i];
                var leftTable = fixTableCtn.querySelector('.jleft-fix-table'),
                    leftCols = leftTable.querySelectorAll('colgroup col'),
                    rightTable = fixTableCtn.querySelector('.jright-fix-table');
                //修改左边table的宽度
                var leftW = 0;
                for (var i = 0; i < leftCols.length; i++) {
                    var col = leftCols[i];
                    leftW += parseInt(col.getAttribute("width").replace('px', ''));
                }

                leftTable.style.width = leftW + 'px';
                //修改右边table的宽度
                rightTable.style.width = (fixTableCtn.clientWidth - leftTable.clientWidth) + 'px';
                tbOption.init(fixTableCtn);
                //对比左右高度修改tr高度
                var leftTableTr = leftTable.querySelectorAll('tbody tr'), rightTableTr = rightTable.querySelectorAll('tbody tr');
                rightTable.style.marginLeft = '-1px';
                for (var index = 0; index < leftTableTr.length; index++) {
                    var tr = leftTableTr[index];
                    var ltrH = tr.clientHeight, rtrH = rightTableTr[index].clientHeight;
                    if (ltrH > rtrH) {
                        rightTableTr[index].style.height = ltrH + 'px';
                    } else {
                        tr.style.height = rtrH + 'px';
                    }
                }

            }

        }
    }
    fixTable.init();
}(window, document));
(function (W, doc) {
    "use strict";
    W.extraTable = {
        _getStyle: function (dom, attr) {
            return (dom.currentStyle ? dom.currentStyle[attr] : getComputedStyle(dom, false)[attr]);
        },
        init: function () {
            var that = this;
            var extraTables = doc.querySelectorAll('.ul-extra-table');
            if (!extraTables || extraTables.length == 0)
                return;
            for (var i = 0; i < extraTables.length; i++) {
                var extraTable = extraTables[i], header = extraTable.querySelector('.header'), headerLis = header.querySelectorAll('li');
                var rows = extraTable.querySelectorAll('.info .content');
                if (!header)
                    return;
                for (var j = 0; j < rows.length; j++) {
                    var row = rows[j];
                    var cells = row.querySelectorAll('li');
                    for (var m = 0; m < cells.length; m++) {
                        var cell = cells[m];
                        cell.style.width = that._getStyle(headerLis[m], 'width');
                    }
                }
            }
        }
    }
    extraTable.init();
}(window, document));
(function (W, doc) {
    "use strict";
    W.tableSel = {
        _getStyle: function (dom, attr) {
            return (dom.currentStyle ? dom.currentStyle[attr] : getComputedStyle(dom, false)[attr]);
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
        _hasClass: function (elem, cls) {
            if (!elem)
                return false;
            cls = cls || '';
            if (cls.replace(/\s/g, '').length == 0) return false; //当cls没有参数时，返回false
            return new RegExp(' ' + cls + ' ').test(' ' + elem.className + ' ');
        },
        _addClass: function (elem, cls) {
            if (!this._hasClass(elem, cls)) {
                elem.className = elem.className == '' ? cls : elem.className + ' ' + cls;
            }
        },
        _removeClass: function (elem, cls) {
            if (this._hasClass(elem, cls)) {
                var newClass = ' ' + elem.className.replace(/[\t\r\n]/g, '') + ' ';
                while (newClass.indexOf(' ' + cls + ' ') >= 0) {
                    newClass = newClass.replace(' ' + cls + ' ', ' ');
                }
                elem.className = newClass.replace(/^\s+|\s+$/g, '');
            }
        },
        init: function () {
            var that = this;
            var tableSelWarps = doc.querySelectorAll('.j-table-sel-warp');
            that._appendHTML(doc.querySelector('body'), '<div class="out-fix-sel" style="display: none;"></div>');
            var fixSel = doc.querySelector('.out-fix-sel');
            for (var i = 0; i < tableSelWarps.length; i++) {
                var tableSelWarp = tableSelWarps[i],
                    selText = tableSelWarp.querySelector('.sel-text'),
                    tableSel = tableSelWarp.querySelector('.j-table-sel'),
                    tableSelOpts = tableSelWarp.querySelectorAll('.j-table-sel li'),
                    activeOpt = tableSelWarp.querySelector('.j-table-sel li.active');
                //设置选择的文字
                selText.innerText = activeOpt ? activeOpt.innerText : '---';
                var dId = tableSelWarp.getAttribute('data-id');
                //给选择文字绑定点击事件
                doc.addEventListener('click', function (e) {
                    var e = e || window.event;
                    var elem = e.target || e.srcElement;
                    while (elem) {
                        if (that._hasClass(elem, 'sel-text')) {
                            var siblingSel = elem.parentNode.querySelector('.j-table-sel');
                            siblingSel.setAttribute('data-id', elem.parentNode.getAttribute('data-id'));
                            siblingSel.setAttribute('data-name', elem.parentNode.getAttribute('data-name'));
                            fixSel.innerHTML = siblingSel.outerHTML;
                            //偏移
                            var rect = elem.getBoundingClientRect();
                            fixSel.style.left = (rect.left - 50) + 'px';
                            fixSel.style.top = (rect.bottom + window.scrollY) + 'px';
                            fixSel.style.display = 'block';
                            return;
                        }
                        elem = elem.parentNode;
                    }
                    fixSel.style.display = 'none'; //点击的不是div或其子元素
                });
            }
            //给选项绑定事件
            fixSel.addEventListener('click', function (e) {
                var e = e || window.event;
                var elem = e.target || e.srcElement;
                var fixTableSel = this.querySelector('.j-table-sel');
                if (elem.nodeName.toUpperCase() == 'LI') {
                    //修改选择的文字
                    var innerText = elem.innerText, value = elem.getAttribute('data-value'),
                        id = elem.parentNode.getAttribute('data-id'), name = elem.parentNode.getAttribute('data-name');
                    var selWrap = doc.querySelector('.j-table-sel-warp[data-id="' + id + '"][data-name="' + name + '"]');
                    if (selWrap) {
                        selWrap.querySelector('.sel-text').innerText = innerText;
                        selWrap.querySelector('.sel-text').setAttribute('data-value', value);
                    }
                    //设置active
                    that._removeClass(doc.querySelector('.j-table-sel[data-id="' + id + '"] .active'), 'active');
                    that._addClass(doc.querySelector('.j-table-sel[data-id="' + id + '"] li[data-value="' + value + '"]'), 'active');
                    //执行onchange函数
                    var evtFun = elem.getAttribute('onchange');
                    eval('(function () { var data={id:"' + id + '",value:"' + value + '"};' + evtFun + '(data)})();');
                }
            })
        }
    }
    tableSel.init();
}(window, document));