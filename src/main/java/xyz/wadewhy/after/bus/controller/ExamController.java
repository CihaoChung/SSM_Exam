package xyz.wadewhy.after.bus.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.wadewhy.after.bus.domain.Exam;
import xyz.wadewhy.after.bus.domain.ExamPaper;
import xyz.wadewhy.after.bus.domain.ExamPaperCreate;
import xyz.wadewhy.after.bus.domain.Question;
import xyz.wadewhy.after.bus.service.ExamPaperCreateService;
import xyz.wadewhy.after.bus.service.ExamPaperService;
import xyz.wadewhy.after.bus.service.ExamService;
import xyz.wadewhy.after.bus.service.QuestionService;
import xyz.wadewhy.after.bus.service.impl.ExamPaperServiceImpl;
import xyz.wadewhy.after.sys.common.Page;

import java.util.*;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.bus.controller
 * @NAME: ExamController
 * @Author: 钟子豪
 * @DATE: 2020/3/20
 * @MONTH_NAME_FULL: 三月
 * @DAY: 20
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 **/
@RestController
@RequestMapping("/after/exam")
public class ExamController {

    @Autowired
    private ExamService examService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ExamPaperCreateService examPaperCreateService;
    @Autowired
    private ExamPaperService examPaperService;
    /**
     * 模糊搜索分页显示列表查询
     * @param name
     * @param page
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> list(
            @RequestParam(name="name",defaultValue="") String name,
            @RequestParam(name="subjectid",required=false) Long subjectid,
            @RequestParam(name="starttime",required=false) String starttime,
            @RequestParam(name="endtime",required=false) String endtime,
            Page page
    ){
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("name", name);
        if(subjectid != null){
            queryMap.put("subjectid", subjectid);
        }
        if(!StringUtils.isEmpty(starttime)){
            queryMap.put("starttime", starttime);
        }
        if(!StringUtils.isEmpty(endtime)){
            queryMap.put("endtime", endtime);
        }
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", examService.findList(queryMap));
        ret.put("total", examService.getTotal(queryMap));
        return ret;
    }

    /**
     * 添加考试
     * @param exam
     * @return
     */
    @RequestMapping(value="add",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> add(Exam exam){
        System.err.println(exam.toString());
        Map<String, String> ret = new HashMap<String, String>();
        if(exam == null){
            ret.put("type", "error");
            ret.put("msg", "请填写正确的考试信息!");
            return ret;
        }
        if(StringUtils.isEmpty(exam.getName())){
            ret.put("type", "error");
            ret.put("msg", "请填写考试名称!");
            return ret;
        }
        if(exam.getSubjectid() == null){
            ret.put("type", "error");
            ret.put("msg", "请选择所属科目!");
            return ret;
        }
        if(exam.getStarttime() == null){
            ret.put("type", "error");
            ret.put("msg", "请填写考试开始时间!");
            return ret;
        }
        if(exam.getEndtime() == null){
            ret.put("type", "error");
            ret.put("msg", "请填写考试结束时间!");
            return ret;
        }
        if(exam.getPassscore() == 0){
            ret.put("type", "error");
            ret.put("msg", "请填写考试及格分数!");
            return ret;
        }
        if(exam.getSinglequestionnum() == 0 && exam.getMuiltquestionnum() == 0 && exam.getWritequestionnum() == 0&&exam.getChargequestionnum()==0){
            ret.put("type", "error");
            ret.put("msg", "单选题、多选题、判断题至少有一种类型的题目!");
            return ret;
        }
        exam.setCreatetime(new Date());
        //此时去查询所填写的题型数量是否满足
        //获取单选题总数
        Map<String, Integer> queryMap = new HashMap<String, Integer>();
        queryMap.put("questiontype", Question.QUESTION_TYPE_SINGLE);
        queryMap.put("subjectid", exam.getSubjectid());
        int singleQuestionTotalNum = questionService.getQuestionNumByType(queryMap);
        System.err.println(singleQuestionTotalNum+"[][][]"+exam.getSinglequestionnum());
        if(exam.getSinglequestionnum() > singleQuestionTotalNum){
            ret.put("type", "error");
            ret.put("msg", "单选题数量超过题库单选题总数，请修改!");
            return ret;
        }
        //获取多选题总数
        queryMap.put("questiontype", Question.QUESTION_TYPE_MUILT);
        int muiltQuestionTotalNum = questionService.getQuestionNumByType(queryMap);
        if(exam.getMuiltquestionnum() > muiltQuestionTotalNum){
            ret.put("type", "error");
            ret.put("msg", "多选题数量超过题库多选题总数，请修改!");
            return ret;
        }
        //获取判断题总数
        queryMap.put("questiontype", Question.QUESTION_TYPE_CHARGE);
        int chargeQuestionTotalNum = questionService.getQuestionNumByType(queryMap);
        if(exam.getChargequestionnum() > chargeQuestionTotalNum){
            ret.put("type", "error");
            ret.put("msg", "判断题数量超过题库判断题总数，请修改!");
            return ret;
        }
        //获取简答题总数
        queryMap.put("questiontype", Question.QUESTION_TYPE_CHARGE);
        int writeQuestionTotalNum = questionService.getQuestionNumByType(queryMap);
        if(exam.getChargequestionnum() > writeQuestionTotalNum){
            ret.put("type", "error");
            ret.put("msg", "判断题数量超过题库判断题总数，请修改!");
            return ret;
        }
        exam.setQuestionnum(exam.getSinglequestionnum() + exam.getMuiltquestionnum() + exam.getChargequestionnum()+exam.getWritequestionnum());
        exam.setTotalscore(exam.getSinglequestionnum() * Question.QUESTION_TYPE_SINGLE_SCORE + exam.getMuiltquestionnum() * Question.QUESTION_TYPE_MUILT_SCORE + exam.getChargequestionnum() * Question.QUESTION_TYPE_CHARGE_SCORE+
                exam.getWritequestionnum()*Question.QUESTION_TYPE_WRITER_SCORE);
        //添加试卷模板
        if(examService.add(exam) <= 0){
            ret.put("type", "error");
            ret.put("msg", "添加失败，请联系管理员!");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "添加成功!");
        return ret;
    }


}
