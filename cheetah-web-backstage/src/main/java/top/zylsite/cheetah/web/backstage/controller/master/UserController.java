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
import top.zylsite.cheetah.backstage.model.master.User;
import top.zylsite.cheetah.backstage.service.master.IUserService;
import top.zylsite.cheetah.base.common.BaseRequestController;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.QueryParameter;
import top.zylsite.cheetah.base.common.tree.BaseTree;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroUtil;

@RestController
@RequestMapping("/user")
public class UserController extends BaseRequestController<User> {
	
	@Autowired
	private IUserService userService;
	
	@Override
	protected BaseService<User> getService() {
		return userService;
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
	public Object save(User entity) {
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
	
	@GetMapping("/userMenus")
	public Object queryMenusById() {
		List<? extends BaseTree> tree = ShiroUtil.getSessionUser().getMenuTree();
		return this.ajaxSuccess(tree);
	}
	
	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		Example example = new Example(User.class);
		return example;
	}
	
}