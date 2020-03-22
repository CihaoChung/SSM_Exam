package xyz.wadewhy.before.service;

import xyz.wadewhy.before.domain.ExamPaperAnswer;

import java.util.List;
import java.util.Map;

public interface ExamPaperAnswerService {
    int add(ExamPaperAnswer examPaperAnswer);

    List<ExamPaperAnswer> findListByUser(Map<String, Object> queryMap);

    int submitAnswer(ExamPaperAnswer examPaperAnswer);

    int submitWriterAnswer(ExamPaperAnswer examPaperAnswer);

    List<ExamPaperAnswer> findList(Map<String, Object> queryMap);

    int getTotal(Map<String, Object> queryMap);
}
