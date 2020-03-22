/**   
 * Copyright © 2020 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: xyz.wadewhy.homeservice.impl
 * @author: 钟子豪   网站wadewhy.xyz
 * @date: 2020年3月11日 下午9:30:04 
 */
package xyz.wadewhy.after.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.wadewhy.after.sys.domain.Role;
import xyz.wadewhy.after.sys.mapper.RoleMapper;
import xyz.wadewhy.after.sys.mapper.UserMapper;
import xyz.wadewhy.after.sys.service.RoleService;


/**
* @author 钟子豪
* 作者 E-mail:wadewhy@yeah.net
* @version 创建时间：2020年3月11日 下午9:30:04
* 类说明
*/
/** 
* @ClassName: RoleServiceImpl 
* @Description: TODO
* @author: wadewhy
* @date: 2020年3月11日 下午9:30:04  
*/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户id查询用户角色
     * @param userId
     * @return
     */
    public List<Integer> queryRoleIdsByUserId(Integer userId) {
        List<Integer> list = roleMapper.queryRoleIdsByUserId(userId);
        return list;
    }

    /**
     * 根据角色ID查询当前角色拥有的所有的权限或菜单ID
     * @param rid
     * @return
     */
    public  List<Integer> queryRolePermissionIdsByRid(Integer rid){
        return roleMapper.queryRolePermissionIdsByRid(rid);
    }

    @Override
    public int delete(Integer pid) {
        return roleMapper.deleteByPid(pid);
    }

    @Override
    public List<Role> findList(Map<String, Object> queryMap) {
        return roleMapper.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return roleMapper.getTotal(queryMap);
    }
    @Override
    public int add(Role role){
        return roleMapper.insert(role);
    }

    @Override
    public int edit(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }
    public int deleteById(Integer id){
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Role> findRoleByUid(List<Integer> ids) {
        return roleMapper.findRoleByUid(ids);
    }

    @Override
    public List<Role> findAllRole() {
        return roleMapper.findAllRole();
    }

    /**
     * 在插入
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public int add_role_user(Integer rid,Integer uid) {
        return userMapper.add_role_user(rid,uid);
    }

    /**
     * 给用户赋予角色时，先清空
     * @return
     */
    @Override
    public int delete_role_user(Integer uid) {
        return userMapper.delete_role_user(uid);
    }


}
