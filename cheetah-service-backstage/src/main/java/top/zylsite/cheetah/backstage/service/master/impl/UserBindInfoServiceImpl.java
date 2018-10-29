package top.zylsite.cheetah.backstage.service.master.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.master.IUserBindInfoService;
import top.zylsite.cheetah.backstage.mapper.master.UserBindInfoMapper;
import top.zylsite.cheetah.backstage.model.master.UserBindInfo;

@Service
public class UserBindInfoServiceImpl extends BaseServiceImpl<UserBindInfo> implements IUserBindInfoService {
 
	@Autowired
	private UserBindInfoMapper userBindInfoMapper;

	@Override
	protected BaseMapper<UserBindInfo> getBaseMapper() {
		return userBindInfoMapper;
	}
	
}