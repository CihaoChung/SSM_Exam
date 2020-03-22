package xyz.wadewhy.after.sys.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.wadewhy.after.sys.common.Constant;
import xyz.wadewhy.after.sys.common.Page;

import org.apache.commons.lang.StringUtils;
import xyz.wadewhy.after.sys.common.ResultObj;
import xyz.wadewhy.after.sys.domain.Permission;
import xyz.wadewhy.after.sys.service.MenuService;
import xyz.wadewhy.after.sys.service.PermissionService;
import xyz.wadewhy.after.sys.service.RoleService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.controller
 * @NAME: MenuController
 * @Author: 钟子豪
 * @DATE: 2020/3/14
 * @MONTH_NAME_FULL: 三月
 * @DAY: 14
 * @DAY_NAME_FULL: 星期六
 * @PROJECT_NAME: OnlineExam
 **/
@RestController
@RequestMapping("/after/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 获取菜单列表
     *
     * @param page
     * @param title
     * @return
     */
    @RequestMapping("list")
    public Map<String, Object> getMenuList(Page page,
                                           @RequestParam(name = "title", required = false, defaultValue = "") String title
    ) {
        System.err.println("【meun/list】"+title);
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        queryMap.put("title", title);
        List<Permission> findList = menuService.findList(queryMap);
        ret.put("rows", findList);
        ret.put("total", menuService.getTotal(queryMap));
        return ret;
    }

    /**
     * 菜单添加
     *
     * @param
     * @return
     */
    @RequestMapping("add")
    public ResultObj add(Permission permission) {
        if (permission.getType().equals("菜单")) {
            permission.setType("menu");
        } else if (permission.getType().equals("权限")) {
            permission.setType("permission");
        }
        if (permission == null) {
            return ResultObj.MENU_WRITER_ERROR;
        }
        if (StringUtils.isEmpty(permission.getTitle())) {
            return new ResultObj(-2, "请填写菜单名");
        }
        if (StringUtils.isEmpty(permission.getIcon())) {
            return new ResultObj(-2, "请填写菜单图标类!");
        }
        if (permission.getPid() == null) {
            permission.setPid(0);
        }
        if (menuService.add(permission) <= 0) {
            return new ResultObj(-2, "添加失败，请联系管理员!");
        }
        return ResultObj.ADD_SUCCESS;
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("edit")
    public ResultObj edit(Permission permission) {
        System.err.println("【】" + permission.toString());

        if (permission == null) {
            return ResultObj.MENU_WRITER_ERROR;
        }
        if (StringUtils.isEmpty(permission.getTitle())) {
            return new ResultObj(-2, "请填写菜单名");
        }
        if (StringUtils.isEmpty(permission.getIcon())) {
            return new ResultObj(-2, "请填写菜单图标类!");
        }
        if (permission.getPid() == null) {
            permission.setPid(0);
        }
        if (menuService.edit(permission) <= 0) {
            return new ResultObj(-2, "修改失败，请联系管理员!");
        }
        return ResultObj.UPDATE_SUCCESS;
    }

    /**
     * 删除菜单或权限
     *
     * @return
     */
    @RequestMapping("delete")
    public ResultObj delete(Permission permission) {
        //删除permission的记录和role_permission记录，存在子分类的不能删除
        try{
            if (permission.getId()!=null) {
                List<Permission> findChildernList= menuService.findChildernList(permission.getId());
                if(findChildernList != null && findChildernList.size() > 0){
                    //表示该分类下存在子分类，不能删除
                    return new ResultObj(-1,"该分类下存在子分类，不能删除!");
                }
                //删除rid_pid记录
//                List<Integer> num=permissionService.findRolePermissionByPid(permission.getId());
//                if (num.size()>0){
//                    roleService.delete(permission.getId());
//                }
                menuService.delete(permission.getId());
                return ResultObj.DELETE_SUCCESS;
            }
            return new ResultObj(Constant.UNKNOWERROR,"未知错误");
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    /**
     * 获取指定目录下的系统icon集合
     * @param request
     * @return
     */
    @RequestMapping(value="/get_icons",method=RequestMethod.POST)
    public Map<String, Object> getIconList(HttpServletRequest request){
        Map<String, Object> ret = new HashMap<String, Object>();
        String realPath = request.getServletContext().getRealPath("/");
        System.err.println(realPath + "\\resources\\admin\\easyui\\css\\icons");
        File file = new File(realPath + "\\resources\\admin\\easyui\\css\\icons");
        List<String> icons = new ArrayList<String>();
        if(!file.exists()){
            ret.put("type", "error");
            ret.put("msg", "文件目录不存在！");
            return ret;
        }
        File[] listFiles = file.listFiles();
        for(File f:listFiles){
            if(f!= null && f.getName().contains("png")){
                icons.add("icon-" + f.getName().substring(0, f.getName().indexOf(".")).replace("_", "-"));
            }
        }
        ret.put("type", "success");
        ret.put("content", icons);
        return ret;
    }
}
