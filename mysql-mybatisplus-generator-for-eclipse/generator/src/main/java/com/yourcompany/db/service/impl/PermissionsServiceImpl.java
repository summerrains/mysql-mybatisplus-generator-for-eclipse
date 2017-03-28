package com.yourcompany.db.service.impl;

import org.springframework.stereotype.Service;

import com.yourcompany.db.mapper.PermissionsMapper;
import com.yourcompany.db.entity.Permissions;
import com.yourcompany.db.service.PermissionsService;

import com.baomidou.framework.service.impl.SuperServiceImpl;

/**
 *
 * @author summerrains
 * @Date 2017-03-28 15:23:21
 */
@Service("permissionsService")
public class PermissionsServiceImpl extends SuperServiceImpl<PermissionsMapper,Permissions> implements PermissionsService {

}
