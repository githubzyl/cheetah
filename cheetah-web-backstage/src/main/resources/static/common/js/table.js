//渲染bootstrapTable
function renderBootstrapTable(options, table) {
	let defaultOpt = {
		striped : true,
		cache : false,
		sortable : true,
		pagination : true,
		pageNumber : 1,
		pageSize : 10,
		queryParamsType : '',
		totalField : 'total',
		dataField : 'list',
		paginationLoop : false,
		pageList : [ 10, 15, 25, 50, 100 ],
		paginationPreText : '上一页',
		paginationNextText : '下一页',
		paginationFirstText : '首页',
		paginationLastText : '末页',
		smartDisplay : true,
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		singleSelect : false,
		locale : 'zh-CN',
		fixedCellWidth : true,// 设置列宽固定
		cellLineBreak : true,// 设置cell内内容换行
		showToggle : true,
		showColumns : true,
		showRefresh : true,
		showFullscreen : false,
		showExport : false,
		responseHandler : function(result) {
			return getServerData(result);
		},
		onLoadError : function(status, res) {

		}
	};
	let opt = $.extend(defaultOpt, options);
	table.bootstrapTable(opt);
}
//bootstrap-table分页查询参数获取
function bootstrapTableQueryParams(params, formParams, otherParams){
	if(formParams){
		params = $.extend(params, formParams);
	}
	if(otherParams){
		params = $.extend(params, otherParams);
	}
	return params;
}
//执行搜索的方法
function bootstrapTableSearch(table){
	let options = table.bootstrapTable('getOptions');
	let params = options.queryParams();
	if(undefined == params || null == params){
		params = {};
	}
	params.pageNumber = 1;
	table.bootstrapTable('removeAll');
	table.bootstrapTable('refresh',params);
}
//获取需要编辑的行(单行)
function getEditRow(table){
	let rows = getSelectedRows(table);
	if(rows.length <= 0){
		showWarn(Message.WARN_SELECT_EDIT_ROW_NONE);
		return;
	}
	if(rows.length > 1){
		showWarn(Message.WARN_SELECT_EDIT_ROW_SINGLE);
		return;
	}
	return rows[0];
}
//获取需要删除的行(单行或多行)
function getRemoveRows(table){
	let rows = getSelectedRows(table);
	if(rows.length <= 0){
		showWarn(Message.WARN_SELECT_REMOVE_ROW_NONE);
		return;
	}
	return rows;
}
//获取选择的行
function getSelectedRows(table){
	return table.bootstrapTable('getSelections');
}
//删除记录
function removeRows(table, removeUrl, successCallback){
	let rows = getRemoveRows(table);
	if(rows && rows.length > 0){
		showConfirmWarn(Message.WARN_REMOVE_RECORD, null, function(dialog){
			let id = "";
			for(let i = 0, length = rows.length ; i < length ; i++){
				id += "&ids=" + rows[i].id;
			}
			if(id != ""){
				id = id.substring(1);
				// 请求后台删除数据
				asyncAjax(removeUrl,null,id,
			    	function(result){
			    		dialog.close();
			    		if(result.status == ServerStatus.SUCCESS){
			    			toastrInfo(Message.SUCCESS_REMOVE);
			    			successCallback();
						}else{
		            		showError(error);
						}
			    	},
			    	function(res){
			    		dialog.close();
	            		showError(ajaxError(res));
			    	}
		    	);
			}
		});
	}
}
//渲染列表并且自适应大小
function resizeTable(table, initTable, initParam){
	if(undefined != initParam && null != initParam){
		initTable(initParam);
	}else{
		initTable();
	}
	changeTableHeight(table);
	$(window).resize(function() {
		changeTableHeight(table);
	});
}
//改变table的高度
function changeTableHeight(table){
	var mainPanelHeight = $('.main-panel').height();
	var mainPanelHeadHeight = $('.main-panel-head').height() + 21;
	var searchContainerHeight = $('.search-condition-container').height();
	var mainPanelBodyHeight = mainPanelHeight - mainPanelHeadHeight;
	var tableHeight = mainPanelBodyHeight - searchContainerHeight + 8;
	table.bootstrapTable("resetView",{
		height: tableHeight
	});
}