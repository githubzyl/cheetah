var IndexPage = new Vue({
	extends: NavigationComp,
	el : '#indexContainer',
	data : {
		loadMenuUrl: "/user/userMenus",
		profileUrl: "/profile",
		modifyPwdUrl: "/profile/modifyPwd",
		homePageUrl: "/home",
		logoutUrl: "/logout"
	},
	methods : {
		loadMenus: function(){
			let that = this;
			asyncAjax(that.loadMenuUrl,null,null,
				function(result){
					if(result.status == ServerStatus.SUCCESS){
						that.getMenus(result.data);
					}else{
						showError(result.msg);
					}
				},
				function(res){
					showError(ajaxError(res));
				}
			);
		},
		//加载首页
		loadHome: function(){
			this.getMenuUL().children("li.active").removeClass("active");
			this.setTopActiveMeunTitle(null, "首页");
		    this.viewPage(this.homePageUrl, "首页","home", '0');
		},
		logout: function() {
			window.location.href = contextPath + this.logoutUrl;
		},
	    profile: function(){
			this.setTopActiveMeunTitle(null,'个人信息');
			//跳转页面
			this.viewPage(this.profileUrl,'个人信息',null,null);
		},
		modifyPwd: function(){
			this.setTopActiveMeunTitle(null,'修改密码');
			//跳转页面
			this.viewPage(this.modifyPwdUrl,'修改密码',null,null);
		}
	},
	mounted : function() {
		this.loadMenus();
		this.loadHome();
	}
});
//菜单页面跳转
function goToPage(option,pageUrl,title,menuId,target,parentNames) {
	IndexPage.goToPage(option,pageUrl,title,menuId,target,parentNames);
}