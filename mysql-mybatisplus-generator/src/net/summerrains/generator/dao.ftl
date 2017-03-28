package ${packageName}.mapper;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import ${packageName}.entity.${entityName};
import ${packageName}.mybatis.MyBatisRepository;

/**
 *
 * @author ${author}
 * @Date ${createDate}
 */
@MyBatisRepository
public interface ${entityName}Mapper extends AutoMapper<${entityName}> {
	
	
}
