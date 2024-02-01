package com.jeartech.Listener;

import com.jeartech.main;
import com.jeartech.util.FileUtil;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;

public class FrameListenDialogFile implements PropertyChangeListener {

    private static List<byte[]> splitBytes;


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("fileDialog1")){
            try {
                if(evt.getNewValue()==null)
                    return;
                File resourceFile = new File((String) evt.getNewValue());
                FileUtil.processFile(resourceFile);
                splitBytes = FileUtil.getSplitBytes(512);
                main.setSplitArrs(splitBytes);
                main.setFileName(resourceFile.getName());
                main.setFileSize((int)resourceFile.length());
            } catch (Exception exp) {
                throw new RuntimeException(exp);
            }
        }
    }





}
