var CompPage = {
	el : '#app',
	data : {
		table : '',
		searchForm : '',
		title : '',
		requestRoot : '',
		searchUrl : '/list',
		removeUrl : '/remove',
		addUrl : '/add',
		editUrl : '/edit',
		editPageUrl : "",
		editFormId : '',
		formDialogStyle : 'width:500px;'
	},
	methods : {
		//初始化部分参数
		initPreParam : function() {
			this.table = $('#' + Component.TABLE_ID);
			this.searchForm = $('#' + Component.SEARCH_FORM_ID);
			this.searchUrl = this.requestRoot + this.searchUrl;
			this.removeUrl = this.requestRoot + this.removeUrl;
			this.addUrl = this.requestRoot + this.addUrl;
			this.editUrl = this.requestRoot + this.editUrl;
		},
		//初始化数据表格
		initTable : function(table) {
			let tableOption = {
				url : this.searchUrl,
				toolbar : '#toolbar',
				queryParams : this.queryParams,
				columns : this.setColumns()
			};
			renderBootstrapTable(tableOption, table);
		},
		//设置表格列,需要返回数组
		setColumns : function() {

		},
		//设置查询参数
		queryParams : function(params) {
			let formParams = getFormData(this.searchForm);
			return bootstrapTableQueryParams(params, formParams);
		},
		//查询
		btnSearch : function() {
			bootstrapTableSearch(this.table);
		},
		//重置
		btnClear : function() {
			clearForm(this.searchForm, this.table);
		},
		//新增
		addItem : function() {
			this.goToEditPage(false, this.title, null, null);
		},
		//编辑
		editItem : function() {
			let row = getEditRow(this.table);
			if (row) {
				this.goToEditPage(true, this.title, row, this.requestRoot + '/'
						+ row.id);
			}
		},
		//删除
		removeItem : function() {
			removeRows(this.table, this.removeUrl, this.btnSearch);
		},
		//跳转到编辑页面
		goToEditPage : function(isEdit, title, row, searchUrl) {
			let saveUrl = isEdit ? this.editUrl : this.addUrl;
			openEditDialog(isEdit, title, row, this.formDialogStyle,
					this.editFormId, this.table, searchUrl, saveUrl,
					this.editPageUrl, this.beforeRenderEditForm, this.initFormValidator);
		},
		//渲染编辑的表单之前触发
		beforeRenderEditForm : function(editForm, isEdit,searchUrl) {

		},
		//初始化表单验证
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
		},
		//数据表格渲染之前出发
		beforeRenderTable : function() {

		},
		//数据表格渲染之后出发
		afterRenderTable : function() {

		}
	},
	mounted : function() {
		this.initPreParam();
		this.beforeRenderTable();
		resizeTable(this.table, this.initTable, this.table);
		this.afterRenderTable();
	}
};