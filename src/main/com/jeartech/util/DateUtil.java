package com.jeartech.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class DateUtil {
    public static String getNowDate(){
        Date now = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String nowStr = format.format(now);
        return nowStr;
    }
}
