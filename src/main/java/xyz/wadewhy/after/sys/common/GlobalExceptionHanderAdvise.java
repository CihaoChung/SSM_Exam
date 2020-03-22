package xyz.wadewhy.after.sys.common;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @PACKAGE_NAME: xyz.wadewhy.util
 * @NAME: GlobalExceptionHanderAdvise
 * @Author: 钟子豪
 * @DATE: 2020/3/12
 * @MONTH_NAME_FULL: 三月
 * @DAY: 12
 * @DAY_NAME_FULL: 星期四
 * @PROJECT_NAME: Shiro_SpringBoot
 * 异常处理类
 **/
@RestControllerAdvice
public class GlobalExceptionHanderAdvise {
    /**
     * 未授权
     */
    @ExceptionHandler(value={UnauthorizedException.class})
    public Object unauthorized(){
        Map<String,Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "未授权");
        return map;
    }
}
