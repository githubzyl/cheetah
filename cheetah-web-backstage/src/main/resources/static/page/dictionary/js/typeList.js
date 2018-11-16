let table = $('#'+Component.TABLE_ID), 
     searchForm = $('#'+Component.SEARCH_FORM_ID),
     title = '字典类型',
     requestRoot = '/dictionaryType',
     searchUrl = requestRoot + '/list',
     removeUrl = requestRoot + '/remove',
     addUrl = requestRoot + '/add',
     editUrl = requestRoot + '/edit',
     editPageUrl = "/dictionary/typeEdit",
     editFormId = 'dictionaryTypeForm',
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
			title : '类型编码',
			field : 'vcCode',
			align : 'center',
			valign : 'middle',
			width: 300
		}, {
			title : '类型名称',
			field : 'vcName',
			align : 'center',
			valign : 'middle',
			width: 300
		}, {
			title : '类型描述',
			field : 'vcDescription',
			align : 'center',
			valign : 'middle'
		},{
			field : 'operate',
			title : '操作',
			align: 'center',
            valign: 'middle',
            width: 150,
            events: operateEvents,
			formatter : operateFormatter,
			visible: false
		}]
	};
	renderBootstrapTable(tableOption, table);
}
function operateFormatter(value, row, index) {
    return [
        '<button type="button" class="LookDictDetail btn btn-success btn-sm">查看数据字典</button>'
    ].join('');
}
window.operateEvents = {
     'click .LookDictDetail': function (e, value, row, index) {
        	lookDictDetail(row);
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
        	vcCode: {
                message: '类型编码不合法',
                validators: {
                    notEmpty: {
                        message: '类型名称不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 10,
                        message: '请输入3到10个字符'
                    }
                }
            },
            vcName: {
                validators: {
                    notEmpty: {
                        message: '类型名称不能为空'
                    }, stringLength: {
                        min: 1,
                        max: 25,
                        message: '请输入1到25个字符'
                    }
                }
            }
        }
    });
}
function lookDictDetail(row){
	let url = contextPath + '/page/dictionary/typeDataDetail?lDictType='+row.id;
	showDialog(url, {
		type: BootstrapDialog.TYPE_SUCCESS,
		title : '数据字典详情',
		style: 'width:800px;',
		onshown: function(dialog){
			
		},
		buttons: [{
            label: '关闭',
            cssClass: 'btn-default',
            action: function(dialog) {
                dialog.close();
            }
        }]
	});
}