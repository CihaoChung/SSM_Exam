package xyz.wadewhy.after.bus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.wadewhy.after.bus.domain.ExamPaperCreate;
import xyz.wadewhy.after.bus.mapper.ExamPaperCreateMapper;
import xyz.wadewhy.after.bus.service.ExamPaperCreateService;

import java.util.List;

@Service
public class ExamPaperCreateServiceImpl implements ExamPaperCreateService {
    @Autowired
    private ExamPaperCreateMapper examPaperCreateMapper;

    @Override
    public int add(ExamPaperCreate examPaperCreate) {
        return examPaperCreateMapper.insertSelective(examPaperCreate);
    }

    @Override
    public List<ExamPaperCreate> findByExamId(Integer examId) {
        return examPaperCreateMapper.findByExamId(examId);
    }
}
