let table = $('#'+Component.TABLE_ID), 
     searchForm = $('#'+Component.SEARCH_FORM_ID),
     title = '数据字典',
     requestRoot = '/dataDictionary',
     searchUrl = requestRoot + '/list',
     removeUrl = requestRoot + '/remove',
     addUrl = requestRoot + '/add',
     editUrl = requestRoot + '/edit',
     editPageUrl = "/dictionary/dataEdit",
     editFormId = 'dataDictionaryForm',
     formDialogStyle = 'width:500px;';
$(function(){
	setTypeSelection($('#lDictType'));
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
			title : '字典类型',
			field : 'lDictType',
			align : 'center',
			valign : 'middle',
			width: 300,
			formatter:function(val,row){
				return val + '('+row.dictTypeName+')';
			}
		}, {
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
		onshownBefore,
		initFormValidator,
		true
	);
}
function onshownBefore(editForm,isEdit,searchUrl){
	asyncAjax('/dictionaryType/all',null,null,
	    function(result){
	    	if(result.status == ServerStatus.SUCCESS){
    			setBaseSelectOptions(editForm.find('#lDictType'), result.data, 'id', 'vcCode','vcName');
    			setEditFormData(isEdit, searchUrl, editForm);
			}else{
				toastrError(result.msg);
			}
	    },
	    function(res){
	    	toastrError(ajaxError(res));
	    }
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
        	lDictType: {
                message: '字典类型不合法',
                validators: {
                    notEmpty: {
                        message: '字典类型不能为空'
                    }
                }
            },
            cDictEntry: {
            	message: '类型编码不合法',
            	validators: {
            		notEmpty: {
            			message: '字典编码不能为空'
            		},
            		stringLength: {
            			min: 1,
            			max: 5,
            			message: '请输入1到5个字符'
            		}
            	}
            },
            vcEntryName: {
                validators: {
                    notEmpty: {
                        message: '字典名称不能为空'
                    }, stringLength: {
                        min: 1,
                        max: 45,
                        message: '请输入1到45个字符'
                    }
                }
            }
        }
    });
}
function setTypeSelection(selectEle){
	asyncAjax('/dictionaryType/all',null,null,
	    function(result){
	    	if(result.status == ServerStatus.SUCCESS){
    			setBaseSelectOptions(selectEle, result.data, 'id', 'vcCode','vcName');
			}else{
				toastrError(result.msg);
			}
	    },
	    function(res){
	    	toastrError(ajaxError(res));
	    }
	);
}