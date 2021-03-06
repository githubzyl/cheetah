package top.zylsite.cheetah.web.backstage.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.zylsite.cheetah.backstage.model.master.User;
import top.zylsite.cheetah.backstage.model.vo.UserVO;
import top.zylsite.cheetah.backstage.service.master.IUserService;
import top.zylsite.cheetah.base.common.BaseController;
import top.zylsite.cheetah.base.utils.EncdDecd;
import top.zylsite.cheetah.web.backstage.common.annotation.ControllerLogs;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroUtil;

@Controller
public class HomeController extends BaseController {

	@Autowired
	private IUserService userService;
	
	@GetMapping("/profile")
	public String profile(Model model) {
		model.addAttribute("user", getUserInfo());
		return "page/home/profile";
	}
	
	@GetMapping("/profile/info")
	@ResponseBody
	public Object profileInfo() {
		return this.ajaxSuccess(getUserInfo());
	}
	
	@ControllerLogs(description="修改个人信息")
	@PostMapping("/profile/save")
	@ResponseBody
	public Object saveProfile(User entity) {
		entity.setId(ShiroUtil.getSessionUser().getId());
		userService.updateInfoByPrimaryKey(entity, true);
		return this.ajaxSuccess(null);
	}
	
	@GetMapping("/profile/modifyPwd")
	public String modefyPwd() {
		return "page/home/modifyPwd";
	}
	
	@ControllerLogs(description="修改密码")
	@PostMapping("/profile/modifyPwd/save")
	@ResponseBody
	public Object savePwd(String oldPwd, String newPwd, String repeatNewPwd) {
		User user = getUserInfo();
		oldPwd = EncdDecd.MD5String(oldPwd);
		if(!oldPwd.equals(user.getVcPassword())) {
			return this.ajaxParamError("原密码不正确");
		}
		if(!repeatNewPwd.equals(newPwd)) {
			return this.ajaxParamError("重复密码不正确");
		}
		userService.updatePassword(ShiroUtil.getSessionUser().getId(), EncdDecd.MD5String(newPwd));
		return this.ajaxSuccess(null);
	}
	
	private UserVO getUserInfo() {
		return userService.queryById(ShiroUtil.getSessionUser().getId());
	}
	
}
