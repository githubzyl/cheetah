var app = new Vue({
	extends: CompPage,
	data : {
		title : '部门',
		requestRoot : '/department',
		editPageUrl : "/department/departmentEdit",
		editFormId : 'departmentForm',
		operateEvents : {
			'click .EnableOrDisable' : function(e, value, row, index) {
				app.enableOrDisable(row.id, row.cEnable == '1' ? '0': '1');
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
					title : '部门编码',
					field : 'vcCode',
					align : 'center',
					valign : 'middle',
					width: 300
				}, {
					title : '部门名称',
					field : 'vcName',
					align : 'center',
					valign : 'middle',
					width: 300
				}, {
					title : '是否启用',
					field : 'cEnable',
					align : 'center',
					valign : 'middle',
					width: 80,
					formatter: function(value, row, index){
						let icon = 'iconfont ';
						icon += (value == 1 ? 'icon-enable' : 'icon-disable');
						let color = (value == 1 ? 'green' : 'red');
						let span = '<span class="'+icon+'" style="color:'+color+'"></span>';
						return span;
					}
				}, {
					title : '排序',
					field : 'lSort',
					align : 'center',
					valign : 'middle',
					width: 80
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
			let className = (row.cEnable == 0 ? 'btn-success': 'btn-danger');
			return '<button type="button" class="EnableOrDisable btn '+ className+ ' btn-sm">'+ btnName+ '</button>';
		},
		queryParams : function(params) {
			let formParams = getFormData(this.searchForm);
			return bootstrapTableQueryParams(params, formParams);
		},
		//重置
		btnClear : function() {
			clearForm(this.searchForm, this.table,function(){
				$('#pid').val('');
			});
		},
		initFormValidator : function(form) {
			form.bootstrapValidator({
		        message: '输入值不合法',
		        feedbackIcons: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	pName : {
						message : '父级部门不合法',
						validators : {
							notEmpty : {
								message : '父级部门不能为空'
							}
						}
					},
					vcCode : {
						validators : {
							notEmpty : {
								message : '部门编码不能为空'
							},
							stringLength : {
								min : 2,
								max : 45,
								message : '请输入2到45个字符'
							}
						}
					},
					vcName : {
						validators : {
							notEmpty : {
								message : '部门名称不能为空'
							},
							stringLength : {
								min : 3,
								max : 85,
								message : '请输入3到85个字符'
							}
						}
					}
		        }
		    });
		},
		enableOrDisable : function(departmentId, status) {
			let message = status == 0 ? '禁用' : '启用';
			let data = 'departmentId='+departmentId+'&status='+status;
			let that = this;
			asyncAjax('/department/status', 'post', data,
			    function(result){
			    	if(result.status == ServerStatus.SUCCESS){
		    			toastrInfo('部门'+message+'成功');
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