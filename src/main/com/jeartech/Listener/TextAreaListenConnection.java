package com.jeartech.Listener;

import com.jeartech.util.ConnectionEventType;
import com.jeartech.util.DateUtil;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TextAreaListenConnection implements PropertyChangeListener {
    private JTextArea jTextArea;
    public TextAreaListenConnection(JTextArea textArea) {
        jTextArea = textArea;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().contains("logServer")){
            String newValue = (String)evt.getNewValue();
            String[] seperateParts = evt.getPropertyName().split("-");
            if(newValue.equals(ConnectionEventType.newConnection.getCode())){
                jTextArea.append(DateUtil.getNowDate()+":"+"新建连接，IP"+seperateParts[1]+"\n");
            }
            if(newValue.equals(ConnectionEventType.closeConnection.getCode())){
                jTextArea.append(DateUtil.getNowDate()+":"+"断开连接，IP"+seperateParts[1]+"\n");
            }
        }
        if(evt.getPropertyName().contains("receiveBuffer")){
            String[] seperateParts = evt.getPropertyName().split("-");
            String receiveData = (String)evt.getNewValue();
            jTextArea.append(DateUtil.getNowDate()+":"+"来自IP："+seperateParts[1]+" 数据："+receiveData+"\n");
        }
    }
}
