package xyz.wadewhy.cache;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import xyz.wadewhy.after.sys.domain.Permission;
import xyz.wadewhy.after.sys.domain.Role;

/**
 * @PACKAGE_NAME: xyz.wadewhy.cache
 * @NAME: MenuAspectCache redis做缓存
 * @Author: 钟子豪
 * @DATE: 2020/7/2
 * @MONTH_NAME_FULL: 七月
 * @DAY: 02
 * @DAY_NAME_FULL: 星期四
 * @PROJECT_NAME: OnlineExam
 **/
/*@Component
@EnableAspectJAutoProxy
@Aspect*/
@Component
@EnableAspectJAutoProxy
@Aspect
public class PermissionAspectCache {
    //获取日志记录器Logger，名字为本类类名
    private static Logger logger = Logger.getLogger(PermissionAspectCache.class);
    private final static String PONIT_queryPIdById = "execution(* xyz.wadewhy.after.sys.service.impl.PermissionServiceImpl.queryPIdById(..))";
    private final static String RoleIds_queryPIdById = "queryPIdById";
    private final static String PONIT_queryMenuById = "execution(* xyz.wadewhy.after.sys.service.impl.PermissionServiceImpl.queryMenuById(..))";
    private final static String queryMenuById_KEY = "queryMenuById";
    //
    @Autowired
    private JedisPool jedisPool;
    Object setKey(String key, ProceedingJoinPoint proceed, Class<?> integerClass) throws Throwable {
        Jedis jedis = jedisPool.getResource();
        String json = jedis.get(key);
        if (null == json) {// 2.为空
            logger.info(key+"---------------------->缓存里面没有数据，去查询数据库并存入缓存");
            // 放行，去查找
            Object object = proceed.proceed();
            // 然后再redis里保存【由于查出来的是List对象，以json格式存储比较好】
            jedis.set(key, JSON.toJSONString(object));
            jedis.close();
            return object;
        } else {// 不为空
            logger.info(key+"---------------------->缓存里面有数据，直接返回");
            jedis.close();
            return JSON.parseArray(json, integerClass);
        }
    }

/*   private final static String PONIT_PermissionfindList = "execution(* xyz.wadewhy.after.sys.service.impl.MenuServiceImpl.findList(..))";
    private final static String PermissionfindList_KEY = "queryMenuById";
    @Around(value = PONIT_PermissionfindList)
    public Object cacheadd_role_user(ProceedingJoinPoint proceed) throws Throwable {
        logger.info("execution(* xyz.wadewhy.after.sys.service.impl.MenuServiceImpl.findList(..))");
        return setKey(PermissionfindList_KEY, proceed, Permission.class);
    }*/
    @Around(value = PONIT_queryPIdById)
    public Object cacheRoleIdsByUserId(ProceedingJoinPoint proceed) throws Throwable {
        logger.info("execution(* xyz.wadewhy.after.sys.service.impl.PermissionServiceImpl.queryPIdById(..))");
        return  setKey(RoleIds_queryPIdById,proceed,Permission.class);
    }
    @Around(value = PONIT_queryMenuById)
    public Object cacheQueryMenuById (ProceedingJoinPoint proceed) throws Throwable {
        logger.info("execution(* xyz.wadewhy.after.sys.service.impl.PermissionServiceImpl.queryMenuById(..))");
        return  setKey(queryMenuById_KEY,proceed,Permission.class);
    }
    /*=========================================PermissionServiceImpl更新和删除和添加Permission===================================================*/
    Object EditAddDelRole(String key, ProceedingJoinPoint proceed, Class<?> integerClass) throws Throwable {
        Jedis jedis = jedisPool.getResource();
        // 放行，去修改
        Object object = proceed.proceed();
        String json = jedis.get(key);
        // 然后再redis里保存【由于查出来的是List对象，以json格式存储比较好】
        if (object!=null){
            jedis.set(key, JSON.toJSONString(object));
            logger.info(key+"---------------------->更新缓存数据"+JSON.toJSONString(object));
            jedis.close();
            return object;
        }else {//表示修改失败时，不更新里面的值
            jedis.close();
            return JSON.parseArray(json, integerClass);
        }

    }
/*    private final static String PONIT_RoleEdit = "execution(* xyz.wadewhy.after.sys.service.impl.PermissionServiceImpl.edit(..))";
    @Around(value = PONIT_RoleEdit)
    public Object cacheRoleEdit(ProceedingJoinPoint proceed) throws Throwable {
        logger.info("execution(* xyz.wadewhy.after.sys.service.impl.PermissionServiceImpl.edit(..))");
        return EditAddDelRole(queryMenuById_KEY, proceed, Role.class);
    }*/

/*    private final static String PONIT_RoleDeleteById = "execution(* xyz.wadewhy.after.sys.service.impl.PermissionServiceImpl.deleteById(..))";
    @Around(value = PONIT_RoleDeleteById)
    public Object cacheRoleDeleteById(ProceedingJoinPoint proceed) throws Throwable {
        logger.info("execution(* xyz.wadewhy.after.sys.service.impl.PermissionServiceImpl.deleteById(..))");
        return EditAddDelRole(queryMenuById_KEY, proceed, Role.class);
    }*/

//    private final static String PONIT_Permissionadd = "execution(* xyz.wadewhy.after.sys.service.impl.PermissionServiceImpl.add(..))";

}

