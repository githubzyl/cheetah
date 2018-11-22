let sidePermissionTreeComp = new Vue({
	el : '#sidePermissionTreePanel',
	data : {
		sidePermissionTreeId : 'sidePermissionTree',
		sidePermissionTree : {}
	},
	methods : {
		initSetting : function() {
			this.setting = {
				async: {
					enable: true,
					url: this.getUrl,
					type:'get',
					dataFilter: this.ajaxDataFilter,
					dataType: 'json'
				},
				check: {
					enable: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				view: {
					expandSpeed: ""
				},
				callback: {
					beforeExpand: this.beforeExpand,
					onAsyncSuccess: this.onAsyncSuccess,
					onAsyncError: this.onAsyncError,
					onClick: this.onClickCallBack
				}
			};
		},
		getUrl : function(treeId, treeNode) {
			return contextPath + '/permission/queryByParentId?parentId='
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
			this.sidePermissionTree.updateNode(treeNode);
			this.sidePermissionTree.selectNode(treeNode.children[0]);
		},
		onAsyncError : function(event, treeId, treeNode, XMLHttpRequest,
				textStatus, errorThrown) {
			treeNode.icon = "";
			this.sidePermissionTree.updateNode(treeNode);
			showError("权限["+treeNode.name+"]的子权限加载失败");
		},
		ajaxGetNodes : function(treeNode, reloadType) {
			if (reloadType == "refresh") {
				treeNode.icon = contextPath
						+ "/plugins/zTree_v3/css/zTreeStyle/img/loading.gif";
				this.sidePermissionTree.updateNode(treeNode);
			}
			this.sidePermissionTree.reAsyncChildNodes(treeNode, reloadType,
					true);
		},
		ajaxDataFilter : function(treeId, parentNode, responseData) {
			if (responseData && responseData.status == 1) {
				return responseData.data;
			}
			return [];
		},
		onClickCallBack : function(event, treeId, treeNode) {
			$('#searchForm').find('#parentId').val(treeNode.id);
			app.btnSearch();
		},
		renderPermissionTree : function() {
			let url = '/permission/queryByParentId';
			let that = this;
			asyncAjax(url, null, null, function(result) {
				if (result.status == 1) {
					let data = result.data;
					that.sidePermissionTree = $.fn.zTree.init($("#"
							+ that.sidePermissionTreeId), that.setting, data);
				} else {
					showError('权限加载失败');
				}
			}, function(res) {
				showError('服务器异常');
			})
		},
		// 刷新权限树（重新加载）
		refreshPermissionTree : function() {
			this.sidePermissionTree.destroy();
			this.renderPermissionTree();
		}
	},
	mounted : function() {
		this.initSetting();
		this.renderPermissionTree();
	}
});