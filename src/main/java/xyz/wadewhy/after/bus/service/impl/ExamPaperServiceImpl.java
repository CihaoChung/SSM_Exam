package xyz.wadewhy.after.bus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.wadewhy.after.bus.domain.ExamPaper;
import xyz.wadewhy.after.bus.mapper.ExamMapper;
import xyz.wadewhy.after.bus.mapper.ExamPaperMapper;
import xyz.wadewhy.after.bus.service.ExamPaperService;

import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.bus.homeservice.impl
 * @NAME: ExamPaperServiceImpl
 * @Author: 钟子豪
 * @DATE: 2020/3/21
 * @MONTH_NAME_FULL: 三月
 * @DAY: 21
 * @DAY_NAME_FULL: 星期六
 * @PROJECT_NAME: OnlineExam
 **/
@Service
public class ExamPaperServiceImpl implements ExamPaperService {
    @Autowired
    private ExamPaperMapper examPaperMapper;
    @Override
    public int add(ExamPaper examPaper) {
        return examPaperMapper.insertSelective(examPaper);
    }

    @Override
    public int delete(Integer id) {
        return examPaperMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return examPaperMapper.getTotal(queryMap);
    }

    @Override
    public List<Map<String, Object>> getExamStats(Integer examId) {
        return examPaperMapper.getExamStats(examId);
    }

    @Override
    public ExamPaper find(Map<String, Object> queryMap) {
        return examPaperMapper.find(queryMap);
    }

    @Override
    public int submitPaper(ExamPaper examPaper) {
        return examPaperMapper.submitPaper(examPaper);
    }

    @Override
    public int update(ExamPaper find) {
        return examPaperMapper.updateByPrimaryKeySelective(find);
    }

    @Override
    public List<ExamPaper> findHistory(Map<String, Object> queryMap) {
        return examPaperMapper.findHistory(queryMap);
    }

    @Override
    public List<ExamPaper> findList(Map<String, Object> queryMap) {
        return examPaperMapper.findList(queryMap);
    }

    @Override
    public int edit(ExamPaper examPaper) {
        return examPaperMapper.updateByPrimaryKeySelective(examPaper);
    }
}
