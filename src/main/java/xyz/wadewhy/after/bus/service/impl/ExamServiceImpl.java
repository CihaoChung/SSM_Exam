package xyz.wadewhy.after.bus.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.wadewhy.after.bus.domain.Exam;
import xyz.wadewhy.after.bus.mapper.ExamMapper;
import xyz.wadewhy.after.bus.service.ExamService;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.bus.homeservice.impl
 * @NAME: ExamServiceImpl
 * @Author: 钟子豪
 * @DATE: 2020/3/20
 * @MONTH_NAME_FULL: 三月
 * @DAY: 20
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 **/
@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamMapper examMapper;

    @Override
    public List<Exam> findList(Map<String, Object> queryMap) {
        return examMapper.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return examMapper.getTotal(queryMap);
    }

    @Override
    public int add(Exam exam) {
        return examMapper.insertSelective(exam);
    }

    @Override
    public List<Exam> findListByUser(Map<String, Object> queryMap) {
        return examMapper.findListByUser(queryMap);
    }

    @Override
    public Exam findById(Integer examId) {
        return examMapper.selectByPrimaryKey(examId);
    }

    @Override
    public int updateExam(Exam exam) {
        return examMapper.updateByPrimaryKeySelective(exam);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * xyz.wadewhy.after.bus.service.ExamService#edit(xyz.wadewhy.after.bus.domain.
     * Exam)
     */
    @Override
    public int edit(Exam exam) {
        // TODO Auto-generated method stub
        return examMapper.updateByPrimaryKeySelective(exam);
    }

    /*
     * (non-Javadoc)
     * 
     * @see xyz.wadewhy.after.bus.service.ExamService#delete(java.lang.Long)
     */
    @Override
    public int delete(Integer id) {
        // TODO Auto-generated method stub
        return examMapper.deleteByPrimaryKey(id);
    }
}
