package top.zylsite.cheetah.web.backstage.controller.master;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.backstage.model.master.User;
import top.zylsite.cheetah.backstage.model.master.UserRole;
import top.zylsite.cheetah.backstage.service.master.IUserService;
import top.zylsite.cheetah.base.common.BaseRequestController;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.QueryParameter;
import top.zylsite.cheetah.base.common.tree.BaseTree;
import top.zylsite.cheetah.base.utils.EncdDecd;
import top.zylsite.cheetah.base.utils.MessageSourceUtil;
import top.zylsite.cheetah.web.backstage.common.annotation.ControllerLogs;
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
			entity.setVcPassword(getInitPassword());
			entity.setcLockStatus("0");
			entity.setcStatus("0");
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
	
	@ControllerLogs(description="获取菜单列表")
	@GetMapping("/userMenus")
	public Object userMenus() {
		List<? extends BaseTree> tree = ShiroUtil.getSessionUser().getMenuTree();
		return this.ajaxSuccess(tree);
	}
	
	@ControllerLogs(description="保存角色")
	@PostMapping("/saveRole")
	public Object saveRole(Integer userId, Integer[] roles) {
		userService.saveRoleInfo(userId, roles);
		return this.ajaxSuccess(null);
	}

	@ControllerLogs(description="查询用户角色")
	@GetMapping("/userRoles")
	public Object queryRolesById(Integer userId) {
		List<UserRole> roles = userService.queryRolesByUserId(userId);
		return this.ajaxSuccess(roles);
	}
	
	@ControllerLogs(description="更改用户状态")
	@PostMapping("/status")
	public Object saveStatus(Integer userId, String status) {
		userService.changeStatus(userId, status);
		return this.ajaxSuccess(null);
	}

	@ControllerLogs(description="更改用户锁定状态")
	@PostMapping("/lockStatus")
	public Object saveLockStatus(Integer userId, String lockStatus) {
		userService.changeLockStatus(userId, lockStatus);
		return this.ajaxSuccess(null);
	}

	@ControllerLogs(description="初始化用户密码")
	@GetMapping("initPassword")
	public Object initPassword(Integer userId) {
		userService.updatePassword(userId, getInitPassword());
		return this.ajaxSuccess(null);
	}

	@ControllerLogs(description="修改用户密码")
	@PostMapping("editPassword")
	public Object editPassword(Integer userId, String oldPassword, String newPassword) {
		if (null == userId) {// 此时修改当前登录用户的密码
			userId = ShiroUtil.getSessionUser().getId();
		}
		User user = getService().queryInfoByPrimaryKey(userId);
		if (StringUtils.isNotBlank(oldPassword) && EncdDecd.MD5String(oldPassword).equals(user.getVcPassword())) {
			if (StringUtils.isNotBlank(newPassword) && StringUtils.isNotBlank(newPassword.trim())) {
				userService.updatePassword(userId, EncdDecd.MD5String(newPassword.trim()));
				return this.ajaxSuccess(null);
			}
			return this.ajaxParamError(MessageSourceUtil.getMessage("new_password_not_empty"));
		}
		return this.ajaxParamError(MessageSourceUtil.getMessage("old_password_error"));
	}
	
	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		Example example = new Example(User.class);
		String vcUserName = request.getParameter("vcUserName");
		String vcEmail = request.getParameter("vcEmail");
		String vcMobile = request.getParameter("vcMobile");
		Example.Criteria criteria = example.createCriteria();
		super.andFullLike(criteria, "vcUserName", vcUserName);
		super.andFullLike(criteria, "vcEmail", vcEmail);
		super.andFullLike(criteria, "vcMobile", vcMobile);
		return example;
	}
	
	private String getInitPassword() {
		return EncdDecd.MD5String("123456");
	}
	
}