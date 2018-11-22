var app = new Vue({
	extends: CompPage,
	data : {
		title : '登录日志',
	    requestRoot: '/userLoginLog'
	},
	methods : {
		setColumns : function(){
			return [
				{
					title : '用户名',
					field : 'vcUserName',
					align : 'center',
					valign : 'middle',
					width:80
				},{
					title : '登录类型',
					field : 'cLoginType',
					align : 'center',
					valign : 'middle',
					width:80
				},{
					title : '登录时间',
					field : 'dLoginTime',
					align : 'center',
					valign : 'middle',
					width:160,
					sortable: true,
					sortName: 'd_login_time',
					formatter: function(value, row, index){
						return longDateFormat(value,'yyyy-MM-dd HH:mm:ss');
					}
				},{
					title : '登录IP',
					field : 'vcIp',
					align : 'center',
					valign : 'middle',
					width:180
				},{
					title : '登录地址',
					field : 'vcLocation',
					align : 'center',
					valign : 'middle',
					width:160
				},{
					title : '登录设备',
					field : 'vcDeviceType',
					align : 'center',
					valign : 'middle',
					width:180
				},{
					title : '浏览器类型',
					field : 'vcBrowserType',
					align : 'center',
					valign : 'middle',
					width:180
				},{
					title : '登出时间',
					field : 'dLogoutTime',
					align : 'center',
					valign : 'middle',
					width:160,
					formatter: function(value, row, index){
						return longDateFormat(value,'yyyy-MM-dd HH:mm:ss');
					}
				}
			];
		},
		beforeRenderTable: function(){
			this.setLoginTypeSelection($('#loginType'));
			jeDate("#loginStartTime",{
				format: 'YYYY-MM-DD hh:mm:ss',
		        isTime:true
		    });
			jeDate("#loginEndTime",{
				format: 'YYYY-MM-DD hh:mm:ss',
		        isTime:true
		    });
		},
		setLoginTypeSelection: function(selectEle){
			asyncAjax('/enum/loginType',null,null,
			    function(result){
			    	if(result.status == ServerStatus.SUCCESS){
		    			setBaseSelectOptions(selectEle, result.data, 'code', 'name');
					}else{
						toastrError(result.msg);
					}
			    },
			    function(res){
			    	toastrError(ajaxError(res));
			    }
			);
		}
	}
});