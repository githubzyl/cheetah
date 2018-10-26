package top.zylsite.cheetah.backstage.service.master.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.master.IDictionaryTypeService;
import top.zylsite.cheetah.backstage.mapper.master.DictionaryTypeMapper;
import top.zylsite.cheetah.backstage.model.master.DictionaryType;

@Service
public class DictionaryTypeServiceImpl extends BaseServiceImpl<DictionaryType> implements IDictionaryTypeService {
 
	@Autowired
	private DictionaryTypeMapper dictionaryTypeMapper;

	@Override
	protected BaseMapper<DictionaryType> getBaseMapper() {
		return dictionaryTypeMapper;
	}
	
}