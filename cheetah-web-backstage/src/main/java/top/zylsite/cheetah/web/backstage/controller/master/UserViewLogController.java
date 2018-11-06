package top.zylsite.cheetah.web.backstage.controller.master;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import top.zylsite.cheetah.web.backstage.common.annotation.ControllerLogs;

@RestController
@RequestMapping("/userViewLog")
public class UserViewLogController extends BaseRequestController<UserViewLog> {
	
	@Autowired
	private IUserViewLogService userViewLogService;
	
	@Override
	protected BaseService<UserViewLog> getService() {
		return userViewLogService;
	}
	
	@ControllerLogs(description="查询访问日志列表")
	@GetMapping("/list")
	public Object list(QueryParameter queryParameter, HttpServletRequest request) {
		return super.list(queryParameter, request);
	}
	
	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		String userName = request.getParameter("userName");
		String viewStartTime = request.getParameter("viewStartTime");
		String viewEndTime = request.getParameter("viewEndTime");
		Example example = new Example(UserViewLog.class);
		Example.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(userName)) {
			criteria.andEqualTo("vcUserName", Integer.parseInt(userName));
		}
		if(StringUtils.isNotBlank(viewStartTime)) {
			criteria.andGreaterThan("dVisitTime", viewStartTime + "00:00:00");
		}
		if(StringUtils.isNotBlank(viewEndTime)) {
			criteria.andLessThan("dVisitTime", viewEndTime + "23:59:59");
		}
		return example;
	}
	
}