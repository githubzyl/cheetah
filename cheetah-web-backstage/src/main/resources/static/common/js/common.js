//获取服务端返回数据
function getServerData(result,defaultData){
	let status = result.status;
	if(status == ServerStatus.SUCCESS){
		return result.data == null? '' : result.data;
	}
	checkStatus(result);
	return defaultData || [];
}
//判断返回结果的状态
function checkStatus(res){
	let status = res.status;
	if(status == ServerStatus.SESSION_TIMEOUT){// session超时
		goToLogin();
	}else{// 其他错误
		if(status == HttpStatus.SUCCESS && res.statusText && res.statusText == 'parsererror'){
			goToLogin();
			return;
		}
		showError(res.msg || Message.ERROR_SYSTEM_EXCEPTION);
	}
}
//session超时提示框
function goToLogin(){
	showWarn(Message.WARN_SESSION_TIMEOUT,'点此登录',function(result) {
    	window.location.href = contextPath + CommonURI.LOGIN_URI;
    });
}
//显示加载页面的弹出框
function showDialog(url, options){
	let $message = $('<div></div>');
	$message.load(url,function(response,status,xhr){
		if(response != null){
			try{
				let result = JSON.parse(response);
				checkStatus(result);
			}catch(e){
				options.message = $message;
				BootstrapDialog.show(options);
			}
		}else{
			showError(Message.ERROR_SYSTEM_EXCEPTION);
		}
	})
}
//显示提示框
function showWarn(msg, buttonLabel, callback){
	BootstrapDialog.alert({
		title: '提示',
		type: BootstrapDialog.TYPE_WARNING, 
	    message: msg, 
	    buttonLabel:buttonLabel || '我知道了', 
        callback: callback
   });
}
// 系统错误消息展示框
function showError(msg){
	BootstrapDialog.alert({
		title: '错误',
		type: BootstrapDialog.TYPE_DANGER, 
	    message:msg,
	    buttonLabel:'我知道了'
     });
}
// 成功消息展示框
function showInfo(msg){
	BootstrapDialog.alert({
		title: '提示',
		type: BootstrapDialog.TYPE_SUCCESS, 
		message:msg,
		buttonLabel:'我知道了'
	});
}
//确认信息提示框
function showConfirmWarn(msg, cancelCallBack, confirmCallBack){
	let dialog = null;
	dialog = BootstrapDialog.confirm({
        title: '确认',
        message: msg,
        type: BootstrapDialog.TYPE_WARNING,
        closable: true,
        draggable: true, 
        btnCancelLabel: '取消',
        btnOKLabel: '确定',
        btnOKClass: 'btn-warning', 
        callback: function(result) {
            if(result) {
            	if(confirmCallBack && $.isFunction(confirmCallBack)){
            		confirmCallBack(dialog);
            	}
            }else {
              	if(cancelCallBack && $.isFunction(cancelCallBack)){
              		cancelCallBack(dialog);
            	}
            }
        }
    });
}
//错误消息提示
function toastrError(error){
	$.message({
        message:error,
        time:'3000',
        body: $('body', parent.document),
//        showClose:true,
        type:'error'
    });
}
//成功消息提示
function toastrInfo(info){
	let message = info || '操作成功';
	$.message({
        message:message,
        time:'1500',
        body: $('body', parent.document),
        type:'success'
    });
}
//异步ajax请求
function asyncAjax(url,method, data, success, error){
	url = contextPath + url;
	var option = {
		url: url,
		cache: false,
		dataType: 'json',
		method: 'get',
		success: function(result){
			success(result);
		},
		error: function(res){
			error(res);
		}
	}
	if(method){
		option.method = method;
	}
	if(data){
		option.data = data;
	}
	$.ajax(option);
}
//ajax请求异常
function ajaxError(res){
	let error = Message.ERROR_SYSTEM_EXCEPTION;
	if(res.status == HttpStatus.NOT_FOUND){
		error = res.responseJSON.msg;
	}
	return error;
}
//设置select框的数据
function setBaseSelectOptions(select, data, valueField, nameField, warnField, showSelectText){
	if(showSelectText != false){
		select.append('<option value="">-----请选择-----</option>');
	}
	if(data){
		for(var i = 0,length = data.length ; i < length; i++){
			let obj = data[i];
			let option = '<option value="'+obj[valueField]+'" data-name="'+obj[nameField]+'">'+obj[nameField]+'</option>';
			if(warnField && warnField != ""){
				option = '<option value="'+obj[valueField]+'" data-name="'+obj[nameField]+'">'+obj[nameField]+'('+obj[warnField]+')</option>';
			}
			select.append(option);
		}
	}
}