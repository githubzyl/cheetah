package top.zylsite.cheetah.web.backstage.controller.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import top.zylsite.cheetah.base.utils.RequestUtil;

@Controller
public class PageController{

	private final static String PAGE_ROOT_PATH = "page/";
	
	@GetMapping(value = { "", "/", "/index" })
	public String index() {
		return "index";
	}
	
	@GetMapping("/home")
	public String home() {
		return PAGE_ROOT_PATH + "/home/home";
	}
	
	@GetMapping("/page/{parent}/{filename}")
	public String page(@PathVariable String parent, @PathVariable String filename, HttpServletRequest request,
			Model model) {
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		model.addAllAttributes(params);
		return PAGE_ROOT_PATH + parent + "/" + filename;
	}

}
