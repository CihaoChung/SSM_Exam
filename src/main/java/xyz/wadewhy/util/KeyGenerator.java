/**   
 * Copyright © 2020 eSunny Info. Tech Ltd. All rights reserved.
 * 作者博客：wadewhy.xyz
 * @Package: xyz.wadewhy.util 
 * @author: wadewhy   
 * @date: 2020年7月5日 下午9:15:40 
 */
package xyz.wadewhy.util;

import java.lang.reflect.Method;

public class KeyGenerator implements org.springframework.cache.interceptor.KeyGenerator {
    @Override
    public Object generate(Object o, Method method, Object... params) {
        // 规定 本类名+方法名+参数名 为key
        StringBuilder sb = new StringBuilder();
        sb.append(o.getClass().getName());
        sb.append(method.getName());
        for (Object param : params) {
            sb.append(param.toString());
        }
        return sb.toString();
    }
}
