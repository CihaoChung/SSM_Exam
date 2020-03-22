package xyz.wadewhy.after.sys.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @PACKAGE_NAME: xyz.wadewhy.after.sys.common
 * @NAME: TreeNode
 * @Author: 钟子豪
 * @DATE: 2020/3/14
 * @MONTH_NAME_FULL: 三月
 * @DAY: 14
 * @DAY_NAME_FULL: 星期六
 * @PROJECT_NAME: OnlineExam
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {
    private Integer id;
    //dtree需要这个json参数，而在左边导航树没用到，这可以这样转换
    @JsonProperty("parentId")
    private Integer pid;
    private String title;
    private String icon;
    private String href;
    //dtree的参数
    private Boolean spread;
    //子节点也是一个TreeNode类型的数据格式
    private List<TreeNode> children = new ArrayList<>();
    //表示是否选中
    private String checkArr="0";//0不选中，1选中

    /**
     * 首页左边导航树的构造器
     *
     * @param id
     * @param pid
     * @param title
     * @param icon
     * @param href
     * @param spread
     */
    public TreeNode(Integer id, Integer pid, String title, String icon, String href, Boolean spread) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.icon = icon;
        this.href = href;
        this.spread = spread;
    }

    /**
     * dtree的数据格式
     *
     * @param id
     * @param pid
     * @param title
     * @param spread
     */
    public TreeNode(Integer id, Integer pid, String title, Boolean spread) {
        super();
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
    }


    /**
     * dTree复选树的构造器
     *
     * @param id
     * @param pid
     * @param title
     * @param spread
     * @param checkArr
     */
    public TreeNode(Integer id, Integer pid, String title, Boolean spread, String checkArr) {
        super();
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
        this.checkArr = checkArr;
    }

}
