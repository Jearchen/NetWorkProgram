package com.jeartech.Component;

import com.jeartech.Listener.TextAreaListenTaskMessage;
import com.jeartech.main;
import com.jeartech.util.ConnectionEventType;
import com.jeartech.util.ServerUtil;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import javax.swing.*;
import javax.swing.plaf.metal.MetalBorders;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class JcButtonComponent {
    private  JButton jButton = new JButton();
    private static ConcurrentHashMap<String,Object> threadLocals =new ConcurrentHashMap<>();
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private  int X;
    private  int Y;
    private static String filePath;
    private float processor;

    private static  List<byte[]> splitBytes;

    private static int fileSize;
    private static String fileName;
    public JcButtonComponent(int width, int height, String text, JPanel parent){
        X = width;
        Y = height;
        this.jButton.setBorder(MetalBorders.getButtonBorder());
        this.jButton.setSize(X,Y);
        this.jButton.setText(text);
        this.jButton.setVisible(true);
        this.jButton.setLocation(10,10);
        parent.add(this.jButton,0);
    }

    public  JButton createOneButton(){
        return jButton;
    }

    //按钮显示对话框
    public  void showFileDialog(JcFileDialog fileDialog){
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            if(e.getButton()==1){
                fileDialog.showFileDialog(true);
                filePath = fileDialog.getFilePath();
                System.out.println(filePath);
                if(filePath!=null)
                {
                    fileDialog.propertyChangeSupport(filePath);
                }
            }
            }
        });
    }

    //执行升级任务
    public void executeTask(){
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==1){
                    List<byte[]> splitBytes = main.getSplitArrs();
                    if(splitBytes==null||splitBytes.size()==0){
                        propertyChangeSupport.firePropertyChange("taskMessage",null,"请先选择需要升级的文件");
                        return;
                    }
                    Map<String, IoSession> sessionMap = ServerUtil.getSessionMap();
                    if(sessionMap.size()==0){
                        propertyChangeSupport.firePropertyChange("taskMessage",null,"暂无网关连接，请检查网络");
                        return;
                    }
                    for (String sessionItem : sessionMap.keySet()) {
                        if(sessionItem!=null){
                            try {
                                String  groupNum = ConnectionEventType.contains(sessionItem);
                                if(groupNum.equals("none"))
                                    return;
                                int groupInt = Integer.parseInt(groupNum);
                                IoSession session = sessionMap.get(sessionItem);
                                int fileSize1 = main.getFileSize();
                                String fileName1 = main.getFileName();
                                byte order1 = (byte)0x09;
                                byte commandWord1 =(byte)0x8F;
                                int packetSize = splitBytes.size();
                                byte[] packet = ServerUtil.checkReady( order1, commandWord1, fileSize1, fileName1,packetSize,groupInt);
                                session.write(IoBuffer.wrap(packet));
                                Thread.sleep(1000);
                                if(ServerUtil.checkOk()){
                                    ServerUtil.reset();
                                    ServerUtil.resetEnd();
                                    byte order = (byte)0x09;
                                    byte commandWord =(byte)0x8F;
                                    for (int i = 0; i < packetSize; i++) {
                                        int packetNum = i+1;
                                        byte[] data = splitBytes.get(i);
                                        byte[] packet1 = ServerUtil.sendPacket(groupInt, order, commandWord, packetSize, packetNum, data);
                                        session.write(IoBuffer.wrap(packet1));
                                        Thread.sleep(500);
                                        if(!ServerUtil.isEnd()){
                                            if(ServerUtil.checkOk()){
                                                propertyChangeSupport.firePropertyChange("taskMessage",null,"第"+i+"包响应OK");
                                            }else{
                                                propertyChangeSupport.firePropertyChange("taskMessage",null,"第"+i+"包响应失败");
                                            }
                                            ServerUtil.reset();
                                        }else{
                                            propertyChangeSupport.firePropertyChange("taskMessage",null,"数据传输结束");
                                            ServerUtil.resetEnd();
                                        }
                                    }
                                }
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                }
            }
        });
    }

    public void resetGateway(){
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==1){

                }
            }
        });
    }
    public void registerTaskMessage(JTextArea jTextArea){
        propertyChangeSupport.addPropertyChangeListener(new TextAreaListenTaskMessage(jTextArea));
    }
}
