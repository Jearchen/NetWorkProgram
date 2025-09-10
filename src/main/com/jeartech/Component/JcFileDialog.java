package com.jeartech.Component;


import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.net.URL;

public class JCFileDialog extends FileDialog{
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private String oldValue;
    private String newValue;

    public JCFileDialog(Frame parent){
        super(parent);
    }

    public void setDefaultPath(){
        URL resource = JCFileDialog.class.getResource("");
        String resourceStr = resource.toString();
        String processedPath = resourceStr.replace("file:/","").toString();
        setDirectory(processedPath);
    }

    public String getFilePath(){
        if(getDirectory()==null||getFile()==null)
            return null;
        return getDirectory()+getFile();
    }
    
}
