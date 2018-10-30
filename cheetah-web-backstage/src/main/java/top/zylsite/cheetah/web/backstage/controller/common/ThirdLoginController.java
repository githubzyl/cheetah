package top.zylsite.cheetah.web.backstage.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import top.zylsite.cheetah.backstage.service.common.LoginConstants;
import top.zylsite.cheetah.backstage.service.common.ThirdLoginUrl;

/**
 * Description: 第三方登录
 * @author jason
 * 2018年10月28日
 * @version 1.0
 */
@Controller
public class ThirdLoginController {

	@GetMapping("/login/thirdconnect")
	private String thirdConnect(Integer code) {
		String url = ThirdLoginUrl.get(code);
		return "redirect:" + url;
	}
	
	@GetMapping(LoginConstants.QQ_AUTH_REDIRECT_URI)
	public String qq(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		return "redirect:/index";
	}
	
	@GetMapping(LoginConstants.WECHAT_AUTH_REDIRECT_URI)
	public String wechat(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		return "redirect:/index";
	}
	
	@GetMapping(LoginConstants.SINA_AUTH_REDIRECT_URI)
	public String sina(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		return "redirect:/index";
	}
	
	@GetMapping(LoginConstants.BAIDU_AUTH_REDIRECT_URI)
	public String baidu(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		return "redirect:/index";
	}
	
	@GetMapping(LoginConstants.ALIPAY_AUTH_REDIRECT_URI)
	public String alipay(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		return "redirect:/index";
	}
	
	@GetMapping(LoginConstants.GITHUB_AUTH_REDIRECT_URI)
	public String github(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		return "redirect:/index";
	}
	
}
