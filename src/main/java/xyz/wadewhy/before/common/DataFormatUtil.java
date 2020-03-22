package xyz.wadewhy.before.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @PACKAGE_NAME: xyz.wadewhy.before.common
 * @NAME: DataFormatUtil
 * @Author: 钟子豪
 * @DATE: 2020/3/20
 * @MONTH_NAME_FULL: 三月
 * @DAY: 20
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 **/
public class DataFormatUtil {
    /**
     * 返回指定日志指定格式的日期字符串
     * @param pattern
     * @param date
     * @return
     */
    public static String getDate(String pattern, Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}
