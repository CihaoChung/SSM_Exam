package xyz.wadewhy.after.bus.service;

import xyz.wadewhy.after.bus.domain.Exam;

import java.util.List;
import java.util.Map;

public interface ExamService {
    List<Exam> findList(Map<String, Object> queryMap);

    int getTotal(Map<String, Object> queryMap);

    int add(Exam exam);

    List<Exam> findListByUser(Map<String, Object> queryMap);

    Exam findById(Integer examId);

    int updateExam(Exam exam);
}
