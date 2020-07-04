package xyz.wadewhy.before.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import xyz.wadewhy.after.sys.common.WebUtils;
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
@Controller
@RequestMapping("/before/test")
public class BeforeTestController {
    @Autowired
   private ExamPaperAnswerService examPaperAnswerService;
    @RequestMapping("test01")
    @ResponseBody
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
    @RequestMapping("forwardMAV")
    public ModelAndView forwardMAV(){
        System.err.println("forwardMAV");
        ModelAndView mv = new ModelAndView();
        WebUtils.getHttpServletRequest().setAttribute("MAV","forward Can get MAV");
        String str="forwardmav";
        mv.setViewName("forward:/before/test/other.action?forwardMAV="+str);
        return mv;
    }
    @RequestMapping("other")
    @ResponseBody
    public String Other(@RequestParam(value = "forwardMAV") String forwardMAV){
        String MAV = (String) WebUtils.getHttpServletRequest().getAttribute("MAV");
        return forwardMAV+"------------"+MAV;
    }
    @RequestMapping("redirectMAV")
    public ModelAndView redirectMAV(RedirectAttributes ra){
        ModelAndView mv = new ModelAndView();
        ra.addAttribute("redirectMAV","redirectmav");
        WebUtils.getHttpServletRequest().setAttribute("redirectMAV","redirect can't get redirectMAV");
        String str = "re";
        mv.setViewName("redirect:/before/test/otherRedirect.action?re="+str);
        return mv;
    }
    @RequestMapping("otherRedirect")
    @ResponseBody
    public String otherRedirect(@ModelAttribute("redirectMAV")String redirectMAV, String re){
        String MAV = (String) WebUtils.getHttpServletRequest().getAttribute("MAV");
        return redirectMAV+"------------"+MAV+"-------------"+re;
    }
}
