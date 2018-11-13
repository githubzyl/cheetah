package top.zylsite.cheetah.web.backstage.controller.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.zylsite.cheetah.backstage.service.common.enums.LoginWayEnum;
import top.zylsite.cheetah.backstage.service.common.enums.ResourceTypeEnum;
import top.zylsite.cheetah.base.common.BaseController;

@RestController
@RequestMapping("/enum")
public class EnumController extends BaseController{

	@GetMapping("/loginType")
	public Object loginType() {
		LoginWayEnum[] loginWayEnums =  LoginWayEnum.values();
		List<Map<String,String>> list = new ArrayList<>(loginWayEnums.length);
		Map<String,String> map = null;
		for(LoginWayEnum entity : loginWayEnums) {
			map = new HashMap<>();
			map.put("code", entity.getCodeStr());
			map.put("name", entity.getName());
			list.add(map);
		}
		return this.ajaxSuccess(list);
	}
	
	@GetMapping("/resourceType")
	public Object resourceType() {
		ResourceTypeEnum[] resourceTypeEnums =  ResourceTypeEnum.values();
		List<Map<String,String>> list = new ArrayList<>(resourceTypeEnums.length);
		Map<String,String> map = null;
		for(ResourceTypeEnum entity : resourceTypeEnums) {
			map = new HashMap<>();
			map.put("code", entity.getCode());
			map.put("name", entity.getName());
			list.add(map);
		}
		return this.ajaxSuccess(list);
	}
	
}
