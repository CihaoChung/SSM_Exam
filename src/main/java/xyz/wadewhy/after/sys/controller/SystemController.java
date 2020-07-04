package xyz.wadewhy.after.sys.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import xyz.wadewhy.after.sys.common.MenuUtil;
import xyz.wadewhy.after.sys.common.WebUtils;
import xyz.wadewhy.after.sys.domain.Permission;
import xyz.wadewhy.after.sys.domain.User;
import xyz.wadewhy.after.sys.service.PermissionService;
import xyz.wadewhy.after.sys.service.UserService;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.controller
 * @NAME: SystemController
 * @Author: 钟子豪
 * @DATE: 2020/3/13
 * @MONTH_NAME_FULL: 三月
 * @DAY: 13
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 * 用于跳转的控制层
 **/
@Controller
@RequestMapping("/after/sys")
public class SystemController {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    //打印日志
    public static final Logger logger = LoggerFactory.getLogger(SystemController.class);

    /**
     * 跳转到登录界面
     *
     * @return
     */
    @RequestMapping("toLogin")
    public String toLogin() {
//        System.err.println("daole....................................");
        logger.info("跳转到登录界面toLogin");
        return "after/system/index/login";
    }

    /**
     * 跳转到首页index.jsp
     *
     * @return
     */
    @RequestMapping("index")
    public ModelAndView index(ModelAndView model) {
        logger.info("跳转到首页index.jsp");
        //查询用户拥有的菜单

        List<Permission> userMenus = (List<Permission>) WebUtils.getSession().getAttribute("userMenus");
        //顶级菜单
//        model.addObject("topMenuList", MenuUtil.getAllTopMenu(userMenus));
        //二级菜单
        model.addObject("secondMenuList", MenuUtil.getAllSecondMenu(userMenus));
        model.setViewName("after/system/index/index");
//        return "after/system/index/index";
        return model;
    }

    /**
     * 跳转菜单管理
     *
     * @return
     */
    @RequestMapping("toMenu")
    public ModelAndView toPermission(ModelAndView model) {
        model.addObject("topList", permissionService.findTopList());
        //跳转menu下list.jsp
        model.setViewName("after/system/menu/list");
        return model;
    }



    /**
     * 跳转角色管理页面
     * @return
     */
    @RequestMapping("toRole")
    public String toRole(){
        return "after/system/role/list";
    }

    /**
     * 跳转用户管理
     * @return
     */
    @RequestMapping("toUser")
    public String toUser(){
        return "after/system/user/list";
    }


    /**
     * 日志列表页面
     * @param model
     * @return
     */
    @RequestMapping(value="/toLog",method= RequestMethod.GET)
    public ModelAndView toLog(ModelAndView model){
        model.setViewName("after/system/log/list");
        return model;
    }
    /**
     * 系统登录后的欢迎页
     * @param model
     * @return
     */
    @RequestMapping(value="/welcome",method=RequestMethod.GET)
    public ModelAndView welcome(ModelAndView model){
        model.setViewName("after/system/index/welcome");
        return model;
    }
}
