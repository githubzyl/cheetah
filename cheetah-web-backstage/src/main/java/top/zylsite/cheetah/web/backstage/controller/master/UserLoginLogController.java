package top.zylsite.cheetah.web.backstage.controller.master;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.backstage.model.master.UserLoginLog;
import top.zylsite.cheetah.backstage.service.master.IUserLoginLogService;
import top.zylsite.cheetah.base.common.BaseRequestController;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.QueryParameter;

@RestController
@RequestMapping("/userLoginLog")
public class UserLoginLogController extends BaseRequestController<UserLoginLog> {
	
	@Autowired
	private IUserLoginLogService userLoginLogService;
	
	@Override
	protected BaseService<UserLoginLog> getService() {
		return userLoginLogService;
	}
	
	@GetMapping("/list")
	public Object list(QueryParameter queryParameter, HttpServletRequest request) {
		return super.list(queryParameter, request);
	}
	
	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		Example example = new Example(UserLoginLog.class);
		return example;
	}
	
}