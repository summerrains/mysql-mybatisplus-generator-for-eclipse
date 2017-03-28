package com.yourcompany.db.service.impl;

import org.springframework.stereotype.Service;

import com.yourcompany.db.mapper.PermissionsResourcesMapper;
import com.yourcompany.db.entity.PermissionsResources;
import com.yourcompany.db.service.PermissionsResourcesService;

import com.baomidou.framework.service.impl.SuperServiceImpl;

/**
 *
 * @author summerrains
 * @Date 2017-03-28 15:23:21
 */
@Service("permissionsResourcesService")
public class PermissionsResourcesServiceImpl extends SuperServiceImpl<PermissionsResourcesMapper,PermissionsResources> implements PermissionsResourcesService {

}
