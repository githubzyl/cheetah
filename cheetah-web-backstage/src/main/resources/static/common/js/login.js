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
	setCookieValue();
});
function realLogin(){
	let error = checkForm();
	if(error && error != ""){
		setError(error);
	}else{
		remeberMe();//记住我
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
function setCookieValue(){
	let value = getCookie();
	if(isEmpty(value)){
		return;
	}
	let array = value.split(';');
	for(let i = 0, length = array.length; i < length ; i++){
		let str = array[i];
		let attr = str.split("=");
		if(attr[0] == 'username'){
			$("#username").val(attr[1]);
		}else if(attr[0] == 'password'){
			$("#password").val(attr[1]);
		}else{
			$("#remeberMe").prop('checked', attr[1]);
		}
	}
}
function remeberMe(){
	let isRember=$('#remeberMe').prop('checked');
	if(isRember){ //如果记住
		let cookieValue = "username="+$("#username").val()+";password="+$("#password").val()+";remeberMe="+isRember;
		setCookie(getCookieName(), cookieValue, { expires: 7 });
	}else{
		removeCookie(getCookieName());
	}
}
function getCookie(){
	let value = $.cookie(getCookieName());
	return decode(value);
}
function setCookie(name,value,option){
	value = encode(value);
	$.cookie(name, value, option);
}
function removeCookie(name){
	$.cookie(name, '', { expires: -1 }); 
}
function getCookieName(){
	return encode('remeber');
}
function encode(value){
	if(isEmpty(value)){
		return null;
	}
	val = $.base64.btoa(value);//加密
	return val;
}
function decode(value){
	if(isEmpty(value)){
		return null;
	}
	let val = $.base64.atob(value);
	return val;
}
function isEmpty(str){
	return (undefined == str || null == str || '' === str);
}