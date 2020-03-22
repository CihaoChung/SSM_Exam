package xyz.wadewhy.after.sys.service;

import xyz.wadewhy.after.sys.domain.Permission;

import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.homeservice
 * @NAME: MenuService
 * @Author: 钟子豪
 * @DATE: 2020/3/14
 * @MONTH_NAME_FULL: 三月
 * @DAY: 14
 * @DAY_NAME_FULL: 星期六
 * @PROJECT_NAME: OnlineExam
 **/
public interface MenuService {
     List<Permission> findList(Map<String, Object> queryMap);

     int getTotal(Map<String, Object> queryMap);

    int add(Permission permission);

    int edit(Permission permission);

    int delete(Integer id);

    List<Permission> findChildernList(Integer id);
}
