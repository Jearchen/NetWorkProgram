package com.jeartech.Component;

import com.jeartech.App;
import com.jeartech.util.ServerUtil;
import com.jeartech.Listener.JCMultiListener;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

public class JCFrame extends JFrame{
    private JCFileDialog jcFileDialog;

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public JCFrame(int width,int height, String title,App app) throws IOException {
        setSize(width,height);
        setTitle(title);
        setResizable(false);
        Image image = Toolkit.getDefaultToolkit().getImage("background.jpeg");
        setIconImage(image);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置点叉号关闭应用
        // registerFileDialog(jcFileDialog);
    }

    // public void registerFileDialog(JCFileDialog fileDialog){
    //     fileDialog.addPropertyChangeListener();
    // }

    // public void registerTextField(JCFileDialog fileDialog,JCTextField jcTextField){
    //     fileDialog.addPropertyChangeListener(new TextFieldListenDialogFile(jcTextField.getjTextField()));
    // }

    // public void logServerSession(ServerUtil server,JTextArea jTextArea){
    //     server.addPropertyChangeListener(new TextAreaListenConnection(jTextArea));
    // }

    // public void subscribeTaskMessage(ServerUtil server,JTextArea jTextArea){
    //     server.addPropertyChangeListener(new TextAreaListenConnection(jTextArea));
    // }

    // public void logChooseFile(JCFileDialog fileDialog,JTextArea jTextArea){
    //     fileDialog.addPropertyChangeListener(new TextAreaListenChooseFile(jTextArea));
    // }

    // public void setVisible(boolean flag){
    //     setVisible(flag);
    // }
}
