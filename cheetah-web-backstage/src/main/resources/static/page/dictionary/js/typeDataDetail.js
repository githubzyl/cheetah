$(function() {
	initTable();
});
function initTable() {
	let options = {
	    url : contextPath + "/dataDictionary/list",
	    queryParams: queryParams,
	    showToggle : false,
		showColumns : false,
		showRefresh : false,
		columns : [{
			title : '字典编码',
			field : 'cDictEntry',
			align : 'center',
			valign : 'middle',
			width: 300
		}, {
			title : '字典名称',
			field : 'vcEntryName',
			align : 'center',
			valign : 'middle',
			width: 300
		}, {
			title : '排序',
			field : 'lSort',
			align : 'center',
			valign : 'middle'
		}]
	};
	renderBootstrapTable(options,$('#typeDataTable'));
}
function queryParams(params) {
	let formParams = getFormData($("#typeDataSearchForm"));
	return bootstrapTableQueryParams(params,formParams);
}
function btnSearch() {
	bootstrapTableSearch($('#typeDataTable'));
}