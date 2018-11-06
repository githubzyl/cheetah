let table = $('#'+Component.TABLE_ID), 
     searchForm = $('#'+Component.SEARCH_FORM_ID),
     title = '登录日志',
     requestRoot = '/userLoginLog',
     searchUrl = requestRoot + '/list';
$(function(){
	setLoginTypeSelection($('#loginType'));
	jeDate("#loginStartTime",{
		format: 'YYYY-MM-DD hh:mm:ss',
        isTime:true
    });
	jeDate("#loginEndTime",{
		format: 'YYYY-MM-DD hh:mm:ss',
        isTime:true
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
			title : '登录类型',
			field : 'cLoginType',
			align : 'center',
			valign : 'middle',
			width:80
		},{
			title : '登录时间',
			field : 'dLoginTime',
			align : 'center',
			valign : 'middle',
			width:160,
			formatter: function(value, row, index){
				return longDateFormat(value,'yyyy-MM-dd HH:mm:ss');
			}
		},{
			title : '登录IP',
			field : 'vcIp',
			align : 'center',
			valign : 'middle',
			width:180
		},{
			title : '登录设备',
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
			title : '登出时间',
			field : 'dLogoutTime',
			align : 'center',
			valign : 'middle',
			width:160,
			formatter: function(value, row, index){
				return longDateFormat(value,'yyyy-MM-dd HH:mm:ss');
			}
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
function setLoginTypeSelection(selectEle){
	asyncAjax('/enum/loginType',null,null,
	    function(result){
	    	if(result.status == ServerStatus.SUCCESS){
    			setBaseSelectOptions(selectEle, result.data, 'code', 'name');
			}else{
				toastrError(result.msg);
			}
	    },
	    function(res){
	    	toastrError(ajaxError(res));
	    }
	);
}