let table = $('#'+Component.TABLE_ID), 
     searchForm = $('#'+Component.SEARCH_FORM_ID),
     title = '数据字典',
     requestRoot = '/dataDictionary',
     searchUrl = requestRoot + '/list',
     removeUrl = requestRoot + '/remove',
     saveUrl = requestRoot + '/save',
     editPageUrl = "/dictionary/dataEdit",
     editFormId = 'dataDictionaryForm',
     formDialogStyle = 'width:500px;';
$(function(){
	resizeTable(table, initTable, table);
});
function initTable(table){
	let tableOption = {
	    url : contextPath + searchUrl,
	    toolbar: '#toolbar',
	    queryParams: queryParams,
		columns : [{
			field : 'checked',
			checkbox : true
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
// 新增
function addItem(){
	goToEditPage(false, title,null,null);
}
// 编辑
function editItem(){
	let row = getEditRow(table);
	if(row){
		goToEditPage(true, title, row, requestRoot + '/'+row.id);
	}
}
// 删除
function removeItem() {
	removeRows(table, removeUrl, btnSearch);
}
// 跳转到编辑页面
function goToEditPage(isEdit, title, row, searchUrl) {
	openEditDialog(
		isEdit, 
		title, 
		row,
		formDialogStyle,
		editFormId,
		table,
		searchUrl,
		saveUrl,
		editPageUrl,
		null,
		initFormValidator
	);
}
// 初始化验证规则
function initFormValidator(form){
	form.bootstrapValidator({
        message: '输入值不合法',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	
        }
    });
}
