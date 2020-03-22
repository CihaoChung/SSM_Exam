package xyz.wadewhy.after.bus.service;

import xyz.wadewhy.after.bus.domain.ExamPaper;

import java.util.List;
import java.util.Map;

public interface ExamPaperService {

    ExamPaper find(Map<String, Object> queryMap);

    int submitPaper(ExamPaper examPaper);

    int update(ExamPaper find);

    List<ExamPaper> findHistory(Map<String, Object> queryMap);

    List<ExamPaper> findList(Map<String, Object> queryMap);
    int edit(ExamPaper examPaper);
    int add(ExamPaper examPaper);
    int delete(Integer id);

    int getTotal(Map<String, Object> queryMap);

    List<Map<String, Object>> getExamStats(Integer examId);
}
