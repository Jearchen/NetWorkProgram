package com.jeartech.Component;

import javax.swing.*;

public class JcTextField {
    private JTextField jTextField;

    public JcTextField(int width,int height,int columnSize){
        jTextField = new JTextField();
        jTextField.setSize(width,height);
        jTextField.setColumns(columnSize);
    }

    public JTextField getjTextField(){
        return jTextField;
    }
}
