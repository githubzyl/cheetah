package top.zylsite.cheetah.base.common;

import java.util.List;

import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * Description: service继承此接口之后可调用部分已实现方法
 * @author jason
 * 2018年10月25日
 * @version 1.0
 */
public interface BaseService<T> {
	
	/**
	 * 新增对象信息,返回自增主键，主键名必须是“id”,而且是整型，并且设置了自增
	 * 
	 * @param entity
	 */
	public int insertInfoAndGetId(T entity);

	/**
	 * 插入集合(mysql专用)
	 * 
	 * @param list
	 */
	public int insertList(List<T> list);

	/**
	 * 新增对象信息
	 * 
	 * @param entity
	 */
	public int insertInfo(T entity);

	/**
	 * 根据主键修改对象信息
	 * 
	 * @param entity
	 * @param isSelective
	 *            设置为true，表示只更新不为空的属性，设置为false，表示全部更新
	 * @return
	 */
	public int updateInfoByPrimaryKey(T entity, boolean isSelective);

	/**
	 * 根据主键删除对象信息
	 * 
	 * @param key
	 * @return
	 */
	public int deleteInfoByPrimaryKey(Object key);

	/**
	 * 根据主键删除对象信息
	 * 
	 * @param key
	 * @return
	 */
	public int deleteInfoByPrimaryKeys(Object[] keys);

	/**
	 * 根据条件删除信息
	 * 
	 * @param example
	 * @return
	 */
	public int deleteByExample(Example example);

	/**
	 * 根据主键查询对象信息
	 * 
	 * @param key
	 * @return
	 */
	public T queryInfoByPrimaryKey(Object key);

	/**
	 * 查询不分页
	 * 
	 * @param example
	 * @return
	 */
	public List<T> queryList(Example example);
	
	/**
	 * 查询数量
	 * @param example
	 * @return
	 */
	public int queryCount(Example example);

	/**
	 * 查询分页（带有特殊参数及排序）
	 * @param queryParameter
	 * @param example
	 * @return
	 */
	public PageInfo<T> queryForPage(QueryParameter queryParameter, Example example);
	
	/**
	 * 查询分页
	 * @param pageNumber
	 * @param pageSize
	 * @param example
	 * @return
	 */
	public PageInfo<T> queryForPage(int pageNumber, int pageSize, Example example);

}
