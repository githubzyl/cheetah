var app = new Vue({
	extends: CompPage,
	data : {
		title : '字典类型',
	     requestRoot : '/dictionaryType',
	     editPageUrl : "/dictionary/typeEdit",
	     editFormId : 'dictionaryTypeForm',
	     operateEvents : {
    	     'click .LookDictDetail': function (e, value, row, index) {
    	          app.lookDictDetail(row);
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
					title : '类型编码',
					field : 'vcCode',
					align : 'center',
					valign : 'middle',
					width: 300
				}, {
					title : '类型名称',
					field : 'vcName',
					align : 'center',
					valign : 'middle',
					width: 300
				}, {
					title : '类型描述',
					field : 'vcDescription',
					align : 'center',
					valign : 'middle'
				},{
					field : 'operate',
					title : '操作',
					align: 'center',
		            valign: 'middle',
		            width: 150,
		            events: this.operateEvents,
					formatter : this.operateFormatter,
					visible: false
				}
			];
		},
		operateFormatter : function(value, row, index) {
		    return [
		    	 '<button type="button" class="LookDictDetail btn btn-success btn-sm">查看数据字典</button>'
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
		                message: '类型编码不合法',
		                validators: {
		                    notEmpty: {
		                        message: '类型名称不能为空'
		                    },
		                    stringLength: {
		                        min: 2,
		                        max: 10,
		                        message: '请输入3到10个字符'
		                    }
		                }
		            },
		            vcName: {
		                validators: {
		                    notEmpty: {
		                        message: '类型名称不能为空'
		                    }, stringLength: {
		                        min: 1,
		                        max: 25,
		                        message: '请输入1到25个字符'
		                    }
		                }
		            }
				}
			});
		},
		lookDictDetail:function (row){
			let url = contextPath + '/page/dictionary/typeDataDetail?lDictType='+row.id;
			showDialog(url, {
				type: BootstrapDialog.TYPE_SUCCESS,
				title : '数据字典详情',
				style: 'width:800px;',
				onshown: function(dialog){
					
				},
				buttons: [{
		            label: '关闭',
		            cssClass: 'btn-default',
		            action: function(dialog) {
		                dialog.close();
		            }
		        }]
			});
		}
	}
});