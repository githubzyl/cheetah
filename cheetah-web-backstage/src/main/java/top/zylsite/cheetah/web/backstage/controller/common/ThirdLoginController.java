package top.zylsite.cheetah.web.backstage.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
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
import top.zylsite.cheetah.backstage.service.common.enums.LoginWayEnum;
import top.zylsite.cheetah.backstage.service.common.thirdaccount.AlipayInfo;
import top.zylsite.cheetah.backstage.service.common.thirdaccount.BaiduInfo;
import top.zylsite.cheetah.backstage.service.common.thirdaccount.DingDindInfo;
import top.zylsite.cheetah.backstage.service.common.thirdaccount.GitHubInfo;
import top.zylsite.cheetah.backstage.service.common.thirdaccount.QQInfo;
import top.zylsite.cheetah.backstage.service.common.thirdaccount.SinaInfo;
import top.zylsite.cheetah.backstage.service.common.thirdaccount.ThirdResult;
import top.zylsite.cheetah.backstage.service.common.thirdaccount.WechatInfo;
import top.zylsite.cheetah.backstage.service.master.IUserBindInfoService;
import top.zylsite.cheetah.backstage.service.master.IUserService;
import top.zylsite.cheetah.base.utils.EncdDecd;
import top.zylsite.cheetah.base.utils.LoggerFactoryUtil;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroUtil;

/**
 * Description: 第三方登录
 * @author jason 
 * 2018年10月28日
 * @version 1.0
 */
@Controller
public class ThirdLoginController {

