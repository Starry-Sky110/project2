package salesSystem.sys.utils;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TreeNode {

    private Integer id;
    @JsonProperty(value = "parentId")
    private Integer pid; //节点关系 parentId
    private String title; //选项名
    private String icon; //图标
    private String href;
    private Boolean spread; //是否展开
    private String checkArr = "0"; //默认不选中

    //children
    private List<TreeNode> children = new ArrayList<>();

    public TreeNode(Integer id, Integer pid, String title, Boolean spread) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
    }

    public TreeNode(Integer id, Integer pid, String title, Boolean spread, String checkArr) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
        this.checkArr = checkArr;
    }

    public TreeNode(Integer id, Integer pid, String title, String icon, String href, Boolean spread) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.icon = icon;
        this.href = href;
        this.spread = spread;
    }
}
