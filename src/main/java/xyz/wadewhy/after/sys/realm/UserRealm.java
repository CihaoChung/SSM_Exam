/**   
 * Copyright © 2020 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: xyz.wadewhy.realm 
 * @author: 钟子豪   网站wadewhy.xyz
 * @date: 2020年3月11日 下午9:28:00 
 */
package xyz.wadewhy.after.sys.realm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.wadewhy.after.sys.common.ActiverUser;
import xyz.wadewhy.after.sys.common.Constant;
import xyz.wadewhy.after.sys.common.WebUtils;
import xyz.wadewhy.after.sys.domain.Permission;
import xyz.wadewhy.after.sys.domain.User;
import xyz.wadewhy.after.sys.service.PermissionService;
import xyz.wadewhy.after.sys.service.RoleService;
import xyz.wadewhy.after.sys.service.UserService;


/**
* @author 钟子豪
* 作者 E-mail:wadewhy@yeah.net
* @version 创建时间：2020年3月11日 下午9:28:00
* 类说明
*/
/** 
* @ClassName: UserRealm 
* @Description: TODO
* @author: wadewhy
* @date: 2020年3月11日 下午9:28:00
* 认证授权  
*/

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        // 得到用户名
        String username = token.getPrincipal().toString();
        // 根据用户名查询用户
        User user = userService.queryUserByUserName(username);
     if (null!=user){
         // 存在该用户
//         List<String> roles = roleService.queryRolesByUserId(user.getId());
         // 根据用户ID查询拥有的角色ID
         List<Integer> roleIds = roleService.queryRoleIdsByUserId(user.getId());
         //根据角色ID取得拥有权限和菜单ID
         Set<Integer> pids = new HashSet<>();
         for (Integer rid:roleIds
              ) {
             //根据角色Id查询出拥有权限和菜单
             List<Integer> permissionIds = roleService.queryRolePermissionIdsByRid(rid);
             pids.addAll(permissionIds);
         }
         //用于保存权限id
         List<Permission> list = new ArrayList<>();
         //得到用户下拥有的菜单
         List<Permission> userMenus = new ArrayList<>();
         //根据角色ID查询权限【权限菜单放在同一表中】
         if (pids.size()>0){//查询到了菜单和权限
             //只查询出权限的数据
             String type = Constant.TYPE_PERMISSION;
             list=permissionService.queryPIdById(type,pids);
//             userMenus = permissionService.queryMenuById(pids);
//             WebUtils.getSession().setAttribute("userMenus", userMenus);
         }
         //用于保存 权限编码【user:delete】
         List<String> percodes=new ArrayList<>();
         for (Permission permission:list
              ) {
             //权限编码
             percodes.add(permission.getPercode());
         }
         // 构造ActiverUser,保存用户对象，角色，权限
         ActiverUser activerUser = new ActiverUser();
         activerUser.setPermissions(percodes);
         activerUser.setUser(user);
         activerUser.setRolesId(roleIds);
         // 创建加密盐
         ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
         SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(activerUser, user.getPwd(), credentialsSalt, this.getName());
         /**
          * 注意：在return前，会调用到doCredentialsMatch方法，而这个方法就是我们在shiro配置文件中的凭证匹配配置里的方法
          *
          <bean id="credentialsMatcher" class="org.apache.shiro.authc.credential.HashedCredentialsMatcher">
          <property name="hashAlgorithmName" value="md5"></property>
          <property name="hashIterations" value="2"></property>
          </bean>
          *  这个方法将token里的密码【用户界面登录的密码，未加密】通过配置的MD5加密算法加密后
          *  ，和authenticationInfo【数据库里加密的密码】相比较。
          doCredentialsMatch(token,authenticationInfo);
          */
         return authenticationInfo;
     }else{
         throw new UnknownAccountException();
     }


    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ActiverUser activerUser = (ActiverUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = activerUser.getUser();
        List<String> permissions = activerUser.getPermissions();
        if (user.getType()==Constant.USER_TYPE_SUPER){//超级用户
            //如果是用户类型是超级管理设置"*:"
            info.addStringPermission("*:");
        }else {
            if (null != permissions && permissions.size() > 0) {
                info.addStringPermissions(permissions);
            }
        }
        return info;
    }

}
