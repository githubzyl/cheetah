package top.zylsite.cheetah.web.backstage.controller.master;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.BaseRequestController;
import top.zylsite.cheetah.base.common.QueryParameter;
import top.zylsite.cheetah.web.backstage.common.annotation.ControllerLogs;
import top.zylsite.cheetah.backstage.service.master.IDictionaryTypeService;
import top.zylsite.cheetah.backstage.model.master.DictionaryType;

@RestController
@RequestMapping("/dictionaryType")
public class DictionaryTypeController extends BaseRequestController<DictionaryType> {
	
	@Autowired
	private IDictionaryTypeService dictionaryTypeService;
	
	@Override
	protected BaseService<DictionaryType> getService() {
		return dictionaryTypeService;
	}
	
	@ControllerLogs(description="查询字典类型列表")
	@GetMapping("/list")
	public Object list(QueryParameter queryParameter, HttpServletRequest request) {
		return super.list(queryParameter, request);
	}
	
	@GetMapping("/{id}")
	public Object queryById(@PathVariable Integer id) {
	    return super.queryByPrimaryKey(id);
	}
	
	@ControllerLogs(description="删除单个字典类型")
	@GetMapping("/remove/{id}")
	public Object remove(@PathVariable Integer id) {
		return super.removeByPrimaryKey(id);
	}
	
	@ControllerLogs(description="保存字典类型")
	@PostMapping("/save")
	public Object save(DictionaryType entity) {
		if (null == entity.getId()) {
			super.insert(entity);
		} else {
			super.update(entity);
		}
		return this.ajaxSuccess(null);
	}
	
	@ControllerLogs(description="批量删除字典类型")
	@GetMapping("/remove")
	public Object remove(Integer[] ids) {
		return super.remove(ids);
	}
	
	@GetMapping("/all")
	public Object all(HttpServletRequest request) {
		return super.all();
	}
	
	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		String vcCode = request.getParameter("vcCode");
		String vcName = request.getParameter("vcName");
		Example example = new Example(DictionaryType.class);
		Example.Criteria criteria = example.createCriteria();
		super.andFullLike(criteria, "vcCode", vcCode);
		super.andFullLike(criteria, "vcName", vcName);
		return example;
	}
	
}