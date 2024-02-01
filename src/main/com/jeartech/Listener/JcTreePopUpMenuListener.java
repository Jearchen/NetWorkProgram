package com.jeartech.Listener;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class JcTreePopUpMenuListener extends MouseAdapter {
    private JPopupMenu popupMenu;

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton()==3){
            popupMenu.show(e.getComponent(),e.getX(),e.getY());
        }
    }

    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    public void setPopupMenu(JPopupMenu popupMenu) {
        this.popupMenu = popupMenu;
    }
}
