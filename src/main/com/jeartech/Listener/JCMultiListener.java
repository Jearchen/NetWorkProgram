package com.jeartech.Listener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JTextArea;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.mina.core.session.IoSession;

import com.jeartech.App;
import com.jeartech.Component.JCTree;
import com.jeartech.util.ConnectionEventType;
import com.jeartech.util.DateUtil;
import com.jeartech.util.FileUtil;
import com.jeartech.util.ServerUtil;


/*
 * Date: 2025/09/10
 * Author: Jearchen
 * use MultiListener instead of single 
 */
public class JCMultiListener implements PropertyChangeListener{
    private App application = new App();
    private JCTree tree;
    private JTextArea jTextArea;
    private JTextArea textArea;
    private ArrayList<byte[]> splitBytes;

    public JCMultiListener(App app){
        this.application = app;
    }

    public JCMultiListener(JCTree jctree){
        this.tree = jctree;
    }

    public JCMultiListener(JTextArea area){
        this.jTextArea = area;
    }

    /*
     *  
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "fileDialog1":
                handleFrameListenerEvent(evt.getNewValue());
                break;
            case "connectChange":
                handleSocketConnectEvent();
                break;
            case "logFileChose":
                logFileChose(evt.getNewValue());
                break;
            case "logServer":
                logServer(evt.getNewValue(),evt.getPropertyName());
                break;
            case "receiveBuffer":
                receiveBuffer(evt.getNewValue(),evt.getPropertyName());
                break;
            case "taskMessage":
                taskMessage(evt.getNewValue());
                break;
            case "filedDialog":
                filedDialog(evt.getNewValue());
            default:
                break;
        }
    }

    private void filedDialog(Object fileDialog){
        
    }

    private void taskMessage(Object errorMessage){
        textArea.append(DateUtil.getNowDate()+":"+(String)errorMessage+"\n");
    }

    private void logServer(Object fp,String prop){
        String newValue = (String)fp;
        String[] seperateParts = prop.split("-");
        if(newValue.equals(ConnectionEventType.newConnection.getCode())){
            jTextArea.append(DateUtil.getNowDate()+":"+"新建连接，IP"+seperateParts[1]+"\n");
        }
        if(newValue.equals(ConnectionEventType.closeConnection.getCode())){
            jTextArea.append(DateUtil.getNowDate()+":"+"断开连接，IP"+seperateParts[1]+"\n");
        }
    }

    private void receiveBuffer(Object fp,String prop){
        String[] seperateParts = prop.split("-");
        String receiveData = (String)fp;
        jTextArea.append(DateUtil.getNowDate()+":"+"来自IP："+seperateParts[1]+" 数据："+receiveData+"\n");
    }

    private void handleFrameListenerEvent(Object fp){
                if(fp ==null) return;
                String filePath = (String) fp;
                File resourceFile = new File(filePath);
                FileUtil.processFile(resourceFile);
                // splitBytes = FileUtil.getSplitBytes(512);
                // application.setSplitArrs(splitBytes);
                // application.setFileName(resourceFile.getName());
                // application.setFileSize((int)resourceFile.length());
    }

    private void handleSocketConnectEvent(){
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
        tree.setModel(treeModel);
        tree.updateUI();
    }

    private void logFileChose(Object newValue){
        String filePath = (String)newValue;
        String[] dirs = filePath.split("\\\\");
        int len = dirs.length;
        String fileName= dirs[len-1];
        if(jTextArea!=null){
            jTextArea.append(DateUtil.getNowDate()+":"+"选择文件"+fileName+" 目录："+filePath+"\n");
            jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
        }
    }
    
}