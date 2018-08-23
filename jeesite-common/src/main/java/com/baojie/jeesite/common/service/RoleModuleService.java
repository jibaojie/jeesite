package com.baojie.jeesite.common.service;


import com.baojie.jeesite.common.base.BaseService;
import com.baojie.jeesite.common.dao.RoleModuleMapper;
import com.baojie.jeesite.common.dao.RoleUserMapper;
import com.baojie.jeesite.entity.sys.RoleModule;
import com.baojie.jeesite.entity.sys.RoleUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


/**
 * @author 冀保杰
 * @date 2018-08-10 12:01:11
 */
@Service
public class RoleModuleService extends BaseService<RoleModuleMapper, RoleModule> {


    /**
     * 根据用户id 获取用户权限
     * @param roleNameSet
     * @return
     */
    public List<String> getMenusByRoleIds(Set<String> roleNameSet) {
        return mapper.getMenusByRoleIds(roleNameSet);
    }
}