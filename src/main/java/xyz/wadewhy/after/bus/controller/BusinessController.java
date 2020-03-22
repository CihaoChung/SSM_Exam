package xyz.wadewhy.after.bus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import xyz.wadewhy.after.bus.domain.Question;
import xyz.wadewhy.after.bus.service.ExamService;
import xyz.wadewhy.after.bus.service.QuestionService;
import xyz.wadewhy.after.bus.service.StudentService;
import xyz.wadewhy.after.bus.service.SubjectService;

import java.util.HashMap;
import java.util.Map;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.bus.controller
 * @NAME: BusinessController
 * @Author: 钟子豪
 * @DATE: 2020/3/17
 * @MONTH_NAME_FULL: 三月
 * @DAY: 17
 * @DAY_NAME_FULL: 星期二
 * @PROJECT_NAME: OnlineExam
 * 用于跳转
 **/
@Controller
@RequestMapping("/after/bus")
public class BusinessController {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ExamService examService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private QuestionService questionService;

    /**
     * 跳转科目管理
     * @return
     */
    @RequestMapping("toSubject")
    public String toSubject(){
        return "after/business/subject/list";
    }
    @RequestMapping("toQuestion")
    public String toQuestion(){
        return "after/business/question/list";
    }

    /**
     * 考生列表页面
     * @param model
     * @return
     */
    @RequestMapping(value="/toStudent",method= RequestMethod.GET)
    public ModelAndView toStudent(ModelAndView model){
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("offset", 0);
        queryMap.put("pageSize", 99999);
        model.addObject("subjectList", subjectService.findList(queryMap));
        model.setViewName("/after/business/student/list");
        return model;
    }
    /**
     * 考试列表页面
     * @param model
     * @return
     */
    @RequestMapping(value="/toExam",method=RequestMethod.GET)
    public ModelAndView toExam(ModelAndView model){
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("offset", 0);
        queryMap.put("pageSize", 99999);
        model.addObject("subjectList", subjectService.findList(queryMap));
        model.addObject("singleScore", Question.QUESTION_TYPE_SINGLE_SCORE);
        model.addObject("muiltScore", Question.QUESTION_TYPE_MUILT_SCORE);
        model.addObject("chargeScore", Question.QUESTION_TYPE_CHARGE_SCORE);
        model.addObject("writerScore", Question.QUESTION_TYPE_WRITER_SCORE);
        model.setViewName("/after/business/exam/list");
        return model;
    }

    /**
     * 试卷列表页面
     * @param model
     * @return
     */
    @RequestMapping(value="/toExamPaper",method=RequestMethod.GET)
    public ModelAndView toExamPaper(ModelAndView model){
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("offset", 0);
        queryMap.put("pageSize", 99999);
        model.addObject("examList", examService.findList(queryMap));
        model.addObject("studentList", studentService.findList(queryMap));
        model.setViewName("after/business/examPaper/list");
        return model;
    }
    /**
     * 试卷答题列表页面
     * @param model
     * @return
     */
    @RequestMapping(value="toExamPaperAnswer",method=RequestMethod.GET)
    public ModelAndView toExamPaperAnswer(ModelAndView model){
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("offset", 0);
        queryMap.put("pageSize", 99999);
        model.addObject("examList", examService.findList(queryMap));
        model.addObject("studentList", studentService.findList(queryMap));
        model.addObject("questionList", questionService.findList(queryMap));
        model.setViewName("/after/business/examPaperAnswer/list");
        return model;
    }

}
