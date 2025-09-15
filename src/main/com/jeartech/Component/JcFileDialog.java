package com.jeartech.Component;


import java.awt.*;
import java.net.URL;

public class JCFileDialog extends FileDialog{
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
