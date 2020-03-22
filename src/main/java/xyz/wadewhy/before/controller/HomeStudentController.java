package xyz.wadewhy.before.controller;

import cn.hutool.core.util.IdUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.wadewhy.after.bus.domain.*;
import xyz.wadewhy.after.bus.service.*;
import xyz.wadewhy.before.common.DataFormatUtil;
import xyz.wadewhy.before.domain.ExamPaperAnswer;
import xyz.wadewhy.before.service.ExamPaperAnswerService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @PACKAGE_NAME: xyz.wadewhy.before.controller
 * @NAME: UserController
 * @Author: 钟子豪
 * @DATE: 2020/3/20
 * @MONTH_NAME_FULL: 三月
 * @DAY: 20
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 **/
@RequestMapping("/before/student")
@RestController
public class HomeStudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ExamService examService;
    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;
    @Autowired
    private QuestionService questionService;

    private int pageSize = 10;
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
    /**
     * 考生中心欢迎页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/welcome",method = RequestMethod.GET)
    public ModelAndView welcome(ModelAndView model, HttpServletRequest request){
        model.addObject("title", "考生中心");
        Student student = (Student)request.getSession().getAttribute("student");
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("subjectid", student.getSubjectid());
        queryMap.put("starttime", DataFormatUtil.getDate("yyyy-MM-dd hh:mm:ss", new Date()));
        queryMap.put("endtime", DataFormatUtil.getDate("yyyy-MM-dd hh:mm:ss", new Date()));
        queryMap.put("offset", 0);
        queryMap.put("pageSize", 10);
        model.addObject("examList", examService.findListByUser(queryMap));
        queryMap.remove("subjectid");
        queryMap.put("studentid", student.getId());
        model.addObject("historyList", examPaperService.findHistory(queryMap));
        model.addObject("subject", subjectService.findSubjectById(student.getSubjectid()));
        model.setViewName("/before/userHome/welcome");
        return model;
    }

    /**
     * 获取当前登录用户的用户名
     * @param request
     * @return
     */
    @RequestMapping(value="/get_current",method=RequestMethod.POST)
    public Map<String,String> getCurrent(HttpServletRequest request){
        Map<String, String> ret = new HashMap<String, String>();
        Object attribute = request.getSession().getAttribute("student");
        if(attribute == null){
            ret.put("type", "error");
            ret.put("msg", "登录信息失效！");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "获取成功！");
        Student student  = (Student)attribute;
        ret.put("username", student.getName());
        ret.put("truename", student.getTruename());
        return ret;
    }


    /**
     * 修改用户信息
     * @param request
     * @return
     */
    @RequestMapping(value="/update_info",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,String> updateInfo(Student student,HttpServletRequest request){
        Map<String, String> ret = new HashMap<String, String>();
        Student onlineStudent  = (Student)request.getSession().getAttribute("student");
        onlineStudent.setTel(student.getTel());
        onlineStudent.setTruename(student.getTruename());
        if(studentService.edit(onlineStudent) <= 0){
            ret.put("type", "error");
            ret.put("msg", "修改失败，请联系管理员！");
            return ret;
        }
        //重置session中的用户信息
        request.getSession().setAttribute("student", onlineStudent);
        ret.put("type", "success");
        ret.put("msg", "获取成功！");
        return ret;
    }
    /**
     * 修改密码提交
     * @param student
     * @param request
     * @return
     */
    @RequestMapping(value="/update_password",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,String> updatePassword(Student student,String oldPassword,HttpServletRequest request){
        Map<String, String> ret = new HashMap<String, String>();
        //onlineStudent表示验证通过后的用户保存在session
        Student onlineStudent  = (Student)request.getSession().getAttribute("student");
        //得到加密盐
       String salt= onlineStudent.getSalt();
       //前台输入的密码加密后的密文
        String consPwd = new Md5Hash(oldPassword, salt, 2).toString();
        if(!onlineStudent.getPassword().equals(consPwd)){
            ret.put("type", "error");
            ret.put("msg", "旧密码错误！");
            return ret;
        }
        //旧密码验证通过，设置新加密盐和密码
        String newsalt = IdUtil.simpleUUID().toUpperCase();
        onlineStudent.setSalt(newsalt);
        String pwd =  new Md5Hash(student.getPassword(), newsalt, 2).toString();
        onlineStudent.setPassword(pwd);
        if(studentService.edit(onlineStudent) <= 0){
            ret.put("type", "error");
            ret.put("msg", "修改失败，请联系管理员！");
            return ret;
        }
        //重置session中的用户信息
        request.getSession().setAttribute("student", onlineStudent);
        ret.put("type", "success");
        ret.put("msg", "获取成功！");
        return ret;
    }

    /**
     * 获取当前学生考过的考试信息
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/history_list",method = RequestMethod.GET)
    public ModelAndView historyList(ModelAndView model,
                                    @RequestParam(name="name",defaultValue="") String name,
                                    @RequestParam(name="page",defaultValue="1") Integer page,
                                    HttpServletRequest request){
        Student student = (Student)request.getSession().getAttribute("student");
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("name", name);
        queryMap.put("studentid", student.getId());
        queryMap.put("offset", getOffset(page, pageSize));
        queryMap.put("pageSize", pageSize);
        model.addObject("historyList", examPaperService.findHistory(queryMap));
        model.addObject("name", name);
        model.addObject("subject", subjectService.findSubjectById(student.getSubjectid()));
        model.setViewName("/before/userHome/history_list");
        if(page < 1)page = 1;
        model.addObject("page", page);
        return model;
    }
    /**
     *
     * @param model
     * @param examPaperId
     * @param request
     * @return
     */
    @RequestMapping(value = "/review_exam",method = RequestMethod.GET)
    public ModelAndView index(ModelAndView model,Integer examId,Integer examPaperId,HttpServletRequest request){
        Student student = (Student)request.getSession().getAttribute("student");
        Exam exam = examService.findById(examId);
        if(exam == null){
            model.setViewName("/before/exam/error");
            model.addObject("msg", "当前考试不存在!");
            return model;
        }
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("examid", examId);
        queryMap.put("studentid", student.getId());
        //根据考试信息和学生信息获取试卷
        ExamPaper examPaper = examPaperService.find(queryMap);
        if(examPaper == null){
            model.setViewName("/before/exam/error");
            model.addObject("msg", "当前考试不存在试卷");
            return model;
        }
        if(examPaper.getStatus() == 0){
            model.setViewName("/before/exam/error");
            model.addObject("msg", "您还没有考过这门考试！");
            return model;
        }
        queryMap.put("exampaperid", examPaper.getId());
        List<ExamPaperAnswer> findListByUser = examPaperAnswerService.findListByUser(queryMap);
        model.addObject("title", exam.getName()+"-回顾试卷");
        model.addObject("singleQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_SINGLE));
        model.addObject("muiltQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_MUILT));
        model.addObject("chargeQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_CHARGE));
        model.addObject("writerQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_WRITER));
        model.addObject("exam", exam);
        model.addObject("examPaper", examPaper);
        model.addObject("singleScore", Question.QUESTION_TYPE_SINGLE_SCORE);
        model.addObject("muiltScore", Question.QUESTION_TYPE_MUILT_SCORE);
        model.addObject("chargeScore", Question.QUESTION_TYPE_CHARGE_SCORE);
        model.addObject("writerScore", Question.QUESTION_TYPE_WRITER_SCORE);
        model.addObject("singleQuestion", Question.QUESTION_TYPE_SINGLE);
        model.addObject("muiltQuestion", Question.QUESTION_TYPE_MUILT);
        model.addObject("chargeQuestion", Question.QUESTION_TYPE_CHARGE);
        model.addObject("writerQuestion", Question.QUESTION_TYPE_WRITER);
        model.setViewName("/before/userHome/review_exam");
        return model;
    }
    /**
     * 返回指定类型的试题
     * @param examPaperAnswers
     * @param questionType
     * @return
     */
    private  List<Map<ExamPaperAnswer,List<Question_Option>>> getExamPaperAnswerList(List<ExamPaperAnswer> examPaperAnswers, int questionType){
        List<Map<ExamPaperAnswer,List<Question_Option>>> lists = new ArrayList<>();
        Map<ExamPaperAnswer,List<Question_Option>> qutionAns=new HashMap<>();
        for(ExamPaperAnswer examPaperAnswer:examPaperAnswers){
            if(examPaperAnswer.getQuestion().getType() == questionType){
                Integer qid=examPaperAnswer.getQuestionid();
                //根据questionid查询出该题目下的选项
                List<Question_Option>qolist=questionService.findQuestionAndOptionById(qid);
                System.err.println(examPaperAnswer.toString());
                //将问题和选项保存在map中
                qutionAns.put(examPaperAnswer,qolist);
                lists.add(qutionAns);
            }
        }
        return lists;
    }
}
