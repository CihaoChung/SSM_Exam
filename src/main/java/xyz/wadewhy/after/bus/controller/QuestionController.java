package xyz.wadewhy.after.bus.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.sql.visitor.functions.Char;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import xyz.wadewhy.after.bus.domain.Question;
import xyz.wadewhy.after.bus.domain.Question_Option;
import xyz.wadewhy.after.bus.domain.Subject;
import xyz.wadewhy.after.bus.service.QuestionService;
import xyz.wadewhy.after.sys.common.Page;
import xyz.wadewhy.after.sys.common.ResultObj;
import xyz.wadewhy.after.sys.common.WebUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.bus.controller
 * @NAME: QuestionController
 * @Author: 钟子豪
 * @DATE: 2020/3/19
 * @MONTH_NAME_FULL: 三月
 * @DAY: 19
 * @DAY_NAME_FULL: 星期四
 * @PROJECT_NAME: OnlineExam
 * 题目控制层
 **/
@RestController
@RequestMapping("/after/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    /**
     * 添加
     * @param question
     * @return
     */
    @RequestMapping("add")
    public ResultObj add(Question question){
//        System.err.println(question.toString());
        if(question == null){
            return new ResultObj(-1,"请填写正确的试题信息!");
        }
        if(StringUtils.isEmpty(question.getTitle())){
            return new ResultObj(-1,"请填写试题题目!");
        }
        if(StringUtils.isEmpty(question.getAnswer())){
            return new ResultObj(-1,"请填写试题正确答案！");
        }
        //设置分数
        question.setScoreByType();
        //插入成功
        if (questionService.insertQuestionAndGetId(question)>0){
            String optionString =question.getOptions();
            if (!optionString.equals("")){//不是简答题
                JSONArray jsonArray = new JSONArray(optionString);
                Question_Option question_option = new Question_Option();
                question_option.setQid(question.getId());
                for(int j=0; j< jsonArray.size(); j++){
                    //关联表插入
                    JSONObject subImageInfoObject = jsonArray.getJSONObject(j);
                    String option = subImageInfoObject.getStr("option");
                    String ans = subImageInfoObject.getStr("ans");
                    question_option.setSelectoption(option);
                    question_option.setOptionanswer(ans);
                    questionService.insertQuestion_Option(question_option);
                }
            }
        }else{
            return new ResultObj(-2,"未知错误，联系管理员");
        }
          return ResultObj.ADD_SUCCESS;
    }

    /**
     * 得到subject
     * @return
     */
    @RequestMapping("findSubject")
    public  List<Subject> findSubject(){
        List<Subject> subjectList=questionService.findSubject();
        return subjectList;
    }
/**
 * 查询【模糊，分页】
 */
    @RequestMapping("list")
    public Map<String,Object> list(@RequestParam(name="title",defaultValue="") String title,
                                   @RequestParam(name="questionType",required=false) Integer questionType,
                                   @RequestParam(name="subjectid",required=false) Integer subjectId,
                                   Page page){
//        System.err.println("[itle]"+title+"questionType"+questionType+"subjectId"+subjectId);
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("title", title);
        if(questionType != null){
            queryMap.put("type", questionType);
        }
        if(subjectId != null){
            queryMap.put("subjectId", subjectId);
        }
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", questionService.findList(queryMap));
        ret.put("total", questionService.getTotal(queryMap));
        return ret;
    }

    /**
     *
     * @param questionId
     * @return
     */
    @RequestMapping("findQuestionById")
    public Map<String,Object> findQuestionById(
            @RequestParam(name="questionId",defaultValue="") Integer questionId){
        //根据题目id查询题目
       Question question =questionService.findQuestionById(questionId);
//        System.err.println(question.toString());
        //根据题目id查询选项和选项值
      List<Question_Option> question_optionList=  questionService.findQuestionAndOptionById(questionId);
      Map<String,Object> ret = new HashMap<>();
      ret.put("question",question);
      ret.put("question_optionList", question_optionList);
      ret.put("code", 200);
      return ret;
    }
    /**
     * 编辑
     */
    @RequestMapping("edit")
    public ResultObj edit( @RequestParam(name="questionId",required=false) Integer questionId,Question question){
//        System.err.println(question.toString()+"【quertionId】"+questionId);
        String optionString =question.getOptions();
        //修改qusetion
        if(question == null){
            return new ResultObj(-1,"请填写正确的试题信息!");
        }
        if(StringUtils.isEmpty(question.getTitle())){
            return new ResultObj(-1,"请填写试题题目!");
        }
        if(StringUtils.isEmpty(question.getAnswer())){
            return new ResultObj(-1,"请填写试题正确答案！");
        }
        //避免在直接修改选项过程中获取不到id
        if (questionId!=null){
            question.setId(questionId);
        }
        //更新question
        if (questionService.UpdateQuestion(question)>0){
            //修改关联表
            if (!optionString.equals("")){//不是简答题
                JSONArray jsonArray = new JSONArray(optionString);
                Question_Option question_option = new Question_Option();
                if (questionId!=null){
                    question_option.setQid(questionId);
                }
                if (question.getId()!=null){
                    question_option.setQid(question.getId());
                }
                for(int j=0; j< jsonArray.size(); j++){
                    //关联表插入
                    JSONObject subImageInfoObject = jsonArray.getJSONObject(j);
                    String option = subImageInfoObject.getStr("option");
                    String ans = subImageInfoObject.getStr("ans");
                    question_option.setSelectoption(option);
                    question_option.setOptionanswer(ans);
                    questionService.UpdateQuestion_Option(question_option);
                }
            }
        }else{
            return new ResultObj(-2,"未知错误");
        }

        return ResultObj.UPDATE_SUCCESS;
    }
    @RequestMapping("delete")
    public ResultObj delete(@RequestParam(name="id") Integer id){
        //由于在设计数据库时，试题选项表设置的外键自动删除，所以只要删除question表中的数据
        if(id == null){
            return new ResultObj(-2,"请选择要删除的数据!");
        }
        try {
            if(questionService.deleteById(id) <= 0){
                return new ResultObj(-1,"删除失败，请联系管理员!");
            }
        } catch (Exception e) {
            // TODO: handle exception
            return new ResultObj(-1,"该试题下存在考试信息，不能删除!");
        }
        return ResultObj.DELETE_SUCCESS;
    }
    /**
     * 上传文件批量导入试题
     * @param excelFile
     * @return
     */
    @RequestMapping(value="upload_file",method= RequestMethod.POST)
    @ResponseBody
    public Map<String, String> uploadFile(MultipartFile excelFile, Integer subjectid){
        Map<String, String> ret = new HashMap<String, String>();
        if(excelFile == null){
            ret.put("type", "error");
            ret.put("msg", "请选择文件!");
            return ret;
        }
        if(subjectid == null){
            ret.put("type", "error");
            ret.put("msg", "请选择所属科目!");
            return ret;
        }
        if(excelFile.getSize() > 5000000){
            ret.put("type", "error");
            ret.put("msg", "文件大小不要超过5M!");
            return ret;
        }
        String suffix = excelFile.getOriginalFilename().substring(excelFile.getOriginalFilename().lastIndexOf(".")+1, excelFile.getOriginalFilename().length());
        if(!"xls,xlsx".contains(suffix)){
            ret.put("type", "error");
            ret.put("msg", "请上传最新xls,xlsx格式的文件!");
            return ret;
        }
        String msg = "导入成功";
        try {
            msg = readExcel(excelFile.getInputStream(),subjectid);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if("".equals(msg))msg = "全部导入成功";
        ret.put("type", "success");
        ret.put("msg", msg);
        return ret;
    }

    /**
     * 读取excel文件，并插入到数据库
     * @param fileInputStream
     * @return
     */
    private String readExcel(InputStream fileInputStream, Integer subjectId){
        String msg = "";
        try {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
            if(sheetAt.getLastRowNum() <= 0){
                msg = "该文件为空";
            }
            //sheet.getLastRowNum() + 1表示行数
            for(int rowIndex = 1;rowIndex <= sheetAt.getLastRowNum(); rowIndex++){
                Question question = new Question();
                HSSFRow row = sheetAt.getRow(rowIndex);
                if(row.getCell(0) == null){
                    msg += "第" + rowIndex + "行，试题类型为空，跳过<br/>";
                    continue;
                }
                Double numericCellValue = row.getCell(0).getNumericCellValue();
                question.setType(numericCellValue.intValue());
                if(row.getCell(1) == null){
                    msg += "第" + rowIndex + "行，题目为空，跳过<br/>";
                    continue;
                }
                question.setTitle(row.getCell(1).getStringCellValue());
                if(row.getCell(2) == null){
                    msg += "第" + rowIndex + "行，分值为空，跳过<br/>";
                    continue;
                }
                numericCellValue = row.getCell(2).getNumericCellValue();
                question.setScore(numericCellValue.intValue());
                if(row.getCell(3) == null){
                    msg += "第" + rowIndex + "行，选项A为空，跳过<br/>";
                    continue;
                }
                if(row.getCell(4) == null){
                    msg += "第" + rowIndex + "行，选项B为空，跳过<br/>";
                    continue;
                }
                //最后一列索引
                if(row.getCell(row.getLastCellNum()-1) == null){
                    msg += "第" + rowIndex + "行，正确答案为空，跳过\n";
                    continue;
                }

                question.setAnswer(row.getCell(row.getLastCellNum()-1).getStringCellValue());
                question.setCreateTime(new Date());
                question.setSubjectid(subjectId);
                //插入题目
                questionService.insertQuestionAndGetId(question);
                Map<String,String> listmap = new HashMap<>();
                Question_Option question_option = new Question_Option();
                //
                question_option.setQid(question.getId());
                //asscii码A
                int num = 65;
                //插入选项
                for (int i=3;i<row.getLastCellNum()-1;i++
                     ) {
                    String s = Integer.toString(num);
                    listmap.put(String.valueOf((char)Integer.parseInt(s)), row.getCell(i).getStringCellValue());
                    num++;
                    question_option.setSelectoption(String.valueOf((char)Integer.parseInt(s)));
                    question_option.setOptionanswer( row.getCell(i).getStringCellValue());
//                    System.err.println(question_option.toString());
                    questionService.insertQuestion_Option(question_option);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }
}
