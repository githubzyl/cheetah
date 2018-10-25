package top.zylsite.cheetah.backstage.service.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.IDataDictionaryService;
import top.zylsite.cheetah.backstage.mapper.DataDictionaryMapper;
import top.zylsite.cheetah.backstage.model.DataDictionary;

@Service
public class DataDictionaryServiceImpl extends BaseServiceImpl<DataDictionary> implements IDataDictionaryService {
 
	@Autowired
	private DataDictionaryMapper dataDictionaryMapper;

	@Override
	protected BaseMapper<DataDictionary> getBaseMapper() {
		return dataDictionaryMapper;
	}
	
}