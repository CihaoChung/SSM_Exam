package xyz.wadewhy.after.sys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.common
 * @NAME: ResultObj
 * @Author: 钟子豪
 * @DATE: 2020/3/13
 * @MONTH_NAME_FULL: 三月
 * @DAY: 13
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: OnlineExam
 * 数据放回类型封装类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj {
    //状态码
    private Integer code;
    //消息
    private String msg;
    public static final ResultObj MENU_WRITER_ERROR =
            new ResultObj(Constant.ERROR, "请填写正确的菜单信息!");
    public static final ResultObj LOGIN_SUCCESS =
            new ResultObj(Constant.ok, "登陆成功");
    public static final ResultObj LOGIN_ERROR_PASS =
            new ResultObj(Constant.ERROR, "登陆失败,用户名或密码不正确");
    public static final ResultObj LOGIN_ERROR_CODE =
            new ResultObj(Constant.ERROR, "登陆失败,验证码不正确");
    public static final ResultObj DELETE_SUCCESS =
            new ResultObj(Constant.ok, "删除成功");
    public static final ResultObj DELETE_ERROR =
            new ResultObj(Constant.ERROR, "删除失败");

    public static final ResultObj ADD_SUCCESS =
            new ResultObj(Constant.ok, "添加成功");
    public static final ResultObj ADD_ERROR =
            new ResultObj(Constant.ERROR, "添加失败");
    public static final ResultObj RESET_SUCCESS =
            new ResultObj(Constant.ok, "重置成功");
    public static final ResultObj RESET_ERROR =
            new ResultObj(Constant.ERROR, "重置失败");
    public static final ResultObj DISPATCH_SUCCESS =
            new ResultObj(Constant.ok, "分配成功");
    public static final ResultObj DISPATCH_ERROR =
            new ResultObj(Constant.ERROR, "分配失败");

    public static final ResultObj UPDATE_SUCCESS =
            new ResultObj(Constant.ok, "修改成功");
    public static final ResultObj UPDATE_ERROR =
            new ResultObj(Constant.ERROR, "修改失败");

    public static final ResultObj OPERATE_SUCCESS = new ResultObj(Constant.ok, "操作成功");
    public static final ResultObj OPERATE_ERROR = new ResultObj(Constant.ERROR, "操作失败");
    public static final ResultObj BACKINPORT_SUCCESS = new ResultObj(Constant.ok, "退货成功");
    public static final ResultObj BACKINPORT_ERROR = new ResultObj(Constant.ERROR, "退货失败");
    public static  final ResultObj UpdatePwd_SUCCESS = new ResultObj(Constant.ok,"修改密码成功");
    public static final  ResultObj UpdatePwd_ERROR = new ResultObj(Constant.ERROR,"原密码错误失败");
    public static final  ResultObj UpdatePwd_UNKNOW = new ResultObj(Constant.UNKNOWERROR,"新密码不能和旧密码相同");
}
