package ${controllerPackage};

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
<#if existIdProperty == true>
import org.springframework.web.bind.annotation.PathVariable;
</#if>
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

<#if existIdProperty == true && idType == 'String'>
import org.apache.commons.lang3.StringUtils;
</#if>
import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.BaseRequestController;
import top.zylsite.cheetah.base.common.QueryParameter;
import ${servicePackage}.I${bigClassName}Service;
import ${modelName};

@RestController
@RequestMapping("/${smallClassName}")
public class ${bigClassName}Controller extends BaseRequestController<${bigClassName}> {
	
	@Autowired
	private I${bigClassName}Service ${smallClassName}Service;
	
	@Override
	protected BaseService<${bigClassName}> getService() {
		return ${smallClassName}Service;
	}
	
	@GetMapping("/list")
	public Object list(QueryParameter queryParameter, HttpServletRequest request) {
		return super.list(queryParameter, request);
	}
	
	<#if existIdProperty == true>
	@GetMapping("/{id}")
	public Object queryById(@PathVariable ${idType} id) {
	    return super.queryByPrimaryKey(id);
	}
	
	@PostMapping("/add")
	public Object add(${bigClassName} entity) {
		return this.save(entity);
	}
	
	@PostMapping("/edit")
	public Object edit(${bigClassName} entity) {
		return this.save(entity);
	}
	
	@GetMapping("/remove/{id}")
	public Object remove(@PathVariable ${idType} id) {
		return super.removeByPrimaryKey(id);
	}
	</#if>
	
	@GetMapping("/remove")
	public Object remove(Integer[] ids) {
		return super.remove(ids);
	}
	
	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		Example example = new Example(${bigClassName}.class);
		return example;
	}
	
	private Object save(${bigClassName} entity) {
	    <#if existIdProperty == true>
	    <#if idType == 'String'>
	    if (StringUtils.isBlank(entity.getId())) {
	    <#else>
		if (null == entity.getId()) {
		</#if>
			super.insert(entity);
		} else {
			super.update(entity);
		}
		</#if>
		return this.ajaxSuccess(null);
	}
	
}