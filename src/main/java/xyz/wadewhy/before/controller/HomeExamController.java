package xyz.wadewhy.before.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import xyz.wadewhy.after.bus.domain.Exam;
import xyz.wadewhy.after.bus.domain.ExamPaper;
import xyz.wadewhy.after.bus.domain.ExamPaperCreate;
import xyz.wadewhy.after.bus.domain.Question;
import xyz.wadewhy.after.bus.domain.Question_Option;
import xyz.wadewhy.after.bus.domain.Student;
import xyz.wadewhy.after.bus.service.ExamPaperCreateService;
import xyz.wadewhy.after.bus.service.ExamPaperService;
import xyz.wadewhy.after.bus.service.ExamService;
import xyz.wadewhy.after.bus.service.QuestionService;
import xyz.wadewhy.before.domain.ExamPaperAnswer;
import xyz.wadewhy.before.service.ExamPaperAnswerService;

/**
 * @PACKAGE_NAME: xyz.wadewhy.before.controller
 * @NAME: HomeExamController
 * @Author: 钟子豪
 * @DATE: 2020/3/21
 * @MONTH_NAME_FULL: 三月
 * @DAY: 21
 * @DAY_NAME_FULL: 星期六
 * @PROJECT_NAME: OnlineExam
 **/
@Controller
@RequestMapping("/before/exam")
public class HomeExamController {
    // 获取日志记录器Logger，名字为本类类名
    private static Logger logger = Logger.getLogger(HomeExamController.class);
    @Autowired
    private ExamService examService;
    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;
    @Autowired
    private ExamPaperCreateService examPaperCreateService;
    @Autowired
    private QuestionService questionService;

