package xyz.wadewhy.after.sys.common;

/**
 * 常量
 */
public interface Constant {
    /**
     * 登录状态码
     */
    Integer ok = 200;
    Integer ERROR = -1;
    Integer UNKNOWERROR = -2;
    /**
     * 菜单权限类型
     */
    String TYPE_MENU = "menu";
    String TYPE_PERMISSION = "permission";
    /**
     * 可用状态
     */
    Object AVAILABLE_TRUE = 1;
    Object AVAILABLE_FALSE = 0;
    /**
     * 用户类型
     */
    Integer USER_TYPE_SUPER = 0;
    Integer USER_TYPE_NORMAL = 1;
    /**
     * 展开类型
     */
    Integer OPEN_TRUE = 1;
    Integer OPEN_FALSE = 0;

    /**
     * 用户默认密码
     */
    public static final String USER_DEFAULT_PWD="123456";

    /**
     * 默认图片
     */
    public static final String IMAGES_DEFAULTGOODSIMG_PNG = "images/2.jpg";
}
