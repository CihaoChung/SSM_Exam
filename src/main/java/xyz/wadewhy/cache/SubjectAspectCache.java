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
import xyz.wadewhy.after.bus.domain.Question;
import xyz.wadewhy.after.bus.domain.Subject;

/**
 * @PACKAGE_NAME: xyz.wadewhy.cache
 * @NAME: QuitAspectCache
 * @Author: 钟子豪
 * @DATE: 2020/7/6
 * @MONTH_NAME_FULL: 七月
 * @DAY: 06
 * @DAY_NAME_FULL: 星期一
 * @PROJECT_NAME: OnlineExam
 **/
@Component
@EnableAspectJAutoProxy
    //题目不需要做缓存
@Aspect
public class SubjectAspectCache {
    //获取日志记录器Logger，名字为本类类名
    private static Logger logger = Logger.getLogger(SubjectAspectCache.class);
    private final static String PONIT_SubjectfindList = "execution(* xyz.wadewhy.after.bus.service.impl.SubjectServiceImpl.findList(..))";
    private final static String SubjectfindList = "SubjectfindList";

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
    @Around(value = PONIT_SubjectfindList)
    public Object cacheQuestionfindList(ProceedingJoinPoint proceed) throws Throwable {
        logger.info("execution(* xyz.wadewhy.after.bus.service.impl.SubjectServiceImpl.findList(..))");
        return  setKey(SubjectfindList,proceed, Subject.class);
    }
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
    private final static String PONIT_editSubject = "execution(* xyz.wadewhy.after.bus.service.impl.SubjectServiceImpl.edit(..))";
    @Around(value = PONIT_editSubject)
    public Object cacheeditSubject(ProceedingJoinPoint proceed) throws Throwable {
        logger.info("execution(* xyz.wadewhy.after.bus.service.impl.SubjectServiceImpl.UpdateQuestion(..))");
        return EditAddDelRole(SubjectfindList, proceed, Question.class);
    }

    private final static String PONIT_deleteSubjectById = "execution(* xyz.wadewhy.after.bus.service.impl.SubjectServiceImpl.deleteSubjectById(..))";
    @Around(value = PONIT_deleteSubjectById)
    public Object cacheRoleEdit(ProceedingJoinPoint proceed) throws Throwable {
        logger.info("execution(* xyz.wadewhy.after.bus.service.impl.SubjectServiceImpl.deleteSubjectById(..))");
        return EditAddDelRole(SubjectfindList, proceed, Question.class);
    }





}
