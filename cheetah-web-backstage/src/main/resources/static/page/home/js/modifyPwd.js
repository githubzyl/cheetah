$(function(){
	let editForm = $('#modifyPwdForm');
	initFormValidator(editForm);
});
function savePwd(){
	let form = $('#modifyPwdForm');
	let data = getFormData(form);
    let fv = form.data('bootstrapValidator');
	fv.validate();
    if (!fv.isValid()) {
    	return;
    }
    let url = '/profile/modifyPwd/save';
    //请求后台保存数据
    asyncAjax(url,'post',data,
    	function(result){
    		if(result.status == ServerStatus.SUCCESS){
    			toastrInfo('密码修改成功,下次登录请使用新密码');
			}else{
				toastrError(result.msg);
				form[0].reset();
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
        	oldPwd: {
                message: '原密码不合法',
                validators: {
                    notEmpty: {
                        message: '原密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 18,
                        message: '请输入6到18个字符'
                    }
                }
            },
            newPwd: {
                validators: {
                    notEmpty: {
                        message: '新密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 18,
                        message: '请输入6到18个字符'
                    }
                }
            }, 
            repeatNewPwd: {
                validators: {
                    notEmpty: {
                        message: '重复密码不能为空'
                    },
                    identical: {
                        field: 'newPwd',
                        message: '重复密码与新密码不一致'
                    }
                }
            }
        }
    });
}