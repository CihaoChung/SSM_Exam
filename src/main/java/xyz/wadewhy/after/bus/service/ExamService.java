package xyz.wadewhy.after.bus.service;

import java.util.List;
import java.util.Map;

import xyz.wadewhy.after.bus.domain.Exam;

public interface ExamService {
    List<Exam> findList(Map<String, Object> queryMap);

    int getTotal(Map<String, Object> queryMap);

    int add(Exam exam);

    List<Exam> findListByUser(Map<String, Object> queryMap);

    Exam findById(Integer examId);

    int updateExam(Exam exam);

    /**
     * @Title: edit
     * @Description: TODO
     * @param exam
     * @return int
     * @author wadewhy
     * @date 2020年7月4日下午10:27:35
     */
    int edit(Exam exam);

    /**
     * @Title: delete
     * @Description: TODO
     * @param id
     * @return int
     * @author wadewhy
     * @date 2020年7月4日下午10:27:47
     */
    int delete(Integer id);
}
