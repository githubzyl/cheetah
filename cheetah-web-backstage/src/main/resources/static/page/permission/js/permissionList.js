var app = new Vue({
	extends: CompPage,
	data : {
		title : '角色',
		requestRoot : '/permission',
		editPageUrl : "/permission/permissionEdit",
		editFormId : 'permissionForm',
		resourceType: [],
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
				title : '权限编码',
				field : 'vcCode',
				align : 'center',
				valign : 'middle'
			},{
				title : '权限名称',
				field : 'vcName',
				align : 'center',
				valign : 'middle',
				width: 130
			},{
				title : '权限URL',
				field : 'vcUrl',
				align : 'center',
				valign : 'middle'
			},{
				title : '资源类型',
				field : 'cResourceType',
				align : 'center',
				valign : 'middle',
				width: 100,
				formatter: function(value,row,index){
					if(value == 0){
						return '<span class="badge badge-primary">目录</span>';
					}else if(value == 1){
						return '<span class="badge badge-success">菜单</span>';
					}
					return '<span class="badge badge-warning">按钮</span>';
				}
			},{
				title : '排序',
				field : 'lSort',
				align : 'center',
				valign : 'middle',
				width: 100
			}, {
				title : '是否启用',
				field : 'cEnable',
				align : 'center',
				valign : 'middle',
				width: 100,
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
			let btnName = (row.cEnable == '0' ? '启用' : '禁用');
			let className = (row.cEnable == '0' ? 'btn-success' : 'btn-danger');
		    return [
		        '<button type="button" class="EnableOrDisable btn '+className+' btn-sm">'+btnName +'</button>'
		    ].join('');
		},
		//重置
		btnClear : function() {
			clearForm(this.searchForm, this.table,function(){
				$('#parentId').val('');
			});
		},
		beforeRenderEditForm : function(editForm, isEdit,searchUrl) {
    		let resourceTypeDiv = editForm.find('#resourceTypeDiv');
    		let openWayDiv = editForm.find('#openWay');
    		if(isBlankArray(this.resourceType)){
    			this.loadResourceType();
    		}
    		setRadioValues(resourceTypeDiv,'cResourceType', this.resourceType);
    		setYesOrNoRadioValues(openWayDiv.find('#openWayDiv'),'cTargetBlank');
    		editForm.find('input[name=cResourceType]').change(function() {
				let val = $(this).val();
				if ('1' == val) {
					openWayDiv.show();
				} else {
					openWayDiv.hide();
				}
			});
    		if(isEdit){
    			setEditFormData(isEdit, searchUrl, editForm,function(editForm, data){
    				if('1' == data.cResourceType){
    					openWayDiv.show();
    				}
    			});
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
					parentName: {
		                message: '父级权限不合法',
		                validators: {
		                    notEmpty: {
		                        message: '请选择父级权限'
		                    }
		                }
		            },
		        	vcName: {
		                message: '权限名称不合法',
		                validators: {
		                    notEmpty: {
		                        message: '权限名称不能为空'
		                    },
		                    stringLength: {
		                        min: 2,
		                        max: 20,
		                        message: '请输入2到20个字符'
		                    }
		                }
		            }
				}
			});
		},
		enableOrDisable: function(permissionId, status){
			let message = (status == '1' ? '禁用' : '启用');
			let data = 'permissionId='+permissionId+'&status='+status;
			let that = this;
			asyncAjax('/permission/status', 'post', data,
			    function(result){
			    	if(result.status == ServerStatus.SUCCESS){
		    			toastrInfo('权限'+message+'成功');
		    			that.btnSearch();
					}else{
						toastrError(result.msg);
					}
			    },
			    function(res){
			    	toastrError(ajaxError(res));
			    }
			);
		},
		beforeRenderTable: function(){
			this.loadResourceType();
		},
		loadResourceType: function(){
			let that = this;
			asyncAjax('/enum/resourceType',null,null,
			    function(result){
			    	if(result.status == ServerStatus.SUCCESS){
			    		that.resourceType = result.data;
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