/**   
 * Copyright © 2020 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: xyz.wadewhy.homeservice
 * @author: 钟子豪   网站wadewhy.xyz
 * @date: 2020年3月11日 下午9:28:55 
 */
package xyz.wadewhy.after.sys.service;

import xyz.wadewhy.after.sys.common.DataGridView;
import xyz.wadewhy.after.sys.domain.Permission;
import xyz.wadewhy.after.sys.domain.Role_Permission;
import xyz.wadewhy.after.sys.vo.PermissionVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @author 钟子豪
* 作者 E-mail:wadewhy@yeah.net
* @version 创建时间：2020年3月11日 下午9:28:55
* 类说明
*/
/** 
* @ClassName: PersionService 
* @Description: TODO
* @author: wadewhy
* @date: 2020年3月11日 下午9:28:55  
*/
public interface PermissionService {
    /**
     * 根据用户ID查询权限
     */
    List<String> queryPermissionByUserId(Integer userId);

    /**
     * 从菜单和权限中查询出权限
     * id=pis即可
     * @param pids
     * @return
     */
    List<Permission> queryPIdById(String type ,Set<Integer> pids);

    /**
     * 查询顶层菜单
     * @return
     */
    List<Permission> findTopList();


    /**
     * 查询用户下的所有
     * @param pids
     * @return
     */
    List<Permission> queryMenuById(Set<Integer> pids);
    /**
     * 根据ids查询permission
     * @param
     * @return
     */
    List<Role_Permission> queryRole_Permissions(Integer rid);

    int deleteByRoleId(Integer rid);

    int add(Role_Permission authority);

    List<Integer> findRolePermissionByPid(Integer id);

    /**
     * 根据ids查询permission
     * @param perId
     * @return
     */
//    List<Permission> queryPermissionByIds(List<Integer> perId);
}
