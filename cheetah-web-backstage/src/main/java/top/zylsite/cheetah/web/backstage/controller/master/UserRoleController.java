package top.zylsite.cheetah.web.backstage.controller.master;

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
import top.zylsite.cheetah.backstage.service.master.IUserRoleService;
import top.zylsite.cheetah.backstage.model.master.UserRole;

@RestController
@RequestMapping("/userRole")
public class UserRoleController extends BaseRequestController<UserRole> {
	
	@Autowired
	private IUserRoleService userRoleService;
	
	@Override
	protected BaseService<UserRole> getService() {
		return userRoleService;
	}
	
	@GetMapping("/list")
	public Object list(QueryParameter queryParameter, HttpServletRequest request) {
		return super.list(queryParameter, request);
	}
	
	
	@PostMapping("/save")
	public Object save(UserRole entity) {
		return this.ajaxSuccess(null);
	}
	
	@GetMapping("/remove")
	public Object remove(Integer[] ids) {
		return super.remove(ids);
	}
	
	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		Example example = new Example(UserRole.class);
		return example;
	}
	
}