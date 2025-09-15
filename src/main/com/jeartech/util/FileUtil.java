package com.jeartech.util;

import java.io.*;
import java.util.ArrayList;

public class FileUtil {
    private static FileInputStream inputStream;

    private static FileOutputStream outputStream;

    private static File source;

    private static byte[] rawByte = new byte[20000];

    private static ArrayList<byte[]> splitBytes ;

    public FileUtil(){
    }

    public static void processFile(File file){
        source = file;
    }
    public static byte[] getBytes() throws IOException {
        inputStream = new FileInputStream(source);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.read(rawByte);
        return rawByte;
    }

    public static ArrayList<byte[]> getSplitBytes(int splitSize) throws Exception {
        inputStream = new FileInputStream(source);
        rawByte = new byte[inputStream.available()];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.read(rawByte);
        int dataLen = rawByte.length;
        if(dataLen==0)
            return null;
        if(!(splitSize>1))
            throw new Exception("分片必须大于1");
        int packetSize = dataLen/splitSize+1;
        splitBytes = new ArrayList<>(packetSize);

        int readSize =0;
        for (int i = 0; i < packetSize; i++) {
            readSize = splitSize;
            byte[] splitParts =new byte[splitSize];
            if(i==packetSize-1){
                readSize = dataLen-splitSize*(packetSize-1);
                splitParts = new byte[readSize];
            }
            System.arraycopy(rawByte,i*splitSize,splitParts,0,readSize);
            splitBytes.add(splitParts);
        }
        return splitBytes;
    }

}
