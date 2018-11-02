package top.zylsite.cheetah.web.backstage.controller.master;

import java.util.List;

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
import top.zylsite.cheetah.backstage.service.master.IRoleService;
import top.zylsite.cheetah.backstage.model.master.Role;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseRequestController<Role> {

	@Autowired
	private IRoleService roleService;

	@Override
	protected BaseService<Role> getService() {
		return roleService;
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
	public Object save(Role entity) {
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

	@ControllerLogs(description = "查询可用的角色")
	@GetMapping("/queryEnableRoles")
	public Object queryEnableRoles() {
		Example example = new Example(Role.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("cEnable", "1");
		List<Role> list = roleService.queryList(example);
		return this.ajaxSuccess(list);
	}

	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		Example example = new Example(Role.class);
		return example;
	}

}