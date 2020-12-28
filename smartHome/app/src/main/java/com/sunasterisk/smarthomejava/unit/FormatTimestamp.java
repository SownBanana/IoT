package com.sunasterisk.smarthomejava.unit;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatTimestamp {
    public static String formatTimestamp(int timestamp){
        Timestamp stamp = new Timestamp(timestamp);
        Date date = new Date(stamp.getTime());
        return new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(date);
    }
}
