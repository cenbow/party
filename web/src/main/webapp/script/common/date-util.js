//console.log(getDateDiff(getDateTimeStamp('2016-07-13 16:59:01')));

function getDateDiff(publishTime) {
	var d_minutes, d_hours, d_days;
	var timeNow = parseInt(new Date().getTime());
	var d;
	d = timeNow - publishTime;
	d_days = parseInt(d / 86400000);
	d_hours = parseInt(d / 3600000);
	d_minutes = parseInt(d / 60000);
	d_second = parseInt(d / 1000);
	if (d_days > 0 && d_days < 4) {
		return d_days + "天前";
	} else if (d_days <= 0 && d_hours > 0) {
		return d_hours + "小时前";
	} else if (d_hours <= 0 && d_minutes > 0) {
		return d_minutes + "分钟前";
	} else if (d_minutes <= 0 && d_second > 0) {
		return "刚刚";
	} else {
		var s = new Date(publishTime);
		return s.getFullYear() + "-" + (s.getMonth() + 1) + "-" + s.getDate() + "";
	}
}

//字符串转日期
function strToDate(dateStr) {
	if(!util.isValid(dateStr))
	return '';
	return new Date(Date.parse(dateStr.replace(/-/g, "/")));
}

//js函数代码：字符串转换为时间戳
function getDateTimeStamp(dateStr) {
	//	return Date.parse(dateStr.replace(/-/gi, "/"));
	var time = strToDate(dateStr);
	time = time.getTime();
	return time;
}

//时间戳转字符串
function getDateStr(timeStamp) {
	if(!util.isValid(timeStamp))
		return '';
	var d = new Date(timeStamp);
	return d;
}

//---------------------------------------------------
// 日期格式化
// 格式 YYYY/yyyy/YY/yy 表示年份
// MM/M 月份
// W/w 星期
// dd/DD/d/D 日期
// hh/HH/h/H 时间
// mm/m 分钟
// ss/SS/s/S 秒
//---------------------------------------------------
Date.prototype.Format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth() + 3) / 3), //quarter
		"S" : this.getMilliseconds() //millisecond
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}

	for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}
//根据毫秒数获取字符串类型时间
function getLocalTimeStr(timestamp, getLong) {
	var systemType = api.systemType;
	var newDate = new Date();
	newDate.setTime(timestamp);
	var newyear = newDate.getFullYear();
	var newmonth = newDate.getMonth();
	var newdate = newDate.getDate();
	var nowDate = new Date();
	var nowyear = nowDate.getFullYear();
	var nowmonth = nowDate.getMonth();
	var nowdate = nowDate.getDate();
	function getDateStr() {
		var str = newDate.toLocaleDateString();
		if (systemType == 'ios') {
			str = str.substring(5);
		}
		if (getLong) {
			str += " " + getTimeStr();
		}
		return str;
	}

	function getTimeStr() {
		var str = newDate.toLocaleTimeString();
		if (systemType == 'ios') {
			str = str.substring(5);
		}
		return str;
	}

	if (newyear < nowyear) {
		//返回年月日
		return getDateStr();
	} else {
		if (newmonth < nowmonth) {
			return getDateStr();
		} else {
			if (newdate < nowdate) {
				return getDateStr();
			} else {
				return getTimeStr();
			}
		}
	}
}