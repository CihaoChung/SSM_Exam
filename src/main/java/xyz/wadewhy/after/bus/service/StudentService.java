package xyz.wadewhy.after.bus.service;

import xyz.wadewhy.after.bus.domain.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {
    List<Student> findList(Map<String, Object> queryMap);

    int getTotal(Map<String, Object> queryMap);

    Student findByName(String name, Long id);

    int add(Student student);

    int edit(Student student);

    int delete(Integer id);
}
