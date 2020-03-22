package xyz.wadewhy.after.bus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.wadewhy.after.bus.domain.Student;
import xyz.wadewhy.after.bus.mapper.StudentMapper;
import xyz.wadewhy.after.bus.service.StudentService;

import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.bus.homeservice.impl
 * @NAME: StudentServiceImpl
 * @Author: 钟子豪
 * @DATE: 2020/3/20
 * @MONTH_NAME_FULL: 三月
 * @DAY: 20
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 **/
@Service
public class StudentServiceImpl implements StudentService {
@Autowired
private StudentMapper studentMapper;
    @Override
    public List<Student> findList(Map<String, Object> queryMap) {
        return studentMapper.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return studentMapper.getTotal(queryMap);
    }

    @Override
    public Student findByName(String name, Long id) {
        return studentMapper.findByName(name,id);
    }

    @Override
    public int add(Student student) {
        return studentMapper.insertSelective(student);
    }

    @Override
    public int edit(Student student) {
        return studentMapper.updateByPrimaryKeySelective(student);
    }

    @Override
    public int delete(Integer id) {
        return studentMapper.deleteByPrimaryKey(id);
    }
}
