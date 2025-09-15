package com.jeartech.Component;

import com.jeartech.App;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class JCFrame extends JFrame{
    public JCFrame(int width,int height, String title,App app) throws IOException {
        setSize(width,height);
        setTitle(title);
        setResizable(false);
        Image image = Toolkit.getDefaultToolkit().getImage("background.jpeg");
        setIconImage(image);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置点叉号关闭应用
    }
}
