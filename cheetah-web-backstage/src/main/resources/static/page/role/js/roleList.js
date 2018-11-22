var app = new Vue({
	extends: CompPage,
	data : {
		title : '角色',
		requestRoot : '/role',
		editPageUrl : "/role/roleEdit",
		editFormId : 'roleForm',
		operateEvents : {
			'click .EnableOrDisable' : function(e, value, row, index) {
				app.enableOrDisable(row.id, row.cEnable == '1' ? '0' : '1');
			}
		}
	},
	methods : {
		setColumns : function(){
			return [
				{
				field : 'checked',
				checkbox : true
			}, {
				title : '角色编码',
				field : 'vcCode',
				align : 'center',
				valign : 'middle'
			}, {
				title : '角色名称',
				field : 'vcName',
				align : 'center',
				valign : 'middle'
			}, {
				title : '是否启用',
				field : 'cEnable',
				align : 'center',
				valign : 'middle',
				formatter: function(value, row, index){
					let icon = 'iconfont ';
					icon += (value == 1 ? 'icon-enable' : 'icon-disable');
					let color = (value == 1 ? 'green' : 'red');
					let span = '<span class="'+icon+'" style="color:'+color+'"></span>';
					return span;
				}
			},{
				field : 'operate',
				title : '操作',
				align: 'center',
	            valign: 'middle',
	            width: 100,
	            events: this.operateEvents,
				formatter : this.operateFormatter
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
					vcCode: {
		                message: '角色编码不合法',
		                validators: {
		                    notEmpty: {
		                        message: '角色编码不能为空'
		                    },
		                    stringLength: {
		                        min: 2,
		                        max: 20,
		                        message: '请输入2到20个字符'
		                    }
		                }
		            },
		            vcName: {
		                validators: {
		                    notEmpty: {
		                        message: '角色名称不能为空'
		                    }, stringLength: {
		                        min: 3,
		                        max: 45,
		                        message: '请输入3到45个字符'
		                    }
		                }
		            }
				}
			});
		},
		assignPermission : function() {
			let row = getEditRow(this.table, '请选择需要分配权限的角色');
			let treeId = 'assignPermissionTree';
			if(row){
				let url = contextPath + '/page/selection/permissionTree?roleId='+row.id+'&treeId='+treeId+'&chkStyle=checkbox';
				showDialog(url,{
					type: BootstrapDialog.TYPE_SUCCESS,
					title : '分配权限',
					style: 'width:400px;',
					modelBodyStyle: 'max-height:370px;overflow-y:auto;overflow-x:hidden;padding: 0px;',
					onshown: function(dialog){
						 let assignPermissionForm = getDialogForm(dialog, 'assignPermissionForm');
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
			            	let permissions = $.fn.zTree.getZTreeObj(treeId).getCheckedNodes(true);
		                	if(permissions.length == 0){
		                		
		                	}else{
		                		let data = 'roleId='+roleId;
		            			for(let i = 0, length = permissions.length; i < length ; i++){
		            				data += '&permissions=' +permissions[i].id;
		            			}
		                		asyncAjax('/role/savePermission', 'post', data,
		            			    function(result){
		            			    	if(result.status == ServerStatus.SUCCESS){
		            		    			toastrInfo('权限分配成功');
		            		    			dialog.close();
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
			        }]
				});
			}
		},
		enableOrDisable : function(roleId, status) {
			let message = status == 0 ? '禁用' : '启用';
			let data = 'roleId='+roleId+'&status='+status;
			let that = this;
			asyncAjax('/role/status', 'post', data,
			    function(result){
			    	if(result.status == ServerStatus.SUCCESS){
		    			toastrInfo('角色'+message+'成功');
		    			that.btnSearch();
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