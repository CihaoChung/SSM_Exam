package xyz.wadewhy.after.sys.common;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.common
 * @NAME: WebUtils
 * @Author: 钟子豪
 * @DATE: 2020/3/13
 * @MONTH_NAME_FULL: 三月
 * @DAY: 13
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 * 网络控制类
 **/
public class WebUtils {
    /**
     * 得到request
     * @return
     */
    public static HttpServletRequest getRequest(){
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }

    /**
     * 得到session
     *
     * @return
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }
    /**
     * 得到当前线程的请求对象
     * @return
     */
    public static HttpServletRequest getHttpServletRequest() {
        return getServletRequestAttributes().getRequest();
    }
    /**
     * 得到session对象
     */
    public static HttpSession getHttpSession() {
        return getHttpServletRequest().getSession();
    }
    /**
     * 清除session对象
     */
    public static void removeHttpSession(){
        getSession().invalidate();
    }
}
