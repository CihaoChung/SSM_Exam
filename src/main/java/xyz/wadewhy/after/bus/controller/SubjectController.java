package xyz.wadewhy.after.bus.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.wadewhy.after.bus.domain.Subject;
import xyz.wadewhy.after.bus.service.SubjectService;
import xyz.wadewhy.after.sys.common.Page;
import xyz.wadewhy.after.sys.common.ResultObj;

import java.util.HashMap;
import java.util.Map;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.bus.controller
 * @NAME: SubjectController
 * @Author: 钟子豪
 * @DATE: 2020/3/17
 * @MONTH_NAME_FULL: 三月
 * @DAY: 17
 * @DAY_NAME_FULL: 星期二
 * @PROJECT_NAME: OnlineExam
 **/
@RestController
@RequestMapping("/after/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    /**
     * 加载数据
     * @param page
     * @param name
     * @return
     */
    @RequestMapping("list")
    public Map<String,Object> list(
            Page page,
            @RequestParam(name="name",defaultValue="") String name
    ){
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("name", name);
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", subjectService.findList(queryMap));
        ret.put("total", subjectService.getTotal(queryMap));
        return ret;
    }

    /**
     * 添加科目
     * @param subject
     * @return
     */
    @RequestMapping("add")
    public ResultObj add(Subject subject){
        if(subject == null){
            return new ResultObj(-2,"请填写正确的学科信息!");
        }
        if(StringUtils.isEmpty(subject.getName())){
            return new ResultObj(-2,"请填写学科名称!");
        }

        if (subjectService.findSubjectByName(subject.getName(),subject.getId())!=null){
            return new ResultObj(-2,"已经存在该科目！");
        }
        if(subjectService.add(subject) <= 0){
            return new ResultObj(-1,"添加失败，请联系管理员!");
        }
        return ResultObj.ADD_SUCCESS;
    }

    /**
     * 编辑科目
     * @param subject
     * @return
     */
    @RequestMapping("edit")
    public ResultObj edit(Subject subject){
        if(subject == null){
            return new ResultObj(-2,"请填写正确的学科信息!");
        }
        if(StringUtils.isEmpty(subject.getName())){
            return new ResultObj(-2,"请填写学科名称!");
        }

        if (subjectService.findSubjectByName(subject.getName(),subject.getId())!=null){
            return new ResultObj(-2,"已经存在该科目！");
        }
        if(subjectService.edit(subject) !=null){
            return new ResultObj(-1,"添加失败，请联系管理员!");
        }
        return ResultObj.ADD_SUCCESS;
    }

    /**
     * 删除科目
     * @param id
     * @return
     */
    @RequestMapping("delete")
    public ResultObj delete(@RequestParam(name="id" ) Integer id){
        if(id == null){
            return new ResultObj(-2,"请选择要删除的数据!");
        }
        try {
            if(subjectService.deleteSubjectById(id)!=null){
                return new ResultObj(-2,"删除失败，请联系管理员!");
            }
        } catch (Exception e) {
            return new ResultObj(-2,"该学科下存在考生信息，不能删除!");
        }
        return ResultObj.DELETE_SUCCESS;
    }
}
