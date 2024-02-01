package com.jeartech.Listener;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TextFieldListenDialogFile implements PropertyChangeListener {

    private JTextField textField;
    public TextFieldListenDialogFile(JTextField field){
        textField = field;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("fileDialog1")){
            String newValue = (String) evt.getNewValue();
            textField.setText(newValue);
        }
    }
}
