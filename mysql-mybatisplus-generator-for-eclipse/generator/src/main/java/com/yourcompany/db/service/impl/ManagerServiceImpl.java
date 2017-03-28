package com.yourcompany.db.service.impl;

import org.springframework.stereotype.Service;

import com.yourcompany.db.mapper.ManagerMapper;
import com.yourcompany.db.entity.Manager;
import com.yourcompany.db.service.ManagerService;

import com.baomidou.framework.service.impl.SuperServiceImpl;

/**
 *
 * @author summerrains
 * @Date 2017-03-28 15:23:21
 */
@Service("managerService")
public class ManagerServiceImpl extends SuperServiceImpl<ManagerMapper,Manager> implements ManagerService {

}
