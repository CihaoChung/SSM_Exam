package xyz.wadewhy.before.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wadewhy.before.service.ExamPaperAnswerService;
import xyz.wadewhy.before.domain.ExamPaperAnswer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: xyz.wadewhy.before.controller
 * @NAME: TestController
 * @Author: 钟子豪
 * @DATE: 2020/3/21
 * @MONTH_NAME_FULL: 三月
 * @DAY: 21
 * @DAY_NAME_FULL: 星期六
 * @PROJECT_NAME: OnlineExam
 **/
@RestController
@RequestMapping("/before/test")
public class BeforeTestController {
    @Autowired
   private ExamPaperAnswerService examPaperAnswerService;
    @RequestMapping("test01")
    public   List<ExamPaperAnswer> test01(){
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("exampaperid", 2);
        queryMap.put("studentid", 2);
        List<ExamPaperAnswer> findListByUser = examPaperAnswerService.findListByUser(queryMap);
        System.err.println(findListByUser.size());
        for (ExamPaperAnswer e:findListByUser
             ) {
            System.err.println(e.toString());
        }
        return findListByUser;
    }
}
