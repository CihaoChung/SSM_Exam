package xyz.wadewhy.after.bus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.wadewhy.after.sys.common.Page;
import xyz.wadewhy.before.service.ExamPaperAnswerService;

import java.util.HashMap;
import java.util.Map;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.bus.controller
 * @NAME: ExamPaperAnswerController
 * @Author: 钟子豪
 * @DATE: 2020/3/22
 * @MONTH_NAME_FULL: 三月
 * @DAY: 22
 * @DAY_NAME_FULL: 星期日
 * @PROJECT_NAME: OnlineExam
 **/
@RestController
@RequestMapping("/after/examPaperAnswer")
public class ExamPaperAnswerController {
    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;
    /**
     * 模糊搜索分页显示列表查询
     * @param page
     * @return
     */
    @RequestMapping("list")
    public Map<String, Object> list(
            @RequestParam(name="examId",required=false) Integer examId,
            @RequestParam(name="studentId",required=false) Integer studentId,
            @RequestParam(name="questionId",required=false) Integer questionId,
            Page page
    ){
//        System.err.println("模糊搜索分页显示列表查询");
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        if(examId != null){
            queryMap.put("examid", examId);
        }
        if(studentId != null){
            queryMap.put("studentid", studentId);
        }
        if(questionId != null){
            queryMap.put("questionid", questionId);
        }
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", examPaperAnswerService.findList(queryMap));
        ret.put("total", examPaperAnswerService.getTotal(queryMap));
        return ret;
    }

}
