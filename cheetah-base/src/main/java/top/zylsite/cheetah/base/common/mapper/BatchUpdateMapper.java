package top.zylsite.cheetah.base.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.UpdateProvider;

public interface BatchUpdateMapper<T> {

	@UpdateProvider(type=BatchUpdateProvider.class, method="dynamicSQL")
	int batchUpdate(List<T> list);
	
}
