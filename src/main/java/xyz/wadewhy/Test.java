package xyz.wadewhy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.wadewhy.after.sys.service.MenuService;
import xyz.wadewhy.after.sys.service.RoleService;

import java.text.SimpleDateFormat;
import java.util.Date;
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
       /* ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        RoleService roleService = context.getBean(RoleService.class);
        List<Integer> list = roleService.queryRoleIdsByUserId(2);
        for (Integer i:list
        ) {
            System.err.println(i+"---------------------");
        }*/
            final String str = "1592528096000+0800";
            final String time = str.substring(0,str.length()-5);
            System.out.println(time);

            //final String timeZone = str.substring(str.length()-5, str.length());
            //System.out.println(timeZone);
            final Date date = new Date(Long.parseLong(time));
            final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(format.format(date));
    }
}
