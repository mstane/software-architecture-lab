package org.sm.lab.mybooks.client.util;

import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;


public class Utility {

    private static DateTimeFormat formatter = DateTimeFormat.getFormat("dd MMM yyyy hh:mm:ss aa");
    
    public static String getTimeString(Date time) {
        return formatter.format(time);
    }
    
}
