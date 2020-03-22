package xyz.wadewhy.after.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wadewhy.after.sys.common.DataGridView;
import xyz.wadewhy.after.sys.service.PermissionService;
import xyz.wadewhy.after.sys.vo.PermissionVo;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.controller
 * @NAME: PermissionController
 * @Author: 钟子豪
 * @DATE: 2020/3/14
 * @MONTH_NAME_FULL: 三月
 * @DAY: 14
 * @DAY_NAME_FULL: 星期六
 * @PROJECT_NAME: OnlineExam
 * 菜单权限控制类
 **/
@RestController
@RequestMapping("/after/permission")
    public class PermissionController {
        @Autowired
        private PermissionService permissionService;

}