    /**
     * 开始考试前检查合法性，随机生成试题
     * 
     * @param examId
     * @return
     */
    @RequestMapping(value = "/start_exam", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> startExam(Integer examId, HttpServletRequest request) {
        Map<String, String> ret = new HashMap<String, String>();
        Exam exam = examService.findById(examId);
        logger.info("[-----------start_exam-----exam---------]" + exam.toString());
        if (exam == null) {
            ret.put("type", "error");
            ret.put("msg", "考试信息不存在!");
            return ret;
        }
        if (exam.getEndtime().getTime() < new Date().getTime()) {
            ret.put("type", "error");
            ret.put("msg", "该考试已结束!");
            return ret;
        }
        Student student = (Student) request.getSession().getAttribute("student");
        if (exam.getSubjectid().longValue() != student.getSubjectid().longValue()) {
            logger.info("[-----------start_exam-----student---------]" + student.toString());
            ret.put("type", "error");
            ret.put("msg", "学科不同，无权进行考试!");
            return ret;
        }
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("examid", exam.getId());
        queryMap.put("studentid", student.getId());
        ExamPaper find = examPaperService.find(queryMap);
        if (find != null) {
            logger.info("[-----------start_exam-----ExamPaper---------]" + find.toString());
            if (find.getStatus() == 1) {
                // 表示已经考过
                ret.put("type", "error");
                ret.put("msg", "您已经考过这门考试了，不能再考!");
                return ret;
            }
            ret.put("type", "success");
            ret.put("msg", "可以开始考试");
            return ret;
        }

        // 在ExamPaper中记录要考试的学生
        // 生成试卷exampaper
        // 所有条件都满足，开始创建试卷，随机生成试题
        ExamPaper examPaper = new ExamPaper();
        examPaper.setCreatetime(new Date());
        examPaper.setExamid(exam.getId());
        examPaper.setStatus(0);
        examPaper.setStudentid(student.getId());
        examPaper.setTotalscore(exam.getTotalscore());
        examPaper.setUsetime(0);
        if (examPaperService.add(examPaper) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "试卷生成失败，请联系管理员!");
            return ret;
        }
        // 试卷已经正确生成，现在开始随机生成试题
        Map<String, Object> queryQuestionMap = new HashMap<String, Object>();
        queryQuestionMap.put("type", Question.QUESTION_TYPE_SINGLE);
        queryQuestionMap.put("subjectId", exam.getSubjectid());
        queryQuestionMap.put("offset", 0);
        queryQuestionMap.put("pageSize", 99999);
        if (exam.getSinglequestionnum() > 0) {
            // 考试规定单选题数量大于0
            // 获取所有的单选题
            List<Question> singleQuestionList = questionService.findList(queryQuestionMap);
            // 随机选取考试规定数量的单选题，插入到数据库中
            List<Question> selectedSingleQuestionList = getRandomList(singleQuestionList, exam.getSinglequestionnum());
            insertQuestionToExamPaperCreate(selectedSingleQuestionList, exam.getId(), examPaper.getId(), null);
        }
        if (exam.getMuiltquestionnum() > 0) {
            queryQuestionMap.put("type", Question.QUESTION_TYPE_MUILT);
            // 获取所有的多选题
            List<Question> muiltQuestionList = questionService.findList(queryQuestionMap);
            for (Question q : muiltQuestionList) {
                logger.info("[-----------start_exam-----muiltQuestionList多选题---------]" + q.toString());
            }
            // 随机选取考试规定数量的多选题，插入到数据库中
            List<Question> selectedMuiltQuestionList = getRandomList(muiltQuestionList, exam.getMuiltquestionnum());
            insertQuestionToExamPaperCreate(selectedMuiltQuestionList, exam.getId(), examPaper.getId(), null);

        }
        if (exam.getChargequestionnum() > 0) {
            // 获取所有的判断题
            queryQuestionMap.put("type", Question.QUESTION_TYPE_CHARGE);
            List<Question> chargeQuestionList = questionService.findList(queryQuestionMap);
            for (Question q : chargeQuestionList) {
                logger.info("[-----------start_exam-----chargeQuestionList判断题---------]" + q.toString());
            }
            // 随机选取考试规定数量的判断题，插入到数据库中
            List<Question> selectedChargeQuestionList = getRandomList(chargeQuestionList, exam.getChargequestionnum());
            insertQuestionToExamPaperCreate(selectedChargeQuestionList, exam.getId(), examPaper.getId(), null);
        }
        if (exam.getWritequestionnum() > 0) {
            // 获取所有的客观题
            queryQuestionMap.put("type", Question.QUESTION_TYPE_WRITER);
            List<Question> writerQuestionList = questionService.findList(queryQuestionMap);
            for (Question q : writerQuestionList) {
                logger.info("[-----------start_exam-----writerQuestionList客观题---------]" + q.toString());
            }
            // 随机选取考试规定数量的判断题，插入到数据库中
            List<Question> selectedWriterQuestionList = getRandomList(writerQuestionList, exam.getWritequestionnum());
            insertQuestionToExamPaperCreate(selectedWriterQuestionList, exam.getId(), examPaper.getId(), null);
        }
        // 走到这里表示试卷已经生成，但是没有提交考试，可以开始重新考试
        List<ExamPaperCreate> examPaperCreateList = examPaperCreateService.findByExamId(examId);
        for (ExamPaperCreate e : examPaperCreateList) {
            ExamPaperAnswer examPaperAnswer = new ExamPaperAnswer();
            examPaperAnswer.setExamid(e.getExamid());
            examPaperAnswer.setExampaperid(e.getExampaperid());
            examPaperAnswer.setIscorrect(0);
            examPaperAnswer.setQuestionid(e.getQuestionid());
            examPaperAnswer.setStudentid(student.getId());
            examPaperAnswerService.add(examPaperAnswer);
        }
        ret.put("type", "success");
        ret.put("msg", "试卷生成成功!");
        return ret;
    }

    /**
     * 随机从给定的list中选取给定数量的元素
     * 
     * @param questionList
     * @param n
     * @return
     */
    private List<Question> getRandomList(List<Question> questionList, int n) {
        if (questionList.size() <= n)
            return questionList;
        Map<Integer, String> selectedMap = new HashMap<Integer, String>();
        List<Question> selectedList = new ArrayList<Question>();
        while (selectedList.size() < n) {
            for (Question question : questionList) {
                int index = (int) (Math.random() * questionList.size());
                if (!selectedMap.containsKey(index)) {
                    selectedMap.put(index, "");
                    selectedList.add(questionList.get(index));
                    if (selectedList.size() >= n)
                        break;
                }
            }
        }
        return selectedList;
    }

