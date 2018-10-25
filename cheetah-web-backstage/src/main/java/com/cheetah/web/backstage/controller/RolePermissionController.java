package com.cheetah.web.backstage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.BaseRequestController;
import top.zylsite.cheetah.base.common.QueryParameter;
import top.zylsite.cheetah.backstage.service.IRolePermissionService;
import top.zylsite.cheetah.backstage.model.RolePermission;

@RestController
@RequestMapping("/rolePermission")
public class RolePermissionController extends BaseRequestController<RolePermission> {
	
	@Autowired
	private IRolePermissionService rolePermissionService;
	
	@Override
	protected BaseService<RolePermission> getService() {
		return rolePermissionService;
	}
	
	@GetMapping("/list")
	public Object list(QueryParameter queryParameter, HttpServletRequest request) {
		return super.list(queryParameter, request);
	}
	
	
	@PostMapping("/save")
	public Object save(RolePermission entity) {
		return this.ajaxSuccess(null);
	}
	
	@GetMapping("/remove")
	public Object remove(Integer[] ids) {
		return super.remove(ids);
	}
	
	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		Example example = new Example(RolePermission.class);
		return example;
	}
	
}