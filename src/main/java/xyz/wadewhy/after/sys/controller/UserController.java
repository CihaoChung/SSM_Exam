package xyz.wadewhy.after.sys.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.wadewhy.after.sys.common.Page;
import xyz.wadewhy.after.sys.common.ResultObj;
import xyz.wadewhy.after.sys.domain.User;
import xyz.wadewhy.after.sys.service.RoleService;
import xyz.wadewhy.after.sys.service.UserService;
import xyz.wadewhy.after.sys.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.controller
 * @NAME: UserController
 * @Author: 钟子豪
 * @DATE: 2020/3/13
 * @MONTH_NAME_FULL: 三月
 * @DAY: 13
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 **/
@RestController
@RequestMapping("/after/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * 显示和模糊查询
     *
     * @param page
     * @param name
     * @param roleId
     * @param sex
     * @return
     */
    @RequestMapping("list")
    public Map<String, Object> getList(Page page, @RequestParam(name = "name", required = false, defaultValue = "") String name,
                                       @RequestParam(name = "roleId", required = false) Integer roleId,
                                       @RequestParam(name = "sex", required = false) Integer sex) {
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        if (name != null) {
            queryMap.put("name", name);
            queryMap.put("sex", sex);
        }
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        List<User> all = new ArrayList<>();
        if (roleId != null) {//如果模糊查询拥有该角色的用户
            List<Integer> uids = userService.queryUidByRid(roleId);//先根据角色id查询用户id
            for (Integer uid : uids
            ) {
                queryMap.put("id", uid);
                List<User> userList = userService.findList(queryMap);
                all.addAll(userList);
            }
        } else {
            all = userService.findList(queryMap);
        }
        ret.put("rows", all);
        ret.put("total", all);
        return ret;
    }

    /**
     * 分配角色
     *
     * @param uid
     * @param checkRole
     * @return
     */
    @RequestMapping("add_role_user")
    public ResultObj add_role_user(@RequestParam(name = "uid") Integer uid, @RequestBody List<Integer> checkRole) {
        try {
            //先清空
            roleService.delete_role_user(uid);
            for (Integer rid : checkRole
            ) {
                roleService.add_role_user(rid, uid);
            }
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {
            return new ResultObj(-2, "未知错误");
        }

    }

    /**
     * 上传图片
     *
     * @param photo
     * @param request
     * @return
     */
    @RequestMapping(value = "upload_photo", method = RequestMethod.POST)
    public Map<String, String> upload_photo(
            MultipartFile photo, HttpServletRequest request
    ) {
        Map<String, String> ret = new HashMap<String, String>();
//        System.err.println("pthoto" + photo);
        if (photo == null) {
            ret.put("type", "error");
            ret.put("msg", "选择要上传的文件！");
            return ret;
        }
        if (photo.getSize() > 1024 * 1024 * 1024) {
            ret.put("type", "error");
            ret.put("msg", "文件大小不能超过10M！");
            return ret;
        }
        //获取文件后缀
        String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1, photo.getOriginalFilename().length());
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            ret.put("type", "error");
            ret.put("msg", "请选择jpg,jpeg,gif,png格式的图片！");
            return ret;
        }
        String savePath = request.getServletContext().getRealPath("/") + "/resources/upload/";
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //若不存在改目录，则创建目录
            savePathFile.mkdir();
        }
        String filename = new Date().getTime() + "." + suffix;
        try {
            //将文件保存至指定目录
            photo.transferTo(new File(savePath + filename));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            ret.put("type", "error");
            ret.put("msg", "保存文件异常！");
            e.printStackTrace();
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "图片上传成功！");
        ret.put("filepath", request.getServletContext().getContextPath() + "/resources/upload/" + filename);
        return ret;
    }

    /**
     * 添加用户
     *
     * @return
     */
    @RequestMapping("add")
    public ResultObj add(User user) {
        user.setHiredate(new Date());
        //加密
        String salt = IdUtil.simpleUUID().toUpperCase();
        user.setSalt(salt);
        user.setPwd(new Md5Hash(user.getPwd(), salt, 2).toString());
        if (user == null) {
            return new ResultObj(-2, "请填写正确的用户信息！");
        }
        if (StringUtils.isEmpty(user.getName())) {
            return new ResultObj(-2, "请填写用户名！");

        }
        if (StringUtils.isEmpty(user.getPwd())) {
            return new ResultObj(-2, "请填写密码！");

        }

        if (isExist(user.getName(), 0)) {
            return new ResultObj(-2, "该用户名已经存在，请重新输入!");
        }
        if (userService.add(user) <= 0) {
            return new ResultObj(-2, "用户添加失败，请联系管理员！");
        }
        return ResultObj.ADD_SUCCESS;
    }

    /**
     * 判断该用户名是否存在
     *
     * @param
     * @param id
     * @return
     */
    private boolean isExist(String name, Integer id) {
        User user = userService.findByUsername(name);
        if (user == null) return false;
        if (user.getId() == id) return false;
        return true;
    }

    /**
     * 编辑用户
     * @param user
     * @return
     */
    @RequestMapping("edit")
    public ResultObj edit(User user) {
        if(user == null){
            return new ResultObj(-2,"请填写正确的用户信息！");
        }
        if(StringUtils.isEmpty(user.getName())){
            return new ResultObj(-2,"请填写用户名！");
        }


      /*  if(isExist(user.getName(), user.getId())){
            return new ResultObj(-2,"该用户名已经存在，请重新输入！");
        }*/
        if(userService.edit(user) <= 0){
            return new ResultObj(-1,"用户添加失败，请联系管理员！");
        }
        return ResultObj.UPDATE_SUCCESS;
    }

    /**
     * 删除用户
     * @return
     */
    @RequestMapping("delete")
    public ResultObj delete(String ids){
        try{
            if(StringUtils.isEmpty(ids)){
                return new ResultObj(-2,"选择要删除的数据！");
            }
            if(ids.contains(",")){
                ids = ids.substring(0,ids.length()-1);
            }
            //删除role_user的记录
            userService.deleteRoleUserByUid(ids);
            if(userService.deleteUserByids(ids) <= 0){
                return new ResultObj(-2,"用户删除失败，请联系管理员！");
            }
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return new ResultObj(-2,"未知错误");
        }

    }
}
