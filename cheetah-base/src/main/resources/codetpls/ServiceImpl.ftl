package ${servicePackage}.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import ${servicePackage}.I${bigClassName}Service;
import ${mapperPackage}.${bigClassName}Mapper;
import ${modelName};

@Service
public class ${bigClassName}ServiceImpl extends BaseServiceImpl<${bigClassName}> implements I${bigClassName}Service {
 
	@Autowired
	private ${bigClassName}Mapper ${smallClassName}Mapper;

	@Override
	protected BaseMapper<${bigClassName}> getBaseMapper() {
		return ${smallClassName}Mapper;
	}
	
}