package xyz.wadewhy.after.sys.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.wadewhy.after.sys.common.ActiverUser;
import xyz.wadewhy.after.sys.common.ResultObj;
import xyz.wadewhy.after.sys.common.WebUtils;
import xyz.wadewhy.after.sys.domain.Role;
import xyz.wadewhy.after.sys.service.LogService;
import xyz.wadewhy.after.sys.service.RoleService;
import xyz.wadewhy.after.sys.service.UserService;

import javax.imageio.ImageIO;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.controller
 * @NAME: LoginController
 * @Author: 钟子豪
 * @DATE: 2020/3/13
 * @MONTH_NAME_FULL: 三月
 * @DAY: 13
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 **/
@Controller
@RequestMapping("/after/login")
public class LoginController {
    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private LogService logService;
    /**
     * 利用Hutool工具类得到验证码
     * @param response
     * @param session
     */
    @RequestMapping("getCode")
    public void getCode(HttpServletResponse response,
                        HttpSession session) throws IOException {
        logger.info("利用Hutool工具类得到验证码");
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(110, 30,4,5);
        session.setAttribute("cpacha",lineCaptcha.getCode());
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(lineCaptcha.getImage(),"JPEG",outputStream);
    }

    /**
     * 登录方法
     * @param name
     * @param pwd
     * @param cpacha
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public ResultObj login(String name,String pwd,String cpacha){
        logger.debug(name+pwd+cpacha+"【登录方法】");
        //得到真正的验证码
        String realCode = WebUtils.getHttpSession().getAttribute("cpacha").toString();
        //判断验证码是否正确
        if (null!=cpacha&&cpacha.equals(realCode)){
            //验证码正确
            //得到subject
            Subject subject = SecurityUtils.getSubject();
            AuthenticationToken token = new UsernamePasswordToken(name,pwd);
            try{
                subject.login(token);
                //之后会进入userRealm中认证方法，认证过后授权
                //得到认证通过的用户
                ActiverUser activerUser= (ActiverUser) subject.getPrincipal();
                //将用户保存在session中
                WebUtils.getSession().setAttribute("user",activerUser);
                //查询该用户角色
               List<Role>  rolelist =  roleService.findRoleByUid(activerUser.getRolesId());
               StringBuilder context= new StringBuilder();
                for (Role r: rolelist
                     ) {
                    context.append(r.getName());
                    context.append(",");
//                    context+=r.getName()+",";
                }
                logService.add("用户名为{"+activerUser.getUser().getName()+"}，角色为{"+context+"}的用户登录成功!");
                //返回验证,登录成功
                return ResultObj.LOGIN_SUCCESS;
            }catch (AuthenticationException e){
                logger.info(name+pwd+cpacha+"【登录失败】");
                //认证不成功
                return ResultObj.LOGIN_ERROR_PASS;
            }
        }else{
            //验证码错误。
            return ResultObj.LOGIN_ERROR_CODE;
        }
    }
}
