package cn.com.wind.wdp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    static SimpleDateFormat simpleDateFormat = null;

    public static String getNowTime(Date date) {
        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(date);
    }

    public static String getNowDay(Date date) {
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(date);
    }

    public static String getToday(Date date) {
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(date) + "000000";
    }

    public static String getNextDay(Date date) {
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(date.getTime() + 1000 * 60 * 60 * 24) + "000000";
    }

}
