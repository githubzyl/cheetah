var loginPageDiv = new Vue({
	el: '#loginPageDiv',
	data : {
		username: '',
		password: '',
		remeberMe: false,
		error: error_message,
		showError: false
	},
	methods : {
		realLogin: function(){
			if(this.username == ''){
				this.error = '用户名不能为空';
				this.showError = true;
			}else if(this.password == ''){
				this.error = '密码不能为空';
				this.showError = true;
			}else{
				this.remeber();//记住我
				this.$refs.loginForm.submit();
			}
		},
		setCookieValue: function (){
			let value = this.getCookie();
			if(this.isEmpty(value)){
				return;
			}
			let array = value.split(';');
			for(let i = 0, length = array.length; i < length ; i++){
				let str = array[i];
				let attr = str.split("=");
				if(attr[0] == 'username'){
					this.username = attr[1];
				}else if(attr[0] == 'password'){
					this.password = attr[1];
				}else{
					this.remeberMe = attr[1];
				}
			}
		},
		remeber: function(){
			let isRember=this.remeberMe;
			if(isRember){ //如果记住
				let cookieValue = "username="+this.username+";password="+this.password+";remeberMe="+isRember;
				this.setCookie(this.getCookieName(), cookieValue, { expires: 7 });
			}else{
				this.removeCookie(this.getCookieName());
			}
		},
		getCookie: function(){
			let value = $.cookie(this.getCookieName());
			return this.decode(value);
		},
		setCookie: function(name,value,option){
			value = this.encode(value);
			$.cookie(name, value, option);
		},
		removeCookie: function(name){
			$.cookie(name, '', { expires: -1 }); 
		},
		getCookieName: function (){
			return this.encode('remeber');
		},
		encode: function(value){
			if(this.isEmpty(value)){
				return null;
			}
			val = $.base64.btoa(value);//加密
			return val;
		},
		decode: function(value){
			if(this.isEmpty(value)){
				return null;
			}
			let val = $.base64.atob(value);
			return val;
		},
		isEmpty: function(str){
			return (undefined == str || null == str || '' === str);
		}
	},
	mounted: function(){
		this.showError = !this.isEmpty(this.error);
		this.setCookieValue();
	}
});