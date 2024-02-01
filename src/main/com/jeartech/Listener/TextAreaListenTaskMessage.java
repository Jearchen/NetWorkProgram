package com.jeartech.Listener;

import com.jeartech.util.DateUtil;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TextAreaListenTaskMessage implements PropertyChangeListener {
    private JTextArea textArea;
    public TextAreaListenTaskMessage(JTextArea jTextArea){
        textArea = jTextArea;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("taskMessage")){
            String errorMessage = (String)evt.getNewValue();
            textArea.append(DateUtil.getNowDate()+":"+errorMessage+"\n");
        }
    }
}
