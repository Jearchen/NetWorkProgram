package com.jeartech.util;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.jeartech.App;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;

public class Server extends IoHandlerAdapter{
    private LogUtil logger = new LogUtil(Server.class.getName());
    private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private static NioSocketAcceptor server = new NioSocketAcceptor();
    private static IoSession workSession;
    private static boolean responseFlag =false;
    private static boolean endFlag = false;
    private static byte command =(byte)0x8f;
    private static Map<String,IoSession> sessionMap = new ConcurrentHashMap<>();
    public Server(int localPort) {
        server.setHandler(this);
        server.getSessionConfig().setReadBufferSize(2024*2024);
        server.getSessionConfig().setBothIdleTime(12);
        bind(localPort);
    }

    public void bind(int port){
        try{
            server.bind(new InetSocketAddress(port));
        }catch(Exception e){
            logger.error("本地端口绑定失败，请更换端口号");
            e.printStackTrace();
        }
    }

    public static Map<String,IoSession> getSessionMap(){
        return sessionMap;
    }

    public  void addPropertyChangeListener(PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    public  void removePropertyChangerListener(PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(listener);
    }


    @Override
    public void sessionOpened(IoSession session) throws Exception {

    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        String rawIP = session.getRemoteAddress().toString();
        rawIP = rawIP.split("/")[1];
        rawIP = rawIP.split(":")[0];
        sessionMap.put(rawIP,session);
        logger.info("进入连接,ip 地址："+rawIP);
        //更新树形列表
        propertyChangeSupport.firePropertyChange("socketTriggerTree","1",rawIP);
        //更新日志
        propertyChangeSupport.firePropertyChange("serverLogger","2",ConnectionEventType.newConnection.getCode());
    }


    @Override
    public void sessionClosed(IoSession session) throws Exception {
        String rawIP = session.getRemoteAddress().toString();
        rawIP = rawIP.split("/")[1];
        rawIP = rawIP.split(":")[0];
        propertyChangeSupport.firePropertyChange("connectChange","1",ConnectionEventType.closeConnection.getCode());
        propertyChangeSupport.firePropertyChange("logServer"+"-"+rawIP,"2",ConnectionEventType.closeConnection.getCode());
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {

    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
//            if(!isEffectConnect(session))
//                return;
        logger.info("收到消息");
        String rawIP = session.getRemoteAddress().toString();
        rawIP = rawIP.split("/")[1];
        rawIP = rawIP.split(":")[0];
        IoBuffer buffer = (IoBuffer) message;
        buffer.order(ByteOrder.BIG_ENDIAN);
        int newBytesLen = buffer.limit();
        byte[] newBytes = new byte[newBytesLen];
        buffer.get(newBytes, buffer.position(), newBytesLen);
        propertyChangeSupport.firePropertyChange("receiveBuffer"+"-"+rawIP,null,byte2Hex(newBytes));
        if(isEndPacket(newBytes))
            endFlag =true;
        if(isEffectPacket(newBytes))
            responseFlag =true;
    }

    private String byte2Hex(byte[] bytes){
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for (byte b : bytes) {
            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
            buf[index++] = HEX_CHAR[b & 0xf];
        }

        return new String(buf);
    }



    // private boolean isEffectConnect(IoSession A){
    //     boolean result =false;
    //     String aIp = A.getRemoteAddress().toString();
    //     aIp = aIp.split("/")[1];
    //     aIp = aIp.split(":")[0];
    //     return result;
    // }

    private boolean isEffectPacket(byte[] response){
        boolean result = false;
        int lenth = response.length;
        if(lenth==10&&(response[0]==(byte)0x55)&&(response[1]==(byte)0xAA)&&(response[5]==(byte)0x8f))
        {
            if((response[6]==(byte)0x00)&&(response[7]==(byte)0x00)){
                result = true;
            }
        }
        return result;
    }
    private boolean isEndPacket(byte[] response){
        boolean result = false;
        int lenth = response.length;
        if(lenth==10&&(response[0]==(byte)0x55)&&(response[1]==(byte)0xAA)&&(response[5]==(byte)0x8f))
        {
            if((response[6]==(byte)0xFF)&&(response[7]==(byte)0xFF)){
                result = true;
            }
        }
        return result;
    }


    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        IoBuffer buffer = (IoBuffer) message;
        buffer.order(ByteOrder.BIG_ENDIAN);
        int newBytesLen = buffer.limit();
        byte[] newBytes = new byte[newBytesLen];
        buffer.get(newBytes, buffer.position(), newBytesLen);
        logger.info("发送报文"+byte2Hex(newBytes));
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    
    public static void setWorkSession(IoSession source){
        workSession = source;
    }
    public static boolean checkOk(){
        return responseFlag;
    }

    public static boolean isEnd(){return endFlag;}

    public static void reset(){
        responseFlag = false;
    }

    public static void resetEnd(){
        endFlag =false;
    }

    public static byte[]  sendPacket(int groupNum,byte order,byte commandWord,int packetSize,int packetNum,byte[] dataBlock){
        byte[] requestMap = new byte[10+dataBlock.length];
        initCommonHead(requestMap);
        requestMap[2] =order;
        requestMap[3] =commandWord;
        requestMap[4] = (byte)(groupNum&0xff);
        requestMap[5] = (byte)0x01;
        requestMap[6] = (byte)(packetSize&0xff);
        requestMap[7] = (byte)(packetNum&0xff);
        System.arraycopy(dataBlock,0,requestMap,8,dataBlock.length);
        requestMap[dataBlock.length+8] =(byte)0x99;
        requestMap[dataBlock.length+9] =(byte)0x99;
        return requestMap;
    }

    public static byte[] checkReady(byte order,byte commandWord ,long fileSize,String fileName,int packetSize,int groupNum){
        int position = fileName.indexOf('.');
        String fileNameLeft = fileName.substring(0, position);
        char[] charArray = fileNameLeft.toCharArray();
        byte[] requestMap = new byte[42];
        initCommonHead(requestMap);
        requestMap[2] =order;
        requestMap[3] =commandWord;
        requestMap[4] =(byte)groupNum;
        requestMap[5] =(byte)0x01;
        requestMap[6] =(byte)packetSize;
        requestMap[7] = (byte)0x00;
        str2ascii(requestMap,charArray);
        longTo8byte(fileSize,requestMap,24);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR)-2000;
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DATE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        requestMap[32] = int2BCD(year);
        requestMap[33] = int2BCD(month);
        requestMap[34] = int2BCD(day) ;
        requestMap[35] = int2BCD(hour);
        requestMap[36] = int2BCD(minute);
        requestMap[37] = int2BCD(second);
        requestMap[38] = 0;
        requestMap[39] = 0;
        requestMap[40] =(byte)0x99;
        requestMap[41] =(byte)0x99;
        return requestMap;
    }
    private static int str2ascii(byte[] requestMap,char[] fileName){
        int length = fileName.length;
        if(length==0)
            return 0;
        if(length>16)
            length = 16;
        for (int i = 0; i < length; i++) {
            requestMap[i+8] = (byte)fileName[i];
        }
        if(length<16){
            int cursor = 0;
            while((cursor+length)<16){
                requestMap[cursor+length+8]=0;
                cursor++;
            }
        }
        return length;
    }
    public static void longTo8byte(long source,byte[] requestMap,int offset){
        String sourceStr = String.valueOf(source);
        char[] target = new char[8];
        char[] sourceArr = sourceStr.toCharArray();
        if(sourceArr.length<8){
            int len = sourceArr.length;
            System.arraycopy(sourceArr,0,target,0,len);
            int cursor = 0;
            while((cursor+len)<8){
                target[cursor+len]=0;
                cursor++;
            }
        }
        requestMap[offset] = (byte)target[0];
        requestMap[offset+1] = (byte)target[1];
        requestMap[offset+2] = (byte)target[2];
        requestMap[offset+3] = (byte)target[3];
        requestMap[offset+4] = (byte)target[4];
        requestMap[offset+5] = (byte)target[5];
        requestMap[offset+6] = (byte)target[6];
        requestMap[offset+7] = (byte)target[7];
    }
    public static byte int2BCD(int num){
        int left = num/10;
        int right = num%10;
        byte data = (byte)(left<<4|right);
        return data;
    }

    private static byte[] initCommonHead(byte[] source){
        source[0] = (byte)0x55;
        source[1] = (byte)0xAA;
        return source;
    }
}
