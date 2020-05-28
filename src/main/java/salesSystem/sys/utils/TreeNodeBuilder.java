package salesSystem.sys.utils;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeBuilder {

    public static List<TreeNode> build(List<TreeNode> treeNodes, int topId) {

        ArrayList<TreeNode> nodes = new ArrayList<>();

        for (TreeNode n1 : treeNodes) {
            if (n1.getPid() < topId) {
                continue; //跳过根节点
            }
            if (n1.getPid() == topId) {
                nodes.add(n1); // 把根节点的孩子放入list
            }
            for (TreeNode n2 : treeNodes) {
                if (n2.getPid() == n1.getId()) {
                    n1.getChildren().add(n2);
                }
            }
        }
        return nodes;
    }
}
