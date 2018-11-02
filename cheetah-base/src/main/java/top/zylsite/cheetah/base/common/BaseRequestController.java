package top.zylsite.cheetah.base.common;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

public abstract class BaseRequestController<T> extends BaseController {

	public PageInfo<T> getPageInfo(QueryParameter queryParameter, HttpServletRequest request) {
		return getService().queryForPage(queryParameter, getExample(queryParameter, request));
	}

	public Object list(QueryParameter queryParameter, HttpServletRequest request) {
		PageInfo<T> pageInfo = getPageInfo(queryParameter, request);
		return this.ajaxSuccess(pageInfo);
	}

	public Object list(Example example) {
		return this.ajaxSuccess(getService().queryList(example));
	}

	public Object all() {
		return list(null);
	}

	public Object remove(Object[] keys) {
		getService().deleteInfoByPrimaryKeys(keys);
		return this.ajaxSuccess(null);
	}
	
	public Object removeByPrimaryKey(Object key) {
		getService().deleteInfoByPrimaryKey(key);
		return this.ajaxSuccess(null);
	}

	public Object queryByPrimaryKey(Object key) {
		return this.ajaxSuccess(getService().queryInfoByPrimaryKey(key));
	}

	public void insert(T entity) {
		getService().insertInfo(entity);
	}

	public void update(T entity) {
		getService().updateInfoByPrimaryKey(entity, true);
	}
	
	protected abstract BaseService<T> getService();

	protected abstract Example getExample(QueryParameter queryParameter, HttpServletRequest request);

}
