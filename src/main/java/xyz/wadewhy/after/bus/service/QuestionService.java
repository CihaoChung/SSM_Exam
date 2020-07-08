package xyz.wadewhy.after.bus.service;

import xyz.wadewhy.after.bus.domain.Question;
import xyz.wadewhy.after.bus.domain.Question_Option;
import xyz.wadewhy.after.bus.domain.Subject;

import java.util.List;
import java.util.Map;

public interface QuestionService {
    int insertQuestionAndGetId(Question question);

    int insertQuestion_Option(Question_Option question_option);

    List<Subject> findSubject();

    List<Question> findList(Map<String, Object> queryMap);
    //全查询
    List<Question> findAllQuestion();

    int getTotal(Map<String, Object> queryMap);

    Question findQuestionById(Integer questionId);

    List<Question_Option> findQuestionAndOptionById(Integer questionId);

    int UpdateQuestion_Option(Question_Option question_option);

    int UpdateQuestion(Question question);

    int deleteById(Integer id);

    int getQuestionNumByType(Map<String, Integer> queryMap);

//    Question findById(Integer questionid);
}
