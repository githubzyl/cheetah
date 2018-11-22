var app = new Vue({
	extends: CompPage,
	data : {
		title : '角色',
	     requestRoot : '/dataDictionary',
	     editPageUrl : "/dictionary/dataEdit",
	     editFormId : 'dataDictionaryForm'
	},
	methods : {
		setColumns : function(){
			return [
				{
					field : 'checked',
					checkbox : true
				}, {
					title : '字典类型',
					field : 'lDictType',
					align : 'center',
					valign : 'middle',
					width: 300,
					formatter:function(val,row){
						return val + '('+row.dictTypeName+')';
					}
				}, {
					title : '字典编码',
					field : 'cDictEntry',
					align : 'center',
					valign : 'middle',
					width: 300
				}, {
					title : '字典名称',
					field : 'vcEntryName',
					align : 'center',
					valign : 'middle',
					width: 300
				}, {
					title : '排序',
					field : 'lSort',
					align : 'center',
					valign : 'middle'
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
			asyncAjax('/dictionaryType/all',null,null,
				    function(result){
				    	if(result.status == ServerStatus.SUCCESS){
			    			setBaseSelectOptions(editForm.find('#lDictType'), result.data, 'id', 'vcCode','vcName');
			    			setEditFormData(isEdit, searchUrl, editForm);
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
			this.setTypeSelection($('#lDictType'));
		},
		goToEditPage : function(isEdit, title, row, searchUrl) {
			let saveUrl = isEdit ? this.editUrl : this.addUrl;
			openEditDialog(isEdit, title, row, this.formDialogStyle,
					this.editFormId, this.table, searchUrl, saveUrl,
					this.editPageUrl, this.beforeRenderEditForm, this.initFormValidator,true);
		},
		setTypeSelection : function (selectEle){
			asyncAjax('/dictionaryType/all',null,null,
			    function(result){
			    	if(result.status == ServerStatus.SUCCESS){
		    			setBaseSelectOptions(selectEle, result.data, 'id', 'vcCode','vcName');
					}else{
						toastrError(result.msg);
					}
			    },
			    function(res){
			    	toastrError(ajaxError(res));
			    }
			);
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
					lDictType: {
		                message: '字典类型不合法',
		                validators: {
		                    notEmpty: {
		                        message: '字典类型不能为空'
		                    }
		                }
		            },
		            cDictEntry: {
		            	message: '类型编码不合法',
		            	validators: {
		            		notEmpty: {
		            			message: '字典编码不能为空'
		            		},
		            		stringLength: {
		            			min: 1,
		            			max: 5,
		            			message: '请输入1到5个字符'
		            		}
		            	}
		            },
		            vcEntryName: {
		                validators: {
		                    notEmpty: {
		                        message: '字典名称不能为空'
		                    }, stringLength: {
		                        min: 1,
		                        max: 45,
		                        message: '请输入1到45个字符'
		                    }
		                }
		            }
				}
			});
		},
	}
});