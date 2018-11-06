//比较日期时间大小
function compareTime(startDate, endDate, fmt){
	let sDate = formatDate(startDate, fmt).getTime();
	let eDate = formatDate(endDate, fmt).getTime();
	let a = longDateFormat(sDate,fmt);
	return sDate > eDate ? 1 : (sDate == eDate ? 0 : -1);
}
//增加天数
function addDay(currDateStr, days, fmt) {
	var date = new Date(currDateStr);// 获取当前时间
	date.setDate(date.getDate() + days);// 设置天数 -1 天
	return dateFormat(date, fmt);
}
// 格式化long型日期
function longDateFormat(longDate, fmt) {
	if(null == longDate || undefined == longDate || '' == longDate
			|| null == fmt || undefined == fmt || '' == fmt){
		return null;
	}
	return dateFormat(new Date(longDate), fmt);
}
// 日期格式化
function dateFormat(date, fmt) {
	let month = date.getMonth() + 1, day = date.getDate(), hours = date.getHours(),
			minutes = date.getMinutes(), seconds = date.getSeconds(),
			quarter = Math.floor((date.getMonth() + 3) / 3),
			milliseconds = (date.getMilliseconds() < 10 ? "00"
					+ date.getMilliseconds()
					: (date.getMilliseconds() < 100 ? '0'
							+ date.getMilliseconds() : date.getMilliseconds()));
	let o = {
		"M+" : month, // 月份
		"d+" : day, // 日
		"H+" : hours, // 小时
		"m+" : minutes, // 分
		"s+" : seconds, // 秒
		"q+" : quarter, // 季度
		"S" : milliseconds
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
// 字符串转日期
function formatDate(value) {
	var date = new Date(value);
	if (date == "1970-01-01 08:00")
		return "--";
	else
		return date;
}
//校验日期格式
function checkDate(dateFormatReg, dateValue){
	if (!dateFormatReg.test(dateValue)) { 
		return false; 
	} 
	return true;
}