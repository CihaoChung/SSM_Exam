package xyz.wadewhy.after.sys.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.wadewhy.after.sys.common.Page;
import xyz.wadewhy.after.sys.common.ResultObj;
import xyz.wadewhy.after.sys.domain.Permission;
import xyz.wadewhy.after.sys.domain.Role;
import xyz.wadewhy.after.sys.domain.Role_Permission;
import xyz.wadewhy.after.sys.mapper.MenuMapper;
import xyz.wadewhy.after.sys.service.MenuService;
import xyz.wadewhy.after.sys.service.PermissionService;
import xyz.wadewhy.after.sys.service.RoleService;
import xyz.wadewhy.after.sys.service.UserService;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.controller
 * @NAME: RoleController
 * @Author: 钟子豪
 * @DATE: 2020/3/15
 * @MONTH_NAME_FULL: 三月
 * @DAY: 15
 * @DAY_NAME_FULL: 星期日
 * @PROJECT_NAME: OnlineExam
 **/

@RequestMapping("after/role")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;

    /**
     * 获取角色列表
     *
     * @param page
     * @param name
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> getList(Page page,
                                       @RequestParam(name = "name", required = false, defaultValue = "") String name
    ) {
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("name", name);
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", roleService.findList(queryMap));
        ret.put("total", roleService.getTotal(queryMap));
        return ret;
    }

    /**
     * 获取某个角色的所有权限
     *
     * @param role
     * @return
     */
    @RequestMapping("get_role_permission")
    public List<Role_Permission> getRolePermission(Role role) {
//        System.err.println(role.getId() + "【】");
        List<Role_Permission> role_permissions = permissionService.queryRole_Permissions(role.getId());
        return role_permissions;
    }

    /**
     * 获取所有的菜单信息
     *
     * @return
     */
    @RequestMapping(value = "get_all_menu", method = RequestMethod.POST)
    public List<Permission> getAllMenu() {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("offset", 0);
        queryMap.put("pageSize", 99999);
        return menuService.findList(queryMap);
    }

    /**
     * 添加权限
     *
     * @param ids
     * @param rid
     * @return
     */
    @RequestMapping(value = "add_role_permission", method = RequestMethod.POST)
    public Map<String, String> addRole_Permission(
            @RequestParam(name = "ids", required = true) String ids,
            @RequestParam(name = "rid", required = true) Integer rid
    ) {
//        System.err.println(ids + "【】" + rid);
        Map<String, String> ret = new HashMap<String, String>();
        if (StringUtils.isEmpty(ids)) {
            ret.put("type", "error");
            ret.put("msg", "请选择相应的权限！");
            return ret;
        }
        if (rid == null) {
            ret.put("type", "error");
            ret.put("msg", "请选择相应的角色！");
            return ret;
        }
        if (ids.contains(",")) {
            ids = ids.substring(0, ids.length() - 1);
        }
        String[] idArr = ids.split(",");
        if (idArr.length > 0) {
            permissionService.deleteByRoleId(rid);
        }
        for (String id : idArr) {
            Role_Permission authority = new Role_Permission();
            authority.setPid(Integer.valueOf(id));
            authority.setRid(rid);
//            System.err.println(authority.toString() + "【【【】");
            permissionService.add(authority);
        }
        ret.put("type", "success");
        ret.put("msg", "权限编辑成功！");
        return ret;
    }

    /**
     * 添加
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResultObj add(Role role) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        role.setCreatetime(new Date());
        if (role == null) {
            return new ResultObj(-2, "请填写正确的角色信息！");
        }
        if (StringUtils.isEmpty(role.getName())) {
            return new ResultObj(-2, "请填写角色名称！");
        }
        if (roleService.add(role) <= 0) {
            return new ResultObj(-1, "角色添加失败，请联系管理员！");
        }
        return ResultObj.ADD_SUCCESS;
    }

    /**
     * 修改
     *
     * @param role
     * @return
     */
    @RequestMapping("edit")
    public ResultObj edit(Role role) {
        if (role == null) {
            return new ResultObj(-2, "请填写正确的角色信息！");
        }
        if (StringUtils.isEmpty(role.getName())) {
            return new ResultObj(-2, "请填写角色名称！");
        }
        if (roleService.edit(role) <= 0) {
            return new ResultObj(-2, "角色修改失败，请联系管理员！");

        }
        return ResultObj.UPDATE_SUCCESS;
    }

    @RequestMapping("delete")
    public ResultObj delete(Integer id) {
        if (id == null) {
            return new ResultObj(-2, "请选择要删除的角色！");
        }
        try {
            //在删除之前删除拥有该角色用户的记录以及角色权限记录
            permissionService.deleteByRoleId(id);
            userService.deleteRoleUserByRid(id);
            if (roleService.deleteById(id) <= 0) {
                return new ResultObj(-1, "删除失败，请联系管理员！");
            }
        } catch (Exception e) {
            // TODO: handle exception
            return new ResultObj(-2, "未知错误！");
        }
        return ResultObj.DELETE_SUCCESS;
    }

    /**
     * 根据用户id查询拥有的角色
     * @param uid
     * @return
     */
    @RequestMapping("findRoleByUid")
    public Map<String, Object> findRoleByUid(@RequestParam(name = "uid") Integer uid) {
        Map<String, Object> ret = new HashMap<String, Object>();
        List<Integer> ridlist = roleService.queryRoleIdsByUserId(uid);
        List<Role> rolesList = roleService.findRoleByUid(ridlist);

        ret.put("roles", rolesList);
        ret.put("code", 200);
        return ret;
    }
    @RequestMapping("findAllRole")
    public Map<String, Object> findAllRole() {
        Map<String, Object> ret = new HashMap<String, Object>();
      List<Role> rolesList = roleService.findAllRole();
        ret.put("rows", rolesList);
        return ret;
    }
}
