// 编辑
function edit(){
	let url = '/page/user/userEdit';
	let searchUrl = '/profile/info';
	showDialog(url,{
		type: BootstrapDialog.TYPE_SUCCESS,
		title : '编辑个人信息',
		style: "width:500px;",
		onshown: function(dialog){
			let editForm = getDialogForm(dialog, 'userForm');
			editForm.find('#vcUserName').attr('readonly','readonly');
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
			initFormValidator(editForm);
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
            	let editForm = getDialogForm(dialog, 'userForm');
            	saveProfile(editForm,dialog);
            }
        }]
	});
}
function saveProfile(form,dialog){
	let data = getFormData(form);
    let fv = form.data('bootstrapValidator');
	fv.validate();
    if (!fv.isValid()) {
    	return;
    }
    let url = '/profile/save';
    //请求后台保存数据
    asyncAjax(url,'post',data,
    	function(result){
    		if(result.status == ServerStatus.SUCCESS){
    			dialog.close();
    			window.location.reload();
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
        	vcUserName: {
                message: '用户名不合法',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength: {
                        min: 4,
                        max: 45,
                        message: '请输入4到45个字符'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\. \u4e00-\u9fa5 ]+$/,
                        message: '用户名只能由字母、数字、点、下划线和汉字组成 '
                    }
                }
            },
            vcRealName: {
                validators: {
                    notEmpty: {
                        message: '真实姓名不能为空'
                    }, stringLength: {
                        min: 2,
                        max: 45,
                        message: '请输入2到45个字符'
                    }
                }
            }, 
            vcMobile: {
                validators: {
                    notEmpty: {
                        message: '手机号码不能为空'
                    },
                    regexp: {
                        regexp: "^([0-9]{11})?$",
                        message: '手机号码格式错误'
                    }
                }
            },
            vcEmail: {
                validators: {
                    notEmpty: {
                        message: '邮箱地址不能为空'
                    },
                    emailAddress: {
                        message: '请输入正确的邮件地址如：123@qq.com'
                    }
                }
            } 
        }
    });
}