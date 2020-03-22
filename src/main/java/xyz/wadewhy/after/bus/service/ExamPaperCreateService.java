package xyz.wadewhy.after.bus.service;

import xyz.wadewhy.after.bus.domain.ExamPaperCreate;

import java.util.List;

public interface ExamPaperCreateService {
    int add(ExamPaperCreate examPaperCreate);

    List<ExamPaperCreate> findByExamId(Integer examId);
}
