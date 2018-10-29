$(function() {
	if(error_message && error_message != ""){
		setError(error_message);
	}
	
	$(document).keydown(function(event){ 
		let code = event.keyCode;
		if(code == 13){ 
			realLogin();
		}
    }); 

})
function realLogin(){
	let error = checkForm();
	if(error && error != ""){
		setError(error);
	}else{
		$('#loginForm').submit();
	}
}
function checkForm(){
	// 校验用户名
	let error = checkInput('username', '用户名');
	if(error && error != ""){
		return error;
	}
	// 校验密码
	error = checkInput('password', '密码');
	if(error && error != ""){
		return error;
	}
}
function checkInput(inputId, inputName){
	let inputVal =  $.trim($("#"+inputId).val());
	if(inputVal.length == 0){
		return inputName + "不能为空";
	}
	$("#"+inputId).val(inputVal);
}
function setError(error){
	$("div#error").removeAttr("class");
	$("div#error").addClass("show_error");
	$("div#error span").html(error);
}