package top.zylsite.cheetah.backstage.service.master.impl;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.backstage.mapper.master.UserBindInfoMapper;
import top.zylsite.cheetah.backstage.model.master.UserBindInfo;
import top.zylsite.cheetah.backstage.service.master.IUserBindInfoService;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.base.common.BaseServiceImpl;

@Service
public class UserBindInfoServiceImpl extends BaseServiceImpl<UserBindInfo> implements IUserBindInfoService {
 
	@Autowired
	private UserBindInfoMapper userBindInfoMapper;

	@Override
	protected BaseMapper<UserBindInfo> getBaseMapper() {
		return userBindInfoMapper;
	}

	@Override
	public Integer insertIfNotExist(UserBindInfo bindInfo) {
		Example example = super.createExample();
		example.createCriteria()
		            .andEqualTo("vcAccount", bindInfo.getVcAccount())
		            .andEqualTo("cType", bindInfo.getcType());
		List<UserBindInfo> list = userBindInfoMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(list)) {
			userBindInfoMapper.insert(bindInfo);
			return bindInfo.getId();
		}
		Integer id = list.get(0).getId();
		bindInfo.setId(id);
		userBindInfoMapper.updateByPrimaryKeySelective(bindInfo);
		return id;
	}

	@Override
	public Integer hasBindingUser(int accountId) {
		UserBindInfo userBindInfo = userBindInfoMapper.selectByPrimaryKey(accountId);
		return userBindInfo.getlUserId();
	}

	@Override
	public void bindUser(int accountId, int userId) {
		UserBindInfo userBindInfo = new UserBindInfo();
		userBindInfo.setId(accountId);
		userBindInfo.setlUserId(userId);
		super.updateInfoByPrimaryKey(userBindInfo, true);
	}

	@Override
	public boolean hasBindSameTypeAccount(int userId, String type) {
		Example example = super.createExample();
		example.createCriteria()
		            .andEqualTo("lUserId", userId)
		            .andEqualTo("cType", type);
		List<UserBindInfo> list = userBindInfoMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? false : true;
	}
	
}