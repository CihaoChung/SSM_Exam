package xyz.wadewhy.after.sys.common;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.common
 * @NAME: TestClass
 * @Author: 钟子豪
 * @DATE: 2020/3/20
 * @MONTH_NAME_FULL: 三月
 * @DAY: 20
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 **/
public class TestClass {
    public static void main(String[] args) {
        int num =65;
        num++;
        String s = Integer.toString(num);
        //ascll码
        System.out.print((char)Integer.parseInt(s));
        String v= String.valueOf((char)Integer.parseInt(s));
        System.err.println(v);
    }

}
