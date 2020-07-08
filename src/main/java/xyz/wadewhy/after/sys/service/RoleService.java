/**   
 * Copyright © 2020 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: xyz.wadewhy.homeservice
 * @author: 钟子豪   网站wadewhy.xyz
 * @date: 2020年3月11日 下午9:29:44 
 */
package xyz.wadewhy.after.sys.service;

import xyz.wadewhy.after.sys.domain.Role;

import java.util.List;
import java.util.Map;

/**
* @author 钟子豪
* 作者 E-mail:wadewhy@yeah.net
* @version 创建时间：2020年3月11日 下午9:29:44
* 类说明
*/
/** 
* @ClassName: RoleService 
* @Description: TODO
* @author: wadewhy
* @date: 2020年3月11日 下午9:29:44  
*/
public interface RoleService {

    /**
     * 根据用户ID查询用户角色
     */
    List<Integer> queryRoleIdsByUserId(Integer userId);

    /**
     * 根据角色id查询权限id
     * @param rid
     * @return
     */
    List<Integer> queryRolePermissionIdsByRid(Integer rid);

    int delete(Integer pid);

    List<Role> findList(Map<String, Object> queryMap);

    int getTotal(Map<String, Object> queryMap);

    List<Role> add(Role role);

    List<Role> edit(Role role);

    List<Role> deleteById(Integer id);

    List<Role> findRoleByUid(List<Integer> ids);

    List<Role> findAllRole();


    int add_role_user( Integer rid,Integer uid);

    int delete_role_user(Integer uid);
}
