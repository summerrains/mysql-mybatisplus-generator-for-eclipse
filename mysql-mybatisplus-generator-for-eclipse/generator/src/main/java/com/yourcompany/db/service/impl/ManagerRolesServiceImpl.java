package com.yourcompany.db.service.impl;

import org.springframework.stereotype.Service;

import com.yourcompany.db.mapper.ManagerRolesMapper;
import com.yourcompany.db.entity.ManagerRoles;
import com.yourcompany.db.service.ManagerRolesService;

import com.baomidou.framework.service.impl.SuperServiceImpl;

/**
 *
 * @author summerrains
 * @Date 2017-03-28 15:23:21
 */
@Service("managerRolesService")
public class ManagerRolesServiceImpl extends SuperServiceImpl<ManagerRolesMapper,ManagerRoles> implements ManagerRolesService {

}
