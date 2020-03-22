package xyz.wadewhy.after.bus.service;

import xyz.wadewhy.after.bus.domain.Subject;

import java.util.List;
import java.util.Map;

public interface SubjectService {
    List<Subject> findList(Map<String, Object> queryMap);

    int getTotal(Map<String, Object> queryMap);

    Subject findSubjectById(Integer id);

    int add(Subject subject);

    Subject findSubjectByName(String name,Integer id);

    int edit(Subject subject);

    int deleteSubjectById(Integer id);
}
