package top.zylsite.cheetah.base.common;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * Description: 抽象实现类，继承此类可用部分已实现方法
 * @author jason
 * 2018年10月25日
 * @version 1.0
 */
public abstract class BaseServiceImpl<T> extends BaseParamHandler implements BaseService<T> {

	protected Class<T> clazz;

	public int insertInfo(T entity) {
		return getBaseMapper().insert(entity);
	}

	public int updateInfoByPrimaryKey(T entity, boolean isSelective) {
		return isSelective ? getBaseMapper().updateByPrimaryKeySelective(entity)
				: getBaseMapper().updateByPrimaryKey(entity);
	}

	public int deleteInfoByPrimaryKey(Object key) {
		return getBaseMapper().deleteByPrimaryKey(key);
	}

	@Transactional
	public int deleteInfoByPrimaryKeys(Object[] keys) {
		int count = 0;
		if (null != keys && keys.length > 0) {
			for (Object key : keys) {
				count += getBaseMapper().deleteByPrimaryKey(key);
			}
		}
		return count;
	}

	public int deleteByExample(Example example) {
		return getBaseMapper().deleteByExample(example);
	}

	public T queryInfoByPrimaryKey(Object key) {
		return getBaseMapper().selectByPrimaryKey(key);
	}

	public List<T> queryList(Example example) {
		return getBaseMapper().selectByExample(example);
	}
	
	public int queryCount(Example example) {
		return getBaseMapper().selectCountByExample(example);
	}

	public PageInfo<T> queryForPage(QueryParameter queryParameter, Example example) {
		PageHelper.startPage(queryParameter.getPageNumber(), queryParameter.getPageSize());
		if (queryParameter.isNeedOrder()) {
			if (null == example) {
				example = new Example(getEntityClass());
			}
			example.setOrderByClause(queryParameter.getOrderByClause());
		}
		List<T> list = getBaseMapper().selectByExample(example);
		PageInfo<T> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public PageInfo<T> queryForPage(int pageNumber, int pageSize, Example example) {
		PageHelper.startPage(pageNumber, pageSize);
		List<T> list = getBaseMapper().selectByExample(example);
		PageInfo<T> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getEntityClass() {
		Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		return tClass;
	}

	protected abstract BaseMapper<T> getBaseMapper();

}
