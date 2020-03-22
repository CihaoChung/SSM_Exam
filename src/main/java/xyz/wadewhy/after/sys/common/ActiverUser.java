package xyz.wadewhy.after.sys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.wadewhy.after.sys.domain.User;

import java.util.List;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys
 * @NAME: ActiverUser
 * @Author: 钟子豪
 * @DATE: 2020/3/13
 * @MONTH_NAME_FULL: 三月
 * @DAY: 13
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 * 工具用户类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiverUser {
    private User user;
    private List<String> roles;
    private List<Integer> rolesId;
    private List<String> permissions;
}
