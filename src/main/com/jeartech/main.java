package com.jeartech;

import com.jeartech.Component.*;
import com.jeartech.util.DateUtil;
import com.jeartech.util.ServerUtil;


import javax.swing.*;
import javax.swing.plaf.basic.BasicIconFactory;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class main {
    private static String filePath;
    private static GridLayout layout = new GridLayout(1,2,10,10);

    private static List<byte[]> splitArrs = new ArrayList<>();

    private static int fileSize;
    private static String fileName;

    public static void main(String[] args)  {
        try{
            JCFrame jcFrame = new JCFrame(480,360,"");
            JFrame rawFrame = jcFrame.getRawFrame();
            rawFrame.setTitle("网口升级网关程序");
            JPanel panel = new JPanel();
            panel.setLayout(layout);
            ServerUtil server = new ServerUtil();


            JcFileDialog jcFileDialog = new JcFileDialog(200,300,rawFrame,"请选择升级程序");
            jcFileDialog.setDefaultPath();
            jcFrame.registerFileDialog(jcFileDialog);

            JcTree jcTree = new JcTree(200,300,"连接列表",panel);
            JPopupMenu menuAll = new JPopupMenu();
            JMenu menu1 = new JMenu("属性");
            menu1.setIcon(BasicIconFactory.getMenuArrowIcon());
            menuAll.add(menu1);
            jcTree.registerConnection(server);
            jcTree.setPopupMenu(menuAll);


            JPanel panel1 = new JPanel();
            panel1.setSize(200,100);
            GridBagLayout gridBagLayout = new GridBagLayout();
            GridBagConstraints constraints = new GridBagConstraints();

            JcButtonComponent choseFileBtn = new JcButtonComponent(45, 50, "选择",panel1);
            choseFileBtn.showFileDialog(jcFileDialog);

            JcButtonComponent excuteBtn = new JcButtonComponent(45, 50, "升级",panel1);
            excuteBtn.executeTask();

            JcButtonComponent resetBtn = new JcButtonComponent(45, 50, "复位",panel1);
            resetBtn.resetGateway();

            JcTextField filePathLocation = new JcTextField(100,50,10);
            jcFrame.registerTextField(jcFileDialog,filePathLocation);


            JTextArea logArea = new JTextArea(DateUtil.getNowDate() +":"+"等待网关连接\n");
            logArea.setLineWrap(true);
            logArea.setWrapStyleWord(true);
            logArea.setColumns(30);
            logArea.setBounds(10,10,200,400);
            logArea.setCaretPosition(logArea.getDocument().getLength());
            JScrollPane logPanel = new JScrollPane(logArea);
            logPanel.setSize(200,200);
            jcFrame.logServerSession(server,logArea);
            jcFrame.logChooseFile(jcFileDialog,logArea);
            excuteBtn.registerTaskMessage(logArea);
            jcFrame.subscribeTaskMessage(server,logArea);


            panel1.add(filePathLocation.getjTextField(),0);
            panel1.add(logPanel);

            constraints.fill=GridBagConstraints.BOTH;constraints.gridwidth=3;constraints.gridheight=1;
            constraints.insets= new Insets(5,5,5,5);constraints.weightx=1;constraints.weighty=0.1;
            constraints.gridx=0;constraints.gridy=0;
            gridBagLayout.setConstraints(filePathLocation.getjTextField(),constraints);

            constraints.fill=GridBagConstraints.BOTH;constraints.gridwidth=1;constraints.gridheight=1;
            constraints.insets= new Insets(5,5,5,5);constraints.weightx=0.3;constraints.weighty=0.1;
            constraints.gridx=0;constraints.gridy=1;
            gridBagLayout.setConstraints(choseFileBtn.createOneButton(),constraints);

            constraints.fill=GridBagConstraints.BOTH;constraints.gridwidth=1;constraints.gridheight=1;
            constraints.insets= new Insets(5,5,5,5);constraints.weightx=0.3;constraints.weighty=0.1;
            constraints.gridx=1;constraints.gridy=1;
            gridBagLayout.setConstraints(excuteBtn.createOneButton(),constraints);

            constraints.fill=GridBagConstraints.BOTH;constraints.gridwidth=1;constraints.gridheight=1;
            constraints.insets= new Insets(5,5,5,5);constraints.weightx=0.3;constraints.weighty=0.1;
            constraints.gridx=2;constraints.gridy=1;
            gridBagLayout.setConstraints(resetBtn.createOneButton(),constraints);

            constraints.fill=GridBagConstraints.BOTH;constraints.gridwidth=3;constraints.gridheight=1;
            constraints.insets= new Insets(5,5,5,5);constraints.weightx=2;constraints.weighty=2;
            constraints.gridx=0;constraints.gridy=2;
            gridBagLayout.setConstraints(logPanel,constraints);

            logArea.setVisible(true);
            panel1.setLayout(gridBagLayout);
            panel.add(panel1);
            rawFrame.add(panel);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            jcFrame.setVisible(true);
            server.createInstance(10099);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<byte[]> getSplitArrs() {
        return main.splitArrs;
    }

    public static int getFileSize(){
        return main.fileSize;
    }

    public static String getFileName(){
        return main.fileName;
    }

    public static void setFileSize(int fileSize) {
        main.fileSize = fileSize;
    }

    public static void setFileName(String fileName) {
        main.fileName = fileName;
    }

    public static void setSplitArrs(List<byte[]> splitArrs) {
        main.splitArrs = splitArrs;
    }
}
