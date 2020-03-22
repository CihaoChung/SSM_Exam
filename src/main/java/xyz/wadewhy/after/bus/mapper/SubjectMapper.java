package xyz.wadewhy.after.bus.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.wadewhy.after.bus.domain.Subject;

import java.util.List;
import java.util.Map;

public interface SubjectMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_subject
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_subject
     *
     * @mbg.generated
     */
    int insert(Subject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_subject
     *
     * @mbg.generated
     */
    int insertSelective(Subject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_subject
     *
     * @mbg.generated
     */
    Subject selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_subject
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Subject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bus_subject
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Subject record);

    List<Subject> findList(Map<String, Object> queryMap);

    int getTotal(Map<String, Object> queryMap);

    Subject findSubjectByName(@Param("name") String name, @Param("id") Integer id);
}