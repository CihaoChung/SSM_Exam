package xyz.wadewhy.after.sys.common;

import xyz.wadewhy.after.sys.domain.Permission;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.common
 * @NAME: MenuUtil
 * @Author: 钟子豪
 * @DATE: 2020/3/14
 * @MONTH_NAME_FULL: 三月
 * @DAY: 14
 * @DAY_NAME_FULL: 星期六
 * @PROJECT_NAME: OnlineExam
 * 菜单转换类
 **/
public class MenuUtil {


    /**
     * 得到顶级菜单
     * @param menuList
     * @return
     */
    public static List<Permission> getAllTopMenu(List<Permission> menuList){
        List<Permission> ret = new ArrayList<>();
        for(Permission menu:menuList){
            if(menu.getPid() == 0){
                ret.add(menu);
            }
        }
       /* for (Permission p:ret
             ) {
            System.err.println("【getAllTopMenu】"+p.toString());
        }*/
        return ret;
    }

    /**
     * 得到二级菜单
     * @param menuList
     * @return
     */
    public static List<Permission> getAllSecondMenu(List<Permission> menuList){
        List<Permission> ret = new ArrayList<>();
        List<Permission> allTopMenu = getAllTopMenu(menuList);
        for(Permission menu:menuList){
            for(Permission topMenu:allTopMenu){
                if(menu.getPid() == topMenu.getId()){
                    ret.add(menu);
                    break;
                }
            }
        }
     /*   for (Permission p:ret
        ) {
            System.err.println("【getAllSecondMenu】"+p.toString());
        }*/
        return ret;
    }

    public static List<Permission> getAllThirdMenu(List<Permission> menuList, Integer secondMenuId) {
        List<Permission> ret = new ArrayList<>();
        for(Permission menu:menuList){
            if(menu.getPid() == secondMenuId){
                ret.add(menu);
            }
        }
        return ret;
    }
}
