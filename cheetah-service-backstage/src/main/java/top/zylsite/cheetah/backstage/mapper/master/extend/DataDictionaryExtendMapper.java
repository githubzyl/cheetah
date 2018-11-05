package top.zylsite.cheetah.backstage.mapper.master.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import top.zylsite.cheetah.backstage.model.master.DataDictionary;
import top.zylsite.cheetah.backstage.model.vo.DataDictionaryVO;

public interface DataDictionaryExtendMapper {

	/**
	 * 根据字典类型编码查询对应的字典值列表
	 * 
	 * @param dictTypeCode
	 * @return
	 * @create: 2018年4月8日 上午9:30:31 zhaoyl
	 * @history:
	 */
	@Select("select d.* from t_data_dict d inner join t_dict_type t on d.l_dict_type = t.id where t.vc_code = #{dictTypeCode} order by d.l_sort asc")
	List<DataDictionary> selectByDictTypeCode(@Param("dictTypeCode") String dictTypeCode);

	/**
	 * 根据字典类型编码和字典值编码查询对应的字典值
	 * 
	 * @param dictTypeCode
	 * @param dictEntry
	 * @return
	 * @create: 2018年4月8日 上午9:33:58 zhaoyl
	 * @history:
	 */
	@Select("select d.* from t_data_dict d inner join t_dict_type t on d.l_dict_type = t.id where t.vc_code = #{dictTypeCode} and d.c_dict_entry = #{dictEntry} limit 1")
	DataDictionary selectByTypeAndEntry(@Param("dictTypeCode") String dictTypeCode,
			@Param("dictEntry") String dictEntry);
	
	List<DataDictionaryVO> selectList(DataDictionaryVO dataDictionaryVO);

}