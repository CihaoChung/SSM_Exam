/**   
 * Copyright © 2020 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: xyz.wadewhy.homeservice
 * @author: 钟子豪   网站wadewhy.xyz
 * @date: 2020年3月11日 下午9:29:34 
 */
package xyz.wadewhy.after.sys.service;

import xyz.wadewhy.after.sys.domain.User;
import xyz.wadewhy.after.sys.vo.UserVo;

import java.util.List;
import java.util.Map;


/**
* @author 钟子豪
* 作者 E-mail:wadewhy@yeah.net
* @version 创建时间：2020年3月11日 下午9:29:34
* 类说明
*/
/** 
* @ClassName: UserService 
* @Description: TODO
* @author: wadewhy
* @date: 2020年3月11日 下午9:29:34  
*/
public interface UserService {
    //根据用户名查询用户
    User queryUserByUserName(String username);
    //保存用户
    int insertSelective(UserVo uservo);

    int deleteRoleUserByRid(Integer id);

    List<User> findList(Map<String, Object> queryMap);

    int getTotal(Map<String, Object> queryMap);

    User findByUsername(String name);

    int add(User user);

    /**
     * 通过rid查询uid
     * @param roleId
     * @return
     */
    List<Integer> queryUidByRid(Integer roleId);

    int edit(User user);

    int deleteRoleUserByUid(String ids);
//删除用户
    int deleteUserByids(String ids);
}
