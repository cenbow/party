/**
 * 工具 2016-12-29 Juliana
 */
(function(W, U) {
	"use strict";
	W.util = {
		isTargetType : function(obj, typeStr) {
			return typeof obj === typeStr;
		},
		isUndefined : function(obj) {
			var that = this;
			return that.isTargetType(obj, 'undefined');
		},
		isNullString : function(obj) {
			var that = this;
			if (typeof (obj) === 'string') {
				if (obj.trim() == '') {
					return true;
				}
			}
			return false;
		},
		isValid : function(obj) {
			var that = this;
			return !that.isUndefined(obj) && obj != null && !that.isNullString(obj);
		},		
		getStorage: function (key) {
            var ls = window.localStorage;
            if (ls) {
                var v = ls.getItem(key);
                if (!v) {
                    return;
                }
                if (v.indexOf('obj-') === 0) {
                    v = v.slice(4);
                    return JSON.parse(v);
                } else if (v.indexOf('str-') === 0) {
                    return v.slice(4);
                }
            }
        },
        setStorage: function (key, value) {
            if (arguments.length === 2) {
                var v = value;
                if (typeof v == 'object') {
                    v = JSON.stringify(v);
                    v = 'obj-' + v;
                } else {
                    v = 'str-' + v;
                }
                var ls = window.localStorage;
                if (ls) {
                    ls.setItem(key, v);
                }
            }
        },
        checkNumber : function(value) {
        	var reg = /^\d+$/g;
			if (value != "" && !reg.test(value)) {
				return false;
			}
			return true;
        },
        textareaToHtml:function (str) {
            return str.replace(/\r{0,}\n/g,"<br/>").replace(/\s/g,"&nbsp;");
        }
        ,
        htmlToTextarea:function (str) {
            return str.replace(/<br\/>/g,"\n").replace(/\&nbsp\;/g," ");
        }
	}
}(window,undefined));