package com.jeartech.Component;

import com.jeartech.Listener.FrameListenDialogFile;
import com.jeartech.Listener.TextAreaListenChooseFile;
import com.jeartech.Listener.TextAreaListenConnection;
import com.jeartech.Listener.TextFieldListenDialogFile;
import com.jeartech.util.ServerUtil;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

public class JCFrame {
    private JFrame jFrame = new JFrame();

    private JcFileDialog jcFileDialog;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public JCFrame(int width,int height, String title) throws IOException {
        jFrame.setSize(width,height);
        jFrame.setTitle(title);
        jFrame.setResizable(false);
        Image image = Toolkit.getDefaultToolkit().getImage("background.jpeg");
        jFrame.setIconImage(image);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置点叉号关闭应用
    }
    public void registerFileDialog(JcFileDialog fileDialog){
        fileDialog.addPropertyChangeListener(new FrameListenDialogFile());
    }
    public void registerTextField(JcFileDialog fileDialog,JcTextField jcTextField){
        fileDialog.addPropertyChangeListener(new TextFieldListenDialogFile(jcTextField.getjTextField()));
    }
    public void logServerSession(ServerUtil server,JTextArea jTextArea){
        server.addPropertyChangeListener(new TextAreaListenConnection(jTextArea));
    }

    public void subscribeTaskMessage(ServerUtil server,JTextArea jTextArea){
        server.addPropertyChangeListener(new TextAreaListenConnection(jTextArea));
    }
    public void logChooseFile(JcFileDialog fileDialog,JTextArea jTextArea){
        fileDialog.addPropertyChangeListener(new TextAreaListenChooseFile(jTextArea));
    }



    public void setVisible(boolean flag){
        jFrame.setVisible(flag);
    }

    public JFrame getRawFrame(){
        return jFrame;
    }

}
