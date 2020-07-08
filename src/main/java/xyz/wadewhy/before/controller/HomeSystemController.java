package xyz.wadewhy.before.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.JedisPool;
import xyz.wadewhy.after.bus.domain.Student;
import xyz.wadewhy.after.bus.service.SubjectService;

/**
 * @PACKAGE_NAME: xyz.wadewhy.before.controller
 * @NAME: SystemController
 * @Author: 钟子豪
 * @DATE: 2020/3/20
 * @MONTH_NAME_FULL: 三月
 * @DAY: 20
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 **/
@Controller
@RequestMapping("/before/sys")
public class HomeSystemController {
    @Autowired
    private SubjectService subjectService;

    /**
     * 前台首页
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView model) {
        return model;
    }

    /**
     * 注册
     * 
     * @param model
     * @return
     */
    @Autowired
    private JedisPool jedisPool;
    @RequestMapping(value = "/register")
    public ModelAndView register(ModelAndView model) {
        jedisPool.getResource().del("SubjectfindList");
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("offset", 0);
        queryMap.put("pageSize", 99999);
        model.addObject("subjectList", subjectService.findList(queryMap));
        model.addObject("title", "用户注册");
        model.setViewName("before/home/register");
        return model;
    }

    /**
     * 前台用户登录
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView model) {
        model.addObject("title", "用户登录");
        model.setViewName("before/home/login");
        return model;
    }

    /**
     * 考生中心首页
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/toIndex", method = RequestMethod.GET)
    public ModelAndView toIndex(ModelAndView model) {

        model.addObject("title", "考生中心");
        model.setViewName("before/userHome/index");
        return model;
    }

    /**
     * 用户修改密码
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public ModelAndView password(ModelAndView model, HttpServletRequest request) {
        Student student = (Student) request.getSession().getAttribute("student");
        model.addObject("student", student);
        model.setViewName("before/userHome/password");
        return model;
    }

    /**
     * 退出登录
     * 
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public ModelAndView logout(ModelAndView model, HttpServletRequest request) {
        request.getSession().setAttribute("student", null);
        model.setViewName("before/home/login");
        return model;
    }

    /**
     * 用户基本信息页面
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("profile")
    public ModelAndView profile(ModelAndView model, HttpServletRequest request) {
        Student student = (Student) request.getSession().getAttribute("student");
        model.addObject("title", "考生信息");
        model.addObject("student", student);
        model.addObject("subject", subjectService.findSubjectById(student.getSubjectid()));
        model.setViewName("before/userHome/profile");
        return model;
    }

}
