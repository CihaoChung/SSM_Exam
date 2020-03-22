package xyz.wadewhy.after.sys.service;

import xyz.wadewhy.after.sys.domain.Log;

import java.util.List;
import java.util.Map;

public interface LogService {
    public int add(Log log);
    public int add(String content);
    public List<Log> findList(Map<String, Object> queryMap);
    public int getTotal(Map<String, Object> queryMap);
    public int delete(String ids);
}
