package com.jeartech.config;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

public class LogRollingAppender extends DailyRollingFileAppender{

    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        // TODO Auto-generated method stub
        return super.isAsSevereAsThreshold(priority);
    }
}
