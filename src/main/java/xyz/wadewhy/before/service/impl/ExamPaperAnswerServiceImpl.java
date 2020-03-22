package xyz.wadewhy.before.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.wadewhy.before.bus.mapper.ExamPaperAnswerMapper;
import xyz.wadewhy.before.service.ExamPaperAnswerService;
import xyz.wadewhy.before.domain.ExamPaperAnswer;


import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: xyz.wadewhy.before.homeservice.impl
 * @NAME: ExamPaperAnswerServiceImpl
 * @Author: 钟子豪
 * @DATE: 2020/3/21
 * @MONTH_NAME_FULL: 三月
 * @DAY: 21
 * @DAY_NAME_FULL: 星期六
 * @PROJECT_NAME: OnlineExam
 **/
@Service
public class ExamPaperAnswerServiceImpl implements ExamPaperAnswerService {
    @Autowired
    private ExamPaperAnswerMapper examPaperAnswerMapper;

    @Override
    public int add(ExamPaperAnswer examPaperAnswer) {
        return examPaperAnswerMapper.insertSelective(examPaperAnswer);
    }

    @Override
    public List<ExamPaperAnswer> findListByUser(Map<String, Object> queryMap) {
        return examPaperAnswerMapper.findListByUser(queryMap);
    }

    @Override
    public int submitAnswer(ExamPaperAnswer examPaperAnswer) {
        return examPaperAnswerMapper.updateByPrimaryKeySelective(examPaperAnswer);
    }

    @Override
    public int submitWriterAnswer(ExamPaperAnswer examPaperAnswer) {
        return examPaperAnswerMapper.submitWriterAnswer(examPaperAnswer);
    }

    @Override
    public List<ExamPaperAnswer> findList(Map<String, Object> queryMap) {
        return examPaperAnswerMapper.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return examPaperAnswerMapper.getTotal(queryMap);
    }
}
