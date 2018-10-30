package top.zylsite.cheetah.web.backstage.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;

import top.zylsite.cheetah.backstage.model.master.User;
import top.zylsite.cheetah.backstage.model.master.UserBindInfo;
import top.zylsite.cheetah.backstage.service.common.LoginConstants;
import top.zylsite.cheetah.backstage.service.common.ThirdLoginUrl;
import top.zylsite.cheetah.backstage.service.common.thirdaccount.QQInfo;
import top.zylsite.cheetah.backstage.service.common.thirdaccount.ThirdResult;
import top.zylsite.cheetah.backstage.service.master.IUserBindInfoService;
import top.zylsite.cheetah.backstage.service.master.IUserService;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroUtil;

/**
 * Description: 第三方登录
 * @author jason
 * 2018年10月28日
 * @version 1.0
 */
@Controller
public class ThirdLoginController {
	
	@Autowired
	private IUserBindInfoService userBindInfoService;
	
	@Autowired
	private IUserService userService;

	@GetMapping("/login/thirdconnect")
	private String thirdConnect(Integer code) {
		String url = ThirdLoginUrl.get(code);
		return "redirect:" + url;
	}
	
	@GetMapping(LoginConstants.QQ_AUTH_REDIRECT_URI)
	public String qq(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String code = request.getParameter("code");
		ThirdResult res = QQInfo.getAccessToken(code);
		if(res.isSuccess()) {
			String access_token = res.getResult().toString();
			res = QQInfo.getOpneId(access_token);
			if(res.isSuccess()) {
				String openid = res.getResult().toString();
				res = QQInfo.getUserInfo(access_token, openid);
				if(res.isSuccess()) {
					UserBindInfo bindInfo = new UserBindInfo();
					bindInfo.setVcAccount(openid);
					bindInfo.setVcNickName(((JSONObject) res.getResult()).getString("nickname"));
					bindInfo.setVcPhoto(QQInfo.getPictureUrl((JSONObject) res.getResult()));
					bindInfo.setcType(String.valueOf(LoginConstants.LOGIN_WAY_QQ));
					int accountId = userBindInfoService.insertIfNotExist(bindInfo);
					//判断是否绑定了用户
					Integer userId = userBindInfoService.hasBindingUser(accountId);
					if(null == userId) {//跳转到绑定页面
						return bind(redirectAttributes, accountId);
					}else {//跳转到首页
						return index(redirectAttributes,userId);
					}
				}else {
					return error(redirectAttributes, res);
				}
			}else {
				return error(redirectAttributes, res);
			}
		}else {
			return error(redirectAttributes, res);
		}
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
	
	@GetMapping("/login/oauthError")
	public String oauthError() {
		return "page/login/oauthError";
	}
	
	@GetMapping("/bindIndex")
	private String bindIndex() {
		return "page/login/bindAccount";
	}
	
	@PostMapping("/bindAccount")
	private String bindAccount() {
		
		
		return "redirect:/index";
	}
	
	private String error(RedirectAttributes redirectAttributes, ThirdResult res) {
		redirectAttributes.addAttribute("res", res);
		return "redirect:/login/oauthError";
	}
	
	private String bind(RedirectAttributes redirectAttributes, Integer accountId) {
		redirectAttributes.addAttribute("accountId", accountId);
		return "redirect:/bindIndex";
	}
	
	private String index(RedirectAttributes redirectAttributes, Integer userId) {
		User user = userService.queryInfoByPrimaryKey(userId);
		if(null == user) {
			return error(redirectAttributes, new ThirdResult(false, "", "绑定的用户已被删除"));
		}else {
			ShiroUtil.login(user.getVcUserName(), user.getVcPassword());
		}
		return "redirect:/index";
	}
	
}
