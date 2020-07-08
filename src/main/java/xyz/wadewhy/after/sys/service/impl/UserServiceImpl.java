/**   
 * Copyright © 2020 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: xyz.wadewhy.homeservice.impl
 * @author: 钟子豪   网站wadewhy.xyz
 * @date: 2020年3月11日 下午9:30:12 
 */
package xyz.wadewhy.after.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import xyz.wadewhy.after.sys.domain.User;
import xyz.wadewhy.after.sys.mapper.UserMapper;
import xyz.wadewhy.after.sys.service.UserService;
import xyz.wadewhy.after.sys.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
* @author 钟子豪
* 作者 E-mail:wadewhy@yeah.net
* @version 创建时间：2020年3月11日 下午9:30:12
* 类说明
*/
/** 
* @ClassName: UserServiceImpl 
* @Description: TODO
* @author: wadewhy
* @date: 2020年3月11日 下午9:30:12  
*/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /* (non-Javadoc)
     * @see xyz.wadewhy.homeservice.UserService#queryUserByUsername(java.lang.String)
     */
    @Cacheable(cacheNames="queryUserByUserName",key="#username")
    public User queryUserByUserName(String username) {

        return userMapper.queryUserByUserName(username);
    }
    public int insertSelective(UserVo userVo){
       return  userMapper.insertSelective(userVo);
    }

    @Override
    public int deleteRoleUserByRid(Integer rid) {
        return userMapper.deleteRoleUserByRid(rid);
    }

    @Override
    public List<User> findList(Map<String, Object> queryMap) {

        return userMapper.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return userMapper.getTotal(queryMap);
    }

    @Override
    public User findByUsername(String name) {
        return userMapper.queryUserByUserName(name);
    }

    @Override
    public int add(User user) {
        return userMapper.insert(user);
    }

    @Override
    public List<Integer> queryUidByRid(Integer roleId) {
        return userMapper.queryUidByRid(roleId);
    }

    @Override
    public int edit(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteRoleUserByUid(String ids) {
        return userMapper.deleteRoleUserByUid(ids);
    }

    @Override
    public int deleteUserByids(String ids) {
        return userMapper.deleteUserByids(ids);
    }

}
