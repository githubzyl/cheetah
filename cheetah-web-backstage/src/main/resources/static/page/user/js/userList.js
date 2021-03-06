var app = new Vue({
	extends: CompPage,
	data : {
		title : '用户',
		requestRoot : '/user',
		editPageUrl : "/user/userEdit",
		editFormId : 'userForm',
		operateEvents : {
			'click .EnableOrDisable' : function(e, value, row, index) {
				app.enableOrDisable(row.id, row.cStatus == '1' ? '0': '1');
			},
			'click .LockOrUnlock' : function(e, value, row, index) {
				app.lockOrUnlock(row.id, row.cLockStatus == '1' ? '0': '1');
			}
		}
	},
	methods : {
		setColumns : function(){
			return [
				{
					field : 'checked',
					checkbox : true
				},
				{
					title : '用户名',
					field : 'vcUserName',
					align : 'center',
					valign : 'middle'
				},
				{
					title : '真实姓名',
					field : 'vcRealName',
					align : 'center',
					valign : 'middle',
					width : 120
				},
				{
					title : '手机号码',
					field : 'vcMobile',
					align : 'center',
					valign : 'middle',
					width : 120
				},
				{
					title : '邮箱地址',
					field : 'vcEmail',
					align : 'center',
					valign : 'middle'
				},
				{
					title : '所属部门',
					field : 'deptName',
					align : 'center',
					valign : 'middle'
				},
				{
					title : '锁定状态',
					field : 'cLockStatus',
					align : 'center',
					valign : 'middle',
					width : 100,
					formatter : function(value, row, index) {
						let text = (value == 0 ? '未锁定' : '已锁定');
						let color = (value == 0 ? 'green': 'red');
						let span = '<span style="color:'+ color + '">' + text+ '</span>';
						return span;
					}
				},
				{
					title : '用户状态',
					field : 'cStatus',
					align : 'center',
					valign : 'middle',
					width : 100,
					formatter : function(value, row, index) {
						let text = (value == 0 ? '正常' : '已禁用');
						let color = (value == 0 ? 'green': 'red');
						let span = '<span style="color:'+ color + '">' + text+ '</span>';
						return span;
					}
				}, {
					field : 'operate',
					title : '操作',
					align : 'center',
					valign : 'middle',
					width : 150,
					events : this.operateEvents,
					formatter : this.operateFormatter
				}
			];
		},
		operateFormatter : function(value, row, index) {
			let btnName = (row.cStatus == 1 ? '激活' : '禁用');
			let className = (row.cStatus == 1 ? 'btn-success': 'btn-danger');
			let btnName2 = (row.cLockStatus == 1 ? '解锁' : '锁定');
			let className2 = (row.cLockStatus == 1 ? 'btn-success': 'btn-danger');
			return [ '<button type="button" class="EnableOrDisable btn '+ className+ ' btn-sm">'+ btnName+ '</button>',
					     '<button type="button" style="margin-left:10px;" class="LockOrUnlock btn '+ className2 + ' btn-sm">' + btnName2 + '</button>'
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
					vcUserName : {
						message : '用户名不合法',
						validators : {
							notEmpty : {
								message : '用户名不能为空'
							},
							stringLength : {
								min : 4,
								max : 45,
								message : '请输入4到45个字符'
							},
							regexp : {
								regexp : /^[a-zA-Z0-9_\. \u4e00-\u9fa5 ]+$/,
								message : '用户名只能由字母、数字、点、下划线和汉字组成 '
							}
						}
					},
					vcRealName : {
						validators : {
							notEmpty : {
								message : '真实姓名不能为空'
							},
							stringLength : {
								min : 2,
								max : 45,
								message : '请输入2到45个字符'
							}
						}
					},
					vcMobile : {
						validators : {
							notEmpty : {
								message : '手机号码不能为空'
							},
							regexp : {
								regexp : "^([0-9]{11})?$",
								message : '手机号码格式错误'
							}
						}
					},
					vcEmail : {
						validators : {
							notEmpty : {
								message : '邮箱地址不能为空'
							},
							emailAddress : {
								message : '请输入正确的邮件地址如：123@qq.com'
							}
						}
					}
				}
			});
		},
		assignRole : function() {
			let row = getEditRow(this.table, '请选择需要分配角色的用户');
			if (row) {
				let url = '/page/user/assignRole?userId=' + row.id;
				showDialog(url,
						{
							type : BootstrapDialog.TYPE_SUCCESS,
							title : '分配角色',
							style : 'width:500px;',
							buttons : [
									{
										label : '取消',
										action : function(dialog) {
											dialog.close();
										}
									},
									{
										label : '保存',
										cssClass : 'btn-success',
										action : function(dialog) {
											let assignRoleForm = getDialogForm(dialog,'assignRoleForm');
											let roles = assignRoleForm.find("#roleName").val();
											let data = 'userId='+ userId;
											if (roles) {
												for (let i = 0, length = roles.length; i < length; i++) {
													data += '&roles='+ roles[i];
												}
											}
											// 保存角色
											asyncAjax(
												'/user/saveRole',
												'post',
												data,
												function(result) {
													if (result.status == ServerStatus.SUCCESS) {
														toastrInfo(Message.SUCCESS_SAVE);
														dialog.close();
													} else {
														toastrError(result.msg);
													}
												},
												function(res) {
													toastrError(ajaxError(res));
												}
											);
										}
									} ]
						});
			}
		},
		initPassword : function() {
			let row = getEditRow(this.table, '请选择需要初始化密码的用户');
			if (row) {
				showConfirmWarn(
						'确定要初始化密码吗?',
						null,
						function(dialog) {
							asyncAjax(
								'/user/initPassword?userId='
										+ row.id,
								null,
								null,
								function(result) {
									if (result.status == ServerStatus.SUCCESS) {
										toastrInfo('密码初始化成功');
									} else {
										toastrError(result.msg);
									}
								}, function(res) {
									toastrError(ajaxError(res));
								}
						   );
					}
				);
			}
		},
		enableOrDisable : function(userId, status) {
			let data = 'userId=' + userId + '&status=' + status;
			let that = this;
			asyncAjax('/user/status', 'post', data, function(result) {
				if (result.status == ServerStatus.SUCCESS) {
					toastrInfo();
					that.btnSearch();
				} else {
					toastrError(result.msg);
				}
			}, function(res) {
				toastrError(ajaxError(res));
			});
		},
		lockOrUnlock : function(userId, lockStatus) {
			let data = 'userId=' + userId + '&lockStatus=' + lockStatus;
			let that = this;
			asyncAjax('/user/lockStatus', 'post', data,
				function(result) {
					if (result.status == ServerStatus.SUCCESS) {
						toastrInfo();
						that.btnSearch();
					} else {
						toastrError(result.msg);
					}
				}, function(res) {
					toastrError(ajaxError(res));
				}
		    );
		}
	}
});