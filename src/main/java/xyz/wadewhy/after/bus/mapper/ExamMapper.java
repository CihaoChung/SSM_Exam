package xyz.wadewhy.after.bus.mapper;

import xyz.wadewhy.after.bus.domain.Exam;

import java.util.List;
import java.util.Map;

public interface ExamMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_exam
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_exam
     *
     * @mbg.generated
     */
    int insert(Exam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_exam
     *
     * @mbg.generated
     */
    int insertSelective(Exam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_exam
     *
     * @mbg.generated
     */
    Exam selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_exam
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Exam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_exam
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Exam record);

    List<Exam> findList(Map<String, Object> queryMap);

    int getTotal(Map<String, Object> queryMap);

    List<Exam> findListByUser(Map<String, Object> queryMap);

//    int updateExam(Exam exam);
}