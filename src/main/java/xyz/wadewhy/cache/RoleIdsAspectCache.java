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
    private final static String PONIT_RoleIds = "execution(* xyz.wadewhy.after.sys.service.impl.queryRoleIdsByUserId(..))";
    private final static String RoleIds_KEY = "RoleIds_ByUserId";
    @Autowired
    private JedisPool jedisPool;

    @Around(value = PONIT_RoleIds)
    public Object cacheRoleIdsByUserId(ProceedingJoinPoint proceed) throws Throwable {
        // 1.先从redis里面找
        // 得到redis
        Jedis jedis = jedisPool.getResource();
        // 获取
        String json = jedis.get(RoleIds_KEY);
        if (null == json) {// 2.为空
            logger.info("cacheRoleIdsByUserId---------------------->缓存里面没有数据，去查询数据库并存入缓存");
            System.err.println("cacheRoleIdsByUserId---------------------->缓存里面没有数据，去查询数据库并存入缓存");
            // 放行，去查找
            Object object = proceed.proceed();
            // 然后再redis里保存【由于查出来的是List对象，以json格式存储比较好】
            jedis.set(RoleIds_KEY, JSON.toJSONString(object));
            return object;
        } else {// 不为空
            //
            logger.info("cacheRoleIdsByUserId---------------------->已从缓存找到数据，直接返回");
            System.err.println("cacheRoleIdsByUserId---------------------->已从缓存找到数据，直接返回");
            return JSON.parseArray(json, Integer.class);
        }
    }
}
