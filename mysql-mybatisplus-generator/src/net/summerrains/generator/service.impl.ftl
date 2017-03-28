package ${packageName}.service.impl;

import org.springframework.stereotype.Service;

import ${packageName}.mapper.${entityName}Mapper;
import ${packageName}.entity.${entityName};
import ${packageName}.service.${entityName}Service;

import com.baomidou.framework.service.impl.SuperServiceImpl;

/**
 *
 * @author ${author}
 * @Date ${createDate}
 */
@Service("${entityLowerName}Service")
public class ${entityName}ServiceImpl extends SuperServiceImpl<${entityName}Mapper,${entityName}> implements ${entityName}Service {

}
