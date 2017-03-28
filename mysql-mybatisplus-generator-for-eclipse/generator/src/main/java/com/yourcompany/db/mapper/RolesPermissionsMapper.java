package com.yourcompany.db.mapper;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.yourcompany.db.entity.RolesPermissions;
import com.yourcompany.db.mybatis.MyBatisRepository;

/**
 *
 * @author summerrains
 * @Date 2017-03-28 15:23:21
 */
@MyBatisRepository
public interface RolesPermissionsMapper extends AutoMapper<RolesPermissions> {
	
	
}
