package com.yourcompany.db.service.impl;

import org.springframework.stereotype.Service;

import com.yourcompany.db.mapper.RolesMapper;
import com.yourcompany.db.entity.Roles;
import com.yourcompany.db.service.RolesService;

import com.baomidou.framework.service.impl.SuperServiceImpl;

/**
 *
 * @author summerrains
 * @Date 2017-03-28 15:23:21
 */
@Service("rolesService")
public class RolesServiceImpl extends SuperServiceImpl<RolesMapper,Roles> implements RolesService {

}
