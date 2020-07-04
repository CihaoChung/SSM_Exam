package xyz.wadewhy.after.bus.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.util.IdUtil;
import xyz.wadewhy.after.bus.domain.Student;
import xyz.wadewhy.after.bus.mapper.StudentMapper;
import xyz.wadewhy.after.bus.service.StudentService;
import xyz.wadewhy.after.sys.common.Page;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.bus.controller
 * @NAME: StudentController
 * @Author: 钟子豪
 * @DATE: 2020/3/20
 * @MONTH_NAME_FULL: 三月
 * @DAY: 20
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 **/
@RestController
@RequestMapping("/after/student")
public class StudentController {
    // 获取日志记录器Logger，名字为本类类名
    private static Logger logger = Logger.getLogger(StudentController.class);
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 模糊搜索分页显示列表查询
     * 
     * @param name
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Map<String, Object> list(@RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "subjectid", required = false) Long subjectid, Page page) {
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("name", name);
        if (subjectid != null) {
            queryMap.put("subjectid", subjectid);
        }
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", studentService.findList(queryMap));
        ret.put("total", studentService.getTotal(queryMap));
        return ret;
    }

    /**
     * 添加考生
     * 
     * @param student
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> add(Student student) {
        System.err.println("-------------------添加考生-----------------------");
        Map<String, String> ret = new HashMap<String, String>();
        if (student == null) {
            ret.put("type", "error");
            ret.put("msg", "请填写正确的考生信息!");
            return ret;
        }
        if (StringUtils.isEmpty(student.getName())) {
            ret.put("type", "error");
            ret.put("msg", "请填写考生用户名!");
            return ret;
        }
        if (StringUtils.isEmpty(student.getPassword())) {
            ret.put("type", "error");
            ret.put("msg", "请填写考生密码!");
            return ret;
        }
        if (student.getSubjectid() == null) {
            ret.put("type", "error");
            ret.put("msg", "请选择考生所属学科专业!");
            return ret;
        }
        student.setCreatetime(new Date());
        // 添加之前判断登录名是否存在
        if (isExistName(student.getName(), null)) {
            ret.put("type", "error");
            ret.put("msg", "该登录账号已经存在!");
            return ret;
        }
        // 加密
        String salt = IdUtil.simpleUUID().toUpperCase();
        student.setSalt(salt);
        student.setPassword(new Md5Hash(student.getPassword(), salt, 2).toString());
        if (studentService.add(student) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "添加失败，请联系管理员!");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "添加成功!");
        return ret;
    }

    /**
     * 判断用户名是否存在
     * 
     * @param name
     * @param id
     * @return
     */
    private boolean isExistName(String name, Long id) {
        Student student = studentService.findByName(name, id);
        logger.info("[----name-----]" + name + "[----id----]" + id);
        if (student == null)
            return false;
        if (null != id) {//

            if (student.getId().longValue() == id.longValue())
                return false;
        }

        return true;
    }

    /**
     * 编辑考生
     * 
     * @param student
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> edit(Student student) {
        Map<String, String> ret = new HashMap<String, String>();
        if (student == null) {
            ret.put("type", "error");
            ret.put("msg", "请填写正确的学科信息!");
            return ret;
        }
        if (StringUtils.isEmpty(student.getName())) {
            ret.put("type", "error");
            ret.put("msg", "请填写考生用户名!");
            return ret;
        }
        if (StringUtils.isEmpty(student.getPassword())) {
            ret.put("type", "error");
            ret.put("msg", "请填写考生密码!");
            return ret;
        }
        if (student.getSubjectid() == null) {
            ret.put("type", "error");
            ret.put("msg", "请选择考生所属学科专业!");
            return ret;
        }
        // 编辑之前判断登录名是否存在
        if (isExistName(student.getName(), Long.valueOf(student.getId()))) {
            ret.put("type", "error");
            ret.put("msg", "该登录账号已经存在!");
            return ret;
        }
        Student actStu = studentMapper.selectByPrimaryKey(student.getId());
//        System.err.println(actStu.getPassword()+"[----------]"+student.getPassword());
        if (null != actStu) {
            // 如果修改了密码在重新
            if (!actStu.getPassword().equals(student.getPassword())) {
                // 加密
                String salt = IdUtil.simpleUUID().toUpperCase();
                student.setSalt(salt);
                student.setPassword(new Md5Hash(student.getPassword(), salt, 2).toString());
            }
        }
        if (studentService.edit(student) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "编辑失败，请联系管理员!");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "编辑成功!");
        return ret;
    }

    /**
     * 删除考生
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> delete(Integer id) {
        Map<String, String> ret = new HashMap<String, String>();
        if (id == null) {
            ret.put("type", "error");
            ret.put("msg", "请选择要删除的数据!");
            return ret;
        }
        try {
            if (studentService.delete(id) <= 0) {
                ret.put("type", "error");
                ret.put("msg", "删除失败，请联系管理员!");
                return ret;
            }
        } catch (Exception e) {
            // TODO: handle exception
            ret.put("type", "error");
            ret.put("msg", "该考生下存在考试信息，不能删除!");
            return ret;
        }

        ret.put("type", "success");
        ret.put("msg", "删除成功!");
        return ret;
    }

}
