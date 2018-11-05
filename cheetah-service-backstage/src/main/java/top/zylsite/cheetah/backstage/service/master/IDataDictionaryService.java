package top.zylsite.cheetah.backstage.service.master;

import java.util.List;

import com.github.pagehelper.PageInfo;

import top.zylsite.cheetah.backstage.model.master.DataDictionary;
import top.zylsite.cheetah.backstage.model.vo.DataDictionaryVO;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.QueryParameter;

public interface IDataDictionaryService extends BaseService<DataDictionary> {

	/**
	 * 根据字典类型编码查找对应的所有字典值
	 * 
	 * @param dictCode
	 * @return
	 * @create: 2018年3月14日 下午1:50:29 zhaoyl
	 * @history:
	 */
	public List<DataDictionary> queryByDictCode(String dictCode);

	/**
	 * 根据字典类型编码和字典字典值查找对应的记录
	 * 
	 * @param dictCode
	 * @param dictEntry
	 * @return
	 * @create: 2018年3月23日 上午11:02:06 zhaoyl
	 * @history:
	 */
	public DataDictionary queryDictName(String dictCode, String dictEntry);

	public PageInfo<DataDictionaryVO> queryForPage(QueryParameter queryParameter, DataDictionaryVO dataDictionaryVO);

}