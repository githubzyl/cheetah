package top.zylsite.cheetah.web.backstage.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import top.zylsite.cheetah.backstage.service.common.ThirdLoginUrl;

/**
 * Description: 第三方登录
 * @author jason
 * 2018年10月28日
 * @version 1.0
 */
@Controller
public class ThirdLoginController {

	@GetMapping("/login/third/{code}")
	private String thirdLogin(@PathVariable Integer code) {
		String url = ThirdLoginUrl.get(code);
		return "redirect:" + url;
	}
	
	@GetMapping("/login/qq/callback")
	public String qq(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		return "redirect:/index";
	}
	
	@GetMapping("/login/wechat/callback")
	public String wechat(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		return "redirect:/index";
	}
	
	@GetMapping("/login/sina/callback")
	public String sina(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		return "redirect:/index";
	}
	
	@GetMapping("/login/baidu/callback")
	public String baidu(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		return "redirect:/index";
	}
	
	@GetMapping("/login/alipay/callback")
	public String alipay(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		return "redirect:/index";
	}
	
	@GetMapping("/login/github/callback")
	public String github(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		return "redirect:/index";
	}
	
}
