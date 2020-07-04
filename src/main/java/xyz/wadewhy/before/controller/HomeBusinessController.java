package xyz.wadewhy.before.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import xyz.wadewhy.after.bus.domain.Exam;
import xyz.wadewhy.after.bus.domain.Student;
import xyz.wadewhy.after.bus.service.ExamService;
import xyz.wadewhy.after.bus.service.SubjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @PACKAGE_NAME: xyz.wadewhy.before.controller
 * @NAME: HomeBusinessController
 * @Author: 钟子豪
 * @DATE: 2020/3/21
 * @MONTH_NAME_FULL: 三月
 * @DAY: 21
 * @DAY_NAME_FULL: 星期六
 * @PROJECT_NAME: OnlineExam
 **/
@Controller
@RequestMapping("/before/bus")
public class HomeBusinessController {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ExamService examService;
    private int pageSize = 10;
    /**
     * 获取当前学生正在进行的考试信息
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/exam_list",method = RequestMethod.GET)
    public ModelAndView exameList(ModelAndView model,
                                  @RequestParam(name="name",defaultValue="") String name,
                                  @RequestParam(name="page",defaultValue="1") Integer page,
                                  HttpServletRequest request){
        Student student = (Student)request.getSession().getAttribute("student");
//        System.err.println(student.toString());
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("subjectid", student.getSubjectid());
        queryMap.put("name", name);
        queryMap.put("offset", getOffset(page, pageSize));
        queryMap.put("pageSize", pageSize);
        model.addObject("examList", examService.findListByUser(queryMap));
       /* for (Exam e:examService.findListByUser(queryMap)
             ) {
//            System.err.println("【exam】"+e.toString());
        }*/
        model.addObject("name", name);
        model.addObject("subject", subjectService.findSubjectById(student.getSubjectid()));
        model.setViewName("before/userHome/exam_list");
        if(page < 1)page = 1;
        model.addObject("page", page);
        model.addObject("nowTime", System.currentTimeMillis());
        return model;
    }
    /**
     * 计算数据库查询游标位置
     * @param page
     * @param pageSize
     * @return
     */
    private int getOffset(int page,int pageSize){
        if(page < 1)page = 1;
        return (page - 1) * pageSize;
    }
}
