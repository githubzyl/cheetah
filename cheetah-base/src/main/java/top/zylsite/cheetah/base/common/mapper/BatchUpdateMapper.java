package top.zylsite.cheetah.base.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.UpdateProvider;

/**
 * Description: 批量更新接口
 * @author jason
 * 2018年10月26日
 * @version 1.0
 */
public interface BatchUpdateMapper<T> {

	@UpdateProvider(type=BatchUpdateProvider.class, method="dynamicSQL")
	int batchUpdate(List<T> list);
	
}
