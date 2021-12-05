package com.zjx.aclservice.service;

import com.zjx.aclservice.entity.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色权限 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface RolePermissionService extends IService<RolePermission> {
    //新增加一个角色信息
    void save2(String roleId);
}
