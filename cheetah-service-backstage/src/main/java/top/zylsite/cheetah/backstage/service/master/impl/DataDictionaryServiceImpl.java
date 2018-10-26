package top.zylsite.cheetah.backstage.service.master.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.master.IDataDictionaryService;
import top.zylsite.cheetah.backstage.mapper.master.DataDictionaryMapper;
import top.zylsite.cheetah.backstage.model.master.DataDictionary;

@Service
public class DataDictionaryServiceImpl extends BaseServiceImpl<DataDictionary> implements IDataDictionaryService {
 
	@Autowired
	private DataDictionaryMapper dataDictionaryMapper;

	@Override
	protected BaseMapper<DataDictionary> getBaseMapper() {
		return dataDictionaryMapper;
	}
	
}