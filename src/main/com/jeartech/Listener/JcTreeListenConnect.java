package com.jeartech.Listener;

import com.jeartech.Component.JcTree;
import com.jeartech.util.ServerUtil;
import org.apache.mina.core.session.IoSession;

import javax.swing.*;
import javax.swing.tree.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;


public class JcTreeListenConnect implements PropertyChangeListener {
    private JcTree jcTree;

    public JcTreeListenConnect(JcTree parent){
        jcTree =parent;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("connectChange")){
            try {
                JTree rawTree = jcTree.getRawTree();
                Map<String, IoSession> sessionMap = ServerUtil.getSessionMap();
                DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("session connect");
                for (String sessionItem : sessionMap.keySet()) {
                    if(sessionItem!=null){
                        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(sessionItem);
                        rootNode.add(newNode);
                    }else{
                        System.out.println("暂无会话");
                    }
                }
                DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
                rawTree.setModel(treeModel);
                rawTree.updateUI();
            } catch (Exception exp) {
                throw new RuntimeException(exp);
            }
        }
    }
}
