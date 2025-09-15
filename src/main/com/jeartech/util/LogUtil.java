package com.jeartech.util;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * using jul define self log Level familiar to log4j.
 */
public class LogUtil{
    private Logger logger;
    private String nameOfClass;

    public LogUtil(String className){
        nameOfClass = className;
        logger = Logger.getLogger(className);
    }
    public void info(String message){
        logger.log(SelfLevel.INFO,"source:"+nameOfClass+"-"+"date:"+new Date()+"-"+"content:"+message);
    }
    public void warn(String message){
        logger.log(SelfLevel.WARN,"source:"+nameOfClass+"-"+"date:"+new Date()+"-"+"content:"+message);
    }
    public void error(String message){
        logger.log(SelfLevel.ERROR,"source:"+nameOfClass+"-"+"date:"+new Date()+"-"+"content:"+message);
    }

    public static class SelfLevel extends Level{
        public static final SelfLevel INFO = new SelfLevel("INFO",1000);
        public static final SelfLevel WARN = new SelfLevel("WARN",2000);
        public static final SelfLevel ERROR = new SelfLevel("ERROR",3000);

        public SelfLevel(String name,int value){
            super(name,value,null);
        }
    }
}
