var app = new Vue({
	extends: CompPage,
	data : {
		title : '${modelCNName}',
	     requestRoot : '/${requestRoot}',
	     editPageUrl : "/${modelName}/${modelName}Edit",
	     editFormId : '${modelName}Form'
	},
	methods : {
		setColumns : function(){
			return [];
		},
		beforeRenderEditForm : function(editForm, isEdit) {
			
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
					
				}
			});
		}
	}
});