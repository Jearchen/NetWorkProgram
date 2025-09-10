package com.jeartech;

import com.jeartech.Component.*;
import com.jeartech.util.DateUtil;
import com.jeartech.util.ServerUtil;


import javax.swing.*;
import javax.swing.plaf.basic.BasicIconFactory;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static String filePath;
    
    private static List<byte[]> splitArrs = new ArrayList<>();

    private static int fileSize;
    private static String fileName;

    // private JCFrame mainFrame;

    private  void createUI() throws Exception{
        JCFrame mainFrame = new JCFrame(480,360,"网口升级网关程序",this);
        
        JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(1,2,10,10);
        panel.setLayout(layout);
        

        JCFileDialog jcFileDialog = new JCFileDialog(mainFrame);
        jcFileDialog.setDefaultPath();
        // mainFrame.registerFileDialog(jcFileDialog);

        JCTree jcTree = new JCTree(200,300);
        JPopupMenu menuAll = new JPopupMenu();
        JMenu menu1 = new JMenu("属性");
        menu1.setIcon(BasicIconFactory.getMenuArrowIcon());
        menuAll.add(menu1);

        // jcTree.registerConnection(server);
        // jcTree.setComponentPopupMenu(menuAll);


        JPanel panel1 = new JPanel();
        panel1.setSize(200,100);
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        JCButtonComponent choseFileBtn = new JCButtonComponent(45, 50, "选择");
        panel1.add(choseFileBtn);
        choseFileBtn.showFileDialog(jcFileDialog);

        JCButtonComponent excuteBtn = new JCButtonComponent(45, 50, "升级");
        panel1.add(excuteBtn);
        excuteBtn.executeTask();

        JCButtonComponent resetBtn = new JCButtonComponent(45, 50, "复位");
        panel1.add(resetBtn);
        resetBtn.resetGateway();

        JCTextField filePathLocation = new JCTextField(100,50,10);
        // mainFrame.registerTextField(jcFileDialog,filePathLocation);


        JTextArea logArea = new JTextArea(DateUtil.getNowDate() +":"+"等待网关连接\n");
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setColumns(30);
        logArea.setBounds(10,10,200,400);
        logArea.setCaretPosition(logArea.getDocument().getLength());
        JScrollPane logPanel = new JScrollPane(logArea);
        logPanel.setSize(200,200);


        // mainFrame.logServerSession(server,logArea);
        // mainFrame.logChooseFile(jcFileDialog,logArea);
        // excuteBtn.registerTaskMessage(logArea);
        // mainFrame.subscribeTaskMessage(server,logArea);


        panel1.add(filePathLocation,0);
        panel1.add(logPanel);

        constraints.fill=GridBagConstraints.BOTH;constraints.gridwidth=3;constraints.gridheight=1;
        constraints.insets= new Insets(5,5,5,5);constraints.weightx=1;constraints.weighty=0.1;
        constraints.gridx=0;constraints.gridy=0;
        gridBagLayout.setConstraints(filePathLocation,constraints);

        constraints.fill=GridBagConstraints.BOTH;constraints.gridwidth=1;constraints.gridheight=1;
        constraints.insets= new Insets(5,5,5,5);constraints.weightx=0.3;constraints.weighty=0.1;
        constraints.gridx=0;constraints.gridy=1;
        gridBagLayout.setConstraints(choseFileBtn,constraints);

        constraints.fill=GridBagConstraints.BOTH;constraints.gridwidth=1;constraints.gridheight=1;
        constraints.insets= new Insets(5,5,5,5);constraints.weightx=0.3;constraints.weighty=0.1;
        constraints.gridx=1;constraints.gridy=1;
        gridBagLayout.setConstraints(excuteBtn,constraints);

        constraints.fill=GridBagConstraints.BOTH;constraints.gridwidth=1;constraints.gridheight=1;
        constraints.insets= new Insets(5,5,5,5);constraints.weightx=0.3;constraints.weighty=0.1;
        constraints.gridx=2;constraints.gridy=1;
        gridBagLayout.setConstraints(resetBtn,constraints);

        constraints.fill=GridBagConstraints.BOTH;constraints.gridwidth=3;constraints.gridheight=1;
        constraints.insets= new Insets(5,5,5,5);constraints.weightx=2;constraints.weighty=2;
        constraints.gridx=0;constraints.gridy=2;
        gridBagLayout.setConstraints(logPanel,constraints);

        logArea.setVisible(true);
        panel1.setLayout(gridBagLayout);
        panel.add(panel1);
        mainFrame.add(panel);
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        mainFrame.setVisible(true);
    }

    private void  startServer() throws Exception{
        ServerUtil server = new ServerUtil();
        registerServerEvent();
        server.createInstance(10099);
    }

    private void registerServerEvent(){
        
    }

    public static void main(String[] args)throws Exception  {
        //start app thread safely
        SwingUtilities.invokeLater(()->{
            try{
                App app = new App();
                app.createUI();
                app.startServer();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        
    }
}
