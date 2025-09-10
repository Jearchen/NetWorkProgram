package com.jeartech.Component;

import com.jeartech.Listener.JCMultiListener;
import com.jeartech.Listener.JcTreeListenConnect;
import com.jeartech.util.ServerUtil;

import java.awt.Container;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;


public class JCTree extends JTree {
    // private JcTreePopUpMenuListener popupListener = new JcTreePopUpMenuListener();
    
    public JCTree(int width, int height){
        setEditable(true);
        setVisible(true);
        setSize(width,height);
        // addPropertyChangeListener(new JcTreeListenConnect(this));
        // DefaultMutableTreeNode newNode = new DefaultMutableTreeNode();
        // newNode.setUserObject("session connect");
        
        // jTree= new JTree(newNode);
        // // jTree.addMouseListener(popupListener);
        // parent.add(jTree);
        // addPropertyChangeListener(new JCMultiListener());
    }

    // public void setPopupMenu(JPopupMenu menu){
    //     popupListener.setPopupMenu(menu);
    // }

    // public JTree getRawTree(){
    //     return jTree;
    // }

    // public void registerConnection(ServerUtil server){
    //     server.addPropertyChangeListener(new JcTreeListenConnect(this));
    // }
    // public void removeConnection(ServerUtil server){
    //     server.removePropertyChangerListener(new JcTreeListenConnect(this));
    // }
}
