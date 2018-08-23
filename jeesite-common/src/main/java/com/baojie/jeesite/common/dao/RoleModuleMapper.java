package com.baojie.jeesite.common.dao;


import com.baojie.jeesite.common.base.MyMapper;
import com.baojie.jeesite.entity.sys.RoleModule;
import com.baojie.jeesite.entity.sys.RoleUser;

import java.util.List;
import java.util.Set;

/**
 * @author 冀保杰
 * @date 2018-08-10 12:01:11
 */

public interface RoleModuleMapper extends MyMapper<RoleModule> {

    List<String> getMenusByRoleIds(Set<String> roleNameSet);
}