	private Logger logger = LoggerFactoryUtil.getLogger(ThirdLoginController.class);

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
		if (res.isSuccess()) {
			String access_token = res.getResult().toString();
			res = QQInfo.getOpneId(access_token);
			if (res.isSuccess()) {
				String openid = res.getResult().toString();
				res = QQInfo.getUserInfo(access_token, openid);
				if (res.isSuccess()) {
					UserBindInfo bindInfo = new UserBindInfo();
					bindInfo.setVcAccount(openid);
					bindInfo.setVcNickName(((JSONObject) res.getResult()).getString("nickname"));
					bindInfo.setVcPhoto(QQInfo.getPictureUrl((JSONObject) res.getResult()));
					bindInfo.setcType(LoginWayEnum.QQ.getCodeStr());
					int accountId = userBindInfoService.insertIfNotExist(bindInfo);
					// 判断是否绑定了用户
					Integer userId = userBindInfoService.hasBindingUser(accountId);
					if (null == userId) {// 跳转到绑定页面
						return bind(redirectAttributes, LoginWayEnum.QQ.getCodeStr(), accountId, null);
					} else {// 跳转到首页
						return index(request, redirectAttributes, userId, LoginWayEnum.QQ.getCodeStr());
					}
				} else {
					return error(redirectAttributes, res);
				}
			} else {
				return error(redirectAttributes, res);
			}
		} else {
			return error(redirectAttributes, res);
		}
	}

	@GetMapping(LoginConstants.WECHAT_AUTH_REDIRECT_URI)
	public String wechat(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String code = request.getParameter("code");
		ThirdResult res = WechatInfo.getAccessToken(code);
		if(res.isSuccess()) {
			JSONObject jsonObject = (JSONObject) res.getResult();
			String access_token = jsonObject.getString("access_token");
			String openid = jsonObject.getString("openid");
			res = WechatInfo.getUserInfo(access_token, openid);
			if(res.isSuccess()) {
				jsonObject = (JSONObject) res.getResult();
				UserBindInfo bindInfo = new UserBindInfo();
				bindInfo.setVcAccount(openid);
				bindInfo.setVcNickName(jsonObject.getString("nickname"));
				bindInfo.setVcPhoto(jsonObject.getString("headimgurl"));
				bindInfo.setcType(LoginWayEnum.WECHAT.getCodeStr());
				int accountId = userBindInfoService.insertIfNotExist(bindInfo);
				// 判断是否绑定了用户
				Integer userId = userBindInfoService.hasBindingUser(accountId);
				if (null == userId) {// 跳转到绑定页面
					return bind(redirectAttributes, LoginWayEnum.WECHAT.getCodeStr(), accountId, null);
				} else {// 跳转到首页
					return index(request, redirectAttributes, userId, LoginWayEnum.WECHAT.getCodeStr());
				}
			}else {
				return error(redirectAttributes, res);
			}
		}else {
			return error(redirectAttributes, res);
		}
	}

	@GetMapping(LoginConstants.SINA_AUTH_REDIRECT_URI)
	public String sina(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String code = request.getParameter("code");
		ThirdResult res = SinaInfo.getAccessToken(code);
		if (res.isSuccess()) {
			JSONObject token = (JSONObject) res.getResult();
			String access_token = token.getString("access_token");
			String uid = token.getString("uid");
			res = SinaInfo.getUserInfo(access_token, uid);
			if (res.isSuccess()) {
				JSONObject userInfo = (JSONObject) res.getResult();
				UserBindInfo bindInfo = new UserBindInfo();
				bindInfo.setVcAccount(uid);
				bindInfo.setVcNickName(userInfo.getString("screen_name"));
				bindInfo.setVcPhoto(userInfo.getString("avatar_large"));
				bindInfo.setcType(LoginWayEnum.SINA.getCodeStr());
				int accountId = userBindInfoService.insertIfNotExist(bindInfo);
				// 判断是否绑定了用户
				Integer userId = userBindInfoService.hasBindingUser(accountId);
				if (null == userId) {// 跳转到绑定页面
					return bind(redirectAttributes, LoginWayEnum.SINA.getCodeStr(), accountId, null);
				} else {// 跳转到首页
					return index(request, redirectAttributes, userId, LoginWayEnum.SINA.getCodeStr());
				}
			} else {
				return error(redirectAttributes, res);
			}
		} else {
			return error(redirectAttributes, res);
		}
	}

	@GetMapping(LoginConstants.BAIDU_AUTH_REDIRECT_URI)
	public String baidu(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String code = request.getParameter("code");
		ThirdResult res = BaiduInfo.getAccessToken(code);
		if (res.isSuccess()) {
			String access_token = res.getResult().toString();
			res = BaiduInfo.getLoggedInUser(access_token);
			if (res.isSuccess()) {
				JSONObject userInfo = (JSONObject) res.getResult();
				UserBindInfo bindInfo = new UserBindInfo();
				bindInfo.setVcAccount(userInfo.get("uid").toString());
				bindInfo.setVcNickName(userInfo.getString("uname"));
				bindInfo.setVcPhoto(userInfo.getString("portrait"));
				bindInfo.setcType(LoginWayEnum.BAIDU.getCodeStr());
				int accountId = userBindInfoService.insertIfNotExist(bindInfo);
				// 判断是否绑定了用户
				Integer userId = userBindInfoService.hasBindingUser(accountId);
				if (null == userId) {// 跳转到绑定页面
					return bind(redirectAttributes, LoginWayEnum.BAIDU.getCodeStr(), accountId, null);
				} else {// 跳转到首页
					return index(request, redirectAttributes, userId, LoginWayEnum.BAIDU.getCodeStr());
				}
			} else {
				return error(redirectAttributes, res);
			}
		} else {
			return error(redirectAttributes, res);
		}
	}

	@GetMapping(LoginConstants.ALIPAY_AUTH_REDIRECT_URI)
	public String alipay(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String code = request.getParameter("auth_code");
		ThirdResult res = AlipayInfo.getAccessToken(code);
		if (res.isSuccess()) {
			String access_token = res.getResult().toString();
			res = AlipayInfo.getUserInfo(access_token);
			if (res.isSuccess()) {
				JSONObject userInfo = (JSONObject) res.getResult();
				UserBindInfo bindInfo = new UserBindInfo();
				bindInfo.setVcAccount(userInfo.getString("userId"));
				bindInfo.setVcNickName(userInfo.getString("nickName"));
				bindInfo.setVcPhoto(userInfo.getString("avatar"));
				bindInfo.setcType(LoginWayEnum.ALIPAY.getCodeStr());
				int accountId = userBindInfoService.insertIfNotExist(bindInfo);
				// 判断是否绑定了用户
				Integer userId = userBindInfoService.hasBindingUser(accountId);
				if (null == userId) {// 跳转到绑定页面
					return bind(redirectAttributes, LoginWayEnum.ALIPAY.getCodeStr(), accountId, null);
				} else {// 跳转到首页
					return index(request, redirectAttributes, userId, LoginWayEnum.ALIPAY.getCodeStr());
				}
			} else {
				return error(redirectAttributes, res);
			}
		} else {
			return error(redirectAttributes, res);
		}
	}

	@GetMapping(LoginConstants.GITHUB_AUTH_REDIRECT_URI)
	public String github(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String code = request.getParameter("code");
		ThirdResult res = GitHubInfo.getAccessToken(code);
		if (res.isSuccess()) {
			String access_token = res.getResult().toString();
			res = GitHubInfo.getUserInfo(access_token);
			if (res.isSuccess()) {
				JSONObject userInfo = (JSONObject) res.getResult();
				UserBindInfo bindInfo = new UserBindInfo();
				bindInfo.setVcAccount(userInfo.get("id").toString());
				bindInfo.setVcNickName(userInfo.getString("name"));
				bindInfo.setVcPhoto(userInfo.getString("avatar_url"));
				bindInfo.setcType(LoginWayEnum.GITHUB.getCodeStr());
				int accountId = userBindInfoService.insertIfNotExist(bindInfo);
				// 判断是否绑定了用户
				Integer userId = userBindInfoService.hasBindingUser(accountId);
				if (null == userId) {// 跳转到绑定页面
					return bind(redirectAttributes, LoginWayEnum.GITHUB.getCodeStr(), accountId, null);
				} else {// 跳转到首页
					return index(request, redirectAttributes, userId, LoginWayEnum.GITHUB.getCodeStr());
				}
			} else {
				return error(redirectAttributes, res);
			}
		} else {
			return error(redirectAttributes, res);
		}
	}
	
	@GetMapping(LoginConstants.DINDGING_AUTH_REDIRECT_URI)
	public String dingding(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String code = request.getParameter("code");
		ThirdResult res = DingDindInfo.getAccessToken();
		if (res.isSuccess()) {
			String access_token = res.getResult().toString();
			res = DingDindInfo.getPersistentCode(access_token, code);
			if (res.isSuccess()) {
				JSONObject jsonObject = (JSONObject) res.getResult();
				String openid = jsonObject.getString("openid");
				String persistent_code = jsonObject.getString("persistent_code");
				res = DingDindInfo.getSNSToken(access_token, openid, persistent_code);
				if(res.isSuccess()) {
					String sns_token = res.getResult().toString();
					res = DingDindInfo.getUserInfo(sns_token);
					if(res.isSuccess()) {
						JSONObject userInfo = (JSONObject) res.getResult();
						UserBindInfo bindInfo = new UserBindInfo();
						bindInfo.setVcAccount(userInfo.getString("openid"));
						bindInfo.setVcNickName(userInfo.getString("nick"));
						bindInfo.setcType(LoginWayEnum.DINGDING.getCodeStr());
						int accountId = userBindInfoService.insertIfNotExist(bindInfo);
						// 判断是否绑定了用户
						Integer userId = userBindInfoService.hasBindingUser(accountId);
						if (null == userId) {// 跳转到绑定页面
							return bind(redirectAttributes, LoginWayEnum.DINGDING.getCodeStr(), accountId, null);
						} else {// 跳转到首页
							return index(request, redirectAttributes, userId, LoginWayEnum.DINGDING.getCodeStr());
						}
					}else {
						return error(redirectAttributes, res);
					}
				}else {
					return error(redirectAttributes, res);
				}
			} else {
				return error(redirectAttributes, res);
			}
		} else {
			return error(redirectAttributes, res);
		}
	}

	@GetMapping("/login/oauthError")
	public String oauthError() {
		return "page/login/oauthError";
	}

	@GetMapping("/login/bind")
	private String bindIndex() {
		return "page/login/bindAccount";
	}

	@PostMapping("/login/bindAccount")
	public String bindAccount(String username, String password, Integer accountId, String loginType,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return bind(redirectAttributes, loginType, accountId, "用户名或密码不能为空");
		}
		User user = userService.findByUserName(username);
		if (null == user) {
			return bind(redirectAttributes, loginType, accountId, "用户名或密码错误");
		}
		if (EncdDecd.MD5String(password).equals(user.getVcPassword())) {
			boolean flag = userBindInfoService.hasBindSameTypeAccount(user.getId(), loginType);
			if(flag) {
				String typeName = LoginWayEnum.getNameByCode(Integer.parseInt(loginType));
				return bind(redirectAttributes, loginType, accountId, "该用户已绑定了一个"+typeName+"账号");
			}else {
				userBindInfoService.bindUser(accountId, user.getId());
				return index(request, redirectAttributes, user.getId(), loginType);
			}
		}
		return bind(redirectAttributes, loginType, accountId, "用户名或密码错误");
	}

	private String error(RedirectAttributes redirectAttributes, ThirdResult res) {
		redirectAttributes.addFlashAttribute("res", res);
		return "redirect:/login/oauthError";
	}

	private String bind(RedirectAttributes redirectAttributes, String loginType, Integer accountId, String error) {
		redirectAttributes.addFlashAttribute("accountId", accountId);
		redirectAttributes.addFlashAttribute("loginType", loginType);
		if (StringUtils.isNotBlank(error)) {
			redirectAttributes.addFlashAttribute("error_message", error);
		}
		return "redirect:/login/bind";
	}

	private String index(HttpServletRequest request, RedirectAttributes redirectAttributes, Integer userId,
			String loginType) {
		User user = userService.queryInfoByPrimaryKey(userId);
		if (null == user) {
			return error(redirectAttributes, new ThirdResult(false, "", "绑定的用户不存在或已被删除"));
		}
		try {
			Subject subject = ShiroUtil.login(user.getVcUserName(), user.getVcPassword(), loginType);
			ShiroUtil.onLoginSuccess(request, subject, loginType);
			return "redirect:/index";
		} catch (Exception e) {
			logger.error("登录失败：", e);
			return error(redirectAttributes, new ThirdResult(false, "", ShiroUtil.getErrorMessage(e)));
		}
	}

}
