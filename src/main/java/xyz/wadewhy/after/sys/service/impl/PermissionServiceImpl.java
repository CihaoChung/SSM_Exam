/**   
 * Copyright © 2020 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: xyz.wadewhy.homeservice.impl
 * @author: 钟子豪   网站wadewhy.xyz
 * @date: 2020年3月11日 下午9:28:26 
 */
package xyz.wadewhy.after.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.wadewhy.after.sys.common.DataGridView;
import xyz.wadewhy.after.sys.domain.Permission;
import xyz.wadewhy.after.sys.domain.Role_Permission;
import xyz.wadewhy.after.sys.mapper.PermissionMapper;
import xyz.wadewhy.after.sys.service.PermissionService;
import xyz.wadewhy.after.sys.vo.PermissionVo;


/**
* @author 钟子豪
* 作者 E-mail:wadewhy@yeah.net
* @version 创建时间：2020年3月11日 下午9:28:26
* 类说明
*/
/** 
* @ClassName: PermissionServiceImpl 
* @Description: TODO
* @author: wadewhy
* @date: 2020年3月11日 下午9:28:26  
*/
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    // 根据userid查询权限权限，多表关联
    public List<String> queryPermissionByUserId(Integer userId) {
        List<Permission> list = permissionMapper.queryPermissionByUserId(userId);
        List<String> permissions = new ArrayList<String>();
        for (Permission permission : list) {
            permissions.add(permission.getPercode());
        }
        return permissions;
    }

    @Override
    public List<Permission> queryPIdById(String type,Set<Integer> pids) {
        return permissionMapper.queryPIdById(type,pids);
    }
    /**
     * 查询顶层菜单
     * @return
     */
    @Override
    public List<Permission> findTopList() {
        return permissionMapper.findTopList();
    }


    @Override
    public List<Permission> queryMenuById(Set<Integer> pids) {
        return permissionMapper.queryMenuById(pids);
    }


    @Override
    public List<Role_Permission> queryRole_Permissions(Integer rid) {
        return permissionMapper.queryRole_Permissions(rid);
    }

    @Override
    public int deleteByRoleId(Integer rid) {
        return permissionMapper.deleteByRoleId(rid);
    }

    @Override
    public int add(Role_Permission authority) {
        return permissionMapper.add(authority);
    }

    @Override
    public List<Integer> findRolePermissionByPid(Integer pid) {
        return permissionMapper.findRolePermissionByPid(pid);
    }


}
