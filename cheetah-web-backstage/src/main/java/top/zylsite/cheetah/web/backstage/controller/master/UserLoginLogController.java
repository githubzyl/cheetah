package top.zylsite.cheetah.web.backstage.controller.master;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.backstage.model.master.UserLoginLog;
import top.zylsite.cheetah.backstage.service.common.enums.LoginWayEnum;
import top.zylsite.cheetah.backstage.service.master.IUserLoginLogService;
import top.zylsite.cheetah.base.common.BaseRequestController;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.QueryParameter;
import top.zylsite.cheetah.web.backstage.common.annotation.ControllerLogs;

@RestController
@RequestMapping("/userLoginLog")
public class UserLoginLogController extends BaseRequestController<UserLoginLog> {
	
	@Autowired
	private IUserLoginLogService userLoginLogService;
	
	@Override
	protected BaseService<UserLoginLog> getService() {
		return userLoginLogService;
	}
	
	@ControllerLogs(description="查询登录日志列表")
	@GetMapping("/list")
	public Object list(QueryParameter queryParameter, HttpServletRequest request) {
		PageInfo<UserLoginLog> pageInfo = getPageInfo(queryParameter, request);
		List<UserLoginLog> list = pageInfo.getList();
		if(!CollectionUtils.isEmpty(list)) {
			for(UserLoginLog entity : list) {
				entity.setcLoginType(LoginWayEnum.getNameByCode(entity.getcLoginType()));
			}
		}
		return this.ajaxSuccess(pageInfo);
	}
	
	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		String loginType = request.getParameter("loginType");
		String userName = request.getParameter("userName");
		String loginStartTime = request.getParameter("loginStartTime");
		String loginEndTime = request.getParameter("loginEndTime");
		Example example = new Example(UserLoginLog.class);
		Example.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(loginType)) {
			criteria.andEqualTo("cLoginType", loginType);
		}
		if(StringUtils.isNotBlank(userName)) {
			criteria.andEqualTo("vcUserName", Integer.parseInt(userName));
		}
		if(StringUtils.isNotBlank(loginStartTime)) {
			criteria.andGreaterThan("dLoginTime", loginStartTime);
		}
		if(StringUtils.isNotBlank(loginEndTime)) {
			criteria.andLessThan("dLoginTime", loginEndTime);
		}
		return example;
	}
	
}