package top.zylsite.cheetah.web.backstage.controller.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.backstage.model.master.Permission;
import top.zylsite.cheetah.backstage.service.master.IPermissionService;
import top.zylsite.cheetah.base.utils.RequestUtil;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroUtil;

@Controller
public class PageController{

	// 存放权限代码和url
	private static Map<String, String> permissionMap = new HashMap<>();

	private final static String PAGE_ROOT_PATH = "page/";
	
	private final static String HOME = "home";
	
	@Autowired
	private IPermissionService permissionService;

	@GetMapping(value = { "", "/", "/index" })
	public String index() {
		return "index";
	}
	
	@GetMapping("/page/{parent}/{filename}")
	public String page(@PathVariable String parent, @PathVariable String filename, HttpServletRequest request,
			Model model) {
//		if (!checkPermission(parent, filename)) {
//			return "redirect:" + ShiroConstants.UNAUTHORIZED_URL;
//		}
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		model.addAllAttributes(params);
		return PAGE_ROOT_PATH + parent + "/" + filename;
	}

	public boolean checkPermission(String parent, String filename) {
		if(ShiroUtil.getSessionUser().isSysadmin() || (HOME.equals(parent) && HOME.equals(filename))) {
			return true;
		}
		String permissionCode = getPermissionCode(parent, filename);
		if (StringUtils.isNotBlank(permissionCode)) {
			return SecurityUtils.getSubject().isPermitted(permissionCode);
		}
		return false;
	}

	public String getPermissionCode(String parent, String filename) {
		String url = "/page/" + parent + "/" + filename;
		String permissionCode = permissionMap.get(url);
		if (StringUtils.isNotBlank(permissionCode)) {
			return permissionCode;
		}
		Example example = new Example(Permission.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("vcUrl", url);
		List<Permission> list = permissionService.queryList(example);
		if (null != list && list.size() > 0) {
			Permission permission = list.get(0);
			if (StringUtils.isNotBlank(permission.getVcCode())) {
				permissionCode = permission.getVcCode();
				permissionMap.put(url, permissionCode);
			}
		}
		return permissionCode;
	}

}
