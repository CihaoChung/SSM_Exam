package xyz.wadewhy.after.sys.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import xyz.wadewhy.after.sys.common.ActiverUser;
import xyz.wadewhy.after.sys.common.MenuUtil;
import xyz.wadewhy.after.sys.common.WebUtils;
import xyz.wadewhy.after.sys.domain.Permission;
import xyz.wadewhy.after.sys.domain.User;
import xyz.wadewhy.after.sys.service.PermissionService;
import xyz.wadewhy.after.sys.service.RoleService;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.interceptor
 * @NAME: LoginInterceptor
 * @Author: 钟子豪
 * @DATE: 2020/3/14
 * @MONTH_NAME_FULL: 三月
 * @DAY: 14
 * @DAY_NAME_FULL: 星期六
 * @PROJECT_NAME: OnlineExam 后台登录拦截器
 **/
//@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    // 获取日志记录器Logger，名字为本类类名
    private static Logger logger = Logger.getLogger(LoginInterceptor.class);
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
            throws Exception {
        logger.info("----------------------------preHandle----------------------------------");
        ActiverUser activerUser = (ActiverUser) WebUtils.getHttpSession().getAttribute("user");
        // 前台注册
        if (null == activerUser) {
            return true;
        }
        User user = activerUser.getUser();
        if (null != user) {
            // 存在该用户
            // 根据用户ID查询拥有的角色ID
            List<Integer> roleIds = roleService.queryRoleIdsByUserId(user.getId());
            logger.info(
                    "----------------LoginInterceptor------------roleService.queryRoleIdsByUserId----------------------------------");
            // 根据角色ID取得拥有权限和菜单ID
            Set<Integer> pids = new HashSet<>();
            for (Integer rid : roleIds) {
                // 根据角色Id查询出拥有权限和菜单
                List<Integer> permissionIds = roleService.queryRolePermissionIdsByRid(rid);
                pids.addAll(permissionIds);
            }
            // 得到用户下拥有的菜单
            List<Permission> userMenus = new ArrayList<>();
            // 根据角色ID查询权限【权限菜单放在同一表中】
            if (pids.size() > 0) {// 查询到了菜单和权限
                // 只查询出权限的数据
                userMenus = permissionService.queryMenuById(pids);
                WebUtils.getSession().setAttribute("userMenus", userMenus);
            }

            /******************************************/
            // 获取菜单id
            String mid = httpServletRequest.getParameter("_mid");
            if (!StringUtils.isEmpty(mid)) {
                // 不为空
                List<Permission> allThirdMenu = MenuUtil.getAllThirdMenu(
                        (List<Permission>) httpServletRequest.getSession().getAttribute("userMenus"),
                        Integer.valueOf(mid));
//                System.err.println("allThirdMenu"+allThirdMenu);
                /*
                 * for (Permission p:allThirdMenu ) { System.err.println("【】"+p.toString()); }
                 */
                httpServletRequest.setAttribute("thirdMenuList", allThirdMenu);
            }
            /************************************************/
            WebUtils.getSession().setAttribute("topMenuList", MenuUtil.getAllTopMenu(userMenus));
            /*************************************************/
            WebUtils.getSession().setAttribute("roleList", roleService.findList(new HashMap<String, Object>()));
            return true;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Object o, Exception e) throws Exception {

    }
}
