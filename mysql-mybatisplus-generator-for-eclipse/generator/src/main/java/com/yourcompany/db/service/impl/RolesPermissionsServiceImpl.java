package com.yourcompany.db.service.impl;

import org.springframework.stereotype.Service;

import com.yourcompany.db.mapper.RolesPermissionsMapper;
import com.yourcompany.db.entity.RolesPermissions;
import com.yourcompany.db.service.RolesPermissionsService;

import com.baomidou.framework.service.impl.SuperServiceImpl;

/**
 *
 * @author summerrains
 * @Date 2017-03-28 15:23:21
 */
@Service("rolesPermissionsService")
public class RolesPermissionsServiceImpl extends SuperServiceImpl<RolesPermissionsMapper,RolesPermissions> implements RolesPermissionsService {

}
