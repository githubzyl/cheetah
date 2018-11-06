package top.zylsite.cheetah.web.backstage.controller.master;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.backstage.model.master.DataDictionary;
import top.zylsite.cheetah.backstage.model.vo.DataDictionaryVO;
import top.zylsite.cheetah.backstage.service.master.IDataDictionaryService;
import top.zylsite.cheetah.base.common.BaseRequestController;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.QueryParameter;
import top.zylsite.cheetah.web.backstage.common.annotation.ControllerLogs;

@RestController
@RequestMapping("/dataDictionary")
public class DataDictionaryController extends BaseRequestController<DataDictionary> {
	
	@Autowired
	private IDataDictionaryService dataDictionaryService;
	
	@Override
	protected BaseService<DataDictionary> getService() {
		return dataDictionaryService;
	}
	
	@ControllerLogs(description="查询数据字典列表")
	@GetMapping("/list")
	public Object list(QueryParameter queryParameter, DataDictionaryVO dataDictionaryVO, HttpServletRequest request) {
		PageInfo<DataDictionaryVO> pageInfo = dataDictionaryService.queryForPage(queryParameter, dataDictionaryVO);
		return this.ajaxSuccess(pageInfo);
	}
	
	@GetMapping("/{id}")
	public Object queryById(@PathVariable Integer id) {
	    return super.queryByPrimaryKey(id);
	}
	
	@ControllerLogs(description="删除单个数据字典")
	@GetMapping("/remove/{id}")
	public Object remove(@PathVariable Integer id) {
		return super.removeByPrimaryKey(id);
	}
	
	@ControllerLogs(description="保存数据字典")
	@PostMapping("/save")
	public Object save(DataDictionary entity) {
		if (null == entity.getId()) {
			super.insert(entity);
		} else {
			super.update(entity);
		}
		return this.ajaxSuccess(null);
	}
	
	@ControllerLogs(description="批量数据字典")
	@GetMapping("/remove")
	public Object remove(Integer[] ids) {
		return super.remove(ids);
	}
	
	@GetMapping("/list/{dictCode}")
	public Object queryByDictCode(@PathVariable String dictCode) {
		return this.ajaxSuccess(dataDictionaryService.queryByDictCode(dictCode));
	}
	
	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		String lDictType = request.getParameter("lDictType");
		String cDictEntry = request.getParameter("cDictEntry");
		String vcEntryName = request.getParameter("vcEntryName");
		Example example = new Example(DataDictionary.class);
		Example.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(lDictType)) {
			criteria.andEqualTo("lDictType", lDictType);
		}
		super.andFullLike(criteria, "cDictEntry", cDictEntry);
		super.andFullLike(criteria, "vcEntryName", vcEntryName);
		return example;
	}
	
}