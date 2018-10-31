package top.zylsite.cheetah.web.backstage.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import top.zylsite.cheetah.backstage.service.common.enums.LoginWayEnum;
import top.zylsite.cheetah.base.common.BaseController;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroConstants;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroUtil;

@Controller
public class LoginController extends BaseController {

	@GetMapping("/login")
	public String login(Model model,RedirectAttributes attributes) {
		if(!attributes.containsAttribute(ShiroConstants.DEFAULT_LOGIN_TYPE_PARAM)) {
			model.addAttribute(ShiroConstants.DEFAULT_LOGIN_TYPE_PARAM, LoginWayEnum.AP.getCodeStr());
		}
		return "login";
	}

	@PostMapping("/login")
	public String loginSubmit(String username, String password, String loginType,
			RedirectAttributes redirectAttributes) {
		try {
			ShiroUtil.login(username, password, loginType);
			return "redirect:/index";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(ShiroConstants.FAIL_LOGIN_KEY_ATTRIBUTE, ShiroUtil.getErrorMessage(e));
			return "redirect:/login";
		}
	}

	@GetMapping("/logout")
	public String logOut(HttpServletRequest request, RedirectAttributes attributes) {
		ShiroUtil.logout();
		attributes.addFlashAttribute(ShiroConstants.DEFAULT_LOGIN_TYPE_PARAM, LoginWayEnum.AP.getCodeStr());
		return "redirect:/login";
	}

}
