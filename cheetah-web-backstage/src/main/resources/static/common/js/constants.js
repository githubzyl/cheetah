/*服务器返回状态码*/
HttpStatus = {
	SUCCESS : 200,
	NOT_FOUND : 400
};
ServerStatus = {
	SUCCESS : 1,
	SESSION_TIMEOUT : 9999
}
/* 通用URI */
CommonURI = {
	LOGIN_URI : '/login'
}

/* 组件 */
Component = {
	TABLE_ID : 'table',
	SEARCH_FORM_ID : 'searchForm'
}

/* 信息 */
Message = {
	/* 提醒 */
	WARN_SELECT_EDIT_ROW_NONE : '请选择需要编辑的行',
	WARN_SELECT_EDIT_ROW_SINGLE : '只能选择单行',
	WARN_SELECT_REMOVE_ROW_NONE : '请选择需要删除的行',
	WARN_SESSION_TIMEOUT : '会话已过期,请重新登录',
	WARN_REMOVE_RECORD : '确定要删除吗?',
	/* 提示 */
	SUCCESS_SAVE : '保存成功',
	SUCCESS_REMOVE : '删除成功',
	/* 错误 */
	ERROR_SYSTEM_EXCEPTION : '服务异常,请联系管理员!'
}