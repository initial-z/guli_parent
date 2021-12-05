package com.zjx.aclservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjx.aclservice.entity.RolePermission;
import com.zjx.aclservice.mapper.RolePermissionMapper;
import com.zjx.aclservice.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    public void save2(String roleId) {
        QueryWrapper<RolePermission> wrapper=new QueryWrapper<RolePermission>();
        RolePermission r=new RolePermission();
        r.setPermissionId("1");
        r.setRoleId(roleId);
        System.out.println(r);
        QueryWrapper<RolePermission> wrapper2=new QueryWrapper<RolePermission>();
        wrapper2.eq("permission_id",1);
        wrapper2.eq("role_id",roleId);
        RolePermission selectOne = baseMapper.selectOne(wrapper2);
        if(selectOne==null){
            baseMapper.insert(r);
        }
    }

}
