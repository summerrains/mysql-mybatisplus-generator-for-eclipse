package com.yourcompany.db.service.impl;

import org.springframework.stereotype.Service;

import com.yourcompany.db.mapper.ResourcesMapper;
import com.yourcompany.db.entity.Resources;
import com.yourcompany.db.service.ResourcesService;

import com.baomidou.framework.service.impl.SuperServiceImpl;

/**
 *
 * @author summerrains
 * @Date 2017-03-28 15:23:21
 */
@Service("resourcesService")
public class ResourcesServiceImpl extends SuperServiceImpl<ResourcesMapper,Resources> implements ResourcesService {

}
