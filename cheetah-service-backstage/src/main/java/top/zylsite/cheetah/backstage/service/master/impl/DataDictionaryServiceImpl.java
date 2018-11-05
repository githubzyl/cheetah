package top.zylsite.cheetah.backstage.service.master.impl;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import top.zylsite.cheetah.backstage.mapper.master.DataDictionaryMapper;
import top.zylsite.cheetah.backstage.mapper.master.extend.DataDictionaryExtendMapper;
import top.zylsite.cheetah.backstage.model.master.DataDictionary;
import top.zylsite.cheetah.backstage.model.vo.DataDictionaryVO;
import top.zylsite.cheetah.backstage.service.master.IDataDictionaryService;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.QueryParameter;

@Service
public class DataDictionaryServiceImpl extends BaseServiceImpl<DataDictionary> implements IDataDictionaryService {
 
	@Autowired
	private DataDictionaryMapper dataDictionaryMapper;
	
	@Autowired
	private DataDictionaryExtendMapper dataDictionaryMapperExtend;

	@Override
	protected BaseMapper<DataDictionary> getBaseMapper() {
		return dataDictionaryMapper;
	}

	@Override
	public DataDictionary queryDictName(String dictCode, String dictEntry) {
		return dataDictionaryMapperExtend.selectByTypeAndEntry(dictCode, dictEntry);
	}

	@Override
	public List<DataDictionary> queryByDictCode(String dictCode) {
		return dataDictionaryMapperExtend.selectByDictTypeCode(dictCode);
	}

	@Override
	public PageInfo<DataDictionaryVO> queryForPage(QueryParameter queryParameter, DataDictionaryVO dataDictionaryVO) {
		PageHelper.startPage(queryParameter.getPageNumber(), queryParameter.getPageSize());
		List<DataDictionaryVO> list = dataDictionaryMapperExtend.selectList(dataDictionaryVO);
		PageInfo<DataDictionaryVO> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

}