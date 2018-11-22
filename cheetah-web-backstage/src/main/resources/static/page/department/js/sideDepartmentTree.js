let sideDepartmentTreeComp = new Vue({
	el : '#sideDepartmentTreePanel',
	data : {
		sideDepartmentTreeId : 'sideDepartmentTree',
		sideDepartmentTree : {}
	},
	methods : {
		initSetting : function() {
			this.setting = {
				async : {
					enable : true,
					url : this.getUrl,
					type : 'get',
					dataFilter : this.ajaxDataFilter,
					dataType : 'json'
				},
				check : {
					enable : false
				},
				data : {
					simpleData : {
						enable : true
					}
				},
				view : {
					expandSpeed : ""
				},
				callback : {
					beforeExpand : this.beforeExpand,
					onAsyncSuccess : this.onAsyncSuccess,
					onAsyncError : this.onAsyncError,
					onClick : this.onClickCallBack
				}
			};
		},
		getUrl : function(treeId, treeNode) {
			return contextPath + '/department/queryByParentId?parentId='
					+ (undefined == treeNode ? 0 : treeNode.id);
		},
		beforeExpand : function(treeId, treeNode) {
			if (!treeNode.isAjaxing) {
				startTime = new Date();
				treeNode.times = 1;
				this.ajaxGetNodes(treeNode, "refresh");
				return true;
			} else {
				return false;
			}
		},
		onAsyncSuccess : function(event, treeId, treeNode, msg) {
			if (msg.status != 1 || msg.data.length <= 0) {
				return;
			}
			treeNode.icon = "";
			this.sideDepartmentTree.updateNode(treeNode);
			this.sideDepartmentTree.selectNode(treeNode.children[0]);
		},
		onAsyncError : function(event, treeId, treeNode, XMLHttpRequest,
				textStatus, errorThrown) {
			treeNode.icon = "";
			this.sideDepartmentTree.updateNode(treeNode);
			showError("部门[" + treeNode.name + "]的子部门加载失败");
		},
		ajaxGetNodes : function(treeNode, reloadType) {
			if (reloadType == "refresh") {
				treeNode.icon = contextPath
						+ "/plugins/zTree_v3/css/zTreeStyle/img/loading.gif";
				this.sideDepartmentTree.updateNode(treeNode);
			}
			this.sideDepartmentTree.reAsyncChildNodes(treeNode, reloadType,
					true);
		},
		ajaxDataFilter : function(treeId, parentNode, responseData) {
			if (responseData && responseData.status == 1) {
				return responseData.data;
			}
			return [];
		},
		onClickCallBack : function(event, treeId, treeNode) {
			$('#searchForm').find('#pid').val(treeNode.id);
			app.btnSearch();
		},
		renderDepartmentTree : function() {
			let url = '/department/queryByParentId';
			let that = this;
			asyncAjax(url, null, null, function(result) {
				if (result.status == 1) {
					let data = result.data;
					that.sideDepartmentTree = $.fn.zTree.init($("#"
							+ that.sideDepartmentTreeId), that.setting, data);
				} else {
					showError('部门加载失败');
				}
			}, function(res) {
				showError('服务器异常');
			})
		},
		// 刷新部门树（重新加载）
		refreshDepartmentTree : function() {
			this.sideDepartmentTree.destroy();
			this.renderDepartmentTree();
		}
	},
	mounted : function() {
		this.initSetting();
		this.renderDepartmentTree();
	}
});