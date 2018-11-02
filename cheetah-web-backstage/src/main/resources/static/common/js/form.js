//清空查询条件的表单
function clearForm(form, table, beforeSearch){
	form[0].reset();
	if(beforeSearch && beforeSearch instanceof Function){
		beforeSearch();
	}
	bootstrapTableSearch(table);
}
//获取弹框中的表单
function getDialogForm(dialog,formId){
	return dialog.getModalBody().find('#'+formId);
}
//批量设置表单的值
function setFormData(form, data){
	form.find('[name]').each(function() {
        let type = $(this)[0].nodeName.toLowerCase();
        let name = $(this).attr('name');
        let nodeType = $(this).attr('type');
        let value = data[''+name+''];
        if(nodeType == 'radio'){
        	form.find('input[name="'+name+'"][value='+value+']').attr("checked",true); 
        }else{
        	form.find(type+"[name='"+name+"']").val(value);
        }
    });
}
//保存弹出框的数据
function saveFormData(dialog, formId, isEdit, row, url, table, successCallBack){
	let form = getDialogForm(dialog, formId);
	let data = getFormData(form);
	if(isEdit){
		data.id = row.id;
	}
    let fv = form.data('bootstrapValidator');
	fv.validate();
    if (!fv.isValid()) {
    	return;
    }
    //请求后台保存数据
    asyncAjax(url,'post',data,
    	function(result){
    		if(result.status == ServerStatus.SUCCESS){
    			toastrInfo(Message.SUCCESS_SAVE);
    			dialog.close();
    			bootstrapTableSearch(table);
    			if(successCallBack && successCallBack instanceof Function){
    				successCallBack();
    			}
			}else{
				toastrError(result.msg);
			}
    	},
    	function(res){
    		toastrError(ajaxError(res));
    	}
    );
}
//批量获取表单的值
function getFormData(form){
	let data = {};
	let radioArray = [];
	form.find('[name]').each(function() {
		let name = $(this).attr('name');
		let nodeType = $(this).attr('type');
		if(nodeType == 'radio'){
			data[name] = form.find('input[name="'+name+'"]:checked').val();
		}else{
			data[name] = $(this).val();
		}
    });
	return data;
}
/**
 * 初始化编辑表单
 * @param dialog 弹框
 * @param isEdit 是否编辑状态
 * @param editFormId 编辑表单的formId
 * @param searchUrl   查询表单数据的URL
 * @param beforeRender 表单渲染之前需要执行的方法
 * @param afterRender 表单初始化之后需要执行的方法
 * @returns
 */
function initDialogEditForm(dialog, isEdit, editFormId, searchUrl, beforeRender, afterRender){
	let editForm = getDialogForm(dialog, editFormId);
	//渲染前
	if(beforeRender && beforeRender instanceof Function){
		beforeRender(editForm);
	}
	if(isEdit){
		asyncAjax(searchUrl,null,null,
	    	function(result){
	    		if(result.status == ServerStatus.SUCCESS){
	    			setFormData(editForm, result.data);
				}else{
					showError(result.msg);
				}
	    	},
	    	function(res){
	    		toastrError(ajaxError(res));
	    	}
	    );
	}
	//渲染后
	if(afterRender && afterRender instanceof Function){
		afterRender(editForm);
	}
}
/**
 * 打开编辑的弹框
 * @param isEdit 是否编辑状态
 * @param title  标题，比如：用户
 * @param row  编辑时选中的行
 * @param dialogStyle 弹框的样式style
 * @param editFormId 弹框中需要编辑的表单的formId
 * @param table 数据列表的表格table
 * @param searchUrl 编辑状态时查询表单数据的url
 * @param saveUrl  保存数据的url
 * @param beforeRender 表单渲染之前需要执行的方法
 * @param afterRender 表单初始化之后需要执行的方法
 * @returns
 */
function openEditDialog(isEdit, title, row, dialogStyle, editFormId, table, searchUrl, saveUrl, beforeRender, afterRender){
	title = (isEdit ? '编辑' : '新增') + title;
	let url = contextPath + '/page/user/userEdit';
	showDialog(url,{
		type: BootstrapDialog.TYPE_SUCCESS,
		title : title,
		style: dialogStyle,
		onshown: function(dialog){
			initDialogEditForm(dialog,isEdit, editFormId, searchUrl, initFormValidator);
		},
		buttons: [{
            label: '取消',
            action: function(dialog) {
                dialog.close();
            }
        }, {
            label: '保存',
            cssClass: 'btn-success',
            action: function(dialog) {
            	saveFormData(dialog, editFormId, isEdit, row, saveUrl, table);
            }
        }]
	});
}