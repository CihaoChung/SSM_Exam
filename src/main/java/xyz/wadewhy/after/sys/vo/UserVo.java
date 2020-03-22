package xyz.wadewhy.after.sys.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.wadewhy.after.sys.domain.User;

import java.util.List;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.vo
 * @NAME: UserVo
 * @Author: 钟子豪
 * @DATE: 2020/3/13
 * @MONTH_NAME_FULL: 三月
 * @DAY: 13
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 * User拓展类
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo extends User {
    private List<Integer> roleId;
    private static final long serialVersionUID = 1L;
    private Integer page = 1;
    private Integer limit = 10;
}
