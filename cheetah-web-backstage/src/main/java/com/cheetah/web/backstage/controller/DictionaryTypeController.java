package com.cheetah.web.backstage.controller;

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
import top.zylsite.cheetah.backstage.service.IDictionaryTypeService;
import top.zylsite.cheetah.backstage.model.DictionaryType;

@RestController
@RequestMapping("/dictionaryType")
public class DictionaryTypeController extends BaseRequestController<DictionaryType> {
	
	@Autowired
	private IDictionaryTypeService dictionaryTypeService;
	
	@Override
	protected BaseService<DictionaryType> getService() {
		return dictionaryTypeService;
	}
	
	@GetMapping("/list")
	public Object list(QueryParameter queryParameter, HttpServletRequest request) {
		return super.list(queryParameter, request);
	}
	
	@GetMapping("/{id}")
	public Object queryById(@PathVariable Integer id) {
	    return super.queryByPrimaryKey(id);
	}
	
	@GetMapping("/remove/{id}")
	public Object remove(@PathVariable Integer id) {
		return super.removeByPrimaryKey(id);
	}
	
	@PostMapping("/save")
	public Object save(DictionaryType entity) {
		if (null == entity.getId()) {
			super.insert(entity);
		} else {
			super.update(entity);
		}
		return this.ajaxSuccess(null);
	}
	
	@GetMapping("/remove")
	public Object remove(Integer[] ids) {
		return super.remove(ids);
	}
	
	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		Example example = new Example(DictionaryType.class);
		return example;
	}
	
}