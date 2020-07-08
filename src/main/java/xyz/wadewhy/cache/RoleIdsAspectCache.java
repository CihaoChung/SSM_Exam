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
public class RoleIdsAspectCache {
    //获取日志记录器Logger，名字为本类类名
    private static Logger logger = Logger.getLogger(RoleIdsAspectCache.class);
    //角色
    /*==============================================RoleServiceImpl查询缓存===================================================*/
    private final static String PONIT_RoleIds = "execution(* xyz.wadewhy.after.sys.service.impl.RoleServiceImpl.queryRoleIdsByUserId(..))";
    private final static String RoleIds_KEY = "queryRoleIdsByUserId";

    private final static String PONIT_PermissionIds = "execution(* xyz.wadewhy.after.sys.service.impl.RoleServiceImpl.queryRolePermissionIdsByRid(..))";
    private final static String PermissionIds_KEY = "queryRolePermissionIdsByRid";

    private final static String PONIT_RolefindList = "execution(* xyz.wadewhy.after.sys.service.impl.RoleServiceImpl.findList(..))";
    private final static String RolefindList_KEY = "RolefindList";
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
    @Around(value = PONIT_RoleIds)
    public Object cacheRoleIdsByUserId(ProceedingJoinPoint proceed) throws Throwable {
        logger.info("execution(* xyz.wadewhy.after.sys.service.impl.RoleServiceImpl.cacheRoleIdsByUserId(..))");
        return  setKey(RoleIds_KEY,proceed,Integer.class);
    }
    @Around(value = PONIT_PermissionIds)
    public Object cacheRolePermissionIdsByRid(ProceedingJoinPoint proceed) throws Throwable {
        logger.info("execution(* xyz.wadewhy.after.sys.service.impl.RoleServiceImpl.cacheRolePermissionIdsByRid(..))");
        return  setKey(PermissionIds_KEY,proceed,Integer.class);
    }
    @Around(value = PONIT_RolefindList)
    public Object cacheRolefindList(ProceedingJoinPoint proceed) throws Throwable {
        logger.info("execution(* xyz.wadewhy.after.sys.service.impl.RoleServiceImpl.cacheRolefindList(..))");
        return setKey(RolefindList_KEY, proceed, Role.class);
    }
/*=========================================RoleServiceImpl更新和删除和添加Role===================================================*/
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
    private final static String PONIT_RoleEdit = "execution(* xyz.wadewhy.after.sys.service.impl.RoleServiceImpl.edit(..))";
    @Around(value = PONIT_RoleEdit)
    public Object cacheRoleEdit(ProceedingJoinPoint proceed) throws Throwable {
        logger.info("execution(* xyz.wadewhy.after.sys.service.impl.RoleServiceImpl.edit(..))");
        return EditAddDelRole(RolefindList_KEY, proceed, Role.class);
    }

    private final static String PONIT_RoleDeleteById = "execution(* xyz.wadewhy.after.sys.service.impl.RoleServiceImpl.deleteById(..))";
    @Around(value = PONIT_RoleDeleteById)
    public Object cacheRoleDeleteById(ProceedingJoinPoint proceed) throws Throwable {
        logger.info("execution(* xyz.wadewhy.after.sys.service.impl.RoleServiceImpl.deleteById(..))");
        return EditAddDelRole(RolefindList_KEY, proceed, Role.class);
    }

    private final static String PONIT_Roleadd = "execution(* xyz.wadewhy.after.sys.service.impl.RoleServiceImpl.add(..))";
    @Around(value = PONIT_Roleadd)
    public Object cacheadd(ProceedingJoinPoint proceed) throws Throwable {
        logger.info("execution(* xyz.wadewhy.after.sys.service.impl.RoleServiceImpl.add(..))");
        return EditAddDelRole(RolefindList_KEY, proceed, Role.class);
    }
}

