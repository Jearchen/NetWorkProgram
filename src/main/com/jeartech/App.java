package com.jeartech;

import com.jeartech.Component.*;
import com.jeartech.Listener.JCMultiListener;
import com.jeartech.util.DateUtil;
import com.jeartech.util.LogUtil;
import com.jeartech.util.Server;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.PropertyConfigurator;

// import org.apache.log4j.PropertyConfigurator;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class App {
    private LogUtil logger = new LogUtil(App.class.getName());
    private  void createUI() throws Exception{
        logger.info("creating UI ");
        //获取屏幕大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int frameWidth = (int)(screenSize.width*0.5);
        int frameHeight = (int)(screenSize.height*0.5);
        
        JCFrame mainFrame = new JCFrame(frameWidth,frameHeight,"网口升级网关程序",this);
        mainFrame.setName("mainFrame");
        
        JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(1,2,10,10);
        panel.setLayout(layout);

        JCTree jcTree = new JCTree();
        jcTree.setName("socketTree");
        jcTree.addPropertyChangeListener("serverChange",new JCMultiListener(mainFrame));
        jcTree.addSocketNode(new DefaultMutableTreeNode("wait for socket"));
        panel.add(jcTree);

        JPanel panel1 = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        // UIManager manager = new UIManager().
        JCButtonComponent choseFileBtn = new JCButtonComponent(45, 50, "选择");
        choseFileBtn.setFont(new Font("JetBrainsMono",Font.PLAIN,16));
        panel1.add(choseFileBtn);
        
        // JCFileDialog jcFileDialog = new JCFileDialog(mainFrame);
        // jcFileDialog.setDefaultPath();
        // choseFileBtn.showFileDialog(jcFileDialog);
        JFileChooser chooser = new JFileChooser();
        
        choseFileBtn.addPropertyChangeListener(new JCMultiListener(mainFrame));
        
        JCButtonComponent excuteBtn = new JCButtonComponent(45, 50, "升级");
        excuteBtn.setFont(new Font("JetBrainsMono",Font.PLAIN,16));
        panel1.add(excuteBtn);
        excuteBtn.executeTask();

        JCButtonComponent resetBtn = new JCButtonComponent(45, 50, "复位");
        resetBtn.setFont(new Font("JetBrainsMono",Font.PLAIN,16));
        panel1.add(resetBtn);
        resetBtn.resetGateway();

        JCTextField filePathLocation = new JCTextField(100,50,10);
        filePathLocation.setName("fileLocation");
        filePathLocation.addPropertyChangeListener("filePathChange",new JCMultiListener(mainFrame));

        JTextArea logArea = new JTextArea(DateUtil.getNowDate() +": "+"等待网关连接\n");
        logArea.setFont(new Font("JetBrainsMono",Font.PLAIN,16));
        logArea.setName("logArea");
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setColumns(30);
        logArea.setBounds(10,10,200,400);
        logArea.setCaretPosition(logArea.getDocument().getLength());
        JScrollPane logPanel = new JScrollPane(logArea);
        logPanel.setSize(200,200);

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

        panel1.setLayout(gridBagLayout);
        panel.add(panel1);
        mainFrame.add(panel);

        // mainFrame.setUndecorated(true);//设置无边框
        //设置居中
        int locationX = (int)(screenSize.width/2-frameWidth/2);
        int locationY =  (int)(screenSize.height/2-frameHeight/2);
        mainFrame.setLocation(locationX,locationY);
        //设置LAF
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
       //设置可见
        mainFrame.setVisible(true);
    }
    public static void main(String[] args)throws Exception{
        //start app thread safely
        SwingUtilities.invokeLater(()->{
            try{
                App app = new App();
                app.createUI();
                new Server(10099);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public static void initLog() {
        try{
            String envs = System.getProperty("user.dir");
            FileInputStream in = new FileInputStream(new File(envs + "\\src\\main\\resources\\log4j.properties"));
            Properties prop = new Properties();
            prop.load(in);
            in.close();
            PropertyConfigurator.configure(prop);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
