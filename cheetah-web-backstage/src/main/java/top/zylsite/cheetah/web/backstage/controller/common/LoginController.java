package top.zylsite.cheetah.web.backstage.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import top.zylsite.cheetah.base.common.BaseController;

@Controller
public class LoginController extends BaseController {

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@PostMapping("/login")
	public void login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 验证
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			// 获取当前的Subject
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);// 完成登陆
		} catch (Exception e) {
			e.printStackTrace();
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
