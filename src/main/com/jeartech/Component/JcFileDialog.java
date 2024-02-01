package com.jeartech.Component;


import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;

public class JcFileDialog{
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private FileDialog fileDialog;

    private String oldValue;
    private String newValue;
    public JcFileDialog(){

    }
    public JcFileDialog(int width,int height,JFrame parent,String title){
        fileDialog = new FileDialog(parent,title);
        fileDialog.setSize(width,height);
        fileDialog.setName("fileDialog1");
    }

    public void showFileDialog(boolean showFlag){
        fileDialog.setVisible(showFlag);
    }


    public void setDefaultPath(){
        URL resource = JcFileDialog.class.getResource("");
        String resourceStr = resource.toString();
        String processedPath = resourceStr.replace("file:/","").toString();
        fileDialog.setDirectory(processedPath);
    }



    public String getFilePath(){
        if(fileDialog.getDirectory()==null||fileDialog.getFile()==null)
            return null;
        return fileDialog.getDirectory()+fileDialog.getFile();
    }

    public void propertyChangeSupport(String value){
        oldValue = null;
        newValue = value;
        propertyChangeSupport.firePropertyChange("fileDialog1",oldValue,newValue);
        propertyChangeSupport.firePropertyChange("logFileChose",oldValue,newValue);
    }


    void addPropertyChangeListener(PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    void removePropertyChangerListener(PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(listener);
    }


}
