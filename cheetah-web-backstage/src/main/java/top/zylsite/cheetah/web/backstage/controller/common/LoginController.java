package top.zylsite.cheetah.web.backstage.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import top.zylsite.cheetah.base.common.BaseController;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroConstants;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroUtil;

@Controller
public class LoginController extends BaseController {

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@PostMapping("/loginSubmit")
	public String loginSubmit(String username, String password, RedirectAttributes redirectAttributes) {
		try {
			// 验证
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			// 获取当前的Subject
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);// 完成登陆
			return "redirect:/index";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(ShiroConstants.FAIL_LOGIN_KEY_ATTRIBUTE, ShiroUtil.getErrorMessage(e));
			return "redirect:/login";
		}
	}

	@GetMapping("/logout")
	public String logOut(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		if (null != subject) {
			subject.logout();
		}
		return "redirect:/login";
	}

}
