package top.zylsite.cheetah.web.backstage.controller.master;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.backstage.model.master.UserBindInfo;
import top.zylsite.cheetah.backstage.service.master.IUserBindInfoService;
import top.zylsite.cheetah.base.common.BaseRequestController;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.QueryParameter;

@RestController
@RequestMapping("/userBindInfo")
public class UserBindInfoController extends BaseRequestController<UserBindInfo> {
	
	@Autowired
	private IUserBindInfoService userBindInfoService;
	
	@Override
	protected BaseService<UserBindInfo> getService() {
		return userBindInfoService;
	}
	
	@GetMapping("/list")
	public Object list(QueryParameter queryParameter, HttpServletRequest request) {
		return super.list(queryParameter, request);
	}
	
	@GetMapping("/remove")
	public Object remove(Integer[] ids) {
		return super.remove(ids);
	}
	
	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		Example example = new Example(UserBindInfo.class);
		return example;
	}
	
}