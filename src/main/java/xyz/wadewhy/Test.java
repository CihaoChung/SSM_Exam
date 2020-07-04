package xyz.wadewhy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.wadewhy.after.sys.service.MenuService;
import xyz.wadewhy.after.sys.service.RoleService;

import java.util.List;

/**
 * @PACKAGE_NAME: xyz.wadewhy
 * @NAME: Test
 * @Author: 钟子豪
 * @DATE: 2020/7/2
 * @MONTH_NAME_FULL: 七月
 * @DAY: 02
 * @DAY_NAME_FULL: 星期四
 * @PROJECT_NAME: OnlineExam
 **/
public class Test {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        RoleService roleService = context.getBean(RoleService.class);
        List<Integer> list = roleService.queryRoleIdsByUserId(2);
        for (Integer i:list
        ) {
            System.err.println(i+"---------------------");
        }
    }
}
