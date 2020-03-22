package xyz.wadewhy.after.sys.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.domain
 * @NAME: Role_Permission
 * @Author: 钟子豪
 * @DATE: 2020/3/15
 * @MONTH_NAME_FULL: 三月
 * @DAY: 15
 * @DAY_NAME_FULL: 星期日
 * @PROJECT_NAME: OnlineExam
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role_Permission {
    private Integer rid;
    private Integer pid;
}
