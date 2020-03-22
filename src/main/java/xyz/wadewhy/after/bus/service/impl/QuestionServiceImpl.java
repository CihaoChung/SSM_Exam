package xyz.wadewhy.after.bus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.wadewhy.after.bus.domain.Question;
import xyz.wadewhy.after.bus.domain.Question_Option;
import xyz.wadewhy.after.bus.domain.Subject;
import xyz.wadewhy.after.bus.mapper.QuestionMapper;
import xyz.wadewhy.after.bus.mapper.Question_OptionMapper;
import xyz.wadewhy.after.bus.mapper.SubjectMapper;
import xyz.wadewhy.after.bus.service.QuestionService;
import xyz.wadewhy.after.bus.service.SubjectService;

import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.bus.homeservice.impl
 * @NAME: QuestionServiceImpl
 * @Author: 钟子豪
 * @DATE: 2020/3/19
 * @MONTH_NAME_FULL: 三月
 * @DAY: 19
 * @DAY_NAME_FULL: 星期四
 * @PROJECT_NAME: OnlineExam
 **/
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private Question_OptionMapper question_optionMapper;
    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public int insertQuestionAndGetId(Question question) {
        return questionMapper.insertQuestionAndGetId(question);
    }

    /**
     * question_option中间表插入数据
     * @param question_option
     * @return
     */
    @Override
    public int insertQuestion_Option(Question_Option question_option) {
        return question_optionMapper.insertSelective(question_option);
    }

    @Override
    public List<Subject> findSubject() {
        return subjectMapper.findList(null);
    }

    @Override
    public List<Question> findList(Map<String, Object> queryMap) {
        return questionMapper.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return questionMapper.getTotal(queryMap);
    }

    @Override
    public Question findQuestionById(Integer questionId) {
        return questionMapper.selectByPrimaryKey(questionId);
    }

    @Override
    public List<Question_Option> findQuestionAndOptionById(Integer questionId) {
        return question_optionMapper.findQuestionAndOptionById(questionId);
    }

    @Override
    public int UpdateQuestion_Option(Question_Option question_option) {
        return question_optionMapper.UpdateQuestion_Option(question_option);
    }

    @Override
    public int UpdateQuestion(Question question) {
        return questionMapper.updateByPrimaryKeySelective(question);
    }

    @Override
    public int deleteById(Integer id) {
        return questionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int getQuestionNumByType(Map<String, Integer> queryMap) {
        return questionMapper.getQuestionNumByType(queryMap);
    }
}
