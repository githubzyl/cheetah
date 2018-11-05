let table = $('#'+Component.TABLE_ID), 
     searchForm = $('#'+Component.SEARCH_FORM_ID),
     title = '定时任务',
     requestRoot = '/scheduledJob',
     searchUrl = requestRoot + '/list',
     removeUrl = requestRoot + '/remove',
     saveUrl = requestRoot + '/save',
     editPageUrl = "/job/jobEdit",
     editFormId = 'jobForm',
     formDialogStyle = 'width:500px;';
$(function(){
	resizeTable(table, initTable, table);
});
function initTable(table){
	let tableOption = {
	    url : contextPath + searchUrl,
	    toolbar: '#toolbar',
	    queryParams: queryParams,
		columns : [{
			field : 'checked',
			checkbox : true
		},{
			title : '任务名称',
			field : 'vcJobName',
			align : 'center',
			valign : 'middle',
			width:180
		}, {
			title : '任务组名',
			field : 'vcJobgroupName',
			align : 'center',
			valign : 'middle',
			visible: false
		}, {
			title : '触发器名',
			field : 'vcTriggerName',
			align : 'center',
			valign : 'middle',
			visible: false
		}, {
			title : '触发器组名',
			field : 'vcTriggergroupName',
			align : 'center',
			valign : 'middle',
			visible: false
		}, {
			title : '任务执行类',
			field : 'vcJobclassName',
			align : 'center',
			valign : 'middle'
		}, {
			title : 'cron表达式',
			field : 'vcCronExpression',
			align : 'center',
			valign : 'middle',
			width:140
		}, {
			title : '任务状态',
			field : 'cStatus',
			align : 'center',
			valign : 'middle',
			width:90,
			formatter: function(value, row, index){
				let cls = (value == 0 ? 'icon-yunxingzhong' : 'icon-yitingzhi');
				let color = (value == 0 ? 'green' : 'red');
				let title = (value == 0 ? '运行中' : '已停止');
				let span = '<span title="'+title+'" class="iconfont '+cls+'"  style="color:'+color+'"></span>';
				return span;
			}
		},{
			title : '程序启动时立即执行',
			field : 'cExecOnStartup',
			align : 'center',
			valign : 'middle',
			width:145,
			visible: false,
			formatter: function(value, row, index){
				let text = (value == 0 ? '否' : '是');
				let color = (value == 0 ? 'red' : 'green');
				let span = '<span style="color:'+color+'">'+text+'</span>';
				return span;
			}
		},{
			title : '最新开始执行时间',
			field : 'lLastStartTime',
			align : 'center',
			valign : 'middle',
			width:160,
			formatter: function(value, row, index){
				return longDateFormat(value,'yyyy-MM-dd HH:mm:ss.S');
			}
		},{
			title : '最新执行完成时间',
			field : 'lLastEndTime',
			align : 'center',
			valign : 'middle',
			width:160,
			formatter: function(value, row, index){
				return longDateFormat(value,'yyyy-MM-dd HH:mm:ss.S');
			}
		},{
			title : '最新执行状态',
			field : 'lLastExecStatus',
			align : 'center',
			valign : 'middle',
			width:110,
			formatter: function(value, row, index){
				if(value == null){
					return value;
				}
				let cls = ( value == 0 ? 'icon-zhixingchenggong' : 'icon-zhixingyichang');
				let color = (value == 0 ? 'green' : 'red');
				let title = (value == 0 ? '执行成功' : '执行异常');
				let span = '<span title="'+title+'" class="iconfont '+cls+'"  style="color:'+color+'"></span>';
				return span;
			}
		}]
	};
	renderBootstrapTable(tableOption, table);
}
function queryParams(params) {
	let formParams = getFormData(searchForm);
	return bootstrapTableQueryParams(params,formParams);
}
// 搜索
function btnSearch(){
	bootstrapTableSearch(table);
}
// 重置
function btnClear(){
	clearForm(searchForm,table);
}
// 新增
function addItem(){
	goToEditPage(false, title,null,null);
}
// 编辑
function editItem(){
	let row = getEditRow(table);
	if(row){
		goToEditPage(true, title, row, requestRoot + '/'+row.id);
	}
}
// 删除
function removeItem() {
	removeRows(table, removeUrl, btnSearch);
}
// 跳转到编辑页面
function goToEditPage(isEdit, title, row, searchUrl) {
	openEditDialog(
		isEdit, 
		title, 
		row,
		formDialogStyle,
		editFormId,
		table,
		searchUrl,
		saveUrl,
		editPageUrl,
		null,
		initFormValidator
	);
}
// 初始化验证规则
function initFormValidator(form){
	form.bootstrapValidator({
        message: '输入值不合法',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	vcJobName: {
                message: '任务名称不合法',
                validators: {
                    notEmpty: {
                        message: '任务名称不能为空'
                    },
                    stringLength: {
                        min: 4,
                        max: 45,
                        message: '请输入4到45个字符'
                    }
                }
            },
            vcJobclassName: {
                validators: {
                    notEmpty: {
                        message: '任务执行类不能为空'
                    }, stringLength: {
                        min: 4,
                        max: 200,
                        message: '请输入4到200个字符'
                    }
                }
            }, 
            vcCronExpression: {
                validators: {
                    notEmpty: {
                        message: 'cron表达式不能为空'
                    }
                }
            }
        }
    });
}
//启动任务
function startup(){
	startOrStop(0);
}
//启动所有任务
function startupAll(){
	startOrStopAll(0);
}
//立即执行一次任务
function executeNow(){
	let rows = getSelectedRows();
	if(rows.length <= 0){
		showWarn('请选择需要执行的任务');
		return;
	}
	if(rows.length > 1){
		showWarn('只能选择一个任务执行');
		return;
	}
	let row = rows[0];
	if(row){
		let error = ajaxJson('/job/executeJobNow?id='+row.id, null, null);
		if(error){
    		showError(error);
    	}else{
    		showInfo("任务正在执行中,请稍后刷新数据查看执行情况");
    	}
	}
}
//停止任务
function stop(){
	startOrStop(1);
}
//停止所有任务
function stopAll(){
	startOrStopAll(1);
}
function startOrStop(type){
	let rows = getSelectedRows();
	let warnMsg = '请选择需要' + (type == 0 ? '启动' : '停止') + '的任务' ;
	let infoMsg = '任务已经' + (type == 0 ? '启动' : '停止');
	let url = '/job/' + (type == 0 ? 'startJob' : 'pauseJob');
	if(rows && rows.length > 0){
		let ids = getIds();
		let error = ajaxJson(url, null, ids);
		if(error){
    		showError(error);
    	}else{
    		showInfo(infoMsg);
    		btnSearch();
    	}
	}else{
		showWarn(warnMsg);
	}
}
function startOrStopAll(type){
	let url = '/job/' + (type == 0 ? 'startJob' : 'pauseJob');
	let infoMsg = '任务已经全部' + (type == 0 ? '启动' : '停止');
	let error = ajaxJson(url, null);
	if(error){
		showError(error);
	}else{
		showInfo(infoMsg);
		btnSearch();
	}
}
function getIds(){
	let rows = getSelectedRows();
	let ids = "";
	for(let i = 0, length = rows.length ; i < length ; i++){
		ids += "&id=" + rows[i].id;
	}
	if(ids != ""){
		ids = ids.substring(1);
	}
	return ids;
}