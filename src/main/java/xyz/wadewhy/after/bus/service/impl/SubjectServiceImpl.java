package xyz.wadewhy.after.bus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.wadewhy.after.bus.domain.Subject;
import xyz.wadewhy.after.bus.mapper.SubjectMapper;
import xyz.wadewhy.after.bus.service.SubjectService;

import java.util.List;
import java.util.Map;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
   private SubjectMapper subjectMapper;

    /**
     * 查询
     * @param queryMap
     * @return
     */
    @Override
    public List<Subject> findList(Map<String, Object> queryMap) {
        return subjectMapper.findList(queryMap);
    }

    /**
     * 总数
     * @param queryMap
     * @return
     */
    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return subjectMapper.getTotal(queryMap);
    }

    /**
     * 根据id查询科目
     * @param id
     * @return
     */
    @Override
    public Subject findSubjectById(Integer id) {
        return subjectMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加科目
     * @param subject
     * @return
     */
    @Override
    public int add(Subject subject) {
        return subjectMapper.insert(subject);
    }

    /**
     * 查询是否存在同一名称的科目
     * @param name
     * @return
     */
    @Override
    public Subject findSubjectByName(String name,Integer id) {
        return subjectMapper.findSubjectByName(name,id);
    }

    /**
     * 更新
     * @param subject
     * @return
     */
    @Override
    public int edit(Subject subject) {
        return subjectMapper.updateByPrimaryKey(subject);
    }

    @Override
    public int deleteSubjectById(Integer id) {
        return subjectMapper.deleteByPrimaryKey(id);
    }
}
