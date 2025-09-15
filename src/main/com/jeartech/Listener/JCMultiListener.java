package com.jeartech.Listener;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.mina.core.session.IoSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeartech.App;
import com.jeartech.Component.JCFileDialog;
import com.jeartech.Component.JCTextField;
import com.jeartech.Component.JCTree;
import com.jeartech.util.ConnectionEventType;
import com.jeartech.util.DateUtil;
import com.jeartech.util.FileUtil;
import com.jeartech.util.LogUtil;



/*
 * Date: 2025/09/10
 * Author: Jearchen
 * use MultiListener instead of single 
 */
public class JCMultiListener implements PropertyChangeListener{
    private LogUtil logger = new LogUtil(JCMultiListener.class.getName());
    private ArrayList<byte[]> splitBytes;
    private JFrame mainFrame;
    public JCMultiListener(){
    }
    public JCMultiListener(JFrame jFrame){
        this.mainFrame = jFrame;
    }
    /*
     *  socketTriggerTree socket 接入
     * 
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "fileDialog1":
                handleFrameListenerEvent(evt.getNewValue());
                break;
            case "socketTriggerTree":
                handleSocketConnectEvent(evt.getNewValue());
                break;
            case "fileChoseLogger":
                fileChoseLogger(evt.getNewValue());
                break;
            case "serverLogger":
                serverLogger(evt.getNewValue(),evt.getPropertyName());
                break;
            case "receiveBuffer":
                receiveBuffer(evt.getNewValue(),evt.getPropertyName());
                break;
            case "taskMessage":
                taskMessage(evt.getNewValue());
                break;
            case "filePathChange":
                filePathChange(evt.getNewValue());
                break;
            default:
                break;
        }
    }

    private void filePathChange(Object path){
        String filePath = (String) path;
        JCTextField fileLocation = retriveTextField("fileLocation");
        fileLocation.setText(filePath);
    }

    private void taskMessage(Object errorMessage){
        JTextArea logArea = retriveTextArea("logArea");
        logArea.append(DateUtil.getNowDate()+":"+(String)errorMessage+"\n");
    }

    private void serverLogger(Object fp,String prop){
        String newValue = (String)fp;
        String[] seperateParts = prop.split("-");
        JTextArea logArea = retriveTextArea("logArea");
        if(newValue.equals(ConnectionEventType.newConnection.getCode())){
            logArea.append(DateUtil.getNowDate()+":"+"新建连接，IP"+seperateParts[1]+"\n");
        }
        if(newValue.equals(ConnectionEventType.closeConnection.getCode())){
            logArea.append(DateUtil.getNowDate()+":"+"断开连接，IP"+seperateParts[1]+"\n");
        }
    }

    private void receiveBuffer(Object fp,String prop){
        String[] seperateParts = prop.split("-");
        String receiveData = (String)fp;
        JTextArea logArea = retriveTextArea("logArea");
        logArea.append(DateUtil.getNowDate()+":"+"来自IP："+seperateParts[1]+" 数据："+receiveData+"\n");
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

    private void handleSocketConnectEvent(Object newValue){
        String ipAddress = (String)newValue;
        JCTree sockTree = retriveTree("socketTree");
        sockTree.addSocketNode(new DefaultMutableTreeNode(ipAddress));
    }

    private void fileChoseLogger(Object newValue){
        String filePath = (String)newValue;
        String[] dirs = filePath.split("\\\\");
        int len = dirs.length;
        String fileName= dirs[len-1];
        JTextArea area = retriveTextArea("logArea");
        if(area==null){
            logger.info("can't find logarea!!!");
            return;
        }
        area.append(DateUtil.getNowDate()+":"+"选择文件"+fileName+" 目录："+filePath+"\n");
        area.setCaretPosition(area.getDocument().getLength()); 
    }

    private JTextArea retriveTextArea(String areaName){
        Component[] components = SwingUtilities.getRootPane(this.mainFrame).getComponents();
        for (Component itemComponent : components) {
            if(itemComponent.getName().equals(areaName)){
                return (JTextArea)itemComponent;
            }
        }
        return null;
    }

    private JCTree retriveTree(String treeName){
        Component[] components = SwingUtilities.getRootPane(this.mainFrame).getComponents();
        for (Component itemComponent : components) {
            if(itemComponent.getName().equals(treeName)){
                return (JCTree)itemComponent;
            }
        }
        return null;
    }


    private JCTextField retriveTextField(String fileLocation){
        Component[] components = SwingUtilities.getRootPane(this.mainFrame).getComponents();
        for (Component itemComponent : components) {
            if(itemComponent.getName().equals(fileLocation)){
                return (JCTextField)itemComponent;
            }
        }
        return null;
    }
}