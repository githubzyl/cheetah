package top.zylsite.cheetah.backstage.service.master.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.master.ITenantService;
import top.zylsite.cheetah.backstage.mapper.master.TenantMapper;
import top.zylsite.cheetah.backstage.model.master.Tenant;

@Service
public class TenantServiceImpl extends BaseServiceImpl<Tenant> implements ITenantService {
 
	@Autowired
	private TenantMapper tenantMapper;

	@Override
	protected BaseMapper<Tenant> getBaseMapper() {
		return tenantMapper;
	}
	
}