package ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeNode {
    private String myName;
    private Set<File> myFileChildren;
    private Set<TreeNode> myTreeNodeChildren;

    public TreeNode(String name){
        myName = name;
        myFileChildren = new HashSet<>();
        myTreeNodeChildren = new HashSet<>();
    }

    public void addChild(TreeNode child){
        myTreeNodeChildren.add(child);
    }

    public void addChild(File child){
        myFileChildren.add(child);
    }


    /**
     * Goes to the indicated next child treenode, note if the child doesn't exist, it creates a new one and returns that
     * @param nextNodeName name of the next node
     * @return TreeNode of the name entered
     */
    public TreeNode next(String nextNodeName){
        for(TreeNode treeNode : myTreeNodeChildren){
            if (treeNode.getName().equals(nextNodeName)){
                return treeNode;
            }
        }
        TreeNode newTreeNode = new TreeNode(nextNodeName);
        this.addChild(newTreeNode);
        return newTreeNode;
    }


    public String getName(){
        return  myName;
    }

    public List<File> getFileChildren(){
        List<File> fileList = new ArrayList<>(myFileChildren);
        return Collections.unmodifiableList(fileList);
    }

    public List<TreeNode> getNodeChildren(){
        List<TreeNode> nodeList = new ArrayList<>(myTreeNodeChildren);
        return Collections.unmodifiableList(nodeList);
    }
}
