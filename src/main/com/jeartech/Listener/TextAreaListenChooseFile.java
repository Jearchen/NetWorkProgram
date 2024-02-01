package com.jeartech.Listener;

import com.jeartech.util.DateUtil;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TextAreaListenChooseFile implements PropertyChangeListener {
    private JTextArea jTextArea;
    public TextAreaListenChooseFile(JTextArea area){
        jTextArea = area;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("logFileChose")){
            String filePath = (String)evt.getNewValue();
            String[] dirs = filePath.split("\\\\");
            int len = dirs.length;
            String fileName= dirs[len-1];
            if(jTextArea!=null){
                jTextArea.append(DateUtil.getNowDate()+":"+"选择文件"+fileName+" 目录："+filePath+"\n");
                jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
            }
        }
    }
}
