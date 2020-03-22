package xyz.wadewhy.before.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
/**
 * @Author 钟子豪
 * @Date 2020/3/22
 * @description
 No such property: code for class: Script1
 * @Return
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String requestURI = request.getRequestURI();
		Object student = request.getSession().getAttribute("student");
		if(student == null){
			//登录失效
			System.err.println("链接"+requestURI+"进入拦截器");
			String header = request.getHeader("X-Requested-With");
			//表示未登录或者登录失效
			if("XMLHttpRequest".equals(header)){
				//是不是ajax请求
				Map<String, String> ret = new HashMap<String, String>();
				ret.put("type", "error");
				ret.put("msg", "登录会话超时或还未登录，请重新登录");
				response.getWriter().write(JSONUtil.parseObj(ret).toString());
				return false;
			}
			//表示普通链接跳转，直接重定向到登录界面
			response.sendRedirect(request.getServletContext().getContextPath() + "/home/login");
			return false;
		}
		return true;
	}

}