    /**
     * 插入指定数量的试题到答题详情表
     * 
     * @param questionList
     * @param examId
     * @param examPaperId
     * @param studentId
     */
    private void insertQuestionToExamPaperCreate(List<Question> questionList, Integer examId, Integer examPaperId,
            Integer studentId) {
        for (Question question : questionList) {
            logger.info("[----------------insertQuestionToExamPaperCreate---------]" + question.toString());
//            System.err.println("【insertQuestionToExamPaperCreate】"+question.toString());
            ExamPaperCreate examPaperCreate = new ExamPaperCreate();
            examPaperCreate.setExamid(examId);
            examPaperCreate.setExampaperid(examPaperId);
            examPaperCreate.setQuestionid(question.getId());
            examPaperCreate.setStudentid(studentId);
            examPaperCreate.setAnswer(question.getAnswer());
            examPaperCreateService.add(examPaperCreate);
        }
    }

    /**
     * 开始进行考试
     * 
     * @param model
     * @param examId
     * @param request
     * @return
     */
    @RequestMapping(value = "/examing", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView model, Integer examId, HttpServletRequest request) {
        Student student = (Student) request.getSession().getAttribute("student");
        Exam exam = examService.findById(examId);
        logger.info("[-----------examing-----student-----]" + student + "[---examing---exam--]" + exam.toString());
        if (exam == null) {
            model.setViewName("before/exam/error");
            model.addObject("msg", "当前考试不存在!");
            return model;
        }
        if (exam.getEndtime().getTime() < new Date().getTime()) {
            model.setViewName("before/exam/error");
            model.addObject("msg", "当前考试时间已经过期!");
            return model;
        }
        if (exam.getSubjectid().longValue() != student.getSubjectid().longValue()) {
            model.setViewName("before/exam/error");
            model.addObject("msg", "您所属科目与考试科目不符合，不能进行考试!");
            return model;
        }
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("examid", examId);
        queryMap.put("studentid", student.getId());
        // 根据考试信息和学生信息获取试卷
        ExamPaper examPaper = examPaperService.find(queryMap);
        logger.info("[-----------examing-----examPaper-----]" + examPaper.toString());
        if (examPaper == null) {
            model.setViewName("before/exam/error");
            model.addObject("msg", "当前考试不存在试卷");
            return model;
        }
        if (examPaper.getStatus() == 1) {
            model.setViewName("before/exam/error");
            model.addObject("msg", "您已经考过这门考试了！");
            return model;
        }
        Date now = new Date();
        Object attributeStartTime = request.getSession().getAttribute("startExamTime");
        if (attributeStartTime == null) {
            request.getSession().setAttribute("startExamTime", now);
        }
        Date startExamTime = (Date) request.getSession().getAttribute("startExamTime");
        int passedTime = (int) (now.getTime() - startExamTime.getTime()) / 1000 / 60;
        if (passedTime >= exam.getAvaliabletime()) {
            // 表示时间已经耗尽，按零分处理
            examPaper.setScore(0);
            examPaper.setStartexamtime(startExamTime);
            examPaper.setStatus(1);
            examPaper.setUsetime(passedTime);
            examPaperService.submitPaper(examPaper);
            model.setViewName("before/exam/error");
            model.addObject("msg", "当前考试时间已耗尽，还未提交试卷，做0分处理！");
            return model;
        }
        Integer hour = (exam.getAvaliabletime() - passedTime) / 60;
        Integer minitute = (exam.getAvaliabletime() - passedTime) % 60;
        Integer second = (exam.getAvaliabletime() * 60 - (int) (now.getTime() - startExamTime.getTime()) / 1000) % 60;
        queryMap.put("exampaperid", examPaper.getId());
        List<ExamPaperAnswer> findListByUser = examPaperAnswerService.findListByUser(queryMap);

        model.addObject("title", exam.getName() + "-开始考试");
        model.addObject("singleQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_SINGLE));
        model.addObject("muiltQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_MUILT));
        model.addObject("chargeQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_CHARGE));
        model.addObject("writerQuestionList", getExamPaperAnswerList(findListByUser, Question.QUESTION_TYPE_WRITER));
        model.addObject("hour", hour);
        model.addObject("minitute", minitute);
        model.addObject("second", second);
        model.addObject("exam", exam);
        model.addObject("examPaper", examPaper);
        model.addObject("singleScore", Question.QUESTION_TYPE_SINGLE_SCORE);
        model.addObject("muiltScore", Question.QUESTION_TYPE_MUILT_SCORE);
        model.addObject("chargeScore", Question.QUESTION_TYPE_CHARGE_SCORE);
        model.addObject("writerScore", Question.QUESTION_TYPE_WRITER_SCORE);
        model.addObject("singleQuestion", Question.QUESTION_TYPE_SINGLE);
        model.addObject("writerQuestion", Question.QUESTION_TYPE_WRITER);
        model.addObject("muiltQuestion", Question.QUESTION_TYPE_MUILT);
        model.addObject("chargeQuestion", Question.QUESTION_TYPE_CHARGE);
        model.setViewName("/before/exam/examing");
        return model;
    }

    /**
     * 返回指定类型的试题
     * 
     * @param examPaperAnswers
     * @param questionType
     * @return
     */
    private List<Map<ExamPaperAnswer, List<Question_Option>>> getExamPaperAnswerList(
            List<ExamPaperAnswer> examPaperAnswers, int questionType) {
        List<Map<ExamPaperAnswer, List<Question_Option>>> lists = new ArrayList<>();
        Map<ExamPaperAnswer, List<Question_Option>> qutionAns = new HashMap<>();
        for (ExamPaperAnswer examPaperAnswer : examPaperAnswers) {
            if (examPaperAnswer.getQuestion().getType() == questionType) {
                Integer qid = examPaperAnswer.getQuestionid();
                // 根据questionid查询出该题目下的选项
                List<Question_Option> qolist = questionService.findQuestionAndOptionById(qid);
//                System.err.println(examPaperAnswer.toString());
                // 将问题和选项保存在map中
                qutionAns.put(examPaperAnswer, qolist);
                lists.add(qutionAns);
            }
        }
        return lists;
    }

    /**
     * 用户选择答题提交单个答案
     * 
     * @param examPaperAnswer
     * @param request
     * @return
     */
    @RequestMapping(value = "/submit_answer", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> submitAnswer(ExamPaperAnswer examPaperAnswer, HttpServletRequest request) {
//        System.err.println("【submit_answer】"+examPaperAnswer.toString());
        Map<String, String> ret = new HashMap<String, String>();
        if (examPaperAnswer == null) {
            ret.put("type", "error");
            ret.put("msg", "请正确操作!");
            return ret;
        }
        Exam exam = examService.findById(examPaperAnswer.getExamid());
        if (exam == null) {
            ret.put("type", "error");
            ret.put("msg", "考试信息不存在!");
            return ret;
        }
        if (exam.getEndtime().getTime() < new Date().getTime()) {
            ret.put("type", "error");
            ret.put("msg", "该考试已结束!");
            return ret;
        }
        Student student = (Student) request.getSession().getAttribute("student");
        if (exam.getSubjectid().longValue() != student.getSubjectid().longValue()) {
            ret.put("type", "error");
            ret.put("msg", "学科不同，无权进行考试!");
            return ret;
        }
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("examid", exam.getId());
        queryMap.put("studentid", student.getId());
        ExamPaper findExamPaper = examPaperService.find(queryMap);
        if (findExamPaper == null) {
            ret.put("type", "error");
            ret.put("msg", "不存在试卷!");
            return ret;
        }
        if (findExamPaper.getId().longValue() != examPaperAnswer.getExampaperid().longValue()) {
            ret.put("type", "error");
            ret.put("msg", "考试试卷不正确，请规范操作!");
            return ret;
        }
        Question question = questionService.findQuestionById(examPaperAnswer.getQuestionid());
        if (question == null) {
            ret.put("type", "error");
            ret.put("msg", "试题不存在，请规范操作!");
            return ret;
        }
        // 此时，可以将答案插入到数据库中
        examPaperAnswer.setStudentid(student.getId());
        if (question.getAnswer().equals(examPaperAnswer.getAnswer())) {
            examPaperAnswer.setIscorrect(1);
        } else {
            examPaperAnswer.setIscorrect(0);
        }
        if (examPaperAnswerService.submitAnswer(examPaperAnswer) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "答题出错，请联系管理员!");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "答题成功!");
        return ret;
    }

    /**
     * 提交
     * 
     * @param examId
     * @param examPaperId
     * @param request
     * @return
     */
    @RequestMapping(value = "/submit_exam", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> submitExam(Integer examId, Integer examPaperId, String questionString,
            HttpServletRequest request) {
//        System.err.println(questionString);
        JSONArray jsonArray = new JSONArray(questionString);
        ExamPaperAnswer examPaperAnswerWriter = new ExamPaperAnswer();
        Student student = (Student) request.getSession().getAttribute("student");
        for (int j = 0; j < jsonArray.size(); j++) {
            // 关联表插入
            JSONObject subImageInfoObject = jsonArray.getJSONObject(j);
            Integer questionid = subImageInfoObject.getInt("questionid");
            String answer = subImageInfoObject.getStr("answer");
            examPaperAnswerWriter.setQuestionid(questionid);
            examPaperAnswerWriter.setAnswer(answer);
            examPaperAnswerWriter.setExamid(examId);
            examPaperAnswerWriter.setExampaperid(examPaperId);
            examPaperAnswerWriter.setStudentid(student.getId());
            logger.info("[-----------submit_exam-----examPaperAnswerWriter-----]" + examPaperAnswerWriter.toString());
            // 插入问答题的答案
            examPaperAnswerService.submitWriterAnswer(examPaperAnswerWriter);
        }
        Map<String, String> ret = new HashMap<String, String>();
        Exam exam = examService.findById(examId);
        if (exam == null) {
            ret.put("type", "error");
            ret.put("msg", "考试不存在，请正确操作!");
            return ret;
        }
        if (exam.getEndtime().getTime() < new Date().getTime()) {
            ret.put("type", "error");
            ret.put("msg", "该考试已结束!");
            return ret;
        }

        if (exam.getSubjectid().longValue() != student.getSubjectid().longValue()) {
            ret.put("type", "error");
            ret.put("msg", "学科不同，无权进行操作!");
            return ret;
        }
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("examid", exam.getId());
        queryMap.put("studentid", student.getId());
        ExamPaper findExamPaper = examPaperService.find(queryMap);
        if (findExamPaper == null) {
            ret.put("type", "error");
            ret.put("msg", "不存在试卷!");
            return ret;
        }
        if (findExamPaper.getId().longValue() != examPaperId.longValue()) {
            ret.put("type", "error");
            ret.put("msg", "考试试卷不正确，请规范操作!");
            return ret;
        }
        if (findExamPaper.getStatus() == 1) {
            ret.put("type", "error");
            ret.put("msg", "请勿重复交卷!");
            return ret;
        }
        // 此时计算考试得分
        queryMap.put("exampaperid", examPaperId);
        List<ExamPaperAnswer> examPaperAnswerList = examPaperAnswerService.findListByUser(queryMap);
        // 此时，可以将答案插入到数据库中
        int score = 0;
        for (ExamPaperAnswer examPaperAnswer : examPaperAnswerList) {
            if (examPaperAnswer.getIscorrect() == 1) {
                score += examPaperAnswer.getQuestion().getScore();
            }
        }
        findExamPaper.setEndexamtime(new Date());
        findExamPaper.setScore(score);
        findExamPaper.setStartexamtime((Date) request.getSession().getAttribute("startExamTime"));
        findExamPaper.setStatus(1);
        findExamPaper.setUsetime(
                (int) (findExamPaper.getEndexamtime().getTime() - findExamPaper.getStartexamtime().getTime()) / 1000
                        / 60);
        examPaperService.submitPaper(findExamPaper);
        // 计算考试统计结果,更新考试的已考人数，及格人数
        exam.setExamedNum(exam.getExamedNum() + 1);
        if (findExamPaper.getScore() >= exam.getPassscore()) {
            // 说明及格了
            logger.info("-------------exam.getPassnum() + 1----------------"+exam.getPassnum() + 1);
            if (null==exam.getPassnum()){
                exam.setPassnum(1);
            }
            exam.setPassnum(exam.getPassnum() + 1);
        }
        request.getSession().setAttribute("startExamTime", null);
        // 更新统计结果
        examService.updateExam(exam);
        ret.put("type", "success");
        ret.put("msg", "提交成功!");
        return ret;
    }

}
