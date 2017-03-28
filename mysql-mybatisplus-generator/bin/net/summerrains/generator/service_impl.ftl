package com.hna.unifiedpay.db.service.impl;

import org.springframework.stereotype.Service;

import com.hna.unifiedpay.db.mapper.${entityName}Mapper;
import com.hna.unifiedpay.db.entity.${entityName};
import com.hna.unifiedpay.db.service.${entityName}Service;

import com.baomidou.framework.service.impl.SuperServiceImpl;

/**
 *
 * @author ${author}
 * @Date ${createDate}
 */
@Service("${entityLowerName}Service")
public class ${entityName}ServiceImpl extends SuperServiceImpl<${entityName}Mapper,${entityName}> implements ${entityName}Service {

}
