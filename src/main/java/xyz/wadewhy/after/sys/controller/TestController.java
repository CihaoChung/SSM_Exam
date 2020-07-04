package xyz.wadewhy.after.sys.controller;

import cn.hutool.core.util.IdUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.wadewhy.after.sys.common.Constant;
import xyz.wadewhy.after.sys.domain.User;
import xyz.wadewhy.after.sys.service.UserService;
import xyz.wadewhy.after.sys.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.controller
 * @NAME: TestController
 * @Author: 钟子豪
 * @DATE: 2020/3/13
 * @MONTH_NAME_FULL: 三月
 * @DAY: 13
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 **/
@Controller
@RequestMapping("/after/test")
public class TestController {
    //打印日志
    public static final Logger logger = LoggerFactory.getLogger(SystemController.class);
    @Autowired
    private UserService userService;
    /**
     * 测试项目搭建
     */
    @RequestMapping(value="start",produces ="application/text;charset=utf-8")
    @ResponseBody
    public String TestStrat(HttpServletRequest request,
                            HttpServletResponse response){
//        System.err.println("测试项目搭建");
        return "index";
    }

    /**
     * 添加用户
     * @param name
     * @param pwd
     * @param address
     */
    @RequestMapping("addUser")
    public void addUser(String name,String pwd,String address){
//        System.err.println(name+pwd+address+"【添加用户测试】");
        UserVo userVo = new UserVo();
        userVo.setName(name);
        userVo.setAddress(address);
        //创建加密盐
        String salt = IdUtil.simpleUUID().toUpperCase();
        userVo.setSalt(salt);
        //加密
        userVo.setPwd(new Md5Hash(pwd, salt, 2).toString());
        //保存用户
        userService.insertSelective(userVo);
    }
    @RequestMapping("ShowMenu")
    public void showMenu(){
        logger.debug("showMenu");
//        System.err.println("ShowMenu");
    }


}
