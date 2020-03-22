package xyz.wadewhy.after.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.wadewhy.after.sys.domain.Log;
import xyz.wadewhy.after.sys.mapper.LogMapper;
import xyz.wadewhy.after.sys.service.LogService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.service.impl
 * @NAME: LogServiceImpl
 * @Author: 钟子豪
 * @DATE: 2020/3/22
 * @MONTH_NAME_FULL: 三月
 * @DAY: 22
 * @DAY_NAME_FULL: 星期日
 * @PROJECT_NAME: OnlineExam
 **/
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public int add(Log log) {
        // TODO Auto-generated method stub
        return logMapper.add(log);
    }

    @Override
    public List<Log> findList(Map<String, Object> queryMap) {
        // TODO Auto-generated method stub
        return logMapper.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        // TODO Auto-generated method stub
        return logMapper.getTotal(queryMap);
    }

    @Override
    public int delete(String ids) {
        // TODO Auto-generated method stub
        return logMapper.delete(ids);
    }

    @Override
    public int add(String content) {
        // TODO Auto-generated method stub
        Log log = new Log();
        log.setContent(content);
        log.setCreatetime(new Date());
        return logMapper.add(log);
    }
}
