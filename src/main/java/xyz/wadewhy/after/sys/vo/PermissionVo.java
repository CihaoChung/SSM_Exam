package xyz.wadewhy.after.sys.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.wadewhy.after.sys.domain.Permission;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.vo
 * @NAME: PermissionVo
 * @Author: 钟子豪
 * @DATE: 2020/3/14
 * @MONTH_NAME_FULL: 三月
 * @DAY: 14
 * @DAY_NAME_FULL: 星期六
 * @PROJECT_NAME: OnlineExam
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionVo extends Permission {
    /**
     * 分页参数
     */
    private Integer page;
    private Integer limit;
}
