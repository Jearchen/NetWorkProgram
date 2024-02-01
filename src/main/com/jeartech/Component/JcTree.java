package com.jeartech.Component;

import com.jeartech.Listener.JcTreeListenConnect;
import com.jeartech.Listener.JcTreePopUpMenuListener;
import com.jeartech.util.ServerUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.Serializable;

public class JcTree implements Serializable {
    private JTree jTree = new JTree();
    private JcTreePopUpMenuListener popupListener = new JcTreePopUpMenuListener();
    public JcTree(int width, int height, String title, Container parent){
        jTree.setEditable(true);
        jTree.setVisible(true);
        jTree.setSize(width,height);
        jTree.addPropertyChangeListener(new JcTreeListenConnect(this));
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode();
        newNode.setUserObject("session connect");
        jTree= new JTree(newNode);
        jTree.addMouseListener(popupListener);
        parent.add(jTree);
    }

    public void setPopupMenu(JPopupMenu menu){
        popupListener.setPopupMenu(menu);
    }

    public JTree getRawTree(){
        return jTree;
    }

    public void registerConnection(ServerUtil server){
        server.addPropertyChangeListener(new JcTreeListenConnect(this));
    }
    public void removeConnection(ServerUtil server){
        server.removePropertyChangerListener(new JcTreeListenConnect(this));
    }
}
