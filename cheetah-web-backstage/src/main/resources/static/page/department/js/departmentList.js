let table = $('#'+Component.TABLE_ID), 
     searchForm = $('#'+Component.SEARCH_FORM_ID),
     title = '部门',
     requestRoot = '/department',
     searchUrl = requestRoot + '/list',
     removeUrl = requestRoot + '/remove',
     addUrl = requestRoot + '/add',
     editUrl = requestRoot + '/edit',
     editPageUrl = "/department/departmentEdit",
     editFormId = 'departmentForm',
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
		}, {
			title : '部门编码',
			field : 'vcCode',
			align : 'center',
			valign : 'middle',
			width: 300
		}, {
			title : '部门名称',
			field : 'vcName',
			align : 'center',
			valign : 'middle',
			width: 300
		}, {
			title : '是否启用',
			field : 'cEnable',
			align : 'center',
			valign : 'middle',
			width: 80,
			formatter: function(value, row, index){
				let icon = 'iconfont ';
				icon += (value == 1 ? 'icon-enable' : 'icon-disable');
				let color = (value == 1 ? 'green' : 'red');
				let span = '<span class="'+icon+'" style="color:'+color+'"></span>';
				return span;
			}
		}, {
			title : '排序',
			field : 'lSort',
			align : 'center',
			valign : 'middle',
			width: 80
		},{
			field : 'operate',
			title : '操作',
			align: 'center',
            valign: 'middle',
            width: 100,
            events: operateEvents,
			formatter : operateFormatter
		}]
	};
	renderBootstrapTable(tableOption, table);
}
function operateFormatter(value, row, index) {
	let btnName = (row.cEnable == 0 ? '启用' : '禁用');
	let className = (row.cEnable == 0 ? 'btn-success' : 'btn-danger');
    return [
        '<button type="button" class="EnableOrDisable btn '+className+' btn-sm">'+btnName +'</button>'
    ].join('');
}
window.operateEvents = {
     'click .EnableOrDisable': function (e, value, row, index) {
    	 enableOrDisable(row.id, row.cEnable == '1' ? '0' : '1');
     }
};
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
	clearForm(searchForm,table, function(){
		$(searchForm).find('#pid').val('');
	});
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
    let saveUrl = isEdit ? editUrl : addUrl;
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
		beforeRender,
		initFormValidator
	);
}
function beforeRender(editForm,isEdit){
	
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
//启用或禁用
function enableOrDisable(departmentId, status){
	let message = status == 0 ? '禁用' : '启用';
	let data = 'departmentId='+departmentId+'&status='+status;
	asyncAjax('/department/status', 'post', data,
	    function(result){
	    	if(result.status == ServerStatus.SUCCESS){
    			toastrInfo('部门'+message+'成功');
    			btnSearch();
			}else{
				toastrError(result.msg);
			}
	    },
	    function(res){
	    	toastrError(ajaxError(res));
	    }
	);
}