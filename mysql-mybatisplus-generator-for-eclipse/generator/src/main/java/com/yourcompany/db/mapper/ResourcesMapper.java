package com.yourcompany.db.mapper;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.yourcompany.db.entity.Resources;
import com.yourcompany.db.mybatis.MyBatisRepository;

/**
 *
 * @author summerrains
 * @Date 2017-03-28 15:23:21
 */
@MyBatisRepository
public interface ResourcesMapper extends AutoMapper<Resources> {
	
	
}
