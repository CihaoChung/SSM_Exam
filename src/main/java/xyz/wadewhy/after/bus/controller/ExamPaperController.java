package xyz.wadewhy.after.bus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.wadewhy.after.bus.domain.ExamPaper;
import xyz.wadewhy.after.bus.service.ExamPaperService;
import xyz.wadewhy.after.bus.service.ExamService;
import xyz.wadewhy.after.bus.service.StudentService;
import xyz.wadewhy.after.sys.common.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.bus.controller
 * @NAME: ExamPaperController
 * @Author: 钟子豪
 * @DATE: 2020/3/22
 * @MONTH_NAME_FULL: 三月
 * @DAY: 22
 * @DAY_NAME_FULL: 星期日
 * @PROJECT_NAME: OnlineExam
 **/
@RequestMapping("/after/exampaper")
@RestController
public class ExamPaperController {
    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ExamService examService;
    /**
     * 模糊搜索分页显示列表查询
     * @param
     * @param page
     * @return
     */
    @RequestMapping(value="/list",method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(
            @RequestParam(name="examId",required=false) Integer examId,
            @RequestParam(name="studentId",required=false) Integer studentId,
            @RequestParam(name="status",required=false) Integer status,
            Page page
    ){
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        if(examId != null){
            queryMap.put("examid", examId);
        }
        if(studentId != null){
            queryMap.put("studentid", studentId);
        }
        if(status != null){
            queryMap.put("status", status);
        }
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", examPaperService.findList(queryMap));
        ret.put("total", examPaperService.getTotal(queryMap));
        return ret;
    }

    /**
     * 添加试卷
     * @param examPaper
     * @return
     */
    @RequestMapping(value="add",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> add(ExamPaper examPaper){
        Map<String, String> ret = new HashMap<String, String>();
        if(examPaper == null){
            ret.put("type", "error");
            ret.put("msg", "请填写正确的试卷信息!");
            return ret;
        }
        if(examPaper.getExamid() == null){
            ret.put("type", "error");
            ret.put("msg", "请选择试卷所属考试!");
            return ret;
        }
        if(examPaper.getStudentid() == null){
            ret.put("type", "error");
            ret.put("msg", "请选择所属学生!");
            return ret;
        }
        if(examPaperService.add(examPaper) <= 0){
            ret.put("type", "error");
            ret.put("msg", "添加失败，请联系管理员!");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "添加成功!");
        return ret;
    }

    /**
     * 编辑试卷
     * @param examPaper
     * @return
     */
    @RequestMapping(value="edit",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> edit(ExamPaper examPaper){
        Map<String, String> ret = new HashMap<String, String>();
        if(examPaper == null){
            ret.put("type", "error");
            ret.put("msg", "请填写正确的试卷信息!");
            return ret;
        }
        if(examPaper.getExamid() == null){
            ret.put("type", "error");
            ret.put("msg", "请选择试卷所属考试!");
            return ret;
        }
        if(examPaper.getStudentid() == null){
            ret.put("type", "error");
            ret.put("msg", "请选择所属学生!");
            return ret;
        }if(examPaperService.edit(examPaper) <= 0){
            ret.put("type", "error");
            ret.put("msg", "编辑失败，请联系管理员!");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "编辑成功!");
        return ret;
    }

    /**
     * 删除试卷
     * @param id
     * @return
     */
    @RequestMapping(value="delete",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> delete(Integer id){
        Map<String, String> ret = new HashMap<String, String>();
        if(id == null){
            ret.put("type", "error");
            ret.put("msg", "请选择要删除的数据!");
            return ret;
        }
        try {
            if(examPaperService.delete(id) <= 0){
                ret.put("type", "error");
                ret.put("msg", "删除失败，请联系管理员!");
                return ret;
            }
        } catch (Exception e) {
            // TODO: handle exception
            ret.put("type", "error");
            ret.put("msg", "该试卷下存在答题信息，不能删除!");
            return ret;
        }

        ret.put("type", "success");
        ret.put("msg", "删除成功!");
        return ret;
    }

}
