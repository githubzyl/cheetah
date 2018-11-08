let table = $('#'+Component.TABLE_ID), 
     searchForm = $('#'+Component.SEARCH_FORM_ID),
     title = '访问日志',
     requestRoot = '/userViewLog',
     searchUrl = requestRoot + '/list';
$(function(){
	jeDate("#viewStartTime",{
		format: 'YYYY-MM-DD',
        isTime:false
    });
	jeDate("#viewEndTime",{
		format: 'YYYY-MM-DD',
        isTime:false
    });
	resizeTable(table, initTable, table);
});
function initTable(table){
	let tableOption = {
	    url : contextPath + searchUrl,
	    toolbar: '#toolbar',
	    queryParams: queryParams,
		columns : [{
			title : '用户名',
			field : 'vcUserName',
			align : 'center',
			valign : 'middle',
			width:80
		},{
			title : '请求描述',
			field : 'vcOperation',
			align : 'center',
			valign : 'middle',
			width:120
		},{
			title : '访问URL',
			field : 'vcUrl',
			align : 'center',
			valign : 'middle',
		    width:160
		},{
			title : '请求方式',
			field : 'vcMethod',
			align : 'center',
			valign : 'middle',
			width:60
		},{
			title : '访问时间',
			field : 'dVisitTime',
			align : 'center',
			valign : 'middle',
			width:150,
			sortable: true,
			sortName: 'd_visit_time',
			formatter: function(value, row, index){
				return longDateFormat(value,'yyyy-MM-dd HH:mm:ss');
			}
		},{
			title : '访问设备',
			field : 'vcDeviceType',
			align : 'center',
			valign : 'middle',
			width:180
		},{
			title : '浏览器类型',
			field : 'vcBrowserType',
			align : 'center',
			valign : 'middle',
			width:180
		},{
			title : '访问IP',
			field : 'vcIp',
			align : 'center',
			valign : 'middle',
			width:100
		},{
			title : '登录地址',
			field : 'vcLocation',
			align : 'center',
			valign : 'middle',
			width:160
		}]
	};
	renderBootstrapTable(tableOption, table);
}
function queryParams(params) {
	let formParams = getFormData(searchForm);
	return bootstrapTableQueryParams(params,formParams);
}
// 搜索
function btnSearch(){
	bootstrapTableSearch(table);
}
// 重置
function btnClear(){
	clearForm(searchForm,table);
}