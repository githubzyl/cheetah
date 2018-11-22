var app = new Vue({
	extends: CompPage,
	data : {
		 title : '定时任务',
	     requestRoot:  '/scheduledJob',
	     editPageUrl : "/job/jobEdit",
	     editFormId : 'jobForm'
	},
	methods : {
		setColumns : function(){
			return [
				{
					field : 'checked',
					checkbox : true
				},{
					title : '任务名称',
					field : 'vcJobName',
					align : 'center',
					valign : 'middle',
					width:180
				}, {
					title : '任务组名',
					field : 'vcJobgroupName',
					align : 'center',
					valign : 'middle',
					visible: false
				}, {
					title : '触发器名',
					field : 'vcTriggerName',
					align : 'center',
					valign : 'middle',
					visible: false
				}, {
					title : '触发器组名',
					field : 'vcTriggergroupName',
					align : 'center',
					valign : 'middle',
					visible: false
				}, {
					title : '任务执行类',
					field : 'vcJobclassName',
					align : 'center',
					valign : 'middle'
				}, {
					title : 'cron表达式',
					field : 'vcCronExpression',
					align : 'center',
					valign : 'middle',
					width:140
				}, {
					title : '任务状态',
					field : 'cStatus',
					align : 'center',
					valign : 'middle',
					width:90,
					formatter: function(value, row, index){
						let cls = (value == 0 ? 'icon-yunxingzhong' : 'icon-yitingzhi');
						let color = (value == 0 ? 'green' : 'red');
						let title = (value == 0 ? '运行中' : '已停止');
						let span = '<span title="'+title+'" class="iconfont '+cls+'"  style="color:'+color+'"></span>';
						return span;
					}
				},{
					title : '程序启动时立即执行',
					field : 'cExecOnStartup',
					align : 'center',
					valign : 'middle',
					width:145,
					visible: false,
					formatter: function(value, row, index){
						let text = (value == 0 ? '否' : '是');
						let color = (value == 0 ? 'red' : 'green');
						let span = '<span style="color:'+color+'">'+text+'</span>';
						return span;
					}
				},{
					title : '最新开始执行时间',
					field : 'lLastStartTime',
					align : 'center',
					valign : 'middle',
					width:160,
					formatter: function(value, row, index){
						return longDateFormat(value,'yyyy-MM-dd HH:mm:ss.S');
					}
				},{
					title : '最新执行完成时间',
					field : 'lLastEndTime',
					align : 'center',
					valign : 'middle',
					width:160,
					formatter: function(value, row, index){
						return longDateFormat(value,'yyyy-MM-dd HH:mm:ss.S');
					}
				},{
					title : '最新执行状态',
					field : 'lLastExecStatus',
					align : 'center',
					valign : 'middle',
					width:110,
					formatter: function(value, row, index){
						if(value == null){
							return value;
						}
						let cls = ( value == 0 ? 'icon-zhixingchenggong' : 'icon-zhixingyichang');
						let color = (value == 0 ? 'green' : 'red');
						let title = (value == 0 ? '执行成功' : '执行异常');
						let span = '<span title="'+title+'" class="iconfont '+cls+'"  style="color:'+color+'"></span>';
						return span;
					}
				}
			];
		},
		operateFormatter : function(value, row, index) {
			let btnName = (row.cEnable == 0 ? '启用' : '禁用');
			let className = (row.cEnable == 0 ? 'btn-success' : 'btn-danger');
		    return [
		        '<button type="button" class="EnableOrDisable btn '+className+' btn-sm">'+btnName +'</button>'
		    ].join('');
		},
		beforeRenderEditForm : function(editForm, isEdit) {
			if (isEdit) {
				editForm.find('#vcUserName').attr('readonly','readonly');
			}
		},
		initFormValidator : function(form) {
			form.bootstrapValidator({
				message : '输入值不合法',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					vcJobName: {
		                message: '任务名称不合法',
		                validators: {
		                    notEmpty: {
		                        message: '任务名称不能为空'
		                    },
		                    stringLength: {
		                        min: 4,
		                        max: 45,
		                        message: '请输入4到45个字符'
		                    }
		                }
		            },
		            vcJobclassName: {
		                validators: {
		                    notEmpty: {
		                        message: '任务执行类不能为空'
		                    }, stringLength: {
		                        min: 4,
		                        max: 200,
		                        message: '请输入4到200个字符'
		                    }
		                }
		            }, 
		            vcCronExpression: {
		                validators: {
		                    notEmpty: {
		                        message: 'cron表达式不能为空'
		                    }
		                }
		            }
				}
			});
		},
		// 启动任务
		startup: function(){
			this.startOrStop(0);
		},
		// 启动所有任务
		startupAll:function(){
			this.startOrStopAll(0);
		},
		// 立即执行一次任务
		executeNow: function(){
			let rows = getSelectedRows(this.table);
			if(rows.length <= 0){
				showWarn('请选择需要执行的任务');
				return;
			}
			if(rows.length > 1){
				showWarn('只能选择一个任务执行');
				return;
			}
			let row = rows[0];
			if(row){
				let url = '/scheduledJob/executeJobNow?id='+row.id;
				asyncAjax(url,null, null,
					function(result){
						if(result.status == ServerStatus.SUCCESS){
							showInfo("任务正在执行中,请稍后刷新数据查看执行情况");
				    		this.btnSearch();
						}else{
							showError(result.msg);
						}
					},
					function(res){
						showError(ajaxError(res));
					}	
				)
			}
		},
		// 停止任务
		stop:function(){
			this.startOrStop(1);
		},
		// 停止所有任务
		stopAll:function (){
			this.startOrStopAll(1);
		},
		startOrStop: function (type){
			let rows = getSelectedRows(this.table);
			let warnMsg = '请选择需要' + (type == 0 ? '启动' : '停止') + '的任务' ;
			let infoMsg = '任务已经' + (type == 0 ? '启动' : '停止');
			let url = '/scheduledJob/' + (type == 0 ? 'startJob' : 'pauseJob');
			if(rows && rows.length > 0){
				let ids = this.getIds();
				asyncAjax(url,'post', ids,
					function(result){
						if(result.status == ServerStatus.SUCCESS){
							showInfo(infoMsg);
				    		this.btnSearch();
						}else{
							showError(result.msg);
						}
					},
					function(res){
						showError(ajaxError(res));
					}	
				)
			}else{
				showWarn(warnMsg);
			}
		},
		startOrStopAll:function(type){
			let url = '/scheduledJob/' + (type == 0 ? 'startJob' : 'pauseJob');
			let infoMsg = '任务已经全部' + (type == 0 ? '启动' : '停止');
			asyncAjax(url,'post', null,
				function(result){
					if(result.status == ServerStatus.SUCCESS){
						showInfo(infoMsg);
			    		this.btnSearch();
					}else{
						showError(result.msg);
					}
				},
				function(res){
					showError(ajaxError(res));
				}	
			)
		},
		getIds:function (){
			let rows = getSelectedRows(this.table);
			let ids = "";
			for(let i = 0, length = rows.length ; i < length ; i++){
				ids += "&ids=" + rows[i].id;
			}
			if(ids != ""){
				ids = ids.substring(1);
			}
			return ids;
		}
	}
});