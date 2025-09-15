package com.jeartech.Component;

import java.awt.Font;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;


public class JCTree extends JTree {
    private DefaultTreeModel treeModel;
    public JCTree(){
        setFont(new Font("JetBrainsMono",Font.BOLD,16));
    }

    public void addSocketNode(DefaultMutableTreeNode treeNode){
        MutableTreeNode parent = (MutableTreeNode)treeNode.getParent();
        if(parent==null){
            treeModel = new DefaultTreeModel(treeNode);
        }else{
            treeModel.insertNodeInto(treeNode,parent,parent.getChildCount());
        }
        setModel(treeModel);
        updateUI();
    }

    public void removeSocketNode(DefaultMutableTreeNode treeNode){
        treeModel.removeNodeFromParent(treeNode);
        updateUI();
    }   
}
