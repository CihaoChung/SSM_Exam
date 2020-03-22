package xyz.wadewhy.after.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.wadewhy.after.sys.domain.Permission;
import xyz.wadewhy.after.sys.mapper.MenuMapper;
import xyz.wadewhy.after.sys.service.MenuService;

import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.homeservice.impl
 * @NAME: MenuServiceImpl
 * @Author: 钟子豪
 * @DATE: 2020/3/14
 * @MONTH_NAME_FULL: 三月
 * @DAY: 14
 * @DAY_NAME_FULL: 星期六
 * @PROJECT_NAME: OnlineExam
 **/
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
   private  MenuMapper menuMapper;
    @Override
    public List<Permission> findList(Map<String, Object> queryMap) {
        return menuMapper.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return menuMapper.getTotal(queryMap);
    }

    @Override
    public int add(Permission permission) {
        return menuMapper.insertSelective(permission);
    }
    public int edit(Permission permission){
        return menuMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public int delete(Integer id) {
        return menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Permission> findChildernList(Integer id) {
        return menuMapper.findChildernList(id);
    }
}
