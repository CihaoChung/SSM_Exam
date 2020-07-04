package xyz.wadewhy.before.controller;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wadewhy.after.bus.domain.Student;
import xyz.wadewhy.after.bus.service.StudentService;
import xyz.wadewhy.after.bus.service.SubjectService;
import xyz.wadewhy.after.sys.common.ResultObj;
import xyz.wadewhy.after.sys.common.WebUtils;

/**
 * @PACKAGE_NAME: xyz.wadewhy.before.controller
 * @NAME: HomeController
 * @Author: 钟子豪
 * @DATE: 2020/3/20
 * @MONTH_NAME_FULL: 三月
 * @DAY: 20
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 **/
@RestController
@RequestMapping("/before/home")
public class HomeController {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private StudentService studentService;
    @RequestMapping("login")
    public ResultObj login(String name,String password, String cpacha){
        //得到真正的验证码
        String realCode = WebUtils.getHttpSession().getAttribute("cpacha").toString();
        //判断验证码是否正确
        if (null!=cpacha&&cpacha.equals(realCode)){
            //验证码正确
            //得到subject
           Student student =studentService.findByName(name, null);
           if (student==null){
               return new ResultObj(-2,"没有该用户");
           }
           //得到加密盐
           String salt = student.getSalt();
//            System.err.println("【salt】"+salt);
           //输入的密码
           String consPwd = new Md5Hash(password, salt, 2).toString();
//            System.err.println("【consPwd】："+consPwd+"【student.getPassword()】："+student.getPassword());
           if (!consPwd.equals(student.getPassword())){
               //登入失败
               return new ResultObj(-1,"账号或密码错误");
           }
            WebUtils.getHttpSession().setAttribute("student", student);
            return ResultObj.LOGIN_SUCCESS;
        }else{
            //验证码错误。
            return ResultObj.LOGIN_ERROR_CODE;
        }
    }

}
