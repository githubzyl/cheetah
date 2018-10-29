package top.zylsite.cheetah.web.backstage.controller.master;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.backstage.model.master.UserViewLog;
import top.zylsite.cheetah.backstage.service.master.IUserViewLogService;
import top.zylsite.cheetah.base.common.BaseRequestController;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.QueryParameter;

@RestController
@RequestMapping("/userViewLog")
public class UserViewLogController extends BaseRequestController<UserViewLog> {
	
	@Autowired
	private IUserViewLogService userViewLogService;
	
	@Override
	protected BaseService<UserViewLog> getService() {
		return userViewLogService;
	}
	
	@GetMapping("/list")
	public Object list(QueryParameter queryParameter, HttpServletRequest request) {
		return super.list(queryParameter, request);
	}
	
	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		Example example = new Example(UserViewLog.class);
		return example;
	}
	
}